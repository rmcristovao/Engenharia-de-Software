package pt.tecnico.drivedb.service;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;

public class ExportTest extends AbstractServiceTest {

    protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
	Manager m = Manager.getInstance();
	User mpl = new User(m, "mpl", "Bruno Lampreia", "mplmpl123");
	User rcv = new User(m, "rcv", "Rui Carlos", "ruicarlos123");
	User lsk = new User(m, "lsk", "Luis Santiago K", "lsklsk123");
	Dir mplHome = (Dir) m.getBase().lookup(m.getRootUser(), "/home/mpl");
	Dir rcvHome = (Dir) m.getBase().lookup(m.getRootUser(), "/home/rcv");
	Dir lskHome = (Dir) m.getBase().lookup(m.getRootUser(), "/home/lsk");
	
	new PlainFile("PlainEmpty", mpl, mplHome, null);
	new PlainFile("Plain", mpl, mplHome, "xpto");
	new Dir("Dir", rcv, rcvHome);
	new App("App", lsk, lskHome, "java.math.Sum");
	new Link("Link", lsk, lskHome, "/home/mpl");

    }

    @Test
    public void success() throws Exception {
        ExportDriveDBService service = new ExportDriveDBService();
        service.execute();
        Document doc = service.result();

		Element e = doc.getRootElement();
        assertEquals("exported 11 elements", 11, e.getChildren().size());
        assertEquals("exported 3 user", 3, e.getChildren("user").size());
        assertEquals("exported 4 dir", 4, e.getChildren("dir").size());
        assertEquals("exported 2 plain", 2, e.getChildren("plain").size());
        assertEquals("exported 1 app", 1, e.getChildren("app").size());
        assertEquals("exported 1 link", 1, e.getChildren("link").size());
        
		for (Element p: e.getChildren("user")) {
		    String username = p.getAttribute("username").getValue();
		    String name = p.getChild("name").getText();
		    String pass = p.getChild("password").getText();
		    String home = p.getChild("home").getText();
		    String mask = p.getChild("mask").getText();
	
		    if (username.equals("mpl")) {
		    	assertEquals("Mpl name", "Bruno Lampreia",name);
		    	assertEquals("Mpl password", "mplmpl123", pass);
		    	assertEquals("Mpl home", "/home/mpl", home);
		    	assertEquals("Mpl mask", "rwxd----", mask);
			
		    } else if (username.equals("lsk")) {
		    	assertEquals("lsk name", "Luis Santiago K",name);
				assertEquals("lsk password", "lsklsk123", pass);
				assertEquals("lsk home", "/home/lsk", home);
				assertEquals("lsk mask", "rwxd----", mask);
			
		    }else if (username.equals("rcv")) {
				assertEquals("lsk name", "Rui Carlos",name);
				assertEquals("lsk password", "ruicarlos123", pass);
				assertEquals("lsk home", "/home/rcv", home);
				assertEquals("lsk mask", "rwxd----", mask);
				
		    } else fail("invalid username.");
		}
		
		for (Element p: e.getChildren("dir")) {
		    String path = p.getChild("path").getText();
		    String name = p.getChild("name").getText();
		    String owner = p.getChild("owner").getText();
		    String perm = p.getChild("perm").getText();
	
		    if (name.equals("Dir")) {
		    	assertEquals("Dir path", "/home/rcv/", path);
		    	assertEquals("Dir owner", "rcv", owner);
		    	assertEquals("Dir permission", "rwxd----", perm);
		    }
		}
		
		for (Element p: e.getChildren("plain")) {
		    String path = p.getChild("path").getText();
		    String name = p.getChild("name").getText();
		    String owner = p.getChild("owner").getText();
		    String perm = p.getChild("perm").getText();
		    String content = p.getChild("contents").getText();
	
		    if (name.equals("Plain")) {
		    	assertEquals("Plain path", "/home/mpl/", path);
		    	assertEquals("Plain owner", "mpl", owner);
		    	assertEquals("Plain permission", "rwxd----", perm);
		    	assertEquals("Plain contents", "xpto", content);
		    }
		}
		
		for (Element p: e.getChildren("app")) {
		    String path = p.getChild("path").getText();
		    String name = p.getChild("name").getText();
		    String owner = p.getChild("owner").getText();
		    String perm = p.getChild("perm").getText();
		    String method = p.getChild("method").getText();
	
		    if (name.equals("App")) {
		    	assertEquals("App path", "/home/lsk/", path);
		    	assertEquals("App owner", "lsk", owner);
		    	assertEquals("App permission", "rwxd----", perm);
		    	assertEquals("App method", "java.math.Sum", method);
		    }
		}
		
		for (Element p: e.getChildren("link")) {
		    String path = p.getChild("path").getText();
		    String name = p.getChild("name").getText();
		    String owner = p.getChild("owner").getText();
		    String perm = p.getChild("perm").getText();
		    String value = p.getChild("value").getText();
	
		    if (name.equals("Link")) {
		    	assertEquals("Link path", "/home/lsk/", path);
		    	assertEquals("Link owner", "lsk", owner);
		    	assertEquals("Link permission", "rwxd----", perm);
		    	assertEquals("Link value", "/home/mpl", value);
		    }
		}
    }
}


