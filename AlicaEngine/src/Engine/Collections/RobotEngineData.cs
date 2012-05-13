
using System;
using System.Collections.Generic;


namespace Alica
{

	/// <summary>
	/// Basic Runtime information relating to a robot within the team
	/// </summary>
	public class RobotEngineData {	
		protected Dictionary<string,Variable> sortedVariables;
		/// <summary>
		/// Basic constructor
		/// </summary>
		/// <param name="r">
		/// This robot's <see cref="RobotProperties"/>
		/// </param>		
		public RobotEngineData (RobotProperties r)
		{
			this.IsActive = false;
			this.LastMessageTime = 0;
			this.Properties = r;
			this.SuccessMarks = new SuccessMarks();
			this.sortedVariables = new Dictionary<string,Variable>();
			this.InitSortedTerms();
			
		}
		
		/// <summary>
		/// Whether or not the robot is considered active
		/// </summary>
		public bool IsActive {get; set;}
		
		/// <summary>
		/// The timestamp of the last message event from this robot
		/// </summary>
		public ulong LastMessageTime{get; set;}
		
		/// <summary>
		/// The robot's <see cref="RobotProperties"/>
		/// </summary>
		public RobotProperties Properties{get; set;}
		
		/// <summary>
		/// The <see cref="SuccessMarks"/> of the robot, indicating which <see cref="EntryPoint"/>s it completed.
		/// </summary>
		public SuccessMarks SuccessMarks{get; set;}
		
		public Role LastRole{get; set;}
		
		public virtual void InitSortedTerms() {
			Dictionary<long,Quantifier> qs = AlicaEngine.Get().PR.Quantifiers;
			foreach(Quantifier q in qs.Values) {
				if (q is ForallAgents) {
					foreach(string s in q.DomainIdentifiers) {
						if(!this.sortedVariables.ContainsKey(s)) {
							Variable v = new Variable(MakeUniqueId(s),this.Properties.Name+"."+s,String.Empty);
							this.sortedVariables.Add(s,v);
						}
					}
				}
			}
		}
		
		public virtual Variable GetSortedVariable(string sort) {
			Variable ret = null;
			this.sortedVariables.TryGetValue(sort,out ret);
			return ret;
		}
		protected long MakeUniqueId(string s) {
			long ret = ((long)this.Properties.Id) <<32;
			ret +=(uint) s.GetHashCode();
			if(AlicaEngine.Get().PP.GetParsedElements().ContainsKey(ret)) {
				AlicaEngine.Get().Abort(String.Format("TO: Hash Collision in generating unique ID: {0}",ret));
			}
			//Console.WriteLine("Generated ID {0} ",ret);
			return ret;
		}
	}
}
