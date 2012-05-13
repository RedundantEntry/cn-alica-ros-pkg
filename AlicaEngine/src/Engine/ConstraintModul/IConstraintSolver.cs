
using System;
using System.Collections.Generic;
using System.Collections;
using System.Runtime.InteropServices;

namespace Alica
{
	/*[StructLayout(LayoutKind.Sequential)]
	public struct RobotInPlan {
		public RobotInPlan(int rid, long plan, long task, long state) {
			this.rid = rid;
			this.plan = plan;
			this.task = task;
			this.state = state;
		}
		int rid;
		long plan;
		long task;
		long state;
	}*/
	
	public interface IConstraintSolver		
	{			
		//bool ExistsSolution(List<Parametrisation> bindings, List<Constraint> calls);
		bool ExistsSolution(List<Variable> vars,List<ConstraintDescriptor> calls);
		//bool GetSolution(List<CVariable> vars,List<Parametrisation> bindings, List<Constraint> calls, out List<double> results);
		bool GetSolution(List<Variable> vars,List<ConstraintDescriptor> calls, out double[] results);
		bool GetSolution(List<Variable> vars,List<ConstraintDescriptor> calls, out object[] results);
				
	}
}
