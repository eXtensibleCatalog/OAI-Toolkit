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

import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import info.extensiblecatalog.OAIToolkit.utils.XPathUtil;

import org.w3c.dom.NodeList;

import test.Constants;

import junit.framework.TestCase;

public class XpathUtilTestCase extends TestCase {

	public void test() throws IOException {
		
		String content = TextUtil.readFileAsString(Constants.XSL_DIR 
				+ "/" + "metadataFormats.xml");

		XPathUtil xpathDoc = new XPathUtil(content);
		NodeList result;
		
		result = xpathDoc.evaluate("//metadata-prefix");
		assertEquals("must have 5 metadata-prefix", 5, result.getLength());

		result = xpathDoc.evaluate("//metadata-prefix");
		String[] expectations = {"oai_dc", "oai_marc", "marc21", "mods", "html"};
		
		for(int i=0; i<result.getLength(); i++) {
			assertEquals(i + " must be " + expectations[i], 
					expectations[i], result.item(i).getTextContent());
		}
	}
}
