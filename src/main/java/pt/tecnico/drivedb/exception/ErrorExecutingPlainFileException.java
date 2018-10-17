package pt.tecnico.drivedb.exception;

public class ErrorExecutingPlainFileException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String filename;

    public ErrorExecutingPlainFileException(String fileName){
    	filename = fileName;
    }

    public String getFileName(){
    	return filename;
    }

    @Override
    public String getMessage(){
    	return "Error excuting " + filename;
    }
}
