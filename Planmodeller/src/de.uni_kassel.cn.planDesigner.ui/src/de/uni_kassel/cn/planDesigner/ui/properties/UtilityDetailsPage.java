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
package de.uni_kassel.cn.planDesigner.ui.properties;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerEditor;
import org.eclipse.jface.viewers.ColumnViewerEditorActivationStrategy;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeViewerListener;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeExpansionEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.jface.viewers.TreeViewerEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TreeEvent;
import org.eclipse.swt.events.TreeListener;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;
import org.eclipse.ui.forms.widgets.TableWrapLayout;

import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.EntryPoint;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityModes;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.planDesigner.translator.Translator;
import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.TreeContentProvider;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.TreeLabelProvider;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.DUtilityModeExpressionTreeNode;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.DUtilityModeTreeNode;
import de.uni_kassel.cn.planDesigner.ui.properties.tree.nodes.DUtilityTreeNode;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;

/**
 * @author till
 * 
 */
public abstract class UtilityDetailsPage implements IDetailsPage
{
	private boolean dirty;

	private IManagedForm form;

	private UtilityReference input;

	public void createContents(Composite parent)
	{
		TableWrapLayout layout = new TableWrapLayout();
		parent.setLayout(layout);

		// general section
		createGeneralSection(parent);

		// modes section
		createModesSection(parent);

		// expression section
		createExpressionSection(parent);
	}

	private TreeViewer treeViewer;

	/**
	 * @param parent
	 * @param tk
	 */
	private void createModesSection(final Composite parent)
	{
		FormToolkit tk = this.form.getToolkit();

		final Section s1 = tk.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
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
		t.addTreeListener(new TreeListener()
		{

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
		treeViewer.expandAll();
		treeViewer.addTreeListener(new ITreeViewerListener()
		{

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

		// override "isEditorActivationEvent" to provide more activation
		// events
		ColumnViewerEditorActivationStrategy actStrategy = new ColumnViewerEditorActivationStrategy(treeViewer);

		TreeViewerEditor.create(treeViewer, actStrategy, ColumnViewerEditor.TABBING_HORIZONTAL | ColumnViewerEditor.TABBING_VERTICAL | ColumnViewerEditor.TABBING_MOVE_TO_ROW_NEIGHBOR | ColumnViewerEditor.KEYBOARD_ACTIVATION);

		TreeViewerColumn tvc = new TreeViewerColumn(treeViewer, SWT.LEFT);
		tvc.getColumn().setWidth(100);
		tvc.getColumn().setMoveable(true);
		tvc.getColumn().setText("Variable");
		tvc.setLabelProvider(new ColumnLabelProvider()
		{
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
		tvc.setEditingSupport(new EditingSupport(treeViewer)
		{
			TextCellEditor textCellEditor = new TextCellEditor(treeViewer.getTree());

			ComboBoxCellEditor cbCellEditor = new ComboBoxCellEditor(treeViewer.getTree(), new String[] { "foo", "bar" });

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
					((DUtilityModeTreeNode)element).setValue(value);
				}

				saveResource();

				treeViewer.update(element, null);
			}
		});

		tvc = new TreeViewerColumn(treeViewer, SWT.LEFT);
		tvc.getColumn().setWidth(100);
		tvc.getColumn().setMoveable(true);
		tvc.getColumn().setText("Task");
		tvc.setLabelProvider(new ColumnLabelProvider()
		{
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
		tvc.setEditingSupport(new EditingSupport(treeViewer)
		{
			ComboBoxCellEditor cbCellEditor = new ComboBoxCellEditor(treeViewer.getTree(), new String[] { "foo", "bar" });

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
						items = (List<String>) getEditingDomain().runExclusive(new RunnableWithResult.Impl<List<String>>()
						{
							public void run()
							{
								LinkedList<String> list = new LinkedList<String>();
								//AbstractPlan model = (AbstractPlan) getModel();
								Plan model = (Plan) getModel();
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
					list = (List<EntryPoint>) getEditingDomain().runExclusive(new RunnableWithResult.Impl<List<EntryPoint>>()
					{
						public void run()
						{
//							setResult(((AbstractPlan) getModel()).getEntryPoints());
							setResult(((Plan) getModel()).getEntryPoints());
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
				if(value instanceof Integer && (Integer)value > -1)
				{
					List<EntryPoint> eps = getEntryPoints();
					EntryPoint ep = eps.get((Integer)value);
					((DUtilityModeExpressionTreeNode) element).setEntryPoint(ep);
				}

				saveResource();

				treeViewer.update(element, null);
			}
		});

		createTreeContextMenu();
		
		treeViewer.getTree().addListener(SWT.FocusOut, getEditController());
//		treeViewer.getTree().addListener(SWT.Modify, getEditController());
		treeViewer.getTree().addListener(SWT.CR, getEditController());
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

		PlanDesignerActivator.getDefault().getWorkbench().getActiveWorkbenchWindow().getPartService().getActivePart().getSite().registerContextMenu(IEditorConstants.PML_UTILITY_ID + ".utilitiesModeExpressionContextMenu", menuMgr, treeViewer); //$NON-NLS-1$
	}

	public void expand(Object notifier)
	{
		// for(TreeItem i : treeViewer.getTree().getItems())
		// {
		// if(notifier == ((IEMFTreeNode)i).getModelObject())
		// {
		// ITreeNode parent = ((ITreeNode)i).getParent();
		// treeViewer.setExpandedState(parent, true);
		// break;
		// }
		// }
	}

	private Text expressionText;

	private Text expressionTextTranslated;
	
	/**
	 * @param parent
	 * @param tk
	 */
	private void createExpressionSection(Composite parent)
	{
		FormToolkit tk = this.form.getToolkit();

		Section s1 = tk.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		s1.setText("Expression");
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);

		Composite c = tk.createComposite(s1, SWT.WRAP);
		c.setLayout(new GridLayout());
		s1.setClient(c);

		expressionText = tk.createText(c, "", SWT.MULTI | SWT.WRAP);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);

		GC gc = new GC(expressionText);
		FontMetrics fm = gc.getFontMetrics();
		gc.dispose();
		int rows = 5;
		int height = rows * fm.getHeight();
		gd.heightHint = height;

		expressionText.setLayoutData(gd);

		expressionText.addListener(SWT.FocusOut, getEditController());
		expressionText.addListener(SWT.Modify, new ModifyListener());
		expressionText.addListener(SWT.KeyDown, getEditController());
		expressionText.addListener(SWT.FocusOut, new ExpressionListener());
		expressionText.addListener(SWT.KeyDown, new ExpressionListener());
		
		Label label = tk.createLabel(c, "Translation:");
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);

		gc = new GC(label);
		label.setLayoutData(gd);

		expressionTextTranslated = tk.createText(c, "", SWT.MULTI | SWT.WRAP);
		gd = new GridData(SWT.FILL, SWT.FILL, true, true);

		gc = new GC(expressionTextTranslated);
		fm = gc.getFontMetrics();
		gc.dispose();
		rows = 5;
		height = rows * fm.getHeight();
		gd.heightHint = height;

		expressionTextTranslated.setLayoutData(gd);
	}

	private Text nameText;

	private Text weightText;

	/**
	 * @param parent
	 * @param tk
	 */
	private void createGeneralSection(Composite parent)
	{
		FormToolkit tk = this.form.getToolkit();

		Section s1 = tk.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		s1.setText("General");
		TableWrapData td = new TableWrapData(TableWrapData.FILL, TableWrapData.TOP);
		td.grabHorizontal = true;
		s1.setLayoutData(td);

		Composite c = tk.createComposite(s1, SWT.WRAP);
		GridLayout gl = new GridLayout(2, true);
		c.setLayout(gl);
		s1.setClient(c);

		Label l = tk.createLabel(c, "Name");
		GridData gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		l.setLayoutData(gd);

		nameText = tk.createText(c, "");
		gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		nameText.setLayoutData(gd);
		nameText.addListener(SWT.FocusOut, getEditController());
		nameText.addListener(SWT.Modify, new ModifyListener());
		nameText.addListener(SWT.KeyDown, getEditController());

		l = tk.createLabel(c, "Weight");
		gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		l.setLayoutData(gd);

		weightText = tk.createText(c, "");
		gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		weightText.setLayoutData(gd);
		weightText.addListener(SWT.FocusOut, getEditController());
		weightText.addListener(SWT.Modify, new ModifyListener());
		weightText.addListener(SWT.KeyDown, getEditController());
	}
	
	private class ModifyListener implements Listener
	{
		public void handleEvent(Event event)
		{
			setDirty(true);
		}
		
	}
	
	private class ExpressionListener implements Listener
	{
		public void handleEvent(Event event)
		{
			String text = expressionText.getText();
			
			String transtext = Translator.translate(text);
//			String transtext = text;
			
			expressionTextTranslated.setText(transtext);
		}
		
	}

	public void commit(boolean onSave)
	{
		final CompoundCommand cc = new CompoundCommand();

		try
		{
			getEditingDomain().runExclusive(new Runnable()
			{
				public void run()
				{
					String weightS = weightText.getText();
					try
					{
						Float weightF = Float.parseFloat(weightS);
						Command wc = SetCommand.create(getEditingDomain(), input, AlicaPackage.eINSTANCE.getUtilityReference_Weight(), weightF.floatValue());
						cc.append(wc);
					} catch (NumberFormatException e)
					{
						// nop
					}

					Command uc = SetCommand.create(getEditingDomain(), input.getUtility(), AlicaPackage.eINSTANCE.getPlanElement_Name(), nameText.getText());
					cc.append(uc);
					
//					IniParser ip = IniParser.get();
//					ip.getMap();
					
					uc = SetCommand.create(getEditingDomain(), input.getUtility(), AlicaPackage.eINSTANCE.getUtility_Expression(), expressionText.getText());
					cc.append(uc);
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		getCommandStack().execute(cc);
		
		this.dirty = false;
	}

	public void dispose()
	{
	}

	public void initialize(IManagedForm form)
	{
		this.form = form;
	}

	public void setDirty(boolean dirty)
	{
		this.dirty = dirty;
	}

	public boolean isDirty()
	{
		return this.dirty;
	}

	public boolean isStale()
	{
		return false;
	}

	public void refresh()
	{
		if (this.input == null)
		{
			return;
		}

		refreshWeight();

		refreshUtility();
	}

	/**
	 * 
	 */
	private void refreshUtility()
	{
		Utility u = null;
		try
		{
			u = (Utility) getEditingDomain().runExclusive(new RunnableWithResult.Impl<Utility>()
			{
				public void run()
				{
					setResult(input.getUtility());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		nameText.setText(u.getName());
		
		expressionText.setText(u.getExpression());

		treeViewer.setInput(new DUtilityTreeNode(getEditingDomain(), getCommandStack(), getEditController(), null, u));
	}

	/**
	 * 
	 */
	private void refreshWeight()
	{
		String w = null;
		try
		{
			w = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>()
			{
				public void run()
				{
					setResult(String.valueOf(input.getWeight()));
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		weightText.setText(w);
	}

	public void setFocus()
	{
		if (this.nameText != null)
		{
			this.nameText.setFocus();
		}
	}

	public boolean setFormInput(Object input)
	{
		return false;
	}

	public void selectionChanged(IFormPart part, ISelection selection)
	{
		if (selection instanceof IStructuredSelection)
		{
			Object o = ((IStructuredSelection) selection).getFirstElement();

			this.input = (UtilityReference) o;
		}

		refresh();
	}

	abstract protected void saveResource();

	abstract protected AbstractPlan getModel();

	abstract public void setModel(AbstractPlan model);

	abstract protected CommandStack getCommandStack();

	abstract protected EditController getEditController();

	abstract protected PMLTransactionalEditingDomain getEditingDomain();
}