package pt.tecnico.drivedb.exception;

public class ExtensionHasNotAppAssociationException extends Exception {
	 private static final long serialVersionUID = 1L;

	    private String extension;

	    public ExtensionHasNotAppAssociationException(String ex){
	    	extension = ex;
	    }

	    public String getFileName(){
	    	return extension;
	    }

	    @Override
	    public String getMessage(){
	    	return "Extension " + extension + " has not app association.";
	    }
}
