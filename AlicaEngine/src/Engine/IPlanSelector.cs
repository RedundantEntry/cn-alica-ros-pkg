using System;
using System.Collections.Generic;

namespace Alica
{	
	
	public interface IPlanSelector
	{
		//normal plan selection
		LinkedList<RunningPlan> GetPlansForState(RunningPlan planningParent, LinkedList<AbstractPlan> plans, ICollection<int> robotIDs);
		
		//a plan in a PlanType failed => try to find a new one
//		RunningPlan GetReplacementPlan(RunningPlan failedRp, ICollection<int> robotIDs);
		
		/// <summary> Get the best Assignment for this RP with its plan and old
		/// Assignment, which is also similar to the old Assignment </summary>
		/// <param name="rp">
		/// The old <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// The new and similar <see cref="Assignment"/>
		/// </returns>
		RunningPlan GetBestSimilarAssignment(RunningPlan rp);
		RunningPlan GetBestSimilarAssignment(RunningPlan rp, ICollection<int> robots);		
	}
}
