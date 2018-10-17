package pt.tecnico.drivedb.presentation;

import pt.tecnico.drivedb.service.ChangeDirectoryService;

public class ChangeDirectory extends DriveDBCommand {
	
	Shell shell;
	
	public ChangeDirectory(Shell sh){
		super(sh, "cwd", "change current directory");
		this.shell = sh;
	}
	
	@Override
	public void execute(String[] args) {
		ChangeDirectoryService cwd;
		if(args.length >1)
			throw new RuntimeException ("USAGE: "+name()+" name");
		if(args.length==0)
		{
			cwd = new ChangeDirectoryService(((DriveDBShell) shell).getToken(), ".");
		}
		else 
			cwd = new ChangeDirectoryService(((DriveDBShell) shell).getToken(), args[0]);
		cwd.execute();
		System.out.println(cwd.result());
		
	}
	
	
}