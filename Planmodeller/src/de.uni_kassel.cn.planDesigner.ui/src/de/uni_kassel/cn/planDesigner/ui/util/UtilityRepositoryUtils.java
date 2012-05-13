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
 * 
 */
package de.uni_kassel.cn.planDesigner.ui.util;

import java.io.IOException;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import de.uni_kassel.cn.alica.AlicaFactory;
import de.uni_kassel.cn.alica.UtilityRepository;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.alica.util.AlicaSerializationHelper;
import de.uni_kassel.cn.planDesigner.ui.commands.ExecuteAndSaveResourceCommand;
import de.uni_kassel.cn.planDesigner.ui.commands.SwitchResourceContentsCommand;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;

/**
 * @author till
 * 
 */
public class UtilityRepositoryUtils
{
	public static UtilityRepository getRepository(PMLTransactionalEditingDomain domain)
	{
		return (UtilityRepository) getResource(domain).getContents().get(0);
	}

	public static URI getResourceURI()
	{
		IFile repoFile = CommonUtils.getConfigFolder(true).getFile(new Path(IEditorConstants.UTILITY_REPOSITORY_FILE));
		return URI.createPlatformResourceURI(repoFile.getFullPath().toString(), true);
	}

	public static Resource getResource()
	{
		ResourceSet rset = new AlicaResourceSet();
		return rset.getResource(getResourceURI(), true);
	}

	public static Resource getResource(PMLTransactionalEditingDomain domain)
	{
		IFile repoFile = CommonUtils.getConfigFolder(true).getFile(new Path(IEditorConstants.UTILITY_REPOSITORY_FILE));
		Resource repoRes = null;
		if (!repoFile.exists())
		{
			repoRes = domain.getResourceSet().createResource(org.eclipse.emf.common.util.URI.createPlatformResourceURI(repoFile.getFullPath().toString(), true));
			UtilityRepository repository = AlicaFactory.eINSTANCE.createUtilityRepository();
			domain.getCommandStack().execute(new SwitchResourceContentsCommand(domain, repoRes, null, repository));

			// Save
			try
			{
				repoRes.save(AlicaSerializationHelper.getInstance().getLoadSaveOptions());
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		} else
		{
			repoRes = domain.getResourceSet().getResource(org.eclipse.emf.common.util.URI.createPlatformResourceURI(repoFile.getFullPath().toString(), true), true);
		}

		return repoRes;
	}

	public static Map<Object, Object> getLoadSaveOptions()
	{
		return AlicaSerializationHelper.getInstance().getLoadSaveOptions();
	}

	public static void saveResource(PMLTransactionalEditingDomain domain)
	{
		try
		{
			getResource(domain).save(getLoadSaveOptions());
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void execCommandAndSaveResource(Command command, CommandStack cs, PMLTransactionalEditingDomain domain)
	{
		ExecuteAndSaveResourceCommand c = new ExecuteAndSaveResourceCommand(command, getResource(domain), getLoadSaveOptions());
		cs.execute(c);
	}
}
