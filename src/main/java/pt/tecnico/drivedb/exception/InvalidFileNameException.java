package pt.tecnico.drivedb.exception;

public class InvalidFileNameException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String  filename;

    public InvalidFileNameException(String fileName) {
        filename = fileName;
    }

    public String getInvalidfileName() {
        return filename;
    }

    @Override
    public String getMessage() {
        return "Invalid file name: " + filename;
    }
}

