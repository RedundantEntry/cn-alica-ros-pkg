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
package de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.Bendpoint;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUIExtensionModelPackage;
import de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.PmlUiExtension;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Pml Ui Extension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#getXPos <em>XPos</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#getYPos <em>YPos</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#getWidth <em>Width</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#getHeight <em>Height</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#isCollapsed <em>Collapsed</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#getBendpoints <em>Bendpoints</em>}</li>
 *   <li>{@link de.uni_kassel.cn.planDesigner.ui.uiextensionmodel.impl.PmlUiExtensionImpl#isVisible <em>Visible</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class PmlUiExtensionImpl extends EObjectImpl implements PmlUiExtension {
	/**
	 * The default value of the '{@link #getXPos() <em>XPos</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXPos()
	 * @generated
	 * @ordered
	 */
	protected static final int XPOS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getXPos() <em>XPos</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getXPos()
	 * @generated
	 * @ordered
	 */
	protected int xPos = XPOS_EDEFAULT;

	/**
	 * The default value of the '{@link #getYPos() <em>YPos</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYPos()
	 * @generated
	 * @ordered
	 */
	protected static final int YPOS_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getYPos() <em>YPos</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getYPos()
	 * @generated
	 * @ordered
	 */
	protected int yPos = YPOS_EDEFAULT;

	/**
	 * The default value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected static final int WIDTH_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getWidth() <em>Width</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWidth()
	 * @generated
	 * @ordered
	 */
	protected int width = WIDTH_EDEFAULT;

	/**
	 * The default value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected static final int HEIGHT_EDEFAULT = -1;

	/**
	 * The cached value of the '{@link #getHeight() <em>Height</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHeight()
	 * @generated
	 * @ordered
	 */
	protected int height = HEIGHT_EDEFAULT;

	/**
	 * The default value of the '{@link #isCollapsed() <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCollapsed()
	 * @generated
	 * @ordered
	 */
	protected static final boolean COLLAPSED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isCollapsed() <em>Collapsed</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isCollapsed()
	 * @generated
	 * @ordered
	 */
	protected boolean collapsed = COLLAPSED_EDEFAULT;

	/**
	 * The cached value of the '{@link #getBendpoints() <em>Bendpoints</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBendpoints()
	 * @generated
	 * @ordered
	 */
	protected EList<Bendpoint> bendpoints;

	/**
	 * The default value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected static final boolean VISIBLE_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isVisible() <em>Visible</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isVisible()
	 * @generated
	 * @ordered
	 */
	protected boolean visible = VISIBLE_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PmlUiExtensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return PmlUIExtensionModelPackage.Literals.PML_UI_EXTENSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getXPos() {
		return xPos;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setXPos(int newXPos) {
		int oldXPos = xPos;
		xPos = newXPos;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PmlUIExtensionModelPackage.PML_UI_EXTENSION__XPOS, oldXPos, xPos));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getYPos() {
		return yPos;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setYPos(int newYPos) {
		int oldYPos = yPos;
		yPos = newYPos;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PmlUIExtensionModelPackage.PML_UI_EXTENSION__YPOS, oldYPos, yPos));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWidth(int newWidth) {
		int oldWidth = width;
		width = newWidth;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PmlUIExtensionModelPackage.PML_UI_EXTENSION__WIDTH, oldWidth, width));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setHeight(int newHeight) {
		int oldHeight = height;
		height = newHeight;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PmlUIExtensionModelPackage.PML_UI_EXTENSION__HEIGHT, oldHeight, height));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isCollapsed() {
		return collapsed;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCollapsed(boolean newCollapsed) {
		boolean oldCollapsed = collapsed;
		collapsed = newCollapsed;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PmlUIExtensionModelPackage.PML_UI_EXTENSION__COLLAPSED, oldCollapsed, collapsed));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Bendpoint> getBendpoints() {
		if (bendpoints == null)
		{
			bendpoints = new EObjectContainmentEList<Bendpoint>(Bendpoint.class, this, PmlUIExtensionModelPackage.PML_UI_EXTENSION__BENDPOINTS);
		}
		return bendpoints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isVisible()
	{
		return visible;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVisible(boolean newVisible)
	{
		boolean oldVisible = visible;
		visible = newVisible;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, PmlUIExtensionModelPackage.PML_UI_EXTENSION__VISIBLE, oldVisible, visible));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__BENDPOINTS:
				return ((InternalEList<?>)getBendpoints()).basicRemove(otherEnd, msgs);
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
		switch (featureID)
		{
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__XPOS:
				return new Integer(getXPos());
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__YPOS:
				return new Integer(getYPos());
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__WIDTH:
				return new Integer(getWidth());
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__HEIGHT:
				return new Integer(getHeight());
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__COLLAPSED:
				return isCollapsed() ? Boolean.TRUE : Boolean.FALSE;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__BENDPOINTS:
				return getBendpoints();
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__VISIBLE:
				return isVisible() ? Boolean.TRUE : Boolean.FALSE;
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
		switch (featureID)
		{
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__XPOS:
				setXPos(((Integer)newValue).intValue());
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__YPOS:
				setYPos(((Integer)newValue).intValue());
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__WIDTH:
				setWidth(((Integer)newValue).intValue());
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__HEIGHT:
				setHeight(((Integer)newValue).intValue());
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__COLLAPSED:
				setCollapsed(((Boolean)newValue).booleanValue());
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__BENDPOINTS:
				getBendpoints().clear();
				getBendpoints().addAll((Collection<? extends Bendpoint>)newValue);
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__VISIBLE:
				setVisible(((Boolean)newValue).booleanValue());
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
		switch (featureID)
		{
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__XPOS:
				setXPos(XPOS_EDEFAULT);
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__YPOS:
				setYPos(YPOS_EDEFAULT);
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__WIDTH:
				setWidth(WIDTH_EDEFAULT);
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__HEIGHT:
				setHeight(HEIGHT_EDEFAULT);
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__COLLAPSED:
				setCollapsed(COLLAPSED_EDEFAULT);
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__BENDPOINTS:
				getBendpoints().clear();
				return;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__VISIBLE:
				setVisible(VISIBLE_EDEFAULT);
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
		switch (featureID)
		{
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__XPOS:
				return xPos != XPOS_EDEFAULT;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__YPOS:
				return yPos != YPOS_EDEFAULT;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__WIDTH:
				return width != WIDTH_EDEFAULT;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__HEIGHT:
				return height != HEIGHT_EDEFAULT;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__COLLAPSED:
				return collapsed != COLLAPSED_EDEFAULT;
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__BENDPOINTS:
				return bendpoints != null && !bendpoints.isEmpty();
			case PmlUIExtensionModelPackage.PML_UI_EXTENSION__VISIBLE:
				return visible != VISIBLE_EDEFAULT;
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
		result.append(" (xPos: ");
		result.append(xPos);
		result.append(", yPos: ");
		result.append(yPos);
		result.append(", width: ");
		result.append(width);
		result.append(", height: ");
		result.append(height);
		result.append(", collapsed: ");
		result.append(collapsed);
		result.append(", visible: ");
		result.append(visible);
		result.append(')');
		return result.toString();
	}

} //PmlUiExtensionImpl
