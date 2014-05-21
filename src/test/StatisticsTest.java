package test;

import static org.junit.Assert.*;
import entities.Member;
import interfaces.BarcodePrinter;

import java.util.Date;
import java.util.LinkedList;

import entities.DayEvent;
import garage.Database;
import garage.Statistics;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import testdrivers.BarcodePrinterTestDriver;

public class StatisticsTest {
	Database db;

	@Before
	public void setUp() throws Exception {
		db = new Database(100, 100);
	}

	@After
	public void tearDown() throws Exception {
		db = null;
	}

	@Test
	public void test() {
		BarcodePrinter p = new BarcodePrinterTestDriver();
		db.getDayEvents().add(new DayEvent());
		db.addMember("fName", "lName", "PIDNbr", "telNbr");
		db.addMember("fName", "lName", "PIDNbr", "telNbr");
		LinkedList<Member> members = db.findMembersByName("");
		db.addBicycle(members.get(0), p);
		Integer [][] matrix = db.getStats().getInfo(new Date(), new Date(new Date().getTime() + (long)86400000*3));
		for (int i = 0;i<matrix.length;i++){
			for (int j = 0; j<matrix[0].length;j++){
				System.out.print(String.valueOf(matrix[i][j])+ " ");
			}
			System.out.println();
		}
	}

}
