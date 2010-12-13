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

import org.apache.lucene.document.Document;
import org.marc4j.marc.Record;

import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.XmlDTO;
import info.extensiblecatalog.OAIToolkit.db.LuceneIndexMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.RecordsMgr;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsToRecordsMgr;
import info.extensiblecatalog.OAIToolkit.importer.MARCRecordWrapper;
import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;
import info.extensiblecatalog.OAIToolkit.utils.XcOaiIdConfigUtil;

/**
 * Mixed importer, which stores the record's metadata in MySQL, and 
 * the XML presentation of MARCXML in Lucene.
 * 
 * @author Király Péter pkiraly@tesuji.eu
 */
public class MixedImporter extends BasicRecordImporter 
		implements IImporter {

	/** The handler of the records records CRUD operations */
	private RecordsMgr recordsMgr = new RecordsMgr();

    /**
     * XC OAI ID Domain Name parameter
     */
    private String oaiIdDomainName;

    /**
     * XC Tracked OAI ID from the database parameter
     */
    private int trackedOaiIdValue;

    /**
     * XC OAI ID Repository Identifier parameter
     */
    private String oaiIdRepositoryIdentifier;

	/** The handler of the sets_to_records records CRUD operations */
	private SetsToRecordsMgr setsToRecordsMgr = new SetsToRecordsMgr();

	private LuceneIndexMgr luceneMgr;

    public MixedImporter(String schemaFile, String luceneIndexDir) {
		super(schemaFile);
		luceneMgr = new LuceneIndexMgr(luceneIndexDir);
	}

	/**
	 * Import one record to the database. First inspect, that this record has
	 * been stored in the database or not. If not, insert it. If yes update the
	 * stored record with the current value.
	 * 
	 * @param record The marc record to insert
	 */
	public List<ImportType> importRecord(Record record, boolean doFileOfDeletedRecords) {

        String xcoaiid;
        String recordType;

        oaiIdDomainName = XcOaiIdConfigUtil.getOaiIdDomainName();
        oaiIdRepositoryIdentifier = XcOaiIdConfigUtil.getOaiIdRepositoryIdentifier();

        xcoaiid = "oai:" + oaiIdDomainName + ":" + oaiIdRepositoryIdentifier + "/";

		List<ImportType> typeList = new ArrayList<ImportType>();

		MARCRecordWrapper rec = new MARCRecordWrapper(record, currentFile, doFileOfDeletedRecords);
		lastRecordToImport = rec.getId();
		if(lastRecordToImport == null) {
			prglog.error("[LIB] The record hasn't got identifier (field 001)");
			typeList.add(ImportType.INVALID);
			return typeList;
		}

		// validation
		try {
			validator.validate(rec.getXml());
		} catch (Exception ex) {
			libloadlog.error("[LIB] " + printError(ex, rec));
                        prglog.error("[PRG] " + printError(ex, rec));
			rec = null;
			typeList.add(ImportType.INVALID);
			return typeList;
		}

		// data preparation
		RecordDTO data = createData(rec);
		XmlDTO xml = new XmlDTO(rec.getXml());
		SetToRecordDTO setsToRecord = createSetToRecordDTO(rec);
		RecordDTO searchData = createSearchData(data);

		//if(rec.isDeleted()) {
			//typeList.add(ImportType.DELETED);
		//}
		typeList.add(rec.getRecordTypeAsImportType());

		try {
			List list = recordsMgr.getImportable(searchData);
			if (list == null || list.size() == 0 || list.get(0) == null) {
				xcoaiid = xcoaiid + trackedOaiIdValue;
                // Assign that new id. Insert it into the DB.
                // It is inserted in the DB in the Facade.java file
                //prglog.debug(" The value of the xcoai ID (created new) is:"+ xcoaiid);
                trackedOaiIdValue++;
                data.setXcOaiId(xcoaiid);
                List<Integer> insertedIds = recordsMgr.insert(data);

				setsToRecord.setRecordId(insertedIds.get(0));
				setsToRecordsMgr.insert(setsToRecord);

				Document doc = new Document();
				doc.add(luceneMgr.keyword("id", insertedIds.get(0).toString()));
                doc.add(luceneMgr.keyword("xc_oaiid", xcoaiid));
				doc.add(luceneMgr.stored("xml", xml.getXml()));
				luceneMgr.addDoc(doc);
					
				insertedIds = null;
				data = null;
				setsToRecord = null;
				searchData = null;
				rec = null;
				typeList.add(ImportType.CREATED);
				return typeList;
			} else {
				prglog.debug("[PRG] already stored");
				RecordDTO storedData = (RecordDTO) list.get(0);
				if (storedData == null || !storedData.equalData(data)) {
					// logger.debug("difference: " +
					// storedData.difference(data));
					prglog.debug("[PRG] updateByExternal data");
					recordsMgr.updateByExternal(data, storedData);

					// refresh the setSpecs state
					SetToRecordDTO setSpecsSearch = new SetToRecordDTO();
					setSpecsSearch.setRecordId(storedData.getRecordId());
					prglog.debug("[PRG] get setSpecsSearch: "
							+ setSpecsSearch.toString());
					setsToRecord.setRecordId(setSpecsSearch.getRecordId());
					List setList = setsToRecordsMgr.get(setSpecsSearch);
					if (setList == null || setList.size() == 0) {
						prglog.debug("[PRG] insert setSpecs: " + setsToRecord);
						setsToRecordsMgr.insert(setsToRecord);
					} else {
						SetToRecordDTO storedSet = (SetToRecordDTO) setList
								.get(0);
						if (!storedSet.equals(setsToRecord)) {
							prglog.debug("[PRG] delete storedSet: " + storedSet);
							setsToRecordsMgr.delete(storedSet);
							prglog.debug("[PRG] insert setSpecs: " + setsToRecord);
							setsToRecordsMgr.insert(setsToRecord);
						}
						storedSet = null;
					}

					// refresh the xml
					luceneMgr.delDoc("id", storedData.getRecordId().toString());
					Document doc = new Document();
					doc.add(luceneMgr.keyword("id", storedData.getRecordId().toString()));
                    doc.add(luceneMgr.keyword("xc_oaiid", storedData.getXcOaiId().toString()));
					doc.add(luceneMgr.stored("xml", xml.getXml()));
					luceneMgr.addDoc(doc);

                    if(rec.isDeleted()) {
                    typeList.add(ImportType.DELETED);
                    }
                    else
                    typeList.add(ImportType.UPDATED);

					data = null;
					setsToRecord = null;
					searchData = null;
					rec = null;
					//typeList.add(ImportType.UPDATED);
					return typeList;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			prglog.error("[PRG] " + e.getMessage() + ", " + e.getSQLState() + e.getCause()
					+ " (Record: " + currentFile + "#" + lastRecordToImport
					+ ") " + data.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
			prglog.error("[PRG] " + ex + " (Record: " + currentFile + "#"
					+ lastRecordToImport + ")");
		}
		searchData = null;
		data = null;
		setsToRecord = null;
		rec = null;
		typeList.add(ImportType.SKIPPED);
		return typeList;
	}

	/** 
	 * Commit the changes in database.
	 * Now it doesn't do anything.
	 */
	public void commit() {
		// TODO: commit MySQL
	}

    public int getTrackedOaiIdValue() {
        return trackedOaiIdValue;
    }

    public void setTrackedOaiIdValue(int trackedOaiIdNumberValue) {
        trackedOaiIdValue = trackedOaiIdNumberValue;
	}

	/**
	 * Optimize the database.
	 * Now it optimise only Lucene, and not MySQL.
	 */
	public void optimize() {
		luceneMgr.optimize();
		luceneMgr.close();
		// TODO: optimize MySQL
	}
}
