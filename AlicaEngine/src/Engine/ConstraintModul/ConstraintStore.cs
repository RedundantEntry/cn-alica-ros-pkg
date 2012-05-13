//#define CS_DEBUG
using System;
using System.Collections.Generic;
namespace Alica
{
	/// <summary>
	/// Holds information about active constraints in the corresponding <see cref="RunningPlan"/>
	/// </summary>
	public class ConstraintStore
	{
		HashSet<Condition> activeConditions;
		Dictionary<Variable,List<Condition>> activeVariables;
		RunningPlan rp;
		/// <summary>
		/// Default constructor
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		public ConstraintStore (RunningPlan rp)
		{
			this.rp = rp;
			this.activeConditions = new HashSet<Condition>();
			this.activeVariables = new Dictionary<Variable,List<Condition>>();
		}
		/// <summary>
		/// Clear store, revoking all constraints
		/// </summary>
		public void Clear() {
			this.activeVariables.Clear();
			lock(this.activeConditions) {
				this.activeConditions.Clear();
			}
		}
		/// <summary>
		/// Add a condition to the store.
		/// </summary>
		/// <param name="con">
		/// A <see cref="Condition"/>
		/// </param>
		public void AddCondition(Condition con) {
			if(con == null) return;
			if(con.Vars.Count == 0 && con.Quantifiers.Count == 0) return;
			bool modified = false;
			lock(this.activeConditions) {
				modified = this.activeConditions.Add(con);
			}
			if(modified) {
				foreach(Variable v in con.Vars) {
					List<Condition> l = null;
					if(activeVariables.TryGetValue(v,out l)) {
						l.Add(con);
					} else {
						l = new List<Condition>();
						l.Add(con);
						activeVariables.Add(v,l);
					}
				}
			}
#if CS_DEBUG
			Console.WriteLine("CS: Added condition in {0} with {1} vars",rp.Plan.Name,con.Vars.Count);
#endif			
		}
		/// <summary>
		/// Revoke a specific condition from the constraint store
		/// </summary>
		/// <param name="con">
		/// A <see cref="Condition"/>
		/// </param>
		public void RemoveCondition(Condition con) {
			if(con==null) return;
			bool modified = false;
			lock(this.activeConditions) {
				modified = this.activeConditions.Remove(con);
			}
			if(modified) {
				foreach(Variable v in con.Vars) {
					activeVariables[v].Remove(con);
				}
			}
#if CS_DEBUG
			Console.WriteLine("CS: Removed condition in {0} with {1} vars",rp.Plan.Name,con.Vars.Count);
#endif			

			
		}
		/// <summary>
		/// Called by the <see cref="ConstraintQuery"/> to obtain all relevant calls.
		/// </summary>
		/// <param name="query">
		/// A <see cref="ConstraintQuery"/>
		/// </param>
		public void AcceptQuery(ConstraintQuery query) {
			if (this.activeConditions.Count == 0) return;
#if CS_DEBUG
			Console.WriteLine("CS: Accepting Query - Store size is {0}",this.activeConditions.Count);
#endif			
			List<Variable> relVars = query.RelevantStaticVariables; //get the set of variables that are relevant in the current plan hierarchy
			List<Variable> relDomainVars = query.RelevantDomainVariables; //get all the domain variables			
			if (relVars.Count == 0 && relDomainVars.Count == 0) return; //nothing to do			
			
			Dictionary<Condition,ConstraintCall> newconditions = new Dictionary<Condition,ConstraintCall>();
			Dictionary<Condition,ConstraintCall> allconditions = new Dictionary<Condition, ConstraintCall>();
			lock(this.activeConditions) {
				foreach(Condition c in this.activeConditions) {
					allconditions.Add(c,new ConstraintCall(c,this.rp));
				}
			}
//Console.WriteLine("Unfolded store!");
//Console.WriteLine("Active Vars: ");
/*foreach(Variable v in this.activeVariables.Keys) {
	Console.WriteLine("{0} {1}",v.Name,v.Id);
}*/
			
			List<Variable> varsToCheck = relVars;
			List<Variable> domVarsToCheck = relDomainVars;
			List<Variable> varsChecked = new List<Variable>();
			List<Variable> domVarsChecked = new List<Variable>();
			while(newconditions.Count < allconditions.Count && (domVarsToCheck.Count > 0 || varsToCheck.Count > 0)) {
				if (varsToCheck.Count > 0) {
					Variable v = varsToCheck[varsToCheck.Count-1];
					varsToCheck.RemoveAt(varsToCheck.Count-1);
					varsChecked.Add(v);
//Console.WriteLine("Checking static Var {0} ({1})",v.Name,v.Id);
					List<Condition> l = null;
					if (activeVariables.TryGetValue(v,out l)) {
//Console.WriteLine("Conditions active under var {0}: {1}",v.Name,l.Count);
						foreach(Condition c in l) {

							if (!newconditions.ContainsKey(c)) {
								ConstraintCall cc = allconditions[c];
								newconditions.Add(c,cc);
								foreach(List<Variable[]> lvarr in cc.SortedVariables) {
									foreach(Variable[] varr in lvarr) {
										for(int i=0; i<varr.Length; i++) {
											if(!domVarsChecked.Contains(varr[i]) && !domVarsToCheck.Contains(varr[i])) {
												domVarsToCheck.Add(varr[i]);
											}
										}
									}
								}
								foreach(Variable vv in c.Vars) {
									if(!varsChecked.Contains(vv) && !varsToCheck.Contains(vv)) {
										varsToCheck.Add(vv);
									}
								}
							}
						}
					}
				}
				else if (domVarsToCheck.Count > 0) {
					Variable v = domVarsToCheck[domVarsToCheck.Count -1];
					domVarsToCheck.RemoveAt(domVarsToCheck.Count-1);
					domVarsChecked.Add(v);
					foreach(KeyValuePair<Condition,ConstraintCall> k in allconditions) {
						if(!newconditions.ContainsKey(k.Key)) {
							if(k.Value.HasVariable(v)) {
								newconditions.Add(k.Key,k.Value);
								foreach(Variable vv in k.Key.Vars) {
									if(!varsChecked.Contains(vv) && !varsToCheck.Contains(vv)) {
										varsToCheck.Add(vv);
									}
								}
								foreach(List<Variable[]> lvarr in k.Value.SortedVariables) {
									foreach(Variable[] varr in lvarr) {
										for(int i=0; i<varr.Length; i++) {
											if(!domVarsChecked.Contains(varr[i]) && !domVarsToCheck.Contains(varr[i])) {
												domVarsToCheck.Add(varr[i]);
											}
										}
									}
								}								
							}
						}
					}
					
				}
			}
			varsChecked.AddRange(varsToCheck);
			
			domVarsChecked.AddRange(domVarsToCheck);
			

						
			query.RelevantStaticVariables = varsChecked; //writeback relevant variables, this contains variables obtained earlier
			query.RelevantDomainVariables = domVarsChecked;
			
			query.AddConstraintCalls(newconditions.Values);
			
		}
		
	}
}

