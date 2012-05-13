using System;
using System.Collections.Generic;
namespace Alica
{
	internal class ConstraintCall
	{
		internal Condition Condition {get; private set;}
		internal List<List<Variable[]>> SortedVariables {get; private set;}
		internal RunningPlan RunningPlan{get; private set;}
		internal List<ICollection<int>> AgentsInScope {get; private set;}
		
		internal ConstraintCall (Condition con, RunningPlan rp)
		{
			this.Condition = con;
			this.SortedVariables = new List<List<Variable[]>>();
			this.AgentsInScope = new List<ICollection<int>>();
			foreach(Quantifier q in this.Condition.Quantifiers) {
				ICollection<int> robots;
				this.SortedVariables.Add(q.GetSortedVariables(rp, out robots));
				if (robots!=null) {
					AgentsInScope.Add(robots);
				} else {
					AgentsInScope.Add(new List<int>());
				}
			}
			this.RunningPlan = rp;
		}
		internal bool HasVariable(Variable v) {
			foreach(List<Variable[]> lvarr in this.SortedVariables) {
				foreach(Variable[] varr in lvarr) {
					for(int i=0; i<varr.Length; i++) {
						if(varr[i]== v) return true;
					}
				}
			}
			return false;
		}
		public override bool Equals (object obj)
		{
			return this.Condition.Equals(((ConstraintCall)obj).Condition);
		}
		public override int GetHashCode ()
		{
			return this.Condition.GetHashCode();
		}
	}
}

