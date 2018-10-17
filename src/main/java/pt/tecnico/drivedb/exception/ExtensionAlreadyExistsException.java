package pt.tecnico.drivedb.exception;

public class ExtensionAlreadyExistsException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String ex;

    public ExtensionAlreadyExistsException(String extension){
    	ex = extension;
    }

    public String getExtensionName(){
    	return ex;
    }

    @Override
    public String getMessage(){
    	return "This extension " + ex + " already exists";
    }
}