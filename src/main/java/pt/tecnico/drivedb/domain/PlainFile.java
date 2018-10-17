package pt.tecnico.drivedb.domain;

import org.apache.commons.lang.ArrayUtils;
import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Attribute;

import javax.swing.text.AbstractDocument.Content;
import javax.xml.parsers.*;
import org.joda.time.DateTime;

import pt.tecnico.drivedb.exception.ContentIsNotExcutableExeption;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.FileIsEmptyException;
import pt.tecnico.drivedb.exception.InvalidAppContentException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public class PlainFile extends PlainFile_Base {   
	
	public PlainFile(){
		
	}

    public PlainFile(String name, User owner, Dir dir,String content) {
    	verifyNameFile(name, dir);
    	dir.checkPermissions(owner, 'w');
    	init(name,owner,dir);
    	setData(content);
    	}
    
	@Override
	public String read(User current) {
    	checkPermissions(current, 'r');
    	if(getData()== null)
    		throw new FileIsEmptyException(getName());
    	return getData();
	}
	
	public String getAppPath(String line)
	{
		String[] parts = line.split(" ");
		String result = parts[0];
		return result;
	}

	public String [] getFileArgs(String line)
	{
		String[] parts = line.split(" ");
		parts =  (String[]) ArrayUtils.remove(parts, 0);
		return parts;
	}
	
	public void isExecutable(String path, User current){
		try {
			getDir().lookup(current, path);	
		} catch (FileDoesNotExistException e) {
			throw new InvalidAppContentException(path);
		}
	}

	@Override
	public void execute(User current, String[] args) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException {
		checkPermissions(current, 'x');
		String[]lines = getData().split("\n");
		for(String tmpLine : lines){
			isExecutable(getAppPath(tmpLine), current);
			App a = (App) getDir().lookup(current,getAppPath(tmpLine));
			a.execute(current,getFileArgs(tmpLine));
			
		}
		
	}

	@Override
	public void write(User current, String content) {
    	checkPermissions(current, 'w');
    	setData(content);
	}
    
    @Override
    public int getSize(){
		if(getData()==null){
			return 0;
		}
		else
			return getData().length();
    	
    }
    
    public String getType(){
    	return "Plain";
    }
    
    public void setData(String data){
    	setLastModified(new DateTime());
    	super.setData(data);
    };
    
    public void xmlImport(Element plainElement, User owner, Dir dir){
    	setId(0);	
    	setDir(dir);
    	setLastModified(new DateTime());	
    	try{
    		setValidName(new String(plainElement.getChild("name").getText().getBytes("UTF-8"))); 
    	}catch (UnsupportedEncodingException e) { System.err.println(e); }
    	
    	Manager m = Manager.getInstance();
    	try{
    		setOwner(owner);
    	}catch (NullPointerException e) {setOwner(m.getUserByUsername("root"));}	
    	
    	try{
    		setPermissions(new String(plainElement.getChild("perm").getText()));
    	}catch (NullPointerException e) {setPermissions(owner.getMask());}	
    	
    	try{
    		setData(new String(plainElement.getChild("contents").getText().getBytes("UTF-8"))); 
    	}catch (UnsupportedEncodingException e) { System.err.println(e); }
    	
    }
    
    @Override

    public Element xmlExport(Element base) {
        
        Element element = super.xmlExport(base);

        String str;
        switch(getType()){
            case "Link":
                str = "value";
                break;
            case "App":
                str = "method";
                break;
            default:
                str = "contents";
                break;
        }
        
        element.addContent(new Element(str).addContent(getData()));
        element.addContent(new Element("lastmodifdate").addContent(getLastModified().toString("dd/MM/yyyy HH:mm:ss")));
        base.addContent(element);
        return element;
    }
    
}
