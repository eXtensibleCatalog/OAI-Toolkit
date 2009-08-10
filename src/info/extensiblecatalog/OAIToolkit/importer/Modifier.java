/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import info.extensiblecatalog.OAIToolkit.utils.ExceptionPrinter;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.XsltTransformator;

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.log4j.Logger;
import org.marc4j.marc.Record;

/**
 * Class to modify records with XML transformation (XSLT)
 * @author Király Péter pkiraly@tesuji.eu
 */
public class Modifier {
	
	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	/** XSLT transformators */
	private List<XsltTransformator> transformators;
	
	private boolean doIndent = false;
    private boolean doFileOfDeletedRecords = false;
	
	
	public Modifier(boolean doIndent, boolean doFileOfDeletedRecords) {
		this.doIndent = doIndent;
        this.doFileOfDeletedRecords = doFileOfDeletedRecords;
	}
	
	/**
	 * Constructor
	 * @param configuration
	 */
	public Modifier(ImporterConfiguration configuration) {
		addStyleSheets(configuration.getXslts());
		setDoIndent(configuration.isDoIndentXml());
	}

	public void addStyleSheets(List<String> xslts) {
		if(transformators == null) {
			transformators = new ArrayList<XsltTransformator>();
		}
		for(String xslt : xslts) {
			prglog.info("[PRG] registering stylesheet " + xslt);
			try {
				transformators.add(new XsltTransformator("xslts/" + xslt));
			} catch(FileNotFoundException e) {
				e.printStackTrace();
				prglog.info("[PRG] " + ExceptionPrinter.getStack(e));
			} catch(TransformerConfigurationException e) {
				e.printStackTrace();
				prglog.info("[PRG] " + ExceptionPrinter.getStack(e));
                
			}
		}
	}
	
	/** Modify a Record's XML string */
	public String modifyRecord(Record record, boolean doFileOfDeletedRecords) {
		MARCRecordWrapper marc = new MARCRecordWrapper(record, doFileOfDeletedRecords);
		marc.setDoIndentXml(doIndent);
        marc.setDoFileOfDeletedRecords(doFileOfDeletedRecords);
		return modifyRecord(marc);
	}

	/** Modify a MARCRecordWrapper's XML string */
	public String modifyRecord(MARCRecordWrapper record) {
		return modifyRecord(record.getXml());
	}

	/** Modify XML string */
	public String modifyRecord(String xml) {
		for(XsltTransformator transformator : transformators) {
			try {
				xml = transformator.transform(xml);
			} catch(TransformerException e) {
				e.printStackTrace();
				prglog.info("[PRG] " + ExceptionPrinter.getStack(e));
			}
		}
		return xml;
	}

	public boolean isDoIndent() {
		return doIndent;
	}

	public void setDoIndent(boolean doIndent) {
		this.doIndent = doIndent;
	}
}
