package pt.tecnico.drivedb.exception;

public class UsernameEmptyException extends DriveDBApplicationException {
	
	    private static final long serialVersionUID = 1L;

	    private String conflictingName;
		
	    public UsernameEmptyException(String conflictingName) {
	    	this.conflictingName = conflictingName;
	    }
		
	    public String getConflictingName() {
	    	return this.conflictingName;
	    }
	    
	    @Override 
	    public String getMessage() {
	        return "USERNAME CANNOT BE EMPTY: " + conflictingName;
	    }
	}

