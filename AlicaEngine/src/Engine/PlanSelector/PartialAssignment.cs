//#define PADEBUG
using System;
using System.Collections.Generic;
using Castor;

namespace Alica
{
	public class PartialAssignment : IAssignment, IComparable<PartialAssignment>
	{
#region *** Multiton ***		
		protected static int maxCount = 10100;
		protected static int maxEpsCount =11;
		protected static int curIndex = 0;
		protected static PartialAssignment[] daPAs = new PartialAssignment[maxCount];		
		protected static EpByTaskComparer epByTaskComparer = new EpByTaskComparer();
		protected static bool allowIdling = SystemConfig.LocalInstance["Alica"].GetBool("Alica.AllowIdling");
		protected static EntryPoint idleEP;
		
		protected SuccessCollection epSuccessMapping;

		private const int INFINITY = int.MaxValue;
		
		protected struct DynCardinality 
		{		
			public int min;
			public int max;
			
			public DynCardinality(int min, int max)
			{
				this.min = min;
				this.max = max;
			}
		}
		
		public static void Init() {
			// IDLE-EntryPoint
			idleEP = new EntryPoint();
			idleEP.Name = "IDLE-EP";
			idleEP.Id = EntryPoint.IDLEID;
			idleEP.MinCardinality = 0;
			idleEP.MaxCardinality = int.MaxValue;
			// Add IDLE-Task
			idleEP.Task = new Task(true);
			idleEP.Task.Name = "IDLE-TASK";
			idleEP.Task.Id = Task.IDLEID;
			
			for (int i=0; i<maxCount; ++i) {
				daPAs[i] = new PartialAssignment();
			}
		}
		
		public void Clear() {
			this.min = 0.0;
			this.max = 1.0;
			this.compareVal = PRECISION;
			this.unAssignedRobots.Clear();	
			for (int i=0; i<this.epRobotsMapping.Count; ++i) {
				this.epRobotsMapping.Robots[i].Clear();			
			}
			this.hashCalculated = false;
		}
		
		private PartialAssignment() {
			this.epRobotsMapping = new AssignmentCollection(maxEpsCount);
			this.unAssignedRobots = new List<int>();
			this.dynCardinalities = new DynCardinality[maxEpsCount];
			this.compareVal = PRECISION;
			
			for (int i=0; i<maxEpsCount; ++i) {
				this.dynCardinalities[i] = new DynCardinality();
			}
		}
		
		public static void Reset() {
			curIndex = 0;
		}
		public static PartialAssignment GetNew(int[] robots, Plan plan, SuccessCollection sucCol) {
			if (curIndex>=maxCount) throw new Exception("max PA count reached!");
			PartialAssignment ret = daPAs[curIndex++];
			//PartialAssignment ret = new PartialAssignment();
			ret.Clear();
			ret.robots = robots; // Should already be sorted! (look at TaskAssignment, or PlanSelector)
			ret.plan = plan;
			ret.utilFunc = plan.UtilityFunction;
			ret.epSuccessMapping = sucCol;
			
			// Create EP-Array
			if(allowIdling) {
				ret.epRobotsMapping.Count = plan.EntryPoints.Count+1;
				// Insert IDLE-EntryPoint
				ret.epRobotsMapping.EntryPoints[ret.epRobotsMapping.Count-1] = idleEP;
			}
			else ret.epRobotsMapping.Count = plan.EntryPoints.Count;
			// Insert plan entrypoints
			plan.EntryPoints.Values.CopyTo(ret.epRobotsMapping.EntryPoints, 0);
			 
			
			
			// Sort the entrypoint array
			if (allowIdling) Array.Sort(ret.epRobotsMapping.EntryPoints, 0, ret.epRobotsMapping.Count-1, epByTaskComparer);
			else Array.Sort(ret.epRobotsMapping.EntryPoints, 0, ret.epRobotsMapping.Count, epByTaskComparer);		
			
			for(int i = 0; i < ret.epRobotsMapping.Count; ++i)
			{
				ret.dynCardinalities[i].min = ret.epRobotsMapping.EntryPoints[i].MinCardinality;
				ret.dynCardinalities[i].max = ret.epRobotsMapping.EntryPoints[i].MaxCardinality;	
				ICollection<int> suc = sucCol.GetRobots(ret.epRobotsMapping.EntryPoints[i]);
				if (suc != null) {
					ret.dynCardinalities[i].min -= suc.Count;
					ret.dynCardinalities[i].max -= suc.Count;
					if (ret.dynCardinalities[i].min < 0) ret.dynCardinalities[i].min = 0;
					if (ret.dynCardinalities[i].max < 0) ret.dynCardinalities[i].max = 0;
				}
			}
			
			// At the beginning all robots are unassigned
			ret.unAssignedRobots.AddRange(robots);

			return ret;
		}
		
		public static PartialAssignment GetNew(PartialAssignment oldPa)
		{
			if (curIndex>=maxCount) throw new Exception("max PA count reached!");
			PartialAssignment ret = daPAs[curIndex++];
			ret.Clear();
			ret.min = oldPa.min;
			ret.max = oldPa.max;
			ret.plan = oldPa.plan;
			ret.robots = oldPa.robots;
			ret.utilFunc = oldPa.utilFunc;
			ret.epSuccessMapping = oldPa.epSuccessMapping;
			for(int i = 0; i<oldPa.unAssignedRobots.Count; ++i)
			{
				ret.unAssignedRobots.Add(oldPa.unAssignedRobots[i]);
			}

			oldPa.dynCardinalities.CopyTo(ret.dynCardinalities, 0);
			
			List<int>[] oldRobotLists = (List<int>[])oldPa.EpRobotsMapping.Robots;
			
			for(int i = 0; i < oldPa.EpRobotsMapping.Count; ++i)
			{
				ret.epRobotsMapping.EntryPoints[i] = oldPa.epRobotsMapping.EntryPoints[i];
				for(int j=0; j < oldRobotLists[i].Count; ++j) {					
					ret.epRobotsMapping.Robots[i].Add(oldRobotLists[i][j]);
				}
			}
			
			ret.epRobotsMapping.Count = oldPa.EpRobotsMapping.Count;
			
			return ret;
		}		
#endregion *** Multiton ***		
		
		// UTILITY THINGS
		// UtilityFunction
		protected UtilityFunction utilFunc;
		
		// Minimum possible utility 
		protected double min;
		
		// Maximum possible utility 
		protected double max;
		
		// ASSIGNMENT THINGS
		// AssignmentCollection for the partial assignment
		protected AssignmentCollection epRobotsMapping;
			
		// RobotId Array for HashCode... (it must be sorted for HashCode)
		protected int[] robots;
						
		// Array of dyn. MinMax for each EP
		protected DynCardinality[] dynCardinalities;
		
		// Unassigned Robots (all robots are unassigned at the beginning)
		protected List<int> unAssignedRobots;
		
		// This partialAssignment is for this plan
		protected Plan plan;
				
		protected const int PRECISION = 1073741824;
		protected int compareVal=0;		
		
#region *** Methods ***
		public EntryPoint[] GetEntryPoints() {
			return this.epRobotsMapping.EntryPoints;
		}
		public int GetEntryPointCount() {
			return this.epRobotsMapping.Count;
		}
		public int TotalRobotCount() {
			int c = 0;
			for(int i=0; i<this.epRobotsMapping.Robots.Length;i++) {
				c+=this.epRobotsMapping.Robots[i].Count;
			}
			return this.NumUnAssignedRobots+c;
		}
		public ICollection<int> GetRobotsWorking(EntryPoint ep)
		{
			return this.epRobotsMapping.GetRobots(ep);
		}
		public ICollection<int> GetRobotsWorking(long epid)
		{
			return this.epRobotsMapping.GetRobotsById(epid);
		}
		public List<int> GetRobotsWorkingAndFinished(EntryPoint ep) {
			List<int> ret = new List<int>();
			ret.AddRange(this.epRobotsMapping.GetRobots(ep));
			ICollection<int> successes = this.epSuccessMapping.GetRobots(ep);
			if (successes!= null) { //are null for idle task	
				ret.AddRange(successes);
			}
			return ret;
		}
		public List<int> GetRobotsWorkingAndFinished(long epid) {
			List<int> ret = new List<int>();
			ret.AddRange(this.epRobotsMapping.GetRobotsById(epid));
			ret.AddRange(this.epSuccessMapping.GetRobotsById(epid));
			return ret;
		}
		public List<int> GetUniqueRobotsWorkingAndFinished(EntryPoint ep) {
			List<int> ret = new List<int>();
			
			ret.AddRange(this.epRobotsMapping.GetRobots(ep));
			ICollection<int> successes = this.epSuccessMapping.GetRobots(ep);
			if (successes!=null) {
				foreach(int r in successes) {
					if(!ret.Contains(r)) ret.Add(r);
				}
			}
			return ret;
		}
		/// <summary>
		/// If the robot has already assigned itself, this method updates the partial assignment accordingly. 
		/// </summary>
		/// <param name="spt">
		/// A <see cref="SimplePlanTree"/>
		/// </param>
		/// <param name="robot">
		/// A <see cref="System.Int32"/>
		/// </param>
		public bool AddIfAlreadyAssigned(SimplePlanTree spt, int robot)
		{			
			if(spt.EntryPoint.InPlan==this.Plan) {
				EntryPoint[] eps = this.epRobotsMapping.EntryPoints;
				EntryPoint curEp;
				int max = this.epRobotsMapping.Count;
				if (allowIdling) max--;
				for(int i = 0; i < max; ++i)
				{				
					curEp = eps[i];
					if (spt.EntryPoint.Id == curEp.Id)
					{
						if(!this.AssignRobot(robot, i)) {
							//throw new Exception("PA: Received state information 'clashes' with entrypoint cardinalities!");
							break;
						}
						//remove robot from "To-Add-List"
						if(!this.unAssignedRobots.Remove(robot))
						{
							throw new Exception("PA: Tried to assign robot " + robot + ", but it was not UNassigned!");
						}
						//return true, because we are ready, when we found the robot here
						return true;
					}
				}
				return false;
			}
			// If there are children and we didnt find the robot until now, then go on recursive
			else if (spt.Children.Count > 0)
			{
				foreach(SimplePlanTree sptChild in spt.Children)
				{
					if (this.AddIfAlreadyAssigned(sptChild, robot))
					{
						return true;
					}
				}
			}
			
			// Did not find the robot in any relevant entry point
			return false;
		}
		
		/// <summary> Assigns the robot into the datastructures according to the given entrypoint. </summary>
		/// <returns> True, when it was possible to assign the robot. False, otherwise. </returns>
//		public bool AssignRobot(int robot, EntryPoint ep)
//		{
//			EntryPoint[] ownEps = this.epRobotsMapping.Keys;
//			for(int i = 0; i <  ownEps.Length; ++i)
//			{
//				if (ownEps[i].Id == ep.Id)
//				{
//					return this.AssignRobot(robot, i);
//				}
//			}
//			return false;
//		}
		
		/// <summary> Assigns the robot into the datastructures according to the given index. </summary>
		/// <returns> True, when it was possible to assign the robot. False, otherwise. </returns>
		public bool AssignRobot(int robot, int index)
		{
			if (this.dynCardinalities[index].max > 0)
			{
				this.epRobotsMapping.Robots[index].Add(robot);
				if (this.dynCardinalities[index].min > 0)
				{
					this.dynCardinalities[index].min -= 1;
				}
				if (this.dynCardinalities[index].max != INFINITY)
				{
					this.dynCardinalities[index].max -= 1;
				}
				return true;
//#if PADEBUG
//			Console.WriteLine("PA: Assigned robot " + robot + " to entrypoint with index " + index + "!");
//#endif
			}
			return false;
		}
		
		public List<PartialAssignment> Expand()
		{
			List<PartialAssignment> newPas = new List<PartialAssignment>(this.epRobotsMapping.Count);
			if (this.unAssignedRobots.Count == 0)
			{
				// No robot left to expand
				return newPas;
			}
			
			// Robot which should be assigned next
			int robot = this.unAssignedRobots[0];
			this.unAssignedRobots.RemoveAt(0);
			PartialAssignment newPa;
			for(int i = 0; i < this.epRobotsMapping.Count; ++i)
			{
				if(this.dynCardinalities[i].max > 0)
				{
					newPa = PartialAssignment.GetNew(this);
					// Update the cardinalities and assign the robot
					newPa.AssignRobot(robot, i);
//					if(!newPa.AssignRobot(robot, i))
//					{
//						throw new Exception("PA: Could not assign the robot: " + robot);
//					}
					newPas.Add(newPa);
				}
			}
			return newPas;
		}
		
		/// <summary> Checks if this PartialAssignment is a complete Assignment. </summary>
		/// <returns> True, if it is, false otherwise. </returns>
		public bool IsGoal()
		{
			// There should be no unassigned robots anymore
			if (this.unAssignedRobots.Count > 0) {
#if PADEBUG
				Console.WriteLine("PA: No Goal - Still robots zu assign.");		
#endif
				return false;
			}
			// Every EntryPoint should be satisfied according to his minCar
			for(int i = 0; i < this.epRobotsMapping.Count; ++i)
			{
				if (this.dynCardinalities[i].min!=0)
				{
					return false;
#if PADEBUG
					Console.WriteLine("PA: No Goal - MinCar of EP:" + this.epRobotsMapping.Keys[i].Id + " is still > 0");		
#endif					
				}
			}
			return true;
		}
		
		/// <summary> Compares this PartialAssignment with another one. </summary>
		/// <returns> 
		/// 0 if it is the same object or they have the same utility, assignment and plan id
		/// 1 if the other PartialAssignment has a higher utility, or plan id
		/// -1 if this PartialAssignment has a higher utility, or plan id
		/// Difference between Hashcodes, if they have the same utility and plan id 
		/// </returns>		
		public int CompareTo(PartialAssignment pa) 
		{
			if (this == pa) return 0; // Same reference -> same object 
			
			// Stupid hack to avoid failures by inserting in SortedArray
//			int myval = (int)Math.Round(this.max*PRECISION);
//			int otval = (int)Math.Round(pa.max*PRECISION);
			
			if (pa.compareVal>this.compareVal) return 1; // other has higher utility
			else if(pa.compareVal<this.compareVal) return -1; // this has higher utility
			
			// Now we are sure that both partial assignments have the same utility
			else if(pa.plan.Id < this.plan.Id) return -1;
			else if(pa.plan.Id > this.plan.Id) return 1;
			
			// Now we are sure that both partial assignments have the same utility and the same plan id
		//	return this.GetHashCode()-pa.GetHashCode();
			
			if(this.unAssignedRobots.Count > pa.unAssignedRobots.Count) return 1;
			else if (this.unAssignedRobots.Count < pa.unAssignedRobots.Count) return -1;
			
			List<int>[] me = (List<int>[])this.epRobotsMapping.Robots;
			List<int>[] you= (List<int>[])pa.epRobotsMapping.Robots;
			
			for(int i=0; i<this.epRobotsMapping.Count; ++i) {				
				if(me[i].Count < you[i].Count) return 1;
				else if(me[i].Count > you[i].Count) return -1;
			}
			for(int i=0; i<this.epRobotsMapping.Count; ++i) {				
					for(int j=0; j<me[i].Count; ++j) {
						if (me[i][j] < you[i][j]) return 1;
						else if (me[i][j] > you[i][j]) return -1;
					}
			}
			return 0;
		}
		
		protected bool hashCalculated = false;
		protected int hash = 0;
		/// <summary> Calculates a HashCode, which depends on the assignments and the plan. </summary>
		/// <returns> A HashCode, which should be robot independent. </returns>
		public override int GetHashCode() 
		{
			if (this.hashCalculated) return this.hash;			
			int basei = this.epRobotsMapping.Count+1;
			ICollection<int> robots;
			for (int i = 0; i< this.epRobotsMapping.Count; ++i) {	
				robots = this.epRobotsMapping.Robots[i];				
				foreach(int robot in robots) {
					this.hash += (i+1)*Pow(basei, Array.BinarySearch(this.robots, robot));
				}
			}
			this.hashCalculated = true;
			return this.hash;
		}
		
		/// <summary>
		/// little helper to calculate the y-th power of x with integers 
		/// </summary>
		private static int Pow(int x,int y){
			int ret = 1;
			for (int i=0; i<y;i++){
				ret*=x;
			}
			return ret;
		}
		
		public override string ToString ()
		{		
			string retString = "Plan: " + this.plan.Name + "\nUtility: " + this.min + ".."
				+ this.max + "\nUnassignedRobots: ";		
			foreach(int robot in this.unAssignedRobots)
			{
				retString += robot + " ";
			}
			retString += "\n";
			EntryPoint[] ownEps = this.epRobotsMapping.EntryPoints;
			ICollection<int> robots;		
			for(int i = 0; i < this.epRobotsMapping.Count; ++i)
			{
				robots = this.epRobotsMapping.Robots[i];
				retString += "EPid: " + ownEps[i].Id + " Task: " 
				                + ownEps[i].Task.Name + " minCar: " 
								+ this.dynCardinalities[i].min + " maxCar: " 
								+ (this.dynCardinalities[i].max == INFINITY ? "*" : this.dynCardinalities[i].max.ToString()) 
						        + " Assigned Robots: ";
				foreach (int robot in robots)
				{
					retString += robot + " ";
				}
				retString += "\n";
			}
			retString += "HashCode: " + this.GetHashCode() + "\n";
			return retString;
		}
#endregion *** Methods ***
#region *** Properties ***
		public Plan Plan
		{
			get{ return this.plan; }
		}
		
		public UtilityFunction UtilityFunction
		{
			get{ return this.utilFunc; }
		}
		
		public AssignmentCollection EpRobotsMapping 
		{
			get{ return this.epRobotsMapping; }
		}
		public SuccessCollection EpSuccessMapping {
			get { return this.epSuccessMapping; }
		}
		public int NumUnAssignedRobots
		{
			get{ return this.unAssignedRobots.Count; }
		}
		
		public List<int> UnAssignedRobots
		{
			get{ return this.unAssignedRobots; }
		}
		
		public double Min
		{
			get{ return this.min; }
			set{ this.min = value; }
		}

		public double Max
		{
			get{ return this.max; }
			set{ 
				 this.max = value;
				 this.compareVal = (int)Math.Round(value*PRECISION);
			}
		}
#endregion *** Properties ***		
	}
	
	public class EpByTaskComparer: IComparer<EntryPoint> {
		public int Compare (EntryPoint x, EntryPoint y)
		{
			if(x.Task.Id < y.Task.Id)
			{
				return 1;
			}
			else if (x.Task.Id == y.Task.Id)
			{
				return 0;
			}
			else
			{
				return -1;
			}
		}
	}
}
