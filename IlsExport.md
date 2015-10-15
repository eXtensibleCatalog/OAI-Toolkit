# Exported ILS Data #

Data exported from an ILS to be processed using the OAI Toolkit must conform to the following:
  * The format must be one of:
    * MARC21 records in Unicode character set
    * MARC21 records in MARC-8 character set
    * Properly formatted MARCXML
  * The record type must be one of:
    * Bibliographic
    * Holding
    * Authority

_Note: “Item records” will be coming in a future release of the toolkit. There is no MARC standard for item data._

## ILS Export Script ##

The ILS Export Script is an ILS- and institution-specific component to retrieve raw MARC from the ILS and place this data in a directory accessible by the OAI Toolkit. As part of the installation and automation setup, users of the OAI Toolkit will need to develop their own ILS Export Script. An experienced ILS system administrator should have the know-how to create this script.  The goal is to write a script or set of scripts (and then schedule them to run regularly) to automate the movement of records from the ILS to the OAI Toolkit.  This will result in a synchronization of metadata in the ILS, which is being added to and updated regularly through cataloging and other ILS processing, and the OAI Toolkit.

Sample scripts are provided for Voyager, Aleph, and III. Wherever possible, these scripts make use of ILS-provided export tools instead of directly accessing the data via database calls. These scripts can be used as examples when building your own script, or they can serve as starting points that will require customization based on an institution’s server configurations.

Consult the "ILS Connectors" section for examples of ILS Export Scripts that extract and move MARC data.