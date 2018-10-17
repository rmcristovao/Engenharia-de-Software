package pt.tecnico.drivedb.presentation;


import pt.tecnico.drivedb.exception.EnvVarDoesNotExistException;
import pt.tecnico.drivedb.service.AddVariableService;
import pt.tecnico.drivedb.service.dto.EnvironmentVarDto;


public class Environment extends DriveDBCommand{
	
	public Environment(Shell sh){
		super(sh,"env","Creates/changes the value of the environment variable with the given name.");
	}


	@Override
	public void execute(String[] args) {
		
		if(args.length > 2){
			throw new RuntimeException("USAGE: "+name()+" [<name> [<value>]]");
		}
		
		DriveDBShell shell = (DriveDBShell) shell();
		long token = shell.getToken();
		
		AddVariableService env;
		
		if(args.length < 2){
			env = new AddVariableService(token);
			env.execute();
			if(args.length == 0){
				
				for(EnvironmentVarDto var : env.result()){
					System.out.println(var.toString());
				}
			}else{
				for(EnvironmentVarDto var : env.result()){
					if(var.getName().equals(args[0])){
						System.out.println(var.toString());
						return;
					}
				}
				throw new EnvVarDoesNotExistException(args[0]);		
			}	
		}else{
			new AddVariableService(token,args[0],args[1]).execute();
		}
	}
}
