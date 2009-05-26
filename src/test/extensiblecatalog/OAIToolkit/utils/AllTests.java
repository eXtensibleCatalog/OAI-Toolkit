/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.utils;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for utils");
		//$JUnit-BEGIN$
		suite.addTestSuite(ApplInfoTestCase.class);
		suite.addTestSuite(ConfigUtilTestCase.class);
		suite.addTestSuite(FileIOTestCase.class);
		suite.addTestSuite(MilliSecFormatterTestCase.class);
		suite.addTestSuite(TextUtilTestCase.class);
		suite.addTestSuite(XercesTest.class);
		suite.addTestSuite(XMLUtilTestCase.class);
		suite.addTestSuite(XMLValidatorTestCase.class);
		suite.addTestSuite(XpathUtilTestCase.class);
		suite.addTestSuite(XMLUtilTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
