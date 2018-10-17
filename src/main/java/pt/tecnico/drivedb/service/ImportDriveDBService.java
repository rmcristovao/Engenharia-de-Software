
package pt.tecnico.drivedb.service;

import java.io.File;
import java.io.IOException;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import pt.tecnico.drivedb.exception.ImportDocumentException;

public class ImportDriveDBService extends  DriveDBService {
    private final Document doc;

    public ImportDriveDBService(Document doc) {
        this.doc = doc;
    }

    @Override
    protected void dispatch() throws ImportDocumentException {
    	 getManager().xmlImport(doc.getRootElement());
    }
}
