package pt.tecnico.drivedb.presentation;

import java.util.Map.Entry;

public class Key extends DriveDBCommand{
	public Key(Shell sh){
		super(sh,"token","select another session");
	}

	@Override
	public void execute(String[] args) {
		DriveDBShell shell = (DriveDBShell) shell();
		long token = shell.getToken();
		
		if(args.length == 0){
			String username = shell.tokenList.get(token);
			System.out.println("Token: " + token + " " + "Username: " + username);
		}
		else {
			for (Entry<Long, String> v : shell.tokenList.entrySet()){
				if (v.getValue().equals(args[0])){
					token = v.getKey();
					shell.setToken(token);
					System.out.println("Token: " + token);
				}
			}
		}
	}
}
