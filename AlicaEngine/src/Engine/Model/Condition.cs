// Condition.cs created with MonoDevelop
// User: stefant at 14:10 06/12/2008
//

using System;
using System.Collections.Generic;
using AD=AutoDiff;

namespace Alica
{	
	/// <summary>
	/// The delegate type used to attach constraints to plans.
	/// </summary>
	public delegate void GetConstraint(ConstraintDescriptor cd,RunningPlan rp);
	/// <summary>
	/// The delegate type used to attach conditions to plans.
	/// </summary>
	public delegate bool Evaluate(RunningPlan rp);
	/// <summary>
	/// A condition encapsulates expressions and constraint specific to a planelement, e.g., a transition, or a plan.
	/// </summary>
	public abstract class Condition : PlanElement
	{
		public GetConstraint Constraint;
		public Evaluate Eval;
		
		protected string conditionString;		
		protected List<Variable> vars;
		
		protected AbstractPlan abstractPlan = null;
		
		public Condition(long id)
		{
			this.Id = id;
			this.vars = new List<Variable>();
			this.Quantifiers = new List<Quantifier>();
			
		}
		//public void Commit(RunningPlan curplan) {
			//ConstraintStore.AttachConstraint(this,curplan);
		//}
		//public void Revoke() {
			//ConstraintStore.RemoveConstraint(this);
		//}
		
		public string ConditionString
		{
			set { this.conditionString = value; }
			get { return this.conditionString; }
		}
		/// <summary>
		/// The Abstract Plan in which this condition occurs.
		/// </summary>
		public AbstractPlan AbstractPlan
		{
			set { this.abstractPlan = value; }
			get { return this.abstractPlan; }
		}
		/// <summary>
		/// The static variables used in the constraint of this condition.
		/// </summary>
		public List<Variable> Vars
		{
			set { this.vars = value; }
			get { return this.vars; }
		}
		/// <summary>
		/// The quantifiers used in the constraint of this condition.
		/// </summary>
		public List<Quantifier> Quantifiers {get; private set;}

	}
}
