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
 * A representation of the model object '<em><b>Connection Point</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.ConnectionPoint#getMinCardinality <em>Min Cardinality</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.ConnectionPoint#getMaxCardinality <em>Max Cardinality</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.ConnectionPoint#getInTransition <em>In Transition</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.ConnectionPoint#getOutTransition <em>Out Transition</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getConnectionPoint()
 * @model abstract="true"
 * @generated
 */
public interface ConnectionPoint extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Min Cardinality</b></em>' attribute.
	 * The default value is <code>"1"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min Cardinality</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min Cardinality</em>' attribute.
	 * @see #setMinCardinality(int)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getConnectionPoint_MinCardinality()
	 * @model default="1"
	 * @generated
	 */
	int getMinCardinality();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.ConnectionPoint#getMinCardinality <em>Min Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min Cardinality</em>' attribute.
	 * @see #getMinCardinality()
	 * @generated
	 */
	void setMinCardinality(int value);

	/**
	 * Returns the value of the '<em><b>Max Cardinality</b></em>' attribute.
	 * The default value is <code>"2147483647"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max Cardinality</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max Cardinality</em>' attribute.
	 * @see #setMaxCardinality(int)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getConnectionPoint_MaxCardinality()
	 * @model default="2147483647"
	 * @generated
	 */
	int getMaxCardinality();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.ConnectionPoint#getMaxCardinality <em>Max Cardinality</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max Cardinality</em>' attribute.
	 * @see #getMaxCardinality()
	 * @generated
	 */
	void setMaxCardinality(int value);

	/**
	 * Returns the value of the '<em><b>In Transition</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Transition}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Transition#getOutPoint <em>Out Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Transition</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Transition</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getConnectionPoint_InTransition()
	 * @see de.uni_kassel.cn.alica.Transition#getOutPoint
	 * @model opposite="outPoint"
	 * @generated
	 */
	EList<Transition> getInTransition();

	/**
	 * Returns the value of the '<em><b>Out Transition</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Transition}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Transition#getInPoint <em>In Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Transition</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Transition</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getConnectionPoint_OutTransition()
	 * @see de.uni_kassel.cn.alica.Transition#getInPoint
	 * @model opposite="inPoint"
	 * @generated
	 */
	EList<Transition> getOutTransition();

} // ConnectionPoint
