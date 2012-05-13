package de.uni_kassel.cn.planDesigner.utility.ui.editor;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail.UtilityDetailsPageSection;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityUtils;

public class UsageSection extends UtilityDetailsPageSection
{
	private ListViewer lv; 
	
	@Override
	public void createSection(Composite parent)
	{
		FormToolkit tk = getManagedForm().getToolkit();
		
		parent.setLayout(new GridLayout());

		final Section s1 = tk.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		s1.setDescription("These plans reference the selected utility");
		s1.setText("Usage");
		s1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite c = tk.createComposite(s1, SWT.WRAP);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		layout.marginWidth = 2;
		layout.marginHeight = 2;
		c.setLayout(layout);
		
		tk.paintBordersFor(c);
		s1.setClient(c);
		
		List l = new List(c, SWT.NONE);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.heightHint = 20;
		gd.widthHint = 100;
		l.setLayoutData(gd);
		l.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER); // forcing border see: "tk.paintBordersFor()" above

		lv = new ListViewer(l);
		lv.setContentProvider(getListViewerContentProvider());
		lv.setLabelProvider(getListViewerLabelProvider());
		
//		lv.refresh();
		lv.setInput(new Object()); // TODO: ... gnar!
	}

	private IBaseLabelProvider getListViewerLabelProvider()
	{
		return new LabelProvider() {
			@Override
			public String getText(Object element)
			{
				if (element instanceof Plan)
				{
					return UtilityUtils.getPlanName((Plan) element);
				}

				return null;
			}
		};
	}

	private IContentProvider getListViewerContentProvider()
	{
		return new IStructuredContentProvider() 
		{
			@Override
			public Object[] getElements(Object inputElement)
			{
				Object input = getInput();
				Set<Object> plans = new HashSet<Object>();
				
				if(input instanceof Utility)
				{
					plans.addAll(UtilityUtils.getPlans((Utility)input));
				}
				else if(input instanceof StructuredSelection)
				{
					StructuredSelection sel = (StructuredSelection)input;
					for(Object o : sel.toList())
					{
						if(o instanceof Utility)
						{
							plans.addAll(UtilityUtils.getPlans((Utility)o));
						}
					}
				}
				
				return (Object[]) plans.toArray(new Object[plans.size()]);
			}

			@Override
			public void dispose()
			{
				// TODO Auto-generated method stub
			}

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
			{
				// TODO Auto-generated method stub
			}
		};
	}
	
	@Override
	public void refresh()
	{
		super.refresh();
		
		this.lv.refresh();
	}
}
