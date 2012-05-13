package de.uni_kassel.cn.planDesigner.utility;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import de.uni_kassel.cn.planDesigner.ui.edit.PMLTransactionalEditingDomain;
import de.uni_kassel.cn.planDesigner.ui.util.IEditorConstants;
import de.uni_kassel.cn.planDesigner.utility.ui.ManagedEditController;
import de.uni_kassel.cn.planDesigner.utility.ui.UtilityView;

/**
 * The activator class controls the plug-in life cycle
 */
public class UtilityActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "de.uni_kassel.cn.planDesigner.utility.ui";

	// The shared instance
	private static UtilityActivator plugin;
	
	/**
	 * The constructor
	 */
	public UtilityActivator() {
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		// there should only be one workbench window
		// ...thus we only need to register the SelectionListener to that
		this.listener = PlanEditorSelectionListener.getInstance();
		getWorkbench().getActiveWorkbenchWindow().getSelectionService().addSelectionListener(this.listener);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static UtilityActivator getDefault() {
		return plugin;
	}
	
	@Override
	protected void initializeImageRegistry(ImageRegistry reg)
	{
		URL baseURL = getBundle().getEntry("/"); //$NON-NLS-1$

		Field fields[] = Constants.class.getFields();	
		for(int i=0; i < fields.length; i++) {
			Field f = fields[i];
			if (f.getType() != String.class) { 
				continue;
			}
			String name = f.getName();
			if (name.startsWith("ICON_")) {   //$NON-NLS-1$ //$NON-NLS-2$
				try {
					String value = (String) f.get(null);
					createImageDescriptor(value, baseURL);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	/**
	 * Creates an image descriptor and places it in the image registry.
	 */
	private void createImageDescriptor(String id, URL baseURL) {
		URL url = null;
		try {
			url = new URL(baseURL, IEditorConstants.ICON_PATH + id);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		getImageRegistry().put(id, desc);
	}

	
	////////////
	////////////
	////////////
	private UtilityView view;
	
	public void setView(UtilityView utilityView) 
	{
		this.view = utilityView;
	}
	
	public UtilityView getUtilityView()
	{
		return this.view;
	}

	private PlanEditorSelectionListener listener;
	
	public PlanEditorSelectionListener getPlanEditorSelectionListener()
	{
		return this.listener;
	}
	
	// TODO: it's a HACK
	public PMLTransactionalEditingDomain getEditingDomain(){
		return (PMLTransactionalEditingDomain) TransactionalEditingDomain.Registry.INSTANCE.getEditingDomain(IEditorConstants.PML_TRANSACTIONAL_EDITING_DOMAIN_ID); 
	}
	
	private ManagedEditController ec;
	
	public ManagedEditController getDefaultUtilityEditController()
	{
		if(this.ec == null)
		{
			this.ec = new ManagedEditController();
		}
		
		return this.ec;
	}
}
