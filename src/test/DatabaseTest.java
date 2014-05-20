package test;

import static org.junit.Assert.*;
import garage.Database;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
	Database db;

	@Before
	public void setUp() throws Exception {
		db = new Database(10, 10);
		db.addMember("Örjan", "Karlsson", "5508273895", "0702239910");
		db.addMember("Bob", "Bobson", "1201231234", "555180069");
	}

	@Test
	public void test() {
		assertNotEquals(db.getMemberList(), null);
	}

}
