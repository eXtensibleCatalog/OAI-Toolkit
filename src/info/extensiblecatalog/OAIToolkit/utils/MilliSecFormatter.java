/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.text.NumberFormat;

/**
 * Format a timespan in milliseconds to human readable string
 * @author Peter Kiraly
 */
public class MilliSecFormatter {

	/** one second in milliseconds */
	private static final int  SEC  = 1000;

	/** one minute in milliseconds */
	private static final int  MIN  = 60 * SEC;

	/** one hour in milliseconds */
	private static final int  HOUR = 60 * MIN;

	/** one day in milliseconds */
	private static final long DAY  = 24L * HOUR;
	
	/**
	 * Format a timespan in milliseconds to human readable string
	 * @param timeInMillis
	 * @return
	 */
	public static String toString(long timeInMillis) {
		
		NumberFormat nf = NumberFormat.getInstance(); 
		nf.setMinimumIntegerDigits(2);

		StringBuffer sb = new StringBuffer();
		
		// add days if any
		int days = (int) ( timeInMillis / DAY );
		if(days > 0) {
			sb.append(days);
		}
		int remainder = (int) (timeInMillis % DAY);
		
		// add hour
		int hours = (int) (remainder / HOUR);
		remainder = (int) (remainder % HOUR);
		if(sb.length() > 0) {
			sb.append(' ');
		}
		sb.append(nf.format(hours));

		// add minutes
		int minutes = remainder / ( MIN );
		remainder = remainder % ( MIN );
		sb.append(':').append(nf.format(minutes));
		
		// add seconds
		int seconds = remainder / SEC;
		sb.append(':').append(nf.format(seconds));

		// add milliseconds
		int ms = remainder % SEC;
		if(ms > 0) {
			nf.setMinimumIntegerDigits(3);
			sb.append('.').append(nf.format(ms));
		} else {
			sb.append('.').append(ms);
		}
		
		return sb.toString();
	}
}
