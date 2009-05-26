/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;

import org.marc4j.MarcXmlHandler;
import org.marc4j.RecordStack;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import junit.framework.TestCase;

public class Xml11ReaderTest extends TestCase {
	public void testReader() throws Exception {
		File xmlFile = new File(
				"test/sample_records/xml11/error_records_in_marc01.xml");
		InputSource input = new InputSource(new FileInputStream(xmlFile));
		
		System.out.println("start");
		//RecordStack recordStack       = new RecordStack();
		//MarcXmlHandler marcXmlHandler = new MarcXmlHandler(recordStack);

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		XMLReader reader = null;
		try {
			reader = saxParserFactory.newSAXParser().getXMLReader();
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setFeature("http://xml.org/sax/features/namespace-prefixes",
					true);
			reader.setContentHandler(new MarcXmlHandler(new RecordStack()));
			reader.parse(input);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
}

class MarcHandler implements ContentHandler {

	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// System.out.println(new String(ch, start, length));
		// TODO Auto-generated method stub
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("end");
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void processingInstruction(String target, String data)
			throws SAXException {
		// TODO Auto-generated method stub

	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub

	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("start");
	}

	public void startElement(String uri, String localName, String name,
			Attributes atts) throws SAXException {
		System.out.println(localName);
		// TODO Auto-generated method stub

	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
		// TODO Auto-generated method stub

	}
}
