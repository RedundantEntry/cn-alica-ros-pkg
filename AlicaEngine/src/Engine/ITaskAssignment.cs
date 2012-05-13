using System;

namespace Alica
{	
	/// <summary>
	/// Interface for the task allocation algorithm.
	/// </summary>
	public interface ITaskAssignment
	{
		/// <summary>
		/// Returns the best possible assignment for a plan, taking similarities to the old assignment into account.
		/// </summary>
		/// <param name="oldAss">
		/// The old <see cref="IAssignment"/>, possibly null in case of a completely new assignment problem.
		/// </param>
		/// <returns>
		/// The new <see cref="Assignment"/>
		/// </returns>
		Assignment GetNextBestAssignment(IAssignment oldAss);
	}
}
