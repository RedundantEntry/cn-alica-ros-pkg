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

import org.eclipse.emf.common.util.EMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Behaviour Configuration</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getDeferring <em>Deferring</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getBehaviour <em>Behaviour</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.BehaviourConfiguration#isEventDriven <em>Event Driven</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getBehaviourConfiguration()
 * @model
 * @generated
 */
public interface BehaviourConfiguration extends AbstractPlan {
	/**
	 * Returns the value of the '<em><b>Parameters</b></em>' map.
	 * The key is of type {@link java.lang.String},
	 * and the value is of type {@link java.lang.String},
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameters</em>' map isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameters</em>' map.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getBehaviourConfiguration_Parameters()
	 * @model mapType="de.uni_kassel.cn.alica.EStringToEStringMapEntry<org.eclipse.emf.ecore.EString, org.eclipse.emf.ecore.EString>"
	 * @generated
	 */
	EMap<String, String> getParameters();

	/**
	 * Returns the value of the '<em><b>Deferring</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deferring</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deferring</em>' attribute.
	 * @see #setDeferring(int)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getBehaviourConfiguration_Deferring()
	 * @model default="0"
	 * @generated
	 */
	int getDeferring();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getDeferring <em>Deferring</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deferring</em>' attribute.
	 * @see #getDeferring()
	 * @generated
	 */
	void setDeferring(int value);

	/**
	 * Returns the value of the '<em><b>Frequency</b></em>' attribute.
	 * The default value is <code>"30"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Frequency</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Frequency</em>' attribute.
	 * @see #setFrequency(int)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getBehaviourConfiguration_Frequency()
	 * @model default="30"
	 * @generated
	 */
	int getFrequency();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getFrequency <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Frequency</em>' attribute.
	 * @see #getFrequency()
	 * @generated
	 */
	void setFrequency(int value);

	/**
	 * Returns the value of the '<em><b>Behaviour</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Behaviour#getConfigurations <em>Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behaviour</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behaviour</em>' container reference.
	 * @see #setBehaviour(Behaviour)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getBehaviourConfiguration_Behaviour()
	 * @see de.uni_kassel.cn.alica.Behaviour#getConfigurations
	 * @model opposite="configurations" required="true" transient="false"
	 * @generated
	 */
	Behaviour getBehaviour();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getBehaviour <em>Behaviour</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Behaviour</em>' container reference.
	 * @see #getBehaviour()
	 * @generated
	 */
	void setBehaviour(Behaviour value);

	/**
	 * Returns the value of the '<em><b>Event Driven</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Driven</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Driven</em>' attribute.
	 * @see #setEventDriven(boolean)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getBehaviourConfiguration_EventDriven()
	 * @model default="false"
	 * @generated
	 */
	boolean isEventDriven();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#isEventDriven <em>Event Driven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Event Driven</em>' attribute.
	 * @see #isEventDriven()
	 * @generated
	 */
	void setEventDriven(boolean value);

} // BehaviourConfiguration
