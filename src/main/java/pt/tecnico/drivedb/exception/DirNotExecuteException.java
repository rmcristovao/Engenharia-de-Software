package pt.tecnico.drivedb.exception;

public class DirNotExecuteException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String filename;

    public DirNotExecuteException(String fileName){
    	filename = fileName;
    }

    public String getFileName(){
    	return filename;
    }

    @Override
    public String getMessage(){
    	return filename + " is a dir and it can't be execute";
    }
}
