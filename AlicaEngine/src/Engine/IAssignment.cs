using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// An IAssignment describes a potentially partial assignment of robots to <see cref="EntryPoint"/>s within a plan.
	/// </summary>
	public interface IAssignment
	{
		
		ICollection<int> GetRobotsWorking(EntryPoint ep);
		ICollection<int> GetRobotsWorking(long epid);

		/// <summary>
		/// The ids of all robots available but not yet assigned.
		/// </summary>
		List<int> UnAssignedRobots 
		{
			get;
		}
		/// <summary>
		/// The number of robots assigned
		/// </summary>
		int NumUnAssignedRobots
		{
			get;
		}
		/// <summary>
		/// The minimal utility this assignment can achieve.
		/// </summary>
		double Min
		{
			get;
			set;
		}
		/// <summary>
		/// the maximal utility this assignment can achieve.
		/// </summary>
		double Max
		{
			get;
			set;
		}
		int TotalRobotCount();
		/// <summary>
		/// 
		/// </summary>
		/// <returns>
		/// The array of <see cref="EntryPoint"/>s relevant to this assignment.
		/// </returns>
		EntryPoint[] GetEntryPoints();
		/// <summary>
		/// The number of distinct entrypoints
		/// </summary>
		/// <returns>
		/// A <see cref="System.Int32"/>
		/// </returns>
		int GetEntryPointCount();
		/// <summary>
		/// Returns all robot ids working on the <see cref="Task"/> defined by ep and those which successfully completed it.
		/// Behaviour is undefined if ep is not relevant or null.
		/// </summary>
		/// <param name="ep">
		/// The <see cref="EntryPoint"/> queried
		/// </param>
		/// <returns>
		/// A <see cref="List<System.Int32>"/> of robot ids
		/// </returns>
		List<int> GetRobotsWorkingAndFinished(EntryPoint ep);
		/// <summary>
		/// Similar to <see cref="GetRobotsWorkingAndFinished"/>, with duplicates removed.
		/// Behaviour is undefined if ep is not relevant or null.
		/// </summary>
		/// <param name="ep">
		/// The <see cref="EntryPoint"/> queried
		/// </param>
		/// <returns>
		/// A <see cref="List<System.Int32>"/> of robot ids
		/// </returns>
		List<int> GetUniqueRobotsWorkingAndFinished(EntryPoint ep);
		
		/// <summary>
		/// Returns all robot ids working on the <see cref="Task"/> defined by ep.
		/// Behaviour is undefined if ep is not relevant or null.
		/// </summary>
		/// <param name="ep">
		/// The <see cref="EntryPoint"/> queried
		/// </param>
		/// <returns>
		/// A <see cref="List<System.Int32>"/> of robot ids
		/// </returns>
		List<int> GetRobotsWorkingAndFinished(long epid);
	}
}
