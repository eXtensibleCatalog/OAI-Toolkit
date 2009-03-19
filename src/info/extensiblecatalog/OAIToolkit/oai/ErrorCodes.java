/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai;

import info.extensiblecatalog.OAIToolkit.utils.XMLUtil;

public class ErrorCodes {
	
	public static final String BAD_ARGUMENT = "The request includes illegal arguments"
		+ " or is missing required arguments.";

	public static final String BAD_ARGUMENT_IDENTIFY = "The request includes "
		+ "illegal arguments.";

	public static final String BAD_RESUMPTION_TOKEN = "The value of the "
		+ "resumptionToken argument is invalid or expired.";
	
	public static final String BAD_VERB = "Illegal OAI verb.";

	public static final String CANNOT_DISSEMINATE_FORMAT = "The value of the "
		+ "metadataPrefix argument is not supported by the item identified by"
		+ " the value of the identifier argument.";
	
	public static final String ID_DOES_NOT_EXIST = "The value of the identifier "
		+ "argument is unknown or illegal in this repository.";

	public static final String NO_METADATA_FORMATS = "There are no metadata "
		+ "formats available for the specified item.";
	
	public static final String NO_RECORDS_MATCH = "The combination of the values " 
		+ "of the from, until, set, and metadataPrefix arguments results in an "
		+ "empty list.";

	public static final String NO_SET_HIERARCHY = "The repository does not support"
		+ " sets.";

	public static String badArgumentError() {
		return badArgumentError(null);
	}

	public static String badArgumentError(String message) {
		return xmlError(BAD_ARGUMENT, message, "badArgument");
	}

	public static String badResumptionTokenError() {
		return badResumptionTokenError(null);
	}

	public static String badResumptionTokenError(String message) {
		return xmlError(BAD_RESUMPTION_TOKEN, message, "badResumptionToken");
	}

	public static String badVerbError() {
		return badVerbError(null);
	}

	public static String badVerbError(String message) {
		return xmlError(BAD_VERB, message, "badVerb");
	}

	public static String cannotDisseminateFormatError() {
		return cannotDisseminateFormatError(null);
	}

	public static String cannotDisseminateFormatError(String message) {
		return xmlError(ID_DOES_NOT_EXIST, message, "cannotDisseminateFormat");
	}

	public static String idDoesNotExistError() {
		return idDoesNotExistError(null);
	}

	public static String idDoesNotExistError(String message) {
		return xmlError(ID_DOES_NOT_EXIST, message, "idDoesNotExist");
	}
	
	public static String noMetadataFormatsError() {
		return noMetadataFormatsError(null);
	}

	public static String noMetadataFormatsError(String message) {
		return xmlError(NO_METADATA_FORMATS, message, "noMetadataFormats");
	}
	
	
	public static String noRecordsMatchError() {
		return noRecordsMatchError(null);
	}

	public static String noRecordsMatchError(String message) {
		return xmlError(NO_RECORDS_MATCH, message, "noRecordsMatch");
	}

	public static String noSetHierarchyError() {
		return noSetHierarchyError(null);
	}

	public static String noSetHierarchyError(String message) {
		return xmlError(NO_SET_HIERARCHY, message, "noSetHierarchy");
	}
	
	private static String xmlError(String base, String message, String code) {
		String msg = base;
		if(null != message && message.length() > 0) {
			msg += " " + message;
		}
		return XMLUtil.xmlTag("error", msg, new String[]{"code", code});
	}
}
