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
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
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
public class AddModeExpressionHandler extends AbstractHandler
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
			if(o instanceof ITreeNode)
			{
				Object utilityMode = ((ITreeNode)o).getData();
				if(utilityMode instanceof UtilityMode)
				{
					createNewModeExpressionStub((UtilityMode)utilityMode);
				}
			}
		}
		
		return null;
	}
	
	private void createNewModeExpressionStub(UtilityMode utilityMode)
	{
		UtilityView utilityView = UtilityActivator.getDefault().getUtilityView();
		
		PMLTransactionalEditingDomain domain = utilityView.getEditingDomain();
		EditController controller = utilityView.getEditController();

		// create new objects
		UtilityModeExpression utilModeExpr = AlicaFactory.eINSTANCE.createUtilityModeExpression();

		// hook new objects to listener
		controller.addToObject(utilModeExpr);

		// mode expression -> utility mode
		Command c = new CreateChildCommand(domain, utilityMode, AlicaPackage.eINSTANCE.getUtilityMode_Expression(), utilModeExpr, null);

		// execute command
		UtilityRepositoryUtils.execCommandAndSaveResource(c, utilityView.getCommandStack(), domain);
	}

}
