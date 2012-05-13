package de.uni_kassel.cn.planDesigner.utility.handler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;

import de.uni_kassel.cn.alica.Utility;
import de.uni_kassel.cn.alica.UtilityRepository;
import de.uni_kassel.cn.planDesigner.utility.Constants;
import de.uni_kassel.cn.planDesigner.utility.UtilityActivator;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityRepositoryUtils;
import de.uni_kassel.cn.planDesigner.utility.ui.util.UtilityUtils;

public class UtilityRepositoryContributionItem extends CompoundContributionItem
{

	public UtilityRepositoryContributionItem()
	{
	}

	public UtilityRepositoryContributionItem(String id)
	{
		super(id);
	}

	@Override
	protected IContributionItem[] getContributionItems()
	{
		UtilityRepository repo = UtilityRepositoryUtils.getRepository(UtilityActivator.getDefault().getEditingDomain());
		EList<Utility> utilities = repo.getUtilities();

        Map<String, String> params;
		LinkedList<IContributionItem> list = new LinkedList<IContributionItem>();
		for(Utility u : utilities)
		{
			params = new HashMap<String, String>();
	        params.put(Constants.ADD_UTILITY_COMMAND_PARAMETER_ID, Long.toString(UtilityUtils.getUtilityID(u)));
	        
			CommandContributionItemParameter para = new CommandContributionItemParameter(PlatformUI.getWorkbench().getActiveWorkbenchWindow(), 
																							"de.uni_kassel.plandesigner.addUtilityCommandContributionItem", 
																							"de.uni_kassel.cn.planDesigner.utility.addUtilityCommand",
																							params,
																							null,										// icon
																							null,										// disabled icon
																							null,										// hover icon
																							UtilityUtils.getUtilityName(u),				// label
																							null, 										// mnemonic
																							Long.toString(UtilityUtils.getUtilityID(u)),// tooltip TODO: make it work...
																							CommandContributionItem.STYLE_PUSH, 		// style
																							null,										// help context id
																							false										// visibleEnable
																						);
			list.add(new CommandContributionItem(para));
		}
		
		return (IContributionItem[]) list.toArray(new IContributionItem[list.size()]);
	}

}
