/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer.importers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.marc4j.marc.Record;

import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.XmlDTO;
import info.extensiblecatalog.OAIToolkit.db.managers.RecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsToRecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.XmlsMgr;
import info.extensiblecatalog.OAIToolkit.importer.MARCRecordWrapper;
import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;

/**
 * Importing into MySQL
 * @author Király Péter pkiraly@tesuji.eu
 */
public class MysqlImporter extends BasicRecordImporter 
		implements IImporter {
	
	/** The handler of the records records CRUD operations */
	private RecordsMgr recordsMgr = new RecordsMgr();

	/** The handler of the sets_to_records records CRUD operations */
	private SetsToRecordsMgr setsToRecordsMgr = new SetsToRecordsMgr();
	
	private static final String[] workflowCreated = {"recordsMgr.insert", 
			"setsToRecordsMgr.insert", "xmlMgr.insert"};
	private static final String[] workflowUpdated = {"recordsMgr.updateByExternal", 
			"recordsMgr.updateByExternal", "setsToRecordsMgr.get", 
			"setsToRecordsMgr.insert", "setsToRecordsMgr.delete", 
			"setsToRecordsMgr.insert", "xmlMgr.get", "xmlMgr.insert", 
			"xmlMgr.update"};

	/** Handler of xml records CRUD operations */
	private XmlsMgr xmlMgr = new XmlsMgr();
	
	public MysqlImporter(String schemaFile){
		super(schemaFile);
	}

	/**
	 * Import one record to the database. First inspect, that this record
	 * has been stored in the database or not. If not, insert it. If yes
	 * update the stored record with the current value.
	 * @param record The marc record to insert
	 */
	public List<ImportType> importRecord(Record record, boolean doFileOfDeletedRecords) {
		
		List<ImportType> typeList = new ArrayList<ImportType>();

		MARCRecordWrapper rec = new MARCRecordWrapper(record, currentFile, createXml11, doFileOfDeletedRecords);
		lastRecordToImport = rec.getId();
		if(lastRecordToImport == null) {
			prglog.error("[LIB] The record hasn't got identifier (field 001)");
			typeList.add(ImportType.INVALID);
			return typeList;
		}
		
		// validation
		try {
			validator.validate(rec.getXml());
		} catch(Exception ex) {
			prglog.error("[PRG] " + printError(ex, rec));
                        libloadlog.error("[LIB] " + printError(ex, rec));
			rec = null;
			typeList.add(ImportType.INVALID);
			return typeList;
		} // /validation

		// data preparation
		RecordDTO data = createData(rec);
		XmlDTO xml = new XmlDTO(rec.getXml());
		SetToRecordDTO setsToRecord = createSetToRecordDTO(rec);
		RecordDTO searchData = createSearchData(data);

		if(rec.isDeleted()) {
			typeList.add(ImportType.DELETED);
		}
		typeList.add(rec.getRecordTypeAsImportType());
		String lastSuccessfullSQL = "";
		
		boolean isCreated = true;

		try {
			//List list = mainDataMgr.get(searchData);
			prglog.debug("[PRG] search data: " + searchData);
			List list = recordsMgr.getImportable(searchData);
			
			//if(test) return ImportType.SKIPPED;
			if(list == null || list.size() == 0 || list.get(0) == null) {
				isCreated = true;
				prglog.debug("[PRG] insert data");
				List<Integer> insertedIds = recordsMgr.insert(data);
				lastSuccessfullSQL = "recordsMgr.insert";
				setsToRecord.setRecordId(insertedIds.get(0));
				prglog.debug("[PRG] insert setSpecs");
				setsToRecordsMgr.insert(setsToRecord);
				lastSuccessfullSQL = "setsToRecordsMgr.insert";
				prglog.debug("[PRG] insert xml");
				xml.setRecordId(insertedIds.get(0));
				xmlMgr.insert(xml);
				lastSuccessfullSQL = "xmlMgr.insert";
				insertedIds = null;
				data = null;
				setsToRecord = null;
				searchData = null;
				rec = null;
				typeList.add(ImportType.CREATED);
				return typeList;
			} else {
				isCreated = false;
				prglog.debug("[PRG] already stored");
				RecordDTO storedData = (RecordDTO) list.get(0);
				if(storedData == null || !storedData.equalData(data)) {
					//logger.debug("difference: " + storedData.difference(data));
					prglog.debug("[PRG] updateByExternal data");
					recordsMgr.updateByExternal(data, searchData.getExternalId());
					lastSuccessfullSQL = "recordsMgr.updateByExternal";
					
					// refresh the setSpecs state
					SetToRecordDTO setSpecsSearch = new SetToRecordDTO();
					setSpecsSearch.setRecordId(storedData.getRecordId());
					prglog.debug("[PRG] get setSpecsSearch: " + setSpecsSearch.toString());
					setsToRecord.setRecordId(setSpecsSearch.getRecordId());
					List setList = setsToRecordsMgr.get(setSpecsSearch);
					lastSuccessfullSQL = "setsToRecordsMgr.get";
					if(setList == null || setList.size() == 0) {
						prglog.debug("[PRG] insert setSpecs: " + setsToRecord);
						setsToRecordsMgr.insert(setsToRecord);
						lastSuccessfullSQL = "setsToRecordsMgr.insert";
					} else {
						SetToRecordDTO storedSet = (SetToRecordDTO)setList.get(0);
						if(!storedSet.equals(setsToRecord)) {
							prglog.debug("[PRG] delete storedSet: " + storedSet);
							setsToRecordsMgr.delete(storedSet);
							lastSuccessfullSQL = "setsToRecordsMgr.delete";
							prglog.debug("[PRG] insert setSpecs: " + setsToRecord);
							setsToRecordsMgr.insert(setsToRecord);
							lastSuccessfullSQL = "setsToRecordsMgr.insert";
						}
						storedSet = null;
					}
					
					// refresh the xml
					XmlDTO xmlSearch = new XmlDTO(storedData.getRecordId());
					List xmlList = xmlMgr.get(xmlSearch);
					lastSuccessfullSQL = "xmlMgr.get";
					xmlSearch = null;
					if(xmlList == null || xmlList.size() == 0) {
						xml.setRecordId(storedData.getRecordId());
						xmlMgr.insert(xml);
						lastSuccessfullSQL = "xmlMgr.insert";
					} else {
						XmlDTO xmlStored = (XmlDTO)xmlList.get(0);
						if(!xmlStored.getXml().equals(xml.getXml())){
							xml.setRecordId(storedData.getRecordId());
							xmlMgr.update(xml, xmlStored);
							lastSuccessfullSQL = "xmlMgr.update";
						}
						xmlStored = null;
						xml = null;
					}

					data = null;
					setsToRecord = null;
					searchData = null;
					rec = null;
					typeList.add(ImportType.UPDATED);
					return typeList;
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
			prglog.error("[PRG] " + e.getMessage() + ", " + e.getSQLState() 
					+ ", " + e.getCause()
					+ ", SQL: " + getLastSQL(lastSuccessfullSQL, isCreated)
					+ " (Record: " + currentFile + "#" + lastRecordToImport + ") "
					+ data.toString());
		} catch(Exception ex) {
			ex.printStackTrace();
			prglog.error("[PRG] " + ex + " (Record: " + currentFile + "#" + lastRecordToImport + ")");
		}
		searchData = null;
		data = null;
		setsToRecord = null;
		rec = null;
		typeList.add(ImportType.SKIPPED);
		return typeList;
	}

	/**
	 * Commit changes.
	 * 
	 * Now it does nothing.
	 */
	public void commit() {
		//TODO
	}

	/**
	 * Optimize the database.
	 * 
	 * Now it does nothing.
	 */
	public void optimize() {
		//TODO
	}
	
	/**
	 * Get the last SQL command issued
	 * @param lastSuccessfullSQL
	 * @param isCreated
	 * @return
	 */
	private String getLastSQL(String lastSuccessfullSQL, boolean isCreated) {
		String[] workflow;
		workflow = (isCreated) ? workflowCreated : workflowUpdated;
		boolean last = false;
		for(String process : workflow) {
			if(last == true) {
				String mgr = process.substring(0, process.indexOf('.'));
				if(mgr.equals("recordsMgr")) {
					return recordsMgr.getLastSQL();
				} else if(mgr.equals("setsToRecordsMgr")) {
					return setsToRecordsMgr.getLastSQL();
				} else if(mgr.equals("xmlMgr")) {
					return xmlMgr.getLastSQL();
				} else {
					System.out.println("unknown mgr type: " + mgr);
				}
			}
			if(lastSuccessfullSQL.equals(process)) {
				last = true;
			}
		}
		return "unknown";
	}
}
