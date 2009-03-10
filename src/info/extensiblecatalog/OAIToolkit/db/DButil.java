/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import info.extensiblecatalog.OAIToolkit.utils.ConfigUtil;
import info.extensiblecatalog.OAIToolkit.utils.Logging;

/**
 * Utility class for setup database connection
 * @author Peter Kiraly
 * @version 0.1
 */
public class DButil {
	
	//private static final Logger logger = Logging.getLogger();
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
        
	private static String host;
	private static String port;
	private static String database;
	private static String user;
	private static String password;
	
	private static DataSource dataSource;
	private static int connectionCounter = 0;
	private static int connectionCounterTotal = 0;
	
	private enum ConnectionTypes{STANDARD, DATASOURCE, JINI};
	private static final ConnectionTypes ConnectionType = ConnectionTypes.STANDARD;

	private DButil(String fileName) throws Exception {
		init(fileName);
	}

	private DButil() throws Exception {
		init("db.properties");
	}
	
	public static void init(String fileName) throws Exception {
		if (ConnectionType.equals(ConnectionTypes.STANDARD)) {
			try {
				load(fileName);
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				prglog.info("[PRG] Class not found: " + e.toString());
				throw e;
			} catch (Exception e) {
				prglog.info("[PRG] " + e);
				throw e;
			}
		} else if (ConnectionType.equals(ConnectionTypes.DATASOURCE)) {
			load(fileName);
			initDataSource();
		}

	}

	private static void load(String fileName) throws Exception {
		PropertiesConfiguration props = ConfigUtil.load(fileName);

		host = (String) props.getProperty("db.host");
		port = (String) props.getProperty("db.port");
		database = (String) props.getProperty("db.database");
		user = (String) props.getProperty("db.user");
		password = (String) props.getProperty("db.password");
		
		prglog.info("[PRG] DB parameters: fileName: " + fileName 
				+ ", host: " + host
				+ ", port: " + port
				+ ", database: " + database
				+ ", user: " + user
				+ ", password: " + password.replaceAll(".", "*")
				);
	}
	
	public static Connection getConnection() throws Exception {
		if(0 != connectionCounter) {
			prglog.info("[PRG] connectionCounter is not 0! " + connectionCounter);
		}
		Connection conn;
		try {
			if(ConnectionType.equals(ConnectionTypes.STANDARD)) {
				conn = DriverManager.getConnection(
						"jdbc:mysql://" + host + ":" + port + "/" + database
						+ "?zeroDateTimeBehavior=convertToNull"
                                                + "&autoReconnect=true"
						+ "&useUnicode=true" 
						+ "&characterEncoding=UTF8",
						user, password);
			} else if(ConnectionType.equals(ConnectionTypes.DATASOURCE)) {
				conn = dataSource.getConnection(user, password);
			} else {
				conn = null;
			}
			connectionCounter++;
			connectionCounterTotal++;
		} catch(SQLException e) {
			logException(e, null);
			throw new Exception(e);
		}
		return conn;
	}
	
	public static void closeConnection(Connection connection) {
		if(null != connection) {
			try {
				connection.close();
				connection = null;
				connectionCounter--;
			} catch(SQLException e) {
				prglog.error("[PRG] Failed to close connection: " + e);
			//} catch(InterruptedException e) {
				//prglog.error("Failed to close connection: " + e);
			}
		} else {
			prglog.info("[PRG] Connection is null!");
		}
		if(0 != connectionCounter) {
			prglog.info("[PRG] connectionCounter is not 0! " + connectionCounter);
		}
	}
	
	
	private static void initDataSource() {
		MysqlDataSource mds = new MysqlDataSource();
		mds.setServerName(host);
		mds.setPortNumber(Integer.parseInt(port));
		mds.setDatabaseName(database);
		mds.setZeroDateTimeBehavior("convertToNull");
		mds.setUseUnicode(true);
		mds.setCharacterEncoding("UTF8");
		dataSource = (DataSource) mds;
	}

	
	public static DatabaseMetaData getMetadata() {
		try {
			return getConnection().getMetaData();
		} catch (SQLException e) {
			e.printStackTrace();
			prglog.error("[PRG] " + e);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			prglog.error("[PRG] " + e);
			return null;
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException, 
			SQLException, Exception {
		DButil.load("db.properties");
		DatabaseMetaData meta = DButil.getMetadata();
		
		prglog.info("[PRG] " + 
				meta.getDatabaseProductName()
				+ " " + meta.getDatabaseProductVersion()
				/*
				+ " " + meta.getDriverMajorVersion()
				+ "." + meta.getDriverMinorVersion()
				+ " " + meta.getDatabaseMajorVersion() 
				+ "." + meta.getDatabaseMinorVersion()
				*/
				);
	}
	
	public static String showInfo() {
		StringBuffer sb = new StringBuffer();
		DatabaseMetaData meta = getMetadata();
		
		Connection conn = null;
		try {
			conn = getConnection();
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		sb.append("Connection: ");
		if(null == conn) {
			sb.append(" (is null)");
		} else {
			sb.append(" (is ok)");
		}

		if(null == meta) {
			sb.append(", [unknown server]");
		} else {
			try {
				sb.append(", server: " + meta.getDatabaseProductName() 
						+ " " + meta.getDatabaseProductVersion());
			} catch(SQLException e) {
				e.printStackTrace();
				prglog.error("[PRG] " + e);
			}
		}
		sb.append(", user: '" + user + "'");
		sb.append(", password: '" + password + "'");
		sb.append(", database: '" + database + "'");
		
		return sb.toString();
	}
	
	private static void logException(SQLException sqlex, String sql) {
		prglog.error("[PRG] SQLException with "
				+ "SQLState='" + sqlex.getSQLState() + "' and "
				+ "errorCode=" + sqlex.getErrorCode() + " and "
				+ "message=" + sqlex.getMessage() + "; sql was '" + sql + "'"
				+ " open/total: " + connectionCounter + "/" + connectionCounterTotal);
	}

}
