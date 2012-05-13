using System;
using System.Collections;
using System.Collections.Generic;
using Castor;

namespace Alica
{	
	public class RolePriority
	{
		private Dictionary<long,Role> roles;
		private Role role;
		protected List<RoleUsage> priorityList;		
		
		public List<RoleUsage> PriorityList
		{
			get{return this.priorityList;}
		}
		
		
		public RolePriority ()
		{
			SystemConfig sc = SystemConfig.LocalInstance;
			this.priorityList = new List<RoleUsage>();
			this.roles = AlicaEngine.Get().PR.Roles;
			
			string[] priorities = sc["Globals"].GetNames("Globals","RolePriority");
			int order = 0;
			foreach(string roleName in priorities) 
			{
				order = sc["Globals"].GetInt("Globals","RolePriority",roleName);
				
				foreach(Role r in this.roles.Values)
				{
					if(r.Name.Equals(roleName))
					{
						this.role = r;
						break;
					}
				}
				
				this.priorityList.Add(new RoleUsage(order,this.role));
			}
			
		}
	}
	/// <summary>
	/// RoleUsage contains a role with it's priorty.
	/// </summary>
	public class RoleUsage
	{
		protected int priorityOrder;
		protected Role role;						
		protected bool bUsed;
		
		//Properties		
		public int PriorityOrder
		{
			get{return this.priorityOrder;}
		}
		public Role Role
		{
			get{return this.role;}
		}
		public bool IsUsed
		{
			get{return this.bUsed;}
			set{this.bUsed = value;}
		}
		
		
		public RoleUsage(int priorityOrder, Role role)
		{
			this.priorityOrder = priorityOrder;				
			this.role = role;			
			this.bUsed = false;
		}
	}
}

