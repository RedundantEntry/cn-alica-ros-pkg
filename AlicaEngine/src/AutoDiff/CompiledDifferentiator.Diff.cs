using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Diagnostics;

namespace AutoDiff
{
    partial class CompiledDifferentiator
    {
        private class DiffVisitor : Compiled.ITapeVisitor
        {
            private readonly Compiled.TapeElement[] tape;
            public double LocalDerivative;
            public int ArgumentIndex;
			protected static double Epsilon = 10E-5;

            public DiffVisitor(Compiled.TapeElement[] tape)
            {
                this.tape = tape;
            }

            public void Visit(Compiled.Constant elem)
            {

            }
			//tan(x)' = 1 / cos(cos(x))
			//asin(x)' = 1 / sqrt(1-x^2)
			//acos(x)' = - 1 / sqrt(1-x^2)
			//d atan2(y,x) = ( -y / (x^2+y^2),			
			//				x / (x^2+y^2) )
            public void Visit(Compiled.Exp elem)
            {
                LocalDerivative = elem.Derivative * elem.Value;
            }

            public void Visit(Compiled.Log elem)
            {
                LocalDerivative = elem.Derivative / ValueOf(elem.Arg);
            }
			
 			public void Visit(Compiled.Sin elem)
            {
                LocalDerivative = elem.Derivative * Math.Cos(ValueOf(elem.Arg));
//Console.WriteLine("Sin: {0} = {1} {2}",LocalDerivative , elem.Derivative , Math.Cos(ValueOf(elem.Arg)));
            }			
 			public void Visit(Compiled.Cos elem)
            {
                LocalDerivative = - elem.Derivative * Math.Sin(ValueOf(elem.Arg));;
//Console.WriteLine("Cos: {0} = -{1} * {2}",LocalDerivative , elem.Derivative , Math.Sin(ValueOf(elem.Arg)));				
            }
			
			public void Visit(Compiled.Abs elem)
            {
				if (ValueOf(elem.Arg) >= 0) {
					LocalDerivative = elem.Derivative;
				} else if (ValueOf(elem.Arg) < 0) {
					LocalDerivative = -elem.Derivative;
				}
			}
			
			public void Visit(Compiled.Power elem)
            {
                LocalDerivative = elem.Derivative * elem.Exponent * Math.Pow(ValueOf(elem.Base), elem.Exponent - 1);
//Console.WriteLine("Power: {0} <= {1} {2}",LocalDerivative, elem.Derivative,ValueOf(elem.Base));				
            }

            public void Visit(Compiled.Product elem)
            {
                Debug.Assert(ArgumentIndex == 0 || ArgumentIndex == 1);
                if (ArgumentIndex == 0) {
                    LocalDerivative = elem.Derivative * ValueOf(elem.Right);
					
				}
                else {
                    LocalDerivative = elem.Derivative * ValueOf(elem.Left);	

				}
				
            }
			public void Visit(Compiled.Reification elem) {
				if (ArgumentIndex == 0) {
					if (ValueOf(elem.Condition) > 0) {						
						LocalDerivative = 0;
					}
					else {
						LocalDerivative = elem.Derivative * (elem.Max-elem.Min); //*elem.Max/Math.Abs(ValueOf(elem.Condition));
					}
					
				} else {
					if (ValueOf(elem.Condition) <= 0) {	
						LocalDerivative = 0;
					} else {
						LocalDerivative = -elem.Derivative * (elem.Max-elem.Min);
					}
				}
			}
			
			public void Visit(Compiled.Min elem) {
				if (ArgumentIndex == 0) {
					if (ValueOf(elem.Left) < ValueOf(elem.Right)) {
						LocalDerivative = elem.Derivative;
					}
					else if (ValueOf(elem.Left) == ValueOf(elem.Right)) {
						if (ValueOf(elem.Left)< 0.5) LocalDerivative = elem.Derivative;
						else LocalDerivative = 0;//elem.Derivative*.5;
					}
					else {
						LocalDerivative = 0;
					}
				} else {
					if (ValueOf(elem.Left) > ValueOf(elem.Right)) {
						LocalDerivative = elem.Derivative;
					} else if (ValueOf(elem.Left) == ValueOf(elem.Right)) {
						LocalDerivative = 0;//elem.Derivative*.5;
					} else {
						LocalDerivative = 0;
					}
				}				
			}
			
			public void Visit(Compiled.Max elem) {
				if (ArgumentIndex == 0) {
					if (ValueOf(elem.Left) > ValueOf(elem.Right)) {
						LocalDerivative = elem.Derivative;
					} else if (ValueOf(elem.Left) == ValueOf(elem.Right)) {
						if (ValueOf(elem.Left) <= 0.5) LocalDerivative = elem.Derivative;
						else LocalDerivative = 0;
					}
					else LocalDerivative = 0;
				} else {
					if (ValueOf(elem.Right) > ValueOf(elem.Left)) {
						LocalDerivative = elem.Derivative;
					} else {
						LocalDerivative = 0;
					}
				}				
			}
			
			
			public void Visit(Compiled.And elem) {
				if (ArgumentIndex == 0) {
					if (ValueOf(elem.Left) > 0.75) {
						LocalDerivative = 0;
					}
					else LocalDerivative = elem.Derivative;					
				} else {
					if (ValueOf(elem.Right) > 0.75) {
						LocalDerivative = 0;
					} else LocalDerivative = elem.Derivative;
				}				
			}
			public void Visit(Compiled.Or elem) {
				if (ArgumentIndex == 0) {
					if (ValueOf(elem.Right) > 0.75) {
						LocalDerivative = 0;
					} else LocalDerivative = elem.Derivative;
				} else {
					if (ValueOf(elem.Left) > 0.75) {
						LocalDerivative = 0;
					} else {
						LocalDerivative = elem.Derivative;
					}
				}				
			}
			
			public void Visit(Compiled.Sigmoid elem)
            {
				Debug.Assert(ArgumentIndex == 0 || ArgumentIndex == 1);
				double e = Math.Exp(elem.Steepness * (ValueOf(elem.Mid) - ValueOf(elem.Arg) ));
//Console.WriteLine("e: {0} deriv: {1}",e,elem.Derivative);				
				if (Double.IsPositiveInfinity(e) || e == 0) {
				
					if (ArgumentIndex == 0) {
						LocalDerivative = elem.Steepness * elem.Derivative * Epsilon;
					} else {
						LocalDerivative = - elem.Steepness * elem.Derivative * Epsilon;
					}
				} else
				if (ArgumentIndex == 0) {
					LocalDerivative = elem.Steepness * elem.Derivative * e / ((e+1)*(e+1));
					if (Math.Abs(LocalDerivative) < Math.Abs(elem.Steepness * elem.Derivative * Epsilon)) {
						LocalDerivative  = elem.Steepness * elem.Derivative * Epsilon;
					}
				} else {
					LocalDerivative = -elem.Steepness * elem.Derivative * e / ((e+1)*(e+1));
					if (Math.Abs(LocalDerivative) < Math.Abs(elem.Steepness * elem.Derivative * Epsilon)) {
						LocalDerivative  = - elem.Steepness * elem.Derivative * Epsilon;
					}
				}
				
//Console.WriteLine("SigDif: {0} <= {1}",LocalDerivative, elem.Derivative);
            }
			public void Visit(Compiled.LTConstraint elem)
            {
				double diff = ValueOf(elem.Left) - ValueOf(elem.Right);
				if (diff < 0) {
					LocalDerivative = 0;
				}
				/*else {
					if (ArgumentIndex == 0) {
						LocalDerivative = - elem.Derivative * Math.Max(elem.Steepness,(ValueOf(elem.Left)-ValueOf(elem.Right)));	
					} else {
						LocalDerivative = elem.Derivative * Math.Max(elem.Steepness,(ValueOf(elem.Left)-ValueOf(elem.Right)));
					}
				}*/
				//Normal behaviour:
				else {
					if (ArgumentIndex == 0) {
						LocalDerivative = - elem.Steepness * elem.Derivative;
					} else {
						LocalDerivative = elem.Steepness * elem.Derivative;
					}
				}
				
			}
			public void Visit(Compiled.LTEConstraint elem)
            {
				double diff = ValueOf(elem.Left) - ValueOf(elem.Right);
				if (diff <= 0) {
					LocalDerivative = 0;
				}				
				//Normal behaviour:
				else {
					if (ArgumentIndex == 0) {
						LocalDerivative = - elem.Steepness * elem.Derivative;
					} else {
						LocalDerivative = elem.Steepness * elem.Derivative;
					}
				}
				
			}
			public void Visit(Compiled.ConstraintUtility elem)
            {
				if (ArgumentIndex == 0) {
					if (ValueOf(elem.Constraint) < 0.999) {
						LocalDerivative = elem.Derivative;
					}
					else LocalDerivative = 0;
				} else {
					if (ValueOf(elem.Constraint) < 0.999) {
						LocalDerivative = 0;
					} else {
						LocalDerivative = ValueOf(elem.Constraint) * elem.Derivative;
					}
				}			
			}
			public void Visit(Compiled.Atan2 elem) {
				double denom = ValueOf(elem.Left)*ValueOf(elem.Left) + ValueOf(elem.Right)*ValueOf(elem.Right);
				if (ArgumentIndex == 0) {
					LocalDerivative = - ValueOf(elem.Right)*elem.Derivative / denom;
				} else {
					LocalDerivative = ValueOf(elem.Left)*elem.Derivative / denom;
				}				
			}
				

            public void Visit(Compiled.Sum elem)
            {
                LocalDerivative = elem.Derivative;
            }

            public void Visit(Compiled.Variable var)
            {
            }

            private double ValueOf(int index)
            {
                return tape[index].Value;
            }
			
        }
    }
}
