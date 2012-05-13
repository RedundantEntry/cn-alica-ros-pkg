using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AutoDiff
{
    /// <summary>
    /// Represents a constant-power function x^n, where n is constant.
    /// </summary>
    [Serializable]
    public class IntPower : Term
    {
        /// <summary>
        /// Constructs a new instance of the <see cref="IntPower"/> class.
        /// </summary>
        /// <param name="baseTerm">The base of the power function</param>
        /// <param name="exponent">The exponent of the power function</param>
        public IntPower(Term baseTerm, double exponent)
        {
            Base = baseTerm;
            Exponent = exponent;
        }

        /// <summary>
        /// Gets the base term of the power function
        /// </summary>
        public Term Base { get; private set; }

        /// <summary>
        /// Gets the exponent term of the power function.
        /// </summary>
        public double Exponent { get; private set; }

        /// <summary>
        /// Accepts a term visitor.
        /// </summary>
        /// <param name="visitor">The term visitor to accept.</param>
        public override void Accept(ITermVisitor visitor)
        {
            visitor.Visit(this);
        }

        /// <summary>
        /// Accepts a term visitor with a generic result
        /// </summary>
        /// <typeparam name="TResult">The type of the result from the visitor's function</typeparam>
        /// <param name="visitor">The visitor to accept</param>
        /// <returns>
        /// The result from the visitor's visit function.
        /// </returns>
        public override TResult Accept<TResult>(ITermVisitor<TResult> visitor)
        {
            return visitor.Visit(this);
        }
        
        public override string ToString()
		{
			return string.Format("intPower( {0}, {1} )", Base, Exponent);
		}
		
		public override Term AggregateConstants ()
		{
			Base = Base.AggregateConstants();
			if (Base is Constant) {
				return Math.Pow ((Base as Constant).Value, Exponent);
			} else if (Base is Zero) {
				if (Exponent >= 0) {
					return Base;
				} else {
					throw new DivideByZeroException ();
				}
			} else if (Base is IntPower) {
				Exponent *= (Base as IntPower).Exponent;
				Base = (Base as IntPower).Base;
				return this;			  }else {
				return this;
			}
		}
		public override Term Derivative(Variable v)
		{
			return this.Exponent * new IntPower(this.Base,(this.Exponent-1)) * this.Base.Derivative(v);
		}
		public override bool Equals (object obj)
		{
			if (this == obj) return true;
			if(obj is IntPower) {
				IntPower o = (IntPower)obj;
				return o.Exponent == this.Exponent && this.Base.Equals(o.Base);
			}
			return false;
		}
    }
}
