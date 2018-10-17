package pt.tecnico.drivedb.service;

import static org.junit.Assert.assertEquals;

import java.util.List;
import org.junit.Test;

import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.Session;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.domain.EnvironmentVar;
import pt.tecnico.drivedb.exception.ExpiredSessionException;
import pt.tecnico.drivedb.exception.InvalidTokenException;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;
import pt.tecnico.drivedb.service.dto.EnvironmentVarDto;

public class AddVariableServiceTest extends AbstractServiceTest{
	
	private long token;
	private long token1;
	private long tokenr;
	private long tokenRT;
	private long tokenUS;
	
	
	protected void setToken(long token){
		this.token = token;
	}
	
	protected long getToken(){
		return token;
	}
	
	protected void setToken1(long token1){
		this.token1 = token1;
	}
	
	protected long getToken1(){
		return token1;
	}
	
	protected void setTokenr(long tokenr){
		this.tokenr = tokenr;
	}
	
	protected long getTokenr(){
		return tokenr;
	}
	
	protected void setTokenRT(long tokenRT){
		this.tokenRT= tokenRT;
	}
	
	protected long getTokenRT(){
		return tokenRT;
	}
	
	protected void setTokenUS(long tokenUS){
		this.tokenUS = tokenUS;
	}
	
	protected long getTokenUS(){
		return tokenUS;
	}
	
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException{		
		Manager m = Manager.getInstance();
		new User(m, "lolo", "Leonor" ,"leonor1234");
		new User(m, "raquel", "Raquel" ,"Raquel1234");
		new User(m, "bruno", "Bruno", "Bruno1234");
		new User(m,"margarida", "Margarida", "Margarida1234");
    	
    	long token = new Session(m, "lolo", "leonor1234").getToken();
    	long token1 = new Session(m, "raquel", "Raquel1234").getToken();
    	long tokenr = new Session(m, "root", "***").getToken();
    	long tokenRT = new Session(m, "bruno", "Bruno1234").getToken();
    	long tokenUS = new Session(m, "margarida", "Margarida1234").getToken();
    	
    	setToken(token);
    	setToken1(token1);
		setTokenr(tokenRT);
		setTokenRT(tokenr);
		setTokenUS(tokenUS);
    	
		Session s = m.getSessionbyToken(token);
		Session u = m.getSessionbyToken(token1);
		Session r = m.getSessionbyToken(tokenr);
		
		Session rt = m.getSessionbyToken(tokenr);
		rt.testExpiredRootSession();
		
		Session us = m.getSessionbyToken(tokenUS);
		us.testExpiredSession();
		
		new EnvironmentVar(s, "PROJ", "/home/dir"); 
		new EnvironmentVar(u,"Eclipse","/home/es/ide");
		new EnvironmentVar(r, "", "");
	
	}
	
	/************CASOS DE SUCESSO************/	
	
	@Test
	/*Can add a new environment variable*/
	public void CanAddNewVar() {
		
		String name = "projeto";
		String value= "/home/es/trabalhos";
		
		AddVariableService service = new AddVariableService(getTokenr(), name, value);
		service.execute();
		List<EnvironmentVarDto> content = service.result();

		assertEquals(1, content.size());
		assertEquals(name, content.get(0).getName());
		assertEquals(value, content.get(0).getValue() );
	}
	
	@Test
	/*Can add a new environment variable with the same name */
	public void CanAddNewEnvVar(){
		
		String name = "Eclipse";
		String value = "/home/dir/right";
		
		AddVariableService service = new AddVariableService(getToken(), name, value);
		service.execute();
		List<EnvironmentVarDto> content = service.result();
		
		assertEquals(2,content.size() );
		assertEquals("PROJ",content.get(1).getName() );
		assertEquals("/home/dir",content.get(1).getValue() );
		assertEquals(name,content.get(0).getName() );
		assertEquals(value,content.get(0).getValue() );
	}

	@Test
	/*Can replace the environment variable with the same name */
	public void CanReplaceEnvVar(){
	
		String name = "Eclipse";
		String value = "/home/IDEs";
		
		AddVariableService service = new AddVariableService(getToken1(), name, value);
		service.execute();
		List<EnvironmentVarDto> content = service.result();
		
		assertEquals(1,content.size() );
		assertEquals(name, content.get(0).getName());
		assertEquals(value, content.get(0).getValue());
	}
	
	
	@Test
	public void AddEnvVar(){
		
		String name = "monkey";
		String value = "monkeybebe";
		
		AddVariableService service = new AddVariableService(getToken1(), name, value);
		service.execute();
		List<EnvironmentVarDto> content = service.result();
		
		assertEquals(2,content.size() );
		assertEquals("Eclipse", content.get(0).getName());
		assertEquals("/home/es/ide", content.get(0).getValue());
		assertEquals(name,content.get(1).getName() );
		assertEquals(value,content.get(1).getValue() );
	}
	
	
	@Test
	/*can list an empety list */
	public void CanListEmpetyList(){
		
		AddVariableService service = new AddVariableService(getTokenr());
		service.execute();
		List<EnvironmentVarDto> content = service.result();

		assertEquals(0,content.size());
	}
	

	
	/**********************CASOS DE INSUCESSO**********************/
	
	/*Expired Session for root*/
	@Test(expected = ExpiredSessionException.class)
	public void SessionExpiredForRoot(){
		
		String name = "tabem";
		String value = "jada";
		AddVariableService service = new AddVariableService(getTokenRT(), name, value);
		service.execute();
	}
	
	/*Expired Session for an user*/
	@Test(expected = ExpiredSessionException.class)
	public void SessionExpiredForUser(){
		
		String name = "ola";
		String value = "olaeadeus";
		
		AddVariableService service = new AddVariableService(getTokenUS(), name, value);
		service.execute();
	}
	
	@Test(expected = InvalidTokenException.class)
	public void invalidToken() {
		final String name =  "invalid";
		final String value =  "invalid";
		AddVariableService service = new AddVariableService(1, name, value);
		service.execute();
	}
	
}