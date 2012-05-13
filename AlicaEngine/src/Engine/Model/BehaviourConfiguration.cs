// BehaviourConfiguration.cs created with MonoDevelop
// User: stefant at 13:26 09/01/2008
//

using System;
using System.Collections.Generic;



namespace Alica
{
	/// <summary>
	/// A Behaviour Configuration encapsulates a set of static parameters and a set of variables for a Behaviour.
	/// </summary>
	public class BehaviourConfiguration : AbstractPlan
	{
		/// <summary>
		/// Basic ctor
		/// </summary>		
		public BehaviourConfiguration()
		{
			this.EventDriven = false;
			this.Frequency = 30;
			this.Deferring = 0;
			this.Parameters = new Dictionary<string, string>();
		}
		/// <summary>
		/// Constructor with id
		/// </summary>
		/// <param name="id">
		/// A <see cref="System.Int64"/>
		/// </param>
		public BehaviourConfiguration(long id) : this()
		{
			this.Id = id;
		}
		/// <summary>
		/// This configuration's Behaviour
		/// </summary>
		public Behaviour Behaviour {get; set;}
		
		/// <summary>
		/// The frequency with which this behaviour is called in case it is not <see cref="EventDriven"/>.
		/// </summary>
		public int Frequency {get; set;}
		
		/// <summary>
		/// The time in ms to wait before this behaviour is executed for the first time after entering the corresponding state.
		/// Has only effect for behaviours not running in EventDriven mode.
		/// </summary>
		public int Deferring {get; set;}

		/// <summary>
		/// Specifies whether this behaviour is run eventDriven. If it is not event driven, a timer will call it according to <see cref="Frequency"/> and <see cref="Deferring"/>.
		/// </summary>
		public bool EventDriven { get; set;}
		
		/// <summary>
		/// The set of static parameters of this behaviour configuration. Usually parsed by <see cref="BasicBehaviour.InitializeParameters"/>.
		/// </summary>
		public Dictionary<string, string> Parameters {get; set;}

		public override string ToString ()
		{
			string ret = "";
			
			ret += "#BehaviourConfiguration: " + this.Name + " " + this.Id + "\n";
			
			ret += "\tBehaviour: ";
			if(this.Behaviour != null)
			{
			   ret += this.Behaviour.Name + " " +this.Behaviour.Id;
			}
			ret += "\n";		
					
			ret += "\tDeferring: " + this.Deferring + "\n";
			ret += "\tFrequency: " + this.Frequency + "\n";
			ret += "\tMasterPlan?: " +  this.MasterPlan + "\n";
			
			ret += "\tParameters: " + this.Parameters.Count + "\n";
			if(this.Parameters.Count != 0)
			{
				foreach (string s in this.Parameters.Keys)
				{
					string val = this.Parameters[s];
					
					ret += "\t" + s + " : " + val + "\n";
				}
			}
			ret += "\n";
			
			ret += "#EndBehaviourConfiguration\n";
			
			return ret;
		}

	}
}
