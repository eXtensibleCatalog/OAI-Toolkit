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
import java.util.BitSet;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.FieldSelector;
import org.apache.lucene.document.FieldSelectorResult;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.HitCollector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

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
    //private BitSet bits;
	
	// Make sure that the searcher is using an up-to-date index reader.
	// If it's not current, then we will miss out on recently committed changes
	private IndexSearcher getSearcher() {
		if (searcher == null) {
			createNewLuceneIndex();
		}
		try {				
			IndexReader ir = searcher.getIndexReader();
	        if (! ir.isCurrent()) {
	        	prglog.info("[PRG] " + "Lucene searcher's index reader is not current. Calling reopen().");
	        	IndexReader newir = ir.reopen();
	        	 if (newir != ir) {
	        	   // reader was reopened
	        	   prglog.info("[PRG] " + "Lucene searcher's index reader was successfully reopen()-ed.");
	        	   ir.close(); 
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
	private IndexReader getIndexReader() {
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
			Directory fsDir = FSDirectory.getDirectory(indexDir);
	
			IndexWriter writer = new IndexWriter(indexDir, new StandardAnalyzer());
			writer.close();
			
			// attempt once more to open index searcher and reader
			searcher = new IndexSearcher(fsDir);
			indexReader = IndexReader.open(indexDir);
			
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
           
			Hits hits = getSearcher().search(query);
			prglog.info("[PRG] " + query + ", found: " + hits.length());
			for (int i = 0; i < hits.length(); i++) {
				docId[0] = hits.id(i);
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
           
			Hits hits = getSearcher().search(query);
			prglog.info("[PRG] " + query + ", found: " + hits.length());
			int docId;
			for (int i = 0; i < hits.length(); i++) {
				docId = hits.id(i);
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
                
            getSearcher().search(query, new HitCollector() {
                 public void collect(int doc, float score) {
                     bits.set(doc);
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
				Hits hits = getSearcher().search(query);
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
			hits = getSearcher().search(query, sort);
		} catch(IOException e) {
			prglog.error("[PRG] " + e);
		}
		return hits;
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
}
