/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.DTOs;

import info.extensiblecatalog.OAIToolkit.DTOs.XmlDTO;
import junit.framework.TestCase;

public class XmlDTOTestCase extends TestCase {
	private Integer id = 1;
	private String xml = "<one/>";

	public void testBigxmlDTO() {
		XmlDTO big = new XmlDTO();
		big.setRecordId(id);
		big.setXml(xml);
		assertEquals(big.getRecordId(), id);
		assertEquals(big.getXml(), xml);
	}

	public void testBigxmlDTOInteger() {
		XmlDTO big = new XmlDTO(id);
		big.setXml(xml);
		assertEquals(big.getRecordId(), id);
		assertEquals(big.getXml(), xml);
	}

	public void testBigxmlDTOString() {
		XmlDTO big = new XmlDTO(xml);
		big.setRecordId(id);
		assertEquals(big.getRecordId(), id);
		assertEquals(big.getXml(), xml);
	}

	public void testBigxmlDTOIntegerString() {
		XmlDTO big = new XmlDTO(id, xml);
		assertEquals(big.getRecordId(), id);
		assertEquals(big.getXml(), xml);
	}

	public void testToString() {
		XmlDTO big = new XmlDTO(id, xml);
		assertEquals(big.toString(), "recordId: 1, xml: <one/>");
	}
}
