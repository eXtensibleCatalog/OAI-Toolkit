/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import java.io.File;

import info.extensiblecatalog.OAIToolkit.importer.DirectoryNameGiver;
import info.extensiblecatalog.OAIToolkit.importer.ImporterConfiguration;
import junit.framework.TestCase;

public class DirectoryNameGiverTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testConvert() throws Exception {
		ImporterConfiguration cfg = new ImporterConfiguration();
		cfg.setNeedConvert(true);
		DirectoryNameGiver d = new DirectoryNameGiver(cfg);
		assertEquals("marc",       d.getConvertSource().getName());
		assertEquals("marcxml",    d.getConvertTarget().getName());
		assertEquals("marc_dest",  d.getConvertDestination().getName());
		assertEquals("marc_error", d.getConvertError().getName());
	}

	public void testConvertLoad() throws Exception {
		ImporterConfiguration cfg = new ImporterConfiguration();
		cfg.setNeedConvert(true);
		cfg.setNeedLoad(true);
		DirectoryNameGiver d = new DirectoryNameGiver(cfg);
		assertEquals(new File("marc"),       d.getConvertSource());
		assertEquals(new File("marcxml"),    d.getConvertTarget());
		assertEquals(new File("marc_dest"),  d.getConvertDestination());
		assertEquals(new File("marc_error"), d.getConvertError());

		assertEquals(new File("marcxml"), d.getLoadSource());
		assertNull(d.getLoadTarget());
		assertEquals(new File("marcxml_dest"),  d.getLoadDestination());
		assertEquals(new File("marcxml_error"), d.getLoadError());
	}

	public void testConvertLoadWithParams1() throws Exception {
		ImporterConfiguration cfg = new ImporterConfiguration();
		cfg.setNeedConvert(true);
		cfg.setNeedLoad(true);
		cfg.setDestinationXmlDir("xml");
		DirectoryNameGiver d = new DirectoryNameGiver(cfg);
		assertEquals(new File("marc"),       d.getConvertSource());
		assertEquals(new File("xml"),        d.getConvertTarget());
		assertEquals(new File("marc_dest"),  d.getConvertDestination());
		assertEquals(new File("marc_error"), d.getConvertError());

		assertEquals(new File("xml"), d.getLoadSource());
		assertNull(d.getLoadTarget());
		assertEquals(new File("xml_dest"),  d.getLoadDestination());
		assertEquals(new File("xml_error"), d.getLoadError());
	}

	public void testModify() throws Exception {
		ImporterConfiguration cfg = new ImporterConfiguration();
		cfg.setNeedModify(true);
		DirectoryNameGiver d = new DirectoryNameGiver(cfg);
		assertEquals("marcxml",      d.getModifySource().getName());
		assertEquals("modifiedxml",  d.getModifyTarget().getName());
		assertEquals("marcxml_dest",  d.getModifyDestination().getName());
		assertEquals("marcxml_error", d.getModifyError().getName());
	}

	public void testLoad() throws Exception {
		ImporterConfiguration cfg = new ImporterConfiguration();
		cfg.setNeedLoad(true);
		DirectoryNameGiver d = new DirectoryNameGiver(cfg);
		assertEquals(new File("marcxml"),    d.getLoadSource());
		assertNull(d.getLoadTarget());
		assertEquals(new File("load_dest"),  d.getLoadDestination());
		assertEquals(new File("load_error"), d.getLoadError());
	}

	public void testModifyLoad() throws Exception {
		ImporterConfiguration cfg = new ImporterConfiguration();
		cfg.setNeedModify(true);
		//cfg.setDestinationModifiedXmlDir("modifiedxml_dest");
		cfg.setNeedLoad(true);
		DirectoryNameGiver d = new DirectoryNameGiver(cfg);
		
		assertEquals("marcxml",       d.getModifySource().getName());
		assertEquals("modifiedxml",   d.getModifyTarget().getName());
		assertEquals("marcxml_dest",  d.getModifyDestination().getName());
		assertEquals("marcxml_error", d.getModifyError().getName());

		assertEquals(new File("modifiedxml"),d.getLoadSource());
		assertNull(d.getLoadTarget());
		assertEquals(new File("modifiedxml_dest"),  d.getLoadDestination());
		assertEquals(new File("modifiedxml_error"), d.getLoadError());
	}
}

