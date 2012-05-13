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

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.State;
import de.uni_kassel.cn.alica.impl.AlicaPackageImpl;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;

public class TestCrossDocumentLoading extends TestCase {
	
	public static final String PLAN_1_URL = "Plan1.pml";
	public static final String PLAN_2_URL = "Plan2.pml";

	public void testSimpleCase(){
		// Initialize the pml package
		AlicaPackageImpl.init();
		
		// Register the XMI resource factory for the .pml extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new ResourceSetImpl();

		// Create a resource for plan1.pml
		File plan1File = new File(TestCrossDocumentLoading.PLAN_1_URL);
		File plan2File = new File(TestCrossDocumentLoading.PLAN_2_URL);
		try {
			plan1File.createNewFile();
			plan2File.createNewFile();
			plan1File.deleteOnExit();
			plan2File.deleteOnExit();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		try {
			System.out.println("CreatedFileHandle: " +plan1File.getCanonicalPath());
			System.out.println("CreatedFileHandle: " +plan2File.getCanonicalPath());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Resource plan1Resource = null;
		Resource plan2Resource = null;
		try {
			plan1Resource = resSet.createResource(URI.createFileURI(plan1File.getCanonicalPath()));
			plan2Resource = resSet.createResource(URI.createFileURI(plan2File.getCanonicalPath()));

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Create a plan with one state which contains plan2
		Plan plan = AlicaFactory.eINSTANCE.createPlan();
		plan.setName("Plan 1");
		
		State state1 = AlicaFactory.eINSTANCE.createState();
		state1.setName("State 1");
		
		// Add both states to the plan
		plan.getStates().add(state1);
		
		// Now add plan1 to plan1Resource
		plan1Resource.getContents().add(plan);
		
		// Create plan2
		Plan plan2 = AlicaFactory.eINSTANCE.createPlan();
		plan2.setName("Plan 2");
		
		// Add plan2 to state1
		state1.getPlans().add(plan2);
		
		// Now add plan1 to plan1Resource
		plan2Resource.getContents().add(plan2);

		// Save both plans
		try {
			plan1Resource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
			plan2Resource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Obtain a new resource set
		ResourceSet resSet2 = new AlicaResourceSet();
		
		// Reload the saved plan to a new resource
		Resource loadPlan1Resource = null;
		try {
			loadPlan1Resource = resSet2.getResource(URI.createFileURI(plan1File.getCanonicalPath()),true);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Plan loadedPlan1 = (Plan)loadPlan1Resource.getContents().get(0);
		
		// Check if the plan was loaded
		assertTrue(loadedPlan1 != null);
		
		assertEquals(1, loadedPlan1.getStates().size());
		
		State lState1 = loadedPlan1.getStates().get(0);
//		System.out.println("State1 proxy: " +lState1.eIsProxy());
			
		
		assertEquals(1, lState1.getPlans().size());
		
		Plan resolvedPlan2 = (Plan)lState1.getPlans().get(0);
		
	}
}
