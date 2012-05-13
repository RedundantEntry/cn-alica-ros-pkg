


using System;
using System.Net;
using System.Threading;
using System.Collections;
using System.Collections.Generic;

using Castor;
using Castor.Dynamic;

using RosCS;

/// <summary>
/// The main namespace of the AlicaEngine
/// </summary>
namespace Alica
{	
	/// <summary>
	/// The main class.
	/// </summary>
	public class AlicaEngine
	{
		protected static AlicaEngine instance = null;
		
		protected SystemConfig sc = SystemConfig.LocalInstance;
		
		protected Plan masterPlan = null;
		protected RoleSet roleSet = null;		
		
//		protected RoleDefinitionSet rdSet = null;
//		protected CapabilityDefinitionSet cdSet = null;

		
		//other information	
		protected string currentRoleName = "";
		
		
		protected Task currentTask = null;
		protected State currentState = null;
		protected Plan currentPlan = null;
		
		protected IList<int> robotIDsWithMe;
		
		
		
		
		
		//behaviourengine modules	
		
		protected IPlanParser planParser;
		protected PlanRepository repository;
		protected PlanBase planBase;
		
		protected IRoleAssignment roleAssignment;
		protected ITeamObserver teamObserver;
		protected IPlanSelector planSelector;
		protected IBehaviourPool behaviourPool;
		protected ISyncModul syncModul;
		protected AuthorityManager auth;
		
		protected IConstraintSolver cSolver;
		
		protected Logger log;
		
		protected ExpressionHandler exprHandler;
		
		protected static object lockobj = new object();
		protected static bool lockready = false;
		
		/// <summary>
		/// Set to have the engine's main loop wait on a signal via <see cref="MayStep"/>.
		/// </summary>
		public bool StepEngine {get; private set;}
		/// <summary>
		/// Allow the main engine loop to do a step. Only relevant if <see cref="StepEngine"/> is set to true.
		/// </summary>
		public void MayStep() {
			this.PB.Signal.Set();
		}
		/// <summary>
		/// Switch the engine between normal operation and silent mode, in which no messages other than debugging information are sent out.
		/// This is useful for a robot on hot standby.
		/// </summary>
		public bool MaySendMessages {get; set;}
		
		/// <summary>
		/// Indicates whether the engine is shutting down.
		/// </summary>
		public bool IsTerminating{get; private set;}	
	
		
		protected AlicaEngine()
		{
			//Sanity check:
			SystemConfig.GetOwnRobotID();

			this.IsTerminating = false;
			//initialise engine modules
			//ModuleLoader<ITeamObserver> toLoader = ModuleLoader<ITeamObserver>.Load("TeamObserver", null, true, true);						
			//this.teamObserver = toLoader.GetInstance(null, null);
			this.teamObserver = new TeamObserver();
			
			//ModuleLoader<IRoleAssignment> raLoader = ModuleLoader<IRoleAssignment>.Load("RoleAssignment", null, true, true);
			//this.roleAssignment = raLoader.GetInstance(null, null);
			this.roleAssignment = new RoleAssignment();
			
			//ModuleLoader<IPlanSelector> psLoader = ModuleLoader<IPlanSelector>.Load("PlanSelector", null, true, true);			
			//this.planSelector = psLoader.GetInstance(null, null);
			
			
			//ModuleLoader<IBehaviourPool> bpLoader = ModuleLoader<IBehaviourPool>.Load("BehaviourPool", null, true, true);			
			//this.behaviourPool = bpLoader.GetInstance(null, null);
			this.behaviourPool = new BehaviourPool();
			
			//ModuleLoader<ISyncModul> smLoader = ModuleLoader<ISyncModul>.Load("SyncModul", null, true, true);			
			//this.syncModul = smLoader.GetInstance(null, null);
			this.syncModul = new SyncModul();
			
			
			ModuleLoader<IConstraintSolver> csLoader = ModuleLoader<IConstraintSolver>.Load("CGSolver", null, true, true);			
			this.cSolver = csLoader.GetInstance(null, null);
			
			Console.WriteLine("AE: Successfully loaded modules");
			
			this.MaySendMessages = ! sc["Alica"].GetBool("Alica.SilentStart");
			
			
		}
		/// <summary>
		/// Get the singleton engine.
		/// </summary>
		/// <returns>
		/// A <see cref="AlicaEngine"/>
		/// </returns>
		public static AlicaEngine Get()
		{			
			if (!lockready)	{	
				lock(lockobj) {
					if(!lockready) {
						instance = new AlicaEngine();
						System.Threading.Thread.MemoryBarrier();
						lockready = true;
					}
				}
			}
			return instance;
		}
		/// <summary>
		/// Gets the robot name, either by access the environment variable "ROBOT", or if that isn't set, the hostname.
		/// </summary>
		/// <returns>
		/// The robot name under which the engine operates, a <see cref="System.String"/>
		/// </returns>
		public string GetRobotName() {
			 return SystemConfig.GetRobotName();
		}
		
		/// <summary>
		/// Starts the engine.
		/// </summary>
		public void Start() {			
			this.planBase.Start();
			Console.WriteLine("AE: Engine started.");
		}
		/// <summary>
		/// Intialise the engine
		/// </summary>
		/// <param name="roleSetName">
		/// A <see cref="System.String"/>, the roleset to be used. If empty, a default roleset is looked for
		/// </param>
		/// <param name="masterPlanName">
		/// A <see cref="System.String"/>, the top-level plan to be used
		/// </param>
		/// <param name="roleSetDir">
		/// A <see cref="System.String"/>, the directory in which to search for roleSets. If empty, the base role path will be used.
		/// </param>
		/// <param name="stepEngine">
		/// A <see cref="System.Boolean"/>: whether or not the engine should start in stepped mode (<see cref="MayStep"/>)
		/// </param>
		public void Init(string roleSetName, string masterPlanName, string roleSetDir,bool stepEngine)
		{		
			
			
			
			
			this.StepEngine = stepEngine;
			
			
			this.repository = new PlanRepository();
			this.planParser = new PlanParser(this.repository);
			
			this.masterPlan = this.planParser.ParsePlanTree(masterPlanName);
		
			this.roleSet = this.planParser.ParseRoleSet(roleSetName,roleSetDir);
			
			
			this.exprHandler = new ExpressionHandler();
			
			this.behaviourPool.Init();
			
			this.log = new Logger();
			
			this.auth = new AuthorityManager();
			
			this.exprHandler.AttachAll();

			this.teamObserver.Init();
			this.roleAssignment.Init();
			this.planSelector = new PlanSelector();
			
			
			ConstraintHelper.Init(this.cSolver);

			
			
			
			this.auth.Init();
			
			this.planBase = new PlanBase(this.masterPlan);
			
		
			UtilityFunction.InitDatastructures();
			
			this.syncModul.Init();
			Console.WriteLine("AE: Initialisation finished, standing by.");
		
		}
		public EngineTrigger OnPlanBaseIterationComplete;
		
		internal void IterationComplete() {
			if(this.OnPlanBaseIterationComplete!=null) this.OnPlanBaseIterationComplete(null);
		}
		
		/// <summary>
		/// Closes the engine for good.
		/// </summary>	
		public void Close()	{
			this.IsTerminating = true;
			this.MaySendMessages = false;
			
			if (this.behaviourPool != null) this.behaviourPool.Stop();
			if (this.planBase != null) this.planBase.Stop();			
			if (this.auth!=null) this.auth.Close();
			if (this.syncModul!=null) this.syncModul.Close();
			if (this.teamObserver!=null) this.teamObserver.Close();
			if (this.log != null) this.log.Close();			
			
		}
		/// <summary>
		/// Abort execution with a message, called if initialisation fails.
		/// </summary>
		/// <param name="msg">
		/// A <see cref="System.String"/>
		/// </param>
		public void Abort(string msg) {
			this.MaySendMessages = false;
			if(RosSharp.Ok()) {
				Node.MainNode.RosFatal(msg);				
			}
			else Console.Error.WriteLine(msg);
			Close();			
			if (RosSharp.Ok()) RosSharp.Shutdown();
			System.Environment.Exit(-1);
		}
		
		/// <summary>
		/// Returns the behaviourpool
		/// </summary>
		public IBehaviourPool BP
		{			
			get { return this.behaviourPool; }
		}
		
		/// <summary>
		/// Returns the <see cref="PlanSelector"/>
		/// </summary>		
		public IPlanSelector PS
		{
			get { return this.planSelector; }
		}
		/// <summary>
		/// Returns the parser which reads ALICAs XML representation
		/// </summary>
		public IPlanParser PP
		{
			get { return this.planParser; }
		}
		/// <summary>
		/// Returns the plan repository, which holds the static ALICA program.
		/// </summary>
		public PlanRepository PR
		{
			get { return this.repository; }
		}
		public PlanBase PB
		{
			get { return this.planBase; }
		}
		/// <summary>
		/// Returns the <see cref="TeamObserver"/>, which handles most communication tasks.
		/// </summary>
		public ITeamObserver TO {get{return this.teamObserver;}}
		
		public IRoleAssignment RA
		{
			get { return this.roleAssignment; }
		}
		/// <summary>
		/// The Logger
		/// </summary>
		public Logger Log
		{
			get {return this.log;}
		}
		
		public ISyncModul SM
		{
			get { return this.syncModul; }
		}
		
		public AuthorityManager AM
		{
			get { return this.auth; }
		}
		/*
		public IRoleSetRepository RR
		{
			get { return this.roleSetRepository; }
		}
		
		
		public IReflector ReflectorModul
		{
			get { return this.reflectorModul; }	
		}
		
		
		*/

		/// <summary>
		/// Returns the RoleSet in use.
		/// </summary>
		public RoleSet CurrentRoleSet
		{
		
			get { return this.roleSet; }
		}
		
	}
}
