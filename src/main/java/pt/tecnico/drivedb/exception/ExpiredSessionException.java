package pt.tecnico.drivedb.exception;
public class ExpiredSessionException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    public ExpiredSessionException() {
        super("Expired Session.");
    }
}