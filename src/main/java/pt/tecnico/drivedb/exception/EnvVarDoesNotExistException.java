package pt.tecnico.drivedb.exception;

public class EnvVarDoesNotExistException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String nameVar;
 
    public EnvVarDoesNotExistException(String name){
    	nameVar = name;
     }
    
    public String getName(){
    	return nameVar;
    }
    
    @Override
    public String getMessage(){
    	return "This var " + nameVar + " does not exist";
    }
    
}
