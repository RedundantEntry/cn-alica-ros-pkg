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
 * A representation of the model object '<em><b>Abstract Plan</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#getRating <em>Rating</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#getConditions <em>Conditions</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#isMasterPlan <em>Master Plan</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilityFunction <em>Utility Function</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilities <em>Utilities</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilityThreshold <em>Utility Threshold</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.AbstractPlan#getVars <em>Vars</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan()
 * @model abstract="true"
 * @generated
 */
public interface AbstractPlan extends PlanElement, IInhabitable {
	/**
	 * Returns the value of the '<em><b>Rating</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rating</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rating</em>' containment reference.
	 * @see #setRating(Rating)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_Rating()
	 * @model containment="true"
	 * @generated
	 */
	Rating getRating();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.AbstractPlan#getRating <em>Rating</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rating</em>' containment reference.
	 * @see #getRating()
	 * @generated
	 */
	void setRating(Rating value);

	/**
	 * Returns the value of the '<em><b>Conditions</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Condition}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Condition#getAbstractPlan <em>Abstract Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Conditions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Conditions</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_Conditions()
	 * @see de.uni_kassel.cn.alica.Condition#getAbstractPlan
	 * @model opposite="abstractPlan" containment="true"
	 * @generated
	 */
	EList<Condition> getConditions();

	/**
	 * Returns the value of the '<em><b>Master Plan</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Master Plan</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Master Plan</em>' attribute.
	 * @see #setMasterPlan(boolean)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_MasterPlan()
	 * @model default="false"
	 * @generated
	 */
	boolean isMasterPlan();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.AbstractPlan#isMasterPlan <em>Master Plan</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Master Plan</em>' attribute.
	 * @see #isMasterPlan()
	 * @generated
	 */
	void setMasterPlan(boolean value);

	/**
	 * Returns the value of the '<em><b>Utility Function</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utility Function</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utility Function</em>' attribute.
	 * @see #setUtilityFunction(String)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_UtilityFunction()
	 * @model default=""
	 * @generated
	 */
	String getUtilityFunction();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilityFunction <em>Utility Function</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Utility Function</em>' attribute.
	 * @see #getUtilityFunction()
	 * @generated
	 */
	void setUtilityFunction(String value);

	/**
	 * Returns the value of the '<em><b>Utilities</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.UtilityReference}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utilities</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utilities</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_Utilities()
	 * @model containment="true"
	 * @generated
	 */
	EList<UtilityReference> getUtilities();

	/**
	 * Returns the value of the '<em><b>Utility Threshold</b></em>' attribute.
	 * The default value is <code>"1.0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Utility Threshold</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Utility Threshold</em>' attribute.
	 * @see #setUtilityThreshold(double)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_UtilityThreshold()
	 * @model default="1.0"
	 * @generated
	 */
	double getUtilityThreshold();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilityThreshold <em>Utility Threshold</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Utility Threshold</em>' attribute.
	 * @see #getUtilityThreshold()
	 * @generated
	 */
	void setUtilityThreshold(double value);

	/**
	 * Returns the value of the '<em><b>Vars</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vars</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vars</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getAbstractPlan_Vars()
	 * @model containment="true"
	 * @generated
	 */
	EList<Variable> getVars();

} // AbstractPlan
