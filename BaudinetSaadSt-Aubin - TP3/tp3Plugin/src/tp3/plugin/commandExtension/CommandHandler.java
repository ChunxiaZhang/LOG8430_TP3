package tp3.plugin.commandExtension;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import tp3.plugin.commands.Command;
import tp3.plugin.commands.Invoker;
import tp3.plugin.models.Item;

public class CommandHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		//Get the menu item name from the event, we use this as a key to retreive the command in our hashmap
		String commandName = ((MenuItem)((Event) event.getTrigger()).widget).getText();
		
		//For the moment, getSelectedItem returns null. its ok because our command simply prints text
		Item item = getSelectedItem(event);
		Command command = CommandLoader.getInstance().getCommands().get(commandName);
		Invoker invoker = new Invoker();
		invoker.execute(command, item);
		return null;
	}

	/**
	 * TODO: Returns the selected item
	 * @param event
	 * @return
	 */
	public Item getSelectedItem(ExecutionEvent event){
		IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		IViewPart view = page.findView(IPageLayout.ID_PROJECT_EXPLORER);
		ISelection selection = view.getSite().getSelectionProvider().getSelection();

		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			Object obj = ssel.getFirstElement();

			if (obj instanceof IProject)
			{
				IProject project = (IProject)((IAdaptable)obj).getAdapter(IProject.class);
				if(project != null){
					IPath path = project.getFullPath();
				}
			}
		}
		return null;
	}

}
