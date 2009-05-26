/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai;

/**
 * Simple bean for a metadata format. It is used for registring a metadata format.
 * A metadata format contains a metadataPrefix - which is a mandatory parameter
 * in several OAI requests and this is the conventional name of a metadata format -,
 * its XML schema file's URL, its namespace URI and the xslt file name. Currently
 * only one XSLT file is supported, which transform from marc21 schema to the
 * given metadata schema.
 * @author Peter Kiraly
 */
public class MetadataFormat {
	
	/** the name of the metadata format */
	private String metadataPrefix;
	
	/** XSD file's URL */
	private String schema;
	
	/** the namespace of metadata */ 
	private String metadataNamespace;
	
	/** the XSLT file's name connected to the formatName */
	private String xsltFileName;
	
	public MetadataFormat(){}
	
	/**
	 * Create a new MetadataFormat
	 * @param metadataPrefix {@link #metadataPrefix}
	 * @param xsltFileName {@link #xsltFileName}
	 */
	public MetadataFormat(String metadataPrefix, String xsltFileName){
		this.metadataPrefix = metadataPrefix;
		this.xsltFileName = xsltFileName;
	}

	/**
	 * Get the {@see #metadataPrefix}
	 * @return
	 */
	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	/**
	 * Set the {@see #metadataPrefix}
	 * @return
	 */
	public void setMetadataPrefix(String metadataPrefix) {
		this.metadataPrefix = metadataPrefix;
	}
	
	/**
	 * Get the {@see #metadataNamespace}
	 * @return
	 */
	public String getMetadataNamespace() {
		return metadataNamespace;
	}

	/**
	 * Set the {@see #metadataNamespace}
	 * @return
	 */
	public void setMetadataNamespace(String metadataNamespace) {
		this.metadataNamespace = metadataNamespace;
	}

	/**
	 * Get the {@see #schema}
	 */
	public String getSchema() {
		return schema;
	}

	/**
	 * Set the {@see #schema}
	 */
	public void setSchema(String schema) {
		this.schema = schema;
	}

	/**
	 * Get the {@see #xsltFileName}
	 */
	public String getXsltFileName() {
		return xsltFileName;
	}

	/**
	 * Set the {@see #xsltFileName}
	 */
	public void setXsltFileName(String xsltFileName) {
		this.xsltFileName = xsltFileName;
	}
	
	/**
	 * The textual representation of the metadata format object
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("metadataPrefix: " + metadataPrefix);
		sb.append(", schema: " + schema);
		sb.append(", metadataNamespace: " + metadataNamespace);
		sb.append(", xsltFileName: " + xsltFileName);
		return sb.toString();
	}
}
