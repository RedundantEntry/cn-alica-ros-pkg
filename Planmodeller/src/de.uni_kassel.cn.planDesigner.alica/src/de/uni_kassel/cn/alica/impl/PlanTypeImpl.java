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
import de.uni_kassel.cn.alica.AnnotatedPlan;
import de.uni_kassel.cn.alica.Parametrisation;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.PlanType;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Plan Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.impl.PlanTypeImpl#getParametrisation <em>Parametrisation</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.PlanTypeImpl#getPlans <em>Plans</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PlanTypeImpl extends AbstractPlanImpl implements PlanType {
	/**
	 * The cached value of the '{@link #getParametrisation() <em>Parametrisation</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParametrisation()
	 * @generated
	 * @ordered
	 */
	protected EList<Parametrisation> parametrisation;

	/**
	 * The cached value of the '{@link #getPlans() <em>Plans</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlans()
	 * @generated
	 * @ordered
	 */
	protected EList<AnnotatedPlan> plans;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PlanTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.PLAN_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<AnnotatedPlan> getPlans() {
		if (plans == null) {
			plans = new EObjectContainmentEList<AnnotatedPlan>(AnnotatedPlan.class, this, AlicaPackage.PLAN_TYPE__PLANS);
		}
		return plans;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Parametrisation> getParametrisation() {
		if (parametrisation == null) {
			parametrisation = new EObjectContainmentEList<Parametrisation>(Parametrisation.class, this, AlicaPackage.PLAN_TYPE__PARAMETRISATION);
		}
		return parametrisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public void ensureParametrisationConsistency() {
		boolean dirty = false;
		if (this.parametrisation==null) return;
		for(int i=0; i<this.parametrisation.size(); i++) {
			boolean remove = false;
			if(!this.getVars().contains(this.parametrisation.get(i).getVar())) {
				remove = true;
			} else if (!this.parametrisation.get(i).getSubplan().getVars().contains(this.parametrisation.get(i).getSubvar())) {
				remove = true;
			}
			if(!this.getPlans().contains(this.parametrisation.get(i).getSubplan())) {
				remove = true;
			}
			if (remove) {
				this.parametrisation.remove(i);
				i--;
				dirty = true;
			}
		}
		if (dirty) {
			eNotify(new ENotificationImpl(this, Notification.REMOVE_MANY, AlicaPackage.PLAN_TYPE__PARAMETRISATION,this.parametrisation,this.parametrisation,true));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				return ((InternalEList<?>)getParametrisation()).basicRemove(otherEnd, msgs);
			case AlicaPackage.PLAN_TYPE__PLANS:
				return ((InternalEList<?>)getPlans()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				return getParametrisation();
			case AlicaPackage.PLAN_TYPE__PLANS:
				return getPlans();
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
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				getParametrisation().clear();
				getParametrisation().addAll((Collection<? extends Parametrisation>)newValue);
				return;
			case AlicaPackage.PLAN_TYPE__PLANS:
				getPlans().clear();
				getPlans().addAll((Collection<? extends AnnotatedPlan>)newValue);
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
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				getParametrisation().clear();
				return;
			case AlicaPackage.PLAN_TYPE__PLANS:
				getPlans().clear();
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
			case AlicaPackage.PLAN_TYPE__PARAMETRISATION:
				return parametrisation != null && !parametrisation.isEmpty();
			case AlicaPackage.PLAN_TYPE__PLANS:
				return plans != null && !plans.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //PlanTypeImpl
