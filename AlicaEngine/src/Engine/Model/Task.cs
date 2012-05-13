// Task.cs created with MonoDevelop
// User: stefan at 5:44 PM 7/4/2008
//

using System;

namespace Alica
{	
	/// <summary>
	/// an abstract description of parts of plans to be taken on by a set of robots
	/// </summary>
	public class Task : PlanElement
	{
		public const long IDLEID = -1; // For Task Id of an Idle EntryPoint...
		protected bool defaultTask;
		protected string description;
		
		
		/// <summary>
		/// Basic ctor
		/// </summary>
		public Task()
		{
		}
		/// <summary>
		/// Ctor
		/// </summary>
		/// <param name="defaultTask">
		/// A <see cref="System.Boolean"/>
		/// </param>
		public Task(bool defaultTask)
		{
			this.defaultTask = defaultTask;
		}
		
		public string Description
		{
			set { this.description = value; }
			get { return this.description; }
		}
		


		public override string ToString ()
		{
			string ret = "";
			
			ret += "#Task: " + this.Name + " " +this.Id +"\n";
			ret += "\tDescription: "+this.Description +"\n";
			ret += "#EndTask\n";
			
			return ret;
		}
		
	}
}
