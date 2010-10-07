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
 * Simple statistics class to count transformed and invalid MARCXML records
 * @author Király Péter
 */
public class ModificationStatistics {
	
	/** The number of successfully converted MARC records */
	private int transformed = 0;
	
	/** The number of invalid, not converted MARC records */
	private int invalid = 0;
	
	/** The number of invalid, not converted MARC files */
	private int invalidFiles = 0;
	
	/** The start time of import */
	private long startTime;

	public ModificationStatistics(){
		init();
	}

	public ModificationStatistics(int transformed, int invalid) {
		this.transformed = transformed;
		this.invalid   = invalid;
		init();
	}
	
	public void add(ModificationStatistics otherStat) {
		this.transformed += otherStat.getTransformed();
		this.invalid   += otherStat.getInvalid();
	}

	private void init() {
		startTime = System.currentTimeMillis();
	}
	
	public String toString(String filename) {
		return "Conversion statistics for " + filename + " " + toString();
	}

	public String toString() {
		return "converted: " + getTransformed()
			+ ", invalid: " + getInvalid()
			+ " records."
			+ "  Invalid files:" + getInvalidFiles() + "."
			+ "  It took " + MilliSecFormatter.toString(getTimeSpan());
	}

	public int getTransformed() {
		return transformed;
	}

	public void setTransformed(int transformed) {
		this.transformed = transformed;
	}

	public void addTransformed(int transformed) {
		this.transformed += transformed;
	}

	public void addTransformed() {
		this.transformed += 1;
	}
	
	public void addInvalidFile() {
		this.invalidFiles += 1;
	}
	
	public int getInvalidFiles() {
		return this.invalidFiles;
	}

	public int getInvalid() {
		return invalid;
	}

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
