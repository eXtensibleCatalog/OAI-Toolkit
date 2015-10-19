# About the OAI Toolkit #

The OAI Toolkit is part of a suite of applications that make up the eXtensible Catalog (XC). In a typical XC setup, metadata is collected from various repositories, aggregated, and then made searchable through a front-end user interface. Since most repositories lack a standardized mechanism for connecting and harvesting metadata, XC software uses the OAI-PMH protocol to this end. Some repositories already support OAI-PMH harvesting of their data, and these repositories are compatible with XC as is. The OAI Toolkit is useful for those repositories that do not have an OAI-PMH provider interface built-in.

The OAI Toolkit is used to make data stored in an institution’s integrated library lystem (ILS) or other repository available to the other applications in XC. This is accomplished by exporting metadata from the ILS into the Toolkit’s built-in OAI-PMH-compliant repository. The Toolkit then makes the repository data available for harvesting by other XC components through its OAI provider interface. In a typical setup of the XC network, metadata follows a path from the ILS to the OAI Toolkit, then on to the Metadata Services Toolkit, and finally into XC user interface clients such as the Drupal Toolkit. The OAI Toolkit may also be used in a standalone capacity for institutions wishing to make their ILS metadata OAI-harvestable for other purposes.  It is possible to use the OAI Toolkit with other (non-ILS) repositories as well.

The OAI Toolkit is a required component in the XC setup for many ILSs and other repositories that do not already have an OAI-PMH provider interface. The OAI Toolkit is compatible with any repository that can export MARC metadata into a file or files.  The following systems have been tested with the OAI Toolkit, and sample scripts are provided for these systems:

  * Voyager
  * Innovative Interfaces
  * Aleph
  * Evergreen (planned)

The OAI Toolkit will not need to be used by repositories and ILSs that already have an OAI-PMH provider interface. The following repositories already have this capability (and are therefore compatible with XC as is):

  * Koha
  * DSpace
  * Fedora
  * CONTENTdm
  * IR+

Making institutional data available from an OAI-PMH compliant repository can be thought of as making the data “harvestable.” The process of moving data from an ILS and getting it into an OAI Repository is shown in the following diagrams and is described in the sections that follow.

### Diagram of Data Flow from a Commercial ILS through the OAI Toolkit: ###

![http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/Data%20Flow%20Diagram.jpg](http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/Data%20Flow%20Diagram.jpg)