package pt.tecnico.drivedb.exception;

public abstract class DriveDBApplicationException extends RuntimeException {
	
		private static final long serialVersionUID = 1L;
		
		public DriveDBApplicationException(){			
		}
		
		public DriveDBApplicationException(String msg){
			super(msg);
		}
	
}
