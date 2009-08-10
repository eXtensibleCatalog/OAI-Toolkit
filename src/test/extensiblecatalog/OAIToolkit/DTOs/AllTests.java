/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.DTOs;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for DTOs");
		//$JUnit-BEGIN$
		suite.addTestSuite(BigxmlDTOTestCase.class);
		suite.addTestSuite(RecordDTOTestCase.class);
		suite.addTestSuite(ResumptionTokenDTOTestCase.class);
		suite.addTestSuite(SetToRecordsDTOTestCase.class);
		suite.addTestSuite(SetDTOTestCase.class);
		suite.addTestSuite(XmlDTOTestCase.class);
		//$JUnit-END$
		return suite;
	}
}
