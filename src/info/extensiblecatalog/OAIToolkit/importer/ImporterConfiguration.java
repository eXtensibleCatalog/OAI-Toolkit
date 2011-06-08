/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.oai.StorageTypes;
import info.extensiblecatalog.OAIToolkit.utils.Logging;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Object to store configuration values come from command line arguments.
 * @author kiru
 */
public class ImporterConfiguration {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();
	
	private boolean isProductionMode = false; 

	/** Flag to convert file(s) with raw MARC records into MARCXML */
	private boolean needConvert = false;
	
	/** Flag to modify MARCXML file(s) before loading into the OAI repository */
	private boolean needModify = false;

	/** Flag to load file(s) into the OAI repository */
	private boolean needLoad = false;

	/** Flag to offer more detailed processing log information */
	private boolean needLogDetail = false;
	
	/** Flag to indent XML */
	private boolean doIndentXml = false;

	/**
	 * The directory that the toolkit moves files into when there is a 
	 * processing error for that file.
	 */
	private String errorDir;

	/** XML Schema file for MARCXML validation */
	private String marcSchema;

	/** Path to log files for warnings and errors */
	private String logDir = "log";
	
	/** The encoding of the MARC file */
	private String marcEncoding;
	
	/** The character conversion method. Possible values: MARC8 (Ansel), 
	 * ISO5426, ISO6937, none */
	private String charConversion;
	
	/** How many records can an XML file contain? */
	private int splitSize = 10000;
	
	/** The Lucene index directory */
	private String luceneIndex = "lucene_index";

	/** The Lucene index directory */
	private String storageType = StorageTypes.LUCENE;
	
	/** create XML 1.1? */
	private boolean createXml11 = false;
	
	/** perform validation during modify step? */
	private boolean modifyValidation = false;

	/** Translate the bad characters in the Leader (1-8, B-C, E-1F hexadecimal 
	 * codes) to zeros */
	private boolean translateLeaderBadCharsToZero = false;
        
        /** Translate the bad characters in the control and data fields (1-8, B-C, E-1F hexadecimal 
	 * codes) to spaces */
	private boolean translateNonleaderBadCharsToSpaces = false;
        
	/** The directory where the toolkit looks for files to process */
	private String sourceDir;
	
	/**
	 * The directory that the toolkit moves the source files into as it 
	 * successfully completes the processing of each file
	 */
	private String destinationDir;

	private String targetDir;

	/** 
	 * The directory that the toolkit places MARCXML versions of the source 
	 * data. 
	 */
	private String destinationXmlDir;
	
	/**
	 * The directory that the toolkit places MARCXML versions of the source 
	 * data, if that MARCXML file was unable to be loaded into the OAI 
	 * repository due to an error condition.
	 */
	private String errorXmlDir;

	/** 
	 * The directory that the toolkit places MARCXML versions of the source 
	 * data. 
	 */
	private String destinationModifiedXmlDir;
	
	/**
	 * The directory that the toolkit places MARCXML versions of the source 
	 * data, if that MARCXML file was unable to be loaded into the OAI 
	 * repository due to an error condition.
	 */
	private String errorModifiedXmlDir;

	private String convertDir        = "marc";
	private String modifyDir         = "marcxml";
	private String loadDir           = "xml";
	private String errorSuffix       = "error";
	private String destinationSuffix = "dest";
	
	private boolean doDeleteTemporaryFiles = false;

    private boolean fileOfDeletedRecords = false;

    private boolean luceneStatistics = false;
    
    private boolean luceneDumpIds = false;

	private String xsltString;
	
	private List<String> xslts = new ArrayList<String>();

	private boolean ignoreRepositoryCode = false;
	
	private String defaultRepositoryCode = null;
	
	public String getParams() {
		StringBuffer sb = new StringBuffer();
		sb.append("needConvert? ").append(needConvert);
		sb.append(", needModify? ").append(needModify);
		sb.append(", needLoad? ").append(needLoad);
		sb.append(", needLogDetail? ").append(needLogDetail);
		sb.append(", doIndentXml? ").append(doIndentXml);
		sb.append(", sourceDir: ").append(sourceDir);
		sb.append(", destinationDir: ").append(destinationDir);
		sb.append(", destinationXmlDir: ").append(destinationXmlDir);
		sb.append(", errorDir: ").append(errorDir);
		sb.append(", errorXmlDir: ").append(errorXmlDir);
        sb.append(", fileOfDeletedRecords: ").append(fileOfDeletedRecords);
		sb.append(", marcSchema: ").append(marcSchema);
		sb.append(", logDir: ").append(logDir);
		sb.append(", marcEncoding: ").append(marcEncoding);
		sb.append(", charConversion: ").append(charConversion);
		sb.append(", splitSize: ").append(splitSize);
		sb.append(", luceneIndex: ").append(luceneIndex);
		sb.append(", storageType: ").append(storageType);
		sb.append(", createXml11: ").append(createXml11);
        sb.append(", translateLeaderBadCharsToZero: ").append(translateLeaderBadCharsToZero);
        sb.append(", translateNonleaderBadCharsToSpaces: ").append(translateNonleaderBadCharsToSpaces);
		sb.append(", convertDir: ").append(convertDir);
		sb.append(", modifyDir: ").append(modifyDir);
		sb.append(", loadDir: ").append(loadDir);
		sb.append(", errorSuffix: ").append(errorSuffix);
		sb.append(", destinationSuffix: ").append(destinationSuffix);
		sb.append(", doDeleteTemporaryFiles: ").append(doDeleteTemporaryFiles);
		sb.append(", errorModifiedXmlDir: ").append(errorModifiedXmlDir);
		sb.append(", destinationModifiedXmlDir: ").append(destinationModifiedXmlDir);
		return sb.toString();
	}

	public String getCharConversion() {
		return charConversion;
	}

	public void setCharConversion(String charConversion) {
		this.charConversion = charConversion;
	}

	public String getDestinationDir() {
		return destinationDir;
	}

	public void setDestinationDir(String destinationDir) {
		this.destinationDir = destinationDir;
	}

	public String getDestinationXmlDir() {
		return destinationXmlDir;
	}

	public void setDestinationXmlDir(String destinationXmlDir) {
		this.destinationXmlDir = destinationXmlDir;
	}

	public String getErrorDir() {
		return errorDir;
	}

	public void setErrorDir(String errorDir) {
		this.errorDir = errorDir;
	}

	public String getErrorXmlDir() {
		return errorXmlDir;
	}

	public void setErrorXmlDir(String errorXmlDir) {
		this.errorXmlDir = errorXmlDir;
	}

    public boolean isFileOfDeletedRecords() {
		return fileOfDeletedRecords;
	}

	public void setFileOfDeletedRecords(boolean fileOfDeletedRecords) {
		this.fileOfDeletedRecords = fileOfDeletedRecords;
	}

	public String getLogDir() {
		return logDir;
	}

	public void setLogDir(String logDir) {
		this.logDir = logDir;
	}

	public String getMarcEncoding() {
		return marcEncoding;
	}

	public void setMarcEncoding(String marcEncoding) {
		this.marcEncoding = marcEncoding;
	}

	public String getMarcSchema() {
		return marcSchema;
	}

	public void setMarcSchema(String marcSchema) {
		this.marcSchema = marcSchema;
	}

	public boolean isNeedLogDetail() {
		return needLogDetail;
	}

	public void setNeedLogDetail(boolean needLogDetail) {
		this.needLogDetail = needLogDetail;
	}

	public String getSourceDir() {
		return sourceDir;
	}

	public void setSourceDir(String sourceDir) {
		this.sourceDir = sourceDir;
	}

	public int getSplitSize() {
		return splitSize;
	}

	public void setSplitSize(int splitSize) {
		this.splitSize = splitSize;
	}

	public void setSplitSize(String splitSize) {
		this.splitSize = Integer.parseInt(splitSize);
	}

	public boolean checkDir(String dir, boolean create, String dirName) {
		if(dir == null) {
			prglog.warn("[PRG] You should add the " + dirName + " directory.");
			return false;
		}
		if(!(new File(dir)).exists()) {
			if(create) {
				boolean created = new File(dir).mkdir();
				prglog.info("[PRG] The application created the '" 
						+ dirName + "' directory. " + created);
			} else {
				prglog.warn("[PRG] You should add an existent '" + dirName + "' directory instead of " + dir);
				return false;
			}
		}
		if(!(new File(dir)).isDirectory()) {
			prglog.warn("[PRG] " + dir + " is not a directory.");
			return false;
		}
		return true;
	}

	public boolean checkSourceDir() {
		return checkDir(sourceDir, false, "source");
	}
	
	public boolean checkDestinationDir() {
		return checkDir(destinationDir, true, "destination");
	}

	public boolean checkDestinationXmlDir() {
		return checkDir(destinationXmlDir, true, "destination_xml");
	}

	public boolean errorDir() {
		return checkDir(errorDir, true, "error");
	}

	public boolean errorXmlDir() {
		return checkDir(errorXmlDir, true, "error_xml");
	}
	
	public boolean isNeedConvert() {
		return needConvert;
	}

	public void setNeedConvert(boolean needConvert) {
		this.needConvert = needConvert;
	}

	public boolean isNeedLoad() {
		return needLoad;
	}

	public void setNeedLoad(boolean needLoad) {
		this.needLoad = needLoad;
	}

	public String getLuceneIndex() {
		return luceneIndex;
	}

	public void setLuceneIndex(String luceneIndex) {
		this.luceneIndex = luceneIndex;
	}

    public boolean isLuceneStatistics() {
		return luceneStatistics;
	}

    public boolean isLuceneDumpIds() {
		return luceneDumpIds;
	}

	public void setLuceneStatistics(boolean luceneStatistics) {
		this.luceneStatistics = luceneStatistics;
    }

	public void setLuceneDumpIds(boolean luceneDumpIds) {
		this.luceneDumpIds = luceneDumpIds;
    }
	
	public boolean isDoIndentXml() {
		return doIndentXml;
	}

	public void setDoIndentXml(boolean doIndentXml) {
		this.doIndentXml = doIndentXml;
	}

	public String getStorageType() {
		return storageType;
	}

	public void setStorageType(String storageType) {
		if(StorageTypes.isValid(storageType)) {
			this.storageType = storageType;
		} else {
			this.storageType = StorageTypes.MYSQL;
		}
	}

	public boolean isCreateXml11() {
		return createXml11;
	}

	public void setCreateXml11(boolean createXml11) {
		this.createXml11 = createXml11;
	}
        
        public boolean isTranslateLeaderBadCharsToZero() {
		return translateLeaderBadCharsToZero;
	}
        
    public void setModifyValidation(boolean mod) {
    	this.modifyValidation = mod;
    }
    
    public boolean isModifyValidation() {
    	return this.modifyValidation;
    }

	public void setTranslateLeaderBadCharsToZero(boolean translateLeaderBadCharsToZero) {
		this.translateLeaderBadCharsToZero = translateLeaderBadCharsToZero;
	}
        
        public boolean isTranslateNonleaderBadCharsToSpaces() {
		return translateNonleaderBadCharsToSpaces;
	}

	public void setTranslateNonleaderBadCharsToSpaces(boolean translateNonleaderBadCharsToSpaces) {
		this.translateNonleaderBadCharsToSpaces = translateNonleaderBadCharsToSpaces;
	}

	public boolean isNeedModify() {
		return needModify;
	}

	public void setNeedModify(boolean needModify) {
		this.needModify = needModify;
	}
	
	public String getTargetDir() {
		return targetDir;
	}

	public void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getConvertDir() {
		return convertDir;
	}

	public void setConvertDir(String convertDir) {
		this.convertDir = convertDir;
	}

	public String getLoadDir() {
		return loadDir;
	}

	public void setLoadDir(String loadDir) {
		this.loadDir = loadDir;
	}

	public String getModifyDir() {
		return modifyDir;
	}

	public void setModifyDir(String modifyDir) {
		this.modifyDir = modifyDir;
	}

	public String getErrorSuffix() {
		return errorSuffix;
	}

	public void setErrorSuffix(String errorSuffix) {
		this.errorSuffix = errorSuffix;
	}

	public String getDestinationSuffix() {
		return destinationSuffix;
	}

	public void setDestinationSuffix(String destinationSuffix) {
		this.destinationSuffix = destinationSuffix;
	}

	public boolean isDoDeleteTemporaryFiles() {
		return doDeleteTemporaryFiles;
	}

	public void setDoDeleteTemporaryFiles(boolean doDeleteTemporaryFiles) {
		this.doDeleteTemporaryFiles = doDeleteTemporaryFiles;
	}

	public String getDestinationModifiedXmlDir() {
		return destinationModifiedXmlDir;
	}

	public void setDestinationModifiedXmlDir(String destinationModifiedXmlDir) {
		this.destinationModifiedXmlDir = destinationModifiedXmlDir;
	}

	public String getErrorModifiedXmlDir() {
		return errorModifiedXmlDir;
	}

	public void setErrorModifiedXmlDir(String errorModifiedXmlDir) {
		this.errorModifiedXmlDir = errorModifiedXmlDir;
	}

	public String getXsltString() {
		return xsltString;
	}

	public void setXsltString(String xsltString) {
		this.xsltString = xsltString;
		xslts = Arrays.asList(xsltString.split(" "));
	}

	public List<String> getXslts() {
		return xslts;
	}

	public void setXslts(List<String> xslts) {
		this.xslts = xslts;
	}

	public boolean isProductionMode() {
		return isProductionMode;
	}

	public void setProductionMode(boolean isProductionMode) {
		this.isProductionMode = isProductionMode;
	}
	
	public boolean doesIgnoreRepositoryCode() {
		return ignoreRepositoryCode;
	}

	public void setIgnoreRepositoryCode(boolean ignoreRepositoryCode) {
		this.ignoreRepositoryCode = ignoreRepositoryCode;
	}

	public String getDefaultRepositoryCode() {
		return defaultRepositoryCode;
	}

	public void setDefaultRepositoryCode(String defaultRepositoryCode) {
		this.defaultRepositoryCode = defaultRepositoryCode;
	}

}
