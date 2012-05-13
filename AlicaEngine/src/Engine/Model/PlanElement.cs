

using System;

namespace Alica
{
	/// <summary>
	/// Base class of all model elements
	/// </summary>
	public abstract class PlanElement
	{
						
		/// <summary>
		/// Basic ctor
		/// </summary>		
		public PlanElement()
		{
		}
		/// <summary>
		/// This element's unique id
		/// </summary>
		public long Id
		{
			get; set;			
		}
		/// <summary>
		/// This element's descriptive name.
		/// </summary>
		public string Name
		{
			get; set;
		}
		public string Comment { get; set;}
		
		//public string XMLRepresentation();
	}
}
