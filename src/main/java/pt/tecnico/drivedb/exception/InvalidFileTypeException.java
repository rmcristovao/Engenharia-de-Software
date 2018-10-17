package pt.tecnico.drivedb.exception;

public class InvalidFileTypeException extends DriveDBApplicationException{
	
	private static final long serialVersionUID = 1L;
	private String typeName;
	
	public InvalidFileTypeException(String type){
		typeName = type;
	}
	
	public String getType(){
		return typeName;
	}
	
	@Override
    public String getMessage() {
        return "Invalid file type: " + getType();
    }
}