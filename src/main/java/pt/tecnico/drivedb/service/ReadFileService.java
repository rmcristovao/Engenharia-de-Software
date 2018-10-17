package pt.tecnico.drivedb.service;

import java.util.List;

import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.FileIsEmptyException;
import pt.tecnico.drivedb.service.dto.FileDto;

public class ReadFileService extends DriveDBService {

    private long token;
	private String filename;
	private String result;
	private String path;

    public ReadFileService(long token,String name) {
    	this.token = token;
    	this.filename = name;
    }
    
    @Override
    protected void dispatch() throws DriveDBApplicationException{
    	Manager m = Manager.getInstance();
		
		Session session = m.getSessionbyToken(token);
		session.updateTime();
		
		result = session.getWorkingDir().lookup(session.getCurrentUser(), filename).read(session.getCurrentUser());
	
    }
    
    public String getResult(){
    	return result;
    }
}
