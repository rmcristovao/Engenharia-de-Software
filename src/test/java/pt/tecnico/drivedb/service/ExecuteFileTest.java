package pt.tecnico.drivedb.service;


import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import mockit.internal.expectations.TestOnlyPhase;
import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.Extension;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.ContentIsNotExcutableExeption;
import pt.tecnico.drivedb.exception.DirNotExecuteException;
import pt.tecnico.drivedb.exception.DriveDBApplicationException;
import pt.tecnico.drivedb.exception.EnvVarDoesNotExistException;
import pt.tecnico.drivedb.exception.ErrorExecutingPlainFileException;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.ExtensionHasNotAppAssociationException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidAppContentException;
import pt.tecnico.drivedb.exception.InvalidPathException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.NotPlainFileException;
import pt.tecnico.drivedb.exception.NothingToExecuteException;
import pt.tecnico.drivedb.exception.UserAlreadyExistsException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.service.ExecuteFileService;

public class ExecuteFileTest extends AbstractServiceTest{
	
	long token, tokenRoot, tokenExp, tokenNoPermission;
	Dir dir;
	User user;
	User userNoPermission;
	App app;
	protected void setToken(long token){
		this.token = token;
	}
	
	protected long getTokenRoot(){
		return tokenRoot;
	}
	
	protected long getTokenExp(){
		return tokenExp;
	}
	
	protected long getToken(){
		return token;
	}
	protected void setDir(Dir dir){
		this.dir= dir;
	}
	protected Dir getDir(){
		return dir;
	}
	protected void setUser(User user){
		this.user= user;
	}
	protected User getUser(){
		return user;
	}
	protected File getFile(String path){
		Manager m = Manager.getInstance();
		return m.getBase().lookup(getUser(), path);
	}

	protected void populate() throws UserAlreadyExistsException, UsernameContainsCharsException, InvalidUsernameException{
		Manager m = Manager.getInstance();
		user = new User(m,"litosbitos" , "Ana");
		userNoPermission = new User(m, "raquel1234", "Raquel Maria");
		new User(m, "userExpirado", "Expira");
		long token = new Session(m,"litosbitos", "litosbitos").getToken();
		tokenRoot = new Session(m,"root", "***").getToken();
		tokenExp = new Session(m,"userExpirado", "userExpirado").getToken();
		Session session = new Session(m,"raquel1234", "raquel1234");
		tokenNoPermission = session.getToken();
		
		setToken(token);
		Session s = m.getSessionbyToken(token);
		
		setUser(s.getCurrentUser());
		setDir(s.getWorkingDir());
		
		new Dir("directorio", s.getCurrentUser(), s.getWorkingDir());
		new PlainFile("ficheiro1",  s.getCurrentUser(), s.getWorkingDir(), "hellooo");
		app = new App("app", s.getCurrentUser(), s.getWorkingDir(), "pt.tecnico.drivedb.presentation.Hello.greet");
		new App("app2", s.getCurrentUser(), s.getWorkingDir(), "pt.tecnico.drivedb.presentation.Hello.getNumberOfStrings");
		new App("app3", s.getCurrentUser(), s.getWorkingDir(), "pt.tecnico.drivedb.presentation.Hello.getNumber");
		new App("app4", s.getCurrentUser(), s.getWorkingDir(), "pt.tecnico.drivedb.presentation.Hello");
		new App("app5", s.getCurrentUser(), s.getWorkingDir());
		new PlainFile("executavel",s.getCurrentUser(),s.getWorkingDir(), "/home/litosbitos/app2 1 2 3");
		new PlainFile("exComplex",s.getCurrentUser(),s.getWorkingDir(), "/home/litosbitos/app2 1 2 3\n/home/litosbitos/app Margarida");
		new Link("linkToPlain", s.getCurrentUser(), s.getWorkingDir(), "/home/litosbitos/executavel");
		new Link("linkToApp", s.getCurrentUser(), s.getWorkingDir(), "/home/litosbitos/app");
		new Link("linkToApp3", s.getCurrentUser(), s.getWorkingDir(), "/home/litosbitos/app3");
		new Link("linkToFicheiro1", s.getCurrentUser(), s.getWorkingDir(), "/home/litosbitos/ficheiro1");
		new PlainFile("texto.pdf", s.getCurrentUser(), s.getWorkingDir(), "xpto");
		new PlainFile("texto.ola", s.getCurrentUser(), s.getWorkingDir(), "xpto");
		new Extension(".pdf", user, app);
		s.createOrAddVar("$HOME", "/home");
		s.createOrAddVar("$ABC", "/litosbitos/app");
		s.createOrAddVar("$WRONG", "/etc/xpto/");
        new Link("linkVar", s.getCurrentUser(), s.getWorkingDir(), "$HOME/litosbitos/app"); //$HOME = /home
        new Link("endVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$ABC"); //$ABC = /litosbitos/app
        new Link("wrongVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$WRONG"); //$WRONG = /etc/xpto/
        new Link("nonExistentVar", s.getCurrentUser(), s.getWorkingDir(), "/home/$NONEXISTENT");
	}
	 @Test
	    public void successLinkWithEnvVar() {
	    	final String fileName = "linkVar";
			final String [] data = {"Teste com mocks"};
			
	        new MockUp<Link>() {
	        	  @Mock
	        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "litosbitos/app"); }
	           };
			
			ExecuteFileService service = new ExecuteFileService(getToken(), fileName, data);
	        service.execute();
	    }
	    
	    @Test
	    public void successLinkWithEnvVarEndOfPath() {
	    	final String fileName = "endVar";
			final String [] data = {"Teste com mocks"};
			
	        new MockUp<Link>() {
	        	  @Mock
	        	  	File lookup(User current, String path) { return getFile("/home").lookup(current, "litosbitos/app"); }
	           };
			
			ExecuteFileService service = new ExecuteFileService(getToken(), fileName, data);
	        service.execute();
	    }
	    
	    @Test(expected = InvalidPathException.class)
	    public void invalidPathLinkWithEnvVar() {
	    	final String fileName = "wrongVar";
			final String [] data = {"Teste com mocks"};
			
	        new MockUp<Link>() {
	        	  @Mock
	        	  	File lookup(User current, String path) { throw new InvalidPathException(path); }
	           };
			
			ExecuteFileService service = new ExecuteFileService(getToken(), fileName, data);
	        service.execute();
	    }
	    
	    @Test(expected = EnvVarDoesNotExistException.class)
	    public void invalidPathLinkWithEnvVarNonExistent() {
	    	final String fileName = "nonExistentVar";
			final String [] data = {"Teste com mocks"};
			
	        new MockUp<Link>() {
	        	  @Mock
	        	  	File lookup(User current, String path) { throw new EnvVarDoesNotExistException(path); }
	           };
			
			ExecuteFileService service = new ExecuteFileService(getToken(), fileName, data);
	        service.execute();
	    }
	    
	@Test(expected = DirNotExecuteException.class)
	public void isNotTextFile()
	{
		final String filepath = "directorio";
		final String [] data = {"a", "b"};
		
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
		
	}
	@Test(expected =  InvalidAppContentException.class)
	public void PlainFileWithContentNotExecutable()
	{
		final String filepath = "ficheiro1";
		final String [] data = {"a", "b"};
		
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
	}
	@Test
	public void sucessExecuteApp() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException
	{
		final String filepath = "app";
		final String [] data = {"Margarida"};
		
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	
	@Test
	public void sucessExecuteAppWithPath() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException
	{
		final String filepath = "/home/litosbitos/app";
		final String [] data = {"Margarida"};
		
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	
	@Test(expected=ErrorExecutingPlainFileException.class)
	public void insucessExecuteApp()
	{
		//execute method that does not exist
		final String filepath = "app3";
		final String [] data = {"Margarida"};
		
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
	}
	@Test
	public void appWithOmmitedMethod()
	{
		//evokes method main
		final String filepath="app4";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	@Test(expected=NothingToExecuteException.class)
	public void emptyApp()
	{
		final String filepath="app5";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
	}
	@Test
	public void sucessExecuteSimplePlainFile()
	{
		final String filepath = "executavel";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	@Test
	public void sucessExecuteComplexPlainFile()
	{
		//plainFile with more than 1 method to execute
		final String filepath = "exComplex";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	@Test
	public void sucessLinkToApp()
	{
		final String filepath = "linkToApp";
		final String [] data = {"Margarida"};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	@Test(expected=ErrorExecutingPlainFileException.class)
	public void insucessLinkToApp()
	{
		//link to unexistent method
		final String filepath = "linkToApp3";
		final String [] data = {"Margarida"};
		
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
	}
	@Test
	public void sucessLinkToPlainFile()
	{
		final String filepath = "linkToPlain";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();

        assertTrue("This will succeed.", true);

	}
	@Test(expected =  InvalidAppContentException.class)
	public void insucessLinkToPlainFile()
	{
		final String filepath ="linkToFicheiro1";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String filepath = "EXPIRA";
		final String [] data = {};
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getTokenExp()).testExpiredSession();
		manager.getSessionbyToken(getTokenExp());
		ExecuteFileService service = new ExecuteFileService(getToken(), filepath, data);
        service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String filepath = "EXPIRA";
		final String [] data = {};
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getTokenRoot()).testExpiredRootSession();
		manager.getSessionbyToken(getTokenRoot());
		ExecuteFileService service = new ExecuteFileService(getTokenRoot(), filepath, data);
        service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String filepath =  "invalid";
		final String [] data = {};
		ExecuteFileService service = new ExecuteFileService(1, filepath, data);
        service.execute();
	}
	
	@Test(expected = ExtensionHasNotAppAssociationException.class)
    public void invalidExtensionHasNotAppAssociation() throws ExtensionHasNotAppAssociationException {
		final String nonexisting = "texto.ola";
		final String[] args = {};
	        new MockUp<ExecuteFileService>() {
		  @Mock
		  void dispatch() throws DriveDBApplicationException, ExtensionHasNotAppAssociationException {
		    throw new ExtensionHasNotAppAssociationException(nonexisting); }
		};
		new ExecuteFileService(token, nonexisting,args).execute();
    }
	
	@Test(expected = FileDoesNotExistException.class)
    public void invalidNonExistentFile() throws FileDoesNotExistException {
		final String nonexisting = "xpto.pdf";
		final String[] args = {};
	        new MockUp<ExecuteFileService>() {
		  @Mock
		  void dispatch() throws DriveDBApplicationException, ExtensionHasNotAppAssociationException {
		    throw new FileDoesNotExistException(nonexisting); }
		};
        new ExecuteFileService(token, nonexisting,args).execute();
    }
	@Test(expected = UserDoesNotHavePermissionException.class)
    public void noPermissionExecutingFileWithExtension() throws FileDoesNotExistException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
		final String[] args = {};
		final String[] argsToApp = {"/home/litosbitos/texto.pdf"};
		final String file = "texto.pdf";
        new MockUp<ExecuteFileService>() {
        	@Mock
        	void dispatch() throws DriveDBApplicationException, ExtensionHasNotAppAssociationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException {
        		app.execute(userNoPermission, argsToApp);
        	}
        };
        new ExecuteFileService(tokenNoPermission, file,args).execute();
	}
	
	@Test
    public void sucessExecutingFileWithExtension() throws FileDoesNotExistException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
		final String[] args = {};
		final String[] argsToApp = {"/home/litosbitos/texto.pdf"};
		final String file = "texto.pdf";
        new MockUp<ExecuteFileService>() {
        	@Mock
        	void dispatch() throws DriveDBApplicationException, ExtensionHasNotAppAssociationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException {
        		app.execute(user, argsToApp);
        	}
        };
        new ExecuteFileService(token, file,args).execute();
	}
	
}
