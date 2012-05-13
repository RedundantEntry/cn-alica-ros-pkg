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

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Condition;

import de.uni_kassel.cn.alica.Quantifier;
import de.uni_kassel.cn.alica.Variable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.impl.ConditionImpl#getConditionString <em>Condition String</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.ConditionImpl#getAbstractPlan <em>Abstract Plan</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.ConditionImpl#getVars <em>Vars</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.ConditionImpl#getQuantifiers <em>Quantifiers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class ConditionImpl extends PlanElementImpl implements Condition {
	/**
	 * The default value of the '{@link #getConditionString() <em>Condition String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditionString()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_STRING_EDEFAULT = "";

	/**
	 * The cached value of the '{@link #getConditionString() <em>Condition String</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConditionString()
	 * @generated
	 * @ordered
	 */
	protected String conditionString = CONDITION_STRING_EDEFAULT;

	/**
	 * The cached value of the '{@link #getVars() <em>Vars</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVars()
	 * @generated
	 * @ordered
	 */
	protected EList<Variable> vars;

	/**
	 * The cached value of the '{@link #getQuantifiers() <em>Quantifiers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQuantifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<Quantifier> quantifiers;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getConditionString() {
		return conditionString;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConditionString(String newConditionString) {
		String oldConditionString = conditionString;
		conditionString = newConditionString;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.CONDITION__CONDITION_STRING, oldConditionString, conditionString));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AbstractPlan getAbstractPlan() {
		if (eContainerFeatureID() != AlicaPackage.CONDITION__ABSTRACT_PLAN) return null;
		return (AbstractPlan)eContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAbstractPlan(AbstractPlan newAbstractPlan, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newAbstractPlan, AlicaPackage.CONDITION__ABSTRACT_PLAN, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAbstractPlan(AbstractPlan newAbstractPlan) {
		if (newAbstractPlan != eInternalContainer() || (eContainerFeatureID() != AlicaPackage.CONDITION__ABSTRACT_PLAN && newAbstractPlan != null)) {
			if (EcoreUtil.isAncestor(this, newAbstractPlan))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newAbstractPlan != null)
				msgs = ((InternalEObject)newAbstractPlan).eInverseAdd(this, AlicaPackage.ABSTRACT_PLAN__CONDITIONS, AbstractPlan.class, msgs);
			msgs = basicSetAbstractPlan(newAbstractPlan, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, AlicaPackage.CONDITION__ABSTRACT_PLAN, newAbstractPlan, newAbstractPlan));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Variable> getVars() {
		if (vars == null) {
			vars = new EObjectResolvingEList<Variable>(Variable.class, this, AlicaPackage.CONDITION__VARS);
		}
		return vars;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Quantifier> getQuantifiers() {
		if (quantifiers == null) {
			quantifiers = new EObjectContainmentEList<Quantifier>(Quantifier.class, this, AlicaPackage.CONDITION__QUANTIFIERS);
		}
		return quantifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void ensureVariableConsistency(AbstractPlan parentPlan) {
		LinkedList<Variable> toremove = new LinkedList<Variable>();
		for(Variable v: this.getVars()) {
			if (!parentPlan.getVars().contains(v)) {
				toremove.add(v);
			}
		}
		this.getVars().removeAll(toremove);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetAbstractPlan((AbstractPlan)otherEnd, msgs);
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
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				return basicSetAbstractPlan(null, msgs);
			case AlicaPackage.CONDITION__QUANTIFIERS:
				return ((InternalEList<?>)getQuantifiers()).basicRemove(otherEnd, msgs);
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
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				return eInternalContainer().eInverseRemove(this, AlicaPackage.ABSTRACT_PLAN__CONDITIONS, AbstractPlan.class, msgs);
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
			case AlicaPackage.CONDITION__CONDITION_STRING:
				return getConditionString();
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				return getAbstractPlan();
			case AlicaPackage.CONDITION__VARS:
				return getVars();
			case AlicaPackage.CONDITION__QUANTIFIERS:
				return getQuantifiers();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case AlicaPackage.CONDITION__CONDITION_STRING:
				setConditionString((String)newValue);
				return;
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				setAbstractPlan((AbstractPlan)newValue);
				return;
			case AlicaPackage.CONDITION__VARS:
				getVars().clear();
				getVars().addAll((Collection<? extends Variable>)newValue);
				return;
			case AlicaPackage.CONDITION__QUANTIFIERS:
				getQuantifiers().clear();
				getQuantifiers().addAll((Collection<? extends Quantifier>)newValue);
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
			case AlicaPackage.CONDITION__CONDITION_STRING:
				setConditionString(CONDITION_STRING_EDEFAULT);
				return;
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				setAbstractPlan((AbstractPlan)null);
				return;
			case AlicaPackage.CONDITION__VARS:
				getVars().clear();
				return;
			case AlicaPackage.CONDITION__QUANTIFIERS:
				getQuantifiers().clear();
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
			case AlicaPackage.CONDITION__CONDITION_STRING:
				return CONDITION_STRING_EDEFAULT == null ? conditionString != null : !CONDITION_STRING_EDEFAULT.equals(conditionString);
			case AlicaPackage.CONDITION__ABSTRACT_PLAN:
				return getAbstractPlan() != null;
			case AlicaPackage.CONDITION__VARS:
				return vars != null && !vars.isEmpty();
			case AlicaPackage.CONDITION__QUANTIFIERS:
				return quantifiers != null && !quantifiers.isEmpty();
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
		result.append(" (conditionString: ");
		result.append(conditionString);
		result.append(')');
		return result.toString();
	}

} //ConditionImpl
