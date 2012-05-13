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
package de.uni_kassel.cn.plandesigner.update;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.commands.operations.OperationStatus;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.PlatformUI;
import org.eclipse.update.configuration.IConfiguredSite;
import org.eclipse.update.configuration.ILocalSite;
import org.eclipse.update.core.IFeature;
import org.eclipse.update.core.IFeatureReference;
import org.eclipse.update.core.IURLEntry;
import org.eclipse.update.core.SiteManager;
import org.eclipse.update.core.VersionedIdentifier;
import org.eclipse.update.standalone.InstallCommand;
import org.eclipse.update.standalone.ScriptedCommand;
import org.eclipse.update.standalone.UpdateCommand;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;



public class UpdateAction implements IWorkbenchWindowActionDelegate {
	
	// Prefix that all planDesigner features must have
	private static final String APP_PREFIX = "de.uni_kassel.cn.planDesigner";
	
	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	
	private static DocumentBuilder builder ;
	
	static {
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	// Map of read site.xml docs
	private Map<URL, Document> docMap = new HashMap<URL, Document>();
	
	private Shell shell;
	
	// Indicates a needed restart
	private boolean restart;
	
	// Indicates if the designer should be updated in case there are updates
	private boolean shouldUpdate;
	
	private boolean updateNeeded;

	public void dispose() {
		// TODO Auto-generated method stub

	}

	public void init(IWorkbenchWindow window) {
		if(window != null)
			shell = window.getShell();
	}

	public void run(IAction action) {
		final MultiStatus status = new MultiStatus(UpdateActivator.PLUGIN_ID, 90 , "Errors during application update", null);
		
		// Define the operation
		IRunnableWithProgress op = new IRunnableWithProgress(){
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {
				updateInstallation(status, monitor);
			}
		};
		
		// Run the update within a progressMonitor
		try {
			new ProgressMonitorDialog(shell).run(true, true, op);
		} catch (InvocationTargetException e) {
			status.add(new OperationStatus(
					OperationStatus.ERROR,
					UpdateActivator.PLUGIN_ID, 94,
					"Application faild with error", e));
		} catch (InterruptedException e) {
			status.add(new OperationStatus(
					OperationStatus.CANCEL,
					UpdateActivator.PLUGIN_ID, 95,
					"Plan Designer update cancelled", null));
		}
		
		if(!status.isOK()){
			ErrorDialog dialog = new ErrorDialog(shell,
					"Update", "Error during automatic update", status, OperationStatus.ERROR | OperationStatus.WARNING);
			dialog.open();
		}else if(!updateNeeded){
			// No updates available
			shell.getDisplay().syncExec(new Runnable(){
				public void run() {
					MessageDialog.openInformation(
							shell, 
							"Update", 
							"There are no updates available at the moment. Please try again later.");
				}
			});
		} else {
			// Inform the user of a needed restart
			if(restart && shell != null){
				shell.getDisplay().syncExec(new Runnable(){
					public void run() {
						restart = MessageDialog.openQuestion(
								shell, 
								"Update", 
								"Plan Designer was successfully updated.\n"
								+ "It is recommended to restart the application.\n"
								+"Restart now?");
					}
				});
			}
			
			if(restart)
				try {
					PlatformUI.getWorkbench().restart();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	
	/**
	 * Updates all PlanDesigner features
	 * @param status
	 * @param monitor
	 */
	private void updateInstallation(final MultiStatus status, IProgressMonitor monitor){
		try {
			// Start of the update progress
			monitor.beginTask("Please wait while Plan Designer is updating", 1110000);
			
			// Clear the docMap
			docMap.clear();
			
			// Local site
			ILocalSite localSite = SiteManager.getLocalSite();
			
			// Configure sites
			IConfiguredSite[] sites = localSite.getCurrentConfiguration().getConfiguredSites();
			
			// Determine discovery sites and installed features
			Set<String> installedFeatures = new HashSet<String>();
			Map<URL, IConfiguredSite> discoverySites = new HashMap<URL, IConfiguredSite>();
			
			for(IConfiguredSite site : sites){
				// Collect installed features
				for(IFeatureReference featureRef : site.getFeatureReferences()){
					VersionedIdentifier featureIdentifier = featureRef.getVersionedIdentifier();
					
					String id = featureIdentifier.getIdentifier();
					installedFeatures.add(id);
					
					// Collect discovery sites
					if(id.startsWith(APP_PREFIX)){
						IFeature feature = featureRef.getFeature(null);
						IURLEntry[] discoverySiteEntries = feature.getDiscoverySiteEntries();
						for(IURLEntry entry : discoverySiteEntries){
							if(entry.getType() == IURLEntry.UPDATE_SITE)
								discoverySites.put(entry.getURL(), site);
						}
					}
				}
			}
			
			monitor.worked(10000);
			
			// Read manifests of feature update sites
			monitor.subTask("Checking for feature updates");
			List<String> toBeUpdated = readSiteManifests(sites, status);
			
			monitor.worked(50000);
			
			// Search for new features in discovery-sites
			monitor.subTask("Checking for new features");
			
			Map<String, InstallCommand> commands = readDiscoverySiteManifests(discoverySites, installedFeatures, status);
			
			monitor.worked(50000);
			
			// set the updateNeededFlag
			updateNeeded = !(toBeUpdated.isEmpty() && commands.isEmpty());
			
			// Ask the user if he wants to proceed
			if(updateNeeded)
				shouldUpate(toBeUpdated, commands);
			
			if(!shouldUpdate){
				monitor.done();
				return;
			}
			
			int increment = 1000000 / (Math.max(1, toBeUpdated.size() + commands.size()));
			
			// Update all features
			restart = toBeUpdated.size() > 0;
			
			for(String id : toBeUpdated){
				UpdateCommand uc = new UpdateCommand(id,"false");
				restart &= performCommand(monitor, increment, uc, "Updating", id, status);
			}
			
			for(String id : commands.keySet()){
				restart |= performCommand(monitor, increment, commands.get(id), "Installing", id, status);
			}

		} catch (Exception e){
			status.add(new OperationStatus(OperationStatus.ERROR, UpdateActivator.PLUGIN_ID, 93, 
					"Plan Designer update failed with error", e));
		} finally{
			monitor.done();
		}
	}
	
	private void shouldUpate(final List<String> toBeUpdated, final Map<String, InstallCommand> commands) {
		shell.getDisplay().syncExec(new Runnable(){
			public void run() {
				boolean moreThanOne = (toBeUpdated.size() + commands.size()) > 1;
				StringBuffer buf = new StringBuffer();
				buf.append("There ");
				buf.append(moreThanOne ? "are " +(toBeUpdated.size() + commands.size()) : "is 1");
				buf.append(" Update");
				if(moreThanOne)
					buf.append("s ");
				else
					buf.append(" ");
				buf.append("available.\n");
				buf.append("Do you want to install ");
				if(moreThanOne)
					buf.append("them?");
				else
					buf.append("it?");
				
				shouldUpdate = MessageDialog.openQuestion(
						shell, 
						"Update", 
						buf.toString());
			}
		});
	}

	/**
	 * Execute the command
	 * @param monitor
	 * @param increment
	 * @param command
	 * @param string
	 * @param id
	 * @param status
	 * @return
	 */
	private boolean performCommand(IProgressMonitor monitor, int increment,
			ScriptedCommand command, String op, String id, MultiStatus status) {
		
		try{
			monitor.subTask(NLS.bind("{0} Feature {1}", op, id));
			
			// Execute
			boolean success = command.run(monitor);
			if(!success){
				status.add(new OperationStatus(
						OperationStatus.WARNING,
						UpdateActivator.PLUGIN_ID,
						91,
						NLS.bind("Update of Feature {0} failed", id), null));
			}
			return success;
		}catch (Exception e){
			status.add(new OperationStatus(
					OperationStatus.ERROR,
					UpdateActivator.PLUGIN_ID,
					92,
					NLS.bind("{0} Feature {1} failed with error", id), e));
		} finally{
			monitor.worked(increment);
		}
		
		return false;
	}

	/**
	 * Read and process discovery site manifest files
	 * @param discoverySites
	 * @param installedFeatures
	 * @param status
	 * @return
	 */
	private Map<String, InstallCommand> readDiscoverySiteManifests(
			Map<URL, IConfiguredSite> discoverySites,
			Set<String> installedFeatures, MultiStatus status) throws Exception {
	
		Map<String, InstallCommand> commands = new HashMap<String, InstallCommand>();
		
		for(URL discoveryURL : discoverySites.keySet()){
			Document siteDoc = readSiteDoc(discoveryURL, status);
			
			// Search for feature in site.xml
			NodeList nl = siteDoc.getElementsByTagName("feature");
			
			for(int i=0; i < nl.getLength(); i++){
				Element remoteFeature = (Element)nl.item(i);
				
				String remoteFeatureId = remoteFeature.getAttribute("id");
				
				// Is the feature already installed?
				if(!installedFeatures.contains(remoteFeatureId)){
					installedFeatures.add(remoteFeatureId);
					
					// Version
					String version = remoteFeature.getAttribute("version");
					
					// Site, which contained the feature, which referenced the discovery site
					IConfiguredSite targetSite = discoverySites.get(discoveryURL);
					
					// Contruct the installCommand
					InstallCommand ic = new InstallCommand(
							remoteFeatureId, 
							version, 
							discoveryURL.toString(), 
							targetSite.getSite().getURL().getFile().toString(),
							"false");
					
					commands.put(remoteFeatureId, ic);
				}
			}
		}
		
		return commands;
	}

	/**
	 * Read and process the update site manifest files
	 * @param sites
	 * @param status
	 * @return
	 */
	private List<String> readSiteManifests(IConfiguredSite[] sites, MultiStatus status) throws CoreException{
		// Save all features that will be updated
		List<String> todo = new ArrayList<String>();
		
		for(IConfiguredSite site : sites){
			for(IFeatureReference featureRef : site.getFeatureReferences()){
				// If the feature is disabled we won't look for updates
				if(!site.isConfigured(featureRef.getFeature(null)))
					continue;
				
				VersionedIdentifier featureIdentifier = featureRef.getVersionedIdentifier();
								
				String featureId = featureIdentifier.getIdentifier();
				
				// Extract update site
				IFeature feature = featureRef.getFeature(null);
				IURLEntry updateSiteEntry = feature.getUpdateSiteEntry();
				
				if(updateSiteEntry == null)
					continue;
				
				Document siteDoc = readSiteDoc(updateSiteEntry.getURL(), status);
				
				// Search for feature in site.xml
				NodeList nl = siteDoc.getElementsByTagName("feature");
				
				for(int i=0; i < nl.getLength(); i++){
					Element remoteFeature = (Element)nl.item(i);
					
					if("true".equals(remoteFeature.getAttribute("patch")))
						continue;
					
					String remoteFeatureId = remoteFeature.getAttribute("id");
					
					// Same ID?
					if(remoteFeatureId.equals(featureId)){
						String version = remoteFeature.getAttribute("version");
						VersionedIdentifier remoteIdentifier = new VersionedIdentifier(remoteFeatureId, version);
						
						// New version available?
						if(remoteIdentifier.getVersion().isGreaterThan(featureIdentifier.getVersion())){
							todo.add(featureId);
							// TODO: Is this break right here? Don't just update only the first feature?
							break;
						}
					}
				}
			}
		}
		
		return todo;
	}

	/**
	 * Read the site manifest file
	 * @param url
	 * @param status
	 * @return
	 */
	private Document readSiteDoc(URL url, MultiStatus status) {
		// Already read?
		Document siteDoc = docMap.get(url);
		if(siteDoc == null){
			try {
			// Read in the site.xml
			String s = url.toString();
			if(!s.endsWith("/site.xml")){
				if(!s.endsWith("/"))
					s += "/";
				
				s += "site.xml";
			}
			
			
				URL siteUrl = new URL(s);
				InputStream is = siteUrl.openStream();
				try{
					siteDoc = builder.parse(is);	
				} finally{
					is.close();
				}
			} catch (Exception e) {
				siteDoc = builder.newDocument();
				status.add(new OperationStatus(
						OperationStatus.WARNING,
						UpdateActivator.PLUGIN_ID,
						94,
						NLS.bind("Problems accessing site {0}", url),
						e));
			}
			docMap.put(url, siteDoc);
		}
		
		return siteDoc;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

	public boolean isRestart() {
		return restart;
	}

}
