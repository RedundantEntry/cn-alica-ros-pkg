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
 * A representation of the model object '<em><b>Utility Repository</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.UtilityRepository#getUtilities <em>Utilities</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getUtilityRepository()
 * @model
 * @generated
 */
public interface UtilityRepository extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Utilities</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Utility}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utilities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utilities</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getUtilityRepository_Utilities()
	 * @model containment="true"
	 * @generated
	 */
	EList<Utility> getUtilities();

} // UtilityRepository
