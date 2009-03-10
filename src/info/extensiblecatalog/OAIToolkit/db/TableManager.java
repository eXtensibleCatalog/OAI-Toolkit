/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import java.sql.SQLException;
import java.util.Map;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;

/**
 * Table manager interface. The implementors should create these methods
 * @author Peter Kiraly
 */
public interface TableManager {
	
	/** get by id */
	DataTransferObject get(int id) throws SQLException;
	
	/** insert a record */
	int insert(DataTransferObject obj) throws SQLException;
	
	/** delete a record */
	int delete(DataTransferObject obj) throws SQLException;
	
	/** delete by id */
	int delete(int id) throws SQLException;
	
	/** modify a record */
	int update(Map criteria, DataTransferObject obj) throws SQLException;
}
