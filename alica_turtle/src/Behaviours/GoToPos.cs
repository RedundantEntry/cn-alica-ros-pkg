using System;
using System.Collections.Generic;
using Alica;
using RosCS.turtlesim;
namespace alica_turtle
{
	public class GoToPos : TurtleBehaviour
	{
		protected ConstraintQuery query;
		
		public GoToPos(string name) : base (name)
		{
 			this.query = new ConstraintQuery();
		}
		protected override void InitializeParameters ()
		{
			//prepare constraint query by adding the requested variables:
			query.ClearDomainVariables();
			query.AddVariable(this.GetOwnId(),"gposx");
			query.AddVariable(this.GetOwnId(),"gposy");
		}
		
		public override void Run (object o)
		{			
			List<double> result = null;
			bool found = query.GetSolution(this.RunningPlan,out result);
			
			/*
			 * Found indicates if a solution is found. Even if none was found, the result is typically
			 * a reasonable value.
			 * In cases the solver can proof that the CSP is infeasible, result will be null and found false.
			*/
			double targetx = 7;
			double targety = 7;

			if(result!=null && result.Count > 0) {
				targetx = result[0];
				targety = result[1];
			} else {
				return; //i.e., simply do nothing and hope a solution will be found soon.
				//otherwise, a failure can be raised:
				//this.FailureStatus = true;
				//It is then up to the parent plan to react to this failure.
			}
			Console.WriteLine("Going to {0} {1} Solution is: {2}",targetx,targety,found);
			Pose p = WM.OwnPos;
			double dx = (targetx-p.X);
			double dy = (targety-p.Y);
			
			double dist = Math.Sqrt(dx*dx+dy*dy);
			
			Velocity v = new Velocity();
			if (dist < 0.05) {
				v.Linear = 0;
				v.Angular = 0;
				Send(v);
				return;
			}
			
			
			
			double ang = Math.Atan2(dy,dx);
			
			double deltaAng = ang - p.Theta;
			if (deltaAng > Math.PI) deltaAng -= 2*Math.PI;
			else if (deltaAng < -Math.PI) deltaAng += 2*Math.PI;
						
			
			//trivial p controller:
			v.Angular =(float) deltaAng;
			
			if (Math.Abs(deltaAng) < 0.25) {
				v.Linear =(float) dist;
			}
			
			Send(v);
			
		}
	}
}

