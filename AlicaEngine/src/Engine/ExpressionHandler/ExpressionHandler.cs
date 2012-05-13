//#define EH_DEBUG
using System;
using System.IO;
using Castor;
using System.Reflection;
using System.Collections.Generic;
using AD=AutoDiff;
//using Alica.Validators;

namespace Alica
{

	/// <summary>
	/// The Expressionhandler attaches expressions and constraints to plans during start-up of the engine.
	/// </summary>
	public class ExpressionHandler
	{
		protected Assembly assem = null;
		/// <summary>
		/// Constructor, loads the assembly containing expressions and constraints.
		/// </summary>
		public ExpressionHandler() {
			try
			{
				//load the library
				
				
				string esRoot = Environment.GetEnvironmentVariable("ES_ROOT");				
				
				SystemConfig sc = SystemConfig.LocalInstance;				
				string file = sc["Alica"].GetString("Alica.ExpressionLibrary");
				
				if (!file.StartsWith("/")) {
					if (!esRoot.EndsWith("/")) {
						file = esRoot+"/"+file;
					} else {
						file = esRoot+file;
					}
				}				
				if(!File.Exists(file)) {
					AlicaEngine.Get().Abort(String.Format("EH: Cannot find expression library: {0}",file));						
				}
				
				this.assem = Assembly.LoadFile(file);
				
			}
			catch (Exception e)
			{
				string msg = "EH: Unable to load assembly: " + e.Message; 
				AlicaEngine.Get().Abort(msg);
			}
			
		}
		/// <summary>
		/// Attaches expressions and constraints to the plans. Called by the <see cref="AlicaEngine"/> during start up.
		/// </summary>
		public void AttachAll() {
			
			
			Console.Write("EH: Attaching all code fragments...");
			string exprClassName = "Expressions";
			string consClassName = "ConstraintFunctions";
			
			Type exprType = null;
			Type consType = null;
			
			try
			{			
				foreach(Type ty in this.assem.GetTypes())
				{
					if(ty.Name.Equals(exprClassName))
					{
						exprType = ty;						
					} else if(ty.Name.Equals(consClassName)) {
						consType = ty;
					}
				}
			}
			catch (Exception e)	{
				AlicaEngine.Get().Abort("EH: Exception while loading expressions: "+e.ToString());
			}
			if (exprType==null) {
				AlicaEngine.Get().Abort(String.Format("EH: Could not find Type {0} in assembly {1}!",exprType,this.assem.GetName()));				
			}
			if(consType == null) {
				AlicaEngine.Get().Abort(String.Format("EH: Could not find Type {0} in assembly {1}!",consType,this.assem.GetName()));				
			}
			PlanRepository pr = AlicaEngine.Get().PR;
			
			foreach(Plan p in pr.Plans.Values) {
				AttachPlanConditions(p,exprType,consType);				
			}
			Console.WriteLine("...done!");
			
		}
		protected void AttachPlanConditions(Plan p, Type exprType, Type consType) {
			string utilityGetterName = "GetUtilityFunction"+p.Id;
			MethodInfo utilInfo = exprType.GetMethod(utilityGetterName);
			if (utilInfo==null) {
				AlicaEngine.Get().Abort(String.Format("EH: Could not find Method {0} in assemly {1}",utilityGetterName,this.assem.Location));
			}
			UtilityFunction uf = (UtilityFunction)utilInfo.Invoke(null,new object[] {p});
			if (uf==null) {
				AlicaEngine.Get().Abort(String.Format("EH: Could not retrieve Utility for {0} in assembly {1}",p.Name,this.assem.Location));
			}
			p.UtilityFunction = uf;
			
			if (p.PreCondition != null) {
				string preConditionName = "F"+p.PreCondition.Id;				
				MethodInfo pcInfo =  exprType.GetMethod(preConditionName);
				if (pcInfo == null) {
					AlicaEngine.Get().Abort(String.Format("EH: Could not find Method {0} in assembly {1}",preConditionName,this.assem.Location));
				}
				Type target = typeof(Evaluate);
				if (p.PreCondition.Enabled) {
					p.PreCondition.Eval = (Evaluate)Delegate.CreateDelegate(target,pcInfo);
					AttachConstraint(p.PreCondition,consType);
				} else {
					p.PreCondition.Eval = DummyFalse;
					p.PreCondition.Constraint = DummyConstraint;

				}
				
				#if EH_DEBUG
					Console.WriteLine("EH: Attached Precondition to plan {0}",p.Name);
				#endif
				
			}
			if (p.RuntimeCondition != null) {
				string rtConditionName = "F"+p.RuntimeCondition.Id;
				MethodInfo rcInfo =  exprType.GetMethod(rtConditionName);
				if (rcInfo == null) {
					AlicaEngine.Get().Abort(String.Format("EH: Could not find Method {0} in assembly",rtConditionName,this.assem.Location));
				}
				Type target = typeof(Evaluate);
				p.RuntimeCondition.Eval = (Evaluate)Delegate.CreateDelegate(target,rcInfo);
				
				AttachConstraint(p.RuntimeCondition,consType);
				
				#if EH_DEBUG
					Console.WriteLine("EH: Attached RuntimeCondition to plan {0}",p.Name);
				#endif				
			}
			foreach(Transition t in p.Transitions) {
				AttachTransConditions(t,exprType,consType);
			}
			
		}
		protected void AttachTransConditions(Transition t, Type exprType, Type consType) {
				string condName = "F"+t.Id;//t.PreCondition.Id;
				MethodInfo cInfo =  exprType.GetMethod(condName);
				if (cInfo == null) {
					AlicaEngine.Get().Abort(String.Format("EH: Could not find method {0} for transition in assembly {1}",condName,this.assem.Location));
				}
				
				if(t.PreCondition==null) {
					t.PreCondition = new PreCondition(t.Id);
					t.PreCondition.Eval = DummyFalse;
					t.PreCondition.Constraint = DummyConstraint;
				}
				else {
					Type target = typeof(Evaluate);
					if(t.PreCondition.Enabled) {
						t.PreCondition.Eval = (Evaluate)Delegate.CreateDelegate(target,cInfo);
						AttachConstraint(t.PreCondition,consType);
					} else {
						t.PreCondition.Eval = DummyFalse;
						t.PreCondition.Constraint = DummyConstraint;
					}
					
				}
				
				#if EH_DEBUG
					Console.WriteLine("EH: Attached Condition to transition {0}",t.Id);
				#endif				
				
		}
		protected void AttachConstraint(Condition c, Type t) {
			if(c.Vars.Count == 0 && c.Quantifiers.Count == 0) {
				c.Constraint = this.DummyConstraint;
				return;
			} 
			string methodName = "GetConstraint"+c.Id;
			MethodInfo mi = t.GetMethod(methodName);
			if (mi==null) {					
				string msg = "EH: Could not find method: " + methodName+" (Constraint in Plan "+c.AbstractPlan.Name+")";
				AlicaEngine.Get().Abort(msg);
				//c.Constraint = this.DummyConstraint;
				
			} else {				
				Type target = typeof(GetConstraint);
				c.Constraint = (GetConstraint)Delegate.CreateDelegate(target,mi);
			}
		}
		/// <summary>
		/// Dummy Constraint builder in case none was found in the assembly.
		/// </summary>
		/// <param name="vars">
		/// A <see cref="AD.Variable[]"/>
		/// </param>
		/// <param name="rp">
		/// A <see cref="RunningPlan"/>
		/// </param>
		/// <returns>
		/// A <see cref="ConstraintDescriptor"/>
		/// </returns>
		public void DummyConstraint(ConstraintDescriptor cd,RunningPlan rp) {
			//this.baseModule.Mon.Warning(1000,"Calling Dummy Constraint (ExprValidators not compiled?");
			//return new ConstraintDescriptor(vars);
		}
		
		public bool DummyTrue(RunningPlan rp) {			
			return true;
		}
		public bool DummyFalse(RunningPlan rp) {
			return false;
		}
	}
}
