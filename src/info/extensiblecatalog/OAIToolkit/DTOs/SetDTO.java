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
 * A set is an optional construct for grouping items for the purpose of 
 * selective harvesting. Repositories may organize items into sets. Set 
 * organization may be flat, i.e. a simple list, or hierarchical. Multiple 
 * hierarchies with distinct, independent top-level nodes are allowed. 
 * Hierarchical organization of sets is expressed in the syntax of the 
 * setSpec parameter as described below. When a repository defines a set 
 * organization it must include set membership information in the headers 
 * of items returned in response to the ListIdentifiers, ListRecords and 
 * GetRecord requests.
 * @author Peter Kiraly
 */
public class SetDTO extends DataTransferObject {
	
	/** Autoincremented identifier */
	private Integer setId;
	
	/** A short human-readable string naming the set */
	private String setName;
	
	/** Set */
	private String setTag;
	
	/** A colon [:] separated list indicating the path from the root 
	 * of the set hierarchy to the respective node. Each element in 
	 * the list is a string consisting of any valid URI unreserved 
	 * characters, which must not contain any colons [:]. Since a 
	 * set_spec forms a unique identifier for the set within the 
	 * repository, it must be unique for each set. Flat set organizations 
	 * have only sets with set_spec that do not contain any colons [:]*/
	private String setSpec;
	
	/** An optional container that may hold community-specific XML-encoded 
	 * data about the set */
	private String setDescription;
	
	public Integer getSetId() {
		return setId;
	}
	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	
	public String getSetTag() {
		return setTag;
	}
	public void setSetTag(String setTag) {
		this.setTag = setTag;
	}

	public String getSetSpec() {
		return setSpec;
	}
	public void setSetSpec(String setSpec) {
		this.setSpec = setSpec;
	}
	
	public String getSetDescription() {
		return setDescription;
	}
	public void setSetDescription(String setDescription) {
		this.setDescription = setDescription;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("setId: ").append(setId);
		sb.append(", name: ").append(setName);
		sb.append(", tag: ").append(setTag);
		sb.append(", spec: ").append(setSpec);
		sb.append(", description: ").append(setDescription);
		return sb.toString();
	}

}
