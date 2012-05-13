//#define RS_DEBUG

using System;
using System.Threading;

using System.Collections.Generic;
using System.Collections;

using Castor;
using C5;

using Alica;
using AD = AutoDiff;

using RosCS;
using RosCS.AlicaEngine;


namespace Alica.Reasoner
{

	/// <summary>
	/// Stores results for constraint variables, publishes them and integrates received results
	/// </summary>
	public class ResultStore
	{
		private static ResultStore instance;
		private static object lockObj = new object();
		private static bool lockReady = false;
		/// <summary>
		/// Singleton
		/// </summary>
		/// <returns>
		/// The single <see cref="ResultStore"/>
		/// </returns>
		public static ResultStore Get() {
			if(!lockReady) {
				lock(lockObj) {
					if(!lockReady) {
						instance = new ResultStore();
						Thread.MemoryBarrier();
						lockReady = true;
					}
				}
			}
			return instance;
		}
		
		
		int ownId;
		Node rosNode;
		Publisher resultPub;
		bool running;
		bool communicationEnabled;
		RosCS.Timer timer;
		
		C5.SortedArray<ResultEntry> store;
		static double distThreshold;
		
		
		private ResultEntry ownResults;
		
		private ResultStore()
		{
			this.running = false;
			this.store = new C5.SortedArray<ResultEntry>();
			
			
		}
		/// <summary>
		///Initialises the store.
		/// </summary>
		public void Init() {
			if(this.running) return;
			this.running = true;
			SystemConfig sc = SystemConfig.LocalInstance;
			this.communicationEnabled = sc["Alica"].GetBool("Alica","CSPSolving","EnableCommunication");
			this.ownId = SystemConfig.GetOwnRobotID();
			this.ownResults = new ResultEntry(this.ownId);
			this.store.Add(ownResults);
			distThreshold = sc["Alica"].GetDouble("Alica","CSPSolving","SeedMergingThreshold");
			if (this.communicationEnabled) {
				this.rosNode = new Node("AlicaEngine");
				this.resultPub = new Publisher(this.rosNode,"SolverResult",SolverResult.TypeId,1);
				this.rosNode.Subscribe("SolverResult",OnSolverResult,6);							
				int interval = (int)Math.Round(1000.0/sc["Alica"].GetDouble("Alica","CSPSolving","CommunicationFrequency"));			
				//this.timer = new Timer(PublishContent,null,300,interval);
				this.timer = new RosCS.Timer(PublishContent,300,interval);
			}
			
			
		}
		/// <summary>
		/// Closes the store
		/// </summary>
		public void Close() {
			this.running = false;
			if(this.timer!=null) this.timer.Stop();
			this.timer = null;
		}
		/// <summary>
		/// Clears the result store. Mainly for testing purposes.
		/// </summary>
		public void Clear() {
			foreach(ResultEntry r in this.store) {
				r.Clear();
			}				                                       
		}
		/// <summary>
		/// Callback for receiving solver results
		/// </summary>
		/// <param name="msg">
		/// A <see cref="SolverResult"/>
		/// </param>
		public void OnSolverResult(SolverResult msg) {
			
			if(msg.SenderID == ownId) return;
			if(AlicaEngine.Get().TO.IsRobotIgnored(msg.SenderID)) return;
			//Console.WriteLine("Receiving Seed from {0}",msg.SenderID);
			bool found = false;
			ResultEntry re = null;
			for(int i=0; i < this.store.Count;i++) {
				re = this.store[i];
				if(re.Id == msg.SenderID) {
					found = true;
					break;
				}				
			}
			if (!found) {
				re = new ResultEntry(msg.SenderID);
				this.store.Add(re);				
			}
			foreach(SolverVar sv in msg.Vars) {
				re.AddValue(sv.Id,sv.Value);
			}			
		}
		/// <summary>
		/// Publishes results if sufficiently new ones are available. Called periodically by a timer.
		/// </summary>
		/// <param name="o">
		/// ignored
		/// </param>
		protected void PublishContent(object o) {			
			if(!this.running) return;			
			if (!AlicaEngine.Get().MaySendMessages) return;			
			List<SolverVar> lv = ownResults.GetCommunicatableResults();
			if (lv.Count == 0) return;			
			SolverResult sr = new SolverResult(false);
			sr.SenderID = ownId;
			sr.Vars = lv;			
			this.resultPub.Send(sr);
		}
		/// <summary>
		/// Integrates a local result into the store. Called by <see cref="CGSolver"/>.
		/// </summary>
		/// <param name="vid">
		/// The variable's id, a <see cref="System.Int64"/> to be stored
		/// </param>
		/// <param name="result">
		/// The value of the variable, a <see cref="System.Double"/>
		/// </param>
		public void PostResult(long vid,double result) {
			this.ownResults.AddValue(vid,result);
		}
		/// <summary>
		/// Returns a set of seeds to be used for a query
		/// </summary>
		/// <param name="query">
		/// The variables relevant to the query, a <see cref="List<CVariable>"/>.
		/// </param>
		/// <param name="limits">
		/// The bounds of the search space, a <see cref="System.Double[,]"/>. Used to determine when to merge different seed points
		/// </param>
		/// <returns>
		/// An array of seeds as <see cref="System.Double[][]"/>, 
		/// </returns>
		public double[][] GetSeeds(List<Variable> query,double[,] limits) {
			int dim = query.Count;
			List<VotedSeed> seeds = new List<VotedSeed>();
			double[] scaling = new double[dim];
			for(int i=0; i<dim; i++) {
				scaling[i] = (limits[i,1]-limits[i,0]);
				scaling[i] *= scaling[i]; //Sqr it for dist calculation speed up
			}
			for(int i=0; i<this.store.Count; i++) {
				ResultEntry re = this.store[i]; //allow for lock free iteration (no value is deleted from store)
				double[] vec = re.GetValues(query);
				bool found = false;
				foreach(VotedSeed s in seeds) {
					if(s.TakeVector(vec,scaling)) {
						found = true;
						break;
					}
				}
				if(!found) {
					seeds.Add(new VotedSeed(dim,vec));
				}
			}
#if RS_DEBUG
			Console.WriteLine("RS: Generated {0} seeds",seeds.Count);
			for(int i=0; i<seeds.Count; i++) {
				Console.Write("Seed {0} (sup:{1}): ",i);
				for(int j=0; j<seeds[i].values.Length; j++) {
					Console.Write(seeds[i].values[j]+"\t");
				}
				Console.WriteLine();
			}
				
#endif			
			
			int maxNum = Math.Min(seeds.Count,dim);
			double[][] ret = new double[maxNum][];
			
			seeds.Sort();
			
			for(int i=0; i<maxNum;i++) {
				ret[i] = seeds[i].values;
			}
			
			return ret;
		}
		/// <summary>
		/// Internal class to calculate merged seeds.
		/// </summary>
		protected class VotedSeed : IComparable<VotedSeed> {
			public VotedSeed(int dim, double[] v) {
				this.values = v;
				this.supporterCount = new int[dim];
				this.dim = dim;
				for(int i=0; i<dim; i++) {
					if (!Double.IsNaN(v[i])) totalSupCount++;
				}
			}
			public double[] values;
			public int[] supporterCount;
			int totalSupCount;
			int dim;
			
			public bool TakeVector(double[] v,double[] scaling) {
				//int additions = 0;
				int nans = 0;
				double distSqr = 0;
				for(int i=0; i<dim; i++) {
					if(!Double.IsNaN(v[i])) {
						if (!Double.IsNaN(this.values[i])) {
							if (scaling[i] != 0) {
								distSqr += ((this.values[i]-v[i])*(this.values[i]-v[i]))/scaling[i];							
							} else {
								distSqr += ((this.values[i]-v[i])*(this.values[i]-v[i]));
							}
						} /*else {
							additions++;
						}*/
					} else nans++;			
				}
				if(dim == nans) {
					return true; //silently absorb a complete NaN vector
				}
				if(distSqr/(dim-nans) < ResultStore.distThreshold) {
					for(int i=0; i<dim; i++) {
						if(!Double.IsNaN(v[i])) {
							if (Double.IsNaN(this.values[i])) {
								this.supporterCount[i] = 1;
								this.totalSupCount++;
								this.values[i] = v[i];
							} else {
								this.values[i] = this.values[i]*this.supporterCount[i]+v[i];
								this.supporterCount[i]++;
								this.totalSupCount++;
								this.values[i] /= this.supporterCount[i];
							}
						}
					}
					return true;
				} else return false;				
			}
			public int CompareTo (VotedSeed other)
			{
				if (this.totalSupCount > other.totalSupCount) return -1;
				if (this.totalSupCount < other.totalSupCount) return 1;
				return this.GetHashCode() - other.GetHashCode();
			}
		}
	}
}
