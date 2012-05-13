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
package de.uni_kassel.cn.planDesigner.utility.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.edit.command.CreateChildCommand;
import org.eclipse.emf.edit.command.SetCommand;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.UtilityRepository;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;
import de.uni_kassel.cn.planDesigner.utility.Constants;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.UtilityView;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityUtils;

/**
 * @author till
 * 
 */
public class AddUtilityHandler extends AbstractHandler
{
	private UtilityView utilityView;
	
	private PMLTransactionalEditingDomain domain;
	
	private EditController controller;
	
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		this.utilityView = UtilityActivator.getDefault().getUtilityView();
		this.domain = this.utilityView.getEditingDomain();
		this.controller = this.utilityView.getEditController();
		
		// command parameter
		String utilityid = event.getParameter(Constants.ADD_UTILITY_COMMAND_PARAMETER_ID);
		
		if (utilityid == null)
		{
			createNewUtility();
		} else
		{
			createNewUtilityReference(Long.parseLong(utilityid));
		}

		return null;
	}

	private void createNewUtilityReference(Long id)
	{
		// get repository and plan
		final UtilityRepository repo = UtilityRepositoryUtils.getRepository(domain);
		AbstractPlan plan = (AbstractPlan) utilityView.getInput();

		// get utility from the repo
		EList<Utility> utils = UtilityUtils.getUtilities(this.domain, repo);
		Utility util = getUtility(utils, id);
		
		if(util == null)
		{
			return; // TODO: Message
		}

		// create new reference
		UtilityReference utilRef = AlicaFactory.eINSTANCE.createUtilityReference();

		// hook new reference to listener
		controller.addToObject(utilRef);

		// create commands
		CompoundCommand cc = new CompoundCommand(0);

		// utility -> utilityReference
		Command c1 = SetCommand.create(domain, utilRef, AlicaPackage.eINSTANCE.getUtilityReference_Utility(), util);
		// utilityReference -> plan
		Command c2 = new CreateChildCommand(domain, plan, AlicaPackage.eINSTANCE.getAbstractPlan_Utilities(), utilRef, null);

		cc.append(c1);
		cc.append(c2);

		// execute command
		UtilityRepositoryUtils.execCommandAndSaveResource(cc, utilityView.getCommandStack(), domain);
	}
	
	private Utility getUtility(EList<Utility> utils, Long id)
	{
		for(Utility u : utils)
		{
			if (UtilityUtils.getUtilityID(domain, u) == id.floatValue())
			{
				return u;
			}
		}
		
		return null;
	}

	private void createNewUtility()
	{
		// create new objects
		Utility util = AlicaFactory.eINSTANCE.createUtility();
		UtilityReference utilRef = AlicaFactory.eINSTANCE.createUtilityReference();
		UtilityMode utilMode = AlicaFactory.eINSTANCE.createUtilityMode();
		UtilityModeExpression utilIDts = AlicaFactory.eINSTANCE.createUtilityModeExpression();

		// get repository and plan
		UtilityRepository repo = UtilityRepositoryUtils.getRepository(domain);
		AbstractPlan plan = (AbstractPlan) utilityView.getInput();

		// hook new objects to listener
		controller.addToObject(util);
		controller.addToObject(utilRef);
		controller.addToObject(utilMode);
		controller.addToObject(utilIDts);

		// TODO: commands... set up new objects
		util.setName("New_utility");
		utilIDts.setVariable("x");

		// create commands
		CompoundCommand cc = new CompoundCommand(0);

		// utility -> utilityReference
		Command c1 = SetCommand.create(domain, utilRef, AlicaPackage.eINSTANCE.getUtilityReference_Utility(), util);
		// utilityReference -> plan
		Command c2 = new CreateChildCommand(domain, plan, AlicaPackage.eINSTANCE.getAbstractPlan_Utilities(), utilRef, null);
		// utility mode -> utility
		Command c3 = new CreateChildCommand(domain, util, AlicaPackage.eINSTANCE.getUtility_Modes(), utilMode, null);
		// id task -> utility mode
		Command c4 = new CreateChildCommand(domain, utilMode, AlicaPackage.eINSTANCE.getUtilityMode_Expression(), utilIDts, null);
		// utility -> repository
		Command c5 = new CreateChildCommand(domain, repo, AlicaPackage.eINSTANCE.getUtilityRepository_Utilities(), util, null);

		cc.append(c1);
		cc.append(c2);
		cc.append(c3);
		cc.append(c4);
		cc.append(c5);

		// execute command
		UtilityRepositoryUtils.execCommandAndSaveResource(cc, utilityView.getCommandStack(), domain);
	}
}
