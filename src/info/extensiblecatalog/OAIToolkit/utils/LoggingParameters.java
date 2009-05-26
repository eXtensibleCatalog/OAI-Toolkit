/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.File;

/**
 * Bean to store logging related parameters
 */
public class LoggingParameters {

	/**
	 * The directory where the log files are stored
	 */
	private String logDir;
	
	/**
	 * The file to write log information 
	 */
	private String logFile = "toolkit.log";
	
	/**
	 * The base level of logging
	 */
	private String logLevel   = "DEBUG";

	public LoggingParameters(){
	}
	
	public LoggingParameters(String logDir){
		this.logDir = logDir;
	}
	
	/** 
	 * Validate whether the log directory exists or not
	 * @return boolean value
	 * @throws ConfigurationException
	 */
	public boolean isValid() throws ToolkitException {
		File logDirectory = new File(logDir);
		
		if (!logDirectory.exists()) {
			throw new ToolkitException(
				"Inexistent logging directory: " + logDir + ". "
				+ "Please create it or specify an other one.");
		}
		
		return true;
	}
		
	/**
	 * Return the level of logging
	 * @return Returns the logLevel.
	 */
	public String getLogLevel() {
		return logLevel;
	}
	
	/**
	 * @param indexingLogLevel The logLevel to set.
	 */
	public void setLogLevel(String logLevel) {
		if (null != logLevel) {
			this.logLevel = logLevel;
		}
	}

	/**
	 * @return Returns the logDir.
	 */
	public String getLogDir() {
		return logDir;
	}

	/**
	 * @param logDir The logDir to set.
	 */
	public void setLogDir(String logDir) {
		if (null != logDir) {
			this.logDir = logDir;
		}
	}
	
	/**
	 * @return Returns the logFile.
	 */
	public String getLogFile() {
		return logFile;
	}
	
	/**
	 * Returns the logFile's absolute path
	 * @return logFile The logFile's absolute path.
	 */
	public String getLogFileAbs() {
		return logDir + "/" + logFile;
	}
	
	/**
	 * @param logFile The logFile to set.
	 */
	public void setLogFile(String logFile) {
		if (null != logFile) {
			this.logFile = logFile;
		}
	}
}
