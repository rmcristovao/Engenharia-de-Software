package pt.tecnico.drivedb;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.ObjectInputStream.GetField;
import java.time.format.TextStyle;
import java.io.File;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;


public class DriveDBApplication {
    static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException, UsernameContainsCharsException, InvalidUsernameException {
        System.out.println("*** Welcome to the DriveDB application! ***");
	try {
		init();
	    setup();
	    for (String s: args) xmlScan(new File(s));
	    print();
	} finally { FenixFramework.shutdown(); }
    }
    
    @Atomic
    public static void init(){
        log.trace("Init: " + FenixFramework.getDomainRoot());
        Manager m =  Manager.getInstance();
    }

    @Atomic
    public static void setup() {
       	log.trace("Setup: " + FenixFramework.getDomainRoot());
        Manager m = Manager.getInstance();
    }
	
	@Atomic
    public static void xmlScan(File file){
        log.trace("xmlScan: " + FenixFramework.getDomainRoot());
        Manager mn = Manager.getInstance();
        SAXBuilder builder = new SAXBuilder();
        try {
        	Document document = (Document)builder.build(file);
        	mn.xmlImport(document.getRootElement());
        } catch (JDOMException | IOException e) {
        	e.printStackTrace();
        }
    }

    @Atomic
    public static void print() {
    	//Code to test
        
    }
    
    @Atomic
    public static void xmlPrint() {
        log.trace("xmlPrint: " + FenixFramework.getDomainRoot());
        Document doc = Manager.getInstance().xmlExport();
        XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
        try { 	
        	xmlOutput.output(doc, new PrintStream(System.out));
        	} catch (IOException e) { 
        		System.out.println(e); 
        	}
    	}
	}
