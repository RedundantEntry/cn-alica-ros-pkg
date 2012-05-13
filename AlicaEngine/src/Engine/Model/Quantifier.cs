// Condition.cs created with MonoDevelop
// User: stefant at 14:10 06/12/2008
//

using System;
using System.Collections.Generic;
using AD=AutoDiff;

namespace Alica
{	
	
	/// <summary>
	/// A quantifier encapsulates a set of <see cref="Variable"/>s, belonging to a domain artifact, scoped under a PlanElement
	/// </summary>
	public abstract class Quantifier : PlanElement
	{
		/// <summary>
		/// Indicates that the scope of this quantifier is an <see cref="EntryPoint"/>
		/// </summary>
		public bool ScopeIsEntryPoint {get; private set;}
		/// <summary>
		/// Indicates that the scope of this quantifier is a <see cref="Plan"/>
		/// </summary>
		public bool ScopeIsPlan {get; private set;}
		/// <summary>
		/// Indicates that the scope of this quantifier is an <see cref="State"/>
		/// </summary>
		public bool ScopeIsState {get; private set;}
			
		EntryPoint ep;
		State s;
		Plan p;
		/// <summary>
		/// The list of strings idendtifying specific domain elements.
		/// </summary>
		public List<string> DomainIdentifiers {get; set;}
		
		/// <summary>
		/// Set the scope of this quantifier, called by the <see cref="ModelFactory"/>
		/// </summary>
		/// <param name="pe">
		/// A <see cref="PlanElement"/>
		/// </param>
		public void SetScope(PlanElement pe) {
			ScopeIsEntryPoint = pe is EntryPoint;
			ScopeIsPlan = pe is Plan;
			ScopeIsState = pe is State;
			
			if (ScopeIsPlan) {
				this.p =(Plan)pe;
			} else if(ScopeIsEntryPoint) {
				this.ep = (EntryPoint)pe;
			} else if(ScopeIsState) {
				this.s = (State)pe;
			} else {
				AlicaEngine.Get().Abort(String.Format("Scope of Quantifier is not an entrypoint, plan, or state: {0}",pe));
			}
		}
		public PlanElement GetScope() {
			if(ScopeIsPlan) return this.p;
			if(ScopeIsState) return this.s;
			if(ScopeIsEntryPoint) return this.ep;
			return null;
		}
		/// <summary>
		/// Returns the scope of this quantifier, returns null, if the scope is not a state.
		/// </summary>
		/// <returns>
		/// A <see cref="State"/>
		/// </returns>
		public State GetScopedState() {return s;}
		/// <summary>
		/// Returns the scope of this quantifier, returns null, if the scope is not an EntryPoint.
		/// </summary>
		/// <returns>
		/// A <see cref="EntryPoint"/>
		/// </returns>
		public EntryPoint GetScopedEntryPoint() {return ep;}
		/// <summary>
		/// Returns the scope of this quantifier, returns null, if the scope is not a Plan.
		/// </summary>
		/// <returns>
		/// A <see cref="Plan"/>
		/// </returns>
		public Plan GetScopedPlan() {return p;}
		
		/// <summary>
		/// Constructor
		/// </summary>
		/// <param name="id">
		/// A <see cref="System.Int64"/>
		/// </param>
		public Quantifier(long id)
		{
			this.Id = id;
			this.DomainIdentifiers = new List<string>();			
		}
		
		/// <summary>
		/// Access the list of sorted Variables under the scope of this quantifier given a runningplan.
		/// </summary>
		/// <param name="p">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<Variable[]>"/>
		/// </returns>
		public abstract List<Variable[]> GetSortedVariables(RunningPlan p,out ICollection<int> agentsInScope);
		/// <summary>
		/// Access the list of sorted AD.Terms under the scope of this quantifier given a runningplan.
		/// </summary>
		/// <param name="p">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<AD.Term[]>"/>
		/// </returns>
		public abstract List<AD.Term[]> GetSortedTerms(RunningPlan p,out ICollection<int> agentsInScope);
		
	}
}
