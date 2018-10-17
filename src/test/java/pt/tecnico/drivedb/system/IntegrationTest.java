package pt.tecnico.drivedb.system;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import pt.tecnico.drivedb.domain.App;
import pt.tecnico.drivedb.domain.File;
import pt.tecnico.drivedb.domain.Link;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.domain.User;
import pt.tecnico.drivedb.exception.*;
import pt.tecnico.drivedb.service.*;
import pt.tecnico.drivedb.service.dto.EnvironmentVarDto;
import pt.tecnico.drivedb.service.dto.FileDto;

public class IntegrationTest extends AbstractServiceTest{

	private static final List<String> files = new ArrayList<String>();
	private static final String importFile = "other.xml";

	private static final String name = "anasilva2";
	private static final String password = "anasilva2";
	private static final String file1 = "plainfile";
	private static final String file2 = "appfile";
	private static final String file3 = "dirfile";
	private static final String file4 = "linkfile";
	private static final String file5 = "linkfileEnvVar";
	private static final String file6 = "texto.pdf";
	private static final String content = "pt.tecnico.drivedb.presentation.Hello.greet";
	private static final String pathToApp = "/home/anasilva2/appfile ola";
	private static final String varName = "$ABC";
	private static final String varValue = "/home/anasilva2/linkfile";
	private static long token;
	
	
	@Override
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
		files.add(file1);
		files.add(file2);
		files.add(file3);
		files.add(file4);
		
	}
	
	@Test
    public void success() throws Exception {
        
		SAXBuilder builder = new SAXBuilder();
		ClassLoader classLoader = getClass().getClassLoader();
		java.io.File file = new java.io.File(classLoader.getResource(importFile).getFile());
        Document doc = (Document)builder.build(file);
		new ImportDriveDBService(doc).execute(); 
		LoginUserService lg = new LoginUserService(name,password);
		lg.execute();
		token = lg.result();
		
		new CreateFileService(token,file1,"Plain").execute();
		new CreateFileService(token,file2,"App").execute();
		new CreateFileService(token,file3,"Dir").execute();
		new CreateFileService(token,file4,"Link","/home/anasilva2/plainfile").execute();
		new CreateFileService(token,file5,"Link","$HOME/anasilva2/plainfile").execute();
		new AddVariableService(token, "$HOME", "/home");
		new CreateFileService(token,file6,"Plain").execute();
		
		
		ListDirectoryService ld = new ListDirectoryService(token);
		ld.execute();
		for(FileDto dto : ld.result()){
			if(dto.getContent()!=null)
				System.out.print(dto.getType() +" "+ dto.getPermission() +" "+ dto.getSize() + " "+ dto.getId() +" "+ dto.getOwner()+" "+ dto.getDate().toString("dd/MM/yyyy HH:mm:ss") +" "+ dto.getName() + " -> " + dto.getContent() + "\n");
			else 
				System.out.print(dto.getType() +" "+ dto.getPermission() +" "+ dto.getSize() + " "+ dto.getId() +" "+ dto.getOwner()+" "+ dto.getDate().toString("dd/MM/yyyy HH:mm:ss") +" "+ dto.getName()+ "\n");
		}
		assertEquals(9,ld.result().size());
		
		new WriteFileService(token,file1,pathToApp).execute();
		new WriteFileService(token,file2,content).execute();
		
		ReadFileService rd = new ReadFileService(token,file1);
		rd.execute();
		assertEquals(rd.getResult(),pathToApp);
		
		AddVariableService av = new AddVariableService(token,varName,varValue);
		av.execute();
		for(EnvironmentVarDto ev : av.result()){
			System.out.println(ev.getName() + " = " + ev.getValue());
		}
		assertEquals(av.result().size(),1);
		
		String args[] = {};
		new ExecuteFileService(token,file4,args).execute();

		final String[] argsToApp = {"/home/anasilva2/texto.pdf"};
		final String fileExt = "texto.pdf";
        new MockUp<ExecuteFileService>() {
        	@Mock
        	void dispatch() throws DriveDBApplicationException, ExtensionHasNotAppAssociationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, NoSuchMethodException, SecurityException {
        		App a = (App) Manager.getInstance().getBase().lookup(Manager.getInstance().getUserByusername("anasilva2"), "/home/anasilva2/appfile");
        		a.execute(Manager.getInstance().getUserByusername("anasilva2"), argsToApp);
        		
        	}
        };
        new ExecuteFileService(token, fileExt,args).execute();
		
        new MockUp<Link>() {
      	  @Mock
      	  	File lookup(User current, String path) { return Manager.getInstance().getBase().lookup(current, "/home/anasilva2/plainfile"); }
         };
        ReadFileService service = new ReadFileService(token, file5);
        service.execute();
        assertEquals(service.getResult(), pathToApp);
        
		new DeleteFileService(token,file2).execute();
		ld = new ListDirectoryService(token);
		ld.execute();
		assertEquals(8,ld.result().size());
		
		ChangeDirectoryService cd = new ChangeDirectoryService(token,file3);
		cd.execute();
		assertEquals(cd.result(),"/home/anasilva2/dirfile");

		
	}

}
