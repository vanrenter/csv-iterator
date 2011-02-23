/*
    Copyright (C) 2006-2011  le.jhe@freesbee.fr

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
package org.jhe.csviterator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author le.jhe@freesbee.fr
 * @license GPL v3
 */

public class CSVIteratorTestMulti extends TestCase {
	private CVSList list;
	private List expectedHeaders;
	private List<Map<String,List<String>>> expectedValues;
	
	@Before
	public void setUp() throws Exception{
		super.setUp();
		list						= new CSVArrayList("target/test-classes/multi.csv");
		expectedHeaders				= Arrays.asList("header1", "header2");
		Map<String,List<String>> line1	= new HashMap<String, List<String>>();
		Map<String,List<String>> line2	= new HashMap<String, List<String>>();
		line1.put("header1", Arrays.asList("value11"));
		line1.put("header2", Arrays.asList("value12"));
		line2.put("header1", Arrays.asList("value21"));
		line2.put("header2", Arrays.asList("value221","value222"));
		expectedValues				= Arrays.asList(line1,line2);
		CSVIteratorImpl.setDebug(true);
	}
	
	@After
	public void tearDown() throws Exception{
		super.tearDown();
	}
	
	@Test
	public void testNull() {
			assertNotNull("list should not be null",list);
	}
	
	@Test
	public void testHeaderEquals() {
		try {
			assertEquals("list equality",expectedHeaders, list.getHeaders());
		} catch (IOException ioe) {
			ioe.printStackTrace();
			fail(ioe.getMessage());	
		}
	}
	
	@Test
	public void testHeaderSize() {
			assertEquals("list size",expectedHeaders.size(), list.size());
	}

	@Test
	public void testValues() {
			//assertEquals("list values",expectedValues, list);
			//assertEquals("iterator values",expectedValues.iterator(), list.iterator());
			assertEquals("expected values",expectedValues.get(1), list.get(1));
			assertEquals("expected values",expectedValues.get(0), list.get(0));
	}

}
