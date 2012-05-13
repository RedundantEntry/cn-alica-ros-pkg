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
import de.uni_kassel.cn.alica.SyncTransition;
import de.uni_kassel.cn.alica.Transition;
import de.uni_kassel.cn.alica.impl.AlicaPackageImpl;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtensionMap;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUIExtensionModelPackageImpl;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.util.PmlUIExtensionModelResourceFactoryImpl;

public class TestSyncTransition {
	
	public static final String PLAN_URI = "planSyncTrans.pml";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Obtain a new resource set
		ResourceSet resSet = new AlicaResourceSet();

		// Initialize the pml package
		AlicaPackageImpl.init();
		
		// Register the XMI resource factory for the .pml extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml", new XMIResourceFactoryImpl());;

		// Create a resource
		Resource resourceModel = resSet.createResource(URI.createFileURI(TestSyncTransition.PLAN_URI));
		
		Plan plan = AlicaFactory.eINSTANCE.createPlan();
		plan.setName("Plan");
		
		Transition t1 = AlicaFactory.eINSTANCE.createSuccessTransition();
		Transition t2 = AlicaFactory.eINSTANCE.createSuccessTransition();
		
		SyncTransition sync = AlicaFactory.eINSTANCE.createSyncTransition();
		
		
		resourceModel.getContents().add(plan);
		
		// Serialize the extension map
		try {
			
			resourceModel.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Finish");

	}

}
