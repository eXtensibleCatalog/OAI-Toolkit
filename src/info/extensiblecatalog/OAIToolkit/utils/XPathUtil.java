/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XPathUtil {
	
	private XPath xPath;
    private Document dom;
	
	public XPathUtil(String content) {
		XPathFactory factory = XPathFactory.newInstance();
		xPath = factory.newXPath();

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(false);
			DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
			dom = documentBuilder.parse(
					new InputSource(
							new StringReader(content)));
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public NodeList evaluate(String xpath) {
		// get nodes with xpath
		NodeList results = null;
		try {
			results = (NodeList) xPath.evaluate(xpath, dom,
					XPathConstants.NODESET);
		} catch(XPathExpressionException e) {
			System.out.println(xpath + " " + e.getCause());
		}
		return results;
	}
}
