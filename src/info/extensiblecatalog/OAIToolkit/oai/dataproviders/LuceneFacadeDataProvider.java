/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai.dataproviders;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.ResumptionTokenDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.db.ResumptionTokensMgr;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * 
 * @author Peter Kiraly
 *
 */
public class LuceneFacadeDataProvider extends BasicFacadeDataProvider
		implements FacadeDataProvider {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static Logger logger = Logging.getLogger();

	/** Manager of resumption_token records */
	private static ResumptionTokensMgr tokenMgr = new ResumptionTokensMgr();


	private String queryString;
	private Query  query;
	private TopDocs   hits;
	private int    currentRecord;
	private int    lastRecord;
	private long   getIdTime      = 0;
	private long   doc2RecordTime = 0;
	private long   getDocTime     = 0;
    
	
	public String getEarliestDatestamp() {
		try {
			return TextUtil.timestampToUTC(
				TextUtil.luceneToTimestamp(
					ApplInfo.luceneSearcher.getEarliestDatestamp()));
		} catch(Exception e) {
                        prglog.error("[PRG] " + e);
			return "";
		}
	}

	public List<DataTransferObject> getRecord(String xcOaiId) {
		List<DataTransferObject> list = new ArrayList<DataTransferObject>();

		Integer docId[] = new Integer[1];
		Document doc = ApplInfo.luceneSearcher.getRecordByXcOaiID(xcOaiId, docId);
		if(doc != null) {
			list.add(doc2RecordDTO(doc, docId[0]));
		}

		return list;
	}
	
	public List<DataTransferObject> getRecord(Integer id, Integer recordType, 
			List<String> filter) { 
		prglog.info("[PRG] id: " + id + ", recordType: " + recordType);
		List<DataTransferObject> list = new ArrayList<DataTransferObject>();

		Document doc = ApplInfo.luceneSearcher.getRecordByID(id);
		if(doc != null) {
			list.add(doc2RecordDTO(doc, id));
		}

		/*
		List<Object[]> docs = ApplInfo.luceneSearcher
			.getRecordByIDAndRecordType(id, recordType);
		for(int i=0; i<docs.size(); i++) {
			Object[] obj = docs.get(i);
			list.add(doc2RecordDTO((Document)obj[1], (Integer)obj[0]));
		}
		*/
		return list;
	}
	
	public void selectRecords() {
        lastRecord = recordLimit;
		if(lastRecord > recordLimit) {
			lastRecord = recordLimit;
		}
		if (lastRecord > hits.scoreDocs.length) {
			lastRecord = hits.scoreDocs.length;
		}
		currentRecord = 0; // count each iteration		
	}
	
	public boolean hasNextRecord() {
		return currentRecord < lastRecord;
	}
	
	public boolean hasMoreRecords() {
		return hits.scoreDocs.length > recordLimit;
	}
    


	public DataTransferObject nextRecord() {
		RecordDTO recordDTO = null;
		int id;
        long t1 = System.currentTimeMillis();
		long t2 = 0;
		long t3 = 0;
		try {
			t2 = System.currentTimeMillis();
			getIdTime += (t2-t1);
            id = hits.scoreDocs[currentRecord].doc;
            Document doc = ApplInfo.luceneSearcher.getDoc(id);
			t3 = System.currentTimeMillis();
			getDocTime += (t3-t2);
			recordDTO = doc2RecordDTO(doc, id);
			doc2RecordTime += (System.currentTimeMillis()-t3);
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
		}
        currentRecord++;
		return recordDTO;
	}

	
	public List<DataTransferObject> getSetsOfRecord(Integer recordId) {
		Document doc = ApplInfo.luceneSearcher.getRecordByID(recordId);
		List<DataTransferObject> sets = new ArrayList<DataTransferObject>();
		sets.add(doc2SetToRecordDTO(doc, recordId));
		return sets;
	}

	public List<DataTransferObject> getSetsOfRecord(Integer recordId, Integer recordType) {
		List<Object[]> docs = ApplInfo.luceneSearcher.getRecordByIDAndRecordType(recordId, recordType);
		Object[] pair = docs.get(0);
		prglog.info("[PRG] docId: " + pair[0]);
		Document doc = (Document)pair[1];
        //Document doc = docs.get(0);
		prglog.info("[PRG] doc: " + doc);
		List<DataTransferObject> sets = new ArrayList<DataTransferObject>();
		sets.add(doc2SetToRecordDTO(doc, recordId));
		return sets;
	}

	public String getXmlOfRecord(Integer recordId, Integer recordType) {
		return ApplInfo.luceneSearcher.getXmlOfRecord(recordId, recordType);
	}
	
	public void prepareQuery() {
		if(null != tokenId) {
			ResumptionTokenDTO tokenDTO = getSQLsFromResumptionToken(tokenId);
			if(tokenDTO == null){
				badResumptionTokenError = true;
			} else {
				queryString = tokenDTO.getQuery();
				metadataPrefix = tokenDTO.getMetadataPrefix();
			}
		} else {
			extractQueriesFromParameters(from, until, set);
			if(0 >= queryString.length()){
				prglog.error("[PRG] query string is null");
			}
		}
		Sort sort = new Sort(new SortField("xc_id", SortField.INT));
		
		try {
			// query recordLimit+1 (one extra) so that way we'll know if we're done with our list
			if (lastRecordRead > 0) {
				String from = String.format("%d", lastRecordRead);
				hits = ApplInfo.luceneSearcher.searchRange(queryString, "xc_id", Integer.valueOf(from), Integer.MAX_VALUE, false, false, sort, recordLimit+1);
			} else {
					hits = ApplInfo.luceneSearcher.search(queryString, sort, recordLimit+1);
			}
		} catch (Exception ex) {
			hits = null;
		}

	}

    public int getTotalRecordCount() {    	   	
	    	Sort sort = null;
	    	Query query = null;
	    	QueryParser parser = new QueryParser(Version.LUCENE_30, "id", new StandardAnalyzer(Version.LUCENE_30));
			try {
				query = parser.parse(queryString);
			} catch (org.apache.lucene.queryParser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
	        BitSet ids = ApplInfo.luceneSearcher.searchForBits(query, sort);
	        return ids.cardinality();
	}
    
	
	public String getMetadataPrefix() {
		if(metadataPrefix != null) {
			return metadataPrefix; 
		} else if(tokenId != null) {
			ResumptionTokenDTO tokenDTO = getSQLsFromResumptionToken(tokenId);
			if(tokenDTO != null) {
				metadataPrefix = tokenDTO.getMetadataPrefix();
				return metadataPrefix;
			}
		}
		return null;
	}

	private ResumptionTokenDTO getSQLsFromResumptionToken(String resumptionToken) {

		ResumptionTokenDTO tokenDTO = new ResumptionTokenDTO();
		tokenDTO.setId(Integer.valueOf(resumptionToken));
		try {
			List<DataTransferObject> tokens = tokenMgr.get(tokenDTO);
			if(null != tokens) {
				prglog.info("[PRG] tokens.size: " + tokens.size());
				return (ResumptionTokenDTO)tokens.get(0);
			} else {
				prglog.info("[PRG] token not found");
				return null;
			}
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
			e.printStackTrace();
			return null;
		}
	}

	private void extractQueriesFromParameters(String from, String until, 
			String set) {
		StringBuffer queryBuffer = new StringBuffer();
		if (null == from) {
			// if this is a clean harvest, there is no need to serve
			// deleted records
			queryBuffer.append("+is_deleted:false");				
		}

		// if until is not set, we set it implicitly to "now"
		if(null == until) {
			Date date = new Timestamp(new Date().getTime());
            SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
            TimeZone tz = TimeZone.getTimeZone( "UTC" );
            df.setTimeZone( tz );
            until = df.format(date);
		} else {
			until = TextUtil.utcToMysqlTimestamp(until);
		}

		if(null != from || null != until) {
			prglog.info("[PRG] " + from + ", " + until);
			if(null == from) {
				from = ApplInfo.luceneSearcher.showFirstTerm("modification_date");
			} else {
				from = TextUtil.utcToMysqlTimestamp(from);
			}
			prglog.info("[PRG] " + from + ", " + until);
			
			if (queryBuffer.length() > 0) {
				queryBuffer.append(" AND ");
			}

			queryBuffer.append("+modification_date:[\"" + from + "\" TO \"" 
					+ until + "\"]");
		}
		if(null != set) {
			// checking the set's existence
			if(ApplInfo.setIdsByName.containsKey(set)) {
				int setId = ApplInfo.setIdsByName.get(set);
				if (queryBuffer.length() > 0) {
					queryBuffer.append(" AND ");
				}
				queryBuffer.append("+set:" + setId);
			}
		}
		queryString = queryBuffer.toString();
		prglog.info("[PRG] " + queryString);
	}
	
	public String storeResumptionToken() {
		ResumptionTokenDTO tokenDTO = new ResumptionTokenDTO();
		tokenDTO.setQuery(queryString);
		tokenDTO.setQueryForCount("");
		tokenDTO.setMetadataPrefix(metadataPrefix);
        tokenDTO.setCreationDate(new Timestamp(new Date().getTime()));

        try {
            List<Integer> intids = tokenMgr.insert(tokenDTO);
            prglog.info("intids.get(0)" + intids.get(0));
			String resumptionToken = String.valueOf(intids.get(0));
            prglog.info("Resumption Token" + resumptionToken);
            return resumptionToken;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private RecordDTO doc2RecordDTO(Document doc, int id) {
		RecordDTO mainData = new RecordDTO(id);
		try {
			if(doc.get("creation_date") != null) {
				mainData.setCreationDate(TextUtil.luceneToTimestamp(
					doc.get("creation_date")));
			}
			if(doc.get("modification_date") != null) {
				mainData.setModificationDate(TextUtil.luceneToTimestamp(
					doc.get("modification_date")));
			}
		} catch(ParseException e) {
			prglog.error("[PRG] " + e);
		}
		mainData.setExternalId(doc.get("external_id"));
        mainData.setXcOaiId(doc.get("xc_oaiid"));
        mainData.setXcId(Integer.parseInt(doc.get("xc_id")));
		mainData.setIsDeleted(Boolean.valueOf(doc.get("is_deleted")));
		mainData.setRecordType(Integer.parseInt(doc.get("record_type")));
		return mainData;
	}
	
	private SetToRecordDTO doc2SetToRecordDTO(Document doc, Integer recordId) {
		SetToRecordDTO setsToRecordDTO = new SetToRecordDTO();
		setsToRecordDTO.setRecordId(recordId.intValue());
		setsToRecordDTO.setSetId(Integer.parseInt(doc.get("set")));
		return setsToRecordDTO;
	}

	public long getDoc2RecordTime() {
		return doc2RecordTime;
	}

	public long getIdTime() {
		return getIdTime;
	}

	public long getDocTime() {
		return getDocTime;
	}
}
