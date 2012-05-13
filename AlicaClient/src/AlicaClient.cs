using System;
using Alica;
using RosCS;
using RosCS.AlicaEngine;
using Castor;

using GLib;
using Gtk;
using GConf;
using System.Collections.Generic;
using System.Threading;


namespace AlicaClient
{
	public class TimedSolverVar {
			public long Id {get; set;}
			public ulong Timestamp {get;set;}
			public double Value {get;set;}
			public string Name {get; private set;}
			public bool IsDomainVar{get; private set;}
			public int RobotId{get; private set;}
			public TimedSolverVar(long id, double val) {
				this.IsDomainVar = !AlicaClient.Get().Repository.Variables.ContainsKey(id);
				this.Value = val;
				this.Id = id;
				this.Timestamp = RosSharp.Now();

				if (this.IsDomainVar) {
					this.RobotId = (int)(id >> 32);
					int hash = (int)(id&0xFFFFFFFF);
					foreach(Quantifier q in AlicaClient.Get().Repository.Quantifiers.Values) {
						foreach(string s in q.DomainIdentifiers) {
							if (s.GetHashCode()==hash) {
								this.Name = s;
								return;
							}
						}
					}
				}
			}
	}
	
	public class AlicaClient : Gtk.Window
	{
		protected static string id = "AlicaClient";
		static string GCONF_APP_PATH = "/apps/AlicaClient";
		static string GCONF_WIDTH = GCONF_APP_PATH+"/width";
		static string GCONF_HEIGHT = GCONF_APP_PATH+"/height";
		static string GCONF_X = GCONF_APP_PATH+"/xpos";
		static string GCONF_Y = GCONF_APP_PATH+"/ypos";
		static string GCONF_SCALING = GCONF_APP_PATH+"/scaling";
		
		public PlanRepository Repository {get; private set;}
		protected Plan masterPlan;
		protected PlanParser planParser;
		//protected RoleSet roleSet;
		protected VBox leftInfoHolder;
		protected CairoCanvas drawingArea;
		
		protected Dictionary<int,RobotInfo> robots;
		protected Node rosNode;
		protected System.Threading.Timer timer;
		
		protected bool isRunning;
		protected ulong solverValidityThreshold;
		protected ulong robotTimeout;
		protected List<RobotInfo> toAdd;
		protected List<RobotInfo> toDel;
		
		protected GConf.Client gcclient;
		protected PlanDetails planDetails;
		
		protected Dictionary<int,List<TimedSolverVar>> constraintResults;
		public Dictionary<long,ulong> AuthorityTimes {get; private set;}
		protected Statusbar status;
		
		
		private static AlicaClient instance = null;
		
		public AbstractPlan HoveredPlan{get; set;}
		
		public static AlicaClient Get() {
			return instance;
		}
		public static AlicaClient Get(string masterplan) {
			if (instance == null) {
				instance = new AlicaClient(masterplan);
			}
			return instance;
		}
		
		protected AlicaClient(string masterPlanName):base(Gtk.WindowType.Toplevel)//id)//, string roleSet, string roleSetDir):base(id)
		{
			InitRepository(masterPlanName);
			this.isRunning = true;
			this.Title = id;
			this.robots = new Dictionary<int, RobotInfo>();
			this.toAdd = new List<RobotInfo>();
			this.toDel = new List<RobotInfo>();
			this.constraintResults = new Dictionary<int, List<TimedSolverVar>>();
			this.AuthorityTimes = new Dictionary<long, ulong>();
			
			HPaned hpane = new HPaned();
			
			
			
			ScrolledWindow scLeft = new ScrolledWindow();
			ScrolledWindow scRight = new ScrolledWindow();
			
			scLeft.SetSizeRequest(160,-1);
			this.leftInfoHolder = new VBox(true,0);
			this.leftInfoHolder.Spacing = 0;
			
			scLeft.AddWithViewport(leftInfoHolder);
			
			/*
			scLeft.ShadowType = ShadowType.None;
			scRight.ShadowType = ShadowType.None;
			 */
			this.drawingArea = new CairoCanvas();
			
			this.planDetails = new PlanDetails();
			/*
			VPaned vpane = new VPaned();
			vpane.Add1(this.drawingArea);
			vpane.Add2(this.planDetails);
			scRight.AddWithViewport(vpane);
			*/
			VBox vbr = new VBox();
			vbr.PackStart(this.drawingArea,true,true,0);
			vbr.PackEnd(this.planDetails,false,false,0);
			scRight.AddWithViewport(vbr);
			
			/*
			VBox vb = new VBox(false,0);
			vb.Add(this.drawingArea);
			vb.Add(this.planDetails);
			scRight.AddWithViewport(vb);
			*/
			
			
			hpane.Add1(scLeft);
			hpane.Add2(scRight);
			
			
			//this.Add(hpane);
			
			status = new Gtk.Statusbar();
			status.HasResizeGrip = true;
			
			
			VBox vb = new VBox(false,0);
			vb.PackStart(hpane,true,true,0);
			vb.PackEnd(status,false,false,0);
			this.Add(vb);
			
			
			
			DeleteEvent += new DeleteEventHandler(OnDelete);
			
			this.timer = new System.Threading.Timer(Update, null, 100, 100);
			int width = 640;
			int height = 480;
			int xpos = 0;
			int ypos = 0;
			double scale = 1;
			//GConf:
			this.gcclient = new GConf.Client();
			
			try {
				scale  = (double)gcclient.Get(GCONF_SCALING);
			}
			catch {
				this.gcclient.Set(GCONF_SCALING,1);
			}
				this.drawingArea.ScalingFactor = scale;
			try {
				width = (int)gcclient.Get(GCONF_WIDTH);
				height =(int)gcclient.Get(GCONF_HEIGHT);
			} catch {
				gcclient.Set(GCONF_WIDTH,width);
				gcclient.Set(GCONF_HEIGHT,height);
			}
			
			this.DefaultSize = new Gdk.Size(width,height);			
			
			ShowAll();
			this.GetPosition(out xpos, out ypos);
			try {
				xpos = (int)gcclient.Get(GCONF_X);
				ypos =(int)gcclient.Get(GCONF_Y);

			} catch {
				gcclient.Set(GCONF_X,xpos);
				gcclient.Set(GCONF_Y,ypos);
			}
			int sx,sy;
			GdkWindow.Parent.GetSize(out sx,out sy);
			bool changed = false;
			if (xpos+width > sx) {
				xpos = sx-width;
				if (xpos < 0) {
					width = sx;
					xpos = 0;
					changed = true;
					gcclient.Set(GCONF_WIDTH,width);
				}
				gcclient.Set(GCONF_X,xpos);				
			}
			if (ypos+height > sy) {
				ypos = sy-height;
				if (ypos < 0) {
					height = sy;
					ypos = 0;
					changed = true;
					gcclient.Set(GCONF_HEIGHT,height);
				}
				gcclient.Set(GCONF_Y,ypos);
			}
			if (changed) {
				this.Resize(width,height);	
			}
			this.Move(xpos,ypos);
			//end Gconf
			
		}
		public void InitRos() {
			SystemConfig sc = SystemConfig.LocalInstance;
			this.solverValidityThreshold = 1000000UL*sc["Alica"].GetULong("Alica","CSPSolving","SeedTTL4Usage");
			
			this.robotTimeout = (ulong)(sc["Alica"].GetDouble("Alica","TeamTimeOut")*1000000.0);
			
			
			
			this.rosNode = Node.MainNode;
			this.rosNode.Subscribe("AlicaEngine/BehaviourEngineInfo",OnBehaviourEngineInfo,12);
			this.rosNode.Subscribe("AlicaEngine/PlanTreeInfo",OnPlanTreeInfo,12);
			this.rosNode.Subscribe("AlicaEngine/SolverResult",OnSolverResult,12);
			this.rosNode.Subscribe("AlicaEngine/AllocationAuthorityInfo",OnAllocationAuthority,12);
		}
		public void OnAllocationAuthority(AllocationAuthorityInfo aai) {
			if (this.AuthorityTimes.ContainsKey(aai.PlanID)) {
				this.AuthorityTimes[aai.PlanID] = RosSharp.Now();
			}
			else this.AuthorityTimes.Add(aai.PlanID,RosSharp.Now());			
		}
		public void OnSolverResult(SolverResult sr) {
			lock(this.constraintResults) {
				List<TimedSolverVar> sols;
				if(!this.constraintResults.TryGetValue(sr.SenderID,out sols)) {
					sols = new List<TimedSolverVar>();
					this.constraintResults.Add(sr.SenderID,sols);
				}
				ulong now = RosSharp.Now();
				foreach(SolverVar v in sr.Vars) {
					bool found = false;
					foreach(TimedSolverVar tsv in sols) {
						if(tsv.Id == v.Id) {
							tsv.Timestamp = now;
							tsv.Value = v.Value;
							found = true;
							break;
						}
					}
					if(!found) sols.Add(new TimedSolverVar(v.Id,v.Value));
				}
				for(int i=0; i<sols.Count; i++) {
					if(sols[i].Timestamp + solverValidityThreshold < now) {
						sols.RemoveAt(i);
						i--;
					}
				}
			}
		}
		public void OnPlanTreeInfo(PlanTreeInfo message) {
			RobotInfo rob = null;
			if (this.robots.TryGetValue(message.SenderID,out rob)) {
				SimplePlanTree s = SptFromMessage(message.SenderID,message.StateIDs);
				rob.LastPTITime = RosSharp.Now();
				rob.Spt = s;	
				//this.drawingArea.QueueDraw();
			} 
			
			
		}
		public void OnBehaviourEngineInfo(BehaviourEngineInfo message) {
			RobotInfo rob = null;
			if (this.robots.TryGetValue(message.SenderID,out rob)) {
				rob.CurStatusInfo = message;
			} else {
				rob = new RobotInfo(this.robotTimeout);
				rob.Id = message.SenderID;
				rob.CurStatusInfo = message;
				lock(this.robots) {
					this.robots.Add(message.SenderID,rob);
				}
				lock(this.toAdd) {
					this.toAdd.Add(rob);				
				}
			}			
		}
		protected void AddRobot(RobotInfo rob) {
			
			this.leftInfoHolder.PackStart(rob, false, false, 0);			
					
			int i = 0;
			lock(this.robots) {
				foreach (RobotInfo r in this.robots.Values)	{
					if (String.Compare(rob.RobotName, r.RobotName) < 0)	{
						i++;
					} else break;
				
				}
			}
			this.leftInfoHolder.ReorderChild(rob, i);
			
			this.leftInfoHolder.ShowAll();
		}
		protected void DeleteRobot(RobotInfo rob) {
			this.leftInfoHolder.Remove(rob);
			this.leftInfoHolder.ShowAll();
		}

		protected void InValidateRobots() {			
			
				ulong now = RosSharp.Now();
				List<RobotInfo> toRemove = new List<RobotInfo>();
				lock(this.robots) {
					foreach(RobotInfo r in this.robots.Values) {
						if (r.LastMessageTime + this.robotTimeout < now) {
							toRemove.Add(r);
						}
					}
					foreach(RobotInfo r in toRemove) {
						r.Invalidate();
						this.robots.Remove(r.Id);
						DeleteRobot(r);
						
					}
				}
				if (toRemove.Count > 0) {
					this.drawingArea.QueueDraw();
				}
				
		}
		#region Model
		protected void InitRepository(string masterPlanName) {
			this.Repository = new PlanRepository();
			this.planParser = new PlanParser(this.Repository);	
			if (String.IsNullOrEmpty(masterPlanName)) {
				this.planParser.ParseAllPlans();
			} else {
				this.masterPlan = this.planParser.ParsePlanTree(masterPlanName);
			}
			//this.roleSet = this.planParser.ParseRoleSet(roleSetName,roleSetDir);
		}
		#endregion
		#region Gui handler
		protected void Update(object o) {
				Application.Invoke(DoUpdate);
		}
		protected void DoUpdate(object sender, EventArgs a) {
			InValidateRobots();			
			lock(this.toAdd) {
				foreach(RobotInfo r in this.toAdd) {
					AddRobot(r);
				}
				toAdd.Clear();
			}
			List<SimplePlanTree> spt = new List<SimplePlanTree>();
			lock(this.robots) {
				foreach(RobotInfo r in this.robots.Values) {
					r.Update();
					if(r.Spt!=null) {
						spt.Add(r.Spt);
					}
				}
			}
			if (this.drawingArea.Tree == null) {				
				this.drawingArea.Tree= new NodeItem(spt);
			} else {
				this.drawingArea.Tree.Update(spt);
			}
			lock(this.constraintResults) {
				this.planDetails.Update(this.HoveredPlan,this.constraintResults);
			}
			this.status.Pop(1);
			this.status.Push(1,String.Format("{0} Robot(s) known",this.robots.Count));
			this.drawingArea.QueueDraw();
		}
		protected void OnDelete(object sender, DeleteEventArgs args) {				
			this.isRunning = false;
			if(this.gcclient != null) {
				int h;
				int w;
				int x;
				int y;
					
				this.GetSize(out w,out h);
				this.GetPosition(out x,out y);
				gcclient.Set(GCONF_WIDTH,w);
				gcclient.Set(GCONF_HEIGHT,h);
				gcclient.Set(GCONF_X,x);
				gcclient.Set(GCONF_Y,y);
				gcclient.Set(GCONF_SCALING,this.drawingArea.ScalingFactor);
				
			}
			Application.Quit();
			RosSharp.Shutdown();
			args.RetVal = true;			
		}
		#endregion
		public static void Main (string[] args)
		{
			Arguments a = new Arguments();
		
		
			a.SetOption("?masterplan|m!", "Set the MasterPlanName to use (optional)");
			
			
			try {
				a.Consume(args);
			} catch (Exception e) {
				Console.WriteLine(e.Message);
				Console.WriteLine(a.ToString());				
				Environment.Exit(1);
			}
			/*
			string rolesetDir = "";
			if(a.OptionIsSet("rolesetdir"))	{
				List<string> rolesets = a.GetOptionValues("rolesetdir");
				if(rolesets.Count !=0)	{
					rolesetDir = rolesets[0];
				}
			}
			else {
				Console.WriteLine("\nYou need to specify -rolesetdir <RolesetDirectory>\n");				
				Environment.Exit(-1);
			}
			string roleSet = "";					
			if(a.OptionIsSet("roleset")){
				List<string> roleSets = a.GetOptionValues("roleset");
				if(roleSets.Count !=0)	{
					roleSet = roleSets[0];
				}
			}*/
			
			string masterPlanName = "";			
			if(a.OptionIsSet("masterplan"))	{
				List<string> masterPlans = a.GetOptionValues("masterplan");
				masterPlanName = masterPlans[0];
			}
			
			Application.Init();
			RosSharp.Init("AlicaClient",args,RosSharp.NoSigIntHandler);			
			AlicaClient ac = AlicaClient.Get(masterPlanName);
			ac.InitRos();
			Application.Run();	
			
			
			
			
		}
		protected SimplePlanTree SptFromMessage(int robotId, List<long> ids) {
			
			if (ids.Count == 0) {
				Console.Error.WriteLine("TO: Empty state list for robot {0}",robotId);
				return null;
			}
			Dictionary<long,State> states = this.Repository.States;
			ulong time = RosSharp.Now();
			SimplePlanTree root = new SimplePlanTree();
			root.RobotID = robotId;
			root.ReceiveTime = time;
			root.StateIDs = ids;
			State s;
			if(states.TryGetValue(ids[0],out s) ){
				root.State = s;
				root.EntryPoint = EntryPointOfState(s);
				
				if(root.EntryPoint == null) {
					Console.Error.WriteLine("TO: Cannot find ep for State ({0}) received from {1}",ids[0],robotId);
					return null;
				}
			} else {
				Console.Error.WriteLine("TO: Unknown State ({0}) received from {1}",ids[0],robotId);
				return null;
			}
			
			
			SimplePlanTree curParent = null;
			SimplePlanTree cur = root;
			
			
			for(int i=1; i<ids.Count; i++) {
				if (ids[i] == -1) {
					curParent = cur;
					cur = null;
				} else if(ids[i] == -2) {
					cur = curParent;
					if(cur==null) {
						Console.Error.WriteLine("TO: Malformed SptMessage from {0}",robotId);
						return null;
					}				
				} else {
					cur = new SimplePlanTree();
					cur.RobotID = robotId;	
					cur.ReceiveTime = time;
					curParent.AddChild(cur);					
					if(states.TryGetValue(ids[i],out s) ){
						cur.State = s;
						cur.EntryPoint = EntryPointOfState(s);						
						if(cur.EntryPoint == null) {
							Console.Error.WriteLine("TO: Cannot find ep for State ({0}) received from {1}",ids[i],robotId);
							return null;
						}
					} else {
						Console.Error.WriteLine("TO: Unknown State ({0}) received from {1}",ids[i],robotId);
						return null;
					}
				}
			}
			
			
			return root;
		}
		protected EntryPoint EntryPointOfState(State s) {
			foreach(EntryPoint ep in s.InPlan.EntryPoints.Values) {
				if (ep.ReachableStates.Contains(s)) return ep;
			}
			return null;
		}
	}
}

