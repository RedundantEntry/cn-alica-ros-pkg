using System;
using System.Collections.Generic;

namespace Alica 
{
	/// <summary>
	/// Holds the mapping from <see cref="EntryPoint"/>s to robots.
	/// </summary>
	public class AssignmentCollection //<T> where T:ICollection<int>
	{
		protected EntryPoint[] keys;
		protected ICollection<int>[] values;
		protected int size=0;
		
		
		/// <summary>
		/// Construct an AssignmentCollection from an EntryPoint array and an array of ICollections, each holding the robot-ids
		/// within the corresponding entrypoint.
		/// </summary>
		/// <param name="eps">
		/// A <see cref="EntryPoint[]"/>
		/// </param>
		/// <param name="robots">
		/// A <see cref="ICollection<System.Int32>[]"/>
		/// </param>	
		internal  AssignmentCollection(EntryPoint[] eps, ICollection<int>[] robots) {
			this.size = eps.Length;
			this.keys = eps;
			this.values = robots;
		}
		/// <summary>
		/// Construct an empty AssignmentCollection of a specific size
		/// </summary>
		/// <param name="maxSize">
		/// A <see cref="System.Int32"/>
		/// </param>
		internal  AssignmentCollection(int maxSize) {
			this.Count = maxSize;
			this.keys= new EntryPoint[maxSize];
			this.values = new List<int>[maxSize];
			for (int i=0; i < maxSize;i++) {
				this.values[i]=new List<int>();
			}
		}
		internal AssignmentCollection() {
		}

#region *** Properties ***	
		/// <summary>
		/// The number of <see cref="EntryPoint"/>s in this AssignmentCollection.
		/// </summary>
		public int Count {
			get {return this.size;}
			internal set {this.size=value;}
		}
		/// <summary>
		/// The EntryPoints referred to
		/// </summary>
		public EntryPoint[] EntryPoints {
			get {
				return this.keys;
			}		
			internal set {
				this.keys=value;
				//this.size = keys.Length;
			}
		}
		/// <summary>
		/// The robots mapped to EntryPoints in this AssignmentCollection.
		/// </summary>
		public ICollection<int>[] Robots {
			get {
				return this.values;
			}	
			internal set {
				this.values=value;
			}
		}
#endregion *** Properties ***
#region *** Methods ***		
		/// <summary>
		/// Returns the robots in EntryPoint k
		/// </summary>
		/// <param name="k">
		/// A <see cref="EntryPoint"/>
		/// </param>
		/// <returns>
		/// A <see cref="ICollection<System.Int32>"/>
		/// </returns>
		public ICollection<int> GetRobots(EntryPoint k) {

			for (int i=0; i<this.size;i++) {
				if (this.keys[i] == k) return this.values[i];
			}
			return null;
		}
		/// <summary>
		/// Returns the robots in the EntryPoint identifyed by id.
		/// </summary>
		/// <param name="id">
		/// A <see cref="System.Int64"/>
		/// </param>
		/// <returns>
		/// A <see cref="ICollection<System.Int32>"/>
		/// </returns>
		public ICollection<int> GetRobotsById(long id) {
			for (int i=0; i<this.size;i++) {
				if (this.keys[i].Id == id) return this.values[i];
			}
			return null;
		}
		/// <summary>
		/// Removes all robots from the AssignmentCollection
		/// </summary>
		internal void Clear() {
			for(int i=0; i<this.size; i++) {
				this.Robots[i].Clear();
			}
		}
		/*public AssignmentCollection Clone() {
			EntryPoint[] neps = new EntryPoint[this.size];
			List<int>[] robs = new List<int>[this.size];
			for(int i=0; i<this.size;i++) {
				neps[i] = this.EntryPoints[i];
				robs[i] = new List<int>(this.Robots[i]);
			}			
			return new AssignmentCollection(neps,robs);
		}*/
#endregion *** Methods ***		
	}
}

