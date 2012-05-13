using System;
using System.Collections;
using System.Collections.Generic;
using C5;

namespace Alica
{		
	// DummyRoleAssignment
	public class RoleAssignment : IRoleAssignment
	{
		protected bool updateRoles = false;
		private RoleSet roleSet;
		// Dictionary<ROBOT, ROLE>
		private Dictionary<int, Role> robotRoleMapping;	
		/// Sorted roles according to utility values of a robot	
		private C5.SortedArray<RobotRoleUtility> sortedRobots;		
		private Dictionary<long,Role> roles;		
		private List<RobotProperties> availableRobots;

		/// <summary>
		/// Current Robot's role.
		/// </summary>
		private Role ownRole;
		/// <summary>
		/// Current Robot's Properties.
		/// </summary>
		private RobotProperties ownRobotProperties;
		private ITeamObserver to;

		public RoleAssignment()
		{
			this.robotRoleMapping = new Dictionary<int,Role>();				
			this.availableRobots  = new List<RobotProperties>();				
			this.sortedRobots 	  = new C5.SortedArray<RobotRoleUtility>();
			
		}
		
#region *** Methods ***
		
		public void Init()
		{
			to = AlicaEngine.Get().TO;
			to.OnTeamChangeEvent += Update;			
			
			this.ownRobotProperties = to.GetOwnRobotProperties();
			RoleUtilities();
			//CalculateRoles();			
		}		
		
		protected void Update()	{
			//lock(this) {
				updateRoles = true;			
			//}
		}
		

		/// <summary>
		/// Assign roles according to capability and role priority list
		/// </summary>
		/// <exception cref='Exception'>
		/// Represents errors that occur during application execution.
		/// </exception>
		protected void RoleUtilities()
		{
			roleSet = AlicaEngine.Get().CurrentRoleSet;	
			this.roles = AlicaEngine.Get().PR.Roles;			
			if(roleSet == null)
			{
				throw new Exception("RA: The current Roleset is null!");
			}
			
			this.availableRobots = AlicaEngine.Get().TO.GetAvailableRobotProperties();
			
			// DEBUG OUTPUT
			Console.WriteLine("RA: Available robots: ");
			foreach(RobotProperties aRobot in this.availableRobots)
			{
				Console.Write("{0} ",aRobot.Id);
			}
			Console.WriteLine();
			double dutility = 0;
		
			RobotRoleUtility rc;				
			this.sortedRobots.Clear();
			foreach (Role rol in this.roles.Values)
			{				
				foreach (RobotProperties rps in availableRobots)
				{
					int y = 0; dutility = 0;
					
					foreach(Characteristic rolChar in rol.Characteristics.Values)
					{
						Characteristic rbChar; 
						if(rps.Characteristics.TryGetValue(rolChar.Name,out rbChar)) 
						{	
							double individualUtility = rolChar.Capability.SimilarityValue(rolChar.CapValue, rbChar.CapValue);							
							if(individualUtility == 0)
							{
								dutility = 0;
								break;
							}
							dutility += rolChar.Weight * individualUtility;							
							y++;
						}								
					}	
					if(y!=0)
					{
						dutility /= y;					
						rc = new RobotRoleUtility(dutility, rps, rol);
							
						this.sortedRobots.Add(rc);
					}
				}		
			}	
			
			if (this.sortedRobots.Count == 0) AlicaEngine.Get().Abort("RA: Could not establish a mapping between robots and roles. Please check capability definitions!");
			
			RolePriority rp = new RolePriority();			
			robotRoleMapping.Clear();
			Console.WriteLine("\n");
			while(this.robotRoleMapping.Count < this.availableRobots.Count)
			{				
				MapRoleToRobot(rp);				
			}
			
			//if (ownRole == null) AlicaEngine.Get().Abort("RA: Cannot find own role!");
						
		}
		
		/// <summary>
		/// Maps the role to robot according to priority list and utility value.
		/// </summary>
		/// <param name='rp'>
		/// Rp.
		/// </param>
		private void MapRoleToRobot(RolePriority rp)
		{			
			foreach (RoleUsage rolUse in rp.PriorityList)
			{	
				foreach(RobotRoleUtility rc in this.sortedRobots)
				{					
					if(rolUse.Role.Equals(rc.Role)) //If role from priority list doesn't match with RobotRoleUtility's role
					{
						if(robotRoleMapping.ContainsKey(rc.Robot.Id)|| rc.UtilityValue == 0)
							continue;
						
						robotRoleMapping.Add(rc.Robot.Id, rc.Role);						
						if(ownRobotProperties.Id == rc.Robot.Id)
						{	
							ownRole = rc.Role;
						}
						to.GetRobotById(rc.Robot.Id).LastRole = rc.Role;
						break;		

					}							
				}
			}

		}
					
		
		
		public Role GetRole(int robotId) {
			// If DirtyFlag (updateRoles) is set, we have to recalculate the roles
			/*if(this.updateRoles) 
			{
				lock(this) {
					this.RoleUtilities();
					//this.CalculateRoles();
					this.updateRoles = false;
				}
			}*/
			
			Role r = null;
			if (this.robotRoleMapping.TryGetValue(robotId, out r))
			{
				return r;
			}
			else
			{
				/*this.RoleUtilities();
				if (this.robotRoleMapping.TryGetValue(robotId, out r))
				{
					return r;
				}
				else*/
				
				r = to.GetRobotById(robotId).LastRole;
				if(r!=null) return r;
				throw new Exception("RA: There is no role assigned for robot " + robotId);	
							
			}
		}
		
		
#endregion *** Methods ***
		
#region *** Properties ***		
		public Role OwnRole {
			get
			{								
				/*if(this.updateRoles)
				{
					lock(this) {
						this.RoleUtilities();
						//this.CalculateRoles();
						this.updateRoles = false;
					}
				}*/
				return this.ownRole;
			}
			//set{ this.ownRole = value; }
		}
		public void Tick() {
				//this.RoleUtilities();
				if(this.updateRoles) {
					//lock(this) {						
						//this.CalculateRoles();
						this.updateRoles = false;
					//}
					this.RoleUtilities();
				}
		}
		public Dictionary<int, Role> RobotRoleMapping {
			get
			{
				/*if(this.updateRoles)
				{
					lock(this) {
						this.RoleUtilities();
						//this.CalculateRoles();
						this.updateRoles = false;
					}
				}*/
				return this.robotRoleMapping;
			}
		}
		
	
		
#endregion *** Properties ***
	}//end RoleAssignment
	
	
	
}

