//#define CSH_DEBUG
using System;
using System.Collections.Generic;
using System.Collections;
using AD=AutoDiff;

namespace Alica {

	
	/// <summary>
	/// The Constraint Helper offers methods to query for variables for valid values and test if solutions exists
	/// </summary>
	public static class ConstraintHelper
	{
		
		
		private static IConstraintSolver solver;
		
		/// <summary>
		/// Init the ConstraintHelper, called once during Initialisation
		/// </summary>
		/// <param name="thesolver">
		/// A <see cref="IConstraintSolver"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public static bool Init(IConstraintSolver thesolver) {
			solver = thesolver;
			return true;
		}
		
		/// <summary>
		/// Returns the constraint solver used
		/// </summary>
		public static IConstraintSolver Solver {
			get { return solver; }
		}
		
		
		
		/// <summary>
		/// Allows to test the existence of a solution. Depending on the solver used, this can be siginificantly faster than finiding one,
		/// especially if the constraint problem includes optimisation tasks.
		/// </summary>
		/// <param name="varids">
		/// A <see cref="List<System.Int64>"/>
		/// </param>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		/*public static bool ExistsSolution(List<long> varids,RunningPlan rp) {			
			
			#if (CSH_DEBUG)
			Console.WriteLine("CSH: Existential Query with {0} vars",varids.Count);				
			#endif			
							
			List<CVariable> varsToCheck = new List<CVariable>();
			//List<CVariable> varsToPost;
			foreach(long varid in varids) {
				CVariable cv = rp.Variables.GetVariable(varid);
				varsToCheck.Add(cv);				
			}
			
			//List<Parametrisation> relBindings = new List<Parametrisation>();
			UniqueVarStore store;
			List<ConstraintDescriptor> calls = BuildQuery(rp,varsToCheck,out store);
			
			
			#if (CSH_DEBUG)
				List<CVariable> allVars = store.GetAllRep();
				Console.WriteLine("Relevant Vars: {0}",allVars.Count);
				Console.WriteLine("Store Content:\n{0}",store.ToString());
			#endif
			if (calls.Count == 0) {
				Console.WriteLine("Empty Query!");			
				return true;	
			}
						
			return solver.ExistsSolution(store.GetAllRep(),calls);
			
			
		}*/
		
	}
}
