package pt.tecnico.drivedb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import static org.junit.Assert.assertFalse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.StringHolder;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.EnvVarDoesNotExistException;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.IsNotDirException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.service.DriveDBService;

@RunWith(JMockit.class)
public class ChangeDirectoryTest extends AbstractServiceTest{
	long token;
	long tokenRoot;
	User current;

	private Link linkVar;
	private Link endVar;
	private Link wrongVar;
	private Link nonExistentVar;

	long tokenExp;

	protected void setTokenExp(long token){
		this.tokenExp = token;
	}
	
	protected long getTokenExp() {
		return tokenExp;
	}
	
	protected void setToken(long token){
		this.token = token;
	}
	
	protected long getToken() {
		return token;
	}
	protected void setTokenRoot(long tokenRoot){
		this.tokenRoot = tokenRoot;
	}
	
	protected long getTokenRoot() {
		return tokenRoot;
	}
	
	protected Session getSession() {
		return DriveDBService.getManager().getSessionbyToken(token);
	}
	
	protected File getFile(String path){
		Manager m = Manager.getInstance();
		return m.getBase().lookup(current, path);
	}
	
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
        Manager m = Manager.getInstance();
        new User(m, "rcv", "Rui Carlos", "ruicarlos");
        current = new User(m, "mpl", "Pedro", "mplmplpedro");
        new User(m, "userExpirado", "Expira");
        setTokenExp(new Session(m, "userExpirado", "userExpirado").getToken());
        setToken(new Session(m, "mpl", "mplmplpedro").getToken());
        Session s = m.getSessionbyToken(token);
        Session b = new Session(m, "rcv", "ruicarlos");
        
        new Dir("test", b.getCurrentUser(), b.getWorkingDir());
        
        Dir test = new Dir("test", s.getCurrentUser(), s.getWorkingDir());
        Dir abc = new Dir("abc", s.getCurrentUser(), test);
        new PlainFile("text", s.getCurrentUser(), test, null);
        Dir def = new Dir("def", s.getCurrentUser(), test);
        new Link("link1", s.getCurrentUser(), test, "/home/mpl/test/def");
        new Link("link2", s.getCurrentUser(), test, "../test/abc");
        new Link("link3", s.getCurrentUser(), test, "/home/mpl/test/link2");
        setTokenRoot(new Session(m, "root", "***").getToken());
        
		s.createOrAddVar("$HOME", "/home");
		s.createOrAddVar("$ABC", "/mpl/test/abc");
		s.createOrAddVar("$WRONG", "/etc/xpto/");
        
        new Link("linkVar", s.getCurrentUser(), test, "$HOME/mpl"); //$HOME = /home
        new Link("endVar", s.getCurrentUser(), test, "/home/$ABC"); //$ABC = /mpl/test/abc
        new Link("wrongVar", s.getCurrentUser(), test, "/home/$WRONG"); //$WRONG = /etc/xpto/
        new Link("nonExistentVar", s.getCurrentUser(), test, "/home/$NONEXISTENT");
    }

    @Test
    public void successAbsolutPath() {
        final String path = "/home/mpl/test";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with absolut path", path, result);         
    }
    
    @Test
    public void successLinkWithEnvVar() {
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "mpl"); }
           };
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), "/home/mpl/test/linkVar");
        service.execute();
        assertEquals(service.result(), "/home/mpl");  
    }
    
    @Test
    public void successLinkWithEnvVarEndOfPath() {
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "mpl/test/abc"); }
           };
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), "/home/mpl/test/endVar");
        service.execute();
        assertEquals(service.result(), "/home/mpl/test/abc");  
    }
    
    @Test(expected = InvalidPathException.class)
    public void invalidPathLinkWithEnvVar() {
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { throw new InvalidPathException(path); }
           };
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), "/home/mpl/test/nonExistentVar");
        service.execute();
    }
    
    @Test(expected = EnvVarDoesNotExistException.class)
    public void invalidPathLinkWithEnvVarNonExistent() {
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { throw new EnvVarDoesNotExistException("path"); }
           };
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), "/home/mpl/test/nonExistentVar");
        service.execute();
    }
    
    @Test
    public void successRelativePath() {
        final String path = "test";
        final String expected = "/home/mpl/test";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path", expected, result);         
    }
    
    @Test
    public void successRelativePathWithDot() {
        final String path = "./test";
        final String expected = "/home/mpl/test";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with dot", expected, result);   
    }
    
    @Test
    public void successRelativePathWithParent() {
        final String path = "../mpl/test";
        final String expected = "/home/mpl/test";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with parent", expected, result); 
    }
    
    @Test
    public void successWithAbsolutLink() {
        final String path = "test/link1";
        final String expected = "/home/mpl/test/def";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with absolut path with link", expected, result);         
    }
    
    @Test
    public void successWithRelativeLink() {
        final String path = "test/link2";
        final String expected = "/home/mpl/test/abc";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with link", expected, result);   
    }
    
    @Test
    public void successWithJustDot() {
        final String path = ".";
        final String expected = "/home/mpl";
        
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with .", expected, result);   
    }
    
    @Test
    public void successParentRootDir() {
        final String path = "../../../../../..";
        final String expected = "/";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with .. passing the / dir", expected, result);      
    }
    
    @Test
    public void successWithComplexLink() {
        final String path = "../../../../.././home/../home/mpl/test/../test/abc/.";
        final String expected = "/home/mpl/test/abc";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with link", expected, result);      
    }
    @Test
    public void successWithLinktoLink() {
        final String path = "/home/mpl/test/link3";
        final String expected = "/home/mpl/test/abc";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with relative path with link", expected, result);      
    }
    @Test
    public void successWithRootPermission() {
		Manager m = Manager.getInstance();
		User root = m.getUserByUsername("root");
		tokenRoot = new Session(m, root.getUsername(), root.getPassword()).getToken();
        final String path = "/home/rcv/test";
        final String expected = "/home/rcv/test";
        ChangeDirectoryService service = new ChangeDirectoryService(getTokenRoot(), path);
        service.execute();
        String  result = service.result();
        assertEquals("Error changing directory with root permissions", expected, result);      
    }
    
    @Test(expected = InvalidPathException.class)
    public void invalidNullPath() {
        final String path = null;
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
    }
    
    @Test(expected = FileDoesNotExistException.class)
    public void invalidNonexistentPath() {
        final String path = "/home/ola/abc";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
    }
    
    @Test(expected = UserDoesNotHavePermissionException.class)
    public void invalidPermission() {
        final String path = "/home/rcv/test";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
    }
    
    @Test(expected = IsNotDirException.class)
    public void invalidPathWithFile() {
        final String path = "/home/mpl/test/text";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
    }
    @Test(expected = IsNotDirException.class)
    public void invalidPathWithFileInMiddle() {
        final String path = "/home/mpl/test/text/abc";
        ChangeDirectoryService service = new ChangeDirectoryService(getToken(), path);
        service.execute();
    }
    
	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String path = "/home/EXPIRA";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getTokenExp()).testExpiredSession();
		manager.getSessionbyToken(getTokenExp());
		ChangeDirectoryService service = new ChangeDirectoryService(getTokenExp(), path);
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String path = "/home/EXPIRA";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getTokenRoot()).testExpiredRootSession();
		manager.getSessionbyToken(getTokenRoot());
        ChangeDirectoryService service = new ChangeDirectoryService(getTokenRoot(), path);
        service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String path =  "invalid";
        ChangeDirectoryService service = new ChangeDirectoryService(1, path);
        service.execute();
	}

}