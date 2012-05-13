package de.uni_kassel.cn.planDesigner.utility.ui.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.EditorPart;

import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityRepository;
import de.uni_kassel.cn.alica.impl.UtilityImpl;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.IEditControllable;
import de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail.UtilityDetailsPage;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityRepositoryUtils;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityUtils;

public class UtilityEditor extends EditorPart implements IEditControllable
{
	private ManagedForm	form	= null;
	
	private TableViewer tv = null;
	
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException
	{
		IFile file = ((IFileEditorInput) input).getFile();

		if (file == null)
		{
			throw new PartInitException("File for " + input + " not found");
		}

		Resource resource = UtilityRepositoryUtils.getResource();
		if (resource != null)
		{
			// TODO: get resource via selected file
		} 
		else
		{
			throw new PartInitException("Could not load the repo resource");
		}

		// set some values for the editor
		setSite(site);
		setInput(input);
		setPartName("Utility Editor");
		
		// register to the default EditController
		UtilityActivator activator = UtilityActivator.getDefault();
		activator.getDefaultUtilityEditController().addToControllables(this);
		
		// TODO: do in it in the Activator(?)
		// add Repository to controlled items
		UtilityRepository repo = UtilityRepositoryUtils.getRepository(activator.getEditingDomain());
		activator.getDefaultUtilityEditController().addToObject(repo);
	}

	public boolean isDirty()
	{
		return false;
	}

	@Override
	public boolean isSaveAsAllowed()
	{
		return false;
	}
	
	@Override
	public void doSave(IProgressMonitor monitor)
	{
	}

	@Override
	public void doSaveAs()
	{
	}
	
	@Override
	public void createPartControl(Composite parent)
	{
		FormToolkit	tk = new FormToolkit(parent.getDisplay());
		ScrolledForm sf = tk.createScrolledForm(parent);
		sf.setText("Utility Repository Editor");
		this.form = new ManagedForm(tk, sf);
		MasterDetailsBlock mdb = new MasterDetailsBlock() 
		{
			@Override
			protected void createMasterPart(IManagedForm managedForm, Composite parent)
			{
				FormToolkit tk = managedForm.getToolkit();

				Section masterSec = tk.createSection(parent, Section.DESCRIPTION | Section.TITLE_BAR);
				masterSec.setText("Utility");
				masterSec.setDescription("Select a utility");
				masterSec.marginWidth = 10;
				masterSec.marginHeight = 5;

				Composite c = tk.createComposite(masterSec, SWT.WRAP);
				GridLayout layout = new GridLayout();
				layout.numColumns = 2;
				layout.marginWidth = 2;
				layout.marginHeight = 2;
				c.setLayout(layout);

				tk.paintBordersFor(c);
				masterSec.setClient(c);
				
				Table l = new Table(c, SWT.MULTI);
				GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
				gd.heightHint = 20;
				gd.widthHint = 100;
				l.setLayoutData(gd);
				
//				Button addButton = tk.createButton(c, "Add...", SWT.PUSH);
//				gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
//				addButton.setLayoutData(gd);
				
				Button removeButton = tk.createButton(c, "Remove...", SWT.PUSH);
				gd = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
				removeButton.setLayoutData(gd);
				removeButton.addSelectionListener(new SelectionListener()
				{
					@Override
					public void widgetDefaultSelected(SelectionEvent e)
					{
						IHandlerService handlerService = (IHandlerService) UtilityActivator.getDefault().getWorkbench().getService(IHandlerService.class);
						try
						{
							handlerService.executeCommand("de.uni_kassel.cn.planDesigner.utility.purgeUtilityCommand", null);
						} 
						catch (Exception ev)
						{
							ev.printStackTrace();
						}
						
					}

					@Override
					public void widgetSelected(SelectionEvent e) 
					{
						widgetDefaultSelected(e);
					}
					
				});

				final SectionPart masterSectionPart = new SectionPart(masterSec);
				
				tv = new TableViewer(l);
				tv.setContentProvider(getTableViewerContentProvider());
				tv.setLabelProvider(getTableViewerLabelProvider());
				tv.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event)
					{
						// clear selection
						form.fireSelectionChanged(masterSectionPart, StructuredSelection.EMPTY);
						 
						// set new selection
						if (event.getSelection() instanceof IStructuredSelection)
						{
							IStructuredSelection sel = (StructuredSelection) event.getSelection();
							Object o = sel.getFirstElement();
							
							if(o != null)
							{
								form.fireSelectionChanged(masterSectionPart, sel);
							}
						}
					}
				});
				tv.setInput(new Object()); // TODO: ... gnar!
				
				managedForm.addPart(masterSectionPart);

				// set the master as SelectionProvider
				getEditorSite().setSelectionProvider(tv);
			}

			private IBaseLabelProvider getTableViewerLabelProvider()
			{
				return new LabelProvider() 
				{
					@Override
					public String getText(Object element)
					{
						if (element instanceof Utility)
						{
							return UtilityUtils.getUtilityName((Utility) element);
						}

						return null;
					}
				};
			}

			private IContentProvider getTableViewerContentProvider()
			{
				return new IStructuredContentProvider() 
				{
					@Override
					public Object[] getElements(Object inputElement)
					{
						PMLTransactionalEditingDomain editingDomain = UtilityActivator.getDefault().getEditingDomain();

						UtilityRepository repo = UtilityRepositoryUtils.getRepository(editingDomain);
						EList<Utility> utils = UtilityUtils.getUtilities(editingDomain, repo);

						return (Utility[]) utils.toArray(new Utility[utils.size()]);
					}

					@Override
					public void dispose()
					{
					}

					@Override
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
					{
					}
				};
			}

			@Override
			protected void createToolBarActions(IManagedForm managedForm)
			{
			}

			@Override
			protected void registerPages(DetailsPart detailsPart)
			{
				final UtilityDetailsPage detailsPage = getDetailsPage();
				detailsPart.registerPage(UtilityImpl.class, detailsPage);
			}
		};

		mdb.createContent(this.form);
	}
	
	private UtilityDetailsPage detailsPage;

	protected UtilityDetailsPage getDetailsPage()
	{
		if (this.detailsPage == null)
		{
			this.detailsPage = new UtilityDetailsPage();
			this.detailsPage.add(new UsageSection());
		}
		return this.detailsPage;
	}

	@Override
	public void setFocus()
	{
	}

	@Override
	public void dispose()
	{
		// unregister from the default EditController
		UtilityActivator.getDefault().getDefaultUtilityEditController().removeFromControllables(this);
		
		super.dispose();
	}

	@Override
	public void enterPressed(Widget source)
	{
	}

	@Override
	public void focusOutEvent(Widget source)
	{
	}

	@Override
	public void modifyEvent(Widget source)
	{
	}

	@Override
	public void selectionEvent(Object source)
	{
	}

	@Override
	public void updateView(Notification n)
	{
		if(n.getNotifier() instanceof EObject)
		{
//			form.refresh();		// this doesn't seem to refresh the master
//			form.reflow(true);
			tv.refresh();
		}
	}
}
