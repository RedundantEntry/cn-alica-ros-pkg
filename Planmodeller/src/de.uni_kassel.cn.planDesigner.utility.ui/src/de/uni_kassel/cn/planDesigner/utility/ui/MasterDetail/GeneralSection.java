package de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.impl.UtilityImpl;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityUtils;

/**
 * This actually is not a FormPart, but it should do the same things as one. The
 * reason is that it is used by the DetailsPage which itself is a FormPart. The
 * DetailsPage forwards events to this page and to make it easier the
 * AbstractFormPart is derived.
 * <br>
 * <br>
 * The usage as a stand-alone form part is not tested 
 * 
 * @author till
 * 
 */
public class GeneralSection extends UtilityDetailsPageSection
{
	private Text	nameText;

	private Text	weightText;

	public void createSection(Composite parent)
	{
		FormToolkit tk = getManagedForm().getToolkit();

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
		nameText.addListener(SWT.Modify, new ModifyListener());
		nameText.addListener(SWT.KeyDown, new EnterPressedListener());

		l = tk.createLabel(c, "Weight");
		gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		l.setLayoutData(gd);

		weightText = tk.createText(c, "");
		gd = new GridData(SWT.FILL, SWT.NONE, true, false);
		weightText.setLayoutData(gd);
		weightText.addListener(SWT.Modify, new ModifyListener());
		weightText.addListener(SWT.KeyDown, new EnterPressedListener());
	}

	@Override
	public void commit(boolean onSave)
	{
		final CompoundCommand cc = new CompoundCommand();

		if (getInput() instanceof UtilityReference)
		{
			String weightS = weightText.getText();
			try
			{
				Float weightF = Float.parseFloat(weightS);
				Command wc = SetCommand.create(getEditingDomain(), getInput(), AlicaPackage.eINSTANCE.getUtilityReference_Weight(), weightF.floatValue());
				cc.append(wc);
			} catch (NumberFormatException e)
			{
				// nop
			}

			Command uc = SetCommand.create(getEditingDomain(), UtilityUtils.getUtility((UtilityReference)getInput()), AlicaPackage.eINSTANCE.getPlanElement_Name(), nameText.getText());
			cc.append(uc);
		} else if (getInput() instanceof Utility)
		{
			Command uc = SetCommand.create(getEditingDomain(), getInput(), AlicaPackage.eINSTANCE.getPlanElement_Name(), nameText.getText());
			cc.append(uc);
		}

		getCommandStack().execute(cc);

		super.commit(onSave);
	}
	
	private void refreshText()
	{
		if(this.nameText == null || this.nameText.isDisposed())
		{
			return;
		}

		// clear
		this.nameText.setText("");

		// if nothing is selected...
		if (getInput() == null)
		{
			return;
		}

		// set
		String name = null;

		if (getInput() instanceof UtilityImpl)
		{
			name = UtilityUtils.getUtilityName((UtilityImpl)getInput());
		} 
		else if (getInput() instanceof UtilityReference)
		{
			name = UtilityUtils.getUtilityName((UtilityReference)getInput());
		}

		nameText.setText(name);
	}

	private void refreshWeight()
	{
		if(this.weightText == null || this.weightText.isDisposed())
		{
			return;
		}
		
		// clear
		weightText.setText("");

		// if nothing is selected...
		if (getInput() == null)
		{
			return;
		}

		// set
		String w = "";

		if (getInput() instanceof UtilityReference)
		{
			try
			{
				w = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>() {
					public void run()
					{
						setResult(String.valueOf(((UtilityReference) getInput()).getWeight()));
					}
				});
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		weightText.setText(w);
	}

	@Override
	public void refresh()
	{
		refreshText();
		refreshWeight();

		super.refresh();
	}

	@Override
	public void setFocus()
	{
		if (this.nameText != null)
		{
			this.nameText.setFocus();
		}
	}
}
