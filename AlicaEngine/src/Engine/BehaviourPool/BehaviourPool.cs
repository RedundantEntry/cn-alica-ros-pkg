

//#define BP_DEBUG

using System;
using Castor;
using System.Collections;
using System.Collections.Generic;
using System.Reflection;
using System.Security;
using System.IO;

using RosCS;

namespace Alica
{
	/// <summary>
	/// Loads behaviours from the specified assembly and manages the context of behaviours started.
	/// </summary>
	public class BehaviourPool : IBehaviourPool
	{
		
		
		//cache loaded types from assemblies
		private Dictionary<Behaviour, Type> loadedBehaviours = new Dictionary<Behaviour,Type>();
		
		//cache instantiated behaviours
		private Dictionary<Behaviour, BasicBehaviour> usedBehaviours = new Dictionary<Behaviour,BasicBehaviour>();
				
		//config stuff		
		private string behavioursDll = "";
		/// <summary>
		/// Basic Ctor
		/// </summary>
		public BehaviourPool()
		{
			string esRoot = Environment.GetEnvironmentVariable("ES_ROOT");
			SystemConfig sc = SystemConfig.LocalInstance;				
			string file = sc["Alica"].GetString("Alica.BehaviourLibrary");
			if (file.StartsWith("/")) {
				this.behavioursDll = file;
			}
			else if (!esRoot.EndsWith("/")) {
					this.behavioursDll = esRoot+"/"+file;
			}
			else this.behavioursDll = esRoot+file;
			
			
		}
		
		
		
		/// <summary>
		/// Adds a Behaviour to the set of currently executed behaviours
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		public void AddBehaviour(RunningPlan rp) {	
			//Console.WriteLine("Adding behaviour {0}",rp.Plan.Name);
			//if(!behaviours.Contains(rp)) {
				if(rp.Plan == null)
				{
					Node.MainNode.RosError("BP: Add Behaviour: RP does not have a BehaviourConfiguration attached");
				}
				
				/*if(! (rp.Plan is BehaviourConfiguration))
				{
					Console.Error.WriteLine("BP: AddBehaviour: Trying to add a Plan as a Behaviour, this will not work: " + rp.Plan.Name);
				}*/
				
				BehaviourConfiguration bc = (BehaviourConfiguration) rp.Plan;
				
				if(bc.Behaviour == null)
				{
					string msg = "BP: BehaviourConfiguration " + bc.Id + " does not have a Behaviour attached";
					AlicaEngine.Get().Abort(msg);
				}
	
				//Behaviour b = bc.Behaviour;
				
				//string behaviourName = bc.Behaviour.Name;
#if (BP_DEBUG)
				Console.WriteLine("BP: Trying to find Behaviour: " + bc.Behaviour.Name);
#endif				
				/*
				if(!loadedBehaviours.ContainsKey(bc.Behaviour))
				{
					string msg = "BP: Could not find Class for Behaviour: " + bc.Behaviour.Name;
					RosCS.Node.MainNode.RosError(msg);
					
					throw new Exception(msg);
				}
				*/

				
				BasicBehaviour bb = bc.Behaviour.Implementation;
				//if(usedBehaviours.TryGetValue(bc.Behaviour,out bb)) {
				if(bb!=null) {
					bb.Parameters = bc.Parameters;
					bb.Variables = bc.Variables;
				}
				else {
					Node.MainNode.RosError(String.Format("BP: Behaviour {0} was not preloaded!", bc.Behaviour.Name));
					return;
				}
								
				rp.BasicBehaviour = bb;
				
				//set opposite direction
				bb.RunningPlan = rp;
				
				//start basic behaviour
				bb.DueTime = bc.Deferring;
				bb.Period = (1000/bc.Frequency);
				bb.Start();
				//behaviours.Add(rp);
				
			//}
		}
		
		/// <summary>
		/// Removes a behaviour out of the pool and disables its thread
		/// </summary>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		public void RemoveBehaviour(RunningPlan rp)
		{
		//Console.WriteLine("Removing behaviour {0}",rp.Plan.Name);	
		//	if(behaviours.Contains(rp))
		//	{
				if(rp.Plan == null)
				{
					Node.MainNode.RosError("BP: RemoveBehaviour: RP does not have a behaviourconfiguration attached");
				}
				
				if(rp.BasicBehaviour == null)
				{
					Node.MainNode.RosError("BP: RemoveBehaviour: RP does not have a BasicBehaviour set");		
				}
				else
				{				
					rp.BasicBehaviour.Stop();
				}
				
			//	behaviours.Remove(rp);				
			//}
		}
		
		/// <summary>
		/// Tests if a behaviour was found in the assembly.
		/// </summary>
		/// <param name="name">
		/// A <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// A <see cref="System.Boolean"/>
		/// </returns>		
		public bool IsBehaviourAvailable(Behaviour b)
		{
			if(this.loadedBehaviours.ContainsKey(b)) return true;

			string msg = "BP: Behaviour (" + b.Name + ") not found!";			
			AlicaEngine.Get().Abort(msg);
			return false;
		}
		/// <summary>
		/// Initialise the behaviourpool, called by <see cref="AlicaEngine"/>
		/// </summary>
		public void Init()
		{
			Console.Write("BP: Loading behaviours...");
			LoadTypesFromFile();
			PreLoadBehaviourThreads();
			Console.WriteLine("done!");
		}
		
		private void PreLoadBehaviourThreads()
		{
			
			Dictionary<long,Behaviour> behaviours = AlicaEngine.Get().PR.Behaviours;
			
			foreach (Behaviour b in behaviours.Values)	{				
				
				
				Type t = null;
				try {
					t = this.loadedBehaviours[b];
				} catch {
					AlicaEngine.Get().Abort("BP: Cannot find behaviour "+b.Name);
				}
				BasicBehaviour bb = GenerateBasicBehaviourFromType(t);
				b.Implementation = bb;
				usedBehaviours.Add(b, bb);		
			}
			
		}
		
		/// <summary>
		/// Loads all behaviour classes from the assembly
		/// </summary>
		private void LoadTypesFromFile()
		{
			Assembly a = null;
			if(!File.Exists(behavioursDll)) {
				AlicaEngine.Get().Abort(String.Format("BP: Unable to find Behaviour library: {0}", behavioursDll));
			}
			try
			{				
				a = Assembly.LoadFile(behavioursDll);
#if (BP_DEBUG)
				Console.WriteLine("BP: Assembly: " + a);
#endif
				
			}
			catch (Exception e)
			{
				AlicaEngine.Get().Abort(String.Format("BP: Unable to load assembly: {0}", e.Message));
			
				
			}
			Dictionary<long, Behaviour> parsedBehaviours = AlicaEngine.Get().PR.Behaviours;
			Type[] types = a.GetTypes();
			
			foreach(Behaviour b in parsedBehaviours.Values) {
				bool found = false;
				foreach(Type t in types) {
					if(b.Name.Equals(t.Name)) {
						if (t.IsAbstract) {
							AlicaEngine.Get().Abort(String.Format("BP: Trying to use an abstract behaviour: {0}",b.Name));
						}
						if(!t.IsSubclassOf(typeof(BasicBehaviour))) {
							AlicaEngine.Get().Abort(String.Format("BP: All behaviours must inherit from BasicBehaviour! Offender is: {0}",b.Name));
						}
						found = true;
						this.loadedBehaviours.Add(b,t);
						break;
					}
				}
				if(!found) AlicaEngine.Get().Abort(String.Format("Cannot find type: {0} in behaviour assembly {1}",b.Name,this.behavioursDll));
				
			}
			/*
			foreach (Type t in a.GetTypes())
			{				
				
				if(!t.IsAbstract && t.IsSubclassOf(typeof(BasicBehaviour)))			
				{
					//Console.WriteLine("BP: type: " + t.Name);
					if (!this.loadedBehaviours.ContainsKey(t.Name))
					{
#if (BP_DEBUG)
						Console.WriteLine("BP: LoadTypesFromFile Adding: " + t.Name);
#endif
						this.loadedBehaviours.Add(t.Name, t);
					}
				}
			}
			*/
		}
		
		/// <summary>
		/// Returns a BasicBehaviour instance out of a given type
		/// </summary>
		/// <param name="t">
		/// A <see cref="Type"/>
		/// </param>
		/// <returns>
		/// A <see cref="BasicBehaviour"/>
		/// </returns>
		private BasicBehaviour GenerateBasicBehaviourFromType( Type t )
		{
			if (t.IsSubclassOf(typeof(BasicBehaviour)))
			{
				try
				{
					// Get the public instance constructor that takes a string
					// parameter. Create a new instance.
					//ConstructorInfo ci = t.GetConstructor(new Type[] { typeof(string), typeof(Dictionary<string, string>) });					
					ConstructorInfo ci = t.GetConstructor(new Type[] { typeof(string) });

					if (ci != null) {
						return (BasicBehaviour)ci.Invoke(new Object[] { t.Name });
					}
					
				}
				catch (Exception e)	{
					AlicaEngine.Get().Abort(String.Format("BP: Error while loading behavior '{0}': {1} \n({2})", t.Name, e.Message, e.GetBaseException()));
				}
			}
			
			return null;
		}
		
		/*public List<RunningPlan> GetBehaviours()
		{		
			return this.behaviours;
		}*/
		/// <summary>
		/// Stop the behaviour pool for good, will terminate all behaviours.
		/// </summary>
		public void Stop() {
						
			/*while(this.behaviours.Count > 0) { 
				this.behaviours[0].Deactivate();
			}*/
			foreach(BasicBehaviour b in this.usedBehaviours.Values) {
				b.Terminate();
			}
		}
	}
}
