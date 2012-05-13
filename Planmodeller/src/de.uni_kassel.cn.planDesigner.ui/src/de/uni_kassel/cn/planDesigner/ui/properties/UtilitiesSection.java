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
package de.uni_kassel.cn.planDesigner.ui.properties;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.impl.UtilityReferenceImpl;
import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.TreeContentProvider;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.TreeLabelProvider;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.ITreeNode;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.MPlanTreeNode;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.MUtilityTreeNode;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.util.UtilityRepositoryUtils;

/**
 * 
 * @author till
 * 
 */
public class UtilitiesSection extends PMLPropertySection
{
	private static final String MASTERDETAILSBLOCK_SECTION_NAME = PropertiesMessages.getString("UtilitiesSection_masterdetailsblock_section_name"); //$NON-NLS-1$

	private TreeViewer treeViewer;

	private ManagedForm managedForm;

	@Override
	public void createControls(Composite parent, TabbedPropertySheetPage tabbedPropertySheetPage)
	{

		super.createControls(parent, tabbedPropertySheetPage);

		Composite composite = getWidgetFactory().createFlatFormComposite(parent);

		FormData fd = new FormData();
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(100, 0);
		fd.right = new FormAttachment(100, 0);
		fd.left = new FormAttachment(0, 0);
		Composite comp = getWidgetFactory().createComposite(composite);
		comp.setLayoutData(fd);
		comp.setLayout(new FillLayout());

		managedForm = new ManagedForm(getWidgetFactory(), getWidgetFactory().createScrolledForm(comp));
		MasterDetailsBlock mdb = new MasterDetailsBlock()
		{

			@Override
			protected void createMasterPart(final IManagedForm managedForm, Composite parent)
			{
				FormToolkit tk = managedForm.getToolkit();

				parent.setLayout(new FillLayout());

				Section sec = tk.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR);
				sec.setText(MASTERDETAILSBLOCK_SECTION_NAME); //$NON-NLS-1$
				sec.setLayout(new FillLayout());

				Composite c = tk.createComposite(sec, SWT.WRAP);
				c.setLayout(new FillLayout());

				tk.paintBordersFor(c);
				sec.setClient(c);

				final SectionPart sp = new SectionPart(sec);
				managedForm.addPart(sp);

				final Tree tree = tk.createTree(c, SWT.H_SCROLL | SWT.V_SCROLL);
				tree.setLinesVisible(true);
				tree.addListener(SWT.Selection, getEditController());

				treeViewer = new TreeViewer(tree);
				treeViewer.setContentProvider(new TreeContentProvider());
				treeViewer.setLabelProvider(new TreeLabelProvider());
				treeViewer.addSelectionChangedListener(new ISelectionChangedListener()
				{
					public void selectionChanged(SelectionChangedEvent event)
					{
						if (event.getSelection() instanceof IStructuredSelection)
						{
							IStructuredSelection sel = (StructuredSelection) event.getSelection();
							if (sel.getFirstElement() instanceof MUtilityTreeNode)
							{
								MUtilityTreeNode tn = (MUtilityTreeNode) sel.getFirstElement();
								managedForm.fireSelectionChanged(sp, new StructuredSelection(tn.getModelObject()));
							}
						}
					}
				});
			}

			@Override
			protected void createToolBarActions(IManagedForm managedForm)
			{
				final ScrolledForm form = managedForm.getForm();

				Action addAction = new Action("new", Action.AS_PUSH_BUTTON)
				{
					public void run()
					{
						IHandlerService handlerService = (IHandlerService) getPart().getSite().getService(IHandlerService.class);
						try
						{
							handlerService.executeCommand("de.uni_kassel.cn.planDesigner.ui.addUtilityCommand", null);
						} catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				};
				addAction.setText("Add Utility");
				addAction.setToolTipText("Adds a new utility to the plan");
				addAction.setImageDescriptor(PlanDesignerActivator.getDefault().getImageRegistry().getDescriptor(IEditorConstants.ICON_UTILITY_ADD_16));
				form.getToolBarManager().add(addAction);

				// TODO: remove relies on the treeviewer selection sent via the
				// ExecutionEvent... think about define the handler here
				// Action removeAction = new Action("new",
				// Action.AS_PUSH_BUTTON)
				// {
				// public void run()
				// {
				// IHandlerService handlerService = (IHandlerService)
				// getPart().getSite().getService(IHandlerService.class);
				// try
				// {
				// handlerService.executeCommand("de.uni_kassel.cn.planDesigner.ui.removeUtilityCommand",
				// null);
				// } catch (Exception e)
				// {
				// e.printStackTrace();
				// }
				// }
				// };
				// removeAction.setText("Remove Utility");
				// removeAction.setToolTipText("Remove the current utility from the plan");
				// removeAction.setImageDescriptor(PlanDesignerActivator.getDefault().getImageRegistry().getDescriptor(IPMLEditorConstants.ICON_UTILITY_REMOVE_16));
				// form.getToolBarManager().add(removeAction);

				form.updateToolBar();
			}

			@Override
			protected void registerPages(DetailsPart detailsPart)
			{
				final UtilityDetailsPage detailsPage = getDetailsPage();
				detailsPart.registerPage(UtilityReferenceImpl.class, detailsPage);
			}

		};

		mdb.createContent(managedForm);

		createContextMenu();

		refresh();
	}

	/**
	 * creates and registers the context menu
	 */
	private void createContextMenu()
	{
		MenuManager menuMgr = new MenuManager();

		Control control = treeViewer.getControl();
		Menu contextMenu = menuMgr.createContextMenu(control);
		control.setMenu(contextMenu);

		// Add the marker where other plugins can contribute new actions
		menuMgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		PlanDesignerActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getPartService().getActivePart().getSite().registerContextMenu(IEditorConstants.PML_UTILITY_ID + ".utilitiesContextMenu", menuMgr, treeViewer); //$NON-NLS-1$
	}

	private UtilityDetailsPage detailsPage;

	/**
	 * @param object 
	 * @return
	 */
	protected UtilityDetailsPage getDetailsPage()
	{
		if (this.detailsPage == null)
		{
			this.detailsPage = new UtilityDetailsPage()
			{
				private EObject model;
				
				@Override
				protected void saveResource()
				{
					UtilitiesSection.this.saveResource();
				}

				@Override
				protected CommandStack getCommandStack()
				{
					return UtilitiesSection.this.getCommandStack();
				}

				@Override
				protected EditController getEditController()
				{
					return UtilitiesSection.this.getEditController();
				}

				@Override
				protected PMLTransactionalEditingDomain getEditingDomain()
				{
					return UtilitiesSection.this.getEditingDomain();
				}

				@Override
				protected AbstractPlan getModel()
				{
					return (AbstractPlan) this.model;
				}

				@Override
				public void setModel(AbstractPlan model)
				{
					this.model = model;
				}

			};
		}
		return this.detailsPage;
	}

	@Override
	protected void basicSetInput(Object input)
	{
		super.basicSetInput(input);
		getDetailsPage().setModel((AbstractPlan)input);

		getEditController().addToObject(getModel());
		getTreeViewer().setInput(getRootNode());

		refresh();
	}

	private ITreeNode rootNode;

	private ITreeNode getRootNode()
	{
		this.rootNode = new MPlanTreeNode(getEditingDomain(), getCommandStack(), getEditController(), null, getModel());
		return this.rootNode;
	}

	public TreeViewer getTreeViewer()
	{
		return treeViewer;
	}

	@Override
	protected void updateView(Notification n)
	{
		final Object notifier = n.getNotifier();
		if (notifier instanceof AbstractPlan)
		{
			refresh();
		} else if (notifier instanceof UtilityReference)
		{
			refresh((UtilityReference) notifier);
			refresh();
		} else if (notifier instanceof Utility || notifier instanceof UtilityMode)
		{
			getDetailsPage().refresh();
			refresh();
		} else if (notifier instanceof UtilityModeExpression)
		{
			getDetailsPage().refresh();
			getDetailsPage().expand(notifier);
			refresh();
		}
	}

	@Override
	protected void focusOutEvent(Widget source)
	{
		commitAndSave();
	}

	@Override
	protected void modifyEvent(Widget source)
	{
		commitAndSave();
	}

	@Override
	protected void selectionEvent(Object source)
	{
		 commitAndSave();
	}

	@Override
	protected void enterPressed(Widget source)
	{
		commitAndSave();
	}

	private void commitAndSave()
	{
		managedForm.commit(false);
		saveResource();
	}

	private void saveResource()
	{
		UtilityRepositoryUtils.saveResource(getEditingDomain());
	}

	@Override
	public boolean shouldUseExtraSpace()
	{
		return true;
	}

	@Override
	public void refresh()
	{
		super.refresh();

		getTreeViewer().getTree().getShell().getDisplay().asyncExec(new Runnable(){
			public void run() {
				getTreeViewer().refresh(true);

				Tree tree = getTreeViewer().getTree();
				if (tree.getItemCount() > 0)
				{
					tree.select(tree.getItem(0));
				}
			}
		});
	}

	// FIXME: doesnt work... the node is not present when this is called
	private void refresh(UtilityReference notifier)
	{
		refresh(); // TODO: update single node

		Tree tree = getTreeViewer().getTree();

		int i = tree.getItemCount() - 1;
		for (TreeItem item : tree.getItems())
		{
			MUtilityTreeNode node = (MUtilityTreeNode) item.getData();
			if (notifier == node.getModelObject())
			{
				break;
			}

			i--;
		}

		if (i > 0)
		{
			tree.select(tree.getItem(i));
		} else if (i == 0)
		{
			tree.select(tree.getItem(0));
		}
	}
}
