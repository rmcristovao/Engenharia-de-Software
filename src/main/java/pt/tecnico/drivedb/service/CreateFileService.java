package pt.tecnico.drivedb.service;

import java.nio.file.FileAlreadyExistsException;

import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DirHaveContentException;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.FileAlreadyExistException;
import pt.tecnico.drivedb.exception.InvalidFileTypeException;
import pt.tecnico.drivedb.exception.LinkDoesNotHaveContentException;

public class CreateFileService extends DriveDBService{
	
	private long token;
	private String name;
	private String type;
	private String content;

	public CreateFileService(long token, String name, String type){
		this.token = token;
		this.name = name;
		this.type = type;
	}
	
	public CreateFileService(long token, String name, String type,String content){
		this.token = token;
		this.name = name;
		this.type = type;
		this.content = content;
	}
	
	@Override
	protected void dispatch() throws DriveDBApplicationException {
		Manager m = Manager.getInstance();
		
		Session session = m.getSessionbyToken(token);
		session.updateTime();
		
		session.getWorkingDir().createFile(name, session.getCurrentUser(), type, content);	
	}

}
