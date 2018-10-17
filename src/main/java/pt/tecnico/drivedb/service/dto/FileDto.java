package pt.tecnico.drivedb.service.dto;


import org.joda.time.DateTime;

import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.User;

public class FileDto implements Comparable<FileDto> {
	private int id;
	private String name;
	private String permission;
	private String username;
	private String type;
	private DateTime date;
	private String content;
	private int size;
	
	public FileDto(int id, String name,String username, String type, DateTime date, int size, String permission){
		this.id = id;
		this.name = name;
		this.permission = permission;
		this.username = username;
		this.type = type;
		this.date = date;
		this.size = size;
	}
	
	public FileDto(int id, String name,String username, String type, DateTime date, int size, String permission, String content){
		this(id, name, username, type, date, size, permission);
		this.content = content;
	}
	
	public final int getId(){
		return this.id;
	}

	public final int getSize(){
		return this.size;
	}
	
	public final String getName(){
		return this.name;
	}
	
	public final DateTime getDate(){
		return this.date;
	}
	
	public final String getPermission(){
		return this.permission;
	}
	
	public final String getOwner(){
		return this.username;
	}
	
	public final String getType(){
		return this.type;
	} 
	
	public final String getContent(){
		return this.content;
	}
	

	@Override
	public int compareTo(FileDto o) {
		return this.getName().compareTo(o.getName());
	}
}