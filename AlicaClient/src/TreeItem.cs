using System;
using System.Collections.Generic;
using Alica;

using Gtk;
using Gdk;
using Pango;
using Cairo;

namespace AlicaClient
{
	public abstract class TreeItem
	{
		public List<TreeItem> Children {get; set;}
		public Cairo.Color Color {get; set;}
		public Cairo.Color TextColor{get; set;}
		
		public TreeItem() {
			this.Color = new Cairo.Color(1.0,0,0);
			this.Children = new List<TreeItem>();
			this.sizex = 100;
			this.sizey = 100;
			
		}
		protected double sizex;
		protected double sizey;

		public double GetWidth() {return sizex;}
		public double GetHeight() {return sizey;}
		
		protected double childX;
		protected double childY;
		public virtual void SetChildDrawPoint(double x, double y) {
			this.childX=x;
			this.childY=y;
		}
		//public abstract void SetRobot(int r,SimplePlanTree spt);
			
		public virtual void Update(List<SimplePlanTree> spts) {
			foreach(TreeItem t in this.Children) {
				t.Update(spts);
			}
		}
		public virtual bool MouseOver(double x, double y) {
			bool ret = false;
			foreach(TreeItem t in this.Children) {
				ret |= t.MouseOver(x,y);
			}
			return ret;
		}
		public virtual void DrawTo(Gdk.Window win, Cairo.Context g) {
Console.WriteLine("Tree Draw");			
			g.Arc(0,0,10,0,2*Math.PI);
			g.LineWidth = 1.5;
			g.Color = this.Color;
			
			g.Stroke();
			
			
			g.Save();
			double x = -(this.Children.Count/2)*20.0;
			g.Translate(x,40);
			foreach(TreeItem t in this.Children) {
				t.DrawTo(win,g);
				g.Translate(20,0);				
			}
			g.Restore();
		}
		public virtual string Description() {
			return "TreeItem";
		}
		
	}
}

