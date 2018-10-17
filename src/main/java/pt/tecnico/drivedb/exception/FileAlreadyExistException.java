package pt.tecnico.drivedb.exception;

public class FileAlreadyExistException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String filename;

    public FileAlreadyExistException(String fileName){
    	filename = fileName;
    }

    public String getFileName(){
    	return filename;
    }

    @Override
    public String getMessage(){
    	return "This file " + getFileName() + " does not exist";
    }
}

