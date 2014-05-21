package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entities.Bicycle;

public class BicycleTest {
	Bicycle b;
	@Before
	public void setUp() throws Exception {
		b = new Bicycle("550827", "00000");
	}

	@After
	public void tearDown() throws Exception {
		b=null;
	}

	@Test
	public void testGetters() {
		assertEquals(b.getBarcode(), "00000");
		assertEquals(b.getOwnerPIN(), "550827");
	}
	@Test
	public void testParking() {
		assertFalse(b.isParked());
		b.park();
		assertTrue(b.isParked());
		b.unPark();
		assertFalse(b.isParked());
	}

}
