using System;
using System.Collections.Generic;
using Castor;

namespace Alica
{
	/// <summary>
	/// Super class of plans, plantypes and behaviourconfigurations.
	/// </summary>
	public abstract class AbstractPlan : PlanElement
	{		
		
			
		protected LinkedList<Variable> vars = new LinkedList<Variable>();
		internal ulong AuthorityTimeInterval {
			get;
			set;
		}
		public AbstractPlan() : base() {
			this.AuthorityTimeInterval = SystemConfig.LocalInstance["Alica"].GetULong("Alica","CycleDetection","MinimalAuthorityTimeInterval")*1000000UL;
		}
		public virtual string FileName { get; set; }
		protected double utilityThreshold = 1.0;
				
		/// <summary>
		/// Tests whether a given variable belongs to this plan.
		/// </summary>
		/// <param name="v">
		/// A <see cref="Variable"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool ContainsVar(Variable v) {
			return this.vars.Contains(v);
		}
		public bool ContainsVar(string name) {
			foreach(Variable v in this.vars) {
				if(v.Name == name) return true;
			}
			return false;
		}
		public LinkedList<Variable> Variables
		{
			set { this.vars = value; }
			get { return this.vars; }
		}
		/// <summary>
		/// This plan's runtime condition.
		/// </summary>
		public RuntimeCondition RuntimeCondition {
			get; set;
		}
		/// <summary>
		/// This plan's precondition
		/// </summary>
		public PreCondition PreCondition {
			get; set;
		}	
		/// <summary>
		/// Whether this plan is marked as a Masterplan.
		/// </summary>
		public bool MasterPlan {get; set;}
		
		/// <summary>
		/// This plan's Utility function
		/// </summary>
		public UtilityFunction UtilityFunction {get; set;}
		
		/// <summary>
		/// The utility threshold, the higher, the less likely dynamic changes are.
		/// </summary>
		public double UtilityThreshold
		{
			set { this.utilityThreshold = value; }
			get { return this.utilityThreshold; }
		}
	}
}
