
using System;
using System.Collections.Generic;

namespace Alica
{	
	/// <summary>
	/// A simple helper class for conflict detection
	/// </summary>
	internal class EntryPointRobotPair {
		
		protected internal EntryPoint key;
		protected internal int val;
		internal EntryPoint Key {
			get { return this.key; }
			set { this.key=value; }			
		}
		internal int Value {
			get { return this.val; }
			set { this.val=value; }			
		}
		internal EntryPointRobotPair(EntryPoint ep, int r) {
			this.key = ep;
			this.val = r;
		}
		public override bool Equals (object obj)
		{
			EntryPointRobotPair o = obj as EntryPointRobotPair;
			if (o== null) return false;
			if (o.Key.Id != this.Key.Id) return false;
			return (o.Value == this.Value);			
		}
		public override int GetHashCode()	{
			return (int)key.Id + 10000*val;
		}


	}
	/// <summary>
	/// A representation of the difference between two allocations
	/// </summary>
	public class AllocationDifference
	{
		/// <summary>
		/// Denoting the reason for an allocation switch
		/// </summary>
		public enum Reason {
			message, utility, empty
		}
		internal List<EntryPointRobotPair> additions;
		internal List<EntryPointRobotPair> subtractions;
		internal AllocationDifference() {
			this.additions= new List<EntryPointRobotPair>();
			this.subtractions = new List<EntryPointRobotPair>();
		}
		public Reason reason;
		/// <summary>
		/// Returns whether the difference is empty, i.e., the corresponding allocations are the same
		/// </summary>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool IsEmpty() {
			return this.additions.Count==0 && this.subtractions.Count==0;
		}
		/// <summary>
		/// Reset this difference to the empty difference
		/// </summary>
		public void Reset() {
			this.additions.Clear();
			this.subtractions.Clear();
			this.reason = Reason.empty;
		}
		public override bool Equals(object obj)
		{
			AllocationDifference o = obj as AllocationDifference;
			if (o==null) return false;
			if (this.additions.Count!=o.additions.Count) return false;
			if (this.subtractions.Count!=o.subtractions.Count) return false;
			for(int i=0; i < this.additions.Count; i++) {
				if (this.additions[i].Key != o.additions[i].Key) return false;
				if (this.additions[i].Value != o.additions[i].Value) return false;				
			}
			for(int i=0; i < this.subtractions.Count; i++) {
				if (this.subtractions[i].Key != o.subtractions[i].Key) return false;
				if (this.subtractions[i].Value != o.subtractions[i].Value) return false;				
			}
			return false;
		}
		public override int GetHashCode()	{
			return base.GetHashCode();
		}
		/// <summary>
		/// Apply another difference to this one resulting in the composition of both
		/// </summary>
		/// <param name="other">
		/// A <see cref="AllocationDifference"/>
		/// </param>
		public void ApplyDifference(AllocationDifference other) {
			for (int i=0; i < other.additions.Count; i++) {
				if (this.subtractions.Contains(other.additions[i])) {
					this.subtractions.Remove(other.additions[i]);
				}
				else if (!this.additions.Contains(other.additions[i])) {
					this.additions.Add(other.additions[i]);	
				}				
			}
			for (int i=0; i < other.subtractions.Count; i++) {
				if (this.additions.Contains(other.subtractions[i])) {
					this.additions.Remove(other.subtractions[i]);
				}
				else if (!this.subtractions.Contains(other.subtractions[i])) {
					this.subtractions.Add(other.subtractions[i]);	
				}				
			}
	
		}
		public override string ToString ()
		{
			String ret ="";
			for (int i=0; i<this.additions.Count; i++) {
				ret+="+ "+this.additions[i].Value+" (" + this.additions[i].Key.Id+")";//Task.Name+")";
			}
			for (int i=0; i<this.subtractions.Count; i++) {
				ret+="- "+this.subtractions[i].Value+" (" + this.subtractions[i].Key.Id+")";//Task.Name+")";
			}
			
			return ret;
		}


	}
}
