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
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.State#getPlans <em>Plans</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.State#getInPlan <em>In Plan</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.State#getParametrisation <em>Parametrisation</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.State#getInTransitions <em>In Transitions</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.State#getOutTransitions <em>Out Transitions</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.State#getEntryPoint <em>Entry Point</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getState()
 * @model
 * @generated
 */
public interface State extends PlanElement, IInhabitable {
	/**
	 * Returns the value of the '<em><b>Plans</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.AbstractPlan}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Plans</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Plans</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getState_Plans()
	 * @model
	 * @generated
	 */
	EList<AbstractPlan> getPlans();

	/**
	 * Returns the value of the '<em><b>In Plan</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Plan#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Plan</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Plan</em>' container reference.
	 * @see #setInPlan(Plan)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getState_InPlan()
	 * @see de.uni_kassel.cn.alica.Plan#getStates
	 * @model opposite="states" transient="false"
	 * @generated
	 */
	Plan getInPlan();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.State#getInPlan <em>In Plan</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>In Plan</em>' container reference.
	 * @see #getInPlan()
	 * @generated
	 */
	void setInPlan(Plan value);

	/**
	 * Returns the value of the '<em><b>Parametrisation</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Parametrisation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parametrisation</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parametrisation</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getState_Parametrisation()
	 * @model containment="true"
	 * @generated
	 */
	EList<Parametrisation> getParametrisation();

	/**
	 * Returns the value of the '<em><b>In Transitions</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Transition}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Transition#getOutState <em>Out State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Transitions</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getState_InTransitions()
	 * @see de.uni_kassel.cn.alica.Transition#getOutState
	 * @model opposite="outState"
	 * @generated
	 */
	EList<Transition> getInTransitions();

	/**
	 * Returns the value of the '<em><b>Out Transitions</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Transition}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Transition#getInState <em>In State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Out Transitions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Out Transitions</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getState_OutTransitions()
	 * @see de.uni_kassel.cn.alica.Transition#getInState
	 * @model opposite="inState"
	 * @generated
	 */
	EList<Transition> getOutTransitions();

	/**
	 * Returns the value of the '<em><b>Entry Point</b></em>' reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.EntryPoint#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Entry Point</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Entry Point</em>' reference.
	 * @see #setEntryPoint(EntryPoint)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getState_EntryPoint()
	 * @see de.uni_kassel.cn.alica.EntryPoint#getState
	 * @model opposite="state"
	 * @generated
	 */
	EntryPoint getEntryPoint();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.State#getEntryPoint <em>Entry Point</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Entry Point</em>' reference.
	 * @see #getEntryPoint()
	 * @generated
	 */
	void setEntryPoint(EntryPoint value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void ensureParametrisationConsistency();

} // State
