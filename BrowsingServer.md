# Browsing the OAI Server #

You can access the OAI server at http://localhost:8080/OAIToolkit (or the location of your server, plus /OAIToolkit). If you point your browser to that URL, you should see the following screen:

![http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/OAIToolkit%20starting%20page.jpg](http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/OAIToolkit%20starting%20page.jpg)

Then, if you click "OAI sample requests / repository configuration link," you should now see the following screen:

![http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/OAI%20sample%20requests.jpg](http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/OAI%20sample%20requests.jpg)

The OAI test requests are only for testing whether the server responds to every OAI request. Keep in mind that, on your server, the parameters may differ; some requests may respond to an OAI error message. This is not abnormal.

Also, on the bottom of this screen, there is a read-only configuration form. Here, you can view the variables that inform harvesters about your repository (the content of response to OAI’s **Identifier** verb), and that govern the size of the **List...** verbs.

![http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/Identity%20settings%20and%20Technical%20fields.jpg](http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/Identity%20settings%20and%20Technical%20fields.jpg)

You can configure the response to the **Identify** verb under **OAI Identify settings**, as depicted above, by editing the OAIToolkit.server.properties file.

Each record in the OAI repository has a unique identifier. The identifier is built up according to the following pattern: **`<scheme><delimiter><repository-identifier><delimiter><local-identifier>`**

|scheme (oaiIdentifierScheme)|The scheme part of the identifier, usually “oai”|
|:---------------------------|:-----------------------------------------------|
|repository identifier (oaiIdentifierRepositoryIdentifier)|The identifier that makes this installation of the repository unique|
|sample identifier (sampleIdentifier)|A sample identifier that functions according to the rules and given variables (eg. oai:library.rochester.edu:URVoyager1/99123)|

The rest of the variables under the **OAI Identify settings**:

|name of repository (repositoryName)|The humanly readable name of your repository (in OAI response: **repositoryName**)|
|:----------------------------------|:---------------------------------------------------------------------------------|
|base URL (baseUrl)                 |The base URL of the repository (**baseURL**)                                      |
|administrator email (adminEmail)   |The e-mail address of an administrator of the repository                          |
|granularity of timestamps (granularity)|The finest harvesting granularity supported by the repository. The legitimate values are **day granularity** (**YYYY-MM-DD**) and **seconds granularity** (**YYYY-MM-DDThh:mm:ssZ**), with meanings as defined in ISO8601.|
|compression types (compression)    |A compression encoding supported by the repository. The recommended values are those defined for the Content-Encoding header in Section 14.11 of RFC 2616 describing HTTP 1.1. The OAIToolkit supports **gzip**, **compress**, and **deflate**.|
|information about deleted records (deletedRecord)|The manner in which the repository supports the notion of deleted records (**deletedRecord**). Legitimate values are **no** (the repository does not maintain information about deletions), transient (the repository does not guarantee that a list of deletions is maintained persistently or consistently), **persistent** (the repository maintains information about deletions with no time limit). A repository that indicates this last level of support must persistently keep track of the full history of deletions and consistently reveal the status of a deleted record over time.|
|OAI protocol version (protocolVersion)|The version of the OAI-PMH supported by the repository (**protocolVersion**)      |

Under **Technical fields**, you can set up the maximum size of the **List...** responses (**ListSets**, **ListIdentifiers**, **ListRecords**) with two values: (1) the maximum number of items (sets, records, identifiers) on one response and (2) the response’s maximal size (in bytes). For example, if we allow 1000 items in a response, but the size of the response XML exceeds the byte limit at the 900th item, the response will contain only 899 items.

|maximum number of sets in a ListSets response chunk (setsChunk\_maxNumberOfRecords)|The maximum number of sets in a ListSets response chunk|
|:----------------------------------------------------------------------------------|:------------------------------------------------------|
|maximum size in bytes of a ListSets response chunk (setsChunk\_maxSizeInBytes)     |The maximum size of a ListSets response chunk (in bytes)|
|maximum number of identifiers in a ListIdentifiers response chunk (identifiersChunk\_maxNumberOfRecords)|The maximum number of identifiers in a ListIdentifiers response chunk|
|maximum size in bytes of a ListIdentifiers response chunk (identifiersChunk\_maxSizeInBytes)|The maximum size of a ListIdentifiers response chunk (in bytes)|
|maximum number of records in a ListRecords response chunk (recordsChunk\_maxNumberOfRecords)|The maximum number of records in a ListRecords response chunk|
|maximum size in bytes of a ListRecords response chunk (recordsChunk\_maxSizeInBytes)|The maximum size of a ListRecords response chunk (in bytes)|
|expiration time of the resumption token (expirationDate)                           |The expiration time of a resumption token (in seconds). If more time is spent between two consecutive requests of the same token, the second will be invaluable. The harvester should restart the harvesting from the starting point. **-1** indicates that there is no such limitation.|
|maximum number of simultaneous requests (maxSimultaneousRequest)                   |The maximum number of simultaneous OAI requests        |
|MARC XML schema file (schema)                                                      |Do you wish to use the standard MARCXML schema file maintained by the Library of Congress or a modified, customized version? If you use the former, there are two options from which you should choose **astandard (LoC) schema** or **bcustomized schema file (OAIToolkit/schema/custom.xsd**). If you decide to use a customized version, place it in **CATALINA\_HOME/OAIToolkit/schema/custom.xsd**.|
|storage type (storageType)                                                         |The same storage type mentioned in the OAIToolkit importing part (_this value should always be set to Lucene_)|
|Maximum lifetime of a cached response (maxCacheLifetime)                           |Maximum lifetime of a cached response (in minutes). After this time, the cache file will be deleted. There is a garbage collection method that runs every 5 minutes; it deletes the files in the cache directory which are older than the maximum lifetime of a cached response value.  See the section on caching for more information.|

The common OAI server URL is http://localhost:8080/OAIToolkit/oai-request.do This URL can be parameterized according to OAI specification.