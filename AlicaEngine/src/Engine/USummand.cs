using System;
using System.Collections.Generic;
using AutoDiff;

namespace Alica
{
	/// <summary>
	/// Abstract super class for domain dependent utility summands.
	/// </summary>
	public abstract class USummand
	{
#region *** Construction Data ***
		protected UtilityInterval ui = new UtilityInterval(0.0,0.0);
		
		protected long[] relevantEntryPointIds;
		protected double weight;
		protected string name;
		protected long id;
		protected string info;
#endregion *** Construction Data ***
		
#region *** Init Data ***		
		protected EntryPoint[] relevantEntryPoints;
#endregion *** Init Data ***
		
		/// <summary>
		/// Searches every needed entrypoint in the hashtable of the xmlparser 
		/// and stores it in the relevant entrypoint array. This will increase the 
		/// performance of the evaluation of this utility summand. 
		/// </summary>
		public virtual void Init() {
			// init relevant entrypoint array
			this.relevantEntryPoints = new EntryPoint[this.relevantEntryPointIds.Length];
			
			// find the right entrypoint for each id in relevant entrypoint id
			//Dictionary<long,PlanElement> elements = AlicaEngine.Get().PP.GetParsedElements();
			
			Dictionary<long,EntryPoint> elements = AlicaEngine.Get().PR.EntryPoints;
			EntryPoint curEp;
			for(int i = 0; i < this.relevantEntryPoints.Length; ++i)
			{
				if(!elements.TryGetValue(this.relevantEntryPointIds[i],out curEp)) {
					throw new Exception(String.Format("Could not find Entrypoint {0} Hint is: {1}",relevantEntryPointIds[i],this.name));
				}
				//curEp = elements[this.relevantEntryPointIds[i]];
				if (curEp != null)
				{
					this.relevantEntryPoints[i] = curEp;
				}
			}
		}
		
		public override string ToString ()
		{
			string retString = this.name + ": Weight " + this.weight + " EntryPoints: ";
			for(int i = 0; i < this.relevantEntryPointIds.Length; ++i)
			{
				retString += this.relevantEntryPointIds[i] + " ";
			}
			return retString;
		}
		
		/// <summary> Evaluates the utilityfunction summand </summary>
		/// <returns> The result of the evaluation </returns>
		public abstract UtilityInterval Eval(IAssignment ass);
	
		public virtual Tuple<double[], double> Differentiate(IAssignment newAss) { return null; }
	
		/// <summary> Cache every data for the current evaluation, to 
		/// assure consistancy over the complete current evaluation. </summary>
		public abstract void CacheEvalData();
		
		/// <value> Weight of this UtilitySummand </value>
		public double Weight
		{
			get{ return this.weight; }
			set{ this.weight = value; }
		}		
	}
}
