/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

/*
 * Copyright 2006 Robert Burrell Donkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.extensiblecatalog.OAIToolkit.importer;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Compare two filenames. If the first is before the second in the abc,
 * it gives 1, if the second is before the first, it gives -1, if
 * the two are equals, it gives 0.
 * <pre>
 * 		File[] files = ...
 * 		Arrays.sort(files, new FileNameComparator());
 * </pre>
 *
 * @author kiru
 */
public class FileNameComparator implements Comparator<Object>, Serializable {
	
	// needs for serialization
	private static final long serialVersionUID = 3637449L; 

	/**
	 * Compare two file names according to the ABC
	 * @param first The first file
	 * @param second The second file
	 * @return -1 if the first is before the second, 1 if the second is 
	 * before the first, and 0 if theirs names are equal
	 */
	public int compare(Object first, Object second) {
		int result = 0;
        File firstFile = (File) first;
        File secondFile = (File) second;
        if (firstFile == null) {
            if (secondFile != null) {
                result = 1;
            }
        } else {
            if (secondFile == null) {
                result = -1;
            } else {
                final String firstName = firstFile.getName();
                final String secondName = secondFile.getName();
                result = firstName.compareTo(secondName);
            }
        }
        return result;
	}
}
