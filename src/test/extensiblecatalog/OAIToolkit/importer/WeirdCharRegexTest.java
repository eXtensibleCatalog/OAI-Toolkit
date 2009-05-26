/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import junit.framework.TestCase;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WeirdCharRegexTest extends TestCase {

	public void test() {
		String regex = "[\u0001-\u0008\u000b-\u000c\u000e-\u001f]";
		Pattern p = Pattern.compile(regex);
		String[] inputs = {"\t", 
				"alal" + String.valueOf('\u0001'),
				"alal" + String.valueOf('\u0002'),
				"alal" + String.valueOf('\u0003'),
				"alal" + String.valueOf('\u0004'),
				"alal" + String.valueOf('\u0005') + "alal" + String.valueOf('\u0004'),
				"lakat",
				};
		Matcher m;
		for(String input : inputs) {
			m = p.matcher(input);
			System.out.println("'" + input + "': " + m.find());
			if(p.matcher(input).find()) {
				int s = m.start();
				int e = m.end();
				String c = input.substring(s, s+1);
				char invalidChar = c.charAt(0);
				System.out.println(input.substring(s, e) + " " + invalidChar + " " 
						+ Integer.toHexString(invalidChar));
				input = input.replaceAll(regex, " ");
				System.out.println("'" + input + "'");

			}
		}
	}
}
