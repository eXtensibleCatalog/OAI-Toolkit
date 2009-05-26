/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test;

/*
import test.extensiblecatalog.OAIToolkit.DTOs.AllTests;
import test.extensiblecatalog.OAIToolkit.api.ImporterTestCase;
import test.extensiblecatalog.OAIToolkit.api.ImporterTestCase;
import test.extensiblecatalog.OAIToolkit.db.DataSourceTestCase;
import test.extensiblecatalog.OAIToolkit.db.LuceneReadWriteTestCase;
import test.extensiblecatalog.OAIToolkit.db.TermTest;
import test.extensiblecatalog.OAIToolkit.db.managers.MainDataMgrTestCase;
import test.extensiblecatalog.OAIToolkit.db.managers.SetSpecsTestCase;
import test.extensiblecatalog.OAIToolkit.db.managers.SetsMgrTestCase;
import test.extensiblecatalog.OAIToolkit.importer.MarcCounterTestCase;
import test.extensiblecatalog.OAIToolkit.oai.MetadataFormatUnmarshalerTestCase;
import test.extensiblecatalog.OAIToolkit.utils.TextUtilTestCase;
import test.extensiblecatalog.OAIToolkit.utils.XMLValidatorTestCase;
import test.extensiblecatalog.OAIToolkit.utils.XercesTest;
*/
import test.extensiblecatalog.OAIToolkit.DTOs.BigxmlDTOTestCase;
import test.extensiblecatalog.OAIToolkit.DTOs.RecordDTOTestCase;
import test.extensiblecatalog.OAIToolkit.DTOs.ResumptionTokenDTOTestCase;
import test.extensiblecatalog.OAIToolkit.DTOs.SetDTOTestCase;
import test.extensiblecatalog.OAIToolkit.DTOs.SetToRecordsDTOTestCase;
import test.extensiblecatalog.OAIToolkit.DTOs.XmlDTOTestCase;
import test.extensiblecatalog.OAIToolkit.api.ConverterTestCase;
import test.extensiblecatalog.OAIToolkit.api.ImporterTestCase;
import test.extensiblecatalog.OAIToolkit.api.ImporterTestCase;
import test.extensiblecatalog.OAIToolkit.configuration.OAIConfigurationTestCase;
import test.extensiblecatalog.OAIToolkit.db.DataSourceTestCase;
import test.extensiblecatalog.OAIToolkit.db.LuceneReadWriteTestCase;
import test.extensiblecatalog.OAIToolkit.db.TermTest;
import test.extensiblecatalog.OAIToolkit.db.managers.MainDataMgrTestCase;
import test.extensiblecatalog.OAIToolkit.db.managers.SetSpecsTestCase;
import test.extensiblecatalog.OAIToolkit.db.managers.SetsMgrTestCase;
import test.extensiblecatalog.OAIToolkit.importer.MarcCounterTestCase;
import test.extensiblecatalog.OAIToolkit.oai.MetadataFormatUnmarshalerTestCase;
import test.extensiblecatalog.OAIToolkit.utils.ApplInfoTestCase;
import test.extensiblecatalog.OAIToolkit.utils.ConfigUtilTestCase;
import test.extensiblecatalog.OAIToolkit.utils.FileIOTestCase;
import test.extensiblecatalog.OAIToolkit.utils.MilliSecFormatterTestCase;
import test.extensiblecatalog.OAIToolkit.utils.TextUtilTestCase;
import test.extensiblecatalog.OAIToolkit.utils.XMLUtilTestCase;
import test.extensiblecatalog.OAIToolkit.utils.XMLValidatorTestCase;
import test.extensiblecatalog.OAIToolkit.utils.XercesTest;
import test.extensiblecatalog.OAIToolkit.utils.XpathUtilTestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * All test of the OAIToolkit
 * @author kiru
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for test");
		//$JUnit-BEGIN$

		// api tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.api.AllTests.class);
		suite.addTestSuite(ConverterTestCase.class);
		suite.addTestSuite(ImporterTestCase.class);

		// configuration tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.configuration.AllTests.class);
		suite.addTestSuite(OAIConfigurationTestCase.class);

		// db tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.db.AllTests.class);
		suite.addTestSuite(LuceneReadWriteTestCase.class);
		suite.addTestSuite(SetsMgrTestCase.class);
		suite.addTestSuite(DataSourceTestCase.class);
		suite.addTestSuite(TermTest.class);
		suite.addTestSuite(MainDataMgrTestCase.class);
		suite.addTestSuite(SetSpecsTestCase.class);

		// db.managers tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.db.managers.AllTests.class);
		suite.addTestSuite(MainDataMgrTestCase.class);
		suite.addTestSuite(SetSpecsTestCase.class);
		suite.addTestSuite(SetsMgrTestCase.class);
		
		// DTOs tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.DTOs.AllTests.class);
		suite.addTestSuite(BigxmlDTOTestCase.class);
		suite.addTestSuite(RecordDTOTestCase.class);
		suite.addTestSuite(ResumptionTokenDTOTestCase.class);
		suite.addTestSuite(SetToRecordsDTOTestCase.class);
		suite.addTestSuite(SetDTOTestCase.class);
		suite.addTestSuite(XmlDTOTestCase.class);

		// importer tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.importer.AllTests.class);
		suite.addTestSuite(MarcCounterTestCase.class);

		// oai tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.oai.AllTests.class);
		suite.addTestSuite(MetadataFormatUnmarshalerTestCase.class);
		
		// util tests
		//suite.addTestSuite(test.extensiblecatalog.OAIToolkit.utils.AllTests.class);
		suite.addTestSuite(ApplInfoTestCase.class);
		suite.addTestSuite(ConfigUtilTestCase.class);
		suite.addTestSuite(FileIOTestCase.class);
		suite.addTestSuite(MilliSecFormatterTestCase.class);
		suite.addTestSuite(TextUtilTestCase.class);
		suite.addTestSuite(XercesTest.class);
		suite.addTestSuite(XMLUtilTestCase.class);
		suite.addTestSuite(XMLValidatorTestCase.class);
		suite.addTestSuite(XpathUtilTestCase.class);

		suite.addTestSuite(test.extensiblecatalog.OAIToolkit.OAIServerTestCase.class);
		/*
		suite.addTestSuite(ImporterTestCase.class);
		suite.addTestSuite(TermTest.class);
		suite.addTestSuite(OAIServerTestCase.class);
		suite.addTestSuite(SetsMgrTestCase.class);
		suite.addTestSuite(TextUtilTestCase.class);
		suite.addTestSuite(XMLValidatorTestCase.class);
		suite.addTestSuite(RecordDTOTestCase.class);
		suite.addTestSuite(MainDataMgrTestCase.class);
		suite.addTestSuite(XercesTest.class);
		suite.addTestSuite(DataSourceTestCase.class);
		suite.addTestSuite(MetadataFormatUnmarshalerTestCase.class);
		suite.addTestSuite(MarcCounterTestCase.class);
		suite.addTestSuite(LuceneReadWriteTestCase.class);
		suite.addTestSuite(SetSpecsTestCase.class);
		suite.addTestSuite(ImporterTestCase.class);
		*/
		//$JUnit-END$
		return suite;
	}

}
