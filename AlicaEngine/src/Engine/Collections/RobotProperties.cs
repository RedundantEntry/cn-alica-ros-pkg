using System;
using System.Collections.Generic;
using Castor;

namespace Alica
{
	
	public class RobotProperties
	{
		protected int id = -1;
		protected string name = "";		
		
		protected string defaultRole = "";
		protected Dictionary<string, Characteristic> characteristics;
		private Dictionary<long,Capability> Capabilities;
		
		//public Dictionary<string,string> Characteristics {get; private set;}

		public RobotProperties() {				
		}
		public RobotProperties(string name):this() 
		{
			SystemConfig sc = SystemConfig.LocalInstance;
			this.Id = sc["Globals"].TryGetInt (-1, "Globals", "Team", name, "ID");
			this.Name = name;
			//this.Characteristics = new Dictionary<string,string>();		
			this.characteristics = new Dictionary<string, Characteristic>();
		
			//string[] caps = sc["Globals"].GetNames("Globals","Team",name);
			//foreach(string s in caps) 
			//{
			//	if(s.Equals("ID") || s.Equals("DefaultRole")) continue;
			//  	Characteristics.Add(s,sc["Globals"].GetString("Globals","Team",name,s));
			//}
			
			this.Capabilities = AlicaEngine.Get().PR.Capabilities;
			
			string key = ""; string kvalue = "";
			string[] caps = sc["Globals"].GetNames("Globals","Team",this.Name);
			foreach(string s in caps) 
			{
				if(s.Equals("ID") || s.Equals("DefaultRole")) continue;
				key = s;
				kvalue = sc["Globals"].GetString("Globals","Team",this.Name,s);
				//Capability cap = new Capability();
				foreach(Capability cap in this.Capabilities.Values)
				{
					if(cap.Name.Equals(key))
					{
						foreach(CapValue val in cap.CapValues)
						{
							if(val.Name.Equals(kvalue, StringComparison.CurrentCultureIgnoreCase))
							{   
								Characteristic cha = new Characteristic();
								cha.Capability = cap;
								cha.CapValue = val;
								this.characteristics.Add(key, cha);
							}
						}
					}
				}
				
			   	//this.characteristics.Add(key, new Characteristic(key, kvalue));
			}	
			
		
			this.DefaultRole = sc["Globals"].TryGetString("Attack", "Globals", "Team", name, "DefaultRole");

			
		}
		
		
		public Dictionary<string, Characteristic> Characteristics
		{
			//set { this.Characteristics = value; }
			
			get 
			{ 				
				return this.characteristics; 
			}
		}
		
		
		public int Id {
			set { this.id = value; }
			get { return this.id; }
		}
		
		public string DefaultRole {
			set { this.defaultRole = value; }
			get { return this.defaultRole; }
		}
			
		public string Name {
			set { this.name = value; }
			get { return this.name; }
		}
		
		public override string ToString ()
		{
			string ret = "[RobotProperties: Id="+this.Id+" Default Role: "+this.DefaultRole+"\n";
			//foreach(KeyValuePair<string,string> k in this.Characteristics) {
			//	ret += k.Key + " = " + k.Value +"\n";
			//}
			foreach(KeyValuePair<string,Characteristic> k in this.Characteristics)
			{
				ret += k.Key + " = " + k.Value.CapValue.Name +"\n";
			}
			return ret;			
		}
	}
}
