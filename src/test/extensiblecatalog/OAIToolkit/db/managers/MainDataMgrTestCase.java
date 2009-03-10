/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.db.managers;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import test.Constants;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.db.DButil;
import info.extensiblecatalog.OAIToolkit.db.managers.RecordsMgr;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

import junit.framework.TestCase;

public class MainDataMgrTestCase extends TestCase {
	
	public void setUp() throws SQLException, Exception {
		super.setUp();
		ApplInfo.init(Constants.TEST_DIR, Constants.TEST_DIR + "/log");
	}
	
	public void testMainDataMgr() throws SQLException, InstantiationException, Exception {
		RecordDTO record = new RecordDTO();
		record.setExternalId("osksaj");
		//record.setXml("<record> é á ű ő ú ú í ö ü ó </record>");
		record.setCreationDate(Timestamp.valueOf("2005-26-25 11:11:11"));
		record.setModificationDate(Timestamp.valueOf("2005-26-25 11:11:11"));
		record.setRootName("record");
		record.setRecordType(Integer.valueOf(1));
		
		RecordsMgr mgr = new RecordsMgr();
		//mgr.deleteAll();
		List<Integer> insertedIds = mgr.insert(record);
		System.out.println(insertedIds.size());
		record.setRecordId(insertedIds.get(0));
		System.out.println(record.toString());

		List<DataTransferObject> savedList = mgr.get(record);
		System.out.println(savedList.size());
		RecordDTO saved = (RecordDTO)savedList.get(0);
		
		if(!saved.equalData(record)){
			System.out.println(TextUtil.join(saved.difference(record), ", "));
			//System.out.println(saved.getXml() + " " + record.getXml());
		}
		assertTrue(saved.equalData(record));

		int i = mgr.delete(record);
		assertEquals("delete one record" , 1, i);
		savedList = mgr.get(record);
		assertEquals("no record with this id", 0, savedList.size());
	}
}
