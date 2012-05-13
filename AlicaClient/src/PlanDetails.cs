
using System;
using System.Collections.Generic;
using Alica;
using Gtk;

namespace AlicaClient
{


	public class PlanDetails : Gtk.Frame
	{
		AbstractPlan plan;
		TreeView content;
		//ListStore store;
		
		public PlanDetails () : base("")
		{
			
//			this.SetSizeRequest(400,200);
			
			
		}
		
		public void SetPlan(AbstractPlan p) {
			this.plan = p;
		}
		public void Update(AbstractPlan p, Dictionary<int,List<TimedSolverVar>> vars) {
			if(this.Child!=null) this.Remove(this.Child);
			this.plan = p;
			if (p == null) {
				this.plan = null;
				this.Visible = false;				
				return;
			}
			int colcount =0;
			
			foreach(List<TimedSolverVar> l in vars.Values) {
				bool found = false;
				foreach(TimedSolverVar tsv in l) {
					if (tsv.IsDomainVar) {
						found = true;
						break;
					}
					else foreach(Variable v in this.plan.Variables) {
						if (v.Id == tsv.Id) {
							found = true;
							break;
						}
					}
				}
				if (found) colcount++;
			}
			if (colcount == 0) {
				this.Visible = false;
				return;
			}
			this.content = new TreeView();	
			TreeViewColumn varcol = new TreeViewColumn();
			varcol.Title = "Variable";
			
			Gtk.CellRendererText crt = new Gtk.CellRendererText();
 			varcol.PackStart(crt, true);
			this.content.AppendColumn(varcol);
			
			//this.content = new Table((uint)this.plan.Variables.Count+1,colcount+1,false);
			Type[] types = new Type[colcount+1];
			types[0] = typeof(string);
			for(int k=1; k<types.Length; k++) {
				types[k] = typeof(string);
			}
			ListStore store = new ListStore(types);
			
			
			//uint i=1;
			
			//store.GetIterFirst(out it);
			foreach(Variable v in this.plan.Variables) {
			 	object[] arr = new object[colcount+1];
				arr[0] = v.Name;
				for(int k=1; k<arr.Length; k++) { arr[k]="-";}
				store.AppendValues(arr);
				
				//i++;				
			}
			//uint domVarStart = i;
			List<long> domVars = new List<long>();
			foreach(KeyValuePair<int,List<TimedSolverVar>> pair in vars) {
				foreach(TimedSolverVar tsv in pair.Value) {
					if(tsv.IsDomainVar && !domVars.Contains(tsv.Id)) {
						domVars.Add(tsv.Id);
						object[] arr = new object[colcount+1];
						arr[0] = "("+tsv.RobotId+")."+tsv.Name;
						for(int k=1; k<arr.Length; k++) { arr[k]="-";}
						store.AppendValues(arr);						
					}
				}
			}
			varcol.AddAttribute(crt, "text", 0);
			
			TreeIter it;
			
			int col = 1;
			foreach(KeyValuePair<int,List<TimedSolverVar>> pair in vars) {
				//uint row = 1;
				bool rowset = false;
				bool found = false;
				TreeViewColumn column;
				store.GetIterFirst(out it);
				foreach(Variable v in this.plan.Variables) {
					foreach(TimedSolverVar tsv in pair.Value) {										
						if (v.Id == tsv.Id) {
							found = true;
							if (!rowset) {
								rowset = true;
								column = new TreeViewColumn();
								column.Title = pair.Key.ToString();
								this.content.AppendColumn(column);
								crt = new Gtk.CellRendererText();
 								column.PackStart(crt, false);
								column.MinWidth = 120;
								column.MaxWidth = 180;
								column.Alignment = -0.33f;
								column.AddAttribute(crt, "text", col);
								//this.content.Attach(new Label(pair.Key.ToString()),col,col+1,0,1);
							}
							store.SetValue(it,col,String.Format("{0,16:0.###}",tsv.Value));
							//this.content.Attach(new Label(tsv.Value.ToString()),col,col+1,row,row+1);
						}						
					}
					//row++;
					store.IterNext(ref it);
				}
				foreach(long id in domVars) {
					foreach(TimedSolverVar tsv in pair.Value) {
						if(tsv.IsDomainVar) {						
							if(tsv.Id == id) {
								found = true;
								if (!rowset) {
									rowset = true;
									column = new TreeViewColumn();
									column.Title = pair.Key.ToString();
									this.content.AppendColumn(column);
									crt = new Gtk.CellRendererText();
			 						column.PackStart(crt, false);
									column.MinWidth = 120;
									column.MaxWidth = 180;
									column.Alignment = -0.33f;
									column.AddAttribute(crt, "text", col);						
								}
								store.SetValue(it,col,String.Format("{0,16:0.###}",tsv.Value));
								
							}
							
						}
					}
					//row++;
					store.IterNext(ref it);
				}				
				if (found) col++;
			}
			
			this.content.Model = store;
			this.Add(this.content);
			
			//this.SetSizeRequest(400,40+20*colcount);			
			this.Visible = true;
			store.SetSortColumnId(0,SortType.Descending);
			store.ChangeSortColumn();
			
			ShowAll();
			
			//Console.WriteLine("Visible");
			this.Label = p.Name;	
			
			
			
			
			//this.Parent.QueueDraw();
			
			
		}
	}
}
