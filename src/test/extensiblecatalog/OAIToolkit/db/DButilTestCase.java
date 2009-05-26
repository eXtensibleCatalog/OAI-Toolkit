/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import test.Constants;
import info.extensiblecatalog.OAIToolkit.db.DButil;
import junit.framework.TestCase;

public class DButilTestCase extends TestCase {
	
	public void testInit() {
		try {
			DButil.init(Constants.DB_PROPS);
			assertTrue(DButil.showInfo().indexOf(
				"database: 'extensiblecatalog_test'") > -1);
		}catch (Exception e){
			fail(e.getMessage());
		}
	}

	public void testGetConnection() {
		try {
			DButil.init(Constants.DB_PROPS);
			Connection c = DButil.getConnection();
			assertNotNull(c);
		}catch (Exception e){
			fail(e.getMessage());
		}
	}

	public void testCloseConnection() {
		try {
			DButil.init(Constants.DB_PROPS);
			Connection c = DButil.getConnection();
			assertNotNull(c);
			assertFalse(c.isClosed());
			DButil.closeConnection(c);
			assertTrue(c.isClosed());
		}catch (Exception e){
			fail(e.getMessage());
		}
	}

	public void testGetMetadata() {
		try {
			DButil.init(Constants.DB_PROPS);
			DatabaseMetaData meta = DButil.getMetadata();
			assertNotNull(meta);
			assertNotNull(meta.getDriverName());
		}catch (Exception e){
			fail(e.getMessage());
		}
	}

	public void testShowInfo() {
		try {
			DButil.init(Constants.DB_PROPS);
			assertTrue(DButil.showInfo().indexOf(
				"database: 'extensiblecatalog_test'") > -1);
		}catch (Exception e){
			fail(e.getMessage());
		}
	}
}
