/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.TestCase;

public class RegexTest extends TestCase {

	public void testRegex() throws Exception {
		Pattern p = Pattern.compile("a");
		Matcher m = p.matcher("hgaidkanshajk");
		if(m.find()) {
			List<Integer> mat = new ArrayList<Integer>();
			do {
				mat.add(m.start());
			} while(m.find());
			for(Integer i : mat) {
				System.out.println(i);
			}
		}
	}
}
