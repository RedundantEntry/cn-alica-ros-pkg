// RoleDefinitionSet.cs created with MonoDevelop
// User: stefant at 17:54 10/20/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{
	
	public class RoleDefinitionSet : PlanElement
	{
		
		public RoleDefinitionSet()
		{
			this.Roles = new List<Role>();
		}
		public List<Role> Roles {get; private set;}
		//nothing in here, because it is not needed until now
	}
}
