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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Bendpoint</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint#getXPos <em>XPos</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint#getYPos <em>YPos</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage#getBendpoint()
 * @model
 * @generated
 */
public interface Bendpoint extends EObject {
	/**
	 * Returns the value of the '<em><b>XPos</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>XPos</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>XPos</em>' attribute.
	 * @see #setXPos(int)
	 * @see de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage#getBendpoint_XPos()
	 * @model
	 * @generated
	 */
	int getXPos();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint#getXPos <em>XPos</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>XPos</em>' attribute.
	 * @see #getXPos()
	 * @generated
	 */
	void setXPos(int value);

	/**
	 * Returns the value of the '<em><b>YPos</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>YPos</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>YPos</em>' attribute.
	 * @see #setYPos(int)
	 * @see de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage#getBendpoint_YPos()
	 * @model
	 * @generated
	 */
	int getYPos();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint#getYPos <em>YPos</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>YPos</em>' attribute.
	 * @see #getYPos()
	 * @generated
	 */
	void setYPos(int value);

} // Bendpoint
