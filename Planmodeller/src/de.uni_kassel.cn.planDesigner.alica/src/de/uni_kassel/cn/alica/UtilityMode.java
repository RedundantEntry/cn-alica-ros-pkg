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
package de.uni_kassel.cn.alica;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Utility Mode</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.UtilityMode#getMode <em>Mode</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.UtilityMode#getExpression <em>Expression</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.UtilityMode#getParent <em>Parent</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getUtilityMode()
 * @model
 * @generated
 */
public interface UtilityMode extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Mode</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * The literals are from the enumeration {@link de.uni_kassel.cn.alica.UtilityModes}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mode</em>' attribute.
	 * @see de.uni_kassel.cn.alica.UtilityModes
	 * @see #setMode(UtilityModes)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getUtilityMode_Mode()
	 * @model default="0"
	 * @generated
	 */
	UtilityModes getMode();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.UtilityMode#getMode <em>Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Mode</em>' attribute.
	 * @see de.uni_kassel.cn.alica.UtilityModes
	 * @see #getMode()
	 * @generated
	 */
	void setMode(UtilityModes value);

	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.UtilityModeExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getUtilityMode_Expression()
	 * @model containment="true"
	 * @generated
	 */
	EList<UtilityModeExpression> getExpression();

	/**
	 * Returns the value of the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parent</em>' reference.
	 * @see #setParent(Utility)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getUtilityMode_Parent()
	 * @model
	 * @generated
	 */
	Utility getParent();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.UtilityMode#getParent <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parent</em>' reference.
	 * @see #getParent()
	 * @generated
	 */
	void setParent(Utility value);

} // UtilityMode
