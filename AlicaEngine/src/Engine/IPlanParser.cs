// IPlanParser.cs created with MonoDevelop
// User: stefant at 17:21 08/05/2008
//

using System;
using System.Collections.Generic;
using System.Xml;

namespace Alica
{
		
	public interface IPlanParser
	{	
		
		Plan ParsePlanTree(string masterplan);
		RoleSet ParseRoleSet(string roleSetName,string roleSetDir);
		
		
		
		Dictionary<long,PlanElement> GetParsedElements();
		
	}
}
