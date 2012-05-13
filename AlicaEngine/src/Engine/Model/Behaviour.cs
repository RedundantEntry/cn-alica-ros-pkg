// Behaviour.cs created with MonoDevelop
// User: stefant at 14:12 06/12/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// Represents a behaviour within the plan tree
	/// </summary>
	public class Behaviour : PlanElement
	{
		
		
		public Behaviour()
		{
			this.Configurations = new LinkedList<BehaviourConfiguration>();
		}

		
		public Behaviour(string name) :this()
		{						
			this.Name = Name;
		}
		string fileName;
		public string FileName {
			get {
				if (string.IsNullOrEmpty(this.fileName)) return this.Name+".beh";
				else return this.fileName;
			}
			set {
				this.fileName = value;
			}
				
		}
		/// <summary>
		/// The set of static configurations of this Behaviour
		/// </summary>
		public LinkedList<BehaviourConfiguration> Configurations{ get; set; }
		
		/// <summary>
		/// The actual implementation of this behaviour, a subclass of BasicBehaviour
		/// </summary>
		public BasicBehaviour Implementation {get; set;}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#Behaviour: " + this.Name + " " + this.Id + "\n";
			ret += "\tConfigurations: " + this.Configurations.Count + "\n";
			
			foreach (BehaviourConfiguration bc in Configurations) {
				ret += "\t" + bc.Name + " " + bc.Id + "\n";
			}

			ret += "#EndBehaviour\n";			
			
			return ret;
		}
	}
}
