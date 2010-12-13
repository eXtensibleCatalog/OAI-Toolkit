/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db.managers;

import java.util.List;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;

/**
 * The manager of records in the records table. The main methods are in the
 * superclass: PrototypeMgr. In this actual class only the special methods
 * exists.
 * @author Peter Kiraly
 */
public class RecordsMgr extends PrototypeMgr {
	
	/**
	 * Constructor. The table name is "records" 
	 */
	public RecordsMgr() {
		super("records");
	}
	
	/**
	 * Get the earliest datestamp. Need for the Identity verb. The earliest 
	 * datestamp is the first record in the timeline.
	 * @param emptyRecord An empty RecordDTO record, this will filed with
	 * the actual values of the first record
	 * @return The earliest record
	 * @throws Exception
	 */
	public RecordDTO getEarliestDatestamp(RecordDTO emptyRecord) 
			throws Exception {
		String sql = "SELECT * FROM records ORDER BY modification_date LIMIT 1";
		return (RecordDTO)select(sql, emptyRecord).get(0);
	}

	public List<DataTransferObject> getImportable(RecordDTO searchRecord) 
			throws Exception {
		String sql = "SELECT * FROM records WHERE external_id = '" 
			+ searchRecord.getExternalId() + "'"
			+ " AND repository_code = '"
			+ searchRecord.getRepositoryCode() + "'"
			+ " AND record_type = " + searchRecord.getRecordType();
		prglog.debug("[PRG] " + sql);
		return select(sql, searchRecord);
	}
}
