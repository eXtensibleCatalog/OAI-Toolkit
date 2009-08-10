/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.DTOs;

/**
 * Table for big xml files
 * @author Péter Király
 */
public class BigxmlDTO extends DataTransferObject {

	/** The ID of the record */
	private Integer recordId;
	
	/** The MARCXML content of the record. 
	 * Accessors: {@link #getXml}, {@link #setXml} */
	private String xml;
	
	public BigxmlDTO() {}

	/**
	 * Create a new record with a given ID
	 * @param recordId The record's ID
	 */
	public BigxmlDTO(Integer recordId) {
		this.recordId = recordId;
	}

	/**
	 * Creates a new record with a given XML
	 * @param xml The XML of the record
	 */
	public BigxmlDTO(String xml) {
		this.xml = xml;
	}

	public BigxmlDTO(Integer recordId, String xml) {
		this.recordId = recordId;
		this.xml = xml;
	}

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer id) {
		this.recordId = id;
	}

	/**
	 * Get the MARCXML of the record
	 * @return {@link #xml}
	 */
	public String getXml() {
		return xml;
	}

	/**
	 * Set the {@link #xml}
	 * @param xml {@link #xml}
	 */
	public void setXml(String xml) {
		this.xml = xml;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("recordId: ").append(recordId);
		sb.append(", xml: ").append(xml);
		return sb.toString();
	}
}
