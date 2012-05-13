
using System;

namespace Alica
{


	public class Parametrisation : PlanElement
	{
		protected Variable var;
		protected Variable subVar;
		protected AbstractPlan subPlan;
		
		
		
		public Parametrisation () {}
		
		public Variable Var {
			get {				
				return this.var;
			}
			set {this.var = value;}
		}
		public Variable SubVar {
			get {return this.subVar;}
			set {this.subVar = value;}
		}
		public AbstractPlan SubPlan {
			get {return this.subPlan;}
			set {this.subPlan = value;}
		}		
		public override string ToString ()
		{
		
			return string.Format("[Parametrisation: Var={0} ({1}), SubVar={2} ({3}), SubPlan={4}]", Var.Name,Var.Id ,SubVar.Name, SubVar.Id, SubPlan.Name);
		}

	}
}
