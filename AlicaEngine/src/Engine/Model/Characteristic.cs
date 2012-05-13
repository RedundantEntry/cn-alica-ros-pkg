using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// A characteristic encapsulates a <see cref="Capability"/> and a <see cref="CapValue"/>.
	/// </summary>
	public class Characteristic : PlanElement
	{
		protected Capability capability = null;
		protected CapValue capVal = null;	
		protected double weight = 0;
		
	
		public Capability Capability					
		{
			set { this.capability = value; }
			get { return this.capability; }
		}
		
		public CapValue CapValue					
		{
			set { this.capVal = value; }
			get { return this.capVal; }
		}
		/// <summary>
		/// The weight, used for role allocation.
		/// </summary>
		public double Weight
		{
			set { this.weight = value; }
			get { return this.weight; }
		}		
		
	
		public override string ToString()
		{
			return String.Format("{0, -20} {1, -20} {2:F}",	this.Capability.Name, this.CapValue.Name, weight);
		}
	}
}

