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
package de.uni_kassel.cn.planDesigner.ui.ltk;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.mapping.IResourceChangeDescriptionFactory;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.ltk.core.refactoring.Change;
import org.eclipse.ltk.core.refactoring.RefactoringStatus;
import org.eclipse.ltk.core.refactoring.participants.CheckConditionsContext;
import org.eclipse.ltk.core.refactoring.participants.DeleteParticipant;
import org.eclipse.ltk.core.refactoring.participants.ResourceChangeChecker;
import org.eclipse.ltk.core.refactoring.resource.DeleteResourceChange;

public class DeleteUIExtensionParticipant extends DeleteParticipant {
	
	private IFile toDeleteFile;
	
	private IFile uiExtensionFile;

	public DeleteUIExtensionParticipant() {
	}

	@Override
	public RefactoringStatus checkConditions(IProgressMonitor pm,
			CheckConditionsContext context) throws OperationCanceledException {
		
		if(getUiExtensionFile().exists()){
			ResourceChangeChecker checker = (ResourceChangeChecker) context.getChecker(ResourceChangeChecker.class);
			IResourceChangeDescriptionFactory deltaFactory= checker.getDeltaFactory();
			
			// Add the UIExtensionResource we want to delete
			
			deltaFactory.delete(getUiExtensionFile());
		}
		
		return new RefactoringStatus();
	}

	@Override
	public Change createChange(IProgressMonitor pm) throws CoreException,
			OperationCanceledException {
		
		Object[] elementsToDelete = getProcessor().getElements();
		
		boolean applicable = true;
		
		for(int i=0; i < elementsToDelete.length; i++)
			if(elementsToDelete[i].equals(getUiExtensionFile())){
				// We want to modify a resource which the user wants to delete
				// so it doesn't make sense to add a change
				applicable = false;
				break;
			}
		
		if(applicable)
			return new DeleteResourceChange(getUiExtensionFile().getFullPath(), true);
		else 
			return null;
	}
	

	@Override
	public String getName() {
		return "Delete UI Extension file";
	}

	@Override
	protected boolean initialize(Object element) {
		boolean result = false;
		if(element instanceof IFile){
			toDeleteFile = (IFile)element;
			if(toDeleteFile.getFileExtension().equals("pml") || 
					toDeleteFile.getFileExtension().equals("rset"))
				result = getUiExtensionFile().exists();
		}
		return result;
	}

	private IFile getUiExtensionFile() {
		if(uiExtensionFile == null){
			IPath uiExt = new Path(toDeleteFile.getName().substring(0,
					toDeleteFile.getName().lastIndexOf(".")).concat(".pmlex"));
			
			uiExtensionFile = toDeleteFile.getParent().getFile(uiExt);
		}
		return uiExtensionFile;
	}

}
