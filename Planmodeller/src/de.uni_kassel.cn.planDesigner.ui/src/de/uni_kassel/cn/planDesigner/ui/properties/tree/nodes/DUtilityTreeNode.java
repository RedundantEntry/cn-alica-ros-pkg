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

import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;

/**
 * @author till
 *
 */
public class DUtilityTreeNode extends AbstractEMFTreeNode
{

	public DUtilityTreeNode(PMLTransactionalEditingDomain editingDomain, CommandStack stack, EditController editController, ITreeNode parent, EObject modelObject)
	{
		super(editingDomain, stack, editController, parent, modelObject);
	}

	/* (non-Javadoc)
	 * @see de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.AbstractEMFTreeNode#doGetName()
	 */
	@Override
	protected String doGetName()
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.AbstractTreeNode#createChildren(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void createChildren(List<ITreeNode> children)
	{
		EList<UtilityMode> modes = null;
		
		try
		{
			modes = (EList<UtilityMode>)getEditingDomain().runExclusive(new RunnableWithResult.Impl<EList<UtilityMode>>()
			{
				public void run()
				{
					setResult(((Utility) getModelObject()).getModes());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		for(UtilityMode m : modes)
		{
			children.add(new DUtilityModeTreeNode(getEditingDomain(), getCommandStack(), getEditController(), this, m));
		}
	}

}
