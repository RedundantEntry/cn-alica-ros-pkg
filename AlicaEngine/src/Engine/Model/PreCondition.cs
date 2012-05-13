// PreCondition.cs created with MonoDevelop
// User: stefant at 14:10 06/12/2008
//

using System;

namespace Alica
{
	/// <summary>
	/// A precondition guards a <see cref="Plan"/> or a <see cref="Transition"/>.
	/// </summary>
	public class PreCondition : Condition
	{
		
		public PreCondition(long id) : base(id)
		{
			this.Enabled = true;
		}
		public bool Enabled {get; set;}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#PreCondition: " + this.Name + " " +this.Id +"\n";
			ret += "\tConditionString: "+this.ConditionString +"\n";
			
			ret += "#EndPreCondition\n";
			
			return ret;
		}
		
	}
}
