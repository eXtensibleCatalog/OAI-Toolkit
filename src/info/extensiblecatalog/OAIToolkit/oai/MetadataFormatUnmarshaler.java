/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;

import info.extensiblecatalog.OAIToolkit.utils.Logging;

/**
 * Load metadata format information from an xml file
 * @author Peter Kiraly
 */
public class MetadataFormatUnmarshaler {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	public static MetadataFormats load(String xmlFileName, String mapFileName) {
		return load(new File(xmlFileName), new File(mapFileName));
	}

	public static MetadataFormats load(File xmlFile, File mapFile) {
		Mapping mapping = new Mapping();

		FileReader reader = null;
		try {
			reader = new FileReader(xmlFile);
			
			mapping.loadMapping(mapFile.getAbsolutePath());
			Unmarshaller unmarshaller = new Unmarshaller(MetadataFormats.class);
            unmarshaller.setMapping(mapping);

			MetadataFormats formats = (MetadataFormats)unmarshaller.unmarshal(reader);
			return formats;
		} catch(FileNotFoundException e){
			prglog.error("[PRG] File not found: " + e);
			e.printStackTrace();
			return null;
		} catch(IOException e){
			prglog.error("[PRG] ERROR: " + e);
			e.printStackTrace();
			return null;
		} catch(ValidationException e) {
			prglog.error("[PRG] Validation error: " + e);
			e.printStackTrace();
			return null;
		} catch(MappingException e) {
			prglog.error("[PRG] Mapping error: " + e);
			e.printStackTrace();
			return null;
		} catch(MarshalException e) {
			prglog.error("[PRG] Marshal error: " + e);
			e.printStackTrace();
			return null;
		} finally {
			if(reader != null) {
				try {
					reader.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
