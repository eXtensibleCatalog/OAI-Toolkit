/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

/**
 * Constant values used by the importing process 
 * @author kiru
 */
public class ImporterConstants {

	/**
	 * Default MARC Schema file. The MARCXML will be validated against it, if
	 * no other schema file is given in the command line with -marc_schema argument.
	 * The default file is: <a href="http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd">http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd</a>
	 */
	public static final String MARC_SCHEMA_URL = 
		"http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd";
	
	/** The prefix of the file where the error records are writen */
	public static final String ERROR_FILE_PREFIX = "error_records_in_";
	
	/** The max size of a blob field in MySQL */
	public static final double BLOB_LIMIT = Math.pow(2, 16);

	/**
	 * Types of the result of import. Possible values are:
	 * <ul>
	 * <li>CREATED: A new record was created</li>
	 * <li>UPDATED: An old record was found and its value was updated</li>
	 * <li>SKIPPED: This record was skipped (the new and old was the same)</li>
	 * <li>INVALID: This record was invalid</li>
	 * <li>DELETED: The record was deleted</li>
	 * <li>BIBLIOGRAPHIC: The record was a bibliographic one</li>
	 * <li>AUTHORITY: The record was an authority one</li>
	 * <li>HOLDINGS: The record was a holding one</li>
	 * <li>CLASSIFICATION: The record was a classification one</li>
	 * <li>COMMUNITY: The record was a community one</li>
	 * <li>INVALID_FILES: The file was invalid</li>
	 * </ul>
	 */
	public enum ImportType { 
		/** A new record created */
		CREATED, 
		
		/** An old record was found and its value was updated */
		UPDATED,
		
		/** The record was skipped (the new and old was the same) */
		SKIPPED,
		
		/** The record was invalid */
		INVALID,
		
		/** The record was deleted */
		DELETED,
		
		/** The record was a bibliographic one */
		BIBLIOGRAPHIC,
		
		/** The record was an authority one */
		AUTHORITY,
		
		/** The record was a holding one */
		HOLDINGS,
		
		/** The record was an classification one */
		CLASSIFICATION,
		
		/** The record was an community one */
		COMMUNITY,
		
		/** The file was invalid */
		INVALID_FILES
	};

}
