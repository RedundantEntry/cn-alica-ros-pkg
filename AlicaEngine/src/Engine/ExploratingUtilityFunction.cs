//#define UFDEBUG

using System;
using System.Collections.Generic;
using System.Collections;
using AutoDiff;

namespace Alica
{	
	public class ExploratingUtilityFunction : UtilityFunction
	{
		protected double variance=0;
		//Last "epsilon"
		protected double epsilon=0;
		
		public ExploratingUtilityFunction(string name, List<USummand> utilSummands, double priorityWeight, double similarityWeight, Plan plan, double variance) : base(name, utilSummands, priorityWeight, similarityWeight, plan)
		{
			this.variance = variance;
		}

		public override double Eval(RunningPlan newRP, RunningPlan oldRP) 
		{			
			return base.Eval(newRP, oldRP);//+WorldModel.Get().getNDRandomNumber(variance);
		}
		
		public override UtilityInterval Eval(IAssignment newAss, IAssignment oldAss)
		{
			UtilityInterval ui = base.Eval(newAss, oldAss);
			//epsilon = WorldModel.Get().getNDRandomNumber(variance);
			ui.Max += epsilon;
			ui.Min += epsilon;
			return ui;
		}
		
		//TODO: Hab die mal umgeschrieben, Endy schau doch mal drüber ob das so noch alles stimmt. Gruß, Stopfer
		// Nochmal umgeschrieben, nochmal drübergucken (oneiric <-> natty compatibility). Hendrik.
		public override Tuple<double[], double> Differentiate (IAssignment newAss)
		{
			double sumOfWeights=0;
			double[] item1 = new double[0]; //bah!
			double item2 = 0;
			for(int i=0; i<this.utilSummands.Count; ++i) {
				Tuple<double[], double> diff = utilSummands[i].Differentiate(newAss);
				sumOfWeights += utilSummands[i].Weight;
				if(diff != null) {
					item2 += diff.Item2;
					
					//Take Summand Weight into account
					for(int j=0; j<diff.Item1.Length; j++) {
						diff.Item1[j] *= utilSummands[i].Weight;
					}
					
					//Put derivatives together
					double[] tmp = new double[item1.Length + diff.Item1.Length];
					item1.CopyTo(tmp, 0);
					diff.Item1.CopyTo(tmp, item1.Length);

					item1 = tmp;
				}
			}
			//Normalize Eval Result
			item2 /= sumOfWeights;
			//Finish Policy Gradient computation by (epsilon/var) and finaly normalize by sumofweights
			for(int i=0; i<item1.Length; i++) {
				item1[i] *= (epsilon/(variance*sumOfWeights));
			}
			return new Tuple<double[], double>(item1,item2);
		}
	}
}
