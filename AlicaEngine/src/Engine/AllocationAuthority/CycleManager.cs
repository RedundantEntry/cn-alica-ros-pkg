//#define CM_DEBUG
using System;
using System.Collections.Generic;
using Castor;
using RosCS.AlicaEngine;

namespace Alica {	
	/// <summary>
	/// Responsibile for detecting cycles in assignment updates and reactions to these
	/// </summary>
	public class CycleManager {

		static SystemConfig sc = SystemConfig.LocalInstance;
		static int maxAllocationCycles = sc["Alica"].GetInt("Alica","CycleDetection","CycleCount");
		static bool enabled = sc["Alica"].GetBool("Alica","CycleDetection","Enabled");

		
		protected AllocationDifference[] allocationHistory;
		protected PlanRepository pr;
		protected int newestAllocationDifference;
		protected int myID;
		
		protected enum CycleState {
			observing, overridden, overriding
		}
		protected ulong overrideTimestamp; //ns
		protected double intervalIncFactor = sc["Alica"].GetDouble("Alica","CycleDetection","IntervalIncreaseFactor");
		protected double intervalDecFactor = sc["Alica"].GetDouble("Alica","CycleDetection","IntervalDecreaseFactor");
		protected static ulong minimalOverrideTimeInterval = sc["Alica"].GetULong("Alica","CycleDetection","MinimalAuthorityTimeInterval")*1000000UL; //ns
		protected static ulong maximalOverrideTimeInterval = sc["Alica"].GetULong("Alica","CycleDetection","MaximalAuthorityTimeInterval")*1000000UL; //ns
		//protected ulong overrideTimeInterval;
		protected static ulong overrideShoutInterval= sc["Alica"].GetULong("Alica","CycleDetection","MessageTimeInterval")*1000000UL; //ns
		protected static ulong overrideWaitInterval = sc["Alica"].GetULong("Alica","CycleDetection","MessageWaitTimeInterval")*1000000UL; //ns
		protected ulong overrideShoutTime;
		protected static int historySize = sc["Alica"].GetInt("Alica","CycleDetection","HistorySize");
		protected CycleState state;
		
		protected RunningPlan rp;
		protected AllocationAuthorityInfo fixedAllocation;
		
		/// <summary>
		/// Construct a cyclemanager for a <see cref="RunningPlan"/>
		/// </summary>
		/// <param name="p">
		/// A <see cref="RunningPlan"/>
		/// </param>
		internal CycleManager(RunningPlan p) {
			this.allocationHistory = new AllocationDifference[historySize];
			for (int i=0; i<this.allocationHistory.Length; i++) {
				this.allocationHistory[i]=new AllocationDifference();
			}
			this.newestAllocationDifference = 0;
			this.state = CycleState.observing;
			this.rp = p;
			this.myID = AlicaEngine.Get().TO.GetOwnId();
			this.pr = AlicaEngine.Get().PR;
			//this.rp.Plan.AuthorityTimeInterval = minimalOverrideTimeInterval;
		}
		/// <summary>
		/// Notifiy the CycleManager of a new AllocationDifference
		/// </summary>
		/// <param name="curP">
		/// The <see cref="RunningPlan"/> of this CycleManager, in case it has changed.
		/// </param>
		/// <param name="aldif">
		/// The new <see cref="AllocationDifference"/>
		/// </param>
		internal void SetNewAllocDiff(RunningPlan curP, AllocationDifference aldif) {
			if (!enabled) return;
			//if(this.state != CycleState.observing) return;
			lock(this.allocationHistory) {
				try {
					this.newestAllocationDifference = (this.newestAllocationDifference+1)%this.allocationHistory.Length;
					this.allocationHistory[this.newestAllocationDifference]=aldif;
				//Console.WriteLine("ADD:MSG: "+aldif);	
					this.rp = curP;
				}
				catch(Exception e) {
					Console.Error.WriteLine("Exception in Alloc Difference Calculation:");
					Console.Error.WriteLine(e.Message);
					Console.Error.WriteLine(e.StackTrace);
				}
			}
		}
		/// <summary>
		/// Notifiy the CycleManager of a change in the assignment
		/// </summary>
		/// <param name="curP">
		/// The <see cref="RunningPlan"/> of this CycleManager, in case it has changed.
		/// </param>
		/// <param name="oldAss">
		/// The former <see cref="Assignment"/>
		/// </param>
		/// <param name="newAss">
		/// The new <see cref="Assignment"/>
		/// </param>
		/// <param name="reas">
		/// The <see cref="AllocationDifference.Reason"/> for this change.
		/// </param>
		internal void SetNewAllocDiff(RunningPlan curP, Assignment oldAss, Assignment newAss, AllocationDifference.Reason reas) {
			if (!enabled) return;
			
			this.rp = curP;
			if (oldAss == null) return;			
			lock(this.allocationHistory) {
				try {
				this.newestAllocationDifference = (this.newestAllocationDifference+1)%this.allocationHistory.Length;
				this.allocationHistory[this.newestAllocationDifference].Reset();
					
				foreach(EntryPoint ep in oldAss.GetEntryPoints()) {				
						
					ICollection<int> newRobots = newAss.GetRobotsWorking(ep);	
					ICollection<int> oldRobots = oldAss.GetRobotsWorking(ep);
					foreach(int oldId in oldRobots) {
						if (newRobots==null || !newRobots.Contains(oldId)) {
							this.allocationHistory[this.newestAllocationDifference].subtractions.Add(new EntryPointRobotPair(ep,oldId));
						}
					}
					if (newRobots!=null) {
						foreach(int newId in newRobots) {
							if (!oldRobots.Contains(newId)) {
								this.allocationHistory[this.newestAllocationDifference].additions.Add(new EntryPointRobotPair(ep,newId));
							}
						}
					}

				}
				this.allocationHistory[this.newestAllocationDifference].reason=reas;
			//Console.WriteLine("ADD:U: "+this.allocationHistory[this.newestAllocationDifference]);
				} catch(Exception e) {
					Console.Error.WriteLine("Exception in Alloc Difference Calculation:");
					Console.Error.WriteLine(e.Message);
					Console.Error.WriteLine(e.StackTrace);
					
				}
			}
			
		}
		protected bool DetectAllocationCycle() {
			//A Cycle occurs n-times,
			//Consists of 1 UtilityChange, m message update
			//after uc, allocation is same again (delta = 0)
//Console.WriteLine("=============ADD: Start Test for rp: {0}",this.rp.Plan.Name);				
			int cyclesFound = 0;
			int count = 0;
			AllocationDifference utChange = null;
			AllocationDifference temp = new AllocationDifference();
			lock(this.allocationHistory) {
				for(int i = this.newestAllocationDifference; count < this.allocationHistory.Length; i--) {
					count ++;
					if (i<0) i = this.allocationHistory.Length-1;
					
					if (this.allocationHistory[i].reason==AllocationDifference.Reason.utility) {
						if (utChange != null) {
//Console.WriteLine("utChange != null, returning");							
							return false;
						}						
						utChange = this.allocationHistory[i];
//Console.WriteLine("utchange: {0}",utChange);						
						temp.Reset();
						temp.ApplyDifference(utChange);
					}
					else {
						if (this.allocationHistory[i].reason == AllocationDifference.Reason.empty) return false;
						if (utChange == null) continue;
						temp.ApplyDifference(this.allocationHistory[i]);
//Console.WriteLine("applied change: {0}",temp);						
						if (temp.IsEmpty())  {
							cyclesFound++;
//Console.WriteLine("Found cycles: {0}",cyclesFound);
							if (cyclesFound > maxAllocationCycles) {
								for (int k=0; k<this.allocationHistory.Length; k++) {
									this.allocationHistory[k].Reset();
								}
								return true;
							}
							utChange = null;
						}
						
					}
				}
				
			}			
			return false;
		}
		/// <summary>
		/// Called once per engine iteration by the corresponding <see cref="RunningPlan"/>.
		/// Checks whether a state change is needed.
		/// </summary>
		internal void Update() {
			if(!enabled) return;
			if (this.rp.IsBehaviour) return;
			if (this.state == CycleState.observing) {
//Console.WriteLine("Update Observing");				
				if (DetectAllocationCycle()) {
					Console.WriteLine("Cycle Detected!");
					
					//find largest robotID:
					/*int maxID = 0;
					foreach(int id in rp.Assignment.GetAllRobots()) {
						if (id > maxID) {
							maxID = id;
						}
					}

					if (maxID == myID) {
						this.state= CycleState.overriding;
						this.overrideTimeInterval = Math.Min(maximalOverrideTimeInterval,(ulong)(this.overrideTimeInterval*intervalIncFactor));
						this.overrideShoutTime = 0;
						Console.WriteLine("Assuming Authority!");
					} else {
						this.state= CycleState.overridden;
						this.fixedAllocation = null;
						this.overrideShoutTime = RosCS.RosSharp.Now();
						Console.WriteLine("Waiting for Authority!");						
					}*/
					this.state= CycleState.overriding;
					this.rp.Plan.AuthorityTimeInterval = Math.Min(maximalOverrideTimeInterval,(ulong)(this.rp.Plan.AuthorityTimeInterval*intervalIncFactor));
					this.overrideShoutTime = 0;
					Console.WriteLine("Assuming Authority for {0}sec!",this.rp.Plan.AuthorityTimeInterval/1000000000.0);
					this.overrideTimestamp = RosCS.RosSharp.Now();
				}
				else {
					this.rp.Plan.AuthorityTimeInterval = Math.Max(minimalOverrideTimeInterval,(ulong)(this.rp.Plan.AuthorityTimeInterval*intervalDecFactor));
				}
			} else {
				if (this.state==CycleState.overriding && this.overrideTimestamp + this.rp.Plan.AuthorityTimeInterval < RosCS.RosSharp.Now() ) {
					#if CM_DEBUG
					Console.WriteLine("Resume Observing!");
					#endif
					this.state = CycleState.observing;
					this.fixedAllocation = null;
				} 
				else if (this.state == CycleState.overridden && this.overrideShoutTime + this.rp.Plan.AuthorityTimeInterval < RosCS.RosSharp.Now() ) {
					#if CM_DEBUG
					Console.WriteLine("Resume Observing!");
					#endif
					this.state = CycleState.observing;
					this.fixedAllocation = null;
					
				}
			}
		}
		/// <summary>
		/// Message Handler
		/// </summary>
		/// <param name="aai">
		/// A <see cref="AllocationAuthorityInfo"/>
		/// </param>
		internal void HandleAuthorityInfo(AllocationAuthorityInfo aai) {
				if (!enabled) return;
				long rid=aai.Authority;
				if (rid == myID) return;
//Console.WriteLine("Rcv Auth!");
			if (rid > myID) {
Console.WriteLine("Rcv: Accepting Authority!");				
					this.state = CycleState.overridden;
					this.overrideShoutTime = RosCS.RosSharp.Now();					
					this.fixedAllocation = aai;
//Console.WriteLine("Alloc: Plan: "+this.fixedAllocation.PlanID);
//Console.WriteLine("Alloc: R: "+this.fixedAllocation.Entrypoints[0].Robots.Count);				
				} else {
Console.WriteLine("Rcv: Rejecting Authority!");	
					if (this.state!= CycleState.overriding) {
						this.state = CycleState.overriding;
						this.rp.Plan.AuthorityTimeInterval = Math.Min(maximalOverrideTimeInterval,(ulong)(this.rp.Plan.AuthorityTimeInterval*intervalIncFactor));
						this.overrideTimestamp = RosCS.RosSharp.Now();
						this.overrideShoutTime = 0;
					}
				}

				
		
		}
		/// <summary>
		/// Indicates wether authority allows the assignment of this plan to be changed.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		internal bool MayDoUtilityCheck() {
			return this.state!=CycleState.overridden;
		}
		/// <summary>
		/// Indicates whether an AllocationAuthorityInfo message should be send.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		internal bool NeedsSending() {
			return this.state==CycleState.overriding && (this.overrideShoutTime+overrideShoutInterval < RosCS.RosSharp.Now());
		}
		/// <summary>
		/// Indicate to the manager that a corresponding message has been sent.
		/// </summary>
		internal void Sent() {
			this.overrideShoutTime = RosCS.RosSharp.Now();
		}
		/// <summary>
		/// Indicates whether an authorative allocation is present
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		internal bool IsOverridden() {
			return this.state==CycleState.overridden && this.fixedAllocation!=null;
		}
		/// <summary>
		/// Indicates whether the local agent currently holds authority over the plan.
		/// </summary>
		/// <returns>
		///  A <see cref="System.Boolean"/>
		/// </returns>
		internal bool HaveAuthority() {
			return this.state==CycleState.overriding;
		}
		/// <summary>
		/// Apply the authorative assignment to the <see cref="RunningPlan"/>
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		internal bool SetAssignment(RunningPlan rp) {
#if CM_DEBUG			
			Console.WriteLine("Setting new Assignment {0}!",rp.Plan.Name);
#endif			
			EntryPoint myEntryPoint = null;
			if (this.fixedAllocation==null) return false;
			bool modifiedSelf = false;
			bool modified = false;
			if (this.fixedAllocation.PlanID != rp.Plan.Id) { //Plantype case
				if (rp.RealisedPlanType.Id != this.fixedAllocation.PlanType) return false;
				Plan newPlan = null;
				foreach (Plan p in rp.RealisedPlanType.Plans) {
					if (p.Id==this.fixedAllocation.PlanID) {
						newPlan = p;
						rp.Plan = p;				
						break;
					}
				}
				rp.Assignment=new Assignment(newPlan,this.fixedAllocation);
				foreach(EntryPointRobots epr in this.fixedAllocation.Entrypoints) {
					if (epr.Robots.Contains(myID)) {
						myEntryPoint = pr.EntryPoints[epr.EntryPoint];
					}
				}
				
				modifiedSelf = true;
				
			}
			else { //plan case
				foreach(EntryPointRobots epr in this.fixedAllocation.Entrypoints) {
					foreach(int robot in epr.Robots) {
						EntryPoint e = pr.EntryPoints[epr.EntryPoint];
						bool changed = rp.Assignment.UpdateRobot(robot,e);
						if (changed) {
							if(robot==myID) {
								modifiedSelf = true;
								myEntryPoint = e;
							}
							else {
								modified = true;
							}
						}
					}
				}
			}
			if (modifiedSelf) {
				rp.OwnEntryPoint = myEntryPoint;
				rp.DeactivateChildren(false);
				rp.ClearChildren();	
				rp.ClearFailedChildren();
				rp.AllocationNeeded = true;
					
			} else {
				if(rp.ActiveState !=null) {
					HashSet<int> robotsJoined = rp.Assignment.RobotStateMapping.GetRobotsInState(rp.ActiveState);
					foreach(RunningPlan c  in rp.Children) {
						c.LimitToRobots(robotsJoined);
					}
				}
			}
#if CM_DEBUG						
			if (!modifiedSelf) Console.WriteLine("But no change!");
#endif			
			return modifiedSelf || modified;
		}


	}
}
	
