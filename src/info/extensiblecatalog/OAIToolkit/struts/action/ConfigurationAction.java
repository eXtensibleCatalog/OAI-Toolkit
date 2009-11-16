/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.struts.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import info.extensiblecatalog.OAIToolkit.configuration.OAIConfiguration;
import info.extensiblecatalog.OAIToolkit.struts.form.ConfigurationForm;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;

/** 
 * MyEclipse Struts
 * Creation date: 02-01-2008
 * 
 * XDoclet definition:
 * @struts.action path="/configuration" name="configurationForm" input="/form/configuration.jsp" scope="request" validate="true"
 */
public class ConfigurationAction extends Action {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		ConfigurationForm configForm = (ConfigurationForm) form;

		if(null != configForm.getSubmitButton()) { 
			// form submitted
			saveData(configForm, ApplInfo.oaiConf);
		} else { 
			// form displayed
			if(ApplInfo.oaiConf != null) {
				loadData(configForm, ApplInfo.oaiConf);
			}
		}

		return mapping.getInputForward();
	}
	
	/**
	 * Save the user inputs to the configuration file
	 * @param form
	 * @param conf
	 */
	private void saveData(ConfigurationForm form, OAIConfiguration conf) {
		
		conf.setRepositoryName(form.getRepositoryName());
		conf.setDescription(form.getDescription());
		conf.setOaiIdentifierScheme(form.getOaiIdentifierScheme());
		conf.setOaiIdentifierRepositoryIdentifier(form.getOaiIdentifierRepositoryIdentifier());
		conf.setOaiIdentifierDomainName(form.getOaiIdentifierDomainName());
		conf.setOaiIdentifierSampleIdentifier(form.getOaiIdentifierSampleIdentifier());
		conf.setBaseUrl(form.getBaseUrl());
		conf.setAdminEmail(form.getAdminEmail());
		conf.setDeletedRecord(form.getDeletedRecord());
		conf.setProtocolVersion(form.getProtocolVersion());
		conf.setGranularity(form.getGranularity());
		conf.setCompression(form.getCompression());
		conf.setSetsChunk_maxNumberOfRecords(
				form.getSetsChunk_maxNumberOfRecords());
		conf.setSetsChunk_maxSizeInBytes(form.getSetsChunk_maxSizeInBytes());
		conf.setRecordsChunk_maxNumberOfRecords(
				form.getRecordsChunk_maxNumberOfRecords());
		conf.setRecordsChunk_maxSizeInBytes(form.getRecordsChunk_maxSizeInBytes());
		conf.setIdentifiersChunk_maxNumberOfRecords(
				form.getIdentifiersChunk_maxNumberOfRecords());
		conf.setIdentifiersChunk_maxSizeInBytes(
				form.getIdentifiersChunk_maxSizeInBytes());
		conf.setMaxSimultaneousRequest(form.getMaxSimultaneousRequest());
		conf.setExpirationDate(form.getExpirationDate());
		conf.setSchema(form.getSchema());
		conf.setStorageType(form.getStorageType());
		conf.setMaxCacheLifetime(form.getMaxCacheLifetime() * 60 * 1000);
		prglog.info("[PRG] " + form.getSchema() + " -> " + conf.getSchema());

		conf.save();
	}

	/**
	 * Load the stored configuration data to the user form
	 * @param form
	 * @param conf
	 */
	private void loadData(ConfigurationForm form, OAIConfiguration conf) {
		conf.load();

		form.setRepositoryName(conf.getRepositoryName());
		form.setDescription(conf.getDescription());
		form.setOaiIdentifierScheme(conf.getOaiIdentifierScheme());
		form.setOaiIdentifierRepositoryIdentifier(conf.getOaiIdentifierRepositoryIdentifier());
		form.setOaiIdentifierDomainName(conf.getOaiIdentifierDomainName());
		form.setOaiIdentifierSampleIdentifier(conf.getOaiIdentifierSampleIdentifier());
		form.setBaseUrl(conf.getBaseUrl());
		form.setAdminEmail(conf.getAdminEmail());
		form.setDeletedRecord(conf.getDeletedRecord());
		form.setProtocolVersion(conf.getProtocolVersion());
		form.setGranularity(conf.getGranularity());
		form.setCompression(conf.getCompression());
		form.setSetsChunk_maxNumberOfRecords(
				conf.getSetsChunk_maxNumberOfRecords());
		form.setSetsChunk_maxSizeInBytes(conf.getSetsChunk_maxSizeInBytes());
		form.setRecordsChunk_maxNumberOfRecords(
				conf.getRecordsChunk_maxNumberOfRecords());
		form.setRecordsChunk_maxSizeInBytes(conf.getRecordsChunk_maxSizeInBytes());
		form.setIdentifiersChunk_maxNumberOfRecords(
				conf.getIdentifiersChunk_maxNumberOfRecords());
		form.setIdentifiersChunk_maxSizeInBytes(
				conf.getIdentifiersChunk_maxSizeInBytes());
		form.setMaxSimultaneousRequest(conf.getMaxSimultaneousRequest());
		form.setExpirationDate(conf.getExpirationDate());
		form.setSchema(conf.getSchema());
		form.setStorageType(conf.getStorageType());
		form.setMaxCacheLifetime(conf.getMaxCacheLifetime() / 60000);
	}
}