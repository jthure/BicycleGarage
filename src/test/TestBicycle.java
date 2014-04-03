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
	public void test() {
		fail("Not yet implemented");
	}

}
