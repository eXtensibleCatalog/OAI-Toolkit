/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.utils;

import info.extensiblecatalog.OAIToolkit.utils.MilliSecFormatter;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

public class MilliSecFormatterTestCase extends TestCase {
	
	public void test() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(       0,   "00:00:00.0"  );
		map.put(       1,   "00:00:00.001");
		map.put(    1100,   "00:00:01.100");
		map.put(   61100,   "00:01:01.100");
		map.put(   61100,   "00:01:01.100");
		map.put( 3600001,   "01:00:00.001");
		map.put(86400001, "1 00:00:00.001");
		
		for(Map.Entry<Integer, String> entry : map.entrySet()) {
			assertEquals(entry.getValue(), MilliSecFormatter.toString(entry.getKey()));
		}
	}
}
