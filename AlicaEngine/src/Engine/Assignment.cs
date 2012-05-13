using System;
using System.Collections.Generic;
using RosCS.AlicaEngine;
using Castor;

namespace Alica
{
	/// <summary>
	/// Contains all allocation information for a single plan. This includes the robot-task mapping, robot-state mapping and success information.
	/// </summary>
	public class Assignment : IAssignment
	{
		//protected long timestamp = 0;
		protected double max = 0.0;
		protected double min = 0.0;
		protected AssignmentCollection epRobotsMapping;
		protected SuccessCollection epSucMapping;
		protected StateCollection robotStateMapping;
		protected static bool allowIdling = SystemConfig.LocalInstance["Alica"].GetBool("Alica.AllowIdling");
		protected Plan plan;
		
		internal Assignment(PartialAssignment pa) {
			this.max = pa.Max;
			this.min = max;
			this.Plan = pa.Plan;
			AssignmentCollection assCol = pa.EpRobotsMapping;
			HashSet<int>[] robots;
			if (allowIdling) {
				robots = new HashSet<int>[assCol.Count-1];// -1 for idling
			} else {
				robots = new HashSet<int>[assCol.Count];
			}
			for(int i = 0; i < robots.Length; ++i) {
				robots[i] = new HashSet<int>(assCol.Robots[i]);
			}
			// create the EntryPoint Array 
			EntryPoint[] newEps = new EntryPoint[robots.Length];
			Array.Copy(assCol.EntryPoints, newEps, newEps.Length);
			this.epRobotsMapping = new AssignmentCollection(newEps,robots);			
			this.robotStateMapping = new StateCollection(this.epRobotsMapping);
			this.epSucMapping = pa.EpSuccessMapping;
			
		}
		internal Assignment(Plan p, AllocationAuthorityInfo aai) {
			this.Plan = p;
			this.max = 1;
			this.min = 1;
			EntryPoint[] eps = new EntryPoint[p.EntryPoints.Count];
			HashSet<int>[] robots = new HashSet<int>[p.EntryPoints.Count];
			int k=0;
			
			foreach(EntryPoint ep in p.EntryPoints.Values) {				
				eps[k] = ep;
				robots[k] = new HashSet<int>();
				for (int i=0; i < aai.Entrypoints.Count; i++) {
						if(ep.Id==aai.Entrypoints[i].EntryPoint) {
							foreach(int rob in aai.Entrypoints[i].Robots) {
								robots[k].Add(rob);
							}
						}
				}
				k++;
			}
			this.epRobotsMapping = new AssignmentCollection(eps,robots);
			this.epSucMapping = new SuccessCollection(p);			
			this.robotStateMapping = new StateCollection(this.epRobotsMapping);
		}
		internal Assignment(Plan masterPlan) {
			this.Plan = masterPlan;
			this.max = 0;
			this.min = 0;
			
			this.epRobotsMapping = new AssignmentCollection(this.Plan.EntryPoints.Count);			
			List<EntryPoint> l = new List<EntryPoint>();
			foreach(EntryPoint ep in this.Plan.EntryPoints.Values) {
				l.Add(ep);
			}
			l.Sort();
			l.CopyTo(this.epRobotsMapping.EntryPoints,0);	

			this.robotStateMapping = new StateCollection(this.epRobotsMapping);
			this.epSucMapping = new SuccessCollection(masterPlan);
		}
				
		
#region *** Properties ***		
		/// <summary>
		/// The Plan this Assignment refers to
		/// </summary>
		public Plan Plan
		{
			get{ return this.plan; }
			internal set{ this.plan = value; }
		}
		/// <summary>
		/// The maximal utility of this assignment, equals <see cref="Min"/> since Assignemnts are always complete.
		/// </summary>
		public double Max
		{
			set { this.max = value; }
			get { return this.max; }
		}
		/// <summary>
		/// The minimal utility of this assignment, equals <see cref="Max"/> since Assignemnts are always complete.
		/// </summary>
		public double Min
		{
			set{ this.min = value; }
			get{ return this.min; }
		}
		/// <summary>
		/// returns 0, as assignemts are always complete. (also see <see cref="IAssignment"/>)
		/// </summary>
		public int NumUnAssignedRobots
		{
			// In a complete Assignment like this, every robot is assigned...
			get{ return 0; }
		}
		/// <summary>
		/// returns null, as no unassigned robot is left.
		/// </summary>
		public List<int> UnAssignedRobots
		{
			// In a complete Assignment like this, every robot is assigned...
			get{ return null; }
		}
		
		/// <summary>
		/// The robot-to-state mapping of this assignment.
		/// </summary>
		public StateCollection RobotStateMapping 
		{
			get { return this.robotStateMapping; }
		}
		/// <summary>
		/// Information about succeeded tasks.
		/// </summary>
		public SuccessCollection EpSuccessMapping
		{
			get { return this.epSucMapping; }
		}
		/*public long Timestamp
		{
			get { return this.timestamp; }
		}*/
#endregion *** Properties ***		
		
#region *** Methods ***	
		/// <summary>
		/// NUmber of Entrypoints in this assignment's plan.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Int32"/>
		/// </returns>
		public int GetEntryPointCount() {
			return this.epRobotsMapping.Count;
		}
		/// <summary>
		/// The robots that are currently working on a specific task, referred to by an EntryPoint.
		/// </summary>
		/// <param name="ep">
		/// A <see cref="EntryPoint"/>
		/// </param>
		/// <returns>
		/// A <see cref="ICollection<System.Int32>"/>
		/// </returns>
		public ICollection<int> GetRobotsWorking(EntryPoint ep)
		{
			return this.epRobotsMapping.GetRobots(ep);
		}
		/// <summary>
		/// The robots that are currently working on a specific task, referred to by an EntryPoint Id.
		/// </summary>
		/// <param name="epid">
		/// A <see cref="System.Int64"/>
		/// </param>
		/// <returns>
		/// A <see cref="ICollection<System.Int32>"/>
		/// </returns>
		public ICollection<int> GetRobotsWorking(long epid) {
			return this.epRobotsMapping.GetRobotsById(epid);
		}
		/// <summary>
		/// The robots that are currently working on or already succeeded in a specific task, referred to by an EntryPoint.
		/// </summary>
		/// <param name="ep">
		/// A <see cref="EntryPoint"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<System.Int32>"/>
		/// </returns>
		public List<int> GetRobotsWorkingAndFinished(EntryPoint ep) {
			List<int> ret = new List<int>();
			ret.AddRange(this.epRobotsMapping.GetRobots(ep));
			ret.AddRange(this.epSucMapping.GetRobots(ep));
			return ret;
		}
		/// <summary>
		/// The robots that are currently working on or already succeeded in a specific task, referred to by an EntryPoint Id.
		/// </summary>
		/// <param name="epid">
		/// A <see cref="System.Int64"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<System.Int32>"/>
		/// </returns>
		public List<int> GetRobotsWorkingAndFinished(long epid) {
			List<int> ret = new List<int>();
			ret.AddRange(this.epRobotsMapping.GetRobotsById(epid));
			ret.AddRange(this.epSucMapping.GetRobotsById(epid));
			return ret;
		}
		/// <summary>
		/// The robots that are currently working on or already succeeded in a specific task, referred to by an EntryPoint.
		/// Each robot only occurs once.
		/// </summary>
		/// <param name="ep">
		/// A <see cref="EntryPoint"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<System.Int32>"/>
		/// </returns>
		public List<int> GetUniqueRobotsWorkingAndFinished(EntryPoint ep) {
			List<int> ret = new List<int>();
			
			ret.AddRange(this.epRobotsMapping.GetRobots(ep));
			foreach(int r in this.epSucMapping.GetRobots(ep)) {
				if(!ret.Contains(r)) ret.Add(r);
			}
			
			return ret;
		}
		/// <summary>
		/// The array of EntryPoints this assignment considers relevant.
		/// </summary>
		/// <returns>
		/// A <see cref="EntryPoint[]"/>
		/// </returns>
		public EntryPoint[] GetEntryPoints() {
			return this.epRobotsMapping.EntryPoints;
		}
		internal bool RemoveRobot(int robot) {
			this.RobotStateMapping.RemoveRobot(robot);
			for(int i=0; i<this.epRobotsMapping.Count; i++) {
				if (this.epRobotsMapping.Robots[i].Contains(robot)) {
					this.epRobotsMapping.Robots[i].Remove(robot);
					return true;
				}
			}
			return false;
		}
		/// <summary>
		/// Test whether at least one robot is working on a task or succeeded with a task.
		/// </summary>
		/// <param name="ep">
		/// A <see cref="EntryPoint"/> identifiying the task in question.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool IsEntryPointNonEmpty(EntryPoint ep) {
			ICollection<int> r = this.epRobotsMapping.GetRobots(ep);
			if (r!=null && r.Count > 0) return true;
			r = this.epSucMapping.GetRobots(ep);
			return (r!=null && r.Count > 0);
		}
		internal bool UpdateRobot(int robot, EntryPoint ep, State s) {
			this.RobotStateMapping.SetState(robot,s);
			bool ret = false;
			for(int i=0; i<this.epRobotsMapping.Count; i++) {
				if (this.epRobotsMapping.EntryPoints[i] == ep) {
					if (this.epRobotsMapping.Robots[i].Contains(robot)) {
						return false;
					} else {
						this.epRobotsMapping.Robots[i].Add(robot);
						ret = true;
					}
				} else {				
					if (this.epRobotsMapping.Robots[i].Contains(robot)) {
						this.epRobotsMapping.Robots[i].Remove(robot);						
						ret = true;
					}
				}
			}
			return ret;
		}
		internal bool UpdateRobot(int robot, EntryPoint ep) {
			bool ret = false;
			for(int i=0; i<this.epRobotsMapping.Count; i++) {
				if (this.epRobotsMapping.EntryPoints[i] == ep) {
					if (this.epRobotsMapping.Robots[i].Contains(robot)) {
						return false;
					} else {
						this.epRobotsMapping.Robots[i].Add(robot);
						ret = true;
					}
				} else {				
					if (this.epRobotsMapping.Robots[i].Contains(robot)) {
						this.epRobotsMapping.Robots[i].Remove(robot);						
						ret = true;
					}
				}
			}
			if (ret) { //set moved robot to initial state
				this.RobotStateMapping.SetState(robot,ep.State);
			}
			return ret;
		}
		internal bool RemoveRobot(int robot,EntryPoint ep) {
			if (ep == null) return false;
			this.RobotStateMapping.RemoveRobot(robot);
			return this.epRobotsMapping.GetRobots(ep).Remove(robot);
		}
		internal void AddRobot(int robot, EntryPoint ep) {
			if (ep == null) return;
			this.epRobotsMapping.GetRobots(ep).Add(robot);
			return;
		}
		internal void AddRobot(int robot, EntryPoint ep, State state) {
			if (ep == null) return;
			this.RobotStateMapping.SetState(robot,state);
			this.epRobotsMapping.GetRobots(ep).Add(robot);
			return;
		}
		internal void MoveRobots(State from, State to) {
			HashSet<int> movingRobots = this.RobotStateMapping.GetRobotsInState(from);
			this.RobotStateMapping.SetStates(movingRobots,to);
			
		}
		/// <summary>
		/// Returns the EntryPoint a robot is currently working on. Returns null, if the robot is currently not working on the respective plan.
		/// </summary>
		/// <param name="robot">
		/// A <see cref="System.Int32"/>
		/// </param>
		/// <returns>
		/// A <see cref="EntryPoint"/>
		/// </returns>
		public EntryPoint EntryPointOfRobot(int robot) {
			for(int i=0; i<this.epRobotsMapping.Count;i++) {
				if (this.epRobotsMapping.Robots[i].Contains(robot)) {
					return this.epRobotsMapping.EntryPoints[i];
				}
			}
			return null;
		}
		/// <summary>
		/// The list of all robots currently allocated by this assignment to work on any task within the plan.
		/// </summary>
		/// <returns>
		/// A <see cref="List<System.Int32>"/>
		/// </returns>
		public List<int> GetAllRobots() {
			List<int> ret = new List<int>();
			for(int i=0; i<this.epRobotsMapping.Count; i++) {
				ret.AddRange(this.epRobotsMapping.Robots[i]);
			}
			return ret;
		}
		internal void Clear() {
			this.RobotStateMapping.Clear();
			this.epRobotsMapping.Clear();
			this.epSucMapping.Clear();
		}
		internal void SetAllToInitialState(IList<int> robots, EntryPoint ep) {
			ICollection<int> rlist = this.epRobotsMapping.GetRobots(ep);
			foreach(int r in robots) {
				rlist.Add(r);
			}
			this.RobotStateMapping.SetStates(robots,ep.State);
		}
		/// <summary>
		/// Tests whether this assignment is valid with respect to the plan's cardinalities.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool IsValid()
		{
			ICollection<int>[] robots = this.epRobotsMapping.Robots;
			EntryPoint[] eps = this.epRobotsMapping.EntryPoints;
			List<int>[] success = this.epSucMapping.Robots;
			for(int i = 0; i < robots.Length; ++i)
			{
				int c = robots[i].Count + success[i].Count;
				if(c > eps[i].MaxCardinality || c < eps[i].MinCardinality)
				{
					return false;
				}
			}
			return true;
		}
		/// <summary>
		/// Tests wether all required tasks have been succesfully completed and thus the plan can be considered as successful.
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool IsSuccessfull() {			
			for(int i=0; i<this.epSucMapping.Count; i++) {
				if(this.epSucMapping.EntryPoints[i].SuccessRequired) {				
					if (!(this.epSucMapping.Robots[i].Count > 0 && this.epSucMapping.Robots[i].Count >= this.epSucMapping.EntryPoints[i].MinCardinality)) {
						return false;
					}
				}
				
			}
			return true;
		}
		public int TotalRobotCount() {
			int c = 0;
			for(int i=0; i<this.epRobotsMapping.Robots.Length;i++) {
				c+=this.epRobotsMapping.Robots[i].Count;
			}
			return this.NumUnAssignedRobots+c;
		}
		public string ToHackString()
		{
			string result = "ASS "+this.plan.Id+ " " + this.plan.Name +":\t";
			
			EntryPoint[] eps = this.epRobotsMapping.EntryPoints;
			ICollection<int>[] robots = this.epRobotsMapping.Robots;
			List<int>[] suc = this.epSucMapping.Robots;
			for(int i=0; i<eps.Length; i++)
			{
				result += eps[i].Task.Name + " ";
				foreach (int robotID in robots[i])
				{
					result += robotID + " ";
				}
				
				if (suc[i].Count > 0) {
					result += "\t Success: ";
					foreach (int robotID in suc[i])
					{
						result += robotID + " ";
					}
				}
				
			}
			
			result += "\n";
			
			return result;
		}
				
		public bool IsEqual(Assignment otherAssignment)
		{
			if(otherAssignment == null)
			{
				return false;
			}
			
			//check for same length
			if(this.epRobotsMapping.Count != otherAssignment.epRobotsMapping.Count)
			{
				return false;
			}
			
			//check for same entrypoints
			EntryPoint[] ownEps = this.epRobotsMapping.EntryPoints;
			EntryPoint[] otherEps = otherAssignment.epRobotsMapping.EntryPoints;
			for(int i = 0; i < ownEps.Length; ++i) // TODO: The order have to be the same? 
			{
				if(ownEps[i].Id != otherEps[i].Id)
				{
					return false;
				}
			}
			
			//check for same robots in entrypoints
			ICollection<int>[] ownRobots = this.epRobotsMapping.Robots;
			ICollection<int>[] otherRobots = otherAssignment.epRobotsMapping.Robots;
			for(int i = 0; i < ownRobots.Length; ++i)
			{
				if(ownRobots[i].Count!=otherRobots[i].Count)
				{
					return false;
				}

				foreach(int robot in ownRobots[i])
				{

					if(!otherRobots[i].Contains(robot))
					{
						return false;
					}
				}
			}
					
			return true;		
		}

		public override string ToString()
		{
			string retString = "\nRating: " +  this.max + "\n";
			EntryPoint[] ownEps = this.epRobotsMapping.EntryPoints;
			ICollection<int>[] ownRobots = this.epRobotsMapping.Robots;
			for(int i = 0; i < this.epRobotsMapping.Count; ++i)
			{
				retString += "EP: " + ownEps[i].Id + " Task: " + ownEps[i].Task.Name + " RobotIDs: ";
				foreach(int robot in ownRobots[i])
				{
					retString += robot + " ";
				}
				retString += "\n";				
			}
			retString += this.robotStateMapping.ToString();
			retString += this.epSucMapping.ToString();
			return retString;
		}
#endregion *** Methods ***
	}
}
