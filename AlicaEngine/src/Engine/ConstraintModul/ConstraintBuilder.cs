
using System;
using System.Collections.Generic;
using Castor;
//using CarpeNoctem.Tracker;
//using CarpeNoctem.Containers;
using AutoDiff;

namespace Alica
{

	/// <summary>
	/// A static helper class, providing simple factory methods for domain-independent constraints.
	/// </summary>
	public static class ConstraintBuilder
	{
		public const double SteepnessWide = 0.005;
		public const double SteepnessDefault = 0.01;
		public const double SteepnessSteep = 10.0;
		
		public static Term True = 1;
		public static Term False = Double.MinValue;
		
		//static double constraint_steepness = 0.01;
		[ObsoleteAttribute("Steepness is irrelevant for the current solver.")]
		public static void SetSteepness(double s) {
			Term.ConstraintSteepness = s;
		}
		
		/// <summary>
		/// Returns the euclidean distance between two n-dimenasional vectors.
		/// </summary>
		/// <param name="t1">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="t2">
		/// A <see cref="TVec"/>
		/// </param>
		/// <returns>
		/// A <see cref="Term"/>
		/// </returns>
		public static Term Distance(TVec t1, TVec t2) {
			return TermBuilder.EuclidianDistance(t1, t2);
		}
		/// <summary>
		/// Returns the square of the euclidean distance between two n-dimensional vectors.
		/// </summary>
		/// <param name="t1">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="t2">
		/// A <see cref="TVec"/>
		/// </param>
		/// <returns>
		/// A <see cref="Term"/>
		/// </returns>
		public static Term DistanceSqr(TVec t1, TVec t2) {
			return TermBuilder.EuclidianDistanceSqr(t1, t2);
		}
		
		/// <summary>
		/// Rotates vec by alpha
		/// </summary>
		/// <param name="vec">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="alpha">
		/// A <see cref="double"/>
		/// </param>
		/// <returns>
		/// A <see cref="TVec"/>
		/// </returns>		
		public static TVec Rotate(TVec vec, double alpha) {
			return new TVec(
				 	new Cos(alpha) * vec.X - new Sin(alpha) * vec.Y,
					new Sin(alpha) * vec.X + new Cos(alpha) * vec.Y);
		}
		
		public static Term ProjectVectorOntoX(TVec origin, TVec dir, Term x) {
			// origin.x + dir.x *t = x => 
			// t = (x-origin.x)/dir.x;
			// y= origin.y+dir.y*(x-origin.x)/dir.x;
			return origin.Y + dir.Y*(x-origin.X)*TermBuilder.Power(dir.X,-1);
		}
		/// <summary>
		/// Returns point in the coordinate system defined by vec and its rectangular.
		/// </summary>
		/// <param name="point">
		/// A two-dimensional <see cref="TVec"/>
		/// </param>
		/// <param name="vec">
		/// A two-dimensional <see cref="TVec"/>
		/// </param>
		/// <returns>
		/// A two-dimensional <see cref="TVec"/>
		/// </returns>
		public static TVec InCoordsOf(TVec point, TVec vec) {
			Term quo = TermBuilder.Power(vec.NormSquared,-1);
			return new TVec(
			                    ( point.X*vec.X+point.Y*vec.Y ) *quo
			                    ,
			                    ( point.X*vec.Y-point.Y*vec.X ) *quo
			           );
		}
		
		
		/// <summary>
		/// Two dimensional geometry:
		/// Returns if toCheck is left of vec.
		/// </summary>
		/// <param name="vec">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="toCheck">
		/// A <see cref="TVec"/>
		/// </param>
		/// <returns>
		/// A <see cref="Term"/>
		/// </returns>
		public static Term LeftOf(TVec vec, TVec toCheck) {
			 return ((toCheck.X * vec.Y) - (toCheck.Y*vec.X)) < 0;
		}
		/// <summary>
		/// Two dimensional geometry:
		/// Returns if toCheck is right of vec.
		/// </summary>
		/// <param name="vec">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="toCheck">
		/// A <see cref="TVec"/>
		/// </param>
		/// <returns>
		/// A <see cref="Term"/>
		/// </returns>
		public static Term RightOf(TVec vec, TVec toCheck) {
			 return ((toCheck.X * vec.Y) - (toCheck.Y*vec.X)) > 0;
		}
		public static Term Equals(Term t1, Term t2, Term tolerance) {
			return (t1 < (t2+tolerance)) & (t1 > (t2-tolerance));
		}
		/// <summary>
		/// Returns wether the distance between two n-dimensional vectors is less than tolerance.
		/// </summary>
		/// <param name="t1">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="t2">
		/// A <see cref="TVec"/>
		/// </param>
		/// <param name="tolerance">
		/// A <see cref="System.Double"/>
		/// </param>
		/// <returns>
		/// A <see cref="Term"/>
		/// </returns>
		public static Term Equals(TVec t1, TVec t2, double tolerance) {
			return Distance(t1,t2) < tolerance;			
		}
		/*public static Term Equals(TVec t, Point2D p, double tolerance) {
			return Distance(t,p) < tolerance;		
		}*/
		public static Term Not(Term t) {
			return !t;
		}
		public static Term And(Term t1, Term t2) {
			return t1 & t2;
		}
		public static Term Or(Term t1,Term t2) {
			return t1 | t2;
		}
		public static Term IfThen(Term tif, Term tthen) {
			return (tif.Negate() | tthen);
		}
		public static Term IfThenElse(Term tif, Term tthen, Term telse) {
			return (tif.Negate() | tthen) & (tif | telse);
		}
		public static Term Equiv(Term a, Term b) {
			return (a & b) | (a.Negate() & b.Negate());
		}
		/// <summary>
		/// Combines a constraint and a utility. This is usually only used by the <see cref="CGSolver"/>.
		/// Use if you need to circumvent CGSolver and work diectly with <see cref="GSolver"/>.
		/// </summary>
		/// <param name="constraint">
		/// A <see cref="Term"/>
		/// </param>
		/// <param name="utility">
		/// A <see cref="Term"/>
		/// </param>
		/// <returns>
		/// A <see cref="Term"/>
		/// </returns>
		public static Term ConstraintApply(Term constraint, Term utility) {
			return (new ConstraintUtility(constraint,utility));
		}
		
	}
}
			                                    