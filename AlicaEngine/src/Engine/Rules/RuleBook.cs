//#define RULE_debug

using System;
using System.Collections.Generic;
using Castor;
namespace Alica
{
	/// <summary>
	/// Defines the operational semantics of the used ALICA dialect.
	/// </summary>
	public class RuleBook
	{
		protected ITeamObserver to;
		protected ISyncModul sm;
		protected int maxConsecutiveChanges;
		protected IPlanSelector ps;
		protected Logger log;
		public bool ChangeOccurred { get; set;}
		
		/// <summary>
		/// Your basic constructor
		/// </summary>
		public RuleBook ()
		{
			to = AlicaEngine.Get().TO;
			ps = AlicaEngine.Get().PS;			
			sm = AlicaEngine.Get().SM;
			log = AlicaEngine.Get().Log;
			SystemConfig sc = SystemConfig.LocalInstance;
			maxConsecutiveChanges = sc["Alica"].GetInt("Alica.MaxRuleApplications");
			this.ChangeOccurred = false;
		}
		/// <summary>
		/// Implementation of the Init Rule
		/// </summary>
		/// <param name="masterPlan">
		/// A <see cref="Plan"/>
		/// </param>
		/// <returns>
		/// The <see cref="RunningPlan"/> constructed from the given plan.
		/// </returns>
		internal RunningPlan InitialisationRule(Plan masterPlan) {
			if (masterPlan.EntryPoints.Count != 1) {
				AlicaEngine.Get().Abort("RB: Masterplan does not have exactly one task!");				
			}
			
			RunningPlan main = new RunningPlan(masterPlan);
			main.Assignment = new Assignment(masterPlan);
			
			
			main.AllocationNeeded = true;
			List<int> robots = to.GetAvailableRobotIds();
			main.SetAvailRobots(robots);
			EntryPoint defep=null;
			foreach(EntryPoint e in masterPlan.EntryPoints.Values) {
				defep = e;
				break;
			}
			main.Assignment.SetAllToInitialState(robots,defep);
			main.Activate();
			main.OwnEntryPoint = defep;
			log.EventOccurred("Init");
			return main;
		}
		/// <summary>
		/// Called in every iteration by a RunningPlan to apply rules to it.
		/// Will consequetively apply rules until no further changes can be made or 
		/// <see cref="maxConsecutiveChanges"/> are made. This method also dictates the sequence in which rules are applied.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		public virtual PlanChange Visit(RunningPlan r) {
			int changes = 0;
			bool doDynAlloc = true;
			//Go Through all rules and apply
			PlanChange changeRecord = PlanChange.NoChange;
			PlanChange mschange = PlanChange.NoChange;
			do {
				mschange = UpdateChange(mschange,changeRecord);
				changeRecord = PlanChange.NoChange;
				changeRecord = UpdateChange(changeRecord,SynchTransitionRule(r));
				
				PlanChange transChange = TransitionRule(r);
				
				while(transChange != PlanChange.NoChange && ++changes < maxConsecutiveChanges) {
					changeRecord = UpdateChange(changeRecord,transChange);
					transChange = TransitionRule(r);
				}
				
				changeRecord = UpdateChange(changeRecord,TransitionRule(r));
				
				changeRecord = UpdateChange(changeRecord,AllocationRule(r));				
				changeRecord = UpdateChange(changeRecord,AuthorityOverrideRule(r));
				if(doDynAlloc) {
					changeRecord = UpdateChange(changeRecord,DynamicAllocationRule(r));
					doDynAlloc = false;
				}
				
				changeRecord = UpdateChange(changeRecord,PlanAbortRule(r));
				changeRecord = UpdateChange(changeRecord,TopFailRule(r));
				changeRecord = UpdateChange(changeRecord,PlanRedoRule(r));
				changeRecord = UpdateChange(changeRecord,PlanReplaceRule(r));				
				
				
				PlanChange propChange = PlanPropagationRule(r);
				changeRecord = UpdateChange(changeRecord,propChange);
				if (propChange != PlanChange.NoChange) break; //abort applying rules to this plan as propagation has occurred
				
			} while(changeRecord!=PlanChange.NoChange && ++changes < maxConsecutiveChanges);
			
			return mschange;
			
		}
		/// <summary>
		/// The abort rule, sets a failure if a failure state is reached, the allocation invalid or the runtimecondition does not hold.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange PlanAbortRule(RunningPlan r) {
			if (r.FailHandlingNeeded) return PlanChange.NoChange;
			if (r.IsBehaviour) return PlanChange.NoChange;
			if (r.Status == PlanStatus.SUCCESS) return PlanChange.NoChange; //a succeeded plan cannot be aborted
			if (!r.CycleManagement.MayDoUtilityCheck()) return PlanChange.NoChange; //do not abort plans executed under authority
			//if (r.CycleManagement.IsOverridden()) return PlanChange.NoChange;
			if ((r.ActiveState != null && r.ActiveState.IsFailureState) ||
			    !r.Assignment.IsValid() ||
			    !r.EvalRuntimeCondition()
			    )
			{
#if RULE_debug
				Console.WriteLine("RB: PlanAbort {0}",r.Plan.Name);
#endif
				r.AddFailure();				
				log.EventOccurred("PAbort("+r.Plan.Name+")");
				return PlanChange.FailChange;
			}
			return PlanChange.NoChange;
			
		}
		/// <summary>
		/// The transition rule, moves an agent along a transition to a next state if the corresponding condition holds,
		/// flags the RunningPlan for allocation in the next state.
		/// Note, in case multiple transitions are eligble, one is chosen implementation dependent.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange TransitionRule(RunningPlan r) {
			if (r.ActiveState == null) return PlanChange.NoChange;
			State nextState = null;
			foreach(Transition t in r.ActiveState.OutTransitions) {
				if (t.SyncTransition!=null) continue;
				if (t.EvalCondition(r)) {
					nextState = t.OutState;
					r.ConstraintStore.AddCondition(t.PreCondition);					
					break;
				}
			}
			if (nextState == null) return PlanChange.NoChange;
#if RULE_debug
				Console.WriteLine("RB: Transition {0}",r.Plan.Name);
#endif			
			
			r.MoveState(nextState);
			
			r.AllocationNeeded = true;
			log.EventOccurred("Transition("+r.Plan.Name+" to State "+r.ActiveState.Name+")");
			if (r.ActiveState.IsSuccessState) return PlanChange.SuccessChange;
			else if (r.ActiveState.IsFailureState) return PlanChange.FailChange;
			return PlanChange.InternalChange;
			
		}
		/// <summary>
		/// Moves the agent along a synchronised transition, if the corresponding transition holds and the <see cref="SynchModul"/>
		/// deems the transition as synchronised.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange SynchTransitionRule(RunningPlan r) {
			if(r.ActiveState == null) return PlanChange.NoChange;
			State nextState = null;
			foreach(Transition t in r.ActiveState.OutTransitions) {
				if (t.SyncTransition==null) continue;
				
				if (this.sm.FollowSyncTransition(t)) {
					if (t.EvalCondition(r)) {
						nextState = t.OutState;
						r.ConstraintStore.AddCondition(t.PreCondition);	
						break;
					} else {
						this.sm.SetSynchronisation(t,false);
					}
				} else {
					this.sm.SetSynchronisation(t,t.EvalCondition(r));
				}
			}
			if (nextState == null) return PlanChange.NoChange;
			#if RULE_debug
				Console.WriteLine("RB: SynchTransition {0}",r.Plan.Name);
			#endif
			r.MoveState(nextState);
			
			r.AllocationNeeded = true;
			log.EventOccurred("SynchTrans("+r.Plan.Name+")");
			if (r.ActiveState.IsSuccessState) return PlanChange.SuccessChange;
			else if (r.ActiveState.IsFailureState) return PlanChange.FailChange;
			return PlanChange.InternalChange;
		}
		/// <summary>
		/// Propagates a failure to the parent in case it couldn't be repaired on this level.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange PlanPropagationRule(RunningPlan r) {
			if(r.Parent==null || !r.FailHandlingNeeded || r.IsBehaviour) return PlanChange.NoChange;
			if(r.Failure < 3) return PlanChange.NoChange;
			r.Parent.AddFailure();			
			r.FailHandlingNeeded = false;
#if RULE_debug
				Console.WriteLine("RB: PlanPropagation {0}",r.Plan.Name);
#endif			
			log.EventOccurred("PProp("+r.Plan.Name+")");
			return PlanChange.FailChange;
			
		}
		/// <summary>
		/// Tries to repair a failure by removing this plan and triggering a new task allocation.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange PlanReplaceRule(RunningPlan r) {			
			if(r.Parent==null || !r.FailHandlingNeeded  || r.IsBehaviour) return PlanChange.NoChange;
			if(r.Failure != 2) return PlanChange.NoChange;
			r.Parent.DeactivateChildren(false);
			r.Parent.SetFailedChild(r.Plan);
			r.Parent.AllocationNeeded = true;
			r.Parent.ClearChildren();			
			r.FailHandlingNeeded = false;
#if RULE_debug
				Console.WriteLine("RB: PlanReplace {0}",r.Plan.Name);
#endif			
			log.EventOccurred("PReplace("+r.Plan.Name+")");
			return PlanChange.FailChange;
		}
		/// <summary>
		/// Tries to repair a plan by moving all robots in the current state to the corresponding initial state.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange PlanRedoRule(RunningPlan r) {
			if(r.Parent==null || !r.FailHandlingNeeded || r.IsBehaviour) return PlanChange.NoChange;
			if(r.Failure != 1) return PlanChange.NoChange;
			if(r.OwnEntryPoint== null)  return PlanChange.NoChange;
			if(r.ActiveState == r.OwnEntryPoint.State) { //Can't do anything
				r.AddFailure();
				return PlanChange.FailChange;
			}
			r.FailHandlingNeeded = false;
			r.DeactivateChildren(true);
			r.ClearChildren();
			r.Assignment.RobotStateMapping.SetStates(r.Assignment.RobotStateMapping.GetRobotsInState(r.ActiveState),r.OwnEntryPoint.State);
			r.ActiveState = r.OwnEntryPoint.State;
			r.AllocationNeeded = true;
#if RULE_debug
				Console.WriteLine("RB: PlanRedo {0}",r.Plan.Name);
#endif			
			log.EventOccurred("PRedo("+r.Plan.Name+")");
			return PlanChange.InternalChange;
		}
		/// <summary>
		/// Handles a failure at the top-level plan by resetting everything.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange TopFailRule(RunningPlan r) {
			if (r.Parent!=null) return PlanChange.NoChange;
			if(r.FailHandlingNeeded) {
				r.FailHandlingNeeded = false;
				r.ClearFailures();
				foreach(EntryPoint e in ((Plan)r.Plan).EntryPoints.Values) {
					r.OwnEntryPoint = e;
					break;
				}
			
				r.AllocationNeeded = true;
				List<int> robots = to.GetAvailableRobotIds();
				r.SetAvailRobots(robots);
				r.Assignment.Clear();
				r.Assignment.SetAllToInitialState(robots,r.OwnEntryPoint);
				r.ActiveState = r.OwnEntryPoint.State;
				r.ClearFailedChildren();
#if RULE_debug
				Console.WriteLine("RB: PlanTopFail {0}",r.Plan.Name);
#endif				
				log.EventOccurred("TopFail");
				return PlanChange.InternalChange;
			}
			return PlanChange.NoChange;
			
		}
		/// <summary>
		/// Adopts an authorative assignment in case the <see cref="CycleManager"/> of r is in overridden mode.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange AuthorityOverrideRule(RunningPlan r) {
			if (r.IsBehaviour) return PlanChange.NoChange;
			if (r.CycleManagement.IsOverridden()) {
				if (r.CycleManagement.SetAssignment(r)) {
					log.EventOccurred("AuthorityOverride("+r.Plan.Name+")");
					return PlanChange.InternalChange;
				}
			}
			return PlanChange.NoChange;
		}
		/// <summary>
		/// Changes the allocation of r to a better one, if one can be found and the plan is currently allowed to change allocation.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange DynamicAllocationRule(RunningPlan r) {
			if (r.AllocationNeeded || r.IsBehaviour) return PlanChange.NoChange;
			if (r.Parent== null) return PlanChange.NoChange; //masterplan excluded
			if (!r.CycleManagement.MayDoUtilityCheck()) return PlanChange.NoChange;						
			
			
			HashSet<int> robots = r.Parent.Assignment.RobotStateMapping.GetRobotsInState(r.Parent.ActiveState);	
/*
Console.Write("Robots for {0}: ",r.Plan.Name);
bool failed = false;
foreach(int rid in robots) {
Console.Write(rid+" ");
Console.WriteLine();
*/
//Console.WriteLine("Available Robot for Adapt in {0}: {1}",r.Plan.Name,robots.Count);			
			//Call to PS refreshes cache!
			RunningPlan newr = ps.GetBestSimilarAssignment(r,robots);
			if(newr == null) return PlanChange.NoChange;
			double curUtil;
			if(!r.EvalRuntimeCondition()) curUtil = -1.0;
			else curUtil = r.Plan.UtilityFunction.Eval(r,null);
			double possibleUtil = newr.Assignment.Max;
#if RULE_debug			
			Console.WriteLine("Old U: {0} | New U: {1}",curUtil,possibleUtil);
			Console.WriteLine("New Assignment: {0}",newr.Assignment);			
			
			Console.WriteLine("Old Assignment: {0}",r.Assignment);
#endif
			if(possibleUtil - curUtil > r.Plan.UtilityThreshold) {
				r.CycleManagement.SetNewAllocDiff(r,r.Assignment,newr.Assignment,AllocationDifference.Reason.utility);
				State before = r.ActiveState;
				r.AdaptAssignment(newr);
				if(r.ActiveState != null && r.ActiveState != before) r.AllocationNeeded = true;
#if RULE_debug
				Console.WriteLine("B4 dynChange: Util is {0} | suggested is {1} | threshold {2}",curUtil,possibleUtil,r.Plan.UtilityThreshold);
				Console.WriteLine("RB: DynAlloc {0}",r.Plan.Name);
#endif
				log.EventOccurred("DynAlloc("+r.Plan.Name+")");
				return PlanChange.InternalChange;
			} 
			
			return PlanChange.NoChange;
		}
		/// <summary>
		/// Allocates agents in the current state within r to sub-plans.
		/// </summary>
		/// <param name="r">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		protected PlanChange AllocationRule(RunningPlan r) {
			if(!r.AllocationNeeded) {
				return PlanChange.NoChange;
			}
			r.AllocationNeeded = false;
			LinkedList<RunningPlan> children = ps.GetPlansForState(r,r.ActiveState.Plans,r.Assignment.RobotStateMapping.GetRobotsInState(r.ActiveState));
			if (children==null || children.Count < r.ActiveState.Plans.Count) {
				r.AddFailure();
#if RULE_debug
				Console.WriteLine("RB: PlanAllocFailed! {0}",r.Plan.Name);
#endif				
				return PlanChange.FailChange;
			}
			
			r.AddChildren(children);			
			
#if RULE_debug
			Console.WriteLine("RB: PlanAlloc {0}",r.Plan.Name);
#endif
			
			if (children.Count > 0) {
				log.EventOccurred("PAlloc("+r.Plan.Name+" in State "+r.ActiveState.Name+")");
				return PlanChange.InternalChange;
			}
			
			return PlanChange.NoChange;
		}
		/// <summary>
		/// Combines to PlanChange flags to one, giving priority to Failures.
		/// </summary>
		/// <param name="cur">
		/// A <see cref="PlanChange"/>
		/// </param>
		/// <param name="update">
		/// A <see cref="PlanChange"/>
		/// </param>
		/// <returns>
		/// A <see cref="PlanChange"/>
		/// </returns>
		public PlanChange UpdateChange(PlanChange cur, PlanChange update) {
			if (update != PlanChange.NoChange) this.ChangeOccurred = true;
			if (cur == PlanChange.NoChange) return update;
			if (cur == PlanChange.FailChange) return cur;
			if (cur == PlanChange.InternalChange) {
				if (update != PlanChange.NoChange) return update;
			}
			if (update == PlanChange.FailChange) return update;
			return cur;
		}
		
		
	}
}

