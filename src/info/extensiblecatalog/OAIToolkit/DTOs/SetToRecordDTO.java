/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.DTOs;

/**
 * This table connect sets to records. Each row repsresent a simple 
 * connection. If we want to connect multiple sets to the same 
 * record, we should create multiple rows in the sets_to_records 
 * table, one row for every sets.
 * @author Peter Kiraly
 */
public class SetToRecordDTO extends DataTransferObject {
	
	/** The referenced record's identifier */
	private Integer recordId;
	
	/** The referenced set's identifier */
	private Integer setId;

	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public Integer getSetId() {
		return setId;
	}
	public void setSetId(Integer setId) {
		this.setId = setId;
	}
	
	public String toString() {
		return recordId + ", " + setId;
	}
	
	public boolean isEmpty() {
		return recordId == null && setId == null;
	}
	
	public boolean equals(SetToRecordDTO other) {
		return (
				(isEmpty() && other.isEmpty())
				||
				(!isEmpty() && !other.isEmpty() 
				&& recordId.equals(other.getRecordId()) 	
				&& setId.equals(other.getSetId()))
			);
	}
}
