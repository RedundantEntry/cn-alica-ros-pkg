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
package de.uni_kassel.cn.planDesigner.ui.edit;

import org.eclipse.core.commands.operations.OperationHistoryFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl.FactoryImpl;

import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.planDesigner.ui.commands.EMF2GEFCommandStack;

public class PMLTransactionalEditingDomainFactory extends FactoryImpl {
	
	public TransactionalEditingDomain createEditingDomain() {
		PMLTransactionalEditingDomain result = new PMLTransactionalEditingDomain(
				new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE),
				new EMF2GEFCommandStack(OperationHistoryFactory.getOperationHistory()),
				new AlicaResourceSet());
			
			mapResourceSet(result);
			
			return result;
	}

	

}
