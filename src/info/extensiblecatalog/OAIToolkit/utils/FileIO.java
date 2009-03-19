/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.io.*;

/**
 * Some simple file IO primitives reimplemented in Java. All methods are static
 * since there is no state.
 */
public class FileIO {

	/** The size of blocking to use */
	protected static final int BUFFERSIZE = 8192;

	/** Copy a file from one filename to another */
	public static void copyFile(String inName, String outName)
			throws FileNotFoundException, IOException {
		if(!new File(inName).exists()) {
			return;
		}
		
		BufferedInputStream is = new BufferedInputStream(new FileInputStream(
				inName));
		BufferedOutputStream os = new BufferedOutputStream(
				new FileOutputStream(outName));
		copyFile(is, os, true);
	}

	/** Copy a file from an opened InputStream to an opened OutputStream */
	public static void copyFile(InputStream is, OutputStream os, boolean close)
			throws IOException {
		byte[] buffer = new byte[BUFFERSIZE]; // the byte read from the file
		int numOfBytes = is.read(buffer);
		while (numOfBytes>0) {
			os.write(buffer, 0, numOfBytes);
			numOfBytes = is.read(buffer);
		}
		is.close();
		if (close) {
			os.close();
		}
	}

	/** Copy a file from an opened Reader to an opened Writer */
	public static void copyFile(Reader is, Writer os, boolean close)
			throws IOException {
		int numOfBytes = is.read(); // the byte read from the file
		while (numOfBytes != -1) {
			os.write(numOfBytes);
			numOfBytes = is.read();
		}
		is.close();
		if (close) {
			os.close();
		}
	}

	/** Copy a file from a filename to a PrintWriter. */
	public static void copyFile(String inName, PrintWriter pw, boolean close)
			throws FileNotFoundException, IOException {
		if(!new File(inName).exists()) {
			return;
		}
		BufferedReader ir = new BufferedReader(new FileReader(inName));
		copyFile(ir, pw, close);
	}

	/** Open a file and read the first line from it. */
	public static String readLine(String inName) throws FileNotFoundException,
			IOException {
		BufferedReader is = new BufferedReader(new FileReader(inName));
		String line = null;
		line = is.readLine();
		is.close();
		return line;
	}

	/**
	 * Copy a data file from one filename to another, alternate method. As the
	 * name suggests, use my own buffer instead of letting the BufferedReader
	 * allocate and use the buffer.
	 */
	public void copyFileBuffered(String inName, String outName) 
			throws FileNotFoundException, IOException {
		if(!new File(inName).exists()) {
			return;
		}
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(inName);
			os = new FileOutputStream(outName);
			byte[] b = new byte[BUFFERSIZE]; // the bytes read from the file
			int numOfBytes = is.read(b); // the byte count
			while (numOfBytes != -1) {
				os.write(b, 0, numOfBytes);
				numOfBytes = is.read(b);
			}
		//} catch(Exception e) {
		} finally {
			if(is != null) {
				try {
					is.close();
				} catch(IOException e) {}
			}
			if(os != null) {
				try {
					os.close();
				} catch(IOException e) {}
			}
		}
	}

	/** Read the entire content of a Reader into a String */
	public static String readerToString(Reader is) throws IOException {
		StringBuffer sb = new StringBuffer();
		char[] buffer = new char[BUFFERSIZE];
		int numOfBytes = is.read(buffer);
		// Read a block. If it gets any chars, append them.
		while (numOfBytes > 0) {
			sb.append(buffer, 0, numOfBytes);
			numOfBytes = is.read(buffer);
		}
		// Only construct the String object once, here.
		return sb.toString();
	}

	/** Read the content of a Stream into a String */
	public static String inputStreamToString(InputStream is)
			throws IOException {
		return readerToString(new InputStreamReader(is));
	}
}