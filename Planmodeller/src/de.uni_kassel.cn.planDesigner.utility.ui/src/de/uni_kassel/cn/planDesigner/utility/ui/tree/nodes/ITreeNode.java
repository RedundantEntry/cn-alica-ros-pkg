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

import org.eclipse.swt.graphics.Image;

/**
 * An interface for a tree node for two columns
 * 
 * @author till
 *
 */
public interface ITreeNode {
	
	/**
	 * 
	 * @return
	 *  the name string for this node
	 */
	public String getName();
	
	/**
	 * 
	 * @return
	 *  the image for this node
	 */
	public Image getImage();
	
	/**
	 * 
	 * @return
	 *  a list of all children nodes
	 */
	public List<ITreeNode> getChildren();

	/**
	 * 
	 * @return
	 * 	true if this node has children nodes
	 */
	public boolean hasChildren();

	/**
	 * 
	 * @return
	 * 	this nodes parent node
	 */
	public ITreeNode getParent();
	
	/**
	 * the root node of the tree
	 * 
	 * @return
	 */
	public ITreeNode getRoot();
	
	/**
	 * 
	 * @return
	 *  this nodes data object
	 */
	public Object getData();

	/**
	 * 
	 */
	public void setData(Object data);
}
