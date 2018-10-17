package pt.tecnico.drivedb.domain;
import org.jdom2.Element;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.jdom2.Attribute;

import pt.tecnico.drivedb.exception.InvalidPasswordException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.RootCanNotBeRemovedException;
import pt.tecnico.drivedb.exception.UserAlreadyExistsException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.exception.UsernameEmptyException;

public class User extends User_Base {
	
	public User(){
		super();
	}
	
	public User(Manager manager, String username, String password,String name,String umask) throws UsernameContainsCharsException, InvalidUsernameException{
		setPassword(password);
		init(manager, username, name, umask);
		initHomeDir(manager, username);
	} 

	public User(Manager manager, String username, String name) throws UsernameContainsCharsException, InvalidUsernameException {
		this(manager, username, username, name, "rwxd----");
	}
	
	public User(Manager manager, String username, String name, String password) throws UsernameContainsCharsException, InvalidUsernameException {
		this(manager, username, password, name, "rwxd----");
	}
	
	public void init(Manager manager, String username,String name,String umask) throws UsernameContainsCharsException, InvalidUsernameException{
		manager.checkIfUsernameExists(username);
		checkValidityOfUsername(username);
		setUsername(username);
		setName(name);
		setPathHome("/home/" + username);
		setMask(umask);
		setManager(manager);
	}
	
	public void initHomeDir(Manager manager, String username){
		Dir home = (Dir) manager.getBase().getFilebyName("home");
    	new Dir(username, this, home, manager.getRootUser());
	}
	
	@Override
	public void addSession(Session session) {
		session.setCurrentUser(this);
	}

	public void checkValidityOfUsername(String username) throws UsernameContainsCharsException, InvalidUsernameException{

		if (username.isEmpty()){
			throw new UsernameEmptyException(username);
		}
		else if (username.length() < 3)
			throw new InvalidUsernameException(username);
		
		for (int i = 0; i < username.length(); i++) {
			if (!Character.isLetter(username.charAt(i)) && !Character.isDigit(username.charAt(i)))
				throw new UsernameContainsCharsException(username);
		}
	}

	@Override
	public void setManager(Manager mg) {
		if (mg == null)
			super.setManager(null);
		else
			mg.addUsers(this);
	}


	public void xmlImport(Element userElement){
		for(File f: getFilesSet())
			removeFiles(f);

		Attribute attribute = userElement.getAttribute("username");
		String username = new String(attribute.getValue());
		setUsername(username);	

		try{
			setPassword(new String(userElement.getChild("password").getText()));
		}catch (NullPointerException e) { setPassword(username);}


		try{
			setName(new String(userElement.getChild("name").getText()));
		}catch (NullPointerException e) { setName(username);}


		try{
			setPathHome(new String(userElement.getChild("home").getText()));
		}catch (NullPointerException e) { setPathHome(new String("/home/" + username));}


		try{
			setMask(new String(userElement.getChild("mask").getText()));
		}catch (NullPointerException e) { setMask("rwxd----");	}		

	}
	
	public boolean validSessionTime(DateTime initial){
		Minutes maxMinutes=Minutes.minutes(120);
    	DateTime current = new DateTime();
    	if (Minutes.minutesBetween(initial, current).isGreaterThan(maxMinutes))
    		return false;
    	else
    		return true;
	}

	public void delete(){
		this.setUsername(null);
		this.setPassword(null);
		this.setName(null);;
		this.setManager(null);
		for (File file : getFilesSet())
			removeFiles(file);	
		this.deleteDomainObject();
	}
	
	public Element xmlExport() {

		Element element = new Element("user");
		element.setAttribute("username", getUsername());

		element.addContent(new Element("name").addContent(getName()));
		element.addContent(new Element("password").addContent(getPassword()));
		element.addContent(new Element("home").addContent(getPathHome()));
		element.addContent(new Element("mask").addContent(getMask()));

		return element;
	}

	public boolean checkPassword(String pass) {
    	if(!getPassword().equals(pass) || getPassword().length() < 8 )
    		throw new InvalidPasswordException(getUsername());
		return true;
	}
}

