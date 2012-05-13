// Synchronisation.cs created with MonoDevelop
// User: stefant at 16:22 01/31/2009
//

//#define SM_MESSAGES
//#define SM_SUCCESS
//#define SM_FAILURE
//#define SM_MISC

using System;
using System.Collections.Generic;

using RosCS.AlicaEngine;

using C5;

namespace Alica
{
	
	public class Synchronisation
	{
		
		
		protected SyncModul syncModul = null;
		
		
		
		protected SyncTransition syncTransition = null;
		protected int myID = -1;

		protected ulong lastTalkTime = 0;
		protected SyncData lastTalkData = null;

		protected ulong syncStartTime =0;


		protected bool readyForSync = false;
		
		protected ulong lastTick = 0;
		
		protected List<SyncReady> receivedSyncReadys = new List<SyncReady>();

		protected List<long> connectedTransitions = new List<long>();

		protected RunningPlan runningPlan = null;

		//rows where robots believe in their conditions
		protected List<SyncRow> rowsOK = new List<SyncRow>();

		protected SyncRow myRow = null;
		
		protected List<SyncRow> syncMatrix = new List<SyncRow>();

		
		

		
		//callback method from syncmodul to notify when this synchronisation fails
		//private SynchronisationFailed syncFailed = null;

		private object lockObj = new Object();

		
		
		public void SetTick(ulong now) {
			this.lastTick = now;
		}
		
		public Synchronisation(int myID, SyncTransition st,SyncModul sm) {
			
			this.syncTransition = st;
			this.myID = myID;
			

			this.syncStartTime = RosCS.RosSharp.Now()/1000000UL;

			foreach (Transition t in st.InSync)
			{
				connectedTransitions.Add(t.Id);
			}
			this.syncModul = sm;
			
		}

		public void ChangeOwnData (long transitionID, bool conditionHolds)
		{
#if(SM_MISC)
			Console.WriteLine("CHOD: ElapsedTime: " + ((RosCS.RosSharp.Now()/1000000UL) - this.syncStartTime));
#endif

			if(!conditionHolds)
			{
				//my condition does not hold => not ready for syncing
				this.readyForSync = false;
			}
			
			SyncData sd = new SyncData();
			sd.RobotID = this.myID;
			sd.TransitionID = transitionID;
			sd.ConditionHolds = conditionHolds;
			sd.Ack = false;

			bool sendTalk = true;

			lock(this.lockObj)
			{
			
				if(myRow != null)
				{
					if(!sd.Equals(myRow.SyncData))
					{
						//my sync row has changed
						myRow.SyncData = sd;
						myRow.ReceivedBy.Clear();
						this.readyForSync = false;
						myRow.ReceivedBy.Add(this.myID);
					}
					else
					{
#if(SM_MISC)
						Console.WriteLine("ChangeOwnData: SendTalk==false");
#endif
						sendTalk = false;
					}
				}
				else //init my row
				{
					SyncRow sr = new SyncRow(sd);
					sr.ReceivedBy.Add(this.myID);
					this.myRow = sr;
					
					this.syncMatrix.Add(sr);
				}				
			}
			
#if(SM_MISC)
			Console.WriteLine("\nMatrix: ChangeOwnData");
			printMatrix();
#endif

			if(sendTalk)
			{
				if(IsSyncComplete())
				{
#if(SM_SUCCESS)
					Console.WriteLine("ChangedOwnData: Synctrans " + this.syncTransition.Id + " ready");
#endif
					SendSyncReady();
					this.readyForSync = true;
				}
				else
				{				
					SendTalk(sd);
				}
			}
		}

		//resend lastTalk, test for failure on synchronisation timeout and stop sync on state change
		public bool IsValid(ulong curTick)
		{
			bool stillActive = (this.lastTick + 2 >= curTick);
			

			if(!stillActive){
				//notify others if i am part of the synchronisation already (i.e. have an own row)
				if(myRow != null)
				{
					if(myRow.SyncData != null)	{
						//myRow.SyncData.TransitionID
						myRow.SyncData.ConditionHolds = false;
						
						SendTalk(myRow.SyncData);
					}
				}
				
				return false;
			}
			
			ulong now = RosCS.RosSharp.Now()/1000000UL;

			if(this.lastTalkTime != 0) //talked already
			{
			
#if(SM_FAILURE)
				Console.WriteLine("TestTimeOut on Sync: " + syncTransition.Id + ": " + calc);
#endif				
				if( (now > syncTransition.TalkTimeOut+this.lastTalkTime) && ! this.readyForSync ) {
					if(this.myRow != null)	{
						SendTalk(this.myRow.SyncData);
					}
				}
			}

			
#if(SM_FAILURE)
			Console.WriteLine("Synchronisation: TestTimeOut(): syncStarTime " + this.syncStartTime + " diff: " + syncDiff );
#endif

			if(this.syncTransition.FailOnSyncTimeOut) {
				if(now > this.syncTransition.SyncTimeOut+this.syncStartTime)
				{
#if(SM_FAILURE)
					Console.WriteLine("Synchronisation: TestTimeOut() sync failed");
#endif
					return false;
				}
			}
			
			return true;
		}

		protected void SendTalk(SyncData sd)
		{
			SyncTalk talk = new SyncTalk();			
			talk.SyncData.Add(sd);

			this.lastTalkTime = RosCS.RosSharp.Now()/1000000UL;
			
#if(SM_MESSAGES)
			Console.WriteLine("Sending Talk TID: "+sd.TransitionID);			
#endif
			this.syncModul.SendSyncTalk(talk);			
		}

		public bool IntegrateSyncTalk(SyncTalk talk, ulong curTick) {
			
			if(this.readyForSync)
			{
				//do not integrate talks if we believe the sync is already finished
				return true;
			}
			
			bool isSynching = (this.lastTick + 2 >= curTick);
			
			if(!isSynching)	{
				//do not accept messages (and send uneccessary ACKs) if we are not in a state for sync
			 	return false;
			}
			
#if(SM_MESSAGES)
			Console.WriteLine("Integrate synctalk in synchronisation");
			Console.WriteLine("ST: ElapsedTime: " + ((RosCS.RosSharp.Now()/1000000UL) - this.syncStartTime));
#endif
			foreach (SyncData sd in talk.SyncData)
			{
#if(SM_MESSAGES)
				Console.WriteLine("syncdata for transID: " + sd.TransitionID);
#endif

				lock (this.lockObj)	{				
					SyncRow rowInMatrix = null;
					foreach (SyncRow row in this.syncMatrix) {
#if(SM_MESSAGES)
						Console.WriteLine("ROW SD: " + row.SyncData.RobotID +  " " + row.SyncData.TransitionID + " " + row.SyncData.ConditionHolds + " " + row.SyncData.Ack);
						Console.WriteLine("CUR SD: " + sd.RobotID +  " " + sd.TransitionID + " " + sd.ConditionHolds + " " + sd.Ack);
#endif
						
						if(sd.Equals(row.SyncData))
						{			
							rowInMatrix = row;
							break;
						}
					}
					
					//if(rowInMatrix != null)
					if(rowInMatrix == null)
					{
#if(SM_MESSAGES)
						Console.WriteLine("NEW MATRIX row");
#endif
						SyncRow newRow = new SyncRow(sd);
						newRow.ReceivedBy.Add(talk.SenderID);
	
						syncMatrix.Add(newRow);
					}
					else {
#if(SM_MESSAGES)
						Console.WriteLine("Received by: " + talk.HostId);
#endif
						rowInMatrix.ReceivedBy.Add(talk.SenderID);
					}

					if(IsSyncComplete())	{
#if(SM_MESSAGES)
						Console.WriteLine("IntegrateSyncTalk: Synctrans " + this.syncTransition.Id + " ready");
#endif
						SendSyncReady();
						this.readyForSync = true;
					}
					else{
						//always reset this in case someone revokes his commitment
						this.readyForSync = false;
					}
#if(SM_MESSAGES)
					Console.WriteLine("Matrix: IntSyncTalk");
					printMatrix();
#endif

					//late acks...
					if(this.readyForSync) {
						if(AllSyncReady())
						{
#if(SM_SUCCESS)
							Console.WriteLine("SyncDONE in Synchronisation (IntTalk): elapsed time: " + ((RosCS.RosSharp.Now()/1000000UL) - this.syncStartTime));
#endif
							//notify syncmodul
							this.syncModul.SynchronisationDone(this.SyncTransition);
							
						}
					}
				}
			}
			
			return true;
		}

		protected void SendSyncReady()
		{
			//send own row again to be sure
			SendTalk(myRow.SyncData);
			
			SyncReady sr = new SyncReady();
			sr.SyncTransitionID = this.syncTransition.Id;			
			this.syncModul.SendSyncReady(sr);
						
		}

		protected bool IsSyncComplete()
		{
			//myRow needs to be acknowledged by all participants
			//every participant needs to believe in its condition
			//there must be at least one participant for every condition

			this.rowsOK.Clear();			

			//collect participants
			foreach(long transID in this.connectedTransitions)
			{
				SyncRow foundRow = null;

				lock(this.lockObj)
				{
				
					foreach(SyncRow row in this.syncMatrix)
					{
						if(row.SyncData.TransitionID == transID && row.SyncData.ConditionHolds)
						{
							foundRow = row;
							break;
						}
					}
				}

				if(foundRow == null) //no robot for transition
				{
					return false;
				}

				if (!rowsOK.Contains(foundRow))	{
					this.rowsOK.Add(foundRow);
				}
			}

			//check for acks in own row
			foreach(SyncRow row in this.rowsOK)
			{
				//there must be an ack on the ownRow from every participant
				if(! myRow.ReceivedBy.Contains(row.SyncData.RobotID))
				{
					return false;
				}
			}

			return true;
			
		}

		public void IntegrateSyncReady(SyncReady ready)
		{
			//every robot that has acknowleged my row needs to send me a SyncReady
			bool found = false;
			foreach(SyncReady sr in this.receivedSyncReadys) {				
				if(sr.SenderID == ready.SenderID) {
					found = true;
					break;
				}
			}

			if(!found)	{
				this.receivedSyncReadys.Add(ready);
			}
#if(SM_MESSAGES)
			Console.WriteLine("Matrix: IntSyncReady");
			printMatrix();
#endif

			//check if all robots are ready
			if(this.readyForSync)
			{
				if(AllSyncReady()) {
					//notify syncModul
#if(SM_SUCCESS)
					Console.WriteLine("SyncDONE in Synchronisation (IntReady): elapsed time: " + ((RosCS.RosSharp.Now()/1000000UL) - this.syncStartTime));
#endif
					this.syncModul.SynchronisationDone(this.syncTransition);
				}
			}
		}

		private bool AllSyncReady()	{
			//test if all robots who acknowledged myRow have sent a SyncReady
			foreach(int robotID in this.myRow.ReceivedBy)
			{
				if(robotID != myID) //we do not necessarily need an ack from ourselves
				{
					bool foundRobot = false;
					foreach(SyncReady sr in this.receivedSyncReadys) {
						if(sr.SenderID == robotID) {
							foundRobot = true;
							break;
						}
					}

					if(!foundRobot) //at least one robot is missing
					{
						return false;
					}
				}
			}
			return true;
		}

		private void printMatrix()
		{
			Console.WriteLine("\nMatrix:");

			lock(this.lockObj)
			{
			
				foreach(SyncRow row in this.syncMatrix)
				{
					string robots = "";
					foreach(int robotID in row.ReceivedBy)
					{
						robots += robotID + ", ";
					}
					Console.WriteLine("Row: " + row.SyncData.RobotID +  " " + row.SyncData.TransitionID + " " + row.SyncData.ConditionHolds + " " + row.SyncData.Ack + " RecvBy: " + robots);
				}
				Console.Write("ReceivedSyncreadys: ");
				foreach(SyncReady sr in this.receivedSyncReadys)
				{
					Console.Write(sr.SenderID + ", ");
				}
			}
			Console.WriteLine();
		}

		public SyncTransition SyncTransition
		{
			get {return this.syncTransition;}
			set {this.syncTransition = value;}
		}
	}
}
