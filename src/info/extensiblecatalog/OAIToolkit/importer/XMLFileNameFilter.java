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
 * Filter the XML files - the files with .xml extension (case insensitive)
 * @author Peter Kiraly
 */
public class XMLFileNameFilter implements FileFilter {
	/**
	 * @param file the file to check
	 * @return boolean true if the file is an XML file (*.xml)
	 */
	public boolean accept(File file){
		return file.isFile() 
				&& file.getName().toLowerCase().endsWith(".xml");
	}
}
