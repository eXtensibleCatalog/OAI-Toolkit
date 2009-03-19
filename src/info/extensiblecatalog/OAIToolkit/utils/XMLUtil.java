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

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/** 
 * Utility class for different XML manipulations
 * @author Peter Kiraly
 */
public class XMLUtil {
	
        /** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	/** an XML formatter object from JDOM */
	public static final XMLOutputter format = new XMLOutputter("  ", true);

	/** XML builder object */
	public static SAXBuilder builder = new SAXBuilder(
			"org.apache.xerces.parsers.SAXParser");
	
	/** MARC namespace */
	public static Namespace marcNs = Namespace.getNamespace(
			"http://www.loc.gov/MARC21/slim");

	/** 
	 * Validate an XML file
	 * @param xmlFile The xml file to validate 
	 * @param schemaFileName The path of the schema file
	 * @return True if the file valid, false if the file invalid
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean isValid(File xmlFile, String schemaFileName)
			throws ParserConfigurationException, IOException, SAXException {
		return isValid(xmlFile, schemaFileName, true);
	}

	/** 
	 * Validate an XML file
	 * @param xmlFile The xml file to validate 
	 * @param schemaFileName The path of the schema file
	 * @param needLogDetail Flag indicating whether to create detailed logs or not
	 * @return True if the file valid, false if the file invalid
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	public static boolean isValid(File xmlFile, String schemaFileName, 
			boolean needLogDetail)
			throws ParserConfigurationException, IOException, SAXException {

		System.setProperty(
			"javax.xml.validation.SchemaFactory:http://www.w3.org/2001/XMLSchema",
			"org.apache.xerces.jaxp.validation.XMLSchemaFactory");

		if (needLogDetail) {
			prglog.info("[PRG] Validating against schema file: " + schemaFileName);
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document xsdDocument = dBuilder.parse(schemaFileName);
		DOMSource xsdDomSource = new DOMSource(xsdDocument);

		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdDomSource);
		dbFactory.setSchema(schema);

		Document document = dBuilder.parse(xmlFile);
		DOMSource domSource = new DOMSource(document);

		Validator validator = schema.newValidator();
		validator.validate(domSource);

		return true;
	}

	public static boolean isValid(String xmlContent, String schemaFileName) 
			throws ParserConfigurationException, IOException, SAXException {
		return isValid(xmlContent, schemaFileName, true);
	}
	public static boolean isValid(String xmlContent, String schemaFileName, 
			boolean needLogDetail)
			throws ParserConfigurationException, IOException, SAXException {

		System.setProperty(
			"javax.xml.validation.SchemaFactory:http://www.w3.org/2001/XMLSchema",
			"org.apache.xerces.jaxp.validation.XMLSchemaFactory");

		if (needLogDetail) {
			prglog.info("[PRG] Validating against schema file: " + schemaFileName);
		}

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document xsdDocument = dBuilder.parse(schemaFileName);
		DOMSource xsdDomSource = new DOMSource(xsdDocument);

		SchemaFactory schemaFactory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdDomSource);
		dbFactory.setSchema(schema);

		Document document = dBuilder.parse(
				new ByteArrayInputStream(xmlContent.getBytes("UTF-8")));
		DOMSource domSource = new DOMSource(document);

		Validator validator = schema.newValidator();
		validator.validate(domSource);

		return true;
	}

	public static boolean isWellFormed(File xmlFile)
			throws ParserConfigurationException, IOException, SAXException {

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		dBuilder.parse(xmlFile);

		return true;
	}
	
	public static boolean isWellFormed2(File file) throws IOException, SAXException {

		SAXParser saxParser = null;
		DefaultHandler dh = null;
		boolean isWellFormed = false;

		// init parser
		try {
			SAXParserFactory spFactory = SAXParserFactory.newInstance();
			spFactory.setNamespaceAware(true);
			saxParser = spFactory.newSAXParser();
			dh = new DefaultHandler();
		} catch (Exception e) {
			prglog.error("[PRG] Cannot initialize SAX parser.");
		}

		// parse the XML document using SAX parser
		try {
			saxParser.parse(file, dh); // SAXException, IOException
		} catch(SAXException e) {
			throw e;
			/*
			if(e.getMessage().indexOf("is an invalid XML character") > -1) {
				//
			} else {
				throw e;
			}
			*/
		}
		isWellFormed = true;
		return isWellFormed;
	}

	public static void validate2(String xml, String xsd) {

		// 1. Lookup a factory for the W3C XML Schema language
		SchemaFactory factory = SchemaFactory
				.newInstance("http://www.w3.org/2001/XMLSchema");

		// 2. Compile the schema.
		// Here the schema is loaded from a java.io.File, but you could use
		// a java.net.URL or a javax.xml.transform.Source instead.
		File schemaLocation = new File(xsd);
		Schema schema;
		try {
			schema = factory.newSchema(schemaLocation);
		} catch(SAXException ex) {
			prglog.error("[PRG] " + ex.getMessage());
			return;
		}

		// 3. Get a validator from the schema.
		Validator validator = schema.newValidator();

		// 4. Parse the document you want to check.
		Source source = new StreamSource(xml);

		// 5. Check the document
		try {
			validator.validate(source);
			prglog.error("[PRG] " + xml + " is valid.");
		} catch (SAXParseException ex) {
			prglog.error("[PRG] " + xml + " is not valid because " 
					+ ex.getLineNumber() + ":" + ex.getColumnNumber() 
					+ " " + ex.getMessage());
		} catch (SAXException ex) {
			prglog.error("[PRG] " + xml + " is not valid because " + ex.getMessage());
		} catch (IOException ex) {
			prglog.error("[PRG] " + xml + " is not valid because " + ex.getMessage());
		}
	}

	/**
	 * Create a simple XML tag. For example xmlTag("foo", "bar") creates
	 * &lt;foo&gt;bar&lt;/foo&gt;
	 * 
	 * @param tagName
	 *            The name of the tag
	 * @param content
	 *            The content of the tag
	 * @return The created XML tag
	 */
	public static String xmlTag(String tagName, String content) {
		return xmlTag(tagName, content, null);
	}

	/**
	 * Create a simple XML tag with attributes. For example 
	 * xmlTag("foo", "bar", new String[]{"foo", "bar}) creates 
	 * &lt;foo foo="bar"&gt;bar&lt;/foo&gt;
	 * @param tagName The name of the tag
	 * @param content The content of the tag
	 * @param attributes The attributes of the element
	 * @return The created XML tag
	 */
	public static String xmlTag(String tagName, String content, 
			String[] attributes) {
		StringBuffer sb = new StringBuffer();
		sb.append('<').append(tagName);
		if(null != attributes) {
			for(int i=0; i<attributes.length; i+=2){
				sb.append(' ').append(attributes[i]).append("=\"")
					.append(attributes[i+1]).append('"');
			}
		}
		if(null == content || 0 == content.length()){
			sb.append(" />");
		} else {
			sb.append('>');
			sb.append(content);
			sb.append("</").append(tagName).append('>');
		}
		return sb.toString();
	}
	
	public static Element xmlEl(String elName, String content) {
		return xmlEl(elName, content, null, null);
	}

	public static Element xmlEl(String elName, String content, Namespace ns) {
		return xmlEl(elName, content, null, ns);
	}

	public static Element xmlEl(String elName, String content, String[] attributes) {
		return xmlEl(elName, content, attributes, null);
	}

	public static Element xmlEl(String elName, String content, 
			String[] attributes, Namespace namespace) {
		Element el = new Element(elName);
		if(null != namespace) {
			el.addNamespaceDeclaration(namespace);
		}
		el.addContent(content);
		if(null != attributes) {
			for(int i=0; i<attributes.length; i+=2){
				el.setAttribute(attributes[i], attributes[i+1]);
			}
		}
		return el;
	}
}
