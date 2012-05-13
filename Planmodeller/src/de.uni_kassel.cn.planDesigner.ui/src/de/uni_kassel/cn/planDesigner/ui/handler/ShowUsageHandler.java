package de.uni_kassel.cn.planDesigner.ui.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.UsageCrossReferencer;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import de.uni_kassel.cn.alica.PlanElement;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.util.PlanEditorUtils;
import de.uni_kassel.cn.planDesigner.ui.util.UsageDialog;

public class ShowUsageHandler extends AbstractHandler implements IHandler {

	public Object execute(ExecutionEvent event) throws ExecutionException {
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil.getCurrentSelection(event);
		if (selection.isEmpty())
			return null;
		
		// find all references to the selected elements
		TransactionalEditingDomain editingDomain =  (PMLTransactionalEditingDomain)TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(IEditorConstants.PML_TRANSACTIONAL_EDITING_DOMAIN_ID);
		
		List<EObject> resolvedList = loadUsages(selection, editingDomain);
		
//		List<Resource> resources = editingDomain.getResourceSet().getResources();
//		for (Resource res : resources) {
//			if (res.getURI().toString().contains("TestLSThrowinMaster.pml"))
//			{
//				System.out.println("Found2: " + res.getURI().toString());
//			}
//		}
		
		Map<EObject, Collection<EStructuralFeature.Setting>> referenceMap = UsageCrossReferencer.findAll(resolvedList
				, editingDomain.getResourceSet());
		
		// filtered map (only PlanElements)
		List<TreeNode> usages = new ArrayList<TreeNode>();
		for (EObject obj : referenceMap.keySet()) {
			if (obj instanceof PlanElement)
			{
				TreeNode tn = new TreeNode((PlanElement) obj);
				List<TreeNode> childrenList = new ArrayList<TreeNode>();
				for (EStructuralFeature.Setting setting : referenceMap.get(obj)) {
					if (setting.getEObject() instanceof PlanElement) {
						childrenList.add(new TreeNode((PlanElement)setting.getEObject()));
					}
				}
				tn.setChildren(childrenList.toArray(new TreeNode[0]));
				usages.add(tn);
			}
		}

		// for debugging
//		for (TreeNode tn : usages) {
//			System.out.println(((PlanElement)tn.getValue()).getName() + ":");
//			for (TreeNode child : tn.getChildren()) {
//				System.out.println("\t" + ((PlanElement)child.getValue()).getName());
//			}
//		}
		
		// some strings for the usage dialog
		String titleString;
		String subString;
		if (usages.size() > 1)
		{
			titleString = "Usages of " + usages.size() + " PlanElements";
			subString = "The PlanElements are used at the following places.";
		} else if (usages.size() == 1) {
			String name = ((PlanElement)usages.get(0).getValue()).getName();
			titleString = "Usages of " + name;
			subString = "The PlanElement \"" + name + "\" is used at the following places.";
		} else {
			titleString = "No Usages found";
			subString = "Maybe you want to delete it? :-)";
		}
		
		// generate the dialog
		Shell activeShell = HandlerUtil.getActiveShell(event);
		final UsageDialog dialog = new UsageDialog(activeShell, usages, titleString, subString,
				MessageDialog.INFORMATION);

		// run the dialog
		activeShell.getDisplay().asyncExec(new Runnable() {
			public void run() {
				dialog.open();
			}
		});

		return null;
	}
	
	/**
	 * Load all Elements which could refer to the selected Elements.
	 */
	private List<EObject> loadUsages(IStructuredSelection selection, TransactionalEditingDomain editingDomain) {
		Set<IFile> files = new HashSet<IFile>();
		List selectionList = selection.toList();
		ArrayList<EObject> resolvedList = new ArrayList<EObject>();
		for (Object obj : selectionList)
		{
			URI proxyURI = ((InternalEObject)obj).eProxyURI();
		    if (proxyURI != null)
		    {
		    	resolvedList.add(EcoreUtil.resolve((EObject) obj, editingDomain.getResourceSet()));
		    }
		    else
		    {
		    	resolvedList.add((EObject) obj);
		    }
		}
		
		String ext;
		for (Object obj : resolvedList)
		{
//			if (obj instanceof PlanElement)
//			{
				// I hope, that this avoids null pointer exceptions
				
				Resource ownerResource = editingDomain.loadResource(EcoreUtil.getURI((EObject) obj).toPlatformString(true));
//				Resource ownerResource = ((PlanElement) obj).eResource();
				IFile fileToChange = WorkspaceSynchronizer.getUnderlyingFile(ownerResource);
				ext = fileToChange.getFileExtension();
				
				/*
				 * Load everything which could reference to the file, you want to
				 * move: Move -> Load
				 * Plans (.pml) -> [.pml, .pty, .pmlex]
				 * Behaviours (.beh) -> [.pml, .pmlex]
				 * PlanTypes (.pty) -> [.pml, .pmlex]
				 * RoleDefSet (.rdefset) -> [.rset, .graph, pmlex]
				 * RolesetGraph (.graph) -> [.pmlex]
				 * UI-Arrangements (.pmlex) -> [nothing]
				 * Roleset (.rset) -> [nothing]
				 */
				if (ext.equals("pml")) {
					files.addAll(PlanEditorUtils.collectAllFilesWithExtension("pml", "pty", "pmlex"));
				} else if (ext.equals("beh") || ext.equals("pty")) {
					files.addAll(PlanEditorUtils.collectAllFilesWithExtension("pml", "pmlex"));
				} else if (ext.equals("rdefset")) {
					files.addAll(PlanEditorUtils.collectAllFilesWithExtension("rset", "graph", "pmlex"));
				} else if (ext.equals("graph")) {
					files.addAll(PlanEditorUtils.collectAllFilesWithExtension("pmlex"));
				}
//			}
		}

		// The load operation is idempotent.
		for (IFile file : files) {
			Resource res = ((PMLTransactionalEditingDomain) editingDomain).load(file);
			if (res.getURI().toString().contains("TestLSThrowinMaster.pml"))
			{
				System.out.println("Found: " + res.getURI().toString());
			}
		}
		
		return resolvedList;
	}

}
