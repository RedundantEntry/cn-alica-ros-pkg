using System;
using System.Collections.Generic;
using Alica;
using Castor;
using RosCS;
namespace alica_turtle
{
	public class AlicaTurtle
	{
		WorldModel wm;
		AlicaEngine ae;
		public AlicaTurtle(string[] args)
		{
			//Get the plan to be executed from the command line
			Arguments a = new Arguments();	
			a.SetOption("masterplan|m!", "Set the toplevel-plan to use (mandatory)");
			try {
				a.Consume(args);
			} catch (Exception e) {
				Console.WriteLine(e.Message);
				Console.WriteLine(a.ToString());
				RosSharp.Shutdown();
				Environment.Exit(1);
			}
			string topLevelPlan = a.GetOptionValues("masterplan")[0];
			
			//Instantiate Engine and WorlModel
			this.ae = AlicaEngine.Get();
			this.wm = WorldModel.Get();
			
			//Initialise the engine
			this.ae.Init("", topLevelPlan, ".",false);
			
			
		}
		public void Run() {
			//Start the engine:
			this.ae.Start();
			//Wait for exit:
			while(RosSharp.Ok()) {
				System.Threading.Thread.Sleep(500);
			}
			this.ae.Close();
			this.wm.Close();	
			
		}
		public static void Main(string[] args) {
			RosSharp.Init(SystemConfig.RobotNodeName("turtleControl"),args);
			AlicaTurtle turtle = new AlicaTurtle(args);
			turtle.Run();
			Environment.Exit(0);
		}
	}
}

