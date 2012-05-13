package de.uni_kassel.cn.planDesigner.utility.ui;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.widgets.Widget;

import de.uni_kassel.cn.planDesigner.ui.properties.EditController;

/**
 * Extends the EditController with the ability for views that implement the
 * IEditControllable interface to register themselves
 * 
 * @author till
 * 
 */
public class ManagedEditController extends EditController
{
	private List<IEditControllable>	controllables;

	public ManagedEditController()
	{
		this.controllables = new LinkedList<IEditControllable>();
	}
	
	public boolean addToControllables(IEditControllable c)
	{
		return this.controllables.add(c);
	}	
	
	public boolean removeFromControllables(IEditControllable c)
	{
		return this.controllables.remove(c);
	}
	
	@Override
	protected void doUpdateView(Notification n)
	{
		for (IEditControllable c : this.controllables)
		{
			c.updateView(n);
		}
	}

	@Override
	protected void enterPressed(Widget source)
	{
		for (IEditControllable c : this.controllables)
		{
			c.enterPressed(source);
		}
	}

	@Override
	protected void focusOutEvent(Widget source)
	{
		for (IEditControllable c : this.controllables)
		{
			c.focusOutEvent(source);
		}
	}

	@Override
	protected void modifyEvent(Widget source)
	{
		for (IEditControllable c : this.controllables)
		{
			c.modifyEvent(source);
		}
	}

	@Override
	protected void selectionEvent(Object source)
	{
		for (IEditControllable c : this.controllables)
		{
			c.selectionEvent(source);
		}
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		
		this.controllables = null;
	}
}
