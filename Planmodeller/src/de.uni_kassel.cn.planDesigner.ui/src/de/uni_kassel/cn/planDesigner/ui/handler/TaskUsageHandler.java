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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import de.uni_kassel.cn.alica.EntryPoint;
import de.uni_kassel.cn.alica.Task;
import de.uni_kassel.cn.planDesigner.ui.util.CommonUtils;
import de.uni_kassel.cn.planDesigner.ui.util.TaskUsageDialog;

public class TaskUsageHandler extends AbstractHandler implements IHandler {
	
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection)HandlerUtil.getCurrentSelection(event);
		Task task = (Task)selection.getFirstElement();
		
		Map<EObject, Collection<EStructuralFeature.Setting>> usages = UsageCrossReferencer.findAll(
				Collections.singleton(task), TransactionUtil.getEditingDomain(task).getResourceSet());
		
		List<EntryPoint> entryPoints = new ArrayList<EntryPoint>();
//		System.out.println("Task: " +taskToDelete);
		for(EObject obj : usages.keySet())
		{
//			System.out.println("\tEObject: " +obj);
			for(EStructuralFeature.Setting setting : usages.get(obj))
			{
//				System.out.println("\t\tSetting: " +setting.getEObject());
				if(setting.getEObject() instanceof EntryPoint)
				{
					entryPoints.add((EntryPoint)setting.getEObject());
				}
			}
		}
		
		Shell activeShell = HandlerUtil.getActiveShell(event);
		
		final TaskUsageDialog dialog = new TaskUsageDialog(
				activeShell, 
				CommonUtils.getAffectedPlans(entryPoints),
				"Usage of Task " +task.getName(),
				"The task \"" +task.getName() +"\" is used in the following plans.",
				MessageDialog.INFORMATION);
		
		activeShell.getDisplay().asyncExec(new Runnable(){
			public void run() {
				dialog.open();
			}
		});
		
		return null;
	}

}
