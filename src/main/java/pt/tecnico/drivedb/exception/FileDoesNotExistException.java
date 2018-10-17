package pt.tecnico.drivedb.exception;

public class FileDoesNotExistException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String filename;

    public FileDoesNotExistException(String fileName){
    	filename = fileName;
    }

    public String getFileName(){
    	return filename;
    }

    @Override
    public String getMessage(){
    	return "This file " + filename + " does not exist";
    }
}
