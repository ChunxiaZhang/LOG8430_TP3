package com.pxz.log8430.menutp3;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;
import org.eclipse.jdt.core.ICompilationUnit;

public class DefineMenuTp3 extends ExtensionContributionFactory {
	
	final private String EXTENTION_ID = "com.pxz.menutp3";
	final private String CONTTRIBUTE_COMMAND_LABEL = "commandLabel";
	final private String CONTTRIBUTE_COMMAND_TARGET_TYPE = "commandTargetType";
	final private String CONTTRIBUTE_COMMAND_ID = "commandId";

	@Override
	public void createContributionItems(IServiceLocator serviceLocator,
			IContributionRoot additions) {
		
		System.out.println("start to create contribution items");
				
		IExtensionPoint ep = Platform.getExtensionRegistry().getExtensionPoint(EXTENTION_ID);
		
		IExtension[] extensions = ep.getExtensions(); //get all extensions that extend this extension point
		
		MenuManager menu = new MenuManager("Command Menu"); //create a menu item to contain commands in sub-extensions
		
		// get the selected element
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
        Object selectedElement = selection.getFirstElement();
         

		
		for(int i = 0; i < extensions.length; i++) {
			
			String menuLabel = null;
			String commandID = null;
			String commandTargetType = null;
			
			IConfigurationElement[] configurations = extensions[i].getConfigurationElements();
			
			for(int j = 0; j < configurations.length; j++) {
				if("commandDescription".equals(configurations[j].getName())) {
					menuLabel = configurations[j].getAttribute(CONTTRIBUTE_COMMAND_LABEL);
					commandTargetType = configurations[j].getAttribute(CONTTRIBUTE_COMMAND_TARGET_TYPE);
					commandID = configurations[j].getAttribute(CONTTRIBUTE_COMMAND_ID);
										
					break; //it can only contain one command
				}
			}
			
			
			if (menuLabel != null && commandID != null && commandTargetType != null){
			
				// To judge if the selected element's type correspond to the specified target type
				boolean selectedElementTypeCorrespondCommandTargetType = false;
				
				String[] types = commandTargetType.split(",");
				
				for (int k=0; k < types.length; k++){
					if(selectedElement instanceof ICompilationUnit) {
						selectedElementTypeCorrespondCommandTargetType = true;
						break;
					} /*
					try {
						//Class<?> aClass = Class.forName("org.eclipse.jdt.core.ICompilationUnit");//types[k]);
						
						//if (aClass.isInstance(selectedElement)){
						if(selectedElement instanceof ICompilationUnit) {
							selectedElementTypeCorrespondCommandTargetType = true;
							break;
						}
					} catch(ClassNotFoundException ex) {
				        System.out.println(ex.toString());
				    }*/
				}
				
				
				if (selectedElementTypeCorrespondCommandTargetType){
					
					// add the sub-extension's command to the popup menu
					CommandContributionItemParameter p = 
		                    new CommandContributionItemParameter(
		                            serviceLocator, 
		                            extensions[i].getUniqueIdentifier(), 
		                            commandID,
		                            CommandContributionItem.STYLE_PUSH);
		
		            p.label = menuLabel;      
		            menu.add(new CommandContributionItem(p));
				}
			}
		}
		
		// if there is no correspond command, don't add the menu to the root
		if (menu.getSize() > 0){
			additions.addContributionItem(menu, null);
		}
		
	}

}
