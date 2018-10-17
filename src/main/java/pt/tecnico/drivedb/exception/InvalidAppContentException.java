package pt.tecnico.drivedb.exception;

public class InvalidAppContentException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String content;

    public InvalidAppContentException( String f){

    	content = f;
    }

    public String getFile(){

    	return content;

    }

    @Override
    public String getMessage(){
    	return "This content " + content + " is not a app content";
    }

    
}