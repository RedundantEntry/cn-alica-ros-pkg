/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.alica.tests;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.PlanningProblem;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Planning Problem</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class PlanningProblemTest extends AbstractPlanTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(PlanningProblemTest.class);
	}

	/**
	 * Constructs a new Planning Problem test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanningProblemTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Planning Problem test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected PlanningProblem getFixture() {
		return (PlanningProblem)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AlicaFactory.eINSTANCE.createPlanningProblem());
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

} //PlanningProblemTest
