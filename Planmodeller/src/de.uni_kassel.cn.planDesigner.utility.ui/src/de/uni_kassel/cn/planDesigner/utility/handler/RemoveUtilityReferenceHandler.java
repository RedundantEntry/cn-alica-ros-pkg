/**
 * 
 */
package de.uni_kassel.cn.planDesigner.utility.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.ui.handlers.HandlerUtil;

import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.ITreeNode;

/**
 * @author till
 * 
 * TODO: remove all children objects(?)
 */
public class RemoveUtilityReferenceHandler extends AbstractHandler
{

	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		EObject modelObject = getModelObject(getDataObject(event));
		
		if(modelObject == null || !(modelObject instanceof UtilityReference))
		{
			return null;
		}
		
		UtilityActivator activator = UtilityActivator.getDefault();
		PMLTransactionalEditingDomain domain = activator.getEditingDomain();

		activator.getDefaultUtilityEditController().removeFromObject(modelObject);
		
		Command del = DeleteCommand.create(domain, modelObject);
		UtilityRepositoryUtils.execCommandAndSaveResource(del, domain.getCommandStack(), domain);
		
		return null;
	}

	private Object getDataObject(ExecutionEvent e)
	{
		ISelection selection = HandlerUtil.getActiveMenuSelection(e);
		
		if (selection instanceof IStructuredSelection)
		{
			IStructuredSelection sel = (IStructuredSelection) selection;
			return sel.getFirstElement();
		}
		
		if(e.getTrigger() instanceof Event)
		{
			return ((Event)e.getTrigger()).data;
		}
		
		return null;
	}
	
	private EObject getModelObject(Object container)
	{
		if(container != null)
		{
			if (container instanceof ITreeNode)
			{
				ITreeNode node = (ITreeNode) container;
				return (EObject)node.getData();
			}
			
			if(container instanceof EObject)
			{
				return (EObject)container;
			}
		}
		
		return null;
	}
}
