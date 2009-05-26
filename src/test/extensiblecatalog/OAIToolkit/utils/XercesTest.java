/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.utils;

import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import test.Constants;

import junit.framework.TestCase;

public class XercesTest extends TestCase {
	
	private String xmlFileName = Constants.TEST_DIR  + "/sample_records/" +
			"xc_sample_recs_bibs_w_mfhds.xml";

	public void testValidateWithLOCSchema() throws IOException,
			ParserConfigurationException, SAXException {
		String xsdFileName = "http://www.loc.gov/standards/marcxml/schema/" +
				"MARC21slim.xsd";
		DocumentBuilder dBuilder = getDocumentBuilder();
		DOMSource domSource = getDomSource(dBuilder);
		Validator validator = getValidator(dBuilder, xsdFileName);
		boolean isValid = false;
		try {
			validator.validate(domSource);
			isValid = true;
		} catch(SAXParseException e) {
			isValid = false;
		}
		assertFalse("not valid with original schema", isValid);
	}

	public void testValidateWithCustomSchema() throws IOException,
			ParserConfigurationException, SAXException {
		String xsdFileName = Constants.TEST_DIR + "/schema/" +
				"MARC21slim_rochester.xsd";
		DocumentBuilder dBuilder = getDocumentBuilder();
		DOMSource domSource = getDomSource(dBuilder);
		Validator validator = getValidator(dBuilder, xsdFileName);
		boolean isValid = false;
		try {
			validator.validate(domSource);
			isValid = true;
		} catch (SAXParseException e) {
			isValid = false;
		}
		assertTrue("not valid with original schema", isValid);
	}

	private DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
		System.setProperty(
				"javax.xml.parsers.DocumentBuilderFactory",
				"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
		System.setProperty(
				"javax.xml.parsers.SaxParserFactory",
				"org.apache.xerces.jaxp.SaxParserFactoryImpl");
		System.setProperty(
				"javax.xml.validation.SchemaFactory:http://www.w3.org/2001/XMLSchema",
				"org.apache.xerces.jaxp.validation.XMLSchemaFactory");

		DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();
		dFactory.setNamespaceAware(true);
		DocumentBuilder dBuilder = dFactory.newDocumentBuilder();
		return dBuilder;
	}
	
	private DOMSource getDomSource(DocumentBuilder dBuilder) throws SAXException, 
			IOException {
		Document document = dBuilder.parse(xmlFileName);
		DOMSource domSource = new DOMSource(document);
		return domSource;
	}
	
	private Validator getValidator(DocumentBuilder dBuilder, 
			String xsdFileName) throws SAXException, IOException {
		Document xsdDocument = dBuilder.parse(xsdFileName);
		DOMSource xsdDomSource = new DOMSource(xsdDocument);
		SchemaFactory schemaFactory = SchemaFactory.newInstance(
				XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(xsdDomSource);
		Validator validator = schema.newValidator();
		return validator;
	}




}
