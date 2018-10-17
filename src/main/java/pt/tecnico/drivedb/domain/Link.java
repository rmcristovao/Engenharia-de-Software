package pt.tecnico.drivedb.domain;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.drivedb.exception.InvalidLengthPathException;
import pt.tecnico.drivedb.exception.InvalidLinkContentException;
import pt.tecnico.drivedb.exception.LinkDoesNotHaveContentException;
import pt.tecnico.drivedb.service.dto.FileDto;

import org.jdom2.Attribute;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import javax.xml.parsers.*;

public class Link extends Link_Base {
    
    public Link() {

    }
    
    public Link(String name, User owner, Dir dir, String content){
    	verifyNameFile(name, dir);
    	dir.checkPermissions(owner, 'w');
    	init(name,owner,dir);
    	setData(content);
    }
    
    @Override
    public void setData(String data){
    	if (data == null)
    		throw new LinkDoesNotHaveContentException();
    	if(data.length() > 1024){
    		throw new InvalidLengthPathException(data);
    	}
    	if (isPath(data)){
    		setLastModified(new DateTime());
    		super.setData(data);
    	}
    	else 
    		throw new InvalidLinkContentException(data);
    }
    
    @Override
    public String read(User current) {
    	checkPermissions(current, 'r');
    	File file = lookup(current, getData());
    	return file.read(current);
    }
    
    @Override
    public void execute(User current, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException {
    	checkPermissions(current, 'x');
		lookup(current, getData()).execute(current, args);

    }
    
    @Override
    public void write(User current, String content) {
    	checkPermissions(current, 'w');
    	File file = lookup(current, getData());
    	file.write(current, content);
    }
    
    @Override
    public FileDto toFileDto(){
		return new FileDto(getId(), getName(), getOwner().getUsername(), getType(), getLastModified(), getSize(), getPermissions(), getData());
    }
    
    public boolean isPath(String data){
    	String[] parts = data.split("/");
    	if (parts.length == 1)
    		return false;
    	for (String s: parts){
    		if (s==null)
    		return false;
    	}
    	return true;
    }
    	
    @Override
    public File lookup(User current, String path) throws StackOverflowError{
    	checkPermissions(current, 'r');
		if (path == null)
			path = "";
    	if (getData().startsWith("/"))
    		return getBase().lookup(current, (getData().endsWith("/") ? getData() : getData() + "/") + path);
    	else
    		return getDir().lookup(current, (getData().endsWith("/") ? getData() : getData() + "/") + path);
    }
    
    public String getType(){
    	return "Link";
    }
    
    public void xmlImport(Element linkElement, User owner, Dir dir){
    	setId(0);   	
    	setDir(dir);
    	setLastModified(new DateTime());
    	 	
    	try{
    		setValidName(new String(linkElement.getChild("name").getText().getBytes("UTF-8"))); 
    	}catch (UnsupportedEncodingException e) { System.err.println(e); }
    	
    	Manager m = Manager.getInstance();
    	try{
    		setOwner(owner);
    	}catch (NullPointerException e) {setOwner(m.getUserByUsername("root"));}	
    	
    	try{
    		setPermissions(new String(linkElement.getChild("perm").getText()));
    	}catch (NullPointerException e) {setPermissions(owner.getMask());}	
    	
    	try{
    		setData(new String(linkElement.getChild("value").getText().getBytes("UTF-8"))); 
    	}catch (UnsupportedEncodingException e) { System.err.println(e); }
    }
    
    
}
