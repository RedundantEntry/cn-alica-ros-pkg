
using System;
using System.IO;
using System.Net;
using System.Text;
using System.Collections.Generic;
using Castor;
using RosCS;

namespace Alica
{

	/// <summary>
	/// The Plan Logger will write a log file according to the settings in the Alica.conf file.
	/// </summary>
	public class Logger : IPlanTreeVisitor
	{
		ITeamObserver to;
		bool active = false;
		StreamWriter sw;
		FileStream f;
		
		bool receivedEvent;
		StringBuilder sBuild;
		List<string> eventStrings;
		
		
		ulong startTime;
		int itcount;
		ulong endTime;
		double time;
		bool inIteration;
		public Logger ()
		{
			SystemConfig sc = SystemConfig.LocalInstance;
			this.active = sc["Alica"].GetBool("Alica.EventLogging.Enabled");
			if(this.active) {
			
				string robotname = AlicaEngine.Get().GetRobotName();
							
				string timeString = DateTime.Now.ToString("s");
				
				timeString = timeString.Replace(':', '-');
				string esRoot = Environment.GetEnvironmentVariable("ES_ROOT");
				
				string logPath = sc["Alica"].GetString("Alica.EventLogging.LogFolder");
				if(!Path.IsPathRooted(logPath)) {
					if(esRoot.LastIndexOf(Path.DirectorySeparatorChar)==esRoot.Length-1) {
						logPath = esRoot+logPath;
					}
					else {
						logPath = esRoot+Path.DirectorySeparatorChar+logPath;
					}
				}
				if (logPath.LastIndexOf(Path.DirectorySeparatorChar)!= logPath.Length-1) {
					logPath += Path.DirectorySeparatorChar;
				}
				if(!Directory.Exists(logPath)) {
					try {
						Directory.CreateDirectory(logPath);
					} catch(Exception e) {
						AlicaEngine.Get().Abort(String.Format("Cannot create log folder: {0} ({1})",logPath,e));
					}
				}				
				string logFile = logPath + "alica-run--"+robotname+"--"+timeString+".txt";
				this.f = new FileStream(logFile, FileMode.Create);
				
				this.sw = new StreamWriter(this.f);				
				this.sw.AutoFlush = true;	
				this.sBuild = new StringBuilder();
				this.eventStrings = new List<string>();
				
				this.inIteration = false;
				this.to = AlicaEngine.Get().TO;
				
					
			}
			this.receivedEvent = false;
		}
		/// <summary>
		/// Notify the logger that an event occurred which changed the plan tree.
		/// </summary>
		/// <param name="e">
		/// A <see cref="System.String"/> denoting the event
		/// </param>
		public void EventOccurred(string e) {
			if(!this.active) return;
			if(!this.inIteration) {
				e += "(FP)"; //add flag for fast path out-of-loop events.
			}
			this.eventStrings.Add(e);
			this.receivedEvent = true;
		}
		/// <summary>
		/// Notify the logger of a new iteration, called by the <see cref="PlanBase"/>
		/// </summary>
		public void IterationStarts() {
			this.inIteration = true;
			this.startTime = RosSharp.Now();
		}
		/// <summary>
		/// Notify that the current iteration is finished, triggering the looger to write an entry if an event occurred in the current iteration.
		/// Called by the <see cref="PlanBase"/>.
		/// </summary>
		/// <param name="root">
		/// The root <see cref="RunningPlan"/> of the plan base.
		/// </param>
		public void IterationEnds(RunningPlan root) {
			if(!this.active) return;
			this.inIteration = false;
			this.endTime = RosSharp.Now();
			this.itcount++;
			this.time += (endTime-startTime)/1000UL; //us
			
			if(!this.receivedEvent) return;
	
			this.receivedEvent = false;
			
			List<string> ownTree = CreateTreeLog(root);
				
			this.sBuild.Append("START:\t");
			this.sBuild.AppendLine((this.startTime/1000000UL).ToString());
			this.sBuild.Append("AVG-RT:\t");
			this.sBuild.AppendLine((this.time/(1000.0*itcount)).ToString());
			this.sBuild.Append("CUR-RT:\t");
			this.sBuild.AppendLine(((double)(this.endTime-this.startTime)/1000000.0).ToString());
			this.sBuild.Append("REASON:");
			foreach(string reason in this.eventStrings) {
				this.sBuild.Append("\t");
				this.sBuild.Append(reason);
			}
			this.sBuild.AppendLine();

			List<int> robs = this.to.GetAvailableRobotIds();
			
			this.sBuild.Append("TeamSize:\t");	
			this.sBuild.AppendLine(robs.Count.ToString());
			
			this.sBuild.Append("TeamMember:");	
			foreach(int id in robs) {
				this.sBuild.Append("\t");
				this.sBuild.Append(id.ToString());
			}
			this.sBuild.AppendLine();
			
			this.sBuild.Append("LocalTree:");
			
			foreach(string id in ownTree) {
				this.sBuild.Append("\t");
				this.sBuild.Append(id.ToString());
			}
			this.sBuild.AppendLine();
			
			EvaluationAssignmentsToString(this.sBuild,root);
			
			Dictionary<int,SimplePlanTree> teamPlanTrees = this.to.GetTeamPlanTrees();
			if(teamPlanTrees != null)
			{		
				this.sBuild.AppendLine("OtherTrees:");
				foreach(KeyValuePair<int, SimplePlanTree> kvp in teamPlanTrees)	{
					
					this.sBuild.Append("OPT:");
					this.sBuild.Append(kvp.Key);
					this.sBuild.Append("\t");
					
					List<string> ids = CreateHumanReadablePlanTree(kvp.Value.StateIDs);

					foreach (string name in ids) {						
						this.sBuild.Append(name + "\t");
					}
					this.sBuild.AppendLine();
					
				}				
			}
			else {				
				this.sBuild.AppendLine("NO OtherPlanTrees");
			}
			this.sBuild.AppendLine("END");
			this.sw.Write(this.sBuild.ToString());
			this.sBuild.Remove(0,this.sBuild.Length);
			this.time = 0;
			this.itcount = 0;
			this.eventStrings.Clear();
			//root.Accept(this);
		}
		/// <summary>
		/// Internal method to create the log string from a serialised plan.
		/// </summary>
		/// <param name="list">
		/// A <see cref="IList<System.Int64>"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<System.String>"/>
		/// </returns>
		protected List<string> CreateHumanReadablePlanTree(IList<long> list)
		{
			List<string> result = new List<string>();
			
			Dictionary<long,State> states = AlicaEngine.Get().PR.States;
			
			State s;
			EntryPoint e;
			foreach(long id in list) {
				if(id > 0)	{					
					if(states.TryGetValue(id,out s)) {
						e = EntryPointOfState(s);						
						result.Add(e.Task.Name);
						result.Add(s.Name);					
					}
				}
				else {
					result.Add(id.ToString());
				}
			}
			
			return result;
		}
		
		/// <summary>
		/// Closes the logger.
		/// </summary>
		public void Close() {
			if(this.active) {
				this.active = false;
				this.sw.Close();
				this.f.Close();
			}
		}
		public void Visit(RunningPlan r) {
			
			
		}
		
		protected EntryPoint EntryPointOfState(State s) {
			foreach(EntryPoint ep in s.InPlan.EntryPoints.Values) {
				if (ep.ReachableStates.Contains(s)) return ep;
			}
			return null;
		}
		protected void EvaluationAssignmentsToString(StringBuilder sb, RunningPlan rp)
		{
			if(rp.IsBehaviour)	{				
				return;
			}
			
			sb.Append(rp.Assignment.ToHackString());			
			foreach(RunningPlan child in rp.Children) {
				EvaluationAssignmentsToString(sb, child);
			}
		}
		protected List<string> CreateTreeLog(RunningPlan rp)
		{
			List<string> result = new List<string>();
			
			if(rp.ActiveState != null)
			{
				if(rp.OwnEntryPoint != null)
				{
					result.Add(rp.OwnEntryPoint.Task.Name);
				}
				else
				{
					result.Add("-3"); //indicates no task
				}
				
				result.Add(rp.ActiveState.Name);
			}
			else
			{
				if(rp.BasicBehaviour != null)
				{
					//behaviour
					//result.Add("DefaultTask");
					result.Add("BasicBehaviour");
					result.Add(rp.BasicBehaviour.Name);
					//result.Add(rp.Plan.Name);
				}
				else //will idle
				{
					result.Add("IDLE");
					result.Add("NOSTATE");
				}
			}
			
			if(rp.Children.Count != 0)
			{
				result.Add("-1"); //start children marker
					
				foreach (RunningPlan r in rp.Children)
				{
					List<string> tmp = CreateTreeLog(r);
					foreach (string s in tmp)
					{
						result.Add(s);
					}
				}
					
				result.Add("-2"); //end children marker
			}
			
			return result;
		}
		
	}
}
