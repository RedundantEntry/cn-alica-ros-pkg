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
package de.uni_kassel.cn.planDesigner.utility;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

import de.uni_kassel.cn.planDesigner.ui.parts.AbstractPlanEditPart;
import de.uni_kassel.cn.planDesigner.utility.ui.UtilityView;

public class PlanEditorSelectionListener implements ISelectionListener 
{

	private static PlanEditorSelectionListener instance;

	public static PlanEditorSelectionListener getInstance()
	{
		if(instance == null)
		{
			instance = new PlanEditorSelectionListener();
		}
		
		return instance;
	}
	
	private PlanEditorSelectionListener() 
	{
		
	}
	
	private AbstractPlanEditPart selectedPart = null;
	
	public AbstractPlanEditPart getSelectedPart()
	{
		return this.selectedPart;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) 
	{
		// don't bother if the selection change comes from the utility view
		if(part instanceof UtilityView)
		{
			return;
		}
		
		// reset selection
		this.selectedPart = null; 

		// set new selection
		UtilityView utilityView = getUtilityView();
		
		if(utilityView == null)
		{
			return;
		}
		
		if(selection instanceof StructuredSelection)
		{
			Object elem = ((StructuredSelection)selection).getFirstElement();

			// set selection to newly selected PlanEditPart
			if(elem instanceof AbstractPlanEditPart)
			{
				this.selectedPart = (AbstractPlanEditPart)elem;
				utilityView.setInput(this.selectedPart.getModel());
				
				return;
			}
			else {
				utilityView.setInput(null);
			}
		}
	}

	public UtilityView getUtilityView()
	{
		return UtilityActivator.getDefault().getUtilityView();
	}
}
