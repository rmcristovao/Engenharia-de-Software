package pt.tecnico.drivedb.service;

import java.lang.reflect.InvocationTargetException;
//import java.io.File;
import java.nio.file.Path;

import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DirNotExecuteException;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.ErrorExecutingPlainFileException;


public class ExecuteFileService extends DriveDBService {

	 long token;
	 String filePath;
	 String []args;
	
	public ExecuteFileService(long key,String path, String[] content) {
    	token = key;
    	filePath = path;
    	args = content;
    }
	
	
	@Override
	protected void dispatch() throws DriveDBApplicationException {
		Manager m = Manager.getInstance();
		
		Session session = m.getSessionbyToken(token);
		
		session.updateTime();
		File file = session.getWorkingDir().lookup(session.getCurrentUser(), filePath);
			try {
				file.execute(session.getCurrentUser(), args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| ClassNotFoundException | NoSuchMethodException | SecurityException e) {
				throw new ErrorExecutingPlainFileException(file.getName());
			}
		}
}