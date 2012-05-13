using System;
using System.Collections.Generic;

namespace Alica 
{
	public class SuccessCollection 
	{
		protected EntryPoint[] keys;
		protected List<int>[] values;
		protected int size=0;
		
		internal SuccessCollection(Plan plan) {
			this.size = plan.EntryPoints.Count;
			this.keys = new EntryPoint[this.size];
			this.values = new List<int>[this.size];
			int i=0;
			List<EntryPoint> eps = new List<EntryPoint>(plan.EntryPoints.Values);
			eps.Sort();
			foreach(EntryPoint ep in eps) {
				this.keys[i] = ep;
				this.values[i] = new List<int>();
				i++;
			}
		}
#region *** Properties ***		
		public int Count {
			get {return this.size;}			
		}

		public EntryPoint[] EntryPoints {
			get {
				return this.keys;
			}			
		}
		public List<int>[] Robots {
			get {
				return this.values;
			}	
			internal set {
				this.values=value;
			}
		}
#endregion *** Properties ***
#region *** Methods ***	
		internal void SetSuccess(int robot, EntryPoint ep) {
				for(int i=0; i<this.size; i++) {
					if(this.keys[i] == ep) {
						this.values[i].Add(robot);
						return;
					}
				}
		}
		internal void Clear() {
			for(int i=0; i<this.size; i++) {
				this.Robots[i].Clear();
			}
		}
		public ICollection<int> GetRobots(EntryPoint k) {

			for (int i=0; i<this.size;i++) {
				if (this.keys[i] == k) return this.values[i];
			}
			return null;
		}
		public ICollection<int> GetRobotsById(long id) {
			for (int i=0; i<this.size;i++) {
				if (this.keys[i].Id == id) return this.values[i];
			}
			return null;
		}
		public override string ToString ()
		{
			string ret = String.Empty;
			for(int i=0; i<this.Count;i++) {
				if(this.values[i].Count > 0) {
					ret += this.keys[i].Task.Name+": ";
					foreach(int r in this.values[i]) {
						ret +=r+" ";
					}
					ret +="\n";
				}
			}
			if (!ret.Equals(String.Empty)) {
				return "Successes:\n"+ret;
			}
			else return ret;
		}

#endregion *** Methods ***		
	}
}

