/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.MilliSecFormatter;

import java.io.IOException;
import java.util.Random;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.FSDirectory;

/**
 * Wrapper class to managing Lucene index creation
 * @author kiru
 */
public class LuceneIndexMgr {

	private IndexWriter   writer;
	private IndexSearcher searcher;
	private IndexReader   reader;
	private Random        generator;
	private FSDirectory   indexDir;
	
	public LuceneIndexMgr(String _indexDir) {
		open(_indexDir);
		generator = new Random();
	}

	/**
	 * Open an index writer
	 * @param indexDir
	 */
	public void open(String _indexDir) {
		try {
			indexDir = FSDirectory.getDirectory(_indexDir);
			writer   = new IndexWriter(indexDir, new StandardAnalyzer());//, false);
			//writer.setRAMBufferSizeMB(256.0);
			writer.setRAMBufferSizeMB((double)
					(Runtime.getRuntime().maxMemory()/2.0) / (double)(1024*1024));
			writer.setMaxBufferedDocs(100000);
			writer.setMergeFactor(2000);
			openSearcher();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void openSearcher() throws IOException {
		reader   = IndexReader.open(indexDir);
		searcher = new IndexSearcher(reader); //reader
	}

	private void closeSearcher() throws IOException {
		reader.close();
		searcher.close();
	}

	public int getRandomId() throws IOException {
		int id;
		do {
			id = generator.nextInt();
			if(id < 0) {
				id *= -1;
			}
		} while(doesExist(id) == true);
		return id;
	}

	public boolean doesExist(int id) throws IOException {
		Query query = new TermQuery(new Term("id", Integer.toString(id)));
		return doesExist(query);
	}

	public boolean doesExist(String id) throws IOException {
		Query query = new TermQuery(new Term("id", id));
		return doesExist(query);
	}

	public boolean doesExist(RecordDTO searchData) throws IOException {
		return doesExist(getQuery(searchData));
	}

	public String getId(RecordDTO searchData) throws IOException {
		return getId(getQuery(searchData));
	}

	public Query getQuery(RecordDTO searchData) throws IOException {
		BooleanQuery query = new BooleanQuery();
		query.add((Query)new TermQuery(new Term("external_id", 
				searchData.getExternalId())), Occur.MUST);
		query.add((Query)new TermQuery(new Term("record_type", 
				searchData.getRecordType().toString())), Occur.MUST);
		return query;
	}

	public boolean doesExist(Query query) throws IOException {
		Hits hits = searcher.search(query);
		if(hits.length() == 0) {
			return false;
		} else {
			return true;
		}
	}

	public String getId(Query query) throws IOException {
		Hits hits = searcher.search(query);
		if(hits.length() == 1) {
			return hits.doc(0).get("id");//Integer.parseInt(hits.doc(0).get("id"));
		} else {
			return "-1";
		}
	}

	/**
	 * Close index
	 */
	public void close() {
		if (writer != null) {
			try {
				writer.close();
				closeSearcher();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Optimize index
	 */
	public void optimize() {
		if (writer != null) {
			try {
				writer.optimize();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Commit changes
	 */
	public void commit() {
		if (writer != null) {
			try {
				writer.flush();
				if(searcher != null) {
					closeSearcher();
				}
				openSearcher();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**  
	 * Add a documentum to the index
	 * @param doc The documentum to add
	 */
	public void addDoc(Document doc) {
		try {
			writer.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delDoc(int id) {
		try {
			if(IndexReader.isLocked(indexDir)) {
				IndexReader.unlock(indexDir);
			}
			reader.deleteDocument(id);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delDoc(String fieldName, String value) {
		try {
			writer.deleteDocuments(new Term(fieldName, value));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Return a text field (tokenized field)
	 * @param name The name of the field
	 * @param content The content of the field
	 * @return The Field object
	 */
	public Field text(String name, String content) {
		return new Field(name, content, Store.YES, Index.TOKENIZED);
	}

	/**
	 * Return a keyword field (untokenized field) 
	 * @param name The name of the field
	 * @param content The content of the field
	 * @return The Field object
	 */
	public Field keyword(String name, String content) {
		return new Field(name, content, Store.YES, Index.UN_TOKENIZED);
	}

	/**
	 * Return a field which wasn't indexed just stored
	 * @param name The name of the field
	 * @param content The content of the field
	 * @return The Field object
	 */
	public Field stored(String name, String content) {
		return new Field(name, content, Store.YES, Index.NO);
	}
	
	/**
	 * Put all id field values from the index to a list
	 * @param ids
	 */
	public void putAllIds(Set<String> ids) {
		try {
			TermEnum tenum = reader.terms();
			while(tenum.next()) {
	            if(tenum.term().field().equals("id")) {
	            	ids.add(tenum.term().text());
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
