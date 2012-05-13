
using System;
using System.Collections.Generic;

namespace Alica 
{

	/// <summary>
	/// Globally holds information about succeeded entrypoints for a specific robot
	/// </summary>
	public class SuccessMarks
	{
		protected Dictionary<AbstractPlan,List<EntryPoint>> successMarks;
		
		/// <summary>
		/// Default Constructor
		/// </summary>
		public SuccessMarks()
		{
			this.successMarks = new Dictionary<AbstractPlan, List<EntryPoint>>();
		}
		/// <summary>
		/// Construct from a list of entrypoint id, as received by a message
		/// </summary>
		/// <param name="epIds">
		/// A <see cref="List<System.Int64>"/>
		/// </param>
		public SuccessMarks(List<long> epIds) {
			this.successMarks = new Dictionary<AbstractPlan, List<EntryPoint>>();
			Dictionary<long,EntryPoint> eps = AlicaEngine.Get().PR.EntryPoints;
			foreach(long id in epIds) {
				EntryPoint ep;
				if(eps.TryGetValue(id,out ep)) {
					List<EntryPoint> s;
					if (successMarks.TryGetValue(ep.InPlan,out s)) {
						if (!s.Contains(ep)) {
							s.Add(ep);
						}
					} else {
						s = new List<EntryPoint>();
						s.Add(ep);
						this.successMarks.Add(ep.InPlan,s);
					}
				}
			}
		}
		/// <summary>
		/// Clear all marks
		/// </summary>
		public void Clear() {
			this.successMarks.Clear();
		}
		/// <summary>
		/// Mark an entrypoint within a plan as successfully completed
		/// </summary>
		/// <param name="p">
		/// A <see cref="AbstractPlan"/>
		/// </param>
		/// <param name="e">
		/// A <see cref="EntryPoint"/>
		/// </param>
		public void MarkSuccessfull(AbstractPlan p, EntryPoint e) {
			if(this.successMarks.ContainsKey(p)) {
				List<EntryPoint> l = this.successMarks[p];
				if (!l.Contains(e)) {
					l.Add(e);
				}
			} else {
				List<EntryPoint> l = new List<EntryPoint>();
				l.Add(e);
				this.successMarks.Add(p,l);
				                      
			}			
		}
		/// <summary>
		/// Check whether an entrypoint in a plan was completed.
		/// </summary>
		/// <param name="p">
		/// A <see cref="AbstractPlan"/>
		/// </param>
		/// <param name="e">
		/// A <see cref="EntryPoint"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool Succeeded(AbstractPlan p, EntryPoint e) {
			List<EntryPoint> l;
			if(this.successMarks.TryGetValue(p,out l)) {
				return l.Contains(e);
			}
			return false;			
		}
		
		/// <summary>
		/// Check whether an entrypoint in a plan was completed.
		/// </summary>
		/// <param name="planId">
		/// A <see cref="System.Int64"/>
		/// </param>
		/// <param name="entryPointId">
		/// A <see cref="System.Int64"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool Succeeded(long planId, long entryPointId) {
			Plan p = AlicaEngine.Get().PR.Plans[planId];
			EntryPoint e = p.EntryPoints[entryPointId];
			return Succeeded(p,e);
		}
		/// <summary>
		/// Get all entrypoints succeeded in a plan. May return null.
		/// </summary>
		/// <param name="p">
		/// A <see cref="AbstractPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="List<EntryPoint>"/>
		/// </returns>
		public List<EntryPoint> SucceededEntryPoints(AbstractPlan p) {
			List<EntryPoint> l;
			if(this.successMarks.TryGetValue(p,out l)) {
				return l;
			}
			return null;			
		}
		/// <summary>
		/// Test if at least one task has succeeded within abstract plan p
		/// </summary>
		/// <param name="p">
		/// A <see cref="AbstractPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>
		public bool AnyTaskSucceeded(AbstractPlan p) {
			List<EntryPoint> l;
			if (this.successMarks.TryGetValue(p,out l)) {
				return (l.Count > 0);
			}
			PlanType pt = p as PlanType;
			if (pt!=null) {
				foreach(Plan cp in pt.Plans) {
					if (this.successMarks.TryGetValue(cp,out l) && l.Count>0) return true;					
				}
			}
			return false;
			
		}
		/// <summary>
		/// serialise to a list of entrypoint ids.
		/// </summary>
		/// <returns>
		/// A <see cref="List<System.Int64>"/>
		/// </returns>
		public List<long> ToList() {
			List<long> ret = new List<long>();
			foreach(List<EntryPoint> l in this.successMarks.Values) {
				foreach(EntryPoint e in l) { ret.Add(e.Id); }
			}
			return ret;
		}
		/// <summary>
		/// Drop every mark not occurring in plans passed as argument.
		/// </summary>
		/// <param name="active">
		/// A <see cref="ICollection<AbstractPlan>"/>
		/// </param>
		public void LimitToPlans(ICollection<AbstractPlan> active) {
			List<AbstractPlan> tr = new List<AbstractPlan>();
			foreach(AbstractPlan p in this.successMarks.Keys) {
				if(!active.Contains(p)) {
					tr.Add(p);
				}
			}
			foreach(AbstractPlan p in tr) {
				this.successMarks.Remove(p);
			}
		}
		/// <summary>
		/// Remove all marks referring to the specified plan.
		/// </summary>
		/// <param name="p">
		/// A <see cref="AbstractPlan"/>
		/// </param>
		public void RemovePlan(AbstractPlan p) {
			this.successMarks.Remove(p);
		}
	}
}
