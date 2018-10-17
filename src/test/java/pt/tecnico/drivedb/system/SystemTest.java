package pt.tecnico.drivedb.system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.drivedb.service.AbstractServiceTest ;
import pt.tecnico.drivedb.presentation.*;

public class SystemTest extends AbstractServiceTest {

    private DriveDBShell sh;

    protected void populate() {
        sh = new DriveDBShell();
    }

    @Test
    public void success() {
        new Import(sh).execute(new String[] { "other.xml" } );
        new Login(sh).execute(new String[]{"brunompl", "brunompl"});
    	new List(sh).execute(new String[]{"/home/brunompl"});
        new Execute(sh).execute(new String[]{"/home/brunompl/app", "Bruno"});
        new Key(sh).execute(new String[]{});
        new Login(sh).execute(new String[]{"margaridamorais", "margaridamorais"});
        new Key(sh).execute(new String[]{});
    	new Write(sh).execute(new String[]{"/home/margaridamorais/profile", "Ola, continuo a ser a margarida"});
    	new ChangeDirectory(sh).execute(new String[]{});
        new ChangeDirectory(sh).execute(new String[]{"/home/margaridamorais/dir"});
        new Login(sh).execute(new String[]{"nobody"});
        new Key(sh).execute(new String[]{"brunompl"});
    	new Environment(sh).execute(new String[]{"teste","/home/hdgf"});
    	new Environment(sh).execute(new String[]{"text","/home/bruno"});
    	new Environment(sh).execute(new String[]{});
    	new Environment(sh).execute(new String[]{"teste","/home/diogo"});
    	new Environment(sh).execute(new String[]{});
    	new Environment(sh).execute(new String[]{"text"});
    }
}