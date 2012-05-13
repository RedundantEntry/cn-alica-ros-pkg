/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.alica.tests;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.AlicaPackage;

import java.util.Map;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>ELong To Double Map Entry</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class ELongToDoubleMapEntryTest extends PlanElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ELongToDoubleMapEntryTest.class);
	}

	/**
	 * Constructs a new ELong To Double Map Entry test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ELongToDoubleMapEntryTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this ELong To Double Map Entry test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Map.Entry<Long, Double> getFixture() {
		return (Map.Entry<Long, Double>)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected void setUp() throws Exception {
		setFixture((Map.Entry<Long, Double>)AlicaFactory.eINSTANCE.create(AlicaPackage.Literals.ELONG_TO_DOUBLE_MAP_ENTRY));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //ELongToDoubleMapEntryTest
