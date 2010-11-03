/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;

import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.utils.ExceptionPrinter;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import info.extensiblecatalog.OAIToolkit.utils.XMLUtil;

/**
 * OAIConfiguration is to store and load property values. It currently supports
 * the properties use for the Identity verb which describe the OAI Repository, 
 * and technical fields, eg. the maximum number of items in a List response.
 * @author Peter Kiraly
 */
public class OAIConfiguration {
	
	    
        /** The logger object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();
                
	/**
	 * The configuration file to store the property values
	 */
	private File configurationFile;
	
	/**
	 * The property object, which handle the persistency of the property values
	 */
	private Properties defaultProps = new Properties();
	
	//-- The properties:

	/** The name of the repository */
	private String repositoryName = "";

	/** description of repository */
	private String description = "";

	/** description/oai-identifier/scheme */
	private String oaiIdentifierScheme;

	/** description/oai-identifier/domainName */
	private String oaiIdentifierDelimiter;

    /** description/oai-identifier/repositoryIdentifier */
	private String oaiIdentifierRepositoryIdentifier;
	
	/** description/oai-identifier/sampleIdentifier */
	private String oaiIdentifierSampleIdentifier;

	/** base URL of repository */
	private String baseUrl = "";

	/** administrator email */
	private String adminEmail = "";

	/** granularity of timestamps */
	private String granularity = "YYYY-MM-DDThh:mm:ssZ";

	/** information about deleted records. Default: no */
	private String deletedRecord = "no";

	/** OAI protocol version. Default: 2.0 */
	private String protocolVersion = "2.0";

	/** supported compression types */
	private String[] compression = {};

	/** maximum number of sets in a ListSets response chunk */
	private int setsChunk_maxNumberOfRecords = 0;

	/** maximum size in bytes of a ListSets response chunk */
	private int setsChunk_maxSizeInBytes = 0;

	/** maximum number of records in a ListRecords response chunk */
	private int recordsChunk_maxNumberOfRecords = 0;

	/** maximum size in bytes of a ListRecords response chunk */
	private int recordsChunk_maxSizeInBytes = 0;

	/** maximum number of identifiers in a ListIdentifiers response chunk */
	private int identifiersChunk_maxNumberOfRecords = 0;

	/** maximum size in bytes of a ListIdentifiers response chunk */
	private int identifiersChunk_maxSizeInBytes = 0;

	/** maximum number of simultaneous requests */
	private int maxSimultaneousRequest = 0;

	/** expiration date of the resumption token */
	private int expirationDate = -1;

	/** supported compression types */
	private String schema = "standard";
	
	/** Storage type: MySQL|mixed|Lucene */
	private String storageType;
	
	/** Maximum lifetime of cached files, in milliseconds
	 * In the UI the administrator will set it in minutes.
	 */
	private int maxCacheLifetime = 60 * 60 * 1000;

	//-- constructors
	/**
	 * Set up the configuration by filename string
	 * @param fileName
	 */
	public OAIConfiguration(String fileName) {
		this(new File(fileName));
	}
	
	/**
	 * Set up the configuration by URL
	 * @param configFile
	 */
	public OAIConfiguration(URL configFile){
		this(new File(configFile.getPath()));
	}

	/**
	 * Set up the configuration by File
	 * @param configFile
	 */
	public OAIConfiguration(File configFile){
		prglog.info("[PRG] configFile: " + configFile.getAbsolutePath());
		configurationFile = configFile;
	}
	
	//-- methods
	/**
	 * Read the defaultProps properties from the configuration file and
	 * copy its values to the OAIConfiguration fields
	 */
	public void load(){
		FileInputStream in = null;
		try {
			in = new FileInputStream(configurationFile);
			defaultProps.load(in);
			in.close();
			
			repositoryName = (String)defaultProps.getProperty("repositoryName"); 
			description = (String)defaultProps.getProperty("description");
			oaiIdentifierScheme = (String)defaultProps.getProperty(
					"oaiIdentifierScheme");
			oaiIdentifierDelimiter = (String)defaultProps
					.getProperty("oaiIdentifierDelimiter");
            oaiIdentifierRepositoryIdentifier = (String)defaultProps
					.getProperty("oaiIdentifierRepositoryIdentifier");
			oaiIdentifierSampleIdentifier = (String)defaultProps.getProperty(
					"oaiIdentifierSampleIdentifier");
			baseUrl = (String)defaultProps.getProperty("baseUrl");
			adminEmail = (String)defaultProps.getProperty("adminEmail");
			deletedRecord = (String)defaultProps.getProperty("deletedRecord");
			protocolVersion = (String)defaultProps.getProperty("protocolVersion");
			granularity = (String)defaultProps.getProperty("granularity");
			compression = ((String)defaultProps.getProperty("compression")).split(", ");
			setsChunk_maxNumberOfRecords = Integer.parseInt(
					defaultProps.getProperty("setsChunk_maxNumberOfRecords"));
			setsChunk_maxSizeInBytes = Integer.parseInt(
					defaultProps.getProperty("setsChunk_maxSizeInBytes"));
			recordsChunk_maxNumberOfRecords = Integer.parseInt(
					defaultProps.getProperty("recordsChunk_maxNumberOfRecords"));
			recordsChunk_maxSizeInBytes = Integer.parseInt(
					defaultProps.getProperty("recordsChunk_maxSizeInBytes"));
			identifiersChunk_maxNumberOfRecords = Integer.parseInt(
					defaultProps.getProperty("identifiersChunk_maxNumberOfRecords"));
			identifiersChunk_maxSizeInBytes = Integer.parseInt(
					defaultProps.getProperty("identifiersChunk_maxSizeInBytes"));
			maxSimultaneousRequest = Integer.parseInt(
					defaultProps.getProperty("maxSimultaneousRequest"));
			expirationDate = Integer.parseInt(
					defaultProps.getProperty("expirationDate"));
			schema = (String)defaultProps.getProperty("schema");
			storageType = (String)defaultProps.getProperty("storageType");
			String maxCacheLifetimeString = defaultProps.getProperty(
					"maxCacheLifetime");
			if(maxCacheLifetimeString != null) {
				maxCacheLifetime = Integer.parseInt(
					defaultProps.getProperty("maxCacheLifetime")) * 60000;
			}

		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch(IOException e) {
					prglog.error("[PRG] " + ExceptionPrinter.getStack(e));
				}
			}
		}
	}
	
	/**
	 * Save the configuration calues to defaultProps and store
	 * defaultProps to the configuration file
	 */
	public void save(){
		FileOutputStream fos = null;
		try {
			if(defaultProps == null) {
				defaultProps = new Properties();
			}

			defaultProps.setProperty("repositoryName", repositoryName);
			defaultProps.setProperty("description", description);
			defaultProps.setProperty("oaiIdentifierScheme", oaiIdentifierScheme);
			defaultProps.setProperty("oaiIdentifierDelimiter", oaiIdentifierDelimiter);
            defaultProps.setProperty("oaiIdentifierRepositoryIdentifier", oaiIdentifierRepositoryIdentifier);
			defaultProps.setProperty("oaiIdentifierSampleIdentifier", oaiIdentifierSampleIdentifier);
			defaultProps.setProperty("baseUrl", baseUrl);
			defaultProps.setProperty("adminEmail", adminEmail);
			defaultProps.setProperty("deletedRecord", deletedRecord);
            defaultProps.setProperty("protocolVersion", protocolVersion);
			defaultProps.setProperty("granularity", granularity);
			defaultProps.setProperty("compression", TextUtil.join(Arrays.asList(compression), ", "));
			defaultProps.setProperty("setsChunk_maxNumberOfRecords", String.valueOf(setsChunk_maxNumberOfRecords));
			defaultProps.setProperty("setsChunk_maxSizeInBytes", String.valueOf(setsChunk_maxSizeInBytes));
			defaultProps.setProperty("recordsChunk_maxNumberOfRecords", String.valueOf(recordsChunk_maxNumberOfRecords));
			defaultProps.setProperty("recordsChunk_maxSizeInBytes", String.valueOf(recordsChunk_maxSizeInBytes));
			defaultProps.setProperty("identifiersChunk_maxNumberOfRecords", String.valueOf(identifiersChunk_maxNumberOfRecords));
			defaultProps.setProperty("identifiersChunk_maxSizeInBytes", String.valueOf(identifiersChunk_maxSizeInBytes));
			defaultProps.setProperty("maxSimultaneousRequest", String.valueOf(maxSimultaneousRequest));
			defaultProps.setProperty("expirationDate", String.valueOf(expirationDate));
			defaultProps.setProperty("schema", schema);
			defaultProps.setProperty("storageType", storageType);
			defaultProps.setProperty("maxCacheLifetime", String.valueOf(maxCacheLifetime / 60000));

			fos = new FileOutputStream(configurationFile);
			defaultProps.store(fos, "OAIToolkit OAI server parameters");

		} catch(FileNotFoundException e){
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		} finally {
			if(fos != null) {
				try {
					fos.close();
				} catch(IOException e) {
					prglog.error("[PRG] " + ExceptionPrinter.getStack(e));
				}
			}
		}
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String[] getCompression() {
		return compression;
	}

	public void setCompression(String[] compression) {
		this.compression = compression;
	}

	public String getDeletedRecord() {
		return deletedRecord;
	}

	public void setDeletedRecord(String deletedRecord) {
		this.deletedRecord = deletedRecord;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getOaiIdentifierDelimiter() {
		return oaiIdentifierDelimiter;
	}

	public void setOaiIdentifierDelimiter(
			String oaiIdentifierDelimiter) {
		this.oaiIdentifierDelimiter = oaiIdentifierDelimiter;
	}

    public String getOaiIdentifierRepositoryIdentifier() {
		return oaiIdentifierRepositoryIdentifier;
	}

	public void setOaiIdentifierRepositoryIdentifier(
			String oaiIdentifierRepositoryIdentifier) {
		this.oaiIdentifierRepositoryIdentifier = oaiIdentifierRepositoryIdentifier;
	}

	public String getOaiIdentifierSampleIdentifier() {
		return oaiIdentifierSampleIdentifier;
	}

	public void setOaiIdentifierSampleIdentifier(
			String oaiIdentifierSampleIdentifier) {
		this.oaiIdentifierSampleIdentifier = oaiIdentifierSampleIdentifier;
	}

	public String getOaiIdentifierScheme() {
		return oaiIdentifierScheme;
	}

	public void setOaiIdentifierScheme(String oaiIdentifierScheme) {
		this.oaiIdentifierScheme = oaiIdentifierScheme;
	}

	public int getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(int expirationDate) {
		this.expirationDate = expirationDate;
	}

    public String getGranularity() {
		return granularity;
	}

	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	public int getIdentifiersChunk_maxNumberOfRecords() {
		return identifiersChunk_maxNumberOfRecords;
	}

	public void setIdentifiersChunk_maxNumberOfRecords(
			int identifiersChunk_maxNumberOfRecords) {
		this.identifiersChunk_maxNumberOfRecords = identifiersChunk_maxNumberOfRecords;
	}

	public int getIdentifiersChunk_maxSizeInBytes() {
		return identifiersChunk_maxSizeInBytes;
	}

	public void setIdentifiersChunk_maxSizeInBytes(
			int identifiersChunk_maxSizeInBytes) {
		this.identifiersChunk_maxSizeInBytes = identifiersChunk_maxSizeInBytes;
	}

	public int getMaxSimultaneousRequest() {
		return maxSimultaneousRequest;
	}

	public void setMaxSimultaneousRequest(int maxSimultaneousRequest) {
		this.maxSimultaneousRequest = maxSimultaneousRequest;
	}

	public String getProtocolVersion() {
		return protocolVersion;
	}

	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	public int getRecordsChunk_maxNumberOfRecords() {
		return recordsChunk_maxNumberOfRecords;
	}

	public void setRecordsChunk_maxNumberOfRecords(
			int recordsChunk_maxNumberOfRecords) {
		this.recordsChunk_maxNumberOfRecords = recordsChunk_maxNumberOfRecords;
	}

	public int getRecordsChunk_maxSizeInBytes() {
		return recordsChunk_maxSizeInBytes;
	}

	public void setRecordsChunk_maxSizeInBytes(int recordsChunk_maxSizeInBytes) {
		this.recordsChunk_maxSizeInBytes = recordsChunk_maxSizeInBytes;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public int getSetsChunk_maxNumberOfRecords() {
		return setsChunk_maxNumberOfRecords;
	}

	public void setSetsChunk_maxNumberOfRecords(int setsChunk_maxNumberOfRecords) {
		this.setsChunk_maxNumberOfRecords = setsChunk_maxNumberOfRecords;
	}

	public int getSetsChunk_maxSizeInBytes() {
		return setsChunk_maxSizeInBytes;
	}

	public void setSetsChunk_maxSizeInBytes(int setsChunk_maxSizeInBytes) {
		this.setsChunk_maxSizeInBytes = setsChunk_maxSizeInBytes;
	}
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
	
	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		if(StorageTypes.isValid(storageType)) {
			this.storageType = storageType;
		} else {
			this.storageType = StorageTypes.MYSQL;
		}
	}
	
	/**
	 * Get the {@link #maxCacheLifetime}
	 * @return
	 */
	public int getMaxCacheLifetime() {
		return maxCacheLifetime;
	}

	public void setMaxCacheLifetime(int maxCacheLifetime) {
		this.maxCacheLifetime = maxCacheLifetime;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(repositoryName);
		sb.append(", " + description);
		sb.append(", " + oaiIdentifierScheme);
		sb.append(", " + oaiIdentifierDelimiter);
        sb.append(", " + oaiIdentifierRepositoryIdentifier);
		sb.append(", " + oaiIdentifierSampleIdentifier);
		sb.append(", " + baseUrl);
		sb.append(", " + adminEmail);
		sb.append(", " + deletedRecord);
		sb.append(", " + protocolVersion);
		sb.append(", " + granularity);
		sb.append(", " + TextUtil.join(compression, ", "));
		sb.append(", " + setsChunk_maxNumberOfRecords);
		sb.append(", " + setsChunk_maxSizeInBytes);
		sb.append(", " + recordsChunk_maxNumberOfRecords);
		sb.append(", " + recordsChunk_maxSizeInBytes);
		sb.append(", " + identifiersChunk_maxNumberOfRecords);
		sb.append(", " + identifiersChunk_maxSizeInBytes);
		sb.append(", " + maxSimultaneousRequest);
		sb.append(", " + expirationDate);
		sb.append(", " + schema);
		sb.append(", " + storageType);
		return sb.toString();
	}
	
	public Element getOaiIdentifier() {
		Namespace xsi = Namespace.getNamespace("xsi", 
				"http://www.w3.org/2001/XMLSchema-instance");
		Namespace oaiNs = Namespace.getNamespace(
				"http://www.openarchives.org/OAI/2.0/oai-identifier");

		Element root = new Element("oai-identifier");
		root.addNamespaceDeclaration(oaiNs);
		root.addNamespaceDeclaration(Namespace.getNamespace("xsi", 
				"http://www.w3.org/2001/XMLSchema-instance"));
		root.setAttribute("schemaLocation", 
				"http://www.openarchives.org/OAI/2.0/oai-identifier"
				+ " http://www.openarchives.org/OAI/2.0/oai-identifier.xsd", 
				xsi);
		root.addContent(XMLUtil.xmlEl("scheme", oaiIdentifierScheme));
        root.addContent(XMLUtil.xmlEl("repositoryIdentifier",
				oaiIdentifierRepositoryIdentifier));
		root.addContent(XMLUtil.xmlEl("delimiter",
				oaiIdentifierDelimiter));
		//root.addContent(XMLUtil.xmlEl("delimiter", oaiIdentifierDomainName));
		root.addContent(XMLUtil.xmlEl("sampleIdentifier", 
				oaiIdentifierSampleIdentifier));
		return root;
	}
}
