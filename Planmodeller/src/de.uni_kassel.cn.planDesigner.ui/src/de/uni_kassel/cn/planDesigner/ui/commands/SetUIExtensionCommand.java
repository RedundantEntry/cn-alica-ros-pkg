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
package de.uni_kassel.cn.planDesigner.ui.commands;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtensionMap;

public class SetUIExtensionCommand extends RecordingCommand {
	
	private EObject model;
	
	private Rectangle constraint;
	
	private PmlUiExtensionMap extensionMap;

	public SetUIExtensionCommand(EObject model, Rectangle constraint, TransactionalEditingDomain domain, PmlUiExtensionMap extensionMap) {
		super(domain, "Move/Resize");
		this.model = model;
		this.constraint = constraint;
		this.extensionMap = extensionMap;
	}

	@Override
	protected void doExecute() {
		// Get the UI Extension 
		PmlUiExtension extension = extensionMap.getExtensions().get(model);
		
		Point oldPos = new Point(extension.getXPos(),extension.getYPos());
		Dimension oldSize = new Dimension(extension.getWidth(), extension.getHeight());
		
		if(!oldSize.equals(constraint.getSize())){
			extension.setWidth(constraint.width);
			extension.setHeight(constraint.height);
		}
		if(!oldPos.equals(constraint.getLocation())){
			extension.setXPos(constraint.x);
			extension.setYPos(constraint.y);
		}
	}

}
