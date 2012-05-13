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
package de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes;

import java.util.List;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RunnableWithResult;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;

/**
 * @author till
 * 
 */
public class MPlanTreeNode extends AbstractEMFTreeNode
{

	public MPlanTreeNode(PMLTransactionalEditingDomain editingDomain, CommandStack stack, EditController editController, ITreeNode parent, EObject modelObject)
	{
		super(editingDomain, stack, editController, parent, modelObject);
	}

	@Override
	protected String doGetName()
	{
		return ((AbstractPlan) getModelObject()).getName();
	}

	@Override
	public boolean hasChildren()
	{
		boolean result = true;
		try
		{
			result = (Boolean) getEditingDomain().runExclusive(new RunnableWithResult.Impl<Boolean>()
			{
				public void run()
				{
					setResult(!((AbstractPlan) getModelObject()).getUtilities().isEmpty());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void createChildren(List<ITreeNode> children)
	{
		EList<UtilityReference> urs = null;
		try
		{
			urs = (EList<UtilityReference>)getEditingDomain().runExclusive(new RunnableWithResult.Impl<EList<UtilityReference>>()
			{
				public void run()
				{
					setResult(((AbstractPlan) getModelObject()).getUtilities());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		// Add the editController to each UtilityReference,
		// Utility, UtilityMode and Utility IDTasks
		// thus making it listen to model changes
		for (UtilityReference ur : urs)
		{
			getEditController().addToObject(ur);
			Utility u = ur.getUtility();
			getEditController().addToObject(u);

			for (UtilityMode um : u.getModes())
			{
				getEditController().addToObject(um);

				for (UtilityModeExpression idt : um.getExpression())
				{
					getEditController().addToObject(idt);
				}
			}	
			children.add(new MUtilityTreeNode(getEditingDomain(), getCommandStack(), getEditController(), this, ur));
		}
	}
}
