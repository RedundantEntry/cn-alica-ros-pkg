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
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.EntryPoint;
import de.uni_kassel.cn.alica.ExitPoint;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.State;
import de.uni_kassel.cn.alica.Transition;
import de.uni_kassel.cn.alica.impl.AlicaPackageImpl;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;

public class TestSimpleSerializationAndDeserialization extends TestCase {
	
	public static final String PLAN_URL = "simplePlan.pml";

	public void testSimpleCase(){
		// Initialize the pml package
		AlicaPackageImpl.init();
		
		// Register the XMI resource factory for the .pml extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml", new XMIResourceFactoryImpl());
		

		// Obtain a new resource set
		ResourceSet resSet = new AlicaResourceSet();

		// Create a resource for plan1.pml
		File planFile = new File(TestSimpleSerializationAndDeserialization.PLAN_URL);
		try {
			System.out.println("CreatedFileHandle: " +planFile.getCanonicalPath());
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Resource planResource = null;
		try {
			planResource = resSet.createResource(URI.createFileURI(planFile.getCanonicalPath()));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// Create a plan, two states and a transition between these two states. 
		Plan plan = AlicaFactory.eINSTANCE.createPlan();
		plan.setName("Plan 1");
		
		State state1 = AlicaFactory.eINSTANCE.createState();
		state1.setName("State 1");
		
		ExitPoint exitPoint = AlicaFactory.eINSTANCE.createSuccessPoint();
		exitPoint.setName("State 1 - Exit Point");
		
		state1.getExitPoints().add(exitPoint);
		
		State state2 = AlicaFactory.eINSTANCE.createState();
		state2.setName("State 2");
		
		EntryPoint entryPoint = AlicaFactory.eINSTANCE.createEntryPoint();
		entryPoint.setName("State 2 - Entry Point");
		
		state2.getEntryPoints().add(entryPoint);
		
		Transition trans = AlicaFactory.eINSTANCE.createSuccessTransition();
		trans.setName("Transition State 1 -> State 2");
		
		trans.setInPoint(exitPoint);
		trans.setOutPoint(entryPoint);
		
		// Add both states to the plan
		plan.getStates().add(state1);
		plan.getStates().add(state2);
		
		// We have to add the transition to the plan
		plan.getTransitions().add(trans);
		
		// Now add plan1 to plan1Resource
		planResource.getContents().add(plan);

		// Save both plans
		try {
			planResource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Reload the saved plan to a new resource
		Resource loadPlanResource = null;
		try {
			loadPlanResource = resSet.createResource(URI.createFileURI(planFile.getCanonicalPath()));
			loadPlanResource.load(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Plan loadedPlan = (Plan)loadPlanResource.getContents().get(0);
		
		assertTrue(loadedPlan != null);
		assertEquals(2, loadedPlan.getStates().size());
		assertEquals(1, loadedPlan.getTransitions().size());
		
		State lState1 = loadedPlan.getStates().get(0);
		
		assertEquals(1, lState1.getExitPoints().size());
		
		ExitPoint lexitP = lState1.getExitPoints().get(0);
		// Check bidirectionality
		assertEquals(lexitP.getConnectable(), lState1);
		
		State lState2 = loadedPlan.getStates().get(1);
		
		assertEquals(1, lState2.getEntryPoints().size());
		
		EntryPoint lentryP = lState2.getEntryPoints().get(0);
		// Check bidirectionality
		assertEquals(lentryP.getConnectable(), lState2);
		
		Transition ltrans = loadedPlan.getTransitions().get(0);
		assertEquals(lexitP, ltrans.getInPoint());
		assertEquals(lentryP, ltrans.getOutPoint());
		
		assertEquals(lexitP.getOutTransition(), ltrans);
		assertEquals(1, lentryP.getInTransition().size());
		assertEquals(lentryP.getInTransition().get(0), ltrans);
		
		
	}
}
