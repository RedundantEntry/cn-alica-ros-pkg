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
package de.uni_kassel.cn.alica.impl;

import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Behaviour;
import de.uni_kassel.cn.alica.BehaviourConfiguration;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EMap;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreEMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Behaviour Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl#getParameters <em>Parameters</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl#getDeferring <em>Deferring</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl#getFrequency <em>Frequency</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl#getBehaviour <em>Behaviour</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl#isEventDriven <em>Event Driven</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class BehaviourConfigurationImpl extends AbstractPlanImpl implements BehaviourConfiguration {
	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	protected EMap<String, String> parameters;

	/**
	 * The default value of the '{@link #getDeferring() <em>Deferring</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeferring()
	 * @generated
	 * @ordered
	 */
	protected static final int DEFERRING_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getDeferring() <em>Deferring</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDeferring()
	 * @generated
	 * @ordered
	 */
	protected int deferring = DEFERRING_EDEFAULT;

	/**
	 * The default value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
	protected static final int FREQUENCY_EDEFAULT = 30;

	/**
	 * The cached value of the '{@link #getFrequency() <em>Frequency</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFrequency()
	 * @generated
	 * @ordered
	 */
	protected int frequency = FREQUENCY_EDEFAULT;

	/**
	 * The default value of the '{@link #isEventDriven() <em>Event Driven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEventDriven()
	 * @generated
	 * @ordered
	 */
	protected static final boolean EVENT_DRIVEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isEventDriven() <em>Event Driven</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isEventDriven()
	 * @generated
	 * @ordered
	 */
	protected boolean eventDriven = EVENT_DRIVEN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BehaviourConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.BEHAVIOUR_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EMap<String, String> getParameters() {
		if (parameters == null) {
			parameters = new EcoreEMap<String,String>(AlicaPackage.Literals.ESTRING_TO_ESTRING_MAP_ENTRY, EStringToEStringMapEntryImpl.class, this, AlicaPackage.BEHAVIOUR_CONFIGURATION__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDeferring() {
		return deferring;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeferring(int newDeferring) {
		int oldDeferring = deferring;
		deferring = newDeferring;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR_CONFIGURATION__DEFERRING, oldDeferring, deferring));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getFrequency() {
		return frequency;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrequency(int newFrequency) {
		int oldFrequency = frequency;
		frequency = newFrequency;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR_CONFIGURATION__FREQUENCY, oldFrequency, frequency));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Behaviour getBehaviour() {
		if (eContainerFeatureID() != AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR) return null;
		return (Behaviour)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBehaviour(Behaviour newBehaviour, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newBehaviour, AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBehaviour(Behaviour newBehaviour) {
		if (newBehaviour != eInternalContainer() || (eContainerFeatureID() != AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR && newBehaviour != null)) {
			if (EcoreUtil.isAncestor(this, newBehaviour))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newBehaviour != null)
				msgs = ((InternalEObject)newBehaviour).eInverseAdd(this, AlicaPackage.BEHAVIOUR__CONFIGURATIONS, Behaviour.class, msgs);
			msgs = basicSetBehaviour(newBehaviour, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR, newBehaviour, newBehaviour));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isEventDriven() {
		return eventDriven;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEventDriven(boolean newEventDriven) {
		boolean oldEventDriven = eventDriven;
		eventDriven = newEventDriven;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN, oldEventDriven, eventDriven));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetBehaviour((Behaviour)otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__PARAMETERS:
				return ((InternalEList<?>)getParameters()).basicRemove(otherEnd, msgs);
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				return basicSetBehaviour(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				return eInternalContainer().eInverseRemove(this, AlicaPackage.BEHAVIOUR__CONFIGURATIONS, Behaviour.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__PARAMETERS:
				if (coreType) return getParameters();
				else return getParameters().map();
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__DEFERRING:
				return getDeferring();
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__FREQUENCY:
				return getFrequency();
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				return getBehaviour();
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN:
				return isEventDriven();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__PARAMETERS:
				((EStructuralFeature.Setting)getParameters()).set(newValue);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__DEFERRING:
				setDeferring((Integer)newValue);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__FREQUENCY:
				setFrequency((Integer)newValue);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				setBehaviour((Behaviour)newValue);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN:
				setEventDriven((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__PARAMETERS:
				getParameters().clear();
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__DEFERRING:
				setDeferring(DEFERRING_EDEFAULT);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__FREQUENCY:
				setFrequency(FREQUENCY_EDEFAULT);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				setBehaviour((Behaviour)null);
				return;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN:
				setEventDriven(EVENT_DRIVEN_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__DEFERRING:
				return deferring != DEFERRING_EDEFAULT;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__FREQUENCY:
				return frequency != FREQUENCY_EDEFAULT;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__BEHAVIOUR:
				return getBehaviour() != null;
			case AlicaPackage.BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN:
				return eventDriven != EVENT_DRIVEN_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (deferring: ");
		result.append(deferring);
		result.append(", frequency: ");
		result.append(frequency);
		result.append(", eventDriven: ");
		result.append(eventDriven);
		result.append(')');
		return result.toString();
	}

} //BehaviourConfigurationImpl
