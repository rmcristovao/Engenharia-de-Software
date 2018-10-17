package pt.tecnico.drivedb.exception;

public class IsNotDirException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String filename;

    public IsNotDirException(String fileName){
    	filename = fileName;
    }

    public String getFileName(){
    	return filename;
    }

    @Override
    public String getMessage(){
    	return "This file " + filename + " is not a dir";
    }
}
