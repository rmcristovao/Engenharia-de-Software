package pt.tecnico.drivedb.exception;

public class RootCanNotBeRemovedException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    public RootCanNotBeRemovedException() {
        super();
    }
    
    @Override 
    public String getMessage() {
        return "User Root cannot be removed. ";
    }
    
}
