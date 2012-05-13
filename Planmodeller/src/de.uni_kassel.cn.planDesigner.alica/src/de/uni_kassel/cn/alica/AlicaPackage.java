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
package de.uni_kassel.cn.alica;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see de.uni_kassel.cn.alica.AlicaFactory
 * @model kind="package"
 * @generated
 */
public interface AlicaPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "alica";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///de.uni_kassel.cn";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "alica";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	AlicaPackage eINSTANCE = de.uni_kassel.cn.alica.impl.AlicaPackageImpl.init();

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.PlanElementImpl <em>Plan Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.PlanElementImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlanElement()
	 * @generated
	 */
	int PLAN_ELEMENT = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT__ID = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT__NAME = 1;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT__COMMENT = 2;

	/**
	 * The number of structural features of the '<em>Plan Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.TransitionImpl <em>Transition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.TransitionImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTransition()
	 * @generated
	 */
	int TRANSITION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Msg</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__MSG = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Pre Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__PRE_CONDITION = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>In State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__IN_STATE = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Out State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__OUT_STATE = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Synchronisation</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION__SYNCHRONISATION = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Transition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRANSITION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.ConditionImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__CONDITION_STRING = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Abstract Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__ABSTRACT_PLAN = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__VARS = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__QUANTIFIERS = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.PreConditionImpl <em>Pre Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.PreConditionImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPreCondition()
	 * @generated
	 */
	int PRE_CONDITION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__ID = CONDITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__NAME = CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__COMMENT = CONDITION__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__CONDITION_STRING = CONDITION__CONDITION_STRING;

	/**
	 * The feature id for the '<em><b>Abstract Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__ABSTRACT_PLAN = CONDITION__ABSTRACT_PLAN;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__VARS = CONDITION__VARS;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__QUANTIFIERS = CONDITION__QUANTIFIERS;

	/**
	 * The feature id for the '<em><b>Enabled</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION__ENABLED = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Pre Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRE_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.EntryPointImpl <em>Entry Point</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.EntryPointImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getEntryPoint()
	 * @generated
	 */
	int ENTRY_POINT = 3;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.AbstractPlanImpl <em>Abstract Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.AbstractPlanImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getAbstractPlan()
	 * @generated
	 */
	int ABSTRACT_PLAN = 7;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.BehaviourImpl <em>Behaviour</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.BehaviourImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getBehaviour()
	 * @generated
	 */
	int BEHAVIOUR = 8;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.StateImpl <em>State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.StateImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getState()
	 * @generated
	 */
	int STATE = 9;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.TerminalStateImpl <em>Terminal State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.TerminalStateImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTerminalState()
	 * @generated
	 */
	int TERMINAL_STATE = 4;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.SuccessStateImpl <em>Success State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.SuccessStateImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getSuccessState()
	 * @generated
	 */
	int SUCCESS_STATE = 5;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.FailureStateImpl <em>Failure State</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.FailureStateImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getFailureState()
	 * @generated
	 */
	int FAILURE_STATE = 6;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.PlanImpl <em>Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.PlanImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlan()
	 * @generated
	 */
	int PLAN = 10;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.UtilityImpl <em>Utility</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.UtilityImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtility()
	 * @generated
	 */
	int UTILITY = 11;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.PlanTypeImpl <em>Plan Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.PlanTypeImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlanType()
	 * @generated
	 */
	int PLAN_TYPE = 12;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.RatingImpl <em>Rating</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.RatingImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRating()
	 * @generated
	 */
	int RATING = 13;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.ResultImpl <em>Result</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.ResultImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getResult()
	 * @generated
	 */
	int RESULT = 14;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.RuntimeConditionImpl <em>Runtime Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.RuntimeConditionImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRuntimeCondition()
	 * @generated
	 */
	int RUNTIME_CONDITION = 15;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.TaskImpl <em>Task</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.TaskImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTask()
	 * @generated
	 */
	int TASK = 17;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.EStringToEStringMapEntryImpl <em>EString To EString Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.EStringToEStringMapEntryImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getEStringToEStringMapEntry()
	 * @generated
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY = 18;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl <em>Behaviour Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getBehaviourConfiguration()
	 * @generated
	 */
	int BEHAVIOUR_CONFIGURATION = 19;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.RoleImpl <em>Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.RoleImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 20;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.RoleSetImpl <em>Role Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.RoleSetImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRoleSet()
	 * @generated
	 */
	int ROLE_SET = 21;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.ELongToDoubleMapEntryImpl <em>ELong To Double Map Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.ELongToDoubleMapEntryImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getELongToDoubleMapEntry()
	 * @generated
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY = 22;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.RoleDefinitionSetImpl <em>Role Definition Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.RoleDefinitionSetImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRoleDefinitionSet()
	 * @generated
	 */
	int ROLE_DEFINITION_SET = 23;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.RoleTaskMappingImpl <em>Role Task Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.RoleTaskMappingImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRoleTaskMapping()
	 * @generated
	 */
	int ROLE_TASK_MAPPING = 24;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.CharacteristicImpl <em>Characteristic</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.CharacteristicImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCharacteristic()
	 * @generated
	 */
	int CHARACTERISTIC = 25;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.TaskGraphImpl <em>Task Graph</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.TaskGraphImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTaskGraph()
	 * @generated
	 */
	int TASK_GRAPH = 26;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.EdgeImpl <em>Edge</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.EdgeImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getEdge()
	 * @generated
	 */
	int EDGE = 27;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.NodeImpl <em>Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.NodeImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getNode()
	 * @generated
	 */
	int NODE = 30;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.TaskWrapperImpl <em>Task Wrapper</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.TaskWrapperImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTaskWrapper()
	 * @generated
	 */
	int TASK_WRAPPER = 28;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.InternalRoleTaskMappingImpl <em>Internal Role Task Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.InternalRoleTaskMappingImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getInternalRoleTaskMapping()
	 * @generated
	 */
	int INTERNAL_ROLE_TASK_MAPPING = 29;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.TaskRepositoryImpl <em>Task Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.TaskRepositoryImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTaskRepository()
	 * @generated
	 */
	int TASK_REPOSITORY = 31;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.UtilityRepositoryImpl <em>Utility Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.UtilityRepositoryImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityRepository()
	 * @generated
	 */
	int UTILITY_REPOSITORY = 32;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.UtilityReferenceImpl <em>Utility Reference</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.UtilityReferenceImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityReference()
	 * @generated
	 */
	int UTILITY_REFERENCE = 33;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.UtilityModeImpl <em>Utility Mode</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.UtilityModeImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityMode()
	 * @generated
	 */
	int UTILITY_MODE = 34;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.UtilityModeExpressionImpl <em>Utility Mode Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.UtilityModeExpressionImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityModeExpression()
	 * @generated
	 */
	int UTILITY_MODE_EXPRESSION = 35;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.SynchronisationImpl <em>Synchronisation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.SynchronisationImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getSynchronisation()
	 * @generated
	 */
	int SYNCHRONISATION = 36;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.VariableImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 37;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.ParametrisationImpl <em>Parametrisation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.ParametrisationImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getParametrisation()
	 * @generated
	 */
	int PARAMETRISATION = 38;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.AnnotatedPlanImpl <em>Annotated Plan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.AnnotatedPlanImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getAnnotatedPlan()
	 * @generated
	 */
	int ANNOTATED_PLAN = 39;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.QuantifierImpl <em>Quantifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.QuantifierImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getQuantifier()
	 * @generated
	 */
	int QUANTIFIER = 40;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.ForallAgentsImpl <em>Forall Agents</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.ForallAgentsImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getForallAgents()
	 * @generated
	 */
	int FORALL_AGENTS = 41;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.IInhabitable <em>IInhabitable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.IInhabitable
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getIInhabitable()
	 * @generated
	 */
	int IINHABITABLE = 42;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>IInhabitable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IINHABITABLE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__ID = IINHABITABLE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__NAME = IINHABITABLE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__COMMENT = IINHABITABLE__COMMENT;

	/**
	 * The feature id for the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__TASK = IINHABITABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Success Required</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__SUCCESS_REQUIRED = IINHABITABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>State</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__STATE = IINHABITABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__MIN_CARDINALITY = IINHABITABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Max Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__MAX_CARDINALITY = IINHABITABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT__PLAN = IINHABITABLE_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Entry Point</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTRY_POINT_FEATURE_COUNT = IINHABITABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PLANS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IN_PLAN = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__PARAMETRISATION = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__IN_TRANSITIONS = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__OUT_TRANSITIONS = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE__ENTRY_POINT = PLAN_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STATE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__ID = STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__NAME = STATE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__COMMENT = STATE__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__PLANS = STATE__PLANS;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__IN_PLAN = STATE__IN_PLAN;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__PARAMETRISATION = STATE__PARAMETRISATION;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__IN_TRANSITIONS = STATE__IN_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__OUT_TRANSITIONS = STATE__OUT_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__ENTRY_POINT = STATE__ENTRY_POINT;

	/**
	 * The feature id for the '<em><b>Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE__RESULT = STATE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Terminal State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERMINAL_STATE_FEATURE_COUNT = STATE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__ID = TERMINAL_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__NAME = TERMINAL_STATE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__COMMENT = TERMINAL_STATE__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__PLANS = TERMINAL_STATE__PLANS;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__IN_PLAN = TERMINAL_STATE__IN_PLAN;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__PARAMETRISATION = TERMINAL_STATE__PARAMETRISATION;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__IN_TRANSITIONS = TERMINAL_STATE__IN_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__OUT_TRANSITIONS = TERMINAL_STATE__OUT_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__ENTRY_POINT = TERMINAL_STATE__ENTRY_POINT;

	/**
	 * The feature id for the '<em><b>Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE__RESULT = TERMINAL_STATE__RESULT;

	/**
	 * The number of structural features of the '<em>Success State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SUCCESS_STATE_FEATURE_COUNT = TERMINAL_STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__ID = TERMINAL_STATE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__NAME = TERMINAL_STATE__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__COMMENT = TERMINAL_STATE__COMMENT;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__PLANS = TERMINAL_STATE__PLANS;

	/**
	 * The feature id for the '<em><b>In Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__IN_PLAN = TERMINAL_STATE__IN_PLAN;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__PARAMETRISATION = TERMINAL_STATE__PARAMETRISATION;

	/**
	 * The feature id for the '<em><b>In Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__IN_TRANSITIONS = TERMINAL_STATE__IN_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Out Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__OUT_TRANSITIONS = TERMINAL_STATE__OUT_TRANSITIONS;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__ENTRY_POINT = TERMINAL_STATE__ENTRY_POINT;

	/**
	 * The feature id for the '<em><b>Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE__RESULT = TERMINAL_STATE__RESULT;

	/**
	 * The number of structural features of the '<em>Failure State</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FAILURE_STATE_FEATURE_COUNT = TERMINAL_STATE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Rating</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__RATING = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__CONDITIONS = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Master Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__MASTER_PLAN = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Utility Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__UTILITY_FUNCTION = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Utilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__UTILITIES = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Utility Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__UTILITY_THRESHOLD = PLAN_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN__VARS = PLAN_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Abstract Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_PLAN_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR__CONFIGURATIONS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Behaviour</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Rating</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__RATING = ABSTRACT_PLAN__RATING;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__CONDITIONS = ABSTRACT_PLAN__CONDITIONS;

	/**
	 * The feature id for the '<em><b>Master Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__MASTER_PLAN = ABSTRACT_PLAN__MASTER_PLAN;

	/**
	 * The feature id for the '<em><b>Utility Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__UTILITY_FUNCTION = ABSTRACT_PLAN__UTILITY_FUNCTION;

	/**
	 * The feature id for the '<em><b>Utilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__UTILITIES = ABSTRACT_PLAN__UTILITIES;

	/**
	 * The feature id for the '<em><b>Utility Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__UTILITY_THRESHOLD = ABSTRACT_PLAN__UTILITY_THRESHOLD;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__VARS = ABSTRACT_PLAN__VARS;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__PRIORITY = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>States</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__STATES = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Transitions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__TRANSITIONS = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__MIN_CARDINALITY = ABSTRACT_PLAN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Max Cardinality</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__MAX_CARDINALITY = ABSTRACT_PLAN_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Synchronisations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__SYNCHRONISATIONS = ABSTRACT_PLAN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Entry Points</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN__ENTRY_POINTS = ABSTRACT_PLAN_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY__EXPRESSION = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Modes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY__MODES = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Utility</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Rating</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__RATING = ABSTRACT_PLAN__RATING;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__CONDITIONS = ABSTRACT_PLAN__CONDITIONS;

	/**
	 * The feature id for the '<em><b>Master Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__MASTER_PLAN = ABSTRACT_PLAN__MASTER_PLAN;

	/**
	 * The feature id for the '<em><b>Utility Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__UTILITY_FUNCTION = ABSTRACT_PLAN__UTILITY_FUNCTION;

	/**
	 * The feature id for the '<em><b>Utilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__UTILITIES = ABSTRACT_PLAN__UTILITIES;

	/**
	 * The feature id for the '<em><b>Utility Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__UTILITY_THRESHOLD = ABSTRACT_PLAN__UTILITY_THRESHOLD;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__VARS = ABSTRACT_PLAN__VARS;

	/**
	 * The feature id for the '<em><b>Parametrisation</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__PARAMETRISATION = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE__PLANS = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Plan Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLAN_TYPE_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Rating</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RATING_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__ID = CONDITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__NAME = CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__COMMENT = CONDITION__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__CONDITION_STRING = CONDITION__CONDITION_STRING;

	/**
	 * The feature id for the '<em><b>Abstract Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__ABSTRACT_PLAN = CONDITION__ABSTRACT_PLAN;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__VARS = CONDITION__VARS;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT__QUANTIFIERS = CONDITION__QUANTIFIERS;

	/**
	 * The number of structural features of the '<em>Result</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESULT_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__ID = CONDITION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__NAME = CONDITION__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__COMMENT = CONDITION__COMMENT;

	/**
	 * The feature id for the '<em><b>Condition String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__CONDITION_STRING = CONDITION__CONDITION_STRING;

	/**
	 * The feature id for the '<em><b>Abstract Plan</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__ABSTRACT_PLAN = CONDITION__ABSTRACT_PLAN;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__VARS = CONDITION__VARS;

	/**
	 * The feature id for the '<em><b>Quantifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION__QUANTIFIERS = CONDITION__QUANTIFIERS;

	/**
	 * The number of structural features of the '<em>Runtime Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RUNTIME_CONDITION_FEATURE_COUNT = CONDITION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK__DESCRIPTION = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Task</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__KEY = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>EString To EString Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ESTRING_TO_ESTRING_MAP_ENTRY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Rating</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__RATING = ABSTRACT_PLAN__RATING;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__CONDITIONS = ABSTRACT_PLAN__CONDITIONS;

	/**
	 * The feature id for the '<em><b>Master Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__MASTER_PLAN = ABSTRACT_PLAN__MASTER_PLAN;

	/**
	 * The feature id for the '<em><b>Utility Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__UTILITY_FUNCTION = ABSTRACT_PLAN__UTILITY_FUNCTION;

	/**
	 * The feature id for the '<em><b>Utilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__UTILITIES = ABSTRACT_PLAN__UTILITIES;

	/**
	 * The feature id for the '<em><b>Utility Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__UTILITY_THRESHOLD = ABSTRACT_PLAN__UTILITY_THRESHOLD;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__VARS = ABSTRACT_PLAN__VARS;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__PARAMETERS = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Deferring</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__DEFERRING = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__FREQUENCY = ABSTRACT_PLAN_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Behaviour</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__BEHAVIOUR = ABSTRACT_PLAN_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Event Driven</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN = ABSTRACT_PLAN_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Behaviour Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BEHAVIOUR_CONFIGURATION_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Characteristics</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__CHARACTERISTICS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Usable With Plan ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__USABLE_WITH_PLAN_ID = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__DEFAULT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET__MAPPINGS = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Role Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_SET_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__KEY = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ELong To Double Map Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELONG_TO_DOUBLE_MAP_ENTRY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET__ROLES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Role Definition Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_DEFINITION_SET_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Task Priorities</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__TASK_PRIORITIES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING__ROLE = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Role Task Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_TASK_MAPPING_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__WEIGHT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Capability</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__CAPABILITY = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC__VALUE = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Characteristic</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHARACTERISTIC_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH__NODES = 0;

	/**
	 * The feature id for the '<em><b>Edges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH__EDGES = 1;

	/**
	 * The number of structural features of the '<em>Task Graph</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_GRAPH_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>From</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__FROM = 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE__TO = 1;

	/**
	 * The number of structural features of the '<em>Edge</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EDGE_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>In Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__IN_EDGE = 0;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE__OUT_EDGE = 1;

	/**
	 * The number of structural features of the '<em>Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NODE_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>In Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__IN_EDGE = NODE__IN_EDGE;

	/**
	 * The feature id for the '<em><b>Out Edge</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__OUT_EDGE = NODE__OUT_EDGE;

	/**
	 * The feature id for the '<em><b>Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__TASK = NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER__MAPPINGS = NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Task Wrapper</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_WRAPPER_FEATURE_COUNT = NODE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING__ROLE = 0;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING__PRIORITY = 1;

	/**
	 * The number of structural features of the '<em>Internal Role Task Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_ROLE_TASK_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Tasks</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__TASKS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Default Task</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY__DEFAULT_TASK = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Task Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TASK_REPOSITORY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REPOSITORY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REPOSITORY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REPOSITORY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Utilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REPOSITORY__UTILITIES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Utility Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REPOSITORY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REFERENCE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REFERENCE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REFERENCE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Weight</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REFERENCE__WEIGHT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Utility</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REFERENCE__UTILITY = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Utility Reference</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_REFERENCE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Mode</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE__MODE = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE__EXPRESSION = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE__PARENT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Utility Mode</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION__VARIABLE = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION__PARENT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Entry Point</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION__ENTRY_POINT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Utility Mode Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UTILITY_MODE_EXPRESSION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Synched Transitions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__SYNCHED_TRANSITIONS = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Talk Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__TALK_TIMEOUT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Sync Timeout</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__SYNC_TIMEOUT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Fail On Sync Time Out</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION__FAIL_ON_SYNC_TIME_OUT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Synchronisation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SYNCHRONISATION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__TYPE = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Subplan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__SUBPLAN = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Subvar</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__SUBVAR = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Var</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION__VAR = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Parametrisation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETRISATION_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Plan</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__PLAN = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Activated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN__ACTIVATED = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Annotated Plan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ANNOTATED_PLAN_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__SCOPE = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Sorts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER__SORTS = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Quantifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUANTIFIER_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__ID = QUANTIFIER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__NAME = QUANTIFIER__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__COMMENT = QUANTIFIER__COMMENT;

	/**
	 * The feature id for the '<em><b>Scope</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__SCOPE = QUANTIFIER__SCOPE;

	/**
	 * The feature id for the '<em><b>Sorts</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS__SORTS = QUANTIFIER__SORTS;

	/**
	 * The number of structural features of the '<em>Forall Agents</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FORALL_AGENTS_FEATURE_COUNT = QUANTIFIER_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.CapabilityImpl <em>Capability</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.CapabilityImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCapability()
	 * @generated
	 */
	int CAPABILITY = 43;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Cap Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY__CAP_VALUES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Capability</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.CapValueImpl <em>Cap Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.CapValueImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCapValue()
	 * @generated
	 */
	int CAP_VALUE = 44;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The number of structural features of the '<em>Cap Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAP_VALUE_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.CapabilityDefinitionSetImpl <em>Capability Definition Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.CapabilityDefinitionSetImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCapabilityDefinitionSet()
	 * @generated
	 */
	int CAPABILITY_DEFINITION_SET = 45;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__ID = PLAN_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__NAME = PLAN_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__COMMENT = PLAN_ELEMENT__COMMENT;

	/**
	 * The feature id for the '<em><b>Capabilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET__CAPABILITIES = PLAN_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Capability Definition Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAPABILITY_DEFINITION_SET_FEATURE_COUNT = PLAN_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.impl.PlanningProblemImpl <em>Planning Problem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.impl.PlanningProblemImpl
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlanningProblem()
	 * @generated
	 */
	int PLANNING_PROBLEM = 46;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__ID = ABSTRACT_PLAN__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__NAME = ABSTRACT_PLAN__NAME;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__COMMENT = ABSTRACT_PLAN__COMMENT;

	/**
	 * The feature id for the '<em><b>Rating</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__RATING = ABSTRACT_PLAN__RATING;

	/**
	 * The feature id for the '<em><b>Conditions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__CONDITIONS = ABSTRACT_PLAN__CONDITIONS;

	/**
	 * The feature id for the '<em><b>Master Plan</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__MASTER_PLAN = ABSTRACT_PLAN__MASTER_PLAN;

	/**
	 * The feature id for the '<em><b>Utility Function</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__UTILITY_FUNCTION = ABSTRACT_PLAN__UTILITY_FUNCTION;

	/**
	 * The feature id for the '<em><b>Utilities</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__UTILITIES = ABSTRACT_PLAN__UTILITIES;

	/**
	 * The feature id for the '<em><b>Utility Threshold</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__UTILITY_THRESHOLD = ABSTRACT_PLAN__UTILITY_THRESHOLD;

	/**
	 * The feature id for the '<em><b>Vars</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__VARS = ABSTRACT_PLAN__VARS;

	/**
	 * The feature id for the '<em><b>Plans</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM__PLANS = ABSTRACT_PLAN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Planning Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PLANNING_PROBLEM_FEATURE_COUNT = ABSTRACT_PLAN_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link de.uni_kassel.cn.alica.UtilityModes <em>Utility Modes</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see de.uni_kassel.cn.alica.UtilityModes
	 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityModes()
	 * @generated
	 */
	int UTILITY_MODES = 47;


	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Transition <em>Transition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Transition</em>'.
	 * @see de.uni_kassel.cn.alica.Transition
	 * @generated
	 */
	EClass getTransition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Transition#getMsg <em>Msg</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Msg</em>'.
	 * @see de.uni_kassel.cn.alica.Transition#getMsg()
	 * @see #getTransition()
	 * @generated
	 */
	EAttribute getTransition_Msg();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.cn.alica.Transition#getPreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Pre Condition</em>'.
	 * @see de.uni_kassel.cn.alica.Transition#getPreCondition()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_PreCondition();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Transition#getInState <em>In State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>In State</em>'.
	 * @see de.uni_kassel.cn.alica.Transition#getInState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_InState();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Transition#getOutState <em>Out State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Out State</em>'.
	 * @see de.uni_kassel.cn.alica.Transition#getOutState()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_OutState();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Transition#getSynchronisation <em>Synchronisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Synchronisation</em>'.
	 * @see de.uni_kassel.cn.alica.Transition#getSynchronisation()
	 * @see #getTransition()
	 * @generated
	 */
	EReference getTransition_Synchronisation();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see de.uni_kassel.cn.alica.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Condition#getConditionString <em>Condition String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition String</em>'.
	 * @see de.uni_kassel.cn.alica.Condition#getConditionString()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_ConditionString();

	/**
	 * Returns the meta object for the container reference '{@link de.uni_kassel.cn.alica.Condition#getAbstractPlan <em>Abstract Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Abstract Plan</em>'.
	 * @see de.uni_kassel.cn.alica.Condition#getAbstractPlan()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_AbstractPlan();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.Condition#getVars <em>Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Vars</em>'.
	 * @see de.uni_kassel.cn.alica.Condition#getVars()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Vars();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Condition#getQuantifiers <em>Quantifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Quantifiers</em>'.
	 * @see de.uni_kassel.cn.alica.Condition#getQuantifiers()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Quantifiers();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.PreCondition <em>Pre Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Pre Condition</em>'.
	 * @see de.uni_kassel.cn.alica.PreCondition
	 * @generated
	 */
	EClass getPreCondition();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.PreCondition#isEnabled <em>Enabled</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Enabled</em>'.
	 * @see de.uni_kassel.cn.alica.PreCondition#isEnabled()
	 * @see #getPreCondition()
	 * @generated
	 */
	EAttribute getPreCondition_Enabled();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.EntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry Point</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint
	 * @generated
	 */
	EClass getEntryPoint();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.EntryPoint#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Task</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint#getTask()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EReference getEntryPoint_Task();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.EntryPoint#isSuccessRequired <em>Success Required</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Success Required</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint#isSuccessRequired()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EAttribute getEntryPoint_SuccessRequired();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.EntryPoint#getState <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>State</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint#getState()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EReference getEntryPoint_State();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.EntryPoint#getMinCardinality <em>Min Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Cardinality</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint#getMinCardinality()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EAttribute getEntryPoint_MinCardinality();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.EntryPoint#getMaxCardinality <em>Max Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Cardinality</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint#getMaxCardinality()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EAttribute getEntryPoint_MaxCardinality();

	/**
	 * Returns the meta object for the container reference '{@link de.uni_kassel.cn.alica.EntryPoint#getPlan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Plan</em>'.
	 * @see de.uni_kassel.cn.alica.EntryPoint#getPlan()
	 * @see #getEntryPoint()
	 * @generated
	 */
	EReference getEntryPoint_Plan();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.TerminalState <em>Terminal State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Terminal State</em>'.
	 * @see de.uni_kassel.cn.alica.TerminalState
	 * @generated
	 */
	EClass getTerminalState();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.cn.alica.TerminalState#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Result</em>'.
	 * @see de.uni_kassel.cn.alica.TerminalState#getResult()
	 * @see #getTerminalState()
	 * @generated
	 */
	EReference getTerminalState_Result();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.SuccessState <em>Success State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Success State</em>'.
	 * @see de.uni_kassel.cn.alica.SuccessState
	 * @generated
	 */
	EClass getSuccessState();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.FailureState <em>Failure State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Failure State</em>'.
	 * @see de.uni_kassel.cn.alica.FailureState
	 * @generated
	 */
	EClass getFailureState();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.AbstractPlan <em>Abstract Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Plan</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan
	 * @generated
	 */
	EClass getAbstractPlan();

	/**
	 * Returns the meta object for the containment reference '{@link de.uni_kassel.cn.alica.AbstractPlan#getRating <em>Rating</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rating</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getRating()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EReference getAbstractPlan_Rating();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.AbstractPlan#getConditions <em>Conditions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Conditions</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getConditions()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EReference getAbstractPlan_Conditions();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.AbstractPlan#isMasterPlan <em>Master Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Master Plan</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#isMasterPlan()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EAttribute getAbstractPlan_MasterPlan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilityFunction <em>Utility Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Utility Function</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getUtilityFunction()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EAttribute getAbstractPlan_UtilityFunction();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilities <em>Utilities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Utilities</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getUtilities()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EReference getAbstractPlan_Utilities();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.AbstractPlan#getUtilityThreshold <em>Utility Threshold</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Utility Threshold</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getUtilityThreshold()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EAttribute getAbstractPlan_UtilityThreshold();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.AbstractPlan#getVars <em>Vars</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vars</em>'.
	 * @see de.uni_kassel.cn.alica.AbstractPlan#getVars()
	 * @see #getAbstractPlan()
	 * @generated
	 */
	EReference getAbstractPlan_Vars();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Behaviour <em>Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Behaviour</em>'.
	 * @see de.uni_kassel.cn.alica.Behaviour
	 * @generated
	 */
	EClass getBehaviour();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Behaviour#getConfigurations <em>Configurations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Configurations</em>'.
	 * @see de.uni_kassel.cn.alica.Behaviour#getConfigurations()
	 * @see #getBehaviour()
	 * @generated
	 */
	EReference getBehaviour_Configurations();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.State <em>State</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>State</em>'.
	 * @see de.uni_kassel.cn.alica.State
	 * @generated
	 */
	EClass getState();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.State#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.cn.alica.State#getPlans()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Plans();

	/**
	 * Returns the meta object for the container reference '{@link de.uni_kassel.cn.alica.State#getInPlan <em>In Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>In Plan</em>'.
	 * @see de.uni_kassel.cn.alica.State#getInPlan()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_InPlan();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.State#getParametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parametrisation</em>'.
	 * @see de.uni_kassel.cn.alica.State#getParametrisation()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_Parametrisation();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.State#getInTransitions <em>In Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Transitions</em>'.
	 * @see de.uni_kassel.cn.alica.State#getInTransitions()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_InTransitions();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.State#getOutTransitions <em>Out Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Transitions</em>'.
	 * @see de.uni_kassel.cn.alica.State#getOutTransitions()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_OutTransitions();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.State#getEntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry Point</em>'.
	 * @see de.uni_kassel.cn.alica.State#getEntryPoint()
	 * @see #getState()
	 * @generated
	 */
	EReference getState_EntryPoint();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Plan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan</em>'.
	 * @see de.uni_kassel.cn.alica.Plan
	 * @generated
	 */
	EClass getPlan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Plan#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getPriority()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_Priority();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Plan#getStates <em>States</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>States</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getStates()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_States();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Plan#getTransitions <em>Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Transitions</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getTransitions()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Transitions();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Plan#getMinCardinality <em>Min Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Cardinality</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getMinCardinality()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_MinCardinality();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Plan#getMaxCardinality <em>Max Cardinality</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Cardinality</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getMaxCardinality()
	 * @see #getPlan()
	 * @generated
	 */
	EAttribute getPlan_MaxCardinality();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Plan#getSynchronisations <em>Synchronisations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Synchronisations</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getSynchronisations()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_Synchronisations();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Plan#getEntryPoints <em>Entry Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entry Points</em>'.
	 * @see de.uni_kassel.cn.alica.Plan#getEntryPoints()
	 * @see #getPlan()
	 * @generated
	 */
	EReference getPlan_EntryPoints();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Utility <em>Utility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Utility</em>'.
	 * @see de.uni_kassel.cn.alica.Utility
	 * @generated
	 */
	EClass getUtility();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Utility#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expression</em>'.
	 * @see de.uni_kassel.cn.alica.Utility#getExpression()
	 * @see #getUtility()
	 * @generated
	 */
	EAttribute getUtility_Expression();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Utility#getModes <em>Modes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Modes</em>'.
	 * @see de.uni_kassel.cn.alica.Utility#getModes()
	 * @see #getUtility()
	 * @generated
	 */
	EReference getUtility_Modes();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.PlanType <em>Plan Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan Type</em>'.
	 * @see de.uni_kassel.cn.alica.PlanType
	 * @generated
	 */
	EClass getPlanType();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.PlanType#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.cn.alica.PlanType#getPlans()
	 * @see #getPlanType()
	 * @generated
	 */
	EReference getPlanType_Plans();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.PlanType#getParametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parametrisation</em>'.
	 * @see de.uni_kassel.cn.alica.PlanType#getParametrisation()
	 * @see #getPlanType()
	 * @generated
	 */
	EReference getPlanType_Parametrisation();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Rating <em>Rating</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rating</em>'.
	 * @see de.uni_kassel.cn.alica.Rating
	 * @generated
	 */
	EClass getRating();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Result <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Result</em>'.
	 * @see de.uni_kassel.cn.alica.Result
	 * @generated
	 */
	EClass getResult();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.RuntimeCondition <em>Runtime Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Runtime Condition</em>'.
	 * @see de.uni_kassel.cn.alica.RuntimeCondition
	 * @generated
	 */
	EClass getRuntimeCondition();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.PlanElement <em>Plan Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Plan Element</em>'.
	 * @see de.uni_kassel.cn.alica.PlanElement
	 * @generated
	 */
	EClass getPlanElement();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.PlanElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see de.uni_kassel.cn.alica.PlanElement#getId()
	 * @see #getPlanElement()
	 * @generated
	 */
	EAttribute getPlanElement_Id();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.PlanElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see de.uni_kassel.cn.alica.PlanElement#getName()
	 * @see #getPlanElement()
	 * @generated
	 */
	EAttribute getPlanElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.PlanElement#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see de.uni_kassel.cn.alica.PlanElement#getComment()
	 * @see #getPlanElement()
	 * @generated
	 */
	EAttribute getPlanElement_Comment();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Task <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task</em>'.
	 * @see de.uni_kassel.cn.alica.Task
	 * @generated
	 */
	EClass getTask();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Task#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see de.uni_kassel.cn.alica.Task#getDescription()
	 * @see #getTask()
	 * @generated
	 */
	EAttribute getTask_Description();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>EString To EString Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>EString To EString Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDefault="" keyDataType="org.eclipse.emf.ecore.EString"
	 *        valueDefault="" valueDataType="org.eclipse.emf.ecore.EString"
	 * @generated
	 */
	EClass getEStringToEStringMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEStringMapEntry()
	 * @generated
	 */
	EAttribute getEStringToEStringMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getEStringToEStringMapEntry()
	 * @generated
	 */
	EAttribute getEStringToEStringMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.BehaviourConfiguration <em>Behaviour Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Behaviour Configuration</em>'.
	 * @see de.uni_kassel.cn.alica.BehaviourConfiguration
	 * @generated
	 */
	EClass getBehaviourConfiguration();

	/**
	 * Returns the meta object for the map '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Parameters</em>'.
	 * @see de.uni_kassel.cn.alica.BehaviourConfiguration#getParameters()
	 * @see #getBehaviourConfiguration()
	 * @generated
	 */
	EReference getBehaviourConfiguration_Parameters();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getDeferring <em>Deferring</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Deferring</em>'.
	 * @see de.uni_kassel.cn.alica.BehaviourConfiguration#getDeferring()
	 * @see #getBehaviourConfiguration()
	 * @generated
	 */
	EAttribute getBehaviourConfiguration_Deferring();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getFrequency <em>Frequency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Frequency</em>'.
	 * @see de.uni_kassel.cn.alica.BehaviourConfiguration#getFrequency()
	 * @see #getBehaviourConfiguration()
	 * @generated
	 */
	EAttribute getBehaviourConfiguration_Frequency();

	/**
	 * Returns the meta object for the container reference '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#getBehaviour <em>Behaviour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the container reference '<em>Behaviour</em>'.
	 * @see de.uni_kassel.cn.alica.BehaviourConfiguration#getBehaviour()
	 * @see #getBehaviourConfiguration()
	 * @generated
	 */
	EReference getBehaviourConfiguration_Behaviour();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.BehaviourConfiguration#isEventDriven <em>Event Driven</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Event Driven</em>'.
	 * @see de.uni_kassel.cn.alica.BehaviourConfiguration#isEventDriven()
	 * @see #getBehaviourConfiguration()
	 * @generated
	 */
	EAttribute getBehaviourConfiguration_EventDriven();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role</em>'.
	 * @see de.uni_kassel.cn.alica.Role
	 * @generated
	 */
	EClass getRole();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Role#getCharacteristics <em>Characteristics</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Characteristics</em>'.
	 * @see de.uni_kassel.cn.alica.Role#getCharacteristics()
	 * @see #getRole()
	 * @generated
	 */
	EReference getRole_Characteristics();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.RoleSet <em>Role Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Set</em>'.
	 * @see de.uni_kassel.cn.alica.RoleSet
	 * @generated
	 */
	EClass getRoleSet();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.RoleSet#getUsableWithPlanID <em>Usable With Plan ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Usable With Plan ID</em>'.
	 * @see de.uni_kassel.cn.alica.RoleSet#getUsableWithPlanID()
	 * @see #getRoleSet()
	 * @generated
	 */
	EAttribute getRoleSet_UsableWithPlanID();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.RoleSet#isDefault <em>Default</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Default</em>'.
	 * @see de.uni_kassel.cn.alica.RoleSet#isDefault()
	 * @see #getRoleSet()
	 * @generated
	 */
	EAttribute getRoleSet_Default();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.RoleSet#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see de.uni_kassel.cn.alica.RoleSet#getMappings()
	 * @see #getRoleSet()
	 * @generated
	 */
	EReference getRoleSet_Mappings();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>ELong To Double Map Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ELong To Double Map Entry</em>'.
	 * @see java.util.Map.Entry
	 * @model keyDataType="org.eclipse.emf.ecore.ELongObject"
	 *        valueDataType="org.eclipse.emf.ecore.EDoubleObject"
	 * @generated
	 */
	EClass getELongToDoubleMapEntry();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getELongToDoubleMapEntry()
	 * @generated
	 */
	EAttribute getELongToDoubleMapEntry_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getELongToDoubleMapEntry()
	 * @generated
	 */
	EAttribute getELongToDoubleMapEntry_Value();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.RoleDefinitionSet <em>Role Definition Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Definition Set</em>'.
	 * @see de.uni_kassel.cn.alica.RoleDefinitionSet
	 * @generated
	 */
	EClass getRoleDefinitionSet();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.RoleDefinitionSet#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Roles</em>'.
	 * @see de.uni_kassel.cn.alica.RoleDefinitionSet#getRoles()
	 * @see #getRoleDefinitionSet()
	 * @generated
	 */
	EReference getRoleDefinitionSet_Roles();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.RoleTaskMapping <em>Role Task Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Task Mapping</em>'.
	 * @see de.uni_kassel.cn.alica.RoleTaskMapping
	 * @generated
	 */
	EClass getRoleTaskMapping();

	/**
	 * Returns the meta object for the map '{@link de.uni_kassel.cn.alica.RoleTaskMapping#getTaskPriorities <em>Task Priorities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the map '<em>Task Priorities</em>'.
	 * @see de.uni_kassel.cn.alica.RoleTaskMapping#getTaskPriorities()
	 * @see #getRoleTaskMapping()
	 * @generated
	 */
	EReference getRoleTaskMapping_TaskPriorities();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.RoleTaskMapping#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Role</em>'.
	 * @see de.uni_kassel.cn.alica.RoleTaskMapping#getRole()
	 * @see #getRoleTaskMapping()
	 * @generated
	 */
	EReference getRoleTaskMapping_Role();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Characteristic <em>Characteristic</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Characteristic</em>'.
	 * @see de.uni_kassel.cn.alica.Characteristic
	 * @generated
	 */
	EClass getCharacteristic();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Characteristic#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see de.uni_kassel.cn.alica.Characteristic#getValue()
	 * @see #getCharacteristic()
	 * @generated
	 */
	EReference getCharacteristic_Value();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Characteristic#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see de.uni_kassel.cn.alica.Characteristic#getWeight()
	 * @see #getCharacteristic()
	 * @generated
	 */
	EAttribute getCharacteristic_Weight();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Characteristic#getCapability <em>Capability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Capability</em>'.
	 * @see de.uni_kassel.cn.alica.Characteristic#getCapability()
	 * @see #getCharacteristic()
	 * @generated
	 */
	EReference getCharacteristic_Capability();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.TaskGraph <em>Task Graph</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Graph</em>'.
	 * @see de.uni_kassel.cn.alica.TaskGraph
	 * @generated
	 */
	EClass getTaskGraph();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.TaskGraph#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see de.uni_kassel.cn.alica.TaskGraph#getNodes()
	 * @see #getTaskGraph()
	 * @generated
	 */
	EReference getTaskGraph_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.TaskGraph#getEdges <em>Edges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Edges</em>'.
	 * @see de.uni_kassel.cn.alica.TaskGraph#getEdges()
	 * @see #getTaskGraph()
	 * @generated
	 */
	EReference getTaskGraph_Edges();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Edge <em>Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Edge</em>'.
	 * @see de.uni_kassel.cn.alica.Edge
	 * @generated
	 */
	EClass getEdge();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Edge#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>From</em>'.
	 * @see de.uni_kassel.cn.alica.Edge#getFrom()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_From();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Edge#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To</em>'.
	 * @see de.uni_kassel.cn.alica.Edge#getTo()
	 * @see #getEdge()
	 * @generated
	 */
	EReference getEdge_To();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.TaskWrapper <em>Task Wrapper</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Wrapper</em>'.
	 * @see de.uni_kassel.cn.alica.TaskWrapper
	 * @generated
	 */
	EClass getTaskWrapper();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.TaskWrapper#getTask <em>Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Task</em>'.
	 * @see de.uni_kassel.cn.alica.TaskWrapper#getTask()
	 * @see #getTaskWrapper()
	 * @generated
	 */
	EReference getTaskWrapper_Task();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.TaskWrapper#getMappings <em>Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Mappings</em>'.
	 * @see de.uni_kassel.cn.alica.TaskWrapper#getMappings()
	 * @see #getTaskWrapper()
	 * @generated
	 */
	EReference getTaskWrapper_Mappings();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.InternalRoleTaskMapping <em>Internal Role Task Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Role Task Mapping</em>'.
	 * @see de.uni_kassel.cn.alica.InternalRoleTaskMapping
	 * @generated
	 */
	EClass getInternalRoleTaskMapping();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.InternalRoleTaskMapping#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Role</em>'.
	 * @see de.uni_kassel.cn.alica.InternalRoleTaskMapping#getRole()
	 * @see #getInternalRoleTaskMapping()
	 * @generated
	 */
	EReference getInternalRoleTaskMapping_Role();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.InternalRoleTaskMapping#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see de.uni_kassel.cn.alica.InternalRoleTaskMapping#getPriority()
	 * @see #getInternalRoleTaskMapping()
	 * @generated
	 */
	EAttribute getInternalRoleTaskMapping_Priority();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Node <em>Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Node</em>'.
	 * @see de.uni_kassel.cn.alica.Node
	 * @generated
	 */
	EClass getNode();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.Node#getInEdge <em>In Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>In Edge</em>'.
	 * @see de.uni_kassel.cn.alica.Node#getInEdge()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_InEdge();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.Node#getOutEdge <em>Out Edge</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Out Edge</em>'.
	 * @see de.uni_kassel.cn.alica.Node#getOutEdge()
	 * @see #getNode()
	 * @generated
	 */
	EReference getNode_OutEdge();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.TaskRepository <em>Task Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Task Repository</em>'.
	 * @see de.uni_kassel.cn.alica.TaskRepository
	 * @generated
	 */
	EClass getTaskRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.TaskRepository#getTasks <em>Tasks</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Tasks</em>'.
	 * @see de.uni_kassel.cn.alica.TaskRepository#getTasks()
	 * @see #getTaskRepository()
	 * @generated
	 */
	EReference getTaskRepository_Tasks();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.TaskRepository#getDefaultTask <em>Default Task</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Default Task</em>'.
	 * @see de.uni_kassel.cn.alica.TaskRepository#getDefaultTask()
	 * @see #getTaskRepository()
	 * @generated
	 */
	EReference getTaskRepository_DefaultTask();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.UtilityRepository <em>Utility Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Utility Repository</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityRepository
	 * @generated
	 */
	EClass getUtilityRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.UtilityRepository#getUtilities <em>Utilities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Utilities</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityRepository#getUtilities()
	 * @see #getUtilityRepository()
	 * @generated
	 */
	EReference getUtilityRepository_Utilities();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.UtilityReference <em>Utility Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Utility Reference</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityReference
	 * @generated
	 */
	EClass getUtilityReference();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.UtilityReference#getWeight <em>Weight</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Weight</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityReference#getWeight()
	 * @see #getUtilityReference()
	 * @generated
	 */
	EAttribute getUtilityReference_Weight();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.UtilityReference#getUtility <em>Utility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Utility</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityReference#getUtility()
	 * @see #getUtilityReference()
	 * @generated
	 */
	EReference getUtilityReference_Utility();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.UtilityMode <em>Utility Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Utility Mode</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityMode
	 * @generated
	 */
	EClass getUtilityMode();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.UtilityMode#getMode <em>Mode</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mode</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityMode#getMode()
	 * @see #getUtilityMode()
	 * @generated
	 */
	EAttribute getUtilityMode_Mode();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.UtilityMode#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expression</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityMode#getExpression()
	 * @see #getUtilityMode()
	 * @generated
	 */
	EReference getUtilityMode_Expression();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.UtilityMode#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityMode#getParent()
	 * @see #getUtilityMode()
	 * @generated
	 */
	EReference getUtilityMode_Parent();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.UtilityModeExpression <em>Utility Mode Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Utility Mode Expression</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityModeExpression
	 * @generated
	 */
	EClass getUtilityModeExpression();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.UtilityModeExpression#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityModeExpression#getVariable()
	 * @see #getUtilityModeExpression()
	 * @generated
	 */
	EAttribute getUtilityModeExpression_Variable();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.UtilityModeExpression#getParent <em>Parent</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parent</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityModeExpression#getParent()
	 * @see #getUtilityModeExpression()
	 * @generated
	 */
	EReference getUtilityModeExpression_Parent();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.UtilityModeExpression#getEntryPoint <em>Entry Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Entry Point</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityModeExpression#getEntryPoint()
	 * @see #getUtilityModeExpression()
	 * @generated
	 */
	EReference getUtilityModeExpression_EntryPoint();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Synchronisation <em>Synchronisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Synchronisation</em>'.
	 * @see de.uni_kassel.cn.alica.Synchronisation
	 * @generated
	 */
	EClass getSynchronisation();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.Synchronisation#getSynchedTransitions <em>Synched Transitions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Synched Transitions</em>'.
	 * @see de.uni_kassel.cn.alica.Synchronisation#getSynchedTransitions()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EReference getSynchronisation_SynchedTransitions();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Synchronisation#getTalkTimeout <em>Talk Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Talk Timeout</em>'.
	 * @see de.uni_kassel.cn.alica.Synchronisation#getTalkTimeout()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EAttribute getSynchronisation_TalkTimeout();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Synchronisation#getSyncTimeout <em>Sync Timeout</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sync Timeout</em>'.
	 * @see de.uni_kassel.cn.alica.Synchronisation#getSyncTimeout()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EAttribute getSynchronisation_SyncTimeout();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Synchronisation#isFailOnSyncTimeOut <em>Fail On Sync Time Out</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Fail On Sync Time Out</em>'.
	 * @see de.uni_kassel.cn.alica.Synchronisation#isFailOnSyncTimeOut()
	 * @see #getSynchronisation()
	 * @generated
	 */
	EAttribute getSynchronisation_FailOnSyncTimeOut();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see de.uni_kassel.cn.alica.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.Variable#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see de.uni_kassel.cn.alica.Variable#getType()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_Type();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Parametrisation <em>Parametrisation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parametrisation</em>'.
	 * @see de.uni_kassel.cn.alica.Parametrisation
	 * @generated
	 */
	EClass getParametrisation();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Parametrisation#getSubplan <em>Subplan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subplan</em>'.
	 * @see de.uni_kassel.cn.alica.Parametrisation#getSubplan()
	 * @see #getParametrisation()
	 * @generated
	 */
	EReference getParametrisation_Subplan();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Parametrisation#getSubvar <em>Subvar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Subvar</em>'.
	 * @see de.uni_kassel.cn.alica.Parametrisation#getSubvar()
	 * @see #getParametrisation()
	 * @generated
	 */
	EReference getParametrisation_Subvar();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Parametrisation#getVar <em>Var</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Var</em>'.
	 * @see de.uni_kassel.cn.alica.Parametrisation#getVar()
	 * @see #getParametrisation()
	 * @generated
	 */
	EReference getParametrisation_Var();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.AnnotatedPlan <em>Annotated Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Annotated Plan</em>'.
	 * @see de.uni_kassel.cn.alica.AnnotatedPlan
	 * @generated
	 */
	EClass getAnnotatedPlan();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.AnnotatedPlan#getPlan <em>Plan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Plan</em>'.
	 * @see de.uni_kassel.cn.alica.AnnotatedPlan#getPlan()
	 * @see #getAnnotatedPlan()
	 * @generated
	 */
	EReference getAnnotatedPlan_Plan();

	/**
	 * Returns the meta object for the attribute '{@link de.uni_kassel.cn.alica.AnnotatedPlan#isActivated <em>Activated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Activated</em>'.
	 * @see de.uni_kassel.cn.alica.AnnotatedPlan#isActivated()
	 * @see #getAnnotatedPlan()
	 * @generated
	 */
	EAttribute getAnnotatedPlan_Activated();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Quantifier <em>Quantifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Quantifier</em>'.
	 * @see de.uni_kassel.cn.alica.Quantifier
	 * @generated
	 */
	EClass getQuantifier();

	/**
	 * Returns the meta object for the reference '{@link de.uni_kassel.cn.alica.Quantifier#getScope <em>Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Scope</em>'.
	 * @see de.uni_kassel.cn.alica.Quantifier#getScope()
	 * @see #getQuantifier()
	 * @generated
	 */
	EReference getQuantifier_Scope();

	/**
	 * Returns the meta object for the attribute list '{@link de.uni_kassel.cn.alica.Quantifier#getSorts <em>Sorts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Sorts</em>'.
	 * @see de.uni_kassel.cn.alica.Quantifier#getSorts()
	 * @see #getQuantifier()
	 * @generated
	 */
	EAttribute getQuantifier_Sorts();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.ForallAgents <em>Forall Agents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Forall Agents</em>'.
	 * @see de.uni_kassel.cn.alica.ForallAgents
	 * @generated
	 */
	EClass getForallAgents();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.IInhabitable <em>IInhabitable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IInhabitable</em>'.
	 * @see de.uni_kassel.cn.alica.IInhabitable
	 * @generated
	 */
	EClass getIInhabitable();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.Capability <em>Capability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Capability</em>'.
	 * @see de.uni_kassel.cn.alica.Capability
	 * @generated
	 */
	EClass getCapability();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.Capability#getCapValues <em>Cap Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cap Values</em>'.
	 * @see de.uni_kassel.cn.alica.Capability#getCapValues()
	 * @see #getCapability()
	 * @generated
	 */
	EReference getCapability_CapValues();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.CapValue <em>Cap Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cap Value</em>'.
	 * @see de.uni_kassel.cn.alica.CapValue
	 * @generated
	 */
	EClass getCapValue();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.CapabilityDefinitionSet <em>Capability Definition Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Capability Definition Set</em>'.
	 * @see de.uni_kassel.cn.alica.CapabilityDefinitionSet
	 * @generated
	 */
	EClass getCapabilityDefinitionSet();

	/**
	 * Returns the meta object for the containment reference list '{@link de.uni_kassel.cn.alica.CapabilityDefinitionSet#getCapabilities <em>Capabilities</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Capabilities</em>'.
	 * @see de.uni_kassel.cn.alica.CapabilityDefinitionSet#getCapabilities()
	 * @see #getCapabilityDefinitionSet()
	 * @generated
	 */
	EReference getCapabilityDefinitionSet_Capabilities();

	/**
	 * Returns the meta object for class '{@link de.uni_kassel.cn.alica.PlanningProblem <em>Planning Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Planning Problem</em>'.
	 * @see de.uni_kassel.cn.alica.PlanningProblem
	 * @generated
	 */
	EClass getPlanningProblem();

	/**
	 * Returns the meta object for the reference list '{@link de.uni_kassel.cn.alica.PlanningProblem#getPlans <em>Plans</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Plans</em>'.
	 * @see de.uni_kassel.cn.alica.PlanningProblem#getPlans()
	 * @see #getPlanningProblem()
	 * @generated
	 */
	EReference getPlanningProblem_Plans();

	/**
	 * Returns the meta object for enum '{@link de.uni_kassel.cn.alica.UtilityModes <em>Utility Modes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Utility Modes</em>'.
	 * @see de.uni_kassel.cn.alica.UtilityModes
	 * @generated
	 */
	EEnum getUtilityModes();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	AlicaFactory getAlicaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.TransitionImpl <em>Transition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.TransitionImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTransition()
		 * @generated
		 */
		EClass TRANSITION = eINSTANCE.getTransition();

		/**
		 * The meta object literal for the '<em><b>Msg</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRANSITION__MSG = eINSTANCE.getTransition_Msg();

		/**
		 * The meta object literal for the '<em><b>Pre Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__PRE_CONDITION = eINSTANCE.getTransition_PreCondition();

		/**
		 * The meta object literal for the '<em><b>In State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__IN_STATE = eINSTANCE.getTransition_InState();

		/**
		 * The meta object literal for the '<em><b>Out State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__OUT_STATE = eINSTANCE.getTransition_OutState();

		/**
		 * The meta object literal for the '<em><b>Synchronisation</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TRANSITION__SYNCHRONISATION = eINSTANCE.getTransition_Synchronisation();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.ConditionImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '<em><b>Condition String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__CONDITION_STRING = eINSTANCE.getCondition_ConditionString();

		/**
		 * The meta object literal for the '<em><b>Abstract Plan</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__ABSTRACT_PLAN = eINSTANCE.getCondition_AbstractPlan();

		/**
		 * The meta object literal for the '<em><b>Vars</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__VARS = eINSTANCE.getCondition_Vars();

		/**
		 * The meta object literal for the '<em><b>Quantifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__QUANTIFIERS = eINSTANCE.getCondition_Quantifiers();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.PreConditionImpl <em>Pre Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.PreConditionImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPreCondition()
		 * @generated
		 */
		EClass PRE_CONDITION = eINSTANCE.getPreCondition();

		/**
		 * The meta object literal for the '<em><b>Enabled</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PRE_CONDITION__ENABLED = eINSTANCE.getPreCondition_Enabled();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.EntryPointImpl <em>Entry Point</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.EntryPointImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getEntryPoint()
		 * @generated
		 */
		EClass ENTRY_POINT = eINSTANCE.getEntryPoint();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRY_POINT__TASK = eINSTANCE.getEntryPoint_Task();

		/**
		 * The meta object literal for the '<em><b>Success Required</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_POINT__SUCCESS_REQUIRED = eINSTANCE.getEntryPoint_SuccessRequired();

		/**
		 * The meta object literal for the '<em><b>State</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRY_POINT__STATE = eINSTANCE.getEntryPoint_State();

		/**
		 * The meta object literal for the '<em><b>Min Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_POINT__MIN_CARDINALITY = eINSTANCE.getEntryPoint_MinCardinality();

		/**
		 * The meta object literal for the '<em><b>Max Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENTRY_POINT__MAX_CARDINALITY = eINSTANCE.getEntryPoint_MaxCardinality();

		/**
		 * The meta object literal for the '<em><b>Plan</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENTRY_POINT__PLAN = eINSTANCE.getEntryPoint_Plan();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.TerminalStateImpl <em>Terminal State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.TerminalStateImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTerminalState()
		 * @generated
		 */
		EClass TERMINAL_STATE = eINSTANCE.getTerminalState();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERMINAL_STATE__RESULT = eINSTANCE.getTerminalState_Result();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.SuccessStateImpl <em>Success State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.SuccessStateImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getSuccessState()
		 * @generated
		 */
		EClass SUCCESS_STATE = eINSTANCE.getSuccessState();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.FailureStateImpl <em>Failure State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.FailureStateImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getFailureState()
		 * @generated
		 */
		EClass FAILURE_STATE = eINSTANCE.getFailureState();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.AbstractPlanImpl <em>Abstract Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.AbstractPlanImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getAbstractPlan()
		 * @generated
		 */
		EClass ABSTRACT_PLAN = eINSTANCE.getAbstractPlan();

		/**
		 * The meta object literal for the '<em><b>Rating</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_PLAN__RATING = eINSTANCE.getAbstractPlan_Rating();

		/**
		 * The meta object literal for the '<em><b>Conditions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_PLAN__CONDITIONS = eINSTANCE.getAbstractPlan_Conditions();

		/**
		 * The meta object literal for the '<em><b>Master Plan</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PLAN__MASTER_PLAN = eINSTANCE.getAbstractPlan_MasterPlan();

		/**
		 * The meta object literal for the '<em><b>Utility Function</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PLAN__UTILITY_FUNCTION = eINSTANCE.getAbstractPlan_UtilityFunction();

		/**
		 * The meta object literal for the '<em><b>Utilities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_PLAN__UTILITIES = eINSTANCE.getAbstractPlan_Utilities();

		/**
		 * The meta object literal for the '<em><b>Utility Threshold</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_PLAN__UTILITY_THRESHOLD = eINSTANCE.getAbstractPlan_UtilityThreshold();

		/**
		 * The meta object literal for the '<em><b>Vars</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_PLAN__VARS = eINSTANCE.getAbstractPlan_Vars();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.BehaviourImpl <em>Behaviour</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.BehaviourImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getBehaviour()
		 * @generated
		 */
		EClass BEHAVIOUR = eINSTANCE.getBehaviour();

		/**
		 * The meta object literal for the '<em><b>Configurations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR__CONFIGURATIONS = eINSTANCE.getBehaviour_Configurations();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.StateImpl <em>State</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.StateImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getState()
		 * @generated
		 */
		EClass STATE = eINSTANCE.getState();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PLANS = eINSTANCE.getState_Plans();

		/**
		 * The meta object literal for the '<em><b>In Plan</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__IN_PLAN = eINSTANCE.getState_InPlan();

		/**
		 * The meta object literal for the '<em><b>Parametrisation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__PARAMETRISATION = eINSTANCE.getState_Parametrisation();

		/**
		 * The meta object literal for the '<em><b>In Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__IN_TRANSITIONS = eINSTANCE.getState_InTransitions();

		/**
		 * The meta object literal for the '<em><b>Out Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__OUT_TRANSITIONS = eINSTANCE.getState_OutTransitions();

		/**
		 * The meta object literal for the '<em><b>Entry Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STATE__ENTRY_POINT = eINSTANCE.getState_EntryPoint();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.PlanImpl <em>Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.PlanImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlan()
		 * @generated
		 */
		EClass PLAN = eINSTANCE.getPlan();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__PRIORITY = eINSTANCE.getPlan_Priority();

		/**
		 * The meta object literal for the '<em><b>States</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__STATES = eINSTANCE.getPlan_States();

		/**
		 * The meta object literal for the '<em><b>Transitions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__TRANSITIONS = eINSTANCE.getPlan_Transitions();

		/**
		 * The meta object literal for the '<em><b>Min Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__MIN_CARDINALITY = eINSTANCE.getPlan_MinCardinality();

		/**
		 * The meta object literal for the '<em><b>Max Cardinality</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN__MAX_CARDINALITY = eINSTANCE.getPlan_MaxCardinality();

		/**
		 * The meta object literal for the '<em><b>Synchronisations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__SYNCHRONISATIONS = eINSTANCE.getPlan_Synchronisations();

		/**
		 * The meta object literal for the '<em><b>Entry Points</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN__ENTRY_POINTS = eINSTANCE.getPlan_EntryPoints();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.UtilityImpl <em>Utility</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.UtilityImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtility()
		 * @generated
		 */
		EClass UTILITY = eINSTANCE.getUtility();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UTILITY__EXPRESSION = eINSTANCE.getUtility_Expression();

		/**
		 * The meta object literal for the '<em><b>Modes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY__MODES = eINSTANCE.getUtility_Modes();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.PlanTypeImpl <em>Plan Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.PlanTypeImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlanType()
		 * @generated
		 */
		EClass PLAN_TYPE = eINSTANCE.getPlanType();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN_TYPE__PLANS = eINSTANCE.getPlanType_Plans();

		/**
		 * The meta object literal for the '<em><b>Parametrisation</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLAN_TYPE__PARAMETRISATION = eINSTANCE.getPlanType_Parametrisation();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.RatingImpl <em>Rating</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.RatingImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRating()
		 * @generated
		 */
		EClass RATING = eINSTANCE.getRating();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.ResultImpl <em>Result</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.ResultImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getResult()
		 * @generated
		 */
		EClass RESULT = eINSTANCE.getResult();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.RuntimeConditionImpl <em>Runtime Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.RuntimeConditionImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRuntimeCondition()
		 * @generated
		 */
		EClass RUNTIME_CONDITION = eINSTANCE.getRuntimeCondition();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.PlanElementImpl <em>Plan Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.PlanElementImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlanElement()
		 * @generated
		 */
		EClass PLAN_ELEMENT = eINSTANCE.getPlanElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN_ELEMENT__ID = eINSTANCE.getPlanElement_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN_ELEMENT__NAME = eINSTANCE.getPlanElement_Name();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PLAN_ELEMENT__COMMENT = eINSTANCE.getPlanElement_Comment();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.TaskImpl <em>Task</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.TaskImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTask()
		 * @generated
		 */
		EClass TASK = eINSTANCE.getTask();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TASK__DESCRIPTION = eINSTANCE.getTask_Description();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.EStringToEStringMapEntryImpl <em>EString To EString Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.EStringToEStringMapEntryImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getEStringToEStringMapEntry()
		 * @generated
		 */
		EClass ESTRING_TO_ESTRING_MAP_ENTRY = eINSTANCE.getEStringToEStringMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_ESTRING_MAP_ENTRY__KEY = eINSTANCE.getEStringToEStringMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ESTRING_TO_ESTRING_MAP_ENTRY__VALUE = eINSTANCE.getEStringToEStringMapEntry_Value();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl <em>Behaviour Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.BehaviourConfigurationImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getBehaviourConfiguration()
		 * @generated
		 */
		EClass BEHAVIOUR_CONFIGURATION = eINSTANCE.getBehaviourConfiguration();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR_CONFIGURATION__PARAMETERS = eINSTANCE.getBehaviourConfiguration_Parameters();

		/**
		 * The meta object literal for the '<em><b>Deferring</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEHAVIOUR_CONFIGURATION__DEFERRING = eINSTANCE.getBehaviourConfiguration_Deferring();

		/**
		 * The meta object literal for the '<em><b>Frequency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEHAVIOUR_CONFIGURATION__FREQUENCY = eINSTANCE.getBehaviourConfiguration_Frequency();

		/**
		 * The meta object literal for the '<em><b>Behaviour</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BEHAVIOUR_CONFIGURATION__BEHAVIOUR = eINSTANCE.getBehaviourConfiguration_Behaviour();

		/**
		 * The meta object literal for the '<em><b>Event Driven</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BEHAVIOUR_CONFIGURATION__EVENT_DRIVEN = eINSTANCE.getBehaviourConfiguration_EventDriven();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.RoleImpl <em>Role</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.RoleImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRole()
		 * @generated
		 */
		EClass ROLE = eINSTANCE.getRole();

		/**
		 * The meta object literal for the '<em><b>Characteristics</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE__CHARACTERISTICS = eINSTANCE.getRole_Characteristics();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.RoleSetImpl <em>Role Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.RoleSetImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRoleSet()
		 * @generated
		 */
		EClass ROLE_SET = eINSTANCE.getRoleSet();

		/**
		 * The meta object literal for the '<em><b>Usable With Plan ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_SET__USABLE_WITH_PLAN_ID = eINSTANCE.getRoleSet_UsableWithPlanID();

		/**
		 * The meta object literal for the '<em><b>Default</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_SET__DEFAULT = eINSTANCE.getRoleSet_Default();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_SET__MAPPINGS = eINSTANCE.getRoleSet_Mappings();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.ELongToDoubleMapEntryImpl <em>ELong To Double Map Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.ELongToDoubleMapEntryImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getELongToDoubleMapEntry()
		 * @generated
		 */
		EClass ELONG_TO_DOUBLE_MAP_ENTRY = eINSTANCE.getELongToDoubleMapEntry();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELONG_TO_DOUBLE_MAP_ENTRY__KEY = eINSTANCE.getELongToDoubleMapEntry_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELONG_TO_DOUBLE_MAP_ENTRY__VALUE = eINSTANCE.getELongToDoubleMapEntry_Value();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.RoleDefinitionSetImpl <em>Role Definition Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.RoleDefinitionSetImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRoleDefinitionSet()
		 * @generated
		 */
		EClass ROLE_DEFINITION_SET = eINSTANCE.getRoleDefinitionSet();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_DEFINITION_SET__ROLES = eINSTANCE.getRoleDefinitionSet_Roles();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.RoleTaskMappingImpl <em>Role Task Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.RoleTaskMappingImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getRoleTaskMapping()
		 * @generated
		 */
		EClass ROLE_TASK_MAPPING = eINSTANCE.getRoleTaskMapping();

		/**
		 * The meta object literal for the '<em><b>Task Priorities</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_TASK_MAPPING__TASK_PRIORITIES = eINSTANCE.getRoleTaskMapping_TaskPriorities();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_TASK_MAPPING__ROLE = eINSTANCE.getRoleTaskMapping_Role();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.CharacteristicImpl <em>Characteristic</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.CharacteristicImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCharacteristic()
		 * @generated
		 */
		EClass CHARACTERISTIC = eINSTANCE.getCharacteristic();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHARACTERISTIC__VALUE = eINSTANCE.getCharacteristic_Value();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHARACTERISTIC__WEIGHT = eINSTANCE.getCharacteristic_Weight();

		/**
		 * The meta object literal for the '<em><b>Capability</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CHARACTERISTIC__CAPABILITY = eINSTANCE.getCharacteristic_Capability();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.TaskGraphImpl <em>Task Graph</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.TaskGraphImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTaskGraph()
		 * @generated
		 */
		EClass TASK_GRAPH = eINSTANCE.getTaskGraph();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_GRAPH__NODES = eINSTANCE.getTaskGraph_Nodes();

		/**
		 * The meta object literal for the '<em><b>Edges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_GRAPH__EDGES = eINSTANCE.getTaskGraph_Edges();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.EdgeImpl <em>Edge</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.EdgeImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getEdge()
		 * @generated
		 */
		EClass EDGE = eINSTANCE.getEdge();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__FROM = eINSTANCE.getEdge_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EDGE__TO = eINSTANCE.getEdge_To();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.TaskWrapperImpl <em>Task Wrapper</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.TaskWrapperImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTaskWrapper()
		 * @generated
		 */
		EClass TASK_WRAPPER = eINSTANCE.getTaskWrapper();

		/**
		 * The meta object literal for the '<em><b>Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_WRAPPER__TASK = eINSTANCE.getTaskWrapper_Task();

		/**
		 * The meta object literal for the '<em><b>Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_WRAPPER__MAPPINGS = eINSTANCE.getTaskWrapper_Mappings();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.InternalRoleTaskMappingImpl <em>Internal Role Task Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.InternalRoleTaskMappingImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getInternalRoleTaskMapping()
		 * @generated
		 */
		EClass INTERNAL_ROLE_TASK_MAPPING = eINSTANCE.getInternalRoleTaskMapping();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_ROLE_TASK_MAPPING__ROLE = eINSTANCE.getInternalRoleTaskMapping_Role();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTERNAL_ROLE_TASK_MAPPING__PRIORITY = eINSTANCE.getInternalRoleTaskMapping_Priority();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.NodeImpl <em>Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.NodeImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getNode()
		 * @generated
		 */
		EClass NODE = eINSTANCE.getNode();

		/**
		 * The meta object literal for the '<em><b>In Edge</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__IN_EDGE = eINSTANCE.getNode_InEdge();

		/**
		 * The meta object literal for the '<em><b>Out Edge</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NODE__OUT_EDGE = eINSTANCE.getNode_OutEdge();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.TaskRepositoryImpl <em>Task Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.TaskRepositoryImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getTaskRepository()
		 * @generated
		 */
		EClass TASK_REPOSITORY = eINSTANCE.getTaskRepository();

		/**
		 * The meta object literal for the '<em><b>Tasks</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_REPOSITORY__TASKS = eINSTANCE.getTaskRepository_Tasks();

		/**
		 * The meta object literal for the '<em><b>Default Task</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TASK_REPOSITORY__DEFAULT_TASK = eINSTANCE.getTaskRepository_DefaultTask();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.UtilityRepositoryImpl <em>Utility Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.UtilityRepositoryImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityRepository()
		 * @generated
		 */
		EClass UTILITY_REPOSITORY = eINSTANCE.getUtilityRepository();

		/**
		 * The meta object literal for the '<em><b>Utilities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_REPOSITORY__UTILITIES = eINSTANCE.getUtilityRepository_Utilities();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.UtilityReferenceImpl <em>Utility Reference</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.UtilityReferenceImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityReference()
		 * @generated
		 */
		EClass UTILITY_REFERENCE = eINSTANCE.getUtilityReference();

		/**
		 * The meta object literal for the '<em><b>Weight</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UTILITY_REFERENCE__WEIGHT = eINSTANCE.getUtilityReference_Weight();

		/**
		 * The meta object literal for the '<em><b>Utility</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_REFERENCE__UTILITY = eINSTANCE.getUtilityReference_Utility();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.UtilityModeImpl <em>Utility Mode</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.UtilityModeImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityMode()
		 * @generated
		 */
		EClass UTILITY_MODE = eINSTANCE.getUtilityMode();

		/**
		 * The meta object literal for the '<em><b>Mode</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UTILITY_MODE__MODE = eINSTANCE.getUtilityMode_Mode();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_MODE__EXPRESSION = eINSTANCE.getUtilityMode_Expression();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_MODE__PARENT = eINSTANCE.getUtilityMode_Parent();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.UtilityModeExpressionImpl <em>Utility Mode Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.UtilityModeExpressionImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityModeExpression()
		 * @generated
		 */
		EClass UTILITY_MODE_EXPRESSION = eINSTANCE.getUtilityModeExpression();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UTILITY_MODE_EXPRESSION__VARIABLE = eINSTANCE.getUtilityModeExpression_Variable();

		/**
		 * The meta object literal for the '<em><b>Parent</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_MODE_EXPRESSION__PARENT = eINSTANCE.getUtilityModeExpression_Parent();

		/**
		 * The meta object literal for the '<em><b>Entry Point</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UTILITY_MODE_EXPRESSION__ENTRY_POINT = eINSTANCE.getUtilityModeExpression_EntryPoint();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.SynchronisationImpl <em>Synchronisation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.SynchronisationImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getSynchronisation()
		 * @generated
		 */
		EClass SYNCHRONISATION = eINSTANCE.getSynchronisation();

		/**
		 * The meta object literal for the '<em><b>Synched Transitions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SYNCHRONISATION__SYNCHED_TRANSITIONS = eINSTANCE.getSynchronisation_SynchedTransitions();

		/**
		 * The meta object literal for the '<em><b>Talk Timeout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONISATION__TALK_TIMEOUT = eINSTANCE.getSynchronisation_TalkTimeout();

		/**
		 * The meta object literal for the '<em><b>Sync Timeout</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONISATION__SYNC_TIMEOUT = eINSTANCE.getSynchronisation_SyncTimeout();

		/**
		 * The meta object literal for the '<em><b>Fail On Sync Time Out</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SYNCHRONISATION__FAIL_ON_SYNC_TIME_OUT = eINSTANCE.getSynchronisation_FailOnSyncTimeOut();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.VariableImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__TYPE = eINSTANCE.getVariable_Type();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.ParametrisationImpl <em>Parametrisation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.ParametrisationImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getParametrisation()
		 * @generated
		 */
		EClass PARAMETRISATION = eINSTANCE.getParametrisation();

		/**
		 * The meta object literal for the '<em><b>Subplan</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETRISATION__SUBPLAN = eINSTANCE.getParametrisation_Subplan();

		/**
		 * The meta object literal for the '<em><b>Subvar</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETRISATION__SUBVAR = eINSTANCE.getParametrisation_Subvar();

		/**
		 * The meta object literal for the '<em><b>Var</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETRISATION__VAR = eINSTANCE.getParametrisation_Var();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.AnnotatedPlanImpl <em>Annotated Plan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.AnnotatedPlanImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getAnnotatedPlan()
		 * @generated
		 */
		EClass ANNOTATED_PLAN = eINSTANCE.getAnnotatedPlan();

		/**
		 * The meta object literal for the '<em><b>Plan</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ANNOTATED_PLAN__PLAN = eINSTANCE.getAnnotatedPlan_Plan();

		/**
		 * The meta object literal for the '<em><b>Activated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ANNOTATED_PLAN__ACTIVATED = eINSTANCE.getAnnotatedPlan_Activated();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.QuantifierImpl <em>Quantifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.QuantifierImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getQuantifier()
		 * @generated
		 */
		EClass QUANTIFIER = eINSTANCE.getQuantifier();

		/**
		 * The meta object literal for the '<em><b>Scope</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference QUANTIFIER__SCOPE = eINSTANCE.getQuantifier_Scope();

		/**
		 * The meta object literal for the '<em><b>Sorts</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute QUANTIFIER__SORTS = eINSTANCE.getQuantifier_Sorts();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.ForallAgentsImpl <em>Forall Agents</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.ForallAgentsImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getForallAgents()
		 * @generated
		 */
		EClass FORALL_AGENTS = eINSTANCE.getForallAgents();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.IInhabitable <em>IInhabitable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.IInhabitable
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getIInhabitable()
		 * @generated
		 */
		EClass IINHABITABLE = eINSTANCE.getIInhabitable();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.CapabilityImpl <em>Capability</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.CapabilityImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCapability()
		 * @generated
		 */
		EClass CAPABILITY = eINSTANCE.getCapability();

		/**
		 * The meta object literal for the '<em><b>Cap Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAPABILITY__CAP_VALUES = eINSTANCE.getCapability_CapValues();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.CapValueImpl <em>Cap Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.CapValueImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCapValue()
		 * @generated
		 */
		EClass CAP_VALUE = eINSTANCE.getCapValue();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.CapabilityDefinitionSetImpl <em>Capability Definition Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.CapabilityDefinitionSetImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getCapabilityDefinitionSet()
		 * @generated
		 */
		EClass CAPABILITY_DEFINITION_SET = eINSTANCE.getCapabilityDefinitionSet();

		/**
		 * The meta object literal for the '<em><b>Capabilities</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAPABILITY_DEFINITION_SET__CAPABILITIES = eINSTANCE.getCapabilityDefinitionSet_Capabilities();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.impl.PlanningProblemImpl <em>Planning Problem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.impl.PlanningProblemImpl
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getPlanningProblem()
		 * @generated
		 */
		EClass PLANNING_PROBLEM = eINSTANCE.getPlanningProblem();

		/**
		 * The meta object literal for the '<em><b>Plans</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PLANNING_PROBLEM__PLANS = eINSTANCE.getPlanningProblem_Plans();

		/**
		 * The meta object literal for the '{@link de.uni_kassel.cn.alica.UtilityModes <em>Utility Modes</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see de.uni_kassel.cn.alica.UtilityModes
		 * @see de.uni_kassel.cn.alica.impl.AlicaPackageImpl#getUtilityModes()
		 * @generated
		 */
		EEnum UTILITY_MODES = eINSTANCE.getUtilityModes();

	}

} //AlicaPackage
