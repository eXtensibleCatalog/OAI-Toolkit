/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai.dataproviders;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;

import java.util.List;

public interface FacadeDataProvider {
	
	/** get the datestamp of the earliest record */
	public String getEarliestDatestamp();

	/** store parameters */
	public void setParams(String tokenId, String from, 
			String until, String set, String metadataPrefix, 
			int lastRecordRead, int offset, int totalRecordCount);
	
	public void prepareQuery();
    
	/** get the total number of records */
	public int getTotalRecordCount();
	
	/** are there records remaining in set? */
	public boolean hasMoreRecords();
	
	/** has next record? */
	public boolean hasNextRecord();

	/** get next record */
	public DataTransferObject nextRecord();

	/** get record */
	public List<DataTransferObject> getRecord(String xcOaiId);
	public List<DataTransferObject> getRecord(Integer id, Integer recordType, 
			List<String> fieldFilter);
	
	//public List<DataTransferObject> selectRecords();

	/** select records */
	public void selectRecords();

	/** get sets of record */
	public List<DataTransferObject> getSetsOfRecord(Integer recordId);
	public List<DataTransferObject> getSetsOfRecord(Integer recordId, Integer recordType);

	/** get the xml of record */
	public String getXmlOfRecord(Integer recordId, Integer recordType);
	
	public boolean hasBadResumptionTokenError();

    public String storeResumptionToken();
    	
	public void setRecordLimit(int recordLimit);
	
	public long getIdTime();
	public long getDoc2RecordTime();
	public long getDocTime();
	
	public String getMetadataPrefix();
	
	//public void setupTransformator(String metadataPrefix) throws Exception;
	//public String transformRecord(RecordDTO record, String verb);
}
