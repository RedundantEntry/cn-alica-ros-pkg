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
/**
 * 
 */
package de.uni_kassel.cn.planDesigner.ui.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.views.properties.PropertySheet;
import org.eclipse.ui.views.properties.tabbed.ISection;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.ui.properties.PMLTabbedPropertySheetPage;
import de.uni_kassel.cn.planDesigner.ui.properties.UtilitiesSection;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.IEMFTreeNode;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;

/**
 * @author till
 *
 * @deprecated will be replaced with the utility feature
 */
public class AddModeHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		UtilitiesSection section = getUtilitiesSection(event);
		
		ISelection sel= section.getTreeViewer().getSelection();
		if(sel instanceof IStructuredSelection)
		{
			Object o = ((IStructuredSelection)sel).getFirstElement();
			if(o instanceof IEMFTreeNode)
			{
				EObject utilityRef = ((IEMFTreeNode)o).getModelObject();
				if(utilityRef instanceof UtilityReference)
				{
					createNewModeStub(section, (UtilityReference)utilityRef);
				}
			}
		}
		
		return null;
	}

	private void createNewModeStub(UtilitiesSection section, UtilityReference utilityRef)
	{
		PMLTransactionalEditingDomain domain = section.getEditingDomain();
		EditController controller = section.getEditController();

		Utility utility = getUtility(domain, utilityRef);

		// create new objects
		UtilityMode utilMode = AlicaFactory.eINSTANCE.createUtilityMode();
		UtilityModeExpression utilIDts = AlicaFactory.eINSTANCE.createUtilityModeExpression();

		// hook new objects to listener
		controller.addToObject(utilMode);
		controller.addToObject(utilIDts);

		// create commands
		CompoundCommand cc = new CompoundCommand(0);

		// utility mode -> utility
		Command c3 = new CreateChildCommand(domain, utility, AlicaPackage.eINSTANCE.getUtility_Modes(), utilMode, null);
		// id task -> utility mode
		Command c4 = new CreateChildCommand(domain, utilMode, AlicaPackage.eINSTANCE.getUtilityMode_Expression(), utilIDts, null);

		cc.append(c3);
		cc.append(c4);

		// execute command
		UtilityRepositoryUtils.execCommandAndSaveResource(cc, section.getCommandStack(), domain);
	}

	private Utility getUtility(final PMLTransactionalEditingDomain domain, final UtilityReference utilityRef)
	{
		Utility util = null;

		try
		{
			util = (Utility) domain.runExclusive(new RunnableWithResult.Impl<Utility>()
			{
				public void run()
				{
					setResult(utilityRef.getUtility());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		return util;
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
