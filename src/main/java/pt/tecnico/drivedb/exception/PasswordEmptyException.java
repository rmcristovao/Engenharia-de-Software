package pt.tecnico.drivedb.exception;

public class PasswordEmptyException extends DriveDBApplicationException {

	private static final long serialVersionUID = 1L;
	private String password;

    public PasswordEmptyException(String p){
    	password = p;
    }

    public String getPassword(){
    	return password; 
    }

    @Override
    public String getMessage(){
    	return "Password" + password + "empty";
    }

    
}