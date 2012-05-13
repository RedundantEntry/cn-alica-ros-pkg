package de.uni_kassel.cn.planDesigner.utility.handler;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.handlers.HandlerUtil;

import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.impl.PlanImpl;
import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.utility.Constants;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.TreeContentProvider;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.TreeLabelProvider;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.AbstractTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.ITreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.tree.nodes.NoChildrenTreeNode;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityRepositoryUtils;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityUtils;

public class PurgeUtilityHandler extends AbstractHandler
{

	private Shell	shell;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException
	{
		shell = HandlerUtil.getActiveShell(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection)
		{
			purge(selection);
		}
		
		return null;
	}

	private Map<String, Set<Object>>	utilityMap;

	public Map<String, Set<Object>> getUtilityMap()
	{
		if (utilityMap == null)
		{
			utilityMap = new TreeMap<String, Set<Object>>();
		}
		return utilityMap;
	}

	private void purge(ISelection selection)
	{
		IStructuredSelection sel = (IStructuredSelection) selection;

		getUtilityMap().clear(); // clear map
		
		// check if there are plans referencing the selected utilities
		for (Iterator<?> i = sel.iterator(); i.hasNext();)
		{
			Object o = i.next();
			if (o instanceof Utility)
			{
				Set<Object> plansForUtility = UtilityUtils.getPlans((Utility) o);
				getUtilityMap().put(Long.toString(((Utility) o).getId()), plansForUtility); // TODO: get rid of "getID()"
			}
		}

		PurgeUtilityDialog dialog = new PurgeUtilityDialog();

		// remove everything
		switch (dialog.open())
		{
			case Dialog.OK:
				UtilityActivator activator = UtilityActivator.getDefault();
				PMLTransactionalEditingDomain domain = activator.getEditingDomain();
				CompoundCommand cc = new CompoundCommand();
				for(String s : dialog.getSelection())
				{
					Utility utility = UtilityRepositoryUtils.getUtility(Long.parseLong(s));
					
					// remove references from plans
					Set<Object> plans = utilityMap.get(s);
					for(Object o : plans)
					{
						UtilityReference ref = UtilityUtils.getUtilityReference(utility, (PlanImpl)o);
						cc.append(DeleteCommand.create(domain, ref));
					}
					
					// remove utility
					activator.getDefaultUtilityEditController().removeFromObject(utility);
					cc.append(DeleteCommand.create(domain, utility));
				}
				
				UtilityRepositoryUtils.execCommandAndSaveResource(cc, domain.getCommandStack(), domain);
				break;

			default:
				break;
		}
		
		
		
	}

	private class PurgeUtilityDialog extends TitleAreaDialog
	{
		public PurgeUtilityDialog()
		{
			super(shell);
		}
		
		private List<String> selection = new LinkedList<String>();

		public List<String> getSelection()
		{
			selection.clear();
			
			for(ITreeNode n : getRootNode().getChildren(false))
			{
				if(n instanceof UtilityChildNode)
				{
					UtilityChildNode node = (UtilityChildNode)n;
					if(node.isChecked())
					{
						selection.add(node.getKey());
					}
				}
			}
			
			return this.selection;
		}

		private TreeViewer	treeViewer;

		@Override
		protected Control createDialogArea(Composite parent)
		{
			Composite composite = new Composite(parent, SWT.NONE);
			composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			composite.setLayout(new FillLayout());

			final Tree tree = new Tree(composite, SWT.H_SCROLL | SWT.V_SCROLL);
			tree.setLinesVisible(true);

			// treeViewer = new TreeViewer(tree, SWT.FULL_SELECTION);
			treeViewer = new TreeViewer(tree);
			treeViewer.setContentProvider(new TreeContentProvider());
			treeViewer.setLabelProvider(new TreeLabelProvider());

			ColumnViewerEditorActivationStrategy actStrategy = new ColumnViewerEditorActivationStrategy(treeViewer);
			TreeViewerEditor.create(treeViewer, actStrategy, ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.KEYBOARD_ACTIVATION);

			TreeViewerColumn tvc = new TreeViewerColumn(treeViewer, SWT.LEFT);
			tvc.getColumn().setWidth(100);
			tvc.getColumn().setMoveable(true);
			tvc.getColumn().setText("asdasd");
			tvc.setLabelProvider(new ColumnLabelProvider() { // TreeLabelProvider?
				@Override
				public Image getImage(Object element)
				{
					if (element instanceof ITreeNode)
					{
						return ((ITreeNode) element).getImage();
					}
					return null;
				}

				@Override
				public String getText(Object element)
				{
					if (element instanceof ITreeNode)
					{
						return ((ITreeNode) element).getName();
					}
					return "Toll";
				}
			});

			tvc.setEditingSupport(new EditingSupport(treeViewer) {
				private CheckboxCellEditor	e	= new CheckboxCellEditor(treeViewer.getTree());
				
				@Override
				protected boolean canEdit(Object element)
				{
					return true;
				}

				@Override
				protected CellEditor getCellEditor(Object element)
				{
					if(element instanceof UtilityChildNode)
					{
						return e;
					}

					return null;
				}

				@Override
				protected Object getValue(Object element)
				{
					if(element instanceof UtilityChildNode)
					{
						return ((UtilityChildNode)element).isChecked();
					}
					return new Boolean(false);
				}

				@Override
				protected void setValue(Object element, Object value)
				{
					if(element instanceof UtilityChildNode)
					{
						((UtilityChildNode)element).switchChecked();
					}

					treeViewer.refresh(element, true);
				}
				
			});

			treeViewer.setInput(getRootNode());
			
			setTitleImage(UtilityActivator.getDefault().getImageRegistry().getDescriptor(Constants.ICON_UTILITY_64).createImage());
			setTitle("Purge Utilities");
			setMessage("Select Utilities that should be removed permanently." + 
					System.getProperty(Platform.PREF_LINE_SEPARATOR) + 
					"ATTENTION: this action is irreversible!"
			);

			return composite;
		}

		private AbstractTreeNode rootNode;
		
		private AbstractTreeNode getRootNode()
		{
			if(rootNode == null)
			{
				rootNode = new RootNode(null);
			}

			return rootNode;
		}
		

		private class RootNode extends AbstractTreeNode
		{
			public RootNode(ITreeNode parent)
			{
				super(parent);
			}

			@Override
			protected void createChildren(List<ITreeNode> children)
			{
				for (final String s : getUtilityMap().keySet())
				{
					AbstractTreeNode utilityChildNode = new UtilityChildNode(this);
					utilityChildNode.setData(new Object[]{s, getUtilityMap().get(s)});
					children.add(utilityChildNode);
				}
			}

			@Override
			public Image getImage()
			{
				return null;
			}

			@Override
			public String getName()
			{
				return "shouldn't be visible";
			}
		}

		private class UtilityChildNode extends AbstractTreeNode
		{
			private boolean checked;
			
			public UtilityChildNode(ITreeNode parent)
			{
				super(parent);
				checked = true;
			}

			private String getKey()
			{
				Object[] e = (Object[]) getData();
				return (String) e[0];
			}
			
			private Set<Object> getValue()
			{
				Object[] e = (Object[]) getData();
				Set<Object> s = (Set<Object>) e[1];
				return s;
			}
			
			public Utility getUtility()
			{
				return UtilityRepositoryUtils.getUtility(Long.parseLong(getKey()));
			}

			public Boolean isChecked()
			{
				return checked;
			}

			public void switchChecked()
			{
				checked = !checked;
			}

			@Override
			public Image getImage()
			{
				String i = Constants.ICON_CHECKBOX;
				
				if(checked)
				{
					i = Constants.ICON_CHECKBOX_CHECKED;
				}
				
				return UtilityActivator.getDefault().getImageRegistry().getDescriptor(i).createImage();
			}

			@Override
			protected void createChildren(List<ITreeNode> children)
			{
				if (getData() instanceof Object[])
				{
					Set<Object> s = getValue();
					for (Object o : s)
					{
						NoChildrenTreeNode planChildNode = new NoChildrenTreeNode(this)
						{
							@Override
							public String getName()
							{
								return ((Plan)getData()).getName();
							}
							
							@Override
							public Image getImage()
							{
								return PlanDesignerActivator.getDefault().getImageRegistry().getDescriptor(IEditorConstants.ICON_PLAN_16).createImage();
							}
						};
						planChildNode.setData(o);
						children.add(planChildNode);
					}
				}
			}

			@Override
			public String getName()
			{

				return UtilityUtils.getUtilityName(getUtility()) + " (" + getKey() + ")";
			}

		}
	}

}
