/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.alica.tests;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.Plan;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Plan</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link de.uni_kassel.cn.alica.Plan#calculateCardinalities() <em>Calculate Cardinalities</em>}</li>
 *   <li>{@link de.uni_kassel.cn.alica.Plan#ensureParametrisationConsistency() <em>Ensure Parametrisation Consistency</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class PlanTest extends AbstractPlanTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PlanTest.class);
	}

	/**
	 * Constructs a new Plan test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Plan test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected Plan getFixture() {
		return (Plan)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AlicaFactory.eINSTANCE.createPlan());
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

	/**
	 * Tests the '{@link de.uni_kassel.cn.alica.Plan#calculateCardinalities() <em>Calculate Cardinalities</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.Plan#calculateCardinalities()
	 * @generated
	 */
	public void testCalculateCardinalities() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		fail();
	}

	/**
	 * Tests the '{@link de.uni_kassel.cn.alica.Plan#ensureParametrisationConsistency() <em>Ensure Parametrisation Consistency</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.Plan#ensureParametrisationConsistency()
	 * @generated
	 */
	public void testEnsureParametrisationConsistency() {
		// TODO: implement this operation test method
		// Ensure that you remove @generated or mark it @generated NOT
		fail();
	}

} //PlanTest
