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
package de.uni_kassel.cn.planDesigner.ui.handler;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ISection;

import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.ui.properties.PMLTabbedPropertySheetPage;
import de.uni_kassel.cn.planDesigner.ui.properties.UtilitiesSection;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.IEMFTreeNode;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;

/**
 * 
 */

/**
 * @author till
 *
 * @deprecated will be replaced with the utility feature
 */
public class RemoveModeExpressionHandler extends AbstractHandler
{

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if(sel instanceof IStructuredSelection)
		{
			Object o = ((IStructuredSelection)sel).getFirstElement();
			if(o instanceof IEMFTreeNode)
			{
				EObject modeExpression = ((IEMFTreeNode)o).getModelObject();
				if(modeExpression instanceof UtilityModeExpression)
				{
					UtilitiesSection section = getUtilitiesSection(event);
					removeModeExpression(section, (UtilityModeExpression)modeExpression);
				}
			}
		}
		
		return null;
	}
	
	private void removeModeExpression(UtilitiesSection section, UtilityModeExpression expression)
	{
		PMLTransactionalEditingDomain domain = section.getEditingDomain();
		EditController controller = section.getEditController();

		// unhook objects to listener
		controller.removeFromObject(expression);

		// collect all objects to be deleted
		List<EObject> l = new LinkedList<EObject>();
		l.add(expression);

		// create command
		Command c1 = new DeleteCommand(domain, l);

		// execute command
		UtilityRepositoryUtils.execCommandAndSaveResource(c1, section.getCommandStack(), domain);		
	}

	private UtilitiesSection getUtilitiesSection(ExecutionEvent event)
	{
		IWorkbenchPart wbp = HandlerUtil.getActivePart(event);
		if (wbp instanceof PropertySheet)
		{
			PropertySheet ps = (PropertySheet) wbp;
			IPage currentPage = ps.getCurrentPage();
			if (currentPage instanceof PMLTabbedPropertySheetPage)
			{
				PMLTabbedPropertySheetPage page = (PMLTabbedPropertySheetPage) currentPage;
				ISection sectionAt = page.getCurrentTab().getSectionAtIndex(0);
				if (sectionAt instanceof UtilitiesSection)
				{
					return (UtilitiesSection) sectionAt;
				}
			}
		}

		return null;
	}
	
	@Override
	public boolean isEnabled() {
		return false;
	}

}
