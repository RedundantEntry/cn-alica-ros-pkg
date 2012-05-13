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
 * A representation of the model object '<em><b>Role Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.RoleSet#getUsableWithPlanID <em>Usable With Plan ID</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.RoleSet#isDefault <em>Default</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.RoleSet#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getRoleSet()
 * @model
 * @generated
 */
public interface RoleSet extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Usable With Plan ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usable With Plan ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usable With Plan ID</em>' attribute.
	 * @see #setUsableWithPlanID(long)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getRoleSet_UsableWithPlanID()
	 * @model required="true"
	 * @generated
	 */
	long getUsableWithPlanID();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.RoleSet#getUsableWithPlanID <em>Usable With Plan ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usable With Plan ID</em>' attribute.
	 * @see #getUsableWithPlanID()
	 * @generated
	 */
	void setUsableWithPlanID(long value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(boolean)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getRoleSet_Default()
	 * @model default="false"
	 * @generated
	 */
	boolean isDefault();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.RoleSet#isDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #isDefault()
	 * @generated
	 */
	void setDefault(boolean value);

	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.RoleTaskMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getRoleSet_Mappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<RoleTaskMapping> getMappings();

} // RoleSet
