/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.StringReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;


import org.apache.log4j.Logger;
//import org.jdom.Document;
import org.jdom.Element;
//import org.jdom.JDOMException;
//import org.jdom.Namespace;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetDTO;
import info.extensiblecatalog.OAIToolkit.configuration.OAIConfiguration;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsMgr;
import info.extensiblecatalog.OAIToolkit.oai.Constants;
import info.extensiblecatalog.OAIToolkit.oai.ErrorCodes;
import info.extensiblecatalog.OAIToolkit.oai.MetadataFormat;
import info.extensiblecatalog.OAIToolkit.oai.RecordListResult;
import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.oai.dataproviders.FacadeDataProvider;
import info.extensiblecatalog.OAIToolkit.oai.dataproviders.LuceneFacadeDataProvider;
import info.extensiblecatalog.OAIToolkit.oai.dataproviders.MySQLFacadeDataProvider;
import info.extensiblecatalog.OAIToolkit.struts.form.OaiRequestForm;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import info.extensiblecatalog.OAIToolkit.utils.XMLUtil;
import info.extensiblecatalog.OAIToolkit.utils.XsltTransformator;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo.RequestState;

/**
 * A facade class to create the response to the different OAI verb requests
 * @author Peter Kiraly
 * @version 1.0
 */
public class Facade {
	
	/** The logger object */
    	private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();
	
	/**
	 * The list of possible OAI verbs
	 */
	private static final List<String> legalVerbs = Arrays.asList(new String[]{
		"Identify", "ListMetadataFormats", "ListSets", "ListIdentifiers",
		"ListRecords", "GetRecord"
	});
	
	/**
	 * Is the result cachable?
	 */
	private boolean cacheable = true;
	
	/** MARC21 schema URL */
	private static String SCHEMA_URL = 
		"http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd";
	
	/*
	private static final Namespace marcNS = Namespace.getNamespace("marc",
									"http://www.loc.gov/MARC21/slim");
	
	private static final Namespace xsiNS = Namespace.getNamespace("xsi",
								"http://www.w3.org/2001/XMLSchema-instance");
	*/
	
	/**
	 * The standard MARCXML schema and namespace
	 */
	private static final String NS_DECL_BEGINING = 
		"<record xmlns:marc=\"http://www.loc.gov/MARC21/slim\" "
		+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
		+ " xsi:schemaLocation=\"http://www.loc.gov/MARC21/slim ";
	
	/** Namespace declaration URL */
	private static String NS_DECL;
	
	/** The extension for the cache's XML files */
	private static final String XML_EXT = ".xml";
	
	/** The extension for the cache's token files */
	private static final String TOKEN_EXT = ".tkn";
	
	/** The XSLT transformator */
	protected XsltTransformator transformator;
	
	//ApplInfo.oaiconfiguration;
	FacadeDataProvider dataProvider;

	/** OAI request form bean */
	private OaiRequestForm form;
	
	/** The OAI verb parameter */
	private String verb;

	/** The OAI from parameter */
	private String from;
	
	/** The OAI until parameter */
	private String until;

    /** The colon_delimter parameter */
	private static final String colon_delimiter = ":";
    
    /** The slash_delimiter parameter */
	private static final String slash_delimiter = "/";

    /** The slash_delimiter parameter */
	private static final String underscore_delimiter = "_";

	/** The OAI metadataPrefix parameter */
	private String metadataPrefix;
	
	/** The OAI set parameter */
	private String set;
	
	/** The OAI identifier parameter */
	private String identifier;
	
	/** The OAI resumptionToken parameter */
	private String resumptionToken;
	
	/** The token's identifier */
	private String tokenId;
	
	/** Offset of a result set from the first (0th) record */
	private int offset = 0;
	
	/** The OAI identifier's prefix */
	private static final String idPrefix = 
		ApplInfo.oaiConf.getOaiIdentifierScheme() 
		+ colon_delimiter
		+ ApplInfo.oaiConf.getOaiIdentifierDomainName()
		+ colon_delimiter
        + ApplInfo.oaiConf.getOaiIdentifierRepositoryIdentifier()
        + slash_delimiter;
	
	/** time to build and manipulate DOM tree */
	private long domBuildTime = 0;
	
	/** timer for measuring XSLT transformation */
	private long xslTransformTime = 0;
	
	/**
	 * Creates a new Facade object
	 * @param oaiForm The OAI request form bean
	 */
	public Facade(OaiRequestForm oaiForm) {
        
		form = oaiForm;
		verb = form.getVerb();
		from = form.getFrom();
		until = form.getUntil();
		metadataPrefix = form.getMetadataPrefix();
		set = form.getSet();
		identifier = form.getIdentifier();
		resumptionToken = form.getResumptionToken();
		prglog.info("[PRG] " + ApplInfo.oaiConf.getStorageType());
		if(ApplInfo.oaiConf.getStorageType().equals(StorageTypes.MYSQL)) {
			dataProvider = new MySQLFacadeDataProvider();
		} else if(ApplInfo.oaiConf.getStorageType().equals(StorageTypes.MIXED)) {
			dataProvider = new MySQLFacadeDataProvider();
		} else if(ApplInfo.oaiConf.getStorageType().equals(StorageTypes.LUCENE)) {
			dataProvider = new LuceneFacadeDataProvider();
		}
		if(form.isCacheable()) {
			cacheable = true;
		}

        boolean res = parseResumptionToken();
        //prglog.info("Value of boolean returned from resumptionToken is " + res);
        if (res == true){
        dataProvider.setParams(tokenId, from, until, set, metadataPrefix,
				offset);
		initCacheRegister();
        }

		//RecordListResult result = initial_parseResumptionToken();
        //if (result!=null) {
        //    form.setXml(result.getContent());
        //}
        }
        
		
	/**
	 * Creates the common header for all responses
	 * @param url The requested URL
	 */
	public void setResponseHeader(StringBuffer url) {

		Element responseDate = new Element("responseDate");
		responseDate.addContent(
			new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
					.format(new Date()));
		form.setResponseDate(XMLUtil.format.outputString(responseDate));

		Element requestEl = new Element("request");
		requestEl.addContent(url.toString());
		if(null != verb && legalVerbs.contains(verb)) {
			requestEl.setAttribute("verb", verb);
		}
		if(null != from) {
			requestEl.setAttribute("from", from);
		}
		if(null != until) { 
			requestEl.setAttribute("until", until);
		}
		if(null != metadataPrefix) { 
			requestEl.setAttribute("metadataPrefix", metadataPrefix);
		}
		if(null != set) {
			requestEl.setAttribute("set", set);
		}
		if(null != identifier) {
			requestEl.setAttribute("identifier", identifier);
		}
		if(null != resumptionToken) {
			requestEl.setAttribute("resumptionToken", resumptionToken);
		}

		form.setRequestUrl(XMLUtil.format.outputString(requestEl));
	}

	/**
	 * Create response to the Identify verb. The parameters arrive in the
	 * form (which is a FormBean). The XML response will be the value of
	 * the form xml field. 
	 * @param form The agruments
	 */
	public void doIdentify() {
		
		Element root = new Element("Identify");
		root.addContent(XMLUtil.xmlEl("repositoryName", 
				ApplInfo.oaiConf.getRepositoryName()));
		root.addContent(XMLUtil.xmlEl("baseURL", 
				ApplInfo.oaiConf.getBaseUrl()));
		root.addContent(XMLUtil.xmlEl("protocolVersion", 
				ApplInfo.oaiConf.getProtocolVersion()));
		root.addContent(XMLUtil.xmlEl("adminEmail", 
				ApplInfo.oaiConf.getAdminEmail()));
		root.addContent(XMLUtil.xmlEl("earliestDatestamp", 
				dataProvider.getEarliestDatestamp()));
		root.addContent(XMLUtil.xmlEl("deletedRecord", 
				ApplInfo.oaiConf.getDeletedRecord()));
		root.addContent(XMLUtil.xmlEl("granularity", 
				ApplInfo.oaiConf.getGranularity()));
		String[] compressions = ApplInfo.oaiConf.getCompression();
		for(String compression : compressions) {
			root.addContent(XMLUtil.xmlEl("compression", compression));
		}
		root.addContent(
			XMLUtil.xmlEl("description", null)
				.addContent(ApplInfo.oaiConf.getOaiIdentifier()));
		form.setXml(XMLUtil.format.outputString(root).replaceAll(
				" xmlns=\"\"", ""));
	}
	
	/**
	 * Create an XML response to the ListMetadataFormat verb.
	 * @param form
	 */
	public void doIllegalVerb() {
		form.setXml(ErrorCodes.badVerbError());
	}

	/**
	 * Create an XML response to the ListMetadataFormat verb.
	 * @param form
	 */
	public void doListMetadataFormats() {
		String content;
		try {
			content = getMetadataFormats();
		} catch(Exception e){
			form.setXml(XMLUtil.xmlTag("error", e.getMessage()));
			return;
		}
		form.setXml(content);
	}

	/**
	 * Create response to the ListSets verb. List the sets in XML format.
	 * The parameters arrive in the form parameter (which is a FormBean). The XML 
	 * response will be the value of the form xml field. 
	 * @param form The arguments
	 */
	public void doListSets() {

		SetsMgr mgr = new SetsMgr();
		StringBuffer xml = new StringBuffer();
		try {
			List<DataTransferObject> all = mgr.get(new SetDTO());
			Element root = new Element("ListSets");
			for(DataTransferObject setObj : all){
				SetDTO set = (SetDTO) setObj;
				root.addContent(
					XMLUtil.xmlEl("set", null)
						.addContent(XMLUtil.xmlEl("setSpec", set.getSetSpec()))
						.addContent(XMLUtil.xmlEl("setName", set.getSetName())));
			}
			form.setXml(XMLUtil.format.outputString(root));
		} catch(SQLException e) {
			xml.delete(0, xml.length());
			xml.append("<exception>");
			xml.append(e);
			xml.append("</exception>");
			form.setXml(xml.toString());
		} catch(Exception e) {
			xml.delete(0, xml.length());
			xml.append("<exception>");
			xml.append(e);
			xml.append("</exception>");
			form.setXml(xml.toString());
		}
	}
	
	/**
	 * Create response for the ListIdentifiers verb
	 */
	public void doListIdentifiers() {
		doCachedSearch();
	}
	
	/**
	 * Create response for the ListRecords verb
	 */
	public boolean doListRecords() {
		boolean hasMoreResult = doCachedSearch();
		return hasMoreResult;
	}
	
	/** 
	 * Cached search: if there is a cache (RequestState.FINISHED), 
	 * or the cache is in making (RequestState.STARTED) read it from 
	 * the cache (after waiting it to be finished), if there is no 
	 * cache and even it is not started, read from database
	 */
	private boolean doCachedSearch() {
		
		boolean hasMoreResult = true;
		// use the cache
		if(cacheable == true) {
			long t0 = System.currentTimeMillis();
			
			// clear
			clearOldCache();
			checkCacheConsistency();
			String cacheId = getCacheId();
			RecordListResult result;
			prglog.info("[PRG] Select strategy for " + cacheId + ": "
					+ "cacheId null: " + (cacheId == null) 
					+ ", hasCache: " + hasCache(cacheId) + ", registered: "
					+ ApplInfo.cacheRegister.containsKey(cacheId));
			if(cacheId == null 
				|| (!hasCache(cacheId) 
					&& !ApplInfo.cacheRegister.containsKey(cacheId))) {
				prglog.info("[PRG] strategy->direct request");
				result = handleRecordLists(from, until, metadataPrefix, set, 
						resumptionToken, verb);
			} else {
				prglog.info("[PRG] strategy->get from cache");
				if(ApplInfo.cacheRegister.containsKey(cacheId)
					&& ApplInfo.cacheRegister.get(cacheId) 
						== RequestState.STARTED){
					boolean isFirst = true;
					while(ApplInfo.cacheRegister.get(cacheId) 
							== RequestState.STARTED) {
						if(isFirst) {
							prglog.info("[PRG] waiting for cache");
							isFirst = false;
						}
						try {
							Thread.sleep(100);
						} catch(InterruptedException e) {
							prglog.error("[PRG] " + e);
						}
					}
				}
				result = getCache(cacheId);
			}
			long t1 = System.currentTimeMillis();
			form.setXml(result.getContent());
			prglog.info("[PRG] first phase: " + (t1-t0) + ", " + (System.currentTimeMillis()-t1));
			if(result.getNextResumptionToken() != null) {
				t0 = System.currentTimeMillis();
				resumptionToken = result.getNextResumptionToken();
                parseResumptionToken();
				//RecordListResult result_resumption_token = parseResumptionToken();
                //form.setXml(result_resumption_token.getContent());
				prglog.info("[PRG] actual next resumption token: " + resumptionToken
						+ ", second phase: " + (System.currentTimeMillis()-t0));
			} else {
				hasMoreResult = false;
			}
		} else {
			RecordListResult result = handleRecordLists(from, until, metadataPrefix, set, 
					resumptionToken, verb);
			form.setXml(result.getContent());
			if(result.getNextResumptionToken() == null) {
				hasMoreResult = false;
			}
		}
		return hasMoreResult;
	}
	
	public void createCache() {
		String cacheId = getCacheId();
		if(!hasCache(cacheId)) {
			RecordListResult result = handleRecordLists(from, until, 
				metadataPrefix, set, resumptionToken, verb);
			setCache(cacheId, result);
		}
	}

	public String getCacheId() {
		if(resumptionToken != null) {
			return tokenId + '_' + offset;
		}
		return null;
	}

	private boolean hasCache(String cacheId) {
		return (new File(ApplInfo.cacheDirectory, cacheId + XML_EXT).exists()
			&& new File(ApplInfo.cacheDirectory, cacheId + TOKEN_EXT).exists());
	}

	private RecordListResult getCache(String cacheId) {
		RecordListResult result = new RecordListResult();
		try {
			result.setContent(TextUtil.readFileAsString(
					new File(ApplInfo.cacheDirectory, cacheId + XML_EXT)));
			result.setNextResumptionToken(TextUtil.readFileAsString(
					new File(ApplInfo.cacheDirectory, cacheId + TOKEN_EXT)));
		} catch(IOException e) {
			result = null;
			prglog.error("[PRG] " + e);
		}
		return result;
	}

	private void setCache(String cacheId, RecordListResult result) {
		try {
			TextUtil.writeFile(new File(ApplInfo.cacheDirectory, cacheId + XML_EXT), 
					result.getContent());
			TextUtil.writeFile(new File(ApplInfo.cacheDirectory, cacheId + TOKEN_EXT), 
					(result.getNextResumptionToken() != null)
					? result.getNextResumptionToken() : "null");
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
	}

	/**
	 * Delete those file from cache which are older, than the "maximum lifetime
	 * of a cached file" value (see 
	 * {@link OAIConfiguration#getMaxCacheLifetime()}). This deletion runs 
	 * once in a given interval, which can be set in 
	 * {@link ApplInfo#cacheClearInterval} (the default value is 5 minutes).
	 */
	private void clearOldCache() {
		// run deletion only if enable. Disable deletion if maxCacheLifetime 
		// is less then zero
		if(ApplInfo.oaiConf.getMaxCacheLifetime() < 0) {
			return;
		}
		long now = System.currentTimeMillis();
		// run deletion once in five minutes
		if(ApplInfo.lastCacheClear > -1 && 
			(now - (ApplInfo.cacheClearInterval) <= ApplInfo.lastCacheClear)) {
			return;
		}
		ApplInfo.lastCacheClear = now;
		long validTimeRange = now - ApplInfo.oaiConf.getMaxCacheLifetime();
		File[] files = ApplInfo.cacheDirectory.listFiles();
		for(File file : files) {
			if(file.lastModified() < validTimeRange) {
				String resupmtionToken = file.getName().substring(0, 
						file.getName().indexOf("."));
				if(ApplInfo.cacheRegister.containsKey(resupmtionToken)) {
					ApplInfo.cacheRegister.remove(resupmtionToken);
				}
				boolean deleted = file.delete();
				if(!deleted) {
					prglog.error("[PRG] Unable to delete cache file: " + file);
				}
			}
		}
	}

	/**
	 * Load cached files into the register of cached files
	 */
	private void initCacheRegister() {
		if(ApplInfo.cacheRegister.isEmpty()) {
			File[] files = ApplInfo.cacheDirectory.listFiles();
			for(File file : files) {
				String name = file.getName();
				if(name.equals(".") || name.equals("..")){
					continue;
				}
				String resupmtionToken = name.substring(0, name.indexOf("."));
				if(resupmtionToken == null || resupmtionToken.trim().equals("")) {
					continue;
				}
				if(!ApplInfo.cacheRegister.containsKey(resupmtionToken)) {
					ApplInfo.cacheRegister.put(resupmtionToken, 
							RequestState.FINISHED);
					prglog.info("[PRG] add " + resupmtionToken + " to cache register");
				}
			}
		}
	}

	/**
	 * Check the cache consistency with file system. If somebody deletes a
	 * cache file, we should delete it from registry
	 */
	private void checkCacheConsistency() {
		long t0 = System.currentTimeMillis();
		if(!ApplInfo.cacheRegister.isEmpty()) {
			List<File> files = Arrays.asList(ApplInfo.cacheDirectory.listFiles());
			List<String> deletables = new ArrayList<String>();
			for(Entry<String, RequestState> entry : ApplInfo.cacheRegister.entrySet()) {
				if(entry.getValue() == RequestState.FINISHED
						&& !files.contains(new File(entry.getKey() + ".xml"))){
					deletables.add(entry.getKey());
				}
			}
			for(String key : deletables) {
				ApplInfo.cacheRegister.remove(key);
			}
		}
		prglog.info("[PRG] registry consistency took: " + (System.currentTimeMillis()-t0));
	}

	/**
	 * Create response to the GetRecord verb
	 */
	public void doGetRecord() {

		prglog.info("[PRG] doGetRecord() identifier: " + identifier);
		
		if(null == identifier || 0 == identifier.trim().length()) {
			form.setXml(ErrorCodes.badArgumentError(
					"Missing identifier parameter"));
			return;
		}

        try {
			setupTransformator(metadataPrefix);
		} catch(Exception e) {
			form.setXml(e.getMessage());
			return;
		}

        char[] crs = identifier.toCharArray();
        int index = 0;
        for (int i = 0; i < crs.length; i++) {
           if (Character.isDigit(crs[i])) {
                prglog.debug("[PRG]first index = " + i);
                index = i;
                break;
            }
        }

        String recordTypeParam = identifier.substring(0,index);
        String idParam = identifier.substring(index);

		//String[] identifierParts = identifier.split(
		//		underscore_delimiter);
		//String recordTypeParam = identifierParts[identifierParts.length-2];
		//String idParam = identifierParts[identifierParts.length-1];
		prglog.info("[PRG] recordTypeParam: " + recordTypeParam);
		prglog.info("[PRG] idParam: " + idParam);

		/*
		String id = identifier.substring(
				identifier.lastIndexOf(
					ApplInfo.oaiConf.getOaiIdentifierDelimiter())+1);
		*/
		Integer id;
		Integer recordType;
		try {
			id = Integer.valueOf(idParam);
			prglog.info("[PRG] id: " + id);
			recordType = ApplInfo.setIdsByName.get(recordTypeParam);
		} catch(NumberFormatException e) {
			prglog.error("[PRG] " + e);
			form.setXml(ErrorCodes.badArgumentError("The identifier parameter"
					+ " should end with an integer."));
			return;
		}

		if(null == recordType){
			form.setXml(ErrorCodes.idDoesNotExistError());
			return;
		}

		StringBuffer xml = new StringBuffer();
		try {
			List<DataTransferObject> records = dataProvider.getRecord(id, 
					recordType,
					Arrays.asList(new String[]{"record_id", "record_type"}));

			if(null == records || 0 == records.size()){
				form.setXml(ErrorCodes.idDoesNotExistError());
				return;
				//xml.append(ErrorCodes.noRecordsMatchError());
			} else {
				prglog.info("[PRG] record set size: " + records.size());
				int insertedRecords = 0;
				for(DataTransferObject r : records) {
					RecordDTO record = (RecordDTO)r;
					xml.append(transformRecord(record, verb));
					insertedRecords++;
				}
			}
			
		} catch(Exception e){
			e.printStackTrace();
		}

		form.setXml(XMLUtil.xmlTag("GetRecord", xml.toString()));
        
	}

	/**
	 * Get records from database
	 * @param from The OAI form parameter
	 * @param until The OAI until parameter
	 * @param metadataPrefix The OAI metadataPrefix parameter
	 * @param set The OAI set parameter
	 * @param resumptionToken The OAI resumptionToken parameter 
	 * @param verb The OAI verb (command)
	 * @return The list of records 
	 */
	private RecordListResult handleRecordLists(String from, String until, 
			String metadataPrefix, String set, String resumptionToken, 
			String verb) {
		
		prglog.info("[PRG] handleRecordLists: " + from + ", " + until 
				+ ", " + metadataPrefix + ", " + set + ", " + resumptionToken
				 + ", " + verb);
		RecordListResult result = new RecordListResult(); 

        boolean var = parseResumptionToken();
        if (var)  {
            if(verb.equals("ListRecords")) {

			// the metadataPrefix is mandatory
			if(dataProvider.getMetadataPrefix() == null) {
				prglog.error("[PRG] no metadata");
				result.setContent(ErrorCodes.noMetadataFormatsError(
						"There must be a metadata format parameter."));
				return result;
			}

			try {
				// set up an XSLT transformator to transform records to
				// different schemas
				setupTransformator(dataProvider.getMetadataPrefix());
			} catch(Exception e) {
				prglog.error("[PRG] " + e.getMessage() + " " + dataProvider.getMetadataPrefix());
				result.setContent(XMLUtil.xmlTag("error", e.getMessage()));
				return result;
			}
            }
		int recordLimit = 0;
		if(verb.equals(Constants.LIST_IDENTIFIERS)) {
			recordLimit = ApplInfo.oaiConf
							.getIdentifiersChunk_maxNumberOfRecords();
		} else if(verb.equals(Constants.LIST_RECORDS)) {
			recordLimit = ApplInfo.oaiConf
							.getRecordsChunk_maxNumberOfRecords();
		}
		prglog.info("[PRG] recordLimit: " + recordLimit);
		dataProvider.setRecordLimit(recordLimit);

		int maxLength = 0;
		if(verb.equals(Constants.LIST_IDENTIFIERS)) {
			maxLength = ApplInfo.oaiConf.getIdentifiersChunk_maxSizeInBytes();
		} else if(verb.equals(Constants.LIST_RECORDS)) {
			maxLength = ApplInfo.oaiConf.getRecordsChunk_maxSizeInBytes();
		}
		prglog.info("[PRG] maxLength: " + maxLength);
		
		dataProvider.prepareQuery();

        int totalRecordNr = dataProvider.getTotalRecordCount();
		prglog.info("[PRG] totalRecordNr: " + totalRecordNr);
		if(dataProvider.hasBadResumptionTokenError()) {
			result.setContent(ErrorCodes.badResumptionTokenError());
			return result;
		}
		//prglog.info("get total record");

		boolean hasMore = false;
		if(totalRecordNr > (offset + recordLimit)) {
			if(null == tokenId) {
				prglog.info("[PRG] ->storeResumptionToken");
                tokenId = dataProvider.storeResumptionToken();
				//sql, counterSQL, metadataPrefix);
			}
			hasMore = true;
		}
		
		//List<DataTransferObject> records;
		try {
            //prglog.info("Value of the offset before select records" + offset);
			dataProvider.selectRecords();
			StringBuffer xml = new StringBuffer();
			if(0 == totalRecordNr){
				xml.append(ErrorCodes.noRecordsMatchError());
			} else {
				int insertedRecords = 0;
				prglog.info("[PRG] while transformRecord");
				long readTime = 0;
				long transformTime = 0;
				xslTransformTime = 0;
				domBuildTime = 0;
				long t1;
				long t2;
				while(dataProvider.hasNextRecord()) {
					t1 = System.currentTimeMillis();
					RecordDTO record = (RecordDTO)dataProvider.nextRecord();
					readTime += (System.currentTimeMillis()-t1);
					if(xml.length() >= maxLength) {
						prglog.info("[PRG] The record exceed the maxSizeInBytes at " +
								"the " + insertedRecords + "th record");
						hasMore = true;
						break;
					}
					t2 = System.currentTimeMillis();
					xml.append(transformRecord(record, verb));
					transformTime += (System.currentTimeMillis()-t2);
					insertedRecords++;
				}
				prglog.info("[PRG] /while transformRecord. " +
						"insertedRecords: " + insertedRecords
						+ ", readTime: " + readTime 
						+ "/" + Math.ceil((double)readTime/insertedRecords) 
						+ " (inside this: getIdTime: " + dataProvider.getIdTime() 
						+ "/" + Math.ceil((double)dataProvider.getIdTime()/insertedRecords)
						+ ", getDocTime: " + dataProvider.getDocTime() 
						+ "/" + Math.ceil((double)dataProvider.getDocTime()/insertedRecords)
						+ ", doc2RecordTime: " + dataProvider.getDoc2RecordTime() 
						+ "/" + Math.ceil((double)dataProvider.getDoc2RecordTime()/insertedRecords)
						+ "), transformTime: " + transformTime 
						+ "/" + Math.ceil((double)transformTime/insertedRecords)
						+ " (inside this: xslTransformTime: " + xslTransformTime 
						+ "/" + Math.ceil((double)xslTransformTime/insertedRecords)
						+ ", domBuildTime: " + domBuildTime 
						+ "/" + Math.ceil((double)domBuildTime/insertedRecords) + 
						")"
						);
				if(hasMore) {
					result.setNextResumptionToken(
							tokenId + "|" + (offset + insertedRecords));
					xml.append(
						XMLUtil.xmlTag("resumptionToken",
							result.getNextResumptionToken(),
							new String[]{
								"cursor", "" + offset, 
								"completeListSize", ""+totalRecordNr}));
				}
				
				if(verb.equals("ListIdentifiers")) {
					result.setContent(XMLUtil.xmlTag("ListIdentifiers", xml.toString()));
					return result;
				} else if(verb.equals("ListRecords")) {
					result.setContent(XMLUtil.xmlTag("ListRecords", xml.toString()));
					return result;
				} else {
					result.setContent(xml.toString());
					return result;
				}
			}
			result.setContent(xml.toString());
			return result;

		} catch(Exception e){
			prglog.error("[PRG] " + e);
			e.printStackTrace();
		}
		return result;
	}

    else {
        prglog.error("[PRG] Bad Resumption token " );

        result.setContent((ErrorCodes.badResumptionTokenError(
						"The resumption token entered does not exist. Please enter the correct resumption token.")));
        return result;
      }
    }
	
	/**
	 * Create an OAI-PMH-compatible list from the metadata format
	 * listing stored in metadataFormats.xml
	 * @return
	 * @throws Exception
	 */
	private String getMetadataFormats() throws Exception {
		String resDir = ApplInfo.getResourceDir();
		File xmlSource = new File(resDir, "metadataFormats.xml");
		File xsltFile = new File(resDir, "meta2oai.xsl"); //
		XsltTransformator transformator;
		try {
			if(!xsltFile.exists()) {
				prglog.info("[PRG] Inexistent xsl: " + xsltFile.getAbsolutePath());
				throw new Exception("Inexistent xsl: " + xsltFile.getAbsolutePath());
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		String content = null;
		try {
			if(!xmlSource.exists()) {
				prglog.info("[PRG] Inexistent xml: " + xmlSource);
				throw new Exception("Inexistent xml: " + xmlSource);
			}
			transformator = new XsltTransformator(xsltFile);
			content = TextUtil.readFileAsString(xmlSource.getAbsolutePath());
			content = transformator.transform(content);
		} catch (IOException e) {
			prglog.info("[PRG] IOException: " + e);
			throw new Exception("Unknown exception");
		} catch(TransformerConfigurationException e) {
			prglog.info("[PRG] TransformerConfigurationException: " + e);
			e.printStackTrace();
		} catch(TransformerException e) {
			prglog.info("[PRG] TransformerException: " + e);
			throw new Exception("Unknown exception");
		}
		return content;
	}
	
	/**
	 * add id,
	 * add xmlns declarations
	 * transform with xsl
	 * @param record
	 * @param verb
	 * @return
	 */
	private String transformRecord(RecordDTO record, String verb) {

		StringBuilder sb = new StringBuilder();
		sb.append(XMLUtil.xmlTag("identifier", 
			record.getXcOaiId().toString()));
            //idPrefix
			//+ ApplInfo.setNamesById.get(record.getRecordType())
            //+ record.getRecordId().toString()));

		// add datestamp
		sb.append(XMLUtil.xmlTag("datestamp", 
				TextUtil.timestampToUTC(record.getModificationDate())));

		List<DataTransferObject> sets = dataProvider.getSetsOfRecord(
				record.getRecordId());
		
		// add sets
		//logger.info(sets.size());
		if(null != sets) {
			for(DataTransferObject set : sets) {
				sb.append(XMLUtil.xmlTag("setSpec", 
					ApplInfo.setNamesById.get(
							((SetToRecordDTO)set).getSetId())));
			}
		}

		String header;
		if(record.getIsDeleted() == true) {
			header = XMLUtil.xmlTag("header", sb.toString(), 
					new String[]{"status", "deleted"});
		} else {
			header = XMLUtil.xmlTag("header", sb.toString());
		}
		if("ListIdentifiers".equals(verb)) {
			return header;
		} else if(record.getIsDeleted() == true) {
			return XMLUtil.xmlTag("record", header);
		}
		
		String content = "";
		try {
			// extract xml from DB
			content = dataProvider.getXmlOfRecord(record.getRecordId(), 
					record.getRecordType());

			if(content == null) {
				prglog.error("[PRG] Record #" + record.getExternalId() 
						+ " has problem: xml is null or empty");
			}
			
			long b0 = System.currentTimeMillis();
			content = content.replaceAll("<\\?xml [^<>]+\\?>", "")
				.replaceAll("<collection[^<>]+>", "")
				.replace("</collection>", "")
				.replace("<record", NS_DECL)
				.replace("</", "</marc:")
				.replaceAll("<([^/])", "<marc:$1")
			;

			/*
			Document doc = XMLUtil.builder.build(new StringReader(content));
			Element recordEl = doc.getRootElement()
									.getChild("record", XMLUtil.marcNs);
			
			recordEl.setAttribute("schemaLocation",
					"http://www.loc.gov/MARC21/slim " + schemaUrl,
					xsiNS);
			recordEl.setNamespace(marcNS);
			content = XMLUtil.format.outputString(recordEl);
			*/
			domBuildTime += System.currentTimeMillis() - b0;
			
			if(!dataProvider.getMetadataPrefix().equals("marc21")
					&& !dataProvider.getMetadataPrefix().equals("marcxml")
					&& transformator != null){
				long t1 = System.currentTimeMillis();
				content = transformator.transform(content);
				xslTransformTime += System.currentTimeMillis() - t1;
			}
		//} catch(JDOMException e) {
		//	e.printStackTrace();
		} catch(TransformerException e) {
			e.printStackTrace();
		}
		content = content.replaceAll("<\\?xml[^<>]*>", "");

		if("ListRecords".equals(verb) || "GetRecord".equals(verb)) {
			return XMLUtil.xmlTag("record", 
					header + XMLUtil.xmlTag("metadata", content));
		}
		
		return "";
	}

    /**
     * Parses the resumption token and returns a boolean value of true if it was a success.
     * @return
     */
	private boolean parseResumptionToken() {
        boolean var = true;
        try {
         if (resumptionToken != null) {
			String[] tokens = resumptionToken.split("\\|");
			tokenId = tokens[0];
			offset  = Integer.parseInt(tokens[1]);
            dataProvider.setOffset(offset);
            //prglog.info("Value of the offset in parseResumptionToken is" + offset);
            }

        } catch (Exception e) {
        prglog.error("[PRG] Bad Resumption token " + e);

            form.setXml((ErrorCodes.badResumptionTokenError(
						"The resumption token entered does not exist. Please enter the correct resumption token.")).toString());
            var = false;
            //prglog.info("[PRG] Variable value in Exception " + var);
        }
        //prglog.info("[PRG] Variable value in Exception " + var);
        return var;
	}


    /**
	 * Set up an XSLT transformator to transform records to
	 * different XML schemas. 
	 * @param metadataPrefix The current metadata prefix, which stand for the
	 * used format. The records should be in this format. 
	 * @throws Exception
	 */
	public void setupTransformator(String metadataPrefix) throws Exception {
		
		if(metadataPrefix == null || metadataPrefix.trim().length() == 0) {
			throw new Exception(ErrorCodes.noMetadataFormatsError(
					"Cause: no metadatada prefix specified in the request."));
		}
		MetadataFormat metadataFormat = 
			ApplInfo.metadataFormats.getMetadataFormat(metadataPrefix);
		if(metadataFormat == null) {
			throw new Exception(ErrorCodes.noMetadataFormatsError(
					"Cause: unsupproted metadatada prefix: " + metadataPrefix));
		}

		try {
			if(metadataFormat.getXsltFileName() != null){
				// the XSLT file should be in the resource directory
				File xsltFile = new File(ApplInfo.getResourceDir(), 
						metadataFormat.getXsltFileName());
				if(!xsltFile.exists()) {
					throw new Exception(ErrorCodes.noMetadataFormatsError(
						"Cause: the specified metadata format doesn't have"
						+ " transformator stylesheet" 
						+ " (" + metadataFormat.getXsltFileName() + ")." 
						+ " Please report the error to the administrator: " 
						+ ApplInfo.oaiConf.getAdminEmail()));
				}
				transformator = new XsltTransformator(
						xsltFile.getAbsolutePath());
			} else {
				transformator = null;
			}
		} catch(TransformerConfigurationException e) {
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set up the URL of custom MARCXML schema file, when the administrator 
	 * choose this option in the configuration interface, and refresh the
	 * namespace declaration value (see {@link #NS_DECL}).
	 * @param baseUrl
	 */
	public void setMarcXMLSchema(String baseUrl) {
		if(ApplInfo.oaiConf.getSchema().equals("custom")) {
			SCHEMA_URL = baseUrl + "/schema/" + "MARC21slim_custom.xsd";
		}
		NS_DECL = NS_DECL_BEGINING + SCHEMA_URL + "\"";
	}
	
	public String getResumptionToken() {
		return resumptionToken;
	}
}
