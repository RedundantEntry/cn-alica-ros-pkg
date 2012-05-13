//#define PSDEBUG

using System;
using System.Collections;
using System.Collections.Generic;
//using CMM = CarpeNoctem.Messages.Modules;

namespace Alica
{
	/// <summary>
	/// Represents an instance of an assignment problem for one plan or a plantype. 
	/// All parameters, which are static for this problem, are stored here. 
	/// </summary>
	public class TaskAssignment : ITaskAssignment
	{		
		// Needed to send messages to LebtClient
		//protected CMM.Base baseModule = CMM.Base.GetInstance();

		// Plan to build an assignment for
		protected LinkedList<Plan> planList = null;
		protected int[] robots = null;
		protected EntryPoint[] entryPointArray = null;
		
		// Fringe of the search tree
		protected C5.SortedArray<PartialAssignment> fringe = null;
		
		
		
		/// <summary> Constructor of a new TaskAssignment </summary>
		/// <param name="plan">
		///  <see cref="Plan"/> to build an assignment for
		/// </param>
		/// <param name="robotIds">
		/// <see cref="HashSet`1"/> with robots to build an assignment for
		/// </param>
		/// <param name="preassignOtherRobots">
		/// A <see cref="System.Boolean"/>
		/// </param>
		public TaskAssignment(LinkedList<Plan> planList, ICollection<int> paraRobots, bool preassignOtherRobots)
		{			                  
			// PLANLIST
			this.planList = planList;
			ITeamObserver to = AlicaEngine.Get().TO;			
			// ROBOTS
			this.robots = new int[paraRobots.Count];
			int k = 0;
			foreach(int i in paraRobots)
			{
				this.robots[k++] = i;
			}
			Array.Sort(this.robots); // sort robot ids ascending
			
			// CREATE THE FRINGE
			this.fringe = new C5.SortedArray<PartialAssignment>();
			
			
			
			Dictionary<int, SimplePlanTree> simplePlanTreeDic = to.GetTeamPlanTrees();
			PartialAssignment curPa;
			foreach(Plan curPlan in this.planList)
			{
				// CACHE EVALUATION DATA IN EACH USUMMAND
				curPlan.UtilityFunction.CacheEvalData();
				
				// CREATE INITIAL PARTIAL ASSIGNMENTS
				curPa = PartialAssignment.GetNew(this.robots, curPlan,to.GetSuccessCollection(curPlan));
				
				// ASSIGN PREASSIGNED OTHER ROBOTS
				if (preassignOtherRobots)// TODO: Maybe this will take some time!? ... we will see
				{					
					if (this.AddAlreadyAssignedRobots(curPa, simplePlanTreeDic))
					{		
#if PSDEBUG					
						Console.WriteLine("TA: The first partial assignment must be updated!"); // DEBUG OUTPUT
#endif					
						// revaluate this pa
						curPlan.UtilityFunction.UpdateAssignment(curPa, null);
					}
				}

				this.fringe.Add(curPa);				
			}
		}
		
		/// <summary> If any robot has already assigned itself, this method updates the partial assignment accordingly.</summary>
		/// <returns> True if any robot has already assigned itself, false otherwise. </returns>
		protected bool AddAlreadyAssignedRobots(PartialAssignment pa, Dictionary<int, SimplePlanTree> simplePlanTreeDic)
		{
			int ownRobotId = AlicaEngine.Get().TO.GetOwnId();
			bool haveToRevaluate = false;
			SimplePlanTree spt = null;
			foreach(int robot in this.robots)
			{
				if(ownRobotId == robot) continue;
				if (simplePlanTreeDic.TryGetValue(robot, out spt))
				{
					if (pa.AddIfAlreadyAssigned(spt, robot))
					{
						haveToRevaluate = true;
					}
				}
			}
			return haveToRevaluate;
		}
		
		/// <summary> Gets the Assignment with next best utility </summary>
		/// <returns> An Assignment for the plan </returns>
		public Assignment GetNextBestAssignment(IAssignment oldAss)
		{
#if PSDEBUG			
			Console.WriteLine("TA: Calculating next best PartialAssignment..."); // DEBUG OUTPUT
#endif			
			PartialAssignment calculatedPa = this.CalcNextBestPartialAssignment(oldAss);
			
			if (calculatedPa == null)
			{
#if PSDEBUG				
				Console.WriteLine("TA: All Assignments tried! (return null)"); // DEBUG OUTPUT
#endif				
				return null;
			}
			
#if PSDEBUG			
			Console.WriteLine("TA: ... calculated this PartialAssignment:\n" + calculatedPa); // DEBUG OUTPUT		
			// ASSIGNMENT		
#endif

			Assignment newAss = new Assignment(calculatedPa);
#if PSDEBUG			
			Console.WriteLine("TA: Return this Assignment to PS:" + newAss);
#endif			
			return newAss;
		}
		
		private PartialAssignment CalcNextBestPartialAssignment(IAssignment oldAss)
		{
			PartialAssignment curPa = null;
			PartialAssignment goal = null;
			while(this.fringe.Count > 0 && goal == null)
			{
				curPa = this.fringe.RemoveAt(0);
#if PSDEBUG				
				Console.WriteLine("<---\nTA: NEXT PA from fringe:\n" + curPa.ToString()+"--->"); // DEBUG OUTPUT
#endif				
				// Check if it is a goal
				if (curPa.IsGoal())
				{
					// Save the goal in result
					goal = curPa;					
				}
				
#if PSDEBUG
				Console.WriteLine("<---\nTA: BEFORE fringe exp:\n" + this.fringe.ToString() + "\n--->"); // DEBUG OUTPUT
#endif				
				// Expand for the next search (maybe neccessary later)
				List<PartialAssignment> newPas = curPa.Expand();
				// Every just expanded partial assignment must get an updated utility  
				for(int i=0; i < newPas.Count; ++i)
				//foreach (PartialAssignment pa in newPas)
				{
					// Update the utility values
					newPas[i].UtilityFunction.UpdateAssignment(newPas[i], oldAss);
					// Add to search fringe
					this.fringe.Add(newPas[i]);
				}
				
#if PSDEBUG	
				Console.WriteLine("<---\nTA: AFTER fringe exp:\n" + this.fringe.ToString() + "\n--->"); // DEBUG OUTPUT
				for (int i=0; i< this.fringe.Count; ++i) {
					Console.WriteLine(fringe[i]);
				}
#endif
			}
			return goal;
		}
		
		public override string ToString()
		{
			string retString ="\n--------------------TA:--------------------\n"
		                 + "NumRobots: " + this.robots.Length + "\nRobotIDs: "; 
			for(int i = 0; i < this.robots.Length; ++i)// RobotIds
			{
				retString += this.robots[i] +  " ";
			}	
			retString += "\nInitial Fringe (Count " + this.fringe.Count + "): \n{";
			for(int i = 0; i < this.fringe.Count; ++i)// Initial PartialAssignments
			{
				retString += this.fringe[i].ToString();
			}
			retString += "}\n";
			retString += "-------------------------------------------\n";
			return retString;
		}
	}
}
