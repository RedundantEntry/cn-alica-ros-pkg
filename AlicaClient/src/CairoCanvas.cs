

using System;
using System.Collections;
using System.Collections.Generic;
using System.Threading;
using Gtk;
using Gdk;
using Pango;
using Cairo;
using Alica;

namespace AlicaClient {
    
    public class CairoCanvas : Gtk.DrawingArea {
      
        protected Cairo.Surface surface = null;
		protected int x;
		protected int y;
		protected int width;
		protected int height;
		protected int depth;
		protected double preScalingFactor = 1.0;
		protected double scalingFactor = 1.0;
		protected double scrollFactor = 1.1;
		protected bool modified = false;
		
		protected double xtrans = 0;
		protected double ytrans = 0;
		
		protected bool indrag = false;
		protected double xdragStart;
		protected double ydragStart;
		
        public CairoCanvas()
        {
			this.preScalingFactor = 1;
			this.CanFocus = true;
            this.Events |= (Gdk.EventMask.ExposureMask |
				   			Gdk.EventMask.PointerMotionMask |
						   	Gdk.EventMask.ButtonPressMask |
						   	Gdk.EventMask.ButtonReleaseMask |
							Gdk.EventMask.ScrollMask | 
			                Gdk.EventMask.Button1MotionMask |
							Gdk.EventMask.KeyPressMask);
            this.ExposeEvent += OnExpose;
			this.ConfigureEvent += OnConfigureEvent;
			this.ScrollEvent += OnScrollEvent;
			this.KeyPressEvent += OnKeyPressEvent;
			this.MotionNotifyEvent += OnMotionEvent;
			
			this.Visible = true;
			//this.SetSizeRequest(400,200);
			//this.OnDragMotion += OnDragMotion;
			
			
        }

		public double X      { get { return (double)this.x; } }
		public double Y      { get { return (double)this.y; } }
		public double Height { get { return (double)this.height; } }
		public double Width  { get { return (double)this.width; } }
		public double Depth  { get { return (double)this.depth; } }
		
		public double ScalingFactor {
			get { return this.scalingFactor; }
			set {
				this.scalingFactor = value;
				this.QueueDraw();//Area(0, 0, this.width, this.height);
		   	}
		}

		internal double PreScalingFactor {
			get { return this.preScalingFactor; }
		}

		public double ScrollFactor {
			get { return this.scrollFactor; }
			set { this.scrollFactor = value; }
		}
		public NodeItem Tree { get; set;}
		
        /// <summary>Pass ButtonPress events to canvas items</summary>
        /// <param name="o">An object pointer</param>
        /// <param name="args">ButtonPressEvent arguments</param>
		protected void OnScrollEvent(object o, ScrollEventArgs args) {
			if (args.Event.Direction == ScrollDirection.Up) {
				this.ScalingFactor *= this.scrollFactor;
			} else {
				this.ScalingFactor /= this.scrollFactor;
			}			
        }
		protected void OnKeyPressEvent(object o, KeyPressEventArgs args) {
			//Console.WriteLine(args.Event.KeyValue);
			if (args.Event.KeyValue=='+') {
				this.ScalingFactor *= this.scrollFactor;
			} else if (args.Event.KeyValue=='-')  {
				this.ScalingFactor /= this.scrollFactor;
			}			
        }
		protected void OnMotionEvent (object o,	MotionNotifyEventArgs args)	{	
			if(this==null || this.Tree == null) return;
			this.HasFocus = true;
			if ((args.Event.State & Gdk.ModifierType.Button1Mask) > 0) {
				if (indrag) {
					this.xtrans += (args.Event.X-this.xdragStart);//*this.ScalingFactor;
					this.ytrans += (args.Event.Y-this.ydragStart);//*this.ScalingFactor;					
				}
				indrag = true;
				this.xdragStart = args.Event.X;
				this.ydragStart = args.Event.Y;
				QueueDraw();
			} 
			else {
				indrag = false;
				this.Tree.MouseOver(args.Event.X,args.Event.Y);
			}
		} 

		public void OnConfigureEvent(
				object o,
			   	ConfigureEventArgs args)
		{
			GdkWindow.GetGeometry(out this.x, out this.y, out this.width, out this.height, out this.depth);
			//SpriteLib.Init(this.width,this.height,this.GdkWindow);
			//GdkWindow.SetBackPixmap(SpriteLib.field.GetPixMap(),false);

		}

/*		protected void OnResize()
		{
			if ((this.items != null) && (GdkWindow != null)) {
				Monitor.Enter(this.items);

				GdkWindow.GetGeometry(out this.x, out this.y, out this.width, out this.height, out this.depth);
				Console.WriteLine("Size: " + this.x + " " + this.y + " " + this.width + " " + this.height);

//				foreach (CairoCanvasItem item in this.items) {
//					item.ScalingFactor = this.scalingFactor;
//					item.CanvasResized(this.x, this.y, this.width, this.height, this.depth);
//				}

//				this.Modified = true;

				Monitor.Exit(this.items);
			}
		}
*/
        protected void OnExpose(
                object o,
                ExposeEventArgs args)
        {


//Console.WriteLine("Exposed");
			if (this.GdkWindow != null) {
//long time1 = DateTime.UtcNow.Ticks;



				Cairo.Context g = Gdk.CairoHelper.Create(this.GdkWindow);//Gdk.Graphics.CreateDrawable(this.GdkWindow);

				if (args != null) {
					g.Rectangle(args.Event.Area.X, args.Event.Area.Y, args.Event.Area.Width, args.Event.Area.Height);
					g.Clip();
				}

				//g.Translate(this.Width / 2.0+this.xtrans, this.Height/3.0+this.ytrans);
				g.Translate(20+this.xtrans, 20+this.ytrans);
				g.Scale(this.scalingFactor, this.scalingFactor);

				//i.DrawTo(this.GdkWindow,g);
				if(this.Tree!=null) {
					this.Tree.DrawTo(this.GdkWindow,g);
				}
//				g.Restore();
				g.ShowPage();

				((IDisposable)g.Target).Dispose();
				((IDisposable)g).Dispose();
				
				//this.SetSizeRequest(400,70*this.Parent.Allocation.Height/100);
//long time2 = (DateTime.UtcNow.Ticks - time1)/100;
//Console.WriteLine("DRAW TIME: {0}",time2);

			}
		}
		
		protected void UpdateView()
		{
			int x = 0;
			int y = 0;
			Gdk.ModifierType m = 0;
			this.GdkWindow.GetPointer(out x, out y, out m);
		}

    }
}
