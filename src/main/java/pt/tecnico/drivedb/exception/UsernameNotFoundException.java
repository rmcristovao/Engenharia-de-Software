package pt.tecnico.drivedb.exception;

public class UsernameNotFoundException extends DriveDBApplicationException {

    private static final long serialVersionUID = 1L;

    private String username;

    public UsernameNotFoundException(String userName) {
        username = userName;
    }

    public String getUserName() {
        return username;
    }

    @Override
    public String getMessage() {
        return "User " + username + " does not exist";
    }
}