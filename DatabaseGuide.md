# Database Guide (DRAFT) #

## Introduction ##

### Database Tables ###

The default name for the database is "extensiblecatalog." Since the OAI Toolkit refers to the database through property files (OAIToolkit.db.properties in the importer and in the server), you can change the database name if you refresh the updated name in these property files. We recall your attention to the fact that you can choose different metadata storage strategies. Your application will use all these tables if you choose the “MySQL” strategy. If you choose “mixed,” the xmls table will be empty. If you choose “Lucene,” all except the **resumption\_token** table will be empty.

![http://extensiblecatalog.org/doc/OAI/OAIWikiPics/Database%20tables.jpg](http://extensiblecatalog.org/doc/OAI/OAIWikiPics/Database%20tables.jpg)

### Records Table ###

The **records** table is the primary table of the OAI Toolkit database, and it stores most of the metadata of the incoming records (in this version only MARCXML is supported). The fields in the table and their descriptions are as follows:

|record\_id|A system generated identifier created by incrementing the identifier of each successive record|
|:---------|:---------------------------------------------------------------------------------------------|
|external\_id|The ID of the MARC record from the ILS. In MARC, this is the 001 field.                       |
|record\_type|The ID of the record type, which is equal to the ID of the set created according to the record type). The possible record types being (1) [Bibliographic records](http://www.loc.gov/marc/bibliographic/ecbdhome.html); (2) [Authority records](http://www.loc.gov/marc/authority/ecadhome.html); (3) [Holdings records](http://www.loc.gov/marc/holdings/echdhome.html); (4) [Classification records](http://www.loc.gov/marc/classification/eccdhome.html); (5)[Community Information records](http://www.loc.gov/marc/community); and (6) Item records`*`. The **record\_type** column contains the numeric representation of the record type.|
|creation\_date|The date of creation of the MARC record (005 field) in the original ILS). If the 005 field is non-existent, then the **creation\_date** will be the date the record is imported to the database by the OAIToolkit.|
|modification\_date|The date of the last modification to the MARC record (005field).  If an ILS does not store the date of the last modification in the 005 field (as Voyager does not, for example), then the **modification\_date** will be the date the record is imported to the database by the OAIToolkit.|
|is\_deleted|Flag to signal if the record is deleted in the ILS or not. (This behaves as a non deleted record. If it is a new record, we simply insert it into the database. If it is an existent record, simply set is deleted to true <sup>(what???)</sup>. If a record is deleted, the OAI server shows only the date and identifier, but not the MARCXML record itself.)|
|root\_name|The XML root element's name (currently unused). It will maintained for future usage.          |
|root\_namespace|The XML root element's namespace (currently unused). It will be maintained for future usage.  |

_`*`The XC project is still working on modelling Item records, since there is currently no standard to govern this record type. This version of OAI Toolkit does not yet handle Item records._

### xmls Table ###

The xmls table stores the MARCXML content. Each xmls row connects to a records row by means of a **record\_id**, which is the ID of the corresponding records row. (There is a practical reason for storing XML content in a different table: it happens that some MySQL querys like count() function are dependent on the physical volume of the records. The loads of MARCXML records greatly slowed down such queries when we stored them in the same table, and they were therefore relocated to their own table.)

|record\_id|The ID of the corresponding records row. This is a referential value, which means that the user cannot delete a record if its **record\_id** is refered to in a row of the xmls table. Concordantly, there can exist no xmls row that is not bound to a row of the records table.|
|:---------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|xml       |The content of the MARCXML. It can be a string of up to 2GB.                                                                                                                                                                                                                     |


### Sets Table ###

The **sets** table defines and lists the record types described above in the **records** table’s **record\_type** column (bibliographic records, authority records, etc.).

In OAI-PMH, a **set** is an optional construct that groups records for the purpose of selective harvesting. Repositories may organize records into sets. Set organization can be flat, as in the case of a simple list, or hierarchical, as in the case of taxanomies. Multiple hierarchies with distinct, independent top-level nodes are allowed. Hierarchical organization of sets is expressed in the syntax of the setSpec parameter as described below. When a repository defines a set organization, it must include set membership information in the headers of records returned in response to the ListIdentifiers , ListRecords and GetRecord requests (http://www.openarchives.org/OAI/2.0/openarchivesprotocol.htm#Set). Currently, the record types are used as sets.

|set\_id|An auto-incremented identifier of the record. This is referred to by the records table’s **record\_type** column, and the sets\_to\_records table’s **set\_id** column.|
|:------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|set\_name|A short, humanly readable string naming the set                                                                                                                        |
|set\_tag|Currently unused                                                                                                                                                       |
|set\_spec|A colon `[:]` separated list indicating the path from the root of the set hierarchy to the respective node. Each element in the list is a string consisting of any valid URI unreserved characters, which must not contain any colons `[:]`. Since a set\_spec forms a unique identifier for the set within the repository, it must be unique for each set. Flat set organizations have only sets with set\_spec that do not contain any colons `[:]`.|
|set\_description|An optional container that may hold community-specific XML-encoded data about the set                                                                                  |

### sets\_to\_records Table ###

This table connects sets to records. Each row represents a simple connection. If you want to connect multiple sets to the same record, you must create multiple rows in the **sets\_to\_records** table, one row for every set.

|**record\_id**|The specified record’s identifier (record\_id)|
|:-------------|:---------------------------------------------|
|**set\_id**   |The soecified set’s identifier (set\_id)      |

### resumption\_tokens Table ###

A **resumptionToken** is a sample session-like token that helps identify and continue the previous queries (http://www.openarchives.org/OAI/2.0/openarchivesprotocol.htm#FlowControl). The OAIToolkit uses the **resumption\_token** table to store the SQL queries generated by the selective harvesting parameters (**sets**, **from**, **until**) and the **metadataPrefix**. The **resumptionToken** parameter in an OAI request contains the ID of a row in the resumption\_token table and a number representing the limit argument in an SQL query.

|id|The auto-incremented ID of the token|
|:-|:-----------------------------------|
|creation\_date|The creation date of the token      |
|query|The SQL query to retrieve the records|
|query\_for\_count|The SQL query to count the total number of records matching the SQL query|
|metadataPrefix|The metadata prefix for displaying records|