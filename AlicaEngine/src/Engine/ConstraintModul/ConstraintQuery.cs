//#define CQ_DEBUG
using System;
using System.Collections.Generic;
using System.Collections;
using AD=AutoDiff;

namespace Alica {
	/// <summary>
	/// Encapsulates specific queries to constraint variable, usually used by behaviours.
	/// </summary>
	public class ConstraintQuery
	{
		/// <summary>
		/// Internal class to deal with bindings in states and plantypes
		/// </summary>
		class UniqueVarStore {
			List<LinkedList<Variable>> store;
				
			public UniqueVarStore() {
				store = new List<LinkedList<Variable>>();
			}
			public void Clear() {
				this.store.Clear();
			}
			public void Add(Variable v) {
				LinkedList<Variable> l = new LinkedList<Variable>();
				l.AddFirst(v);
				store.Add(l);
			}
			public Variable GetRep(Variable v) {
				foreach(LinkedList<Variable> l in store) {
					foreach(Variable s in l) {
						if (s == v) {
							return l.First.Value;
						}
					}
				}
				Add(v);			
				return v;
			}
			public void AddVarTo(Variable representing,Variable toAdd) {
				foreach(LinkedList<Variable> l in store) {
					foreach(Variable cv in l) {					
						if(representing == cv) {
							l.AddFirst(toAdd);
							return;
						}
					}
				}
				LinkedList<Variable> nl = new LinkedList<Variable>();
				nl.AddFirst(representing);
				nl.AddFirst(toAdd);
				store.Add(nl);
				
			}
			public List<Variable> GetAllRep() {
				List<Variable> ret = new List<Variable>();
				foreach(LinkedList<Variable> l in store) {
					ret.Add(l.First.Value);
				}
				return ret;
			}
			public int GetIndexOf(Variable v) {
				for(int i=0; i<store.Count; i++) {
					foreach(Variable c in store[i]) {
						if (c == v) return i;
					}
				}
				return -1;
			}
			public override string ToString ()
			{
				string ret = "";
				foreach(LinkedList<Variable> l in this.store) {
					ret +=l.First.Value.Id+": ";
					foreach(Variable cv in l) {
						ret+= cv.Id+" ";
					}
					ret+="\n";
				}
				return ret;
			}

		}
		
		private UniqueVarStore store;
		private List<Variable> queriedStaticVariables;
		private List<Variable> queriedDomainVariables;
		//private HashSet<string> involvedSorts;
		//private HashSet<int> involvedRobots;
		private ITeamObserver to;
		private List<ConstraintCall> calls;
		
		
		/// <summary>
		/// Default constructor
		/// </summary>
		public ConstraintQuery ()
		{
			this.store = new ConstraintQuery.UniqueVarStore();
			this.queriedStaticVariables = new List<Variable>();
			this.queriedDomainVariables = new List<Variable>();
			this.RelevantStaticVariables = new List<Variable>();
			this.RelevantDomainVariables = new List<Variable>();
			this.calls = new List<ConstraintCall>();
			this.to = AlicaEngine.Get().TO;
		}
		/// <summary>
		/// Add the static variable v to the query.
		/// </summary>
		/// <param name="v">
		/// A <see cref="Variable"/>
		/// </param>
		public void AddVariable(Variable v) {
			this.queriedStaticVariables.Add(v);
		}
		/// <summary>
		/// Add the robot specific variable identifie by ident to the query.
		/// </summary>
		/// <param name="robot">
		/// A <see cref="System.Int32"/>
		/// </param>
		/// <param name="ident">
		/// A <see cref="System.String"/>
		/// </param>
		public void AddVariable(int robot, string ident) {
			this.queriedDomainVariables.Add(this.to.GetRobotById(robot).GetSortedVariable(ident));
		}
		/// <summary>
		/// Remove all added domain (i.e., robot specific) variables from the query.
		/// </summary>
		public void ClearDomainVariables() {
			this.queriedDomainVariables.Clear();
		}
		/// <summary>
		/// Remove all static variables from the query.
		/// </summary>
		public void ClearStaticVariables() {
			this.queriedStaticVariables.Clear();
		}
		/// <summary>
		/// Check whether a solution can be calculated. Keep in mind, the used solver might be incomplete.
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool ExistsSolution(RunningPlan rp) {
			this.store.Clear();
			this.RelevantStaticVariables.Clear();
			this.RelevantDomainVariables.Clear();
			this.calls.Clear();
			this.RelevantStaticVariables.AddRange(this.queriedStaticVariables);
			this.RelevantDomainVariables.AddRange(this.queriedDomainVariables);
			foreach(Variable v in this.RelevantStaticVariables) {
				store.Add(v);
			}
#if CQ_DEBUG
			Console.Write("CQ: Starting Existential Query with static Vars:");
			foreach(Variable v in this.RelevantStaticVariables) {
				Console.Write(" {0}",v.Name);
			}
			Console.WriteLine();
#endif
			while(rp != null && (this.RelevantStaticVariables.Count > 0 || this.RelevantDomainVariables.Count > 0)) {
#if CQ_DEBUG
				Console.WriteLine("CQ: Query at {0}",rp.Plan.Name);
#endif				
				rp.ConstraintStore.AcceptQuery(this);
				if(rp.RealisedPlanType!=null) { //process bindings for plantype
					List<Variable> tmpList = new List<Variable>();
					foreach(Parametrisation p in rp.RealisedPlanType.Parametrisation) {
						if (p.SubPlan == rp.Plan && this.RelevantStaticVariables.Contains(p.SubVar)) {
							tmpList.Add(p.Var);
							store.AddVarTo(p.SubVar,p.Var);
						}
					}
					this.RelevantStaticVariables = tmpList;					
				}
				
				
				if(rp.Parent != null && rp.Parent.ActiveState != null) { //process bindings for state
					List<Variable> tmpList = new List<Variable>();
					foreach(Parametrisation p in rp.Parent.ActiveState.Parametrisation) {
						if ((p.SubPlan == rp.Plan || p.SubPlan == rp.RealisedPlanType) && this.RelevantStaticVariables.Contains(p.SubVar)) {
							tmpList.Add(p.Var);
							store.AddVarTo(p.SubVar,p.Var);
						}
					}
					this.RelevantStaticVariables = tmpList;						
				}
				rp = rp.Parent;
				
				
			}			
			if(this.calls.Count == 0) {
#if CQ_DEBUG
				Console.WriteLine("CQ: Empty Query!");
#endif
				return false;
			}
			List<ConstraintDescriptor> cds = new List<ConstraintDescriptor>();
			foreach(ConstraintCall c in this.calls) {
				AD.Variable[] varr = new AD.Variable[c.Condition.Vars.Count];
				for(int j=0; j<c.Condition.Vars.Count; j++) {
						varr[j] = store.GetRep(c.Condition.Vars[j]).SolverVar;								
				}
				List<List<AD.Term[]>> sortedVars = new List<List<AD.Term[]>>();
				List<ICollection<int>> agentsInScope = new List<ICollection<int>>();
				for(int j=0; j < c.SortedVariables.Count; j++) {
				//foreach(List<Variable[]> l in c.SortedVariables) {
					List<AD.Term[]> ll = new List<AD.Term[]>();
					agentsInScope.Add(c.AgentsInScope[j]);
					sortedVars.Add(ll);
					foreach(Variable[] dvarr in c.SortedVariables[j]) {
						AD.Term[] dtvarr = new AD.Term[dvarr.Length];
						for(int i=0; i<dtvarr.Length; i++) {
							dtvarr[i] = dvarr[i].SolverVar;
						}
						ll.Add(dtvarr);
					}
				}
				ConstraintDescriptor cd = new ConstraintDescriptor(varr,sortedVars);
				cd.AgentsInScope = agentsInScope;
				c.Condition.Constraint(cd,c.RunningPlan);
				cds.Add(cd);
			}
			List<Variable> qVars = store.GetAllRep();			
			qVars.AddRange(this.RelevantDomainVariables);			
			return ConstraintHelper.Solver.ExistsSolution(qVars,cds);
				
		}
		/// <summary>
		/// Obtain a result for this query as a list of doubles
		/// </summary>
		/// <returns>
		/// The solution.
		/// </returns>
		/// <param name='rp'>
		/// The <see cref="RunningPlan"/> in which this query is executed.
		/// </param>
		/// <param name='result'>
		/// The resulting list of double, contains firstly all values for the static variables, followed by the robot specific variables.
		/// </param>
		public bool GetSolution(RunningPlan rp,out List<double> result) {
#if CQ_DEBUG			
			long time = DateTime.UtcNow.Ticks;
#endif			
			this.store.Clear();
			this.RelevantStaticVariables.Clear();
			this.RelevantDomainVariables.Clear();
			this.calls.Clear();
			this.RelevantStaticVariables.AddRange(this.queriedStaticVariables);
			this.RelevantDomainVariables.AddRange(this.queriedDomainVariables);
			foreach(Variable v in this.RelevantStaticVariables) {
				store.Add(v);
			}
#if CQ_DEBUG
			Console.Write("Starting Query with static Vars:");
			foreach(Variable v in this.RelevantStaticVariables) {
				Console.Write(" {0}",v.Name);
			}
			Console.WriteLine();
#endif
			while(rp != null && (this.RelevantStaticVariables.Count > 0 || this.RelevantDomainVariables.Count > 0)) {
#if CQ_DEBUG
				Console.WriteLine("CQ: Query at {0}",rp.Plan.Name);
#endif				
				rp.ConstraintStore.AcceptQuery(this);
				if(rp.RealisedPlanType!=null) { //process bindings for plantype
					List<Variable> tmpList = new List<Variable>();
					foreach(Parametrisation p in rp.RealisedPlanType.Parametrisation) {
						if (p.SubPlan == rp.Plan && this.RelevantStaticVariables.Contains(p.SubVar)) {
							tmpList.Add(p.Var);
							store.AddVarTo(p.SubVar,p.Var);
						}
					}
					this.RelevantStaticVariables = tmpList;					
				}
				
				
				if(rp.Parent != null && rp.Parent.ActiveState != null) { //process bindings for state
					List<Variable> tmpList = new List<Variable>();
					foreach(Parametrisation p in rp.Parent.ActiveState.Parametrisation) {
						if ((p.SubPlan == rp.Plan || p.SubPlan == rp.RealisedPlanType) && this.RelevantStaticVariables.Contains(p.SubVar)) {
							tmpList.Add(p.Var);
							store.AddVarTo(p.SubVar,p.Var);
						}
					}
					this.RelevantStaticVariables = tmpList;						
				}
				rp = rp.Parent;
				
				
			}
			//now we have a list of ConstraintCalls in calls ready to be queried together with a store of unifications
			result = null;
			if(this.calls.Count == 0) {
#if CQ_DEBUG
				Console.WriteLine("CQ: Empty Query!");
#endif
				return false;
			}
			List<ConstraintDescriptor> cds = new List<ConstraintDescriptor>();
			
			foreach(ConstraintCall c in this.calls) {
				AD.Variable[] varr = new AD.Variable[c.Condition.Vars.Count];
				for(int j=0; j<c.Condition.Vars.Count; j++) {
						varr[j] = store.GetRep(c.Condition.Vars[j]).SolverVar;								
				}
				List<List<AD.Term[]>> sortedVars = new List<List<AD.Term[]>>();
				List<ICollection<int>> agentsInScope = new List<ICollection<int>>();

				for(int j=0; j < c.SortedVariables.Count; j++) {
				//foreach(List<Variable[]> l in c.SortedVariables) {
					List<AD.Term[]> ll = new List<AD.Term[]>();
					agentsInScope.Add(c.AgentsInScope[j]);
					sortedVars.Add(ll);
					foreach(Variable[] dvarr in c.SortedVariables[j]) {
						AD.Term[] dtvarr = new AD.Term[dvarr.Length];
						for(int i=0; i<dtvarr.Length; i++) {
							dtvarr[i] = dvarr[i].SolverVar;
						}
						ll.Add(dtvarr);
					}
				}
				ConstraintDescriptor cd = new ConstraintDescriptor(varr,sortedVars);
				cd.AgentsInScope = agentsInScope;
				c.Condition.Constraint(cd,c.RunningPlan);
				cds.Add(cd);
			}
			List<Variable> qVars = store.GetAllRep();
			int domOffset = qVars.Count;
			qVars.AddRange(this.RelevantDomainVariables);
			/*foreach(Variable v in this.RelevantDomainVariables) {
				qVars.Add(v);
			}*/

			double[] solverResult;
#if CQ_DEBUG			
			Console.WriteLine("CQ PrepTime: {0}",(DateTime.UtcNow.Ticks-time)/10000.0);
#endif			
			bool ret =  ConstraintHelper.Solver.GetSolution(qVars,cds,out solverResult);
			result = new List<double>();
			
			if (solverResult!=null) {
				//throw new Exception("Unexpected Result in Multiple Variables Query!");
				for(int i=0; i< this.queriedStaticVariables.Count; i++) {
					result.Add(solverResult[store.GetIndexOf(this.queriedStaticVariables[i])]);
				}
				for(int i=0; i<this.queriedDomainVariables.Count;i++) {
					result.Add(solverResult[domOffset+this.RelevantDomainVariables.IndexOf(this.queriedDomainVariables[i])]);
				}
			}	
			
			return ret;			
		}
		/// <summary>
		/// Obtain a result for this query, including non-numerals.
		/// </summary>
		/// <param name="rp">
		/// The <see cref="RunningPlan"/> in which this query is executed.
		/// </param>
		/// <param name="result">
		/// The resulting <see cref="List<System.Object>"/>, contains firstly all values for the static variables, followed by the robot specific variables.
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool GetSolution(RunningPlan rp,out List<object> result) {
#if CQ_DEBUG			
			long time = DateTime.UtcNow.Ticks;
#endif			
			this.store.Clear();
			this.RelevantStaticVariables.Clear();
			this.RelevantDomainVariables.Clear();
			this.calls.Clear();
			this.RelevantStaticVariables.AddRange(this.queriedStaticVariables);
			this.RelevantDomainVariables.AddRange(this.queriedDomainVariables);
			foreach(Variable v in this.RelevantStaticVariables) {
				store.Add(v);
			}
#if CQ_DEBUG
			Console.Write("Starting Query with static Vars:");
			foreach(Variable v in this.RelevantStaticVariables) {
				Console.Write(" {0}",v.Name);
			}
			Console.WriteLine();
#endif
			while(rp != null && (this.RelevantStaticVariables.Count > 0 || this.RelevantDomainVariables.Count > 0)) {
#if CQ_DEBUG
				Console.WriteLine("CQ: Query at {0}",rp.Plan.Name);
#endif				
				rp.ConstraintStore.AcceptQuery(this);
				if(rp.RealisedPlanType!=null) { //process bindings for plantype
					List<Variable> tmpList = new List<Variable>();
					foreach(Parametrisation p in rp.RealisedPlanType.Parametrisation) {
						if (p.SubPlan == rp.Plan && this.RelevantStaticVariables.Contains(p.SubVar)) {
							tmpList.Add(p.Var);
							store.AddVarTo(p.SubVar,p.Var);
						}
					}
					this.RelevantStaticVariables = tmpList;					
				}
				
				
				if(rp.Parent != null && rp.Parent.ActiveState != null) { //process bindings for state
					List<Variable> tmpList = new List<Variable>();
					foreach(Parametrisation p in rp.Parent.ActiveState.Parametrisation) {
						if ((p.SubPlan == rp.Plan || p.SubPlan == rp.RealisedPlanType) && this.RelevantStaticVariables.Contains(p.SubVar)) {
							tmpList.Add(p.Var);
							store.AddVarTo(p.SubVar,p.Var);
						}
					}
					this.RelevantStaticVariables = tmpList;						
				}
				rp = rp.Parent;
				
				
			}
			//now we have a list of ConstraintCalls in calls ready to be queried together with a store of unifications
			result = null;
			if(this.calls.Count == 0) {
#if CQ_DEBUG
				Console.WriteLine("CQ: Empty Query!");
#endif
				return false;
			}
			List<ConstraintDescriptor> cds = new List<ConstraintDescriptor>();
			foreach(ConstraintCall c in this.calls) {
				AD.Variable[] varr = new AD.Variable[c.Condition.Vars.Count];
				for(int j=0; j<c.Condition.Vars.Count; j++) {
						varr[j] = store.GetRep(c.Condition.Vars[j]).SolverVar;								
				}
				List<List<AD.Term[]>> sortedVars = new List<List<AD.Term[]>>();
				List<ICollection<int>> agentsInScope = new List<ICollection<int>>();
				for(int j=0; j < c.SortedVariables.Count; j++) {
				//foreach(List<Variable[]> l in c.SortedVariables) {
					List<AD.Term[]> ll = new List<AD.Term[]>();
					agentsInScope.Add(c.AgentsInScope[j]);
					sortedVars.Add(ll);
					foreach(Variable[] dvarr in c.SortedVariables[j]) {
						AD.Term[] dtvarr = new AD.Term[dvarr.Length];
						for(int i=0; i<dtvarr.Length; i++) {
							dtvarr[i] = dvarr[i].SolverVar;
						}
						ll.Add(dtvarr);
					}
				}
				ConstraintDescriptor cd = new ConstraintDescriptor(varr,sortedVars);
				cd.AgentsInScope = agentsInScope;
				c.Condition.Constraint(cd,c.RunningPlan);
				cds.Add(cd);
			}
			List<Variable> qVars = store.GetAllRep();
			int domOffset = qVars.Count;
			qVars.AddRange(this.RelevantDomainVariables);
			object[] solverResult;
#if CQ_DEBUG			
			Console.WriteLine("CQ PrepTime: {0}",(DateTime.UtcNow.Ticks-time)/10000.0);
#endif			
			bool ret =  ConstraintHelper.Solver.GetSolution(qVars,cds,out solverResult);
			
			result = new List<object>();			
			if (solverResult!=null) {
					//throw new Exception("Unexpected Result in Multiple Variables Query!");

				for(int i=0; i< this.queriedStaticVariables.Count; i++) {
					result.Add(solverResult[store.GetIndexOf(this.queriedStaticVariables[i])]);
				}
				for(int i=0; i<this.queriedDomainVariables.Count;i++) {
					result.Add(solverResult[domOffset+this.RelevantDomainVariables.IndexOf(this.queriedDomainVariables[i])]);
				}
				
			}
			return ret;			
		}
		
		internal List<Variable> RelevantStaticVariables {get; set;}
		internal List<Variable> RelevantDomainVariables {get; set;}
		internal void AddConstraintCalls(ICollection<ConstraintCall> l) {
			this.calls.AddRange(l);
		}
	}
}

