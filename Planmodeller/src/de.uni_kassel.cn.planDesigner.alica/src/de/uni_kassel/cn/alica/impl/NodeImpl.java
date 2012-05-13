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
import de.uni_kassel.cn.alica.Edge;
import de.uni_kassel.cn.alica.Node;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.impl.NodeImpl#getInEdge <em>In Edge</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.impl.NodeImpl#getOutEdge <em>Out Edge</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NodeImpl extends EObjectImpl implements Node {
	/**
	 * The cached value of the '{@link #getInEdge() <em>In Edge</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getInEdge()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> inEdge;

	/**
	 * The cached value of the '{@link #getOutEdge() <em>Out Edge</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOutEdge()
	 * @generated
	 * @ordered
	 */
	protected EList<Edge> outEdge;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return AlicaPackage.Literals.NODE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getInEdge() {
		if (inEdge == null) {
			inEdge = new EObjectWithInverseResolvingEList<Edge>(Edge.class, this, AlicaPackage.NODE__IN_EDGE, AlicaPackage.EDGE__TO);
		}
		return inEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Edge> getOutEdge() {
		if (outEdge == null) {
			outEdge = new EObjectWithInverseResolvingEList<Edge>(Edge.class, this, AlicaPackage.NODE__OUT_EDGE, AlicaPackage.EDGE__FROM);
		}
		return outEdge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case AlicaPackage.NODE__IN_EDGE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getInEdge()).basicAdd(otherEnd, msgs);
			case AlicaPackage.NODE__OUT_EDGE:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getOutEdge()).basicAdd(otherEnd, msgs);
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
			case AlicaPackage.NODE__IN_EDGE:
				return ((InternalEList<?>)getInEdge()).basicRemove(otherEnd, msgs);
			case AlicaPackage.NODE__OUT_EDGE:
				return ((InternalEList<?>)getOutEdge()).basicRemove(otherEnd, msgs);
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
			case AlicaPackage.NODE__IN_EDGE:
				return getInEdge();
			case AlicaPackage.NODE__OUT_EDGE:
				return getOutEdge();
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
			case AlicaPackage.NODE__IN_EDGE:
				getInEdge().clear();
				getInEdge().addAll((Collection<? extends Edge>)newValue);
				return;
			case AlicaPackage.NODE__OUT_EDGE:
				getOutEdge().clear();
				getOutEdge().addAll((Collection<? extends Edge>)newValue);
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
			case AlicaPackage.NODE__IN_EDGE:
				getInEdge().clear();
				return;
			case AlicaPackage.NODE__OUT_EDGE:
				getOutEdge().clear();
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
			case AlicaPackage.NODE__IN_EDGE:
				return inEdge != null && !inEdge.isEmpty();
			case AlicaPackage.NODE__OUT_EDGE:
				return outEdge != null && !outEdge.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //NodeImpl
