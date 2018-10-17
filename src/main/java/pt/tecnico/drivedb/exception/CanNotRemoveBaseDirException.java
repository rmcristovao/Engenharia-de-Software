package pt.tecnico.drivedb.exception;

public class CanNotRemoveBaseDirException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String dirname;
	
    public CanNotRemoveBaseDirException (String dirName) {
    	dirname = dirName;
    }
    
    @Override 
    public String getMessage() {
        return "Can not remove / from the name of directory: " + dirname; 
    }
}