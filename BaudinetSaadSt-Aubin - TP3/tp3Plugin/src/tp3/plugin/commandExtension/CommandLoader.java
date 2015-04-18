package tp3.plugin.commandExtension;

import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import tp3.plugin.commands.Command;;

public class CommandLoader {

	private HashMap<String,Command> commands = new HashMap<>();

	private static CommandLoader instance;

	/**
	 * Singleton logic
	 */
	private CommandLoader(){
		loadCommands();
	}

	public static CommandLoader getInstance(){
		if(instance == null){
			instance = new CommandLoader();
			return instance;
		}
		else return instance;
	}

	/**
	 * Load all the commands found in the extensions that use the extension point "tp3.plugin.command"
	 */
	public void loadCommands(){
		
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		
		//Fetch commands that use the extension point "tp3.plugin.command"
		IConfigurationElement[] configurationElements = extensionRegistry
				.getConfigurationElementsFor("tp3.plugin.command");

		//Iterate on all found commands
		for (IConfigurationElement configurationElement : configurationElements) {
			try {
				//Create and store command using the values in the plugin.xml file of the extension
				Command commandImpl = (Command) configurationElement
						.createExecutableExtension("class");
				String name = configurationElement.getAttribute("name");
				commands.put(name, commandImpl);
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @return the fetched commands
	 */
	public HashMap<String,Command> getCommands(){
		return commands;
	}
}
