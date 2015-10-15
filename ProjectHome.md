The <a href='http://www.extensiblecatalog.org'>eXtensible Catalog (XC)</a> project is working to design and develop a set of open-source applications that will provide libraries with an alternative way to reveal their collections to library users.

Please visit our project website at http://www.extensiblecatalog.org for a more complete overview of the eXtensible Catalog project and the software we are creating.

### Latest News ###
  * **Update:** September 2011: 1.0.8 released.  See [release notes](http://code.google.com/p/xcoaitoolkit/wiki/ReleaseNotes)

  * **Issue Tracking and public access** now in [Jira](http://extensiblecatalog.lib.rochester.edu:8080/browse/OAI)

### OAI Toolkit ###

The OAI Toolkit is used to make data stored in an institution’s ILS or other repository available for harvesting via OAI-PMH, including other eXtensible Catalog applications. For an ILS, this is accomplished by exporting ILS metadata, converting it from MARC to MARCXML, and loading it into an OAI-PMH compliant repository. The repository (embedded in the OAI Toolkit) makes the data available for harvesting by other XC components.

The OAI Toolkit can be used as part of the XC system, or on its own to enable OAI-PMH harvestability of an existing repository. It is a server application written in Java and is only needed for ILS’s and other repositories that do not already have the ability to be act as OAI-PMH Repositories (OAI Servers).

Documentation for the OAI Toolkit can be found on the wiki tab of this project http://code.google.com/p/xcoaitoolkit/wiki/WikiHome?tm=6.

The software code repository for the OAI Toolkit is: http://code.google.com/p/xcoaitoolkit

There are sample scripts provided that serve as implementation starting points for integration with the following ILS:
  * Voyager 6.2 and the 7.0 Classic Interface
  * Aleph 18

XC OAI Toolkit Primary Software Developers:

  * Peter Kiraly - developed original code base
  * Shreyansh Vakil - enhancements through Jan. 2011
  * Chris Delis - enhancements from Feb. 2011 on