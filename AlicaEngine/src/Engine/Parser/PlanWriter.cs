using System;
using System.IO;
using System.Xml;
using System.Xml.Schema;
using System.Collections.Generic;
using Castor;
namespace Alica
{
	/// <summary>
	/// The PlanWriter can be used to store generated plans.
	/// </summary>
	public class PlanWriter
	{
		private PlanRepository rep;
		public PlanWriter(PlanRepository rep)
		{ 
			this.Directory = Path.GetTempPath();
			this.rep = rep;
			this.PlansToSave = new List<PlanElement>();
			this.plansSaved = new List<PlanElement>();
			this.esRootDir = Environment.GetEnvironmentVariable("ES_ROOT");
			if(!this.esRootDir.EndsWith(""+Path.DirectorySeparatorChar)) {
				this.esRootDir += Path.DirectorySeparatorChar;
			}
		}
		/// <summary>
		/// Gets or sets the directory to save to.
		/// </summary>
		/// <value>
		/// The directory.
		/// </value>
		public string Directory { get; set;}
		private string esRootDir;
		private string currentFile;
		/// <summary>
		/// Gets or sets the plans to save.
		/// </summary>
		/// <value>
		/// The plans to save.
		/// </value>
		public List<PlanElement> PlansToSave { get; private set;}
		List<PlanElement> plansSaved;
		
		
		/// <summary>
		/// Save all plans in the repository.
		/// </summary>
		public void SaveAllPlans() {
			this.PlansToSave.Clear();
			foreach(Plan p in this.rep.Plans.Values) {
				this.PlansToSave.Add(p);
			}
			SaveFileLoop();
		}
		/// <summary>
		/// Saves all plans added to <see cref="PlansToSave"/>.
		/// </summary>
		public void SaveCollected() {
			SaveFileLoop();
		}
		/// <summary>
		/// Saves a single plan.
		/// </summary>
		/// <param name='p'>
		/// The plan to save.
		/// </param>
		public void SaveSinglePlan(Plan p) {
			if (this.Directory.EndsWith(""+Path.DirectorySeparatorChar)) {
				this.currentFile = this.Directory+p.FileName;
			}
			else this.currentFile = this.Directory+Path.DirectorySeparatorChar+p.FileName;
			XmlDocument doc = CreatePlanXMLDocument(p);			
			
			Console.WriteLine(p.FileName);
			string dir = Path.GetDirectoryName(this.currentFile);
			if(!System.IO.Directory.Exists(dir)) {
				System.IO.Directory.CreateDirectory(dir);
			}
			doc.Save(this.currentFile);
			
			
		}
		private void SaveFileLoop() {
			while(PlansToSave.Count > 0) {
				PlanElement p = PlansToSave[PlansToSave.Count-1];
				PlansToSave.RemoveAt(PlansToSave.Count-1);
				if (p is Plan) {
					SaveSinglePlan((Plan)p);					
				} else {
					throw new NotImplementedException(String.Format("Saving of type {0} is not implemented.",p.GetType()));
				}
				plansSaved.Add(p);
			}
			this.PlansToSave.Clear();
			this.plansSaved.Clear();
		}
		internal XmlDocument CreatePlanXMLDocument(Plan p) {
			XmlDocument doc = new XmlDocument();
			
			XmlDeclaration decl = doc.CreateXmlDeclaration("1.0","ASCII",null);
			doc.AppendChild(decl);
			
			CreatePlanXMLNode(p,doc);		
			
			return doc;
		}
		internal void CreatePlanXMLNode(Plan p, XmlDocument doc) {
			XmlElement xp = doc.CreateElement("alica:Plan","http:///de.uni_kassel.cn");
			doc.AppendChild(xp);
			XmlAttribute xsiType = doc.CreateAttribute("xmi", "version",XmlSchema.InstanceNamespace);
			xsiType.Value = "2.0";
			xp.SetAttributeNode(xsiType);
			//xp.SetAttribute("xmi:version","2.0");
			xp.SetAttribute("xmlns:xmi","http://www.omg.org/XMI");
			xp.SetAttribute("xmlns:xsi","http://www.w3.org/2001/XMLSchema-instance");
						
			AddPlanElementAttributes(p,xp);
			
			xp.SetAttribute("masterPlan",p.MasterPlan.ToString());
			xp.SetAttribute("utilityFunction","");
			xp.SetAttribute("utilityThreshold",p.UtilityThreshold.ToString());
			xp.SetAttribute("minCardinality",p.MinCardinality.ToString());
			xp.SetAttribute("maxCardinality",p.MaxCardinality.ToString());
			
			if(p.PreCondition!=null) {
				XmlElement xc = doc.CreateElement("conditions");
				xp.AppendChild(xc);
				xc.SetAttributeNode(GetXsiTypeAttribute("alica:PreCondition",doc));
				AddPlanElementAttributes(p.PreCondition,xc);				
				xc.SetAttribute("conditionString",p.PreCondition.ConditionString);
				xc.SetAttribute("enabled",p.PreCondition.Enabled.ToString());
				AddConditionChildren(p.PreCondition,xc,doc);
			}
			if(p.RuntimeCondition!=null) {
				XmlElement xc = doc.CreateElement("conditions");
				xp.AppendChild(xc);
				xc.SetAttributeNode(GetXsiTypeAttribute("alica:RuntimeCondition",doc));
				AddPlanElementAttributes(p.RuntimeCondition,xc);
				xc.SetAttribute("conditionString",p.RuntimeCondition.ConditionString);
				AddConditionChildren(p.RuntimeCondition,xc,doc);
			}
			if(p.Result!=null) {
				XmlElement xc = doc.CreateElement("conditions");
				xp.AppendChild(xc);
				xc.SetAttributeNode(GetXsiTypeAttribute("alica:Result",doc));
				AddPlanElementAttributes(p.Result,xc);				
				xc.SetAttribute("conditionString",p.Result.ConditionString);
				AddConditionChildren(p.Result,xc,doc);
			}
			foreach(Variable v in p.Variables) {
				XmlElement xc = doc.CreateElement("vars");
				AddPlanElementAttributes(v,xc);
				if(!String.IsNullOrEmpty(v.Type)) {
					xc.SetAttribute("Type",v.Type);
				}
				xp.AppendChild(xc);
			}
			foreach(EntryPoint e in p.EntryPoints.Values) {
				xp.AppendChild(CreateEntryPointXMLNode(e,doc));
			}
			foreach(State s in p.States) {
				xp.AppendChild(CreateStateXMLNode(s,doc));
			}
			foreach(Transition t in p.Transitions) {
				xp.AppendChild(CreateTransitionXMLNode(t,doc));
			}
			foreach(SyncTransition s in p.SyncTransitions) {
				xp.AppendChild(CreateSynchronisationXMLNode(s,doc));
			}
			
			
			
		}
		private void AddConditionChildren(Condition c,XmlElement xn, XmlDocument doc) {
			foreach(Variable v in c.Vars) {
				XmlElement xc = doc.CreateElement("vars");
				xc.AppendChild(doc.CreateTextNode("#"+v.Id));
				xn.AppendChild(xc);
			}
			foreach(Quantifier q in c.Quantifiers) {
				XmlElement xc = doc.CreateElement("quantifiers");
				AddPlanElementAttributes(q,xc);
				if(q is ForallAgents) {
					xc.SetAttributeNode(GetXsiTypeAttribute("alica:ForallAgents",doc));
				} else {
					throw new Exception(String.Format("Unknown Quantifier: {0}",q));
				}
				xc.SetAttribute("scope",q.GetScope().Id.ToString());
				foreach(string sort in q.DomainIdentifiers) {
					XmlElement xcc = doc.CreateElement("sorts");
					xc.AppendChild(xcc);
					xcc.AppendChild(doc.CreateTextNode(sort));
				}
				xn.AppendChild(xc);				
			}
		}
		
		private XmlElement CreateStateXMLNode(State s, XmlDocument doc) {
			XmlElement xs = doc.CreateElement("states");
			if(s is SuccessPoint) {
				
				xs.SetAttributeNode(GetXsiTypeAttribute("alica:SuccessState",doc));
				SuccessPoint su = (SuccessPoint)s;
				if(su.Result!=null) xs.AppendChild(CreateResultXMLNode(su.Result,doc));
				
			} else if (s is FailurePoint) {
				xs.SetAttributeNode(GetXsiTypeAttribute("alica:FailureState",doc));
				FailurePoint fu = (FailurePoint)s;
				if(fu.Result!=null) xs.AppendChild(CreateResultXMLNode(fu.Result,doc));
			} else {
				
			}
			AddPlanElementAttributes(s,xs);
			
			foreach(Parametrisation p in s.Parametrisation) {
				xs.AppendChild(CreateParametrisationXMLNode(p,doc));
			}
			
			foreach(Transition t in s.InTransitions) {
				XmlElement xc = doc.CreateElement("inTransitions");
				xs.AppendChild(xc);
				xc.AppendChild(doc.CreateTextNode("#"+t.Id));
			}
			
			foreach(Transition t in s.OutTransitions) {
				XmlElement xc = doc.CreateElement("outTransitions");
				xs.AppendChild(xc);
				xc.AppendChild(doc.CreateTextNode("#"+t.Id));
			}
			foreach(AbstractPlan p in s.Plans) {
				XmlElement xc = doc.CreateElement("plans");
				xs.AppendChild(xc);
				if(p is Plan) {
					xc.SetAttributeNode(this.GetXsiTypeAttribute("alica:Plan",doc));
				} else if(p is PlanType) {
					xc.SetAttributeNode(this.GetXsiTypeAttribute("alica:PlanType",doc));
				} else if(p is BehaviourConfiguration) {
					xc.SetAttributeNode(this.GetXsiTypeAttribute("alica:BehaviourConfiguration",doc));
				}
				xc.AppendChild(doc.CreateTextNode(GetRelativeFileName(p)+"#"+p.Id));
			}
			
			return xs;
			
		}
		private XmlElement CreateParametrisationXMLNode(Parametrisation p, XmlDocument doc) {
			XmlElement xr = doc.CreateElement("parametrisation");
			AddPlanElementAttributes(p,xr);
			XmlElement xc = doc.CreateElement("subplan");
			XmlAttribute attr = null;
			if(p.SubPlan is Plan) {
				attr = GetXsiTypeAttribute("alica:Plan",doc);
			} else if (p.SubPlan is PlanType) {
				attr = GetXsiTypeAttribute("alica:PlanType",doc);
			} else if (p.SubPlan is BehaviourConfiguration) {
				attr = GetXsiTypeAttribute("alica:BehaviourConfiguration",doc);
			}
			xc.SetAttributeNode(attr);
			xc.AppendChild(doc.CreateTextNode(GetRelativeFileName(p.SubPlan)+"#"+p.SubPlan.Id));
			xr.AppendChild(xc);
			
			xc = doc.CreateElement("subvar");
			xc.AppendChild(doc.CreateTextNode(GetRelativeFileName(p.SubPlan)+"#"+p.SubVar.Id));
			xr.AppendChild(xc);
			
			xc = doc.CreateElement("var");
			xc.AppendChild(doc.CreateTextNode("#"+p.Var.Id));
			xr.AppendChild(xc);
			return xr;
		}
		private XmlElement CreateResultXMLNode(Result r, XmlDocument doc) {
			XmlElement xr = doc.CreateElement("result");
			AddPlanElementAttributes(r,xr);
			xr.SetAttribute("conditionString",r.ConditionString);
			AddConditionChildren(r,xr,doc);
			return xr;
		}
		private XmlElement CreatePreConditionXMLNode(PreCondition c, XmlDocument doc) {
			XmlElement xr = doc.CreateElement("preCondition");
			AddPlanElementAttributes(c,xr);
			xr.SetAttribute("conditionString",c.ConditionString);
			xr.SetAttribute("enabled",c.Enabled.ToString());
			AddConditionChildren(c,xr,doc);
			return xr;
		}
		private XmlElement CreateSynchronisationXMLNode(SyncTransition s, XmlDocument doc) {
			XmlElement xr = doc.CreateElement("synchronisations");
			AddPlanElementAttributes(s,xr);
			xr.SetAttribute("talkTimeout",s.TalkTimeOut.ToString());
			xr.SetAttribute("syncTimeout",s.SyncTimeOut.ToString());
			xr.SetAttribute("failOnSyncTimeOut","false");
			string synched = "";
			foreach(Transition t in s.InSync) {
				synched+=t.Id+" ";
			}
			xr.SetAttribute("synchedTransitions",synched.Trim());
			return xr;
		}
		private XmlElement CreateTransitionXMLNode(Transition t, XmlDocument doc) {
			XmlElement xt = doc.CreateElement("transitions");
			AddPlanElementAttributes(t,xt);
			xt.SetAttribute("msg","");
			if(t.PreCondition!=null) {
				xt.AppendChild(CreatePreConditionXMLNode(t.PreCondition,doc));
			}
			XmlElement xc = doc.CreateElement("inState");
			xt.AppendChild(xc);
			xc.AppendChild(doc.CreateTextNode("#"+t.InState.Id));
			xc = doc.CreateElement("outState");
			xt.AppendChild(xc);
			xc.AppendChild(doc.CreateTextNode("#"+t.OutState.Id));
			if(t.SyncTransition!=null) {
				xc = doc.CreateElement("synchronisation");
				xt.AppendChild(xc);
				xc.AppendChild(doc.CreateTextNode("#"+t.SyncTransition.Id));
			}
			return xt;
		}
		private XmlElement CreateEntryPointXMLNode(EntryPoint e, XmlDocument doc) {
			XmlElement xe = doc.CreateElement("entryPoints");
			AddPlanElementAttributes(e,xe);			
			xe.SetAttribute("minCardinality",e.MinCardinality.ToString());
			xe.SetAttribute("maxCardinality",e.MaxCardinality.ToString());
			xe.SetAttribute("successRequired",e.SuccessRequired.ToString());
			XmlElement xc = doc.CreateElement("state");
			xe.AppendChild(xc);
			xc.AppendChild(doc.CreateTextNode("#"+e.State.Id));
			xc = doc.CreateElement("task");
			xe.AppendChild(xc);
			xc.AppendChild(doc.CreateTextNode(GetRelativeFileName(this.rep.TaskRepositoryFile)+"#"+e.Task.Id));
			return xe;
		}
		private void AddPlanElementAttributes(PlanElement p, XmlElement x) {
			x.SetAttribute("id",p.Id.ToString());	
			x.SetAttribute("name",p.Name);	
			x.SetAttribute("comment",p.Comment);			
		}
		private XmlAttribute GetXsiTypeAttribute(string type, XmlDocument doc) {
			XmlAttribute xsiType = doc.CreateAttribute("xsi", "type",XmlSchema.InstanceNamespace);
			xsiType.Value = type;
			return xsiType;
		}
		private string GetRelativeFileName(string file) {			
			Uri curdir = new Uri(this.currentFile);
			Uri ufile = null;
			if (Path.IsPathRooted(file)) {
				ufile = new Uri(file);		
			} else {
				if (file.EndsWith(".beh") || file.EndsWith(".pty") || file.EndsWith(".pml")) {
					string tfile = SystemConfig.LocalInstance["Alica"].GetString("Alica.PlanDir");
					if(!tfile.EndsWith(""+Path.DirectorySeparatorChar)) tfile+= Path.DirectorySeparatorChar;
					tfile += file;
					if (!Path.IsPathRooted(tfile)) {
						tfile = esRootDir+tfile;
					}
					ufile = new Uri(tfile);
				}
				else throw new NotImplementedException(String.Format("File reference not implemented: {0} (occurred in file {1})",file,this.currentFile));
			}
			return Uri.UnescapeDataString(curdir.MakeRelativeUri(ufile).ToString());			
		}
		private string GetRelativeFileName(AbstractPlan p) {
			if(this.PlansToSave.Contains(p) || this.plansSaved.Contains(p)) {
				string dirfile = this.Directory;
				if(!dirfile.EndsWith(""+Path.DirectorySeparatorChar)) {
					dirfile += Path.DirectorySeparatorChar;
				}
				dirfile += p.FileName;
				return GetRelativeFileName(dirfile);
			} else {				
				return GetRelativeFileName(p.FileName);
			}
		}
		
	}
}

