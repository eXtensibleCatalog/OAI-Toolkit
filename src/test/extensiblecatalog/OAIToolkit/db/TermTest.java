/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.db;

import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

import junit.framework.TestCase;

public class TermTest extends TestCase {

	private String dir = "E:/doku/extensiblecatalog/lucene_index";
	private IndexReader ir;
	
	public void testFirstTerm() {
		System.out.println(showFirstTerm("modification_date"));
	}

	public void testQuery() throws IOException, ParseException {
		String q = "+id:1765552923 +record_type:1";
		searchQuery(q);
	}

	public void testFromUntil() throws IOException, ParseException {
		String q = "modification_date:[2007-08-01 TO 2007-08-02]";
		searchQuery(q);

		q = "modification_date:[2007-08-01 TO \"2007-08-01 20:02:46.0\"]";
		searchQuery(q);

		q = "modification_date:[\"2007-08-01 19\" TO \"2007-08-01 20:02:46.0\"]";
		searchQuery(q);

		q = "modification_date:[\"2007-08-01 19:31:00\" TO \"2007-08-01 20:01\"]";
		searchQuery(q);
	}

	public void testFrom() throws IOException, ParseException {
		String from = "2008-05-01";
		String until = new Timestamp(new Date().getTime()).toString();
		//System.out.println(timestamp);
		//String until = TextUtil.timestampToString(timestamp);
		System.out.println(until);

		String q = "modification_date:[" + from + " TO \"" + until + "\"]";
		searchQuery(q);
	}
	
	public void testIncr() {
		for(int i=0; i<10;) {
			System.out.println(i + " = " + Integer.toBinaryString(i++));
		}
	}

	public void searchQuery(String q) throws IOException, ParseException {
		Searcher searcher = new IndexSearcher(dir);
		QueryParser qp = new QueryParser("id", new StandardAnalyzer());
		Query query = qp.parse(q);
		System.out.println(query);
		Hits hits = searcher.search(query);
		System.out.println(hits.length());
	}

	public String showFirstTerm(String field) {
		try {
			ir = IndexReader.open(dir);
			TermEnum te = ir.terms(new Term(field, ""));
			Term t = te.term();
			return t.text();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
