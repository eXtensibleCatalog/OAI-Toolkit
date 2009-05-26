/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Date;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.MarcXmlWriter;
import org.marc4j.converter.CharConverter;
import org.marc4j.converter.impl.AnselToUnicode;

public class MarcImporter {
	
	private static String MARC_FILE;
	private static CharConverter charconv = new AnselToUnicode();
	
	MarcImporter(String marc_file){
		MarcImporter.MARC_FILE = marc_file;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException, 
				IOException {
		String dir = "c:/doku/extensiblecatalog/OAIToolkit-0.2alpha/marc/";
		MARC_FILE = dir + "Authority01.mrc";

		InputStream input = null;
		System.out.println("Making sure the marc file exists...");
		try {
			input = new FileInputStream(MARC_FILE);
		} catch (FileNotFoundException e) {
			System.err.println("Marc file does not exist. Please provide the filename");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Found " + MARC_FILE);
		
		Date start = new Date();
		
		MarcReader reader = new MarcStreamReader(input);
		OutputStream out = new FileOutputStream(MARC_FILE + ".xml");
		MarcXmlWriter writer = new MarcXmlWriter(out, true);
		writer.setConverter(charconv);
		writer.setUnicodeNormalization(true);
		int totalRecords = 0;
		while(reader.hasNext()){
			writer.write(reader.next());
			totalRecords++;
		}
		writer.close();
		input.close();
		
		Date end = new Date();
		long totalTime = end.getTime() - start.getTime();
		System.out.println("Finished in " + calcTime(totalTime) );
	}
	
	public static String calcTime(final long totalTime){
		DecimalFormat timeFormat = new DecimalFormat("00.00");
		final long minutes = totalTime / 60000;
		final long seconds = ( totalTime % 60000 ) / 1000;
		return minutes + ":" + timeFormat.format(seconds);
	}

}
