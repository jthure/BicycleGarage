package test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import garage.*;

public class TestBicycle {
	Bicycle b;

	@Before
	public void setUp() throws Exception {
		b = new Bicycle("12345");
	}

	@After
	public void tearDown() throws Exception {
		b = null;
	}

	@Test
	public void testValidID() {
		assertEquals("12345", b.getId());
		assertTrue(b.setID("23456"));
		assertEquals("23456", b.getId());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreationInvalidID() {
		b = new Bicycle("1234");
	}

	public void testSetInvalidID() {
		assertFalse(b.setID("1234"));
		assertEquals("12345", b.getId());
	}

}
