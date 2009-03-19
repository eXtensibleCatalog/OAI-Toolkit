/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.EditorKit;
import javax.swing.text.ElementIterator;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import info.extensiblecatalog.OAIToolkit.utils.XMLUtil;
import info.extensiblecatalog.OAIToolkit.utils.XMLValidator;
import junit.framework.TestCase;

public class XMLValidatorTestCase extends TestCase {
	private static final String DIR = "c:/doku/extensiblecatalog/OAI/"; 
	private final File[] xmlFiles = {
			new File(DIR, "error_xml/large-2_40000.xml"),
			new File(DIR, "error_xml/large_sample_bibs_marc8.xml"),
			new File(DIR, "xml/large-2.xml")
	};
	//private String schemaUrl = "http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd";
	private static final String schemaFileName = DIR + "/MARC21slim_rochester.xsd";

	public void xtest1() throws ParserConfigurationException, IOException, 
			SAXException {
		for(File xmlFile : xmlFiles) {
			System.out.println(xmlFile);
			XMLUtil.isValid(xmlFile, schemaFileName);
		}
	}
	
	public void testShrey() {
		XMLValidator validator = new XMLValidator();
        try {
			// oaipmh = (Node) root;
			// oaiXml3 = serialize1(oaipmh);
			// System.out.println("\n The XML in the record using serialize1 is"
			// + oaiXml3);

			validator.validate("<file/>");
		} catch (SAXParseException e) {
			System.out.println(e);// do error report
		} catch (FileNotFoundException s) {
			System.out.println(s);
		} catch (IOException io) {
			System.out.println(io);
		//} catch (ParserConfigurationException pce) {
			//	System.out.println(pce);
		} catch (SAXException sx) {
			System.out.println(sx);
		} catch (Exception ce) {
			System.out.println(ce);
		}
	}

	public void xtest2() throws ParserConfigurationException, IOException, 
			SAXException {
		for(File xmlFile : xmlFiles) {
			System.out.println(xmlFile);
			boolean isWellFormed = false;
			try {
				XMLUtil.isWellFormed2(xmlFile);
				isWellFormed = true;
			} catch(Exception e) {
				System.out.println(e);
			}
			if(isWellFormed) {
				XMLUtil.validate2(xmlFile.getAbsolutePath(), schemaFileName);
			}
		}
	}

	public void xtest3() throws ParserConfigurationException, IOException,
			SAXException {
		System.out.println("test3");

		XMLValidator validator = new XMLValidator(schemaFileName);
		for (File xmlFile : xmlFiles) {
			System.out.println(xmlFile);
			boolean isWellFormed = false;
			try {
				isWellFormed = XMLUtil.isWellFormed2(xmlFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (isWellFormed) {
				try {
					validator.validate(xmlFile);
				} catch(Exception ex) {
					String error = "The XML file (" + xmlFile.getName() + ") "
						+ "isn't well formed. Please correct the errors and "
						+ "load again. Error description: " + ex.getMessage();
					if(ex instanceof SAXParseException) {
						error += " Location: " 
							+ ((SAXParseException)ex).getLineNumber()
							+ ":" + ((SAXParseException)ex).getColumnNumber() + ".";
					}
					System.out.println(error);
				}
			}
		}
	}
	
	public void testValidator() throws IOException {
		XMLValidator validator = new XMLValidator();
		// "http://128.151.244.134:8090/ILSToolkit/oai-request.do?verb=ListMetadataFormats"
		try {
			validator.validate(new File(DIR, "ListMetadataFormats.xml"));
		} catch(SAXParseException e){
			System.out.println(e);
		} catch(SAXException e){
			System.out.println(e);
		}
	}
	
	public void testKit() throws MalformedURLException, IOException, 
			BadLocationException {
		Set<String> uriList = new TreeSet<String>();
		HttpURLConnection.setFollowRedirects(false);
		EditorKit kit = new HTMLEditorKit();
		Document doc = kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);

		String uri = "http://index.hu";
		Reader reader = null;

		if (uri != null && uri.startsWith("http")) {
			URLConnection conn = new URL(uri).openConnection();
			reader = new InputStreamReader(conn.getInputStream());
		} else {
			System.err.println("Usage: java ListUrls http://example.com/startingpage");
			return;
		}

		kit.read(reader, doc, 0);
		ElementIterator it = new ElementIterator(doc);
		AttributeSet s;
		String href;
		javax.swing.text.Element elem = it.next();
		while (elem != null) {
			s = (AttributeSet) elem.getAttributes().getAttribute(HTML.Tag.A);
			if (s != null) {
				href = (String) s.getAttribute(HTML.Attribute.HREF);
				if(href != null) {
					uriList.add(href);
				}
			}
			elem = it.next();
		}
		for (String element: uriList) {
			//System.out.printf(">>%s<<%n", element);
		}
	}
}
