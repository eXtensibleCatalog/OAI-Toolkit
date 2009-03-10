/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai;

import java.util.ArrayList;
import java.util.List;

import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;

/**  
 * Simple register of metadata formats. If you want to search for a metadata format,
 * use the {@link #getMetadataFormat(String)} method
 * @author Peter Kiraly
 */
public class MetadataFormats {
	
	/** list of metadata formats */
	private List<MetadataFormat> metadataFormats;
	
	/** Simple no-argument constructor */
	public MetadataFormats () {}

	/** 
	 * Get the metadata formats as List of <code>MetadataFormat</code>s 
	 * @return The list of registred metadata formats
	 */
	public List<MetadataFormat> getMetadataFormats() {
		return metadataFormats;
	}

	/**
	 * Regirster the list of metadata formats
	 * @param metadataFormats The metadata formats to register
	 */
	public void setMetadataFormats(List<MetadataFormat> metadataFormats) {
		this.metadataFormats = metadataFormats;
	}
	
	/**
	 * Register a new metadata format
	 * @param metadataFormats
	 */
	public void addMetadataFormat(MetadataFormat metadataFormat) {
		if(null == metadataFormats) {
			metadataFormats = new ArrayList<MetadataFormat>();
		}
		metadataFormats.add(metadataFormat);
	}
	
	/**
	 * Get the string representation of the registered metadata formats
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(MetadataFormat f : metadataFormats) {
			sb.append(f.toString()).append(ApplInfo.LN);
		}
		return sb.toString();
	}
	
	/**
	 * Get a MetadataFormat object by the name of its metadataPrefix.
	 * @param metadataPrefix The metadata prefix (short name) of the metadata format 
	 * @return The requested metadata format or null if it doesn't exist in that name
	 */
	public MetadataFormat getMetadataFormat(String metadataPrefix) {
		for(MetadataFormat metadataFormat : metadataFormats) {
			if(metadataPrefix.equals(metadataFormat.getMetadataPrefix())) {
				return metadataFormat;
			}
		}
		return null;
	}
}
