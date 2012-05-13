
using System;
using AD=AutoDiff;
namespace Alica
{

	/// <summary>
	/// A variable is constraint by conditions, feasible values can be queried using a <see cref="ConstraintQuery"/>.
	/// </summary>
	public class Variable : PlanElement
	{
		public String Type {get; set;}
		public AD.Variable SolverVar {get; private set;}
		public Variable () {}
		public Variable(AutoDiff.Variable v) {
			this.SolverVar = v;			
		}
		public Variable(long id, String name, String type): base() {
			this.Id = id;
			this.Name = name;
			this.Type = type;
			this.SolverVar = new AutoDiff.Variable();
		}
		public override string ToString ()
		{
			return string.Format ("[Variable: Name={0}, ID={1}]", this.Name,this.Id);
		}
		
	}
}
