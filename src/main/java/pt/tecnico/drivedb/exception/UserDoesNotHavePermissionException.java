package pt.tecnico.drivedb.exception;

public class UserDoesNotHavePermissionException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String username;

    public UserDoesNotHavePermissionException (String user){
    	username = user;
    }

    public String getFileName(){
    	return username;
    }

    @Override
    public String getMessage(){
    	return "This user" + username + "doesn't have permission";
    }
}

