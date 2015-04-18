package tp3.plugin.commandExtension;

import java.util.HashMap;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.menus.CommandContributionItem;
import org.eclipse.ui.menus.CommandContributionItemParameter;
import org.eclipse.ui.menus.ExtensionContributionFactory;
import org.eclipse.ui.menus.IContributionRoot;
import org.eclipse.ui.services.IServiceLocator;

import tp3.plugin.commands.Command;

public class InterfaceBuilder extends ExtensionContributionFactory {

	/* 
	 * After fetching and creating the commands, we build the menu items.
	 * (non-Javadoc)
	 * @see org.eclipse.ui.menus.AbstractContributionFactory#createContributionItems(org.eclipse.ui.services.IServiceLocator, org.eclipse.ui.menus.IContributionRoot)
	 */
	@Override
	public void createContributionItems(IServiceLocator serviceLocator,IContributionRoot additions) {
		
		HashMap<String,Command> commands = CommandLoader.getInstance().getCommands();
		
		MenuManager menuManager = new MenuManager("Commands");
		menuManager.setVisible(true);
		additions.addContributionItem(menuManager, null);
		
		for(String key : commands.keySet()){
		
			String commandId = key;
			CommandContributionItemParameter parameters = new CommandContributionItemParameter(
					serviceLocator, commandId, "tp3.plugin.commands.loadedCommands",
					SWT.PUSH);
	
			parameters.label = commandId;
			CommandContributionItem item = new CommandContributionItem(parameters);
			item.setVisible(true);
			menuManager.add(item);
		}
	}

}
