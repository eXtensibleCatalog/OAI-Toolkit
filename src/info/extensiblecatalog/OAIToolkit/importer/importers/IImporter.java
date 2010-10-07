/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer.importers;

import java.util.List;

import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;

import org.marc4j.marc.Record;

/**
 * Record importer interface used for importing records to database.
 * @author Király Péter pkiraly@tesuji.eu
 */
public interface IImporter {
	
	/**
	 * Import one MARC record
	 * @param record The MARC record to import
	 * @return Information about the record and the success
	 */
	public List<ImportType> importRecord(Record record, boolean doFileOfDeletedRecords);
	
	/**
	 * Get the control number of the last record
	 * @return
	 */
	public String getLastRecordToImport();

	/**
	 * Set up the corrent MARCXML file's name
	 * @param currentFile The MARCXML files's name
	 */
	public void setCurrentFile(String currentFile);
	
	/**
	 * allow importer do do clean-up on this re-usable object
	 */
	public void closeCurrentFile();

	/**
	 * Decide whether indent or not the MARCXML record
	 * @param doIndentXml True means indent, false means not to indent 
	 * MARCXML record
	 */
	public void setDoIndentXml(boolean doIndentXml);

	/**
	 * Decide whether create XML 1.1 or XML 1.0. The difference between the
	 * versions, is that in 1.1 the "weird characters" (ASCII Control Characters)
	 * are valid ones, while they are invalid in XML 1.0.
	 * @param createXml11
	 * @deprecated This method is debprecated since 0.4
	 */
	public void setCreateXml11(boolean createXml11);

	/**
	 * Optimize the database
	 */
	public void optimize();
	
	/**
	 * Commit changes in the database
	 */
	public void commit();
	
	/**
	 * Write out bad record into a "bad records file"
	 * @param record The bad (invalid) record
	 */
	public void writeBadRecord(Record record);
	
	/**
	 * Set up XML directory for the error files
	 * @param errorXmlDir The name of directory
	 */
	public void setErrorXmlDir(String errorXmlDir);
	
	/**
	 * Get the duration of checking whether the record is existent 
	 * in the database or not 
	 * @return Time in milliseconds
	 */
	public long getCheckTime();

    /**
     * Get the Tracked value used for the XC OAI ID creation
     * @return trackedOaiIdValue as Integer.
     */
    public int getTrackedOaiIdValue();

    /**
     * Set the Tracked value used for the XC OAI ID creation
     */
    public void setTrackedOaiIdValue(int trackedOaiIdNumberValue);

	/**
	 * Get the duration of inserting a record into the database
	 * @return Time in milliseconds
	 */
	public long getInsertTime();
}
