package test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Member;

public class MemberTest {
	Member m;

	@Before
	public void setUp() throws Exception {
		m = new Member("Örjan", "Karlsson", "550827-3895", "0702239910",
				"550827");
	}

	@After
	public void tearDown() throws Exception {
		m = null;
	}

	@Test
	public void testCheckIn() {
		assertFalse(m.isCheckedIn());
		m.checkIn();
		assertTrue(m.isCheckedIn());
	}

	@Test
	public void testBicycles() {
		assertEquals(0, m.getBicycles().size());
		m.addBicycle("00000");
		assertEquals(1, m.getBicycles().size());
		for (int i = 1; i < 10; i++) {
			m.addBicycle("0000" + String.valueOf(i));
		}
		assertEquals(2, m.getBicycles().size());
		m.removeBicycle("00000");
		assertEquals(1, m.getBicycles().size());

	}

	public void testSuspension() {
		Date suspensionTime = new Date(new Date().getTime() + 10000);
		assertFalse(m.isSuspended());
		m.suspend(suspensionTime);
		assertTrue(m.isSuspended());
		assertEquals(suspensionTime.getTime(), m.suspendedUntil().getTime());
		m.unsuspend();
		assertFalse(m.isSuspended());
	}

	@Test
	public void testGetPin() {
		assertEquals("550827", m.getPIN());
	}

	@Test
	public void testGetPID() {
		assertEquals("550827-3895", m.getPIDNbr());
	}

	@Test
	public void testGetInfo() {
		String[] info = { "Örjan", "Karlsson", "0702239910" };
		for (int i = 0; i < m.getInfo().length; i++) {
			assertEquals(info[i], m.getInfo()[i]);
		}
	}

	@Test
	public void testEquals() {
		Member m2 = new Member("Örjan", "Karlsson", "660827-3895",
				"0702239910", "550827");
		assertFalse(m.equals(m2));
		Member m3 = new Member("Lars", "Örjansson", "550827-3895",
				"0702239911", "550823");
		assertTrue(m.equals(m3));
	}

}
