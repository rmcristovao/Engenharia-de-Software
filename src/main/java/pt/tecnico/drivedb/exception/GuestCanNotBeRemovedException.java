package pt.tecnico.drivedb.exception;

public class GuestCanNotBeRemovedException extends DriveDBApplicationException{
    private static final long serialVersionUID = 1L;

    public GuestCanNotBeRemovedException() {
        super();
    }
    
    @Override 
    public String getMessage() {
        return "User Guest cannot be removed. ";
    }
}
