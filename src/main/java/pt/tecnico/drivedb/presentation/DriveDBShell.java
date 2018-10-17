package pt.tecnico.drivedb.presentation;

import java.util.HashMap;

public class DriveDBShell extends Shell {
	public long token;
	public HashMap<Long, String> tokenList =  new HashMap<>();
	
	void setToken(long token){
		this.token = token;
	}
	
	long getToken(){
		return token;
	}
	
	void addToken(Long token, String username){
		tokenList.put(token, username);
	}
	
  public static void main(String[] args) throws Exception {
    DriveDBShell sh = new DriveDBShell();
    sh.execute();
  }

  public DriveDBShell() { // add commands here
    super("DriveDBApplication");
    new Write(this);
    new Key(this);
    new ChangeDirectory(this);
    new Login(this);
    new Import(this);
    new List(this);
    new Environment(this);

  }
}
