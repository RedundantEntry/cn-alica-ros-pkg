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
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.swt.graphics.Image;

import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;

/**
 * @author till
 * 
 */
public abstract class AbstractEMFTreeNode extends AbstractTreeNode implements IEMFTreeNode
{

	private PMLTransactionalEditingDomain editingDomain;

	private CommandStack commandStack;
	
	private EditController editController;

	private EObject modelObject;
	
	public AbstractEMFTreeNode(PMLTransactionalEditingDomain editingDomain, CommandStack stack, EditController editController, ITreeNode parent, EObject modelObject)
	{
		super(parent);
		this.editingDomain = editingDomain;
		this.commandStack = stack;
		this.modelObject = modelObject;
		this.editController = editController;
	}

	/**
	 * 
	 * @return the editing domain for this node
	 */
	public PMLTransactionalEditingDomain getEditingDomain()
	{
		return editingDomain;
	}

	/**
	 * 
	 * @return the command stack for this node
	 */
	public CommandStack getCommandStack()
	{
		return commandStack;
	}
	
	public EditController getEditController()
	{
		return this.editController;
	}

	public Image getImage()
	{
		return null;
	}

	public EObject getModelObject()
	{
		return this.modelObject;
	}

	/**
	 * This implementation creates a runnable to obtain the name from the emf
	 * meta model. The run method calls doGetName(). <br>
	 * <br>
	 * Implementors should override this method if a simple return is enough
	 * 
	 * @see AbstractEMFTreeNode#doGetName()
	 * 
	 * @return the value string for this node
	 */
	public String getName()
	{
		String result = "EMPTY";

		try
		{
			result = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>()
			{
				public void run()
				{
					setResult(doGetName());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		return result;
	}

	abstract protected String doGetName();
	
}
