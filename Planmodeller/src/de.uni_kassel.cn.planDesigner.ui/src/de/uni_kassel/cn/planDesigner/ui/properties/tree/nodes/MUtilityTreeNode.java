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

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;

import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;

/**
 * @author till
 * 
 */
public class MUtilityTreeNode extends AbstractNoChildrenEMFTreeNode
{
	public MUtilityTreeNode(PMLTransactionalEditingDomain editingDomain, CommandStack stack, EditController editController, ITreeNode parent, EObject modelObject)
	{
		super(editingDomain, stack, editController, parent, modelObject);
	}

	@Override
	protected String doGetName()
	{
		String result = "EMPTY";
		
		if(getModelObject() instanceof UtilityReference)
		{
			UtilityReference ur = (UtilityReference)getModelObject();
			
			result = String.valueOf(ur.getWeight());
			
			Utility u = ur.getUtility();
			if(u != null)
			{
				result = ur.getUtility().getName() + " : " + result;
			}
		}
		
		return result;
	}

}
