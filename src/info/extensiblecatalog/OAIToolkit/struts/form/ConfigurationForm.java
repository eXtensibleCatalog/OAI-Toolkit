/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.struts.form;

import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;

/** 
 * MyEclipse Struts
 * Creation date: 02-01-2008
 * 
 * XDoclet definition:
 * @struts.form name="congigurationForm"
 */
public class ConfigurationForm extends ActionForm {

	private static final long serialVersionUID = 17161717L; 

	/** repositoryName property */
	private String repositoryName;

	/** description property */
	private String description;
	
	/** description/oai-identifier/scheme */
	private String oaiIdentifierScheme;

    /** description/oai-identifier/domainName */
	private String oaiIdentifierDomainName;

	/** description/oai-identifier/repositoryIdentifier */
	private String oaiIdentifierRepositoryIdentifier;
	
	/** description/oai-identifier/sampleIdentifier */
	private String oaiIdentifierSampleIdentifier;

	/** baseUrl property */
	private String baseUrl;

	/** adminEmail property */
	private String adminEmail;

	/** deletedRecord property */
	private String deletedRecord;

	/** protocolVersion property */
	private String protocolVersion;

	/** granuality property */
	private String granularity;

	/** compression property */
	private String[] compression = {};

	/** setsChunk_maxNumberOfRecords property */
	private int setsChunk_maxNumberOfRecords;

	/** setsChunk_maxSizeInBytes property */
	private int setsChunk_maxSizeInBytes;

	/** setsChunk_maxNumberOfRecords property */
	private int recordsChunk_maxNumberOfRecords;

	/** setsChunk_maxSizeInBytes property */
	private int recordsChunk_maxSizeInBytes;

	/** setsChunk_maxNumberOfRecords property */
	private int identifiersChunk_maxNumberOfRecords;

	/** setsChunk_maxSizeInBytes property */
	private int identifiersChunk_maxSizeInBytes;

	/** maxSimultaneousRequest property */
	private int maxSimultaneousRequest;

	/** expirationDate property */
	private int expirationDate = -1;
	
	/** used MarcXML schema file */
	private String schema;

	/** Storage type: MySQL|mixed|Lucene */
	private String storageType;
	
	private int maxCacheLifetime = 60;

	private String submitButton;

	/*
	 * Generated Methods
	 */

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(repositoryName);
		sb.append(", " + description);
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
		return sb.toString();
	}
	
	/** 
	 * Returns the maxSimultaneousRequest.
	 * @return int
	 */
	public int getMaxSimultaneousRequest() {
		return maxSimultaneousRequest;
	}

	/** 
	 * Set the maxSimultaneousRequest.
	 * @param maxSimultaneousRequest The maxSimultaneousRequest to set
	 */
	public void setMaxSimultaneousRequest(int maxSimultaneousRequest) {
		this.maxSimultaneousRequest = maxSimultaneousRequest;
	}

	/** 
	 * Returns the granularity.
	 * @return String
	 */
	public String getGranularity() {
		return granularity;
	}

	/** 
	 * Set the granularity.
	 * @param granularity The granularity to set
	 */
	public void setGranularity(String granularity) {
		this.granularity = granularity;
	}

	/** 
	 * Returns the setChunk_maxNumberOfRecords.
	 * @return String
	 */
	public int getSetsChunk_maxNumberOfRecords() {
		return setsChunk_maxNumberOfRecords;
	}

	/** 
	 * Set the setChunk_maxNumberOfRecords.
	 * @param setChunk_maxNumberOfRecords The setChunk_maxNumberOfRecords to set
	 */
	public void setSetsChunk_maxNumberOfRecords(
			int setChunk_maxNumberOfRecords) {
		setsChunk_maxNumberOfRecords = setChunk_maxNumberOfRecords;
	}

	/** 
	 * Returns the compression.
	 * @return String
	 */
	public String[] getCompression() {
		return compression;
	}

	/** 
	 * Set the compression.
	 * @param compression The compression to set
	 */
	public void setCompression(String[] compression) {
		this.compression = compression;
	}

	/** 
	 * Returns the setsChunk_maxSizeInBytes.
	 * @return String
	 */
	public int getSetsChunk_maxSizeInBytes() {
		return setsChunk_maxSizeInBytes;
	}

	/** 
	 * Set the setsChunk_maxSizeInBytes.
	 * @param setsChunk_maxSizeInBytes The setsChunk_maxSizeInBytes to set
	 */
	public void setSetsChunk_maxSizeInBytes(int setsChunk_maxSizeInBytes) {
		this.setsChunk_maxSizeInBytes = setsChunk_maxSizeInBytes;
	}

	/** 
	 * Returns the description.
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/** 
	 * Set the description.
	 * @param description The description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** 
	 * Returns the baseUrl.
	 * @return String
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/** 
	 * Set the baseUrl.
	 * @param baseUrl The baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/** 
	 * Returns the expirationDate.
	 * @return String
	 */
	public int getExpirationDate() {
		return expirationDate;
	}

	/** 
	 * Set the expirationDate.
	 * @param expirationDate The expirationDate to set
	 */
	public void setExpirationDate(int expirationDate) {
		this.expirationDate = expirationDate;
	}

	/** 
	 * Returns the deletedRecord.
	 * @return String
	 */
	public String getDeletedRecord() {
		return deletedRecord;
	}

	/** 
	 * Set the deletedRecord.
	 * @param deletedRecord The deletedRecord to set
	 */
	public void setDeletedRecord(String deletedRecord) {
		this.deletedRecord = deletedRecord;
	}

	/** 
	 * Returns the adminEmail.
	 * @return String
	 */
	public String getAdminEmail() {
		return adminEmail;
	}

	/** 
	 * Set the adminEmail.
	 * @param adminEmail The adminEmail to set
	 */
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	/** 
	 * Returns the protocolVersion.
	 * @return String
	 */
	public String getProtocolVersion() {
		return protocolVersion;
	}

	/** 
	 * Set the protocolVersion.
	 * @param protocolVersion The protocolVersion to set
	 */
	public void setProtocolVersion(String protocolVersion) {
		this.protocolVersion = protocolVersion;
	}

	/** 
	 * Returns the repositoryName.
	 * @return String
	 */
	public String getRepositoryName() {
		return repositoryName;
	}

	/** 
	 * Set the repositoryName.
	 * @param repositoryName The repositoryName to set
	 */
	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
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

	public String getSubmitButton() {
		return submitButton;
	}

	public void setSubmitButton(String submitButton) {
		this.submitButton = submitButton;
	}

	public String getOaiIdentifierDomainName() {
		return oaiIdentifierDomainName;
    }

	public void setOaiIdentifierDomainName(String oaiIdentifierDomainName) {
		this.oaiIdentifierDomainName = oaiIdentifierDomainName;
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

	public int getMaxCacheLifetime() {
		return maxCacheLifetime;
	}

	public void setMaxCacheLifetime(int maxCacheLifetime) {
		this.maxCacheLifetime = maxCacheLifetime;
	}
}