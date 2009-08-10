/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.utils.ExceptionPrinter;

import junit.framework.TestCase;

public class ExceptionTest extends TestCase {

	public void testException() throws Exception {
		try {
			Integer.parseInt(" ");
		} catch(NumberFormatException e) {
			StringBuffer sb = new StringBuffer();
			System.out.println(ExceptionPrinter.getStack(e, sb));
		}
	}
}
