#define BEH_DEBUG
using System;
using System.Collections.Generic;
using System.Threading;

using Castor;
using RosCS;
namespace Alica
{
	/// <summary>
	/// The base class for all behaviours. All Behaviours must inherit from this class.
	/// </summary>
	public abstract class BasicBehaviour
	{		
		
		//protected static object msgLock = new Object();
				
		
		//protected long lasttime = 0;
		
		public int DueTime {get; set;}
		public int Period {get; set;}
		
		/// <summary>
		/// The name of this behaviour.
		/// </summary>
		protected string name = "";
		
		/// <summary>
		/// Parameters are behaviour configuration specific fixed values. They are set before the behaviour is activated.
		/// </summary>
		protected Dictionary<string, string> parameters = new Dictionary<string, string>();
		
		private LinkedList<Variable> variables = null;
		
		private RunningPlan runningPlan = null;
		private bool success = false;
		private bool failure = false;
		
		public object MessageObj {get;set;}
		
		
		public AutoResetEvent Signaler {get; private set;}
		private bool terminating;
		private EventListener listener;
		private bool callInit;
		
		/// <summary>
		/// Basic constructor. Initialises various timers. Should only be called from the contructor of inherting classes.
		/// </summary>
		/// <param name='name'>
		/// The name of the behaviour.
		/// </param>
		protected BasicBehaviour(string name)
		{						
						
			this.name = name;
			this.Signaler = new AutoResetEvent(false);
			this.listener = new EventListener(this);
			this.terminating = false;
			this.callInit = false;
			Thread exe = new Thread(new ThreadStart(this.__RunEventDriven));
			exe.Start();					
			
		}
		
		
		
		
		private void __RunEventDriven()	{			
			while(!AlicaEngine.Get().IsTerminating && !this.terminating) {
				this.Signaler.WaitOne();
				if (this.terminating) return;
				if (this.callInit) this.Init();
#if BEH_DEBUG
				ulong now = RosSharp.Now();
#endif
				try {
					Run(MessageObj);
				} catch(Exception e) {					
					Node.MainNode.RosError(String.Format("Exception in Behaviour: {0}\n{1}",this.name,e.ToString()));
				}
#if BEH_DEBUG
				BehaviourConfiguration conf = (BehaviourConfiguration)(this.RunningPlan.Plan);
				if(!conf.EventDriven) {
					double delta =  ((double)(RosSharp.Now() - now))/1000000.0-1.0/conf.Frequency*1000.0;					
					if (delta > 0.1) {						
						Node.MainNode.RosWarn(String.Format("Behaviour "+conf.Behaviour.Name+" exceeded runtime by {0,1:0.##}ms!",delta));
					}
				}
#endif
			}
		}
		/// <summary>
		/// Terminate this behaviour, should only be called if the Engine shuts down. (Called by the <see cref="IBehaviourPool"/>).
		/// </summary>
		internal void Terminate() {
			this.terminating = true;
			this.listener.Terminate();
			this.Signaler.Set();
			
		}
		
		/// <summary>
		/// Starts this behaviour, called by the <see cref="IBehaviourPool"/>.
		/// </summary>
		internal void Start() {
			this.callInit = true;
			this.listener.Start();
		}
		/// <summary>
		/// Stops this behaviour, called by the <see cref="IBehaviourPool"/>.
		/// </summary>
		internal void Stop() {
			this.listener.Stop();
		}
		/// <summary>
		/// The set of static Parameters as defined by the <see cref="BehaviourConfiguration"/>.
		/// </summary>	
		public Dictionary<string, string> Parameters
		{
			internal set { this.parameters = value; }
			get { return this.parameters; }			
		}
		/// <summary>
		/// The set of <see cref="Variable"/>s attached to this behaviours as defined by the <see cref="BehaviourConfiguration"/>.
		/// </summary>
		public LinkedList<Variable> Variables
		{
			internal set { this.variables = value; }
			get { return this.variables; }			
		}
		/// <summary>
		/// The Success flag. Raised by a behaviour to indicate it reached whatever it ment to reach.
		/// </summary>
		public bool SuccessStatus
		{		
			set {
				bool notify = value && !this.success;				
				this.success = value;
				if (notify) AlicaEngine.Get().PB.AddFastPathEvent(this.runningPlan);
			}
			get { return this.success && !this.callInit; }			
		}
		/// <summary>
		/// The Failure flag. Raised by a behaviour to indicate it has failed in some way.
		/// </summary>
		public bool FailureStatus
		{
			set {
				bool notify = value && !this.failure;
				this.failure = value; 
				if (notify) AlicaEngine.Get().PB.AddFastPathEvent(this.runningPlan);
			}
			get { return this.failure && !this.callInit; }			
		}
		/// <summary>
		/// Gets or sets the name.
		/// </summary>
		/// <value>
		/// The name.
		/// </value>
		public string Name
		{
			set { this.name = value; }
			get { return this.name; }			
		}
		/// <summary>
		/// The running plan representing this behaviour within the <see cref="PlanBase"/>.
		/// </summary>
		public RunningPlan RunningPlan
		{
			internal set { this.runningPlan = value; }
			get { return this.runningPlan; }			
		}
		/// <summary>
		/// Access a variable's by its name.
		/// </summary>
		/// <param name="name">
		/// A <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Int64"/>
		/// </returns>
		public long VariableIdByName(string name) {
			foreach(Variable v in this.Variables) {
				if (v.Name.Equals(name)) return v.Id;
			}
			return -1;
		}
		/// <summary>
		/// Access a variable by its name. This is the most common way to access variables during the <see cref="Run"/> method of the behaviour.
		/// </summary>
		/// <param name="name">
		/// A <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// A <see cref="Variable"/>
		/// </returns>
		public Variable VariableByName(string name) {
			foreach(Variable v in this.Variables) {
				if (v.Name.Equals(name)) return v;
			}
			return null;
		}		
		
		/// <summary>
		/// Called by the runloop of the behaviour, wraps <see cref="InitializeParameters"/>
		/// </summary>
		private void Init()
		{	
			
			this.success = false;
			this.failure = false;
			this.callInit = false;
			try
			{
				InitializeParameters();
			}
			catch(Exception e)
			{
				RosCS.Node.MainNode.RosError("Exception in Behaviour-INIT of: " + this.name + "\n" + e.ToString());
			}
		}
		
		/// <summary>
		/// Called whenever a behaviour is started, i.e., when the corresponding state is entered.
		/// Override for behaviour specific initialisation
		/// </summary>
		protected virtual void InitializeParameters()
		{			
		}
		
		
		
		/// <summary>
		/// The running loop of a behaviour, to be overriden by a specific behaviour
		/// </summary>
		/// <param name="msg">
		/// A <see cref="System.Object"/>, null is called by a timer, otherwise defined by the callee of the <see cref="EngineTrigger"/>
		/// </param>
		public abstract void Run(object msg);
		
		/// <summary>
		/// Override this to have <see cref="Run"/> called on specific events.
		/// The EngineTrigger can be added to any appropriately typed event. SetTrigger will only be called once.
		/// </summary>
		/// <param name="e">
		/// A <see cref="EngineTrigger"/>
		/// </param>
		public virtual void SetTrigger(EngineTrigger e) {			
		}
		
		
		//Behaviours querying the Engine:
		
		/// <summary>
		/// Can be used to query plan information of the parent plan from within a behaviour.
		/// Usually called from within <see cref="InitializeParameters"/>
		/// </summary>
		/// <param name="taskName">
		/// A <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// The <see cref="EntryPoint"/> of the parent plan. Can be used to query for robots (<see cref="RobotsInEntryPoint"/>).
		/// </returns>
		protected EntryPoint GetParentEntryPoint(string taskName) {
			foreach(EntryPoint e in ((Plan)this.RunningPlan.Parent.Plan).EntryPoints.Values) {
				if (e.Task.Name.Equals(taskName)) return e;
			}
			return null;
		}
		/// <summary>
		/// Similar to <see cref="GetParentEntryPoint"/>, but queries the plan hierarchy upwards.
		/// </summary>
		/// <param name="planName">
		/// A <see cref="System.String"/>
		/// </param>
		/// <param name="taskName">
		/// A <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// The <see cref="EntryPoint"/> of an ancestor plan. Can be used to query for robots (<see cref="RobotsInEntryPointOfHigherPlan"/>).
		/// </returns>
		protected EntryPoint GetHigherEntryPoint(string planName,string taskName) {			
			RunningPlan cur = this.RunningPlan.Parent;
			while(cur!=null) {
				if (cur.Plan.Name.Equals(planName)) {
					foreach(EntryPoint e in ((Plan)cur.Plan).EntryPoints.Values) {
						if (e.Task.Name.Equals(taskName)) return e;
					}
					return null;
				}
				cur = cur.Parent;
			}
			return null;
		}
		
		
		/// <summary>
		/// Returns the set of robots that are currently inhabiting the state in which this behaviour is executed
		/// </summary>
		/// <returns>
		/// A set containing all ids of robots inhabiting the state in which the current behaviour is executed
		/// </returns>
		protected HashSet<int> RobotsInThisState() {
			return this.RunningPlan.Parent.Assignment.RobotStateMapping.GetRobotsInState(this.RunningPlan.Parent.ActiveState);			
		}
		/// <summary>
		/// Returns the set of robots that currently inhabit state s in the parent plan.
		/// </summary>
		/// <param name="s">
		/// A <see cref="State"/>
		/// </param>
		/// <returns>
		/// A set of robot ids.
		/// </returns>
		protected HashSet<int> RobotsInState(State s) {
			if (s==null) return null;
			return this.RunningPlan.Parent.Assignment.RobotStateMapping.GetRobotsInState(s);	
		} 			
		/// <summary>
		/// Returns all robots currently working on the parent plan.
		/// </summary>
		/// <returns>
		/// A <see cref="List`1"/>
		/// </returns>
		protected List<int> RobotsInThisPlan() {
			return this.RunningPlan.Parent.Assignment.GetAllRobots();
		}
		/// <summary>
		/// Returns all robots currently working on the task this robot is assigned to in the parent plan.
		/// </summary>
		/// <returns>
		/// A <see cref="ICollection`1"/>
		/// </returns>
		protected ICollection<int> RobotsInThisTask() {
			return this.RunningPlan.Parent.Assignment.GetRobotsWorking(this.RunningPlan.Parent.OwnEntryPoint);
		}
		/// <summary>
		// Returns all robots currently working on the task identified by the provided entrypoint.
		/// </summary>
		/// <param name="ep">
		/// A <see cref="EntryPoint"/>
		/// </param>
		/// <returns>
		/// A <see cref="ICollection`1"/>
		/// </returns>
		protected ICollection<int> RobotsInEntryPoint(EntryPoint ep) {
			if (ep==null) return null;
			return this.RunningPlan.Parent.Assignment.GetRobotsWorking(ep);
		}
		/// <summary>
		/// Allows to query for robots that are working on a different task at some higher level of the plan hierarchy
		/// </summary>
		/// <returns>
		/// The set of robots, identified by id that are working in the specified EntryPoint.
		/// </returns>
		/// <param name='ep'>
		/// The EntryPoint of the higher level plan.
		/// </param>
		protected ICollection<int> RobotsInEntryPointOfHigherPlan(EntryPoint ep) {
			if (ep==null) return null;
			RunningPlan cur = this.RunningPlan.Parent;
			while(cur!=null) {
				if(((Plan)cur.Plan).EntryPoints.ContainsKey(ep.Id)) {
					return cur.Assignment.GetRobotsWorking(ep);
				}
				cur = cur.Parent;
			}
			return null;
		}
		/// <summary>
		/// Convenience method to obtain the robot's own id.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Int32"/>
		/// </returns>
		protected int GetOwnId() {
			return AlicaEngine.Get().TO.GetOwnId();
		}
		
	}
	
}
