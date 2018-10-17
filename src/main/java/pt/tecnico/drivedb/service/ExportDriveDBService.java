package pt.tecnico.drivedb.service;

import org.jdom2.Document;

import pt.tecnico.drivedb.exception.ExportDocumentException;

public class ExportDriveDBService extends  DriveDBService {
	
    private Document doc;

    public ExportDriveDBService() {
    }

    @Override
    protected void dispatch() throws ExportDocumentException {
    	doc = getManager().xmlExport();
    }
    
    public final Document result() {
        return doc;
    }
}
