using System;
using System.Collections.Generic;
using Alica;
namespace AlicaClient
{
	public class StateItem : TreeItem
	{
		
		public State State { get; private set; }
		List<int> Robots {get; set;}
		public Cairo.Color RobotColor{get; set;}
		public StateItem(State s,List<SimplePlanTree> spts) :base() {
			this.State = s;
			this.Robots = new List<int>();
			List<SimplePlanTree> subtrees = new List<SimplePlanTree>();
			foreach(SimplePlanTree spt in spts) {
				this.Robots.Add(spt.RobotID);
				subtrees.AddRange(spt.Children);
			}
			List<Plan> plans = new List<Plan>();
			foreach(SimplePlanTree spt in subtrees) {
				Plan pta = spt.State.InPlan;
				if (!plans.Contains(pta)) {
					plans.Add(pta);
				}
			}
			foreach(Plan p in plans) {
				List<SimplePlanTree> sptp = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in subtrees) {
					if (spt.State.InPlan == p) {
						sptp.Add(spt);
					}
				}
				PlanItem pi = new PlanItem(p,sptp);
				this.Children.Add(pi);
			}
			this.Color = new Cairo.Color(0.7,0.7,0.2);
			this.TextColor = new Cairo.Color(0.0,0.0,0.0);
			this.RobotColor = new Cairo.Color(0.6,0.6,0.8);			
		}
		public override void Update (List<SimplePlanTree> spts)
		{
			List<SimplePlanTree> subtrees = new List<SimplePlanTree>();
			this.Robots.Clear();
			foreach(SimplePlanTree spt in spts) {
				this.Robots.Add(spt.RobotID);
				subtrees.AddRange(spt.Children);
			}
			
			List<TreeItem> toremove = new List<TreeItem>();			
			List<SimplePlanTree> lower = new List<SimplePlanTree>();
			foreach(TreeItem c in this.Children) {
				PlanItem p = (PlanItem)c;
				lower.Clear();
				bool found = false;
				for(int i=0; i<subtrees.Count; i++) {
					if(subtrees[i].State.InPlan == p.AbstractPlan) {
						found = true;
						lower.Add(subtrees[i]);
					}
				}
				if (!found) {					
					toremove.Add(c);
				}
				else p.Update(lower);
			}
			foreach(TreeItem t in toremove) {
				this.Children.Remove(t);
			}
			List<Plan> newplans = new List<Plan>();
			foreach(SimplePlanTree spt in subtrees) {
				bool found = false;
				foreach(TreeItem c in this.Children) {
					PlanItem p = (PlanItem)c;
					if (p.AbstractPlan == spt.State.InPlan) {
						found = true;
						break;
					}					
				}
				if (!found && !newplans.Contains(spt.State.InPlan)) newplans.Add(spt.State.InPlan);
			}
			foreach(Plan p in newplans) {
				List<SimplePlanTree> sptp = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in subtrees) {
					if (spt.State.InPlan == p) {
						sptp.Add(spt);
					}
				}
				PlanItem pi = new PlanItem(p,sptp);
				this.Children.Add(pi);
			}	
		}

		public override void DrawTo(Gdk.Window win, Cairo.Context g) {
			//Console.WriteLine("State Draw");
			Cairo.TextExtents te = g.TextExtents(this.State.Name);
			g.Save();
			
			g.Color = this.Color;	
			this.sizex = te.Width+4;
			this.sizey = te.Height+4;
			g.Color = this.Color;
			g.Rectangle(0,0,this.sizex,this.sizey);
			g.Fill();
			
			g.LineWidth = 1.5;			
			g.Translate(2,te.Height+2);
			g.Color = this.TextColor;
			g.ShowText(this.State.Name);			
			
			
			g.Translate(0,15);
			foreach(int r in this.Robots) {
				g.Color = this.TextColor;
				Cairo.TextExtents tr = g.TextExtents(r.ToString());
				g.MoveTo(0,0);
				g.MoveTo(-tr.Width/2,tr.Height/2);
				g.ShowText(r.ToString());
				g.Color = this.RobotColor;
				g.NewPath();
				g.Arc(0,0,8,0,2*Math.PI);
				g.Stroke();
				g.Translate(18,0);			
			}
			this.sizey +=30;
			this.sizex = Math.Max(this.Robots.Count*18,this.sizex);
			
			
		//	double xmax = 0;
		//	double ymax = 0;
			
			/*
			foreach(TreeItem t in this.Children) {
				t.DrawTo(win,g);
				double yt = t.GetHeight();
				g.Translate(0,EntryPointDistance+yt);
				xmax = Math.Max(xmax,t.GetWidth());
				ymax += yt+EntryPointDistance;
			}*/
			g.Restore();
			g.LineWidth = 1.5;
			
			
			
		}

	}	
	
	
}

