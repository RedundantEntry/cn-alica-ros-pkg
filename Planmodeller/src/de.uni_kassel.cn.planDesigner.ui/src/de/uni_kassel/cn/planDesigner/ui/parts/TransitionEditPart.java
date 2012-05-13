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
package de.uni_kassel.cn.planDesigner.ui.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.draw2d.BendpointConnectionRouter;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.tools.DirectEditManager;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.graphics.Color;

import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.State;
import de.uni_kassel.cn.alica.Transition;
import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.dummyModelObjects.SynchedTransitionDummyConnection;
import de.uni_kassel.cn.planDesigner.ui.layout.MidpointConnectionAnchor;
import de.uni_kassel.cn.planDesigner.ui.policies.PMLBendPointEditPolicy;
import de.uni_kassel.cn.planDesigner.ui.policies.PMLConnectionEditPolicy;
import de.uni_kassel.cn.planDesigner.ui.policies.PMLDirectEditPolicy;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.cn.planDesigner.ui.util.CommonUtils;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.util.PMLCellEditorLocator;
import de.uni_kassel.cn.planDesigner.ui.util.PMLDirectEditManager;

public class TransitionEditPart extends AbstractTransitionEditPart implements NodeEditPart {

	/**
	 * The DirectEditManager is responsible for handling the in place editing of
	 * property values.
	 */
	private DirectEditManager directEditManager;

	@Override
	protected void addAllAdapters() {
		super.addAllAdapters();

		// Register the edirPart with the UI extension
		PmlUiExtension extension = CommonUtils.getUIAwareEditorAdapter(this).getUIExtension(getPlanElementModel(), true);
		if (extension != null)
			this.adapter.addToObject(extension);

		// Add an adapter to each bendpoint of the ui extension
		for (Bendpoint bp : extension.getBendpoints())
			this.adapter.addToObject(bp);
	}

	@Override
	protected void configureConnection(PolylineConnection con) {
		con.add(getNameLabel());
		con.add(getNameLabel());

		con.setConnectionRouter(new BendpointConnectionRouter());
		con.setRoutingConstraint(getRoutingConstraint());

		PlanDesignerActivator plugin = PlanDesignerActivator.getDefault();
		State state = getTransition().getOutState();
		Color color = null;
		if (state.eClass() == AlicaPackage.eINSTANCE.getFailureState())
			color = plugin.getColorRegistry().get(IEditorConstants.FAILURERANSITION_FOREGROUND_COLOR);
		else
			color = plugin.getColorRegistry().get(IEditorConstants.SUCCESSTRANSITION_FOREGROUND_COLOR);

		con.setForegroundColor(color);
	}

	@Override
	protected void createEditPolicies() {
		super.createEditPolicies();
		installEditPolicy(EditPolicy.DIRECT_EDIT_ROLE, new PMLDirectEditPolicy());
		installEditPolicy(EditPolicy.CONNECTION_BENDPOINTS_ROLE, new PMLBendPointEditPolicy());
		installEditPolicy(EditPolicy.GRAPHICAL_NODE_ROLE, new PMLConnectionEditPolicy());
	}

	protected DirectEditManager getDirectEditManager() {
		if (directEditManager == null) {
			directEditManager = new PMLDirectEditManager(this, TextCellEditor.class, new PMLCellEditorLocator(getNameLabel()));
		}

		return directEditManager;
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType() == RequestConstants.REQ_DIRECT_EDIT)
			performDirectEdit();
		else
			super.performRequest(req);

	}

	/**
	 * Invoke direct edit on the receiver
	 */
	public void performDirectEdit() {
		if (getNameLabel() != null) {
			getDirectEditManager().show();
		}
	}

	protected Transition getTransition() {
		return (Transition) getModel();
	}

	@Override
	protected List<SynchedTransitionDummyConnection> getModelTargetConnections() {
		List<SynchedTransitionDummyConnection> connections = null;
		if (getTransition().getSynchronisation() != null) {
			connections = new ArrayList<SynchedTransitionDummyConnection>();
			connections.add(new SynchedTransitionDummyConnection(getTransition().getSynchronisation(), getTransition()));
		} else {
			connections = Collections.emptyList();
		}
		return connections;
	}

	public ConnectionAnchor getSourceConnectionAnchor(ConnectionEditPart connection) {
		return new MidpointConnectionAnchor(getFigure());
	}

	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new MidpointConnectionAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(ConnectionEditPart connection) {
		return new MidpointConnectionAnchor(getFigure());
	}

	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new MidpointConnectionAnchor(getFigure());
	}
}
