/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Utility class to validate an XML file againts XML Schema.
 * @author Peter Kiraly
 */
public class XMLValidator {
	
	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
        //private static final Logger logger = Logging.getLogger();

	/**
	 * The validator object. It validates the XML file.
	 */
	private Validator validator;
	
	/**
	 * Constuct a new XMLValidator object.
	 * @param schemaFile The XML Schema file which against the XML file will
	 * be validated
	 */
	public XMLValidator(String schemaFileName) {
		
		// 1. Lookup a factory for the W3C XML Schema language
		SchemaFactory factory = SchemaFactory.newInstance(
				"http://www.w3.org/2001/XMLSchema");

		// 2. Compile the schema.
		// Here the schema is loaded from a java.io.File, but you could use
		// a java.net.URL or a javax.xml.transform.Source instead.
		File schemaFile = null;
		URL schemaURL = null;
		if(!schemaFileName.equals("")) {
			if(schemaFileName.startsWith("http")) {
				try {
					schemaURL = new URL(schemaFileName);
				} catch(MalformedURLException e) {
					prglog.error("[PRG] " + e);
				}
			} else {
				schemaFile = new File(schemaFileName);
			}
		}
		Schema schema;
		try {
			if(null != schemaFile) {
				schema = factory.newSchema(schemaFile);
			} else if(null != schemaURL) {
				schema = factory.newSchema(schemaURL);
			} else {
				schema = factory.newSchema();
			}
		} catch(SAXException ex) {
			prglog.error("[PRG] " + ex.getMessage());
			return;
		}

		// 3. Get a validator from the schema.
		validator = schema.newValidator();
	}
	
	public XMLValidator() {
		this("");
	}

	/**
	 * Validate an XML content. If nothing happens, this content is
	 * valid. If an exception raised, this content is invalid.
	 * @param xmlContent The content of an XML file to validate
	 * @throws SAXException
	 * @throws IOException
	 */
	public void validate(String xmlContent) throws SAXException, IOException {

		// 4. Parse the document you want to check.
		Source source = new StreamSource(
				new InputStreamReader(
						new ByteArrayInputStream(
								xmlContent.getBytes("UTF-8")),"UTF-8"));

		// 5. Check the document
		validator.validate(source);
		source = null;
	}

	/**
	 * Validate an XML input stream. If nothing happens, this content is
	 * valid. If an exception raised, this input stream is invalid.
	 * @param xmlContent The content of an XML file to validate
	 * @throws SAXException
	 * @throws IOException
	 */
	public void validate(InputStream xmlContent) throws SAXException, IOException {

		// 4. Parse the document you want to check.
		Source source = new StreamSource(
				new InputStreamReader(xmlContent, 
						Charset.forName("UTF-8")));

		// 5. Check the document
		validator.validate(source);
		source = null;
	}

	/**
	 * Validate an XML file. If nothing happens, this file is
	 * valid. If an exception raised, this file is invalid.
	 * @param xmlFile The XML file to validate
	 * @throws SAXException
	 * @throws IOException
	 */
	public void validate(File xmlFile) throws SAXException, IOException {

		// 4. Parse the document you want to check.
		Source source = new StreamSource(xmlFile);

		// 5. Check the document
		validator.validate(source);
	}

	/**
	 * Show the context of the error (50 characters before and after)
	 * we suppose, that the content is intended
	 * @param ex
	 * @param content
	 * @return
	 */
	public String showContext(SAXParseException ex, String content) {
		return showContext(ex, content, true);
	}

	/**
	 * Show the context of the error (50 characters before and after)
	 * @param ex - The exception
	 * @param content - The xml string
	 * @param indented - flag whether the XML is intended or not
	 * @return
	 */
	public String showContext(SAXParseException ex, String content, 
			boolean indented) {
		StringBuffer error = new StringBuffer(); 
		int lineNumber = ((SAXParseException) ex).getLineNumber();
		int charNumber = ((SAXParseException) ex).getColumnNumber();
		error.append(" The error is at line: " + lineNumber + " char #"
			+ charNumber + ".");
		String[] lines = content.split(ApplInfo.LN);
		error.append(ApplInfo.LN + "Source:" + ApplInfo.LN);
		String line = lines[lineNumber - 1];
		if(indented == false) {
			int start, end;
			if(charNumber > 50) {
				start = charNumber - 50;
				end   = charNumber + 50;
				charNumber = 50;
			} else {
				start = 0;
				end   = charNumber + 10;
			}
			if(end > line.length()-1) {
				end = line.length()-1;
			}
			error.append(line.substring(start, end));
		} else {
			error.append(line);
		}
		error.append(ApplInfo.LN);
		for (int i = 1; i < charNumber - 1; i++) {
			error.append('-');
		}
		error.append('^');
		return error.toString();
	}
}
