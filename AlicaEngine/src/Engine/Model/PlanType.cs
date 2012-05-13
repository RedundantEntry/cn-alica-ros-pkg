using System;
using System.Collections.Generic;

namespace Alica
{
	
	public class PlanType : AbstractPlan
	{
		protected LinkedList<Plan> plans = new LinkedList<Plan>();
		protected List<Parametrisation> parametrisation = new List<Parametrisation>();
		
		public PlanType()
		{
		}
		
		public LinkedList<Plan> Plans
		{
			set { this.plans = value; }
			get { return this.plans; }
		}
		public List<Parametrisation> Parametrisation
		{
			set { this.parametrisation = value; }
			get { return this.parametrisation; }
		}
		
		string fileName;
		public override string FileName {
			get {
				if (string.IsNullOrEmpty(this.fileName)) return this.Name+".pty";
				else return this.fileName;
			}
			set {
				this.fileName = value;
			}
				
		}	
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#PlanType: " + this.Name + " " + this.Id + "\n";			
			
			ret += "\tPlans: " +this.Plans.Count+ "\n";
			if(this.Plans.Count != 0)
			{				
				foreach (Plan p in this.Plans)
				{
					//ret += "\t" + p;
					ret += "\t" + p.Id + " " + p.Name + "\n";
				}
			}
			ret += "#EndPlanType\n";			
			
			return ret;
		}
	}
}
