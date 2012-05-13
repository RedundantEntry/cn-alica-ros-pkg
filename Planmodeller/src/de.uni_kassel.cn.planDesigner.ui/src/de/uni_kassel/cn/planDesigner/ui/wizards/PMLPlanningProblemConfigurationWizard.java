package de.uni_kassel.cn.planDesigner.ui.wizards;

import java.io.IOException;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.wizard.Wizard;
import de.uni_kassel.cn.alica.AbstractPlan;
import de.uni_kassel.cn.alica.PlanningProblem;
import de.uni_kassel.cn.alica.Result;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.ui.wizards.pages.PMLNewPlanningProblemConfigurationWizardPage;

public class PMLPlanningProblemConfigurationWizard extends Wizard {

	private PMLNewPlanningProblemConfigurationWizardPage page;
	
	private PlanningProblem pp;
	
	private PMLTransactionalEditingDomain domain;
	
	public PMLPlanningProblemConfigurationWizard(PlanningProblem pp) {
		super();
		setWindowTitle("Configure planningProblem " +pp.getName());
		
		this.pp = pp;
	}
	
	@Override
	public void addPages() {
		page = new PMLNewPlanningProblemConfigurationWizardPage(getEditingDomain(), pp);
		addPage(page);
	}
	
	@Override
	public boolean performFinish() {
		// Save the resource where the plantype we are configuring is in
		getEditingDomain().getCommandStack().execute(new AbstractCommand(){

			@Override
			public boolean canUndo() {
				return false;
			}

			@Override
			protected boolean prepare() {
				return true;
			}

			public void execute() {
				
				pp.getPlans().clear();
				
				for(AbstractPlan ap : page.getPlanningProblemViewerList()) {
					pp.getPlans().add(ap);
				}
				
				for(int i=0; i<pp.getConditions().size(); i++) {
					if( pp.getConditions().get(i) instanceof Result ) {
						pp.getConditions().get(i).setName(page.getName());
						pp.getConditions().get(i).setComment(page.getComment());
						pp.getConditions().get(i).setConditionString(page.getCondition());		
						break;
					}
				}
				
				// Save the planType
				Resource ppResource = pp.eResource();
				try {
					ppResource.save(ppResource.getResourceSet().getLoadOptions());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			public void redo() {
				// Nothing to redo since we cannot undo
			}
			
		});
		
		return true;
	}
	
	private PMLTransactionalEditingDomain getEditingDomain() {
		if(domain == null)
			domain = (PMLTransactionalEditingDomain)TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(
					IEditorConstants.PML_TRANSACTIONAL_EDITING_DOMAIN_ID);
		
		return domain;
	}
}
