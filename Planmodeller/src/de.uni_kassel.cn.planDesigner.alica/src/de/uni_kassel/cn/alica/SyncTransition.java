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
 * A representation of the model object '<em><b>Sync Transition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.SyncTransition#getInSync <em>In Sync</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.SyncTransition#getTalkTimeout <em>Talk Timeout</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.SyncTransition#getSyncTimeout <em>Sync Timeout</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.SyncTransition#isFailOnSyncTimeOut <em>Fail On Sync Time Out</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getSyncTransition()
 * @model
 * @generated
 */
public interface SyncTransition extends PlanElement {
	/**
	 * Returns the value of the '<em><b>In Sync</b></em>' reference list.
	 * The list contents are of type {@link de.uni_kassel.cn.alica.Transition}.
	 * It is bidirectional and its opposite is '{@link de.uni_kassel.cn.alica.Transition#getSyncTransitions <em>Sync Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>In Sync</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>In Sync</em>' reference list.
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getSyncTransition_InSync()
	 * @see de.uni_kassel.cn.alica.Transition#getSyncTransitions
	 * @model opposite="syncTransitions"
	 * @generated
	 */
	EList<Transition> getInSync();

	/**
	 * Returns the value of the '<em><b>Talk Timeout</b></em>' attribute.
	 * The default value is <code>"30"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Talk Timeout</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Talk Timeout</em>' attribute.
	 * @see #setTalkTimeout(int)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getSyncTransition_TalkTimeout()
	 * @model default="30"
	 * @generated
	 */
	int getTalkTimeout();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.SyncTransition#getTalkTimeout <em>Talk Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Talk Timeout</em>' attribute.
	 * @see #getTalkTimeout()
	 * @generated
	 */
	void setTalkTimeout(int value);

	/**
	 * Returns the value of the '<em><b>Sync Timeout</b></em>' attribute.
	 * The default value is <code>"3000"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Sync Timeout</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Sync Timeout</em>' attribute.
	 * @see #setSyncTimeout(int)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getSyncTransition_SyncTimeout()
	 * @model default="3000"
	 * @generated
	 */
	int getSyncTimeout();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.SyncTransition#getSyncTimeout <em>Sync Timeout</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Sync Timeout</em>' attribute.
	 * @see #getSyncTimeout()
	 * @generated
	 */
	void setSyncTimeout(int value);

	/**
	 * Returns the value of the '<em><b>Fail On Sync Time Out</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fail On Sync Time Out</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fail On Sync Time Out</em>' attribute.
	 * @see #setFailOnSyncTimeOut(boolean)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getSyncTransition_FailOnSyncTimeOut()
	 * @model default="false"
	 * @generated
	 */
	boolean isFailOnSyncTimeOut();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.SyncTransition#isFailOnSyncTimeOut <em>Fail On Sync Time Out</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fail On Sync Time Out</em>' attribute.
	 * @see #isFailOnSyncTimeOut()
	 * @generated
	 */
	void setFailOnSyncTimeOut(boolean value);

} // SyncTransition
