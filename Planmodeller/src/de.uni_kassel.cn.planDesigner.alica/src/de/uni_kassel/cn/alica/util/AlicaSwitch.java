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
package de.uni_kassel.cn.alica.util;

import de.uni_kassel.cn.alica.*;

import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see de.uni_kassel.cn.alica.AlicaPackage
 * @generated
 */
public class AlicaSwitch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static AlicaPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaSwitch() {
		if (modelPackage == null) {
			modelPackage = AlicaPackage.eINSTANCE;
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	public T doSwitch(EObject theEObject) {
		return doSwitch(theEObject.eClass(), theEObject);
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(EClass theEClass, EObject theEObject) {
		if (theEClass.eContainer() == modelPackage) {
			return doSwitch(theEClass.getClassifierID(), theEObject);
		}
		else {
			List<EClass> eSuperTypes = theEClass.getESuperTypes();
			return
				eSuperTypes.isEmpty() ?
					defaultCase(theEObject) :
					doSwitch(eSuperTypes.get(0), theEObject);
		}
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case AlicaPackage.TRANSITION: {
				Transition transition = (Transition)theEObject;
				T result = caseTransition(transition);
				if (result == null) result = casePlanElement(transition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.CONDITION: {
				Condition condition = (Condition)theEObject;
				T result = caseCondition(condition);
				if (result == null) result = casePlanElement(condition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.PRE_CONDITION: {
				PreCondition preCondition = (PreCondition)theEObject;
				T result = casePreCondition(preCondition);
				if (result == null) result = caseCondition(preCondition);
				if (result == null) result = casePlanElement(preCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ENTRY_POINT: {
				EntryPoint entryPoint = (EntryPoint)theEObject;
				T result = caseEntryPoint(entryPoint);
				if (result == null) result = caseIInhabitable(entryPoint);
				if (result == null) result = casePlanElement(entryPoint);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.TERMINAL_STATE: {
				TerminalState terminalState = (TerminalState)theEObject;
				T result = caseTerminalState(terminalState);
				if (result == null) result = caseState(terminalState);
				if (result == null) result = caseIInhabitable(terminalState);
				if (result == null) result = casePlanElement(terminalState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.SUCCESS_STATE: {
				SuccessState successState = (SuccessState)theEObject;
				T result = caseSuccessState(successState);
				if (result == null) result = caseTerminalState(successState);
				if (result == null) result = caseState(successState);
				if (result == null) result = caseIInhabitable(successState);
				if (result == null) result = casePlanElement(successState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.FAILURE_STATE: {
				FailureState failureState = (FailureState)theEObject;
				T result = caseFailureState(failureState);
				if (result == null) result = caseTerminalState(failureState);
				if (result == null) result = caseState(failureState);
				if (result == null) result = caseIInhabitable(failureState);
				if (result == null) result = casePlanElement(failureState);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ABSTRACT_PLAN: {
				AbstractPlan abstractPlan = (AbstractPlan)theEObject;
				T result = caseAbstractPlan(abstractPlan);
				if (result == null) result = caseIInhabitable(abstractPlan);
				if (result == null) result = casePlanElement(abstractPlan);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.BEHAVIOUR: {
				Behaviour behaviour = (Behaviour)theEObject;
				T result = caseBehaviour(behaviour);
				if (result == null) result = casePlanElement(behaviour);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.STATE: {
				State state = (State)theEObject;
				T result = caseState(state);
				if (result == null) result = caseIInhabitable(state);
				if (result == null) result = casePlanElement(state);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.PLAN: {
				Plan plan = (Plan)theEObject;
				T result = casePlan(plan);
				if (result == null) result = caseAbstractPlan(plan);
				if (result == null) result = caseIInhabitable(plan);
				if (result == null) result = casePlanElement(plan);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.UTILITY: {
				Utility utility = (Utility)theEObject;
				T result = caseUtility(utility);
				if (result == null) result = casePlanElement(utility);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.PLAN_TYPE: {
				PlanType planType = (PlanType)theEObject;
				T result = casePlanType(planType);
				if (result == null) result = caseAbstractPlan(planType);
				if (result == null) result = caseIInhabitable(planType);
				if (result == null) result = casePlanElement(planType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.RATING: {
				Rating rating = (Rating)theEObject;
				T result = caseRating(rating);
				if (result == null) result = casePlanElement(rating);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.RESULT: {
				Result result = (Result)theEObject;
				T theResult = caseResult(result);
				if (theResult == null) theResult = caseCondition(result);
				if (theResult == null) theResult = casePlanElement(result);
				if (theResult == null) theResult = defaultCase(theEObject);
				return theResult;
			}
			case AlicaPackage.RUNTIME_CONDITION: {
				RuntimeCondition runtimeCondition = (RuntimeCondition)theEObject;
				T result = caseRuntimeCondition(runtimeCondition);
				if (result == null) result = caseCondition(runtimeCondition);
				if (result == null) result = casePlanElement(runtimeCondition);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.PLAN_ELEMENT: {
				PlanElement planElement = (PlanElement)theEObject;
				T result = casePlanElement(planElement);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.TASK: {
				Task task = (Task)theEObject;
				T result = caseTask(task);
				if (result == null) result = casePlanElement(task);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ESTRING_TO_ESTRING_MAP_ENTRY: {
				@SuppressWarnings("unchecked") Map.Entry<String, String> eStringToEStringMapEntry = (Map.Entry<String, String>)theEObject;
				T result = caseEStringToEStringMapEntry(eStringToEStringMapEntry);
				if (result == null) result = casePlanElement((PlanElement)eStringToEStringMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.BEHAVIOUR_CONFIGURATION: {
				BehaviourConfiguration behaviourConfiguration = (BehaviourConfiguration)theEObject;
				T result = caseBehaviourConfiguration(behaviourConfiguration);
				if (result == null) result = caseAbstractPlan(behaviourConfiguration);
				if (result == null) result = caseIInhabitable(behaviourConfiguration);
				if (result == null) result = casePlanElement(behaviourConfiguration);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ROLE: {
				Role role = (Role)theEObject;
				T result = caseRole(role);
				if (result == null) result = casePlanElement(role);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ROLE_SET: {
				RoleSet roleSet = (RoleSet)theEObject;
				T result = caseRoleSet(roleSet);
				if (result == null) result = casePlanElement(roleSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ELONG_TO_DOUBLE_MAP_ENTRY: {
				@SuppressWarnings("unchecked") Map.Entry<Long, Double> eLongToDoubleMapEntry = (Map.Entry<Long, Double>)theEObject;
				T result = caseELongToDoubleMapEntry(eLongToDoubleMapEntry);
				if (result == null) result = casePlanElement((PlanElement)eLongToDoubleMapEntry);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ROLE_DEFINITION_SET: {
				RoleDefinitionSet roleDefinitionSet = (RoleDefinitionSet)theEObject;
				T result = caseRoleDefinitionSet(roleDefinitionSet);
				if (result == null) result = casePlanElement(roleDefinitionSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ROLE_TASK_MAPPING: {
				RoleTaskMapping roleTaskMapping = (RoleTaskMapping)theEObject;
				T result = caseRoleTaskMapping(roleTaskMapping);
				if (result == null) result = casePlanElement(roleTaskMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.CHARACTERISTIC: {
				Characteristic characteristic = (Characteristic)theEObject;
				T result = caseCharacteristic(characteristic);
				if (result == null) result = casePlanElement(characteristic);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.TASK_GRAPH: {
				TaskGraph taskGraph = (TaskGraph)theEObject;
				T result = caseTaskGraph(taskGraph);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.EDGE: {
				Edge edge = (Edge)theEObject;
				T result = caseEdge(edge);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.TASK_WRAPPER: {
				TaskWrapper taskWrapper = (TaskWrapper)theEObject;
				T result = caseTaskWrapper(taskWrapper);
				if (result == null) result = caseNode(taskWrapper);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.INTERNAL_ROLE_TASK_MAPPING: {
				InternalRoleTaskMapping internalRoleTaskMapping = (InternalRoleTaskMapping)theEObject;
				T result = caseInternalRoleTaskMapping(internalRoleTaskMapping);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.NODE: {
				Node node = (Node)theEObject;
				T result = caseNode(node);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.TASK_REPOSITORY: {
				TaskRepository taskRepository = (TaskRepository)theEObject;
				T result = caseTaskRepository(taskRepository);
				if (result == null) result = casePlanElement(taskRepository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.UTILITY_REPOSITORY: {
				UtilityRepository utilityRepository = (UtilityRepository)theEObject;
				T result = caseUtilityRepository(utilityRepository);
				if (result == null) result = casePlanElement(utilityRepository);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.UTILITY_REFERENCE: {
				UtilityReference utilityReference = (UtilityReference)theEObject;
				T result = caseUtilityReference(utilityReference);
				if (result == null) result = casePlanElement(utilityReference);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.UTILITY_MODE: {
				UtilityMode utilityMode = (UtilityMode)theEObject;
				T result = caseUtilityMode(utilityMode);
				if (result == null) result = casePlanElement(utilityMode);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.UTILITY_MODE_EXPRESSION: {
				UtilityModeExpression utilityModeExpression = (UtilityModeExpression)theEObject;
				T result = caseUtilityModeExpression(utilityModeExpression);
				if (result == null) result = casePlanElement(utilityModeExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.SYNCHRONISATION: {
				Synchronisation synchronisation = (Synchronisation)theEObject;
				T result = caseSynchronisation(synchronisation);
				if (result == null) result = casePlanElement(synchronisation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.VARIABLE: {
				Variable variable = (Variable)theEObject;
				T result = caseVariable(variable);
				if (result == null) result = casePlanElement(variable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.PARAMETRISATION: {
				Parametrisation parametrisation = (Parametrisation)theEObject;
				T result = caseParametrisation(parametrisation);
				if (result == null) result = casePlanElement(parametrisation);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.ANNOTATED_PLAN: {
				AnnotatedPlan annotatedPlan = (AnnotatedPlan)theEObject;
				T result = caseAnnotatedPlan(annotatedPlan);
				if (result == null) result = casePlanElement(annotatedPlan);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.QUANTIFIER: {
				Quantifier quantifier = (Quantifier)theEObject;
				T result = caseQuantifier(quantifier);
				if (result == null) result = casePlanElement(quantifier);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.FORALL_AGENTS: {
				ForallAgents forallAgents = (ForallAgents)theEObject;
				T result = caseForallAgents(forallAgents);
				if (result == null) result = caseQuantifier(forallAgents);
				if (result == null) result = casePlanElement(forallAgents);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.IINHABITABLE: {
				IInhabitable iInhabitable = (IInhabitable)theEObject;
				T result = caseIInhabitable(iInhabitable);
				if (result == null) result = casePlanElement(iInhabitable);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.CAPABILITY: {
				Capability capability = (Capability)theEObject;
				T result = caseCapability(capability);
				if (result == null) result = casePlanElement(capability);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.CAP_VALUE: {
				CapValue capValue = (CapValue)theEObject;
				T result = caseCapValue(capValue);
				if (result == null) result = casePlanElement(capValue);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.CAPABILITY_DEFINITION_SET: {
				CapabilityDefinitionSet capabilityDefinitionSet = (CapabilityDefinitionSet)theEObject;
				T result = caseCapabilityDefinitionSet(capabilityDefinitionSet);
				if (result == null) result = casePlanElement(capabilityDefinitionSet);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case AlicaPackage.PLANNING_PROBLEM: {
				PlanningProblem planningProblem = (PlanningProblem)theEObject;
				T result = casePlanningProblem(planningProblem);
				if (result == null) result = caseAbstractPlan(planningProblem);
				if (result == null) result = caseIInhabitable(planningProblem);
				if (result == null) result = casePlanElement(planningProblem);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Transition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTransition(Transition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCondition(Condition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Pre Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Pre Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreCondition(PreCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Entry Point</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Entry Point</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEntryPoint(EntryPoint object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Terminal State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Terminal State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTerminalState(TerminalState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Success State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Success State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSuccessState(SuccessState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Failure State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Failure State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseFailureState(FailureState object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Plan</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Plan</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractPlan(AbstractPlan object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Behaviour</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Behaviour</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBehaviour(Behaviour object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>State</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>State</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseState(State object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plan</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plan</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlan(Plan object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Utility</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Utility</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUtility(Utility object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plan Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plan Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlanType(PlanType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Rating</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Rating</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRating(Rating object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Result</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Result</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseResult(Result object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Runtime Condition</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Runtime Condition</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRuntimeCondition(RuntimeCondition object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Plan Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Plan Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlanElement(PlanElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTask(Task object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EString To EString Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EString To EString Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEStringToEStringMapEntry(Map.Entry<String, String> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Behaviour Configuration</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Behaviour Configuration</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBehaviourConfiguration(BehaviourConfiguration object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRole(Role object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoleSet(RoleSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>ELong To Double Map Entry</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>ELong To Double Map Entry</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseELongToDoubleMapEntry(Map.Entry<Long, Double> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role Definition Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role Definition Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoleDefinitionSet(RoleDefinitionSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Role Task Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Role Task Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRoleTaskMapping(RoleTaskMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Characteristic</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Characteristic</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCharacteristic(Characteristic object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Graph</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Graph</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskGraph(TaskGraph object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Edge</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseEdge(Edge object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Wrapper</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Wrapper</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskWrapper(TaskWrapper object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Internal Role Task Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Internal Role Task Mapping</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInternalRoleTaskMapping(InternalRoleTaskMapping object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Node</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Node</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNode(Node object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Task Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Task Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTaskRepository(TaskRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Utility Repository</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Utility Repository</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUtilityRepository(UtilityRepository object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Utility Reference</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Utility Reference</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUtilityReference(UtilityReference object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Utility Mode</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Utility Mode</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUtilityMode(UtilityMode object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Utility Mode Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Utility Mode Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseUtilityModeExpression(UtilityModeExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Synchronisation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Synchronisation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSynchronisation(Synchronisation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Variable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseVariable(Variable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parametrisation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parametrisation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParametrisation(Parametrisation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Annotated Plan</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Annotated Plan</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAnnotatedPlan(AnnotatedPlan object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Quantifier</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Quantifier</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQuantifier(Quantifier object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Forall Agents</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Forall Agents</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseForallAgents(ForallAgents object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IInhabitable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IInhabitable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIInhabitable(IInhabitable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Capability</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Capability</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCapability(Capability object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Cap Value</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Cap Value</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCapValue(CapValue object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Capability Definition Set</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Capability Definition Set</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCapabilityDefinitionSet(CapabilityDefinitionSet object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Planning Problem</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Planning Problem</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePlanningProblem(PlanningProblem object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	public T defaultCase(EObject object) {
		return null;
	}

} //AlicaSwitch
