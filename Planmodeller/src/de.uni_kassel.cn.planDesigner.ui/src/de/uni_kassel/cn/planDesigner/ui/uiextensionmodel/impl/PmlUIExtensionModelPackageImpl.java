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
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtensionMap;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PmlUIExtensionModelPackageImpl extends EPackageImpl implements PmlUIExtensionModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pmlUiExtensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass pmlUiExtensionMapEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass eObjectToPmlUiExtensionMapEntryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bendpointEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private PmlUIExtensionModelPackageImpl() {
		super(eNS_URI, PmlUIExtensionModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this
	 * model, and for any others upon which it depends.  Simple
	 * dependencies are satisfied by calling this method on all
	 * dependent packages before doing anything else.  This method drives
	 * initialization for interdependent packages directly, in parallel
	 * with this package, itself.
	 * <p>Of this package and its interdependencies, all packages which
	 * have not yet been registered by their URI values are first created
	 * and registered.  The packages are then initialized in two steps:
	 * meta-model objects for all of the packages are created before any
	 * are initialized, since one package's meta-model objects may refer to
	 * those of another.
	 * <p>Invocation of this method will not affect any packages that have
	 * already been initialized.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static PmlUIExtensionModelPackage init() {
		if (isInited) return (PmlUIExtensionModelPackage)EPackage.Registry.INSTANCE.getEPackage(PmlUIExtensionModelPackage.eNS_URI);

		// Obtain or create and register package
		PmlUIExtensionModelPackageImpl thePmlUIExtensionModelPackage = (PmlUIExtensionModelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof PmlUIExtensionModelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new PmlUIExtensionModelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		thePmlUIExtensionModelPackage.createPackageContents();

		// Initialize created meta-data
		thePmlUIExtensionModelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		thePmlUIExtensionModelPackage.freeze();

		return thePmlUIExtensionModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPmlUiExtension() {
		return pmlUiExtensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPmlUiExtension_XPos() {
		return (EAttribute)pmlUiExtensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPmlUiExtension_YPos() {
		return (EAttribute)pmlUiExtensionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPmlUiExtension_Width() {
		return (EAttribute)pmlUiExtensionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPmlUiExtension_Height() {
		return (EAttribute)pmlUiExtensionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPmlUiExtension_Collapsed() {
		return (EAttribute)pmlUiExtensionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPmlUiExtension_Bendpoints() {
		return (EReference)pmlUiExtensionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPmlUiExtension_Visible()
	{
		return (EAttribute)pmlUiExtensionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPmlUiExtensionMap() {
		return pmlUiExtensionMapEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPmlUiExtensionMap_Extensions() {
		return (EReference)pmlUiExtensionMapEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEObjectToPmlUiExtensionMapEntry() {
		return eObjectToPmlUiExtensionMapEntryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToPmlUiExtensionMapEntry_Value() {
		return (EReference)eObjectToPmlUiExtensionMapEntryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBendpoint() {
		return bendpointEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBendpoint_XPos() {
		return (EAttribute)bendpointEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBendpoint_YPos() {
		return (EAttribute)bendpointEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEObjectToPmlUiExtensionMapEntry_Key() {
		return (EReference)eObjectToPmlUiExtensionMapEntryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PmlUIExtensionModelFactory getPmlUIExtensionModelFactory() {
		return (PmlUIExtensionModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		pmlUiExtensionEClass = createEClass(PML_UI_EXTENSION);
		createEAttribute(pmlUiExtensionEClass, PML_UI_EXTENSION__XPOS);
		createEAttribute(pmlUiExtensionEClass, PML_UI_EXTENSION__YPOS);
		createEAttribute(pmlUiExtensionEClass, PML_UI_EXTENSION__WIDTH);
		createEAttribute(pmlUiExtensionEClass, PML_UI_EXTENSION__HEIGHT);
		createEAttribute(pmlUiExtensionEClass, PML_UI_EXTENSION__COLLAPSED);
		createEReference(pmlUiExtensionEClass, PML_UI_EXTENSION__BENDPOINTS);
		createEAttribute(pmlUiExtensionEClass, PML_UI_EXTENSION__VISIBLE);

		pmlUiExtensionMapEClass = createEClass(PML_UI_EXTENSION_MAP);
		createEReference(pmlUiExtensionMapEClass, PML_UI_EXTENSION_MAP__EXTENSIONS);

		eObjectToPmlUiExtensionMapEntryEClass = createEClass(EOBJECT_TO_PML_UI_EXTENSION_MAP_ENTRY);
		createEReference(eObjectToPmlUiExtensionMapEntryEClass, EOBJECT_TO_PML_UI_EXTENSION_MAP_ENTRY__KEY);
		createEReference(eObjectToPmlUiExtensionMapEntryEClass, EOBJECT_TO_PML_UI_EXTENSION_MAP_ENTRY__VALUE);

		bendpointEClass = createEClass(BENDPOINT);
		createEAttribute(bendpointEClass, BENDPOINT__XPOS);
		createEAttribute(bendpointEClass, BENDPOINT__YPOS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes and features; add operations and parameters
		initEClass(pmlUiExtensionEClass, PmlUiExtension.class, "PmlUiExtension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPmlUiExtension_XPos(), ecorePackage.getEInt(), "xPos", null, 0, 1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPmlUiExtension_YPos(), ecorePackage.getEInt(), "yPos", null, 0, 1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPmlUiExtension_Width(), ecorePackage.getEInt(), "width", "-1", 0, 1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPmlUiExtension_Height(), ecorePackage.getEInt(), "height", "-1", 0, 1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPmlUiExtension_Collapsed(), ecorePackage.getEBoolean(), "collapsed", "true", 0, 1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPmlUiExtension_Bendpoints(), this.getBendpoint(), null, "bendpoints", null, 0, -1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPmlUiExtension_Visible(), ecorePackage.getEBoolean(), "visible", "true", 0, 1, PmlUiExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pmlUiExtensionMapEClass, PmlUiExtensionMap.class, "PmlUiExtensionMap", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPmlUiExtensionMap_Extensions(), this.getEObjectToPmlUiExtensionMapEntry(), null, "extensions", null, 0, -1, PmlUiExtensionMap.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(eObjectToPmlUiExtensionMapEntryEClass, Map.Entry.class, "EObjectToPmlUiExtensionMapEntry", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEObjectToPmlUiExtensionMapEntry_Key(), ecorePackage.getEObject(), null, "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEObjectToPmlUiExtensionMapEntry_Value(), this.getPmlUiExtension(), null, "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bendpointEClass, Bendpoint.class, "Bendpoint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBendpoint_XPos(), ecorePackage.getEInt(), "xPos", null, 0, 1, Bendpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBendpoint_YPos(), ecorePackage.getEInt(), "yPos", null, 0, 1, Bendpoint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore
		createXSD2EcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createXSD2EcoreAnnotations() {
		String source = "http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] 
		   {
			 "representation", "schema",
			 "targetNamespace", "http:///de.uni_kassel.cn/alica/ui/extensionmodel"
		   });		
		addAnnotation
		  (getPmlUiExtensionMap_Extensions(), 
		   source, 
		   new String[] 
		   {
			 "name", "extension"
		   });
	}

} //PmlUIExtensionModelPackageImpl
