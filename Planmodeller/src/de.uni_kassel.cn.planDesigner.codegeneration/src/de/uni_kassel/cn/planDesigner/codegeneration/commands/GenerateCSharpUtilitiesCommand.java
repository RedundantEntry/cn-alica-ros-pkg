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
package de.uni_kassel.cn.planDesigner.codegeneration.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.openarchitectureware.workflow.WorkflowRunner;
import org.openarchitectureware.workflow.monitor.NullProgressMonitor;

import de.uni_kassel.cn.alica.UtilityRepository;
import de.uni_kassel.cn.planDesigner.codegeneration.CodeGenActivator;
import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;

public class GenerateCSharpUtilitiesCommand extends AbstractHandler
{
	private class GenerateCodeJob extends WorkspaceJob
	{

		private final List<IFile> selectedFiles;

		public GenerateCodeJob(List<IFile> selectedFiles)
		{
			super("Generate Utilities");
			this.selectedFiles = selectedFiles;
		}

		@Override
		public IStatus runInWorkspace(IProgressMonitor monitor)
				throws CoreException
		{
			monitor.beginTask("Generating Utilities", selectedFiles.size());
			IStatus returnStatus = Status.OK_STATUS;
			
			Map<String, String> properties = new HashMap<String, String>();
			
			Iterator<IFile> iter = selectedFiles.iterator();
			
			List<IStatus> errors = new ArrayList<IStatus>();
			
			while(iter.hasNext()){
				
				if(monitor.isCanceled())
				{
					returnStatus = Status.CANCEL_STATUS;
					break;
				}
				
				IFile file = iter.next();
				
				String desPath = file.getParent().getLocation().toFile().getAbsolutePath() +File.separator +"generated" +File.separator;
				System.out.println("Destination of " +file +": " +desPath);
				
				File desPathFile = new File(desPath);
				if(!desPathFile.exists())
					desPathFile.mkdir();

				// Load the model
				Resource res = UtilityRepositoryUtils.getResource();
				
				UtilityRepository plan = (UtilityRepository)res.getContents().get(0);
				
				Map<String, Object> slotContents = new HashMap<String, Object>();
				slotContents.put("plan", plan);
				
				// Put the .pml file to parse into the properties
//				String pmlLocation = ((IFile)((IStructuredSelection)selection).getFirstElement()).getLocation().toFile().getAbsolutePath();
//				properties.put("modelFile", pmlLocation);
				properties.put("metaModelPackage", ALICA_PACKAGE_FILE);
				properties.put("srcGenPath", desPath);
				
				WorkflowRunner runner = new WorkflowRunner();
				
				if(!runner.run(WORKFLOW_FILE , new NullProgressMonitor(), properties, slotContents))
				{
					errors.add(new Status(IStatus.ERROR,
							CodeGenActivator.PLUGIN_ID,
							"Codegeneration failed."));					
				}
				
				monitor.worked(1);
			}
			
			if(!monitor.isCanceled())
			{
				if (!errors.isEmpty())
				{
					MultiStatus status = new MultiStatus(
							CodeGenActivator.PLUGIN_ID, 42, errors
							.toArray(new IStatus[errors.size()]),
							"There were errors during codegeneration", null);
					returnStatus = status;
				} 
				else
				{
					returnStatus = new Status(IStatus.OK,CodeGenActivator.PLUGIN_ID,"Codegeneration successful.");
				}
			}
			
			monitor.done();

			return returnStatus;
		}
		
	}
	
	public static final String ALICA_PACKAGE_FILE = "de.uni_kassel.cn.alica.AlicaPackage";
	
	public static final String WORKFLOW_FILE = "utilityGenerationWorkflow.oaw";

	public Object execute(ExecutionEvent event) throws ExecutionException
	{

		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
				.getCurrentSelection(event);
		
		if (!selection.isEmpty())
		{
			final Shell activeShell = HandlerUtil.getActiveShell(event);

			String base = PlanDesignerActivator.getDefault()
					.getPreferenceStore().getString(
							IEditorConstants.PREF_CODEGEN_BASE_PATH);

			final IResource basePath = ResourcesPlugin.getWorkspace().getRoot()
					.findMember(base);
			if (basePath == null)
			{
				showPathNotFoundError(activeShell, base);
			} else
			{
//				String destPath = basePath.getLocation().append(File.separator).toOSString();
				
				List<IFile> selectedFiles = new ArrayList<IFile>();
				
				for(Iterator<?> iter = selection.iterator(); iter.hasNext();)
				{
					Object next = iter.next();
					if(next instanceof IFile)
					{
						selectedFiles.add((IFile)next);
					}
				}
				
				final GenerateCodeJob generateCodeJob = new GenerateCodeJob(selectedFiles);
				generateCodeJob.addJobChangeListener(new JobChangeAdapter()
				{
					@Override
					public void done(final IJobChangeEvent event)
					{
						if(event.getResult().isOK())
						{
							Display.getDefault().asyncExec(new Runnable()
							{
								public void run()
								{
									MessageDialog.openInformation(activeShell,
											"Codegeneration successful",
											"Codegeneration of utilities was successful!");
								}
							});
						}
						else
						{
							if(event.getResult() != Status.CANCEL_STATUS)
							{
								Display.getDefault().asyncExec(new Runnable()
								{
									public void run()
									{
										showCodegenerationErrorMessage(activeShell, event.getResult());
									}
								});
							}
						}
						
						try
						{
							basePath.refreshLocal(IResource.DEPTH_ONE, null);
						} catch (CoreException e)
						{
							CodeGenActivator.getDefault().getLog().log(
									new Status(IStatus.ERROR, CodeGenActivator.PLUGIN_ID, "Error while refreshing workspace", e));
						}
						
					}
				});
				generateCodeJob.schedule();
			}

		}

		return null;
	}

	

	private void showCodegenerationErrorMessage(final Shell activeShell, IStatus status)
	{
		ErrorDialog.openError(activeShell, 
				"Error(s) during codegeneration", 
				"It was not possible to generate expression validators for one or more plans", 
				status); 
	}

	private void showPathNotFoundError(final Shell activeShell, final String basePath) {
		activeShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				MessageDialog
						.openError(
								activeShell,
								"Path not found",
								"The base path for codegeneration couldn't be found: "
										+ basePath
										+ "\nCheck the preferences for the correct path!");
			}
		});

	}

}
