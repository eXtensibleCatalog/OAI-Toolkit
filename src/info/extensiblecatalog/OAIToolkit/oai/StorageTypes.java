/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai;

import java.util.Arrays;
import java.util.List;

/**
 * Keys of the possible storage mechanisms of the records.
 * 
 * Possible values:
 * <ol>
 * <li>MySQL -- clear MySQL storage</li>
 * <li>mixed -- records, sets, set_to_records: in MySQL, xml: in Lucene</li>
 * <li>Lucene -- clear Lucene storage</li>
 * </ol>
 * @author Kiraly Peter pkiraly@tesuji.eu
 */
public class StorageTypes {
	
	/** the key of the MySQL storage mechanism */
	public static final String MYSQL  = "MySQL";

	/** the key of the mixed storage mechanism */
	public static final String MIXED  = "mixed";
	
	/** the key of the Lucene storage mechanism */
	public static final String LUCENE = "Lucene";
	
	private static final List<String> types = Arrays.asList(new String[]{
			MYSQL, MIXED, LUCENE
	});
	
	/** check if the key is valid */
	public static boolean isValid(String storageType) {
		return types.contains(storageType);
	}
}
