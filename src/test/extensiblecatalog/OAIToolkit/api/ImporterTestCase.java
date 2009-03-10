/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.api;

import info.extensiblecatalog.OAIToolkit.api.Importer;
import info.extensiblecatalog.OAIToolkit.db.managers.RecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsToRecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.XmlsMgr;
import info.extensiblecatalog.OAIToolkit.importer.statistics.LoadStatistics;
import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;

import java.io.File;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import test.Constants;

import junit.framework.TestCase;

/**
 * Test importing
 * @author kiru
 */
public class ImporterTestCase extends TestCase {

	private String LUCENEDIR = Constants.TEST_DIR + "/lucene_index";
	protected static final Logger logger = Logging.getLogger();
	
	protected void setUp() throws Exception {
		super.setUp();
		ApplInfo.init(Constants.TEST_DIR, Constants.TEST_DIR + "/log");
		deleteLucene();
		deleteXml();
		deleteXmlDest();
		deleteMySQL();
	}
	
	public void testSingleImportMySQL() {
		doSingleImport(StorageTypes.MYSQL);
	}

	public void testSingleImportMixed() {
		doSingleImport(StorageTypes.MIXED);
	}

	public void testSingleImportLucene() {
		doSingleImport(StorageTypes.LUCENE);
	}

	public void doSingleImport(String storageType) {
		Constants.copyToDir("marc01.xml", "xml");
		Importer importer = getImporter(storageType);
		assertEquals("no convert", false, importer.configuration.isNeedConvert());

		importer.execute();
		LoadStatistics stat = importer.getImportStatistics();
		System.out.println(stat.toString());
		assertNotNull("import statistics not null", stat);
		assertEquals("1000 bib", 1000, stat.getBibliographic());
		assertEquals("1000 created", 1000, stat.getCreated());

		importer.execute();
		stat = importer.getImportStatistics();
		System.out.println(stat.toString());
		assertEquals("created != 0", 0, stat.getCreated());
		assertEquals("invalid != 0", 0, stat.getInvalid());
		assertEquals("skipped != 0", 0, stat.getSkipped());
		assertEquals("updated != 0", 0, stat.getUpdated());
	}

	public void testConsecutiveImportsMySQL() {
		doConsecutiveImports(StorageTypes.MYSQL);
	}

	public void testConsecutiveImportsMixed() {
		doConsecutiveImports(StorageTypes.MIXED);
	}

	public void testConsecutiveImportsLucene() {
		doConsecutiveImports(StorageTypes.LUCENE);
	}

	public void doConsecutiveImports(String storageType) {
		Constants.copyToDir("marc01.xml", "xml");
		Importer importer = getImporter(storageType);
		assertEquals("no convert", false, importer.configuration.isNeedConvert());
		
		importer.execute();
		assertEquals("1000 records", 1000, 
				importer.getImportStatistics().getCreated());
		
		// add more file
		Constants.copyToDir("marc02.xml", "xml");

		importer.execute();
		assertEquals("1000 records", 1000, 
				importer.getImportStatistics().getCreated());
	}

	public void xtestDeletedRecords() {
		Constants.copyToDir("deleted/deleted.mfhd.xml", "xml");
		Constants.copyToDir("deleted/deleted.mfhd.marc_20080425.xml", "xml");
		Constants.copyToDir("deleted/deleted.bib.marc_20080425.xml", "xml");
		
		Importer importer = getImporter(StorageTypes.LUCENE);
		assertEquals("no convert", false, importer.configuration.isNeedConvert());

		importer.execute();
		LoadStatistics stat = importer.getImportStatistics();
		assertNotNull("import statistics not null", stat);
		System.out.println(stat.toString());
		assertEquals("created != 13056", 13056, stat.getCreated());
		assertEquals("invalid != 0", 0, stat.getInvalid());
		assertEquals("skipped != 0", 0, stat.getSkipped());
		assertEquals("updated != 0", 0, stat.getUpdated());
	}

	public void xtestDiscardedRecords() {
		Constants.copyToDir("discarded/discard.auth.xml", "xml");
		Constants.copyToDir("discarded/discard.bib.xml", "xml");
		
		Importer importer = getImporter(StorageTypes.LUCENE);
		assertEquals("no convert", false, importer.configuration.isNeedConvert());

		importer.execute();
		LoadStatistics stat = importer.getImportStatistics();
		assertNotNull("import statistics not null", 
				importer.getImportStatistics());
		assertEquals("created != 0", 0,    stat.getCreated());
		assertEquals("invalid != 0", 1380, stat.getInvalid());
		assertEquals("skipped != 0", 0,    stat.getSkipped());
		assertEquals("updated != 0", 0,    stat.getUpdated());
	}
	
	private void deleteLucene() {
		File luceneDir = new File(LUCENEDIR);
		if(luceneDir.exists()) {
			File[] files = luceneDir.listFiles();
			if(files != null && files.length > 0) {
				for(File file : files) {
					boolean deleted = file.delete();
					if(!deleted) {
						System.out.println("Unable to delete file: " + file);
					}

				}
			}
			boolean deleted = luceneDir.delete();
			if(!deleted) {
				System.out.println("Unable to delete file: " + luceneDir);
			}

		}
	}

	private void deleteMySQL() {
		try {
			SetsToRecordsMgr setsToRMgr = new SetsToRecordsMgr();
			int s = setsToRMgr.deleteAll();

			XmlsMgr xmlMgr = new XmlsMgr();
			int x = xmlMgr.deleteAll();

			RecordsMgr recordMgr = new RecordsMgr();
			int r = recordMgr.deleteAll();

			logger.info("deleted from MySQL: sets_to_records: " + s 
					+ ", xmls: " + x + ", records: " + r);
		} catch(SQLException e){
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private void deleteXmlDest() {
		Importer importer = getImporter(StorageTypes.LUCENE);
		File dir = new File(importer.configuration.getDestinationXmlDir());
		if(dir.exists()) {
			File[] files = dir.listFiles();
			if(files != null && files.length > 0) {
				for(File file : files) {
					boolean deleted = file.delete();
					if(!deleted) {
						System.out.println("Unable to delete file: " + file);
					}

				}
			}
		}
	}

	private void deleteXml() {
		Importer importer = getImporter(StorageTypes.LUCENE);
		File dir = new File(importer.configuration.getSourceDir());
		if(dir.exists()) {
			File[] files = dir.listFiles();
			if(files != null && files.length > 0) {
				for(File file : files) {
					boolean deleted = file.delete();
					if(!deleted) {
						System.out.println("Unable to delete file: " + file);
					}

				}
			}
		}
	}

	private Importer getImporter(String storageType) {
		Importer importer = new Importer();
		importer.configuration.setNeedConvert(false);
		importer.configuration.setNeedLoad(true);
		importer.configuration.setNeedLogDetail(false);
		importer.configuration.setSourceDir(Constants.TEST_DIR + "/xml");
		importer.configuration.setDestinationDir(Constants.TEST_DIR + "/marc_dest");
		importer.configuration.setDestinationXmlDir(Constants.TEST_DIR + "/xml_dest");
		importer.configuration.setErrorDir(Constants.TEST_DIR + "/error");
		importer.configuration.setErrorXmlDir(Constants.TEST_DIR + "/error_xml");
		importer.configuration.setMarcSchema(Constants.TEST_DIR + "/schema/MARC21slim_rochester.xsd");
		importer.configuration.setLogDir(Constants.TEST_DIR + "/log");
		importer.configuration.setMarcEncoding("UTF-8");
		importer.configuration.setCharConversion("MARC8");
		importer.configuration.setLuceneIndex(Constants.TEST_DIR + "/lucene_index");
		importer.configuration.setStorageType(storageType);
		return importer;
	}
}
