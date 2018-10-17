package pt.tecnico.drivedb.service;


import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidFileNameException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;

public class DeleteFileService extends DriveDBService {

	private long token;
	private String fileName;
	
	public DeleteFileService(long key, String filename){
		token = key;
		fileName = filename;
	}
	
	@Override
	protected void dispatch() throws DriveDBApplicationException {
		Manager m = Manager.getInstance();
		
		Session session = m.getSessionbyToken(token);
		session.updateTime();	
		session.getWorkingDir().lookup(session.getCurrentUser(), fileName).delete(session.getCurrentUser());
	}
}
