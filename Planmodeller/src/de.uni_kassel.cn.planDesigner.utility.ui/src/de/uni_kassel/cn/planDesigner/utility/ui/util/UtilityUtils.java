package de.uni_kassel.cn.planDesigner.utility.ui.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;

import de.uni_kassel.cn.alica.Plan;
import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityReference;
import de.uni_kassel.cn.alica.UtilityRepository;
import de.uni_kassel.cn.alica.impl.PlanImpl;
import de.uni_kassel.cn.alica.util.AlicaResourceSet;
import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.CommonUtils;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;

public class UtilityUtils
{
	// TODO: wrap into WorkspaceJob
	private static Map<Long, Object> plans;
	
	public static Set<Object> getPlans(Utility utility)
	{
		if(plans == null)
		{
			plans = new HashMap<Long, Object>();
		}
		
		plans.clear();
		
		// get ResourceSet from Domain
		PMLTransactionalEditingDomain domain = UtilityActivator.getDefault().getEditingDomain();
		addPlansForResourceSet(domain.getResourceSet(), utility.getId());
		
		// add plans from a freshly loaded resource set
		// to get plans not yet loaded
		addPlansLoadNewResource(utility);
	
		return new HashSet<Object>(plans.values());
	}
	
	private static void addPlansLoadNewResource(Utility utility) 
	{
		Set<IFile> filesInWorkspace = CommonUtils.collectAllFilesWithExtension("pml");

		ResourceSet rSet = new AlicaResourceSet();
		for (IFile file : filesInWorkspace)
		{
			CommonUtils.load(rSet, file);
		}
		
		addPlansForResourceSet(rSet, utility.getId());
		
		for (Resource r : rSet.getResources())
		{
			r.unload();
		}
		
		rSet.getResources().clear();
		rSet = null;
	}
	
	private static void addPlansForResourceSet(ResourceSet set, Long searchId)
	{
		EList<Resource> resources =  new BasicEList<Resource>(set.getResources());
		for (Resource r : resources)
		{
			Object content = r.getContents().get(0);
			if(content instanceof Plan)
			{
				Plan plan = (Plan)content;
				EList<UtilityReference> utilities = plan.getUtilities();
				for(UtilityReference ur : utilities)
				{
					if(ur.getUtility().getId() == searchId && !plans.containsKey(plan.getId())) // TODO: make runable
					{
						plans.put(plan.getId(), plan);
					}
				}
			}
		}
	}
	
	/**
	 * Returns the utility ID for a given utility
	 * 
	 * @param domain
	 * @param u
	 * @return
	 */
	public static Long getUtilityID(PMLTransactionalEditingDomain domain, final Utility u)
	{
		Long l = (Long)new EMFRunnable()
		{
			@Override
			public Object doStuff()
			{
				return u.getId();
			}
		}.run();
		
		return l;
	}
	
	public static long getUtilityID(Utility u)
	{
		return getUtilityID(UtilityActivator.getDefault().getEditingDomain(), u);
	}

	/**
	 * Returns the utilities from the given repository
	 * 
	 * @param domain
	 * @param repo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static EList<Utility> getUtilities(PMLTransactionalEditingDomain domain, final UtilityRepository repo)
	{
		EList<Utility> utils = (EList<Utility>)new EMFRunnable()
		{
			@Override
			public Object doStuff()
			{
				return repo.getUtilities();
			}
		}.run();
		
		return utils;
	}
	
	public static String getUtilityName(final UtilityReference ref)
	{
		return getUtilityName(getUtility(ref));
	}
	
	public static String getUtilityName(final Utility util)
	{
		String name = (String)new EMFRunnable()
		{
			@Override
			public Object doStuff()
			{
				return util.getName();
			}
		}.run();
		
		return name;
	}

	public static String getPlanName(final Plan plan)
	{
		String name = (String)new EMFRunnable()
		{
			@Override
			public Object doStuff()
			{
				return plan.getName();
			}
		}.run();
		
		return name;
	}
	
	public static Utility getUtility(final UtilityReference ref)
	{
		Utility util = (Utility)new EMFRunnable()
		{
			@Override
			public Object doStuff()
			{
				return ref.getUtility();
			}
		}.run();
		
		return util;
	}

	public static UtilityReference getUtilityReference(final Utility u, final PlanImpl plan)
	{		
		UtilityReference utilRef = (UtilityReference)new EMFRunnable()
		{
			@Override
			public Object doStuff()
			{
				for(UtilityReference ref : plan.getUtilities())
				{
					if(ref.getUtility().getId() == u.getId())
					{
						return ref;
					}
				}
				
				return null;
			}
		}.run();
		
		return utilRef;
	}
	
}
