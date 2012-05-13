// ISyncModul.cs created with MonoDevelop
// User: stefant at 16:18 07/29/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{	
	/// <summary>
	/// Handles synchronisations between agents, that is tightly synchronised transitions.
	/// </summary>
	public interface ISyncModul
	{
		/// <summary>
		/// Starts this module.
		/// </summary>
		void Init();
		/// <summary>
		/// Regularly called by the <see cref="PlanBase"/>.
		/// </summary>
		void Tick();
		/// <summary>
		/// Closes the module for good.
		/// </summary>
		void Close();
		/// <summary>
		/// Called by the <see cref="RuleBook"/> to indicate that a synchronisation may happen.
		/// </summary>
		/// <param name="trans">
		/// The <see cref="Transition"/> belonging to a synchronised set.
		/// </param>
		/// <param name="holds">
		/// A <see cref="System.Boolean"/> indicating if the condition guarding trans holds.
		/// </param>
		void SetSynchronisation(Transition trans, bool holds);
		//void UnsetSynchronisation(State s);
		//void UnsetSynchronisation(Plan p);
		//void UnsetSynchronisation(Transition t);
		/// <summary>
		/// Indicates that a synchronised transition is to be followed now.
		/// If true is returned, the transition must be followed immediately, as the synchronisation is completed with the call of this method.
		/// </summary>
		/// <param name="trans">
		/// A <see cref="Transition"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		bool FollowSyncTransition(Transition trans);
	
	
		
	}
}
