/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import java.lang.reflect.Method;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

/**
 * Storage of the most important features of a field in a database.
 * @author Peter Kiraly
 */
public class FieldMetadata {

	/** name of the field */
	private String name;

	/** SQL type of the column */
	private String type;

	/** Flag to signal whether the field can contain null value */
	private boolean isNullable;
	
	private String javaField;
	private String accessor;
	private Class javaType;
	private Method accessorMthd;

	/**
	 * Create a new FieldMetadata object
	 * @param type The SQL type of the field
	 * @param isNullable Flag to signal whether the field can contain null value
	 */
	public FieldMetadata(String name, String type, boolean isNullable) {
		this.name = name;
		this.type = type;
		this.isNullable = isNullable;
	}

	/**
	 * Create a new FieldMetadata object
	 * @param type The SQL type of the field
	 * @param isNullable Flag to signal whether the field can contain null value
	 */
	public FieldMetadata(String type, boolean isNullable) {
		this.type = type;
		this.isNullable = isNullable;
	}

	/** 
	 * Get the name of the field
	 * @return name of field
	 */
	public String getName() {
		return name;
	}

	/** 
	 * Can the field contain null value?
	 * @return true if the field is nullable, false if not
	 */
	public boolean isNullable() {
		return isNullable;
	}

	/**
	 * Get the SQL type of the field
	 * @return The SQL type of the field
	 */
	public String getType() {
		return type;
	}
	
	public String getJavaField() {
		if(javaField == null) {
			javaField = TextUtil.toCamelCase(name);
		}
		return javaField;
	}
	
	public String getAccessor() {
		if(accessor == null) {
			accessor = TextUtil.toCamelCase("set_" + name);
		}
		return accessor;
	}
	
	public Class getJavaType(DataTransferObject record) 
			throws NoSuchFieldException {
		if(javaType == null) {
			javaType = record.getClass().getDeclaredField(getJavaField())
							.getType();
		}
		return javaType;
	}
	
	public Method getAccessorMethod(DataTransferObject record) 
			throws NoSuchMethodException, NoSuchFieldException {
		if(accessorMthd == null) {
			accessorMthd = record.getClass().getDeclaredMethod(
					getAccessor(), new Class[] { getJavaType(record) });
		}
		return accessorMthd;
	}
}
