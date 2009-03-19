/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import java.io.File;
import java.io.FileFilter;

/**
 * Filters the files and accepts only those which names have 
 * .mrc or .marc extension (case insensitive)
 * @author Peter Kiraly
 */
public class MARCFileNameFilter implements FileFilter {

	/**
	 * enables files with .mrc or .marc extension
	 * @param file the file to check
	 * @return boolean true if the file is a MARC file (*.mrc)
	 */
	public boolean accept(File file){
		String name = file.getName().toLowerCase();
		return file.isFile() 
				&& (name.endsWith(".mrc") || name.endsWith(".marc"));
	}
}
