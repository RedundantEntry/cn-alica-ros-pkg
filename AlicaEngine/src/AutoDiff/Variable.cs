using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AutoDiff
{
    /// <summary>
    /// Represents a variable term. Variable terms are substituted for real values during evaluation and
    /// differentiation. For more info see <see cref="Evaluator"/> and <see cref="Differentiator"/> classes.
    /// </summary>
    [Serializable]
    public class Variable : Term
    {
        /// <summary>
        /// Accepts a term visitor
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
			int hash = this.GetHashCode();
			return (hash<0 ? string.Format ("Var_{0}",-hash) : string.Format ("Var{0}",hash));
		}
		
		public override Term AggregateConstants()
		{
			return this;
		}
		public override Term Derivative(Variable v)
		{
			if(this==v) return 1;
			else return 0;
		}
    }
}
