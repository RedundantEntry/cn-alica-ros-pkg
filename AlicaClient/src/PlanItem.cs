using System;
using System.Collections.Generic;
using Alica;
using RosCS;
using Castor;
namespace AlicaClient
{
	
	public class PlanItem : TreeItem
	{
		
		public AbstractPlan AbstractPlan { get; private set; }
		public static double EntryPointDistance = 5;
		public static double EntryPointYSize = 20;
		public static double StateDistance = 10;
		
		protected double minxUI,minyUI,maxxUI,maxyUI;
		
		
		protected static ulong allocationValidTime = 1000000UL*SystemConfig.LocalInstance["Alica"].GetULong("Alica","CycleDetection","MessageWaitTimeInterval");
		
		protected Cairo.Color AuthorityColor;
		protected bool IsAuthority() {
			ulong time;
			if(AlicaClient.Get().AuthorityTimes.TryGetValue(this.AbstractPlan.Id,out time)) {
				return RosSharp.Now() < time+allocationValidTime;
			}
			return false;
		}
		
		public PlanItem(Plan p,List<SimplePlanTree> spts) :base() {
			this.AbstractPlan = p;
			List<EntryPoint> entrypoints = new List<EntryPoint>();
			foreach(SimplePlanTree spt in spts) {
				if (!entrypoints.Contains(spt.EntryPoint)) {
					entrypoints.Add(spt.EntryPoint);
				}
			}
			foreach(EntryPoint e in entrypoints) {
				List<SimplePlanTree> spte = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in spts) {
					if (spt.EntryPoint == e) {
						spte.Add(spt);
					}
				}
				EntryPointItem ei = new EntryPointItem(e,spte);
				this.Children.Add(ei);
				this.AuthorityColor = new Cairo.Color(0.6,0.1,0.1);
				this.Color = new Cairo.Color(0.1,0.1,0.6);
			}			
		}
		public override void Update (List<SimplePlanTree> spts)
		{
			List<TreeItem> toremove = new List<TreeItem>();
			List<SimplePlanTree> lower = new List<SimplePlanTree>();
			foreach(TreeItem t in this.Children) {
				EntryPointItem e = (EntryPointItem) t;
				lower.Clear();
				bool found = false;
				foreach(SimplePlanTree spt in spts) {
					if (spt.EntryPoint == e.EntryPoint) {
						found = true;
						lower.Add(spt);						
					}
				}
				if(!found) {
					toremove.Add(t);
				} else {
					e.Update(lower);
				}
				
			}
			foreach(TreeItem t in toremove) this.Children.Remove(t);
			List<EntryPoint> neweps = new List<EntryPoint>();
			foreach(SimplePlanTree spt in spts) {
				bool found = false;
				foreach(TreeItem c in this.Children) {
					EntryPointItem e = (EntryPointItem)c;
					if (e.EntryPoint == spt.EntryPoint) {
						found = true;
						break;
					}					
				}
				if (!found && !neweps.Contains(spt.EntryPoint)) neweps.Add(spt.EntryPoint);
			}
			foreach(EntryPoint ep in neweps) {
				List<SimplePlanTree> sptp = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in spts) {
					if (spt.EntryPoint == ep) {
						sptp.Add(spt);
					}
				}
				EntryPointItem pi = new EntryPointItem(ep,sptp);
				this.Children.Add(pi);
			}
		}

		
		protected int MaxNumStates() {
			int ret = 0;
			foreach(TreeItem t in this.Children) {
				ret = Math.Max(ret,t.Children.Count);
			}
			return ret;
		}
		public override void DrawTo(Gdk.Window win, Cairo.Context g) {
			//Console.WriteLine("Plan Draw");
			double a =0;
			double b =0;
			g.TransformPoint(ref a,ref b);
			this.minxUI = a;
			this.minyUI = b;
			Cairo.TextExtents te = g.TextExtents(this.AbstractPlan.Name);
			g.Save();
			g.MoveTo(5,te.Height+5);
			
			g.LineWidth = 1.5;
			g.Color = this.Color;	
			g.ShowText(this.AbstractPlan.Name);			
			g.NewPath();
			
			g.Translate(5,te.Height+10);
			
			double xmax = 0;
			double ymax = 0;

			foreach(TreeItem t in this.Children) {
				
				t.DrawTo(win,g);
				double yt = t.GetHeight();
				g.Translate(0,EntryPointDistance+yt);
				xmax = Math.Max(xmax,t.GetWidth());
				ymax += yt+EntryPointDistance;
			}
			g.Restore();
			g.LineWidth = 1.5;
			g.Color = (IsAuthority()?this.AuthorityColor:this.Color);
			
			this.sizex = Math.Max(xmax+10,te.Width+10);
			this.sizey = ymax + 15+te.Height;
			g.Rectangle(0,0,this.sizex,this.sizey);
			g.Stroke();
			
			a = this.sizex;
			b = this.sizey;
			g.TransformPoint(ref a, ref b);
			this.maxxUI = a;
			this.maxyUI = b;
			
		}
		
		public override bool MouseOver(double x, double y) {		
			bool ret = false;
			if (x >= this.minxUI && x <= this.maxxUI && y >= this.minyUI && y <= this.maxyUI) {
				AlicaClient.Get().HoveredPlan = this.AbstractPlan;
				//Console.WriteLine("Mouse over "+this.AbstractPlan.Name);
				ret = true;
			} else {
				if (AlicaClient.Get().HoveredPlan == this.AbstractPlan) AlicaClient.Get().HoveredPlan = null;
			}
			foreach(TreeItem t in this.Children) {
				ret |= t.MouseOver(x,y);
			}
			return ret;
		}
		public override string Description() {
			return "Plan: "+this.AbstractPlan.Name;
		}
		
	}
	
}

