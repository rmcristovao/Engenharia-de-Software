package pt.tecnico.drivedb.exception;

public class InvalidPasswordException extends DriveDBApplicationException{

	private static final long serialVersionUID = 1L;
	private String password;

    public InvalidPasswordException(String p){
    	password = p;
    }

    public String getPassword(){
    	return password; 
    }

    @Override
    public String getMessage(){
    	return "Invalid " + password + " password";
    }

    
}