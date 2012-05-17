using System;
using Alica;
using Castor;
using RosCS;
using RosCS.turtlesim;
namespace alica_turtle
{
	public abstract class TurtleBehaviour : BasicBehaviour
	{
		private static Node rosNode;		
		private static Publisher motionPub;		
		private static int ownID = SystemConfig.GetOwnRobotID();
		
		public static void RosInit() {
			if(rosNode!= null) return;			
			rosNode = Node.MainNode;
			motionPub = new Publisher(rosNode,"turtle"+ownID+"/command_velocity", Velocity.TypeId,1);			
		}
		
		public TurtleBehaviour(string name) : base(name)
		{			
		 	if(rosNode == null) {				
				RosInit();
			}
		}
		protected WorldModel WM
		{			
			get { return WorldModel.Get(); }			
		}
		protected bool Send(Velocity v) {
			motionPub.Send(v);
			return true;
		}
	}
}

