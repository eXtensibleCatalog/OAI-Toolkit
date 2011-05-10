/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package test.extensiblecatalog.OAIToolkit.oai;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

import junit.framework.TestCase;

public class DocumentReadingTimeDelayTest extends TestCase {

	private IndexReader indexReader;
	static List<Integer> ids = new ArrayList<Integer>();
	
	public DocumentReadingTimeDelayTest() {
		try {
			indexReader = IndexReader.open(new SimpleFSDirectory( new File("c:/lucene_MST/index")));
		} catch (CorruptIndexException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testTimeDelay() throws Exception {
		Searcher searcher = new IndexSearcher(indexReader);
		System.out.println(indexReader.maxDoc());
		final BitSet bits = new BitSet(indexReader.maxDoc());
		
		System.out.println(ids.size());
		QueryParser parser = new QueryParser(Version.LUCENE_30, "id", new StandardAnalyzer(Version.LUCENE_30));
		Query query = parser.parse("+is_deleted:(true OR false)");
		System.out.println(query);
		long start = System.currentTimeMillis();
		
		searcher.search(query, new Collector() {
			   private int docBase;
			 
			   // ignore scorer
			   public void setScorer(Scorer scorer) {
			   }

			   // accept docs out of order (for a BitSet it doesn't matter)
			   public boolean acceptsDocsOutOfOrder() {
			     return true;
			   }
			 
			   public void collect(int doc) {
			     bits.set(doc + docBase);
			     ids.add(doc + docBase);
			   }
			 
			   public void setNextReader(IndexReader reader, int docBase) {
			     this.docBase = docBase;
			   }
			 });
			 
		System.out.println(System.currentTimeMillis()-start);
		System.out.println("ok");
		//System.out.println(bits);
		BitSet range = bits.get(5000000, 5001000);
		for(int i=0; i<range.length(); i++) {
			System.out.print(range.get(i));
		}
		for(int i=5000000; i<5001000; i++) {
			System.out.println(ids.get(i));
		}
		System.out.println(System.currentTimeMillis()-start);
	}
}
