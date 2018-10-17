package pt.tecnico.drivedb.exception;


public class UsernameContainsCharsException extends Exception {

    private static final long serialVersionUID = 1L;

    private String conflictingName;
	
    public UsernameContainsCharsException(String conflictingName) {
    	this.conflictingName = conflictingName;
    }
	
    public String getConflictingName() {
    	return this.conflictingName;
    }
    
    @Override 
    public String getMessage() {
        return "Username contains char: " + conflictingName;
    }
}
