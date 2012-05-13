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

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;

public class PlanMapper {

	private final IFile file;
	
	private PMLTransactionalEditingDomain editingDomain;
	
	private Resource planResource;
	
	private Plan loadedPlan;

	public PlanMapper(IFile file) {
		this.file = file;
	}
	
	public void save() throws IOException{
		Resource planResource = getPlanResource();
		
		try {
			// Copy the plan which will be serialized
			Plan copiedPlan = (Plan)EcoreUtil.copy(getPlan());
			
			// I think we dont need that anymore ... (Stopfer)
			//mapBackwards(copiedPlan);
			
			// Replace the contents of the planResource
			// with the copiedPlan...
			planResource.getContents().clear();
			planResource.getContents().add(copiedPlan);
			
			// ...save...
			planResource.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
			
			// ...and put the original plan back
			planResource.getContents().clear();
			planResource.getContents().add(getPlan());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Resource load(){
		// Load the file with the editingDomain
		Resource resource = getEditingDomain().load(file);
		setPlanResource(resource);
		
		// I think we dont need that anymore... (Stopfer)
//		mapForward();
		
		return planResource;
	}

	private PMLTransactionalEditingDomain getEditingDomain() {
		if(editingDomain == null){
			editingDomain = (PMLTransactionalEditingDomain)TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(
					IEditorConstants.PML_TRANSACTIONAL_EDITING_DOMAIN_ID);
		}
		
		return editingDomain;
		
	}

	/*private void mapForward() {
		final Plan plan = getPlan();
		
		getEditingDomain()
					.getCommandStack().execute(new RecordingCommand(getEditingDomain()){
						@Override
						protected void doExecute() {
							final List<SyncTransition> createdSyncTransitions = new ArrayList<SyncTransition>();
							final List<Synchronisation> createdSynchronisations = new ArrayList<Synchronisation>();
							
							// Map SyncTransitions back to Synchronisations
							Synchronisation syncPoint = null;
							SyncTransition newSyncTrans = null;
							
							EList<SyncTransition> syncTransitions = plan.getSyncTransitions();
							for(SyncTransition syncTrans : syncTransitions){
								syncPoint = AlicaFactory.eINSTANCE.createSynchronisation();
								syncPoint.setId(syncTrans.getId());
								syncPoint.setComment(syncTrans.getComment());
								syncPoint.setName(syncTrans.getName());
								syncPoint.setFailOnSyncTimeOut(syncTrans.isFailOnSyncTimeOut());
								syncPoint.setSyncTimeout(syncTrans.getSyncTimeout());
								syncPoint.setTalkTimeout(syncTrans.getTalkTimeout());
								
								createdSynchronisations.add(syncPoint);
								
								for(Transition transition : syncTrans.getInSync()){
									newSyncTrans = AlicaFactory.eINSTANCE.createSyncTransition();
									newSyncTrans.getInSync().add(transition);
									
									syncPoint.getSyncTransitions().add(newSyncTrans);
									createdSyncTransitions.add(newSyncTrans);
								}
							}
							
							List<SyncTransition> oldSyncTransitions = new ArrayList<SyncTransition>();
							oldSyncTransitions.addAll(syncTransitions);
							
							// Remove all syncTransitions from the plan
							for(SyncTransition oldSync : oldSyncTransitions){
								EcoreUtil.delete(oldSync);
							}

							// Add all created Synctransitions to the plan
							syncTransitions.addAll(createdSyncTransitions);

							// Add all created Synchronisations to the plan
							plan.getSynchronisations().addAll(createdSynchronisations);
						}
					});
			
	}*/

//	private void mapBackwards(final Plan plan){
//		getEditingDomain().getCommandStack().execute(new RecordingCommand(getEditingDomain()){
//			@Override
//			protected void doExecute() {
//				List<SyncTransition> createdSynctransitions = new ArrayList<SyncTransition>();
//
//				SyncTransition syncTransition = null;
//				for(Synchronisation syncPoint : plan.getSynchronisations()){
//					if(!syncPoint.getSyncTransitions().isEmpty()){
//						syncTransition = AlicaFactory.eINSTANCE.createSyncTransition();
//						syncTransition.setId(syncPoint.getId());
//						syncTransition.setComment(syncPoint.getComment());
//						syncTransition.setName(syncPoint.getName());
//						syncTransition.setFailOnSyncTimeOut(syncPoint.isFailOnSyncTimeOut());
//						syncTransition.setSyncTimeout(syncPoint.getSyncTimeout());
//						syncTransition.setTalkTimeout(syncPoint.getTalkTimeout());
//						
//						for(SyncTransition inSync : syncPoint.getSyncTransitions()){
//							syncTransition.getInSync().add(inSync.getInSync().get(0));
//						}
//						createdSynctransitions.add(syncTransition);
//					}
//				}
//				List<EObject> oldElements = new ArrayList<EObject>();
//				
//				// Remove all SyncPoints from the plan
//				oldElements.addAll(plan.getSynchronisations());
//				// Remove all Synctransitions from the plan
//				oldElements.addAll(plan.getSyncTransitions());
//				for(EObject oldSynPoint : oldElements){
//					EcoreUtil.delete(oldSynPoint);
//				}
//
//				plan.getSyncTransitions().addAll(createdSynctransitions);
//			}
//		});
//	}
	
	public Plan getPlan(){
		if(loadedPlan == null){
			loadedPlan = (Plan)getPlanResource().getContents().get(0);
		}
		
		return loadedPlan;
	}
	
	
	public Resource getPlanResource() {
		return planResource;
	}

	protected void setPlanResource(Resource planResource) {
		this.planResource = planResource;
	}
}
