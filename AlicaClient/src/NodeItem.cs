using System;
using System.Collections.Generic;
using Alica;
namespace AlicaClient
{
	
	public class NodeItem : TreeItem
	{
		
		protected static double PlanDistance = 25;
		
		public NodeItem(List<SimplePlanTree> spts) :base() {
			
			List<Plan> plans = new List<Plan>();
			foreach(SimplePlanTree spt in spts) {
				Plan pta = spt.State.InPlan;
				if (!plans.Contains(pta)) {
					plans.Add(pta);
				}
			}
			foreach(Plan p in plans) {
				List<SimplePlanTree> sptp = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in spts) {
					if (spt.State.InPlan == p) {
						sptp.Add(spt);
					}
				}
				PlanItem pi = new PlanItem(p,sptp);
				this.Children.Add(pi);
			}	
			
		}
		public override void Update(List<SimplePlanTree> spts) {
			List<TreeItem> toremove = new List<TreeItem>();			
			List<SimplePlanTree> lower = new List<SimplePlanTree>();
			foreach(TreeItem c in this.Children) {
				PlanItem p = (PlanItem)c;
				lower.Clear();
				bool found = false;
				for(int i=0; i<spts.Count; i++) {
					if(spts[i].State.InPlan == p.AbstractPlan) {
						found = true;
						lower.Add(spts[i]);						
					}
				}
				if (!found) toremove.Add(c);
				else p.Update(lower);
			}
			foreach(TreeItem t in toremove) {
				this.Children.Remove(t);
			}
			List<Plan> newplans = new List<Plan>();
			foreach(SimplePlanTree spt in spts) {
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
				foreach(SimplePlanTree spt in spts) {
					if (spt.State.InPlan == p) {
						sptp.Add(spt);
					}
				}
				PlanItem pi = new PlanItem(p,sptp);
				this.Children.Add(pi);
			}	
			
		}
		protected void DrawPlanList(List<TreeItem> list, Gdk.Window win, Cairo.Context g, double curXoffSet) {
			g.Save();
			double xplus = 0;
			double planX = curXoffSet;
			foreach(TreeItem t in list) {
				g.Save();
				g.Translate(curXoffSet,0);	
//Console.WriteLine("Drawing "+t.Description());				
				t.DrawTo(win,g);
				
				
				
				
				g.Save();
				
				g.Translate(0,t.GetHeight()+PlanDistance);
				curXoffSet = 0;
				foreach(TreeItem tep in t.Children) {
					foreach(TreeItem ts in tep.Children) {
//Console.WriteLine("Rec on "+t.Description());						
						DrawPlanList(ts.Children,win,g,curXoffSet);
						foreach(TreeItem tp in ts.Children) {
							curXoffSet += PlanDistance+tp.GetWidth();
						}
//Console.WriteLine("End Rec on "+t.Description());						
						
					}
				}	
				g.Restore();
				g.Restore();
				xplus = 0;
				foreach(TreeItem tep in t.Children) {
					foreach(TreeItem ts in tep.Children) {
						foreach(TreeItem tp in ts.Children) {
							xplus += PlanDistance+tp.GetWidth();					
							g.MoveTo(t.GetWidth()/2+planX,t.GetHeight());
							g.RelLineTo(xplus-tp.GetWidth()/2-t.GetWidth()/2-PlanDistance,PlanDistance);
							g.Stroke();
						}
					}
				}
				g.Translate(Math.Max(t.GetWidth()+PlanDistance,xplus),0);
			}
			g.Restore();
		}
		public override void DrawTo(Gdk.Window win, Cairo.Context g) {
			DrawPlanList(this.Children,win,g,0);

		}
		
		
	}
	
}

