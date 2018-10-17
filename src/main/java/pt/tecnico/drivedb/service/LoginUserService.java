package pt.tecnico.drivedb.service;

import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.PasswordEmptyException;
import pt.tecnico.drivedb.exception.UsernameEmptyException;

public class LoginUserService extends DriveDBService {
	long result;
	String username;
	String password;
	
	
	public LoginUserService(String name, String pass) {
    	username = name;
    	password= pass;
    	
    }

	public long result(){
		return result;
	}

	@Override
	protected void dispatch() throws DriveDBApplicationException {
		result = new Session(getManager(), username, password).getToken();
	}
}
