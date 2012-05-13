package de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.forms.AbstractFormPart;
import org.eclipse.ui.forms.IFormPart;

import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;

public class UtilityDetailsPageSection extends AbstractFormPart
{

	private Object formInput;
	
	private Object input;
	
	@Override
	public boolean setFormInput(Object input)
	{
		this.formInput = input;
		return false;
	}
	
	public Object getFormInput()
	{
		return formInput;
	}
	
	protected EditController getEditController()
	{
		return UtilityActivator.getDefault().getDefaultUtilityEditController();
	}
	
	protected CommandStack getCommandStack()
	{
		return getEditingDomain().getCommandStack();
	}

	protected PMLTransactionalEditingDomain getEditingDomain()
	{
		return UtilityActivator.getDefault().getEditingDomain();
	}
	
	@Override
	public void setFocus()
	{
		// nothing
	}
	
	@Override
	public void refresh()
	{
		super.refresh();
	}

	@Override
	public void commit(boolean onSave)
	{
		super.commit(onSave);
	}
	
	/**
	 * Subclasses should override this Method
	 */
	public void createSection(Composite parent)
	{
		
	}
	
	public void selectionChanged(IFormPart part, ISelection selection) 
	{
		if(selection instanceof StructuredSelection)
		{
			StructuredSelection se = (StructuredSelection)selection;
			
			if(se.getFirstElement() instanceof EObject && se.size() == 1)
			{
				setInput((EObject)se.getFirstElement());
			}
		
			if(se.size() > 1)
			{
				setInput(se);
			}
		
		}
		
		refresh(); // TODO: here?
	}

	protected void setInput(Object input) 
	{
		this.input = input;
	}
	
	public Object getInput()
	{
		return this.input;
	}
	
	protected class ModifyListener implements Listener
	{
		public void handleEvent(Event event)
		{
			if (getManagedForm() != null)
			{
				markDirty();
			}
		}
	}
	
	protected class EnterPressedListener implements Listener
	{
		public void handleEvent(Event event)
		{
			if(event.keyCode == SWT.CR)
			{
				markDirty();
				commit(false);
			}	
		}
	}
}
