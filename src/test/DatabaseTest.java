package test;

import static org.junit.Assert.*;
import interfaces.BarcodePrinter;

import java.util.*;
import java.util.Map.Entry;

import entities.Bicycle;
import entities.Member;
import garage.Database;

import org.junit.Before;
import org.junit.Test;

import testdrivers.BarcodePrinterTestDriver;

public class DatabaseTest {
	Database db;
	int memCap, bikeCap;
	String[][] members;

	@Before
	public void setUp() throws Exception {
		memCap = 1000;
		bikeCap = 2000;
		db = new Database(memCap, bikeCap);
		members = new String[][] {
				{ "Örjan", "Karlsson", "5508273895", "0702239910" },
				{ "Bob", "Bobson", "1201231234", "555180069" } };
	}

	@Test
	public void testPINCodesSize() {
		TreeSet<String> set = new TreeSet<String>();
		LinkedList<String> pins = db.getAvailPIN();
		for (String s : pins) {
			set.add(s);
		}
		assertEquals("Wrong size", 1000000, pins.size());
		assertEquals("Duplicate code", pins.size(), set.size());
	}

	@Test
	public void testBarCodesSize() {
		TreeSet<String> set = new TreeSet<String>();
		LinkedList<String> bars = db.getAvailBar();
		for (String s : bars) {
			set.add(s);
		}
		assertEquals("Wrong size", 100000, bars.size());
		assertEquals("Duplicate code", bars.size(), set.size());
	}

	@Test
	public void testAddAndGetMembers() {
		String p1 = db.addMember(members[0]);
		String p2 = db.addMember(members[1]);
		assertEquals("Wrong size", 2, db.getMemberSize());

		Member m1 = new Member(members[0][0], members[0][1], members[0][2],
				members[0][3], p1);
		Member m2 = new Member(members[1][0], members[1][1], members[1][2],
				members[1][3], p2);
		assertEquals("Members do not match", m1, db.getMember(p1));
		assertEquals("Members do not match", m2, db.getMember(p2));
	}

	@Test
	public void testAddAndRemove() {
		BarcodePrinter p = new BarcodePrinterTestDriver();
		String p1 = db.addMember(members[0]);
		String p2 = db.addMember(members[1]);
		db.addBicycle(db.getMember(p1), p);
		db.addBicycle(db.getMember(p2), p);
		assertEquals(2, db.getMemberSize());
		assertEquals(2, db.getBicycleSize());
		db.removeMember(p2);
		assertEquals(1, db.getMemberSize());
		assertEquals(1, db.getBicycleSize());
	}

	@Test
	public void testSaveAndLoad() {
		BarcodePrinter p = new BarcodePrinterTestDriver();
		String p1 = db.addMember(members[0]);
		String p2 = db.addMember(members[1]);
		db.addBicycle(db.getMember(p1), p);
		db.addBicycle(db.getMember(p2), p);

		db = null;
		db = new Database("members.bg", "bicycles.bg", "availablePIN.bg",
				"availableBar.bg", "stats.bg");
		assertEquals(2, db.getMemberSize());
		assertEquals(2, db.getBicycleSize());

		db.removeMember(p2);
		// assertEquals(1, db.getMemberSize());
		// assertEquals(1, db.getBicycleSize());
		db = null;
		db = new Database("members.bg", "bicycles.bg", "availablePIN.bg",
				"availableBar.bg", "stats.bg");
		assertEquals(1, db.getMemberSize());
		assertEquals(1, db.getBicycleSize());
	}
}
