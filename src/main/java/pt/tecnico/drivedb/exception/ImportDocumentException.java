package pt.tecnico.drivedb.exception;

public class ImportDocumentException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    public ImportDocumentException() {
        super("Error in importing user from XML");
    }
}