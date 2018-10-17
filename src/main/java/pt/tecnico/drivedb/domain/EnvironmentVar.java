package pt.tecnico.drivedb.domain;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.jdbc.log.Log;

import pt.tecnico.drivedb.service.dto.EnvironmentVarDto;
import pt.tecnico.drivedb.service.dto.FileDto;

public class EnvironmentVar extends EnvironmentVar_Base{
    protected static final Logger log = LogManager.getRootLogger();
	
	public EnvironmentVar(Session session, String name, String path) {
		super.setSession(session);
		super.setName(name);
		setPath(path);
	}
	
	@Override
	public void setSession(Session session) {
		log.warn("Can't change environment variable Session.");
	}
	
	@Override
	public void setName(String name) {
		log.warn("Can't change environment variable name.");
	}
	
	public String toString(){
		return getName() + " = " + getPath();
	}
	
    public EnvironmentVarDto toEnvironmentVarDto(){
		return new EnvironmentVarDto(getName(),getPath());
    }
}
