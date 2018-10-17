package pt.tecnico.drivedb.service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;

public abstract class DriveDBService {
	protected static final Logger log = LogManager.getRootLogger();
	@Atomic
    public final void execute() throws DriveDBApplicationException {
        dispatch();
    }

	public static Manager getManager() {
        return Manager.getInstance();
    }

    protected abstract void dispatch() throws DriveDBApplicationException;
}

