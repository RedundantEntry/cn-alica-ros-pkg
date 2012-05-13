/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.cn.alica.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>alica</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class AlicaTests extends TestSuite {

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(suite());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static Test suite() {
		TestSuite suite = new AlicaTests("alica Tests");
		suite.addTestSuite(SuccessTransitionTest.class);
		suite.addTestSuite(PreConditionTest.class);
		suite.addTestSuite(FailureTransitionTest.class);
		suite.addTestSuite(SyncTransitionTest.class);
		suite.addTestSuite(EntryPointTest.class);
		suite.addTestSuite(SuccessPointTest.class);
		suite.addTestSuite(FailurePointTest.class);
		suite.addTestSuite(BehaviourTest.class);
		suite.addTestSuite(StateTest.class);
		suite.addTestSuite(PlanTest.class);
		suite.addTestSuite(UtilityTest.class);
		suite.addTestSuite(PlanTypeTest.class);
		suite.addTestSuite(RatingTest.class);
		suite.addTestSuite(ResultTest.class);
		suite.addTestSuite(RuntimeConditionTest.class);
		suite.addTestSuite(TaskTest.class);
		suite.addTestSuite(BehaviourConfigurationTest.class);
		suite.addTestSuite(RoleTest.class);
		suite.addTestSuite(RoleSetTest.class);
		suite.addTestSuite(RoleDefinitionSetTest.class);
		suite.addTestSuite(RoleTaskMappingTest.class);
		suite.addTestSuite(CharacteristicTest.class);
		suite.addTestSuite(TaskRepositoryTest.class);
		suite.addTestSuite(UtilityRepositoryTest.class);
		suite.addTestSuite(UtilityReferenceTest.class);
		suite.addTestSuite(UtilityModeTest.class);
		suite.addTestSuite(UtilityModeExpressionTest.class);
		suite.addTestSuite(SynchronisationTest.class);
		suite.addTestSuite(VariableTest.class);
		suite.addTestSuite(ParametrisationTest.class);
		suite.addTestSuite(AnnotatedPlanTest.class);
		suite.addTestSuite(ForallAgentsTest.class);
		suite.addTestSuite(CapabilityTest.class);
		suite.addTestSuite(CapValueTest.class);
		suite.addTestSuite(CapabilityDefinitionSetTest.class);
		suite.addTestSuite(PlanningProblemTest.class);
		return suite;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaTests(String name) {
		super(name);
	}

} //AlicaTests
