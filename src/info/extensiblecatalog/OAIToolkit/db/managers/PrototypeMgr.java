/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db.managers;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.db.DButil;
import info.extensiblecatalog.OAIToolkit.db.FieldMetadata;
import info.extensiblecatalog.OAIToolkit.db.SQLPlaceholder;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

/**
 * The top-level manager of records. I manages the CRUD (create, read, update,
 * delete) operations of a given record type. 
 * @author Peter Kiraly
 */
public class PrototypeMgr {

	/** The prglog object, which helps to create log messages */
        private static String programmer_log = "programmer";
        protected static final Logger prglog = Logging.getLogger(programmer_log);
	//protected static final Logger logger = Logging.getLogger();

	/** The name of the table on which the object operates */
	protected String TABLE_NAME = "";
	
	protected String lastSQL;

	/** A connection (session) with a specific database. SQL statements are 
	 * executed and results are returned within the context of a connection. */
	Connection conn;
	
	/** The metadata of the table columns 
	 * { String name_of_column: { String SQL_TYPE, boolean isNullable }} 
	 */
	protected Map<String, FieldMetadata> meta;

	/**
	 * Constructor. Create a manager of the specified table name
	 * @param tableName The name of the database table
	 */
	public PrototypeMgr(String tableName) {
		TABLE_NAME = tableName;
		try {
			conn = DButil.getConnection();
			getMeta();
		} catch (Exception e) {
			// TODO:
			prglog.error("[PRG] " + e);
		}
	}
	
	/**
	 * Check if the connection does exist and open. If not, reopen this.
	 * @throws Exception
	 */
	private void checkConnection() throws Exception {
		try {
            //prglog.info("The conn value in check connection before the if loop for resetting: " + conn);
			if(conn == null || conn.isClosed()) {
				conn = DButil.getConnection();
                //prglog.info("The conn value in check connection after resetting if needed is: " + conn);
			}
		} catch(SQLException e) {
			throw e;
		}
	}
	
	/**
	 * Destructor. Close the database connection
	 */
	protected void finalize() throws Throwable
	{
		DButil.closeConnection(conn);
	}

	/**
	 * Get the metadata of the table (field names, types, is nullable).
	 * Collect the info to {@link #meta} map. 
	 * @throws Exception
	 */
	private void getMeta() throws Exception {
		try {
			//Connection conn = DButil.getConnection();
			String db = conn.getCatalog();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet colMeta = metaData.getColumns(db, null, TABLE_NAME, "");
			meta = new HashMap<String, FieldMetadata>();

			// the name of the column
			String name;
			
			// the type of the column
			String type;
			
			// the fields is nullable?
			boolean nullable;
			
			while (colMeta.next()) {
				name = colMeta.getString("COLUMN_NAME");
				type = colMeta.getString("TYPE_NAME");
				nullable = (colMeta.getString("NULLABLE").equals("1"));
				meta.put(name, new FieldMetadata(name, type, nullable));
			}
			db = null;
			colMeta.close();
			metaData = null;
			//DButil.closeConnection(conn);

		} catch(SQLException e) {
			logException(e, "getMeta()");
			throw new Exception(e);
		}
	}

	/**
	 * Delete the specified record(s).
	 * @param record 
	 * @return The number of deleted records
	 * @throws SQLException
	 * @throws Exception
	 */
	public int delete(DataTransferObject record) throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(record, null, true);
		String deleteSQL = "DELETE FROM " + TABLE_NAME + " WHERE "
				+ placeholder.createWhere();
		PreparedStatement stmt = null;
		int rowsNumber;
		try {
			stmt = conn.prepareStatement(deleteSQL);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] deletion SQL: " + stmt.toString());
			lastSQL = stmt.toString();
			rowsNumber = execute(stmt);
		} finally {
			stmt.close();
		}
		//DButil.closeConnection(conn);
		return rowsNumber;
	}

	public int deleteAll() throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		String deleteSQL = "DELETE FROM " + TABLE_NAME;
		PreparedStatement stmt = null;
		int rowsNumber;
		try {
			stmt = conn.prepareStatement(deleteSQL);
			prglog.debug("[PRG] deletion SQL: " + stmt.toString());
			lastSQL = stmt.toString();
			rowsNumber = execute(stmt);
		} finally {
			stmt.close();
		}
		//DButil.closeConnection(conn);
		return rowsNumber;
	}

	public List<Integer> insert(DataTransferObject record)
			throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(record);
		String sql = "INSERT INTO " + TABLE_NAME + placeholder.createInsert();

		List<Integer> insertedIds = new ArrayList<Integer>();
		PreparedStatement stmt = null;
		ResultSet rs           = null;
        int execrs = -1;
		try {
            stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] insertion SQL: " + stmt.toString());
			lastSQL = stmt.toString();
            execrs = executeUpdate(stmt);
            //prglog.info("Value of the result of execute Update " + execrs);
            if (execrs == -1) {
                //prglog.debug("Inside the if loop again doing the execute update");
                stmt = conn.prepareStatement(sql);
                placeholder.replacePlaceholders(stmt);
                prglog.debug("[PRG] After resetting connection, insertion SQL: " + stmt.toString());
                stmt.executeUpdate();
            }

			rs = stmt.getGeneratedKeys();
			ResultSetMetaData rsMetaData;
			int columnCount;
			while (rs.next()) {
				rsMetaData = rs.getMetaData();
				columnCount = rsMetaData.getColumnCount();
				for (int i=1; i<=columnCount; i++) {
					insertedIds.add((Integer)rs.getInt(i));
				}
			}
		} finally {
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
		}
		//DButil.closeConnection(conn);
		return insertedIds; //execute(stmt);
	}

	public int update(DataTransferObject newRecord, DataTransferObject oldRecord)
			throws SQLException, Exception {
		checkConnection();
		
		SQLPlaceholder placeholder    = getValuesFromDTO(newRecord);
		SQLPlaceholder placeholderOld = getValuesFromDTO(oldRecord);
		String sql = "UPDATE " + TABLE_NAME + " SET "
				+ placeholder.createUpdate() + " WHERE "
				+ placeholderOld.createWhere();
		PreparedStatement stmt = null;
		int rowNumber;
		try {
			stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			placeholderOld.replacePlaceholders(stmt, placeholder.getDataValues()
				.size()+1);
			prglog.debug("[PRG] update SQL: " + stmt.toString());
			lastSQL = stmt.toString();
			rowNumber = execute(stmt);
		} finally {
			stmt.close();
		}
		//DButil.closeConnection(conn);

		return rowNumber; 
	}

	public int update(DataTransferObject newRecord, int id) 
			throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(newRecord);
		String sql = "UPDATE " + TABLE_NAME + " SET "
				+ placeholder.createUpdate() + " WHERE id = " + id;
		PreparedStatement stmt = null;
		int rowNumber;
		try {
			stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] update SQL: " + stmt.toString());
			lastSQL = stmt.toString();
			rowNumber = execute(stmt);
		} finally {
			stmt.close();
		}
		//DButil.closeConnection(conn);

		return rowNumber; 
	}

	public int updateByExternal(DataTransferObject newRecord, RecordDTO originalRecord) 
			throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(newRecord);
		String sql = "UPDATE " + TABLE_NAME + " SET "
				+ placeholder.createUpdate() 
				+ " WHERE external_id = '" + originalRecord.getExternalId() + "'"
				+ " AND repository_code = '"
				+ originalRecord.getRepositoryCode() + "'"
				+ " AND record_type = " + originalRecord.getRecordType();				

		PreparedStatement stmt = null;
		int rowNumber;
		try {
			stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] " + stmt.toString());
			lastSQL = stmt.toString();
			rowNumber = execute(stmt);
		} finally {
			stmt.close();
		}
		//DButil.closeConnection(conn);
		return rowNumber; 
	}

    public int updateByTrackingId(DataTransferObject newRecord, int trackingId)
			throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(newRecord);
		String sql = "UPDATE " + TABLE_NAME + " SET "
				+ placeholder.createUpdate()
				+ " WHERE tracking_id = '" + trackingId + "'";
		PreparedStatement stmt = null;
		int rowNumber;
		try {
			stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] " + stmt.toString());
			lastSQL = stmt.toString();
			rowNumber = execute(stmt);
		} finally {
			stmt.close();
		}
		//DButil.closeConnection(conn);
		return rowNumber;
	}

	public List<DataTransferObject> get(DataTransferObject record)
			throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(record);
		String sql = "SELECT * FROM " + TABLE_NAME;
		if(!placeholder.createWhere().equals("")) {
			sql +=  " WHERE " + placeholder.createWhere();
		}
		PreparedStatement stmt = null;
		ResultSet result = null;
		List<DataTransferObject> records = new ArrayList<DataTransferObject>();
		try {
			stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] selection SQL: " + stmt.toString());
			lastSQL = stmt.toString();

			result = stmt.executeQuery();
			result.last();
			int rowcount = result.getRow();
			prglog.debug("[PRG] number of rows: " + rowcount);
			result.beforeFirst();
			while (result.next()) {
				records.add(result2record(result, record.getClass().newInstance()));
			}
		} finally {
			if(result != null)
				result.close();
			if(stmt != null)
				stmt.close();
		}
		//DButil.closeConnection(conn);
		return records;
	}

	public List<DataTransferObject> get(DataTransferObject record,
			List<String> fieldNames) 
			throws SQLException, Exception {
		checkConnection();

		// Connection conn = DButil.getConnection();
		SQLPlaceholder placeholder = getValuesFromDTO(record, fieldNames, false);
		String sql = "SELECT * FROM " + TABLE_NAME;
		if (!placeholder.createWhere().equals("")) {
			sql += " WHERE " + placeholder.createWhere();
		}

		List<DataTransferObject> records = new ArrayList<DataTransferObject>();
		PreparedStatement stmt = null;
		ResultSet result       = null;
		try {
			stmt = conn.prepareStatement(sql);
			placeholder.replacePlaceholders(stmt);
			prglog.debug("[PRG] selection SQL: " + stmt.toString());
			lastSQL = stmt.toString();

			result = stmt.executeQuery();
			result.last();
			int rowcount = result.getRow();
			prglog.debug("[PRG] number of rows: " + rowcount);
			result.beforeFirst();
			while (result.next()) {
				records.add(result2record(result, record.getClass().newInstance()));
			}
		} finally {
			if(result != null)
				result.close();
			if(stmt != null)
				stmt.close();
		}
		// DButil.closeConnection(conn);
		return records;
	}

	public List<DataTransferObject> select(String sql, DataTransferObject record)
			throws SQLException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		prglog.debug("[PRG] selection SQL: " + sql);
		lastSQL = sql;
		Statement stmt = null;
		ResultSet result = null;
		List<DataTransferObject> records = new ArrayList<DataTransferObject>();
		try {
			stmt   = conn.createStatement();
			result = stmt.executeQuery(sql);
			prglog.debug("[PRG] FetchSize: " + result.getFetchSize());
			while (result.next()) {
				records.add(result2record(result, record.getClass().newInstance()));
			}
		} finally {
			if(result != null)
				result.close();
			if(stmt != null)
				stmt.close();
		}
		//DButil.closeConnection(conn);

		return records;
	}

	public int selectCount(String sql) throws SQLException, Exception {
		checkConnection();

		//Connection conn = DButil.getConnection();
		Statement stmt = null;
		ResultSet result = null;
		int rowNumber;
		try {
			stmt = conn.createStatement();
			prglog.trace("[PRG] selection SQL: " + sql);
			result = stmt.executeQuery(sql);
			lastSQL = sql;
			prglog.trace("[PRG] get result");
			result.next();
			rowNumber = result.getInt(1);
		} finally {
			if(result != null)
				result.close();
			if(stmt != null)
				stmt.close();
		}
		//DButil.closeConnection(conn);

		return rowNumber;
	}

	/**
	 * Create a new object instance and load the stored values into it.
	 * 
	 * @param result The result set come from the query
	 * @param record The (tipically blank) DataTransferObject to mofify
	 * @return The modified DataTransferObject
	 * @throws SQLException
	 * @throws Exception
	 */
	private DataTransferObject result2record(ResultSet result,
			DataTransferObject record) throws SQLException, Exception {
		if (null == result) {
			return null;
		}

		FieldMetadata fieldMeta;
		Method accessorMthd;
		Object value;
		Class<?> javaType;
		for (String field : meta.keySet()) {
			fieldMeta = meta.get(field);
			//String javaField = fieldMeta.getJavaField(); //TextUtil.toCamelCase(field);
			//String accessor = fieldMeta.getAccessor(); // TextUtil.toCamelCase("set_" + field);
			try {
				try {
					javaType = fieldMeta.getJavaType(record);
					if(javaType == null) {
						continue;
					}
					accessorMthd = fieldMeta.getAccessorMethod(record);
					if (javaType.equals(String.class)) {
						if(null != result.getBytes(field)){
							value = new String(result.getBytes(field), "utf-8");
						} else {
							value = null;
						}
					} else if (javaType.equals("chr")) {
						value = (char)result.getInt(field);
						/*
						if(result.getBytes(field) != null){
							value = new String(result.getBytes(field), "utf-8");
						} else {
							value = null;
						}
						*/
					} else if (javaType.equals(Date.class)) {
						value = result.getDate(field);
					} else if (javaType.equals(Integer.class)) {
						value = result.getInt(field);
					} else if (javaType.equals("int")) {
						value = result.getInt(field);
					} else if (javaType.equals(Boolean.class)) {
						value = result.getBoolean(field);
					} else if (javaType.equals(Timestamp.class)) {
						value = result.getTimestamp(field);
					} else {
						prglog.error("[PRG] Unhandled type: " + javaType);
						throw new Exception("Unhandled type: " + javaType);
					}
					if (null != accessorMthd) {
						accessorMthd.invoke(record, new Object[] { value });
					}
				} catch (NoSuchFieldException e) {
					prglog.warn("[PRG] No such field: " + field + ". "
							+ e.getMessage());
					continue;
				} catch (NoSuchMethodException e) {
					prglog.warn("[PRG] No such method: " + fieldMeta.getAccessor() + ". "
							+ e.getMessage());
					continue;
				}
			} catch (InvocationTargetException e) {
				throw new Exception(e);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			}
		}
		result = null;
		return record;
	}

	public int execute(PreparedStatement stmt) throws SQLException, Exception {
		try {
			checkConnection();
			return stmt.executeUpdate();
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
				// TODO:
				prglog.error("[PRG] " + e);
			}
		}
	}

	public SQLPlaceholder getValuesFromDTO(DataTransferObject record) 
			throws Exception {
		return getValuesFromDTO(record, null, false);
	}

	public SQLPlaceholder getValuesFromDTO(DataTransferObject record, 
			List<String> fieldNames) 
			throws Exception {
		return getValuesFromDTO(record, fieldNames, false);
	}

	public SQLPlaceholder getValuesFromDTO(DataTransferObject record,
			List<String> fieldNames, boolean includeNullables) 
			throws Exception {
		List<String> sqlFieldNames = new ArrayList<String>();
		List<String> sqlValuePlaceholders = new ArrayList<String>();
		List<Object[]> dataValues = new ArrayList<Object[]>();
		Field[] fields = record.getClass().getDeclaredFields();
		for (Field field : fields) {
			String name = field.getName();
			String dbName = TextUtil.fromCamelCase(name);
			if(fieldNames != null && !fieldNames.contains(dbName)) {
				continue;
			}
			String accessor = TextUtil.toCamelCase("get_" + name);
			try {
				Method accessorMthd = record.getClass().getDeclaredMethod(
						accessor, new Class[] {});
				Object value = accessorMthd.invoke(record, new Object[] {});
				if (null == value) {
					if (includeNullables) {
						sqlFieldNames.add(TextUtil.fromCamelCase(name));
						if (meta.get(dbName).isNullable()) {
							sqlValuePlaceholders.add("NULL");
						} else {
							sqlValuePlaceholders.add("''");
						}
					}
				} else {
					sqlFieldNames.add(TextUtil.fromCamelCase(name));
					sqlValuePlaceholders.add("?");
					dataValues.add(new Object[] { field.getType().getName(),
							value });
				}
			} catch (Exception e) {
				throw new Exception(e);
			}
		}
		return new SQLPlaceholder(sqlFieldNames, sqlValuePlaceholders,
				dataValues);
	}

	private void logException(SQLException sqlex, String sql) {
		prglog.error("[PRG] SQLException with "
				+ "SQLState='" + sqlex.getSQLState() + "' and "
				+ "errorCode=" + sqlex.getErrorCode() + " and "
				+ "message=" + sqlex.getMessage() + "; sql was '" + sql + "'");
	}

    /**
	 * Attempts to reset the connection to the database
	 *
	 * @throws SQLException If the connection could not be re-established
	 */
	private void resetConnection() throws SQLException
	{
		// Expire the database connection
		try
		{
			if(!conn.isClosed()) {
               prglog.info("Connection if loop where it checks the open databse connection");
                conn.close();
            }
		}
		catch(SQLException e)
		{
			prglog.error("Error trying to close the database connection." );
		}
		finally
		{
			// Reopen the connection
            try {
                prglog.info("In the finally loop of reset connection, before getting the new connection");
                conn = DButil.getConnection();
            }
			catch (Exception ne){
                prglog.error("[PRG] Exception " + ne);
            }
		}
	}


	public String getLastSQL() {
		return lastSQL;
	}

    /**
	 * Runs a query against the database.  If it fails, attempts to reconnect to
	 * the database.
	 *
	 * @param query The query to run
	 * @return The result of running the query
	 * @throws SQLException If the query failed twice
	 */
	public int executeUpdate(PreparedStatement query) throws SQLException
	{
		try
		{
            //prglog.info("In the executeUpdate try loop before return statement");
			return query.executeUpdate();
   		}
		catch(SQLException e)
		{
            prglog.info("In the executeUpdate loop catch before resetting the connection");
			resetConnection();
            prglog.info("In the executeUpdate loop catch after resetting the connection and before return -1");
            return -1;
		}
	}

}
