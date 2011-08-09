/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.db;

import info.extensiblecatalog.OAIToolkit.oai.dataproviders.LuceneFacadeDataProvider;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.BitSet;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.document.FieldSelectorResult;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeFilter;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.util.Version;

/**
 * Class where all the search methods are defined to interact and
 * search from the Lucene index
 * @author shreyanshv
 */
public class LuceneSearcher {
	
    private static String programmer_log = "programmer";
    private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static final Logger logger = Logging.getLogger();

	private IndexSearcher searcher;
	private IndexReader indexReader;
		
	private String luceneDir;
	
	private String earliestDatestamp;
	//private FieldSelector xmlSelector;
	private FieldSelector allFieldSelector;
	private FieldSelector idFieldSelector;
    //private BitSet bits;
	
	// Make sure that the searcher is using an up-to-date index reader.
	// If it's not current, then we will miss out on recently committed changes
	public IndexSearcher getSearcher() {
		if (searcher == null) {
			createNewLuceneIndex();
		}
		try {				
			IndexReader ir = searcher.getIndexReader();
	        if (! ir.isCurrent()) {
	        	prglog.info("[PRG] " + "Lucene searcher's index reader is not current. Retrieving new IndexReader.");
	        	IndexReader newir = getIndexReader();
	        	 if (newir != ir) {
	        	   // reader was reopened
	        	   prglog.info("[PRG] " + "Lucene searcher's index reader was successfully reopen()-ed.");
	        	   searcher = new IndexSearcher(newir);
	        	 }
	        }

		} catch (java.io.FileNotFoundException fnfe) {
			System.out.println("lucene index files not found: " + fnfe);
			createNewLuceneIndex();
			
		} catch (Exception e) {
			prglog.error("[ERROR] " + "Failed to retrieve Lucene searcher's index reader.  Continuing to use original reader.");
		}
		return searcher;
	}

	// Make sure we are using an up-to-date index reader.
	// If it's not current, then we will miss out on recently committed changes
	public IndexReader getIndexReader() {
		if (indexReader == null) {
			createNewLuceneIndex();
		}
		try {	
	        if (! indexReader.isCurrent()) {
	        	prglog.info("[PRG] " + "Lucene index reader is not current. Calling reopen().");
	        	IndexReader newir = indexReader.reopen();
	        	 if (newir != indexReader) {
	        	   // reader was reopened
	        	   prglog.info("[PRG] " + "Lucene index reader was successfully reopen()-ed.");
	        	   indexReader.close(); 
	        	   indexReader = newir;
	        	 }
	        }

		} catch (java.io.FileNotFoundException fnfe) {
			System.out.println("lucene index files not found: " + fnfe);
			createNewLuceneIndex();
			
		} catch (Exception e) {
			prglog.error("[ERROR] " + "Failed to retrieve Lucene index reader.  Continuing to use original reader." + e);
		}
		return indexReader;
	}
	
	private void createNewLuceneIndex() {
		try {
			File indexDir = new File(luceneDir);
			SimpleFSDirectory fsDir = new SimpleFSDirectory(indexDir);
	
			IndexWriter writer = new IndexWriter(fsDir, new StandardAnalyzer(Version.LUCENE_30), IndexWriter.MaxFieldLength.UNLIMITED);
			writer.close();
			
			// attempt once more to open index searcher and reader
			indexReader = IndexReader.open(fsDir);
			searcher = new IndexSearcher(indexReader);
			
		} catch (IOException e) {
			System.out.println("Failed to create new lucene index: " + e);
		}

	}
	
	
	public LuceneSearcher(String luceneDir) {
		
		this.luceneDir = luceneDir;

		getSearcher();
		getIndexReader();
					
        //bits = new BitSet(indexReader.maxDoc());
            		
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
		
		idFieldSelector = new FieldSelector() {
			private static final long serialVersionUID = 1426724242925499003L;

			public FieldSelectorResult accept(String fieldName) {
				if (fieldName.equals("id")) {
					return FieldSelectorResult.LOAD;
				} else {
					return FieldSelectorResult.NO_LOAD;
				}
			};
		};

	}

    /**
     * Get the XML of the records, given its id and recordType
     * @param recordId
     * @param recordType
     * @return content (String)
     */

	public String getXmlOfRecord(Integer recordId, Integer recordType) {
		String content = null;
		try {
			Document doc = getSearcher().doc(recordId);
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
			Hits hits = getSearcher().search(query);
			prglog.info("getXmlOfRecord: " + recordId + ", " + recordType + "->" + hits.length());
			if(hits.length() == 1) {
				prglog.info("hits.id(0): " + hits.id(0));
				Document doc = getIndexReader().document(hits.id(0), xmlSelector);
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

    /**
     * Get the record from the ID passed to it
     * @param recordId
     * @return Document
     */
	public Document getRecordByID(Integer recordId) {
		Document doc = null;
		try {
			if(recordId >= 0 && recordId <= getSearcher().getIndexReader().numDocs()) {
				doc = getSearcher().doc(recordId);
			}
			//Query query = new TermQuery(new Term("id", recordId.toString()));
			//Hits hits = getSearcher().search(query);
			//doc = hits.doc(0);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
			e.printStackTrace();
		}
		return doc;
	}

    /**
     * Get the record from the Xc OAI ID passed to it
     * @param xcOaiId (String)
     * @param docId An Integer array (of size 1) which will be set to the document id number of the record
     * @return Document
     */
	public Document getRecordByXcOaiID(String xcOaiId, Integer[] docId) {
		Document doc = null;

        try {
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("xc_oaiid", xcOaiId)), 
					Occur.MUST);
           
			TopDocs hits = getSearcher().search(query, Integer.MAX_VALUE);
			prglog.info("[PRG] " + query + ", found: " + hits.scoreDocs.length);
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				docId[0] = hits.scoreDocs[i].doc;
				doc = getIndexReader().document(docId[0], allFieldSelector);
			} 
		} catch (IOException e) {
			prglog.error("[PRG] " + e);
		}
				
		return doc;
	}



    /**
     * Getting the record by querying the Lucene having the ID and the recordtype passed to it
     * @param recordId
     * @param recordType
     * @return list of objects
     */
	public List<Object[]> getRecordByIDAndRecordType(Integer id,
			Integer recordType) {
		List<Object[]> list = new ArrayList<Object[]>();
        //final List<Integer> ids = new ArrayList<Integer>();
        try {
			BooleanQuery query = new BooleanQuery();
			query.add(new TermQuery(new Term("id", id.toString())), 
					Occur.MUST);
			query.add(new TermQuery(new Term("record_type", 
					recordType.toString())), Occur.MUST);

            /*
            getSearcher().search(query, new HitCollector() {
             public void collect(int doc, float score) {
                bits.set(doc);
                ids.add(doc);
             }
             });

             for (int i=0; i< ids.size(); i++) {
                 list.add(getIndexReader().document(ids.get(i)));
             }
            */
           
			TopDocs hits = getSearcher().search(query, Integer.MAX_VALUE);
			prglog.info("[PRG] " + query + ", found: " + hits.scoreDocs.length);
			int docId;
			for (int i = 0; i < hits.scoreDocs.length; i++) {
				docId = hits.scoreDocs[i].doc;
				list.add(new Object[]{docId, 
						getIndexReader().document(docId, allFieldSelector)});
			} 
		} catch (IOException e) {
			prglog.error("[PRG] " + e);
		}
		return list;
	}

    /**
     * Gets the Hit count of the query string passed.
     * @param queryString
     */
	public int getHitCount(String queryString) {
		prglog.info("[PRG] " + queryString);
		Query query = parseQuery(queryString);
		return getHitCount(query);
	}

    /**
     * Given the query, this is a HitCollector implementation, where it
     * returns the vector of bits which got a hit by the query.
     * @param query
     * @param sort
     */
    public BitSet searchForBits(Query query, Sort sort) {
        try {
            //indexReader = IndexReader.open(indexDir);

        final BitSet bits = new BitSet(getIndexReader().maxDoc());                           
            getSearcher().search(query, new Collector() {
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
            	   }
            	 
            	   public void setNextReader(IndexReader reader, int docBase) {
            	     this.docBase = docBase;
            	   }
            	 });            
            
                 return bits;
      } catch (IOException e) {
        prglog.error("[PRG] " + e);
        return null;
        }
        
    }
       
	public int getHitCount(Query query) {
		int count = 0;
		try {
			if(query == null) {
				count = getSearcher().maxDoc();
			} else {
				TopDocs hits = getSearcher().search(query, Integer.MAX_VALUE);
				count = hits.scoreDocs.length;
			}
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return count;
	}

	public TopDocs search(String queryString) {
		Query query = parseQuery(queryString);
		return search(query, null);
	}

	public TopDocs search(String queryString, Sort sort) {
		Query query = parseQuery(queryString);
		return search(query, sort);
	}

	public TopDocs search(Query query, Sort sort) {
		TopDocs hits = null;
		try {
			if(query == null) {
				query = parseQuery("id:*");
			}
			if (sort == null) {
				hits = getSearcher().search(query, Integer.MAX_VALUE);				
			} else {
				hits = getSearcher().search(query, null, Integer.MAX_VALUE, sort);
			}
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return hits;
	}

	public TopDocs search(String queryString, Sort sort, int numrecs) throws IOException {
		Query query = parseQuery(queryString);
		return getSearcher().search(query, null, numrecs, sort);
	}

	
	public TopDocs searchRange(String queryString, String rangeField, Integer from, Integer to, boolean includeFrom, boolean includeTo, Sort sort, int numrecs) throws IOException {
		Query query = parseQuery(queryString);
		NumericRangeFilter<Integer> filter = NumericRangeFilter.newIntRange(rangeField, from, to, includeFrom, includeTo);
		return getSearcher().search(query, filter, numrecs, sort);
	}

	
	public Document getDoc(int i) {
		Document doc = null;
		try {
            //doc = getIndexReader().document(i);
			doc = getSearcher().doc(i);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return doc;
	}

	public Document getDoc(int i, FieldSelector fieldSelector) {
		Document doc = null;
		try {
			doc = getSearcher().doc(i, fieldSelector);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return doc;
	}

    public int getMaxDoc() {
		int maxDoc = 0;
		try {
			maxDoc = getSearcher().maxDoc();
		} catch(IOException e){
			prglog.error("[PRG] " + e);
		}
		return maxDoc;
	}

	public Query parseQuery(String queryString) {
		Query query = null;
		if(!queryString.equals("")) {
			try {
				QueryParser parser = new QueryParser(Version.LUCENE_30, "id", new StandardAnalyzer(Version.LUCENE_30));
				query = parser.parse(queryString);
			} catch(ParseException e) {
				prglog.error("[PRG] " + e);
			}
		}
		return query;
	}

	public String getLatestDatestamp() {
		String latest = null;
		try {
			IndexSearcher searcher = getSearcher();
            Document doc = searcher.doc(searcher.maxDoc() - 1);			
			Field[] flds = doc.getFields("modification_date");
			// this field is stored in order (if it weren't we'd have to sort them first)
			// most recent is at the top of the list
			latest = flds[0].stringValue();
			prglog.info("getLatestDatestamp:" + latest);
			
			// It's extremely possible that the max doc id is NOT the most recent record
			// we just need to narrow our range search to something reasonable
			Sort sort = new Sort(new SortField("modification_date", SortField.STRING, true));
			String queryString = "+modification_date:[\"" + latest + "\" TO \"" 
			+ TextUtil.utcToMysqlTimestamp(TextUtil.nowInUTC()) + "\"]";
			prglog.info("queryString for latest datestamp:" + queryString);
			TopDocs hits = search(queryString , sort, 1);
			
			if (hits.scoreDocs.length > 0) {
				int id = hits.scoreDocs[0].doc;
				doc = searcher.doc(id);
				flds = doc.getFields("modification_date");
				latest = flds[0].stringValue();
			}			
			prglog.info("getLatestDatestamp pass two:" + latest);

		} catch (Exception e) {		
			prglog.error("[PRG] " + e);
		}
		return latest;
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
				indexReader = getSearcher().getIndexReader();
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
	
	public void dumpIds() {
		TopDocs hits = search("is_deleted:false");
		int docId;
		for (int i = 0; i < hits.scoreDocs.length; i++) {
			try {
			docId = hits.scoreDocs[i].doc;
			String theId = getIndexReader().document(docId, idFieldSelector).get("id");
			System.out.println(theId);
			} catch (IOException io) {
				
			}
		} 		
	}

}
