using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace AutoDiff
{
    partial class CompiledDifferentiator
    {
        private class Compiler : ITermVisitor<int> // int --> the index of the compiled element in the tape
        {
            private readonly List<Compiled.TapeElement> tape;
            private readonly List<List<AutoDiff.Compiled.InputConnection>> inputConnections;
            private readonly Dictionary<Term, int> indexOf;

            public Compiler(Variable[] variables, List<Compiled.TapeElement> tape)
            {
                this.tape = tape;
                indexOf = new Dictionary<Term, int>();
                inputConnections = new List<List<Compiled.InputConnection>>();
                foreach (var i in Enumerable.Range(0, variables.Length))
                {
                    indexOf[variables[i]] = i;
                    tape.Add(new Compiled.Variable());
                    inputConnections.Add(new List<Compiled.InputConnection>());
                }
            }

            public void Compile(Term term)
            {
                term.Accept(this);
                for (int i = 0; i < tape.Count; ++i)
                    tape[i].InputOf = inputConnections[i].ToArray();
            }

            public int Visit(Constant constant)
            {
                return Compile(constant, () =>
                    new CompileResult { Element = new Compiled.Constant(constant.Value), InputTapeIndices = new int[0] });
            }

            public int Visit(Zero zero)
            {
                return Compile(zero, () => new CompileResult { Element = new Compiled.Constant(0), InputTapeIndices = new int[0] });
            }

            public int Visit(IntPower intPower)
            {
                return Compile(intPower, () =>
                    {
                        var baseIndex = intPower.Base.Accept(this);
                        var element = new Compiled.Power { Base = baseIndex, Exponent = intPower.Exponent };
                        return new CompileResult { Element = element, InputTapeIndices = new int[] { baseIndex } };
                    });
            }

            public int Visit(Product product)
            {
                return Compile(product, () =>
                    {
                        var leftIndex = product.Left.Accept(this);
                        var rightIndex = product.Right.Accept(this);
                        var element = new Compiled.Product
                        {
                            Left = leftIndex,
                            Right = rightIndex,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { leftIndex, rightIndex },
                        };
                    });
            }
			
			public int Visit(Min min)
            {
                return Compile(min, () =>
                    {
                        var leftIndex = min.Left.Accept(this);
                        var rightIndex = min.Right.Accept(this);
                        var element = new Compiled.Min
                        {
                            Left = leftIndex,
                            Right = rightIndex,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { leftIndex, rightIndex },
                        };
                    });
            }
			public int Visit(Max max)
            {
                return Compile(max, () =>
                    {
                        var leftIndex = max.Left.Accept(this);
                        var rightIndex = max.Right.Accept(this);
                        var element = new Compiled.Max
                        {
                            Left = leftIndex,
                            Right = rightIndex,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { leftIndex, rightIndex },
                        };
                    });
            }
			public int Visit(Reification dis)
            {
                return Compile(dis, () =>
                    {                        
						var conIndex   = dis.Condition.Accept(this);
						var negConIndex = dis.NegatedCondition.Accept(this);
                        var element = new Compiled.Reification
                        {
                            Min = dis.Min,
                            Max = dis.Max,
							Condition = conIndex,
							NegatedCondition = negConIndex
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { conIndex, negConIndex },
                        };
                    });
            }
			public int Visit(And and)
            {
                return Compile(and, () =>
                    {
                        var leftIndex = and.Left.Accept(this);
                        var rightIndex = and.Right.Accept(this);
                        var element = new Compiled.And
                        {
                            Left = leftIndex,
                            Right = rightIndex,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { leftIndex, rightIndex },
                        };
                    });
            }
			public int Visit(Or or)
            {
                return Compile(or, () =>
                    {
                        var leftIndex = or.Left.Accept(this);
                        var rightIndex = or.Right.Accept(this);
                        var element = new Compiled.Or
                        {
                            Left = leftIndex,
                            Right = rightIndex,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { leftIndex, rightIndex },
                        };
                    });
            }
			public int Visit(ConstraintUtility cu)
            {
                return Compile(cu, () =>
                    {
						var constraint = cu.Constraint.Accept(this);
                        var util = cu.Utility.Accept(this);
                        
                        var element = new Compiled.ConstraintUtility
                        {
                            Constraint = constraint,
                            Utility = util,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { constraint, util },
                        };
                    });
            }
			
			public int Visit(Sigmoid sigmoid)
            {
                return Compile(sigmoid, () =>
                    {
                        var argIndex = sigmoid.Arg.Accept(this);
                        var midIndex = sigmoid.Mid.Accept(this);
						
                        var element = new Compiled.Sigmoid
                        {
                            Arg = argIndex,
                            Mid = midIndex,
							Steepness = sigmoid.Steepness,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { argIndex, midIndex },
                        };
                    });
            }
			public int Visit(LTConstraint constraint)
            {
                return Compile(constraint, () =>
                    {
                        var lIndex = constraint.Left.Accept(this);
                        var rIndex = constraint.Right.Accept(this);
						
                        var element = new Compiled.LTConstraint
                        {
                            Left = lIndex,
                            Right = rIndex,
							Steepness = constraint.Steepness,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { lIndex, rIndex },
                        };
                    });
            }
			public int Visit(LTEConstraint constraint)
            {
                return Compile(constraint, () =>
                    {
                        var lIndex = constraint.Left.Accept(this);
                        var rIndex = constraint.Right.Accept(this);
						
                        var element = new Compiled.LTEConstraint
                        {
                            Left = lIndex,
                            Right = rIndex,
							Steepness = constraint.Steepness,
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { lIndex, rIndex },
                        };
                    });
            }
			
            public int Visit(Sum sum)
            {
                return Compile(sum, () =>
                    {
                        var indicesQuery = from term in sum.Terms
                                           select term.Accept(this);
                        var indices = indicesQuery.ToArray();
                        var element = new Compiled.Sum { Terms = indices };
                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = indices,
                        };
                    });
            }

            public int Visit(Variable variable)
            {
                return indexOf[variable];
            }
			
 			public int Visit(Sin sin)
            {
                return Compile(sin, () =>
                    {
                        var argIndex = sin.Arg.Accept(this);
                        var element = new Compiled.Sin { Arg = argIndex };
                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { argIndex },
                        };
                    });
            }
			public int Visit(Cos cos)
            {
                return Compile(cos, () =>
                    {
                        var argIndex = cos.Arg.Accept(this);
                        var element = new Compiled.Cos { Arg = argIndex };
                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { argIndex },
                        };
                    });
            }
           public int Visit(Log log)
            {
                return Compile(log, () =>
                    {
                        var argIndex = log.Arg.Accept(this);
                        var element = new Compiled.Log { Arg = argIndex };
                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { argIndex },
                        };
                    });
            }

            public int Visit(Exp exp)
            {
                return Compile(exp, () =>
                    {
                        var argIndex = exp.Arg.Accept(this);
                        var element = new Compiled.Exp { Arg = argIndex };
                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { argIndex },
                        };
                    });
            }
			public int Visit(Abs abs)
            {
                return Compile(abs, () =>
                    {
                        var argIndex = abs.Arg.Accept(this);
                        var element = new Compiled.Abs { Arg = argIndex };
                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { argIndex },
                        };
                    });
            }
			public int Visit(Atan2 atan2)
            {
                return Compile(atan2, () =>
                    {
                        var lIndex = atan2.Left.Accept(this);
                        var rIndex = atan2.Right.Accept(this);
						
                        var element = new Compiled.Atan2
                        {
                            Left = lIndex,
                            Right = rIndex,							
                        };

                        return new CompileResult
                        {
                            Element = element,
                            InputTapeIndices = new int[] { lIndex, rIndex },
                        };
                    });
            }

            private int Compile(Term term, Func<CompileResult> compiler)
            {
                int index;
                if (!indexOf.TryGetValue(term, out index))
                {
                    var compileResult = compiler();
                    tape.Add(compileResult.Element);

                    index = tape.Count - 1;
                    indexOf.Add(term, index);

                    inputConnections.Add(new List<Compiled.InputConnection>());
                    for (int i = 0; i < compileResult.InputTapeIndices.Length; ++i)
                    {
                        var inputTapeIndex = compileResult.InputTapeIndices[i];
                        inputConnections[inputTapeIndex].Add(new Compiled.InputConnection
                            {
                                IndexOnTape = index,
                                ArgumentIndex = i,
                            });
                    }
                }

                return index;
            }

            private class CompileResult
            {
                public Compiled.TapeElement Element;
                public int[] InputTapeIndices;
            }
        }
    }
}
