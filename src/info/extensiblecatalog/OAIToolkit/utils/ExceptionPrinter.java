/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.utils;

import java.util.ArrayList;
import java.util.List;

public class ExceptionPrinter {
	
	private static final String MYPACKAGE = "extensiblecatalog";
	
	public static String getStack(Throwable e) {
		return getStack(e, new StringBuffer());
	}
	
	public static String getStack(Throwable e, StringBuffer sb) {
		if(sb.length() > 0) {
			sb.append(" ");
		}
		sb.append(e.getClass().getSimpleName())
			.append(" ")
			.append(e.getMessage())
			.append(".");
		StackTraceElement[] stack = e.getStackTrace();
		List<StackTraceElement> ministack = new ArrayList<StackTraceElement>();
		int i = 0;
		int j = 0; 
		for(StackTraceElement el : stack) {
			if(i == 1) {
				ministack.add(el);
			}
			if(el.getClassName().indexOf(MYPACKAGE) > -1 
				&& el.getClassName().indexOf("ExceptionPrinter") == -1) {
				ministack.add(el);
				if(++j > 3) {
					break;
				}
			}
			i++;
		}
		
		sb.append(" Throwed by ").append(ministack.get(0).getClassName())
			.append(".").append(ministack.get(0).getMethodName()).append("().");
		sb.append(" Called from ").append(ministack.get(1).toString())
			.append(".");
		
		if(e.getCause() != null) {
			return getStack(e.getCause(), sb);
		} else {
			return sb.toString();
		}
	}
}
