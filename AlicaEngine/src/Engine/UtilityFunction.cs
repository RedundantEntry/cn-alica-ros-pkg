//#define UFDEBUG

using System;
using System.Collections.Generic;
using System.Collections;
//using CarpeNoctem;
using AutoDiff;

namespace Alica
{	
	
	public struct TaskRoleStruct
	{
		public long taskId;
		public long roleId;
		public TaskRoleStruct(long tid, long rid) 
		{
			taskId = tid;
			roleId = rid;
		}
		
        public override bool Equals( object ob ){
            if( ob is TaskRoleStruct) {
                TaskRoleStruct trs = (TaskRoleStruct) ob;
                return taskId==trs.taskId && roleId==trs.roleId;
            }
            else {
                return false;
            }
        }

        public override int GetHashCode(){
            return taskId.GetHashCode() ^ roleId.GetHashCode();
        }
	}
	
	public class UtilityFunction
	{
		public const double DIFFERENCETHRESHOLD = 0.0001; // Max difference for the same result	
		
		//bi-directional
		protected Plan plan = null;
		
		protected string name = "DefaultUtilityFunction";
		
		// For default priority based utility summand (which is integrated in every UF)
		protected Dictionary<TaskRoleStruct, double> taskRolePriorityDic = null; 
		protected Dictionary<long, double> roleHighestPriorityDic = null;
		protected double priorityWeight;
		
		// For default similarity based utility summand (which is integrated in every UF)
		protected double similarityWeight;
		protected AlicaEngine bpe;
		protected IRoleAssignment ra;
		
		// List of normal utility summands
		protected List<USummand> utilSummands = null;				
		
		public UtilityFunction(string name, List<USummand> utilSummands, double priorityWeight, double similarityWeight, Plan plan)
		{
			this.name = name;
			this.utilSummands = utilSummands;
			this.priorityWeight = priorityWeight;
			this.similarityWeight = similarityWeight;
			this.plan = plan;
			
		}
		
#region *** internal computational stuff ***
		protected TaskRoleStruct lookupStruct = new TaskRoleStruct(0,0);
#endregion *** internal computational stuff ***
		
#region *** Methods ***		
		/// <summary> Evaluates the utility function according to the priorities of the assigned 
		/// roles and according to the similarity, if an oldRP is given and according to all 
		/// other utility summands of this utility function. </summary>
		/// <returns> The utility </returns>
		public virtual double Eval(RunningPlan newRP, RunningPlan oldRP) 
		{			
			// Invalid Assignments have an Utility of -1 changed from 0 according to specs
			if (!newRP.Assignment.IsValid())
				return -1.0;
			double sumOfWeights = 0.0;
			
			// Sum up priority summand	
			UtilityInterval sumOfUI = this.GetPriorityResult(newRP.Assignment);
			if(sumOfUI.Max < 0.0)
				return -1.0; // one robot has a negativ priority for his task -> -1.0 for the complete assignment
			sumOfUI.Max = this.priorityWeight * sumOfUI.Max;
			sumOfUI.Min = this.priorityWeight * sumOfUI.Min;
			sumOfWeights += this.priorityWeight;
			
			// Sum up all normal utility summands
			UtilityInterval curUI;
			for(int i=0; i<this.utilSummands.Count; ++i) 
			{
				curUI = utilSummands[i].Eval(newRP.Assignment);
				sumOfWeights += utilSummands[i].Weight;
				sumOfUI.Max += utilSummands[i].Weight * curUI.Max;
				sumOfUI.Min += utilSummands[i].Weight * curUI.Min;
			}
			
			if (oldRP != null && this.similarityWeight > 0)
			{				
				// Sum up similarity summand
				UtilityInterval simUI = this.GetSimilarity(newRP.Assignment, oldRP.Assignment);
				sumOfUI.Max += this.similarityWeight * simUI.Max;
				sumOfUI.Min += this.similarityWeight * simUI.Min;
				//sumOfWeights += this.similarityWeight;
			}
			
			// Normalize to 0..1
			if (sumOfWeights > 0.0)
			{
				sumOfUI.Max /= sumOfWeights;
				sumOfUI.Min /= sumOfWeights;
				
				// Min == Max because RP.Assignment must be an complete Assignment!
				if ((sumOfUI.Max - sumOfUI.Min) > DIFFERENCETHRESHOLD)
				{
					Console.Error.WriteLine("UF: The utility min and max value differs more than " 
					                        + DIFFERENCETHRESHOLD + " for an Assignment!");
				}
				return sumOfUI.Max; 
			}
			
			return 0.0;
		}
		
		/// <summary> Evaluates the utility function according to the priorities of the assigned 
		/// roles and according to the similarity, if an oldAss is given. 
		/// ATTENTION PLZ: Return value is only significant with respect to current Utility of oldAss! (SimilarityMeasure)</summary>
		/// <returns> The utility interval </returns>
		public virtual UtilityInterval Eval(IAssignment newAss, IAssignment oldAss)
		{
			double sumOfWeights = 0.0;
			
			// Sum up priority summand
			UtilityInterval sumOfUI = this.GetPriorityResult(newAss);
			if(sumOfUI.Max == -1.0)
				return sumOfUI; // one robot have a negativ priority for his task -> (-1.0, -1.0) for the complete assignment
			sumOfUI.Max = this.priorityWeight * sumOfUI.Max;
			sumOfUI.Min = this.priorityWeight * sumOfUI.Min;
			sumOfWeights += this.priorityWeight;
			
			// Sum up all normal utility summands
			UtilityInterval curUI;
			for(int i=0; i<this.utilSummands.Count; ++i)
			{
				curUI = utilSummands[i].Eval(newAss);
				sumOfWeights += utilSummands[i].Weight;
				sumOfUI.Max += utilSummands[i].Weight * curUI.Max;
				sumOfUI.Min += utilSummands[i].Weight * curUI.Min;
			}
			
			if (oldAss != null)
			{				
				// Sum up similarity summand
				UtilityInterval simUI = this.GetSimilarity(newAss, oldAss);
				sumOfUI.Max += this.similarityWeight * simUI.Max;
				sumOfUI.Min += this.similarityWeight * simUI.Min;
			}
			
			// Normalize to 0..1
			if (sumOfWeights > 0.0)
			{
				sumOfUI.Max /= sumOfWeights;
				sumOfUI.Min /= sumOfWeights;
				return sumOfUI; 
			}
			
			sumOfUI.Min = 0.0;
			sumOfUI.Max = 0.0;
			return sumOfUI;
		}
		
		/// <summary> Updates the utility function according to the priorities of the assigned 
		/// roles and according to the similarity, if an oldAss is given. </summary>
		/// <returns> void </returns>
		public virtual void UpdateAssignment(IAssignment newAss, IAssignment oldAss) 
		{
			UtilityInterval utilityInterval = this.Eval(newAss, oldAss);
			newAss.Min = utilityInterval.Min;
			newAss.Max = utilityInterval.Max;
		}
		
		/// <summary> Calculates the priority result for the specified Assignment</summary>
		/// <returns> the priority result </returns>
		protected UtilityInterval priResult = new UtilityInterval(0.0,0.0);
		protected UtilityInterval GetPriorityResult(IAssignment ass)
		{
			this.priResult.Max=0.0;
			this.priResult.Min=0.0;
			
			// SUM UP HEURISTIC PART OF PRIORITY UTILITY
			if(ass.UnAssignedRobots != null) // == null, when it is a normal assignment
			{
				for (int i=0; i < ass.UnAssignedRobots.Count; i++)
				{
					this.priResult.Max += this.roleHighestPriorityDic[this.ra.GetRole(ass.UnAssignedRobots[i]).Id];
				}
			}
			
			// SUM UP DEFINED PART OF PRIORITY UTILITY
			
			// for better comparability of different utility functions			
			int denum = Math.Min(this.plan.MaxCardinality, bpe.TO.TeamSize());
			
			long taskId;
			long roleId;
			EntryPoint[] eps = ass.GetEntryPoints();
			
			double curPrio;
			
			for (int i = 0; i < ass.GetEntryPointCount(); ++i)
			{
				
				taskId = eps[i].Task.Id;
				List<int> robotList = ass.GetUniqueRobotsWorkingAndFinished(eps[i]);
				for(int j = 0; j < robotList.Count;++j)
				{
// Debug Output because of the bug in List<int>.AddRange(ICollection<int>); which adds 0 instead of 2 ?!
//					if(robotsLists[i][j] == 0) 
//						Console.WriteLine("GetPrio: " + ass.ToString());
					roleId = ra.GetRole(robotList[j]).Id;

					this.lookupStruct.taskId = taskId;
					this.lookupStruct.roleId = roleId;
					curPrio = this.taskRolePriorityDic[this.lookupStruct];
					if (curPrio < 0.0)// because one Robot has a negative priority for his task
					{
						priResult.Min = -1.0;
						priResult.Max = -1.0;
						return priResult;
					}
					priResult.Min += curPrio;
#if UFDEBUG							
					Console.WriteLine("UF: taskId:" + taskId + " roleId:" + roleId + " prio:"+this.taskRolePriorityDic[this.lookupStruct]);
#endif						
				}
			}

#if UFDEBUG
			Console.WriteLine("##");
			Console.WriteLine("UF: prioUI.Min = " + priResult.Min );
			Console.WriteLine("UF: prioUI.Max = " + priResult.Max );
			Console.WriteLine("UF: denum = " + denum );
#endif					
			priResult.Max += priResult.Min;
			if(denum != 0)
			{
				priResult.Min /= denum;
				priResult.Max /= denum;
			}
#if UFDEBUG	
			Console.WriteLine("UF: prioUI.Min = " + priResult.Min );
			Console.WriteLine("UF: prioUI.Max = " + priResult.Max );
			Console.WriteLine("##");
#endif								
			return priResult;
		}
		
		/// <summary> Evaluates the similarity of the new Assignment to the old Assignment </summary>
		/// <returns> The result of the evaluation </returns>
		protected UtilityInterval simUI = new UtilityInterval(0.0,0.0);
		protected UtilityInterval GetSimilarity(IAssignment newAss, IAssignment oldAss)
		{
//			UtilityInterval retUI = new UtilityInterval(0.0,0.0);
			simUI.Max=0.0;
			simUI.Min=0.0;
			
			// Calculate the similarity to the old Assignment
			int numOldAssignedRobots = 0;
			
			//EntryPoint[] newAssEps = newAss.GetEntryPoints();
			EntryPoint[] oldAssEps = oldAss.GetEntryPoints();
			
			for (int i = 0; i < oldAss.GetEntryPointCount(); ++i)
			{
				// for normalisation
				List<int> oldRobots = oldAss.GetRobotsWorkingAndFinished(oldAssEps[i]);
				numOldAssignedRobots += oldRobots.Count;
				
				List<int> newRobots = oldAss.GetRobotsWorkingAndFinished(oldAssEps[i]);
				
				if(newRobots!=null) {
					foreach(int oldRobot in oldRobots) {
						if (newRobots.Contains(oldRobot)) {
							simUI.Min +=1;
						}
						else if(oldAssEps[i].MaxCardinality > newRobots.Count && newAss.UnAssignedRobots.Contains(oldRobot)) {
							simUI.Max +=1;
						}
					}
				} 
				
				
			}
			
			simUI.Max += simUI.Min;
			// Normalise if possible
			if (numOldAssignedRobots > 0)
			{
				simUI.Min /= numOldAssignedRobots;
				simUI.Max /= numOldAssignedRobots;

			} 
			simUI.Min -=1;
			simUI.Max -=1;
			return simUI;
		}
		
		public void CacheEvalData()
		{
			if(this.utilSummands != null) // == null for default utility function
			{
				for(int i = 0; i < this.utilSummands.Count; ++i)
				{
					this.utilSummands[i].CacheEvalData();
				}
			}
		}
		
		/// <summary> Initialises the '(Task x Role) -> Priority'-Dictionary and the 
		/// 'Role -> Highest Priority'-Dictionary for each role of the current roleset.</summary>
		/// <returns> void </returns>
		public void Init() {
			// CREATE MATRIX && HIGHEST PRIORITY ARRAY
			// init dicts
			this.roleHighestPriorityDic = new Dictionary<long,double>();
			this.taskRolePriorityDic = new Dictionary<TaskRoleStruct,double>();
//Console.WriteLine("Inititing Utility of {0}",this.plan.Name);			
			RoleSet roleset = AlicaEngine.Get().CurrentRoleSet;
			long taskId;
			long roleId;
			double curPrio;
			foreach(RoleTaskMapping rtm in roleset.RoleTaskMappings)
			{
				roleId = rtm.Role.Id;
				this.roleHighestPriorityDic.Add(roleId, 0.0);
				foreach(EntryPoint ep in this.plan.EntryPoints.Values)
				{
					taskId = ep.Task.Id;
					if (!rtm.TaskPriorities.TryGetValue(taskId, out curPrio))
					{
						AlicaEngine.Get().Abort("UF: There is no priority for the task " 
						                    + taskId + " in the roleTaskMapping of the role " 
						                    + rtm.Role.Name + " with id " + roleId + "!\n We are in the UF for the plan " + this.plan.Name + "!");
					}
//#if UFDEBUG
					//Console.WriteLine("UF: TaskId " + taskId + " RoleId " + roleId + "Priority: " + curPrio);
//#endif
					TaskRoleStruct trs = new TaskRoleStruct(taskId, roleId);
					if (!this.taskRolePriorityDic.ContainsKey(trs)) {
						this.taskRolePriorityDic.Add(new TaskRoleStruct(taskId, roleId), curPrio);
					}
					if (this.roleHighestPriorityDic[roleId] < curPrio) 
					{
						this.roleHighestPriorityDic[roleId] = curPrio;
					}
				}
				// Add Priority for Idle-EntryPoint
				this.taskRolePriorityDic.Add(new TaskRoleStruct(Task.IDLEID, roleId), 0.0); 				
			}
			
			// INIT UTILITYSUMMANDS
			if (this.utilSummands != null) // it is null for default utility function
			{
				foreach(USummand utilSum in this.utilSummands) {
					utilSum.Init();
				}
			}
			this.bpe = AlicaEngine.Get();
			this.ra  = this.bpe.RA;
		}
		public virtual Tuple<double[], double> Differentiate(IAssignment newAss) {
                       return null;
		}
		public List<USummand> getUtilSummands() {return utilSummands;}
	
		/// <summary> Calls Init() for every utiltiyfunction. 
		/// Is called and the end of AlicaEngine.Init(..), because it 
		/// needs the currentroleset (circular dependency otherwise). </summary>
		public static void InitDatastructures() 
		{
			
			Dictionary<long,Plan> plans = AlicaEngine.Get().PR.Plans;
			
			foreach (Plan p in plans.Values) {
				p.UtilityFunction.Init();				
			}
		}
		
		public override string ToString()
		{
			string retString = this.name + "\n";
			retString += "prioW: " + this.priorityWeight + " simW: " + this.similarityWeight + "\n"; 
			foreach(USummand utilSummand in this.utilSummands)
			{
				retString += utilSummand.ToString() + "\n";
			}
			return retString;
		}
#endregion *** Methods ***		
		
#region *** Properties ***		
		public Dictionary<TaskRoleStruct, double> PriorityMatrix {
			get{ return this.taskRolePriorityDic; }
//			set{ this.taskRobotPriorityDic = value; }
		}
		
		public Plan Plan
		{
			get { return this.plan; }
			//set { this.plan = value; }
		}
#endregion *** Properties ***	
	}
}
