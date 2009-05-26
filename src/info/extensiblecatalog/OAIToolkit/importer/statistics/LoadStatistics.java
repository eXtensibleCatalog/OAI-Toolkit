/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.importer.statistics;

import java.util.List;

import info.extensiblecatalog.OAIToolkit.importer.ImporterConstants.ImportType;
import info.extensiblecatalog.OAIToolkit.utils.MilliSecFormatter;

/** 
 * Simple statistics class to count converted and invalid MARC records
 * 
 * @author Király Péter
 */
public class LoadStatistics {
	
	/** The number of successfully created MARC records */
	private int created;

	/** The number of successfully updated MARC records (they 
	 * have existed already but contained different value(s)) */
	private int updated;

	/** The number of skipped MARC records (they have existed already) */
	private int skipped;

	/** The number of invalid, not converted MARC records */
	private int invalid;

	/** The number of deleted records */
	private int deleted;
	
	/** The number of bibliographic records */
	private int bibliographic;
	
	/** The number of authority records */
	private int authority;
	
	/** The number of holding records */
	private int holdings;
	
	/** The number of classification records */
	private int classification;
	
	/** The number of community records */
	private int community;
	
	/** The number of invalid files */
	private int invalidFiles = 0;

	/** The start time of import */
	private long startTime;

	/** Time taken to check whether a record is existent in the database */
	private long checkTime = 0;

	/** Time taken to insert/modify record in the database */
	private long insertTime = 0;

	/** Creates a new load statistics object */
	public LoadStatistics(){
		init();
	}

	/**
	 * Creates a new load statistics object with initial values.
	 * @param created The number of newly created records.
	 * @param updated The number of updated records.
	 * @param skipped The number of skipped rcords.
	 * @param invalid The number of invalid records.
	 */
	public LoadStatistics(int created, int updated, int skipped, int invalid) {
		this.created = created;
		this.updated = updated;
		this.skipped = skipped;
		this.invalid = invalid;
		init();
	}
	
	/**
	 * Creates a new load statistics object with initial values.
	 * @param created The number of newly created records.
	 * @param updated The number of updated records.
	 * @param skipped The number of skipped rcords.
	 * @param invalid The number of invalid records.
	 * @param deleted The number of deleted records.
	 * @param bibliographic The number of bibiographical records.
	 * @param authority The number of authority records.
	 * @param holdings The number of holding records.
	 * @param classification The number of classification records.
	 * @param community The number of community records.
	 * @param invalidFiles The number of invalid files.
	 */
	public LoadStatistics(int created, int updated, int skipped, int invalid, 
			int deleted, int bibliographic, int authority, int holdings, 
			int classification, int community, int invalidFiles) {
		this.created = created;
		this.updated = updated;
		this.skipped = skipped;
		this.invalid = invalid;
		this.deleted = deleted;
		this.bibliographic = bibliographic;
		this.authority = authority;
		this.holdings = holdings;
		this.classification = classification;
		this.community = community;
		this.invalidFiles = invalidFiles;
		init();
	}

	/**
	 * Initialize the statistics object. Save the time the object was created.
	 */
	private void init() {
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * Increase the number of the stored values with new numbers.
	 * @param created The number of newly created records.
	 * @param updated The number of updated records.
	 * @param skipped The number of skipped records.
	 * @param invalid The number of invalid records.
	 */
	public void add(int created, int updated, int skipped, int invalid) {
		this.created += created;
		this.updated += updated;
		this.skipped += skipped;
		this.invalid += invalid;
	}
	
	/**
	 * Increase the number of the stored values with new numbers.
	 * @param created The number of newly created records.
	 * @param updated The number of updated records.
	 * @param skipped The number of skipped records.
	 * @param invalid The number of invalid records.
	 * @param deleted The number of deleted records.
	 * @param bibliographic The number of bibliographic records.
	 * @param authority The number of authority records.
	 * @param holdings The number of holding records.
	 * @param classification The number of classification records.
	 * @param community The number of community records.
	 * @param invalidFiles The number of invalid files.
	 * @param checkTime The amout of time during cheking whether the records 
	 * is already existent in the database
	 * @param insertTime The amount of time during inserting records into
	 * the database
	 */
	public void add(int created, int updated, int skipped, int invalid, 
			int deleted, int bibliographic, int authority, int holdings, 
			int classification, int community, int invalidFiles, 
			long checkTime, long insertTime) {
		this.created += created;
		this.updated += updated;
		this.skipped += skipped;
		this.invalid += invalid;
		this.deleted += deleted;
		this.bibliographic += bibliographic;
		this.authority += authority;
		this.holdings += holdings;
		this.classification += classification;
		this.community += community;
		this.invalidFiles += invalidFiles;
		this.checkTime += checkTime;
		this.insertTime += insertTime;
	}

	/**
	 * Increase the values of variable according to the given {@link ImportType}
	 * @param importType
	 */
	public void add(ImportType importType) {
		if(importType == ImportType.CREATED) {
			created++;
		} else if(importType == ImportType.UPDATED) {
			updated++;
		} else if(importType == ImportType.SKIPPED) {
			skipped++;
		} else if(importType == ImportType.INVALID) {
			invalid++;
		} else if(importType == ImportType.DELETED) {
			deleted++;
		} else if(importType == ImportType.BIBLIOGRAPHIC) {
			bibliographic++;
		} else if(importType == ImportType.AUTHORITY) {
			authority++;
		} else if(importType == ImportType.HOLDINGS) {
			holdings++;
		} else if(importType == ImportType.CLASSIFICATION) {
			classification++;
		} else if(importType == ImportType.COMMUNITY) {
			community++;
		} else if(importType == ImportType.INVALID_FILES) {
			invalidFiles++;
		}
	}

	/**
	 * Increase the values of variables according to the given list of 
	 * {@link ImportType}
	 * @param typeList List of one or more {@link ImportType} 
	 */
	public void add(List<ImportType> typeList) {
		for(ImportType type : typeList) {
			add(type);
		}
	}
	
	/**
	 * Increase the dureation of checking and inserting time.
	 * @param checkTime The checking time
	 * @param insertTime The inserting time
	 */
	public void add(long checkTime, long insertTime) {
		this.checkTime += checkTime;
		this.insertTime += insertTime;
	}

	/**
	 * Increase the values of this object with another object's values 
	 * @param other The other statistical object
	 */
	public void add(LoadStatistics other) {
		add(	
			other.getCreated(), 
			other.getUpdated(), 
			other.getSkipped(), 
			other.getInvalid(),
			other.getDeleted(),
			other.getBibliographic(),
			other.getAuthority(),
			other.getHoldings(),
			other.getClassification(),
			other.getCommunity(),
			other.getInvalidFiles(),
			other.getCheckTime(),
			other.getInsertTime()
		);
	}

	/**
	 * Increase the number of newly created records
	 * @param created
	 */
	public void addCreated(int created) {
		this.created += created;
	}

	/**
	 * Increase the number of updated records
	 * @param updated
	 */
	public void addUpdated(int updated) {
		this.updated += updated;
	}

	/**
	 * Increase the number of skipped records
	 * @param skipped
	 */
	public void addSkipped(int skipped) {
		this.skipped += skipped;
	}
	
	/**
	 * Increase the number of invalid records
	 * @param invalid
	 */
	public void addInvalid(int invalid) {
		this.invalid += invalid;
	}

	/**
	 * Gives a human readable message including the file name which of the 
	 * statistics is about.
	 * @param filename The name of file, which of the statistics is about
	 * @return A statistics
	 */
	public String toString(String filename) {
		return "Import statistics for " + filename + ": " + toString();
	}
	
	/**
	 * Gives the value of the fields, and the time taken.
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("created ").append(getCreated()); 
		sb.append(", updated: ").append(getUpdated());
		sb.append(", skipped: ").append(getSkipped());
		sb.append(", invalid: ").append(getInvalid());
		sb.append(", deleted: ").append(getDeleted()); 
		sb.append(", bib: ").append(getBibliographic());
		sb.append(", auth: ").append(getAuthority());
		sb.append(", holdings: ").append(getHoldings());
		sb.append(" records.");
		sb.append(" Invalid files: ").append(getInvalidFiles());
		sb.append(". It took ").append(
				MilliSecFormatter.toString(getTimeSpan()));
		sb.append(". checkTime: ").append(
				MilliSecFormatter.toString(getCheckTime()));
		sb.append(". insertTime: ").append(
				MilliSecFormatter.toString(getInsertTime()));
		sb.append(". others: ").append(
				MilliSecFormatter.toString(getTimeSpan() 
						- getCheckTime() 
						- getInsertTime()));
		return sb.toString();
	}

	/** 
	 * Get the total time of running
	 * @return
	 */
	public long getTimeSpan() {
		return System.currentTimeMillis() - startTime;
	}

	/**
	 * Get the number of newly created records
	 * @return
	 */
	public int getCreated() {
		return created;
	}

	/**
	 * Set the number of newly created records
	 * @param created
	 */
	public void setCreated(int created) {
		this.created = created;
	}

	/**
	 * Get the number of skipped records
	 * @return
	 */
	public int getSkipped() {
		return skipped;
	}

	/**
	 * Set the number of skipped records
	 * @param skipped
	 */
	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	/**
	 * Get the number of updated records
	 * @return
	 */
	public int getUpdated() {
		return updated;
	}

	/**
	 * Set the number of updated records
	 * @param updated
	 */
	public void setUpdated(int updated) {
		this.updated = updated;
	}

	/**
	 * Get the number of invalid records
	 * @return
	 */
	public int getInvalid() {
		return invalid;
	}

	/**
	 * Set the number of invalid records
	 * @param invalid
	 */
	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	/**
	 * Get the number of authority records
	 * @return
	 */
	public int getAuthority() {
		return authority;
	}

	/**
	 * Set the number of authority records
	 * @param authority
	 */
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	/**
	 * Get the number of bibliographic records
	 * @return
	 */
	public int getBibliographic() {
		return bibliographic;
	}

	/**
	 * Set the number of bibliographic records
	 * @param bibliographic
	 */
	public void setBibliographic(int bibliographic) {
		this.bibliographic = bibliographic;
	}

	/**
	 * Get the number of classification records
	 * @return
	 */
	public int getClassification() {
		return classification;
	}

	/**
	 * Set the number of classification records
	 * @param classification
	 */
	public void setClassification(int classification) {
		this.classification = classification;
	}

	/**
	 * Get the number of community records
	 * @return
	 */
	public int getCommunity() {
		return community;
	}

	/**
	 * Set the number of community records
	 * @param community
	 */
	public void setCommunity(int community) {
		this.community = community;
	}

	/**
	 * Get the number of deleted records
	 * @return
	 */
	public int getDeleted() {
		return deleted;
	}

	/**
	 * Set the number of deleted records
	 * @param deleted
	 */
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	/**
	 * Get the number of holding records
	 * @return
	 */
	public int getHoldings() {
		return holdings;
	}

	/**
	 * Set the number of holding records
	 * @param holdings
	 */
	public void setHoldings(int holdings) {
		this.holdings = holdings;
	}

	/**
	 * Get the number of invalid files
	 * @return
	 */
	public int getInvalidFiles() {
		return invalidFiles;
	}

	/**
	 * Set the number of invalid files
	 * @param invalidFiles
	 */
	public void setInvalidFiles(int invalidFiles) {
		this.invalidFiles = invalidFiles;
	}

	/**
	 * Get the duration of checking time
	 * @return
	 */
	public long getCheckTime() {
		return checkTime;
	}

	/**
	 * Set the duration of checking time
	 * @param checkTime
	 */
	public void setCheckTime(long checkTime) {
		this.checkTime = checkTime;
	}

	/**
	 * Get the duration of inserting time
	 * @return
	 */
	public long getInsertTime() {
		return insertTime;
	}

	/**
	 * Set the duration of inserting time
	 * @param insertTime
	 */
	public void setInsertTime(long insertTime) {
		this.insertTime = insertTime;
	}
}
