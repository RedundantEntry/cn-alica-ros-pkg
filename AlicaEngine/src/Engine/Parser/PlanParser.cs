//#define PP_DEBUG

using System;
using System.IO;
using System.Xml;
using System.Xml.XPath;
using System.Collections;
using System.Collections.Generic;
using Castor;

namespace Alica
{	
	/// <summary>
	/// The default parser, parsing the XML encoding of an ALICA plan-tree
	/// </summary>
	public class PlanParser : IPlanParser
	{
		
		private SystemConfig sc;
		private ModelFactory mf;
		private PlanRepository rep;
		
		
		private List<string> filesToParse;
		private List<string> filesParsed;
		
		string basePlanPath;
		string baseRolePath;
		string currentDirectory;
		
		private string currentFile;
		internal string CurrentFile {
			get { return currentFile; }
			private set {
				if(value.StartsWith(basePlanPath)) {
					currentFile = value.Substring(basePlanPath.Length);
				} else if(value.StartsWith(baseRolePath)) {
					currentFile = value.Substring(baseRolePath.Length);
				}
				else currentFile = value;
			}
		}
		
		private Plan masterPlan;
		/// <summary>
		/// Ctor
		/// </summary>
		/// <param name="rep">
		/// A <see cref="PlanRepository"/>, in which parsed elements are stored.
		/// </param>
		public PlanParser(PlanRepository rep) {
			
			this.filesParsed = new List<string>();
			this.filesToParse = new List<string>();
			this.mf = new ModelFactory(this, rep);
			this.rep = rep;
			
			this.sc = SystemConfig.LocalInstance;
		
			string esRoot = Environment.GetEnvironmentVariable("ES_ROOT");
			
			
			string planDir = this.sc["Alica"].GetString("Alica.PlanDir");
			string roleDir = this.sc["Alica"].GetString("Alica.RoleDir");
			if (esRoot.LastIndexOf(Path.DirectorySeparatorChar) != esRoot.Length-1) {
				esRoot += Path.DirectorySeparatorChar;
			}	
			if (planDir.LastIndexOf(Path.DirectorySeparatorChar) != planDir.Length-1) {
				planDir += Path.DirectorySeparatorChar;
			}
			if (roleDir.LastIndexOf(Path.DirectorySeparatorChar) != roleDir.Length-1) {
				roleDir += Path.DirectorySeparatorChar;
			}
			if (!Path.IsPathRooted(planDir)) {
				basePlanPath = Path.Combine(esRoot,planDir);				
			} else basePlanPath = planDir;
			
			if (!Path.IsPathRooted(roleDir)) {	
				baseRolePath = Path.Combine(esRoot,roleDir);					
			} else baseRolePath = roleDir;		
			
			if (!Directory.Exists(basePlanPath)) {
				AlicaEngine.Get().Abort(String.Format("PP: Cannot find plan directoy: {0}",basePlanPath));				
			}
			if (!Directory.Exists(baseRolePath)) {
				AlicaEngine.Get().Abort(String.Format("PP: Cannot find role directoy: {0}",baseRolePath));				
			}
		
		}
		
		
		
		/// <summary>
		/// Provides access to all parsed elements, indexed by their id.
		/// </summary>
		/// <returns>
		/// A <see cref="Dictionary<System.Int64, PlanElement>"/>
		/// </returns>
		public Dictionary<long,PlanElement> GetParsedElements()
		{
			return this.mf.Elements;
		}
		/// <summary>
		/// Parses a plan tree
		/// </summary>
		/// <param name="masterplan">
		/// The name of the top-level plan, a <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// The top-level <see cref="Plan"/>.
		/// </returns>
		public Plan ParsePlanTree(string masterplan) {
			string topFile = FindPmlFile(this.basePlanPath,masterplan);
			
			
			if (topFile.Equals("")) {
				AlicaEngine.Get().Abort(String.Format("PP: Cannot find Masterplan {0} in {1}",masterplan,this.basePlanPath));				
			}
			this.currentDirectory =  Directory.GetParent(topFile).FullName+Path.DirectorySeparatorChar;
			Console.WriteLine("PP: Using Masterplan {0}",topFile);
			this.CurrentFile = topFile;
			this.masterPlan = ParsePlanFile(topFile);
			ParseFileLoop();
			this.mf.ComputeReachabilities();
			return this.masterPlan;
			
		}
		/// <summary>
		/// Parse all plans available.
		/// </summary>
		public void ParseAllPlans() {
			GetAllPmlFiles(this.basePlanPath);
			ParseFileLoop();
			this.mf.ComputeReachabilities();
			
		}
		/// <summary>
		/// Parse a roleset
		/// </summary>
		/// <param name="roleSetName">
		/// The name of the roleset to parse, a <see cref="System.String"/>. May be empty, in which case a default roleset is looked for.
		/// </param>
		/// <param name="roleSetDir">
		/// The relative directory in which to search for a roleset, a <see cref="System.String"/>
		/// </param>
		/// <returns>
		/// The parsed <see cref="RoleSet"/>
		/// </returns>
		public RoleSet ParseRoleSet(string roleSetName,string roleSetDir) {
			if(roleSetName=="") {
				roleSetName = FindDefaultRoleSet(roleSetDir);				
			} else {
				if (roleSetDir.LastIndexOf(Path.DirectorySeparatorChar) != roleSetDir.Length-1 && roleSetDir.Length > 0) {
					roleSetDir += Path.DirectorySeparatorChar;
				}
				if(!Path.IsPathRooted(roleSetDir)) {				
					roleSetName = Path.Combine(Path.Combine(baseRolePath,roleSetDir),roleSetName);
					//roleSetName = baseRolePath+roleSetDir+roleSetName;
				} else {
					roleSetName = Path.Combine(roleSetDir,roleSetName);
					//roleSetDir+roleSetName;
				}

			}
			if (!roleSetName.EndsWith(".rset")) {
				roleSetName += ".rset";
			}
			if(!File.Exists(roleSetName)) {
				AlicaEngine.Get().Abort(String.Format("PP: Cannot find roleset {0}",roleSetName));				
			}
			
			Console.WriteLine("PP: Parsing RoleSet {0}",roleSetName);
			
			this.currentDirectory = Directory.GetParent(roleSetName).FullName+Path.DirectorySeparatorChar;
			
			XmlDocument doc = new XmlDocument();
			
			doc.Load(roleSetName);
			XmlElement node = doc.DocumentElement; 
			this.CurrentFile = roleSetName;
			RoleSet r = this.mf.CreateRoleSet(node,this.masterPlan);
			
			filesParsed.Add(roleSetName);
			
			while(this.filesToParse.Count > 0) {
				string file = filesToParse[0];
				filesToParse.RemoveAt(0);
				this.currentDirectory = Directory.GetParent(file).FullName+"/";
				this.CurrentFile = file;
				if (!File.Exists(file)) {
					AlicaEngine.Get().Abort(String.Format("PP: Cannot Find referenced file {0}",file));					
				}
				if (file.EndsWith(".rdefset")) {					
					ParseRoleDefFile(file);
				}
				else if(file.EndsWith(".cdefset")) 
				{					
					ParseCapabilityDefFile(file);
				}
				else 
				{
					AlicaEngine.Get().Abort("PP: Cannot Parse file "+file);					
				}				
				filesParsed.Add(file);
			}
			
			this.mf.AttachRoleReferences();
			this.mf.AttachCharacteristicReferences();			
			
			
			
			return r;
		}
		
		private void ParseFileLoop() {
			while(this.filesToParse.Count > 0) {
				string file = filesToParse[0];
				filesToParse.RemoveAt(0);
				this.currentDirectory = Directory.GetParent(file).FullName+Path.DirectorySeparatorChar;
				this.CurrentFile = file;
				if (!File.Exists(file)) {
					AlicaEngine.Get().Abort(String.Format("PP: Cannot Find referenced file {0}",file));					
				}
				
				if (file.EndsWith(".pml")) {
					ParsePlanFile(file);
				} else
				if (file.EndsWith(".tsk")) {
					ParseTaskFile(file);
				} else
				if (file.EndsWith(".beh")) {
					ParseBehaviourFile(file);
				} else
				if (file.EndsWith(".pty")) {
					ParsePlanTypeFile(file);
				}
				else {
					AlicaEngine.Get().Abort(String.Format("PP: Cannot Parse file "+file));					
				}
				
				filesParsed.Add(file);
			}
			//now go through all parsed files and fix remote references
			
			this.mf.AttachPlanReferences();
			
			
		}
		private void ParseRoleDefFile(string fileName) {
			#if PP_DEBUG
			Console.WriteLine("PP: parsing RoleDef file: {0}",fileName);
			#endif
			XmlDocument doc = new XmlDocument();
			
			doc.Load(fileName);
			XmlElement node = doc.DocumentElement; 
			
			mf.CreateRoleDefinitionSet(node);

		}
		
		private void ParseCapabilityDefFile(string fileName) {
			#if PP_DEBUG
			Console.WriteLine("PP: parsing CapabilityDef file: {0}",fileName);
			#endif
			XmlDocument doc = new XmlDocument();
			
			doc.Load(fileName);
			XmlElement node = doc.DocumentElement; 			
			mf.CreateCapabilityDefinitionSet(node);

		}
		
		private void ParsePlanTypeFile(string fileName) {
			#if PP_DEBUG
			Console.WriteLine("PP: parsing PlanType file: {0}",fileName);
			#endif
			XmlDocument doc = new XmlDocument();
			
			doc.Load(fileName);
			XmlElement node = doc.DocumentElement; 
			
			mf.CreatePlanType(node);

		}
		private void ParseBehaviourFile(string fileName) {
			#if PP_DEBUG
			Console.WriteLine("PP: parsing Behaviour file: {0}",fileName);
			#endif
			XmlDocument doc = new XmlDocument();
			
			doc.Load(fileName);
			XmlElement node = doc.DocumentElement; 
			
			mf.CreateBehaviour(node);
		}
		
		private void ParseTaskFile(string fileName) {
			#if PP_DEBUG
			Console.WriteLine("PP: parsing Task Repository file: {0}",fileName);
			#endif
			
			XmlDocument doc = new XmlDocument();
			
			doc.Load(fileName);
			XmlElement node = doc.DocumentElement; 
			
			this.rep.TaskRepositoryFile = fileName;
			
			mf.CreateTasks(node);			
		}
		
		private Plan ParsePlanFile(string fileName) {
			#if PP_DEBUG
			Console.WriteLine("PP: parsing Plan file: {0}",fileName);
			#endif
			
			XmlDocument doc = new XmlDocument();
			
			doc.Load(fileName);
				
			XmlElement root = doc.DocumentElement; //get root of xml tree
				
			Plan p = mf.CreatePlan(root);
			
			return p;
				
		}
		
		private string FindDefaultRoleSet(string dir) {	
			if(!Path.IsPathRooted(dir)) {
				dir = Path.Combine(baseRolePath,dir);				
			}
			if (!Directory.Exists(dir)) {
				AlicaEngine.Get().Abort(String.Format("PP: RoleSet directory does not exist: {0}",dir));				
			}
			string[] files = Directory.GetFiles(dir,"*.rset");
			
			foreach(string file in files) {
				XmlDocument doc = new XmlDocument();
				doc.Load(file);				
				XmlElement root = doc.DocumentElement;					
				XmlAttribute attr = (XmlAttribute)root.Attributes.GetNamedItem("default");
				if (attr!=null && attr.Value.Equals("true")) {
					return file;
				}
				
			}
			if (files.Length == 1) return files[0]; //make an effort to parse the single file found
			AlicaEngine.Get().Abort(String.Format("PP: Cannot find a default roleset in directory {0}",dir));			
			return "";
		}
		
		private string FindPmlFile(string path, string plan) {
			string fname = plan+".pml";
			foreach(string file in Directory.GetFiles(path,"*.pml")) {				
				if (file.EndsWith(Path.DirectorySeparatorChar+fname)) {
					return file;
				}
			}
			foreach(string subfolder in Directory.GetDirectories(path)) {
				string file = FindPmlFile(subfolder,plan);
				if (!file.Equals("")) return file;				
			}
			return "";
		}
		private void GetAllPmlFiles(string path) {
			foreach(string file in Directory.GetFiles(path,"*.pml")) {
				filesToParse.Add(file);
			}
			foreach(string subfolder in Directory.GetDirectories(path)) {				
				GetAllPmlFiles(subfolder);
			}
		}
		
		internal long ParseId(XmlNode node)
		{
			long result = -1;
			
			//Console.WriteLine("getidofnode calling for: " + node.LocalName);
			XmlAttributeCollection ac = node.Attributes;
			XmlAttribute attr = (XmlAttribute)ac.GetNamedItem("id");
			
			if(attr != null)
			{				
				try
				{
					result = Convert.ToInt64(attr.Value);
				}
				catch (Exception e)
				{
					AlicaEngine.Get().Abort(String.Format("PP: Cannot convert ID to long: " + e.StackTrace));					
				}
				return result;

			} else {
				//fetch the referenced ID and return it
				XmlNodeList list = node.ChildNodes;
				for(int i=0; i < list.Count; i++)
				{
					XmlNode n = (XmlNode) list[i];
					if(n.NodeType == XmlNodeType.Text)
					{
					
						string[] locator = n.Value.Split('#');
				
						if (!locator[0].Equals("")) {
							string path = this.currentDirectory + locator[0];
							
							DirectoryInfo d = new DirectoryInfo(path);
							
							string absPath = d.FullName;
							//Directory d = new Directory(path);
							
							
							if (!filesParsed.Contains(absPath) && !filesToParse.Contains(absPath)) {
								#if PP_DEBUG
								Console.WriteLine("PP: Adding {0} to parse queue",absPath);
								#endif
								filesToParse.Add(absPath);
							}
						
						}
						return Convert.ToInt64(locator[1]);
						
						
					}					
				}
				Console.Error.WriteLine("Cannot resolve remote reference!\nAttributes of node in question are:");
				foreach(XmlAttribute a in ac) {
					Console.Error.WriteLine(a.Name + ": "+a.Value);
				}
				AlicaEngine.Get().Abort(String.Format("PP: Couldn't resolve remote reference: {0}",node.Name));
				return -1;
			}
		}
		
	}
}
