//#define PSDEBUG
using System;
using System.Collections;
using System.Collections.Generic;

namespace Alica
{	
	/// <summary>
	/// Implements the task allocation algorithm
	/// </summary>
	public class PlanSelector : IPlanSelector
	{
		
		private ITeamObserver to;
		
		/// <summary>
		/// Basic ctor
		/// </summary>
		public PlanSelector()
		{
			
			this.to = AlicaEngine.Get().TO;
			PartialAssignment.Init();
			
		}
		/// <summary>
		/// Solves the task allocation problem for a given state.
		/// </summary>
		/// <param name="parent">
		/// The <see cref="RunningPlan"/> in which task allocation is due for the <see cref="RunningPlan.ActiveState"/>.
		/// </param>
		/// <param name="plans">
		/// The list of children of the state for which to allocate, a <see cref="LinkedList<AbstractPlan>"/>.
		/// </param>
		/// <param name="robots">
		/// The set of robots or agents, which are available <see cref="ICollection<System.Int32>"/>
		/// </param>
		/// <returns>
		/// A <see cref="LinkedList<RunningPlan>"/>, encoding the solution.
		/// </returns>
		public LinkedList<RunningPlan> GetPlansForState(RunningPlan parent, LinkedList<AbstractPlan> plans, ICollection<int> robots)
		{
			// Reset set index of the partial assignment multiton
			PartialAssignment.Reset();
			LinkedList<RunningPlan> ll =  this.GetPlansForStateInternal(parent, plans, robots);
			//Console.WriteLine("PS: Return to BE");
			return ll;
		}
		
		private LinkedList<RunningPlan> GetPlansForStateInternal(RunningPlan parent, LinkedList<AbstractPlan> plans, ICollection<int> robots)
		{
			// The Collection, which will be returned
			LinkedList<RunningPlan> rps = new LinkedList<RunningPlan>();
#if PSDEBUG 
			Console.WriteLine("<######PS: GetPlansForState: Parent:"+(parent!=null? parent.Plan.Name:"null") +" plan count: "+plans.Count + " robot count: "+robots.Count);
#endif		
			/*
			foreach(int robot in robots)
			{
				Console.WriteLine("PS: GetPlansForState Robotid: " + robot);
			}
			foreach(AbstractPlan pl in plans)
			{
				Console.WriteLine("PS: GetPlansForState AbstractPlan: " + pl.Name);
			}
			*/
			
			RunningPlan rp;
			LinkedList<Plan> planList;
			BehaviourConfiguration bc;
			Plan p;
			PlanType pt;
			foreach (AbstractPlan ap in plans)
			{	
				// BEHAVIOUR CONFIGURATION
				bc = ap as BehaviourConfiguration;
				if (bc != null) 
				{
					//Console.WriteLine("PS: GetPlansForState Behaviour: " + bc.Name);
					rp = new RunningPlan(bc);
					// A BehaviourConfiguration is a Plan too (in this context)
					rp.Plan = bc;
					rps.AddLast(rp);
					rp.Parent = parent;
#if PSDEBUG 
					Console.WriteLine("PS: Added Behaviour " + bc.Behaviour.Name);
#endif
				}
				else
				{
					// PLAN
					p = ap as Plan;				
					if (p != null)
					{
						//Console.WriteLine("PS: GetPlansForState Plan: " + p.Name);
						planList = new LinkedList<Plan>();
						planList.AddLast(p);
						rp = this.CreateRunningPlan(parent, planList, robots, null,null);
						if (rp == null)
						{
#if PSDEBUG 							
							Console.Error.WriteLine("PS: It was not possible to create a RunningPlan for the Plan " + p.Name + "!");
#endif							
							return null;
						}
						

						rps.AddLast(rp);
					}
					else 
					{
						// PLANTYPE
						pt = ap as PlanType;
						if (pt == null) 
							throw new Exception("PS: WTF? An AbstractPlan wasnt a BehaviourConfiguration, a Plan nor a PlanType: " + ap.Id);
						//Console.WriteLine("PS: GetPlansForState PlanType: " + pt.Name);							
						rp = this.CreateRunningPlan(parent, pt.Plans, robots, null,pt);
						if (rp == null)
						{
#if PSDEBUG
							Console.Error.WriteLine("PS: It was not possible to create a RunningPlan for the Plan " + pt.Name + "!");
#endif							
							return null;
						}
						
						// SET REALISED PLANTYPE
						//rp.RealisedPlanType = pt;
						
						rps.AddLast(rp);
						pt = null;
					}// else Plan
				}// else BehaviourConfiguration	
			}// foreach AbstractPlan
			
			return rps;
		}
		
		internal RunningPlan CreateRunningPlan(RunningPlan parent, LinkedList<Plan> planList, ICollection<int> robots, RunningPlan oldRp, PlanType relevantPlanType)
		{				
#if PSDEBUG
			Console.WriteLine("PS: CreateRunningPlan for " + planList.Count + " plans and " + robots.Count + " robots!");	
#endif 
			
			LinkedList<Plan> newPlanList = new LinkedList<Plan>();
			// REMOVE EVERY PLAN WITH TOO GREAT MIN CARDINALITY
			foreach(Plan p in planList)
			{
				
				// CHECK: number of robots < minimum cardinality of this plan
				if (p.MinCardinality > robots.Count+to.SuccessesInPlan(p))
				{
					
#if PSDEBUG
					string msg = "PS: RobotIds:";
					foreach(int robot in robots)
					{
						msg+= robot + ", ";
					}
					msg += "= " + robots.Count + " IDs are not enough for the plan " + p.Name + "!";
					//this.baseModule.Mon.Error(1000, msg);

					Console.WriteLine(msg);
#endif
				}
				else
				{
					// this plan was ok according to its cardinalities, so we can add it
//					Console.WriteLine("PS: Plan ok " + p.Name);
					newPlanList.AddLast(p);
				}
			}
			
			// WE HAVE NOT ENOUGH ROBOTS TO EXECUTE ANY PLAN
			if (newPlanList.Count == 0) return null;
			
			// TASKASSIGNMENT
			TaskAssignment ta;
			Assignment oldAss = null;
			
			RunningPlan rp;
			
			if(oldRp == null)
			{
				// preassign other robots, because we dont need a similar assignment
				rp = new RunningPlan(relevantPlanType);
				ta = new TaskAssignment(newPlanList, robots, true);
				
			}
			else
			{
				// dont preassign other robots, because we need a similar assignment (not the same)
				rp = new RunningPlan(oldRp.RealisedPlanType);
				ta = new TaskAssignment(newPlanList, robots, false);
				oldAss = oldRp.Assignment;				
			}
#if PSDEBUG
			Console.WriteLine(ta.ToString()); // DEBUG OUTPUT
#endif
			
			// some variables for the do while loop
			EntryPoint ep = null;
			RobotProperties ownRobProb = to.GetOwnRobotProperties();
			
			
			// PLANNINGPARENT
			rp.Parent = parent;
			
			LinkedList<RunningPlan> rpChildren = null;
			do
			{
				// ASSIGNMENT
				rp.Assignment = ta.GetNextBestAssignment(oldAss); // oldAss should be null, if we didnt get an oldRp

				if (rp.Assignment == null) {
#if PSDEBUG
					Console.WriteLine("PS: rp.Assignment is NULL");				
#endif
					return null; // All Assignments were tried
				} 
#if PSDEBUG
				Console.WriteLine("PS: Got Assignment from TA: " + rp.Assignment);			
#endif

				// PLAN (needed for Conditionchecks)
				rp.Plan = rp.Assignment.Plan;
				
				// CONDITIONCHECK
			
				if (!rp.EvalPreCondition()) {
					
					continue;
				}

				if (!rp.EvalRuntimeCondition()) {
					continue;
				}
				
				// OWN ENTRYPOINT
				
				ep = rp.Assignment.EntryPointOfRobot(ownRobProb.Id);
				
				
				if (ep == null)
				{
#if PSDEBUG					
					Console.WriteLine("PS: The robot " + ownRobProb.Name + "(Id: " + ownRobProb.Id 
					                  + ") is not assigned to enter the plan " + rp.Plan.Name + " and will IDLE!");
#endif
					rp.ActiveState = null;
					rp.OwnEntryPoint = null;
					return rp;// If we return here, this robot will idle (no ep at rp)
				}
				else
				{
					// assign found EntryPoint (this robot dont idle)
#if PSDEBUG
					Console.WriteLine("PS: Set my Entrypoint (I will NOT idle)");
#endif
					rp.OwnEntryPoint = ep;
				}
				
				// ACTIVE STATE set by RunningPlan
				
				
				if(oldRp == null)
				{
					// RECURSIVE PLANSELECTING FOR NEW STATE
					rpChildren = this.GetPlansForStateInternal(rp, rp.ActiveState.Plans, rp.Assignment.GetRobotsWorking(ep));
				}
				else
				{
#if PSDEBUG
					Console.WriteLine("PS: no recursion due to utilitycheck");
#endif
					// Dont calculate children, because we have an 
					// oldRp -> we just replace the oldRp 
					// (not its children -> this will happen in an extra call)
					break;
				}
			} while (rpChildren == null);
			
			// WHEN WE GOT HERE, THIS ROBOT WONT IDLE AND WE HAVE A 
			// VALID ASSIGNMENT, WHICH PASSED ALL RUNTIME CONDITIONS
			if (rpChildren != null)
			{
#if PSDEBUG
				Console.WriteLine("PS: Set child -> father reference");
#endif				
				
				// Create the son -> father association for each child
				
				rp.AddChildren(rpChildren);
			}
			
			return rp; // If we return here, this robot is normal assigned
		}
		
		/// <summary> Edits data from the old running plan to call the method CreateRunningPlan appropriatly. </summary>
		public RunningPlan GetBestSimilarAssignment(RunningPlan oldRp)
		{
#if PSDEBUG
			Console.WriteLine("PS: GetBestSimilarAssignment without robots - AbstractPlan.Name:" + oldRp.Plan.Name+" AbstractPlan.Id:"+ oldRp.Plan.Id);// DEBUG OUTPUT
#endif
			// Reset set index of the partial assignment multiton
			PartialAssignment.Reset();
			
			// CREATE NEW PLAN LIST
			LinkedList<Plan> newPlanList; 
			if(oldRp.RealisedPlanType == null)
			{
				newPlanList = new LinkedList<Plan>();
				newPlanList.AddLast(oldRp.Plan as Plan);
			}
			else
			{
				newPlanList = oldRp.RealisedPlanType.Plans;				
			}
			
			// GET ROBOTS TO ASSIGN
			
			List<int> robots = oldRp.Assignment.GetAllRobots();
			
			return this.CreateRunningPlan(oldRp.Parent, newPlanList, robots, oldRp, oldRp.RealisedPlanType);
		}

		/// <summary> Edits data from the old running plan to call the method CreateRunningPlan appropriatly. </summary>
		public RunningPlan GetBestSimilarAssignment(RunningPlan oldRp, ICollection<int> robots)
		{
#if PSDEBUG
			Console.WriteLine("PS: GetBestSimilarAssignment with robots - AbstractPlan.Name:" + oldRp.Plan.Name+" AbstractPlan.Id:"+ oldRp.Plan.Id);// DEBUG OUTPUT
#endif
			// Reset set index of the partial assignment object pool
			PartialAssignment.Reset();
			
			// CREATE NEW PLAN LIST
			LinkedList<Plan> newPlanList; 
			if(oldRp.RealisedPlanType == null)
			{
				newPlanList = new LinkedList<Plan>();
				newPlanList.AddLast(oldRp.Plan as Plan);
			}
			else
			{
				newPlanList = oldRp.RealisedPlanType.Plans;				
			}
			return this.CreateRunningPlan(oldRp.Parent, newPlanList, robots, oldRp,oldRp.RealisedPlanType);
		}		
	}
}