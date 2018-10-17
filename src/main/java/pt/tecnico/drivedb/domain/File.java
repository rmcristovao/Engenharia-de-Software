package pt.tecnico.drivedb.domain;
import java.lang.reflect.InvocationTargetException;
//import java.lang.invoke.MethodHandles.Lookup;
import java.util.HashMap;
import java.util.Map;

import javax.tools.Diagnostic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Partial;

import pt.tecnico.drivedb.exception.FileAlreadyExistException;
import pt.tecnico.drivedb.exception.InvalidFileNameException;
import pt.tecnico.drivedb.exception.InvalidLengthPathException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.IsNotDirException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.service.dto.FileDto;

public abstract class File extends File_Base {
	static final Logger log = LogManager.getRootLogger();
	
    public File() {
        super();
    }
    
    protected void init(String name, User owner, Dir dir){
    	setId(getNextId());
        checkFullPathLength(name, dir);
        setValidName(name);
        setPermissions(owner.getMask());
        setOwner(owner);
        setDir(dir);
        setLastModified(new DateTime());
    }
    
    public abstract String read(User current);
    public abstract void execute(User current, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException;
    public abstract void write(User current, String content);
    
    public abstract void xmlImport(Element dirElement, User owner, Dir dir);
    public abstract String getType();
    public abstract int getSize();
    
	public static int getNextId() {
		Manager manager = Manager.getInstance();
        return manager.nextCounter();
    }
	
    
    public void checkFullPathLength(String name, Dir dir) throws InvalidLengthPathException{
    	int length = dir.getFullPath().length() + name.length();
    	if(length > 1024)
    		throw new InvalidLengthPathException(name,dir.toString());
    }
    
   public boolean checkPermissions(User user, char c) throws UserDoesNotHavePermissionException{
	   Map<Character,Integer> map = new HashMap<Character,Integer>();
	   map.put('r', 0); map.put('w', 1); map.put('x', 2); map.put('d', 3); 
	   
	   if(user.getUsername().equals("root"))
			   return true;
	   
	   else if(user.getUsername().equals("nobody") && (c == 'r' || c== 'd'))
		   return false;
	   
	   else if(getOwner().getUsername().equals(user.getUsername())){
		   if(getPermissions().charAt(map.get(c)) == c)
			   return true;
	   }
	   else{
		   if(getPermissions().charAt(map.get(c) + 4) == c)
			   return true;
	   }
	   throw new UserDoesNotHavePermissionException(user.getUsername());
		   
   }
   
   public boolean verifyNameFile(String nameFile, Dir working){
	   if(working.getFilebyName(nameFile) !=null)
		   throw new FileAlreadyExistException(nameFile);
	   return true;
   }
   
   public Dir getBase(){
	   if (getName().equals("/"))
		   return (Dir) this;
	   else
		   return getDir().getBase();
   }
  
    public void setValidName(String name) throws InvalidFileNameException{
    	if (name == null || name.contains("/") || name.contains("\0"))
    		throw new InvalidFileNameException(name);
    	else
    		super.setName(name);
    }
    
    public Dir getParent(){
    		if(getName().equals("/"))
    			return (Dir) this;
    		else
    			return getDir();
    }
    
    public String toString(){
    	return getType() +" "+ getPermissions() +" "+ getSize() + " "+ getId() +" "+ getOwner().getName()+" "+ getLastModified().toString("dd/MM/yyyy HH:mm:ss") +" "+ getName();
    }
    
    public FileDto toFileDto(){
		return new FileDto(getId(), getName(), getOwner().getUsername(), getType(), getLastModified(), getSize(), getPermissions());
    }
    
    
    public File lookup(User current, String path) throws IsNotDirException{
    	if(isLastInPath(path)){
    		return this;
    	}else{
    		throw new IsNotDirException(path);
    	}
    }
    
    public void delete(User current) {
    	checkPermissions(current, 'd');
        setDir(null);
        setOwner(null);
        deleteDomainObject();
    }
    
    public String firstFromPath(String path){
    	if (isValidPath(path)){
    		if (path.startsWith("/"))
    			return "/";
    		else
    			return path.split("/")[0];
    	}else
    		return null;
    }
    

    public String getNextInPath(String path){
    	if(path!=null && path.split("/").length == 1)
    		return path;
    	else if (!isLastInPath(path))
    		return path.split("/")[1];
    	else
    		return null;
    }
    
    public boolean isInPath(String path){
    	return firstFromPath(path).equals(getName());
    }

    
    public boolean isValidPath(String path) throws InvalidPathException{
    	if (path == null){
    		throw new InvalidPathException(path);
    	}
    	else
    		return true;
    }
    public String pathFromHere(String path){
    		if(isLastInPath(path) || path.split("/").length == 1)
    			return null;
    		else if (path.startsWith("/") && path.split("/").length > 2)
    			return path.substring(getNextInPath(path).length() + 2);
    		else if (path.startsWith("/"))
    			return path.substring(getNextInPath(path).length() + 1);
			else 
				return path.substring(firstFromPath(path).length() + 1);
    }
    
    public boolean isLastInPath(String path){
    	return path == null;
    }
    
    public String getFullPath(){
    	File parent = this;
    	String path = getName();

    	while ((parent = parent.getDir()) != null){
    		if (parent.getName().equals("/"))
    			path = parent.getName() + path;
    		else
    			path = parent.getName() + "/" + path;
    	}
    	return path;
    }
    

    public String getFullPathFromParent(){
    	File parent = this;
    	String path = "";
    	while ((parent = parent.getDir()) != null){
    		if (parent.getName().equals("/"))
    			path = parent.getName() + path;
    		else
    			path = parent.getName() + "/" + path;
    	}
    	return path;
    }
    
    public Element xmlExport(Element base){
        
        Element element = new Element(getType().toLowerCase());
        element.setAttribute("id", Integer.toString(getId()));
        
        element.addContent(new Element("path").addContent(getFullPathFromParent()));
        element.addContent(new Element("name").addContent(getName()));
        element.addContent(new Element("owner").addContent(getOwner().getUsername()));
        element.addContent(new Element("perm").addContent(getPermissions()));
        
    	return element;

    }
    
    public boolean allPermissions (User u, char c){
    	return checkPermissions(u, c); 
    }
    
}


