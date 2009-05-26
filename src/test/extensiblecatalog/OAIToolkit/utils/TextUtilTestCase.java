/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.utils;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import info.extensiblecatalog.OAIToolkit.utils.TextUtil;
import junit.framework.TestCase;

public class TextUtilTestCase extends TestCase {

	private static final String NonCamelCase = "non_camel";
	private static final String CamelCase = "nonCamel";
	private Date date;

	public void setUp() {
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse("2001-04-04");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test for toCamelCase
	 */
	public void testToCamelCase() {
		assertEquals("toCamelCase", CamelCase, 
				TextUtil.toCamelCase(NonCamelCase));
	}

	/**
	 * Test for fromCamelCase
	 */
	public void testFromCamelCase() {
		assertEquals("fromCamelCase", NonCamelCase, 
				TextUtil.fromCamelCase(CamelCase));
	}
	
	public void testConditionals() {
		String aString = null;
		String bString = null;
		String cString = "";
		assertNull(aString);
		assertNull(bString);
		assertNotNull(cString);
		
		/*
		if(a == null && b != null){
			System.out.println("b not null");
		}
		if(a != null && b == null){
			System.out.println("a not null");
		}
		if(a == null && c != null){
			System.out.println("c not null");
		}
		*/
	}
	
	/**
	 * Test for StringToTimestamp
	 * @throws ParseException
	 */
	public void testStringToTimestamp() throws ParseException {
		Timestamp origTimestamp = new Timestamp(date.getTime());
		String timestampString = TextUtil.timestampToString(origTimestamp);
		Timestamp newTimestamp = TextUtil.stringToTimestamp(timestampString);
		assertEquals(newTimestamp, origTimestamp);
	}
	
	/**
	 * Test for TimestampToString
	 * @throws ParseException
	 */
	public void testTimestampToString() throws ParseException {
		Timestamp timestamp = new Timestamp(date.getTime());
		assertEquals("zero hour", "20010404000000.0", 
				TextUtil.timestampToString(timestamp));
	}

	/**
	 * Test for TimestampToUTC
	 * @throws ParseException
	 */
	public void testTimestampToUTC() throws ParseException {
		Timestamp timestamp = new Timestamp(date.getTime());
		assertEquals("2001-04-04T00:00:00Z", 
				TextUtil.timestampToUTC(timestamp));
	}

	/**
	 * Test for TimestampToUTC
	 * @throws ParseException
	 */
	public void testUTCToTimestamp() throws ParseException {
		String utc = "2001-04-04T00:00:00+0200";
		assertEquals("2001-04-04 00:00:00.0", 
				TextUtil.utcToTimestamp(utc).toString());
	}
	
	public void testMalformedDatPattern() {
		Pattern malformedDatePattern = Pattern.compile("^\\d{14}\\.\\d $");
		assertTrue(malformedDatePattern.matcher("20010404000000.0 ").matches());

	}
	
	public void testSize() throws UnsupportedEncodingException {
		String text = "árvíztűrő tükörfúrógép";
		System.out.println(text.length());
		System.out.println(text.getBytes("UTF-8").length);
		byte[] bytes = text.getBytes("UTF-8");
		for(byte b : bytes) System.out.print(b + " ");
		assertNotSame(text.length(), text.getBytes("UTF-8").length);
	}
}
