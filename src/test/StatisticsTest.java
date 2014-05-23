package test;

import static org.junit.Assert.*;
import garage.Database;
import garage.Statistics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StatisticsTest {
	Database db;
	Statistics stats;

	@Before
	public void setUp() throws Exception {
		db = new Database(100, 100);
		stats = new Statistics(db);
		
	}

	@After
	public void tearDown() throws Exception {
		db = null;
		stats = null;
	}

	@Test
	public void testStatistics() {
		for (int i=0;i<5;i++){
			db.addMember("fName", "lName", "PIDNbr", "telNbr");
		}
		
	}

}
