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
package de.uni_kassel.cn.planDesigner.ui.adapter;

import org.eclipse.gef.EditPart;

import de.uni_kassel.cn.planDesigner.ui.util.RolesetEditorUtils;

public class TaskGraphSelectionAdapter implements SelectionAdapter {

	public TaskGraphSelectionAdapter() {
	}

	public Object getAdaptedSelection(EditPart editPartToAdapt) {
		return RolesetEditorUtils.getRolesetEditor(editPartToAdapt).getRoleSet();
	}

}
