package pt.tecnico.drivedb.exception;

public class InvalidLengthPathException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String path;
    private String name;

    public InvalidLengthPathException(String p, String n){
    	path = p;
    	name = n;
    }

    public InvalidLengthPathException(String p){
    	path = p;
    }
    
    public String getPath(){
    	return path; 
    }

    public String getName(){
    	return name; 
    }
    @Override
    public String getMessage(){
    	return "Path " + path + " is too long!";
    }
    
}
