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
package de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RunnableWithResult;

import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.EntryPoint;
import de.uni_kassel.cn.alica.Task;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.AbstractNoChildrenEMFTreeNode;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;

/**
 * @author till
 * 
 */
public class DUtilityModeExpressionTreeNode extends AbstractNoChildrenEMFTreeNode
{

	public DUtilityModeExpressionTreeNode(PMLTransactionalEditingDomain editingDomain, CommandStack stack, EditController editController, ITreeNode parent, EObject modelObject)
	{
		super(editingDomain, stack, editController, parent, modelObject);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.AbstractEMFTreeNode
	 * #doGetName()
	 */
	@Override
	protected String doGetName()
	{
		UtilityModeExpression idt = (UtilityModeExpression) getData();
		String res = idt.getId() + " € ";

		Task t = idt.getEntryPoint().getTask(); // TODO: EntryPoint vs Task
		if (t != null)
		{
			res = res.concat(t.getName());
		} else
		{
			res = res.concat("NO_TASK");
		}

		return res;
	}

	public String getTask()
	{
		String task = "NO_TASK";

		try
		{
			task = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>()
			{
				public void run()
				{
					EntryPoint entryPoint = ((UtilityModeExpression) getData()).getEntryPoint();
					if (entryPoint == null)
					{
						setResult(null);
						return;
					}
					
					Task t = entryPoint.getTask();
					if (t == null)
					{
						setResult(null);
						return;
					}
					
					setResult(t.getName());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		if (task == null)
		{
			task = "NO_TASK";
		}

		return task;
	}

	public Integer getTaskCount(List<EntryPoint> eps)
	{
		if(eps != null)
		{
			EntryPoint t = internalGetEntryPoint();
			
			return eps.indexOf(t);
		}
		return 0;
	}

	private EntryPoint internalGetEntryPoint()
	{
		EntryPoint t = null;
		try
		{
			t = (EntryPoint) getEditingDomain().runExclusive(new RunnableWithResult.Impl<EntryPoint>()
			{
				public void run()
				{
					EntryPoint t = ((UtilityModeExpression) getData()).getEntryPoint(); // TODO: EntryPoint vs Task
					if (t == null)
					{
						setResult(null);
						return;
					}
					setResult(t);
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		return t;
	}

	public void setEntryPoint(EntryPoint value)
	{
		Command uc = SetCommand.create(getEditingDomain(), getData(), AlicaPackage.eINSTANCE.getUtilityModeExpression_EntryPoint(), value);
		UtilityRepositoryUtils.execCommandAndSaveResource(uc, getCommandStack(), getEditingDomain());
	}

	public String getVariable()
	{
		String variable = "NO_TASK";

		try
		{
			variable = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>()
			{
				public void run()
				{
					String t = ((UtilityModeExpression) getData()).getVariable();
					setResult(t);
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		if (variable == null)
		{
			variable = "NO_TASK";
		}

		return variable;
	}

	public void setVariable(Object value)
	{
		if(value instanceof String)
		{
			Command uc = SetCommand.create(getEditingDomain(), getData(), AlicaPackage.eINSTANCE.getUtilityModeExpression_Variable(), value);
			UtilityRepositoryUtils.execCommandAndSaveResource(uc, getCommandStack(), getEditingDomain());
		}
	}
}
