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
package de.uni_kassel.cn.planDesigner.utility.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.UtilityView;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.ITreeNode;

/**
 * @author till
 *
 */
public class AddModeHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		ISelection sel = HandlerUtil.getActiveMenuSelection(event);
		if(sel instanceof IStructuredSelection)
		{
			Object o = ((IStructuredSelection)sel).getFirstElement();
			if(o instanceof ITreeNode)
			{
				Object utilityRef = ((ITreeNode)o).getData();
				if(utilityRef instanceof UtilityReference)
				{
					createNewModeStub((UtilityReference)utilityRef);
				}
			}
		}
		
		return null;
	}

	private void createNewModeStub(UtilityReference utilityRef)
	{
		UtilityView utilityView = UtilityActivator.getDefault().getUtilityView();
		
		PMLTransactionalEditingDomain domain = utilityView.getEditingDomain();
		EditController controller = utilityView.getEditController();

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
		UtilityRepositoryUtils.execCommandAndSaveResource(cc, utilityView.getCommandStack(), domain);
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
}
