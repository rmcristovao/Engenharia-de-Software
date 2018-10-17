package pt.tecnico.drivedb.exception;


public class InvalidUsernameException extends Exception {

    private static final long serialVersionUID = 1L;

    private String name;
	
    public InvalidUsernameException(String name) {
    	this.name = name;
    }
	
    public String getName() {
    	return this.name;
    }
    
    @Override 
    public String getMessage() {
        return "Username must have more than 3 characters: " + getName();
    }
}
