/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import info.extensiblecatalog.OAIToolkit.utils.Logging;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.document.FieldSelectorResult;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class LuceneSearcher {
	
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	private IndexSearcher searcher;
	private IndexReader indexReader;
	
	private String earliestDatestamp;
	//private FieldSelector xmlSelector;
	private FieldSelector allFieldSelector;
	
	public LuceneSearcher(String luceneDir) {
		try {
			File indexDir = new File(luceneDir);
			Directory fsDir = FSDirectory.getDirectory(indexDir);
			searcher = new IndexSearcher(fsDir);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		
		/*
		xmlSelector = new FieldSelector() {
			private static final long serialVersionUID = 1426724242925499003L;

			public FieldSelectorResult accept(String fieldName) {
				if (fieldName.equals("xml")) {
					return FieldSelectorResult.LOAD;
				} else {
					return FieldSelectorResult.NO_LOAD;
				}
			};
		};
		*/

		allFieldSelector = new FieldSelector() {
			private static final long serialVersionUID = 1426724242925499003L;

			public FieldSelectorResult accept(String fieldName) {
				if (fieldName.equals("xml")) {
					return FieldSelectorResult.NO_LOAD;
				} else {
					return FieldSelectorResult.LOAD;
				}
			};
		};

	}
	
	public String getXmlOfRecord(Integer recordId, Integer recordType) {
		String content = null;
		try {
			Document doc = searcher.doc(recordId);
            if(doc != null) {
				content = doc.get("xml");
			} else {
				prglog.error("[PRG] There's no record with this ID: " + recordId);
			}
			/*
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("id", recordId.toString())), 
					Occur.MUST);
			query.add(new TermQuery(new Term("record_type", 
					recordType.toString())), Occur.MUST);
			Hits hits = searcher.search(query);
			prglog.info("getXmlOfRecord: " + recordId + ", " + recordType + "->" + hits.length());
			if(hits.length() == 1) {
				prglog.info("hits.id(0): " + hits.id(0));
				Document doc = indexReader.document(hits.id(0), xmlSelector);
				content = doc.get("xml");
			} else if(hits.length() == 0){
				prglog.error("There's no record with this ID: " + recordId);
			} else if(hits.length() > 1){
				prglog.error("There are multiple records with this ID: " + recordId);
			}
			*/
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return content;
	}

	public Document getRecordByID(Integer recordId) {
		Document doc = null;
		try {
			if(recordId >= 0 && recordId <= searcher.getIndexReader().numDocs()) {
				doc = searcher.doc(recordId);
			}
			//Query query = new TermQuery(new Term("id", recordId.toString()));
			//Hits hits = searcher.search(query);
			//doc = hits.doc(0);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
			e.printStackTrace();
		}
		return doc;
	}

	public List<Object[]> getRecordByIDAndRecordType(Integer id, 
			Integer recordType) {
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("id", id.toString())), 
					Occur.MUST);
			query.add(new TermQuery(new Term("record_type", 
					recordType.toString())), Occur.MUST);
			Hits hits = searcher.search(query);
			prglog.info("[PRG] " + query + ", found: " + hits.length());
			int docId;
			for (int i = 0; i < hits.length(); i++) {
				docId = hits.id(i);
				list.add(new Object[]{docId, 
						indexReader.document(docId, allFieldSelector)});
			}
		} catch (IOException e) {
			prglog.error("[PRG] " + e);
		}
		return list;
	}
	
	public int getHitCount(String queryString) {
		prglog.info("[PRG] " + queryString);
		Query query = parseQuery(queryString);
		return getHitCount(query);
	}

	public int getHitCount(Query query) {
		int count = 0;
		try {
			if(query == null) {
				count = searcher.maxDoc();
			} else {
				Hits hits = searcher.search(query);
				count = hits.length();
			}
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return count;
	}

	public Hits search(String queryString) {
		Query query = parseQuery(queryString);
		return search(query, null);
	}

	public Hits search(String queryString, Sort sort) {
		Query query = parseQuery(queryString);
		return search(query, sort);
	}

	public Hits search(Query query, Sort sort) {
		Hits hits = null;
		try {
			if(query == null) {
				query = parseQuery("id:*");
			}
			hits = searcher.search(query, sort);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return hits;
	}
	
	public Document getDoc(int i) {
		Document doc = null;
		try {
			doc = searcher.doc(i);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return doc;
	}

	public Document getDoc(int i, FieldSelector fieldSelector) {
		Document doc = null;
		try {
			doc = searcher.doc(i, fieldSelector);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return doc;
	}

	public int getMaxDoc() {
		int maxDoc = 0;
		try {
			maxDoc = searcher.maxDoc();
		} catch(IOException e){
			prglog.error("[PRG] " + e);
		}
		return maxDoc;
	}

	public Query parseQuery(String queryString) {
		Query query = null;
		if(!queryString.equals("")) {
			try {
				QueryParser parser = new QueryParser("id", new StandardAnalyzer());
				query = parser.parse(queryString);
			} catch(ParseException e) {
				prglog.error("[PRG] " + e);
			}
		}
		return query;
	}

	public String getEarliestDatestamp() {
		if(earliestDatestamp == null) {
			earliestDatestamp = showFirstTerm("modification_date");
		}
		return earliestDatestamp;
	}

	public String showFirstTerm(String field) {
		String firstTerm = null;
		try {
			if(indexReader == null) {
				indexReader = searcher.getIndexReader();
			}
			TermEnum te = indexReader.terms(new Term(field, ""));
			firstTerm = te.term().text();
		} catch (Exception e) {
			prglog.error("[PRG] " + e);
		}
		return firstTerm;
	}
	
	public FieldSelector getAllFieldSelector() {
		return allFieldSelector;
	}
}
