// RoleTaskMapping.cs created with MonoDevelop
// User: stefant at 16:42 10/20/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{
	
	public class RoleTaskMapping : PlanElement
	{
		
		protected Role role = null;
		protected Dictionary<long, double> taskPriorities = new Dictionary<long,double>();
		
		public RoleTaskMapping()
		{
		}
		
		public Role Role
		{
			set { this.role = value; }
			get { return this.role; }
		}
		
		public Dictionary<long, double> TaskPriorities
		{
			set { this.taskPriorities = value; }
			get { return this.taskPriorities; }
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#RoleTaskMapping: " + this.Name + " " + this.Id + "\n";
			
			ret += "\tRole-Name: " + this.role.Name + "\n";
			
			ret += "\tTaskPriorities: " + this.taskPriorities.Count + "\n";
			foreach (long l in this.taskPriorities.Keys)
			{
				double val = this.taskPriorities[l];
					
				ret += "\t" + l + " : " + val + "\n";
			}			
			ret += "\n";
			
			ret += "#EndRoleTaskMapping\n\n";
			
			return ret;
		}

		
	}
}
