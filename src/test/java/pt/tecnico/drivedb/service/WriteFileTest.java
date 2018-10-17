package pt.tecnico.drivedb.service;


import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import javax.xml.soap.Text;

import org.junit.Test;

import mockit.Mock;
import mockit.MockUp;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.EnvVarDoesNotExistException;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidAppContentException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.NotPlainFileException;
import pt.tecnico.drivedb.exception.UserAlreadyExistsException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

public class WriteFileTest extends AbstractServiceTest{

	long token,rootToken,expToken;;
	Dir dir;
	User current;
	protected void setToken(long token)
	{
		this.token = token;
	}
	protected long getToken()
	{
		return token;
	}
	private long getExpToken(){
		return expToken;
	}

	private long getRootToken(){
		return rootToken;
	}
	
	protected File getFile(String path){
		Manager m = Manager.getInstance();
		return m.getBase().lookup(current, path);
	}

	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
		Manager m = Manager.getInstance();
		current = new User(m, "margaridamorais", "Margarida");
		new User(m, "mariamorais", "Maria");
		new User(m, "userExpirado", "Expira");
		User root = m.getUserByUsername("root");
		User exp = m.getUserByUsername("userExpirado");

		long token = new Session(m, "margaridamorais", "margaridamorais").getToken();
		long tokenU = new Session(m, "mariamorais", "mariamorais").getToken();

		setToken(token);
		rootToken = new Session(m, root.getUsername(), root.getPassword()).getToken();
		expToken = new Session(m, exp.getUsername(), exp.getPassword()).getToken();

		Session s = m.getSessionbyToken(token);
		Session b = m.getSessionbyToken(tokenU);
		PlainFile Esof = new PlainFile("ESof", b.getCurrentUser(), b.getWorkingDir(), "blhac");
		new PlainFile("teste", s.getCurrentUser(), s.getWorkingDir(), "oioioioi");

		new Link("link", s.getCurrentUser(), s.getWorkingDir(), "/home/margaridamorais/teste");
		new Dir("directorio", s.getCurrentUser(), s.getWorkingDir());
		new Link("linktodir", s.getCurrentUser(), s.getWorkingDir(), "/home/margaridamorais/directorio");
		new Link("linktolink", s.getCurrentUser(), s.getWorkingDir(), "/home/margaridamorais/link");
		new Link("looplink", s.getCurrentUser(), s.getWorkingDir(), "/home/margaridamorais/looplink2");
		new Link("looplink2", s.getCurrentUser(), s.getWorkingDir(), "/home/margaridamorais/looplink");
		new App("app", s.getCurrentUser(), s.getWorkingDir(), "java.Math.Sum");
		new App("appvazia", s.getCurrentUser(), s.getWorkingDir(), null);
		new PlainFile("vazio", s.getCurrentUser(), s.getWorkingDir(), null);
		
		s.createOrAddVar("$HOME", "/home");
		s.createOrAddVar("$ABC", "/margaridamorais/teste");
		s.createOrAddVar("$WRONG", "/etc/xpto/");
		
        new Link("linkVar", s.getCurrentUser(), s.getWorkingDir(), "$HOME/margaridamorais/teste"); //$HOME = /home
        new Link("endVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$ABC"); //$ABC = /margaridamorais/teste
        new Link("wrongVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$WRONG"); //$WRONG = /etc/xpto/
        new Link("nonExistentVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$NONEXISTENT");
	}
    
	private void setTokenAndWorkingDirRoot()
	{
		Manager m = Manager.getInstance();
		//m.getRootUser();
		long tokenR = new Session(m, m.getRootUser().getUsername(), m.getRootUser().getPassword()).getToken();

		Session r = m.getSessionbyToken(tokenR);
		Session s= m.getSessionbyToken(token);
		//setWorkingDir(s.getWorkingDir());
		r.setWorkingDir(s.getWorkingDir());
		setToken(tokenR);


	}
	private void ChangeToOtherUserDir() 
	{
		Manager m = Manager.getInstance();
		try {
			new User(m, "joni12345", "Joao");
		}	catch (UserAlreadyExistsException e) {e.printStackTrace();} 
		catch (UsernameContainsCharsException e) {e.printStackTrace();} 
		catch (InvalidUsernameException e) {e.printStackTrace();}
		long tokenP = new Session(m, "joni12345", "joni12345").getToken();
		Session p = m.getSessionbyToken(tokenP);
		Session o = m.getSessionbyToken(token);
		p.setWorkingDir(o.getWorkingDir());
		setToken(tokenP);

	}

	private String getFileData(String path) {
		Manager m = Manager.getInstance();
		PlainFile file = (PlainFile) m.getBase().lookup(m.getRootUser(), path);
		String data = file.getData();
		return data;
	}
	private String getFileName(String path)
	{
		Manager m = Manager.getInstance();
		PlainFile file = (PlainFile) m.getBase().lookup(m.getRootUser(), path);
		String name = file.getName();
		return name;
	}

	@Test(expected = FileDoesNotExistException.class)
	public void fileDoesNotExists()
	{
		final String filename = "oi";

		WriteFileService service = new WriteFileService(getToken(), filename, "olaaaa");
		service.execute();
	}
	@Test
	public void sucessFileExists()
	{
		final String filename = "teste";
		final String data = "olaaaa";


		WriteFileService service = new WriteFileService(getToken(), filename, data);
		service.execute();

		String content = getFileData("/home/margaridamorais/teste");
		String name = getFileName("/home/margaridamorais/teste");

		//check if file was written
		assertEquals("Content was not written", "olaaaa", content);
		assertEquals("Invalid filename", "teste", name);


	}
	@Test(expected = NotPlainFileException.class)
	public void fileNotPlainFile()
	{
		final String filename = "directorio";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(), filename, data);
		service.execute();

	}

	@Test
	public void successEmptyFile()
	{
		final String filename = "vazio";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();
		String content = getFileData("/home/margaridamorais/vazio");
		String name = getFileName("/home/margaridamorais/vazio");

		//check if file was written
		assertEquals("Content was not written", "olaaaa", content);


	}
	@Test(expected = InvalidAppContentException.class)
	public void emptyApp()
	{
		final String filename = "appvazia";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();
	}

	@Test
	public void sucessEmptyApp()
	{
		final String filename = "appvazia";
		final String data = "java.lang.Math.sqrt";
		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();
		String content = getFileData("/home/margaridamorais/appvazia");


		//check if file was written
		assertEquals("Content was not written", data, content);
	}
	@Test(expected = InvalidAppContentException.class)
	public void WriteApp()
	{
		final String filename = "app";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();

	}

	@Test
	public void sucessWriteApp()
	{
		final String filename = "app";
		final String data = "java.lang.Math.sqrt";
		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();
		String content = getFileData("/home/margaridamorais/app");


		//check if file was written
		assertEquals("Content was not written", data, content);

	}

	@Test(expected = NotPlainFileException.class)
	public void LinkToDir()
	{
		final String filename = "linktodir";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(), filename, data);
		service.execute();
	}
	@Test
	public void LinkToLink()
	{
		final String filename = "linktolink";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(), filename, data);
		service.execute();

		String content = getFileData("/home/margaridamorais/teste");


		//check if file was written
		assertEquals("Content was not written", "olaaaa", content);

	}
	@Test(expected = java.lang.StackOverflowError.class)
	public void LoopLink()
	{	
		final String filename = "looplink";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(), filename, data);
		service.execute();
	}

	@Test
	public void sucessWriteLink()
	{
		final String filename = "link";
		final String data = "olaaaa";
		WriteFileService service = new WriteFileService(getToken(), filename, data);
		service.execute();

		String content = getFileData("/home/margaridamorais/teste");
		String name = getFileName("/home/margaridamorais/teste");


		//check if file was written
		assertEquals("Content was not written", "olaaaa", content);
		//assertEquals("Invalid filename", "link", name);	
	}
	@Test(expected = UserDoesNotHavePermissionException.class)
	public void UserDoesNotHavePermission()
	{

		final String filename = "teste";
		final String data = "olaaa";
		ChangeToOtherUserDir();

		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();
	}
	@Test
	public void successRootHasPermission()
	{
		final String filename = "teste";
		final String data = "olaaa";
		setTokenAndWorkingDirRoot();

		WriteFileService service = new WriteFileService(getToken(),filename, data);
		service.execute();
		String content = getFileData("/home/margaridamorais/teste");


		//check if file was written
		assertEquals("Content was not written", "olaaa", content);

	}
	
	public void successWithPath()
	{
		final String filename = "teste";
		final String data = "oleee";
		final String path = "/home/margaridamorais/teste";
		setTokenAndWorkingDirRoot();

		WriteFileService service = new WriteFileService(getToken(),filename, data, path);
		service.execute();
		String content = getFileData("/home/margaridamorais/teste");


		//check if file was written
		assertEquals("Content was not written", "oleee", content);

	}

	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String fileName = "EXPIRA";
		final String content = "texto de teste";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getExpToken()).testExpiredSession();
		manager.getSessionbyToken(getExpToken());
		WriteFileService service = new WriteFileService(getExpToken(),fileName,content);
		service.execute();
	}

	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String fileName = "fileTest";
		final String content = "texto de teste";

		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getRootToken()).testExpiredRootSession();
		manager.getSessionbyToken(getRootToken());
		WriteFileService service = new WriteFileService(getRootToken(),fileName,content);
		service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String fileName =  "invalid";
		final String content =  "invalid";
		WriteFileService service = new WriteFileService(1,fileName,content);
		service.execute();
	}
	 @Test
	    public void successLinkWithEnvVar() {
	    	final String fileName = "linkVar";
	        new MockUp<Link>() {
	        	  @Mock
	        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "margaridamorais/teste"); }
	           };
	        WriteFileService service = new WriteFileService(getToken(), fileName, "Test mock");
	        service.execute();
			String content = getFileData("/home/margaridamorais/teste");

			//check if file was written
			assertEquals("Content was not written", "Test mock", content); 
	    }
	    
	    @Test
	    public void successLinkWithEnvVarEndOfPath() {
	    	final String fileName = "endVar";
	        new MockUp<Link>() {
	      	  @Mock
	      	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "margaridamorais/teste"); }
	         };
	      WriteFileService service = new WriteFileService(getToken(), fileName, "Test mock");
	      service.execute();
			String content = getFileData("/home/margaridamorais/teste");

			//check if file was written
			assertEquals("Content was not written", "Test mock", content);  
	    }
	    
	    @Test(expected = InvalidPathException.class)
	    public void invalidPathLinkWithEnvVar() {
	    	final String fileName = "wrongVar";
	        new MockUp<Link>() {
	        	  @Mock
	        	  	File lookup(User current, String path) { throw new InvalidPathException(path); }
	           };
	        WriteFileService service = new WriteFileService(getToken(), fileName, "Test mock");
	        service.execute();
	 
	    }
	    
	    @Test(expected = EnvVarDoesNotExistException.class)
	    public void invalidPathLinkWithEnvVarNonExistent() {
	    	final String fileName = "nonExistentVar";
	        new MockUp<Link>() {
	      	  @Mock
	      	  	File lookup(User current, String path) { throw new EnvVarDoesNotExistException(path); }
	         };
	      WriteFileService service = new WriteFileService(getToken(), fileName, "Test mock");
	      service.execute();
	    }
} 