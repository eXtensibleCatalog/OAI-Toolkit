/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.struts.form;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 03-03-2008
 * 
 * XDoclet definition:
 * @struts.form name="oaiRequestForm"
 */
public class OaiRequestForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	/** verb property */
	private String verb;
	
	/** XML response */
	private String xml;
	
	/** request url */
	private String requestUrl;
	
	/** response date */
	private String responseDate;
	
	/** An optional argument with a UTCdatetime value, which specifies 
	 * a lower bound for datestamp-based selective harvesting */
	private String from;
	
	/** An optional argument with a UTCdatetime value, which specifies 
	 * a upper bound for datestamp-based selective harvesting */
	private String until;
	
	/** A required argument, which specifies that headers should be returned 
	 * only if the metadata format matching the supplied metadataPrefix is 
	 * available or, depending on the repository's support for deletions, 
	 * has been deleted. The metadata formats supported by a repository and 
	 * for a particular item can be retrieved using the ListMetadataFormats 
	 * request. */
	private String metadataPrefix;
	
	/** an optional argument with a setSpec value , which specifies set criteria 
	 * for selective harvesting */
	private String set;
	
	/** an exclusive argument with a value that is the flow control token returned 
	 * by a previous ListIdentifiers request that issued an incomplete list. */
	private String resumptionToken;
	
	/** GetRecord's identifier parameter */
	private String identifier;

	/** do cache */
	private boolean cacheable = true;

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(String responseDate) {
		this.responseDate = responseDate;
	}

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

	/** 
	 * Returns the verb.
	 * @return String
	 */
	public String getVerb() {
		return verb;
	}

	/** 
	 * Set the verb.
	 * @param verb The verb to set
	 */
	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	public void setMetadataPrefix(String metadataPrefix) {
		this.metadataPrefix = metadataPrefix;
	}

	public String getResumptionToken() {
		return resumptionToken;
	}

	public void setResumptionToken(String resumptionToken) {
		this.resumptionToken = resumptionToken;
	}

	public String getSet() {
		return set;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public String getUntil() {
		return until;
	}

	public void setUntil(String until) {
		this.until = until;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public boolean isCacheable() {
		return cacheable;
	}

	public void setCacheable(boolean cacheable) {
		this.cacheable = cacheable;
	}

	public void setCache(String cacheable) {
		if(cacheable.equals("1") || cacheable.equals("true") || cacheable.equals("on")) {
			this.cacheable = true;
		} else {
			this.cacheable = false;
		}
	}
}