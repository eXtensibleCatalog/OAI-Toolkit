/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.db;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import junit.framework.TestCase;

public class LuceneReadWriteTestCase extends TestCase {
	private String luceneDirName  = "c:/doku/extensiblecatalog"
									+ "/OAIToolkit-0.2alpha/test_lucene_index";
	private File luceneDir = new File(luceneDirName);
	private Directory     dir        = null;
	private IndexWriter   writer     = null;
	private IndexSearcher searcher   = null;
	private IndexReader   reader     = null;
	private boolean       openAsNeed = false;
	private int           doCommit   = 0;
	private long          flushTime  = 0;
	private long          searcherTime = 0;
	private int           commitCounter = 0;
	
	private int max = 1000;
	
	public void test() throws IOException {
		dir = FSDirectory.getDirectory(luceneDir);
		
		long start = System.currentTimeMillis();
		for(int j=0; j<3; j++) {
			doCommit = j;
			System.out.println(j + ". round");
			long start2 = System.currentTimeMillis();
			for(int i=1; i<max; i++) {
				Document doc = new Document();
				doc.add(keyword("id", "" + i));
				doc.add(keyword("name", "" + (max-i)));
				//if(!doesExist(i)) {
					addDocument(doc);
				//}
			}
			commit();
			long total2 = (System.currentTimeMillis()-start2);
			System.out.println("took: " + total2);
			System.out.println("flushTime: " + flushTime 
					+ " (" + Math.ceil((double)flushTime*100/total2) + "%)");
			System.out.println("searcherTime: " + searcherTime
					+ " (" + Math.ceil((double)searcherTime*100/total2) + "%)");
			searcherTime = flushTime = 0;
		}
		long total = (System.currentTimeMillis()-start);
		System.out.println("total: " + total);
		optimize();
		File[] files = luceneDir.listFiles();
		for(File f : files) {
			boolean deleted = f.delete();
			if(!deleted) {
				System.out.println("Unable to delete file");
			}
		}
		boolean b = luceneDir.delete();
		System.out.println(b);
	}
	
	public void delete(int id) throws IOException {
		writer.deleteDocuments(new Term("id", Integer.toString(id)));
	}

	public boolean doesExist(int id) throws IOException {
		Query query = new TermQuery(new Term("id", Integer.toString(id)));
		return doesExist(query);
	}

	public boolean doesExist(Query query) throws IOException {
		if(reader == null) {
			if(!openSearcher()) {
				return false;
			}
		}
		Hits hits = searcher.search(query);
		boolean exists = false;
		if(hits.length() == 0) {
			exists = false;
		} else {
			exists = true;
		}
		return exists;
	}

	public Field keyword(String name, String content) {
		return new Field(name, content, Store.YES, Index.UN_TOKENIZED);
	}
	
	public void addDocument(Document doc) throws IOException {
		if(openAsNeed) {
			writer = new IndexWriter(dir, true, new StandardAnalyzer());
		} else {
			if(writer == null) {
				writer = new IndexWriter(luceneDir, new StandardAnalyzer());
				System.out.print('.');
			}
		}
		writer.addDocument(doc);
		if(doCommit == 1 || doCommit == 2) {
			commit();
		}
		if(openAsNeed) {
			writer.close();
		}
	}
	
	public void commit() throws IOException {
		if(openAsNeed) {
			writer = new IndexWriter(dir, true, new StandardAnalyzer());
		} else {
			if(writer == null) {
				writer = new IndexWriter(luceneDir, new StandardAnalyzer());
				System.out.print('.');
			}
		}
		if(doCommit == 1 || (doCommit == 2 && commitCounter == 9)) {
			long bf = System.currentTimeMillis();
			writer.flush();
			long bs = System.currentTimeMillis();
			flushTime += (bs-bf);
			closeSearcher();
			openSearcher();
			searcherTime += (System.currentTimeMillis()-bs);
		}
		commitCounter++;
		if(openAsNeed) {
			writer.close();
		}
	}

	public void optimize() throws IOException {
		if(openAsNeed) {
			writer = new IndexWriter(luceneDir, new StandardAnalyzer());
		} else {
			if(writer == null) {
				writer = new IndexWriter(luceneDir, new StandardAnalyzer());
			}
		}
		writer.optimize();
		System.out.println("docCount: " + writer.docCount());
		writer.close();
		closeSearcher();
	}
	
	private boolean openSearcher() {
		try {
			reader   = IndexReader.open(dir);
			searcher = new IndexSearcher(reader);
			return true;
		} catch(FileNotFoundException e) {
			//
		} catch(IOException e) {
			//
		}
		return false;
	}

	private void closeSearcher() {
		try {
			if(reader != null)
				reader.close();
			if(searcher != null)
				searcher.close();
		} catch(IOException e) {
			//
		}
	}
}
