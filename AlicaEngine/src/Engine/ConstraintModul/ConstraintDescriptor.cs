using System;
using System.Collections.Generic;
using AD=AutoDiff;

namespace Alica
{
	/// <summary>
	/// Holds all information relevant to a constraint imposed by a <see cref="Condition"/>. The information is typically provided by a <see cref="GetConstraint"/> delegate.
	/// 
	/// </summary>
	public class ConstraintDescriptor
	{
		int dim;
		const double min = -10E29;
		const double max = 10E29;
		
		double utilitySignificanceThreshold = 1E-22;
		public bool SetsUtilitySignificanceThreshold {get; private set;}
		public double UtilitySignificanceThreshold { 
			get {return this.utilitySignificanceThreshold;}
			set {
				this.utilitySignificanceThreshold = value;
				this.SetsUtilitySignificanceThreshold=true;
			}
		}
		Dictionary<AD.Term,object> fixedValues;
		/// <summary>
		/// Constructor. Typically only called internally. Exposed for test implementations.
		/// </summary>
		/// <param name="vars">
		/// A <see cref="AD.Variable[]"/>, the solver variables representing the static variables of this constraint.
		/// </param>
		/// <param name="domVars">
		/// A <see cref="List<List<AutoDiff.Term[]>>"/>, the solver variables presenting the dynamic domain variables of this constraint.
		/// </param>
		public ConstraintDescriptor (AD.Variable[] vars,List<List<AD.Term[]>> domVars)
		{
			this.dim = vars.Length;
			this.Constraint = ConstraintBuilder.True;
			this.Utility = 1;
			this.UtilitySufficiencyThreshold = Double.MaxValue;
			this.fixedValues = new Dictionary<AD.Term, object>();
			this.StaticRanges = new double[dim,2];
			this.AllVars = new List<AD.Term>();
			for(int i=0; i<dim; i++) {
				this.StaticRanges[i,0] = min;
				this.StaticRanges[i,1] = max;
				this.AllVars.Add(vars[i]);
			}
			this.StaticVars = vars;
			this.DomainVars = domVars;
			this.DomainRanges = new List<List<double[,]>>();
			
			
			foreach(List<AD.Term[]> lat in domVars) {
				List<double[,]> l = new List<double[,]>();
				this.DomainRanges.Add(l);
				foreach(AD.Term[] tarr in lat) {
					double[,] r = new double[tarr.Length,2];
					l.Add(r);
					for(int i=0; i<tarr.Length; i++) {
						dim++;
						r[i,0] = min;
						r[i,1] = max;
						this.AllVars.Add(tarr[i]);
					}
				}
			}
			this.SetsUtilitySignificanceThreshold = false;
			
		}
		/// <summary>
		/// Set an arbitrary fixed value for a varable, i.e, ground a variable.
		/// </summary>
		/// <param name="variable">
		/// A <see cref="AutoDiff.Term"/>, the variable the object is bound to.
		/// </param>
		/// <param name="val">
		/// A <see cref="System.Object"/>, the object bound to the variable.
		/// </param>
		public void SetFixedValue(AD.Term variable,object val) {
			if(this.fixedValues.ContainsKey(variable)) {
				this.fixedValues[variable] = val;
			} else {
				this.fixedValues.Add(variable,val);
			}
		}
		/// <summary>
		/// The fixed value of a variable. Returns null if the value is not fixed (nonground variable).
		/// </summary>
		/// <param name="variable">
		/// A <see cref="AutoDiff.Term"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Object"/>
		/// </returns>
		public object FixedValue(AD.Term variable) {
			object ret = null;
			this.fixedValues.TryGetValue(variable,out ret);
			return ret;
		}
		
		/// <summary>
		/// The Constraint function
		/// </summary>
		public AD.Term Constraint {get; set;}
		/// <summary>
		/// The objective function.
		/// </summary>
		public AD.Term Utility {get; set;}
		/// <summary>
		/// A value above which the utility is deemed to be sufficient, i.e., close enough to the global optimum.
		/// A solver may stop looking for a solution if this value has been reached.
		/// </summary>
		public double UtilitySufficiencyThreshold {get; set;}
		/// <summary>
		/// The static variables used in the constraint and the utility
		/// </summary>
		public AD.Term[] StaticVars {get;private set;}
		/// <summary>
		/// The current instance of the domain quantifiers, each quantifier results in a list of arrays of Variables (or potentially terms).
		/// </summary>
		public List<List<AD.Term[]>> DomainVars {get;private set;}
		public List<ICollection<int>> AgentsInScope {get; internal set;}
		/// <summary>
		/// All variables, both static and dynamic
		/// </summary>
		public List<AD.Term> AllVars {get; private set;}
		/// <summary>
		/// Returns the ranges of all variables involved. Used by <see cref="Alica.Reasoner.CGSolver"/> to initialiase a constraint problem for solving.
		/// </summary>
		/// <returns>
		/// A 2-dimensional double array.
		/// </returns>
		public double[,] AllRanges(){		
			double[,] allranges = new double[this.dim,2];
			int i= this.StaticRanges.GetLength(0);
			/*for(; i<this.StaticRanges.GetLength(0); i++) {
				allranges[i,0] = this.StaticRanges[i,0];
				allranges[i,1] = this.StaticRanges[i,1];					
			}*/
			Buffer.BlockCopy(this.StaticRanges,0,allranges,0,sizeof(double)*this.StaticRanges.Length);
			foreach(List<double[,]> ld in this.DomainRanges) {
				foreach(double[,] darr in ld) {
					/*for(int k=0; k<darr.GetLength(0); k++) {
						allranges[i,0] = darr[k,0];
						allranges[i,1] = darr[k,1];
						i++;
					}*/
					Buffer.BlockCopy(darr,0,allranges,(2*i)*sizeof(double),sizeof(double)*darr.Length);
					i+=darr.GetLength(0);
				}
			}
			return allranges;			
		}
		/// <summary>
		/// The ranges, i.e., lower and upper bounds for all dynamic domain variables.
		/// </summary>
		public List<List<double[,]>> DomainRanges {get; set;}
		/// <summary>
		/// The ranges, i.e., lower and upper bounds for all static domain variables.
		/// </summary>
		public double[,] StaticRanges {get; set;}
		
	}
}

