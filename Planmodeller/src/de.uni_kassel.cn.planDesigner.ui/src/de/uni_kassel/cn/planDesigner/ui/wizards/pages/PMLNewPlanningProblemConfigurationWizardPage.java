package de.uni_kassel.cn.planDesigner.ui.wizards.pages;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.Behaviour;
import de.uni_kassel.cn.alica.BehaviourConfiguration;
import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.PlanType;
import de.uni_kassel.cn.alica.PlanningProblem;
import de.uni_kassel.cn.alica.Result;
import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.util.PlanEditorUtils;

public class PMLNewPlanningProblemConfigurationWizardPage extends WizardPage {

	private class PlanningProblemListLabelProvider extends LabelProvider{
		@Override
		public Image getImage(Object element) {
			if(element instanceof AbstractPlan){
				return PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLANNING_PROBLEM_16);
			}else
				return null;
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof AbstractPlan){
				return ((AbstractPlan)element).getName();
			}else
				return "Unrecognized element";
		}
	}
	private class PlanListLabelProvider extends LabelProvider{
		@Override
		public Image getImage(Object element) {
			if(element instanceof Plan){
				return PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLAN_16);
			}else
				return null;
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof Plan){
				return ((Plan)element).getName();
			}else
				return "Unrecognized element";
		}
	}
	private class PlanTypeListLabelProvider extends LabelProvider{
		@Override
		public Image getImage(Object element) {
			if(element instanceof PlanType){
				return PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLAN_16);
			}else
				return null;
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof PlanType){
				return ((PlanType)element).getName();
			}else
				return "Unrecognized element";
		}
	}
	private class BehaviourListLabelProvider extends LabelProvider{
		@Override
		public Image getImage(Object element) {
			if(element instanceof Plan){
				return PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_BEHAVIOUR_16);
			}else
				return null;
		}
		
		@Override
		public String getText(Object element) {
			if(element instanceof BehaviourConfiguration){
				return ((BehaviourConfiguration)element).getName();
			}else
				return "Unrecognized element";
		}
	}
	private class AbstractPlanListLabelProvider extends LabelProvider implements ITableLabelProvider{
		@Override
		public Image getImage(Object element) {
			if(element instanceof Plan){
				return PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLAN_16);
			}else
				return null;
		}
				
		public String getColumnText(Object element, int col) {
			if (col == 0) {
				if(element instanceof AbstractPlan){
					return ((AbstractPlan)element).getName();
				}else
					return "Unrecognized element";
			} else { return "";}
		}
		public Image getColumnImage(Object element, int columnIndex)
        {
        	return null;
        }
	}
	private class AbstractPlanListContentProvider implements IStructuredContentProvider{
		
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((Set<AbstractPlan>)inputElement).toArray();
		}
	}
	private class PlanningProblemListContentProvider implements IStructuredContentProvider{
		
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((Set<AbstractPlan>)inputElement).toArray();
		}
	}
	private class PlanListContentProvider implements IStructuredContentProvider{
		
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((Set<Plan>)inputElement).toArray();
		}
	}
	private class PlanTypeListContentProvider implements IStructuredContentProvider{
		
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((Set<PlanType>)inputElement).toArray();
		}
	}
	private class BehaviourListContentProvider implements IStructuredContentProvider{
		
		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}

		@SuppressWarnings("unchecked")
		public Object[] getElements(Object inputElement) {
			return ((Set<BehaviourConfiguration>)inputElement).toArray();
		}
	}
	
	private TableViewer plansViewer;
	private TableViewer behaviourViewer;
	private TableViewer plantypeViewer;
	private TableViewer planningProblemViewer;
	private TableViewer planningElementsViewer;
	private Text name;
	private String nameS = "";
	private StyledText comment;
	private String commentS = "";
	private StyledText condition;
	private String conditionS = "";
	private TabFolder folder;
	private Set<Plan> plansViewerList;
	private Set<BehaviourConfiguration> behaviourViewerList;
	private Set<PlanType> plantypeViewerList;
	private Set<AbstractPlan> existPlanningProblemViewerList;
	private Set<AbstractPlan> planningProblemViewerList;
	private PlanningProblem pp;
	private PMLTransactionalEditingDomain domain;
	
	public PMLNewPlanningProblemConfigurationWizardPage(PMLTransactionalEditingDomain domain) {
		this(domain, null);
	}
	
	public PMLNewPlanningProblemConfigurationWizardPage(PMLTransactionalEditingDomain domain, PlanningProblem type) {
		super("New PlanningProblem");
		setTitle("Configure the planningProblem");
		this.pp = type;
		this.domain = domain;
	}
	
	public void createControl(Composite parent) {
				
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new GridLayout(3,false));
		
		// Create elements for the left (plan) side
		Label availablePlansLabel = new Label(container, SWT.NONE);
		availablePlansLabel.setText("Available Plans");
		
		Label planTypeLabel = new Label(container, SWT.NONE);
		planTypeLabel.setText("PlanningProblem elements");
		
		//create tabFolder
		folder = new TabFolder(container, SWT.TOP);
		folder.setSize(SWT.FILL, SWT.FILL);
		folder.addSelectionListener(new SelectionListener() {
		      public void widgetSelected(SelectionEvent e) {
//		    	  System.out.println("Selected item index = " + folder.getSelectionIndex());
//		    	  System.out.println("Selected item = " + (folder.getSelection() == null ? "null" : folder.getSelection()[0].toString()));
		      }

		      public void widgetDefaultSelected(SelectionEvent e) {
		        widgetSelected(e);
		      }
		    });
		
		// Add the TabItem for the plans
		TabItem tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setText("Plans");
		tabItem.setImage(PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLAN_16));
				
		plansViewer = new TableViewer(folder);
		plansViewer.setSorter(new ViewerSorter());
		plansViewer.setContentProvider(new PlanListContentProvider());
		plansViewer.setLabelProvider(new PlanListLabelProvider());
		tabItem.setControl(plansViewer.getControl());
		plansViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
				Object selection = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(selection instanceof EObject){
					try {
						EObject element = (EObject)selection;
						IWorkbenchPage page =
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, WorkspaceSynchronizer.getFile(element.eResource()));
					} catch (PartInitException e) {
						PlanDesignerActivator.getDefault().getLog().log(
								new Status(IStatus.ERROR, PlanDesignerActivator.PLUGIN_ID,"Error while opening plan",e));
					}
				}
				
			}
		});
		
		// Add the TabItem for the plantypes
		tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setText("Plantypes");
		tabItem.setImage(PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLANTYPE_16));
		
		plantypeViewer = new TableViewer(folder);
		plantypeViewer.setSorter(new ViewerSorter());
		plantypeViewer.setContentProvider(new PlanTypeListContentProvider());
		plantypeViewer.setLabelProvider(new PlanTypeListLabelProvider());
		tabItem.setControl(plantypeViewer.getControl());
		plantypeViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
				Object selection = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(selection instanceof EObject){
					try {
						EObject element = (EObject)selection;
						IWorkbenchPage page =
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, WorkspaceSynchronizer.getFile(element.eResource()));
					} catch (PartInitException e) {
						PlanDesignerActivator.getDefault().getLog().log(
								new Status(IStatus.ERROR, PlanDesignerActivator.PLUGIN_ID,"Error while opening plantype",e));
					}
				}
				
			}
		});
		
		// Add the TabItem for the behaviours
		tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setText("Behaviours");
		tabItem.setImage(PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_BEHAVIOUR_16));
		
		behaviourViewer = new TableViewer(folder);
		behaviourViewer.setSorter(new ViewerSorter());
		behaviourViewer.setContentProvider(new BehaviourListContentProvider());
		behaviourViewer.setLabelProvider(new BehaviourListLabelProvider());
		tabItem.setControl(behaviourViewer.getControl());
		behaviourViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
				Object selection = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(selection instanceof EObject){
					try {
						EObject element = (EObject)selection;
						IWorkbenchPage page =
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, WorkspaceSynchronizer.getFile(element.eResource()));
					} catch (PartInitException e) {
						PlanDesignerActivator.getDefault().getLog().log(
								new Status(IStatus.ERROR, PlanDesignerActivator.PLUGIN_ID,"Error while opening behaviour",e));
					}
				}
				
			}
		});
		
		// Add the TabItem for the plantypes
		tabItem = new TabItem(folder, SWT.NONE);
		tabItem.setText("PlanningProblems");
		tabItem.setImage(PlanDesignerActivator.getDefault().getImageRegistry().get(IEditorConstants.ICON_PLANNING_PROBLEM_16));
		
		planningProblemViewer = new TableViewer(folder);
		planningProblemViewer.setSorter(new ViewerSorter());
		planningProblemViewer.setContentProvider(new PlanningProblemListContentProvider());
		planningProblemViewer.setLabelProvider(new PlanningProblemListLabelProvider());
		tabItem.setControl(planningProblemViewer.getControl());
		planningProblemViewer.addDoubleClickListener(new IDoubleClickListener(){
			public void doubleClick(DoubleClickEvent event) {
				Object selection = ((IStructuredSelection)event.getSelection()).getFirstElement();
				if(selection instanceof EObject){
					try {
						EObject element = (EObject)selection;
						IWorkbenchPage page =
							PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
						IDE.openEditor(page, WorkspaceSynchronizer.getFile(element.eResource()));
					} catch (PartInitException e) {
						PlanDesignerActivator.getDefault().getLog().log(
								new Status(IStatus.ERROR, PlanDesignerActivator.PLUGIN_ID,"Error while opening behaviour",e));
					}
				}
				
			}
		});
		
		// Create the buttons for the mid
		Composite buttonsContainer = new Composite(container, SWT.NONE);
		FormLayout fLayout = new FormLayout();
		fLayout.spacing = 15;
		buttonsContainer.setLayout(fLayout);

		Button addButton = new Button(buttonsContainer, SWT.PUSH);
		addButton.setText("Add >");
		addButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Move the selected plan from the plansViewerList to
				// the planTypeViewerList
				if( folder.getSelectionIndex() == 0 ) {
					moveToPlan(plansViewer.getSelection(), plansViewerList, planningProblemViewerList);
				} else if( folder.getSelectionIndex() == 1 ) {
					moveToPlanType(plantypeViewer.getSelection(), plantypeViewerList, planningProblemViewerList);
				} else if( folder.getSelectionIndex() == 2 ) {
					moveToBehaviour(behaviourViewer.getSelection(), behaviourViewerList, planningProblemViewerList);
				} else if( folder.getSelectionIndex() == 3 ) {
					moveToPP(planningProblemViewer.getSelection(), existPlanningProblemViewerList, planningProblemViewerList);
				}
				dialogChanged();
			}
		});
		
		Button addAllButton = new Button(buttonsContainer, SWT.PUSH);
		addAllButton.setText("Add all >>");
		addAllButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if( folder.getSelectionIndex() == 0 ) {
					moveAllToPlan(plansViewerList, planningProblemViewerList);
				} else if( folder.getSelectionIndex() == 1 ) {
					moveAllToPlanType(plantypeViewerList, planningProblemViewerList);
				} else if( folder.getSelectionIndex() == 2 ) {
					moveAllToBehaviour(behaviourViewerList, planningProblemViewerList);
				} else if( folder.getSelectionIndex() == 3 ) {
					moveAllToPP(existPlanningProblemViewerList, planningProblemViewerList);
				}
				dialogChanged();
			}
		});
		
		Button removeButton = new Button(buttonsContainer, SWT.PUSH);
		removeButton.setText("< Remove");
		removeButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if( folder.getSelectionIndex() == 0 ) {
					moveFromPlan(planningElementsViewer.getSelection(), planningProblemViewerList, plansViewerList);
				} else if( folder.getSelectionIndex() == 1 ) {
					moveFromPlanType(planningElementsViewer.getSelection(), planningProblemViewerList, plantypeViewerList);
				} else if( folder.getSelectionIndex() == 2 ) {
					moveFromBehaviour(planningElementsViewer.getSelection(), planningProblemViewerList, behaviourViewerList);
				} else if( folder.getSelectionIndex() == 3 ) {
					moveFromPP(planningElementsViewer.getSelection(), planningProblemViewerList, existPlanningProblemViewerList);
				}
				dialogChanged();
			}
		});
		
		Button removeAllButton = new Button(buttonsContainer, SWT.PUSH);
		removeAllButton.setText("<< Remove all");
		removeAllButton.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				if( folder.getSelectionIndex() == 0 ) {
					moveAllFromPlan(planningProblemViewerList, plansViewerList);
				} else if( folder.getSelectionIndex() == 1 ) {
					moveAllFromPlanType(planningProblemViewerList, plantypeViewerList);
				} else if( folder.getSelectionIndex() == 2 ) {
					moveAllFromBehaviour(planningProblemViewerList, behaviourViewerList);
				} else if( folder.getSelectionIndex() == 3 ) {
					moveAllFromPP(planningProblemViewerList, existPlanningProblemViewerList);
				}
				dialogChanged();
			}
		});
		
		// Create the elements for the right (new planning problem) side
		planningElementsViewer = new TableViewer(container);
		
		planningElementsViewer.setSorter(new ViewerSorter());
		
		
		Table table = planningElementsViewer.getTable();
    	table.setLinesVisible(true);
    	table.setHeaderVisible(true);
    	table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    	
    	TableColumn colPlanName = new TableColumn(table, SWT.NONE);
    	colPlanName.setWidth(140);
    	colPlanName.setText("Elements");
    	
    	
		planningElementsViewer.setContentProvider(new AbstractPlanListContentProvider());
		planningElementsViewer.setLabelProvider(new AbstractPlanListLabelProvider());
			        
		// Do the layout for the buttonsContainer
		FormData fData = new FormData();
		fData.top = new FormAttachment();
		fData.width = 100;
		
		addButton.setLayoutData(fData);
		
		fData = new FormData();
		fData.top = new FormAttachment(addButton);
		fData.width = 100;
		addAllButton.setLayoutData(fData);
		
		fData = new FormData();
		fData.top = new FormAttachment(addAllButton, 50);
		fData.width = 100;
		removeButton.setLayoutData(fData);
		
		fData = new FormData();
		fData.top = new FormAttachment(removeButton);
		fData.width = 100;
		removeAllButton.setLayoutData(fData);
		
		//input text for goal definition
		Composite goalDefinition = new Composite(container, SWT.BORDER);
		goalDefinition.setLayout(new GridLayout(2,false));
		Label label = new Label(goalDefinition,SWT.BORDER);
		label.setText("Name : ");
		name = new Text(goalDefinition, SWT.BORDER);
		name.setSize(50,1);
		name.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent e) {
				dialogChanged();
			}
			
			public void keyPressed(KeyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(goalDefinition, SWT.BORDER);
		label.setText("Comment : ");
		comment = new StyledText(goalDefinition, SWT.BORDER);
		comment.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent e) {
				dialogChanged();
			}
			
			public void keyPressed(KeyEvent e) {
				dialogChanged();
			}
		});
		label = new Label(goalDefinition, SWT.BORDER);
		label.setText("ConditionString : ");
		condition = new StyledText(goalDefinition, SWT.BORDER);
		condition.addKeyListener(new KeyListener() {
			
			public void keyReleased(KeyEvent e) {
				dialogChanged();
			}
			
			public void keyPressed(KeyEvent e) {
				dialogChanged();
			}
		});
		
		GridData textLayout = new GridData();
		textLayout.widthHint = 150;
		name.setLayoutData(textLayout);
		textLayout = new GridData();
		textLayout.widthHint = 250;
		textLayout.heightHint = 100;
		comment.setLayoutData(textLayout);
		condition.setLayoutData(textLayout);
		
		// Do the layout for the whole container
		availablePlansLabel.setLayoutData(new GridData(SWT.BEGINNING,SWT.CENTER,true,false,2,1));
		planTypeLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER,true,false));
		
		GridData gData = new GridData(SWT.FILL,SWT.FILL,true,true);
		gData.widthHint = 170;
		gData.heightHint = 250;
		folder.setLayoutData(gData);
		
		buttonsContainer.setLayoutData(new GridData(SWT.FILL,SWT.CENTER,false,true));
		
		gData = new GridData(SWT.FILL,SWT.FILL,true,true);
		gData.widthHint = 170;
		gData.heightHint = 250;
		planningElementsViewer.getTable().setLayoutData(gData);
		
		gData = new GridData(SWT.FILL,SWT.NONE,true,true);
		gData.verticalIndent = 20;
		gData.horizontalSpan = 3;
		gData.horizontalAlignment = SWT.CENTER;
		goalDefinition.setLayoutData(gData);
		
		this.getWizard().getContainer().getShell().setMinimumSize(1000, 800);
		setControl(container);
		initializeInput();
		dialogChanged();
	}

	/**
	 * Moves all plans from one set to the other and calls refresh
	 * on both viewers.
	 * @param from
	 * @param to
	 */
	private void moveAllToPlan(Set<Plan> from, Set<AbstractPlan> to){
		for(Plan p : from) {
			to.add(p);
		}
			//to.addAll(from);		
		from.clear();
		plansViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllToPlanType(Set<PlanType> from, Set<AbstractPlan> to){
		for(PlanType p : from) {
			to.add(p);
		}
			//to.addAll(from);		
		from.clear();
		plantypeViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllToBehaviour(Set<BehaviourConfiguration> from, Set<AbstractPlan> to){
		for(BehaviourConfiguration p : from) {
			to.add(p);
		}
			//to.addAll(from);		
		from.clear();
		behaviourViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllToPP(Set<AbstractPlan> from, Set<AbstractPlan> to){
		for(AbstractPlan p : from) {
			to.add(p);
		}
			//to.addAll(from);		
		from.clear();
		planningProblemViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllFromPlan(Set<AbstractPlan> from, Set<Plan> to) {
		Set<AbstractPlan> delList = new HashSet<AbstractPlan>();
		for(AbstractPlan ap : from) {	
			if( ap instanceof Plan) {
				to.add((Plan)ap);
				delList.add(ap);
			}
		}
		from.removeAll(delList);
		plansViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllFromPlanType(Set<AbstractPlan> from, Set<PlanType> to) {
		Set<AbstractPlan> delList = new HashSet<AbstractPlan>();
		for(AbstractPlan ap : from) {
			if( ap instanceof PlanType) {
				to.add((PlanType)ap);
				delList.add(ap);
			}
		}
		from.removeAll(delList);
		plantypeViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllFromBehaviour(Set<AbstractPlan> from, Set<BehaviourConfiguration> to) {
		Set<AbstractPlan> delList = new HashSet<AbstractPlan>();
		for(AbstractPlan ap : from) {		
			if( ap instanceof BehaviourConfiguration) {
				to.add((BehaviourConfiguration)ap);
				delList.add(ap);
			}
		}
		from.removeAll(delList);
		behaviourViewer.refresh();
		planningElementsViewer.refresh();
	}
	private void moveAllFromPP(Set<AbstractPlan> from, Set<AbstractPlan> to) {
		Set<AbstractPlan> delList = new HashSet<AbstractPlan>();
		for(AbstractPlan ap : from) {	
			if( ap instanceof PlanningProblem) {
				to.add(ap);
				delList.add(ap);
			}
		}
		from.removeAll(delList);
		planningProblemViewer.refresh();
		planningElementsViewer.refresh();
	}
	
	/**
	 * Removes the given plans from the <code>from</code> set and 
	 * adds it to the <code>to</code> set. After that it calls
	 * refresh on both viewers. 
	 * @param plan
	 * @param from
	 * @param to
	 */
	@SuppressWarnings("unchecked")
	private void moveToPlan(ISelection plans, Set<Plan> from, Set<AbstractPlan> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<Plan> selectedPland = sel.toList();
		from.removeAll(selectedPland);
		for(Plan p : selectedPland) {
			to.add(p);
		}
		//to.addAll(selectedPland);
		
		plansViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveToPlanType(ISelection plans, Set<PlanType> from, Set<AbstractPlan> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<PlanType> selectedPland = sel.toList();
		from.removeAll(selectedPland);
		for(PlanType p : selectedPland) {
			to.add(p);
		}
		//to.addAll(selectedPland);
		
		plantypeViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveToBehaviour(ISelection plans, Set<BehaviourConfiguration> from, Set<AbstractPlan> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<BehaviourConfiguration> selectedPland = sel.toList();
		from.removeAll(selectedPland);
		for(BehaviourConfiguration p : selectedPland) {
			to.add(p);
		}
		//to.addAll(selectedPland);
		
		behaviourViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveToPP(ISelection plans, Set<AbstractPlan> from, Set<AbstractPlan> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<AbstractPlan> selectedPland = sel.toList();
		from.removeAll(selectedPland);
		for(AbstractPlan p : selectedPland) {
			to.add(p);
		}
		//to.addAll(selectedPland);
		
		planningProblemViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveFromPlan(ISelection plans, Set<AbstractPlan> from, Set<Plan> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<AbstractPlan> selectedPland = sel.toList();
		for(AbstractPlan p : selectedPland) {
			if( p instanceof Plan ) {
				to.add((Plan)p);
				from.remove(p);
			}
		}
		//to.addAll(selectedPland);
		
		plansViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveFromPlanType(ISelection plans, Set<AbstractPlan> from, Set<PlanType> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<AbstractPlan> selectedPland = sel.toList();
		for(AbstractPlan p : selectedPland) {
			if( p instanceof PlanType ) {
				to.add((PlanType)p);
				from.remove(p);
			}
		}
		//to.addAll(selectedPland);
		
		plantypeViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveFromBehaviour(ISelection plans, Set<AbstractPlan> from, Set<BehaviourConfiguration> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<AbstractPlan> selectedPland = sel.toList();
		for(AbstractPlan p : selectedPland) {
			if( p instanceof BehaviourConfiguration ) {
				to.add((BehaviourConfiguration)p);
				from.remove(p);
			}
		}
		//to.addAll(selectedPland);
		
		behaviourViewer.refresh();
		planningElementsViewer.refresh();
	}
	@SuppressWarnings("unchecked")
	private void moveFromPP(ISelection plans, Set<AbstractPlan> from, Set<AbstractPlan> to){
		IStructuredSelection sel = (IStructuredSelection)plans;
		List<AbstractPlan> selectedPland = sel.toList();
		for(AbstractPlan p : selectedPland) {
			if( p instanceof PlanningProblem ) {
				to.add(p);
				from.remove(p);
			}
		}
		//to.addAll(selectedPland);
		
		planningProblemViewer.refresh();
		planningElementsViewer.refresh();
	}
	
	private void initializePlans(){
		// Get all planFiles within the workspace
		Set<IFile> planFiles = PlanEditorUtils.collectAllFilesWithExtension("pml");
		
		plansViewerList = new HashSet<Plan>();
		
		// Load all plans into the resourceset
		for(IFile file : planFiles)
			plansViewerList.add((Plan)domain.load(file).getContents().get(0));
		
		planningProblemViewerList = new HashSet<AbstractPlan>();
		
		// If the the page was initialized with a plantype 
		// we will add all plans in the plantype to the 
		// plantypeViewerList and remove those from the
		// plansViewerList
		if(pp != null){
			List<AbstractPlan> plans = pp.getPlans();
			planningProblemViewerList.addAll(plans);
			
			// TODO: This is not working cause the plan object from the planningproblem
			// are different from those which are in the plansViewerList. This is
			// caused by loading them through different editingdomains
			plansViewerList.removeAll(plans);
		}
		
		// Set the files as input for the viewer
		plansViewer.setInput(plansViewerList);
	}
	
	private void initializeBehaviours(){
		// Get all planFiles within the workspace
		Set<IFile> behFiles = PlanEditorUtils.collectAllFilesWithExtension("beh");
		
		behaviourViewerList = new HashSet<BehaviourConfiguration>();
		
		// Load all plans into the resourceset
		for(IFile file : behFiles) {
			Behaviour beh = (Behaviour)domain.load(file).getContents().get(0);
			for(BehaviourConfiguration config : beh.getConfigurations())
				behaviourViewerList.add(config);
		}
		
		// If the the page was initialized with a plantype 
		// we will add all plans in the plantype to the 
		// plantypeViewerList and remove those from the
		// plansViewerList
		if(pp != null){
			List<AbstractPlan> plans = pp.getPlans();
			planningProblemViewerList.addAll(plans);
			
			// TODO: This is not working cause the plan object from the behaviours
			// are different from those which are in the plansViewerList. This is
			// caused by loading them through different editingdomains
			behaviourViewerList.removeAll(plans);
		}
		//planningElementsViewer.setInput(planningProblemViewerList);
		
		// Set the files as input for the viewer
		behaviourViewer.setInput(behaviourViewerList);
	}
	
	private void initializePlanningProblems(){
		// Get all planFiles within the workspace
		Set<IFile> ppFiles = PlanEditorUtils.collectAllFilesWithExtension("pp");
		
		existPlanningProblemViewerList = new HashSet<AbstractPlan>();
		
		// Load all plans into the resourceset
		for(IFile file : ppFiles) {
			existPlanningProblemViewerList.add((AbstractPlan)domain.load(file).getContents().get(0));
		}
		
		// If the the page was initialized with a plantype 
		// we will add all plans in the plantype to the 
		// plantypeViewerList and remove those from the
		// plansViewerList
		if(pp != null){
			List<AbstractPlan> plans = pp.getPlans();
			planningProblemViewerList.addAll(plans);
			
			// TODO: This is not working cause the plan object from the pp
			// are different from those which are in the plansViewerList. This is
			// caused by loading them through different editingdomains
			existPlanningProblemViewerList.removeAll(plans);
		}
		//planningElementsViewer.setInput(existPlanningProblemViewerList);
		
		// Set the files as input for the viewer
		planningProblemViewer.setInput(existPlanningProblemViewerList);
	}
	
	private void initializePlanTypes(){
		// Get all planFiles within the workspace
		Set<IFile> planTFiles = PlanEditorUtils.collectAllFilesWithExtension("pty");
		
		plantypeViewerList = new HashSet<PlanType>();
		
		// Load all plans into the resourceset
		for(IFile file : planTFiles)
			plantypeViewerList.add((PlanType)domain.load(file).getContents().get(0));
		
		// If the the page was initialized with a plantype 
		// we will add all plans in the plantype to the 
		// plantypeViewerList and remove those from the
		// plansViewerList
		if(pp != null){
			List<AbstractPlan> plans = pp.getPlans();
			planningProblemViewerList.addAll(plans);
			
			// TODO: This is not working cause the plan object from the plantype
			// are different from those which are in the plansViewerList. This is
			// caused by loading them through different editingdomains
			plantypeViewerList.removeAll(plans);
		}
		
		// Set the files as input for the viewer
		plantypeViewer.setInput(plantypeViewerList);
	}
	
	private void initializeGoal(){
		if( pp != null ) {
			if( pp.getConditions() != null ) {
				for(int i=0; i<pp.getConditions().size(); i++) {
					if( pp.getConditions().get(i) instanceof Result ) {
						name.setText(pp.getConditions().get(i).getName());
						nameS = name.getText();
						comment.setText(pp.getConditions().get(i).getComment());
						commentS = comment.getText();
						condition.setText(pp.getConditions().get(i).getConditionString());
						conditionS = condition.getText();
						break;
					}
				}
			}
		}
	}
	
	private void initializeInput(){
		initializePlans();
		initializeBehaviours();
		initializePlanTypes();
		initializePlanningProblems();
		initializeGoal();
		planningElementsViewer.setInput(planningProblemViewerList);
	}
	
	/**
	 * Ensures that both text fields are set.
	 */
	private void dialogChanged() {
		
		conditionS = condition.getText();
		nameS = name.getText();
		commentS = comment.getText();
		
		if( !(planningElementsViewer.getTable().getItemCount() > 0) ){
			updateStatus("Select at least one plan to add to the plantype!");
			return;
		}
		else if( !(name.getText().length() > 0) || !(condition.getText().length() > 0) ) {
			updateStatus("Enter a name for goal and a conditionstring!");
			return;
		}
			
		updateStatus(null);
	}
	
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public Set<AbstractPlan> getPlanningProblemViewerList() {
		return planningProblemViewerList;
	}

	public String getName() {
		return nameS;
	}

	public String getComment() {
		return commentS;
	}

	public String getCondition() {
		return conditionS;
	}

}
