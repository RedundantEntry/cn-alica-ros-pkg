package de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerService;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityMode;
import de.uni_kassel.cn.alica.UtilityModeExpression;
import de.uni_kassel.cn.alica.impl.UtilityReferenceImpl;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.EditController;
import de.uni_kassel.cn.planDesigner.ui.properties.PropertiesMessages;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.Constants;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.TreeContentProvider;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.TreeLabelProvider;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.ITreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.MPlanTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.MUtilityTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityRepositoryUtils;

public class UtilityMasterDetailsBlockOld extends MasterDetailsBlock
{
	private static final String	MASTERDETAILSBLOCK_SECTION_NAME	= PropertiesMessages.getString("UtilitiesSection_masterdetailsblock_section_name"); //$NON-NLS-1$

	private ITreeNode			rootNode;

	private TreeViewer			treeViewer;

	private EditController		editController;

	private UtilityDetailsPageOld	detailsPage;

	private IWorkbenchPartSite	site;

	public UtilityMasterDetailsBlockOld(IWorkbenchPartSite workbenchPartSite, EditController controller)
	{
		this.site = workbenchPartSite;
		this.editController = controller;
	}

	@Override
	protected void createMasterPart(final IManagedForm managedForm, Composite parent)
	{
		FormToolkit tk = managedForm.getToolkit();

		parent.setLayout(new FillLayout());

		Section sec = tk.createSection(parent, Section.TITLE_BAR);
		sec.setText(MASTERDETAILSBLOCK_SECTION_NAME);
		sec.setLayout(new FillLayout());
		sec.setTextClient(createToolBar(sec));

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
		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event)
			{
				// clear selection
				// managedForm.fireSelectionChanged(sp,
				// StructuredSelection.EMPTY); // this causes flaws in
				// the UI

				// set new selection
				if (event.getSelection() instanceof IStructuredSelection)
				{
					IStructuredSelection sel = (StructuredSelection) event.getSelection();
					if (sel.getFirstElement() instanceof MUtilityTreeNode)
					{
						MUtilityTreeNode tn = (MUtilityTreeNode) sel.getFirstElement();
						managedForm.fireSelectionChanged(sp, new StructuredSelection(tn.getData()));
					}
				}
			}
		});

		createContextMenu();
	}

	private ToolBar createToolBar(Composite parent)
	{
		ToolBarManager tbm = new ToolBarManager(SWT.FLAT);
		ToolBar tb = tbm.createControl(parent);
		Action addUtilityAction = new Action("new", Action.AS_PUSH_BUTTON) {
			public void run()
			{
				IHandlerService handlerService = (IHandlerService) UtilityActivator.getDefault().getWorkbench().getService(IHandlerService.class);
				try
				{
					handlerService.executeCommand("de.uni_kassel.cn.planDesigner.utility.addUtilityCommand", null);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		};

		addUtilityAction.setText("Add Utility");
		addUtilityAction.setToolTipText("Adds a new utility to the plan");
		addUtilityAction.setImageDescriptor(UtilityActivator.getDefault().getImageRegistry().getDescriptor(Constants.ICON_UTILITY_ADD_16));

		tbm.add(addUtilityAction);
		tbm.update(true);

		return tb;
	}

	@Override
	protected void createToolBarActions(IManagedForm managedForm)
	{
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
	}

	@Override
	protected void registerPages(DetailsPart detailsPart)
	{
		final IDetailsPage detailsPage = getDetailsPage();
		detailsPart.registerPage(UtilityReferenceImpl.class, detailsPage);
	}

	/**
	 * @param object
	 * @return
	 */
	protected UtilityDetailsPageOld getDetailsPage()
	{
		if (this.detailsPage == null)
		{
			this.detailsPage = new UtilityDetailsPageOld() {
				private EObject	model;

				@Override
				protected void saveResource()
				{
					UtilityRepositoryUtils.saveResource(getEditingDomain());
				}

				@Override
				protected CommandStack getCommandStack()
				{
					return UtilityMasterDetailsBlockOld.this.getCommandStack();
				}

				@Override
				protected EditController getEditController()
				{
					return editController;
				}

				@Override
				protected PMLTransactionalEditingDomain getEditingDomain()
				{
					return UtilityMasterDetailsBlockOld.this.getEditingDomain();
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

	private EditController getEditController()
	{
		return editController;
	}

	protected CommandStack getCommandStack()
	{
		return UtilityActivator.getDefault().getEditingDomain().getCommandStack();
	}

	protected PMLTransactionalEditingDomain getEditingDomain()
	{
		return UtilityActivator.getDefault().getEditingDomain();
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
		this.site.registerContextMenu("de.uni_kassel.cn.planDesigner" + ".utilitiesContextMenu", menuMgr, this.treeViewer); //$NON-NLS-1$
	}

	public void update(Notification n)
	{
		final Object notifier = n.getNotifier();

		if (notifier instanceof Utility || notifier instanceof UtilityMode)
		{
			getDetailsPage().refresh();
		} else if (notifier instanceof UtilityModeExpression)
		{
			getDetailsPage().refresh();
		}
	}

	public void refresh()
	{
		treeViewer.getTree().getShell().getDisplay().asyncExec(new Runnable() {
			public void run()
			{
				treeViewer.refresh();

				// Tree tree = treeViewer.getTree();
				// if (tree.getItemCount() > 0)
				// {
				// tree.select(tree.getItem(0));
				// }
			}
		});

		getDetailsPage().refresh();
	}

	private EObject	model;

	public void setInput(Object input)
	{
		this.model = (EObject) input;
		getDetailsPage().setModel((AbstractPlan) input);
		this.treeViewer.setInput(getRootNode());
		
		refresh();
	}

	private ITreeNode getRootNode()
	{
		if (this.model == null)
		{
			return null;
		}

		this.rootNode = new MPlanTreeNode(getEditingDomain(), getCommandStack(), getEditController(), null, this.model);
		return this.rootNode;
	}
}
