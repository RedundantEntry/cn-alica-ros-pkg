
using System;
using System.Collections.Generic;
using Castor;

namespace Alica
{
	/// <summary>
	/// Captures the result of a rule application.
	/// </summary>
	public enum PlanChange {
		/// <summary>
		/// No Change occurred, rule was not applicable
		/// </summary>
		NoChange,
		/// <summary>
		/// Change occurred, but is of no interest to upper level plans
		/// </summary>
		InternalChange,
		/// <summary>
		/// Change occurred and led to a success, upper level can react
		/// </summary>
		SuccessChange,
		/// <summary>
		/// Change occurred and led to a failure, upper level should react
		/// </summary>
		FailChange
	}
	/// <summary>
	/// A RunningPlan represents a plan or a behaviour in execution, holding all informaiton relevant at runtime.
	/// </summary>
	public class RunningPlan
	{
		protected AbstractPlan plan;
		protected Assignment ass;		
		
		protected RunningPlan parent;

		protected State activeState;
		protected EntryPoint activeEntryPoint;
		
		protected PlanStatus status;
		protected ulong stateStartTime;
		protected ulong planStartTime;
		
		/// <summary>
		/// The (ROS-)timestamp referring to when this plan was started by the local robot.
		/// </summary>		
		public ulong PlanStartTime {get { return this.planStartTime;}}
		/// <summary>
		/// The (ROS-)timestamp referring to when the local robot entered the <see cref="ActiveState"/>.
		/// </summary>
		public ulong StateStartTime {get { return this.stateStartTime;}}
		
		
		protected int ownId;
		
		protected List<RunningPlan> children;
		
		//protected VariableSet vars;
			
		protected List<int> robotsAvail;
		protected Dictionary<AbstractPlan,int> failedSubPlans;
		
		protected PlanType planType;
		
		protected int failCount;
		protected bool failHandlingNeeded;
		
		/// <summary>
		/// Whether or not this running plan is active or has been removed from the plan tree
		/// </summary>
		public bool Active {get; private set;}
		
		protected IBehaviourPool bp;
		protected ITeamObserver to;
		
		protected static ulong assignmentProtectionTime = SystemConfig.LocalInstance["Alica"].GetULong("Alica.AssignmentProtectionTime")*1000000UL;
				

		internal protected RunningPlan() {
			
			this.to = AlicaEngine.Get().TO;
			this.ownId = to.GetOwnId();
			this.children = new List<RunningPlan>();
			this.robotsAvail = new List<int>();
			
			this.status = PlanStatus.RUNNING;
			this.failCount = 0;
			this.BasicBehaviour = null;
			//this.RuntimeCondition = true;
			
			this.failedSubPlans = new Dictionary<AbstractPlan, int>();
			this.Active = false;
			this.AllocationNeeded = false;
			this.FailHandlingNeeded = false;
			
			//this.vars = new VariableSet(this);
			this.ConstraintStore = new ConstraintStore(this);
			this.CycleManagement = new CycleManager(this);
			
		}
		
		internal RunningPlan(Plan plan) :this() {
						
			this.Plan = plan;
			
			ICollection<EntryPoint> epCol = plan.EntryPoints.Values;
			List<EntryPoint> epList = new List<EntryPoint>(epCol);
			epList.Sort();
			EntryPoint[] eps = epList.ToArray();
			HashSet<int>[] robots = new HashSet<int>[eps.Length];
			for(int i=0; i<eps.Length; i++) {
				robots[i] = new HashSet<int>();	
			}
			//this.Assignment = new Assignment(0,eps,robots,plan);
			
			this.IsBehaviour = false;
			
			
		}
		internal RunningPlan(PlanType pt): this() {
			this.Plan = null;
			this.planType = pt;
			this.IsBehaviour = false;
			
		}
		internal RunningPlan(BehaviourConfiguration bc) :this() {
			this.Plan = bc;
			this.IsBehaviour = true;			
			this.bp = AlicaEngine.Get().BP;
			
		}
		/// <summary>
		/// Indicate that an AbstractPlan has failed while being a child of this plan.
		/// </summary>
		/// <param name="child">
		/// A <see cref="AbstractPlan"/>
		/// </param>
		internal void SetFailedChild(AbstractPlan child) {
			if(this.failedSubPlans.ContainsKey(child)) {
				this.failedSubPlans[child]++;
			} else {
				this.failedSubPlans.Add(child,1);
			}
		}
		/// <summary>
		/// Clears the failure history of failed plans.
		/// </summary>
		internal void ClearFailedChildren() {
			this.failedSubPlans.Clear();
		}
		/// <summary>
		/// Sets the set of robots currently participating in this plan.
		/// </summary>
		/// <param name="robots">
		/// A <see cref="List<System.Int32>"/>
		/// </param>
		internal void SetAvailRobots(List<int> robots) {
			this.robotsAvail.Clear();
			this.robotsAvail.AddRange(robots);
		}
		/// <summary>
		/// Returns all robots currently participating in this plan.
		/// </summary>
		public List<int> AvailableRobots {get {return this.robotsAvail;}}
		
		internal void SetRobotAvail(int robot) {
			if (this.robotsAvail.Contains(robot)) return;
			this.robotsAvail.Add(robot);
		}
		internal void SetRobotUnAvail(int robot) {
			this.robotsAvail.Remove(robot);			
		}
		
		/// <summary>
		/// Called once per Engine iteration, performs all neccessary checks and executes rules from the rulebook.
		/// </summary>
		/// <param name="rules">
		/// A <see cref="RuleBook"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		internal PlanChange Tick(RuleBook rules) {
			this.CycleManagement.Update();
			PlanChange myChange = rules.Visit(this);
			PlanChange childChange = PlanChange.NoChange;
			for(int i=0; i<this.children.Count; i++) {
				childChange = rules.UpdateChange(childChange,this.children[i].Tick(rules));
			}
			if (childChange != PlanChange.NoChange && childChange != PlanChange.InternalChange) {
				myChange = rules.UpdateChange(myChange,rules.Visit(this));
			}
			return myChange;
		}
		/// <summary>
		/// General Visitor pattern for the plan graph.
		/// </summary>
		/// <param name="vis">
		/// A <see cref="IPlanTreeVisitor"/>
		/// </param>
		public void Accept(IPlanTreeVisitor vis) {
			vis.Visit(this);
			for(int i=0; i<this.children.Count; i++) {
				this.children[i].Accept(vis);
			}			
		}
		/// <summary>
		/// Deactivate this plan, to be called before the plan is removed from the graph.
		/// Ensures that all sub-behaviours are stopped and all constraints are revoked.
		/// </summary>
		/// <param name="allAreLeaving">
		/// Indicates if all robots are leaving the corresponding plan. This will determine wether or not success marks are reset.
		/// </param>
		internal void Deactivate(bool allAreLeaving) {
			this.Active = false;
			if (this.IsBehaviour) {
				bp.RemoveBehaviour(this);				
			} else {
				if (allAreLeaving) {
					this.to.NotifyTeamLeftPlan(this.Plan);
				} else {
					this.to.NotifyILeftPlan(this.Plan);
				}
			}
			RevokeAllConstraints();
			DeactivateChildren(allAreLeaving);
			
		}
		/// <summary>
		/// Remove all children without passing any command to them.
		/// </summary>
		internal void ClearChildren() {
			this.children.Clear();
		}	
		/// <summary>
		/// Deactivate all children, causing behaviours to be stopped and constraints to be revoked.
		/// </summary>
		/// <param name="allAreLeaving">
		/// Indicates whether the team is beliefed to be deactivating the corresponding plans as well.
		/// </param>
		internal void DeactivateChildren(bool allAreLeaving) {			
			foreach(RunningPlan r in this.children) {
				r.Deactivate(allAreLeaving);
			}
		}
		/// <summary>
		/// Tests whether any child has a specific status.
		/// </summary>
		/// <param name="ps">
		/// A <see cref="PlanStatus"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool AnyChildrenStatus(PlanStatus ps) {
			for(int i=0; i<this.children.Count; i++) {
				if (ps == this.children[i].Status) return true;
			}
			return false;
		}
		/// <summary>
		/// Tests whether for any child, the robot completed a task
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool AnyChildrenTaskSuccess() {			
			for(int i=0; i<this.children.Count; i++) {
				if (this.children[i].IsBehaviour) {
					if (this.children[i].Status == PlanStatus.SUCCESS) return true;					
				} else if (this.children[i].ActiveState != null && this.children[i].ActiveState.IsSuccessState) return true;
				foreach(List<int> successes in this.children[i].Assignment.EpSuccessMapping.Robots) {
					if (successes.Contains(this.ownId)) return true;
				}
			}
			return false;			
		}
		/// <summary>
		/// Tests whether for any child, the robot failed a task
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool AnyChildrenTaskFailure() {			
			for(int i=0; i<this.children.Count; i++) {
				if (this.children[i].Status == PlanStatus.FAILED) return true;
				
				if (this.children[i].ActiveState != null && this.children[i].ActiveState.IsFailureState) return true;
				
			}
			return false;			
		}
		/// <summary>
		/// Tests whether for any child, the robot reached a terminal state
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool AnyChildrenTaskTerminated() {			
			for(int i=0; i<this.children.Count; i++) {
				if (this.children[i].IsBehaviour) {
					if (this.children[i].Status == PlanStatus.FAILED || this.children[i].Status == PlanStatus.SUCCESS) return true;					
				} else if (this.children[i].ActiveState != null && this.children[i].ActiveState.IsTerminal) return true;
			}
			return false;			
		}
		
		/// <summary>
		/// Move this very robot to another state. Performs all neccessary operations, such as updating the assignment.
		/// </summary>
		/// <param name="nextState">
		/// A <see cref="State"/>
		/// </param>
		internal void MoveState(State nextState) {
			
			DeactivateChildren(true);			
			ClearChildren();
			
			
			this.Assignment.MoveRobots(this.activeState,nextState);
			
			ActiveState = nextState;
			this.failedSubPlans.Clear();
		}
		/// <summary>
		/// Activate this plan, called when it is inserted into the plan graph.
		/// </summary>
		internal void Activate() {
			this.Active = true;
			if(this.IsBehaviour) {
				bp.AddBehaviour(this);
			}
			this.AttachPlanConstraints();
			foreach(RunningPlan r in this.children) {
				r.Activate();
			}
		}
		/// <summary>
		/// The robot's current EntryPoint. Null if it is idling
		/// </summary>
		public EntryPoint OwnEntryPoint {
			get {return this.activeEntryPoint;}
			internal set {
				if (this.activeEntryPoint != value) {
					this.Assignment.RemoveRobot(ownId);
					this.activeEntryPoint = value;
					if (this.activeEntryPoint != null) {					
						this.ActiveState = this.activeEntryPoint.State;	
						this.Assignment.AddRobot(ownId,this.activeEntryPoint,this.ActiveState);											
					}
					
				}
				
			}
		}
		/// <summary>
		/// Adapt the assignment of this plan to the one supplied. This can also change plan
		/// </summary>
		/// <param name="newR">
		/// A <see cref="RunningPlan"/>
		/// </param>
		internal void AdaptAssignment(RunningPlan newR) {
			State newState = newR.Assignment.RobotStateMapping.GetState(ownId);
			newR.Assignment.RobotStateMapping.ReconsiderOldAssignment(this.Assignment,newR.Assignment);
			bool reactivate = false;
			
			if (this.ActiveState != newState) {
				this.Active = false;
				this.DeactivateChildren(false);
				this.RevokeAllConstraints();
				this.ClearChildren();
				this.AddChildren(newR.children);					
				this.failedSubPlans.Clear();
				reactivate = true;
			} else {
				HashSet<int> robotsJoined = newR.Assignment.RobotStateMapping.GetRobotsInState(newState);
				foreach(RunningPlan r in this.Children) {
					r.LimitToRobots(robotsJoined);
				}
			}
			
			this.Plan = newR.Plan;			
			this.activeEntryPoint = newR.OwnEntryPoint;			
			this.Assignment = newR.Assignment;
			this.ActiveState = newState;
			if (reactivate) {
				this.Activate();
			}
			
		}
		/// <summary>
		/// Removes any robot not in robots
		/// </summary>
		/// <param name='robots'>
		/// The set of robots that can participate in this running plan.
		/// </param>
		public void LimitToRobots(ICollection<int> robots) {
			if(this.IsBehaviour) return;
			if(!this.CycleManagement.MayDoUtilityCheck()) return;
			bool recurse = false;
			List<int> curRobots = this.Assignment.GetAllRobots();
			foreach(int r in curRobots) {
				if(!robots.Contains(r)) {
					if(this.ActiveState!= null && this.Assignment.RobotStateMapping.StateOfRobot(r)==this.ActiveState) recurse = true;
					this.Assignment.RemoveRobot(r);
				}
			}
			if(recurse) {
				foreach(RunningPlan c in this.Children) {
					c.LimitToRobots(robots);
				}
			}

		}
		/// <summary>
		/// Indicates wether an allocation is needed in the <see cref="RunningPlan.ActiveState"/>.
		/// If set to true, the next engine iteration will perform a task allocation and set it to false.
		/// </summary>
		/// <value>
		/// <c>true</c> if allocation needed; otherwise, <c>false</c>.
		/// </value>
		public bool AllocationNeeded { get; internal set; }
		/// <summary>
		/// The <see cref="CycleManager"/> of this running plan, which detects and resolvs conflicts in task allocation.
		/// </summary>
		/// <value>
		/// The cycle manager
		/// </value>
		public CycleManager CycleManagement { get; private set;}
		/// <summary>
		/// Indicates whether this running plan represents a behaviour.
		/// </summary>
		/// <value>
		/// <c>true</c> if this instance is representing a behaviour; otherwise, <c>false</c>.
		/// </value>
		public bool IsBehaviour{get; private set; }
		/// <summary>
		/// The behaviour represented by this running plan, in case there is any, otherwise null.
		/// </summary>
		/// <value>
		/// The basic behaviour.
		/// </value>
		public BasicBehaviour BasicBehaviour{get; internal set;}
		
		//public bool RuntimeCondition{get; set;}
		
		
		/// <summary>
		/// Indicates whether this plan needs failure handling
		/// </summary>
		public bool FailHandlingNeeded { 
			get {
				return this.failHandlingNeeded;
			}
			internal set {
				if (value) this.status = PlanStatus.FAILED;
				else {
					if (this.status == PlanStatus.FAILED) {
						this.status = PlanStatus.RUNNING;
					}
				}
				this.failHandlingNeeded = value;
			}			
		}
		/// <summary>
		/// The current assignment of robots to <see cref="EntryPoint"/>s.
		/// </summary>
		/// <value>
		/// The assignment.
		/// </value>
		public Assignment Assignment{
			get { return this.ass;}
			internal set { this.ass = value;}
		}
		/// <summary>
		/// The children of this RunningPlan.
		/// </summary>
		/// <value>
		/// The children.
		/// </value>
		public List<RunningPlan> Children {
			get { return this.children; }
		}
		/// <summary>
		/// The abstract plan associated with this running plan, a model element.
		/// </summary>
		/// <value>
		/// The plan.
		/// </value>
		public AbstractPlan Plan {
			get {return this.plan;}
			internal set {
				if (this.plan != value) {
					this.planStartTime = RosCS.RosSharp.Now();
					this.ConstraintStore.Clear();
				}
				this.plan = value;
			}
		}
		/// <summary>
		/// Gets the state currently inhabited by the local agent. Null if none exists.
		/// </summary>
		/// <value>
		/// The state of the active.
		/// </value>
		public State ActiveState {
			get {return this.activeState;}
			internal set {
				if (this.activeState != value) {
					this.activeState = value;
					this.stateStartTime = RosCS.RosSharp.Now();
					if (this.activeState!=null) {
						if (this.activeState.IsFailureState) {
							this.status = PlanStatus.FAILED;
						} else if(this.activeState.IsSuccessState) {
							//this.status = PlanStatus.SUCCESS;
							//Console.WriteLine("Success State reached!");
							this.Assignment.EpSuccessMapping.GetRobots(this.activeEntryPoint).Add(this.ownId);
							this.to.GetOwnEngineData().SuccessMarks.MarkSuccessfull(this.plan,this.activeEntryPoint);
						}
					}
				}
				
			}
		}
		/// <summary>
		/// Gets the parent RunningPlan of this RunningPlan. Null in case this is the top-level element.
		/// </summary>
		/// <value>
		/// The parent.
		/// </value>
		public RunningPlan Parent {
			get { return this.parent;}
			internal set { this.parent = value;}
		}
		/// <summary>
		/// Gets the PlanType of the currently executed plan. Null if the <see cref="AbstractPlan"/> associated does not belong to a PlanType.
		/// </summary>
		/// <value>
		/// The type of the realised plan.
		/// </value>
		public PlanType RealisedPlanType {
			get { return this.planType;}
		}
		/*public VariableSet Variables {
			get { return this.vars; }
		}*/
		public PlanStatus Status {
			get { 
				if(this.BasicBehaviour!=null) {
					if (this.BasicBehaviour.SuccessStatus) return PlanStatus.SUCCESS;
					if (this.BasicBehaviour.FailureStatus) return PlanStatus.FAILED;
					return PlanStatus.RUNNING;
				}
				if (this.Assignment != null && this.Assignment.IsSuccessfull()) return PlanStatus.SUCCESS;
				return this.status;
			}
		}
		/// <summary>
		/// Evaluates the precondition of the associated plan.
		/// </summary>
		/// <returns>
		/// Wether the precondition currently holds or not.
		/// </returns>
		public bool EvalPreCondition() {
			if(this.plan==null) {
				throw new Exception("Cannot Eval Condition, Plan is null");
			}
			if (this.Plan.PreCondition == null) return true;
			try {
				return this.Plan.PreCondition.Eval(this);
			} catch(Exception e) {
				RosCS.Node.MainNode.RosError("Exception in precondition: "+e.ToString());
				return false;
			}
		}
		/// <summary>
		/// Evals the runtime condition of the associated plan.
		/// </summary>
		/// <returns>
		/// Wether the runtime currently holds or not.
		/// </returns>		
		public bool EvalRuntimeCondition() {
			if(this.plan==null) {
				throw new Exception("Cannot Eval Condition, Plan is null");
			}
			if (this.Plan.RuntimeCondition == null) return true;
			try {
				return this.Plan.RuntimeCondition.Eval(this);
			} catch(Exception e) {
				RosCS.Node.MainNode.RosError("Exception in runtimecondition: "+e.ToString());
				return false;
			}
		}
		internal void AddChildren(ICollection<RunningPlan> children) {
			foreach(RunningPlan r in children) {
				r.Parent = this;
				this.children.Add(r);
				int f=0;
				if(this.failedSubPlans.TryGetValue(r.Plan,out f)) {
					r.failCount = f;
				}
				if (this.Active) {
					r.Activate();
				}
			}
		}
		
		internal void AddFailure() {
			this.failCount++;
			this.FailHandlingNeeded = true;
		}
		/// <summary>
		/// Returns the number of failures detected while this RunningPlan was executed.
		/// </summary>
		/// <value>
		/// The number of failures detected.
		/// </value>
		public int Failure {get {return this.failCount;}}
		internal void ClearFailures() {
			this.failCount = 0;
		}
		
#region *** Constraint Specific ***
		/// <summary>
		/// Gets the constraint store, which contains all constrains associated with this RunningPlan.
		/// </summary>
		/// <value>
		/// The constraint store.
		/// </value>
		public ConstraintStore ConstraintStore {get; private set;}
		/*public void RecursiveAttachPlanConstraints() {
			ConstraintStore.AttachConstraint(this.Plan.PreCondition,this);
			ConstraintStore.AttachConstraint(this.Plan.RuntimeCondition,this);			
			foreach(RunningPlan p in this.Children) {
				p.RecursiveAttachPlanConstraints();
			}
			
		}
		public void RecursiveRevokeAllConstraints() {
			foreach(Variable v in this.Plan.Variables) {
				ConstraintStore.RemoveVariable(v.Id);
			}
			foreach(RunningPlan p in this.Children) {
				p.RecursiveRevokeAllConstraints();
			}			
		}*/
		//convenience method as recursive case might have been called for the children already
		internal void RevokeAllConstraints() {
			//lock(this.ConstraintStore) {
				this.ConstraintStore.Clear();
			//}
		}
		internal void AttachPlanConstraints() {
			this.ConstraintStore.AddCondition(this.Plan.PreCondition);
			this.ConstraintStore.AddCondition(this.Plan.RuntimeCondition);
		}
		
#endregion		
		
		
		internal bool RecursiveUpdateAssignment(List<SimplePlanTree> spts, List<int> availableAgents,List<int> noUpdates, ulong now) {
			if(this.IsBehaviour) return false;			
			bool keepTask = (this.planStartTime + assignmentProtectionTime > now);
			bool auth = this.CycleManagement.HaveAuthority();
			
			//if keepTask, the task Assignment should not be changed!
			bool ret = false;
			AllocationDifference aldif = new AllocationDifference();
			foreach(SimplePlanTree spt in spts) {
				if (spt.State.InPlan != this.Plan) { //the robot is no longer participating in this plan
					if(!keepTask & !auth) {
						EntryPoint ep = this.Assignment.EntryPointOfRobot(spt.RobotID);
						if(ep!=null) {
							this.Assignment.RemoveRobot(spt.RobotID);
							ret = true;
							aldif.subtractions.Add(new EntryPointRobotPair(ep,spt.RobotID));
						}
					}
				}
				else {
					if(keepTask || auth) { //Update only state, and that only if it is in the reachablity graph of its current entrypoint, else ignore
						EntryPoint cep = this.Assignment.EntryPointOfRobot(spt.RobotID);
						if (cep!=null) {
							if (cep.ReachableStates.Contains(spt.State)) {
								this.Assignment.RobotStateMapping.SetState(spt.RobotID,spt.State);
							}
						} else { //robot was not expected to be here during protected assignment time, add it.
							this.Assignment.AddRobot(spt.RobotID,spt.EntryPoint,spt.State);
							aldif.additions.Add(new EntryPointRobotPair(spt.EntryPoint,spt.RobotID));
							
						}
					} else {//Normal Update
						EntryPoint ep = this.Assignment.EntryPointOfRobot(spt.RobotID);													
						ret |= this.Assignment.UpdateRobot(spt.RobotID,spt.EntryPoint,spt.State);
						if (spt.EntryPoint != ep) {
							aldif.additions.Add(new EntryPointRobotPair(spt.EntryPoint,spt.RobotID));
							if(ep!=null) aldif.subtractions.Add(new EntryPointRobotPair(ep,spt.RobotID));
						}
						
					}
				}
			}
			EntryPoint[] eps = this.Assignment.GetEntryPoints();
			List<int> rem = new List<int>();
			if(!keepTask) { //remove any robot no longer available in the spts (auth flag obey here, as robot might be unavailable)
				//EntryPoint[] eps = this.Assignment.GetEntryPoints();
				
			
				for(int i=0; i<eps.Length; i++) {
					rem.Clear();
					ICollection<int> robs = this.Assignment.GetRobotsWorking(eps[i]);
					foreach(int rob in robs) {
						if (rob==ownId) continue;
						bool found = false;
						if (noUpdates.Contains(rob)) {
							//found = true;
							continue;
						}
						foreach(SimplePlanTree spt in spts) {
							if (spt.RobotID==rob) {
								found = true;
								break;
							}
						}
						if (!found) {
							rem.Add(rob);
							//this.Assignment.RemoveRobot(rob);
							aldif.subtractions.Add(new EntryPointRobotPair(eps[i],rob));
							ret = true;
						}						
					}
					foreach(int rob in rem) {
						this.Assignment.RemoveRobot(rob); //TODO: use entrypoint to speed things up
					}
				}				
			} 
			
			//enforce consistency between RA and PlanTree by removing robots deemed inactive:
			if(!auth) { //under authority do not remove robots from assignment
				for(int i=0; i<eps.Length; i++) {
					rem.Clear();
					ICollection<int> robs = this.Assignment.GetRobotsWorking(eps[i]);
					foreach(int rob in robs) {
						//if (rob==ownId) continue;
						if (!availableAgents.Contains(rob)) {
							rem.Add(rob);
							//this.Assignment.RemoveRobot(rob);
							aldif.subtractions.Add(new EntryPointRobotPair(eps[i],rob));
							ret = true;
						}						
					}
	
					foreach(int rob in rem) {
						this.Assignment.RemoveRobot(rob);
					}
				}
			}
			
			aldif.reason = AllocationDifference.Reason.message;
			this.CycleManagement.SetNewAllocDiff(this,aldif);
			//Update Success Collection:
			this.to.UpdateSuccessCollection((Plan)this.Plan,this.Assignment.EpSuccessMapping);
			
			//If Assignment Protection Time for newly started plans is over, limit available robots to those in this active state.
			if(this.StateStartTime + assignmentProtectionTime > now) {
				HashSet<int> robotsJoined = this.Assignment.RobotStateMapping.GetRobotsInState(this.ActiveState);
				for(int i=0; i<availableAgents.Count; i++) {
					if(!robotsJoined.Contains(availableAgents[i])) {
						availableAgents.RemoveAt(i);
						i--;
					}
				}
			} else if (auth) { // in case of authority, remove all that are not assigned to same task
				ICollection<int> robotsJoined = this.Assignment.GetRobotsWorking(this.OwnEntryPoint);
				for(int i=0; i<availableAgents.Count; i++) {
					if(!robotsJoined.Contains(availableAgents[i])) {
						availableAgents.RemoveAt(i);
						i--;
					}
				}
			}
			//Give Plans to children
			foreach(RunningPlan r in this.children) {
				if (r.IsBehaviour) continue;
				List<SimplePlanTree> newcspts = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in spts) {
					if(spt.State == this.ActiveState) {
						foreach(SimplePlanTree cspt in spt.Children) {
							if(cspt.State.InPlan == r.Plan) {
								newcspts.Add(cspt);
								break;
							}
						}
					}
				}
				ret |= r.RecursiveUpdateAssignment(newcspts, availableAgents,noUpdates,now);
			}
			return ret;
		}
		internal void ToMessage(List<long> message, ref RunningPlan deepestNode, ref int depth, int curDepth) {
			if(this.IsBehaviour) return;
			if (this.ActiveState!= null) message.Add(this.ActiveState.Id);
			else return;
			if (curDepth > depth) {
				depth = curDepth;
				deepestNode = this;
			}
			if(this.children.Count > 0) {
				message.Add(-1);
				foreach(RunningPlan r in this.children) {				
					r.ToMessage(message,ref deepestNode,ref depth,curDepth+1);
				}
				message.Add(-2);
			}
		}
		internal SimplePlanTree GetSimplePlanTree() { //unneccessary really, as SPTs are only constructed from messages, and RunningPlans should directly construct the message
			if (this.IsBehaviour) return null;
			SimplePlanTree ret = new SimplePlanTree(this.ActiveState,this.OwnEntryPoint);
			ret.RobotID = ownId;
			foreach(RunningPlan r in this.children) {
				if(!r.IsBehaviour) {
					SimplePlanTree c = r.GetSimplePlanTree();
					c.Parent = ret;
					ret.AddChild(c);
				}
			}
			return ret;
		}
		
		public override string ToString ()
		{
			string ret = "######## RP ##########\n";
			ret +="Plan: "+(Plan!=null?Plan.Name:"NULL")+"\n";
			ret +="PlanType: "+(planType!=null?planType.Name:"NULL")+"\n";
			ret +="ActState: "+(activeState!=null?activeState.Name:"NULL")+"\t";
			ret +="Task: "+(this.OwnEntryPoint!=null?OwnEntryPoint.Task.Name:"NULL")+"\n";
			ret +="IsBehaviour: "+this.IsBehaviour+"\t";
			if (this.IsBehaviour) {
				ret+="Behaviour: "+(this.BasicBehaviour==null?"NULL":this.BasicBehaviour.Name)+"\n";
			}
			ret +="AllocNeeded: "+this.AllocationNeeded+"\n";
			ret +="FailHNeeded: "+this.FailHandlingNeeded+"\t";
			ret +="FailCount: "+this.failCount+"\n";
			ret +="IsActive: "+this.Active+"\n";
			ret +="Status: "+(this.Status==PlanStatus.RUNNING?"RUNNING":(this.Status==PlanStatus.SUCCESS?"SUCCESS":"FAILED"))+"\n";
			ret +="AvailRobots: ";
			foreach(int r in this.robotsAvail) {ret+=" "+r;}
			ret+="\n";
			if (this.Assignment!=null) {
			ret+="Assignment:"+ this.Assignment.ToString();
			} else ret+="Assignment is null.\n";
			ret += "Children: "+this.children.Count;
			if (this.children.Count > 0) {
				ret += " ( ";
				foreach(RunningPlan r in this.children) {
					if (r.Plan==null) ret+="NULL PLAN - ";
					else ret +=r.Plan.Name+" - ";
				}
				ret += ")";
			}
			ret += "\n########## ENDRP ###########\n";
			return ret;
			
		}
		/// <summary>
		/// Simple method to recursively print the plan-tree.
		/// </summary>
		public void PrintRecursive() {
			Console.WriteLine(this);
			foreach(RunningPlan c in this.children) {
				c.PrintRecursive();
			}
			if(this.children.Count > 0) Console.WriteLine("END CHILDREN of {0}",(this.Plan==null?"NULL":this.Plan.Name));
		}
		                       
	}
}
