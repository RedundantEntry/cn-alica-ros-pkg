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

import java.util.Arrays;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.SetCommand;

import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.UtilityModes;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.AbstractEMFTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.DUtilityModeExpressionTreeNode;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;

/**
 * @author till
 *
 */
public class DUtilityModeTreeNode extends AbstractEMFTreeNode
{

	public DUtilityModeTreeNode(PMLTransactionalEditingDomain editingDomain, CommandStack stack, EditController editController, ITreeNode parent, EObject modelObject)
	{
		super(editingDomain, stack, editController, parent, modelObject);
	}

	@Override
	protected String doGetName()
	{
		return ((UtilityMode)getData()).getMode().getLiteral();
	}

	@Override
	protected void createChildren(List<ITreeNode> children)
	{
		UtilityMode m = (UtilityMode)getData();
		for(UtilityModeExpression idt : m.getExpression())
		{
			children.add(new DUtilityModeExpressionTreeNode(getEditingDomain(), getCommandStack(), getEditController(), this, idt));
		}		
	}

	public Object getModeCount()
	{
		String mode = getName();
		
		List<UtilityModes> list = Arrays.asList(UtilityModes.values());
		
		return list.indexOf(mode);
	}

	public void setValue(Object value)
	{
		if(value instanceof Integer)
		{
			UtilityModes mode = UtilityModes.get((Integer)value);
			
			Command uc = SetCommand.create(getEditingDomain(), getData(), AlicaPackage.eINSTANCE.getUtilityMode_Mode(), mode);
			UtilityRepositoryUtils.execCommandAndSaveResource(uc, getCommandStack(), getEditingDomain());
		}
	}
}
