/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test;

import info.extensiblecatalog.OAIToolkit.utils.FileIO;

import java.io.File;
import java.io.IOException;

/**
 * Constants and static functions used by test cases 
 * @author kiru
 */
public class Constants {
	
	/** The base directory. You should edit this before running tests */
	private static final String BASEDIR = "c:/workspace2/OAIToolkit";
	
	/** Test directory of the project */
	public static final String TEST_DIR  = BASEDIR + "/test";
	
	/** The directory of xsl files */
	public static final String XSL_DIR   = BASEDIR + "/xsl";
	
	/** location of test classes */
	public static final String TESTS     = BASEDIR + "/src/test";
	
	/** DB properties file */
	public static final String DB_PROPS  = TESTS + "/OAIToolkit.db.properties";

	/** Log4j properties file */
	public static final String LOG_PROPS = TESTS + "/OAIToolkit.log4j.properties";
	
	/**
	 * Copy a file from the test directory to a given subdirectory
	 * @param file
	 * @param subDir
	 */
	public static void copyToDir(String file, String subDir) {
		try {
			String inName  = TEST_DIR + "/sample_records/" + file;
			String outName = TEST_DIR + "/" + subDir + "/" + (new File(file)).getName();
			FileIO.copyFile(inName, outName);
		} catch (IOException e) {
			System.out.println(e);
		}
	}
}
