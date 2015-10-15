### Release 1.0.11 ###

  * http://extensiblecatalog.lib.rochester.edu:8080/browse/OAI-50

  * http://extensiblecatalog.lib.rochester.edu:8080/browse/OAI-51

### Release 1.0.10 ###

  * Allow a single OAI repo the ability to be harvested for a subset of its records based on organization code (i.e., the 003 MARC field or "repository\_code"):

> http://extensiblecatalog.lib.rochester.edu:8080/browse/OAI-49

### Release 1.0.9 ###

  * Simple, but very important fix that solves an issue that may cause OAI server to fail to read an updated index:

> http://extensiblecatalog.lib.rochester.edu:8080/browse/OAI-31

### Release 1.0.8 ###

  * Patched marc4j so that it will read Directory information for field offsets

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=64

### Release 1.0.7 ###

  * Really fix the Zulu from/until parameter issue this time ;-)

### Release 1.0.6 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=87

### Release 1.0.5 ###

  * One-liner fix to handle Zulu from/until parameters properly

### Release 1.0.4 ###

  * The full repository cache introduced in 1.0.3 wasn't getting triggered properly.  Fixed some date processing bugs.  Fixed the getLatestDatestamp method in Lucene's getLatestDatestamp method which is critical in determining "cached-ness."

### Release 1.0.3 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=21

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=85

  * Created a cache of the full repository which will enable very fast initial harvests (~ 6X faster)

  * Removed unnecessary "maxSizeInBytes" properties in OAIToolkit.server.properties

### Release 1.0.2 ###

  * Fixed a bug that was introduced when upgrading to Lucene 3.0.3 which made it possible for duplicate IDs to get imported into the index.

  * Packaged Xerces version 2.11.0 which seems to be more robust than the current stock version (2.6.2 in my case) included in the Sun JDK

  * Edited sample scripts to use newer version of Xerces

  * Removed unnecessary -stats\_lucene\_dir parameter which was used to specify the lucene index directory for -lucene\_statistics and -lucene\_dump\_ids operations; it now uses the standard -lucene\_index parameter instead

### Release 1.0.1 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=41

  * Fix "-lucene\_statistics" which was broken in release 1.0.0 (during Lucene 2.3.2 to 3.0.3 upgrade)

### Release 1.0.0 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=80

  * Now supports Lucene data storage option only (mysql or mixed no longer supported)

  * Upgraded from Lucene 2.3.2 to Lucene 3.0.3

### Release 0.6.11 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=78

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=79

### Release 0.6.10 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=74

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=76

### Release 0.6.9 ###

  * http://code.google.com/p/xcoaitoolkit/issues/detail?id=73

### Release 0.6.8 ###

  * Version 0.6.8, released January 5, 2011 represents a fairly “stable” version.  While a few lower priority enhancements/bugs remain in the issue list, none are serious and we are deferring work on them in lieu of higher priority work elsewhere.
  * **Highlights of some of the improvements:**
    * Better throughput/higher success rate at processing records – We have been able to improve the way we detect bad records (e.g. bad MARC) within a batch/file set of records so that we can now reject the bad record instead of rejecting the block of records.
    * Scalable to larger data sets – previously we were caching the IDs of records to determine if that record is new or an updated record.  This proved very problematic for larger record count sizes (e.g. 5 million and up).  We are now able to consult the Lucene index directly to make this determination with better results.
    * Better accounting of records – as we expanded our testing to larger data sets and data from multiple institutions we started to find that some records were not being accounted for.  With the enhancements and testing we have done we are able to account for all records as either successfully processed or as errors.
    * Miscellaneous bug fixes – which can be found at the Google code list.