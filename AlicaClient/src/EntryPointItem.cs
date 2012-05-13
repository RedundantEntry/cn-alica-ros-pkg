using System;
using System.Collections.Generic;
using Alica;
namespace AlicaClient
{
	public class EntryPointItem : TreeItem
	{
		
		public EntryPoint EntryPoint { get;private set; }
		
		
		public EntryPointItem(EntryPoint e,List<SimplePlanTree> spts) :base() {
			this.EntryPoint = e;
			List<State> states = new List<State>();
			foreach(SimplePlanTree spt in spts) {
				if (!states.Contains(spt.State)) {
					states.Add(spt.State);
				}
			}
			foreach(State s in states) {
				List<SimplePlanTree> spte = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in spts) {
					if (spt.State == s) {
						spte.Add(spt);
					}
				}
				StateItem si = new StateItem(s,spte);
				this.Children.Add(si);
			}
			this.Color = new Cairo.Color(0.2,0.6,0.2);
			this.TextColor = new Cairo.Color(0,0,0);
			this.sizey = PlanItem.EntryPointYSize;			
		}
		public override void Update (List<SimplePlanTree> spts)
		{
			List<TreeItem> toremove = new List<TreeItem>();
			List<SimplePlanTree> lower = new List<SimplePlanTree>();
			foreach(TreeItem t in this.Children) {
				StateItem s = (StateItem) t;
				lower.Clear();
				bool found = false;
				foreach(SimplePlanTree spt in spts) {
					if (spt.State == s.State) {
						found = true;
						lower.Add(spt);						
					}
				}
				if(!found) {
					toremove.Add(t);
				} else {
					s.Update(lower);
				}
				
			}
			foreach(TreeItem t in toremove) this.Children.Remove(t);
			List<State> newstates = new List<State>();
			foreach(SimplePlanTree spt in spts) {
				bool found = false;
				foreach(TreeItem c in this.Children) {
					StateItem s = (StateItem)c;
					if (s.State == spt.State) {
						found = true;
						break;
					}					
				}
				if (!found && !newstates.Contains(spt.State)) newstates.Add(spt.State);
			}
			foreach(State s in newstates) {
				List<SimplePlanTree> sptp = new List<SimplePlanTree>();
				foreach(SimplePlanTree spt in spts) {
					if (spt.State == s) {
						sptp.Add(spt);
					}
				}
				StateItem pi = new StateItem(s,sptp);
				this.Children.Add(pi);
			}
		}

		public override void DrawTo(Gdk.Window win, Cairo.Context g) {
			//Console.WriteLine("EP Draw");
			g.Save();
			//double ymax = PlanItem.EntryPointYSize;
			//double xmax = Math.Max(20,(this.Children.Count+1)*StDistance+10);
			g.LineWidth = 1.5;
			g.Color = this.Color;	
			
			Cairo.TextExtents te = g.TextExtents(this.EntryPoint.Task.Name);
			g.Rectangle(0,0,te.Width+5,te.Height+4);
			g.Fill();
			
			g.MoveTo(2,te.Height+2);
			g.Color = this.TextColor;
			g.ShowText(this.EntryPoint.Task.Name);
			
			
			g.Stroke();
			
			g.NewPath();
			double xmaxep = 0;
			//g.Restore();
			g.Translate(te.Width+5+PlanItem.StateDistance,0);
			foreach(TreeItem t in this.Children) {
				
				t.DrawTo(win,g);
				double xt = t.GetWidth();
				xmaxep += xt;
				this.sizey = Math.Max(this.sizey,t.GetHeight());
				
				g.Translate(xt+PlanItem.StateDistance,0);				
				
			
			}
			this.sizex = te.Width+5+xmaxep+PlanItem.StateDistance*this.Children.Count;			
			g.Restore();
		}
		
	}

	
}

