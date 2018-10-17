package pt.tecnico.drivedb.presentation;

import pt.tecnico.drivedb.service.ExecuteFileService;

public class Execute extends DriveDBCommand {
	
	Shell shell;
	
	public Execute(Shell sh){
		super(sh, "do", "execute the App File");
		this.shell = sh;
	}
	
	@Override
	public void execute(String[] args) {
		
		if (args.length < 1){
			throw new RuntimeException("USAGE: "+name()+" <path> [<args>]");
		}

		else{
			DriveDBShell shell = (DriveDBShell) shell();
			long token = shell.getToken();
			String path = args[0];
			String [] arg;
			if(args.length == 2)
				arg = args[1].split(",");
			else
				arg = null;

			String filename = path.substring(path.lastIndexOf('/')+1);
			
			new ExecuteFileService(token,filename,arg).execute();
		}
	}
}