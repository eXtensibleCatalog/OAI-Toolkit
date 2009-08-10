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
import java.sql.SQLException;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import junit.framework.TestCase;

public class DataSourceTestCase extends TestCase {

	public void testDS() throws SQLException {
		DataSource ds = getDataSource();
		Connection conn = ds.getConnection("excat", "roChesTer08");
		DatabaseMetaData meta = conn.getMetaData();
		assertEquals("MySQL", meta.getDatabaseProductName());
		assertEquals("5.0.51a-community-nt", meta.getDatabaseProductVersion());
		conn.close();
		ds = null;
	}

	public void testInteger() {
		String resumptionToken = "26";
		Integer id = Integer.valueOf(resumptionToken);
		assertEquals(Integer.valueOf(26), id);
	}

	public void testContext() throws NamingException, SQLException {
		
		String NAME = "jdbc/mysql";

		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.fscontext.RefFSContextFactory");
		env.put(Context.PROVIDER_URL, "file:" + "c:/Java");
		Context context = new InitialContext(env);

		context.rebind(NAME, getDataSource());

		DataSource ds2 = (DataSource) context.lookup(NAME);

		// Now, get the connection
		Connection conn = ds2.getConnection("excat", "roChesTer08");
		DatabaseMetaData meta = conn.getMetaData();
		assertEquals("MySQL", meta.getDatabaseProductName());
		assertEquals("5.0.51a-community-nt", meta.getDatabaseProductVersion());
		conn.close();
		context.unbind(NAME);
	}
	
	private DataSource getDataSource() {
		MysqlDataSource mds = new MysqlDataSource();
		mds.setServerName("localhost");
		mds.setDatabaseName("extensiblecatalog_test");
		mds.setPortNumber(3307);
		mds.setEncoding("UTF8");
		mds.setZeroDateTimeBehavior("convertToNull");
		mds.setUseUnicode(true);
		DataSource ds = (DataSource) mds;
		return ds;
	}

	private static Context getContext(String classFactory, String providerURL)
			throws javax.naming.NamingException {
		// Set up environment for creating initial context
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, classFactory);
		env.put(Context.PROVIDER_URL, providerURL);
		Context context = new InitialContext(env);
		return context;
	}

	private static Context getFileSystemContext(String directoryName)
			throws javax.naming.NamingException {
		// Set up environment for creating initial context
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.fscontext.RefFSContextFactory");
		env.put(Context.PROVIDER_URL, "file:" + directoryName);
		Context context = new InitialContext(env);
		return context;
	}
	
	private static void deployDataSource(Context context,
			String jndiName,
			DataSource ds)
			throws javax.naming.NamingException{
			//
//			 register the data source under the jndiName using
//			 the provided context
			//
			context.bind(jndiName, ds);
	}

}
