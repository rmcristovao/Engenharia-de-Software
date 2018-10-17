package pt.tecnico.drivedb.exception;

public class ContentIsNotExcutableExeption extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String content;

    public ContentIsNotExcutableExeption(String c){
    	content = c;
    }

    public String getFileName(){
    	return content;
    }

    @Override
    public String getMessage(){
    	return "This content " + content + " can't be execute";
    }
}

