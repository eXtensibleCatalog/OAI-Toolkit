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
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.marc4j.ErrorHandler;
import org.marc4j.MarcException;
import org.marc4j.MarcPermissiveStreamReader;
import org.marc4j.MarcReader;
import org.marc4j.MarcStreamWriter;
import org.marc4j.MarcWriter;
import org.marc4j.marc.Record;

import junit.framework.TestCase;

public class Marc4j24Test extends TestCase {

	private static final String FILE = "E:/doku/extensiblecatalog/marc/ub-test.mrc";

	public void testSimpleImport() throws Exception {

		InputStream input = new FileInputStream(FILE);
		// InputStream input = new
		// FileInputStream("\\\\Skoda/down/OAIToolkit-0.2alpha/marc/mjl.keys4.mrc");
		MarcReader reader = new MarcPermissiveStreamReader(input, true, true);
		int i = 0;
		long start = System.currentTimeMillis();
		long now, total = 0;
		while (reader.hasNext()) {
			Record record;
			try {
				record = reader.next();
			} catch (MarcException me) {
				StringBuffer error = new StringBuffer(me.getMessage()); 
				Throwable e = me.getCause();
				do {
					error.append(" - ").append(e.getMessage());
					e = e.getCause();
				} while (e != null);
				System.out.println(error.toString());
				continue;
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			if (++i % 1000 == 0) {
				now = System.currentTimeMillis();
				total += now - start;
				System.out.println(i + ") " + record.getControlNumber() + " "
						+ total + ", " + (now - start));
				start = now;
			}
		}
	}

	public void xtestPermissive() {
		PrintStream out = System.out;
		boolean verbose = true;
		boolean veryverbose = true;

		File file = new File(FILE);
		MarcReader readerNormal = null;
		MarcReader readerPermissive = null;
		boolean to_utf_8 = true;

		InputStream inNorm;
		InputStream inPerm;
		OutputStream patchedRecStream = null;
		MarcWriter patchedRecs = null;
		ErrorHandler errorHandler = new ErrorHandler();
		try {
			inNorm = new FileInputStream(file);
			readerNormal = new MarcPermissiveStreamReader(inNorm, false,
					to_utf_8);
			inPerm = new FileInputStream(file);
			readerPermissive = new MarcPermissiveStreamReader(inPerm,
					errorHandler, to_utf_8, "BESTGUESS");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			patchedRecStream = new FileOutputStream(new File("test_bad.mrc"));
			patchedRecs = new MarcStreamWriter(patchedRecStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i=0;
		while (readerNormal.hasNext() && readerPermissive.hasNext()) {
			Record recNorm;
			Record recPerm;
			String strPerm;
			try {
				recPerm = readerPermissive.next();
				if (++i % 1000 == 0) {
					System.out.println(recPerm.getControlNumber());
				}
				strPerm = recPerm.toString();
				recNorm = readerNormal.next();
			} catch (MarcException me) {
				if (verbose) {
					out.println("Fatal Exception: " + me.getMessage());
					out.println("-------------------------------------------------------------------------------------");
				}
				continue;
			}
			String strNorm = recNorm.toString();
			if (!strNorm.equals(strPerm)) {
				if (verbose) {
					//dumpErrors(out, errorHandler);
					//showDiffs(out, strNorm, strPerm);
					//out.println("-------------------------------------------------------------------------------------");
				}
				if (patchedRecs != null) {
					patchedRecs.write(recPerm);
				}
			} else if (errorHandler.hasErrors()) {
				if (verbose) {
					out.println("Results identical, but errors reported");
					//dumpErrors(out, errorHandler);
					//showDiffs(out, strNorm, strPerm);
					out.println("-------------------------------------------------------------------------------------");
				}
				if (patchedRecs != null) {
					patchedRecs.write(recPerm);
				}
			} else if (veryverbose) {
				//showDiffs(out, strNorm, strPerm);
			}

		}
	}

}
