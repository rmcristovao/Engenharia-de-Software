package pt.tecnico.drivedb.exception;

public class InvalidPathException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String path;

    public InvalidPathException(String p){
    	path = p;
    }

    public String getPath(){
    	return path; 
    }

    @Override
    public String getMessage(){
    	return "Path " + path + "does not exist";
    }
    
}
