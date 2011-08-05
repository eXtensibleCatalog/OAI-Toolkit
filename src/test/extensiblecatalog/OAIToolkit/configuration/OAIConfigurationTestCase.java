/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.configuration;

import java.io.File;

import junit.framework.TestCase;
import info.extensiblecatalog.OAIToolkit.configuration.OAIConfiguration;
import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import test.Constants;

/**
 * Test OAIConfiguration
 * @author kiru
 * @version 0.1
 */
public class OAIConfigurationTestCase extends TestCase {
	
	private String configFile = Constants.TEST_DIR 
							+ "/OAIToolkit.server.properties";
	private OAIConfiguration conf;
	private static final String protocol = "2.0";
	private static final String deletedRecord = "transient";
	private static final String baseUrl = "http://localhost:8160/ILSToolkit/oai-request.do";
	private static final String granularity = "YYYY-MM-DDThh:mm:ssZ";
	private static final String oaiIdentifierSampleIdentifier = "oai:extensiblecatalog.info:99123";
	private static final String protocolVersion = "2.0";
	private static final String repositoryName = "University of Rochester";
	private static final String schema = "custom";
	private static final String oaiIdentifierScheme = "oai";
	private static final String description = "<oai-identifier xmlns=\"http://www." +
			"openarchives.org/OAI/2.0/oai-identifier\" xmlns:xsi=\"http://" +
			"www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://" +
			"www.openarchives.org/OAI/2.0/oai-identifier http://www." +
			"openarchives.org/OAI/2.0/oai-identifier.xsd\">\r\n  <scheme>" +
			"oai</scheme>\r\n  <repositoryIdentifier>extensiblecatalog.info" +
			"</repositoryIdentifier>\r\n  <delimiter>:</delimiter>\r\n  " +
			"<sampleIdentifier>oai:extensiblecatalog.info:99123</" +
			"sampleIdentifier>\r\n</oai-identifier>\r\n";
	private static final String adminEmail = "pkiraly@tesuji.eu";
	private static final String oaiIdentifierRepositoryIdentifier = "extensiblecatalog.info";
    private static final String oaiIdentifierDelimiter = ":";
	private static final String storageType = "Lucene";
	private static final String[] compression = {"gzip", "compress", "deflate"};
	private static final int expirationDate = -1;
	private static final int maxSimultaneousRequest = 0;
	private static final int identifiersChunk_maxNumberOfRecords = 50;
	private static final int identifiersChunk_maxSizeInBytes     = 1000000;
	private static final int setsChunk_maxSizeInBytes            = 1000000;
	private static final int setsChunk_maxNumberOfRecords        = 50;
	private static final int recordsChunk_maxNumberOfRecords     = 1000;
	private static final int recordsChunk_maxSizeInBytes         = 1000000;


	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		conf = new OAIConfiguration(configFile);
	}

	public void testOAIConfigurationString() {
		conf = new OAIConfiguration(configFile);
		assertNotNull("is null", conf);
		conf.load();
		assertEquals("bad protocol", protocol, conf.getProtocolVersion());
	}

	public void testOAIConfigurationURL() {
		//fail("Not yet implemented");
	}

	public void testOAIConfigurationFile() {
		conf = new OAIConfiguration(new File(configFile));
		assertNotNull("is null", conf);
		conf.load();
		assertEquals("bad protocol", protocol, conf.getProtocolVersion());
	}

	public void testLoad() {
		conf = new OAIConfiguration(new File(configFile));
		assertNotNull("is null", conf);
		conf.load();
		assertEquals("bad protocol", protocol, conf.getProtocolVersion());
	}

	public void testSave() {
		String newProtocol = "2.1";
		conf = new OAIConfiguration(new File(configFile));
		assertNotNull("is null", conf);
		conf.load();
		assertEquals("bad protocol", protocol, conf.getProtocolVersion());
		conf.setProtocolVersion(newProtocol);
		conf.save();

		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("saved protocol", newProtocol, conf.getProtocolVersion());
		conf.setProtocolVersion(protocol);
		conf.save();
	}

	public void testGetAdminEmail() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad protocol", adminEmail, conf.getAdminEmail());
	}

	public void testSetAdminEmail() {
		String newEmail = "test@extensiblecatalog.info";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setAdminEmail(newEmail);
		assertEquals("bad protocol", newEmail, conf.getAdminEmail());
	}

	public void testGetBaseUrl() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad protocol", baseUrl, conf.getBaseUrl());
	}

	public void testSetBaseUrl() {
		String newValue = baseUrl + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setBaseUrl(newValue);
		assertEquals("bad protocol", newValue, conf.getBaseUrl());
	}

	public void testGetCompression() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		String[] stored = conf.getCompression();
		assertEquals("same length", compression.length, stored.length);
		for(int i=0; i<stored.length; i++) {
			assertEquals("bad compression", compression[i], stored[i]);
		}
	}

	public void testSetCompression() {
		String[] newValue = {"compress", "gzip", "deflate"};

		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setCompression(newValue);
		assertEquals("bad compression", newValue, conf.getCompression());
	}

	public void testGetDeletedRecord() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad deleted record", deletedRecord, conf.getDeletedRecord());
	}

	public void testSetDeletedRecord() {
		String newValue = deletedRecord + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setDeletedRecord(newValue);
		assertEquals("bad deleted record", newValue, conf.getDeletedRecord());
	}

	public void testGetDescription() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad description", description, conf.getDescription());
	}

	public void testSetDescription() {
		String newValue = deletedRecord + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setDescription(newValue);
		assertEquals("bad description", newValue, conf.getDescription());
	}

	public void testGetOaiIdentifierRepositoryIdentifier() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad oaiIdentifierRepositoryIdentifier", 
				oaiIdentifierRepositoryIdentifier, 
				conf.getOaiIdentifierRepositoryIdentifier());
	}

	public void testSetOaiIdentifierRepositoryIdentifier() {
		String newValue = oaiIdentifierRepositoryIdentifier + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setOaiIdentifierRepositoryIdentifier(newValue);
		assertEquals("bad oaiIdentifierRepositoryIdentifier", newValue, 
				conf.getOaiIdentifierRepositoryIdentifier());
	}

    public void testGetOaiIdentifierDelimiter() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad oaiIdentifierDelimiter",
				oaiIdentifierDelimiter,
				conf.getOaiIdentifierDelimiter());
	}

	public void testSetOaiIdentifierDelimiter() {
		String newValue = oaiIdentifierDelimiter + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setOaiIdentifierDelimiter(newValue);
		assertEquals("bad oaiIdentifierDelimiter", newValue,
				conf.getOaiIdentifierDelimiter());
	}

	public void testGetOaiIdentifierSampleIdentifier() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad oaiIdentifierSampleIdentifier", 
				oaiIdentifierSampleIdentifier, 
				conf.getOaiIdentifierSampleIdentifier());
	}

	public void testSetOaiIdentifierSampleIdentifier() {
		String newValue = oaiIdentifierSampleIdentifier + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setOaiIdentifierSampleIdentifier(newValue);
		assertEquals("bad oaiIdentifierSampleIdentifier", newValue, 
				conf.getOaiIdentifierSampleIdentifier());
	}

	public void testGetOaiIdentifierScheme() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad oaiIdentifierScheme", oaiIdentifierScheme, 
				conf.getOaiIdentifierScheme());
	}

	public void testSetOaiIdentifierScheme() {
		String newValue = oaiIdentifierScheme + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setOaiIdentifierScheme(newValue);
		assertEquals("bad oaiIdentifierScheme", newValue, 
				conf.getOaiIdentifierScheme());
	}

	public void testGetExpirationDate() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad expirationDate", expirationDate, 
				conf.getExpirationDate());
	}

	public void testSetExpirationDate() {
		int newValue = expirationDate + 1;
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setExpirationDate(newValue);
		assertEquals("bad expirationDate", newValue, 
				conf.getExpirationDate());
	}

	public void testGetGranularity() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad oaiIdentifierScheme", granularity, 
				conf.getGranularity());
	}

	public void testSetGranularity() {
		String newValue = granularity + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setGranularity(newValue);
		assertEquals("bad granularity", newValue, 
				conf.getGranularity());
	}

	public void testGetIdentifiersChunk_maxNumberOfRecords() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad identifiersChunk_maxNumberOfRecords", 
				identifiersChunk_maxNumberOfRecords, 
				conf.getIdentifiersChunk_maxNumberOfRecords());
	}

	public void testSetIdentifiersChunk_maxNumberOfRecords() {
		int newValue = identifiersChunk_maxNumberOfRecords + 1;
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setIdentifiersChunk_maxNumberOfRecords(newValue);
		assertEquals("bad identifiersChunk_maxNumberOfRecords", newValue, 
				conf.getIdentifiersChunk_maxNumberOfRecords());
	}

	public void testGetMaxSimultaneousRequest() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad maxSimultaneousRequest", maxSimultaneousRequest, 
				conf.getMaxSimultaneousRequest());
	}

	public void testSetMaxSimultaneousRequest() {
		int newValue = maxSimultaneousRequest + 1;
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setMaxSimultaneousRequest(newValue);
		assertEquals("bad maxSimultaneousRequest", newValue, 
				conf.getMaxSimultaneousRequest());
	}

	public void testGetProtocolVersion() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad protocolVersion", protocolVersion, conf.getProtocolVersion());
	}

	public void testSetProtocolVersion() {
		String newValue = protocolVersion + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setProtocolVersion(newValue);
		assertEquals("bad protocolVersion", newValue, conf.getProtocolVersion());

	}

	public void testGetRecordsChunk_maxNumberOfRecords() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad recordsChunk_maxNumberOfRecords", recordsChunk_maxNumberOfRecords, 
				conf.getRecordsChunk_maxNumberOfRecords());
	}

	public void testSetRecordsChunk_maxNumberOfRecords() {
		int newValue = recordsChunk_maxNumberOfRecords + 1;
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setRecordsChunk_maxNumberOfRecords(newValue);
		assertEquals("bad recordsChunk_maxNumberOfRecords", newValue, 
				conf.getRecordsChunk_maxNumberOfRecords());
	}

	public void testGetSetsChunk_maxNumberOfRecords() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad setsChunk_maxNumberOfRecords", setsChunk_maxNumberOfRecords, 
				conf.getSetsChunk_maxNumberOfRecords());
	}

	public void testSetSetsChunk_maxNumberOfRecords() {
		int newValue = setsChunk_maxNumberOfRecords + 1;
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setSetsChunk_maxNumberOfRecords(newValue);
		assertEquals("bad setsChunk_maxNumberOfRecords", newValue, 
				conf.getSetsChunk_maxNumberOfRecords());
	}

	public void testGetRepositoryName() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad repositoryName", repositoryName, 
				conf.getRepositoryName());
	}

	public void testSetRepositoryName() {
		String newValue = repositoryName + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setRepositoryName(newValue);
		assertEquals("bad repositoryName", newValue, conf.getRepositoryName());
	}

	public void testGetSchema() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad schema", schema, conf.getSchema());
	}

	public void testSetSchema() {
		String newValue = schema + "_fake";
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setSchema(newValue);
		assertEquals("bad schema", newValue, conf.getSchema());
	}

	public void testGetStorageType() {
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("bad storageType", storageType, conf.getStorageType());
	}

	public void testSetStorageType() {
		String newValue = StorageTypes.MIXED;
		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		conf.setStorageType(newValue);
		assertEquals("bad storageType", newValue, conf.getStorageType());

		conf.setStorageType("some value");
		assertEquals("bad storageType", StorageTypes.MYSQL, conf.getStorageType());
	}

	public void testToString() {
		StringBuilder expected = new StringBuilder();
		expected.append(repositoryName);
		expected.append(", " + description);
		expected.append(", " + oaiIdentifierScheme);
		expected.append(", " + oaiIdentifierRepositoryIdentifier);
		expected.append(", " + oaiIdentifierDelimiter);
		expected.append(", " + oaiIdentifierSampleIdentifier);
		expected.append(", " + baseUrl);
		expected.append(", " + adminEmail);
		expected.append(", " + deletedRecord);
		expected.append(", " + protocolVersion);
		expected.append(", " + granularity);
		expected.append(", " + TextUtil.join(compression, ", "));
		expected.append(", " + setsChunk_maxNumberOfRecords);
		expected.append(", " + setsChunk_maxSizeInBytes);
		expected.append(", " + recordsChunk_maxNumberOfRecords);
		expected.append(", " + recordsChunk_maxSizeInBytes);
		expected.append(", " + identifiersChunk_maxNumberOfRecords);
		expected.append(", " + identifiersChunk_maxSizeInBytes);
		expected.append(", " + maxSimultaneousRequest);
		expected.append(", " + expirationDate);
		expected.append(", " + schema);
		expected.append(", " + storageType);

		conf = new OAIConfiguration(new File(configFile));
		conf.load();
		assertEquals("to string not equals", 
				expected.toString(), conf.toString());
	}
}
