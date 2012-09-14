/**
  * Copyright (c) 2009 University of Rochester
  *
  * This program is free software; you can redistribute it and/or modify it under the terms of the MIT/X11 license. The text of the  
  * license can be found at http://www.opensource.org/licenses/mit-license.php and copy of the license can be found on the project
  * website http://www.extensiblecatalog.org/. 
  *
  */

package info.extensiblecatalog.OAIToolkit.oai.dataproviders;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.util.Version;

import info.extensiblecatalog.OAIToolkit.DTOs.DataTransferObject;
import info.extensiblecatalog.OAIToolkit.DTOs.RecordDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.ResumptionTokenDTO;
import info.extensiblecatalog.OAIToolkit.DTOs.SetToRecordDTO;
import info.extensiblecatalog.OAIToolkit.db.ResumptionTokensMgr;
import info.extensiblecatalog.OAIToolkit.utils.ApplInfo;
import info.extensiblecatalog.OAIToolkit.utils.Logging;
import info.extensiblecatalog.OAIToolkit.utils.TextUtil;

/**
 * 
 * @author Peter Kiraly
 *
 */
public class LuceneFacadeDataProvider extends BasicFacadeDataProvider
		implements FacadeDataProvider {

	/** The programmer's log object */
        private static String programmer_log = "programmer";
        private static final Logger prglog = Logging.getLogger(programmer_log);
	//private static Logger logger = Logging.getLogger();

	/** Manager of resumption_token records */
	private static ResumptionTokensMgr tokenMgr = new ResumptionTokensMgr();


	private String queryString;
	private TopDocs   hits;
	private int    currentRecord;
	private int    lastRecord;
	private long   getIdTime      = 0;
	private long   doc2RecordTime = 0;
	private long   getDocTime     = 0;
	
    // we want to keep a full harvest in memory for fast initial harvesting (first/initial harvest since server started)
    static private BitSet cachedFullHarvestIds = null;
    static private String cachedFullHarvestExpiry = null;
    static private Date cachedFullHarvestEarliestDate = null;
    static private IndexSearcher cachedFullHarvestIndexSearcher = null;
    static private Set<String> cachedFullHarvestTokenIds = new HashSet<String>();
    // vars used to handle cachedFullHarvest
    private boolean cachedFullHarvest = false;
    private int    tempIndex;

	synchronized static public void initializeCachedFullHarvest() {
		if (cachedFullHarvestIds == null) {
			
			IndexReader indexReader;
			try {
				indexReader = ApplInfo.luceneSearcher.getIndexReader().clone(true);
			} catch (CorruptIndexException e1) {
				prglog.error("[PRG] " + e1);
				return;
			} catch (IOException e1) {
				prglog.error("[PRG] " + e1);
				return;
			}
			cachedFullHarvestIndexSearcher = new IndexSearcher(indexReader);
			
			try {
				cachedFullHarvestEarliestDate = TextUtil.luceneToDate(
						ApplInfo.luceneSearcher.getEarliestDatestamp());
				cachedFullHarvestExpiry = ApplInfo.luceneSearcher.getLatestDatestamp();					
			} catch (ParseException pe) {
				prglog.error("[PRG] " + pe);
				return;
			}

	    	BooleanQuery query = new BooleanQuery();

	    	// don't include deleted records
			query.add((Query)new TermQuery(new Term("is_deleted", 
					"false")), Occur.MUST);

			// do we need to filter based on orgCode?
			if (ApplInfo.getOrgCodeFilter() != null) {
				query.add((Query)new TermQuery(new Term("repository_code", 
						ApplInfo.getOrgCodeFilter())), Occur.MUST);
			}
				
	        try {
	        	cachedFullHarvestIds = new BitSet(indexReader.maxDoc());                           
	        	cachedFullHarvestIndexSearcher.search(query, new Collector() {
	            	   private int docBase;
	            	 
	            	   // ignore scorer
	            	   public void setScorer(Scorer scorer) {
	            	   }

	            	   // accept docs out of order (for a BitSet it doesn't matter)
	            	   public boolean acceptsDocsOutOfOrder() {
	            	     return true;
	            	   }
	            	 
	            	   public void collect(int doc) {
	            		   cachedFullHarvestIds.set(doc + docBase);
	            	   }
	            	 
	            	   public void setNextReader(IndexReader reader, int docBase) {
	            	     this.docBase = docBase;
	            	   }
	            	 });   
	        	
	        	prglog.info("[PRG] Initial Full Harvest Cache created successfully.");

	      } catch (IOException e) {
	        prglog.error("[PRG] " + e);
	        cachedFullHarvestIds = null;
	        return;
	      }			
					
		}

	}
	
	public String getEarliestDatestamp() {
		try {
			return TextUtil.timestampToUTC(
				TextUtil.luceneToTimestamp(
					ApplInfo.luceneSearcher.getEarliestDatestamp()));
		} catch(Exception e) {
                        prglog.error("[PRG] " + e);
			return "";
		}
	}

	public List<DataTransferObject> getRecord(String xcOaiId) {
		List<DataTransferObject> list = new ArrayList<DataTransferObject>();

		Integer docId[] = new Integer[1];
		Document doc = ApplInfo.luceneSearcher.getRecordByXcOaiID(xcOaiId, docId);
		if(doc != null) {
			// make sure this record is part of the orgCode subset!
			final String orgCode = ApplInfo.getOrgCodeFilter();
			if (orgCode != null) {
				if (! doc.get("repository_code").equals(orgCode)) {
					return list;
				}
			}
			list.add(doc2RecordDTO(doc, docId[0]));
		}

		return list;
	}
	
	public List<DataTransferObject> getRecord(Integer id, Integer recordType, 
			List<String> filter) { 
		prglog.info("[PRG] id: " + id + ", recordType: " + recordType);
		List<DataTransferObject> list = new ArrayList<DataTransferObject>();

		Document doc = ApplInfo.luceneSearcher.getRecordByID(id);
		if(doc != null) {
			// make sure this record is part of the orgCode subset!
			final String orgCode = ApplInfo.getOrgCodeFilter();
			if (orgCode != null) {
				if (! doc.get("repository_code").equals(orgCode)) {
					return list;
				}
			}
			list.add(doc2RecordDTO(doc, id));
		}

		/*
		List<Object[]> docs = ApplInfo.luceneSearcher
			.getRecordByIDAndRecordType(id, recordType);
		for(int i=0; i<docs.size(); i++) {
			Object[] obj = docs.get(i);
			list.add(doc2RecordDTO((Document)obj[1], (Integer)obj[0]));
		}
		*/
		return list;
	}
	
	public void selectRecords() {
		if (cachedFullHarvest) {
			selectRecordsCachedFullHarvest();
			return;
		}
		lastRecord = recordLimit;
		if(lastRecord > recordLimit) {
			lastRecord = recordLimit;
		}
		if (lastRecord > hits.scoreDocs.length) {
			lastRecord = hits.scoreDocs.length;
		}
		currentRecord = 0; // count each iteration		
		
		getIdTime      = 0;
		doc2RecordTime = 0;
		getDocTime     = 0;
	}
	
	public void selectRecordsCachedFullHarvest() {
        lastRecord = offset + recordLimit;
		if(lastRecord > cachedFullHarvestIds.cardinality()) {
			lastRecord = cachedFullHarvestIds.cardinality();
		}
		currentRecord = offset; // count each iteration
		
		int NthBit = 0;
		int n = offset;
		// Is the first bit set?  If not, then we need to account for the fact we aren't starting at N=0.
		if (! cachedFullHarvestIds.get(0)) {
			n++;
		}
		for (; n > 0; n--) {
			NthBit = cachedFullHarvestIds.nextSetBit(NthBit + 1);
		}
		tempIndex = NthBit; // keep track of the current bit (not always incremental!)

		getIdTime      = 0;
		doc2RecordTime = 0;
		getDocTime     = 0;
	}
	
	public boolean hasNextRecord() {
		return currentRecord < lastRecord;
	}
	
	public boolean hasMoreRecords() {
		if (cachedFullHarvest) {
			return hasMoreRecordsCachedFullHarvest();			
		}
		return hits.scoreDocs.length > recordLimit;
	}
    
	public boolean hasMoreRecordsCachedFullHarvest() {
		return cachedFullHarvestIds.cardinality() > lastRecord;
	}


	public DataTransferObject nextRecord() {
		if (cachedFullHarvest) {
			return nextRecordCachedFullHarvest();
		}
		RecordDTO recordDTO = null;
		int id;
        long t1 = System.currentTimeMillis();
		long t2 = 0;
		long t3 = 0;
		try {
			t2 = System.currentTimeMillis();
			getIdTime += (t2-t1);
            id = hits.scoreDocs[currentRecord].doc;
            Document doc = ApplInfo.luceneSearcher.getDoc(id);
			t3 = System.currentTimeMillis();
			getDocTime += (t3-t2);
			recordDTO = doc2RecordDTO(doc, id);
			doc2RecordTime += (System.currentTimeMillis()-t3);
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
		}
        currentRecord++;
		return recordDTO;
	}
	
	public DataTransferObject nextRecordCachedFullHarvest() {
		RecordDTO recordDTO = null;
		int id;
        long t1 = System.currentTimeMillis();
		long t2 = 0;
		long t3 = 0;
		try {
			t2 = System.currentTimeMillis();
			getIdTime += (t2-t1);
            id = cachedFullHarvestIds.nextSetBit(tempIndex);
            Document doc = cachedFullHarvestIndexSearcher.doc(id);
            t3 = System.currentTimeMillis();
			getDocTime += (t3-t2);
            tempIndex = id + 1;
			recordDTO = doc2RecordDTO(doc, id);
			doc2RecordTime += (System.currentTimeMillis()-t3);
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
		}
        currentRecord++;
		return recordDTO;
	}


	
	public List<DataTransferObject> getSetsOfRecord(Integer recordId) {
		Document doc = ApplInfo.luceneSearcher.getRecordByID(recordId);
		List<DataTransferObject> sets = new ArrayList<DataTransferObject>();
		sets.add(doc2SetToRecordDTO(doc, recordId));
		return sets;
	}

	public List<DataTransferObject> getSetsOfRecord(Integer recordId, Integer recordType) {
		List<Object[]> docs = ApplInfo.luceneSearcher.getRecordByIDAndRecordType(recordId, recordType);
		Object[] pair = docs.get(0);
		prglog.info("[PRG] docId: " + pair[0]);
		Document doc = (Document)pair[1];
        //Document doc = docs.get(0);
		prglog.info("[PRG] doc: " + doc);
		List<DataTransferObject> sets = new ArrayList<DataTransferObject>();
		sets.add(doc2SetToRecordDTO(doc, recordId));
		return sets;
	}

	public String getXmlOfRecord(Integer recordId, Integer recordType) {
		return ApplInfo.luceneSearcher.getXmlOfRecord(recordId, recordType);
	}
	
	public int prepareQuery() {
		if(null != tokenId) {
			ResumptionTokenDTO tokenDTO = getSQLsFromResumptionToken(tokenId);
			if(tokenDTO == null){
				badResumptionTokenError = true;
			} else {
				queryString = tokenDTO.getQuery();
				metadataPrefix = tokenDTO.getMetadataPrefix();
			}
			if (cachedFullHarvestTokenIds.contains(tokenId)) {
				cachedFullHarvest = true;
			} else if (initialHarvest == 1) {
				// Uh-oh.  This harvester using this resumption token had used the cached full harvest prior to this server's restart
				// This means it's STALE.  We need to throw an exception here.
				//TODO: throw invalid resumption token error
				prglog.warn("[PRG] A prior harvester is attempting to harvest via STALE (no longer viable) cached full harvest resumptionToken.");
				return -1;
			}
		} else {
			extractQueriesFromParameters(from, until, set);
			if(0 >= queryString.length()){
				prglog.error("[PRG] query string is null");
			}

			// Can we use the cached full harvest? (fast!)
			initializeCachedFullHarvest();						
			if (cachedFullHarvestIds == null) {
				
				prglog.warn("[PRG] The cached full harvest was not created for some reason (???)");		
			
			} else {
							
				if (set == null) {
					boolean fromIsTooRecent = true;
					boolean untilIsTooRecent = true;
					boolean untilIsTooOld = true;
					if (until == null) {
						until = TextUtil.nowInUTC();
					}

					try {
						String queryString = "+modification_date:{\"" + cachedFullHarvestExpiry + "\" TO \"" 
							+ TextUtil.utcToMysqlTimestamp(until) + "\"}";
						//prglog.info("testing if untilIsTooRecent, queryString:" + queryString);
						TopDocs h = ApplInfo.luceneSearcher.search(queryString);
						if (h.totalHits < 1)
							untilIsTooRecent = false;
								
						Date uts = TextUtil.utcToDate(until); 						
						Date lts = TextUtil.luceneToDate(cachedFullHarvestExpiry);
						//prglog.info("testing if untilTimestamp:" + uts + " is more recent than the oldest record:" + lts);
						if (uts.after(lts)) {
							untilIsTooOld = false;
						}
					} catch (ParseException pe) {
						prglog.error("[PRG] " + pe);						
					}

					if (from == null) {
						fromIsTooRecent = false;
					} else {
						try {							
							Date fts = TextUtil.utcToDate(from);
							if (fts.before(cachedFullHarvestEarliestDate))
								fromIsTooRecent = false;
							//prglog.info("testing if fromTimestamp:" + fts + " is before oldest created rec:" + cachedFullHarvestEarliestDate);							
						} catch (ParseException pe) {
							prglog.error("[PRG] " + pe);
						}
					}
					if (!fromIsTooRecent && !untilIsTooRecent && !untilIsTooOld) {
						cachedFullHarvest = true;
					}
					prglog.info("fromIsTooRecent:" + fromIsTooRecent + " untilIsTooRecent:" + untilIsTooRecent + " untilIsTooOld:" + untilIsTooOld);				
					
				}
			}		
		}
						
		if (cachedFullHarvest) {
			
			prglog.info("[PRG] We are using the cached full harvest for extra speed! (That's good!)");

		// for all others, we perform the search each time
		} else {
			
			prglog.info("[PRG] We are not using the cached full harvest. (Standard query.)");
			
			Sort sort = new Sort(new SortField("xc_id", SortField.INT));			
			try {
				// query recordLimit+1 (one extra) so that way we'll know if we're done with our list
				if (lastRecordRead > 0) {
					String from = String.format("%d", lastRecordRead);
					hits = ApplInfo.luceneSearcher.searchRange(queryString, "xc_id", Integer.valueOf(from), null, false, false, sort, recordLimit+1);
				} else {
						hits = ApplInfo.luceneSearcher.search(queryString, sort, recordLimit+1);
				}
			} catch (Exception ex) {
				hits = null;
			}
		}
		
		return cachedFullHarvest ? 1 : 0;

	}

    public int getTotalRecordCount() {    	 
    	if (cachedFullHarvest) {
    		return getTotalRecordCountCachedFullHarvest();
    	}
	    	Sort sort = null;
	    	Query query = null;
	    	QueryParser parser = new QueryParser(Version.LUCENE_30, "id", new StandardAnalyzer(Version.LUCENE_30));
			try {
				query = parser.parse(queryString);
			} catch (org.apache.lucene.queryParser.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
	        BitSet ids = ApplInfo.luceneSearcher.searchForBits(query, sort);
	        return ids.cardinality();
	}
  
    public int getTotalRecordCountCachedFullHarvest() {    	   	
        return cachedFullHarvestIds.cardinality();
    }

	
	public String getMetadataPrefix() {
		if(metadataPrefix != null) {
			return metadataPrefix; 
		} else if(tokenId != null) {
			ResumptionTokenDTO tokenDTO = getSQLsFromResumptionToken(tokenId);
			if(tokenDTO != null) {
				metadataPrefix = tokenDTO.getMetadataPrefix();
				return metadataPrefix;
			}
		}
		return null;
	}

	private ResumptionTokenDTO getSQLsFromResumptionToken(String resumptionToken) {

		ResumptionTokenDTO tokenDTO = new ResumptionTokenDTO();
		tokenDTO.setId(Integer.valueOf(resumptionToken));
		try {
			List<DataTransferObject> tokens = tokenMgr.get(tokenDTO);
			if(null != tokens) {
				prglog.info("[PRG] tokens.size: " + tokens.size());
				return (ResumptionTokenDTO)tokens.get(0);
			} else {
				prglog.info("[PRG] token not found");
				return null;
			}
		} catch(Exception e) {
			prglog.error("[PRG] " + e);
			e.printStackTrace();
			return null;
		}
	}

	private void extractQueriesFromParameters(String from, String until, 
			String set) {
		StringBuffer queryBuffer = new StringBuffer();
		if (null == from) {
			// if this is a clean harvest, there is no need to serve
			// deleted records
			queryBuffer.append("+is_deleted:false");				
		}

		// if until is not set, we set it implicitly to "now"
		try {
			if(null == until) {
	            until = TextUtil.utcToMysqlTimestamp(TextUtil.nowInUTC());
			} else {
				until = TextUtil.utcToMysqlTimestamp(until);
			}
		} catch (ParseException e) {
			prglog.error("[PRG]" + e);
			return;
		}
			
		if(null != from || null != until) {
			prglog.info("[PRG] " + from + ", " + until);
			if(null == from) {
				from = ApplInfo.luceneSearcher.showFirstTerm("modification_date");
			} else {
				try {
					from = TextUtil.utcToMysqlTimestamp(from);
				} catch (ParseException e) {
					prglog.error("[PRG]" + e);
					return;
				}
			}
			prglog.info("[PRG] " + from + ", " + until);
			
			if (queryBuffer.length() > 0) {
				queryBuffer.append(" AND ");
			}

			queryBuffer.append("+modification_date:[\"" + from + "\" TO \"" 
					+ until + "\"]");
		}
		if(null != set) {
			// checking the set's existence
			if(ApplInfo.setIdsByName.containsKey(set)) {
				int setId = ApplInfo.setIdsByName.get(set);
				if (queryBuffer.length() > 0) {
					queryBuffer.append(" AND ");
				}
				queryBuffer.append("+set:" + setId);
			}
		}
		
		// do we need to filter by orgCode?
		if (ApplInfo.getOrgCodeFilter() != null) {
			queryBuffer.append(" AND +repository_code:\"" + ApplInfo.getOrgCodeFilter() + "\"");					
		}

		queryString = queryBuffer.toString();
		prglog.info("[PRG] " + queryString);
	}
	
	public String storeResumptionToken() {
		ResumptionTokenDTO tokenDTO = new ResumptionTokenDTO();
		tokenDTO.setQuery(queryString);
		tokenDTO.setQueryForCount("");
		tokenDTO.setMetadataPrefix(metadataPrefix);
        tokenDTO.setCreationDate(new Timestamp(new Date().getTime()));

        try {
            List<Integer> intids = tokenMgr.insert(tokenDTO);
            prglog.info("intids.get(0)" + intids.get(0));
			String resumptionToken = String.valueOf(intids.get(0));
            prglog.info("Resumption Token: " + resumptionToken);
            // we need to keep track of cached harvests based on resumption token
        	if (cachedFullHarvest) {
        		cachedFullHarvestTokenIds.add(resumptionToken);
        	}
            
            return resumptionToken;
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	private RecordDTO doc2RecordDTO(Document doc, int id) {
		RecordDTO mainData = new RecordDTO(id);
		try {
			if(doc.get("creation_date") != null) {
				mainData.setCreationDate(TextUtil.luceneToTimestamp(
					doc.get("creation_date")));
			}
			if(doc.get("modification_date") != null) {
				mainData.setModificationDate(TextUtil.luceneToTimestamp(
					doc.get("modification_date")));
			}
		} catch(ParseException e) {
			prglog.error("[PRG] " + e);
		}
		mainData.setExternalId(doc.get("external_id"));
        mainData.setXcOaiId(doc.get("xc_oaiid"));
        mainData.setXcId(Integer.parseInt(doc.get("xc_id")));
		mainData.setIsDeleted(Boolean.valueOf(doc.get("is_deleted")));
		mainData.setRecordType(Integer.parseInt(doc.get("record_type")));
		return mainData;
	}
	
	private SetToRecordDTO doc2SetToRecordDTO(Document doc, Integer recordId) {
		SetToRecordDTO setsToRecordDTO = new SetToRecordDTO();
		setsToRecordDTO.setRecordId(recordId.intValue());
		setsToRecordDTO.setSetId(Integer.parseInt(doc.get("set")));
		return setsToRecordDTO;
	}

	public long getDoc2RecordTime() {
		return doc2RecordTime;
	}

	public long getIdTime() {
		return getIdTime;
	}

	public long getDocTime() {
		return getDocTime;
	}

}
