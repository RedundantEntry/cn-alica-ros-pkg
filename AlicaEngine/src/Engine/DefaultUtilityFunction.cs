//#define UFDEBUG
using System;
using System.Collections.Generic;

namespace Alica
{	
	/// <summary>
	/// A default implementation for a plan's utility function. The only occuring summand referrs to the task-role preferences.
	/// </summary>
	public class DefaultUtilityFunction : UtilityFunction
	{
		/// <summary>
		/// Basic ctor
		/// </summary>
		/// <param name="plan">
		/// The <see cref="Plan"/>, this utility function belongs to.
		/// </param>
		public DefaultUtilityFunction(Plan plan):base("DefaultUtility", null, 1.0, 0.0, plan)
		{	
		}
		
#region *** Methods ***
		/// <summary> Evaluates the utility function according to the priorities of the assigned 
		/// roles and according to the similarity, if an oldRP is given. </summary>
		/// <returns> The utility </returns>
		public override double Eval(RunningPlan newRP, RunningPlan oldRP) 
		{
			if (newRP.Assignment == null)
			{
				throw new Exception("DefUF: The Assignment of the RunningPlan is null!");
			}
			// Invalid Assignments have an Utility of -1 changed from 0 according to specs
			if (!newRP.Assignment.IsValid())
				return -1.0;
			UtilityInterval sumOfUI = new UtilityInterval(0.0, 0.0);
			double sumOfWeights = 0.0;

			// Sum up priority summand 
			UtilityInterval prioUI = this.GetPriorityResult(newRP.Assignment); 
			sumOfUI.Max += this.priorityWeight * prioUI.Max;
			sumOfUI.Min += this.priorityWeight * prioUI.Min;
			sumOfWeights += this.priorityWeight;
			
			if (oldRP != null)
			{				
				// Sum up similarity summand
				UtilityInterval simUI = this.GetSimilarity(newRP.Assignment, oldRP.Assignment);
				sumOfUI.Max += this.similarityWeight * simUI.Max;
				sumOfUI.Min += this.similarityWeight * simUI.Min;
			}
			
			// Normalize to 0..1
			if (sumOfWeights > 0.0)
			{
				sumOfUI.Max /= sumOfWeights;
				sumOfUI.Min /= sumOfWeights;
				// Min == Max because RP.Assignment must be an complete Assignment!
				
				if ((sumOfUI.Max - sumOfUI.Min) > DIFFERENCETHRESHOLD)
				{
					Console.Error.WriteLine("DefUF: The Min and Max utility differs more than " 
					                        + DIFFERENCETHRESHOLD + " for a complete Assignment!");
				}
				return sumOfUI.Max; 
			}
			
			return 0.0;
		}
		
		/// <summary> Evaluates the utility function according to the priorities of the assigned 
		/// roles and according to the similarity, if an oldRP is given. </summary>
		/// <returns> The utility interval </returns>
		public override UtilityInterval Eval(IAssignment newAss, IAssignment oldAss) 
		{
			UtilityInterval sumOfUI = new UtilityInterval(0.0, 0.0);
			double sumOfWeights = 0.0;

			// Sum up priority summand
			UtilityInterval prioUI = this.GetPriorityResult(newAss); 
			sumOfUI.Max += this.priorityWeight * prioUI.Max;
			sumOfUI.Min += this.priorityWeight * prioUI.Min;
			sumOfWeights += this.priorityWeight;
#if UFDEBUG	
			Console.WriteLine("DF: prioUI.Min = " + prioUI.Min );
			Console.WriteLine("DF: prioUI.Max = " + prioUI.Max );
			Console.WriteLine("DF: priorityWeight = " + priorityWeight );
#endif			
			if (oldAss != null)
			{				
				// Sum up similarity summand
				UtilityInterval simUI = this.GetSimilarity(newAss, oldAss);
				sumOfUI.Max += this.similarityWeight * simUI.Max;
				sumOfUI.Min += this.similarityWeight * simUI.Min;
			}
			
			// Normalize to 0..1
			if (sumOfWeights > 0.0)
			{
				sumOfUI.Max /= sumOfWeights;
				sumOfUI.Min /= sumOfWeights;
				return sumOfUI; 
			}
			
			return new UtilityInterval(0.0, 0.0);
		}
		
		public override string ToString()
		{
			return this.name + ": prioW: " + this.priorityWeight + " simW: " + this.similarityWeight + "\n";
		}
#endregion *** Methods ***
	}
}

