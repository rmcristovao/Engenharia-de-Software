package pt.tecnico.drivedb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.InvalidFileNameException;
import pt.tecnico.drivedb.exception.InvalidFileTypeException;
import pt.tecnico.drivedb.exception.InvalidLengthPathException;
import pt.tecnico.drivedb.exception.InvalidLinkContentException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.LinkDoesNotHaveContentException;
import pt.tecnico.drivedb.exception.DirHaveContentException;
import pt.tecnico.drivedb.exception.FileAlreadyExistException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

public class CreateFileTest extends AbstractServiceTest{

	static final Logger log = LogManager.getRootLogger();
	
	private long token,notOwnerToken,rootToken,expToken;
	
	Dir dir;

	private File getFile(long token, String name){
		for(File f : DriveDBService.getManager().getSessionbyToken(token).getCurrentUser().getFilesSet()){
			if(f.getName().equals(name))
				return f;
		}
		return null;
	}

	private long getToken(){
		return token;
	}
	
	private long getExpToken(){
		return expToken;
	}
	
	private long getNotOwnerToken(){
		return notOwnerToken;
	}
	
	private long getRootToken(){
		return rootToken;
	}
	
	protected void setWorkingDir(Dir dir)
	{
		this.dir = dir;
	}
	
	private Manager getManager(){
		return Manager.getInstance();
	}
	
	@Override
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
		Manager m = Manager.getInstance();
		new User(m, "anasilva2", "Ana");
		new User(m, "otherUser", "user2");
        new User(m, "userExpirado", "Expira");
		User user = m.getUserByUsername("anasilva2");
		User user2 = m.getUserByUsername("otherUser");
		User root = m.getUserByUsername("root");
		User exp = m.getUserByUsername("userExpirado");

		token = new Session(m, user.getUsername(), user.getPassword()).getToken();
		notOwnerToken = new Session(m, user2.getUsername(), user2.getPassword()).getToken();
		rootToken = new Session(m, root.getUsername(), root.getPassword()).getToken();
		expToken = new Session(m, exp.getUsername(), exp.getPassword()).getToken();
		Session session = m.getSessionbyToken(getToken());
		new PlainFile("Fich1", session.getCurrentUser(), session.getWorkingDir(), null);
	}
	
	@Test
	public void sucessWithPlainFileFile(){
		
		final String name = "Test";
		final String type = "Plain";
		CreateFileService service = new CreateFileService(getToken(),name,type);
		service.execute();
		
		File f = getFile(getToken(),name);
        assertNotNull("PlainFile was not created", f);
        assertEquals("Invalid PlainFile File",type,f.getType());
	}
	
	@Test
	public void sucessWithDirFile(){
		
		final String name = "Test";
		final String type = "Dir";
		CreateFileService service = new CreateFileService(getToken(),name,type);
		service.execute();
		
		File f = getFile(getToken(),name);
        assertNotNull("Directory was not created", f);
        assertEquals("Invalid Directory File",type,f.getType());

	}
	
	@Test
	public void sucessWithAppFile(){
		
		final String name = "Test";
		final String type = "App";
		CreateFileService service = new CreateFileService(getToken(),name,type);
		service.execute();
		
		File f = getFile(getToken(),name);
        assertNotNull("App was not created", f);
        assertEquals("Invalid App File",type,f.getType());

	}
	
	@Test
	public void sucessWithLinkFile(){
		
		final String name = "TestLink";
		final String type = "Link";
		final String content = "/home/anasilva2/Fich1";
		CreateFileService service = new CreateFileService(getToken(),name,type,content);
		service.execute();
		
		File f = getFile(getToken(),name);
        assertNotNull("Link was not created", f);
        assertEquals("Invalid Link File",type,f.getType());

	}
	
	
	@Test
	public void sucessWithRootCreatingFileInAnyDir(){
		
		final String name = "Test";
		CreateFileService service = new CreateFileService(getRootToken(),name,"App");
		service.execute();
		
		File file = getFile(getRootToken(),name);
		assertNotNull("File was not created by root",file);
		
	}
	
	@Test(expected = InvalidLinkContentException.class)
	public void invalidLinkContent(){
		CreateFileService service = new CreateFileService(getToken(),"Test","Link","ABCDE");
		service.execute();
	}
	
	@Test(expected = LinkDoesNotHaveContentException.class)
	public void invalidLinkCreationWithoutContent(){
		
		CreateFileService service = new CreateFileService(getToken(),"Test","Link");
		service.execute();
		
	}
	
	@Test(expected = DirHaveContentException.class)
	public void invalidCreateDirWithContent(){
		
		CreateFileService service = new CreateFileService(getToken(),"Test","Dir","ABCD");
		service.execute();
		
	}
	
	@Test(expected = FileAlreadyExistException.class)
	public void invalidFileCreationWithFileThatAlreadyExists(){	
		CreateFileService service = new CreateFileService(getToken(),"Fich1","Plain");
		service.execute();
	}
	
	
	@Test(expected = InvalidFileNameException.class)
	public void invalidFileCreationWithInvalidFileName(){	
		CreateFileService service = new CreateFileService(getToken(),"test/","Plain");
		service.execute();
	}
	
	@Test(expected = InvalidFileTypeException.class)
	public void invalidFileCreationWithInvalidFileType(){
		
		CreateFileService service = new CreateFileService(getToken(),"Test","ABC");
		service.execute();
	}
	
	@Test(expected = UserDoesNotHavePermissionException.class)
	public void userDoesNotHavePermissionToCreateFile(){
		getManager().getSessionbyToken(notOwnerToken).changeDir("/../");
		CreateFileService service = new CreateFileService(notOwnerToken,"Test","Dir", null);
		service.execute();
		
	}
	
	@Test(expected = InvalidLengthPathException.class)
	public void invalidLinkPathLengthException(){
		
		StringBuilder str = new StringBuilder("/home/anasilva2/");
		str.setLength(1026);
		final String content = str.toString();
		CreateFileService service = new CreateFileService(getToken(),"Test","Link",content);
		service.execute();
	}
	
	@Test(expected = InvalidLengthPathException.class)
	public void invalidPathLengthException(){
		
		StringBuilder str = new StringBuilder("test");
		str.setLength(1026);
		final String name = str.toString();
		CreateFileService service = new CreateFileService(getToken(),name,"Plain");
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String fileName = "fileTest";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getExpToken()).testExpiredSession();
		manager.getSessionbyToken(getExpToken());
		CreateFileService service = new CreateFileService(getExpToken(),fileName,"Plain");
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String fileName = "ahfb";
		final String type = "dir";

		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getRootToken()).testExpiredRootSession();
		manager.getSessionbyToken(getRootToken());
		CreateFileService service = new CreateFileService(getRootToken(),fileName,type);
		service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String fileName =  "invalid";
		final String type =  "invalid";
		CreateFileService service = new CreateFileService(1,fileName,type);
		service.execute();
	}
	
}
