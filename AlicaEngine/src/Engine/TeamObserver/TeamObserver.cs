//#define TO_DEBUG
using System;
using System.Collections.Generic;
using System.Net;
using Castor;
using RosCS;

namespace Alica
{

	/// <summary>
	/// The TeamObserver manages communication with the team. Thus it sends and receives PlanTreeInfo messages.
	/// Specialised Modules may communicate through other means.
	/// </summary>
	public class TeamObserver : ITeamObserver
	{
		protected List<RobotEngineData> allOtherRobots;
		protected int myId;
		protected RobotEngineData me;
		
		protected IntPtr planTreePublisher;
		
		protected Node rosNode;
		
		protected Dictionary<int,SimplePlanTree> simplePlanTrees;
		
		protected ulong teamTimeOut;
		protected Logger log;
		protected HashSet<int> ignoredRobots;
		//private bool robotAdded;
		//private object lockBool = new object();
		
		public event OnTeamChange OnTeamChangeEvent;
		
		protected AlicaEngine ae;
		
		public TeamObserver ()
		{
			this.allOtherRobots = new List<RobotEngineData>();
			this.OnTeamChangeEvent = null;
			this.simplePlanTrees = new Dictionary<int, SimplePlanTree>();
			this.ignoredRobots = new HashSet<int>();
			//this.robotAdded = false;
						
		}
		public void Close() {
			this.OnTeamChangeEvent = null;
			if (this.rosNode != null) this.rosNode.Close();
			this.rosNode = null;			
			Console.WriteLine("Closed TO");
		}
		public void MessageReceivedFrom(int rid) {			
			foreach(RobotEngineData re in this.allOtherRobots) {
				if (re.Properties.Id == rid) {
					re.LastMessageTime = RosSharp.Now();
					break;
				}				
			}			
		}
		public void Init() {
			SystemConfig sc = SystemConfig.LocalInstance;
			
			this.ae = AlicaEngine.Get();			
			this.log = ae.Log;
			
			string ownPlayerName = AlicaEngine.Get().GetRobotName();
			
			Console.WriteLine("TO: Initing Robot {0}",ownPlayerName);
			
			this.teamTimeOut = sc["Alica"].GetULong("Alica.TeamTimeOut")*1000000UL;
			
			string[] playerNames = sc["Globals"].GetSections ("Globals.Team");	
			bool foundSelf = false;

			for (int i = 0; i < playerNames.Length; i++) {
				RobotProperties rp = new RobotProperties(playerNames[i]);
				if (!foundSelf && playerNames[i].Equals(ownPlayerName)) {					
					foundSelf = true;
					me = new RobotEngineData(rp);
					me.IsActive = true;
					myId = rp.Id;
				} else {
					this.allOtherRobots.Add(new RobotEngineData(rp));			
				}
			}
			if (!foundSelf) {
				AlicaEngine.Get().Abort(String.Format("TO: Could not find own robot name in Globals ({0})",ownPlayerName));				
			}
			if (sc["Alica"].GetBool("Alica.TeamBlackList.InitiallyFull")) {
				foreach(RobotEngineData r in this.allOtherRobots) {
					this.ignoredRobots.Add(r.Properties.Id);
				}
			}
			
			
			rosNode = new Node("AlicaEngine");
			planTreePublisher = rosNode.Advertise("PlanTreeInfo",RosCS.AlicaEngine.PlanTreeInfo.TypeId,1);			
			rosNode.Subscribe("PlanTreeInfo",this.HandlePlanTreeInfo);
		}
		
		/// <summary>
		/// Returns this robot's own unique id.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Int32"/>
		/// </returns>
		public int GetOwnId() {
			return this.myId;
		}
		/// <summary>
		/// Access engine data of a robot by its id. Returns null if none found.
		/// </summary>
		/// <param name="id">
		/// A <see cref="System.Int32"/>
		/// </param>
		/// <returns>
		/// A <see cref="RobotEngineData"/>
		/// </returns>
		public RobotEngineData GetRobotById(int id) {
			if(id == myId) return me;
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.Properties.Id == id) return r;
			}
			return null;
		}
		/// <summary>
		/// Returns the dynamic engine data of robots from which message have been received recently. Includes own engine data.
		/// </summary>
		/// <returns>
		/// A <see cref="List<RobotEngineData>"/>
		/// </returns>
		public List<RobotEngineData> GetAvailableRobots() {
			List<RobotEngineData> ret = new List<RobotEngineData>();
			ret.Add(me);
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) ret.Add(r);
			}
			return ret;
		}
		/// <summary>
		/// Returns the static properties of robots from which message have been received recently. Includes own properties.
		/// </summary>
		/// <returns>
		/// A <see cref="List<RobotProperties>"/>
		/// </returns>
		public List<RobotProperties> GetAvailableRobotProperties() {
			List<RobotProperties> ret = new List<RobotProperties>();
			ret.Add(me.Properties);
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) ret.Add(r.Properties);
			}
			return ret;
		}
		/// <summary>
		/// Returns the ids of robots from which message have been received recently. Includes own id.
		/// </summary>
		/// <returns>
		/// A <see cref="List<System.Int32>"/>
		/// </returns>
		public List<int> GetAvailableRobotIds() {
			List<int> ret = new List<int>();
			ret.Add(myId);
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) ret.Add(r.Properties.Id);
			}
			return ret;
		}
		public RobotProperties GetOwnRobotProperties() {
			return me.Properties;
		}
		public RobotEngineData GetOwnEngineData() {
			return me;
		}
		
		/// <summary>
		/// The current size of the team.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Int32"/>
		/// </returns>
		public int TeamSize() {
			int i=1;
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) i++;
			}
			return i;
		}
		public Dictionary<int, SimplePlanTree> GetTeamPlanTrees() { 
			Dictionary<int,SimplePlanTree> ret= new Dictionary<int, SimplePlanTree>();
			lock(this.simplePlanTrees) {
				foreach(RobotEngineData r in this.allOtherRobots) {
					if(r.IsActive) {
						SimplePlanTree t=null;
						this.simplePlanTrees.TryGetValue(r.Properties.Id, out t);
						if(t!=null) ret.Add(r.Properties.Id,t);
					}
				}
				//ret 				
			}
			return ret;
		} 
		
			
		public void Tick(RunningPlan root) { 
			ulong time = RosSharp.Now();
			bool changed = false;
			/*lock(this.lockBool) {
				changed = this.robotAdded;
				this.robotAdded = false;
			}*/
			List<int> robotsAvail = new List<int>();
			robotsAvail.Add(this.myId);
			foreach(RobotEngineData r in this.allOtherRobots) {
				if(r.LastMessageTime + teamTimeOut < time) {
					changed |= r.IsActive;
					r.IsActive = false;
					r.SuccessMarks.Clear();
					lock(this.simplePlanTrees) {
						this.simplePlanTrees.Remove(r.Properties.Id);
					}
				} else if(!r.IsActive) {
					r.IsActive = true;
					changed = true;
				}
				if(r.IsActive) robotsAvail.Add(r.Properties.Id);
			} 
			if(changed && OnTeamChangeEvent!=null) {				
				OnTeamChangeEvent();
				this.log.EventOccurred("TeamChanged");
			}
			
			CleanOwnSuccessMarks(root);
			if(root!=null) {
				List<SimplePlanTree> updatedspts=new List<SimplePlanTree>();
				List<int> noUpdates = new List<int>();
				lock(this.simplePlanTrees) {
					foreach(SimplePlanTree spt in this.simplePlanTrees.Values) {
						if(robotsAvail.Contains(spt.RobotID)) {
							if(spt.IsNew) {
							//if(spt.ReceiveTime > time - this.ae.PB.LoopInterval*2) {
								updatedspts.Add(spt);
								spt.IsNew = false;
							} else {
								noUpdates.Add(spt.RobotID);
							}
						}
					}
				}
				if(root.RecursiveUpdateAssignment(updatedspts,robotsAvail,noUpdates, time)) {
					this.log.EventOccurred("MsgUpdate");
				}
			}			
		}
		/// <summary>
		/// Broadcasts a PlanTreeInfo Message
		/// </summary>
		/// <param name="msg">
		/// A <see cref="List<System.Int64>"/>, a serialised version of the current planning tree
		/// as constructed by <see cref="RunningPlan.ToMessage"/>.
		/// </param>
		public void DoBroadcast(List<long> msg) {
			if(!ae.MaySendMessages) return;
			RosCS.AlicaEngine.PlanTreeInfo pti = new RosCS.AlicaEngine.PlanTreeInfo();
			pti.SenderID = this.myId;
			pti.StateIDs = msg;
			pti.SucceededEps = this.GetOwnEngineData().SuccessMarks.ToList();
			rosNode.Send(planTreePublisher,pti);			

#if (TO_DEBUG)
			Console.WriteLine("Sending Plan Message:");
			foreach(long i in msg) {
				Console.Write("{0}\t",i);
			}
			Console.WriteLine();
#endif
			//SptFromMessage(me.Properties.Id,msg);
		}
		/// <summary>
		/// Removes any successmarks left by this robot in plans no longer inhabited by any agent.
		/// </summary>
		/// <param name="root">
		/// A <see cref="RunningPlan"/>
		/// </param>
		protected void CleanOwnSuccessMarks(RunningPlan root) {
			HashSet<AbstractPlan> presentPlans = new HashSet<AbstractPlan>();
			if (root!=null) {
				LinkedList<RunningPlan> q = new LinkedList<RunningPlan>();
				q.AddFirst(root);
				while(q.Count > 0) {
					RunningPlan p = q.First.Value;
					q.RemoveFirst();
					if(!p.IsBehaviour) {
						presentPlans.Add(p.Plan);
						foreach(RunningPlan c in p.Children) {
							q.AddLast(c);
						}
					}
				}
			}
			LinkedList<SimplePlanTree> queue;
			lock (this.simplePlanTrees) {
				queue = new LinkedList<SimplePlanTree>(this.simplePlanTrees.Values);
			}
			while(queue.Count > 0) {
				SimplePlanTree spt = queue.First.Value;
				queue.RemoveFirst();
				presentPlans.Add(spt.State.InPlan);
				foreach(SimplePlanTree c in spt.Children) {
					queue.AddLast(c);
				}				
			}
			this.GetOwnEngineData().SuccessMarks.LimitToPlans(presentPlans);		
			
		}
		/// <summary>
		/// Constructs a SimplePlanTree from a received message
		/// </summary>
		/// <param name="robotId">
		/// The id as <see cref="System.Int32"/> of the other robot.
		/// </param>
		/// <param name="ids">
		/// The <see cref="List<System.Int64>"/> encoding another robot's plantree as received in a PlanTreeInfo message.
		/// </param>
		/// <returns>
		/// A <see cref="SimplePlanTree"/>
		/// </returns>
		protected SimplePlanTree SptFromMessage(int robotId, List<long> ids) {
			#if (TO_DEBUG)
			Console.WriteLine("Spt from robot {0}",robotId);
			foreach(long id in ids) {
				Console.Write("{0}\t",id);
			}			
			Console.WriteLine();
			#endif
			if (ids.Count == 0) {
				this.rosNode.RosWarn(String.Format("TO: Empty state list for robot {0}",robotId));
				return null;
			}
			Dictionary<long,State> states = AlicaEngine.Get().PR.States;
			ulong time = RosSharp.Now();
			SimplePlanTree root = new SimplePlanTree();
			root.RobotID = robotId;
			root.ReceiveTime = time;
			root.StateIDs = ids;
			State s;
			if(states.TryGetValue(ids[0],out s) ){
				root.State = s;
				root.EntryPoint = EntryPointOfState(s);
				
				if(root.EntryPoint == null) {
					this.rosNode.RosWarn(String.Format("TO: Cannot find ep for State ({0}) received from {1}",ids[0],robotId));
					return null;
				}
			} else {
				Console.Error.WriteLine("TO: Unknown State ({0}) received from {1}",ids[0],robotId);
				return null;
			}
			
			
			SimplePlanTree curParent = null;
			SimplePlanTree cur = root;
			
			
			for(int i=1; i<ids.Count; i++) {
				if (ids[i] == -1) {
					curParent = cur;
					cur = null;
				} else if(ids[i] == -2) {
					cur = curParent;
					if(cur==null) {
						Console.Error.WriteLine("TO: Malformed SptMessage from {0}",robotId);
						return null;
					}				
				} else {
					cur = new SimplePlanTree();
					cur.RobotID = robotId;	
					cur.ReceiveTime = time;
					curParent.AddChild(cur);					
					if(states.TryGetValue(ids[i],out s) ){
						cur.State = s;
						cur.EntryPoint = EntryPointOfState(s);						
						if(cur.EntryPoint == null) {
							Console.Error.WriteLine("TO: Cannot find ep for State ({0}) received from {1}",ids[i],robotId);
							return null;
						}
					} else {
						Console.Error.WriteLine("TO: Unknown State ({0}) received from {1}",ids[i],robotId);
						return null;
					}
				}
			}
			
			
			return root;
		}
		/// <summary>
		/// Returns the number of successes the team knows about in the given plan.
		/// </summary>
		/// <param name="p">
		/// A <see cref="Plan"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Int32"/>
		/// </returns>
		public int SuccessesInPlan(Plan p) {
			int ret = 0;
			List<EntryPoint> suc;
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) {
					suc = r.SuccessMarks.SucceededEntryPoints(p);
					if (suc != null) {
						ret += suc.Count;
					}
				}
			}
			suc = me.SuccessMarks.SucceededEntryPoints(p);
			if (suc != null) {
				ret += suc.Count;
			}
			return ret;
			
		}
		
		public SuccessCollection GetSuccessCollection(Plan p) {
			SuccessCollection ret = new SuccessCollection(p);
			List<EntryPoint> suc;
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) {
					suc = r.SuccessMarks.SucceededEntryPoints(p);
					if (suc != null) {
						foreach(EntryPoint ep in suc) {
							ret.SetSuccess(r.Properties.Id,ep);
						}
					}
				}
			}
			suc = me.SuccessMarks.SucceededEntryPoints(p);
			if (suc != null) {
				foreach(EntryPoint ep in suc) {
					ret.SetSuccess(myId,ep);
				}
			}
			return ret;
		}
		public void UpdateSuccessCollection(Plan p, SuccessCollection sc) {
			sc.Clear();
			List<EntryPoint> suc;
			foreach(RobotEngineData r in this.allOtherRobots) {
				if (r.IsActive) {
					suc = r.SuccessMarks.SucceededEntryPoints(p);
					if (suc != null) {
						foreach(EntryPoint ep in suc) {
							sc.SetSuccess(r.Properties.Id,ep);
						}
					}
				}
			}
			suc = me.SuccessMarks.SucceededEntryPoints(p);
			if (suc != null) {
				foreach(EntryPoint ep in suc) {
					sc.SetSuccess(myId,ep);
				}
			}			
		}
		
		private EntryPoint EntryPointOfState(State s) {
			foreach(EntryPoint ep in s.InPlan.EntryPoints.Values) {
				if (ep.ReachableStates.Contains(s)) return ep;
			}
			return null;
		}
		public void HandlePlanTreeInfo(RosCS.AlicaEngine.PlanTreeInfo incoming) {
			if (incoming.SenderID!=myId) {
				if(IsRobotIgnored(incoming.SenderID)) {
					//Console.WriteLine("Ignoring {0}",incoming.SenderID);
					return;
				} else {
					//Console.WriteLine("Playing {0}",incoming.SenderID);
				}
				//bool changed = false;
				SimplePlanTree spt = SptFromMessage(incoming.SenderID, incoming.StateIDs);
				if(spt != null) { //null result indicates an unparseable message, probably due to robot doing something completely different
					//we ignore this robot, as cooperation cannot be achieved in this case.
					foreach(RobotEngineData r in this.allOtherRobots) {
						if (r.Properties.Id == incoming.SenderID) {
							//changed = ! r.IsActive;
							r.LastMessageTime = RosSharp.Now();
							//r.IsActive = true;
							r.SuccessMarks = new SuccessMarks(incoming.SucceededEps);
							break;
						}
						
					}
					lock(this.simplePlanTrees) {
						if(simplePlanTrees.ContainsKey(incoming.SenderID)) {
							simplePlanTrees[incoming.SenderID] = spt;
							
						} else {
							simplePlanTrees.Add(incoming.SenderID,spt);					
						}
						
					}
					/*if(changed) {
						lock(this.lockBool) {
							this.robotAdded = true;
						}
						//if (changed && this.OnTeamChangeEvent!=null) OnTeamChangeEvent();
					}*/
					
					
				} else {
					//Console.Error.WriteLine("Empty PlanTreeInfo from robot {0}",incoming.SenderID);					
				}
				
			}
			
			//Console.WriteLine("Received msg from {0}",incoming.SenderID);
			//foreach(long id in incoming.StateIDs) { Console.WriteLine(id); }
		}
		/// <summary>
		/// Notify the TeamObserver that the team has left a plan. This will reset any successmarks left.
		/// </summary>
		/// <param name="p">
		/// The <see cref="AbstractPlan"/> left by the team.
		/// </param>
		public void NotifyTeamLeftPlan(AbstractPlan p) {
			
			NotifyILeftPlan(p); //Only remove mark if other robots confirmed they left,
								//otherwise they might never receive success signal.
			
			/*foreach(RobotEngineData red in this.allOtherRobots) {
				red.SuccessMarks.RemovePlan(p);
			}
			this.me.SuccessMarks.RemovePlan(p);*/
			
		}   
		/// <summary>
		/// Notify the TeamObserver that this robot has left a plan. This will reset any successmarks left, if no other robot is beliefed to be left in the plan.
		/// </summary>
		/// <param name="p">
		/// The <see cref="AbstractPlan"/> left by the robot
		/// </param>
		public void NotifyILeftPlan(AbstractPlan p) {
			//See if a robot still inhabits p:
			
			lock(this.simplePlanTrees) {
				foreach(SimplePlanTree spt in this.simplePlanTrees.Values) {
					if (spt.ContainsPlan(p)) return;
				}
			}
			
			this.me.SuccessMarks.RemovePlan(p);
		}     
		
		
		/// <summary>
		/// Ignore all messages received by a robot.
		/// </summary>
		/// <param name="rid">
		/// A <see cref="System.Int32"/> identifying the robot to ignore.
		/// </param>
		public void IgnoreRobot(int rid) {
			if(this.ignoredRobots.Contains(rid)) return;
			this.ignoredRobots.Add(rid);
		}
		public void UnIgnoreRobot(int rid) {
			if(this.ignoredRobots.Contains(rid)) this.ignoredRobots.Remove(rid);
		}
		public bool IsRobotIgnored(int rid) {
			return this.ignoredRobots.Contains(rid);
		}
		
		
	}
}
