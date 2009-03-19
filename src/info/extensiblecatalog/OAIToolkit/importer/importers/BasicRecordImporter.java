/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer.importers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.importer.MARCRecordWrapper;
import info.extensiblecatalog.OAIToolkit.importer.MarcXmlErrorParser;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.ExceptionPrinter;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.XMLValidator;

import org.apache.log4j.Logger;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.Record;
import org.xml.sax.SAXParseException;

/**
 * Common methods for the implementations of {@link IImporter} interface
 * @author Király Péter pkiraly@tesuji.eu
 */
public class BasicRecordImporter {
        
        /** The programmer's and the librarian's log object */
       
        private static String library_loadlog = "librarian_load";
        private static String programmer_log = "programmer";

	protected static final Logger libloadlog = Logging.getLogger(library_loadlog);
        protected static final Logger prglog = Logging.getLogger(programmer_log);
    
	/** The logger object */
	//protected static final Logger Log = Logging.getLogger();

	/** The current file to convert or import */
	protected String currentFile;

	/**
	 * The last record entering into the import process. Used only for tracking
	 * errors.
	 */
	protected String lastRecordToImport;
	
	/** The SAX based XML validator, validates against schema file */
	protected XMLValidator validator;
	
	/** Flag to indent XML */
	protected boolean doIndentXml;

	/** 
	 * Flag to create XML 1.1
	 * @deprecated Since version 0.4 
	 */
	protected boolean createXml11 = true;
	
	/** The Writer object responsible for writing out the invalid records */
	protected MarcXmlWriter badRecordWriter = null;
	
	/**
	 * The name of the directory where the invalid records will be stored.
	 */
	protected String errorXmlDir;
	
	/**
	 * The duration of checking whether the record is existent.
	 */
	protected long checkTime = 0;

	/**
	 * The duration of inserting record into the database.
	 */
	protected long insertTime = 0;
	
	/**
	 * Create a new instance. Set up the {@link validator} by adding the schema
	 * file, which the validator use are rules of validation.
	 * @param schemaFile The path of XML schema file (.xsd)  
	 */
	public BasicRecordImporter(String schemaFile) {
		validator = new XMLValidator(schemaFile);
	}

	/**
	 * Create a human readable error message from the exception and the record
	 * object. 
	 * @param ex The exception object
	 * @param rec The invalid record 
	 * @return A human readable error message
	 */
	protected String printError(Exception ex, MARCRecordWrapper rec) {
		StringBuffer error = new StringBuffer();
		String message = MarcXmlErrorParser.parseErrorMessage(ex.getMessage());
		error.append("The MARC record " + currentFile + "#" + rec.getId() + " of Type " + rec.getRecordTypeAsImportType()
			+ " isn't well formed. Please correct the errors and "
			+ "load again. Cause: " + message
		);
                if (ex instanceof SAXParseException) {
			error.append(validator.showContext((SAXParseException)ex, 
					rec.getXml(), doIndentXml));
                        error.append("\n The above arrow shows the area location where the error is. \n");
		}
                //liblog.info("The xml of the record is:" + rec.getXml());
		return error.toString();
	}
	
	/**
	 * Creates a {@link RecordDTO} object from the record.
	 * @param record The MARC record
	 * @return {@link RecordDTO} object created from MARC record
	 */
	protected RecordDTO createData(MARCRecordWrapper record) {
		RecordDTO data = new RecordDTO();
		data.setExternalId(record.getId());
		data.setIsDeleted((record.isDeleted()) ? true : false);
		if (record.isNew()) {
			data.setCreationDate(record.getLastTransactionDate());
			data.setModificationDate(record.getLastTransactionDate());
		} else {
			data.setModificationDate(record.getLastTransactionDate());
		}
		data.setRecordType(ApplInfo.setIdsByName.get(
				record.getRecordTypeAbbreviation()));
		return data;
	}
	
	/**
	 * Create a {@link SetToRecordDTO} object from the record.
	 * @param rec The full record
	 * @return The {@link SetToRecordDTO} object
	 */
	protected SetToRecordDTO createSetToRecordDTO(MARCRecordWrapper rec) {
		SetToRecordDTO setsToRecords = new SetToRecordDTO();
		setsToRecords.setSetId(ApplInfo.setIdsByName.get(rec
			.getRecordTypeAbbreviation()));
		return setsToRecords;
	}
	
	/**
	 * Create a lightweight {@link RecordDTO} object with only id and 
	 * record type value from the full record. This lightweight object
	 * is used by searching whether this exists or not.
	 * @param data The full record.
	 * @return The lighweight "search" object
	 */
	protected RecordDTO createSearchData(RecordDTO data) {
		RecordDTO searchData = new RecordDTO();
		searchData.setExternalId(data.getExternalId());
		searchData.setRecordType(data.getRecordType());
		return searchData;
	}
	
	/**
	 * Creates the Write object for the invalid records. The file names
	 * follows the pattern:
	 * <pre>
	 *   "error_records_in_" + &lt;currentFile&gt;
	 * </pre>
	 */
	protected void createBadRecordWriter() {
		try {
			File err = new File(errorXmlDir, "error_records_in_" + currentFile);
			prglog.info("[PRG] XML error file is: " + err.getAbsolutePath());
                        libloadlog.info("[LIB] XML error file is: " + err.getAbsolutePath());
			
			badRecordWriter = new MarcXmlWriter(
				new FileOutputStream(err), "UTF8", // encoding
				true//, // indent
			);//configuration.isCreateXml11()); // xml 1.0
		} catch(FileNotFoundException e) {
			prglog.error(ExceptionPrinter.getStack(e));
		}
	}
	
	/**
	 * Write out invalid MARC record to the error file
	 * @param record The invalid MARC record
	 */
	public void writeBadRecord(Record record) {
		if(null == badRecordWriter){
			createBadRecordWriter();
		}
		badRecordWriter.write(record);
	}

	/**
	 * Get the last record's ID to import
	 * @return
	 */
	public String getLastRecordToImport() {
		return lastRecordToImport;
	}

	/**
	 * Save the name of the current MARCXML file
	 * @param currentFile
	 */
	public void setCurrentFile(String currentFile) {
		this.currentFile = currentFile;
	}

	/**
	 * Decide whether indent or not the MARCXML record
	 * @param doIndentXml True means indent, false means not to indent 
	 * MARCXML record
	 */
	public void setDoIndentXml(boolean doIndentXml) {
		this.doIndentXml = doIndentXml;
	}
	
	/**
	 * Get the current value of the {@link #createXml11}. 
	 * @return True means that the importer should create XML 1.1, false means
	 * that the importer creates XML 1.0
	 */
	public boolean isCreateXml11() {
		return createXml11;
	}
	
	/**
	 * Decide whether create XML 1.1 or XML 1.0. The difference between the
	 * versions, is that in 1.1 the "weird characters" (ASCII Control Characters)
	 * are valid ones, while they are invalid in XML 1.0.
	 * @param createXml11
	 * @deprecated This method is debprecated since 0.4
	 */
	public void setCreateXml11(boolean createXml11) {
		this.createXml11 = createXml11;
	}

	/**
	 * Get the name of the directory of xml error files 
	 * @return The name of directory
	 */
	public String getErrorXmlDir() {
		return errorXmlDir;
	}

	/**
	 * Set the name of the directory of xml error files
	 * @param errorXmlDir The name of directory
	 */
	public void setErrorXmlDir(String errorXmlDir) {
		this.errorXmlDir = errorXmlDir;
	}

	/**
	 * Get the {@link #checkTime} value
	 * @return
	 */
	public long getCheckTime() {
		return checkTime;
	}

	/**
	 * Get the {@link #insertTime} value
	 * @return
	 */
	public long getInsertTime() {
		return insertTime;
	}
}
