/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import org.marc4j.marc.Record;
import org.marc4j.marc.impl.ControlFieldImpl;
import org.marc4j.marc.impl.DataFieldImpl;
import org.marc4j.marc.impl.LeaderImpl;
import org.marc4j.marc.impl.RecordImpl;
import org.marc4j.marc.impl.SubfieldImpl;

import junit.framework.TestCase;

public class MarcRecordTest extends TestCase {

	public void test() {
		String recordString = "LEADER 00714cam a2200205 a 4500" + "\n"
			+ "001 12883376" + "\n"
			+ "005 20030616111422.0" + "\n"
			+ "008 020805s2002    nyu    j      000 1 eng" + "\n"
			+ "020   $a0786808772" + "\n"
			+ "020   $a0786816155 (pbk.)" + "\n"
			+ "040   $aDLC$cDLC$dDLC" + "\n"
			+ "100 1 $aChabon, Michael." + "\n"
			+ "245 10$aSummerland /$cMichael Chabon." + "\n"
			+ "250   $a1st ed." + "\n"
			+ "260   $aNew York :$bMiramax Books/Hyperion Books for Children,$cc2002." + "\n"
			+ "300   $a500 p. ;$c22 cm." + "\n"
			+ "520   $aEthan Feld, the worst baseball player in the history of the game, finds" +
					" himself recruited by a 100-year-old scout to help a band of fairies triumph over" +
					" an ancient enemy." + "\n"
			+ "650  1$aFantasy." + "\n"
			+ "650  1$aBaseball$vFiction." + "\n"
			+ "650  1$aMagic$vFiction.\n";
		Record newRecord = new RecordImpl();
		String[] lines = recordString.split("\n");
		for(String line : lines) {
			if(line.startsWith("LEADER")) {
				newRecord.setLeader(new LeaderImpl(line.substring(7)));
			} else if(line.startsWith("00")) {
				newRecord.addVariableField(new ControlFieldImpl(line.substring(0,3), line.substring(4)));
			} else {
				DataFieldImpl field = new DataFieldImpl();
				field.setTag(line.substring(0,3));
				field.setIndicator1(line.substring(4,5).charAt(0));
				field.setIndicator2(line.substring(5,6).charAt(0));
				String[] subfields = line.substring(7).split("\\$");
				for(String subfield : subfields) {
					field.addSubfield(new SubfieldImpl(subfield.substring(0,1).charAt(0), 
							subfield.substring(1)));
				}
				newRecord.addVariableField(field);
			}
		}
		System.out.println(newRecord.toString());
		System.out.println(newRecord.toString().equals(recordString));
	}
}
