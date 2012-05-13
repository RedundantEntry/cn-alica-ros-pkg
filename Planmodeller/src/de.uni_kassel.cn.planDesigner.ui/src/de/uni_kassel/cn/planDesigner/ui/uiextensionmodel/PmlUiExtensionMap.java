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
package de.uni_kassel.cn.planDesigner.ui.uiextensionmodel;

import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pml Ui Extension Map</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtensionMap#getExtensions <em>Extensions</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage#getPmlUiExtensionMap()
 * @model
 * @generated
 */
public interface PmlUiExtensionMap extends EObject {
	/**
	 * Returns the value of the '<em><b>Extensions</b></em>' map.
	 * The key is of type {@link org.eclipse.emf.ecore.EObject},
	 * and the value is of type {@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Extensions</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Extensions</em>' map.
	 * @see de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage#getPmlUiExtensionMap_Extensions()
	 * @model mapType="de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.EObjectToPmlUiExtensionMapEntry<org.eclipse.emf.ecore.EObject, de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension>"
	 *        annotation="http:///org/eclipse/emf/mapping/xsd2ecore/XSD2Ecore name='extension'"
	 * @generated
	 */
	EMap<EObject, PmlUiExtension> getExtensions();

} // PmlUiExtensionMap
