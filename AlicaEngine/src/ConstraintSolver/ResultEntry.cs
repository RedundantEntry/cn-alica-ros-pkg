

using System;
using System.Threading;

using System.Collections.Generic;
using System.Collections;

using Castor;

using Alica;
using AD = AutoDiff;


using RosCS;
using RosCS.AlicaEngine;


namespace Alica.Reasoner
{
	internal class VarValue  {
		public long id;
		public double val;	
		public ulong lastUpdate;
		public VarValue(long vid,double v, ulong now) {
			this.id = vid;
			this.val = v;
			this.lastUpdate = now;
		}
		



	}
	

	internal class ResultEntry: IComparable<ResultEntry>
	{
		static ulong ttl4Communication = 1000000UL*SystemConfig.LocalInstance["Alica"].GetULong("Alica","CSPSolving","SeedTTL4Communication");
		static ulong ttl4Usage = 1000000UL*SystemConfig.LocalInstance["Alica"].GetULong("Alica","CSPSolving","SeedTTL4Usage");
		
		Dictionary<long,VarValue> values;
		public int Id {get; private set;}
		
		public ResultEntry(int robotId) {
			this.Id = robotId;
			this.values = new Dictionary<long,VarValue>();
		}
		public void AddValue(long vid, double val) {
			ulong now = RosSharp.Now();
			VarValue vv;
			if (this.values.TryGetValue(vid,out vv)) {
				vv.val = val;
				vv.lastUpdate = now;					
			} else {
				lock(this.values) {
					if (this.values.TryGetValue(vid,out vv)) {
						vv.val = val;
						vv.lastUpdate = now;					
					}
					else {
						vv = new VarValue(vid,val,now);				
						this.values.Add(vid,vv);				
					}
				}
			}
		}
		public void Clear() {
			lock(this.values) {
				this.values.Clear();
			}
		}
		public List<SolverVar> GetCommunicatableResults() {
			List<VarValue> lv= new List<VarValue>();
			lock(this.values) {
				lv = new List<VarValue>(this.values.Values);
			}
			ulong now = RosSharp.Now();
			List<SolverVar> lsv = new List<SolverVar>();
			foreach(VarValue vv in lv) {
				if (vv.lastUpdate + ttl4Communication > now) {
					SolverVar sv = new SolverVar();
					sv.Id = vv.id;
					sv.Value = vv.val;
					lsv.Add(sv);
				}
			}
			return lsv;
		}
		public double GetValue(long vid) {
			ulong now = RosSharp.Now();
			VarValue vv;
			if (this.values.TryGetValue(vid,out vv)) {
				if(vv.lastUpdate + ttl4Usage > now) return vv.val;
			}
			return Double.NaN;
		}
		public double[] GetValues(List<Variable> query) {
			double[] ret = new double[query.Count];
			for(int i=0; i<query.Count;i++) {
				ret[i] = GetValue(query[i].Id);
			}
			return ret;
		}
		public override bool Equals (object obj)
		{
			return this.Id == ((ResultEntry)obj).Id;
		}
		public override int GetHashCode ()
		{
			return this.Id;
		}
		public int CompareTo (ResultEntry other)
		{
			return this.Id - other.Id;
		}
		
	}
}
