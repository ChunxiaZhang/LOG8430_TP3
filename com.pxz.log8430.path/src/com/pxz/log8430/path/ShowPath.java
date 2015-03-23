package com.pxz.log8430.path;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowPath extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IPath path = null;
		
		// get the selected element
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
		        .getActiveSite(event).getSelectionProvider().getSelection();
		    if (selection == null) {
		      return null;
		    }
		Object firstElement = selection.getFirstElement();
		
		// get the element's type
		if(firstElement instanceof IResource) {
        	IResource element = (IResource)firstElement;
        	path = element.getLocation();

        } else if(firstElement instanceof IJavaElement) {
        	IJavaElement element = (IJavaElement) firstElement;
        	path = element.getPath();
        	
        }
		
		// print the element's path
		if(path != null) {
			
			MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(),
				"Path", path.toString());
		}
		return null;
	}

}
