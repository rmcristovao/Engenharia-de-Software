package pt.tecnico.drivedb.domain;

import org.joda.time.DateTime;
import org.joda.time.Minutes;

import pt.tecnico.drivedb.exception.GuestCanNotBeRemovedException;
import pt.tecnico.drivedb.exception.InvalidPasswordException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

public class Guest extends Guest_Base {
    
    public Guest(Manager manager) throws UsernameContainsCharsException, InvalidUsernameException {
    	super.setPassword("");
        init(manager, "nobody", "Guest", "rxwdr-x-");
        initHomeDir(manager, "nobody");
}

    @Override
    public void delete() throws GuestCanNotBeRemovedException{
    	throw new GuestCanNotBeRemovedException();
    }
    
    @Override
    public void setPassword(String password){
    	throw new InvalidPasswordException(password);
    }
    
    @Override
	public boolean validSessionTime(DateTime initial){
    	return true;
	}
    
    @Override
    public boolean checkPassword(String pass) {
    	if(!pass.equals(""))
    		throw new InvalidPasswordException(getUsername());
    	return true;
    }
}
