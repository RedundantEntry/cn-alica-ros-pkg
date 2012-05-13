// SyncTransition.cs created with MonoDevelop
// User: stefant at 14:20 06/12/2008
//

using System;
using System.Collections.Generic;

namespace Alica
{	
	
	public class SyncTransition : PlanElement
	{
		protected ulong talkTimeOut = 30;
		protected ulong syncTimeOut = 3000;
		protected bool failOnSyncTimeOut = false;
		
		protected LinkedList<Transition> inSync = new LinkedList<Transition>();

		protected Plan plan = null;
		
		public SyncTransition()
		{
		}

		public Plan Plan
		{
			set { this.plan = value; }
			get { return this.plan; }
		}

		public ulong TalkTimeOut
		{
			set { this.talkTimeOut = value; }
			get { return this.talkTimeOut; }
		}

		public ulong SyncTimeOut
		{
			set { this.syncTimeOut = value; }
			get { return this.syncTimeOut; }
		}

		public bool FailOnSyncTimeOut
		{
			set { this.failOnSyncTimeOut = value; }
			get { return this.failOnSyncTimeOut; }
		}
		
		public LinkedList<Transition> InSync
		{
			set { this.inSync = value; }
			get { return this.inSync; }
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#SyncTransition: " + this.Name + " " + this.Id +"\n";

			if(this.Plan != null)
			{
				ret += "\tPlan: " + this.Plan.Id + " " + this.Plan.Name+"\n";
			}
			ret += "\n";

			ret += "\tTalkTimeOut: " + this.talkTimeOut + "\n";
			ret += "\tSyncTimeOut: " + this.syncTimeOut + "\n";
			ret += "\tFailOnSyncTimeOut: " + this.failOnSyncTimeOut + "\n";
						
			ret += "\tInSync: " +this.InSync.Count+ "\n";
			if(this.InSync.Count != 0)
			{				
				foreach (Transition t in this.InSync)
				{
					ret += "\t" + t.Id + " " + t.Name + "\n";
				}
			}
			ret += "\n";
			
			ret += "#EndSyncTransition\n";
			
			return ret;
		}
	}
}
