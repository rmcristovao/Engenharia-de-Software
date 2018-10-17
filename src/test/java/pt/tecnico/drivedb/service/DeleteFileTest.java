package pt.tecnico.drivedb.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.net.ssl.SSLSession;

import org.junit.Test;

import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.CanNotRemoveBaseDirException;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.FileDoesNotExistException;
import pt.tecnico.drivedb.exception.InvalidFileNameException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.exception.UsernameNotFoundException;

public class DeleteFileTest extends AbstractServiceTest{

	private long ownerToken,notOwnerToken,rootToken, expToken;
	
	private long getExpToken(){
		return expToken;
	}
	
	private long getOwnerToken(){
		return ownerToken;
	}
	
	private long getNotOwnerToken(){
		return notOwnerToken;
	}
	
	private long getRootToken(){
		return rootToken;
	}
	
	private File getFile(long token, String name){
		for(File f : DriveDBService.getManager().getSessionbyToken(token)
				.getCurrentUser().getFilesSet()){
			if(f.getName().equals(name)){
				return f;
			}
		}
		return null;
	}
	
	private Manager getManager(){
		return Manager.getInstance();
	}
	
	@Override
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
		Manager m = getManager();
		new User(m, "anasilva2", "Ana");
		new User(m, "otherUser", "user2");
        new User(m, "userExpirado", "Expira");
		User user = m.getUserByUsername("anasilva2");
		User user2 = m.getUserByUsername("otherUser");
		User root = m.getUserByUsername("root");
		User exp = m.getUserByUsername("userExpirado");
		
		ownerToken = new Session(m, user.getUsername(), user.getPassword()).getToken();
		notOwnerToken = new Session(m, user2.getUsername(), user2.getPassword()).getToken();
		rootToken = new Session(m, root.getUsername(), root.getPassword()).getToken();
		expToken = new Session(m, exp.getUsername(), exp.getPassword()).getToken();
		Session session = m.getSessionbyToken(getOwnerToken());
		new PlainFile("Plain1", session.getCurrentUser(), session.getWorkingDir(), null);
		new Dir("Folder1", session.getCurrentUser(), session.getWorkingDir());
		Dir folder2 = new Dir("Folder2", session.getCurrentUser(), session.getWorkingDir());
		new App("App1", session.getCurrentUser(), session.getWorkingDir(), null);
		new PlainFile("Plain2", session.getCurrentUser(), folder2, null);
	}
	
	@Test
	public void sucessWithPlainFileFile(){
		
		final String name = "Plain1";
		DeleteFileService service = new DeleteFileService(getOwnerToken(),name);
		service.execute();
		
		File f = getFile(getOwnerToken(),name);
		assertNull("File was not deleted",f);
		
	}

	@Test
	public void sucessWithDirectory(){
		
		final String name = "Folder1";
		DeleteFileService service = new DeleteFileService(getOwnerToken(),name);
		service.execute();
		
		assertNull("File was not deleted",getFile(getOwnerToken(),"Folder1"));
	}

	@Test(expected = FileDoesNotExistException.class)
	public void sucessWithRootDeletingFile(){
		
		Manager m = Manager.getInstance();
		User root = m.getUserByUsername("root");
		rootToken = new Session(m, root.getUsername(), root.getPassword()).getToken();
		
		final String fileName = "Plain2";
		
		DeleteFileService service = new DeleteFileService(getRootToken(),fileName);
		service.execute();
		
		File afterDeletingFile = getFile(getRootToken(),fileName);
		assertNull("File was not deleted by root",afterDeletingFile);
		
	}
	
	@Test(expected = FileDoesNotExistException.class)
	public void invalidDeleteFileWithFileNotFound(){
		
		DeleteFileService service = new DeleteFileService(getOwnerToken(),"ABCD");
		service.execute();
	}
	
	@Test(expected = UserDoesNotHavePermissionException.class)
	public void userDoesNotHavePermissionToDeleteFile(){
		
		getManager().getSessionbyToken(getNotOwnerToken()).changeDir("/home/anasilva2");
		DeleteFileService service = new DeleteFileService(getNotOwnerToken(),"Folder1");
		service.execute();
	}

	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String fileName = "EXPIRA";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getExpToken()).testExpiredSession();
		manager.getSessionbyToken(getExpToken());
		DeleteFileService service = new DeleteFileService(getExpToken(),fileName);
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String fileName = "ahfb";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getRootToken()).testExpiredRootSession();
		manager.getSessionbyToken(getRootToken());
		DeleteFileService service = new DeleteFileService(getRootToken(),fileName);
		service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String fileName =  "invalid";
		DeleteFileService service = new DeleteFileService(1,fileName);
		service.execute();
	}
	
}
