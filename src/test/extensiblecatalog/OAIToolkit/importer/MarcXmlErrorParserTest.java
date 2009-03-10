/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.importer.MarcXmlErrorParser;

import junit.framework.TestCase;

public class MarcXmlErrorParserTest extends TestCase {

	public void testPattern() {
		String message = "cvc-pattern-valid: Value ' ' is not facet-valid with respect to pattern" +
				" '[\\da-z!\"\\#$%&'()*+,-./:;<=>?{}_^`~\\[\\]\\]{1}' for type 'subfieldcodeDataType'.";
		System.out.println(MarcXmlErrorParser.parseErrorMessage(message));
	}
}
