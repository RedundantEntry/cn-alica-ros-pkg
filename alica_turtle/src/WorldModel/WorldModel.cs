using System;
using System.Collections.Generic;
using Alica;
using RosCS;
using RosCS.turtlesim;
using RosCS.alica_turtle;
using Castor;
namespace alica_turtle
{
	public class WorldModel
	{
		private static WorldModel instance = null;
		private static object lockobj = new object();
		private static bool lockready = false;
		//Singleton
		public static WorldModel Get() {
			if(lockready) return instance;
			lock(lockobj) {
				if(!lockready) {
					instance = new WorldModel();
					System.Threading.Thread.MemoryBarrier();
					lockready = true;
				}
			}
			return instance;
		}
		
		Dictionary<int,Pose> turtlePositions;
		Pose ownPos;
		int ownID;
		Node node;
		Publisher sharedWorldPub;
		RosCS.Timer timer;
		private WorldModel ()
		{
			this.turtlePositions = new Dictionary<int, Pose>();
			this.ownID = SystemConfig.GetOwnRobotID();
			
			this.node = Node.MainNode;
			
			this.sharedWorldPub = new Publisher(this.node,"SharedWorld",SharedWorld.TypeId,1);
			this.node.Subscribe("SharedWorld",OnSharedWorld,10);
			this.node.Subscribe("turtle"+this.ownID+"/pose",OnPose,1);
			
			this.timer = new RosCS.Timer(this.SendState,300,100);
			
		}
		//Public properties:
		public Pose OwnPos {
			get { return this.ownPos; }
		}
		public Dictionary<int, Pose> TurtlePositions {
			get {return this.turtlePositions;}
		}
		//Message Sending and Receiving:
		public void SendState(object o) {
			if(this.ownPos == null) return;
			SharedWorld msg = new SharedWorld();
			msg.Position = this.ownPos;
			msg.SenderID = this.ownID;	
			this.sharedWorldPub.Send(msg);
		}
		public void OnPose(Pose p) {
			//Normalize theta:
			if (p.Theta > Math.PI) p.Theta -=(float)(2*Math.PI);
			else if (p.Theta < Math.PI) p.Theta +=(float)(2*Math.PI);
			this.ownPos = p;
			//Console.WriteLine("My Position is {0} {1} {2}",p.X,p.Y,p.Theta);
		}
		public void OnSharedWorld(SharedWorld msg) {
			if(!this.turtlePositions.ContainsKey(msg.SenderID)) {
				lock(this.turtlePositions) {
					this.turtlePositions.Add(msg.SenderID,msg.Position);
				}
			} else {
				this.turtlePositions[msg.SenderID] = msg.Position;
			}
		}
		public void Close() {
			if(this.timer != null) {
				this.timer.Stop();
				this.timer = null;
			}
		}
	}
}

