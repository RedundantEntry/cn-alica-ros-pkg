/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.alica.tests;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.RoleTaskMapping;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Role Task Mapping</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class RoleTaskMappingTest extends PlanElementTest {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(RoleTaskMappingTest.class);
	}

	/**
	 * Constructs a new Role Task Mapping test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleTaskMappingTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Role Task Mapping test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected RoleTaskMapping getFixture() {
		return (RoleTaskMapping)fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(AlicaFactory.eINSTANCE.createRoleTaskMapping());
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

} //RoleTaskMappingTest
