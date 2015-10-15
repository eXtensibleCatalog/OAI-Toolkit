# Exported ILS Data #

Data exported from an ILS that will be processed using the OAI Toolkit must conform to the following:

  * Format must be one of:
    * MARC21 records in Unicode character set
    * MARC21 records in MARC-8 character set
    * Properly formatted MARCXML
  * Record type must be one of:
    * Bibliographic
    * Holding
    * Authority

Note that “Item records” may be coming in a future release of the toolkit, but this has not been planned. There is not yet any MARC standard for item data.

## ILS Export Script ##

The ILS Export Script is an ILS- and institution-specific component to retrieve raw MARC from the ILS and place it in a directory accessible by the OAI Toolkit. As part of the installation and automation setup, users of the OAI Toolkit will need to develop their own ILS Export Script. An experienced ILS system administrator should have the know-how to create this script. The goal is to write a script or set of scripts (and then schedule them to run regularly) to automate the movement of records from the ILS to the OAI Toolkit. This will result in a synchronization of metadata in the ILS (that is being added to and updated regularly through cataloging and other ILS processing) and the OAI Toolkit.

Sample scripts are provided for Voyager, Aleph, and III. Where possible, these scripts make use of ILS-provided export tools instead of directly accessing the data via database calls.  These scripts can be used as examples when building your script, or they can be used as starting points that will require customization based on an institution’s server configurations.

Please see the ["More Info on the OAI-PMH Protocol"](MoreonProtocol.md) section for examples of ILS Export Scripts that extract and move MARC data.