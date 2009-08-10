/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer.statistics;

import info.extensiblecatalog.OAIToolkit.utils.MilliSecFormatter;

/** 
 * Simple statistics class to count converted and invalid MARC records
 * @author Király Péter
 */
public class ConversionStatistics {
	
	/** The number of successfully converted MARC records */
	private int converted = 0;
	
	/** The number of invalid, not converted MARC records */
	private int invalid = 0;
	
	/** The start time of import */
	private long startTime;

	/**
	 * Creates a new conversion statistics object
	 */
	public ConversionStatistics(){
		init();
	}

	/**
	 * Creates a new conversion statistics object with initial values
	 * @param converted The number of converted records
	 * @param invalid The nomber of invalid records
	 */
	public ConversionStatistics(int converted, int invalid) {
		this.converted = converted;
		this.invalid   = invalid;
		init();
	}
	
	/**
	 * Add another statistics object's values to the current values
	 * @param otherStat The other statistics object
	 */
	public void add(ConversionStatistics otherStat) {
		this.converted += otherStat.getConverted();
		this.invalid   += otherStat.getInvalid();
	}

	/**
	 * Initialize the statistics object. Save the time the object was created.
	 */
	private void init() {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Gives a human readable message including the file name which of the 
	 * statistics is about.
	 * @param filename The name of file, which of the statistics is about
	 * @return A statistics
	 */
	public String toString(String filename) {
		return "Conversion statistics for " + filename + " " + toString();
	}

	/**
	 * Gives the value of the fields, and the time taken.
	 */
	public String toString() {
		return "converted: " + getConverted()
			+ ", invalid: " + getInvalid()
			+ " records. It took " + MilliSecFormatter.toString(getTimeSpan());
	}

	/**
	 * Get {@link #converted}, the number of converted records
	 * @return {@link #converted}
	 */
	public int getConverted() {
		return converted;
	}

	/**
	 * Set {@link #converted}, the number of converted records
	 * @param converted The number of converted records
	 */
	public void setConverted(int converted) {
		this.converted = converted;
	}

	/**
	 * Add the number of converted records to the current value
	 * @param converted
	 */
	public void addConverted(int converted) {
		this.converted += converted;
	}

	/**
	 * Add one more converted record
	 */
	public void addConverted() {
		this.converted += 1;
	}

	/**
	 * Get the number of invalid records
	 * @return The number of invalid records
	 */
	public int getInvalid() {
		return invalid;
	}

	/**
	 * Set the number of invalid records
	 * @param invalid The number of invalid records
	 */
	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	/**
	 * Increase the number invalid by a given number
	 * @param invalid The addition
	 */
	public void addInvalid(int invalid) {
		this.invalid += invalid;
	}

	/**
	 * Increase the number invalid records by 1
	 */
	public void addInvalid() {
		this.invalid += 1;
	}
	
	/** 
	 * Get the total time of running
	 * @return
	 */
	public long getTimeSpan() {
		return System.currentTimeMillis() - startTime;
	}
}
