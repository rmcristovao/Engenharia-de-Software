package pt.tecnico.drivedb.domain;
import org.jdom2.Element;
import org.joda.time.DateTime;

import com.mysql.fabric.proto.xmlrpc.DigestAuthentication;

import antlr.debug.NewLineEvent;
import pt.tecnico.drivedb.exception.InvalidAppContentException;
import pt.tecnico.drivedb.exception.NothingToExecuteException;
import pt.tecnico.drivedb.presentation.Hello;

import org.jdom2.Attribute;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.parsers.*;



public class App extends App_Base {
    
    protected App() {

    }
    
    public App(String name, User owner, Dir dir){
    	verifyNameFile(name, dir);
    	dir.checkPermissions(owner, 'w');
    	init(name,owner,dir);
    }

    public App(String name, User owner, Dir dir, String content){
    	verifyNameFile(name, dir);
    	dir.checkPermissions(owner, 'w');
    	init(name,owner,dir);
    	setData(content);
    }
    
    public String getType(){
    	return "App";
    }

    public void execute(User u, String[] args) throws NothingToExecuteException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException
    {
    	checkPermissions(u, 'x');
    	if(this.getData() == null)
    		throw new NothingToExecuteException(this.getName());
    	Class<?> cls;
        Method meth;
        try { // name is a class: call main()
          cls = Class.forName(getData());
          meth = cls.getMethod("main", String[].class);
        } catch (ClassNotFoundException cnfe) { // name is a method
          int pos;
          if ((pos = getData().lastIndexOf('.')) < 0) throw cnfe;
          cls = Class.forName(getData().substring(0, pos));
          meth = cls.getMethod(getData().substring(pos+1), String[].class);
        }
        meth.invoke(null, (Object)args); // static method (ignore return)
      }
    
    public void setData(String data) throws InvalidAppContentException, NothingToExecuteException{
    	
    	 if(data != null){
    		if(!isValidAppData(data))
    			throw new InvalidAppContentException(data);
    	  }
    	super.setData(data);
    }
    
    public void xmlImport(Element appElement, User owner, Dir dir){
    	setId(0);	
    	setDir(dir);
    
    	setLastModified(new DateTime());
    
    	try{
    		setValidName(new String(appElement.getChild("name").getText().getBytes("UTF-8"))); 
    	}catch (UnsupportedEncodingException e) { System.err.println(e); }
    	
    	Manager m = Manager.getInstance();
    	try{
    		setOwner(owner);
    	}catch (NullPointerException e) {setOwner(m.getUserByUsername("root"));}	
    	
    	try{
    		setPermissions(new String(appElement.getChild("perm").getText()));
    	}catch (NullPointerException e) {setPermissions(owner.getMask());}	
    	
    	try{
    		setData(new String(appElement.getChild("method").getText().getBytes("UTF-8"))); 
    	}catch (UnsupportedEncodingException e) { System.err.println(e); }
    }
    
    public boolean isValidAppData(String content){
    	if(content.length() == 0)
    		return false;
    	
    	String[] str = content.split("[\\.]");
    	
    	if(str.length < 2){
    		return false;
    	}
    	
    	for(String s : str){
    		for(int i = 0; i < s.length(); i++)
    			if(!Character.isJavaIdentifierPart(s.charAt(i)))
    				return false;
    	}
    	return true;
    }
    
    
}
