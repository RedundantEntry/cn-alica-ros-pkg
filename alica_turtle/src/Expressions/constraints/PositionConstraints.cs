
	
		using System;
		using System.Collections.Generic;
		using Alica;
		using AD = AutoDiff;
		
		/*PROTECTED REGION ID(ch1337246194633) ENABLED START*/
			using RosCS.turtlesim;
			using alica_turtle;
		/*PROTECTED REGION END*/
		
		namespace Alica.Validators
		{	
			public partial class ConstraintFunctions{
			//Plan:Position
			
/*		
* Tasks: 
* - EP:1337246213064 : DefaultTask (1337239596602)
*
* States:
* - Position (1337246213063)
*
* Vars:
*/

						
		
	
	
/*
* RuntimeCondition - (Name): NewRuntimeCondition
* (ConditionString): True
* Static Variables: []
* Domain Variables:

* forall agents in Position let v = [gposx, gposy] 

*/
public static void GetConstraint1337246237969(ConstraintDescriptor c, RunningPlan rp) {
/*PROTECTED REGION ID(cc1337246237969) ENABLED START*/
	WorldModel wm = WorldModel.Get();
	
	//Firstly, set reasonable boundaries for all variables:
	for(int i=0; i<c.DomainRanges[0].Count; i++) {
			c.DomainRanges[0][i][0,0] = 0; //min x
			c.DomainRanges[0][i][0,1] = 11; //max x
			c.DomainRanges[0][i][1,0] = 0; //min y
			c.DomainRanges[0][i][1,1] = 11; //max y

	}
	//The engine is not aware that we are talking about 2D-positions, so let's construct some:
	List<AD.TVec> positions = new List<AD.TVec>();
			
	for(int i=0; i<c.DomainVars[0].Count; i++) {
			//since there is a domain range for every variable, this could be merged with the loop above
			//for reasons for readibility, we do not.
			AD.TVec p = new AD.TVec(c.DomainVars[0][i][0],c.DomainVars[0][i][1]);
			//Console.WriteLine(c.DomainVars[0][i][0].GetHashCode()+ "\t"+c.DomainVars[0][i][1].GetHashCode());
			positions.Add(p);	
	}
	//Now we have a set of positions, one for each turtle.
	AD.TVec center = new AD.TVec(5,5); // a point on the plane, roughly at the center
	
	//now we constraint the distance of each turtle to the center:
	foreach(AD.TVec pos in positions) { 
		AD.Term dist = ConstraintBuilder.Distance(pos,center);
		c.Constraint &= (dist < 4) & (dist > 3.98);
	}
	//and require a minimal distance between turtles:
	for(int i=0; i<positions.Count-1; i++) {
		for(int j=i+1; j<positions.Count; j++) {
			AD.Term distab = ConstraintBuilder.Distance(positions[i],positions[j]);
			c.Constraint &= (distab > 2);			
		}
	}

	//additionally, we require a maximal distance between pairs of the turtles:			

	for(int i=0; i<positions.Count-1; i++) {
			AD.Term distab = ConstraintBuilder.Distance(positions[i],positions[i+1]);
			c.Constraint &= (distab < 2.15);			
	}
	//turtles are lazy, so let us add a lazyness function:
	AD.Term distSum = 0;
	int count = 0;
	int k=0;
	Dictionary<int, Pose> currentPositions = wm.TurtlePositions;
	foreach(int id in c.AgentsInScope[0]) {
			Pose p = null;
			//we cannot be sure that we know the position of another turtle, so we check:
			if (currentPositions.TryGetValue(id,out p)) {
				count++;
				distSum += ConstraintBuilder.DistanceSqr(positions[k],new AD.TVec(p.X,p.Y));
			}
			k++;
				
	}	
	if (count > 0) {
			c.Utility = 1 - distSum / (count * 1000);
	}
	//tell the solver what we consider a sufficiently good solution wrt. the utility function
	c.UtilitySufficiencyThreshold = 0.98; 
		
/*PROTECTED REGION END*/			    
}				
	
	
	
				

			
	
		
// State: Position


		


	
			
			}
		}
	