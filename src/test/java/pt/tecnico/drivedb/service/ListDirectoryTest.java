package pt.tecnico.drivedb.service;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import pt.tecnico.drivedb.domain.Dir;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.PlainFile;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UserDoesNotHavePermissionException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.service.dto.FileDto;
import java.util.List;

public class ListDirectoryTest extends AbstractServiceTest{
	long tokenR;
	long tokenL;
	long tokenB;
	long tokenExp;
	
	protected long getTokenExp(){
		return tokenExp;
	}
	
	protected long getTokenR(){
		return tokenR;
	}
	
	protected long getTokenL(){
		return tokenL;
	}
	
	protected long getTokenB(){
		return tokenB;
	}
	
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException{
		Manager m = Manager.getInstance();
		new User(m, "raqs","amaisrude", "Raquel","rwxd----");
		new User(m, "lolo","estanaosei", "Leonor","rwxd----");
		new User(m, "userExpirado", "Expira");
		
		tokenR = new Session(m, "raqs", "amaisrude").getToken();
		tokenL = new Session(m, "lolo", "estanaosei").getToken();
		tokenB = new Session(m, "root", "***").getToken();
		tokenExp = new Session(m, "userExpirado", "userExpirado").getToken();
		
		Session r = m.getSessionbyToken(tokenR);
		Session l = m.getSessionbyToken(tokenL);
		new Dir("test", l.getCurrentUser(), l.getWorkingDir());

		new PlainFile("text", l.getCurrentUser(), l.getWorkingDir(), null);
		new PlainFile("abc", l.getCurrentUser(), l.getWorkingDir(), null);
		new PlainFile("cool", l.getCurrentUser(), l.getWorkingDir(), null);
		
		new Link("zlink", l.getCurrentUser(), l.getWorkingDir(), "/home/root");

		Session ro = m.getSessionbyToken(tokenB);
		ro.changeDir("/../");
	}
	
	@Test
	public void successEmptyDir(){
		ListDirectoryService service = new ListDirectoryService(getTokenR());
		service.execute();
		List <FileDto> ls = service.result();
				
		assertEquals("List with 2 elements", 2 , ls.size());
		
		assertEquals("First element is a dir", "Dir", ls.get(0).getType());
		assertEquals("First element has permission", "rwxd----", ls.get(0).getPermission());
		assertEquals("First element has size 2 ", 2 , ls.get(0).getSize());
		assertEquals("First element id is 4", 4 , ls.get(0).getId());
		assertEquals("First element name owner's is raqs", "raqs" , ls.get(0).getOwner());
		assertEquals("First element name is .", "." , ls.get(0).getName());
		
		assertEquals("Second element is a dir", "Dir", ls.get(1).getType());
		assertEquals("Second element has permission", "rwxdr-x-", ls.get(1).getPermission());
		assertEquals("Second element has size 7 ", 7 , ls.get(1).getSize());
		assertEquals("Second element id is 1", 1 , ls.get(1).getId());
		assertEquals("Second element name owner's is root", "root" , ls.get(1).getOwner());
		assertEquals("Second element name is ..", ".." , ls.get(1).getName());
	}
	
	@Test
	public void successBaseDir(){
		Manager m = Manager.getInstance();
		User root = m.getUserByUsername("root");
		tokenB = new Session(m, root.getUsername(), root.getPassword()).getToken();
		Session ro = m.getSessionbyToken(tokenB);
		ro.changeDir("/../");
		ListDirectoryService service = new ListDirectoryService(getTokenB());
		service.execute();
		List <FileDto> ls1 = service.result();
		
		assertEquals("List with 3 element", 3 , ls1.size());
		
		assertEquals("First element is a dir", "Dir", ls1.get(0).getType());
		assertEquals("First element has permission", "rwxdr-x-", ls1.get(0).getPermission());
		assertEquals("First element has size 3 ", 3 , ls1.get(0).getSize());
		assertEquals("First element id is 0", 0 , ls1.get(0).getId());
		assertEquals("First element name owner's is root", "root" , ls1.get(0).getOwner());
		assertEquals("First element name is .", "." , ls1.get(0).getName());
		
		assertEquals("Second element is a dir", "Dir", ls1.get(2).getType());
		assertEquals("Second element has permission", "rwxdr-x-", ls1.get(2).getPermission());
		assertEquals("Second element has size 7 ", 7 , ls1.get(2).getSize());
		assertEquals("Second element id is 1", 1 , ls1.get(2).getId());
		assertEquals("Second element name owner's is root", "root" , ls1.get(2).getOwner());
		assertEquals("Second element name is home", "home" , ls1.get(2).getName());
		
		assertEquals("Third element is a dir", "Dir", ls1.get(1).getType());
		assertEquals("Third element has permission", "rwxdr-x-", ls1.get(1).getPermission());
		assertEquals("Third element has size 3 ", 3 , ls1.get(1).getSize());
		assertEquals("Third element id is 0", 0 , ls1.get(1).getId());
		assertEquals("Third element name owner's is root", "root" , ls1.get(1).getOwner());
		assertEquals("Third element name is ..", ".." , ls1.get(1).getName());

	}
	
	@Test
	public void success(){
		ListDirectoryService service = new ListDirectoryService(getTokenL());
		service.execute();
		List <FileDto> ls = service.result();
		
		assertEquals("List with 7 elements", 7 , ls.size());
		
		assertEquals("First element is a dir", "Dir", ls.get(0).getType());
		assertEquals("First element has permission", "rwxd----", ls.get(0).getPermission());
		assertEquals("First element has size 7 ", 7 , ls.get(0).getSize());
		assertEquals("First element id is 5", 5 , ls.get(0).getId());
		assertEquals("First element name owner's is lolo", "lolo" , ls.get(0).getOwner());
		assertEquals("First element name is .", "." , ls.get(0).getName());
		
		assertEquals("Second element is a dir", "Dir", ls.get(1).getType());
		assertEquals("Second element has permission", "rwxdr-x-", ls.get(1).getPermission());
		assertEquals("Second element has size 7", 7 , ls.get(1).getSize());
		assertEquals("Second element id is 1", 1 , ls.get(1).getId());
		assertEquals("Second element name owner's is root", "root" , ls.get(1).getOwner());
		assertEquals("Second element name is ..", ".." , ls.get(1).getName());
		
		assertEquals("Third element is a plainfile", "Plain", ls.get(2).getType());
		assertEquals("Third element has permission", "rwxd----", ls.get(2).getPermission());
		assertEquals("Third element has size 0 ", 0 , ls.get(2).getSize());
		assertEquals("Third element id is 9", 9 , ls.get(2).getId());
		assertEquals("Third element name owner's is lolo", "lolo" , ls.get(2).getOwner());
		assertEquals("Third element name is abc", "abc" , ls.get(2).getName());
		
		assertEquals("Fourth element is a plainfile", "Plain", ls.get(3).getType());
		assertEquals("Fourth element has permission", "rwxd----", ls.get(3).getPermission());
		assertEquals("Fourth element has size 0 ", 0 , ls.get(3).getSize());
		assertEquals("Fourth element id is 10", 10, ls.get(3).getId());
		assertEquals("Fourth element name owner's is lolo", "lolo" , ls.get(3).getOwner());
		assertEquals("Fourth element name is cool", "cool" , ls.get(3).getName());
		
		assertEquals("Fifth element is a dir", "Dir", ls.get(4).getType());
		assertEquals("Fifth element has permission", "rwxd----", ls.get(4).getPermission());
		assertEquals("Fifth element has size 2 ", 2 , ls.get(4).getSize());
		assertEquals("Fifth element id is 7", 7 , ls.get(4).getId());
		assertEquals("Fifth element name owner's is lolo", "lolo" , ls.get(4).getOwner());
		assertEquals("Fifth element name is test", "test" , ls.get(4).getName());
		
		assertEquals("Sixth element is a plainfile", "Plain", ls.get(5).getType());
		assertEquals("Sixth element has permission", "rwxd----", ls.get(5).getPermission());
		assertEquals("Sixth element has size 0 ", 0 , ls.get(5).getSize());
		assertEquals("Sixth element id is 8", 8 , ls.get(5).getId());
		assertEquals("Sixth element name owner's is lolo", "lolo" , ls.get(5).getOwner());
		assertEquals("Sixth element name is text", "text" , ls.get(5).getName());
		
		assertEquals("Sixth element is a plainfile", "Link", ls.get(6).getType());
		assertEquals("Sixth element has permission", "rwxd----", ls.get(6).getPermission());
		assertEquals("Sixth element has size 10 ", 10 , ls.get(6).getSize());
		assertEquals("Sixth element id is 11", 11 , ls.get(6).getId());
		assertEquals("Sixth element name owner's is lolo", "lolo" , ls.get(6).getOwner());
		assertEquals("Sixth element name is zlink", "zlink" , ls.get(6).getName());
		assertEquals("Sixth element name is /home/root", "/home/root" , ls.get(6).getContent());
	}
	
	@Test
	public void successWithPath(){
		final String path = "../../home/lolo/.";
		ListDirectoryService service = new ListDirectoryService(getTokenL(), path);
		service.execute();
		List <FileDto> ls = service.result();
		
		assertEquals("List with 7 elements", 7 , ls.size());
		
		assertEquals("First element is a dir", "Dir", ls.get(0).getType());
		assertEquals("First element has permission", "rwxd----", ls.get(0).getPermission());
		assertEquals("First element has size 7 ", 7 , ls.get(0).getSize());
		assertEquals("First element id is 5", 5 , ls.get(0).getId());
		assertEquals("First element name owner's is lolo", "lolo" , ls.get(0).getOwner());
		assertEquals("First element name is .", "." , ls.get(0).getName());
		assertEquals("First element name is 2016-05-13T13:18:15.395+01:00", ls.get(0).getDate(), ls.get(0).getDate());
		
		assertEquals("Second element is a dir", "Dir", ls.get(1).getType());
		assertEquals("Second element has permission", "rwxdr-x-", ls.get(1).getPermission());
		assertEquals("Second element has size 7", 7 , ls.get(1).getSize());
		assertEquals("Second element id is 1", 1 , ls.get(1).getId());
		assertEquals("Second element name owner's is root", "root" , ls.get(1).getOwner());
		assertEquals("Second element name is ..", ".." , ls.get(1).getName());
		
		assertEquals("Third element is a plainfile", "Plain", ls.get(2).getType());
		assertEquals("Third element has permission", "rwxd----", ls.get(2).getPermission());
		assertEquals("Third element has size 0 ", 0 , ls.get(2).getSize());
		assertEquals("Third element id is 9", 9 , ls.get(2).getId());
		assertEquals("Third element name owner's is lolo", "lolo" , ls.get(2).getOwner());
		assertEquals("Third element name is abc", "abc" , ls.get(2).getName());
		
		assertEquals("Fourth element is a plainfile", "Plain", ls.get(3).getType());
		assertEquals("Fourth element has permission", "rwxd----", ls.get(3).getPermission());
		assertEquals("Fourth element has size 0 ", 0 , ls.get(3).getSize());
		assertEquals("Fourth element id is 10", 10, ls.get(3).getId());
		assertEquals("Fourth element name owner's is lolo", "lolo" , ls.get(3).getOwner());
		assertEquals("Fourth element name is cool", "cool" , ls.get(3).getName());
		
		assertEquals("Fifth element is a dir", "Dir", ls.get(4).getType());
		assertEquals("Fifth element has permission", "rwxd----", ls.get(4).getPermission());
		assertEquals("Fifth element has size 2 ", 2 , ls.get(4).getSize());
		assertEquals("Fifth element id is 7", 7 , ls.get(4).getId());
		assertEquals("Fifth element name owner's is lolo", "lolo" , ls.get(4).getOwner());
		assertEquals("Fifth element name is test", "test" , ls.get(4).getName());
		
		assertEquals("Sixth element is a plainfile", "Plain", ls.get(5).getType());
		assertEquals("Sixth element has permission", "rwxd----", ls.get(5).getPermission());
		assertEquals("Sixth element has size 0 ", 0 , ls.get(5).getSize());
		assertEquals("Sixth element id is 8", 8 , ls.get(5).getId());
		assertEquals("Sixth element name owner's is lolo", "lolo" , ls.get(5).getOwner());
		assertEquals("Sixth element name is text", "text" , ls.get(5).getName());
		
		assertEquals("Sixth element is a plainfile", "Link", ls.get(6).getType());
		assertEquals("Sixth element has permission", "rwxd----", ls.get(6).getPermission());
		assertEquals("Sixth element has size 10 ", 10 , ls.get(6).getSize());
		assertEquals("Sixth element id is 11", 11 , ls.get(6).getId());
		assertEquals("Sixth element name owner's is lolo", "lolo" , ls.get(6).getOwner());
		assertEquals("Sixth element name is zlink", "zlink" , ls.get(6).getName());
		assertEquals("Sixth element name is /home/root", "/home/root" , ls.get(6).getContent());
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		final String path = "EXPIRA";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getTokenExp()).testExpiredSession();
		manager.getSessionbyToken(getTokenExp());
		ListDirectoryService service = new ListDirectoryService(getTokenExp(), path);
		service.execute();
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession() {
		final String path = "EXPIRA";
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(getTokenB()).testExpiredRootSession();
		manager.getSessionbyToken(getTokenB());
		ListDirectoryService service = new ListDirectoryService(getTokenB(), path);
		service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String path = "invalid";
		ListDirectoryService service = new ListDirectoryService(1, path);
		service.execute();
	}
	
	@Test(expected = UserDoesNotHavePermissionException.class)
	public void permissionDenied() {
		final String path = "/home/raqs";
		ListDirectoryService service = new ListDirectoryService(getTokenL(), path);
		service.execute();
	}
}