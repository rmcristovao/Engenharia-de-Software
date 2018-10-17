package pt.tecnico.drivedb.exception;

public class NothingToExecuteException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String filename;

    public NothingToExecuteException(String fileName){
    	filename = fileName;
    }

    public String getFileName(){
    	return filename;
    }

    @Override
    public String getMessage(){
    	return "This app " + filename + " is empty, nothing to execute";
    }
}
