/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.api;

import java.io.File;

import info.extensiblecatalog.OAIToolkit.api.Importer;
import info.extensiblecatalog.OAIToolkit.importer.statistics.ConversionStatistics;
import test.Constants;
import junit.framework.TestCase;

public class ConverterTestCase extends TestCase {
	
	private static final String TEST_DIR = Constants.TEST_DIR;

	protected void setUp() throws Exception {
		super.setUp();
		deleteMarcSource();
	}
	
	public void xtestSimpleConversion() {
		Constants.copyToDir("marc15.mrc", "marc");
		Importer importer = getImporter();
		importer.execute();
		ConversionStatistics stat = importer.getConversionStatistics();
		assertNotSame("converted != 0", 0, stat.getConverted());
		assertSame("invalid != 0", 0, stat.getInvalid());

		importer.execute();
		stat = importer.getConversionStatistics();
		assertEquals("converted != 0", 0, stat.getConverted());
		assertEquals("invalid != 0",   0, stat.getInvalid());
	}

	public void xtestConsecutiveConversion() {
		Constants.copyToDir("marc15.mrc", "marc");
		Importer importer = getImporter();
		importer.execute();
		ConversionStatistics stat = importer.getConversionStatistics();
		assertNotSame("converted != 0", 0, stat.getConverted());
		assertSame("invalid != 0", 0, stat.getInvalid());

		int converted = importer.getConversionStatistics().getConverted();
		Constants.copyToDir("marc01.mrc", "marc");
		
		importer.execute();
		stat = importer.getConversionStatistics();
		assertNotSame("converted != 0", 0, stat.getConverted());
		assertNotSame("invalid != 0",   0, stat.getInvalid());
		assertTrue("new records added", importer.getConversionStatistics()
				.getConverted() > converted);
	}

	public void testConversionOfDeletedRecords() {
		Constants.copyToDir("deleted/deleted.mfhd.mrc", "marc");
		Constants.copyToDir("deleted/deleted.mfhd.marc_20080425.mrc", "marc");
		Constants.copyToDir("deleted/deleted.bib.marc_20080425.mrc", "marc");
		
		Importer importer = getImporter();
		importer.execute();
		ConversionStatistics stat = importer.getConversionStatistics();
		assertNotSame("converted != 0", 0, stat.getConverted());
		assertSame("invalid != 0", 0, stat.getInvalid());

		int converted = importer.getConversionStatistics().getConverted();
		Constants.copyToDir("marc01.mrc", "marc");
		
		importer.execute();
		stat = importer.getConversionStatistics();
		assertNotSame("converted != 0", 0, stat.getConverted());
		assertNotSame("invalid != 0",   0, stat.getInvalid());
		assertTrue("new records added", importer.getConversionStatistics()
				.getConverted() > converted);
	}

	private void deleteMarcSource() {
		Importer importer = getImporter();
		File dir = new File(importer.configuration.getSourceDir());
		if(dir.exists()) {
			File[] files = dir.listFiles();
			if(files != null && files.length > 0) {
				for(File file : files) {
					boolean deleted = file.delete();
					if(!deleted) {
						System.out.println("Can't able to delete this file: " + file);
					}
				}
			}
		}
	}

	private Importer getImporter() {
		Importer importer = new Importer();
		importer.configuration.setNeedConvert(true);
		importer.configuration.setNeedLoad(false);
		importer.configuration.setNeedLogDetail(false);
		importer.configuration.setSourceDir(TEST_DIR + "/marc");
		importer.configuration.setDestinationDir(TEST_DIR + "/marc_dest");
		importer.configuration.setDestinationXmlDir(TEST_DIR + "/xml");
		importer.configuration.setErrorDir(TEST_DIR + "/error");
		importer.configuration.setErrorXmlDir(TEST_DIR + "/error_xml");
		importer.configuration.setMarcSchema(TEST_DIR + "/schema/MARC21slim_rochester.xsd");
		importer.configuration.setLogDir(TEST_DIR + "/log");
		importer.configuration.setMarcEncoding("UTF-8");
		importer.configuration.setCharConversion("MARC8");
		importer.configuration.setDoIndentXml(true);
		return importer;
	}
}
