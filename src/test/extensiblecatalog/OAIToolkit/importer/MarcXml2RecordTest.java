/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.importer;

import info.extensiblecatalog.OAIToolkit.importer.MARCRecordWrapper;
import info.extensiblecatalog.OAIToolkit.importer.Modifier;
import info.extensiblecatalog.OAIToolkit.importer.RecordLine;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.marc4j.MarcReader;
import org.marc4j.MarcStreamReader;
import org.marc4j.marc.ControlField;
import org.marc4j.marc.DataField;
import org.marc4j.marc.Record;
import org.marc4j.marc.Subfield;
import org.marc4j.marc.impl.LeaderImpl;
import org.marc4j.marc.impl.SubfieldImpl;

import junit.framework.TestCase;

public class MarcXml2RecordTest extends TestCase {
	
	private static final String WEIRD_CHARACTERS = 
							"[\u0001-\u0008\u000b-\u000c\u000e-\u001f]";

	/** Regex Pattern for the non allowable characters in XML 1.0 (ASCII 
	 * Control Characters) */
	private static final Pattern WEIRD_CHARACTERS_PATTERN = 
										Pattern.compile(WEIRD_CHARACTERS);

	public void xtestMarc2Record() throws Exception {
		File xmlFile = new File("test/error.xml");
		System.out.println(xmlFile.getAbsolutePath() + " " + xmlFile.length());
		String MARCXML = TextUtil.readFileAsString(xmlFile);
		System.out.println(MARCXML);
		Record r = MARCRecordWrapper.MARCXML2Record(MARCXML);
		assertNotNull(r.getControlNumber());
	}
	
	public void xtestDropPipeline() throws Exception {
		File marcFile = new File("test/error.mrc");
		List<String> xslt = Arrays.asList(new String[]{"drop_pipeline.xsl"});
		Modifier modifier = new Modifier(true,false);
		modifier.addStyleSheets(xslt);
		
		MarcReader reader = new MarcStreamReader(new FileInputStream(marcFile));
		
		while(reader.hasNext()) {
			Record rec = reader.next();
			MARCRecordWrapper w = new MARCRecordWrapper(rec, false);
			w.setDoIndentXml(true);
			//System.out.println(w.getXml());
			System.out.println(modifier.modifyRecord(w));
		}
	}
	
	public void testMergeFields() throws Exception {
		File marcFile = new File("test/error.mrc");
		List<String> xslt = Arrays.asList(new String[]{"drop_pipeline.xsl"});
		Modifier modifier = new Modifier(true,false);
		modifier.addStyleSheets(xslt);
		
		MarcReader reader = new MarcStreamReader(new FileInputStream(marcFile));
		
		String default003Value = null;
		while(reader.hasNext()) {
			Record record = reader.next();
			
			ControlField collection = (ControlField)record.getVariableField("003");
			String newID = (collection != null) 
				? collection.getData() + record.getControlNumber()
				: (default003Value != null)
					? default003Value + record.getControlNumber()
					: null;
			if(newID != null) {
				ControlField fd = (ControlField)record.getVariableField("001");
				fd.setData(newID);
				record.addVariableField(fd);
			}
			
			System.out.println(record.toString());
	
			MARCRecordWrapper w = new MARCRecordWrapper(record,false);
			w.setDoIndentXml(true);
			//System.out.println(w.getXml());
			System.out.println(modifier.modifyRecord(w));
		}
	}
	

	
	public void testBadChars() throws Exception {
		File marcFile = new File("E:/doku/extensiblecatalog/marc/carli/uiu_bibs_5.mrc");
		MarcReader reader = new MarcStreamReader(new FileInputStream(marcFile));
		
		while(reader.hasNext()) {
			Record record = reader.next();
			if(!record.getControlNumber().equals("5124391")) { //4939046
				continue;
			}
			
			boolean hasInvalidChars = false;
			String recordString = record.toString();
			Matcher matcher = WEIRD_CHARACTERS_PATTERN.matcher(recordString);

			if(matcher.find()) {
				//invalidCharIndex = matcher.start();
				List<Integer> invalidCharsIndex = new ArrayList<Integer>();
				do {
					invalidCharsIndex.add(matcher.start());
				} while(matcher.find());
				StringBuffer badCharLocator = new StringBuffer();
				//List<String> invalidChars = new ArrayList<String>();
				for(Integer i : invalidCharsIndex) {
					RecordLine line = getLineOfRecord(recordString, i);
					badCharLocator.append(line.getErrorLocation()).append(ApplInfo.LN);
					System.out.println(
							line.getInvalidChar() 
							+ " (" + line.getInvalidCharHexa() + ")"
							+ " position: " + i + " " + line.getLine());
					modifyRecord(record, line);
					System.out.println(record.toString());
				}
			}

			MARCRecordWrapper w = new MARCRecordWrapper(record, false);
			w.setDoIndentXml(true);
			System.out.println(w.getXml());
			break;
		}
	}
	
	private RecordLine getLineOfRecord(String recordString, int position) {
		return new RecordLine(recordString, position);
	}

	private void modifyRecord(Record record, RecordLine line) {
		if(line.getLine().startsWith("LEADER")) {
			record.setLeader(new LeaderImpl(record.getLeader().toString().replaceAll(WEIRD_CHARACTERS, "0")));
		} else if(line.getLine().startsWith("00")) {
			String tag = line.getLine().substring(0,3);
			ControlField fd = (ControlField)record.getVariableField(tag);
			fd.setData(fd.getData().replaceAll(WEIRD_CHARACTERS, " "));
			System.out.println(fd);
			record.addVariableField(fd);
		} else {
			String tag = line.getLine().substring(0,3);
			DataField fd = (DataField)record.getVariableField(tag);
			
			// indicators
			fd.setIndicator1(String.valueOf(fd.getIndicator1())
					.replaceAll(WEIRD_CHARACTERS, " ").charAt(0));
			fd.setIndicator2(String.valueOf(fd.getIndicator2())
					.replaceAll(WEIRD_CHARACTERS, " ").charAt(0));
			record.removeVariableField(fd);
			List<Subfield> sfs = (List<Subfield>)fd.getSubfields();
			List<Subfield> newSfs = new ArrayList<Subfield>();
			List<Subfield> oldSfs = new ArrayList<Subfield>();
			for(Subfield sf : sfs) {
				oldSfs.add(sf);
				char code;
				if(WEIRD_CHARACTERS_PATTERN.matcher(String.valueOf(sf.getCode())).find()) {
					code = String.valueOf(sf.getCode())
						.replaceAll(WEIRD_CHARACTERS, " ").charAt(0);
				} else {
					code = sf.getCode();
				}
				newSfs.add(new SubfieldImpl(
						code,
						sf.getData().replaceAll(WEIRD_CHARACTERS, " ")));
			}
			for(Subfield sf : oldSfs) {
				fd.removeSubfield(sf);
			}
			for(Subfield sf : newSfs) {
				fd.addSubfield(sf);
			}
			System.out.println(fd);
			record.addVariableField(fd);
		}
	}
}
