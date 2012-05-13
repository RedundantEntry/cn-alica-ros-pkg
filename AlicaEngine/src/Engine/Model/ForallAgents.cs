// Condition.cs created with MonoDevelop
// User: stefant at 14:10 06/12/2008
//

using System;
using System.Collections.Generic;
using AD=AutoDiff;

namespace Alica
{	
	
	/// <summary>
	/// A quantifier associated with agents, i.e., the domain identifiers of this quantifier refer to properties of an agent
	/// </summary>
	public class ForallAgents : Quantifier
	{
		
		
		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="id">
		/// A <see cref="System.Int64"/>
		/// </param>
		public ForallAgents(long id) : base(id)
		{			
			
		}
		/// <summary>
		/// Returns the <see cref="AutoDiff.Term"/>s currently associated with the agents occupying the scope of this quantifier.
		/// </summary>
		/// <param name="p">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<AD.Term[]>"/>, containing one element per agent, each AD.Term[] represents the domain identifiers specified.
		/// </returns>
		public override List<AD.Term[]> GetSortedTerms(RunningPlan p,out ICollection<int> robots) {
			robots = null;
			if (this.ScopeIsPlan) {
				if(p.Plan == this.GetScopedPlan())
				  robots = p.Assignment.GetAllRobots();
			} else
			if (this.ScopeIsEntryPoint) {
				robots = p.Assignment.GetRobotsWorking(this.GetScopedEntryPoint());
			} else
			if (this.ScopeIsState) {
				robots = p.Assignment.RobotStateMapping.GetRobotsInState(this.GetScopedState());
			}
			if(robots == null) return null;
			List<AD.Term[]> ret = new List<AD.Term[]>();
			ITeamObserver to = AlicaEngine.Get().TO;
			foreach(int r in robots) {
				AD.Term[] terms = new AD.Term[this.DomainIdentifiers.Count];
				RobotEngineData re = to.GetRobotById(r);
				for(int i=0; i<terms.Length; i++) {
					terms[i] = re.GetSortedVariable(this.DomainIdentifiers[i]).SolverVar;
				}
				ret.Add(terms);
			}
			return ret;
		}
		/// <summary>
		/// Returns the <see cref="Variable"/>s currently associated with the agents occupying the scope of this quantifier.
		/// </summary>
		/// <param name="p">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<Variable[]>"/>, containing one element per agent, each Variable[] represents the domain identifiers specified.
		/// </returns>
		public override List<Variable[]> GetSortedVariables(RunningPlan p,out ICollection<int> robots) {
			robots = null;
			if (this.ScopeIsPlan) {
				if(p.Plan == this.GetScopedPlan())
				  robots = p.Assignment.GetAllRobots();
			} else
			if (this.ScopeIsEntryPoint) {
				robots = p.Assignment.GetRobotsWorking(this.GetScopedEntryPoint());
			} else
			if (this.ScopeIsState) {
				robots = p.Assignment.RobotStateMapping.GetRobotsInState(this.GetScopedState());
			}
			if(robots == null) return null;
			List<Variable[]> ret = new List<Variable[]>();
			ITeamObserver to = AlicaEngine.Get().TO;
			foreach(int r in robots) {
				Variable[] terms = new Variable[this.DomainIdentifiers.Count];
				RobotEngineData re = to.GetRobotById(r);
				for(int i=0; i<terms.Length; i++) {
					terms[i] = re.GetSortedVariable(this.DomainIdentifiers[i]);
				}
				ret.Add(terms);
			}
			return ret;
		}
		
	}
}
