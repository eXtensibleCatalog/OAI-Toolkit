# Brief Description of the OAI-PMH Protocol Process #

A harvester issues an HTTP request for data. The OAI Server (also called an OAI Provider or OAI Repository) provides XML responses to the HTTP request. Each request has a main command that is denoted by a **verb** parameter, though it may also have other parameters. There are six verbs:
  1. **Identify** describes the repository.
  1. **ListMetadataFormats** gives a list of available metadata formats into which the records stored in the repository may be transformed.
  1. **ListSets** lists the available "sets." Sets are categories for grouping data, and each record may be connected to one or more sets. In the OAI Toolkit, sets exist for the various MARC data types, including bibliographic, authority, and holding.
  1. **ListIdentifiers** lists the identifiers (headers) of the records in the repository.
  1. **ListRecords** lists the records in the repository (the whole record, not just its identifier).
  1. **GetRecord** retrieves an individual metadata record from the repository.

The other parameters usually filter the records, such as by set or date span. If the data requested by a harvest produces more sets, identifiers, or records than the number of items that can be passed in one data transfer, then a special session identifier called a **resumptionToken** is created. In order for the rest of the data to be harvested, the **resumptionToken** parameter is passed back and forth between the OAI Server and the requesting (harvesting) server, allowing the remainder of the data from the original harvest request to be passed.