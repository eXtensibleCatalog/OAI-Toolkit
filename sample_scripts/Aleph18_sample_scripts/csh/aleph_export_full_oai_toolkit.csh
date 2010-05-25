#!/bin/csh -f
#
#  This script automates the full data load of MARC Records from Aleph to the XC OAIToolkit.
#  It first computes the last time the update was run for each sublibrary and then 
#  calls the aleph_export_to_oai_toolkit script with the date range of last updated
#  to current time for each sublibrary.  The aleph_export_to_oai_toolkit script does the
#  following:
#
#        1. Retrieves all MARC records from aleph for bib library using p-ret-01
#        2. Exports all selected records in MARC format using p-print-03
#        3. Runs clean-up perl script
#        4. Runs OAIToolkitImporter on cleaned up data to import into OAIToolkit
#
#  This script is designed to support calling aleph_export_to_oai_toolkit for multiple sublibraries,
#  but all are commented out except for NDU.
#
#  Requirements:
#         1. Run on machine with Aleph install
#         2. Local Perl installation
#         3. Perl Module MARC::Record
#         4. JVM version 6 or greater install
#         5. Configured OAIToolkitImporter 
#
#  06/19/09  Created                                        Rick Johnson (U. of Notre Dame)
#  07/15/09  Added more parameterization					Rick Johnson

set echo

#setup local environment
csh -f $XC_HOME/bin/environment.csh

#get file last modified date in form yyyymmdd
set start_date = 00000000
set end_date = 99999999

set get_deleted = N

csh -f $xc_bin_dir/aleph_export_to_oai_toolkit.csh $biblibrary1 $ALEPH_BIB1_LOGON /aleph_scratch/$biblibrary1_lc $start_date $end_date $get_deleted
