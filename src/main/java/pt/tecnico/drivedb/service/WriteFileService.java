package pt.tecnico.drivedb.service;

import java.lang.reflect.GenericArrayType;

import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;

public class WriteFileService extends DriveDBService {

    private long token;
	private String filename;
	private String content;
	private String path;

	public WriteFileService(long key,String fileName, String data, String Path) {
    	token = key;
    	filename = fileName;
    	content = data;
    	path =Path;
    }
        
    public WriteFileService(long key,String fileName, String data) {
    	token = key;
    	filename = fileName;
    	content = data;
    }
   
    @Override
    protected void dispatch() throws DriveDBApplicationException{
    	
    	Session s = getManager().getSessionbyToken(token);

		s.updateTime();
		
		s.getWorkingDir().writeFile(s.getCurrentUser(), filename, path, getContent());
    }
    
    private String getContent(){
    	return content;
    }
}