using System;
using System.Collections.Generic;

namespace Alica
{
	/// <summary>
	/// A capability definition set holds all defined capabilities.
	/// </summary>
	public class CapabilityDefinitionSet : PlanElement
	{
		
		public CapabilityDefinitionSet()
		{
			this.Capabilities = new List<Capability>();
		}
		public List<Capability> Capabilities {get; private set;}		
	}
	
	/// <summary>
	/// A capability is used to match agents to roles.
	/// </summary>
	public class Capability : PlanElement
	{
		protected List<CapValue> values;		
		
		/// <summary>
		/// Gets the List of possible values for this capability
		/// </summary>
		/// <value>
		/// The cap values.
		/// </value>
		public List<CapValue> CapValues
		{
			get{ return this.values;}			
		}
		
		public Capability()
		{
			this.values = new List<CapValue>();
		}
		/// <summary>
		/// Computes the similarity between two capability values.
		/// </summary>
		/// <returns>
		/// The value, ranges between 0 and 1.
		/// </returns>
		/// <param name='roleVal'>
		/// Role value.
		/// </param>
		/// <param name='robotVal'>
		/// Robot value.
		/// </param>
		public double SimilarityValue(CapValue roleVal, CapValue robotVal)
		{
			int rlIndex = this.CapValues.IndexOf(roleVal);
			int rbIndex = this.CapValues.IndexOf(robotVal); 
			double[,] sTable;			
			SimilarityTable( out sTable );		
			
			return sTable[rlIndex,rbIndex];
		}
		
		private void SimilarityTable( out double[,] sTable )
		{
			int nCount = this.CapValues.Count;
			sTable = new double[nCount, nCount];
			
			for(int i = 0; i < nCount; i++)
			{
				double k; 
				for(int j = 0; j < nCount; j++)
				{
					k = nCount - Math.Abs(i-j)- 1;						
					sTable[i,j] = k/(nCount-1);
					//s = s + sTable[i,j] + " ";
				}
				//Console.WriteLine(s);
			}
		}
		
		
	}
	/// <summary>
	/// A value for a <see cref="Capability"/>.
	/// </summary>
	public class CapValue : PlanElement
	{
		
	}
}

