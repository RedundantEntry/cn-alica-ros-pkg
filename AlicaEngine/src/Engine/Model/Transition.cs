

using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// Connects two <see cref="State"/>s in a <see cref="Plan"/>
	/// </summary>
	public class Transition : PlanElement
	{
		
		private PreCondition preCondition = null;
		
		private State inState = null;
		private State outState = null;
		

		private SyncTransition syncTrans = null;
		/// <summary>
		/// Basic ctor
		/// </summary>
		public Transition()
		{
		}
		/// <summary>
		/// The SyncTransition this transition belongs to. Null if it does not belong to any.
		/// </summary>
		public SyncTransition SyncTransition
		{
			set { this.syncTrans = value; }
			get { return this.syncTrans; }
		}
		
		/// <summary>
		/// The condition guarding this transition.
		/// </summary>
		public PreCondition PreCondition
		{
			set { this.preCondition = value; }
			get { return this.preCondition; }
		}
		/// <summary>
		/// The state from which this transition leads away.
		/// </summary>
		public State InState
		{
			set { this.inState = value; }
			get { return this.inState; }
		}
		/// <summary>
		/// The state this transition leads to.
		/// </summary>
		public State OutState
		{
			set { this.outState = value; }
			get { return this.outState; }
		}	
		public bool EvalCondition(RunningPlan r) {
			try {
				return this.PreCondition.Eval(r);
			}
			catch(Exception e) {
				RosCS.Node.MainNode.RosError("exception in cond. of transition: "+e.ToString());
				return false;
			}
		}
		
	}
}
