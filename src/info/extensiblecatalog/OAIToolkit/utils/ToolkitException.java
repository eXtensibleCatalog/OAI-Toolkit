/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

public class ToolkitException extends Exception {

	static final long serialVersionUID = 1L;

	public ToolkitException() {
		super("Exception in the ILS Toolkit");
	}

	public ToolkitException(String message) {
		super(message);
	}

	public ToolkitException(String message, Throwable cause) {
		super(message, cause);
	}

	public ToolkitException(Throwable cause) {
		super(cause);
	}

}
