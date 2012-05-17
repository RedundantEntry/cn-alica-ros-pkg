		using System;
		using System.Collections.Generic;
		using Alica;
		
		/*PROTECTED REGION ID(eph1337239596521) ENABLED START*/			//Add additional using directives here
		using alica_turtle;
		/*PROTECTED REGION END*/		
		namespace Alica.Validators
		{	
			//Plan:Example
			public partial class Expressions : AbstractExpressions {
										
				
	
	

				
				/* generated comment
					
	Task: DefaultTask  -> EntryPoint-ID: 1337239596525

				*/
				public static UtilityFunction GetUtilityFunction1337239596521(Plan plan)
				{   
					/*PROTECTED REGION ID(1337239596521) ENABLED START*/
					return new DefaultUtilityFunction(plan);
					/*PROTECTED REGION END*/					
				}


			
	
		
			//State: NewState in Plan: Example
			
	
	
			/*		
			* Transition:
			*   - Name: , ConditionString: ownPos!=null
			*
			* Plans in State: 				
			*   - Plan - (Name): SpawnSelf, (PlanID): 1337242331627 
			*
			* Tasks: 
			*   - DefaultTask (1337239596602) (Entrypoint: 1337239596525)
			*
			* States:
			*   - NewState (1337239596523)
			*   - Spawned (1337242336944)
			*
			* Vars:
			*/
			public static bool F1337242346024(RunningPlan rp)
			{	
				/*PROTECTED REGION ID(1337242346024) ENABLED START*/
					WorldModel wm = WorldModel.Get();
					return wm.OwnPos != null;					
				/*PROTECTED REGION END*/	
				
			}
	
			

		

	


	
		
			//State: Spawned in Plan: Example
			
	
	
			

		

	


	
		
			}	
		}
	