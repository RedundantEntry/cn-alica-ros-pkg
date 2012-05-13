// SyncModul.cs created with MonoDevelop
// User: stefant at 14:46 07/30/2008

//#define SM_MESSAGES
//#define SM_SUCCESS
//#define SM_FAILURE
//#define SM_MISC

using System;
using System.Threading;
using System.Collections;
using System.Collections.Generic;

using Castor;
using RosCS;
using RosCS.AlicaEngine;

namespace Alica
{
	
	public class SyncModul : ISyncModul
	{
		
		//protected Thread ownThread;
		protected bool running = false;
		
		protected Node rosNode;
		protected AlicaEngine ae;
		IntPtr syncTalkPublisher;
		IntPtr syncReadyPublisher;
		protected int myId;
		protected ulong ticks;
		private Object lockObj = new Object();
		
		protected PlanRepository pr;
		
		protected Dictionary<SyncTransition,Synchronisation> synchSet;
		protected List<SyncTransition> synchedTransitions;
		
		public void Init() {
			this.synchSet = new Dictionary<SyncTransition, Synchronisation>();
			this.synchedTransitions = new List<SyncTransition>();
			ticks = 0;
			this.running = true;
			//Register handles
			this.rosNode = new Node("AlicaEngine");
			this.ae = AlicaEngine.Get();
			this.myId = this.ae.TO.GetOwnId();
			this.pr = this.ae.PR;
			
			
			syncTalkPublisher = this.rosNode.Advertise("SyncTalk",SyncTalk.TypeId,5);
			syncReadyPublisher = this.rosNode.Advertise("SyncReady",SyncReady.TypeId,5);
			
			this.rosNode.Subscribe("SyncTalk",OnSyncTalk,10);
			this.rosNode.Subscribe("SyncReady",OnSyncReady,10);
			
			//this.running = true;
			//this.ownThread = new Thread(new ThreadStart(this.Run));
			//this.ownThread.Start();
			
			
		}
		public void Tick() {
			List<Synchronisation> failedSyncs = new List<Synchronisation>();
			lock(this.lockObj) {
				foreach(Synchronisation s in this.synchSet.Values) {
					if(!s.IsValid(ticks)) {
						failedSyncs.Add(s);
					}
				}
				ticks++;			
				foreach(Synchronisation s in failedSyncs) {
					this.synchSet.Remove(s.SyncTransition);
				}
			}
		}
		
		
		public void Close() {
			this.running = false;
			if(this.rosNode != null) this.rosNode.Close();
			this.rosNode = null;			
			Console.WriteLine("Closed SyncModul");
			
			//this.ownThread = null;
		}

		public void SetSynchronisation(Transition trans, bool holds) {
			Synchronisation s;
			
			if (synchSet.TryGetValue(trans.SyncTransition,out s)) {
				s.SetTick(this.ticks);
				s.ChangeOwnData(trans.Id,holds);
			} else {
				s = new Synchronisation(myId,trans.SyncTransition,this);
				s.SetTick(this.ticks);
				s.ChangeOwnData(trans.Id,holds);
				lock(this.lockObj) {
					synchSet.Add(trans.SyncTransition,s);
				}
			}
			
			
		}
		public void SendSyncTalk(SyncTalk st) {
			if (!this.ae.MaySendMessages) return;
			st.SenderID = this.myId;
			this.rosNode.Send(syncTalkPublisher,st);
		}
		public void SendSyncReady(SyncReady sr) {
			if (!this.ae.MaySendMessages) return;
			sr.SenderID = this.myId;
			this.rosNode.Send(syncReadyPublisher,sr);
		}
		protected void SendAcks(List<SyncData> syncDataList) {
			if (!this.ae.MaySendMessages) return;
			SyncTalk st = new SyncTalk();
			st.SenderID = this.myId;
			st.SyncData = syncDataList;			
			this.rosNode.Send(syncTalkPublisher,st);			
		}
		
		/*public void UnsetSynchronisation(Plan p) {
		}
		public void UnsetSynchronisation(Transition t) {
		}
		public void UnsetSynchronisation(State s) {
		}*/
		public void SynchronisationDone(SyncTransition st) {
			#if(SM_SUCCESS)
			Console.WriteLine("\nSyncDONE in SYNCMODUL for synctransID: "+syncTransitionID+"\n");
			#endif
			
			
			lock(this.lockObj) {
				#if(SM_SUCCESS)
				Console.WriteLine("Remove synchronisation object for syntransID: " + syncTransitionID);
				#endif
				this.synchSet.Remove(st);
			}
			this.synchedTransitions.Add(st);
			#if(SM_SUCCESS)
			Console.WriteLine("SM: SYNC TRIGGER TIME: " + (RosCS.RosSharp.Now()/1000000UL));
			#endif
		}
		/*public void SynchronisationFailed(SyncTransition st) {
		}*/
		
		public bool FollowSyncTransition(Transition trans) {
			if (this.synchedTransitions.Contains(trans.SyncTransition)) {
				this.synchedTransitions.Remove(trans.SyncTransition);
				return true;
			}
			return false;
		}

		public void OnSyncTalk(SyncTalk st) {
			if (!this.running || st.SenderID == this.myId) return;
			if(ae.TO.IsRobotIgnored(st.SenderID)) return;
			#if(SM_MESSAGES)
			Console.WriteLine("\nHandle Synctalk in SyncModul");
			#endif
			List<SyncData> toAck = new List<SyncData>();
			
			foreach (SyncData sd in st.SyncData) {
				#if(SM_MESSAGES)
				Console.WriteLine("\tTransID: " + sd.TransitionID);					
				Console.WriteLine("\tRobotID: " + sd.RobotID);
				Console.WriteLine("\tCondition: " + sd.ConditionHolds);
				Console.WriteLine("\tACK: " + sd.Ack);
				#endif
				Transition trans = null;
				SyncTransition syncTrans = null;
				
				if(this.pr.Transitions.TryGetValue(sd.TransitionID,out trans)) {				
					if(trans.SyncTransition != null) {
						syncTrans = trans.SyncTransition;
					}
					else {
						string msg = "SyncModul: Transition " + trans.Id + " is not connected to a SyncTransition";
						this.rosNode.RosError(msg);
						return;
						//throw new Exception(msg);
					}
				}
				else {
					string msg = "SyncModul: Could not find Element for Transition with ID: " + sd.TransitionID;
					this.rosNode.RosError(msg);
					return;
					//throw new Exception(msg);
				}
				
				Synchronisation sync = null;				
				bool doAck = true;

				lock(this.lockObj) {
					
					if(this.synchSet.TryGetValue(syncTrans, out sync)) {
#if(SM_MESSAGES)
						Console.WriteLine("Synchronisation " + syncTrans.Id + " found");
#endif
						sync.IntegrateSyncTalk(st,this.ticks);
					}
					else {
#if(SM_MESSAGES)
						Console.WriteLine("SyncModul: HandleSyncTalk: create new synchronisation");
#endif												
						sync = new Synchronisation(this.myId, syncTrans,this);
						synchSet.Add(syncTrans, sync);	
						doAck = sync.IntegrateSyncTalk(st,this.ticks);
					}
				}
				if(!sd.Ack && st.SenderID == sd.RobotID && doAck) { //acknowledge pakets from robots about themselves				
					toAck.Add(sd);
				}
				
			}

			foreach(SyncData sd in toAck) {
				sd.Ack = true;
			}
			if(toAck.Count > 0)	{
				SendAcks(toAck);
			}
		}
		
		
		public void OnSyncReady(SyncReady sr) {
			if(!this.running || sr.SenderID == this.myId) return;
			if(ae.TO.IsRobotIgnored(sr.SenderID)) return;
			#if(SM_MESSAGES)
			Console.WriteLine("HandleSyncReady (at robot "+syncReady.HostId+") for ID: " + syncReady.Body.SyncTransitionID);
			#endif
			SyncTransition trans = null;
			if (!this.pr.SyncTransitions.TryGetValue(sr.SyncTransitionID,out trans)) {
				this.rosNode.RosError(String.Format("SyncModul: Unable to find synchronisation {0} sent by {1}!",sr.SyncTransitionID,sr.SenderID));
				return;
			}
			Synchronisation sync = null;

			lock(this.lockObj) {
				if(this.synchSet.TryGetValue(trans, out sync)) {
						sync.IntegrateSyncReady(sr);
				}
			}
		}
		
	}
}
