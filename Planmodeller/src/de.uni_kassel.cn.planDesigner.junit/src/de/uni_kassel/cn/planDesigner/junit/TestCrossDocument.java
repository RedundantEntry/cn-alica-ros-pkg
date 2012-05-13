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

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.State;
import de.uni_kassel.cn.alica.impl.AlicaPackageImpl;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;

public class TestCrossDocument extends TestCase {
	
	public static final String MASTERPLAN_URI = "../plans/StefanEngineTest/MasterPlanStefan.pml";

	public void testSimpleCase(){
		// Initialize the pml package
		AlicaPackageImpl.init();
		
		// Register the XMI resource factory for the .pml extension
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("pml", new XMIResourceFactoryImpl());

		// Obtain a new resource set
		ResourceSet resSet = new AlicaResourceSet();

		// Create a resource for plan1.pml
		File plan1File = new File(MASTERPLAN_URI);
		
		try {
			System.out.println("CreatedFileHandle: " +plan1File.getCanonicalPath());
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Resource planResource = null;
		try {
			planResource = resSet.getResource(URI.createFileURI(plan1File.getCanonicalPath()),true);
//			planResource.load(PmlSerializationHelper.getInstance().getLoadSaveOptions());

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Plan loadedPlan = (Plan)planResource.getContents().get(0);
		System.out.println("Name of loadedPlan: " +loadedPlan.getName());
		
		// Check if the plan was loaded
		assertTrue(loadedPlan != null);
		
		// Check that the plan has 2 states
		assertEquals(2, loadedPlan.getStates().size());
		
		State lState1 = loadedPlan.getStates().get(0);
		
		// Check that the state has 2 abstractplans
		assertEquals(3, lState1.getPlans().size());
		
		AbstractPlan resolvedPlan2 = (AbstractPlan)lState1.getPlans().get(0);
		
		System.out.println("Name of Plan1: " +resolvedPlan2.getName());
	}
}
