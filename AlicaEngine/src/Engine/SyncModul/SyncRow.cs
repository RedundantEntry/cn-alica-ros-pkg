
using System;
using C5;
using RosCS.AlicaEngine;

namespace Alica
{
	
	public class SyncRow
	{

		protected SyncData syncData = null;

		protected SortedArray<int> receivedBy = new SortedArray<int>();
		
		public SyncRow()
		{			
		}

		public SyncRow(SyncData sd)
		{
			this.syncData = sd;
		}

		public SyncData SyncData
		{
			get {return this.syncData;}
			set {this.syncData = value;}
		}

		public SortedArray<int> ReceivedBy
		{
			get {return this.receivedBy;}
			set {this.receivedBy = value;}
		}
	}
}
