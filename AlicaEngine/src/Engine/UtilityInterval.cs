using System;

namespace Alica
{
	/// <summary>
	/// Holds a minimal and a maximal possible utility value. Used internally for searching.
	/// </summary>
	public struct UtilityInterval {		
		private double min;
		private double max;
		
		public UtilityInterval(double a,double b) {
			min = a; max = b;
		}
		/// <summary>
		/// The minimally achievable utility.
		/// </summary>
		public double Min
		{
			get{ return this.min; }
			set
			{ 
					this.min = value;
			}
		}
		/// <summary>
		/// The maximally achievable utility.
		/// </summary>
		public double Max
		{
			get{ return this.max; }
			set
			{ 
					this.max = value;
			}
		}
	}
}
