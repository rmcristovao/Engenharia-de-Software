package pt.tecnico.drivedb.presentation;

import pt.tecnico.drivedb.service.LoginUserService;

public class Login extends DriveDBCommand
{

	public Login(Shell sh)
	{
		super(sh,"login","do login service");
		
	}

	@Override
	public void execute(String[] args) 
	{
		DriveDBShell shell = (DriveDBShell) shell();
		
		if (args.length < 1){
			throw new RuntimeException("USAGE: "+name()+" <username> [<password>]");
		}
		
		if (!args[0].equals("nobody") && args.length < 2)
			throw new RuntimeException("USAGE: "+name()+" <username> <password>");
		
		else{ 
			LoginUserService log;
			String username = args[0];
			String password;
			
			if(args.length < 2)
				log = new LoginUserService(username,"");
			else{
				password = args[1];
				log = new LoginUserService(username,password);
			}

			log.execute();
			shell.setToken(log.result());
			shell.addToken(log.result(), username);
		}
	}
}