using System;
using System.Collections.Generic;
using Castor;
using RosCS;
using RosCS.AlicaEngine;

namespace Alica {	
	/// <summary>
	/// Manages communication wrt. conflict resolution.
	/// </summary>
	public class AuthorityManager {
		protected Node rosNode;
		protected Publisher authorityPub;
		
		protected List<AllocationAuthorityInfo> queue;
		protected AlicaEngine ae;
		protected int ownID;
		
		/// <summary>
		/// Constructor
		/// </summary>
		public AuthorityManager() {
			this.queue = new List<AllocationAuthorityInfo>();
			this.ae = AlicaEngine.Get();
			
			
		}
		/// <summary>
		/// Initialises this engine module
		/// </summary>
		public void Init() {
			this.ownID = AlicaEngine.Get().TO.GetOwnId();
			this.rosNode = new Node("AlicaEngine");
			this.rosNode.Subscribe("AllocationAuthorityInfo",HandleIncomingAuthorityMessage,10);
			authorityPub = new Publisher(this.rosNode,"AllocationAuthorityInfo",AllocationAuthorityInfo.TypeId,2);
			
		}
		/// <summary>
		/// Closes this engine module
		/// </summary>
		public void Close() {
			if(this.rosNode!= null) this.rosNode.Close();
		}
		/// <summary>
		/// Message Handler
		/// </summary>
		/// <param name="aai">
		/// A <see cref="AllocationAuthorityInfo"/>
		/// </param>
		public void HandleIncomingAuthorityMessage(AllocationAuthorityInfo aai) {
			if(ae.TO.IsRobotIgnored(aai.SenderID)) return;
			if(aai.SenderID != this.ownID) {
				ae.TO.MessageReceivedFrom(aai.SenderID);
				if(aai.SenderID > this.ownID) {			 //notify TO that evidence about other robots is available	
					foreach(EntryPointRobots epr in aai.Entrypoints) {
						foreach(int rid in epr.Robots) {
							if (rid != this.ownID) {
								ae.TO.MessageReceivedFrom(rid);
							}
						}
					}
				}
			}
			lock (this.queue) {
				this.queue.Add(aai);
			}			
		}
		/// <summary>
		/// Cyclic tick function, called by the plan base every iteration
		/// </summary>
		/// <param name="root">
		/// A <see cref="RunningPlan"/>
		/// </param>
		public void Tick(RunningPlan root) {
			lock(this.queue) {
				ProcessPlan(root);
				this.queue.Clear();
			}
		}
		protected void ProcessPlan(RunningPlan p) {
			if (p == null) return;
			if (p.IsBehaviour) return;
			if (p.CycleManagement.NeedsSending()) {
				SendAllocation(p);
				p.CycleManagement.Sent();
			}
			for(int i=0; i<this.queue.Count;i++) {
				if(AuthorityMatchesPlan(this.queue[i],p)) {
					p.CycleManagement.HandleAuthorityInfo(this.queue[i]);
					this.queue.RemoveAt(i);
					i--;
				}
			}
			foreach(RunningPlan c in p.Children) {
				ProcessPlan(c);
			}
			
		}
		/// <summary>
		/// Sends an AllocationAuthorityInfo message containing the assignment of rp
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		public void SendAllocation(RunningPlan rp) {
			if (!this.ae.MaySendMessages) return;
			AllocationAuthorityInfo aai = new AllocationAuthorityInfo();
			
			EntryPointRobots it;
			Assignment ass = rp.Assignment;
//Console.WriteLine("Sending Assignment: {0}",ass);			
			EntryPoint[] eps = ass.GetEntryPoints();
			for(int i=0; i<eps.Length; i++) {
				it = new EntryPointRobots();
				it.EntryPoint = eps[i].Id;
				it.Robots.AddRange(ass.GetRobotsWorking(eps[i]));
				aai.Entrypoints.Add(it);
				
			}
			
			aai.ParentState = (rp.Parent==null || rp.Parent.ActiveState==null?-1:rp.Parent.ActiveState.Id);
			aai.PlanID = rp.Plan.Id;
			aai.Authority = this.ownID;
			aai.SenderID = this.ownID;			
			aai.PlanType = (rp.RealisedPlanType==null?-1:rp.RealisedPlanType.Id);
			
			this.authorityPub.Send(aai);			
			
		}
		protected bool AuthorityMatchesPlan(AllocationAuthorityInfo aai, RunningPlan p) {
			if ((p.Parent == null && aai.ParentState== -1) || (p.Parent!=null && p.Parent.ActiveState!=null && aai.ParentState == p.Parent.ActiveState.Id)) {
				if (p.Plan.Id == aai.PlanID) {
					return true;
				} else if (aai.PlanType!=-1 && p.RealisedPlanType!=null && p.RealisedPlanType.Id==aai.PlanType) {
					return true;
				}
			}
			return false;
		}
		
	}
}