
using System;

namespace Alica
{
	/// <summary>
	/// A terminal failure state in a plan. Indicates unsuccesful termination.
	/// </summary>
	public class FailurePoint : ExitPoint
	{
		/// <summary>
		/// Basic Ctor
		/// </summary>
		public FailurePoint()
		{
			this.IsTerminal = true;
			this.IsSuccessState = false;
			this.IsFailureState = true;
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#FailurePoint: " + this.Name + " " +this.Id +"\n";
			
			
			ret += "\tResult:\n";
			//ret += this.Result;
			if(this.Result != null)
				ret += "\t" + this.Result.Id + " " + this.Result.Name + "\n";
			ret += "\n";
			
			ret += "\tInTransitions: " +this.InTransitions.Count+ "\n";		
			if(this.InTransitions.Count != 0)
			{				
				foreach (Transition t in this.InTransitions)
				{
					//ret += t;
					ret += "\t" + t.Id + " " + t.Name + "\n";
				}
			}
			
					
			
			ret += "\n#EndFailurePoint\n";
			
			return ret;
			
		}
	}
}
