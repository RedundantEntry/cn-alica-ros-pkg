
using System;

using System.Collections.Generic;

namespace Alica
{	

	/// <summary>
	/// The PlanRepository holds the ALICA program, neatly seperated into different Dictionaries.
	/// It is especially useful to map element ids back to their object, e.g., when receiving messages referring to plan elements.
	/// </summary>
	public class PlanRepository
	{

		
		/// <summary>
		/// Default constructor
		/// </summary>
		public PlanRepository ()
		{
			this.Plans = new Dictionary<long, Plan>();
			this.Tasks = new Dictionary<long, Task>();
			this.Behaviours = new Dictionary<long, Behaviour>();
			this.PlanTypes = new Dictionary<long, PlanType>();
			this.Roles = new Dictionary<long, Role>();
			this.Characteristics = new Dictionary<long, Characteristic>();
			this.Capabilities = new Dictionary<long, Capability>();
			this.States = new Dictionary<long, State>();
			this.EntryPoints = new Dictionary<long, EntryPoint>();
			this.Transitions = new Dictionary<long, Transition>();
			this.SyncTransitions = new Dictionary<long, SyncTransition>();
			this.Quantifiers = new Dictionary<long,Quantifier>();
			this.Variables = new Dictionary<long,Variable>();
		}
		
		public Dictionary<long,Plan> Plans {get; private set;}
		public Dictionary<long,Task> Tasks {get; private set;}
		public Dictionary<long,Behaviour> Behaviours {get; private set;}
		public Dictionary<long,PlanType> PlanTypes {get; private set;}
		public Dictionary<long,Role> Roles {get; private set;}
		public Dictionary<long,Characteristic> Characteristics {get; private set;}
		public Dictionary<long,Capability> Capabilities {get; private set;}
		public Dictionary<long,State> States {get; private set;}
		public Dictionary<long,EntryPoint> EntryPoints {get; private set;}
		public Dictionary<long,Transition> Transitions {get; private set;}
		public Dictionary<long,SyncTransition> SyncTransitions {get; private set;}
		public Dictionary<long,Quantifier> Quantifiers {get; private set;}
		public Dictionary<long,Variable> Variables {get; private set;}
		
		public string TaskRepositoryFile {get; set;}
		
	}
}
