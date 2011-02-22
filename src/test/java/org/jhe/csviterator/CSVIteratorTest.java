package org.jhe.csviterator;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CSVIteratorTest extends TestCase {
	CSVMap map;
	@Before
	public void setUp() throws Exception{
		super.setUp();
		
		map = new CSVHashMap();
	}
	
	@After
	public void tearDown() throws Exception{
		super.tearDown();
	}
	
	@Test
	public void testMain() {
		fail("Not implemented");
	}
}
