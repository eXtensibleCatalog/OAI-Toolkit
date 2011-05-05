/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.DTOs;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An object representative of a records record.
 */
public class RecordDTO extends DataTransferObject {
	
	/** The identifier of the record. 
	 * Accessors: {@link #getId}, {@link #setId} */
	private Integer recordId;
	
	/** The identifier of the repository (the MARC 003 field). 
	 * Accessors: {@link #getRepositoryCode}, {@link #setRepositoryCode} */
	private String repositoryCode = null;
	
	/** The id of the MARC record (external_id in DB). 
	 * Accessors: {@link #getExternalId}, {@link #setExternalId} */
	private String externalId;

    /** The XC oai id of the MARC record (xc_oaiid in DB).
	 * Accessors: {@link #getExternalId}, {@link #setExternalId} */
	private String xcOaiId;
	
	/**
	 * The numeric part of XC oai id
	 * This is used for sorting and comparisons during resumption processing
	 */
	private Integer xcId;

    /** The id of record type (which is equal the id of set created
	 * according to the record type) */
	private Integer recordType;
	
	/** The date of creation (creation_date in DB).
	 * Accessors: {@link #getCreationDate}, {@link #setCreationDate} */
	private Timestamp creationDate;
	
	/** The date of last modification (modification_date in DB).
	 * Accessors: {@link #getModificationDate}, {@link #setModificationDate} */
	private Timestamp modificationDate;
	/* private Date deletionDate; */
	
	/** flag to signal if the record is deleted in the ILS or not (is_deleted in DB). 
	 * Accessors: {@link #getIsDeleted}, {@link #setIsDeleted} */
	private Boolean isDeleted = false;
	
	//private Boolean isBigxml = false;
	
	/** the XML root's name (root_name in DB).
	 * Accessors: {@link #getRootName}, {@link #setRootName} */
	private String rootName = "";
	
	/** the XML root's namespace (root_namespace in DB).
	 * Accessors: {@link #getRootNamespace}, {@link #setRootNamespace} */
	private String rootNamespace = "";
	
	public RecordDTO() {}

	public RecordDTO(Integer recordId) {
		this.recordId = recordId;
	}

	public RecordDTO(Map map) {
		if(map.containsKey("record_id")) {
			recordId = (Integer) map.get("record_id");
		}
		if(map.containsKey("repository_code")) {
			repositoryCode = (String) map.get("repository_code");
		}
		if(map.containsKey("externalid")) {
			externalId = (String) map.get("externalid");
		}
        if(map.containsKey("xc_oaiid")) {
			xcOaiId = (String) map.get("xc_oaiid");
		}
		if(map.containsKey("xc_id")) {
			xcId = (Integer) map.get("xc_id");
		}
		if(map.containsKey("record_type")) {
			recordType = (Integer) map.get("record_type");
		}
		if(map.containsKey("created")) {
			creationDate = (Timestamp) map.get("created");
		}
		if(map.containsKey("modified")) {
			modificationDate = (Timestamp) map.get("modified");
		}
		/*
		if(map.containsKey("deleted")) {
			deletionDate = (Date) map.get("deleted");
		}
		*/
		if(map.containsKey("is_deleted")) {
			isDeleted = (Boolean)map.get("is_deleted");
		}
		if(map.containsKey("root_name")) {
			rootName = (String) map.get("root_name");
		}
		if(map.containsKey("root_namespace")) {
			rootNamespace = (String) map.get("root_namespace");
		}
	}

	/**
	 * @return {@link #id}
	 */
	public Integer getRecordId() {
		return recordId;
	}
	/**
	 * Set the {@link #id}
	 * @param id {@link #id}
	 */
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	/**
	 * @return {@link #repositoryCode}
	 */
	public String getRepositoryCode() {
		return repositoryCode;
	}
	/**
	 * Set the {@link #repositoryCode}
	 * @param repositoryCode {@link #repositoryCode}
	 */
	public void setRepositoryCode(String repositoryCode) {
		this.repositoryCode = repositoryCode;
	}	
	
	/**
	 * @return {@link #externalId}
	 */
	public String getExternalId() {
		return externalId;
	}
	/**
	 * Set the {@link #externalId}
	 * @param externalId {@link #externalId}
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

    /**
	 * @return {@link #xcOaiId}
	 */
	public String getXcOaiId() {
		return xcOaiId;
	}
	/**
	 * Set the {@link #xcOaiId}
	 * @param externalId {@link #xcOaiId}
	 */
	public void setXcOaiId(String xcOaiId) {
		this.xcOaiId = xcOaiId;
	}
	
	/**
	 * Get the {@link #recordType}
	 * @return {@link #recordType}
	 */
	public Integer getRecordType() {
		return recordType;
	}

	/**
	 * Set the {@link #recordType}
	 * @param recordType {@link #recordType}
	 */
	public void setRecordType(Integer recordType) {
		this.recordType = recordType;
	}

	/**
	 * Get the {@link #xcId}
	 * @return {@link #xcId}
	 */
	public Integer getXcId() {
		return xcId;
	}

	/**
	 * Set the {@link #xcId}
	 * @param recordType {@link #xcId}
	 */
	public void setXcId(Integer xcId) {
		this.xcId = xcId;
	}

	
	/**
	 * @return {@link #creationDate}
	 */
	public Timestamp getCreationDate() {
		return creationDate;
	}
	/**
	 * Set the {@link #creationDate}
	 * @param creationDate {@link #creationDate}
	 */
	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}
	
	/**
	 * @return {@link #modificationDate}
	 */
	public Timestamp getModificationDate() {
		return modificationDate;
	}
	/**
	 * Set the {@link #modificationDate}
	 * @param modificationDate {@link #modificationDate}
	 */
	public void setModificationDate(Timestamp modificationDate) {
		this.modificationDate = modificationDate;
	}
	/*
	public Date getDeletionDate() {
		return deletionDate;
	}
	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	*/
	
	/**
	 * @return {@link #rootName}
	 */
	public String getRootName() {
		return rootName;
	}
	/**
	 * Set the {@link #rootName}
	 * @param rootName {@link #rootName}
	 */
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	
	/**
	 * @return {@link #externalId}
	 */
	public String getRootNamespace() {
		return rootNamespace;
	}
	/**
	 * Set the {@link #rootNamespace}
	 * @param rootNamespace {@link #rootNamespace}
	 */
	public void setRootNamespace(String rootNamespace) {
		this.rootNamespace = rootNamespace;
	}

	/**
	 * @return {@link #isDeleted}
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	/**
	 * Set the {@link #isDeleted}
	 * @param isDeleted {@link #isDeleted}
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	/*
	public Boolean getIsBigxml() {
		return isBigxml;
	}

	public void setIsBigxml(Boolean isBigxml) {
		this.isBigxml = isBigxml;
	}
	*/

	/**
	 * Show every fields and its value in a string
	 * @return The string representation of the record
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("recordId: ").append(recordId);
		sb.append(", externalId: ").append(externalId);
        sb.append(", repositoryCode: ").append(repositoryCode);
        sb.append(", xcOaiId: ").append(xcOaiId);
		sb.append(", xcId: ").append(xcId);
		sb.append(", recordType: ").append(recordType);
		sb.append(", creationDate: ").append(creationDate);
		sb.append(", modificationDate: ").append(modificationDate);
		//sb.append(", deletionDate: ").append(deletionDate);
		sb.append(", isDeleted: ").append(isDeleted);
		//sb.append(", isBigxml: ").append(isBigxml);
		sb.append(", rootName: ").append(rootName);
		sb.append(", rootNamespace: ").append(rootNamespace);
		return sb.toString();
	}

	/**
	 * Show an "abstract" form, which doesn't include the id and the
	 * XML data. It is used for printing a short form of the object.
	 * @return The short/abstract form of the record
	 */
	public String toAbstract() {
		StringBuilder sb = new StringBuilder();
		sb.append("externalId: ").append(externalId);
        sb.append(", repositoryCode: ").append(repositoryCode);
        sb.append(", xcOaiId: ").append(xcOaiId);
		sb.append(", xcId: ").append(xcId);
		sb.append(", recordType: ").append(recordType);
		sb.append(", creationDate: ").append(creationDate);
		sb.append(", modificationDate: ").append(modificationDate);
		sb.append(", isDeleted: ").append(isDeleted);
		//sb.append(", isBigxml: ").append(isBigxml);
		sb.append(", rootName: ").append(rootName);
		sb.append(", rootNamespace: ").append(rootNamespace);
		return sb.toString();
	}

	/**
	 * Compare every fields of two documents. True if every fields
	 * has the same value.
	 * @param other The other RecordDTO object
	 * @return true if the two objects equals, false if there's a difference
	 */
	public boolean equals(RecordDTO other) {
		return 
			other instanceof RecordDTO 
			&& ((null == recordId && null == other.getRecordId())
				|| (null != recordId && null != other.getRecordId()  
					&& recordId.equals(other.getRecordId())))
			&& equalData(other)
		;
	}

	/**
	 * Same as equals() except it doesn't compare the id, because when we
	 * get the object from a MARC record, it hasn't got the ID came from the
	 * database.
	 * @param other The other RecordDTO object
	 * @return true if the two object's data are the same, false if not
	 */
	public boolean equalData(RecordDTO other) {
		return 
			((externalId == null && other.getExternalId() == null) 
				|| (externalId != null && other.getExternalId() != null 
					&& externalId.equals(other.getExternalId())))
			&& ((repositoryCode == null && other.getRepositoryCode() == null) 
				|| (repositoryCode != null && other.getRepositoryCode() != null
					&& repositoryCode.equals(other.getRepositoryCode())))			
			&& ((recordType == null && other.getRecordType() == null) 
				|| (recordType != null && other.getRecordType() != null
					&& recordType.equals(other.getRecordType())))
			&& ((creationDate == null && other.getCreationDate() == null) 
				|| (creationDate != null && other.getCreationDate() != null
					&& creationDate.equals(other.getCreationDate())))
			&& ((modificationDate == null && other.getModificationDate() == null) 
				|| (modificationDate != null && other.getModificationDate() != null
					&& modificationDate.equals(other.getModificationDate())))
			&& ((isDeleted == null && other.getIsDeleted() == null) 
				|| (isDeleted != null && other.getIsDeleted() != null 
					&& isDeleted.equals(other.getIsDeleted())))
			/*
			&& ((isBigxml == null && other.getIsBigxml() == null) 
				|| (isBigxml != null && other.getIsBigxml() != null 
					&& isBigxml.equals(other.getIsBigxml())))
			*/
			&& ((rootName == null && other.getRootName() == null)
				|| (rootName != null && other.getRootName() != null
					&& rootName.equals(other.getRootName())))
			&& ((rootNamespace == null && other.getRootNamespace() == null)
				|| (rootNamespace != null && other.getRootNamespace() != null 
					&& rootNamespace.equals(other.getRootNamespace())))
			/*
			&& ((xml == null && other.getXml() == null)
				|| (xml != null && other.getXml() != null
					&& xml.equals(other.getXml())))
			*/
		;
	}

	/**
	 * Get the array of the different fields between two RecordDTO objects
	 * @param other The other RecordDTO object
	 * @return The array of field name, where there is a difference between
	 * the two objects
	 */
	public String[] difference(RecordDTO other) {
		List<String> differentFields = new ArrayList<String>();
		if((externalId == null && other.getExternalId() != null)
			|| (externalId != null && other.getExternalId() == null)
			|| (externalId != null && other.getExternalId() != null 
				&& ! externalId.equals(other.getExternalId()))) {
			differentFields.add("externalId");
		} else if((recordType == null && other.getRecordType() != null)
				||	(recordType != null && other.getRecordType() == null)
				|| (recordType != null && other.getRecordType() != null
					&& ! recordType.equals(other.getRecordType()))) {
				differentFields.add("recordType");
		} else if((repositoryCode == null && other.getRepositoryCode() != null)
				||	(repositoryCode != null && other.getRepositoryCode() == null)
				|| (repositoryCode != null && other.getRepositoryCode() != null
					&& ! repositoryCode.equals(other.getRepositoryCode()))) {
				differentFields.add("repositoryCode");
		} else if((creationDate == null && other.getCreationDate() != null)
			||	(creationDate != null && other.getCreationDate() == null)
			|| (creationDate != null && other.getCreationDate() != null
				&& ! creationDate.equals(other.getCreationDate()))) {
			differentFields.add("creationDate");
		} else if((modificationDate == null && other.getModificationDate() != null)
			|| (modificationDate != null && other.getModificationDate() == null)
			|| (modificationDate != null && other.getModificationDate() != null
				&& ! modificationDate.equals(other.getModificationDate()))) {
			differentFields.add("modificationDate");
		} else if((isDeleted == null && other.getIsDeleted() != null)
			|| (isDeleted != null && other.getIsDeleted() == null)
			|| (isDeleted != null && other.getIsDeleted() != null
				&& ! isDeleted.equals(other.getIsDeleted()))){
			differentFields.add("isDeleted");
		/*
		} else if((isBigxml == null && other.getIsBigxml() != null)
				|| (isBigxml != null && other.getIsBigxml() == null)
				|| (isBigxml != null && other.getIsBigxml() != null
					&& ! isBigxml.equals(other.getIsBigxml()))){
				differentFields.add("isBigxml");
		*/
		} else if((rootName == null && other.getRootName() != null)
			|| (rootName != null && other.getRootName() == null)
			|| (rootName != null && other.getRootName() != null
				&& ! rootName.equals(other.getRootName()))) {
			differentFields.add("rootName");
		} else if((rootNamespace == null && other.getRootNamespace() != null)
			|| (rootNamespace != null && other.getRootNamespace() == null)
			|| (rootNamespace != null && other.getRootNamespace() != null
				&& ! rootNamespace.equals(other.getRootNamespace()))){
			differentFields.add("rootNamespace");
		/*
		} else if((xml == null && other.getXml() != null)
			|| (xml != null && other.getXml() == null)
			|| (xml != null && other.getXml() != null 
				&& ! xml.equals(other.getXml()))) {
			differentFields.add("xml");
		*/
		}
		
		return differentFields.toArray(new String[differentFields.size()]);
	}
}
