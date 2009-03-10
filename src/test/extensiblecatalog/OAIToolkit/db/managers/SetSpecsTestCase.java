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
import java.util.List;

import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.db.DButil;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsToRecordsMgr;

import junit.framework.TestCase;

public class SetSpecsTestCase extends TestCase {
	
	public void setUp() throws SQLException, Exception {
		//DButil.init();
		DButil.init("c:/doku/extensiblecatalog/OAI/db.properties");
	}

	public void testSetSpecsMgr() throws SQLException, InstantiationException, Exception {
		SetToRecordDTO record = new SetToRecordDTO();
		record.setRecordId(1001);
		record.setSetId(2);
		
		SetsToRecordsMgr mgr = new SetsToRecordsMgr();
		mgr.insert(record);

		List saved = mgr.get(record);
		assertEquals("unique", 1, saved.size());
		assertEquals(((SetToRecordDTO)saved.get(0)).getRecordId(), record.getRecordId());

		int i = mgr.delete(record);
		assertEquals("delete one record" , 1, i);
		saved = mgr.get(record);
		assertEquals("no record with this id", 0, saved.size());
	}

}
