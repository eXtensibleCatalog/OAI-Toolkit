/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.utils.Logging;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * Gives right directory names for the given methods
 * @author Király Péter pkiraly@tesuji.eu
 */
public class DirectoryNameGiver {
	
	/** The logger object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	private static final String MARC        = "marc";
	private static final String MARCXML     = "marcxml";
	private static final String TEMPMARCXML = "tempMarcxml";
	private static final String MODIFIEDXML = "modifiedxml";
	
	/** The configuration is the source of all decisions */
	private ImporterConfiguration configuration;
	
	/** The source directory of convert() */
	private File convertSource = null;

	/** The target directory of convert() */
	private File convertTarget = null;

	/** The target directory of convert() */
	private File convertError = null;

	/** The target directory of convert() */
	private File convertDestination = null;

	/** The source directory of modify() */
	private File modifySource  = null;

	/** The target directory of modify() */
	private File modifyTarget  = null;

	/** The target directory of convert() */
	private File modifyError = null;

	/** The target directory of convert() */
	private File modifyDestination = null;

	/** The source directory of load() */
	private File loadSource    = null;

	/** The target directory of load() */
	private File loadTarget    = null;

	private File loadError = null;

	/** The target directory of convert() */
	private File loadDestination = null;
	
	/**
	 * Create a new instance. 
	 * @param _configuration The configuration object stores all information
	 * needed for giving directory names 
	 */
	public DirectoryNameGiver(ImporterConfiguration _configuration) {
		configuration = _configuration;
		initConvert();
		initModify();
		initLoad();
	}
	
	/**
	 * Establish the directories in the conversion process (source, target,
	 * destination, and error directories) 
	 */
	private void initConvert() {
		// convertSource && convertTarget
		if(configuration.isNeedConvert()) {
			String basedir = configuration.getConvertDir();

			// name the source dir
			if(configuration.getSourceDir() != null) {
				convertSource = new File(configuration.getSourceDir());
			} else {
				convertSource = new File(MARC);
			}

			// name the target dir
			if(configuration.getDestinationXmlDir() != null) {
				convertTarget = new File(configuration.getDestinationXmlDir());
			} else {
				File parent = convertSource.getParentFile();
				convertTarget = new File(parent, MARCXML);
			}

			// name the error and destination dirs
			if(basedir.indexOf("/") >-1 || basedir.indexOf("\\") >-1) {
				convertError = new File(basedir, configuration.getErrorSuffix());
				convertDestination = new File(basedir, 
						configuration.getDestinationSuffix());
			} else {
				convertError = new File(basedir + '_' 
					+ configuration.getErrorSuffix());
				convertDestination = new File(basedir + '_' 
					+ configuration.getDestinationSuffix());
			}

			// create error dir
			if(!convertError.exists()) {
				boolean created = convertError.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create convert error directory");
				}

			}

			// create destination dir
			if(!convertDestination.exists()) {
				boolean created = convertDestination.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create convert destination directory");
				}
			}
		}
	}
	
	/**
	 * Establish the directories in the modify process (source, target,
	 * destination, and error directories) 
	 */
	private void initModify() {
		// modifySource && modifyTarget
		if(configuration.isNeedModify()) {
			
			// name the source dir
			if(configuration.isNeedConvert()) {
				modifySource = convertTarget;
			} else {
				if(configuration.getSourceDir() != null) {
					modifySource = new File(configuration.getSourceDir());
				} else {
					modifySource = new File(MARCXML);
				}
			}

			File parent = modifySource.getParentFile();
			// name the target dir
			if(configuration.isNeedLoad()) {
				modifyTarget = new File(parent, MODIFIEDXML);
			} else {
				if(configuration.getDestinationDir() != null) {
					modifyTarget = new File(configuration.getDestinationDir());
				} else {
					modifyTarget = new File(MODIFIEDXML);
				}
			}

			// name the error and destination dirs
			String basedir = configuration.getModifyDir();
			if(basedir.indexOf("/") >-1 || basedir.indexOf("\\") >-1) {
				modifyError = new File(basedir, configuration.getErrorSuffix());
				modifyDestination = new File(basedir, 
					configuration.getDestinationSuffix());
			} else {
				modifyError = new File(basedir + '_' 
					+ configuration.getErrorSuffix());
				modifyDestination = new File(basedir + '_' 
					+ configuration.getDestinationSuffix());
			}
			
			// create target dir
			if(!modifyTarget.exists()) {
				boolean created = modifyTarget.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create modify target directory");
				}
			}

			// create error dir
			if(!modifyError.exists()) {
				boolean created = modifyError.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create modify error directory");
				}
			}

			// create destination dir
			if(!modifyDestination.exists()) {
				boolean created = modifyDestination.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create modify destination directory");
				}
			}
		}
	}

	/**
	 * Establish the directories in the load process (source, destination, 
	 * and error directories) 
	 */
	private void initLoad() {

		// loadSource && loadTarget
		if(configuration.isNeedLoad()) {

			// name load source directory
			if(configuration.isNeedConvert() && !configuration.isNeedModify()) {
				loadSource = convertTarget;
			} else if(configuration.isNeedModify()){
				loadSource = modifyTarget;
			} else {
				if(configuration.getSourceDir() != null) {
					loadSource = new File(configuration.getSourceDir());
				} else {
					loadSource = new File(MARCXML);
				}
			}
			
			String basedir = configuration.getLoadDir();
			
			// name load destination directory
			if(configuration.isNeedModify()) {
				loadDestination = new File(loadSource.getName() + "_" 
						+ configuration.getDestinationSuffix());
			} else if(configuration.getDestinationModifiedXmlDir() != null) {
				loadDestination = new File(configuration
						.getDestinationModifiedXmlDir());
			} else if(configuration.isNeedConvert() &&
					!configuration.isNeedModify()) {
				loadDestination = new File(loadSource.getName() + "_" 
						+ configuration.getDestinationSuffix());
			} else {
				if(basedir.indexOf("/") >-1 || basedir.indexOf("\\") >-1) {
					loadDestination = new File(basedir, configuration
							.getDestinationSuffix());
				} else {
					loadDestination = new File(basedir + '_'
						+ configuration.getDestinationSuffix());
				}
			}

			// name load error directory
			if(configuration.isNeedModify()) {
				loadError = new File(MODIFIEDXML + "_" + configuration.getErrorSuffix());
			} else if(configuration.getErrorModifiedXmlDir() != null) {
				loadError = new File(configuration.getErrorModifiedXmlDir());
			} else if(configuration.isNeedConvert() &&
					!configuration.isNeedModify()) {
				loadError = new File(loadSource.getName() + "_" 
						+ configuration.getErrorSuffix());
			} else {
				if(configuration.getDestinationModifiedXmlDir() != null) {
					loadError = new File(MODIFIEDXML + "_" 
							+ configuration.getErrorSuffix());
				} else {
					if(basedir.indexOf("/") >-1 || basedir.indexOf("\\") >-1) {
						loadError = new File(basedir, configuration.getErrorSuffix());
					} else {
						loadError = new File(basedir + '_' 
						+ configuration.getErrorSuffix());
					}
				}
			}

			// create load destination directory
			if(!loadDestination.exists()) {
				boolean created = loadDestination.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create load destination directory");
				}
			}
			
			// create load error directory
			if(!loadError.exists()) {
				boolean created = loadError.mkdir();
				if(!created) {
					prglog.error("[PRG] Unable to create load error directory");
				}
			}
		}
	}
	
	/**
	 * Write out the directory names
	 * @return
	 */
	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("[DirectoryNameGiver]");
		sb.append(" convert source: ").append(convertSource);
		sb.append(", convert target: ").append(convertTarget);
		sb.append(", convert destination: ").append(convertDestination);
		sb.append(", convert error: ").append(convertError);
		sb.append(", modify source: ").append(modifySource);
		sb.append(", modify target: ").append(modifyTarget);
		sb.append(", modify destination: ").append(modifyDestination);
		sb.append(", modify error: ").append(modifyError);
		sb.append(", load source: ").append(loadSource);
		sb.append(", load target: ").append(loadTarget);
		sb.append(", load destination: ").append(loadDestination);
		sb.append(", load error: ").append(loadError);
		return sb.toString();
	}

	public File getConvertSource() {
		return convertSource;
	}

	public void setConvertSource(File convertSource) {
		this.convertSource = convertSource;
	}

	public File getConvertTarget() {
		return convertTarget;
	}

	public void setConvertTarget(File convertTarget) {
		this.convertTarget = convertTarget;
	}

	public File getModifySource() {
		return modifySource;
	}

	public void setModifySource(File modifySource) {
		this.modifySource = modifySource;
	}

	public File getModifyTarget() {
		return modifyTarget;
	}

	public void setModifyTarget(File modifyTarget) {
		this.modifyTarget = modifyTarget;
	}

	public File getLoadSource() {
		return loadSource;
	}

	public void setLoadSource(File loadSource) {
		this.loadSource = loadSource;
	}

	public File getLoadTarget() {
		return loadTarget;
	}

	public void setLoadTarget(File loadTarget) {
		this.loadTarget = loadTarget;
	}

	public File getConvertError() {
		return convertError;
	}

	public void setConvertError(File convertError) {
		this.convertError = convertError;
	}

	public File getConvertDestination() {
		return convertDestination;
	}

	public void setConvertDestination(File convertDestination) {
		this.convertDestination = convertDestination;
	}

	public File getModifyError() {
		return modifyError;
	}

	public void setModifyError(File modifyError) {
		this.modifyError = modifyError;
	}

	public File getModifyDestination() {
		return modifyDestination;
	}

	public void setModifyDestination(File modifyDestination) {
		this.modifyDestination = modifyDestination;
	}

	public File getLoadError() {
		return loadError;
	}

	public void setLoadError(File loadError) {
		this.loadError = loadError;
	}

	public File getLoadDestination() {
		return loadDestination;
	}

	public void setLoadDestination(File loadDestination) {
		this.loadDestination = loadDestination;
	}
}
