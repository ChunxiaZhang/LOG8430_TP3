package com.pxz.log8430.path;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowPath extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		
		IPath path = null;
		
		IStructuredSelection selection = (IStructuredSelection) HandlerUtil
		        .getActiveSite(event).getSelectionProvider().getSelection();
		    if (selection == null) {
		      return null;
		    }
		Object firstElement = selection.getFirstElement();
		if(firstElement instanceof ICompilationUnit) {
        	ICompilationUnit element = (ICompilationUnit)firstElement;
        	path = element.getResource().getLocation();

        } else if(firstElement instanceof IPackageFragmentRoot) {
        	IPackageFragmentRoot element = (IPackageFragmentRoot) firstElement; 
        	path = element.getResource().getLocation();
        	
        } else if(firstElement instanceof IPackageFragment) {
        	IPackageFragment element = (IPackageFragment) firstElement; 
        	path = element.getResource().getLocation();
        } 
		
		if(path != null) {
			
			MessageDialog.openInformation(HandlerUtil.getActiveWorkbenchWindow(event).getShell(),
				"Path", path.toString());
		}
		return null;
	}

}
