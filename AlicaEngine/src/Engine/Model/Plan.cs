// Plan.cs created with MonoDevelop
// User: stefant at 13:54 06/12/2008
//

using System;
using System.Collections.Generic;



namespace Alica
{
	/// <summary>
	/// An ALICA plan
	/// </summary>
	public class Plan : AbstractPlan
	{
			
		protected int minCardinality = 0;
		protected int maxCardinality = 0;
		
		string fileName;
		public override string FileName {
			get {
				if (string.IsNullOrEmpty(this.fileName)) return this.Name+".pml";
				else return this.fileName;
			}
			set {
				this.fileName = value;
			}
				
		}
				
		public Plan() :base()
		{
			EntryPoints = new Dictionary<long, EntryPoint>();
			States = new LinkedList<State>();
			FailurePoints = new LinkedList<FailurePoint>();
			SuccessPoints = new LinkedList<SuccessPoint>();
			SyncTransitions = new LinkedList<SyncTransition>();
			Transitions = new LinkedList<Transition>();
		}

		
		public Plan(long id) : this()
		{
				
			this.Id = id;
		}

		public Dictionary<long, EntryPoint> EntryPoints {get; set;}
		public LinkedList<State> States {get; set;}
		public LinkedList<FailurePoint> FailurePoints { get; set;}
		public LinkedList<SuccessPoint> SuccessPoints { get; set;}		
		public LinkedList<SyncTransition> SyncTransitions {get; set;}

		
		public LinkedList<Transition> Transitions {get; set;}
		
		public Result Result {get; set;}	
		
		
		public int MinCardinality {get; set;}
		
		public int MaxCardinality {get; set;}
				
		
		public EntryPoint GetEntryPointByTaskID(long taskID)
		{
			foreach(EntryPoint ep in this.EntryPoints.Values)
			{
				if (ep.Task != null)
				{
					if(ep.Task.Id == taskID)
					{
						return ep;
					}
				}
				else
				{
					throw new Exception("Model: Class Plan: Entrypoint with ID" + ep.Id + " does not have a Task");
				}
			}
			return null;
		}
		
		public override string ToString ()
		{
			string ret = "";
			ret += "#Plan: " + this.Name + " "  + this.Id + "\n";
			
			//if(this.inState != null)
			//	ret += "\tinState: " +  this.inState.Id + "\n";			
			ret += "\tMasterPlan?: " +  this.MasterPlan + "\n";
			ret += "\tUtilityThreshold: " +  this.UtilityThreshold + "\n";		
			
			ret += "\tEntryPoints: " + this.EntryPoints.Count + "\n";
			if(this.EntryPoints.Count != 0)
			{				
				foreach (EntryPoint ep in this.EntryPoints.Values)
				{
					//ret += ep;
					ret += "\t" + ep.Id + " " + ep.Name + "\n";
				}
			}
			ret += "\n";
			
			ret += "\tFailurePoints: " +this.FailurePoints.Count+ "\n";
			if(this.FailurePoints.Count != 0)
			{				
				foreach (FailurePoint ep in this.FailurePoints)
				{
					//ret += ep;
					ret += "\t" + ep.Id + " " + ep.Name + "\n";
				}
			}
			ret += "\n";
			ret += "\tSuccessPoints: " +this.SuccessPoints.Count+ "\n";
			if(this.SuccessPoints.Count != 0)
			{				
				foreach (SuccessPoint ep in this.SuccessPoints)
				{
					//ret += ep;
					ret += "\t" + ep.Id + " " + ep.Name + "\n";
				}
			}
			ret += "\n";
			
			ret += "\tStates: " +this.States.Count+ "\n";
			if(this.States.Count != 0)
			{				
				foreach (State s in this.States)
				{
					//ret += s;
					ret += "\t" + s.Id + " " + s.Name + "\n";
				}
			}
			ret += "\n";
			
			ret += "\tTransitions: " +this.Transitions.Count+ "\n";
			if(this.Transitions.Count != 0)
			{				
				foreach (Transition t in this.Transitions)
				{
					//ret += ep;
					ret += "\t" + t.Id + " " + t.Name + "\n";
				}
			}
			ret += "\n";

			ret += "\tSyncTransitions: " +this.SyncTransitions.Count+ "\n";
			if(this.SyncTransitions.Count != 0)
			{				
				foreach (SyncTransition st in this.SyncTransitions)
				{
					//ret += ep;
					ret += "\t" + st.Id + " " + st.Name + "\n";
				}
			}
			ret += "\n";
			
			ret += "EndPlan\n";			
			
			return ret;
		}

	}
}
