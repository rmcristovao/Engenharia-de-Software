package pt.tecnico.drivedb.exception;

public class LinkDoesNotHaveContentException extends DriveDBApplicationException{

	private static final long serialVersionUID = 1L;
	
	public LinkDoesNotHaveContentException(){
	}
	
	@Override
	public String getMessage(){
		return "Link must have a content";
	}
}