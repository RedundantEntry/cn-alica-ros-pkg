package de.uni_kassel.cn.planDesigner.utility.ui.MasterDetail;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.TableWrapData;

import de.uni_kassel.cn.alica.AlicaPackage;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.impl.UtilityImpl;
import de.uni_kassel.cn.planDesigner.translator.Translator;

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
public class ExpressionSection extends UtilityDetailsPageSection
{
	private Text	expressionText;

	private Text	expressionTextTranslated;
	
	public void createSection(Composite parent)
	{
		FormToolkit tk = getManagedForm().getToolkit();

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

		expressionText.addListener(SWT.Modify, new ModifyListener());
		expressionText.addListener(SWT.KeyDown, new EnterPressedListener());
		expressionText.addListener(SWT.KeyDown, new ExpressionListener());

		// Translation
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

	private class ExpressionListener implements Listener
	{
		public void handleEvent(Event event)
		{
			String text = expressionText.getText();

			String transtext = Translator.translate(text);
			// String transtext = text;

			expressionTextTranslated.setText(transtext);
			expressionTextTranslated.update();
		}
	}

	@Override
	public void commit(boolean onSave)
	{
		final CompoundCommand cc = new CompoundCommand();

		try
		{
			getEditingDomain().runExclusive(new Runnable() 
			{
				public void run()
				{
					
					if (getInput() instanceof UtilityReference)
					{
						Command uc = SetCommand.create(getEditingDomain(), ((UtilityReference) getInput()).getUtility(), AlicaPackage.eINSTANCE.getUtility_Expression(), expressionText.getText());
						cc.append(uc);
					} else if (getInput() instanceof Utility)
					{
						Command uc = SetCommand.create(getEditingDomain(), getInput(), AlicaPackage.eINSTANCE.getPlanElement_Name(), expressionText.getText());
						cc.append(uc);
					}
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		getCommandStack().execute(cc);

		super.commit(onSave);
	}

	private void refreshText()
	{
		if(this.expressionText == null || this.expressionText.isDisposed())
		{
			return;
		}
		
		 // clear
		this.expressionText.setText("");

		// if nothing is selected...
		if (getInput() == null)
		{
			return;
		}

		// set
		String name = null;

		if (getInput() instanceof UtilityImpl)
		{
			try
			{
				name = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>() {
					public void run()
					{
						setResult(((UtilityImpl) getInput()).getExpression());
					}
				});
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		} else if (getInput() instanceof UtilityReference)
		{
			try
			{
				name = (String) getEditingDomain().runExclusive(new RunnableWithResult.Impl<String>() {
					public void run()
					{
						setResult(((UtilityReference) getInput()).getUtility().getExpression());
					}
				});
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
		expressionText.setText(name);
	}

	@Override
	public void refresh()
	{
		super.refresh();
		
		refreshText();
	}
}
