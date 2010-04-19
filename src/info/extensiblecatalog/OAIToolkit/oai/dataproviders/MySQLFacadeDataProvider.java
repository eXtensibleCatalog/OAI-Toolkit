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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.ResumptionTokenDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.XmlDTO;
import info.extensiblecatalog.OAIToolkit.db.ResumptionTokensMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.RecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsToRecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.XmlsMgr;
import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

public class MySQLFacadeDataProvider extends BasicFacadeDataProvider
		implements FacadeDataProvider {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static Logger logger = Logging.getLogger();

	/** Manager of records records */
	private static RecordsMgr mgr = new RecordsMgr();
	
	/** Manager of set records */
	private static SetsMgr setMgr = new SetsMgr();
	
	/** Manager of sets_to_records records */
	private static SetsToRecordsMgr setToRecordsMgr = new SetsToRecordsMgr();

	/** Manager of xml records */
	private static XmlsMgr xmlMgr = new XmlsMgr();

	/** Manager of resumption_token records */
	private static ResumptionTokensMgr tokenMgr = new ResumptionTokensMgr();
	
	private String sql = ""; 
	private String counterSQL = "";
	
	private List<DataTransferObject> result;
	private int currentRecord;
	private int lastRecord;
	
	public String getEarliestDatestamp() {
		String earliestDatestamp = "";
		try {
			RecordDTO mainData = new RecordDTO();
			earliestDatestamp = TextUtil.timestampToUTC(mgr.getEarliestDatestamp(mainData)
				.getModificationDate());
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
		}
		return earliestDatestamp;
	}
	
	public List<DataTransferObject> getRecord(String xcOaiId) {
		/* TODO: implement this method
		 * 
		 */
		return null;
	}

	public List<DataTransferObject> getRecord(Integer id, Integer recordType, 
			List<String> filter) { 
		try {
			RecordDTO mainData = new RecordDTO(id);
			mainData.setRecordType(recordType);

			return mgr.get(mainData, filter);
		} catch(SQLException e) {
			prglog.error("[PRG] " + e);
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
		}
		return null;
	}
	
	public void selectRecords() {
		sql += " LIMIT " + offset + "," + recordLimit;
		try {
			RecordDTO mainData = new RecordDTO();
			result = mgr.select(sql, mainData);
		} catch(IllegalAccessException e) {
			prglog.error("[PRG] " + e);
		} catch(SQLException e){
			prglog.error("[PRG] " + e);
			e.printStackTrace();
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
		}
		currentRecord = 0;
		lastRecord    = result.size();
		//return null;
	}
	
	public boolean hasNextRecord() {
		return currentRecord < lastRecord;
	}
	
	public DataTransferObject nextRecord() {
		DataTransferObject record = result.get(currentRecord++);
		return record;
	}
	
	public List<DataTransferObject> getSetsOfRecord(Integer recordId) {

		SetToRecordDTO setsToRecordDTO = new SetToRecordDTO();
		setsToRecordDTO.setRecordId(recordId);
		List<DataTransferObject> sets = null;
		try {
			sets = setToRecordsMgr.get(setsToRecordDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	public List<DataTransferObject> getSetsOfRecord(Integer recordId, Integer recordType) {
		SetToRecordDTO setsToRecordDTO = new SetToRecordDTO();
		setsToRecordDTO.setRecordId(recordId);
		setsToRecordDTO.setSetId(recordType);
		List<DataTransferObject> sets = null;
		try {
			sets = setToRecordsMgr.get(setsToRecordDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sets;
	}

	public String getXmlOfRecord(Integer recordId, Integer recordType) {
		String content = null;
		if(ApplInfo.oaiConf.getStorageType().equals(StorageTypes.MIXED)) {
			content = ApplInfo.luceneSearcher.getXmlOfRecord(recordId, recordType);
		} else {
			try {
				XmlDTO searchDTO = new XmlDTO(recordId);
				List<DataTransferObject> list = xmlMgr.get(searchDTO);
				if(list != null && list.size() > 0) {
					XmlDTO xmlDTO = (XmlDTO) list.get(0);
					content = xmlDTO.getXml();
				}
			} catch(SQLException e) {
				prglog.error("[PRG] " + e);
			} catch(Exception e) {
				prglog.error("[PRG] " + e);
			}
		}
		return content;
	}
	
	public void prepareQuery() {
		if(null != tokenId) {
			ResumptionTokenDTO tokenDTO = getSQLsFromResumptionToken(tokenId);
			if(tokenDTO == null){
				badResumptionTokenError = true;
			} else {
				sql = tokenDTO.getQuery();
				counterSQL = tokenDTO.getQueryForCount();
				metadataPrefix = tokenDTO.getMetadataPrefix();
			}
		} else {
			extractSQLsFromParameters(from, until, set);
			if(null != sql){
				prglog.error("[PRG] sql is null");
			}
		}
	}

	public int getTotalRecordCount() {
		int totalRecordNr = -1;
		try {
			totalRecordNr = mgr.selectCount(counterSQL);
		} catch (SQLException e) {
			prglog.error("[PRG] " + e);
			e.printStackTrace();
		} catch (Exception e) {
			prglog.error("[PRG] " + e);
			e.printStackTrace();
		}
		return totalRecordNr;
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
			List tokens = tokenMgr.get(tokenDTO);
			if(null != tokens) {
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

	private void extractSQLsFromParameters(String from, String until, 
			String set) {
		List<String> whereList = new ArrayList<String>();
		List<String> fromList = new ArrayList<String>();
		fromList.add("records AS a");
		if(null != from) {
			whereList.add("modification_date >= '" 
				+ TextUtil.utcToMysqlTimestamp(from) + "'");
		}
		if(null != until) {
			whereList.add("modification_date <= '" 
				+ TextUtil.utcToMysqlTimestamp(until) + "'");
		}
		if(null != set) {
			// checking the set's existence
			SetDTO setsDTO = new SetDTO();
			setsDTO.setSetSpec(set);
			try {
				List sets = setMgr.get(setsDTO);
				if(1 == sets.size()) {
					fromList.add("sets_to_records AS b");
					whereList.add("a.record_id = b.record_id AND b.set_id = " 
							+ ((SetDTO)sets.get(0)).getSetId());
				}
			} catch(SQLException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		StringBuffer counterSQLBfr = new StringBuffer();
		counterSQLBfr.append("SELECT count(a.record_id) FROM ");
		counterSQLBfr.append(TextUtil.join(fromList, ", "));
		if(whereList.size() > 0) {
			counterSQLBfr.append(" WHERE ");
			counterSQLBfr.append(TextUtil.join(whereList, " AND "));
		}
		
		StringBuffer sqlBfr = new StringBuffer();
		sqlBfr.append("SELECT a.* FROM ").append(TextUtil.join(fromList, ", "));
		if(whereList.size() > 0) {
			sqlBfr.append(" WHERE ").append(TextUtil.join(whereList, " AND "));
		}
		sqlBfr.append(" ORDER BY record_id");
		
		sql        = sqlBfr.toString();
		counterSQL = counterSQLBfr.toString();
	}
	
	public String storeResumptionToken() {
		ResumptionTokenDTO tokenDTO = new ResumptionTokenDTO();
		tokenDTO.setQuery(sql);
		tokenDTO.setQueryForCount(counterSQL);
		tokenDTO.setMetadataPrefix(metadataPrefix);
		tokenDTO.setCreationDate(new Timestamp(new Date().getTime()));
		try {
			List<Integer> ids = tokenMgr.insert(tokenDTO);
			String resumptionToken = String.valueOf(ids.get(0));
			return resumptionToken;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public long getDoc2RecordTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getDocTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	public long getIdTime() {
		// TODO Auto-generated method stub
		return 0;
	}
}
