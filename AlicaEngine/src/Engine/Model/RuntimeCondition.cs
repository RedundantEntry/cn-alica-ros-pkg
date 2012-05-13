// RuntimeCondition.cs created with MonoDevelop
// User: stefant at 14:11 06/12/2008
//

using System;

namespace Alica
{
	
	
	public class RuntimeCondition : Condition
	{
		
		public RuntimeCondition(long id) : base(id)
		{
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#RuntimeCondition: " + this.Name + " " +this.Id +"\n";
			ret += "\tConditionString: "+this.ConditionString +"\n";
			
			ret += "#EndRuntimeCondition\n";
			
			return ret;
		}
	}
}
