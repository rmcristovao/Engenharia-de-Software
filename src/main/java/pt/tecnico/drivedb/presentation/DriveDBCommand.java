package pt.tecnico.drivedb.presentation;

public abstract class DriveDBCommand extends Command {
  public DriveDBCommand(Shell sh, String n) { super(sh, n); }
  public DriveDBCommand(Shell sh, String n, String h) { super(sh, n, h); }
}
