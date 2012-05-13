package de.uni_kassel.cn.planDesigner.utility.ui.util;

import org.eclipse.emf.transaction.RunnableWithResult;

import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;

public abstract class EMFRunnable
{
	PMLTransactionalEditingDomain domain = UtilityActivator.getDefault().getEditingDomain();
	
	public Object run()
	{
		Object ret = null;
		try
		{
			ret = (Object) domain.runExclusive(new RunnableWithResult.Impl<Object>()
			{
				public void run()
				{
					setResult(doStuff());
				}
			});
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		return ret;
	}
	
	abstract public Object doStuff();
}
