// SimplePlanTree.cs created with MonoDevelop
// User: stefant at 18:12 08/28/2008
//

using System;
using System.Collections.Generic;

//using CarpeNoctem.Messages;

namespace Alica
{
	/// <summary>
	/// A SimplePlanTree is a simplified version of the <see cref="RunningPlan"/>, usually created from an incoming message. It thus represents the plan graph of another robot.
	/// </summary>
	public class SimplePlanTree
	{
		
		protected SimplePlanTree parent = null;
		protected HashSet<SimplePlanTree> children = new HashSet<SimplePlanTree>();
		
		//contains the "serialized" form of this tree
		//protected PlanTreeInfo pti = null;
		
		
		
		protected State state = null;
		//protected Task task = null;
		protected EntryPoint entryPoint = null;
		
		protected IList<long> message;
		protected int robotID = -1;
		internal bool IsNew {get; set;}
		
		public SimplePlanTree() {
			this.IsNew = true;
		}
		
		
		public SimplePlanTree(State s, EntryPoint ep)
		{
			this.state = s;
			this.entryPoint = ep;
		}
		public IList<long> StateIDs { get; set;}
		
		public bool RemoveChild (SimplePlanTree spt)
		{
			if(children.Contains(spt))
			{
				children.Remove(spt);

				spt.Parent = null;
				return true;
			}
			return false;
		}
		
		public void RemoveAllChildren()
		{
			
			foreach (SimplePlanTree spt in this.children)
			{
				spt.Parent = null;
			}
			
			this.children.Clear();
		}
		
		public bool AddChild (SimplePlanTree spt)
		{
			if(!children.Contains(spt))
			{
				children.Add(spt);
				spt.Parent = this;
				
				return true;
			}
			return false;
		}
				
		public bool AddChildren (LinkedList<SimplePlanTree> spts)
		{
			foreach (SimplePlanTree spt in spts)
			{
				if (!children.Contains(spt))
				{
					children.Add(spt);
					spt.Parent = this;
				}
				else
				{					
					return false;
				}
			}
			return true;
		}
		/// <summary>
		/// The parent SimplePlanTree
		/// </summary>
		public SimplePlanTree Parent
		{
			set { this.parent = value; }
			get { return this.parent; }
		}
		
		/// <summary>
		/// The state occupied by the respective robot.
		/// </summary>
		public State State
		{
			set { this.state = value; }
			get { return this.state; }
		}
		
		public EntryPoint EntryPoint
		{
			set { this.entryPoint = value; }
			get { return this.entryPoint; }
		}
		
		public HashSet<SimplePlanTree> Children
		{
			set { this.children = value; }
			get { return this.children; }
		}
		/// <summary>
		/// The (ROS-)timestamp denoting when this tree was received.
		/// </summary>
		public ulong ReceiveTime {get; set;}

		/// <summary>
		/// The id of the robot to which this tree refers to
		/// </summary>
		public int RobotID
		{
			set { this.robotID = value; }
			get { return this.robotID; }
		}
		public bool ContainsPlan(AbstractPlan p) {
			if (this.EntryPoint.InPlan == p) return true;
			foreach(SimplePlanTree spt in this.Children) {
				if (spt.ContainsPlan(p)) return true;
			}
			return false;
		}
		public override string ToString ()
		{
			string result = "";
			
			result += "RobotID: " + this.robotID + "\n";
			result += "Parent: ";
			
			if(this.parent != null)
			{
				result += parent.State.Id;
			}
			result += "\n";
			
			result += "State: ";
			if(state != null)
			{
				result += state.Id;
				result += " " + state.Name;
			}
			else
			{
				result += "ERROR !!!NO STATE !!!";
			}
						
			result += "\n";
			
			result += "EntryPoint: ";
			
			if (this.entryPoint != null)
			{
				result += entryPoint.Id + " "+this.entryPoint.Task.Name;
				//result += " " + entr;
			}
			else
			{
				result += "NoEntryPoint";
			}
			result += "\n";
			
			result += "Children: " + this.children.Count + "\n";
			foreach(SimplePlanTree spt in this.children)
			{
				result += spt;
			}
			
			result += "\n\n";
			
			return result;
		}

		
	}
}
