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

import de.uni_kassel.cn.alica.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AlicaFactoryImpl extends EFactoryImpl implements AlicaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AlicaFactory init() {
		try {
			AlicaFactory theAlicaFactory = (AlicaFactory)EPackage.Registry.INSTANCE.getEFactory("http:///de.uni_kassel.cn"); 
			if (theAlicaFactory != null) {
				return theAlicaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AlicaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AlicaPackage.TRANSITION: return createTransition();
			case AlicaPackage.PRE_CONDITION: return createPreCondition();
			case AlicaPackage.ENTRY_POINT: return createEntryPoint();
			case AlicaPackage.SUCCESS_STATE: return createSuccessState();
			case AlicaPackage.FAILURE_STATE: return createFailureState();
			case AlicaPackage.BEHAVIOUR: return createBehaviour();
			case AlicaPackage.STATE: return createState();
			case AlicaPackage.PLAN: return createPlan();
			case AlicaPackage.UTILITY: return createUtility();
			case AlicaPackage.PLAN_TYPE: return createPlanType();
			case AlicaPackage.RATING: return createRating();
			case AlicaPackage.RESULT: return createResult();
			case AlicaPackage.RUNTIME_CONDITION: return createRuntimeCondition();
			case AlicaPackage.TASK: return createTask();
			case AlicaPackage.ESTRING_TO_ESTRING_MAP_ENTRY: return (EObject)createEStringToEStringMapEntry();
			case AlicaPackage.BEHAVIOUR_CONFIGURATION: return createBehaviourConfiguration();
			case AlicaPackage.ROLE: return createRole();
			case AlicaPackage.ROLE_SET: return createRoleSet();
			case AlicaPackage.ELONG_TO_DOUBLE_MAP_ENTRY: return (EObject)createELongToDoubleMapEntry();
			case AlicaPackage.ROLE_DEFINITION_SET: return createRoleDefinitionSet();
			case AlicaPackage.ROLE_TASK_MAPPING: return createRoleTaskMapping();
			case AlicaPackage.CHARACTERISTIC: return createCharacteristic();
			case AlicaPackage.TASK_GRAPH: return createTaskGraph();
			case AlicaPackage.EDGE: return createEdge();
			case AlicaPackage.TASK_WRAPPER: return createTaskWrapper();
			case AlicaPackage.INTERNAL_ROLE_TASK_MAPPING: return createInternalRoleTaskMapping();
			case AlicaPackage.NODE: return createNode();
			case AlicaPackage.TASK_REPOSITORY: return createTaskRepository();
			case AlicaPackage.UTILITY_REPOSITORY: return createUtilityRepository();
			case AlicaPackage.UTILITY_REFERENCE: return createUtilityReference();
			case AlicaPackage.UTILITY_MODE: return createUtilityMode();
			case AlicaPackage.UTILITY_MODE_EXPRESSION: return createUtilityModeExpression();
			case AlicaPackage.SYNCHRONISATION: return createSynchronisation();
			case AlicaPackage.VARIABLE: return createVariable();
			case AlicaPackage.PARAMETRISATION: return createParametrisation();
			case AlicaPackage.ANNOTATED_PLAN: return createAnnotatedPlan();
			case AlicaPackage.FORALL_AGENTS: return createForallAgents();
			case AlicaPackage.CAPABILITY: return createCapability();
			case AlicaPackage.CAP_VALUE: return createCapValue();
			case AlicaPackage.CAPABILITY_DEFINITION_SET: return createCapabilityDefinitionSet();
			case AlicaPackage.PLANNING_PROBLEM: return createPlanningProblem();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AlicaPackage.UTILITY_MODES:
				return createUtilityModesFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AlicaPackage.UTILITY_MODES:
				return convertUtilityModesToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreCondition createPreCondition() {
		PreConditionImpl preCondition = new PreConditionImpl();
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryPoint createEntryPoint() {
		EntryPointImpl entryPoint = new EntryPointImpl();
		return entryPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SuccessState createSuccessState() {
		SuccessStateImpl successState = new SuccessStateImpl();
		return successState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FailureState createFailureState() {
		FailureStateImpl failureState = new FailureStateImpl();
		return failureState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Behaviour createBehaviour() {
		BehaviourImpl behaviour = new BehaviourImpl();
		return behaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plan createPlan() {
		PlanImpl plan = new PlanImpl();
		return plan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Utility createUtility() {
		UtilityImpl utility = new UtilityImpl();
		return utility;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanType createPlanType() {
		PlanTypeImpl planType = new PlanTypeImpl();
		return planType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rating createRating() {
		RatingImpl rating = new RatingImpl();
		return rating;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Result createResult() {
		ResultImpl result = new ResultImpl();
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeCondition createRuntimeCondition() {
		RuntimeConditionImpl runtimeCondition = new RuntimeConditionImpl();
		return runtimeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Task createTask() {
		TaskImpl task = new TaskImpl();
		return task;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createEStringToEStringMapEntry() {
		EStringToEStringMapEntryImpl eStringToEStringMapEntry = new EStringToEStringMapEntryImpl();
		return eStringToEStringMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviourConfiguration createBehaviourConfiguration() {
		BehaviourConfigurationImpl behaviourConfiguration = new BehaviourConfigurationImpl();
		return behaviourConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleSet createRoleSet() {
		RoleSetImpl roleSet = new RoleSetImpl();
		return roleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Long, Double> createELongToDoubleMapEntry() {
		ELongToDoubleMapEntryImpl eLongToDoubleMapEntry = new ELongToDoubleMapEntryImpl();
		return eLongToDoubleMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleDefinitionSet createRoleDefinitionSet() {
		RoleDefinitionSetImpl roleDefinitionSet = new RoleDefinitionSetImpl();
		return roleDefinitionSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleTaskMapping createRoleTaskMapping() {
		RoleTaskMappingImpl roleTaskMapping = new RoleTaskMappingImpl();
		return roleTaskMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Characteristic createCharacteristic() {
		CharacteristicImpl characteristic = new CharacteristicImpl();
		return characteristic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskGraph createTaskGraph() {
		TaskGraphImpl taskGraph = new TaskGraphImpl();
		return taskGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge createEdge() {
		EdgeImpl edge = new EdgeImpl();
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskWrapper createTaskWrapper() {
		TaskWrapperImpl taskWrapper = new TaskWrapperImpl();
		return taskWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InternalRoleTaskMapping createInternalRoleTaskMapping() {
		InternalRoleTaskMappingImpl internalRoleTaskMapping = new InternalRoleTaskMappingImpl();
		return internalRoleTaskMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskRepository createTaskRepository() {
		TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
		return taskRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UtilityRepository createUtilityRepository() {
		UtilityRepositoryImpl utilityRepository = new UtilityRepositoryImpl();
		return utilityRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UtilityReference createUtilityReference() {
		UtilityReferenceImpl utilityReference = new UtilityReferenceImpl();
		return utilityReference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UtilityMode createUtilityMode() {
		UtilityModeImpl utilityMode = new UtilityModeImpl();
		return utilityMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UtilityModeExpression createUtilityModeExpression() {
		UtilityModeExpressionImpl utilityModeExpression = new UtilityModeExpressionImpl();
		return utilityModeExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Synchronisation createSynchronisation() {
		SynchronisationImpl synchronisation = new SynchronisationImpl();
		return synchronisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parametrisation createParametrisation() {
		ParametrisationImpl parametrisation = new ParametrisationImpl();
		return parametrisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotatedPlan createAnnotatedPlan() {
		AnnotatedPlanImpl annotatedPlan = new AnnotatedPlanImpl();
		return annotatedPlan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForallAgents createForallAgents() {
		ForallAgentsImpl forallAgents = new ForallAgentsImpl();
		return forallAgents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Capability createCapability() {
		CapabilityImpl capability = new CapabilityImpl();
		return capability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CapValue createCapValue() {
		CapValueImpl capValue = new CapValueImpl();
		return capValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CapabilityDefinitionSet createCapabilityDefinitionSet() {
		CapabilityDefinitionSetImpl capabilityDefinitionSet = new CapabilityDefinitionSetImpl();
		return capabilityDefinitionSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanningProblem createPlanningProblem() {
		PlanningProblemImpl planningProblem = new PlanningProblemImpl();
		return planningProblem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UtilityModes createUtilityModesFromString(EDataType eDataType, String initialValue) {
		UtilityModes result = UtilityModes.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUtilityModesToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaPackage getAlicaPackage() {
		return (AlicaPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AlicaPackage getPackage() {
		return AlicaPackage.eINSTANCE;
	}

} //AlicaFactoryImpl
