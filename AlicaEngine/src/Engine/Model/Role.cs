using System;
using System.Collections.Generic;

namespace Alica
{
	public class Role : PlanElement
	{		
		//private Dictionary<string, Parameter> Characteristics;
		//like a parameter-object
		//protected Dictionary<string, string> Characteristics = new Dictionary<string,string>();
		
		protected RoleTaskMapping rtm = null;
		
		protected Dictionary<string,Characteristic> characteristics;
		
		// Dictionary<TaskID, Priority>
		//private Dictionary<long, double> taskPriorities = new Dictionary<long,double>();
		
		public Role()
		{
			this.characteristics = new Dictionary<string, Characteristic>();
		}
		
#region *** Properties ***
		
		//public Dictionary<string, Parameter> Characteristics
//		public Dictionary<string, string> Characteristics
//		{
//			get{ return this.Characteristics; }
//		}
		
		public RoleTaskMapping RoleTaskMapping
		{
			set{ this.rtm = value; }
			get{ return this.rtm; }
		}
		
		public Dictionary<string, Characteristic> Characteristics
		{			
			get 
			{ 			
				return this.characteristics; 
			}
		}
		/*
		public Dictionary<long, double> TaskPriorities
		{
			get{ return this.taskPriorities; }
		}
		*/
#endregion *** Properties ***	
		
#region *** Methods ***
		public double GetPriority(long taskId)
		{
			if (this.rtm.TaskPriorities.ContainsKey(taskId))
			{
				return this.rtm.TaskPriorities[taskId];
			}
			else
			{
				throw new Exception("ROLE DOES NOT HAVE A PRIORITY FOR TASK: " + taskId);
				//return 0.5;
			}
		}
		
		public override string ToString ()
		{
			string ret = "";
			
			ret += "#Role: " + this.Name + " " + this.Id + "\n";
			
//			ret += "\tCharacteristics: " + this.Characteristics.Count + "\n";
//			foreach (string s in this.Characteristics.Keys)
//			{
//				string val = this.Characteristics[s];
//					
//				ret += "\t" + s + " : " + val + "\n";
//			}			
//			ret += "\n";
			
			ret += "\tCharacteristics: " + this.Characteristics.Count + "\n";
			
			foreach (Characteristic c in this.Characteristics.Values)
			{
					
				ret += "\t" + c.Capability.Name + " : " + c.CapValue.Name + "\n";
			}			
			ret += "\n";
			
			ret += "\tRTM TaskPriorities ("+this.rtm.Id+"): " + this.rtm.TaskPriorities.Count + "\n";
			foreach (long l in this.rtm.TaskPriorities.Keys)
			{
				double val = this.rtm.TaskPriorities[l];
					
				ret += "\t" + l + " : " + val + "\n";
			}			
			ret += "\n";
			
			ret += "#EndRole\n\n";
			
			return ret;
		}
#endregion *** Methods ***
	}//end class Role

}
