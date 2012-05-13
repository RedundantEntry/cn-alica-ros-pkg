package de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.EntryPoint;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityModes;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.impl.UtilityImpl;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.TreeContentProvider;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.TreeLabelProvider;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.DUtilityModeExpressionTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.DUtilityModeTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.DUtilityTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.ITreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityRepositoryUtils;

/**
 * This actually is not a FormPart, but it should do the same things as one. The
 * reason is that it is used by the DetailsPage which itself is a FormPart. The
 * DetailsPage forwards events to this page and to make it easier the
 * AbstractFormPart is derived. <br>
 * <br>
 * The usage as a stand-alone form part is not tested
 * 
 * @author till
 * 
 */
public class ModeSection extends UtilityDetailsPageSection
{

	private TreeViewer	treeViewer;
	
	public void createSection(Composite parent)
	{
		FormToolkit tk = getManagedForm().getToolkit();

		final Section s1 = tk.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		s1.setText("Modes");
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);

		Composite c = tk.createComposite(s1, SWT.WRAP);
		c.setLayout(new GridLayout());
		s1.setClient(c);

		final Tree t = tk.createTree(c, SWT.SINGLE | SWT.H_SCROLL);
		t.setLinesVisible(true);
		GridData gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		t.setLayoutData(gd);
		t.addTreeListener(new TreeListener() {

			public void treeCollapsed(TreeEvent event)
			{
				update(event);
			}

			public void treeExpanded(TreeEvent event)
			{
				update(event);
			}

			private void update(TreeEvent event)
			{
			}

		});

		treeViewer = new TreeViewer(t);
		treeViewer.setContentProvider(new TreeContentProvider());
		treeViewer.setLabelProvider(new TreeLabelProvider());
		
		treeViewer.addTreeListener(new ITreeViewerListener() {

			public void treeCollapsed(TreeExpansionEvent event)
			{
				update(event);
			}

			public void treeExpanded(TreeExpansionEvent event)
			{
				update(event);
			}

			private void update(TreeExpansionEvent event)
			{
			}

		});

		// activation via keyboard
		// TreeViewerFocusCellManager tvfcm = new
		// TreeViewerFocusCellManager(treeViewer, new
		// FocusCellOwnerDrawHighlighter(treeViewer));

		// override "isEditorActivationEvent" to provide more activation events
		ColumnViewerEditorActivationStrategy actStrategy = new ColumnViewerEditorActivationStrategy(treeViewer);

		TreeViewerEditor.create(treeViewer, actStrategy, ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.KEYBOARD_ACTIVATION);

		TreeViewerColumn tvc = new TreeViewerColumn(treeViewer, SWT.LEFT);
		tvc.getColumn().setWidth(100);
		tvc.getColumn().setMoveable(true);
		tvc.getColumn().setText("Variable");
		tvc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element)
			{
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					return ((DUtilityModeExpressionTreeNode) element).getVariable();
				} else if (element instanceof DUtilityModeTreeNode)
				{
					return ((DUtilityModeTreeNode) element).getName();
				}

				return "";
			}
		});
		tvc.setEditingSupport(new EditingSupport(treeViewer) {
			TextCellEditor		textCellEditor	= new TextCellEditor(treeViewer.getTree());

			ComboBoxCellEditor	cbCellEditor	= new ComboBoxCellEditor(treeViewer.getTree(), new String[] { "foo", "bar" });

			@Override
			protected boolean canEdit(Object element)
			{
				return true;
			}

			@Override
			protected CellEditor getCellEditor(Object element)
			{
				if (element instanceof DUtilityModeTreeNode)
				{
					List<UtilityModes> l = Arrays.asList(UtilityModes.values());
					LinkedList<String> items = new LinkedList<String>();
					for (UtilityModes m : l)
					{
						items.add(m.getName());
					}

					cbCellEditor.setItems(items.toArray(new String[0]));

					return cbCellEditor;
				}
				return textCellEditor;
			}

			@Override
			protected Object getValue(Object element)
			{
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					return ((DUtilityModeExpressionTreeNode) element).getVariable();
				} else if (element instanceof DUtilityModeTreeNode)
				{
					return ((DUtilityModeTreeNode) element).getModeCount();
				}
				return "NO_VAL";
			}

			@Override
			protected void setValue(Object element, Object value)
			{
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					((DUtilityModeExpressionTreeNode) element).setVariable(value);
				} else if (element instanceof DUtilityModeTreeNode)
				{
					((DUtilityModeTreeNode) element).setValue(value);
				}

				saveResource();

				treeViewer.update(element, null);
			}
		});

		tvc = new TreeViewerColumn(treeViewer, SWT.LEFT);
		tvc.getColumn().setWidth(100);
		tvc.getColumn().setMoveable(true);
		tvc.getColumn().setText("Task");
		tvc.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element)
			{
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					return ((DUtilityModeExpressionTreeNode) element).getTask();
				}

				return "";
			}
		});
		tvc.setEditingSupport(new EditingSupport(treeViewer) {
			ComboBoxCellEditor	cbCellEditor	= new ComboBoxCellEditor(treeViewer.getTree(), new String[] { "foo", "bar" });

			protected boolean canEdit(Object element)
			{
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					return true;
				}

				return false;
			}

			@SuppressWarnings("unchecked")
			protected CellEditor getCellEditor(Object element)
			{
				// set values ;)
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					List<String> items = null;
					try
					{
						items = (List<String>) getEditingDomain().runExclusive(new RunnableWithResult.Impl<List<String>>() {
							public void run()
							{
								LinkedList<String> list = new LinkedList<String>();
								Plan model = (Plan) getFormInput();
								for (EntryPoint ep : model.getEntryPoints())
								{
									list.add(ep.getTask().getName());
								}

								setResult(list);
							}
						});
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}

					if (items != null)
					{
						cbCellEditor.setItems(items.toArray(new String[0]));
					}
				}
				return cbCellEditor;
			}

			protected Object getValue(Object element)
			{
				if (element instanceof DUtilityModeExpressionTreeNode)
				{
					List<EntryPoint> list = getEntryPoints();

					return ((DUtilityModeExpressionTreeNode) element).getTaskCount(list);
				}
				return "NO_TASK";
			}

			/**
			 * @return
			 */
			@SuppressWarnings("unchecked")
			private List<EntryPoint> getEntryPoints()
			{
				List<EntryPoint> list = null;
				try
				{
					list = (List<EntryPoint>) getEditingDomain().runExclusive(new RunnableWithResult.Impl<List<EntryPoint>>() {
						public void run()
						{
							setResult(((Plan) getFormInput()).getEntryPoints());
						}
					});
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				return list;
			}

			protected void setValue(Object element, Object value)
			{
				if (value instanceof Integer && (Integer) value > -1)
				{
					List<EntryPoint> eps = getEntryPoints();
					EntryPoint ep = eps.get((Integer) value);
					((DUtilityModeExpressionTreeNode) element).setEntryPoint(ep);
				}

				saveResource();

				treeViewer.update(element, null);
			}
		});

		createTreeContextMenu();

		treeViewer.getTree().addListener(SWT.Modify, new ModifyListener());
		treeViewer.getTree().addListener(SWT.KeyDown, new EnterPressedListener());
		
		refresh();
	}

	private void saveResource()
	{
		UtilityRepositoryUtils.saveResource(getEditingDomain());
	}

	/**
	 * creates and registers the context menu
	 */
	private void createTreeContextMenu()
	{
		MenuManager menuMgr = new MenuManager();

		Control control = treeViewer.getControl();
		Menu contextMenu = menuMgr.createContextMenu(control);
		control.setMenu(contextMenu);

		// Add the marker where other plugins can contribute new actions
		menuMgr.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		UtilityActivator.getDefault().getUtilityView().getSite().registerContextMenu("de.uni_kassel.cn.planDesigner.utilitiesModeExpressionContextMenu", menuMgr, treeViewer); //$NON-NLS-1$
	}

	public void expand(Object notifier)
	{
		 for (TreeItem i : treeViewer.getTree().getItems())
		{
			if (notifier == ((ITreeNode) i).getData())
			{
				ITreeNode parent = ((ITreeNode) i).getParent();
				treeViewer.setExpandedState(parent, true);
				break;
			}
		}
	}

	@Override
	public void commit(boolean onSave)
	{
		super.commit(onSave);
	}

	private void refreshText()
	{
		if(this.treeViewer == null || this.treeViewer.getControl().isDisposed())
		{
			return;
		}
		
		 // clear
		this.treeViewer.setInput(null);

		// if nothing is selected...
		if (getInput() == null)
		{
			return;
		}

		// set
		Utility u = null;

		if (getInput() instanceof UtilityImpl)
		{
			u = (UtilityImpl)getInput();
		} 
		else if (getInput() instanceof UtilityReference)
		{
			try
			{
				u = (Utility) getEditingDomain().runExclusive(new RunnableWithResult.Impl<Utility>() {
					public void run()
					{
						setResult(((UtilityReference)getInput()).getUtility());
					}
				});
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		treeViewer.setInput(new DUtilityTreeNode(getEditingDomain(), getCommandStack(), getEditController(), null, u));
	}

	@Override
	public void refresh()
	{
		super.refresh();
		
		refreshText();
	}
}
