package com.pxz.log8430.menutp3;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

public class DefineMenuTp3 extends ExtensionContributionFactory {

	boolean isFolder;
	boolean isFile;
	
	@Override
	public void createContributionItems(IServiceLocator serviceLocator,
			IContributionRoot additions) {
		
		System.out.println("start to create contribution items");
		final String EXTENTION_ID = "com.pxz.menutp3";
		final String CONTTRIBUTE_NAME = "name";
		final String CONTTRIBUTE_TYPE = "type";
		final String CONTTRIBUTE_COMMAND_ID = "commandId";
		
		isFolder = false;
		isFile = false;
		
		//IExtension e = Platform.getExtensionRegistry().getExtension(EXTENTION_ID);
		
		
		IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
		IStructuredSelection selection = (IStructuredSelection) window.getSelectionService().getSelection();
        if(selection == null) {
        	System.out.println("selection is null");
        }
        
        Object firstElement = selection.getFirstElement();
         
        if(firstElement instanceof ICompilationUnit) {
        	ICompilationUnit element = (ICompilationUnit)firstElement;
        	isFile = true;
        	System.out.println("Type: " + element.getResource().getType());
        	System.out.println("Rescource name:" + element.getResource().getName());
        } else if(firstElement instanceof IPackageFragmentRoot) {
        	IPackageFragmentRoot element = (IPackageFragmentRoot) firstElement; 
        	isFolder = true;
        	System.out.println("Type: "+element.getResource().getType());
        	System.out.println("Rescource name:" + element.getResource().getName());
        } else if(firstElement instanceof IPackageFragment) {
        	IPackageFragment element = (IPackageFragment) firstElement; 
        	isFolder = true;
        	System.out.println("Type: "+ element.getResource().getType());
        	System.out.println("Rescource name:" + element.getResource().getName());
        } 
        
        if(!isFolder && !isFile) {
        	return;
        }
		IExtensionPoint ep = Platform.getExtensionRegistry().getExtensionPoint(EXTENTION_ID);
		
		IExtension[] extensions = ep.getExtensions(); //get all extensions
		
		MenuManager menu = new MenuManager("Menu Tp3");
		
		
		for(int i = 0; i < extensions.length; i++) {
			String menuName = null;
			String menuType = null;
		
			
			String commandID = null;
			
			IConfigurationElement[] configurations = extensions[i].getConfigurationElements();
			
			for(int j = 0; j < configurations.length; j++) {
				if("menu".equals(configurations[j].getName())) {
					menuName = configurations[j].getAttribute(CONTTRIBUTE_NAME);
					menuType = configurations[j].getAttribute(CONTTRIBUTE_TYPE);
					commandID = configurations[j].getAttribute(CONTTRIBUTE_COMMAND_ID);
					System.out.println("commandID " + commandID);
				}			
			}
			
			if(isSuitable(menuType)) {
				
				CommandContributionItemParameter p = 
                    new CommandContributionItemParameter(
                            serviceLocator, 
                            extensions[i].getUniqueIdentifier(), 
                            commandID,
                            CommandContributionItem.STYLE_PUSH);

				p.label = menuName;      
				menu.add(new CommandContributionItem(p));
			}
		}
		
		additions.addContributionItem(menu, null);
		
	}

	private boolean isSuitable(String type) {
		boolean suitable = false;
		if(!type.contains(",")) {
			if("folder".equals(type) && isFolder) {
				suitable = true;
			} else if("file".equals(type) && isFile) {
				suitable = true;
			}
		} else {
		
			String[] types = type.split(",");
		
			System.out.println(types[0] + types[1]);
		
			if(types.length > 0) {
				for(int k = 0; k < types.length; k++) {
					if("folder".equals(types[k]) && isFolder) {
						suitable = true;
					}
					if("file".equals(types[k]) && isFile) {
						suitable = true;
					}
				}
			}
		}
		return suitable;
	}
}
