// Result.cs created with MonoDevelop
// User: stefant at 14:11 06/12/2008
//

using System;

namespace Alica
{	
	public class Result : Condition
	{
		
		public Result(long id) : base(id)
		{
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#Result: " + this.Name + " " +this.Id +"\n";
			ret += "\tConditionString: "+this.ConditionString +"\n";
			ret += "#EndResult\n";
			
			return ret;
		}
	}
}
