package pt.tecnico.drivedb.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.impl.StaticLoggerBinder;

import com.mysql.jdbc.log.Log;

import pt.tecnico.drivedb.exception.InvalidPasswordException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.RootCanNotBeRemovedException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

public class Root extends Root_Base {
	static final Logger log = LogManager.getRootLogger();

	public Root(Manager manager) throws UsernameContainsCharsException, InvalidUsernameException {
    	super.setPassword("***");
    	init(manager, "root", "Super User", "rwxdr-x-");
    }
    
    @Override
    public void setPassword(String password){
    	throw new InvalidPasswordException(password);
    }
    
    @Override
	public void delete() throws RootCanNotBeRemovedException{
		throw new RootCanNotBeRemovedException();
	}
    
    @Override
	public boolean validSessionTime(DateTime initial){
		Minutes maxMinutes=Minutes.minutes(10);
    	DateTime current = new DateTime();
    	if (Minutes.minutesBetween(initial, current).isGreaterThan(maxMinutes))
    		return false;
    	else
    		return true;
	}
    
    @Override
    public boolean checkPassword(String pass) {
    	if(!getPassword().equals(pass))
    		throw new InvalidPasswordException(getUsername());
    	return true;
    }
}
