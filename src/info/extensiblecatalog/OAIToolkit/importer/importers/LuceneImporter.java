/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/.
  *
  */

package info.extensiblecatalog.OAIToolkit.importer.importers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.marc4j.marc.Record;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;

import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.XmlDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.TrackingOaiIdNumberDTO;
import info.extensiblecatalog.OAIToolkit.db.managers.TrackingOaiIdNumberMgr;
import info.extensiblecatalog.OAIToolkit.db.LuceneIndexMgr;
import info.extensiblecatalog.OAIToolkit.importer.MARCRecordWrapper;
import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;
import info.extensiblecatalog.OAIToolkit.utils.MilliSecFormatter;
import info.extensiblecatalog.OAIToolkit.utils.XcOaiIdConfigUtil;

/**
 * Importing into Lucene
 * @author Király Péter pkiraly@tesuji.eu
 */
public class LuceneImporter extends BasicRecordImporter
		implements IImporter {

	/**
	 * The Lucene index manager
	 */
	private LuceneIndexMgr luceneMgr;

	/**
	 * Record counter
	 */
	private int recordCounter = 0;

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

    /**
	 * The list of IDs stored in index
	 */
	//private static Set<String> ids = new TreeSet<String>();

	/**
	 * Creates a new importer, which creates Lucene index
	 * @param schemaFile Name of XML schema file (.xsd). The validator use
	 * this file to decide whether the record is valid or not.
	 * @param luceneIndexDir The location of Lucene index directory
	 */
	public LuceneImporter(String schemaFile, String luceneIndexDir) {
		super(schemaFile);
		luceneMgr = new LuceneIndexMgr(luceneIndexDir);
		long start = System.currentTimeMillis();
		//luceneMgr.putAllIds(ids);
		prglog.info("[PRG] Read all ids. It took " + MilliSecFormatter.toString(
				System.currentTimeMillis()-start));
	}

	/**
	 * Import one record to the database. First inspect, that this record has
	 * been stored in the database or not. If not, insert it. If yes update the
	 * stored record with the current value.
	 *
	 * @param record The marc record to insert
	 */
	public List<ImportType> importRecord(Record record, boolean doFileOfDeletedRecords) {
        String recordType;
        String xcoaiid;
        
        recordCounter++;
        //prglog.debug("Inside the importRecord of Lucene Importer");
        oaiIdDomainName = XcOaiIdConfigUtil.getOaiIdDomainName();
        oaiIdRepositoryIdentifier = XcOaiIdConfigUtil.getOaiIdRepositoryIdentifier();

        xcoaiid = "oai:" + oaiIdDomainName + ":" + oaiIdRepositoryIdentifier + "/";
		List<ImportType> typeList = new ArrayList<ImportType>();

		MARCRecordWrapper rec = new MARCRecordWrapper(record, currentFile, createXml11, doFileOfDeletedRecords);
		rec.setDoIndentXml(doIndentXml);
        rec.setDoFileOfDeletedRecords(doFileOfDeletedRecords);
        lastRecordToImport = rec.getId();
		if(lastRecordToImport == null) {
                        prglog.error("[PRG] The record hasn't got identifier (field 001)");
			typeList.add(ImportType.INVALID);
			return typeList;
		}

		// validation
		try {
			validator.validate(rec.getXml());
		} catch (Exception ex) {
			libloadlog.error(printError(ex, rec));
                        prglog.error(printError(ex, rec));
			rec = null;
			typeList.add(ImportType.INVALID);
			return typeList;
		}

		// data preparation
        recordType = rec.getRecordTypeAbbreviation();
        RecordDTO data = createData(rec);
		XmlDTO xml = new XmlDTO(rec.getXml());
		SetToRecordDTO setsToRecord = createSetToRecordDTO(rec);
		RecordDTO searchData = createSearchData(data);
        
		try {
			//String id;
			long start = System.currentTimeMillis();
			String id = searchData.getExternalId() + "t" + searchData.getRecordType();
            boolean docTest = true;

            //prglog.debug("The id inserted is" + id);
            //boolean isExistent = ids.contains(id);//luceneMgr.doesExist(id);
			boolean isExistent = luceneMgr.doesExist(id);
            checkTime = System.currentTimeMillis() - start;
			if (isExistent == false) {
				//id = data.getExternalId() + "t" + data.getRecordType();

				typeList.add(ImportType.CREATED);
				//ids.add(id);

                // Get the last_inserted successful ID from the database.
                xcoaiid = xcoaiid + trackedOaiIdValue;
                // Assign that new id. Insert it into the DB.
                // Increase the ID, and then update the DB with the new last_inserted_value
                //prglog.debug(" The value of the xcoai ID (created new) is:"+ xcoaiid);
                trackedOaiIdValue++;
                                
			} else {
                   //searchData.getXcOaiId()
                   Document doc = luceneMgr.getDoc(searchData);
                           //ApplInfo.luceneSearcher.getRecordByID(externalId);
                   if (doc == null){
                       prglog.debug("The document is null");
                       docTest = false;
                   }
                   else {
                       xcoaiid = doc.get("xc_oaiid");
                       //prglog.debug("the xcoaiid is" + xcoaiid);
                       //prglog.debug("The xcoai ID value got before update/deletion of record is" + xcoaiid);
                                     
                   id = luceneMgr.getId(searchData);
                   //prglog.debug("The value of the id is" + id);
                   
                   luceneMgr.delDoc("id", id);
                   // Get the xcoaiid which was inserted in Lucene.
                   // Store it in a variable and then utilise it down there, when you insert again.
                   
                   if(rec.isDeleted()) {
                    typeList.add(ImportType.DELETED);
                    }
                   else
                    typeList.add(ImportType.UPDATED);
                   }
			}

            if (docTest == true) {
	            // we did _something_ with this record (add/update/delete)
	        	typeList.add(rec.getRecordTypeAsImportType());
	            	
				Document doc = new Document();
				doc.add(luceneMgr.keyword("id", id));
				doc.add(luceneMgr.keyword("external_id",
						data.getExternalId()));
	            doc.add(luceneMgr.keyword("xc_oaiid", xcoaiid));
				doc.add(luceneMgr.keyword("record_type",
						data.getRecordType().toString()));
	            doc.add(luceneMgr.keyword("is_deleted",
						data.getIsDeleted().toString()));
				if(data.getCreationDate() != null) {
					doc.add(luceneMgr.keyword("creation_date",
						data.getCreationDate().toString()));
				}
				doc.add(luceneMgr.keyword("modification_date",
						data.getModificationDate().toString()));
	
				doc.add(luceneMgr.keyword("set",
						setsToRecord.getSetId().toString()));
				doc.add(luceneMgr.stored("xml", xml.getXml()));
	
				start = System.currentTimeMillis();
				luceneMgr.addDoc(doc);
				insertTime = System.currentTimeMillis() - start;
            } else {
            	// existing record, but not in lucene -
            	// this means it is probably still in the buffer?
            	// ignore it since we can't do anything with it
            	// (we need the re-use the xcoaiid!)
    			typeList.add(ImportType.SKIPPED);   	
            }

		} catch (Exception ex) {
			ex.printStackTrace();
			prglog.error(ex + " (Record: " + currentFile + "#"
					+ lastRecordToImport + ")");
			typeList.add(ImportType.SKIPPED);
		} finally {
			data = null;
			setsToRecord = null;
			searchData = null;
            rec = null;
		}
		if(recordCounter % 1000 == 0) {
			//commit();


		}
		return typeList;
	}

    
    public int getTrackedOaiIdValue() {
        return trackedOaiIdValue;
    }

   
    public void setTrackedOaiIdValue(int trackedOaiIdNumberValue) {
        trackedOaiIdValue = trackedOaiIdNumberValue;
    }

	public void commit() {
		luceneMgr.commit();
	}
	
	public void optimize() {
		luceneMgr.optimize();
		luceneMgr.close();
	}

	public void setCurrentFile(String currentFile) {
		super.setCurrentFile(currentFile);
		recordCounter = 0;
	}
}
