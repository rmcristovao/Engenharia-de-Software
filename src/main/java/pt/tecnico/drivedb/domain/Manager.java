package pt.tecnico.drivedb.domain;

import org.jdom2.Document;
import org.jdom2.Element;

import java.util.Set;
import java.util.TreeMap;

import javax.xml.transform.Templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;

import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.InvalidPasswordException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.PasswordEmptyException;
import pt.tecnico.drivedb.exception.UserAlreadyExistsException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.exception.UsernameEmptyException;
import pt.tecnico.drivedb.exception.UsernameNotFoundException;

public class Manager extends Manager_Base {
    static final Logger log = LogManager.getRootLogger();

    public static Manager getInstance() {
        Manager m = FenixFramework.getDomainRoot().getManager();
        //log.trace("has manager Manager = " + m);
        if (m != null)
        	return m;
        
	log.trace("new Manager");
	
        return new Manager();
    }

    private Manager() {
        setRoot(FenixFramework.getDomainRoot());
        init();
    }
    
    public void init(){
    	try{
    		setCounter(0);
    		Root root = new Root(this);
    		Dir base = new Dir(root);
    		setBase(base);
    		Dir home = new Dir("home", root, base);
    		new Dir("root", root, home);
    		new Guest(this);
    		addUsers(root);
    	}catch(UsernameContainsCharsException | InvalidUsernameException e){
    		log.warn("Error creating Manager.");
    	}
    }
    
    @Override
    public Set<Session> getSessionSet() {
    	log.warn("Can't access SessionSet");
    	return null;
    }
    
    public void updateSessions(){
    	for(Session s : super.getSessionSet())
			s.validate();
    }
    
    public Session getSessionbyToken(long token){
    	for(Session s: super.getSessionSet()){
    		if (s.getToken() == token){
    			if(s.invalid())
    				throw new ExpiredSessionException();
    			else
    				return s;
    		}
    	}
    	throw new InvalidTokenException();
    }
    
    public User getRootUser(){
    	return getUserByUsername("root");
    }

    public int nextCounter(){
    	int nextId = getCounter() + 1;
    	setCounter(nextId);
    	return nextId;
    }
    
    
    public User getUserByUsername(String username) {
        for (User user : getUsersSet()) {
            if (user.getUsername().equals(username)) {
                return user;
            }	
        }
        throw new UsernameNotFoundException(username);
    }
    
    public User getUserByusername(String username)
    {
    	for (User user : getUsersSet()) {
            if (user.getUsername().equals(username)) {
                return user;
            }	
        }
       return null;
    }
    
    
    
    public void checkIfUsernameExists(String username) throws UserAlreadyExistsException{
    	for(User user : getUsersSet()){
    		if(user.getUsername().equals(username))
    			throw new UserAlreadyExistsException(username);
    	}
    }
    
    public String listUsers(){
    	String users = "";
    	for (User user : getUsersSet())
    		users = users + user.getName() + " " + user.getUsername() + "\n";
    	return users;
    }
    
    public void cleanup() {
    	for (User u: getUsersSet())
    	    u.delete();
    	for (Session s: super.getSessionSet()){
    		s.delete();
    	}
    	setBase(null);
    }
    
    
    public Dir checkPath(Dir dir, String path){
    	if (dir.getFilebyName(dir.getNextInPath(path)) == null){
    		return createAllInPath(dir, path);}
   		else{
   			Dir next = (Dir) dir.getFilebyName(dir.getNextInPath(path));
   			return checkPath(next, dir.pathFromHere(path));
    	}
    }
    
    public Dir createAllInPath(Dir dir, String path){
    	
    	if (dir.isLastInPath(path))
    		return dir;
   		else{
   			Dir newDir = new Dir(dir.getNextInPath(path),getRootUser(), dir);
   			return createAllInPath(newDir, dir.pathFromHere(path));
   		}
    }
    
    public void xmlImport(Element element){
    	
    	for (Element node: element.getChildren()){
    		if (node.getName().equals("user")){
    			String username = node.getAttribute("username").getValue();
    			User user = getUserByusername(username);			
    			if(user == null)
    				user = new User();
    			user.xmlImport(node);
        		addUsers(user);
            	Dir home = (Dir) getBase().getFilebyName("home");
            	Dir dir = new Dir(username, user, home, getRootUser());
    		}
    		else{
        		String ownerName = node.getChild("owner").getText(); 		
        		String path = node.getChild("path").getText();  
        		String name = node.getChild("name").getText();  
        		User owner = getUserByusername(ownerName);		
        		Dir parent = checkPath(getBase(), path);
	    		if (node.getName().equals("dir")){
	    			Dir dir = (Dir) parent.getFilebyName(name);
	        		if(dir==null)//Does not exist
	        			dir = new Dir();
	        		dir.xmlImport(node, owner, parent);
	        		dir.setId(nextCounter());
	    		}
	    		else if (node.getName().equals("plain")){
	        		PlainFile plain = (PlainFile) parent.getFilebyName(name);
	        		if(plain ==null) //Does not exist
	        			plain = new PlainFile();     		
	        		plain.xmlImport(node, owner, parent);
	        		plain.setId(nextCounter());
	    		}
	    		else if (node.getName().equals("link")){
	        		Link link = (Link) parent.getFilebyName(name);
	        		if(link==null) //Does not exist
	        			link = new Link();
	        		link.xmlImport(node, owner, parent);
	        		link.setId(nextCounter());
	    		}
	    		else if (node.getName().equals("app")){
	        		App app = (App) parent.getFilebyName(name);   
	        		if(app == null) //Does not exist
        				app = new App();      		
        			app.xmlImport(node, owner, parent);
	        		app.setId(nextCounter());
	    		}	
    		}
    	}	
    }
  
    public Document xmlExport() {
        
        Element element = new Element("myDrive");
        Document doc = new Document(element); 

        for (User p : getUsersSet()){
            if(!p.getUsername().equals("root") && !p.getUsername().equals("nobody"))
                element.addContent(p.xmlExport());
        }
             
        if (getBase().getFilesSet().size() != 0){
            for(File f: getBase().getFilesSet()){
            	f.xmlExport(element);
            }
        }
        return doc;
    }
}
