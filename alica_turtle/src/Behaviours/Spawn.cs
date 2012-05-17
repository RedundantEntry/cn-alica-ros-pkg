using System;
using RosCS;
using RosCS.turtlesim;
namespace alica_turtle
{
	public class Spawn : TurtleBehaviour
	{
		bool spawned;
		Service node;
		ServiceClient sp;
		public Spawn(string name) : base (name)
		{
			node = Service.MainNode; 
			sp = new ServiceClient(node,"spawn",RosCS.turtlesim.Spawn.TypeId);
 
		}
		protected override void InitializeParameters ()
		{
			this.spawned = false;
		}
		
		public override void Run (object o)
		{
			if(!spawned) {
				RosCS.turtlesim.Spawn s = new RosCS.turtlesim.Spawn();
				s.Request.X = 1;
				s.Request.Y = 1;
	 			s.Request.Theta = 0;
				s.Request.Name = "turtle"+this.GetOwnId();
				this.spawned = sp.Call(s);
				Console.WriteLine("Spawn Response : " + s.Response.Name + " " + spawned);
			}
		}
	}
}

