package pt.tecnico.drivedb.exception;

public class ExportDocumentException extends DriveDBApplicationException{
	
	private static final long serialVersionUID = 1L;

	public ExportDocumentException(){
		super("Error exporting to XML");
	}
}
