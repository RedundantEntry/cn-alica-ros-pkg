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
package de.uni_kassel.cn.planDesigner.ui.actions;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.window.Window;

import de.uni_kassel.cn.alica.Behaviour;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;
import de.uni_kassel.cn.planDesigner.ui.commands.RemoveReadOnlyCommand;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.BehaviourDeletionDialog;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;

public class DeleteBehaviourConfigurationAction extends Action {
	
	private StructuredViewer viewer;

	public DeleteBehaviourConfigurationAction(StructuredViewer viewer) {
		this.viewer = viewer;
	}

	@Override
	public void run() {
 		if(reallyDeleteDialog()){
			// Prepare the selection i.e. if the user has selected configurations
			// and the behaviour which contains these configuration, remove 
			// the behaviour from the selection
			Set<EObject> preparedSelection = prepareSelection(viewer.getSelection());
			Set<Behaviour> containingBehavious = extractBehaviours(preparedSelection);
			
			PMLTransactionalEditingDomain editingDomain = (PMLTransactionalEditingDomain)TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(
					IEditorConstants.PML_TRANSACTIONAL_EDITING_DOMAIN_ID);
			
			// We assume that at the time this action is invoked (since it can only 
			// be invoked from the repository) all necessary resources are loaded and we can 
			// find all cross-references
			Map<EObject, Collection<EStructuralFeature.Setting>> usages = UsageCrossReferencer.findAll(preparedSelection, editingDomain.getResourceSet());
			
			for(EObject eo : usages.keySet()){
//				System.err.println("Usages for: " +eo);
				for(EStructuralFeature.Setting setting : usages.get(eo)){
//					System.out.println("\tEObject: " +setting.getEObject() +", Feature: " +setting.getEStructuralFeature());
				}
			}
			
			if(usages.keySet().isEmpty() || (!usages.keySet().isEmpty() && performDeletionPreview(usages, editingDomain))){
				// Remove the read-only flag since this action is invoked from the Repository.
				// This is a hack: Because we don't want the user to be able to adjust the properties 
				// from within the repositoryView, the PMLTabbedPropertySheetPage put all resources
				// into the editingDomain.resourceToReadOnly map. If we can avoid doing this, we could 
				// remove this hack. The read-only flag will be set to true the next time the user selects
				// something from the repository
				editingDomain.getCommandStack().execute(new RemoveReadOnlyCommand(editingDomain));
				try {
					Set<Resource> affectedResources = getAffectedResources(preparedSelection);
					editingDomain.getCommandStack().execute(
							DeleteCommand.create(editingDomain, preparedSelection),
							Collections.singletonMap(Transaction.OPTION_NO_UNDO, Boolean.TRUE));
					
					// Save the resources that were affacted
					for (Resource r : affectedResources)
					{
						try
						{
							r.save(AlicaSerializationHelper.getInstance()
									.getLoadSaveOptions());
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
					
					Set<Resource> resourcesToDelete = new HashSet<Resource>();
					// Check for empty Behaviours and delete them
					for(Behaviour behaviour : containingBehavious)
					{
						if(behaviour.getConfigurations().isEmpty())
						{
							resourcesToDelete.add(behaviour.eResource());
						}
					}
					
					for(Resource r : resourcesToDelete)
					{
						IResource files = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(r.getURI().toPlatformString(true)));
						try
						{
							files.delete(true, null);
						} catch (CoreException e)
						{
							e.printStackTrace();
						}
						System.out.println();
//						File file = new File(r.getResourceSet().getURIConverter().normalize(r.getURI().toPlatformString(true)));
//					    System.out.println("File <" +file +"> deleted: " +file.delete());
					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (RollbackException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Set<Behaviour> extractBehaviours(Set<EObject> preparedSelection)
	{
		Set<Behaviour> behaviours = new HashSet<Behaviour>();
		for (EObject obj : preparedSelection)
		{
			EObject current = obj;
			while (current != null && !(current instanceof Behaviour))
			{
				current = obj.eContainer();
			}
			if (current instanceof Behaviour)
			{
				behaviours.add((Behaviour) current);
			}
		}

		return behaviours;
	}

	private Set<Resource> getAffectedResources(Set<EObject> preparedSelection){
		Set<Resource> affectedResources = new HashSet<Resource>();
		for(EObject eo : preparedSelection)
			affectedResources.add(eo.eResource());
		
		return affectedResources;
	}
	
	private boolean performDeletionPreview(final Map<EObject, Collection<EStructuralFeature.Setting>> usages, PMLTransactionalEditingDomain domain){
		boolean result = false;
		try {
			result = (Boolean)domain.runExclusive(new RunnableWithResult.Impl<Boolean>(){
				public void run() {
					setResult(new BehaviourDeletionDialog(viewer.getControl().getShell(), usages).open() == Window.OK ? Boolean.TRUE : Boolean.FALSE);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private boolean reallyDeleteDialog(){
		return MessageDialog.openQuestion(
				viewer.getControl().getShell(), 
				"Confirm delete", 
				"Do you really want to delete the selected items?");
	}
	
	private Set<EObject> prepareSelection(ISelection sel){
		IStructuredSelection strucSel = (IStructuredSelection)sel;
		
		List<EObject> objectsInSelection = strucSel.toList();
		Set<EObject> toRemove = new HashSet<EObject>();
		Set<EObject> toAdd = new HashSet<EObject>();
		
		for(EObject o : objectsInSelection){
			if(o instanceof Behaviour)
			{
				toRemove.add(o);
				toAdd.addAll(((Behaviour)o).getConfigurations());
			}
		}
		
		
		Set<EObject> result = new HashSet<EObject>();

		for(EObject o : objectsInSelection)
		{
			if(!toRemove.contains(o))
			{
				result.add(o);
			}
		}
		
		result.addAll(toAdd);
		return result;
		
	}
}
