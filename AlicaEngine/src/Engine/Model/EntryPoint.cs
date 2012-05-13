// EntryPoint.cs created with MonoDevelop
// User: stefant at 14:14 06/12/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// An EntryPoint is used to identify the initial state of a task within a plan.
	/// It also holds cardinalities and any information specific to this (task,plan) tuple.
	/// </summary>
	public class EntryPoint : PlanElement, IComparable<EntryPoint>
	{
		/// <summary>
		/// A value encoding the do-nothing task used in loosely coupled task allocation.
		/// </summary>
		public const long IDLEID = -1; // For Idle EntryPoint...
		
		/// <summary>
		/// Basic ctor
		/// </summary>
		public EntryPoint()
		{
		}
		/// <summary>
		/// The task of this entrypoint.
		/// </summary>
		public Task Task { get; set;}
		/// <summary>
		/// The initial state of this entrypoint's task.
		/// </summary>
		public State State { get; set;}
		/// <summary>
		/// The plan to which this entrypoint belongs.
		/// </summary>
		public Plan InPlan {get; set;}
		/// <summary>
		/// The maximum amount of agents allowed to execute this entrypoint's task within <see cref="InPlan"/>.
		/// </summary>
		public int MaxCardinality { get; set;}
		/// <summary>
		/// The minimum amount of agents required to execute this entrypoint's task within <see cref="InPlan"/>.
		/// </summary>
		public int MinCardinality { get; set;}
		/// <summary>
		/// whether or not a success of this task is required for <see cref="InPlan"/> to be successful. Otherwise, this task is optional.
		/// </summary>
		public bool SuccessRequired{ get; set;}
		/// <summary>
		/// The set of states reachable from the initial state.
		/// </summary>
		public HashSet<State> ReachableStates {get; private set;}
		
		public void ComputeReachabilitySet() {
			this.ReachableStates = new HashSet<State>();
			LinkedList<State> queue = new LinkedList<State>();			
			queue.AddFirst(this.State);
			while(queue.Count>0) {
				State cs = queue.First.Value;				
				queue.RemoveFirst();
				if (ReachableStates.Add(cs)) { //not yet visited					
					foreach(Transition t in cs.OutTransitions) {
						queue.AddLast(t.OutState);
					}
				}
				
			}			
		}

		public override string ToString ()
		{
			string ret = "";
			
			ret += "#EntryPoint: " + this.Name + " " +this.Id +"\n";
			ret += "\tMinCardinality: "+this.MinCardinality+"\n";
			ret += "\tMaxCardinality: "+this.MaxCardinality+"\n";
			
			
			
			ret += "\tTask:\n";
			if(this.Task != null)
				ret += "\t" + this.Task.Id + " " + this.Task.Name + "\n";
			else ret += "null";
			ret += "\n";
			ret += "\tInitial State:\n";
			if(this.State != null)
				ret += "\t" + this.State.Id + " " + this.State.Name + "\n";
			else ret += "null";

			
			ret += "#EndEntryPoint\n";
			
			return ret;
			
		}
		
		/// <summary> Little helper to sort an entrypoint array. </summary>
		public int CompareTo(EntryPoint otherEp) {
			if (this.Task.Id<otherEp.Task.Id) return 1;
			else if (this.Task.Id==otherEp.Task.Id) return 0;
			else return -1;
		}		
	}
}
