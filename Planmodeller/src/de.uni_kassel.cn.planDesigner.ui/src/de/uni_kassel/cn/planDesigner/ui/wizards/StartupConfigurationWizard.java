package de.uni_kassel.cn.planDesigner.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.ide.undo.CreateProjectOperation;
import org.eclipse.ui.ide.undo.WorkspaceUndoUtil;


import de.uni_kassel.cn.planDesigner.ui.PlanDesignerActivator;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.wizards.pages.StartupConfigurationProjectsPage;

/**
 * 
 * @author till
 * 
 */
/*
 * 
 * see: BasicNewProjectResourceWizard
 */
public class StartupConfigurationWizard extends Wizard implements INewWizard
{

	private StartupConfigurationProjectsPage	projectsPage;

	public StartupConfigurationWizard()
	{
		setWindowTitle("Projects Configuration Wizard");
	}

	public void init(IWorkbench arg0, IStructuredSelection arg1)
	{
	}

	@Override
	public void addPages()
	{
		projectsPage = new StartupConfigurationProjectsPage();
		addPage(projectsPage);
	}

	@Override
	public boolean performFinish()
	{
		boolean ret = true;

		// obtain root path --> should be the git root (ES_ROOT)
		String rootPath = projectsPage.getRootPath();

		// create plans project
		// add "etc/plans"
		String projname = projectsPage.getPlanProjectName();
		if (!ResourcesPlugin.getWorkspace().getRoot().getProject(projname).exists())
		{
			ret &= createProject(projname, new Path(projectsPage.getPlanPath()));
		}

		// create expression validators project
		// add "src/Base/BehaviourEngine/ExpressionValidators"
		projname = projectsPage.getExprValProject();
		if (!ResourcesPlugin.getWorkspace().getRoot().getProject(projname).exists())
		{
			ret &= createProject(projname, new Path(projectsPage.getExprValPath()));
		}

		// create misc project
		// add "etc/Misc"
		projname = projectsPage.getMiscProject();
		if (!ResourcesPlugin.getWorkspace().getRoot().getProject(projname).exists())
		{
			ret &= createProject(projname, new Path(projectsPage.getMiscPath()));
		}

		// create roles project
		// add "etc/roles"
		projname = projectsPage.getRolesProject();
		if (!ResourcesPlugin.getWorkspace().getRoot().getProject(projname).exists())
		{
			ret &= createProject(projname, new Path(projectsPage.getRolesPath()));
		}
		IPreferenceStore store = PlanDesignerActivator.getDefault().getPreferenceStore();
		
		store.setValue(IEditorConstants.PREF_ROLE_DEFINITION_CONTAINER, "/"+projectsPage.getRolesProject());
		store.setValue(IEditorConstants.PREF_CAPABILITY_DEFINITION_CONTAINER,  "/"+projectsPage.getRolesProject());
		store.setValue(IEditorConstants.PREF_CODEGEN_BASE_PATH, "/"+projectsPage.getExprValProject());
		store.setValue(IEditorConstants.PREF_MISC_PATH,"/"+projectsPage.getMiscProject());
		return ret;
	}

	private boolean createProject(String proj, IPath location)
	{
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IProject project = root.getProject(proj);

		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProjectDescription description = workspace.newProjectDescription(project.getName());
		description.setLocationURI(location.toFile().toURI());

		// create the new project operation
		IRunnableWithProgress op = new IRunnableWithProgress()
		{
			public void run(IProgressMonitor monitor) throws InvocationTargetException
			{
//				CreateProjectOperation op = new CreateProjectOperation(description, ResourceMessages.NewProject_windowTitle);
				CreateProjectOperation op = new CreateProjectOperation(description, "Creating new project");
				try
				{
					// see bug https://bugs.eclipse.org/bugs/show_bug.cgi?id=219901
					// directly execute the operation so that the undo state is
					// not preserved. Making this undoable resulted in too many
					// accidental file deletions.
					op.execute(monitor, WorkspaceUndoUtil.getUIInfoAdapter(getShell()));
				}
				catch (ExecutionException e)
				{
					throw new InvocationTargetException(e);
				}
			}
		};

		// run the new project creation operation
		try
		{
			getContainer().run(true, true, op);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
			return false;
		}
		catch (InvocationTargetException e)
		{
			Throwable t = e.getTargetException();
			if (t instanceof ExecutionException && t.getCause() instanceof CoreException)
			{
//				CoreException cause = (CoreException) t.getCause();
//				StatusAdapter status;
//				if (cause.getStatus().getCode() == IResourceStatus.CASE_VARIANT_EXISTS)
//				{
//					status = new StatusAdapter(StatusUtil.newStatus(IStatus.WARNING, NLS.bind(ResourceMessages.NewProject_caseVariantExistsError, project.getName()), cause));
//				}
//				else
//				{
//					status = new StatusAdapter(StatusUtil.newStatus(cause.getStatus().getSeverity(), ResourceMessages.NewProject_errorMessage, cause));
//				}
//				status.setProperty(StatusAdapter.TITLE_PROPERTY, ResourceMessages.NewProject_errorMessage);
//				StatusManager.getManager().handle(status, StatusManager.BLOCK);
			}
			else
			{
//				StatusAdapter status = new StatusAdapter(new Status(IStatus.WARNING, IDEWorkbenchPlugin.IDE_WORKBENCH, 0, NLS.bind(ResourceMessages.NewProject_internalError, t.getMessage()), t));
//				status.setProperty(StatusAdapter.TITLE_PROPERTY, ResourceMessages.NewProject_errorMessage);
//				StatusManager.getManager().handle(status, StatusManager.LOG | StatusManager.BLOCK);
			}

			e.printStackTrace();
			return false;
		}

		// project.create(progressMonitor);
		// project.open(progressMonitor);

		return true;
	}
}
