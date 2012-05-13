// Copyright 2009 Distributed Systems Group, University of Kassel
// This program is distributed under the GNU Lesser General Public License (LGPL).
//
// This file is part of the Carpe Noctem Software Framework.
//
//    The Carpe Noctem Software Framework is free software: you can redistribute it and/or modify
//    it under the terms of the GNU Lesser General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    The Carpe Noctem Software Framework is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU Lesser General Public License for more details.
package de.uni_kassel.cn.planDesigner.utility.ui;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;

import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail.UtilityMasterDetailsBlock;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityRepositoryUtils;

public class UtilityView extends ViewPart implements IEditControllable
{
	private ManagedForm					managedForm;

	private UtilityMasterDetailsBlock	mdb;

	public UtilityView()
	{
		// add reference to Activator
		UtilityActivator.getDefault().setView(this);
		
		// register to the default EditController
		UtilityActivator.getDefault().getDefaultUtilityEditController().addToControllables(this);
	}
	
	@Override
	public void dispose()
	{
		// unregister from the default EditController
		UtilityActivator.getDefault().getDefaultUtilityEditController().removeFromControllables(this);
		
		// remove reference from Activator
		UtilityActivator.getDefault().setView(null);
		
		super.dispose();
	}

	@Override
	public void createPartControl(Composite parent)
	{
		// use the following for cross checking??
		// getSite().getWorkbenchWindow().getSelectionService().getSelection();

		Composite composite = getWidgetFactory().createComposite(parent);
		FormLayout layout = new FormLayout();
		composite.setLayout(layout);

		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.right = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		Composite comp = getWidgetFactory().createComposite(composite);
		comp.setLayoutData(fd);
		comp.setLayout(new FillLayout());

		ScrolledForm scrolledForm = getWidgetFactory().createScrolledForm(comp);
		scrolledForm.setText("Utility");

		mdb = new UtilityMasterDetailsBlock(getSite(), getEditController());

		managedForm = new ManagedForm(getWidgetFactory(), scrolledForm);
		mdb.createContent(managedForm);
	}

	private FormToolkit	tk	= null;

	private FormToolkit getWidgetFactory()
	{
		if (this.tk == null)
		{
			this.tk = new FormToolkit(Display.getCurrent());
		}

		return this.tk;
	}

	public EditController getEditController()
	{
		return UtilityActivator.getDefault().getDefaultUtilityEditController();
	}

	public PMLTransactionalEditingDomain getEditingDomain()
	{
		return UtilityActivator.getDefault().getEditingDomain();
	}

	public CommandStack getCommandStack()
	{
		return getEditingDomain().getCommandStack();
	}

	private EObject	input;

	public EObject getInput()
	{
		return this.input;
	}

	public void setInput(Object input)
	{
		// save everything old
		UtilityRepositoryUtils.saveResource(getEditingDomain());
		
		// set new stuff
		this.input = (EObject) input;
		this.mdb.setModel(input);

		if (input != null) // this should be caught in EditController imho
		{
			getEditController().addToObject(this.input);
		}
	}

	@Override
	public void updateView(Notification n)
	{
		final Object notifier = n.getNotifier();
		
		if (notifier instanceof EObject)
		{
			refresh();
		}
	}	
	
	public void refresh()
	{
		this.mdb.refresh();
	}

	@Override
	public void focusOutEvent(Widget source)
	{
	}

	@Override
	public void modifyEvent(Widget source)
	{
	}

	@Override
	public void selectionEvent(Object source)
	{
	}

	@Override
	public void enterPressed(Widget source)
	{
	}

	@Override
	public void setFocus()
	{

	}
}
