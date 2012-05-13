// State.cs created with MonoDevelop
// User: stefant at 14:09 06/12/2008
//

using System;
using System.Collections.Generic;



namespace Alica
{	
	/// <summary>
	/// A State is a plan element inhabitable by agents, which contains sub-plans, sub-plantypes, and behaviours.
	/// </summary>
	public class State : PlanElement
	{

		protected LinkedList<AbstractPlan> plans = new LinkedList<AbstractPlan>();
		
		
		protected List<Transition> inTransitions = new List<Transition>();
		protected List<Transition> outTransitions = new List<Transition>();
		
		protected List<Parametrisation> parametrisation = new List<Parametrisation>();
		
		protected Plan inPlan = null;
		
		/// <summary>
		/// Basic Ctor
		/// </summary>
		public State()
		{
			this.IsTerminal = false;
			this.IsSuccessState = false;
			this.IsFailureState = false;			
		}
		
		/// <summary>
		/// Constructor which accepts a unique id.
		/// </summary>
		/// <param name="id">
		/// A <see cref="System.Int64"/>
		/// </param>
		public State(long id) : this()
		{
			this.Id = id;
		}
		/// <summary>
		/// The list of <see cref="AbstractPlan"/>s ment to be executed in the context of this state.
		/// </summary>
		public LinkedList<AbstractPlan> Plans
		{
			set { this.plans = value; }
			get { return this.plans; }
		}
		
		/// <summary>
		/// The list of <see cref="Parametrisation"/>s, which bind variables of sub-plans to variables in this state's plan.
		/// </summary>
		public List<Parametrisation> Parametrisation
		{
			set { this.parametrisation = value; }
			get { return this.parametrisation; }
		}
		/// <summary>
		/// The list of <see cref="Transition"/>s leading to this state.
		/// </summary>
		public List<Transition> InTransitions
		{
			set { this.inTransitions = value; }
			get { return this.inTransitions; }
		}
		/// <summary>
		/// The list of <see cref="Transition"/>s going from this stae to another one.
		/// </summary>
		public List<Transition> OutTransitions
		{
			set { this.outTransitions = value; }
			get { return this.outTransitions; }
		}
		/// <summary>
		/// whether or not this is a terminal state.
		/// </summary>
		public bool IsTerminal{get; set;}
		/// <summary>
		/// whether or not this is a <see cref="FailurePoint"/>, used to avoid casting and type checking during runtime.
		/// </summary>
		public bool IsFailureState{get; set;}
		/// <summary>
		/// whether or not this is a <see cref="SuccessPoint"/>, used to avoid casting and type checking during runtime.
		/// </summary>
		public bool IsSuccessState{get; set;}
		
		/// <summary>
		/// The plan containing this state.
		/// </summary>
		public Plan InPlan
		{
			set { this.inPlan = value; }
			get { return this.inPlan; }
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#State: " + this.Name + " "  + this.Id +"\n";
			
			ret += "\tInTranstions: "+this.InTransitions.Count +"\n";
			if(this.InTransitions.Count != 0)
			{				
				foreach (Transition ep in this.InTransitions)
				{
					//ret += ep;
					ret += "\t" + ep.Id + " " + ep.Name + "\n";
				}
			}
			ret += "\n";
			ret += "\tOutTranstions: "+this.OutTransitions.Count +"\n";
			if(this.OutTransitions.Count != 0)
			{				
				foreach (Transition ep in this.OutTransitions)
				{
					//ret += ep;
					ret += "\t" + ep.Id + " " + ep.Name + "\n";
				}
			}
			ret += "\n";
			
			
			ret += "\tPlans: "+this.Plans.Count+"\n";
			if(this.Plans.Count != 0)
			{				
				foreach (AbstractPlan ap in this.Plans)
				{
					//ret += ap;
					if(ap != null)
						ret += "\t" + ap.Id + " " + ap.Name + "\n";
				}
			}
			ret += "\n";
			ret += "#EndState\n";
			
			return ret;
		}
		
	}
}
