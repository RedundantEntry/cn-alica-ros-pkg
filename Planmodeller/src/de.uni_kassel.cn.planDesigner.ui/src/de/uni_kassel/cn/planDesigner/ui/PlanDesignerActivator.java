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
package de.uni_kassel.cn.planDesigner.ui;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.util.WorkspaceChangeListener;

/**
 * The activator class controls the plug-in life cycle
 */
public class PlanDesignerActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.uni_kassel.cn.planDesigner.ui";

	// The shared instance
	private static PlanDesignerActivator plugin;
	
	/** The color registry handles handles SWT colors for our editor */
	private ColorRegistry colorRegistry;
	
	/** boolean to indicate if colors and images have been initialize */
	private boolean imagesAndColorInitialized = false;
	
	private WorkspaceSynchronizer globalWorkspaceSynchronizer;
	
	private WorkspaceChangeListener workspaceListener;
	
	/**
	 * The constructor
	 */
	public PlanDesignerActivator() {
	}
	
	/**2
	 * Creates an image descriptor and places it in the image registry.
	 */
	private void createImageDescriptor(String id, URL baseURL) {
		URL url = null;
		try {
			url = new URL(baseURL, IEditorConstants.ICON_PATH + id);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		getImageRegistry().put(id, desc);
	}
	
	private void initializeColors(){
		colorRegistry = new ColorRegistry();
		
		colorRegistry.put(IEditorConstants.ENTRY_POINT_BACKGROUND_COLOR, new RGB(68,145,224));
		colorRegistry.put(IEditorConstants.FAILURE_POINT_BACKGROUND_COLOR, new RGB(255,120,0));
		colorRegistry.put(IEditorConstants.PLAN_STATE_BACKGROUND_COLOR, new RGB(255,249,197));
		colorRegistry.put(IEditorConstants.STATE_BACKGROUND_COLOR, new RGB(255,200,0));
		colorRegistry.put(IEditorConstants.SUCCESS_POINT_BACKGROUND_COLOR, new RGB(169,255,3));
		colorRegistry.put(IEditorConstants.PLAN_TYPE_POINT_BACKGROUND_COLOR, new RGB(255,118,106));
		colorRegistry.put(IEditorConstants.PLAN_LABEL_BACKGROUND_COLOR, new RGB(96,137,255));
		colorRegistry.put(IEditorConstants.MASTER_PLAN_LABEL_BACKGROUND_COLOR, new RGB(255,96,96));
		colorRegistry.put(IEditorConstants.FAILURERANSITION_FOREGROUND_COLOR, new RGB(200,76,76));
		colorRegistry.put(IEditorConstants.SUCCESSTRANSITION_FOREGROUND_COLOR, new RGB(91,192,102));
		
		colorRegistry.put(IEditorConstants.PRIORITY_0_COLOR, new RGB(200,0,0));
		colorRegistry.put(IEditorConstants.PRIORITY_1_COLOR, new RGB(255,204,51));
		colorRegistry.put(IEditorConstants.PRIORITY_2_COLOR, new RGB(21,146,0));
		colorRegistry.put(IEditorConstants.PRIORITY_DEFAULT_COLOR, new RGB(208,208,208));
	}
	
	/**
	 * Initializes the table of images used in this plugin.
	 */
	private void initializeImages() {
		URL baseURL = getBundle().getEntry("/"); //$NON-NLS-1$

		// A little reflection magic ... so that we don't
		// have to add the createImageDescriptor every time
		// we add it to the IBPELUIConstants ..
		Field fields[] = IEditorConstants.class.getFields();	
		for(int i=0; i < fields.length; i++) {
			Field f = fields[i];
			if (f.getType() != String.class) { 
				continue;
			}
			String name = f.getName();
			if (name.startsWith("ICON_")) {   //$NON-NLS-1$ //$NON-NLS-2$
				try {
					String value = (String) f.get(null);
					createImageDescriptor(value, baseURL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	private void initialize(){
		if(!imagesAndColorInitialized){
			imagesAndColorInitialized = true;
			initializeColors();
			initializeImages();
		}
	}

	
	private static final String PES_EXTENSION_ID = "de.uni_kassel.cn.planDesigner.planEditorSelection";
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		// Commented out due to bugs of reloading resources that are saved in editors
//		globalWorkspaceSynchronizer = new WorkspaceSynchronizer(TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(
//				IPMLEditorConstants.PML_TRANSACTIONAL_EDITING_DOMAIN_ID), createSynchronizationDelegate());
		plugin = this;
		
		// Refresh the workspace on startup
		ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);

//		ResourcesPlugin.getWorkspace().addResourceChangeListener(getWorkspaceListener(), IResourceDelta.ADDED);		
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
//		ResourcesPlugin.getWorkspace().removeResourceChangeListener(getWorkspaceListener());
		plugin = null;
		ResourcesPlugin.getWorkspace().save(true,null);
//		globalWorkspaceSynchronizer.dispose();
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static PlanDesignerActivator getDefault() {
		return plugin;
	}

	public ColorRegistry getColorRegistry() {
		if(colorRegistry == null){
			initialize();
		}
		return colorRegistry;
	}
	
	@Override
	public ImageRegistry getImageRegistry() {
		ImageRegistry result = super.getImageRegistry();
		initialize();
		return result;
	}
	
	private WorkspaceSynchronizer.Delegate createSynchronizationDelegate() {
		return new WorkspaceSynchronizer.Delegate() {
			public boolean handleResourceDeleted(Resource resource) {
				// Unload the deleted Resource				
				resource.unload();
				// Remove the deleted resource from the resourceset
				ResourceSet rSet = resource.getResourceSet();
				rSet.getResources().remove(resource);
				System.out.println("PDActivator: Resource " +resource +" deleted and removed from the resourceset.");
				return true;
			}	   


			public boolean handleResourceMoved(Resource resource, URI newURI) {
				// Just unload the resource for now
				resource.unload();
				return true;
			}

			public boolean handleResourceChanged(Resource resource) {
				resource.unload();
				System.out.print("PDActivator: Resource " +resource +" changed and unloaded... ");
				try {
					resource.load(resource.getResourceSet().getLoadOptions());
					System.out.println("loaded!");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return true;
			}

			public void dispose() {
				// Nothing for now
			}
		
		};
	}

	private WorkspaceChangeListener getWorkspaceListener() {
		if(workspaceListener == null)
		{
			workspaceListener = new WorkspaceChangeListener();
		}
		return workspaceListener;
	}
}
