/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.db;

import test.extensiblecatalog.OAIToolkit.db.managers.MainDataMgrTestCase;
import test.extensiblecatalog.OAIToolkit.db.managers.SetSpecsTestCase;
import test.extensiblecatalog.OAIToolkit.db.managers.SetsMgrTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for test.extensiblecatalog.OAIToolkit.db");
		//$JUnit-BEGIN$
		suite.addTestSuite(LuceneReadWriteTestCase.class);
		suite.addTestSuite(SetsMgrTestCase.class);
		suite.addTestSuite(DataSourceTestCase.class);
		suite.addTestSuite(TermTest.class);
		suite.addTestSuite(MainDataMgrTestCase.class);
		suite.addTestSuite(SetSpecsTestCase.class);
		//$JUnit-END$
		return suite;
	}

}
