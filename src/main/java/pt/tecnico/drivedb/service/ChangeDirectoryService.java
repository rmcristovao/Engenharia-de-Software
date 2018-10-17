package pt.tecnico.drivedb.service;

import javax.naming.spi.DirStateFactory.Result;

import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ch.qos.logback.core.joran.conditional.IfAction;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.IsNotDirException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;

public class ChangeDirectoryService extends DriveDBService{
	
	long token;
	String path;
	String result;
	
	public ChangeDirectoryService(long token, String path) {
		this.token = token;
		this.path = path;
	}
	
	@Override
	protected void dispatch() throws DriveDBApplicationException {
	Manager m = Manager.getInstance();
		
		Session s = m.getSessionbyToken(token);
		s.updateTime();
		User user = s.getCurrentUser();

		s.changeDir(path);
		result = s.getWorkingDir().getFullPath();
		
		
	}
	
	public String result(){
		return result;
	}

}