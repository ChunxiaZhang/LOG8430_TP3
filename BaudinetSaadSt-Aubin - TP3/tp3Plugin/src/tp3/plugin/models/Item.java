package tp3.plugin.models;

import org.eclipse.core.resources.IResource;

public class Item implements Receiver {

	protected IResource resource;
	protected String path;
	
	public Item(IResource resource){
		this.path = resource.getFullPath().toOSString();
	}
	
	public String getPath(){
		return path;
	}
	
	public String getClassName(){
		return this.getClass().getSimpleName();
	}

}
