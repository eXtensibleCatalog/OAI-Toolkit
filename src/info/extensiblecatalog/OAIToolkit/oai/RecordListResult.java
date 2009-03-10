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
 * Bean for result list.
 * @author Kiraly Peter
 * @version $Id$
 */
public class RecordListResult {
	
	/** The content of the current XML response */
	private String content;

	/** The resumptionToken of the next request */
	private String nextResumptionToken;
	
	public RecordListResult() {
	}

	/**
	 * Initialize with content
	 * @param xml The content of XML response
	 */
	public RecordListResult(String xml) {
		this.content = xml;
	}
		
	/**
	 * Create a new record list with XML content and resumptionToken
	 * @param content The content of XML response
	 * @param nextResumptionToken The resumption token
	 */
	public RecordListResult(String content, String nextResumptionToken) {
		this.content = content;
		this.nextResumptionToken = nextResumptionToken;
	}

	/**
	 * Get the XML content of the response
	 * @return The content of XML response
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set the XML content of the response
	 * @param content The content of XML response
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Get the resumption token of the result
	 * @return The resumption token
	 */
	public String getNextResumptionToken() {
		return nextResumptionToken;
	}

	/**
	 * Set the resumption token of the result
	 * @param nextResumptionToken The resumption token
	 */
	public void setNextResumptionToken(String nextResumptionToken) {
		if(nextResumptionToken != null && !"null".equals(nextResumptionToken)) {
			this.nextResumptionToken = nextResumptionToken;
		}
	}
}
