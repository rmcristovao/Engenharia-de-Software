package pt.tecnico.drivedb.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.apache.logging.log4j.core.config.plugins.ResolverUtil;

import pt.tecnico.drivedb.domain.EnvironmentVar;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.EnvVarDoesNotExistException;
import pt.tecnico.drivedb.presentation.Shell;
import pt.tecnico.drivedb.service.dto.EnvironmentVarDto;


public class AddVariableService extends DriveDBService {

	private List<EnvironmentVarDto> result;
	private long token;
	private String varname;
	private String varvalue;
	
	public AddVariableService(long key){

		token = key;
		varname = null;
		varvalue = null;
		result = new ArrayList<EnvironmentVarDto>();
		
	}

	public AddVariableService(long key, String varName, String varValue){

		token = key;
		varname = varName;
		varvalue = varValue;
		result = new ArrayList<EnvironmentVarDto>();
	}

	@Override
	protected void dispatch() throws DriveDBApplicationException{
		Manager m = Manager.getInstance();

		Session s = m.getSessionbyToken(token);
		s.updateTime();
		
		s.createOrAddVar(varname, varvalue);
		result = s.listVar();

		Collections.sort(result);
	}

	public List<EnvironmentVarDto> result() {
		return result;
	}
}