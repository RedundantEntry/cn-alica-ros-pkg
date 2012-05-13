//#define PP_DEBUG 

using System;
using System.Collections;
using System.Collections.Generic;
using System.Xml;

using System.Globalization;

namespace Alica
{	
	/// <summary>
	/// Constructs Model elements, i.e., objects inheriting from <see cref="PlanElement"/> given their XML representation.
	/// </summary>
	public class ModelFactory
	{				
		protected Dictionary<long,PlanElement> elements;
		protected PlanRepository rep;
		
		protected PlanParser parser;
		//protected List<KeyValuePair<long,long>> references;
		protected List<KeyValuePair<long,long>> epTaskReferences;
		protected List<KeyValuePair<long,long>> transitionAimReferences;
		protected List<KeyValuePair<long,long>> epStateReferences;
		protected List<KeyValuePair<long,long>> stateInTransitionReferences;
		protected List<KeyValuePair<long,long>> stateOutTransitionReferences;
		protected List<KeyValuePair<long,long>> statePlanReferences;
		
		protected List<KeyValuePair<long,long>> planTypePlanReferences;
		protected List<KeyValuePair<long,long>> conditionVarReferences;
		protected List<KeyValuePair<long,long>> paramSubPlanReferences;
		protected List<KeyValuePair<long,long>> paramVarReferences;
		protected List<KeyValuePair<long,long>> paramSubVarReferences;
		protected List<KeyValuePair<long,long>> transitionSynchReferences;
		protected List<KeyValuePair<long,long>> rtmRoleReferences;
		protected List<KeyValuePair<long,long>> quantifierScopeReferences;
		protected List<KeyValuePair<long,long>> charCapReferences;
		protected List<KeyValuePair<long,long>> charCapValReferences;
				
		
		/// <summary>
		/// Ctor
		/// </summary>
		/// <param name="p">
		/// The <see cref="PlanParser"/> handling the plan and role files.
		/// </param>
		/// <param name="rep">
		/// The <see cref="PlanRepository"/> holding all plan elements. Elements will be added to it.
		/// </param>
		public ModelFactory(PlanParser p, PlanRepository rep)
		{
			
			this.parser = p;
			this.rep = rep;
			//this.references = new List<KeyValuePair<long, long>>();	
			
			this.epTaskReferences = new List<KeyValuePair<long, long>>();
			this.epStateReferences = new List<KeyValuePair<long, long>>();
			this.stateInTransitionReferences = new List<KeyValuePair<long, long>>();
			this.stateOutTransitionReferences = new List<KeyValuePair<long, long>>();
			this.statePlanReferences = new List<KeyValuePair<long, long>>();
			this.transitionAimReferences = new List<KeyValuePair<long, long>>();
			this.planTypePlanReferences = new List<KeyValuePair<long, long>>();
			this.conditionVarReferences = new List<KeyValuePair<long, long>>();
			this.paramSubPlanReferences = new List<KeyValuePair<long, long>>();
			this.paramSubVarReferences = new List<KeyValuePair<long, long>>();
			this.paramVarReferences = new List<KeyValuePair<long, long>>();
			this.transitionSynchReferences = new List<KeyValuePair<long, long>>();
			this.quantifierScopeReferences = new List<KeyValuePair<long, long>>();
			this.rtmRoleReferences = new List<KeyValuePair<long, long>>();
			this.charCapReferences = new List<KeyValuePair<long, long>>();
			this.charCapValReferences = new List<KeyValuePair<long, long>>();
						
			
			this.elements = new Dictionary<long, PlanElement>();
		
		}
		/// <summary>
		/// Computes the sets of reachable states for all entrypoints created.
		/// This speeds up some calculations during run-time.
		/// </summary>
		public void ComputeReachabilities() {
			Console.Write("PP: Computing Reachability Sets...");
			foreach(Plan p in this.rep.Plans.Values) {
				foreach(EntryPoint ep in p.EntryPoints.Values) {
					ep.ComputeReachabilitySet();
				}
			}			
			
			Console.WriteLine("..done!");
		}
		
		public Plan CreatePlan(XmlNode node)
		{									
			long id = this.parser.ParseId(node);

			Plan p = new Plan(id);
			p.FileName = this.parser.CurrentFile;
			SetPlanElementAttributes(p,node);
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("masterPlan");
			
			if(attr != null)
			{
				if("true".Equals(attr.Value.ToLower()))
				{
					p.MasterPlan = true;
				}
			}
						
			
			attr = (XmlAttribute)ac.GetNamedItem("minCardinality");
			if(attr != null)
			{
				p.MinCardinality = Convert.ToInt32(attr.Value);
			}
			
			attr = (XmlAttribute)ac.GetNamedItem("maxCardinality");
			
			if(attr != null)
			{
				p.MaxCardinality = Convert.ToInt32(attr.Value);
			}
			
			attr = (XmlAttribute)ac.GetNamedItem("utilityThreshold");
			if(attr != null)
			{
				p.UtilityThreshold = Double.Parse(attr.Value, CultureInfo.InvariantCulture.NumberFormat);
			}
			
			this.AddElement(p);
			this.rep.Plans.Add(p.Id,p);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				
				//do not create references here
				if(IsReferenceNode(child)) { 
					AlicaEngine.Get().Abort(String.Format("PP: Plan Child is reference: {0}",child));
					//long cid = this.parser.ParseId(node);
					//AddReference(id,cid);					
					
				} else {
					XmlAttribute cattr;
					switch(child.LocalName) {
						case "entryPoints":
							EntryPoint ep = CreateEntryPoint(child);
							p.EntryPoints.Add(ep.Id,ep);
							ep.InPlan = p;
						break;
						case "states":
							cattr = (XmlAttribute)child.Attributes.GetNamedItem("xsi:type");
							if (cattr == null) { //ordinary state
								State s = CreateState(child);
								p.States.AddFirst(s);
								s.InPlan = p;	
							}	
							else if(cattr.Value.Equals("alica:SuccessState")) {
								SuccessPoint suc = CreateSuccessPoint(child);
								suc.InPlan = p;
								p.SuccessPoints.AddFirst(suc);
								p.States.AddFirst(suc);
							} else if(cattr.Value.Equals("alica:FailureState")) {
								FailurePoint f = CreateFailurePoint(child);
								f.InPlan = p;
								p.FailurePoints.AddFirst(f);
								p.States.AddFirst(f);
							}
							else {
								AlicaEngine.Get().Abort(String.Format("PP: Unknown State type: {0}",cattr.Value));								
							}
													
						break;
						case "transitions":
							Transition t = CreateTransition(child,p);
							p.Transitions.AddFirst(t);							
						break;
						
						case "conditions":
							//long cid = this.parser.ParseId(child);
							cattr = (XmlAttribute)child.Attributes.GetNamedItem("xsi:type");
							if (cattr == null) {
								AlicaEngine.Get().Abort(String.Format("PP: Condition without xsi:type in plan {0}",p.Name));								
							}
							if(cattr.Value.Equals("alica:RuntimeCondition")) {
								RuntimeCondition rc = CreateRuntimeCondition(child);
								rc.AbstractPlan = p;
								p.RuntimeCondition = rc;
							} else
							if (cattr.Value.Equals("alica:PreCondition")) {
								PreCondition pc = CreatePreCondition(child);
								pc.AbstractPlan = p;
								p.PreCondition = pc;
							} else
							if (cattr.Value.Equals("alica:Result")) {
								Result r = CreateResult(child);
								p.Result = r;								
							}
							else {
								AlicaEngine.Get().Abort(String.Format("PP: Unknown Condition type: {0}",cattr.Value));								
							}
						break;
						case "vars":
							Variable v = CreateVariable(child);
							p.Variables.AddFirst(v);
							
						break;
						case "synchronisations":
							SyncTransition st = CreateSyncTransition(child);
							st.Plan = p;
							p.SyncTransitions.AddFirst(st);
						break;
						case "rating":
							//ignore							
						break;
						default:
							AlicaEngine.Get().Abort(String.Format("PP: Unhandled Plan Child: {0}",child.LocalName));							
						break;
					}
					
				
				
				}
			}
						
			return p;
		}
		protected SyncTransition CreateSyncTransition(XmlNode node) {
			SyncTransition s = new SyncTransition();
			s.Id = parser.ParseId(node);
			SetPlanElementAttributes(s,node);
			
			XmlAttribute attr = ((XmlAttribute)node.Attributes.GetNamedItem("talkTimeout"));
			s.TalkTimeOut = Convert.ToUInt64(attr.Value);
			attr = ((XmlAttribute)node.Attributes.GetNamedItem("syncTimeout"));
			s.SyncTimeOut = Convert.ToUInt64(attr.Value);			
			
			this.AddElement(s);
			this.rep.SyncTransitions.Add(s.Id,s);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
								
				switch(child.LocalName) {
					case "inSync":
						//silently ignore
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Synchtransition Child: {0}",child.LocalName));						
					break;
				}
			}
			return s;
		}
		protected Variable CreateVariable(XmlNode node) {
			string type = "";			
			XmlAttribute attr = ((XmlAttribute)node.Attributes.GetNamedItem("Type"));
			if (attr!=null) type = attr.Value;			
			Variable v = new Variable(this.parser.ParseId(node),GetNameOfNode(node),type);
			SetPlanElementAttributes(v,node);
			this.AddElement(v);
			this.rep.Variables.Add(v.Id,v);
			return v;
		}
		protected Quantifier CreateQuantifier(XmlNode node) {
			long id  = parser.ParseId(node);
			
			XmlAttributeCollection ac = node.Attributes;
					
			Quantifier q=null;
			
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("xsi:type");
			if (attr == null) {
				AlicaEngine.Get().Abort(String.Format("PP: Quantifier without type! {0}",id));								
			}	
			if(attr.Value.Equals("alica:ForallAgents")) {
					q = new ForallAgents(id);
			} else {
				AlicaEngine.Get().Abort(String.Format("PP: Unsupported quantifier type! {0}",attr.Value));		
			}	
			this.AddElement(q);
			this.rep.Quantifiers.Add(q.Id,q);
			SetPlanElementAttributes(q,node);			
			
			attr = (XmlAttribute)ac.GetNamedItem("scope");
			long cid = Convert.ToInt64(attr.Value);
			this.quantifierScopeReferences.Add(new KeyValuePair<long,long>(q.Id,cid));
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {					
				XmlNode child = (XmlNode) list[i];				
				switch(child.LocalName) {
					case "sorts":
						q.DomainIdentifiers.Add(child.ChildNodes[0].Value);						
					break;
					
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Quantifier Child: {0}",child.LocalName));						
					break;
				}
			}			
			return q;
			
		}
		protected RuntimeCondition CreateRuntimeCondition(XmlNode node) {			
			RuntimeCondition r = new RuntimeCondition(this.parser.ParseId(node));
			SetPlanElementAttributes(r,node);			
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("conditionString");
			if (attr!=null) {
				r.ConditionString = attr.Value;
			}
			this.AddElement(r);
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
				
				long cid = this.parser.ParseId(child);
				switch(child.LocalName) {
					case "vars":
						this.conditionVarReferences.Add(new KeyValuePair<long,long>(r.Id,cid));
					break;
					case "quantifiers":
						Quantifier q = CreateQuantifier(child);
						r.Quantifiers.Add(q);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled RuntimeCondition Child: {0}",child.LocalName));						
					break;
				}
			}
			return r;
			
		}
		protected SuccessPoint CreateSuccessPoint(XmlNode node) {
			long id = this.parser.ParseId(node);

			SuccessPoint p = new SuccessPoint();
			p.Id = id;
			
			SetPlanElementAttributes(p,node);
			
			this.AddElement(p);
			this.rep.States.Add(p.Id,p);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
				
				long cid = this.parser.ParseId(child);
				switch(child.LocalName) {
					case "inTransitions":
						this.stateInTransitionReferences.Add(new KeyValuePair<long,long>(id,cid));
					break;
					case "result":
						Result r = CreateResult(child);
						p.Result = r;
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled SuccessPoint Child: {0}",child.LocalName));						
					break;
				}
			}
			return p;			
		}
		protected FailurePoint CreateFailurePoint(XmlNode node) {
			long id = this.parser.ParseId(node);

			FailurePoint p = new FailurePoint();
			p.Id = id;
			
			SetPlanElementAttributes(p,node);
			
			this.AddElement(p);
			this.rep.States.Add(p.Id,p);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
				
				long cid = this.parser.ParseId(child);
				switch(child.LocalName) {
					case "inTransitions":
						this.stateInTransitionReferences.Add(new KeyValuePair<long,long>(id,cid));
					break;
					case "result":
						Result r = CreateResult(child);
						p.Result = r;
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled FailurePoint Child: {0}",child.LocalName));						
					break;
				}
			}
			return p;			
		}
		public void CreatePlanType(XmlNode node) {
			long id = this.parser.ParseId(node);	
			PlanType pt = new PlanType();
			pt.Id = id;
			SetPlanElementAttributes(pt,node);
			pt.FileName = this.parser.CurrentFile;
			this.AddElement(pt);
			this.rep.PlanTypes.Add(id,pt);
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
				
				
				switch(child.LocalName) {
					case "plans":
						XmlAttribute cattr = (XmlAttribute)child.Attributes.GetNamedItem("activated");						
						if(cattr.Value.ToLower().Equals("true")) {
							XmlNode plan = (XmlNode) child.ChildNodes[0];
							long cid = this.parser.ParseId(plan);
							#if PP_DEBUG
							Console.WriteLine("PP: Adding activated plan {0} to {1}",cid,pt.Name);
							#endif
							this.planTypePlanReferences.Add(new KeyValuePair<long,long>(id,cid));
						
						} else {
							#if PP_DEBUG
							Console.WriteLine("PP: Skipping deactivated plan");
							#endif
						}
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled PlanType Child: {0}",child.LocalName));						
					break;
				}
			}
		}
		public void CreateBehaviour(XmlNode node)
		{									
			long id = this.parser.ParseId(node);

			Behaviour b = new Behaviour();
			b.FileName = this.parser.CurrentFile;
			b.Id = id;
			
			SetPlanElementAttributes(b,node);
			
			
			
			this.AddElement(b);
			this.rep.Behaviours.Add(b.Id,b);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				
			
				switch(child.LocalName) {
					case "configurations":
						BehaviourConfiguration bc = CreateBehaviourConfiguration(child);
						bc.Behaviour = b;
						b.Configurations.AddFirst(bc);	
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Behaviour Child: {0}",child.LocalName));
					break;
				}
			}
						
			
		}
		protected BehaviourConfiguration CreateBehaviourConfiguration(XmlNode node) {
			BehaviourConfiguration b = new BehaviourConfiguration(this.parser.ParseId(node));
			b.FileName = this.parser.CurrentFile;
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("masterPlan");
			
			if(attr != null && "true".Equals(attr.Value.ToLower()))	{
					b.MasterPlan = true;				
			}					
			//For backwards compability:
			attr = (XmlAttribute)ac.GetNamedItem("receiveRemoteCommand");
			if(attr != null && "true".Equals(attr.Value.ToLower()))
			{
				b.EventDriven = true;
			}
			
			//For backwards compability:
			attr = (XmlAttribute)ac.GetNamedItem("visionTriggered");
			if(attr != null && "true".Equals(attr.Value.ToLower()))
			{
				b.EventDriven = true;
			}
			
			attr = (XmlAttribute)ac.GetNamedItem("eventDriven");
			if(attr != null && "true".Equals(attr.Value.ToLower()))
			{
				b.EventDriven = true;
			}
			
			attr = (XmlAttribute)ac.GetNamedItem("deferring");
			if(attr != null)
			{
				b.Deferring = Int32.Parse(attr.Value, CultureInfo.InvariantCulture.NumberFormat);
			}
			
			attr = (XmlAttribute)ac.GetNamedItem("frequency");
			if(attr != null)
			{
				b.Frequency = Int32.Parse(attr.Value, CultureInfo.InvariantCulture.NumberFormat);
			}
			
			
			SetPlanElementAttributes(b,node);
			this.elements.Add(b.Id,b);
			XmlNodeList list = node.ChildNodes;
			
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				//long cid = this.parser.ParseId(child);
				switch (child.LocalName) {
					case "parameters":
						XmlAttribute cattr = (XmlAttribute)child.Attributes.GetNamedItem("key");
						string key = cattr.Value;
						cattr = (XmlAttribute)child.Attributes.GetNamedItem("value");
						string value = cattr.Value;
						b.Parameters.Add(key,value);
					break;
					case "vars":
						Variable v = CreateVariable(child);
						b.Variables.AddFirst(v);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled BehaviourConfiguration Child: {0}",child.LocalName));						
						break;
				}
				
			}
			
			
			return b;
			
		}
		
		protected Transition CreateTransition(XmlNode node, Plan plan) {
			Transition t = new Transition();
			t.Id = this.parser.ParseId(node);
			
			SetPlanElementAttributes(t,node);
			
			this.AddElement(t);
			this.rep.Transitions.Add(t.Id,t);
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				long cid = this.parser.ParseId(child);
				switch (child.LocalName) {
					case "inState": //silently ignore					
						break;
					case "outState": 
						this.transitionAimReferences.Add(new KeyValuePair<long,long>(
					              t.Id,cid));
						break;
					case "preCondition":
						PreCondition p = CreatePreCondition(child);
						t.PreCondition = p;
						p.AbstractPlan = plan;
						break;
					case "synchronisation":
						this.transitionSynchReferences.Add(new KeyValuePair<long,long>(t.Id,cid));
						break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Transition Child: {0}",child.LocalName));						
						break;
				}
				
			}
			return t;
			
		}
		protected PreCondition CreatePreCondition(XmlNode node) {
			PreCondition p = new PreCondition(this.parser.ParseId(node));
			
			SetPlanElementAttributes(p,node);	
			XmlAttribute attr = (XmlAttribute)node.Attributes.GetNamedItem("conditionString");
			if (attr!=null) {
				p.ConditionString = attr.Value;
			}
			attr = (XmlAttribute)node.Attributes.GetNamedItem("enabled");
			if (attr!=null) {
				p.Enabled = Boolean.Parse(attr.Value);
			} else {
				p.Enabled = true;
			}
			this.AddElement(p);
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				long cid = this.parser.ParseId(child);
				
				switch (child.LocalName) {
					case "vars":
						this.conditionVarReferences.Add(new KeyValuePair<long,long>(p.Id,cid));
					break;
				case "quantifiers":
						Quantifier q = CreateQuantifier(child);
						p.Quantifiers.Add(q);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Precondition Child: {0}",child.LocalName));						
						break;
				}
				
			}
			return p;
			
		}
		protected Result CreateResult(XmlNode node) {
			Result res = new Result(this.parser.ParseId(node));
			SetPlanElementAttributes(res,node);
			XmlAttribute attr = (XmlAttribute)node.Attributes.GetNamedItem("conditionString");
			if (attr != null) {
				res.ConditionString = attr.Value;
			}
			this.AddElement(res);
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{							
				XmlNode child = (XmlNode) list[i];
				//long cid = this.parser.ParseId(child);
				
				switch (child.LocalName) {				
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Result Child: {0}",child.LocalName));						
						break;
				}				
			}
			return res;
		}
		protected State CreateState(XmlNode node) {
			
			State s = new State();
			s.Id = this.parser.ParseId(node);
			
			SetPlanElementAttributes(s,node);
			
			this.AddElement(s);
			this.rep.States.Add(s.Id,s);
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
				long cid = this.parser.ParseId(child);
				switch (child.LocalName) {
					case "inTransitions":
						cid = this.parser.ParseId((XmlNode)child);
						this.stateInTransitionReferences.Add(new KeyValuePair<long,long>(s.Id,cid));					
					break;
					case "outTransitions":
						cid = this.parser.ParseId((XmlNode)child);
						this.stateOutTransitionReferences.Add(new KeyValuePair<long,long>(s.Id,cid));					
					break;					
					case "plans":
						this.statePlanReferences.Add(new KeyValuePair<long,long>(
					                 s.Id,cid));
					break;
					case "parametrisation":
						Parametrisation para = CreateParametrisation(child);
						s.Parametrisation.Add(para);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled State Child: {0}",child.LocalName));
						break;
				}
				
			}
			return s;
			
		}
		protected Parametrisation CreateParametrisation(XmlNode node) {
			Parametrisation p = new Parametrisation();
			p.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(p,node);
				
			this.AddElement(p);
			XmlNodeList list = node.ChildNodes;
			
			for(int i=0; i < list.Count; i++) {			
				
				XmlNode child = (XmlNode) list[i];
			
				long cid = this.parser.ParseId(child);
				switch (child.LocalName) {
					case "subplan":
						this.paramSubPlanReferences.Add(new KeyValuePair<long,long>(p.Id,cid));
					break;
					case "subvar":
						this.paramSubVarReferences.Add(new KeyValuePair<long,long>(p.Id,cid));
					break;
					case "var":
						this.paramVarReferences.Add(new KeyValuePair<long,long>(p.Id,cid));
					break;

					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Parametrisation Child: {0}",child.LocalName));
						break;
				}
				
			}
			
			return p;
		}
		protected EntryPoint CreateEntryPoint(XmlNode node) {
			EntryPoint ep = new EntryPoint();
			
			ep.Id = this.parser.ParseId(node);
			
			SetPlanElementAttributes(ep,node);
			
			XmlAttributeCollection ac = node.Attributes;
			
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("minCardinality");
			if(attr != null)
			{
				ep.MinCardinality = Convert.ToInt32(attr.Value);
			}
			
			attr = (XmlAttribute)ac.GetNamedItem("maxCardinality");
			if(attr != null)
			{
				ep.MaxCardinality = Convert.ToInt32(attr.Value);
			}
			attr = (XmlAttribute)ac.GetNamedItem("successRequired");
			if (attr != null) {
				ep.SuccessRequired = attr.Value.ToLower().Equals("true");
			} else ep.SuccessRequired = false;
			this.AddElement(ep);
			this.rep.EntryPoints.Add(ep.Id,ep);
			
			XmlNodeList list = node.ChildNodes;
			bool haveState = false;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				long tid;
				switch (child.LocalName) {
					case "state":
						tid = this.parser.ParseId(child);
						this.epStateReferences.Add(new KeyValuePair<long,long>(ep.Id,tid));
					haveState = true;
					break;
					case "task":
						tid = this.parser.ParseId(child);
						this.epTaskReferences.Add(new KeyValuePair<long,long>(ep.Id,tid));
					break;
						
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled EntryPoint Child: {0}",child.LocalName));						
						break;
				}
				
			}
			if(!haveState) {
				AlicaEngine.Get().Abort(String.Format("PP: Entrypoint {0} does not identify an initial state!",ep.Id));
			}
			return ep;
			
		}
		public void CreateTasks(XmlNode node) {
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("defaultTask");
			long defaultTask = Convert.ToInt64(attr.Value);
			#if PP_DEBUG
			Console.WriteLine("PP: Default Task is {0}", defaultTask);
			#endif
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)	{			
				
				XmlNode child = (XmlNode) list[i];
				long id = this.parser.ParseId(child);
				Task t;
				
				t = new Task(id == defaultTask);
				t.Id = id;
				SetPlanElementAttributes(t,child);
				this.AddElement(t);	
				this.rep.Tasks.Add(t.Id,t);
			}
				
				
			
		}
		public RoleDefinitionSet CreateRoleDefinitionSet(XmlNode node) {
			RoleDefinitionSet r = new RoleDefinitionSet();
			r.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(r,node);
			
			this.AddElement(r);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				
				switch (child.LocalName) {
					case "roles":						
						Role role = CreateRole(child);
						r.Roles.Add(role);
						
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled RoleDefinitionSet Child: {0}",child.LocalName));						
					break;
				}
				
			}
			return r;
		}
		protected Role CreateRole(XmlNode node) {
			Role r = new Role();
			r.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(r,node);
			
			this.AddElement(r);
			this.rep.Roles.Add(r.Id,r);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				//XmlAttribute cattr;
				
				switch (child.LocalName) {						
					case "characteristics":
						Characteristic cha = CreateCharacteristic(child);
						r.Characteristics.Add(cha.Name, cha);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled RoleDefinitionSet Child: {0}",child.LocalName));						
					break;
					
				}
				
			}
			return r;
			
		}
		
		protected Characteristic CreateCharacteristic(XmlNode node) {
			Characteristic cha = new Characteristic();
			cha.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(cha,node);
			XmlAttribute cattr = (XmlAttribute)node.Attributes.GetNamedItem("weight");
			cha.Weight = Convert.ToDouble(cattr.Value);
			
			this.AddElement(cha);
			this.rep.Characteristics.Add(cha.Id,cha);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				
				
				switch (child.LocalName) {							
					case "capability":
						long capid = this.parser.ParseId(child);
						this.charCapReferences.Add(new KeyValuePair<long,long>(cha.Id,capid));
						break;
					case "value":
						long capValid = this.parser.ParseId(child);
						this.charCapValReferences.Add(new KeyValuePair<long,long>(cha.Id,capValid));								
						break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Characteristic Child: {0}",child.LocalName));						
					break;
				}
				
			}
			return cha;
			
		}
		
		public CapabilityDefinitionSet CreateCapabilityDefinitionSet(XmlNode node) {
			CapabilityDefinitionSet capSet = new CapabilityDefinitionSet();
			capSet.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(capSet,node);	
			this.AddElement(capSet);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				
				switch (child.LocalName) {
					case "capabilities":
						Capability cap = CreateCapability(child);
						capSet.Capabilities.Add(cap);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled CapabilityDefinitionSet Child: {0}",child.LocalName));						
					break;
				}
				
			}
			return capSet;
		}
		
		protected Capability CreateCapability(XmlNode node) {
			Capability cap = new Capability();
			cap.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(cap,node);
			
			this.AddElement(cap);
			this.rep.Capabilities.Add(cap.Id,cap);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				//XmlAttribute cattr;
				
				switch (child.LocalName) {					
					case "capValues":					
						CapValue val = new CapValue();
						val.Id = this.parser.ParseId(child);
						SetPlanElementAttributes(val,child);			
						this.AddElement(val);
						cap.CapValues.Add(val);									
						break;
					
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled Capability Child: {0}",child.LocalName));						
					break;
				}
				
			}
			return cap;
			
		}		
		
		public RoleSet CreateRoleSet(XmlNode node, Plan masterPlan) {
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("default");
			bool isDefault = (attr!=null) && attr.Value.ToLower().Equals("true");
			attr = (XmlAttribute)ac.GetNamedItem("usableWithPlanID");
			long pid =0;
			if (attr!=null) pid = Convert.ToInt64(attr.Value);
			
			bool isUseable = (attr!=null) && pid==masterPlan.Id;
			
			if (!isDefault && !isUseable) {
				AlicaEngine.Get().Abort("PP: Selected RoleSet is not default, nor useable with current masterplan");
			}
			RoleSet rs = new RoleSet();
			rs.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(rs,node);
			rs.IsDefault = isDefault;
			rs.UsableWithPlanID = pid;
			this.AddElement(rs);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				
				switch (child.LocalName) {		
					case "mappings":
						RoleTaskMapping rtm = CreateRoleTaskMapping(child);
						rs.RoleTaskMappings.AddFirst(rtm);
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled RoleSet Child: {0}",child.LocalName));					
						break;
				}
				
			}
			
			
			return rs;
		}
		protected RoleTaskMapping CreateRoleTaskMapping(XmlNode node) {
			RoleTaskMapping rtm = new RoleTaskMapping();
			rtm.Id = this.parser.ParseId(node);
			SetPlanElementAttributes(rtm,node);
			this.AddElement(rtm);
			
			XmlNodeList list = node.ChildNodes;
			for(int i=0; i < list.Count; i++)
			{			
				
				XmlNode child = (XmlNode) list[i];
				XmlAttribute cattr;
				switch (child.LocalName) {		
					case "taskPriorities":
						cattr = (XmlAttribute)child.Attributes.GetNamedItem("key");
						long tid = Convert.ToInt64(cattr.Value);
						cattr = (XmlAttribute)child.Attributes.GetNamedItem("value");
						double val = Double.Parse(cattr.Value, CultureInfo.InvariantCulture.NumberFormat);
						rtm.TaskPriorities.Add(tid,val);					
					break;
					case "role":
						long cid = this.parser.ParseId(child);
						this.rtmRoleReferences.Add(new KeyValuePair<long,long>(rtm.Id,cid));
					break;
					default:
						AlicaEngine.Get().Abort(String.Format("PP: Unhandled RoleTaskMapping Child: {0}",child.LocalName));						
						break;
				}
				
			}
			return rtm;
		}
		
		
		
		protected void AddElement(PlanElement pe) {
			if (elements.ContainsKey(pe.Id)) {
				AlicaEngine.Get().Abort(String.Format("PP: ERROR Double IDs: {0}",pe.Id));
			}
			elements.Add(pe.Id,pe);
		}
		public Dictionary<long,PlanElement> Elements
		{
			set { this.elements = value; }
			get	{  return this.elements; }
		}
		public bool IsReferenceNode(XmlNode node)
		{
			XmlNodeList children = node.ChildNodes;
			
			for(int i=0; i < children.Count; i++)
			{
				XmlNode n = (XmlNode)children[i];
				if(n.NodeType == XmlNodeType.Text)
				{
					return true;
				}
			}			
			return false;
		}
		
		public string GetNameOfNode(XmlNode node)
		{
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("name");
			if(attr != null)
			{
				return attr.Value;
			}
			else
			{
				//Console.WriteLine("Name Missing in " + node.LocalName);
				return "MISSING-NAME";
			}
		}
		
		public void AttachCharacteristicReferences() {
			Console.Write("PP: Attaching Characteristics references...");
			foreach(KeyValuePair<long,long> pair in this.charCapReferences) {
				Characteristic cha = this.rep.Characteristics[pair.Key];
				Capability cap = (Capability)this.elements[pair.Value];
				cha.Capability = cap;
				
				//Console.WriteLine("Attached Characteristic {0} to Capability {1}", cha.Name,cap.Id);
			}
			this.charCapReferences.Clear();
			
			foreach(KeyValuePair<long,long> pair in this.charCapValReferences) {
				Characteristic cha = this.rep.Characteristics[pair.Key];
				CapValue capVal = (CapValue)this.elements[pair.Value];
				cha.CapValue = capVal;
				
				//Console.WriteLine("Attached Characteristic {0} to CapValue {1}", cha.Name,capVal.Id);
			}
			this.charCapValReferences.Clear();
			
			Console.WriteLine("done!");
		}
		
		public void AttachRoleReferences() {
			Console.Write("PP: Attaching Role references...");
			foreach(KeyValuePair<long,long> pair in this.rtmRoleReferences) {
				Role r = this.rep.Roles[pair.Value];
				RoleTaskMapping rtm = (RoleTaskMapping)this.elements[pair.Key];
				r.RoleTaskMapping = rtm;
				rtm.Role = r;
				//Console.WriteLine("Attached role {0} to rtm {1}", r.Name,rtm.Id);
			}
			this.rtmRoleReferences.Clear();
			
			Console.WriteLine("done!");
		}
		
		public void AttachPlanReferences() {
			Console.Write("PP: Attaching Plan references...");
			foreach(KeyValuePair<long,long> pair in this.epTaskReferences) {
				//Console.WriteLine("ID: {0}",pair.Value);
				Task t = this.rep.Tasks[pair.Value];
				//Console.WriteLine("ID: {0}",pair.Key);
				EntryPoint e = (EntryPoint)this.elements[pair.Key];
				
				e.Task = t;
				//Console.WriteLine("Attached task {0} to entrypoint {1}", t.Name,e.Id);
			}
			this.epTaskReferences.Clear();
			foreach(KeyValuePair<long,long> pair in this.transitionAimReferences) {
				Transition t = (Transition)this.elements[pair.Key];
				State s = GetStateById(pair.Value);
				if (s==null) {
					AlicaEngine.Get().Abort(String.Format("PP: Cannot resolve transition target {0}",pair.Value));					
				}
				t.OutState = s;
				s.InTransitions.Add(t);
				//Console.WriteLine("Attached transition {0} to state {1}",t.Id,s.Name);
			}
			this.transitionAimReferences.Clear();
			
			
			foreach(KeyValuePair<long,long> pair in this.epStateReferences) {				
				EntryPoint e = (EntryPoint)this.elements[pair.Key];
				State s = (State)this.elements[pair.Value];
				e.State = s;				
				//Console.WriteLine("Attached ep {0} to state {1}",e.Id,e.State.Name);
				
			}
			this.epStateReferences.Clear();
			foreach(KeyValuePair<long,long> pair in this.stateInTransitionReferences) {
				State s = (State)this.elements[pair.Key];
				if (this.elements.ContainsKey(pair.Value)) {
					Transition t = (Transition)this.elements[pair.Value];
					
					if(t.OutState != s) {
						AlicaEngine.Get().Abort("PP: Unexpected reference in a transition!");
					}
				} else {
					//Console.WriteLine("Redundant Transition {0} has been removed",pair.Value);
				}
				
				
			}
			this.stateInTransitionReferences.Clear();
			foreach(KeyValuePair<long,long> pair in this.stateOutTransitionReferences) {
				State s = (State)this.elements[pair.Key];
				Transition t = (Transition)this.elements[pair.Value];
				s.OutTransitions.Add(t);
				t.InState = s;
				
			}
			this.stateOutTransitionReferences.Clear();
			
			foreach(KeyValuePair<long,long> pair in this.statePlanReferences) {
				State s = (State)this.elements[pair.Key];
				AbstractPlan p = (AbstractPlan)this.elements[pair.Value];
				s.Plans.AddFirst(p);
			}
			this.statePlanReferences.Clear();
			
			foreach(KeyValuePair<long,long> pair in this.planTypePlanReferences) {
				//Console.WriteLine("Attaching plan {0} to pt {1}", pair.Key,pair.Value);
				PlanType pt = this.rep.PlanTypes[pair.Key];
				Plan p = this.rep.Plans[pair.Value];
				pt.Plans.AddFirst(p);
				//Console.WriteLine("Attached plan {0} to pt {1}", p.Name,pt.Name);
			}
			this.planTypePlanReferences.Clear();
			
			foreach(KeyValuePair<long,long> pair in this.conditionVarReferences) {
				Condition c = (Condition)this.elements[pair.Key];
				Variable v = (Variable)this.elements[pair.Value];
				c.Vars.Add(v);
			}
			
			
			this.conditionVarReferences.Clear();
			foreach(KeyValuePair<long,long> pair in this.paramSubPlanReferences) {
				Parametrisation p = (Parametrisation)this.elements[pair.Key];
				AbstractPlan ap = (AbstractPlan)this.elements[pair.Value];
				p.SubPlan = ap;
			}
			this.paramSubPlanReferences.Clear();
			foreach(KeyValuePair<long,long> pair in this.paramSubVarReferences) {
				Parametrisation p = (Parametrisation)this.elements[pair.Key];				
				Variable v = (Variable)this.elements[pair.Value];
				p.SubVar = v;
			}
			this.paramSubVarReferences.Clear();
			foreach(KeyValuePair<long,long> pair in this.paramVarReferences) {
				Parametrisation p = (Parametrisation)this.elements[pair.Key];
				Variable v = (Variable)this.elements[pair.Value];
				p.Var = v;
			}
			this.paramVarReferences.Clear();
			
			foreach(KeyValuePair<long,long> pair in this.transitionSynchReferences) {
				Transition t = (Transition)this.elements[pair.Key];
				SyncTransition s = (SyncTransition)this.elements[pair.Value];
				t.SyncTransition = s;
				s.InSync.AddFirst(t);
				//Console.WriteLine("Attaching Transition {0} to Synch {1}",t.Id,s.Name);
			}
			this.transitionSynchReferences.Clear();
			
			foreach(KeyValuePair<long,long> pair in this.quantifierScopeReferences) {
				PlanElement pe = this.elements[pair.Value];
				Quantifier q = (Quantifier)this.elements[pair.Key];
				q.SetScope(pe);
			}
			this.quantifierScopeReferences.Clear();
			
			RemoveRedundancy();
			
			Console.WriteLine("done!");
			
		}
		protected State GetStateById(long id) {
			if (this.elements.ContainsKey(id)) {
				PlanElement pe = this.elements[id];
				if (pe is State) return (State)pe;
			}			
			return null;
			
		}
				
		protected void RemoveRedundancy() {
			foreach(Plan p in this.rep.Plans.Values) {
				List<Transition> toremove = new List<Transition>();
				foreach(Transition t in p.Transitions) {
					if (t.InState == null) {
						toremove.Add(t);						
					}
				}
				foreach(Transition t in toremove) {
					p.Transitions.Remove(t);
					#if PP_DEBUG
					Console.WriteLine("Removing initial transition from plan {0}",p.Name);
					#endif
				}
			}
		}
		private void SetPlanElementAttributes(PlanElement p, XmlNode n) {
			XmlAttributeCollection ac = n.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("name");
			if (attr != null) {
				p.Name = attr.Value;
			} else p.Name = "MISSING-NAME";
			attr = (XmlAttribute)ac.GetNamedItem("comment");
			if (attr != null) {
				p.Comment = attr.Value;
			} else p.Comment = String.Empty;
		}

	}
}
