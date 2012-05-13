// RoleSet.cs created with MonoDevelop
// User: stefant at 18:51 09/01/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{	
	public class RoleSet : PlanElement
	{
		protected LinkedList<RoleTaskMapping> roleTaskMappings = new LinkedList<RoleTaskMapping>();
		
		protected bool isDefault = false;
		
		//the plan ID this roleset is defined for
		protected long usableWithPlanID;
		
		public RoleSet()
		{
		}
		
		public LinkedList<RoleTaskMapping> RoleTaskMappings
		{
			set { this.roleTaskMappings = value; }
			get { return this.roleTaskMappings; }
		}
		
		public bool IsDefault
		{
			set { this.isDefault = value; }
			get { return this.isDefault; }
		}
		
		public long UsableWithPlanID
		{
			set { this.usableWithPlanID = value; }
			get { return this.usableWithPlanID; }
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#RoleSet: " + this.Name + " " + this.Id + "\n";
			
			ret += "\tUsableWithPlanID: " + this.usableWithPlanID + "\n";

			ret += "\tContains Mappings: " + this.roleTaskMappings.Count + "\n";
			foreach (RoleTaskMapping rtm in this.roleTaskMappings)
			{
				ret += "\tRoleTaskMapping: "+rtm+ "\n";
			}
			ret += "#EndRoleSet\n\n";
			
			return ret;
		}

	}
}
