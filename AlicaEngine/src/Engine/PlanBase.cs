#define PB_DEBUG
//#define COSTLY_DEBUG_CHECKS
using System;
using System.Threading;
using System.Collections.Generic;
using Castor;

using RosCS;
using RosCS.AlicaEngine;

namespace Alica
{
	/// <summary>
	/// A PlanBase holds the internal representation of the plan graph and issues all operations on it.
	/// It is the most central object within the ALICA Engine.
	/// </summary>
	public class PlanBase
	{
		protected Plan masterPlan;
		protected RunningPlan rootNode;
		protected AlicaEngine ae;
		
		protected RunningPlan deepestNode;
		protected int treeDepth;
		
		protected RuleBook rulebook;
		protected ITeamObserver teamObserver;
		protected ISyncModul syncModul;
		protected AuthorityManager authModul;
		protected IRoleAssignment ra;
		
		protected ulong loopTime; 
		protected ulong lastSendTime;
		protected ulong minSendInterval;
		protected ulong maxSendInterval;
		
		internal ulong LoopInterval { get { return this.loopTime; }}
		
		protected bool running;
		protected Thread mainThread;
		protected Logger log;
		
		protected bool sendStatusMessages;
		protected ulong sendStatusInterval;
		protected ulong lastSentStatusTime;
		protected Node rosNode;
		protected Publisher statusPublisher;
		protected BehaviourEngineInfo statusMessage;
		
		
		
		internal AutoResetEvent Signal {get; private set;}
		
		private RosCS.Timer loopTimer;
		private AutoResetEvent loopGuard;
		/// <summary>
		/// List of RunningPlans scheduled for out-of-loop evaluation.
		/// </summary>
		private Queue<RunningPlan> fpEvents = new Queue<RunningPlan>();
		
		/// <summary>
		/// Constructs the PlanBase given a top-level plan to execute
		/// </summary>
		/// <param name="masterplan">
		/// A <see cref="Plan"/>
		/// </param>
		public PlanBase(Plan masterplan)
		{
			this.masterPlan = masterplan;
			this.ae = AlicaEngine.Get();
			this.teamObserver = ae.TO;
			this.syncModul = ae.SM;
			this.authModul = ae.AM;
			this.ra = ae.RA;
			this.rulebook = new RuleBook();
			SystemConfig sc = SystemConfig.LocalInstance;
			
			
			double freq = sc["Alica"].GetDouble("Alica.EngineFrequency");
			double minbcfreq = sc["Alica"].GetDouble("Alica.MinBroadcastFrequency");
			double maxbcfreq = sc["Alica"].GetDouble("Alica.MaxBroadcastFrequency");
			this.loopTime = (ulong)Math.Max(1000000,Math.Round(1.0/freq*1000000000));
			
			if(minbcfreq > maxbcfreq) {
				AlicaEngine.Get().Abort("PB: Alica.conf: Minimal brodcast frequency must be lower or equal to maximal broadcast frequency!");
			}
			
			this.minSendInterval = (ulong)Math.Max(1000000,Math.Round(1.0/maxbcfreq*1000000000));
			this.maxSendInterval = (ulong)Math.Max(1000000,Math.Round(1.0/minbcfreq*1000000000));
			ulong halfLoopTime = this.loopTime/2;
			this.running = false;
			
			this.sendStatusMessages = sc["Alica"].GetBool("Alica.StatusMessages.Enabled");
			if (this.sendStatusMessages) {
				this.rosNode = new Node("AlicaEngine");
				this.statusPublisher = new Publisher(this.rosNode,"BehaviourEngineInfo",BehaviourEngineInfo.TypeId,1);
				double stfreq = sc["Alica"].GetDouble("Alica.StatusMessages.Frequency");
				this.sendStatusInterval = (ulong)Math.Max(1000000,Math.Round(1.0/stfreq*1000000000));
				this.statusMessage = new BehaviourEngineInfo();
				this.statusMessage.SenderID = this.teamObserver.GetOwnId();
				this.statusMessage.MasterPlan = masterplan.Name;
			}
			this.Signal = new AutoResetEvent(false);
			this.loopGuard = new AutoResetEvent(false);
			this.loopTimer = new RosCS.Timer(this.TimerCallBack);
			
			
			
#if PB_DEBUG
			Console.WriteLine("PB: Engine loop time is {0}ms, broadcast interval is {1}ms - {2}ms",loopTime/1000000,this.minSendInterval/1000000,this.maxSendInterval/1000000);
#endif
			if (halfLoopTime < this.minSendInterval) {
				this.minSendInterval -= halfLoopTime;
				this.maxSendInterval -= halfLoopTime;
			}
		}
		/// <summary>
		/// Returns the root node of the ALICA plan tree in execution.
		/// </summary>
		public RunningPlan PlanRoot { get {return this.rootNode;}}
		/// <summary>
		/// Starts execution of the plan tree, call once all necessary modules are initialised.
		/// </summary>
		public void Start() {
			this.log = AlicaEngine.Get().Log;
			this.mainThread = new Thread(new ThreadStart(this.Run));			
			this.running = true;			
			this.mainThread.Start();
			this.loopTimer.SetActive((int)(this.loopTime/1000000),(int)(this.loopTime/1000000));
			
			
		}
		/// <summary>
		/// The Engine's main loop
		/// </summary>		
		protected void Run() {
			while(this.running) {				
				ulong beginTime = RosSharp.Now();
				this.log.IterationStarts();
				
				
				//Send Tick to additional modules:
				this.teamObserver.Tick(this.rootNode);
				this.ra.Tick();
				this.syncModul.Tick();
				this.authModul.Tick(this.rootNode);
				
				if(AlicaEngine.Get().StepEngine) {
					Console.WriteLine("====CUR TREE====");				
					if (this.rootNode == null) { Console.WriteLine("NULL"); }
					else this.rootNode.PrintRecursive();		
					Console.WriteLine("====END CUR TREE====");
					this.Signal.WaitOne();
				}

				if(this.rootNode == null) {
					this.rootNode = rulebook.InitialisationRule(this.masterPlan);
				}
				if(this.rootNode.Tick(rulebook) == PlanChange.FailChange) {
					#if PB_DEBUG
					Console.WriteLine("PB: MasterPlan Failed");
					#endif
					//this.rootNode.Deactivate();
					//this.rootNode = null;
	
				}
				lock(fpEvents) {
					this.fpEvents.Clear();
				}
				ulong now = RosSharp.Now();
				if((this.rulebook.ChangeOccurred && this.lastSendTime + this.minSendInterval < now) || this.lastSendTime + this.maxSendInterval < now) {
				//if(this.lastSendTime + this.sendInterval < RosSharp.Now()) {
					List<long> msg = new List<long>();
					this.deepestNode = this.rootNode;
					this.treeDepth = 0;
					this.rootNode.ToMessage(msg,ref this.deepestNode, ref this.treeDepth,0);
					this.teamObserver.DoBroadcast(msg);
					this.lastSendTime = now;
					this.rulebook.ChangeOccurred = false;
				}
				if(this.sendStatusMessages && this.lastSentStatusTime + this.sendStatusInterval < RosSharp.Now()) {
					if (this.deepestNode != null) {
						this.statusMessage.RobotIDsWithMe.Clear();						
						this.statusMessage.CurrentPlan = this.deepestNode.Plan.Name;
						if (this.deepestNode.OwnEntryPoint!=null) {
							this.statusMessage.CurrentTask = this.deepestNode.OwnEntryPoint.Task.Name;
						} else {
							this.statusMessage.CurrentTask = "IDLE";							
						}
						if (this.deepestNode.ActiveState!=null) {
							this.statusMessage.CurrentState = this.deepestNode.ActiveState.Name;
							this.statusMessage.RobotIDsWithMe.AddRange(this.deepestNode.Assignment.RobotStateMapping.GetRobotsInState(this.deepestNode.ActiveState));
						} else {
							this.statusMessage.CurrentState = "NONE";							
						}
						this.statusMessage.CurrentRole = this.ra.OwnRole.Name;						
						this.statusPublisher.Send(this.statusMessage);
						this.lastSentStatusTime = RosSharp.Now();
						
					}
				}
				
				this.log.IterationEnds(this.rootNode);
				
				this.ae.IterationComplete();
#if COSTLY_DEBUG_CHECKS
				this.CheckPlanBase(this.rootNode);
#endif				
				/*
				
				if (sleepTime > 0) {
					RosSharp.Sleep(sleepTime);
				}*/
				this.loopGuard.WaitOne();
					
				int availTime = (int)((this.loopTime-(RosSharp.Now()-beginTime))/1000000UL);	
				if (fpEvents.Count > 0 ) { 
					lock(fpEvents) {
						while(this.running && availTime > 1 && fpEvents.Count > 0) {
							RunningPlan rp = fpEvents.Dequeue();
							
		//Console.WriteLine("Fast Path Active {0}ms in front",availTime);						
							if(rp.Active) {
								bool first = true;
								while(rp!=null) {
									PlanChange change = this.rulebook.Visit(rp);
									if(!first && change == PlanChange.NoChange) break;
		//Console.WriteLine("Reaction in {0}",rp.Plan.Name);								
									rp = rp.Parent;
									first = false;
								}
							}
							//this.loopGuard.WaitOne();
							availTime = (int)((this.loopTime-(RosSharp.Now()-beginTime))/1000000UL);
						}
					}
				}
				if(availTime > 1) RosSharp.Sleep(availTime);				
					
			}
		}
		/// <summary>
		/// Stops the plan base thread.
		/// </summary>
		public void Stop() {
			this.running = false;
			if(AlicaEngine.Get().StepEngine) this.Signal.Set();
			this.loopGuard.Set();
			this.loopGuard.Set();
			if(this.mainThread != Thread.CurrentThread) {
			   this.mainThread.Join();
			}
			if (this.loopTimer != null) {
				this.loopTimer.Stop();
				this.loopTimer = null;
			}			
		}
		/// <summary>
		/// Set a custom RuleBook to use.
		/// </summary>
		/// <param name="rb">
		/// A <see cref="RuleBook"/>
		/// </param>
		public void SetRuleBook(RuleBook rb) {
			this.rulebook = rb;
		}
		
		private void TimerCallBack(object o) {
			this.loopGuard.Set();
		}
		
		internal void AddFastPathEvent(RunningPlan p) {
			lock(fpEvents) {fpEvents.Enqueue(p);}
			//fpEvents.AddLast(p);
			this.loopGuard.Set();			
		}
		internal void CheckPlanBase(RunningPlan r) {
			if(r == null) return;
			if(r.IsBehaviour) return;
			List<int> robots = r.Assignment.GetAllRobots();
			foreach(RunningPlan c in r.Children) {
				if(c.IsBehaviour) continue;
				List<int> cr= c.Assignment.GetAllRobots();
				foreach(int rid in cr) {
					if (!robots.Contains(rid)) {
						AlicaEngine.Get().Abort(String.Format("Mismatch Assignment in Plan {0}: {1}",c.Plan.Name,rid));
					}
				}
				CheckPlanBase(c);
			}
			
		}
	}
}

