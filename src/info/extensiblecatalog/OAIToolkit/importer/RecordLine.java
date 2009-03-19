/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

import org.apache.log4j.Logger;

/**
 * Information about one line in a MARC record's textual presentation: 
 * the text of {@link #line}, {@link #beginningOfLine}, {@link #endOfLine},
 * {@link #position} (character positions inside the record), 
 * {@link #positionInLine}. A line practically represents a MARC field.
 * 
 * Examples of textual representation of lines:
 * <pre>
 * LEADER 00714cam a2200205 a 4500 // a Leader
 * 001 12883376                    // a control field
 * 020   $a0786808772              // a data field
 * 100 1 $aChabon, Michael.        // a data field with indicator
 * </pre>
 * 
 * @author kiru
 */
public class RecordLine {
	
	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);

	/** The character position of the begining of the line */
	private int beginningOfLine;
	
	/** The character position of the end of the line */
	private int endOfLine;
	
	/** The String representation of the line */
	private String line;
	
	/** The position of the error character in the record */
	private int position;

	/** The position of the error character in the line */
	private int positionInLine = -2;
	
	/**
	 * Create a new RecordLine object
	 * @param recordString The string representation of a MARC record
	 * @param position The prosition of a invalid character inside record
	 */
	public RecordLine(String recordString, int position) {
		this.position = position;
		endOfLine = recordString.indexOf("\n", position);
		beginningOfLine = recordString.substring(0, endOfLine).lastIndexOf(
				"\n") + 1;
		if(beginningOfLine == -1) {
			beginningOfLine = 0;
		}
		line = recordString.substring(beginningOfLine, endOfLine);
	}

	/**
	 * Get the character position of the beginning of the line
	 * @return {@link #beginningOfLine}
	 */
	public int getBeginningOfLine() {
		return beginningOfLine;
	}

	/**
	 * Get the character position of the end of the line
	 * @return {@link #endOfLine}
	 */
	public int getEndOfLine() {
		return endOfLine;
	}

	/**
	 * Get the string representation of the line
	 * @return {@link #line}
	 */
	public String getLine() {
		return line;
	}
	
	/**
	 * Get the position of the invalid character inside the record
	 * @return {@link #position}
	 */
	public int getPosition() {
		return position;
	}
	
	/**
	 * Get the position of the invalid character inside the line
	 * @return {@link #positionInLine}
	 */
	public int getPositionInLine() {
		if(positionInLine == -2) {
			positionInLine = position - beginningOfLine;
		}
		return positionInLine;
	}
	
	/**
	 * Get the invalid character
	 * @return
	 */
	public String getInvalidChar() {
		return line.substring(getPositionInLine(), getPositionInLine()+1);
	}

	/**
	 * Get the hexa representation of the invalid character 
	 * @return
	 */
	public String getInvalidCharHexa() {
		return TextUtil.charToHexa(getInvalidChar().charAt(0));
	}
	
	/**
	 * Show the location of the invalid character. An example:
	 * <pre>
	 * LEADER 01502ccm a2200361 4500
	 * ------------------------^ ('\u000E')
	 * </pre> 
	 * @return
	 */
	public String getErrorLocation() {
		StringBuffer invalidCharLocator = new StringBuffer();
		invalidCharLocator.append(line).append(ApplInfo.LN);
		for(int pos = beginningOfLine; pos < position; pos++) {
			invalidCharLocator.append('-');
		}
		invalidCharLocator.append("^ (");
		invalidCharLocator.append(getInvalidCharHexa()).append(")");
		return invalidCharLocator.toString();
	}
}