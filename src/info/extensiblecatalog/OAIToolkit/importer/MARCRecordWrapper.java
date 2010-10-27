/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.marc4j.MarcException;
import org.marc4j.MarcXmlReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.Leader;
import org.marc4j.marc.Record;

import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;
import info.extensiblecatalog.OAIToolkit.utils.ExceptionPrinter;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

/**
 * Wrapper class for the Marc4j's Record class. This wraps some
 * methods and creates new ones which are missing in the Record.
 * @author Peter Kiraly pkiraly@tesuji.eu
 */
public class MARCRecordWrapper {
	
	private static String library_loadlog = "librarian_load";
        private static String programmer_log = "programmer";

	private static final Logger libloadlog = Logging.getLogger(library_loadlog);
        private static final Logger prglog = Logging.getLogger(programmer_log);

   /** The configurations came from command line arguments */
	public ImporterConfiguration configuration = new ImporterConfiguration();

        /** The logger object */
	//private static final Logger logger = Logging.getLogger();

	/** regex pattern for malformed last transaction date string */
	private final Pattern malformedTransactionPattern = 
		Pattern.compile("^\\d{14}\\.\\d $");

	/** The base Record, which is wrapped in this object */
	private final Record record;
	
	/** The Leader of the record */
	private Leader leader;
	
	/** abbreviation of bibliographic record type */
	public static final String REC_TYPE_BIBLIOGRAPHIC         = "bib";

	/** abbreviation of authority record type */
	public static final String REC_TYPE_AUTHORITY             = "auth";

	/** abbreviation of holdings record type */
	public static final String REC_TYPE_HOLDINGS              = "hold";

	/** abbreviation of classification record type */
	public static final String REC_TYPE_CLASSIFICATION        = "class";
	
	/** abbreviation of community information record type */
	public static final String REC_TYPE_COMMUNITY_INFORMATION = "comm";
	
	/** 
	 * The status code of the record (Leader/05)<br/>
	 * a - Increase in encoding level<br/>
	 * c - Corrected or revised<br/>
	 * d - Deleted<br/>
	 * n - New<br/>
	 * p - Increase in encoding level from prepublication
	 */
	private char status;
	
	/** 
	 * <p>The type of the record (Leader/06)</p> 
	 * 
	 * <p>
	 * BIBLIOGRAPHIC:<br/>
	 * a - Language material<br/>
	 * c - Notated music<br/>
	 * d - Manuscript notated music<br/> 
	 * e - Cartographic material<br/>
	 * f - Manuscript cartographic material<br/> 
	 * g - Projected medium<br/>
	 * i - Nonmusical sound recording<br/> 
	 * j - Musical sound recording<br/>
	 * k - Two-dimensional nonprojectable graphic<br/> 
	 * m - Computer file <br/>
	 * o - Kit<br/>
	 * p - Mixed materials<br/>
	 * r - Three-dimensional artifact or naturally occurring object<br/> 
	 * t - Manuscript language material
	 * </p>
	 * 
	 * <p>
	 * AUTHORITY:<br/>
	 * z - Authority data
	 * </p>
	 * 
	 * <p>
	 * HOLDINGS:<br/>
	 * u - Unknown<br/>
	 * v - Multipart item holdings<br/>
	 * x - Single-part item holdings<br/>
	 * y - Serial item holdings
	 * </p>
	 * 
	 * <p>
	 * CLASSIFICATION<br/>
	 * w - Classification data 
	 * </p>
	 * 
	 * <p>
	 * COMMUNITY INFORMATION:<br/>
	 * q - Community information
	 * </p> 
	 */
	private char type;
	
	/**
	 * Bibliographic level<br/>
	 * a - Monographic component part<br/> 
	 * b - Serial component part<br/>
	 * c - Collection<br/>
	 * d - Subunit<br/>
	 * i - Integrating resource<br/>
	 * m - Monograph/Item<br/>
	 * s - Serial<br/>
	 */
	private char bibliographicLevel;
	
	/** The MARCXML version of MARC record */
	private String xml;
	
	/** The 003 field */
	private String repositoryCode = null;
	
	/** The current file in which this marc record takes place */
	private String currentFile = "";
	
	/** flag to indent xml */
	private boolean doIndentXml = false;

	/** flag to create XML 1.1 (true) or 1.0 (false) */
	private boolean createXml11 = false;

    /** flag to indeicate whether the parameter -fileof_deleted_records is true or false */
    private boolean doFileOfDeletedRecords = false;

	/** create a new object */
	public MARCRecordWrapper(Record record, boolean doFileOfDeletedRecords){
		this.record = record;
        this.doFileOfDeletedRecords = doFileOfDeletedRecords;
		init();
	}

	/** 
	 * create a new object
	 * @param record The raw Marc4j Record
	 * @param currentFile The name of the file in which the record takes place
	 */
	public MARCRecordWrapper(Record record, String currentFile, boolean doFileOfDeletedRecords){
		this(record, currentFile, false, doFileOfDeletedRecords);
	}
	
	/** 
	 * create a new object
	 * @param record The raw Marc4j Record
	 * @param currentFile The name of the file in which the record takes place
	 * @param createXml11 Flag to create XML 1.1 (true) or 1.0 (false)
	 */
	public MARCRecordWrapper(Record record, String currentFile, boolean createXml11, boolean doFileOfDeletedRecords){
		this.record = record;
		this.currentFile = currentFile;
		this.createXml11 = createXml11;
        this.doFileOfDeletedRecords = doFileOfDeletedRecords;
        init();
	}

	/**
	 * Initialize the record: {@link #setLeader()}, {@link #setStatus()}, 
	 * {@link #setType()}, and {@link #setBibliographicLevel()}    
	 */
	private void init() {
		setLeader();
        //libloadlog.info("The file of deleted records value is:" + getDoFileOfDeletedRecords());
        if(getDoFileOfDeletedRecords())
        {
            setStatus('d');
        }
        else setStatus();
		setType();
		setBibliographicLevel();
		setRepositoryCode();
	}

	/** Read the Leader of the record into the leader property */
	private void setLeader() {
		leader = record.getLeader();
	}
	
	/** Read the 003 of the record into the repository code property */
	private void setRepositoryCode() {
		ControlField the003 = (ControlField)record.getVariableField("003");
		if(null != the003) {
			repositoryCode = the003.getData();
		}		
	}
	
	/**
	 * Get the repository code
	 * @return The repository code
	 */
	public String getRepositoryCode() {
		return repositoryCode;
	}

	/** Read the status code of the record into the status property */
	private void setStatus() {
		status = leader.getRecordStatus();
	}

    private void setStatus(char st) {
        status = st;
    }
	/**  
	 * Get the status code of the record
	 * @return The status code
	 */
	public char getStatus() {
		return status;
	}

	/** Read the type code of the record into the type property */
	private void setType() {
		type = leader.getTypeOfRecord();
	}

	/** Get the type code of the record 
	 * @return The type code
	 */
	public char getType() {
		return type;
	}
	
	private void setBibliographicLevel() {
		bibliographicLevel = leader.getImplDefined1()[0];
	}

	/** The record is an authoritiy record */
	public boolean isAuthority() {
		return 'z' == type;
	}

	/** The record is a holdings record */
	public boolean isHolding() {
		return 'u' == type || 'v' == type || 'x' == type || 'y' == type;
	}

	/** The record is a classification record */
	public boolean isClassification() {
		return 'w' == type;
	}

	/** The record is a classification record */
	public boolean isCommunityInformation() {
		return 'q' == type;
	}
	
	/** Get the abbreviation type of the record */
	public String getRecordTypeAbbreviation() {
		if(isCommunityInformation()) {
			return REC_TYPE_COMMUNITY_INFORMATION;
		} else if(isClassification()) {
			return REC_TYPE_CLASSIFICATION;
		} else if(isHolding()) {
			return REC_TYPE_HOLDINGS;
		} else if(isAuthority()) {
			return REC_TYPE_AUTHORITY;
		} else {
			return REC_TYPE_BIBLIOGRAPHIC;
		}
	}

	public ImportType getRecordTypeAsImportType() {
		if(isCommunityInformation()) {
			return ImportType.COMMUNITY;
		} else if(isClassification()) {
			return ImportType.CLASSIFICATION;
		} else if(isHolding()) {
			return ImportType.HOLDINGS;
		} else if(isAuthority()) {
			return ImportType.AUTHORITY;
		} else {
			return ImportType.BIBLIOGRAPHIC;
		}
	}

	/** Is the record deleted? */
	public boolean isDeleted() {
        return 'd' == status ||
			('z' == type && ('s' == status || 'x' == status));
	}
	
	/** Is the record a new one? */
	public boolean isNew() {
		return 'n' == status;
	}
	
	/** Get the ID of the record */
	public String getId() {
		if(record.getControlNumberField() == null) {
			return null;
		} else {
			return record.getControlNumber();
		}
	}

	/** Get the last transaction's date as String */
	public String getLastTransactionString() {
		ControlField lastTrField = (ControlField)record.getVariableField("005");
		if(null == lastTrField) {
			libloadlog.trace("[LIB] MARC record " + currentFile + "#" + getId() 
					+ " doesn't have last transaction date");
                        prglog.trace("[PR] MARC record " + currentFile + "#" + getId() 
					+ " doesn't have last transaction date");
			return "0";
		} else {
			return lastTrField.getData();
		}
	}

	/** Get the last transaction's date as sql timestamp */  
	public Timestamp getLastTransactionDate() {
		Timestamp timestamp;
		String lastTransaction = getLastTransactionString();
		if(malformedTransactionPattern.matcher(lastTransaction).matches()) {
			libloadlog.debug("[LIB] MARC record " + currentFile + "#" + getId() + " has malformed timestamp: '" + lastTransaction 
					+ "'. Converted to '" + lastTransaction.trim() + "'");
                        prglog.debug("[PRG] MARC record " + currentFile + "#" + getId() + " has malformed timestamp: '" + lastTransaction 
					+ "'. Converted to '" + lastTransaction.trim() + "'");
			lastTransaction = lastTransaction.trim();
			
			// modify the field
			ControlField lastTrField = (ControlField)record.getVariableField("005");
			lastTrField.setData(lastTransaction);

			// recreate XML
			if(null != xml) {
				createXml();
			}
		}
		try {
			timestamp = TextUtil.stringToTimestamp(lastTransaction);
		} catch(NumberFormatException e) {
			libloadlog.error("[LIB] Malformed MARC timestamp: '" + lastTransaction 
					+ "' in record #" + getId() + ". Cause: " + e.getCause());
                        prglog.error("[PRG] Malformed MARC timestamp: '" + lastTransaction 
					+ "' in record #" + getId() + ". Cause: " + e.getCause());
			timestamp = new Timestamp(new Date().getTime());
		} catch(ParseException e) {
			if(!lastTransaction.equals("0")) {
				libloadlog.trace("[LIB] Malformed MARC timestamp: " + e 
					+ " LastTransaction: " + lastTransaction);
                                prglog.trace("[PRG] Malformed MARC timestamp: " + e 
					+ " LastTransaction: " + lastTransaction);
			}
			timestamp = new Timestamp(new Date().getTime());
		}
		return timestamp;
	}

	/** Get the XML presentation of the record */
	public String getXml() {
		if(null == xml) {
			createXml();
		}
		return xml;
	}

	/**
	 * Create the MARCXML from MARC Record. Store this in 
	 * {@link #xml} variable 
	 */
	private void createXml() {
		ByteArrayOutputStream stringOut = new ByteArrayOutputStream();
		MarcXmlWriter writer = new MarcXmlWriter(stringOut, "UTF-8", doIndentXml); //, createXml11);
		//writer.setIndent(doIndentXml);
		writer.write(record);
		writer.close();
		try {
			xml = stringOut.toString("UTF-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			xml = stringOut.toString();
		}
	}

	/**
	 * Decides the type of the source. The type is used by those MARC fields,
	 * which may have alternative values for different material types.
	 * 
	 * <p>
	 * If Leader/06 = a and Leader/07 = a, c, d, or m: Books<br/>
	 * If Leader/06 = a and Leader/07 = b, i, or s: Continuing Resources<br/>
	 * If Leader/06 = t: Books<br/>
	 * If Leader/06 = c, d, i, or j: Music<br/>
	 * If Leader/06 = e, or f: Maps<br/>
	 * If Leader/06 = g, k, o, or r: Visual Materials<br/>
	 * If Leader/06 = m: Computer Files<br/>
	 * If Leader/06 = p: Mixed Materials<br/>
	 * </p> 
	 * @see http://www.loc.gov/marc/bibliographic/bdleader.html
	 * @return
	 */
	public String getSourceType() {
		//char t = type;
		char l = bibliographicLevel;
		if('a' == type && ('a' == l || 'c' == l || 'd' == l || 'm' == l)) {
			return "Books";
		} else if('a' == type && ('b' == l || 'i' == l || 's' == l)) {
			return "Continuing Resources";
		} else if('t' == type) {
			return "Books";
		} else if('c' == type || 'd' == type || 'i' == type || 'j' == type) {
			return "Music";
		} else if('e' == type || 'f' == type) {
			return "Maps";
		} else if('g' == type || 'k' == type || 'o' == type || 'r' == type) {
			return "Visual Materials";
		} else if('m' == type) {
			return "Computer Files";
		} else if('p' == type) {
			return "Mixed Materials";
		} else {
			return "";
		}
	}

	/**
	 * Set the doIndentt flag. If this is true, the XML will be indented.
	 * @param doIndentXml
	 */
	public void setDoIndentXml(boolean doIndentXml) {
		this.doIndentXml = doIndentXml;
	}
	
    /** Sets the value of the flag -fileof_deleted_records to its corresponding boolean value */
    public void setDoFileOfDeletedRecords(boolean doFileOfDeletedRecords) {
        this.doFileOfDeletedRecords = doFileOfDeletedRecords;
    }

    public boolean getDoFileOfDeletedRecords() {
        return doFileOfDeletedRecords;
    }

	/** Return the first Record from MARCXML */
	public static Record MARCXML2Record(String MARCXML) {
		List<Record> records = MARCXML2Records(MARCXML);
		if(records.size() > 0) {
			return records.get(0);
		}
		return null;
	}

	/** Creates a List of Records from MARCXML */
	public static List<Record> MARCXML2Records(String MARCXML) {
		List<Record> records = new ArrayList<Record>();
		try {
			MarcXmlReader reader = new MarcXmlReader(
				new ByteArrayInputStream(MARCXML.getBytes("UTF-8")));
			while(reader.hasNext()) {
				records.add(reader.next());
			}
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			prglog.error("[PRG] " + ExceptionPrinter.getStack(e));
		} catch(MarcException e) {
			e.printStackTrace();
			prglog.error("[PRG] " + ExceptionPrinter.getStack(e));
		}
		return records;
	}
}
