/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.DTOs;

import java.text.ParseException;

import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import junit.framework.TestCase;

public class RecordDTOTestCase extends TestCase {
	
	public void testEqual() throws ParseException {
		
		RecordDTO data = new RecordDTO();
		data.setExternalId("2095130");
		//data.setCreationDate: null, 
		data.setModificationDate(TextUtil.stringToTimestamp("20040203135953.0"));
		data.setIsDeleted(false); 

		RecordDTO storedData = new RecordDTO();
		storedData.setExternalId("2095130");
		storedData.setIsDeleted(false); 
		
		assertFalse(data.equals(storedData));
		assertFalse(data.equalData(storedData));
	}
}
