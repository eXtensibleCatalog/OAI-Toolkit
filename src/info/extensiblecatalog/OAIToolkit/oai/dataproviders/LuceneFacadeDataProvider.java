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
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.ResumptionTokenDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.db.ResumptionTokensMgr;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import java.text.SimpleDateFormat;
import java.util.BitSet;
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
	private Hits   hits;
	private int    currentRecord;
	private int    lastRecord;
    private int    tempIndex;
	private long   getIdTime      = 0;
	private long   doc2RecordTime = 0;
	private long   getDocTime     = 0;
    //private BitSet range;
    private BitSet ids;
	//private HitIterator hitIterator;
    
	
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
        lastRecord = offset + recordLimit;
        //prglog.info("In select Records offset: " + offset );
        //prglog.info("In select Records recordLimit: " + recordLimit );

		if(lastRecord > ids.cardinality()) {
			lastRecord = ids.cardinality();
		}
		//hitIterator    = (HitIterator) hits.iterator();
		currentRecord  = offset;
		getIdTime      = 0;
		doc2RecordTime = 0;
		getDocTime     = 0;
        tempIndex = currentRecord;

        //bits = ApplInfo.luceneSearcher.getBits();
        //range = bits.get(currentRecord, lastRecord);
	}
	
	public boolean hasNextRecord() {
        //prglog.info("In HasNextRecord Current Record: " + currentRecord);
        //prglog.info("In HasNextRecord Last Record: " + lastRecord);
		return currentRecord < lastRecord;
	}

	public DataTransferObject nextRecord() {
		//Hit hit = (Hit)hitIterator.next();
		//DataTransferObject recordDTO = null;
		RecordDTO recordDTO = null;
		int id;
        long t1 = System.currentTimeMillis();
		long t2 = 0;
		long t3 = 0;
		try {

            //Document doc = ApplInfo.luceneSearcher.getDoc(currentRecord);
            //id =
			//id = hits.id(currentRecord);
            //ids.get(currentRecord);
			t2 = System.currentTimeMillis();
			getIdTime += (t2-t1);
			//Document doc = hit.getDocument();

            id = ids.nextSetBit(tempIndex);
            //prglog.info("Inserted Record ID: " +id);
            Document doc = ApplInfo.luceneSearcher.getDoc(id);

            //currentRecord = id;

            /*
			Document doc = hits.doc(currentRecord);
			id = hits.id(currentRecord); */

			/*
			prglog.info(id + ", " + doc.get("external_id") + ", " + doc.get("record_type") + ", " 
					+ doc.get("xml").substring(90, 300));
			*/
			//Document doc = ApplInfo.luceneSearcher.getDoc(id);//, 
				//ApplInfo.luceneSearcher.getAllFieldSelector());
			t3 = System.currentTimeMillis();
			getDocTime += (t3-t2);
            //prglog.info("In Next Record current Record "+ currentRecord);
            //prglog.info("The id of the record in the List of the records is " + id);
            tempIndex = id + 1;
			//id = currentRecord;
			recordDTO = doc2RecordDTO(doc, id);
			//prglog.info(recordDTO.getRecordId() + ", " + recordDTO.getExternalId() + ", " + recordDTO.getRecordType());
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
				query = ApplInfo.luceneSearcher.parseQuery(queryString);
				metadataPrefix = tokenDTO.getMetadataPrefix();
			}
		} else {
			extractQueriesFromParameters(from, until, set);
			if(null == query){
				prglog.error("[PRG] query is null");
			}
		}
	}

    public int getTotalRecordCount() {
		//Sort sort = Sort.INDEXORDER;
		//Sort sort = new Sort("modification_date");
		//Sort sort = null;

        Sort sort = null;
        ids = ApplInfo.luceneSearcher.searchForBits(query, sort);
        return ids.cardinality();

		//hits = ApplInfo.luceneSearcher.search(query, sort);
		//return hits.length();
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
		if(null != from || null != until) {
			prglog.info("[PRG] " + from + ", " + until);
			if(null == from) {
				from = ApplInfo.luceneSearcher.showFirstTerm("modification_date");
			} else {
				from = TextUtil.utcToMysqlTimestamp(from);
			}
			if(null == until) {
				Date date = new Timestamp(new Date().getTime());
                SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssZ" );
                TimeZone tz = TimeZone.getTimeZone( "UTC" );
                df.setTimeZone( tz );
                until = df.format(date);
			} else {
				until = TextUtil.utcToMysqlTimestamp(until);
			}
			prglog.info("[PRG] " + from + ", " + until);
			queryBuffer.append("+modification_date:[\"" + from + "\" TO \"" 
					+ until + "\"]");
		}
		if(null != set) {
			// checking the set's existence
			if(ApplInfo.setIdsByName.containsKey(set)) {
				int setId = ApplInfo.setIdsByName.get(set);
				queryBuffer.append("+set:" + setId);
			}
		}
		if(null == from && until == null && set == null) {
			//from  = getEarliestDatestamp();
			//from  = ApplInfo.luceneSearcher.getEarliestDatestamp();
			//until = new Timestamp(new Date().getTime()).toString();
			//queryBuffer.append("+modification_date:[\"" + from + "\" TO \"" 
			//	+ until + "\"]");
			queryBuffer.append("+is_deleted:(true OR false)");
		}
		queryString = queryBuffer.toString();
		prglog.info("[PRG] " + queryString);
		query = ApplInfo.luceneSearcher.parseQuery(queryString);
		prglog.info("[PRG] " + query);
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
