

using System;
using System.Runtime.InteropServices;

using System.Collections.Generic;
using System.Collections;

using Castor;
using Alica;
using AD = AutoDiff;


namespace Alica.Reasoner {	
	/// <summary>
	/// The Alica Constraint Solver. 
	/// </summary>
	public class CGSolver : IConstraintSolver {
		
		ResultStore resultCache;
		
		GSolver gs;
		GSolver sgs;
		
		public double LastUtil {get; private set;}
		public double LastRuns {get; private set;}
		public double LastFEvals {get; private set;}
		public CGSolver() {
			AD.Term.SetAnd(AD.Term.AndType.and);
			AD.Term.SetOr(AD.Term.OrType.max);
			gs = new GSolver();
			sgs = new GSolver();
			resultCache = ResultStore.Get();
			resultCache.Init();			
		}
		
					
			
		/// <summary>
		/// Query for the existence of a solution, usually called by a <see cref="ConstraintQuery"/>.
		/// </summary>
		/// <param name="vars">
		/// The <see cref="List<Variable>"/> holding the variables in question.
		/// </param>
		/// <param name="calls">
		/// The <see cref="List<ConstraintDescriptor>"/> describing the problem.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool ExistsSolution(List<Variable> vars,List<ConstraintDescriptor> calls) {
			
			AD.Term constraint = ConstraintBuilder.True;			
			int dim = vars.Count;
			
			AD.Variable[] cVars = new AD.Variable[dim];
			double[,] ranges = new double[dim,2];
			for(int i=0; i<vars.Count; i++) {
				ranges[i,0] = Double.MinValue;
				ranges[i,1] = Double.MaxValue;
				cVars[i] = vars[i].SolverVar;				
			}
			
			object[] fixedValues = new object[dim];
			
			for(int i=0; i<calls.Count; i++) {
				for(int j=0; j<dim; j++) {
					object n = calls[i].FixedValue(vars[j].SolverVar);
					if(n!=null && fixedValues[j]!=null) {
						if (!n.Equals(fixedValues[j])) return false;
					}
					fixedValues[j] = n;
				}
			}
			for(int i=0; i<dim; i++) {
				if (fixedValues[i]!=null && fixedValues[i] is IConvertible) {
					try {
						double val =  Convert.ToDouble(fixedValues[i]);
						ranges[i,0] = val;
						ranges[i,1] = val;
					} catch {
					}
				}
			}
			
			foreach(ConstraintDescriptor c in calls) {
				constraint &= c.Constraint;	
				double[,] allRanges = c.AllRanges();
				for(int i=0; i<c.AllVars.Count; i++) {
					for(int j=0; j<cVars.Length;j++) {
						if(cVars[j] == c.AllVars[i]) {
							ranges[j,0] = Math.Max(ranges[j,0],allRanges[i,0]);
							ranges[j,1] = Math.Min(ranges[j,1],allRanges[i,1]);
							if(ranges[j,0] > ranges[j,1]) return false;
							break;
						}
					}
				}				
			}
			double[][] seeds = resultCache.GetSeeds(vars,ranges);
			
			AD.Term all = constraint;
			
			return sgs.SolveSimple(all,cVars,ranges,seeds);
			
		}
		/// <summary>
		/// Query for a solution usually called by a <see cref="ConstraintQuery"/>.
		/// </summary>
		/// <param name="vars">
		/// A <see cref="List<Variable>"/>
		/// </param>
		/// <param name="calls">
		/// A <see cref="List<ConstraintDescriptor>"/>
		/// </param>
		/// <param name="results">
		/// A <see cref="System.Double[]"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool GetSolution(List<Variable> vars,List<ConstraintDescriptor> calls, out double[] results) {


			AD.Term constraint = ConstraintBuilder.True;
			AD.Term utility = 1;
			results = null;
			int dim = vars.Count;
			
			object[] fixedValues = new object[dim];
			
			AD.Variable[] cVars = new AD.Variable[dim];
			double[,] ranges = new double[dim,2];
			for(int i=0; i<dim; i++) {
				ranges[i,0] = Double.MinValue;
				ranges[i,1] = Double.MaxValue;
				cVars[i] = vars[i].SolverVar;				
			}
			double usigVal = calls[0].UtilitySignificanceThreshold;
			for(int i=0; i<calls.Count; i++) {
				for(int j=0; j<dim; j++) {
					object n = calls[i].FixedValue(vars[j].SolverVar);
					if(n!=null && fixedValues[j]!=null) {
						if (!n.Equals(fixedValues[j])) return false;
					}
					fixedValues[j] = n;
				}
				if (calls[i].SetsUtilitySignificanceThreshold) usigVal = calls[i].UtilitySignificanceThreshold;
			}
			for(int i=0; i<dim; i++) {
				if (fixedValues[i]!=null && fixedValues[i] is IConvertible) {
					try {
						double val =  Convert.ToDouble(fixedValues[i]);
						ranges[i,0] = val;
						ranges[i,1] = val;
					} catch {
					}
				}
			}
			double sufficientUtility = 0;
			
			foreach(ConstraintDescriptor c in calls) {
				constraint &= c.Constraint;
				utility += c.Utility;
				sufficientUtility += c.UtilitySufficiencyThreshold;
				double[,] allRanges = c.AllRanges();

				for(int i=0; i<c.AllVars.Count; i++) {
					for(int j=0; j<cVars.Length;j++) {
						if(cVars[j] == c.AllVars[i]) {
							ranges[j,0] = Math.Max(ranges[j,0],allRanges[i,0]);
							ranges[j,1] = Math.Min(ranges[j,1],allRanges[i,1]);
							if(ranges[j,0] > ranges[j,1]) {								
								return false;
							}
							break;
						}
					}
				}				
			}			
			AD.Term all = new AutoDiff.ConstraintUtility(constraint,utility);
			
			double[][] seeds = resultCache.GetSeeds(vars,ranges);
			
			/*
			Console.WriteLine("Problem is: ");
			for(int i=0; i<vars.Count; i++) {
				Console.WriteLine("{0} [{1} - {2}]",vars[i].Name,ranges[i,0],ranges[i,1]);
			}*/
/*
Console.WriteLine("Usings seeds: ");
foreach(double[] d in seeds) {
	Console.Write("S: ");
	for(int i=0; i<d.Length; i++) {
		Console.Write("{0} ",d[i]);
	}
	Console.WriteLine();
}*/
			
			double util=0;
			lock(this) {	
				gs.utilitySignificanceThreshold = usigVal;
				results = gs.Solve(all,cVars,ranges,seeds,sufficientUtility, out util);				
			}			
			if(results != null) {
				for(int i=0; i<dim; i++) {
					resultCache.PostResult(vars[i].Id,results[i]);
				}
			}
			this.LastUtil = util;
			this.LastFEvals = gs.FEvals;
			this.LastRuns = gs.Runs;
			return (util > 0.75);		
			
		}
		
		public bool GetSolution(List<Variable> vars,List<ConstraintDescriptor> calls, out object[] results) {
			
			AD.Term constraint = ConstraintBuilder.True;
			AD.Term utility = 1;
			results = null;
			int dim = vars.Count;
			
			object[] fixedValues = new object[dim];

			AD.Variable[] cVars = new AD.Variable[dim];
			double[,] ranges = new double[dim,2];
			for(int i=0; i<dim; i++) {
				ranges[i,0] = Double.MinValue;
				ranges[i,1] = Double.MaxValue;
				cVars[i] = vars[i].SolverVar;				
			}

			for(int i=0; i<calls.Count; i++) {
				for(int j=0; j<dim; j++) {
					object n = calls[i].FixedValue(vars[j].SolverVar);
					if(n!=null && fixedValues[j]!=null) {
						if (!n.Equals(fixedValues[j])) return false;
					}
					fixedValues[j] = n;
				}
			}
			for(int i=0; i<dim; i++) {
				if (fixedValues[i]!=null && fixedValues[i] is IConvertible) {
					try {
						double val =  Convert.ToDouble(fixedValues[i]);
						ranges[i,0] = val;
						ranges[i,1] = val;
					} catch {
					}
				}
			}
			double sufficientUtility = 0;
			
			foreach(ConstraintDescriptor c in calls) {
				constraint &= c.Constraint;
				utility += c.Utility;
				sufficientUtility += c.UtilitySufficiencyThreshold;
				double[,] allRanges = c.AllRanges();
				for(int i=0; i<c.AllVars.Count; i++) {
					for(int j=0; j<cVars.Length;j++) {
						if(cVars[j] == c.AllVars[i]) {
							ranges[j,0] = Math.Max(ranges[j,0],allRanges[i,0]);
							ranges[j,1] = Math.Min(ranges[j,1],allRanges[i,1]);
							if(ranges[j,0] > ranges[j,1]) return false;
							break;
						}
					}
				}				
			}
			
			AD.Term all = new AutoDiff.ConstraintUtility(constraint,utility);
			
			double[][] seeds = resultCache.GetSeeds(vars,ranges);
			
			/*
			Console.WriteLine("Problem is: ");
			for(int i=0; i<vars.Count; i++) {
				Console.WriteLine("{0} [{1} - {2}]",vars[i].Name,ranges[i,0],ranges[i,1]);
			}*/
/*
Console.WriteLine("Usings seeds: ");
foreach(double[] d in seeds) {
	Console.Write("S: ");
	for(int i=0; i<d.Length; i++) {
		Console.Write("{0} ",d[i]);
	}
	Console.WriteLine();
}
*/			
			double util=0;
			double[] res = null;
			lock(this) {				
				res = gs.Solve(all,cVars,ranges,seeds,sufficientUtility, out util);				
			}			
			if(res != null) {
				results = fixedValues;
				for(int i=0; i<dim; i++) {
					resultCache.PostResult(vars[i].Id,res[i]);
					if (results[i] == null) results[i] = res[i];
				}		
			}
			this.LastRuns = gs.Runs;
			this.LastFEvals = gs.FEvals;
			this.LastUtil = util;
			return (util > 0.75);		
			
		}		
	}
	
}
