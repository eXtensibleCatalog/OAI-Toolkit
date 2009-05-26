/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;

import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

/**
 * A helper class to create SQL commands
 * @author Peter Kiraly
 */
public class SQLPlaceholder {

	/** The logger object, which helps to create log messages */
        private static String programmer_log = "programmer";
        protected static final Logger prglog = Logging.getLogger(programmer_log);
	//protected static Logger logger = Logging.getLogger();

	/** 
	 * List of field names 
	 */ 
	private List<String> sqlFieldNames;
	
	/** 
	 * List of value placeholders in a SQL preparation string
	 * eg. insert into book (author, title, date) VALUES (?, ?, NULL)
	 * or select * from book where author = ? 
	 */ 
	private List<String> sqlValuePlaceholders;
	
	/**
	 * The concrete values to replace with placeholders. The List
	 * contains arrays with two elements (String, Object): the first 
	 * element is the data type, the second is the value 
	 * (eg. java.lang.String, "Dickens")
	 */
	private List<Object[]> dataValues;
	
	public SQLPlaceholder(
			List<String> sqlFieldNames, 
			List<String> sqlValuePlaceholders, 
			List<Object[]> dataValues){
		this.sqlFieldNames = sqlFieldNames;
		this.sqlValuePlaceholders = sqlValuePlaceholders;
		this.dataValues = dataValues;
	}
	
	/**
	 * Product "(fieldName1, fieldName2, ...) VALUES (val1, val2, ...)"
	 * @return An SQL string protion used in an INSERT command 
	 */
	public String createInsert() {
		StringBuilder sb = new StringBuilder();
		sb.append('(').append(TextUtil.join((List)sqlFieldNames, ", ")).append(')');
		sb.append(" VALUES (")
			.append(TextUtil.join((List)sqlValuePlaceholders, ", "))
			.append(')');
		return sb.toString();
	}

	/**
	 * Product "fieldName1 = val1, fieldName2 = val2, ..."
	 * @return An SQL string protion used in an UPDATE command
	 */
	public String createUpdate() {
		return createList(", ");
	}

	/**
	 * Product "fieldName1 = val1 AND fieldName2 = val2 AND ..."
	 * @return An SQL string protion used in a WHERE command
	 */
	public String createWhere() {
		return createList(" AND ");
	}

	/**
	 * Product "fieldName1 = val1<separator>fieldName2 = val2<separator>..."
	 * If valX is "NULL", the result will be "fieldNameX IS NULL". The string is
	 * build up from atomic elements, which are name=value pairs
	 * @param separator The string which separate the name-value pairs. 
	 * @return A joined list of the field name-field value pairs
	 */
	public String createList(String separator) {
		if(sqlFieldNames.size() != sqlValuePlaceholders.size()) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		int size = sqlFieldNames.size();
		for(int i=0; i<size; i++){
			if(i > 0){
				sb.append(separator);
			}
			sb.append(sqlFieldNames.get(i));
			if(sqlValuePlaceholders.get(i).equals("NULL")) {
				sb.append(" IS NULL");
			} else { 
				sb.append(" = ").append(sqlValuePlaceholders.get(i));
			}
		}
		return sb.toString();
	}
	
	public void replacePlaceholders(PreparedStatement stmt) throws SQLException {
		replacePlaceholders(stmt, 1);
	}

	public void replacePlaceholders(PreparedStatement stmt, int counter) 
		throws SQLException {
		for(Object[] pair : dataValues) {
			if(pair[0].equals("java.lang.String")) {
				stmt.setString(counter, (String)pair[1]);
			} else if(pair[0].equals("java.sql.Date")) {
				stmt.setTimestamp(counter, (Timestamp)pair[1]);
			} else if(pair[0].equals("java.lang.Boolean")) {
				stmt.setBoolean(counter, (Boolean)pair[1]);
			} else if(pair[0].equals("java.lang.Integer")) {
				stmt.setInt(counter, ((Integer)pair[1]).intValue());
			} else if(pair[0].equals("java.sql.Timestamp")) {
				stmt.setTimestamp(counter, (Timestamp)pair[1]);
			} else if(pair[0].equals("int")) {
				stmt.setInt(counter, ((Integer)pair[1]).intValue());
			} else {
				throw new SQLException("Unhandled data type in replacement: " + pair[0]);
			}
			counter++;
		}
	}

	public String listDataValues() {
		StringBuilder sb = new StringBuilder(); 
		for(Object[] pair : dataValues) {
			if(sb.length() > 0) {
				sb.append(", ");
			}
			if(pair[0].equals("java.lang.String")) {
				sb.append((String)pair[1]);
			} else if(pair[0].equals("java.sql.Date")) {
				sb.append((Timestamp)pair[1]);
			} else if(pair[0].equals("java.lang.Boolean")) {
				sb.append((Boolean)pair[1]);
			} else if(pair[0].equals("java.lang.Integer")) {
				sb.append(((Integer)pair[1]).intValue());
			} else if(pair[0].equals("java.sql.Timestamp")) {
				sb.append((Timestamp)pair[1]);
			} else if(pair[0].equals("int")) {
				sb.append(((Integer)pair[1]).intValue());
			} else {
				//throw new SQLException("Unhandled data type in replacement: " + pair[0]);
			}
		}
		return sb.toString();
	}

	public List<Object[]> getDataValues() {
		return dataValues;
	}

	public void setDataValues(List<Object[]> dataValues) {
		this.dataValues = dataValues;
	}

	public List<String> getSqlFieldNames() {
		return sqlFieldNames;
	}

	public void setSqlFieldNames(List<String> sqlFieldNames) {
		this.sqlFieldNames = sqlFieldNames;
	}

	public List<String> getSqlValuePlaceholders() {
		return sqlValuePlaceholders;
	}

	public void setSqlValuePlaceholders(List<String> sqlValuePlaceholders) {
		this.sqlValuePlaceholders = sqlValuePlaceholders;
	}
}
