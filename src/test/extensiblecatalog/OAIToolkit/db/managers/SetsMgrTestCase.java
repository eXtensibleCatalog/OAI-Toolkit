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

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.SetDTO;
import info.extensiblecatalog.OAIToolkit.db.DButil;
import info.extensiblecatalog.OAIToolkit.db.managers.SetsMgr;

import junit.framework.TestCase;

public class SetsMgrTestCase extends TestCase {

	public void setUp() throws SQLException, Exception {
		// TODO: change this
		//DButil.init();
		DButil.init("c:/doku/extensiblecatalog/OAI/db.properties");
	}

	public void testGetAll() throws SQLException, InstantiationException, Exception {
		SetsMgr mgr = new SetsMgr();
		List<DataTransferObject> all = mgr.get(new SetDTO());
		for(DataTransferObject set : all){
			System.out.println(((SetDTO)set).toString());
		}
		System.out.println(all.size());
	}

	public void xtestSetsMgr() throws SQLException, InstantiationException, Exception {
		SetDTO record = new SetDTO();
		record.setSetName("1001");
		record.setSetSpec("osksij");
		
		SetsMgr mgr = new SetsMgr();
		//List<Integer> insertedIds = mgr.insert(record);

		List savedList = mgr.get(record);
		assertEquals("unique", 1, savedList.size());
		SetDTO saved = (SetDTO)savedList.get(0);
		assertEquals(saved.getSetName(), record.getSetName());

		int i = mgr.delete(record);
		assertEquals("delete one record" , 1, i);
		savedList = mgr.get(record);
		assertEquals("no record with this id", 0, savedList.size());
	}
}
