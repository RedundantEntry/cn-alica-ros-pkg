using System;
using Alica;
using RosCS;
using RosCS.AlicaEngine;
using Castor;

using GLib;
using Gtk;
using Pango;

using System.Collections.Generic;
using System.Threading;
namespace AlicaClient
{
	public class RobotInfo : Gtk.Frame
	{
		protected static SystemConfig sc = SystemConfig.LocalInstance;
		protected static string fontstr = "Ahafoni CLM Normal 9";
		protected BehaviourEngineInfo statusMsg;
		
		protected bool dirty;
		protected bool valid;
		
		protected ulong robotTimeout;
		protected Label roleName;
		protected Label stateName;
		protected Label taskName;
		protected Label planName;
		protected Label robotsWithMe;
		
		protected Table content;
		
		protected FontDescription elementFont;
		public int Id {get; set;}
		public string RobotName{get; set;}
		public PlanTreeInfo CurPlanTree{get; set;}
		public ulong LastMessageTime{get; set;}
		public ulong LastPTITime{get; set;}
		public SimplePlanTree Spt{get; set;}
		
		
		public BehaviourEngineInfo CurStatusInfo {
			get { return this.statusMsg; }
			set {
				this.statusMsg = value;
				this.LastMessageTime = RosSharp.Now();
				if (String.IsNullOrEmpty(this.RobotName)) {
					this.RobotName = NameOfRobot(this.statusMsg.SenderID);
					this.Label = this.RobotName + "("+this.Id+")";
				}
				this.dirty = true;
							
			}
		}
		public void Update() {
			if (LastPTITime + this.robotTimeout < RosSharp.Now() && this.Spt!=null) {
				this.Spt = null;				
			}
			else if(!this.dirty) return;
			this.dirty = false;
			if (this.statusMsg != null) {
				this.Label = this.RobotName + "("+this.Id+")";
				if (Spt==null) this.Label+=" [silent]";
				string s = this.statusMsg.CurrentState;
				string p = this.statusMsg.CurrentPlan;
				string t = this.statusMsg.CurrentTask;
				string r = this.statusMsg.CurrentRole;
				
				if (s!=null) this.stateName.Text = s;
				if (p!=null) this.planName.Text = p;
				if (t!=null) this.taskName.Text = t; 
				if (r!=null) this.roleName.Text = r;
				string rs="";
				bool first = true;
				foreach(int rob in this.statusMsg.RobotIDsWithMe) {
					if(first) {
						first = false;
					} else {
						rs+=", ";
					}
					rs+=rob;
				}
				this.robotsWithMe.Text=rs;	
			}			
		}
		public RobotInfo (ulong timeout):base()
		{
			this.robotTimeout =timeout;
			this.dirty = false;
			//Box b = new Box();
			this.content = new Table(5,3,false);
			this.elementFont =  Pango.FontDescription.FromString(fontstr);
			
			Gtk.Alignment rlt = new Gtk.Alignment(0f,0.5f,0f,0f);
			Label rl = new Label("<b>Role:</b>");
			rl.UseMarkup = true;						
			rl.ModifyFont(this.elementFont);
			rlt.Add(rl);

			Gtk.Alignment tlt = new Gtk.Alignment(0f,0.5f,0f,0f);			
			Label tl = new Label("<b>Task:</b>");
			tl.UseMarkup = true;						
			tl.ModifyFont(this.elementFont);
			tlt.Add(tl);

			Gtk.Alignment plt = new Gtk.Alignment(0f,0.5f,0f,0f);			
			Label pl = new Label("<b>Plan:</b>");
			pl.UseMarkup = true;						
			pl.ModifyFont(this.elementFont);
			plt.Add(pl);
			
			Gtk.Alignment slt = new Gtk.Alignment(0f,0.5f,0f,0f);			
			Label sl = new Label("<b>State:</b>");
			sl.UseMarkup = true;						
			sl.ModifyFont(this.elementFont);
			slt.Add(sl);
						
			Gtk.Alignment nlt = new Gtk.Alignment(0f,0.5f,0f,0f);			
			Label nl = new Label("<b>Robots:</b>");
			nl.UseMarkup = true;						
			nl.ModifyFont(this.elementFont);
			nlt.Add(nl);

			
			this.content.Attach(rlt,0,1,0,1);			
			this.content.Attach(plt,0,1,1,2);
			this.content.Attach(tlt,0,1,2,3);
			this.content.Attach(slt,0,1,3,4);
			this.content.Attach(nlt,0,1,4,5);
			
			Gtk.Alignment rva = new Gtk.Alignment(0f,0.5f,0f,0f);
			this.roleName = new Label("---");
			this.roleName.ModifyFont(this.elementFont);
			rva.Add(this.roleName);

			Gtk.Alignment tva = new Gtk.Alignment(0f,0.5f,0f,0f);			
			this.taskName = new Label("---");
			this.taskName.ModifyFont(this.elementFont);
			tva.Add(this.taskName);

			Gtk.Alignment pva = new Gtk.Alignment(0f,0.5f,0f,0f);
			this.planName = new Label("---");
			this.planName.ModifyFont(this.elementFont);
			pva.Add(this.planName);
			
			Gtk.Alignment sva = new Gtk.Alignment(0f,0.5f,0f,0f);
			this.stateName = new Label("---");
			this.stateName.ModifyFont(this.elementFont);
			sva.Add(this.stateName);

			Gtk.Alignment wva = new Gtk.Alignment(0f,0.5f,0f,0f);
			this.robotsWithMe = new Label("");
			this.robotsWithMe.ModifyFont(this.elementFont);
			wva.Add(this.robotsWithMe);
			
			this.content.Attach(rva,1,2,0,1);			
			this.content.Attach(pva,1,2,1,2);
			this.content.Attach(tva,1,2,2,3);
			this.content.Attach(sva,1,2,3,4);
			this.content.Attach(wva,1,2,4,5);
			
			this.Add(content);
			this.valid = true;
			
		}
		protected static string NameOfRobot(int id) {
			string[] names = sc["Globals"].GetSections("Globals","Team");
			foreach(string name in names) {
				if(sc["Globals"].GetInt("Globals","Team",name,"ID") == id) {
					return name;
				}
			}
			return "Unknown RobotID";
		}
		public void Invalidate() {
			Console.WriteLine("Robot {0} is invalidated",this.RobotName);
			this.valid = false;
		}
	}
}

