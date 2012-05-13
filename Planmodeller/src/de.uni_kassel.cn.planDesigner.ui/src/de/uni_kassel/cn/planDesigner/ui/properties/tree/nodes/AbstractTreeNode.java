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

import java.util.ArrayList;
import java.util.List;


/**
 * @author till
 * 
 */
public abstract class AbstractTreeNode implements ITreeNode
{

	protected ITreeNode parent;

	protected ITreeNode root;

	protected List<ITreeNode> children;

	public AbstractTreeNode(ITreeNode parent)
	{
		this.parent = parent;

		if (this.root == null && getParent() != null)
		{
			this.root = getParent().getRoot();
		}
	}
	
	public boolean hasChildren()
	{
		return true;
	}

	public ITreeNode getParent()
	{
		return parent;
	}

	public ITreeNode getRoot()
	{
		return this.root;
	}

	public List<ITreeNode> getChildren()
	{
		children = new ArrayList<ITreeNode>();
		createChildren(children);

		return children;
	}

	/**
	 * create the children nodes
	 * 
	 * @param children
	 *            the list where to add the children too
	 */
	protected abstract void createChildren(List<ITreeNode> children);
}
