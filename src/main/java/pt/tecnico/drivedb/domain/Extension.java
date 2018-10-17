package pt.tecnico.drivedb.domain;

import pt.tecnico.drivedb.exception.CannotChangeExtensionOwnerException;
import pt.tecnico.drivedb.exception.ExtensionAlreadyExistsException;

public class Extension extends Extension_Base {
    
    public Extension() {
        super();
    }
    public Extension (String name, User u)
    {
    	super();
    	for(Extension e : u.getExtensionSet()){
    		if(name.equals(e.getName()))
    			throw new ExtensionAlreadyExistsException(e.getName());
    	}
    	setName(name);
    	super.setUser(u);
    }
    public Extension (String name, User u, App a)
    {
    	this(name, u); 	
    	setApp(a);
    }
    @Override
    public void setUser(User u)  
    {
    	throw new CannotChangeExtensionOwnerException(u.getUsername());
    }

   
    
    
    
    
}
