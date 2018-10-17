package pt.tecnico.drivedb.service;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.xml.soap.Text;

import org.junit.Test;
import org.junit.validator.PublicClassValidator;

import mockit.Mock;
import mockit.MockUp;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.exception.EnvVarDoesNotExistException;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.FileIsEmptyException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.NotPlainFileException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

public class ReadFileTest extends AbstractServiceTest{

		private long token,tokent,rootToken,expToken;
		User current;
		protected void setToken(long token){
			this.token = token;
		}
		
		protected long getToken(){
			return token;
		}
		
		private long getExpToken(){
			return expToken;
		}
		
		private long getRootToken(){
			return rootToken;
		}
		
		protected void setTokenT(long token){
			this.tokent = token;
		}
		protected long getTokenT(){
			return tokent;
		}
		protected File getFile(String path){
			Manager m = Manager.getInstance();
			return m.getBase().lookup(current, path);
		}
	@Override	
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException{
		Manager m = Manager.getInstance();
		current = new User(m, "lolo", "Leonor" ,"leonor1234");
		new User(m, "tecas", "Reresa" ,"teresa1234");
		new User(m, "userExpirado", "Expira");
		
		User root = m.getUserByUsername("root");
		User exp = m.getUserByUsername("userExpirado");
		
    	long token = new Session(m, "lolo", "leonor1234").getToken();
    	long tokenT = new Session(m, "tecas", "teresa1234").getToken();
    	setToken(token);
    	setTokenT(tokenT);
    	rootToken = new Session(m, root.getUsername(), root.getPassword()).getToken();
		expToken = new Session(m, exp.getUsername(), exp.getPassword()).getToken();
		
		Session s = m.getSessionbyToken(token);
		Session v = m.getSessionbyToken(tokenT);

		new PlainFile("prove", v.getCurrentUser(), v.getWorkingDir(), "ola sou a teresinha!");
    	
    	new PlainFile("test", s.getCurrentUser(), s.getWorkingDir(), "ola jaques");
    	new PlainFile("vazio", s.getCurrentUser(), s.getWorkingDir(), null);

    	new Dir("Dir", s.getCurrentUser(), s.getWorkingDir());
    	new App("App", s.getCurrentUser(), s.getWorkingDir(), "package.arabian.nights");
    	new App("AppVazia", s.getCurrentUser(), s.getWorkingDir(), null);
    	
    	new Link("link", s.getCurrentUser(), s.getWorkingDir(), "/home/lolo/test");
    	new Link("linkToDir", s.getCurrentUser(), s.getWorkingDir(), "/home/lolo/Dir");
    	new Link("linkToLink", s.getCurrentUser(), s.getWorkingDir(), "/home/lolo/link");
    	new Link("linkToApp", s.getCurrentUser(), s.getWorkingDir(), "/home/lolo/App");
    	
		s.createOrAddVar("$HOME", "/home");
		s.createOrAddVar("$ABC", "/lolo/test");
		s.createOrAddVar("$WRONG", "/etc/xpto/");
		
        new Link("linkVar", s.getCurrentUser(), s.getWorkingDir(), "$HOME/lolo/test"); //$HOME = /home
        new Link("endVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$ABC"); //$ABC = /lolo/test
        new Link("wrongVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$WRONG"); //$WRONG = /etc/xpto/
        new Link("nonExistentVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$NONEXISTENT");
	}

	/************CASOS DE SUCESSO************/	
	@Test
    public void successLinkWithEnvVar() {
		final String fileName = "linkVar";
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "lolo/test"); }
           };
        ReadFileService service = new ReadFileService(getToken(), fileName);
        service.execute();
        assertEquals(service.getResult(), "ola jaques");  
    }
    
    @Test
    public void successLinkWithEnvVarEndOfPath() {
    	final String fileName = "endVar";
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "lolo/test"); }
           };
        ReadFileService service = new ReadFileService(getToken(), fileName);
        service.execute();
        assertEquals(service.getResult(), "ola jaques");   
    }
    
    @Test(expected = InvalidPathException.class)
    public void invalidPathLinkWithEnvVar() {
    	final String fileName = "wrongVar";
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { throw new InvalidPathException(path); }
           };
        ReadFileService service = new ReadFileService(getToken(), fileName);
        service.execute();
    }
    
    @Test(expected = EnvVarDoesNotExistException.class)
    public void invalidPathLinkWithEnvVarNonExistent() {
    	final String fileName = "nonExistentVar";
        new MockUp<Link>() {
        	  @Mock
        	  	File lookup(User current, String path) { throw new EnvVarDoesNotExistException("path"); }
           };
           ReadFileService service = new ReadFileService(getToken(), fileName);
        service.execute();
    }
    
	@Test
	public void CanReadFile(){
		
		final String fileName = "prove";
		final String data = "ola sou a teresinha!";
		
		ReadFileService service = new ReadFileService(getTokenT(), fileName);
        service.execute();
        String result = service.getResult(); 
        assertEquals("Content can not be read", data, result);
    }
	
	@Test
	public void CanReadLinkToLink(){

		final String fileName = "linkToLink";
		final String data = "ola jaques";

		ReadFileService service = new ReadFileService(getToken(), fileName);
		service.execute();
		String result = service.getResult(); 
        
        assertEquals("Content can not be read", data, result);
	}


	@Test
	public void CanReadApp(){
		
		final String fileName = "App";
		final String data = "package.arabian.nights";

		ReadFileService service = new ReadFileService(getToken(),fileName);
		service.execute();
		 String result = service.getResult(); 
       
 		assertEquals("Content can not be read", data, result);
    }

	@Test
	public void CanReadLinkToApp(){

		final String fileName = "linkToApp";
		final String data = "package.arabian.nights";

		ReadFileService service = new ReadFileService(getToken(), fileName);
		service.execute();
		
		 String result = service.getResult(); 
		
		assertEquals("Content can not be read", data, result);
	}

/************CASOS DE INSUCESSO*************/

	@Test(expected = FileDoesNotExistException.class)	
	public void fileDoesNotExist(){
		
		final String fileName = "opaa";
		
		ReadFileService service = new ReadFileService(getToken(), fileName);
        service.execute();
	}

	@Test(expected = FileIsEmptyException.class)
	public void emptyFile(){
		
		final String fileName = "vazio";
		
		ReadFileService service = new ReadFileService(getToken(),fileName);
		service.execute();
	}

	@Test(expected = UserDoesNotHavePermissionException.class)
	public void UserDoesNotHavePermission(){
		final String fileName = "prove";
		Manager.getInstance().getSessionbyToken(getToken()).changeDir("/home/tecas");
		ReadFileService service = new ReadFileService(getToken(),fileName);
		service.execute();
	}

	@Test(expected = NotPlainFileException.class)
	public void CanNotReadLinkToDir(){
		
		final String fileName = "linkToDir";
		ReadFileService service = new ReadFileService(getToken(), fileName);
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String fileName = "EXPIRA";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getExpToken()).testExpiredSession();
		manager.getSessionbyToken(getExpToken());
		ReadFileService service = new ReadFileService(getExpToken(),fileName);
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String fileName = "fileTest";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getRootToken()).testExpiredRootSession();
		manager.getSessionbyToken(getRootToken());
		ReadFileService service = new ReadFileService(getRootToken(),fileName);
		service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String fileName =  "invalid";
		ReadFileService service = new ReadFileService(1,fileName);
		service.execute();
	}
	
}
