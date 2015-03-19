package com.pxz.log8430.menutp3;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

public class DefineMenuTp3 extends ExtensionContributionFactory {

	@Override
	public void createContributionItems(IServiceLocator serviceLocator,
			IContributionRoot additions) {
		
		System.out.println("start to create contribution items");
		final String EXTENTION_ID = "com.pxz.menutp3";
		final String CONTTRIBUTE_NAME = "name";
		final String CONTTRIBUTE_FOLDER = "folder";
		final String CONTTRIBUTE_FILE = "file";
		final String CONTTRIBUTE_COMMAND_ID = "commandId";
		
		
		IExtension e = Platform.getExtensionRegistry().getExtension(EXTENTION_ID);
		
		IExtensionPoint ep = Platform.getExtensionRegistry().getExtensionPoint(EXTENTION_ID);
		
		IExtension[] extensions = ep.getExtensions(); //get all extensions
		
		MenuManager menu = new MenuManager("Menu Tp3");
		
		for(int i = 0; i < extensions.length; i++) {
			String menuName = null;
			String menuForFoleder = null;
			String menuForFile = null;
			
			String commandID = null;
			
			IConfigurationElement[] configurations = extensions[i].getConfigurationElements();
			
			for(int j = 0; j < configurations.length; j++) {
				if("menutp3".equals(configurations[j].getName())) {
					menuName = configurations[j].getAttribute(CONTTRIBUTE_NAME);
					menuForFoleder = configurations[j].getAttribute(CONTTRIBUTE_FOLDER);
					menuForFile = configurations[j].getAttribute(CONTTRIBUTE_FILE);
				}
				if("handler".equals(configurations[j].getName())) {
					commandID = configurations[j].getAttribute(CONTTRIBUTE_COMMAND_ID);
				}
			}
			CommandContributionItemParameter p = 
                    new CommandContributionItemParameter(
                            serviceLocator, 
                            extensions[i].getUniqueIdentifier(), 
                            "org.eclipse.ui.file.exit",
                            CommandContributionItem.STYLE_PUSH);

            p.label = menuName;      
            menu.add(new CommandContributionItem(p));
		}
		
		additions.addContributionItem(menu, null);
		
	}

}
