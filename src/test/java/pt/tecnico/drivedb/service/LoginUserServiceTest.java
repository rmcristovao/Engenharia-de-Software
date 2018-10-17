package pt.tecnico.drivedb.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pt.tecnico.drivedb.domain.*;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.InvalidPasswordException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.PasswordEmptyException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.exception.UsernameEmptyException;
import pt.tecnico.drivedb.exception.UsernameNotFoundException;

public class LoginUserServiceTest extends AbstractServiceTest{

	@Override
	public void populate() throws UsernameContainsCharsException, InvalidUsernameException {
		
		Manager manager = Manager.getInstance();
		new User(manager, "brunolampreia", "brunolampreia");
		new User(manager, "margaridamorais", "margaridamorais");
		new User(manager, "raquelcristovao", "raquelcristovao");
		new User(manager,"bruno","bruno");
	}

	@Test 
	public void invalidToken() {
		LoginUserService service = new LoginUserService("brunolampreia","brunolampreia");
		service.execute();
		long token = service.result();
		assertFalse("Invalid token", token <=0);

	}

	@Test
	public void successLoginService() {
		LoginUserService service = new LoginUserService("raquelcristovao","raquelcristovao");
		service.execute();
		long token = service.result();
		Manager manager = Manager.getInstance();
		User user = manager.getSessionbyToken(token).getCurrentUser();
		assertEquals("raquelcristovao", user.getUsername());
		assertEquals("raquelcristovao", user.getPassword());
	}
	
	@Test
	public void successLoginTwice() {
		LoginUserService service = new LoginUserService("raquelcristovao","raquelcristovao");	
		service.execute();
		long token1 = service.result();
		User user = Manager.getInstance().getSessionbyToken(token1).getCurrentUser();
		service.execute();
		long token2 = service.result();
		User user2 = Manager.getInstance().getSessionbyToken(token2).getCurrentUser();
		assertEquals(user.getUsername(), user2.getUsername());
		assertNotEquals(token1, token2);	
	}

	@Test 
	public void successGuestSession(){
		LoginUserService service = new LoginUserService("nobody","");
		service.execute();
		long token = service.result();
		Manager manager = Manager.getInstance();
		//User user = Manager.getInstance().getSessionbyToken(token).getCurrentUser();
		assertFalse(manager.getSessionbyToken(token).invalid());
		

	}
	
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredSession() {
		LoginUserService service = new LoginUserService("margaridamorais", "margaridamorais");
		service.execute();
		long token = service.result();
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(token).testExpiredSession();
		manager.getSessionbyToken(token);
	}
	
	@Test(expected = ExpiredSessionException.class)
	public void expiredRootSession(){
		LoginUserService service = new LoginUserService("root", "***");
		service.execute();
		long token = service.result();
		Manager manager = Manager.getInstance();
		manager.getSessionbyToken(token).testExpiredRootSession();
		manager.getSessionbyToken(token);		
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void loginUnknownUser() {
		LoginUserService service = new LoginUserService("margarida", "margaridamorais");
		service.execute();
	}

	@Test(expected = InvalidPasswordException.class)
	public void loginUserWithInvalidPassword() {
		LoginUserService service = new LoginUserService("brunolampreia", "bruno");
		service.execute();
	}
	
	@Test(expected = UsernameEmptyException.class)
	public void userEmpty() {
		LoginUserService service = new LoginUserService("", "margaridamorais");
		service.execute();
	}
	
	@Test(expected = InvalidPasswordException.class)
	public void passwordEmpty() {
		LoginUserService service = new LoginUserService("raquelcristovao", "");
		service.execute();
	}
	
	@Test(expected = InvalidPasswordException.class)
	public void invalidPassword() {
		LoginUserService service = new LoginUserService("bruno", "bruno");
		service.execute();
	}
	
}
