package pt.tecnico.drivedb.service.dto;

import org.joda.time.DateTime;

import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.User;


public class EnvironmentVarDto implements Comparable<EnvironmentVarDto> {
	
	private String name;
	private String value;
	
	public EnvironmentVarDto( String name,String value){
		
		this.name = name;
		this.value = value;
	
	}
	
	public final String getName(){
		return this.name;
	}

	public final String getValue(){
		return this.value;
	}
	
	/*public final String getContent(){
		return this.content;
	}
	*/

	@Override
	public int compareTo(EnvironmentVarDto o) {
		return this.getName().compareTo(o.getName());
	}
}