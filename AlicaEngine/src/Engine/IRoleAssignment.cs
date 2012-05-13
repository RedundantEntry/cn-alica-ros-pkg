// IRoleAssignment.cs created with MonoDevelop
// User: stefant at 16:00 07/29/2008
//

using System;
using System.Collections.Generic;
using Alica;

namespace Alica
{
	public interface IRoleAssignment
	{
		Role GetRole(int robotID);
		Role OwnRole {get;}
		// Dictionary<ROLE, ROBOT>
		//Dictionary<int, Role> RobotRoleMapping {get;}
		Dictionary<int, Role> RobotRoleMapping {get;}
		//Role GetRoleOf(int id);
		//Dictionary<int, RobotRoleUtility> RobotRoleUtilityMapping {get;}
		
		
		void Init();
		void Tick();
	}
}
