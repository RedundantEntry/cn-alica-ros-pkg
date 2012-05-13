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

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.Node#getInEdge <em>In Edge</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.Node#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getNode()
 * @model
 * @generated
 */
public interface Node extends EObject {
	/**
	 * Returns the value of the '<em><b>In Edge</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Edge}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Edge#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Edge</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Edge</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getNode_InEdge()
	 * @see de.uni_kassel.cn.alica.Edge#getTo
	 * @model opposite="to"
	 * @generated
	 */
	EList<Edge> getInEdge();

	/**
	 * Returns the value of the '<em><b>Out Edge</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Edge}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Edge#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Edge</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Edge</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getNode_OutEdge()
	 * @see de.uni_kassel.cn.alica.Edge#getFrom
	 * @model opposite="from"
	 * @generated
	 */
	EList<Edge> getOutEdge();

} // Node
