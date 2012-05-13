/**
 * 
 */
package de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

/**
 * Trouble:
 * <ul>
 * <li>
 * <b>setFormItem()</b> is not propagated from the DetailsPart to the
 * DetailsPage! It simply returns false. Thus the DetailsPage needs to be added
 * to the form. SUCKS!</li>
 * </ul>
 * 
 * @author till
 * 
 */
public class UtilityDetailsPage implements IDetailsPage
{
	private boolean			dirty;

	private List<UtilityDetailsPageSection> sections;
	
	public UtilityDetailsPage()
	{
		this.sections = new LinkedList<UtilityDetailsPageSection>();
	}

	public void createContents(Composite parent)
	{
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		for(UtilityDetailsPageSection s : this.sections)
		{
			s.createSection(parent);
		}
	}
	
	public void commit(boolean onSave)
	{
		for(UtilityDetailsPageSection s : this.sections)
		{
			s.commit(onSave);
		}
	}

	public void initialize(IManagedForm form)
	{
		for(UtilityDetailsPageSection s : this.sections)
		{
			s.initialize(form);
		}
	}

	public void setDirty(boolean dirty)
	{
		this.dirty = dirty;
	}

	public boolean isDirty()
	{
		for(UtilityDetailsPageSection s : this.sections)
		{
			this.dirty = this.dirty | s.isDirty();
		}
		
		return this.dirty;
	}

	public boolean isStale()
	{
		return false;
	}

	public void refresh()
	{
		for(UtilityDetailsPageSection s : this.sections)
		{
			s.refresh();
		}
	}
	
	@Override
	public boolean setFormInput(Object input)
	{
		for(UtilityDetailsPageSection s : this.sections)
		{
			s.setFormInput(input);
		}
		
		return false;
	}

	@Override
	public void selectionChanged(IFormPart part, ISelection selection)
	{
		for(UtilityDetailsPageSection s : this.sections)
		{
			s.selectionChanged(part, selection);
		}
	}

	@Override
	public void setFocus()
	{
		// TODO focus on what?
	}

	public boolean add(UtilityDetailsPageSection e)
	{
		return sections.add(e);
	}

	public boolean addAll(Collection<? extends UtilityDetailsPageSection> c)
	{
		return sections.addAll(c);
	}

	public boolean remove(Object o)
	{
		return sections.remove(o);
	}

	public boolean removeAll(Collection<?> c)
	{
		return sections.removeAll(c);
	}

	@Override
	public void dispose()
	{
		
	}	
}