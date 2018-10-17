package pt.tecnico.drivedb.exception;

public class InvalidLinkContentException extends DriveDBApplicationException{

	private static final long serialVersionUID = 1L;
	
	private String content;
	
	public InvalidLinkContentException(String linkContent){
		content = linkContent;
	}
	
	public String getContent(){
		return content;
	}
	
	@Override
	public String getMessage(){
		return getContent() + " is not a valid link";
	}

}
