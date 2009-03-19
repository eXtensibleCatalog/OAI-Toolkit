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

import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import test.Constants;

import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import info.extensiblecatalog.OAIToolkit.utils.XsltTransformator;
import junit.framework.TestCase;

public class XsltTransformatorTestCase extends TestCase {
	
	public void test() throws TransformerConfigurationException, 
				FileNotFoundException, IOException, TransformerException {

		File xmlSource = new File(Constants.XSL_DIR, "metadataFormats.xml");
		File xsltFile  = new File(Constants.XSL_DIR, "meta2oai.xsl"); //

		XsltTransformator transformator = new XsltTransformator(xsltFile);
		String content = TextUtil.readFileAsString(xmlSource.getAbsolutePath());
		content = transformator.transform(content);
		
		assertTrue(content.indexOf("<metadataPrefix>html" +
				"</metadataPrefix>") > -1);
		assertTrue(content.indexOf("<schema>http://www.loc.gov/standards" +
				"/mods/v3/mods-3-0.xsd</schema>") > -1);
		assertTrue(content.indexOf("<metadataNamespace>http://www.loc.gov" +
				"/MARC21/slim</metadataNamespace>") > -1);
	}
}
