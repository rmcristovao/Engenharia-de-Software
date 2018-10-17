package pt.tecnico.drivedb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.service.dto.FileDto;

public class ListDirectoryService extends DriveDBService{
	
	private List<FileDto> result;
	long token;
	String path;
	
	
	public ListDirectoryService(long token) {
		this.token=token;
		result = new ArrayList<FileDto>();
	}
	
	public ListDirectoryService(long token, String path) {
		this.token = token;
		this.path = path;
		result = new ArrayList<FileDto>();
	}
	
	@Override
	protected void dispatch() throws DriveDBApplicationException {
		Session s = getManager().getSessionbyToken(token);
		s.updateTime();
		Dir working;
		
		working = s.getWorkingDir().searchDir(s.getCurrentUser(), path);
		
		result = working.listDir(s.getCurrentUser());
		
		Collections.sort(result);
		
	}
	
	public List<FileDto> result(){
		return result;
	}
	
}