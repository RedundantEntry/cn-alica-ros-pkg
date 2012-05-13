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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.graphics.Image;


/**
 * @author till
 * 
 */
public abstract class AbstractTreeNode implements ITreeNode
{

	protected ITreeNode parent;

	protected ITreeNode root;

	protected List<ITreeNode> children;

	protected Object	data;

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
		return !getChildren().isEmpty();
	}

	public ITreeNode getParent()
	{
		return parent;
	}

	public ITreeNode getRoot()
	{
		return this.root;
	}

	public List<ITreeNode> getChildren(boolean recreate)
	{
		if(children == null)
		{
			children = new ArrayList<ITreeNode>();
		}
		
		if(recreate)
		{
			children.clear();
			createChildren(children);
		}

		return children;
	}	
	
	public List<ITreeNode> getChildren()
	{
		return getChildren(true);
	}

	/**
	 * create the children nodes
	 * 
	 * @param children
	 *            the list where to add the children too
	 */
	protected abstract void createChildren(List<ITreeNode> children);

	public Object getData()
	{
		return this.data;
	}
	
	public void setData(Object data)
	{
		this.data = data;
	}

	@Override
	public String getName()
	{
		return getData().toString();
	}

	@Override
	public Image getImage()
	{
		return null;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof AbstractTreeNode)
		{
			if(getData() == ((AbstractTreeNode)obj).getData())
			{
				return true;
			}
		}
		
		return super.equals(obj);
	}
}
