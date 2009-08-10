/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.DTOs;

import java.sql.Timestamp;

/**
 * resumptionToken is a sample session-like token, which help identify and
 * and continue the previous queries
 * @author Peter Kiraly
 */
public class ResumptionTokenDTO extends DataTransferObject {

	/** the id of the token */
	private Integer id;
	
	/** the creation date of the token */
	private Timestamp creationDate;
	
	/** the SQL query to get the records */
	private String query;
	
	/** the SQL query to count the total number of records matching the SQL query */
	private String queryForCount;
	
	/** the metadata prefix for displaying records */
	private String metadataPrefix;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getQueryForCount() {
		return queryForCount;
	}

	public void setQueryForCount(String queryForCount) {
		this.queryForCount = queryForCount;
	}
	
	public String getMetadataPrefix() {
		return metadataPrefix;
	}

	public void setMetadataPrefix(String metadataPrefix) {
		this.metadataPrefix = metadataPrefix;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id: ").append(id);
		sb.append(", creationDate: ").append(creationDate);
		sb.append(", query: ").append(query);
		sb.append(", queryForCount: ").append(queryForCount);
		sb.append(", metadataPrefix: ").append(metadataPrefix);
		return sb.toString();
	}
}
