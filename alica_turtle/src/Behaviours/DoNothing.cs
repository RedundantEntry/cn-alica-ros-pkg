using System;
namespace alica_turtle
{
	public class DoNothing : TurtleBehaviour
	{
		public DoNothing(string name) : base (name)
		{
		
		}
		
		public override void Run (object o)
		{
			//This behaviour just does nothing. It is not needed, and exists only as a stub
		}
	}
}

