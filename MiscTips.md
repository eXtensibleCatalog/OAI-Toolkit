# Tips, Tricks, Miscellany #

## "Invalid files" ##

There are times when you may encounter "Invalid files."  This situation seems to occur randomly and, thankfully, infrequently. We have not yet been able to find a solution to this problem, but we think it's related to the standard SAX Parser included in the Java VM.  Below is an example log entry containing an invalid file:

```
2011-10-12 17:04:41,766 [main] (Importer.java:808) INFO  - [LIB] Import statistics summary:  
created 12943660, updated: 0, skipped: 1, invalid: 2324, deleted: 0, bib: 5972074, auth: 0, holdings: 6971586 records. 
Invalid files: 1. It took 02:54:11.882. checkTime: 00:08:23.928. insertTime: 00:13:52.333. others: 02:31:55.621
```

Fortunately, the work-around is simple: re-run your load script (or convert/load script, or convert/modify/load script).  In most cases, these invalid files will get "picked up" and processed correctly the second time around.