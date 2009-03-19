/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser of the error messages created in the course of MARCXML valiation
 * @author Király Péter pkiraly@tesuji.eu
 */
public class MarcXmlErrorParser {

	/** Invalid values */
	protected static final Pattern MARCXML_PATTERNERROR_PATTERN = Pattern
			.compile("Value ('[^']+') is not facet-valid with respect " +
					"to pattern");
	
	/** The element */
	protected static final Pattern MARCXML_PATTERNERROR_FORTYPE = Pattern
			.compile("for type '([^']+)'");
	
	/** Invalid XML character */
	protected static final Pattern MARCXML_CHARACTERERROR_INVALID = Pattern
			.compile("Character reference \"(.+?)\" is an invalid XML " +
					"character.");

	/** Incomplete content */
	protected static final Pattern MARCXML_MISSINGERROR_MISSING = Pattern
			.compile("The content of element '(.*?)' is not complete. " +
					"One of '(.*?)' is expected\\.");
	
	/** Expected element */
	protected static final Pattern MARCXML_MISSINGERROR_EXPECTED = Pattern
			.compile("\\{\"http://www\\.loc\\.gov/MARC21/slim\":(.*?)\\}");

	
	/** 
	 * The possible MARC data types and its XPath addresses
	 */
	protected static final Map<String, String[]> MARCXML_DATATYPES 
											= new HashMap<String, String[]>();
	static {
		// collection
		MARCXML_DATATYPES.put("collectionType", 
				new String[] { 
					"MARC file", 
					"collection", 
					"" });
		// content of control field
		MARCXML_DATATYPES.put("controlDataType", 
				new String[] { 
					"content of control field", 
					"controlfield/text()",
					"record/controlfield/text()" });
		// control field element
		MARCXML_DATATYPES.put("controlFieldType", 
				new String[] {
					"control field (00x field)", 
					"controlfield",
					"record/controlfield" });
		// content of control field's tag attribute
		MARCXML_DATATYPES.put("controltagDataType", 
				new String[] {
					"tag of controlfield", 
					"controlfield/@tag",
					"record/controlfield/@tag" });
		// datafield
		MARCXML_DATATYPES.put("dataFieldType", 
				new String[] {
						"data field (1xx-9xx field)", 
						"datafield",
						"record/datafield" });
		// control number
		MARCXML_DATATYPES.put("idDataType", 
				new String[] { 
						"control number",
						"record/@id", 
						"record/@id" });
		// MARCXML_DATATYPES.put("idDataType", new String[]{"id of
		// controlfield", "controlfield/@id", "record/controlfield/@id"});
		// MARCXML_DATATYPES.put("idDataType", new String[]{"id of data field",
		// "datafield/@id", "record/datafield/@id"});
		// MARCXML_DATATYPES.put("idDataType", new String[]{"id of record
		// leader", "leader/@id", "record/leader/@id"});
		// MARCXML_DATATYPES.put("idDataType", new String[]{"id of subfield",
		// "subfield/@id", "record/datafield/subfield/@id"});
		
		// indicator
		MARCXML_DATATYPES.put("indicatorDataType", new String[] {
				"indicator of data field", "datafield/[@ind1 or @ind2]",
				"record/datafield/[@ind1 or @ind2]" });
		// MARCXML_DATATYPES.put("indicatorDataType", new String[]{"2nd
		// indicator of data field", "datafield/@ind2",
		// "record/datafield/@ind2"});
		
		// content of leader
		MARCXML_DATATYPES.put("leaderDataType", 
				new String[] {
						"content of record leader", 
						"leader/text()",
						"record/leader/text()" });
		// leader
		MARCXML_DATATYPES.put("leaderFieldType", 
				new String[] {
						"record leader", 
						"leader", 
						"record/leader" });
		// record
		MARCXML_DATATYPES.put("recordType", 
				new String[] { 
						"record", 
						"record", 
						"record" });
		// type of record
		MARCXML_DATATYPES.put("recordTypeType", 
				new String[] {
						"type of record", 
						"record/@type", 
						"record/@type" });
		// content of subfield
		MARCXML_DATATYPES.put("subfieldDataType", 
				new String[] {
						"content of subfield", 
						"subfield/text()",
						"record/datafield/subfield/text()" });
		// subfield
		MARCXML_DATATYPES.put("subfieldatafieldType", 
				new String[] {
						"subfield", 
						"subfield", 
						"record/datafield/subfield" });
		// code of subfield
		MARCXML_DATATYPES.put("subfieldcodeDataType", 
				new String[] {
						"subfield code", 
						"subfield/@code",
						"record/datafield/subfield/@code" });
		// tag of data field
		MARCXML_DATATYPES.put("tagDataType",
				new String[] { 
						"tag of data field", 
						"datafield/@tag",
						"record/datafield/@tag" });
	}

	/**
	 * Parse the raw error message come from the XML validator, and creates 
	 * a human-readable version.
	 * @param message The raw error message
	 * @return The human-readable version of the error message
	 */
	public static String parseErrorMessage(String message) {

		// invalid value of an element?
		Matcher m = MARCXML_PATTERNERROR_PATTERN.matcher(message);
		if(m.find()) {
			String value = m.group(1);
			// get the element's name
			m = MARCXML_PATTERNERROR_FORTYPE.matcher(message);
			if(m.find()) {
				String dataType = m.group(1);
				// translate the element's name
				if(MARCXML_DATATYPES.containsKey(dataType)) {
					dataType = MARCXML_DATATYPES.get(dataType)[0];
				}
				message = dataType + " has non-valid value: " + value;
			}
		} else {
			// invalid character?
			m = MARCXML_CHARACTERERROR_INVALID.matcher(message);
			if(m.find()) {
				message = " has invalid XML character: " + m.group(1);
			} else {
				// missing element?
				m = MARCXML_MISSINGERROR_MISSING.matcher(message);
				if(m.find()) {
					String element = m.group(1);
					String content = m.group(2);
					m = MARCXML_MISSINGERROR_EXPECTED.matcher(content);
					if(m.find()) {
						content = m.group(1);
					}
					message = element + " without " + content;
				}
			}
		}
		return message;
	}
}
