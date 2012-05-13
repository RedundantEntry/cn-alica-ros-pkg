
using System;
using System.Collections.Generic;

namespace Alica
{
	public delegate void OnTeamChange();

	public interface ITeamObserver
	{
		void Init();
		void Close();
		void Tick(RunningPlan root);
		List<RobotEngineData> GetAvailableRobots();
		List<RobotProperties> GetAvailableRobotProperties();
		List<int> GetAvailableRobotIds();
		int GetOwnId();
		int TeamSize();
		RobotEngineData GetOwnEngineData();
		RobotEngineData GetRobotById(int id);
		RobotProperties GetOwnRobotProperties();
		Dictionary<int,SimplePlanTree> GetTeamPlanTrees();
		//List<SimplePlanTree> GetNewPlanTrees();
			
		int SuccessesInPlan(Plan p);
		SuccessCollection GetSuccessCollection(Plan p);
		void UpdateSuccessCollection(Plan p, SuccessCollection sc);
		
		
		void DoBroadcast(List<long> planmsg);
		
		
		void IgnoreRobot(int rid);
		void UnIgnoreRobot(int rid);
		bool IsRobotIgnored(int rid);
		
		void NotifyTeamLeftPlan(AbstractPlan p);
		void NotifyILeftPlan(AbstractPlan p);
		void MessageReceivedFrom(int rid);
			
		event OnTeamChange OnTeamChangeEvent;
		
		
	}
}
