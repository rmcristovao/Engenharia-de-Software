package pt.tecnico.drivedb.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;
import org.jdom2.Attribute;
import javax.xml.parsers.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;

import org.joda.time.DateTime;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import com.mysql.jdbc.log.Log;

import pt.ist.fenixframework.DomainRoot;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.drivedb.exception.RootCanNotBeRemovedException;
import pt.tecnico.drivedb.exception.CanNotRemoveBaseDirException;
import pt.tecnico.drivedb.exception.DirHaveContentException;
import pt.tecnico.drivedb.exception.DirNotExecuteException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidFileTypeException;
import pt.tecnico.drivedb.exception.NotPlainFileException;
import pt.tecnico.drivedb.service.CreateFileService;
import pt.tecnico.drivedb.service.dto.FileDto;


public class Dir extends Dir_Base {
	static final Logger log = LogManager.getRootLogger();

	public Dir(){
		super();
	}

	public Dir(String name, User owner, Dir dir) {
		verifyNameFile(name, dir);
		dir.checkPermissions(owner, 'w');
		init(name, owner, dir);
	}

	public Dir(User owner) {
		super();
		setId(0);
		setName("/");
		setPermissions(owner.getMask());
		setOwner(owner);
		setLastModified(new DateTime());
	}

	public Dir(String name, User user, Dir dir, User rootUser) {
		dir.checkPermissions(rootUser, 'w');
		init(name, user, dir);
	}
	
	@Override
	public String read(User current) {
		throw new NotPlainFileException(getName());
	}

	@Override
	public int getSize(){
		return getFilesSet().size() + 2;

	}
	@Override
	public String getType(){
		return "Dir";
	}

	@Override
	public void delete(User current){
		checkPermissions(current, 'd');
		allPermissions(current, 'd');
		setDir(null);
		setManager(null);
		setSession(null);
		setOwner(null);
		if(getFilesSet().size()>0){
			for(File f : getFilesSet())
				f.delete(current);
		}
		deleteDomainObject();
	}

	@Override 
	public void setManager(Manager manager){  	
		if(manager == null)
			super.setManager(null);
		else
			throw new CanNotRemoveBaseDirException("/");
	}

	public File getFilebyName(String name){
		if(name == null)
			return null;
		else if (name.equals(".") || (name.equals("..") && getName().equals("/")))
			return this;
		else if(name.equals(".."))
			return getDir();
		else{
			for (File f : getFilesSet()){
				if(f.getName().equals(name))
					return f;
			}
			return null;
		}
	}

	@Override
	public File lookup(User current, String path) throws FileDoesNotExistException{
		checkPermissions(current, 'r');
		if(path!=null && path.equals("/"))
				return getBase();
		else if(isLastInPath(path) || path.equals("/") || path.equals(""))
			return this;
		else if (getName().equals("/") && path.startsWith("/")){
			if (getFilebyName(getNextInPath(path)) == null)
				throw new FileDoesNotExistException(this.getNextInPath(path));
			else
				return getFilebyName(getNextInPath(path)).lookup(current, pathFromHere(path));
		}
		else if (!getName().equals("/") && path.startsWith("/"))
			return getBase().lookup(current, path);
		else{	
			if (getFilebyName(firstFromPath(path)) == null)
				throw new FileDoesNotExistException(this.firstFromPath(path));
			else
				return getFilebyName(firstFromPath(path)).lookup(current, pathFromHere(path));
		}
	}


	public String convertPath(String path){
		if (!isInPath(path) && getFilebyName(firstFromPath(path)) != null && !firstFromPath(path).equals(".."))
			return getName() + "/" + path;   	
		return path;
	}

	public String parentToString() {
		return getType() +" "+ getPermissions() +" "+ getSize() + " "+ getId() +" "+ getOwner().getName()+" "+ getLastModified().toString("dd/MM/yyyy HH:mm:ss") +" "+ "..";
	}

	public String thisToString() {
		return getType() +" "+ getPermissions() +" "+ getSize() + " " +getId() +" "+ getOwner().getName()+" "+ getLastModified().toString("dd/MM/yyyy HH:mm:ss") +" "+ ".";
	}

	public String listDirectory(){
		String list = "";
		if (getName().equals("/"))
			list = list + this.parentToString() + "\n";
		else
			list = list + getDir().parentToString() + "\n";

		list = list + this.thisToString();
		for (File f : getFilesSet()){
			list = list + "\n" + f.toString();
		}
		return list + "\n";
	}

	public String listDirectorySimple(){
		String list = "";
		for (File f : getFilesSet()){
			list = f.getName() +"\n"+ list ;
		}
		return list;
	}
	
	public void writeFile(User user,String filename, String path, String content){
		if(path != null)
			lookup(user, path).write(user, content);
		else if(path == null)
			lookup(user, filename).write(user, content);
	}
	
	public List<FileDto> listDir(User user){
		List<FileDto> result = new ArrayList<FileDto>();
		checkPermissions(user, 'r');
		Dir parent = getParent();
		result.add(new FileDto(getId(), ".", getOwner().getUsername(), getType(), getLastModified(), getSize(), getPermissions()));
		result.add(new FileDto(parent.getId(), "..", parent.getOwner().getUsername(), parent.getType(), parent.getLastModified(), parent.getSize(), parent.getPermissions()));
		
		for ( File f : getFilesSet()){
			result.add(f.toFileDto());
		}
		return result;
	}

	public void xmlImport(Element dirElement, User owner, Dir dir){
		setId(0);
		setDir(dir);
		setLastModified(new DateTime());

		try{
			setValidName(new String(dirElement.getChild("name").getText().getBytes("UTF-8"))); 
		}catch (UnsupportedEncodingException e) { System.err.println(e); }

		Manager m = Manager.getInstance();
		try{
			setOwner(owner);
		}catch (NullPointerException e) {setOwner(m.getUserByUsername("root"));}

		try{
			setPermissions(new String(dirElement.getChild("perm").getText()));
		}catch (NullPointerException e) {setPermissions(owner.getMask());}	


	}

	@Override
	public Element xmlExport(Element base) {

		Element element = super.xmlExport(base);

		if(getFilesSet()!=null){
			for(File f : getFilesSet()){
				f.xmlExport(base);
			}
		}

		if(!getFullPath().equals("/home") && !getFullPath().equals("/home/root") && !getFullPath().equals("/home/nobody")){
			element.addContent(new Element("lastmodifdate").addContent(getLastModified().toString("dd/MM/yyyy HH:mm:ss")));
			base.addContent(element);
		}

		return element;
	}

	@Override
	public boolean allPermissions(User u, char c){

		if (this.checkPermissions(u, c)== true && this.getFilesSet().size()==0)
			return true; 

		if (this.checkPermissions(u, c)==false)
			return false;

		else {
			for (File f: this.getFilesSet())
			{
				if(f.allPermissions(u, c)==false)
					return false;
				else 
					return true;
			}

		}    	return false;   	
	}

	@Override
	public void execute(User current, String[] args) throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException {
		throw new DirNotExecuteException(getName());
		
	}
	
	public boolean ValidType(String type){
		if (type == "Dir" || type == "Link" || type == "Plain" || type == "App")
			return true;
		return false;
	}
	
	public void createFile(String name, User user, String type,String content){
		if (content != null && type == "Dir")
			throw new DirHaveContentException();
		else if (!(ValidType(type)))
			throw new InvalidFileTypeException(type);
		
		if (type == "Dir")
			new Dir(name, user, this);
		else if (type == "Plain")
			new PlainFile(name, user, this, content);
		else if (type == "Link")
			new Link(name, user, this, content);
		else if (type == "App")
			new App(name, user, this);	
	}
	
	public Dir searchDir(User user, String path){
		if (path == null)
			return this;
		else
			return (Dir) lookup(user, path);
	}

	@Override
	public void write(User current, String content) {
		throw new NotPlainFileException(getName());
		
	}
}

