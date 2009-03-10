/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Initialize application logging.
 */
public class Logging {
	
	public static final String DEFAULT_LOGGER = "info.extensiblecatalog";
	
	/**
	 * 
	 * @param params
	 * @param propsFile
	 * @throws IOException
	 */
	public static void initLogging(LoggingParameters params, InputStream propsFile) {

		Logger logger = Logging.getLogger();
		
    	if (null != propsFile){
			try {
				Properties props = new Properties();
				props.load(propsFile);
				
				props.setProperty(
					"log4j.appender.toolkit.File", params.getLogFileAbs());
			
				/*
				props.setProperty(
					"log4j.logger.toolkit", params.getLogLevel() + ", toolkit");
				*/
		
				PropertyConfigurator.configure(props);
				logger.info(
					"Logging started in directory: " + params.getLogDir());
			} catch (IOException e1) {
				logger.error("Unable to init logging. Root Cause: " + e1);
			} finally {
				try {
					propsFile.close();
				} catch (IOException e) {
					logger.error("Unable to close logfile. Root Cause: " + e);
				}
			}
		} else {
			logger.error("Unable to init logging. Root casuse: init file" +
					" not found" );
		}
	}
	
	public static Logger getLogger(){
		return Logger.getLogger(Logging.DEFAULT_LOGGER);
	}

        public static Logger getLogger(String filename) {
            return Logger.getLogger(filename);
        }
}
