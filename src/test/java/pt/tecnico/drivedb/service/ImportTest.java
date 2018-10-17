package pt.tecnico.drivedb.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.drivedb.DriveDBApplication;
import pt.tecnico.drivedb.domain.Manager;
import pt.tecnico.drivedb.exception.InvalidUsernameException;
import pt.tecnico.drivedb.exception.UsernameContainsCharsException;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;

import javax.xml.bind.annotation.XmlAccessOrder;

public class ImportTest extends AbstractServiceTest {
	
	
	
	private final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
+"<myDrive>"
+"  <user username=\"margaridamorais\">"
+"    <password>margaridamorais</password>"
+"    <name>Margarida Guerreiro Morais</name>"
+"    <home>/home/margaridamorais</home>"
+"    <mask>rwxdr-x-</mask>"
+"  </user>"
+"  <user username=\"raquelcristovao\">"
+"    <password>bonitinha</password>"
+"    <name>Raquel Cristovao</name>"
+"    <home>/home/raquelcristovao</home>"
+"    <mask>rwxdr-x-</mask>"
+"  </user>"
+"  <user username=\"brunompl\">"
+"    <password>brunompl</password>"
+"    <name>Bruno Lampreia</name>"
+"    <home>/home/brunompl</home>"
+"    <mask>rwxdr-x-</mask>"
+"  </user>"
+"  <user username=\"diogogoncas\">"
+"    <password>diogogoncas</password>"
+"    <name>Diogo Goncalves</name>"
+"    <home>/home/diogogoncas</home>"
+"    <mask>rwxdr-x-</mask>"
+"  </user>"
+"  <user username=\"anasilva2\">"
+"    <password>anasilva2</password>"
+"    <name>Ana Silva</name>"
+"    <home>/home/anasilva2</home>"
+"    <mask>rwxdr-x-</mask>"
+"  </user>"
+"  <user username=\"leonorclem\">"
+"    <password>leonorclem</password>"
+"    <name>Leonor Clemente</name>"
+"    <home>/home/leonorclem</home>"
+"    <mask>rwxdr-x-</mask>"
+"  </user>"
+"  <plain>"
+"    <path>/home/margaridamorais</path>"
+"    <name>profile</name>"
+"    <owner>margaridamorais</owner>"
+"    <perm>rw-dr---</perm>"
+"    <contents>Olá eu sou a Margarida</contents>"
+"  </plain>"
+"  <plain>"
+"    <path>/home/raquelcristovao</path>"
+"    <name>profile</name>"
+"    <owner>raquelcristovao</owner>"
+"    <perm>rwxdr-x-</perm>"
+"    <contents>Olá eu sou a Raquel</contents>"
+"  </plain>"
+"  <link>"
+"    <path>/home/margaridamorais</path>"
+"    <name>doc</name>"
+"    <owner>margaridamorais</owner>"
+"    <perm>rwxdr-x-</perm>"
+"    <value>/home/margaridamorais/profile</value>"
+"  </link>"
+"  <plain>"
+"    <path>/home/brunompl</path>"
+"    <name>xpto</name>"
+"    <owner>brunompl</owner>"
+"    <perm>rwxdr-x-</perm>"
+"    <contents>/home/brunompl/app Bruno</contents>"
+"  </plain>"
+"  <app>"
+"    <path>/home/brunompl</path>"
+"    <name>app</name>"
+"    <owner>brunompl</owner>"
+"    <perm>rwxd--x-</perm>"
+"    <method>pt.tecnico.drivedb.presentation.Hello.greet</method>"
+"  </app>"
+"  <plain>"
+"    <path>/home/leonorclem</path>"
+"    <name>profile</name>"
+"    <owner>leonorclem</owner>"
+"    <perm>rwxdr-x-</perm>"
+"    <contents>Olá eu sou a Leonor</contents>"
+"  </plain>"
+"  <plain>"
+"    <path>/home/anasilva2</path>"
+"    <name>profile</name>"
+"    <owner>anasilva2</owner>"
+"    <perm>rwxdr-x-</perm>"
+"    <contents>Olá eu sou a Ana</contents>"
+"  </plain>"
+"  <plain>"
+"    <path>/home/diogogoncas</path>"
+"    <name>profile</name>"
+"    <owner>diogogoncas</owner>"
+"    <perm>rwxdr-x-</perm>"
+"    <contents>Olá eu sou o Diogo</contents>"
+"  </plain>"
+"</myDrive>";
	

	@Override
	protected void populate() throws UsernameContainsCharsException, InvalidUsernameException {
		Manager m= Manager.getInstance();
		
		
	}
	
	
	@Test
	public void sucess() throws Exception
	{
		Document doc = new SAXBuilder().build(new StringReader(xml));
		ImportDriveDBService service = new ImportDriveDBService(doc);
		service.execute();
		
		//check imported data
		Manager m = Manager.getInstance();
		//counting with root and guest that is automatically created
		
		assertEquals("Created 6 Users", 8, m.getUsersSet().size());
		assertEquals("Created Raquel", "Raquel Cristovao", m.getUserByUsername("raquelcristovao").getName());
		assertEquals("Created Margarida", "Margarida Guerreiro Morais", m.getUserByUsername("margaridamorais").getName());
		assertEquals("Created Leonor", "Leonor Clemente", m.getUserByUsername("leonorclem").getName());
		assertEquals("Created Diogo", "Diogo Goncalves", m.getUserByUsername("diogogoncas").getName());
		assertEquals("Created Ana", "Ana Silva", m.getUserByUsername("anasilva2").getName());
		assertEquals("Created Bruno", "Bruno Lampreia", m.getUserByUsername("brunompl").getName());
		//counting with home that is automatically created
		assertEquals("Margarida has 3 files", 3, m.getUserByUsername("margaridamorais").getFilesSet().size());
		assertEquals("Raquel has 2 files", 2, m.getUserByUsername("raquelcristovao").getFilesSet().size());
		assertEquals("Ana has 2 files", 2, m.getUserByUsername("anasilva2").getFilesSet().size());
		assertEquals("Bruno has 3 files", 3, m.getUserByUsername("brunompl").getFilesSet().size());
		assertEquals("Diogo has 2 files", 2, m.getUserByUsername("diogogoncas").getFilesSet().size());
		assertEquals("Leonor has 2 files", 2, m.getUserByUsername("leonorclem").getFilesSet().size());
	}




}