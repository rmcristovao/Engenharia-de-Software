package pt.tecnico.drivedb.exception;

public class NotPlainFileException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String file;

    public NotPlainFileException( String f){

    	file = f;
    }

    public String getFile(){

    	return file;

    }

    @Override
    public String getMessage(){
    	return "This file " + file + " is not a plain file";
    }

    
}
