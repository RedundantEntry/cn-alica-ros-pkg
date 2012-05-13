// IBehaviourPool.cs created with MonoDevelop
// User: stefant at 17:19 07/28/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// An engine trigger is a delegate used to call eventDriven <see cref="BasicBehaviour"/>s. Events of this type can be used by a BasicBehaviour to manage when it is called.
	/// </summary>
	public delegate void EngineTrigger(object arg);	

	/// <summary>
	/// An IBehaviourPool manages the actual behaviours controlling the agent's actuators.
	/// </summary>
	public interface IBehaviourPool
	{
		/// <summary>
		/// Indicates whether a class for a specific behaviour was succesfully loaded and is available.
		/// </summary>
		/// <param name="b">
		/// A <see cref="Behaviour"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>		
		bool IsBehaviourAvailable(Behaviour b);

		/// <summary>
		/// Add a behaviour represented by its RunningPlan to the set of currently active behaviour. Usually called by the <see cref="RunningPlan"/>.
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		void AddBehaviour(RunningPlan rp);

		/// <summary>
		/// Remove a behaviour represented by its RunningPlan from the set of currently active behaviour. Usually called by the <see cref="RunningPlan"/>.
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		void RemoveBehaviour(RunningPlan rp);
		
		/// <summary>
		/// Initialises this Engine Module
		/// </summary>
		void Init();
		
		/// <summary>
		/// Stops this engine module
		/// </summary>
		void Stop();
		
	}
}
