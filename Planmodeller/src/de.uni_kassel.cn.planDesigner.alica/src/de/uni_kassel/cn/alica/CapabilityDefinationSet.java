/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.alica;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability Definition Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.CapabilityDefinationSet#getCapabilities <em>Capabilities</em>}</li>
 * </ul>
 * </p>
 *
 * @see de.uni_kassel.cn.alica.AlicaPackage#getCapabilityDefinationSet()
 * @model
 * @generated
 */
public interface CapabilityDefinationSet extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Capabilities</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Capabilities</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Capabilities</em>' reference.
	 * @see #setCapabilities(Capability)
	 * @see de.uni_kassel.cn.alica.AlicaPackage#getCapabilityDefinationSet_Capabilities()
	 * @model
	 * @generated
	 */
	Capability getCapabilities();

	/**
	 * Sets the value of the '{@link de.uni_kassel.cn.alica.CapabilityDefinationSet#getCapabilities <em>Capabilities</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Capabilities</em>' reference.
	 * @see #getCapabilities()
	 * @generated
	 */
	void setCapabilities(Capability value);

} // CapabilityDefinationSet
