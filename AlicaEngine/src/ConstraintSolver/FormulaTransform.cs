using System;
using System.Collections.Generic;
using AutoDiff;
namespace Alica.Reasoner
{
	public class Literal {
		public Literal(Term t, bool negated, bool temp) {
			this.Atom = t;
			this.IsNegated = negated;
			this.IsTemporary = temp;
		}
		public Term Atom {get; set;}
		public bool IsNegated {get; set;}
		public bool IsTemporary {get; set;}
		public int Id {get; set;}
		
		/*public override int GetHashCode ()
		{
			return this.Atom.GetHashCode();
		}
		public override bool Equals (object obj)
		{
			if (obj is Literal) {
				return this.Atom == ((Literal)obj).Atom;
			}
			return base.Equals(obj);
		}*/
	}
	
	public class Clause {
		public List<Literal> Literals {
			get;
			private set;
		}
		public void Add(Literal l) {
			bool found = false;
			if (l.IsTemporary) {
				for(int i=0; i<this.Literals.Count; i++) {
					if (this.Literals[i].IsTemporary && l.Atom == this.Literals[i].Atom) {
						found = true;
						break;
					}					
				}
				if (!found) {
					Literals.Add(l);
				} else { 
Console.WriteLine("TTT: Early!");
				}
				return;
			}			
			for(int i=0; i<this.Literals.Count; i++) {
				if (l.Atom == this.Literals[i].Atom) {
					found = true;
				    if (l.IsNegated != this.Literals[i].IsNegated) {
						this.IsTautologic = true;
						this.Literals.Clear();
Console.WriteLine("TTT: Tautologic");						
					}
					else {
Console.WriteLine("TTT: Ordinary");						
					}
					break;
				}					
			}
			if (!found) {
				Literals.Add(l);
			}
			return;

		}
		public bool IsTautologic {
			get;
			private set;
		}
		internal bool IsFinished {
			get;
			set;
		}
		public Clause() {
			this.Literals = new List<Literal>();
			this.IsFinished = false;
		}
		public Clause Clone() {
			Clause clone = new Clause();
			clone.Literals.AddRange(this.Literals);
			clone.IsFinished = this.IsFinished;
			clone.IsTautologic = this.IsTautologic;
			return clone;
		}
	}
	
	
	public class FormulaTransform
	{
		public List<Term> Atoms {
			get;
			private set;
		}
		public int AtomOccurrence {
			get; 
			private set;
		}
		public Dictionary<Term,List<Literal>> References {
			get;
			private set;
		}
		public FormulaTransform ()
		{
			this.Atoms = new List<Term>();//new HashSet<Term>();	
			this.References = new Dictionary<Term, List<Literal>>();
			this.AtomOccurrence = 0;
			
		}
		public void Reset() {
			this.Atoms.Clear();
			this.AtomOccurrence = 0;
			this.References.Clear();
		}
		public List<Clause> TransformToCNF(Term formula) {
			List<Clause> clauses = new List<Clause>();
			Clause initial = new Clause();
			initial.Literals.Add(new Literal(formula,false,true));
			
			clauses.Add(initial);
			
			DoTransform(clauses);
			
			return clauses;
		}
		protected void DoTransform(List<Clause> clauses) {
			bool finished;
			do {
				finished = true;
				Clause curClause = null;
				Literal curLit = null;
				int i=0, j=0;
				for(i=0; i<clauses.Count;i++) {
					if (clauses[i].IsFinished) {
						continue;
					}
					for(j=0; j < clauses[i].Literals.Count; j++) {
						if(clauses[i].Literals[j].IsTemporary) {
							finished = false;
							curClause = clauses[i];
							curLit = clauses[i].Literals[j];
							break;
						}						
					}	
					if(!finished) {
						break;
					} else {
						clauses[i].IsFinished = true;
					}
				}
				
				//break clause on lit:
				if (!finished) {
					clauses.RemoveAt(i);
					curClause.Literals.RemoveAt(j);
					clauses.AddRange(PerformStep(curClause,curLit));
				}
			} while(!finished);
		}
		
		protected List<Clause> PerformStep(Clause c, Literal lit) {
			List<Clause> ret = new List<Clause>();
			Term formula = lit.Atom;
			if (formula is Max) {
				Max m = (Max)formula;
				Literal l = new Literal(m.Left,false,true);
				Literal r = new Literal(m.Right,false,true);
				c.Add(l);
				c.Add(r);
				ret.Add(c);
				return ret;
			}
			if (formula is Or) {
				Or m = (Or)formula;
				Literal l = new Literal(m.Left,false,true);
				Literal r = new Literal(m.Right,false,true);
				c.Add(l);
				c.Add(r);
				ret.Add(c);
				return ret;
			}
			if (formula is And) {
				And m = (And)formula;
				Literal l = new Literal(m.Left,false,true);
				Literal r = new Literal(m.Right,false,true);
				Clause c2 = c.Clone();
				c.Add(l);
				c2.Add(r);
				ret.Add(c);
				ret.Add(c2);
				return ret;				
			}
			if (formula is Min) {
				Min m = (Min)formula;
				Literal l = new Literal(m.Left,false,true);
				Literal r = new Literal(m.Right,false,true);
				Clause c2 = c.Clone();
				c.Add(l);
				c2.Add(r);
				ret.Add(c);
				ret.Add(c2);
				return ret;				
			}
			if (formula is LTConstraint) {				
				lit.IsTemporary = false;
				lit.IsNegated = false;
				this.AtomOccurrence++;
				int idx = Atoms.IndexOf(formula);
				if(idx < 0) {
					Atoms.Add(formula);
					List<Literal> o = new List<Literal>();
					o.Add(lit);
					References.Add(formula,o);					
				} else {
					References[Atoms[idx]].Add(lit);
				}
				c.Add(lit);
				ret.Add(c);
				return ret;	
			}
			if (formula is LTEConstraint) {
				
				lit.IsTemporary = false;
				lit.IsNegated = true;
				this.AtomOccurrence++;
				Term p = ((LTEConstraint)formula).Negate();
				lit.Atom = p;
				c.Add(lit);
				int idx = Atoms.IndexOf(p);
				if(idx < 0) {
					Atoms.Add(p);
					List<Literal> o = new List<Literal>();
					o.Add(lit);
					References.Add(p,o);
				} else {
					References[Atoms[idx]].Add(lit);					
				}
				ret.Add(c);
				return ret;					
			}
			if (formula is Constant) {
				if (((Constant)formula).Value <= 0.0) {
					ret.Add(c);	
				}
				return ret;
			}
			Console.Error.WriteLine("U C: {0}",formula);
			throw new Exception("Unknown constraint in transformation: "+formula);
			

			
		}
		
		/*public List<Clause> TransformToCNF(Term formula) {
//Console.WriteLine("Transforming: {0}",formula);
			List<Clause> ret = new List<Clause>();
			if(formula is Max) {
				Max m = (Max)formula;
				Term l = TransformToCNF(m.Left);
				Term r = TransformToCNF(m.Right);
				if (l is And && r is And) {
					And ml = (And)l;
					And mr = (And)r;
					ret.AddRange(TransformToCNF(ml.Left | mr.Left));
					ret.AddRange(TransformToCNF(ml.Left | mr.Right));
					ret.AddRange(TransformToCNF(ml.Right | mr.Left));
					ret.AddRange(TransformToCNF(ml.Right | mr.Right));					
				}
				if (l is And) {
					And ml = (And)l;
					return TransformToCNF(ml.Left | r) & TransformToCNF(ml.Right | r);
				}
				if (r is And) {
					And mr = (And)r;
					return TransformToCNF(mr.Left | l) & TransformToCNF(mr.Right | l);
				}
				if (l is Min && r is Min) {
					Min ml = (Min)l;
					Min mr = (Min)r;
					return TransformToCNF(ml.Left | mr.Left) & TransformToCNF(ml.Left | mr.Right) & TransformToCNF(ml.Right | mr.Left) & TransformToCNF(ml.Right | mr.Right);
				}
				if (l is Min) {
					Min ml = (Min)l;
					return TransformToCNF(ml.Left | r) & TransformToCNF(ml.Right | r);
				}
				if (r is Min) {
					Min mr = (Min)r;
					return TransformToCNF(mr.Left | l) & TransformToCNF(mr.Right | l);
				}
				return (l | r);
			}
			else if (formula is Or) {
				Or m = (Or)formula;
				Term l = TransformToCNF(m.Left);
				Term r = TransformToCNF(m.Right);
				if (l is And && r is And) {
					And ml = (And)l;
					And mr = (And)r;
					return TransformToCNF(ml.Left | mr.Left) & TransformToCNF(ml.Left | mr.Right) & TransformToCNF(ml.Right | mr.Left) & TransformToCNF(ml.Right | mr.Right);
				}
				if (l is And) {
					And ml = (And)l;
					return TransformToCNF(ml.Left | r) & TransformToCNF(ml.Right | r);
				}
				if (r is And) {
					And mr = (And)r;
					return TransformToCNF(mr.Left | l) & TransformToCNF(mr.Right | l);
				}
				if (l is Min && r is Min) {
					Min ml = (Min)l;
					Min mr = (Min)r;
					return TransformToCNF(ml.Left | mr.Left) & TransformToCNF(ml.Left | mr.Right) & TransformToCNF(ml.Right | mr.Left) & TransformToCNF(ml.Right | mr.Right);
				}
				if (l is Min) {
					Min ml = (Min)l;
					return TransformToCNF(ml.Left | r) & TransformToCNF(ml.Right | r);
				}
				if (r is Min) {
					Min mr = (Min)r;
					return TransformToCNF(mr.Left | l) & TransformToCNF(mr.Right | l);
				}
				return (l | r);
			}
			else if (formula is And) {
				And a = (And)formula;
				return TransformToCNF(a.Left) & TransformToCNF(a.Right);
			}
			else if (formula is Min) {
				Min a = (Min)formula;
				return TransformToCNF(a.Left) & TransformToCNF(a.Right);				
			}
			else {
				if (formula is LTConstraint) {
					if(!Atoms.Contains(formula)) {
						Atoms.Add(formula);
					}
				}
				else if (formula is LTEConstraint) {
					Term p = ((LTEConstraint)formula).Negate();
					if(!Atoms.Contains(p)) {
						Atoms.Add(p);
					}
				}
				else {
					return formula; // True and False Constants
					//throw new Exception("Unexpected Constraint in Formula Transformation: "+formula);
				}
				this.AtomOccurrence++;
				return formula;
			}
			
		}*/
		
	}
}

