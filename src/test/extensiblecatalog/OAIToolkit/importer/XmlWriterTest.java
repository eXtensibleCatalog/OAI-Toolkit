/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.utils.XMLUtil;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;

import org.jdom.Element;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

import junit.framework.TestCase;

public class XmlWriterTest extends TestCase {

	private XMLReader saxParser;
	
	public void test() throws UnsupportedEncodingException, TransformerConfigurationException,
			SAXException, IOException, ParserConfigurationException {
		ByteArrayOutputStream stringOut = new ByteArrayOutputStream();
		Writer writer = new OutputStreamWriter(stringOut, "UTF-8");

		writer = new BufferedWriter(writer);
		Result result = new StreamResult(writer);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        if (!transformerFactory.getFeature(SAXTransformerFactory.FEATURE)) {
            throw new UnsupportedOperationException(
                    "SAXTransformerFactory is not supported");
        }
        SAXTransformerFactory saxFactory = (SAXTransformerFactory) transformerFactory;
        //saxFactory.setFeature("http://xml.org/sax/features/xml-1.1", true);
        TransformerHandler handler = saxFactory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
        handler.getTransformer().setOutputProperty(OutputKeys.VERSION, "1.1");
        handler.setResult(result);
        handler.startDocument();
        handler.startElement(null, "y", "y", null);
        char[] temp = "egy\f".toCharArray();
        handler.characters(temp, 0, temp.length);

        handler.endElement(null, "y", "y");
        handler.endDocument();
        System.out.println("3: " + stringOut.toString());

		if(validate(stringOut.toString())) {
			System.out.println("valid");
		} else {
			System.out.println("invalid");
		}

		/*
        SchemaFactory schemaFactory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
		Schema schema = schemaFactory.newSchema();
		Validator validator = schema.newValidator();
		Source source = new StreamSource(
				new InputStreamReader(
						new ByteArrayInputStream(
								stringOut.toByteArray()),"UTF-8"));
		validator.validate(source);
        
        String out = stringOut.toString().replace("&#", "&#x");
		for(int i=0; i<30; i++) {
			out = "<?xml version='1.1' encoding='UTF-8'?><y>egy " + Integer.toHexString(i) + " </y>";
			if(validate(out)) {
				System.out.println("88: " + Integer.toHexString(i));
			}
		}
		*/
	}
	
	public void xtest2() {
		Element root = new Element("y");
		root.addContent("egy&#x12;");
		System.out.println(XMLUtil.format.outputString(root));
	}
	
	public void xtest3() {
		try {
			Builder parser = new Builder();
			Document doc = parser.build("<greeting>Hello <![CDATA[" + String.valueOf('\u0001') 
					+ "]]> World!</greeting>", "http://www.example.org/");
			System.out.println(doc.toXML());
			//Document doc = parser.build("http://www.cafeconleche.org/");
		} catch (ParsingException ex) {
			System.err.println("Cafe con Leche is malformed today. How embarrassing!");
		} catch (IOException ex) {
			System.err.println("Could not connect to Cafe con Leche. The site may be down.");
		}
	}
	
	private boolean validate(String input) throws SAXException, ParserConfigurationException, 
			UnsupportedEncodingException, IOException {
		
		if(saxParser == null) {
			SAXParserFactory spFactory = SAXParserFactory.newInstance();
			spFactory.setNamespaceAware(true);
			saxParser = spFactory.newSAXParser().getXMLReader();
			System.out.println(saxParser.getFeature("http://xml.org/sax/features/xml-1.1"));
		}

		InputSource is = new InputSource(new InputStreamReader(new ByteArrayInputStream(
				input.getBytes("UTF-8")),"UTF-8"));
		try {
			saxParser.parse(is);
			return true;
		}catch(SAXParseException e) {
			System.out.println("131: " + e.getLineNumber() + ":" + e.getColumnNumber()
					+ " " + e.getMessage());
			StringBuffer sb = new StringBuffer();
			sb.append(input + "\n");
			for(int i=0; i<e.getColumnNumber()-2; i++) {
				sb.append("-");
			}
			sb.append("^");
			System.out.println(sb.toString());
		}
		return false;
	}
}

class ForgivingErrorHandler implements ErrorHandler {

    public void warning(SAXParseException ex) {
        System.err.println(ex.getMessage());
    }

    public void error(SAXParseException ex) {
        System.err.println(ex.getMessage());
    }

    public void fatalError(SAXParseException ex) throws SAXException {
        throw ex;
    }

}
