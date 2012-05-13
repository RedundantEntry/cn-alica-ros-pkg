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
 * A representation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.Condition#getConditionString <em>Condition String</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.Condition#getAbstractPlan <em>Abstract Plan</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.Condition#getVars <em>Vars</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.Condition#getQuantifiers <em>Quantifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getCondition()
 * @model abstract="true"
 * @generated
 */
public interface Condition extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Condition String</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Condition String</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Condition String</em>' attribute.
	 * @see #setConditionString(String)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getCondition_ConditionString()
	 * @model default=""
	 * @generated
	 */
	String getConditionString();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.Condition#getConditionString <em>Condition String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Condition String</em>' attribute.
	 * @see #getConditionString()
	 * @generated
	 */
	void setConditionString(String value);

	/**
	 * Returns the value of the '<em><b>Abstract Plan</b></em>' container reference.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.AbstractPlan#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Abstract Plan</em>' container reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Abstract Plan</em>' container reference.
	 * @see #setAbstractPlan(AbstractPlan)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getCondition_AbstractPlan()
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getConditions
	 * @model opposite="conditions" transient="false"
	 * @generated
	 */
	AbstractPlan getAbstractPlan();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.Condition#getAbstractPlan <em>Abstract Plan</em>}' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Abstract Plan</em>' container reference.
	 * @see #getAbstractPlan()
	 * @generated
	 */
	void setAbstractPlan(AbstractPlan value);

	/**
	 * Returns the value of the '<em><b>Vars</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Variable}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vars</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vars</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getCondition_Vars()
	 * @model
	 * @generated
	 */
	EList<Variable> getVars();

	/**
	 * Returns the value of the '<em><b>Quantifiers</b></em>' containment reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Quantifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Quantifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Quantifiers</em>' containment reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getCondition_Quantifiers()
	 * @model containment="true"
	 * @generated
	 */
	EList<Quantifier> getQuantifiers();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model
	 * @generated
	 */
	void ensureVariableConsistency(AbstractPlan parentPlan);

} // Condition
