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
package de.uni_kassel.cn.planDesigner.ui.properties;

import java.util.Collections;

import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Condition;
import de.uni_kassel.cn.alica.EntryPoint;
import de.uni_kassel.cn.alica.ForallAgents;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.PreCondition;
import de.uni_kassel.cn.alica.Quantifier;
import de.uni_kassel.cn.alica.Transition;
import de.uni_kassel.cn.alica.Variable;
import de.uni_kassel.cn.planDesigner.ui.wizards.PMLModifyConditionQuantifiersWizard;
import de.uni_kassel.cn.planDesigner.ui.wizards.PMLModifyConditionVariablesWizard;

public class PreConditionSection extends PMLPropertySection {

	private Button enabledButton;	
	private Text nameText;
	private Text conditionText;
	private Label nameLabel;
	private Label conditionLabel;
	private Label varLabel;
	private Text varText;
	private Button modifyVarsButton;
	private Label quantLabel;
	private Text quantText;
	private Button modifyQuantsButton;
	private Shell shell;
	@Override
	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		this.shell = parent.getShell();
		Group group = getWidgetFactory().createGroup(parent, getGroupLabel());
		group.setLayout(new FillLayout());
		Composite form = getWidgetFactory().createFlatFormComposite(group);
		
		enabledButton = getWidgetFactory().createButton(form, "Enabled",
				SWT.CHECK);
		

		nameLabel = getWidgetFactory().createLabel(form, "Name:");
		nameText = getWidgetFactory().createText(form, "",
				SWT.BORDER | SWT.SINGLE);

		varLabel = getWidgetFactory().createLabel(form,"Vars: ");
		varText = getWidgetFactory().createText(form, "", SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		modifyVarsButton = getWidgetFactory().createButton(form, "Modify", SWT.DEFAULT);
		
		quantLabel = getWidgetFactory().createLabel(form,"Quantifiers: ");
		quantText = getWidgetFactory().createText(form, "", SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		modifyQuantsButton = getWidgetFactory().createButton(form, "Modify", SWT.DEFAULT);
		
		
		conditionLabel = getWidgetFactory().createLabel(form, "Condition:");
		conditionText = getWidgetFactory().createText(form, "",
				SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);

		// Do the layout
		FormData data = null;

		data = new FormData();
		data.top = new FormAttachment(0, 0);
		data.left = new FormAttachment(0, 0);
		enabledButton.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(enabledButton, 0);
		data.left = new FormAttachment(0, 0);
		nameLabel.setLayoutData(data);

		data = new FormData();
		data.top = new FormAttachment(enabledButton, 0);
		data.left = new FormAttachment(enabledButton, 0);
		data.right = new FormAttachment(50, 0);
		nameText.setLayoutData(data);


		data = new FormData();
		data.top = new FormAttachment(nameText);
		data.left = new FormAttachment(0, 0);
		varLabel.setLayoutData(data);

		data = new FormData(SWT.DEFAULT, 50);
		data.top = new FormAttachment(nameText);
		data.left = new FormAttachment(enabledButton,0);
		data.right = new FormAttachment(50, 0);
		varText.setLayoutData(data);
		
		data = new FormData();
		data.top = new FormAttachment(varLabel,0);
		data.left = new FormAttachment(0,0);
		modifyVarsButton.setLayoutData(data);
		
		//-----
		data = new FormData();
		data.top = new FormAttachment(varText);
		data.left = new FormAttachment(0, 0);
		quantLabel.setLayoutData(data);

		data = new FormData(SWT.DEFAULT, 50);
		data.top = new FormAttachment(varText);
		data.left = new FormAttachment(enabledButton,0);
		data.right = new FormAttachment(50, 0);
		quantText.setLayoutData(data);
		
		data = new FormData();
		data.top = new FormAttachment(quantLabel,0);
		data.left = new FormAttachment(0,0);
		modifyQuantsButton.setLayoutData(data);
		//----
		
		data = new FormData();
		data.top = new FormAttachment(quantText, 0);
		data.left = new FormAttachment(0, 0);
		conditionLabel.setLayoutData(data);

		
		data = new FormData(SWT.DEFAULT, 100);
		data.top = new FormAttachment(quantText, 0);
		data.left = new FormAttachment(enabledButton, 0);
		data.right = new FormAttachment(50, 0);
		conditionText.setLayoutData(data);
		
		

		registerListeners();
	}

	@Override
	protected void basicSetInput(Object input) {
		super.basicSetInput(input);

		// Reset the UI
		getEditController().pauseListening();
		resetUI();
		getEditController().resumeListening();
		
	}
	
	@Override
	protected void addAllAdapters() {
		super.addAllAdapters();
		
		// Add an adapter to the condition if possible
		if(getCondition() != null)
			getEditController().addToObject(getCondition());
	}

	private void registerListeners() {
		getEnabledButton().addListener(SWT.Selection, getEditController());
		getNameText().addListener(SWT.KeyDown, getEditController());
		getNameText().addListener(SWT.FocusOut, getEditController());
		getConditionText().addListener(SWT.FocusOut, getEditController());
		getVariableButton().addListener(SWT.Selection, getEditController());
		getQuantButton().addListener(SWT.Selection, getEditController());
	}
	

	protected Button getEnabledButton() {
		return enabledButton;
	}

	protected Button getVariableButton() {
		return modifyVarsButton;
	}
	protected Button getQuantButton() {
		return modifyQuantsButton;
	}
	protected Text getNameText() {
		return nameText;
	}

	protected Text getConditionText() {
		return conditionText;
	}
	protected Text getVariableText() {
		return varText;
	}
	protected Text getQuantText() {
		return quantText;
	}
	protected Label getNameLabel() {
		return nameLabel;
	}

	protected Label getConditionLabel() {
		return conditionLabel;
	}
	protected Label getVariableLabel() {
		return varLabel;
	}

	/**
	 * Enabled or disables all visuals depending on the editable state
	 * 
	 * @param enabled
	 */
	protected void refreshControls() {
		boolean editable = isEditable();
		
		getEnabledButton().setEnabled(editable);		
		
		Condition condition = getCondition();
		boolean enabledRadioSelected = (condition != null && getEnabledButton().getSelection());		
		getEnabledButton().setSelection(enabledRadioSelected);		
		
		getNameLabel().setEnabled(editable && enabledRadioSelected);
		getNameText().setEnabled(editable && enabledRadioSelected);
		getConditionLabel().setEnabled(editable && enabledRadioSelected);
		getConditionText().setEnabled(editable && enabledRadioSelected);
		
		getVariableButton().setEnabled(editable && enabledRadioSelected);
		getVariableLabel().setEnabled(editable && enabledRadioSelected);
		getVariableText().setEnabled(false);
		getQuantButton().setEnabled(editable && enabledRadioSelected);
		
		getQuantText().setEnabled(false);

	}
	
	@Override
	protected void updateView(Notification n) {
		final PreCondition condition = getCondition();		
		if(condition != null){			
			try {
				getEditingDomain().runExclusive(new Runnable(){
					public void run() {
						getEnabledButton().setSelection(condition.isEnabled());
						getNameText().setText(condition.getName());
						getConditionText().setText(condition.getConditionString());
						EList<Variable> vars = condition.getVars();
						StringBuffer buf = new StringBuffer();
						for(Variable v : vars) {
							buf.append(v.getName());
							buf.append(", ");
						}
						getVariableText().setText(buf.substring(0,Math.max(buf.length()-2,0)));
						
						buf= new StringBuffer();
						for(Quantifier q : condition.getQuantifiers()) {
							if(q instanceof ForallAgents) {
							  String sname =  q.getScope().getName();
							  if (q.getScope() instanceof EntryPoint) {
								  sname = ((EntryPoint)q.getScope()).getTask().getName();
							  }
							  buf.append("Forall Agents in "+sname+" let v be (");
							  boolean first = true;
							  for(String s : q.getSorts()) {
								  if(first) {
									  buf.append(s);
									 first = false;
								  }
								  else buf.append(", "+s);
							  }
							  buf.append(")\n");							  
							} else {
								buf.append("Unknown Quantifier!\n");
							}							
						}
						getQuantText().setText(buf.toString());
						
					}
				});
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		refreshControls();
	}
	
	protected void resetUI(){
		getNameText().setText("");
		getConditionText().setText("");
		getVariableText().setText("");
		getQuantText().setText("");
	}
	
	@Override
	public void refresh() {
		getEditController().updateView(null);
	}
	
	@Override
	protected void selectionEvent(Object source) {
		
		if(source.equals(getEnabledButton())) {
			
			boolean enabled = getEnabledButton().getSelection();
			
			//enableVisuals(enabled);
			PreCondition condition = getCondition();
			CompoundCommand cmd = new CompoundCommand(0);
			if(condition==null) {
				condition = (PreCondition) AlicaFactory.eINSTANCE.create(AlicaPackage.eINSTANCE.getPreCondition());
				cmd.append(CreateChildCommand.create(
					getEditingDomain(), 
					getModel(), 
					new CommandParameter(null,null,condition), 
					Collections.EMPTY_LIST));
			
				// Add the model to the editController
				getEditController().addToObject(condition);
			}
			
			cmd.append(SetCommand.create(
					getEditingDomain(), 
					condition, 
					AlicaPackage.eINSTANCE.getPreCondition_Enabled(), 
					enabled));

			
			executeCommand(cmd);
			refreshControls();
		} else if (source.equals(getVariableButton())) {
			PMLModifyConditionVariablesWizard wiz = new PMLModifyConditionVariablesWizard(getCondition(),getPlan());

			
			WizardDialog dialog = new WizardDialog(shell,wiz);
					//getViewer().getControl().getShell(), wiz);
			
			dialog.setBlockOnOpen(true);
			dialog.open();
		} else if (source.equals(getQuantButton())) {
			PMLModifyConditionQuantifiersWizard wiz = new PMLModifyConditionQuantifiersWizard(getCondition(), getPlan());
			WizardDialog dialog = new WizardDialog(shell,wiz);	
	
			dialog.setBlockOnOpen(true);
			dialog.open();
			
		}
	}
	
	@Override
	protected void focusOutEvent(Widget source) {
		applyValueToModel(source);
	}
	
	@Override
	protected void enterPressed(Widget source) {
		// Save caret position
		int pos = ((Text)source).getCaretPosition();
		applyValueToModel(source);
		// Apply the caret position
		((Text)source).setSelection(pos);
	}
	
	private void applyValueToModel(Widget source){
		if(source.equals(getNameText())){
			if (getCondition() != null)
				executeCommand(SetCommand.create(
						getEditingDomain(), 
						getCondition(), 
						AlicaPackage.eINSTANCE.getPlanElement_Name(), 
						getNameText().getText()));
		}else if(source.equals(getConditionText())){
			if (getCondition() != null)
				executeCommand(SetCommand.create(
						getEditingDomain(), 
						getCondition(), 
						AlicaPackage.eINSTANCE.getCondition_ConditionString(), 
						getConditionText().getText()));
		}
	}
	
	protected String getGroupLabel() {
		return "Condition";
	}
	

	
	protected PreCondition getCondition() {
		// Because this PropertySection is intended to be display while
		// a Transition is selected, we know which reference holds the condition
		return ((Transition)getModel()).getPreCondition();
	}
	
	protected Plan getPlan() {
		return (Plan)getModel().eContainer();
	}
		
	protected EClass getConditionType() {
		// Return the PreCondition MetaClass
		return AlicaPackage.eINSTANCE.getPreCondition();
	}
}
