
using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// A terminal state within a plan. Indicates termination of the corresponding task.
	/// </summary>
	public abstract class ExitPoint : State
	{
		
		protected Result result;
		
		
		
		public ExitPoint()
		{			
			this.IsTerminal = true;
		}
		
		
		
		public Result Result
		{
			set { this.result = value; }
			get { return this.result; }
		}
		
		
		
	}
}
