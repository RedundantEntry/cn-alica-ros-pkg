// SuccessPoint.cs created with MonoDevelop
// User: stefant at 14:19 06/12/2008
//

using System;

namespace Alica
{
	/// <summary>
	/// A terminal state, encoding the succesful termination of a task.
	/// </summary>
	public class SuccessPoint : ExitPoint
	{
		/// <summary>
		/// Basic Ctor
		/// </summary>
		public SuccessPoint()
		{
			this.IsTerminal = true;
			this.IsSuccessState = true;
			this.IsFailureState = false;
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#SuccessPoint: " + this.Name + " " +this.Id +"\n";
			
			ret += "\tResult:\n";
			//ret += this.Result;
			if(this.Result != null)
				ret += "\t" + this.Result.Id + " " + this.Result.Name;
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
			ret += "\n";
			
			
			
			ret += "#EndSuccessPoint\n";
			
			return ret;
			
		}
	}
}
