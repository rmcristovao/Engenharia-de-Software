package pt.tecnico.drivedb.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.math.BigInteger;
import java.nio.file.FileAlreadyExistsException;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Minutes;

import pt.tecnico.drivedb.exception.InvalidLengthPathException;
import pt.tecnico.drivedb.exception.InvalidPasswordException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.IsNotDirException;
import pt.tecnico.drivedb.exception.NotPlainFileException;
import pt.tecnico.drivedb.exception.UserAlreadyExistsException;
import pt.tecnico.drivedb.exception.UsernameEmptyException;
import pt.tecnico.drivedb.service.dto.EnvironmentVarDto;

public class Session extends Session_Base {
	static final Logger log = LogManager.getRootLogger();
    
    
    public Session(Manager manager, String username, String pass){
		if(username.equals("") || username == null || username.length()<3)
			throw new UsernameEmptyException(username);
    	User user = manager.getUserByUsername(username);
    	if(user.checkPassword(pass)){
    		setManager(manager);
    		Dir working = (Dir) manager.getBase().lookup(user, user.getPathHome());
    		init(working, user);
    	}
    }
    
    public void init(Dir working, User logged) {
        super.setCurrentUser(logged);
        setWorkingDir(working);
        long token = new BigInteger(64, new Random()).longValue();
        super.setToken(Math.abs(token));
        super.setDate(new DateTime());
    }
    
    @Override
    public void setDate(DateTime date) {
    	log.warn("Can't change session date");
    }
    
    @Override
    public void setToken(long token) {
    	log.warn("Can't change session token");
    }
    
    @Override
    public void setCurrentUser(User currentUser) {
    	log.warn("Can't change session current user");
    }
    
    public Dir getBase(){
    	return getManager().getBase();
    }
    
    public void validate(){
    	if (invalid())
    		delete();
    }
    
    public boolean invalid(){
    	return !getCurrentUser().validSessionTime(getDate());
    }
    
    public String getValidUntil(){
    	return getDate().plusHours(2).toString("dd/MM/yyyy HH:mm:ss");
    }
    
    public void delete(){
    	super.setCurrentUser(null);
    	setManager(null);
    	setWorkingDir(null);
    	deleteDomainObject();
    }
    
    public void reset(){
    	setWorkingDir(getBase());
    }
    
    public void updateTime(){
    	super.setDate(new DateTime());
    }

    public void changeDir(String path) throws IsNotDirException{
    	if (path == null)
    		throw new InvalidPathException(path);
    	File file = getWorkingDir().lookup(getCurrentUser(), path);
    	if (file.getType().equals("Dir")){
    		Dir working = (Dir) file;
    		setWorkingDir(working);
    	}else
    		throw new IsNotDirException(file.getName());
    }
    
    public void testExpiredSession(){
    	DateTime current = new DateTime();
    	current = current.minusHours(3);
    	super.setDate(current);
    }
    
    public void testExpiredRootSession(){
    	DateTime current = new DateTime();
    	current = current.minusMinutes(12);
    	super.setDate(current);
    }
    
    public EnvironmentVar createOrAddVar(String name, String value){
    	if (name != null && value != null){
    		for(EnvironmentVar ev : getVarSet()){
    			if(ev.getName().equals(name)){
    				ev.setPath(value);
    					return ev;
    			}
    		}
    	return new EnvironmentVar(this, name, value);
    	}
    	return null;
    }
    
    public List<EnvironmentVarDto> listVar(){
    	List<EnvironmentVarDto> result = new ArrayList<EnvironmentVarDto>();
		for (EnvironmentVar e: getVarSet()){
			result.add(e.toEnvironmentVarDto());
		}
		return result;
    }

    
}