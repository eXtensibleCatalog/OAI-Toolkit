/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai.dataproviders;

public class BasicFacadeDataProvider {

	protected String tokenId;
	protected String from;
	protected String until;
	protected String set;
	protected String metadataPrefix;
	protected int offset;
	protected int recordLimit;

	protected boolean badResumptionTokenError = false;

	public void setParams(String tokenId, String from, 
			String until, String set, String metadataPrefix, int offset) {
		this.tokenId         = tokenId;
		this.from            = from;
		this.until           = until;
		this.set             = set;
		this.metadataPrefix  = metadataPrefix;
		this.offset          = offset;
	}

	public void setRecordLimit(int recordLimit) {
		this.recordLimit = recordLimit;
	}

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean hasBadResumptionTokenError() {
		return badResumptionTokenError;
	}

}
