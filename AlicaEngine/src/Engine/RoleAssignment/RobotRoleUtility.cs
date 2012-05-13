using System;
using System.Collections;
using System.Collections.Generic;

namespace Alica
{
	public class RobotRoleUtility : IComparable<RobotRoleUtility>
	{		
		protected RobotProperties robot;
		protected Role role;
		protected double dUtility;
		
		
		//Properties
		public RobotProperties Robot
		{
			get{return this.robot;}
		}
		
		public Role Role
		{
			get{return this.role;}
		}
		public double UtilityValue
		{
			get{return this.dUtility;}
		}
		
		
		public RobotRoleUtility(double dUtilityVal, RobotProperties robot, Role role)
		{
			this.dUtility = dUtilityVal;
			this.robot = robot;
			this.role = role;			
		}		
		
		public int CompareTo (RobotRoleUtility other)
		{			
//			int compare = other.dUtility.CompareTo( this.dUtility );
//			if(compare == 0)
//				compare = other.robot.Id.CompareTo(this.robot.Id);
//			if(compare == 0)
//				compare = other.role.Id.CompareTo(this.role.Id);

			int compare = other.role.Id.CompareTo(this.role.Id);
			if(compare == 0)
				compare = other.dUtility.CompareTo( this.dUtility );
			if(compare == 0)
				compare = other.robot.Id.CompareTo(this.robot.Id);
			return compare;
		}
		
	}
}

