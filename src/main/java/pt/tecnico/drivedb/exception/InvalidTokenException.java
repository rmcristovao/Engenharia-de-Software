package pt.tecnico.drivedb.exception;

public class InvalidTokenException extends  DriveDBApplicationException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenException(){
    }

    @Override
    public String getMessage(){
    	return "This token does not exist";
    }
}
