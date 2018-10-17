package pt.tecnico.drivedb.presentation;

import pt.tecnico.drivedb.service.WriteFileService;

public class Write extends DriveDBCommand{
	
	public Write(Shell sh){
		super(sh,"update","replaces the file content");
	}

	@Override
	public void execute(String[] args) {
		if(args.length < 2)
			throw new RuntimeException("USAGE: "+name()+" <path> <text>");
		else{
			DriveDBShell shell = (DriveDBShell) shell();
			long token = shell.getToken();
			String path = args[0];
			String content = args[1];

			String filename = path.substring(path.lastIndexOf('/')+1);
			
			new WriteFileService(token,filename,content).execute();
		}
	}
}
