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
package de.uni_kassel.cn.planDesigner.junit;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.State;
import de.uni_kassel.cn.alica.impl.AlicaPackageImpl;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUIExtensionModelPackageImpl;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.util.PmlUIExtensionModelResourceFactoryImpl;

public class TestExtension {
	
	public static final String EXTENSION_URI = "plan.pmlex";
	public static final String PLAN_URI = "plan.pml";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Initialize the pmlext package
		PmlUIExtensionModelPackageImpl.init();
		
		// Register the XMI resource factory for the .pmlex extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pmlex", new PmlUIExtensionModelResourceFactoryImpl());;
		
		// Obtain a new resource set
		ResourceSet resSet = new AlicaResourceSet();

		// Create a resource
		Resource resource = resSet.createResource(URI.createFileURI(TestExtension.EXTENSION_URI));
		
		// Initialize the pml package
		AlicaPackageImpl.init();
		
		// Register the XMI resource factory for the .pml extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml", new XMIResourceFactoryImpl());;

		// Create a resource
		Resource resourceModel = resSet.createResource(URI.createFileURI(TestExtension.PLAN_URI));
		
		Plan plan = AlicaFactory.eINSTANCE.createPlan();
		plan.setName("Plan");
		System.out.println("ID of generated plan: " +plan.getId());
		
		State state = AlicaFactory.eINSTANCE.createState();
		state.setName("State");
		System.out.println("ID of generated state: " +state.getId());
		
		plan.getStates().add(state);
		
		// Create the map which will map an uiExtension object to
		// a model object
		PmlUiExtensionMap map = PmlUIExtensionModelFactory.eINSTANCE.createPmlUiExtensionMap();
		
		// Create an ui extension for the plan
		PmlUiExtension ext = PmlUIExtensionModelFactory.eINSTANCE.createPmlUiExtension();
		ext.setWidth(200);
		ext.setHeight(200);
		ext.setXPos(10);
		ext.setYPos(10);
		
		map.getExtensions().put(plan, ext);
		
		ext = PmlUIExtensionModelFactory.eINSTANCE.createPmlUiExtension();
		ext.setWidth(50);
		ext.setHeight(50);
		ext.setXPos(22);
		ext.setYPos(42);
		
		map.getExtensions().put(state,ext);
		
		resource.getContents().add(map);
		
		resourceModel.getContents().add(plan);
		
		// Serialize the extension map
		try {
			
			resourceModel.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
			resource.save(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finish");

	}

}
