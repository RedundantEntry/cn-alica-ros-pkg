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
package de.uni_kassel.cn.planDesigner.ui.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.BehaviourConfiguration;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.State;
import de.uni_kassel.cn.alica.SyncTransition;
import de.uni_kassel.cn.alica.Synchronisation;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.editors.PlanEditor;

public class PlanEditorUtils extends CommonUtils{
	
	/**
	 * Convenience method to get the editor from a given editPart;
	 * @return
	 */
	public static PlanEditor getPlanEditor(EditPart part){
		Assert.isNotNull(part);
		return ((PlanEditor)((DefaultEditDomain)part.getViewer().getEditDomain())
				.getEditorPart());
	}
	
	/**
	 * Checks for unresolvable references within the given resource and cleans
	 * any of these reference within the given resource. After the cleaning step,
	 * the resource will be saved to make he changes persistent.
	 * 
	 * @param r
	 * @return Returns the map of unresolvable proxies.
	 */
	public static Map<EObject, Collection<Setting>> checkForUnresolvableReferences(final PlanEditor editor, Resource r){
		Map<EObject, Collection<Setting>> proxies = EcoreUtil.UnresolvedProxyCrossReferencer.find(r);
		
		if(!proxies.isEmpty()){
			CompoundCommand cmp = new CompoundCommand();
			PMLTransactionalEditingDomain editingDomain = editor.getEditingDomain();
			
			for(Map.Entry<EObject, Collection<Setting>> entry : proxies.entrySet()){
				EObject proxy = (EObject)entry.getKey();
				Collection<Setting> settings = entry.getValue();
				for(Setting setting : settings){
					EObject owner = setting.getEObject();
					EStructuralFeature ownerFeature = setting.getEStructuralFeature();
					cmp.append(RemoveCommand.create(editingDomain,owner, ownerFeature, proxy));
				}
				
			}
			
			// Add a save command
			cmp.append(new RecordingCommand(editingDomain)
			{
				@Override
				protected void doExecute() {
					try {
						editor.getPlanMapper().save();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			});
			
			// Execute all commands
			editingDomain.getCommandStack().execute(cmp);
			
			// Because the unresolved resources are now contained in the resourceset
			// (due to the check of the CrossReferencer) we remove the resources that
			// contain errors and are not loaded
			Set<Resource> toRemove = new HashSet<Resource>();
			for(Resource res : editingDomain.getResourceSet().getResources())
				if(!res.getErrors().isEmpty() && !res.isLoaded())
					toRemove.add(res);
			
			editingDomain.getResourceSet().getResources().removeAll(toRemove);
		}
		
		
			
		return proxies;
	}
	
	/**
	 * Collects all modified resources, that the given editor might have modified, which
	 * are all BehaviourConfigurations, contained in any state. If <code>includeUIResource</code>
	 * ist true, the UI Resource which belongs to the editor is included in the resulting list
	 * of modified resources. 
	 * @param editor
	 * @param includeUIResource
	 * @return
	 */
	public static List<Resource> collectModifiedResources(PlanEditor editor, boolean includeUIResource){
		List<Resource> modified = new ArrayList<Resource>();
		
		// Add the main resource: the plan
//		modified.add(editor.getResource());
		
		// Add the taskRepository resource
//		modified.add(getTaskRepositoryResource(editor.getEditingDomain()));
		
		if(includeUIResource)
			// Add the ui resource
			modified.add(editor.getUIExtensionResource());
		
		// Add all resources that we can indirectly modify
		for(State s : editor.getPlan().getStates())
		{
			for(AbstractPlan aPlan : s.getPlans())
			{
				//if(aPlan instanceof BehaviourConfiguration)
				//{
					modified.add(aPlan.eResource());
				//}
			}
		}
		
		return modified;
	}
	
	public static boolean isModifiedResourceAffected(PlanEditor editor, Collection<Resource> affectedResourcesToTest){
		// Add all resources which may be indirectly modified by the given editor
		List<Resource> collectedResources = PlanEditorUtils.collectModifiedResources(editor, true);
		collectedResources.add(editor.getResource());
		
		boolean result = false;
		
		// Test each affectedResource against the possible modified resources
		for(Resource affected : affectedResourcesToTest){
			if(collectedResources.contains(affected)){
				result = true;
				break;
			}
		}
		
		return result;
	}
	
//	public static Plan mapSynchronisationsToSynctransitions(Plan plan){
//		Plan copiedPlan = (Plan)EcoreUtil.copy(plan);
//		List<SyncTransition> createdSynctransitions = new ArrayList<SyncTransition>();
//		
//		SyncTransition syncTransition = null;
//		for(Synchronisation syncPoint : copiedPlan.getSynchronisations()){
//			syncTransition = AlicaFactory.eINSTANCE.createSyncTransition();
//			for(SyncTransition inSync : syncPoint.getSyncTransitions()){
//				syncTransition.getInSync().add(inSync.getInSync().get(0));
//			}
//			createdSynctransitions.add(syncTransition);
//		}
//		
//		// Remove all SyncPoints from the plan
//		copiedPlan.getSynchronisations().clear();
//		
//		// Remove all Synctransitions from the plan
//		copiedPlan.getSyncTransitions().clear();
//		
//		// Add all new created syncTransitions
//		copiedPlan.getSyncTransitions().addAll(createdSynctransitions);
//		
//		return copiedPlan;
//	}
	
}
