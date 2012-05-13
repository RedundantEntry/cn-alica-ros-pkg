// IExpressionValidator.cs created with MonoDevelop
// User: stefant at 14:12 08/19/2008
//

using System;
using System.Collections.Generic;

//using CarpeNoctem.Messages;
//using CarpeNoctem.Containers;
//using CMM = CarpeNoctem.Messages.Modules;

namespace Alica
{
	/// <summary>
	/// The base class of all Expressions, implements staitc helper functions to query the plan base.
	/// </summary>
	public abstract class AbstractExpressions		
	{
		//collection of usefull functions for expression evaluation
		
	
		/// <summary>
		/// Tests whether a given event has passed for an amount of time.
		/// </summary>
		/// <param name="timeOut">
		/// A <see cref="System.Int64"/> denoting the time interval in milliseconds.
		/// </param>
		/// <param name="startTime">
		/// A <see cref="System.UInt64"/> denoting the time of the event in question, a ros timestamp.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/> 
		/// </returns>
		public static bool IsTimeOut(long timeOut, ulong startTime)
		{
			if(startTime == 0) return false;			
			long time = (long)(RosCS.RosSharp.Now()/1000000UL);
			long timeDiff = time - (long)(startTime/1000000UL);				
			if(timeDiff > timeOut)	{
				return true;
			}
			return false;
		}
		
	
		
		/// <summary>
		/// Tests whether a task in rp is assigned to at least one robot.
		/// </summary>
		/// <param name="taskID">
		/// A <see cref="System.Int64"/>, specifying the task in question.
		/// </param>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>, specifying the plan in question.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public static bool IsTaskAssigned(long taskID, RunningPlan rp)	{
			if(rp.Assignment == null)
			{
				throw new Exception("AbstractExprVal: IsTaskAssigned, Plan does not have an assignment");
			}			
			EntryPoint e=null;
			foreach(EntryPoint ep in ((Plan)rp.Plan).EntryPoints.Values) {
				if (ep.Task.Id == taskID) {
					e = ep;
					break;
				}
			}
			if (e==null) return false;
			return rp.Assignment.IsEntryPointNonEmpty(e);			
		}
	
		/// <summary>
		/// Indicates whether a state is inhabitated by at least one robot.
		/// </summary>
		/// <param name="stateID">
		/// A <see cref="System.Int64"/>, indicating the state
		/// </param>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>, the plan in which to check.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public static bool IsRobotInState(long stateID, RunningPlan rp)
		{
			if(rp.Assignment==null) return false;
			return (rp.Assignment.RobotStateMapping.GetRobotsInState(stateID).Count > 0);
		}
		/// <summary>
		/// Returns whether at least one robot has succeeded with the task described by epId in rp.
		/// </summary>
		/// <param name="epId">
		/// A <see cref="System.Int64"/>, referring to an EntryPoint in rp.
		/// </param>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>, the plan in question.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public static bool IsEntryPointSuccessful(long epId,RunningPlan rp) {
			Plan p = rp.Plan as Plan;
			if (p==null) return false;
			EntryPoint ep = p.EntryPoints[epId];
			List<RobotEngineData> rs = AlicaEngine.Get().TO.GetAvailableRobots();
			foreach(RobotEngineData r in rs) {
				if (r.SuccessMarks.Succeeded(p,ep)) return true;
			}
			return false;
		}
		
	}
}
