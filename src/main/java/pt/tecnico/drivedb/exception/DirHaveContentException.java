package pt.tecnico.drivedb.exception;

public class DirHaveContentException extends DriveDBApplicationException{

	private static final long serialVersionUID = 1L;
	
	public DirHaveContentException(){
	}
	
	@Override
	public String getMessage(){
		return "Dir doesn't have a content";
	}
}