package pt.tecnico.drivedb.presentation;

import java.util.Iterator;
import pt.tecnico.drivedb.service.ListDirectoryService;
import pt.tecnico.drivedb.service.dto.FileDto;

public class List extends DriveDBCommand
{

	public List(Shell sh)
	{
		super(sh,"list","do list directory service");
		
	}

	@Override
	public void execute(String[] args) 
	{
		DriveDBShell shell = (DriveDBShell) shell();

		ListDirectoryService service;
		String path;

		if(args.length < 1){
			service = new ListDirectoryService(shell.getToken());
			System.out.println("use 'list <path>' to list a directory by path");
		}
		else{
			path = args[0];
			service = new ListDirectoryService(shell.getToken(), path);
		}
		service.execute();
		Iterator<FileDto> result = service.result().iterator();
			
		while (result.hasNext()){
			FileDto  f = result.next();
			if(f.getContent()!=null)
				System.out.print(f.getType() +" "+ f.getPermission() +" "+ f.getSize() + " "+ f.getId() +" "+ f.getOwner()+" "+ f.getDate().toString("dd/MM/yyyy HH:mm:ss") +" "+ f.getName() + " -> " + f.getContent() + "\n");
			else 
				System.out.print(f.getType() +" "+ f.getPermission() +" "+ f.getSize() + " "+ f.getId() +" "+ f.getOwner()+" "+ f.getDate().toString("dd/MM/yyyy HH:mm:ss") +" "+ f.getName()+ "\n");
			}
		}
}