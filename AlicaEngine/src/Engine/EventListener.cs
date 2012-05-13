
using System;
using System.Collections.Generic;
using RosCS;

namespace Alica
{

	/// <summary>
	/// Handles triggering of behaviours.
	/// </summary>
	public class EventListener
	{
		protected BasicBehaviour behaviour;
		protected bool running = false;
		protected Timer timer = null;
		public EventListener (BasicBehaviour beh)	{
			this.behaviour = beh;
						
			this.behaviour.SetTrigger(this.OnEvent);
			this.timer = new Timer(OnEvent);
		}
		/// <summary>
		/// Starts execution of the behaviour. Called by the <see cref="BasicBehaviour"/>.
		/// </summary>
		public void Start() {
			this.running = true;
			if(!((BehaviourConfiguration)this.behaviour.RunningPlan.Plan).EventDriven) {
				this.timer.SetActive(this.behaviour.DueTime, this.behaviour.Period);
				//this.timer.Change(this.behaviour.DueTime, this.behaviour.Period);				
			}
		}
		
		/// <summary>
		/// Stops execution of the behaviour. Called by the <see cref="BasicBehaviour"/>.
		/// </summary>
		public void Stop() {
			this.running = false;
			this.timer.Pause();
			//this.timer.Change(Timeout.Infinite, Timeout.Infinite);			
		}
		
		/// <summary>
		/// Callback called by an <see cref="EngineTrigger"/> event or a timer, triggering the behaviour if it is active.
		/// </summary>
		/// <param name="o">
		/// A <see cref="System.Object"/>
		/// </param>
		public void OnEvent(object o) {
			if(this.running) {
				//Console.WriteLine("Triggering behaviour {0}",this.behaviour.RunningPlan.Plan.Name);
				behaviour.MessageObj = o;
				behaviour.Signaler.Set();
			}
		}
		/// <summary>
		/// Terminate this listener permanently.
		/// </summary>
		public void Terminate() {
			this.running = false;
			this.timer.Stop();
		}
		/*
		public override bool Equals(object obj) {
			return this.e.Equals(obj);
		}
		public override int GetHashCode()
		{
			return this.e.GetHashCode();
		}*/


		
	}
}
