using System;
using System.Collections.Generic;

namespace Alica 
{
	public class StateCollection
	{
		protected List<int> keys; //robos
		protected List<State> values;		
		
		public StateCollection(List<int> robots, List<State> states) {			
			this.keys = robots;
			this.values = states;
		}
		public StateCollection(int maxSize) {
			this.keys= new List<int>(maxSize);
			this.values = new List<State>(maxSize);
		}
		public StateCollection() {
			this.keys = new List<int>();
			this.values = new List<State>();
		}
		public StateCollection(AssignmentCollection ac) {
			this.keys = new List<int>();
			this.values = new List<State>();
			for(int i=0; i<ac.Count; i++) {
				State initialState = ac.EntryPoints[i].State;
				foreach(int r in ac.Robots[i]) {
					this.SetState(r,initialState);
				}
			}
		
			
		}
		
#region *** Properties ***		
		public int Count {
			get {return this.keys.Count;}			
		}

		public List<int> Robots {
			get {
				return this.keys;
			}		
			set {
				this.keys=value;
				//this.size = keys.Length;
			}
		}
		public List<State> States {
			get {
				return this.values;
			}	
			set {
				this.values=value;
			}
		}
#endregion *** Properties ***
#region *** Methods ***		
		public State GetState(int r) {

			for (int i=0; i<this.keys.Count;i++) {
				if (this.keys[i] == r) return this.values[i];
			}
			return null;
		}
		public HashSet<int> GetRobotsInState(State s) {
			HashSet<int> ret = new HashSet<int>();
			for(int i=0; i <this.keys.Count; i++) {
				if (this.values[i] == s) {
					ret.Add(this.keys[i]);
				}
			}			
			return ret;
		}
		public HashSet<int> GetRobotsInState(long sid) {
			HashSet<int> ret = new HashSet<int>();
			for(int i=0; i <this.keys.Count; i++) {
				if (this.values[i].Id == sid) {
					ret.Add(this.keys[i]);
				}
			}			
			return ret;
		}
		
		public void RemoveRobot(int r) {
			for (int i=0; i<this.keys.Count;i++) {
				if (this.keys[i] == r) {
					this.values.RemoveAt(i);
					this.keys.RemoveAt(i);					
					return;
				}
			}
		}
		public void Clear() {
			this.keys.Clear();
			this.values.Clear();
		}
		public State StateOfRobot(int robot) {
			for (int i=0; i<this.keys.Count;i++) {
				if (this.keys[i] == robot) {					
					return this.values[i];
				}				
			}
			return null;
		}
		public void SetState(int robot, State state) {
			for (int i=0; i<this.keys.Count;i++) {
				if (this.keys[i] == robot) {
					this.values[i] = state;
					return;
				}				
			}
			this.keys.Add(robot);
			this.values.Add(state);
			
		}
		public void SetStates(ICollection<int> robots,State state) {
//bool debug = robots.Count==0;			
//int myid = BehaviourPlanningEngine.Get().SHWM.GetOwnRobotProperties().Id;
			foreach(int r in robots) {
				SetState(r,state);
//debug |= myid == r;
			}
/*if (!debug) {
				throw new Exception("Fatal: Own Id not in state");
}*/
		}
		
		public void SetInitialState(int robot, EntryPoint ep) {
			State initialState = ep.State;
			SetState(robot, initialState);
		}
		public override string ToString ()
		{
			string ret = "";
			for(int i=0; i<this.keys.Count; i++) {
				ret+= "R: "+this.keys[i]+" in State: "+(this.values[i]==null?"NULL": this.values[i].Name+" ("+this.values[i].Id+")");
			}
			return ret+"\n";
		}
		
		
		//we are at new assignment, so everything is set to initial states, set them back:
		public void ReconsiderOldAssignment(Assignment olda, Assignment newa) {
			if (olda.Plan != newa.Plan) return;
			EntryPoint[] eps = olda.GetEntryPoints();
			for(int i=0; i<eps.Length; i++) {
				foreach(int rid in olda.GetRobotsWorking(eps[i])) {
					if(newa.GetRobotsWorking(eps[i]).Contains(rid)) { //Robot rid hasn't changed assignment
						this.SetState(rid,olda.RobotStateMapping.GetState(rid));
					}
				}
			}
			
		}

#endregion *** Methods ***		
	}
}

