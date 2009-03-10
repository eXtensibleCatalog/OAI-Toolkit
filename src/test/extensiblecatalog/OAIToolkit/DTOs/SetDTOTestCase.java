/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.DTOs;

import info.extensiblecatalog.OAIToolkit.DTOs.SetDTO;
import junit.framework.TestCase;

public class SetDTOTestCase extends TestCase {
	
	private Integer setId   = 1;
	private String  setName = "bib";
	private String  setTag  = "bib";
	private String  setSpec = "bib";
	private String  setDescription = "Biblographic record";

	public void testGetSetId() {
		SetDTO set = new SetDTO();
		set.setSetId(setId);
		assertEquals(setId, set.getSetId());
	}

	public void testGetSetName() {
		SetDTO set = new SetDTO();
		set.setSetName(setName);
		assertEquals(setName, set.getSetName());
	}

	public void testGetSetTag() {
		SetDTO set = new SetDTO();
		set.setSetTag(setTag);
		assertEquals(setTag, set.getSetTag());
	}

	public void testGetSetSpec() {
		SetDTO set = new SetDTO();
		set.setSetSpec(setSpec);
		assertEquals(setSpec, set.getSetSpec());
	}

	public void testGetSetDescription() {
		SetDTO set = new SetDTO();
		set.setSetDescription(setDescription);
		assertEquals(setDescription, set.getSetDescription());
	}

	public void testToString() {
		SetDTO set = new SetDTO();
		set.setSetId(setId);
		assertEquals("setId: " + setId + ", name: null, tag: null, " +
				"spec: null, description: null", set.toString());
		
		set.setSetName(setName);
		assertEquals("setId: " + setId + ", name: " + setName 
				+ ", tag: null, spec: null, " +
				"description: null", set.toString());

		set.setSetDescription(setDescription);
		assertEquals("setId: " + setId + ", name: " + setName 
				+ ", tag: null, spec: null, " +
				"description: " + setDescription, set.toString());

		set.setSetTag(setTag);
		assertEquals("setId: " + setId + ", name: " + setName 
				+ ", tag: " + setTag + ", spec: null, "
				+ "description: " + setDescription, set.toString());

		set.setSetSpec(setSpec);
		assertEquals("setId: " + setId 
				+ ", name: " + setName + ", tag: " + setTag 
				+ ", spec: " + setSpec + ", " 
				+ "description: " + setDescription, set.toString());
	}
}
