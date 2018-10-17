package pt.tecnico.drivedb.exception;

public class CannotChangeExtensionOwnerException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String username;

    public CannotChangeExtensionOwnerException(String Name){
    	username = Name;
    }

    public String getFileName(){
    	return username;
    }

    @Override
    public String getMessage(){
    	return "Cannot change ownership of extension to this user " + username;
    }
}
