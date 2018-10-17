package pt.tecnico.drivedb.exception;

public class UserAlreadyExistsException extends DriveDBApplicationException {
    
    private static final long serialVersionUID = 1L;

    private String conflitingname;

    public UserAlreadyExistsException(String conflitingName){
    	conflitingname = conflitingName;
    }

    public String getConflitingName(){
    	return conflitingname;
    }

    @Override
    public String getMessage(){
    	return "This User " + conflitingname + " already exists";
    }
}

