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

import org.eclipse.core.runtime.CoreException;
import org.eclipse.update.core.BaseInstallHandler;
import org.eclipse.update.core.IFeature;
import org.eclipse.update.core.IFeatureContentConsumer;
import org.eclipse.update.core.IInstallHandlerEntry;
import org.eclipse.update.core.INonPluginEntry;
import org.eclipse.update.core.IPluginEntry;
import org.eclipse.update.core.IVerificationListener;
import org.eclipse.update.core.InstallMonitor;

public class PlanDesignerInstallHandler extends BaseInstallHandler {

	@Override
	public void completeConfigure() throws CoreException {
		// TODO Auto-generated method stub
		super.completeConfigure();
	}

	@Override
	public void completeInstall(IFeatureContentConsumer consumer)
			throws CoreException {
		// TODO Auto-generated method stub
		super.completeInstall(consumer);
	}

	@Override
	public void completeUnconfigure() throws CoreException {
		// TODO Auto-generated method stub
		super.completeUnconfigure();
	}

	@Override
	public void completeUninstall() throws CoreException {
		// TODO Auto-generated method stub
		super.completeUninstall();
	}

	@Override
	public void configureCompleted(boolean success) throws CoreException {
		// TODO Auto-generated method stub
		super.configureCompleted(success);
	}

	@Override
	public void configureInitiated() throws CoreException {
		// TODO Auto-generated method stub
		super.configureInitiated();
	}

	@Override
	public void initialize(int type, IFeature feature,
			IInstallHandlerEntry entry, InstallMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		super.initialize(type, feature, entry, monitor);
	}

	@Override
	public void installCompleted(boolean success) throws CoreException {
		// TODO Auto-generated method stub
		super.installCompleted(success);
	}

	@Override
	public void installInitiated() throws CoreException {
		// TODO Auto-generated method stub
		super.installInitiated();
	}

	@Override
	public void nonPluginDataDownloaded(INonPluginEntry[] nonPluginData,
			IVerificationListener listener) throws CoreException {
		// TODO Auto-generated method stub
		super.nonPluginDataDownloaded(nonPluginData, listener);
	}

	@Override
	public void pluginsDownloaded(IPluginEntry[] plugins) throws CoreException {
		// TODO Auto-generated method stub
		super.pluginsDownloaded(plugins);
	}

	@Override
	public void unconfigureCompleted(boolean success) throws CoreException {
		// TODO Auto-generated method stub
		super.unconfigureCompleted(success);
	}

	@Override
	public void unconfigureInitiated() throws CoreException {
		// TODO Auto-generated method stub
		super.unconfigureInitiated();
	}

	@Override
	public void uninstallCompleted(boolean success) throws CoreException {
		// TODO Auto-generated method stub
		super.uninstallCompleted(success);
	}

	@Override
	public void uninstallInitiated() throws CoreException {
		// TODO Auto-generated method stub
		super.uninstallInitiated();
	}
	
	

}
