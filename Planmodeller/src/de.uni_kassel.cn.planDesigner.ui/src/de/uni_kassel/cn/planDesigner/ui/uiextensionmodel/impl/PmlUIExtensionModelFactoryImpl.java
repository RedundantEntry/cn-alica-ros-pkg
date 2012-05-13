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

import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.*;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelFactory;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtensionMap;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PmlUIExtensionModelFactoryImpl extends EFactoryImpl implements PmlUIExtensionModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static PmlUIExtensionModelFactory init() {
		try
		{
			PmlUIExtensionModelFactory thePmlUIExtensionModelFactory = (PmlUIExtensionModelFactory)EPackage.Registry.INSTANCE.getEFactory("http:///de.uni_kassel.cn/planDesigner/ui/extensionmodel"); 
			if (thePmlUIExtensionModelFactory != null)
			{
				return thePmlUIExtensionModelFactory;
			}
		}
		catch (Exception exception)
		{
			EcorePlugin.INSTANCE.log(exception);
		}
		return new PmlUIExtensionModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PmlUIExtensionModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID())
		{
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION: return createPmlUiExtension();
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION_MAP: return createPmlUiExtensionMap();
			case PmlUIExtensionModelPackage.EOBJECT_TO_PML_UI_EXTENSION_MAP_ENTRY: return (EObject)createEObjectToPmlUiExtensionMapEntry();
			case PmlUIExtensionModelPackage.BENDPOINT: return createBendpoint();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PmlUiExtension createPmlUiExtension() {
		PmlUiExtensionImpl pmlUiExtension = new PmlUiExtensionImpl();
		return pmlUiExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PmlUiExtensionMap createPmlUiExtensionMap() {
		PmlUiExtensionMapImpl pmlUiExtensionMap = new PmlUiExtensionMapImpl();
		return pmlUiExtensionMap;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<EObject, PmlUiExtension> createEObjectToPmlUiExtensionMapEntry() {
		EObjectToPmlUiExtensionMapEntryImpl eObjectToPmlUiExtensionMapEntry = new EObjectToPmlUiExtensionMapEntryImpl();
		return eObjectToPmlUiExtensionMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Bendpoint createBendpoint() {
		BendpointImpl bendpoint = new BendpointImpl();
		return bendpoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PmlUIExtensionModelPackage getPmlUIExtensionModelPackage() {
		return (PmlUIExtensionModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static PmlUIExtensionModelPackage getPackage() {
		return PmlUIExtensionModelPackage.eINSTANCE;
	}

} //PmlUIExtensionModelFactoryImpl
