/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlReader;
import org.marc4j.marc.Record;

import info.extensiblecatalog.OAIToolkit.importer.MARCFileNameFilter;

import junit.framework.TestCase;

/**
 * Test class for counting marc records
 * @author Peter Kiraly
 */
public class MarcCounterTestCase extends TestCase {
	
	/** directory of marc files */
	String marcDir = "e:/doku/extensiblecatalog/marc";
	String xmlFile = "c:/doku/extensiblecatalog/OAI/xml/marc01.xml"; 
	InputStream in;
	MarcReader marcReader;
	private static final String HEX = "0123456789ABCDEF";

	public void test() throws FileNotFoundException, IOException {
		
		File fSourceDir = new File(marcDir);
		
		File[] files = fSourceDir.listFiles(new MARCFileNameFilter());
		
		int total = 0;
		for(File file : files){
			
			long len = file.length();
			System.out.println(file.length());
			

			openReader(file);
			int counter = 0;

			long t1 = System.currentTimeMillis();
			boolean isFirst = true;
			Record rec;
			while (marcReader.hasNext()) {
				if(isFirst) {
					rec = marcReader.next();
					System.out.println(rec.getControlNumber());
					isFirst = false;
				} else {
					marcReader.next();
				}
				System.out.println((len - in.available()) * 100 / len);
				counter++;
			}
			long t2 = System.currentTimeMillis();
			total += counter;
			System.out.println(file.getName() 
					+ " counter: " + counter + "/" + total 
					+ " (" + (t2 - t1) + ") " 
					+ (float)((float)counter/(float)(t2 - t1)));

			closeReader();
		}
	}
	
	public void testCahr() throws UnsupportedEncodingException {
		char[] invalidChars = {'\u0001', '\u0002', '\u0006', '\u0011', '\u001F', 'a', 'b', 'c', 
				'\u00e1'}; // á
		System.out.println(Character.getNumericValue(invalidChars[0]) + ", "
				+ Character.getNumericValue(invalidChars[1]));
		for(char a : invalidChars) {
			byte[] bytes = String.valueOf(a).getBytes("UTF-8");
			System.out.println(bytes.length);
			StringBuffer sb = new StringBuffer();
			sb.append("'\\u");
			if(1 == bytes.length){
				sb.append("00");
			}
			String hex;
			for (int j = 0; j < bytes.length; j++) {
				hex = Integer.toHexString(bytes[j] & 0xff).toUpperCase();
				System.out.println(Integer.toHexString(bytes[j]) + " " + hex);
				if(hex.length() == 1) {
					sb.append('0');
				}
				sb.append(hex);
			}
			sb.append('\'');
			

			Character c = new Character(a);
			System.out.print("a: " + a + " " + sb);
			System.out.print(", forDigit: " + Character.forDigit(a, 10));
			System.out.print(", digit: " + Character.digit(a, 8));
			System.out.print(", getNumericValue: " + Character.getNumericValue(a));
			System.out.print(", getType: " + Character.getType(a));
			System.out.print(", isWhitespace: " + Character.isWhitespace(a));
			System.out.print(", hash: " + c.hashCode());
			System.out.print(", int: " + (int)a);
			//System.out.print(", d: " + Character.codePointAt(a, 2));
			System.out.println();
		}

	}

	private void openReader(File xmlFile) throws FileNotFoundException {
		in = new FileInputStream(xmlFile);
		marcReader = new MarcStreamReader(in);
	}

	private void openReader() throws FileNotFoundException {
		in = new FileInputStream(xmlFile);
		marcReader = new MarcXmlReader(in);
	}

	private void closeReader() throws IOException {
		in.close();
	}
	
	public static String encode(String s, String encoding)
			throws UnsupportedEncodingException {
		int length = s.length();
		int start = 0;
		int i = 0;

		char c;             // current character
		String unsafePart;  // unsafe part of the string
		byte[] unsafeBytes; // byte representation of the unsafe part
		StringBuffer result = new StringBuffer(length);
		int intVal;
		while (true) {
			while (i < length && isSafe(s.charAt(i))) {
				i++;
			}

			// Safe character can just be added
			result.append(s.substring(start, i));

			// Are we done?
			if (i >= length) {
				return result.toString();
			} else if (s.charAt(i) == ' ') {
				result.append('+'); // Replace space char with plus symbol.
				i++;
			} else {
				// Get all unsafe characters
				start = i;
				
				while (i < length && (c = s.charAt(i)) != ' ' && !isSafe(c)) {
					i++;
				}

				// Convert them to %XY encoded strings
				unsafePart = s.substring(start, i);
				unsafeBytes = unsafePart.getBytes(encoding);
				for (int j = 0; j < unsafeBytes.length; j++) {
					result.append('%');
					intVal = unsafeBytes[j];
					result.append(HEX.charAt((intVal & 0xf0) >> 4));
					result.append(HEX.charAt(intVal & 0x0f));
				}
			}
			start = i;
		}
	}
	
	private static boolean isSafe(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
				|| (c >= '0' && c <= '9') || c == '-' || c == '_' 
				|| c == '.' || c == '*';
	}



}
