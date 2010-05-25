#!/bin/csh -f
#
#  This script automates regular delta updates of deleted MARC records from Aleph to the XC OAIToolkit.
#  It first computes the last time the update was run for each sublibrary and then 
#  calls the aleph_export_to_oai_toolkit script with the date range of last updated
#  to current time for each sublibrary.  The aleph_export_to_oai_toolkit script does the
#  following:
#
#        1. Retrieves MARC record updates from aleph for bib library and date range using p-ret-01
#        2. Exports all selected records in MARC format using p-print-03
#        3. Runs clean-up perl script
#        4. Runs OAIToolkitImporter on cleaned up data to import into OAIToolkit
#  
#  This script is designed to support calling aleph_export_to_oai_toolkit for multiple sublibraries,
#  but all are commented out except for NDU.
#
#
#  Requirements:
#         1. Run on machine with Aleph install
#         2. Local Perl installation
#         3. Perl Module MARC::Record
#         4. JVM version 6 or greater install
#         5. Configured OAIToolkitImporter 
#
#  09/10/09  Created from active record script         Rick Johnson (U. of Notre Dame)

set echo

#setup local environment
csh -f $XC_HOME/bin/environment.csh

set get_deleted = Y

#get file last modified date in form yyyymmdd
set tomorrow = `date --date="+1 days" +%Y%m%d`
set end_date = $tomorrow

#get most recent date for file matching this pattern
set start_date = `ls -lt --time-style=long-iso $record_id_file_dir | grep xc_out_del_record_ids_$biblibrary1_lc | awk '{print $6}' | sed -n '1p' | sed 's/\-//g'`
if ($start_date !~ [0-9][0-9]*) then
	#default to all records if file does not exist
	set start_date = 00000000
	set end_date = 99999999
endif

csh -f $xc_bin_dir/aleph_export_to_oai_toolkit.csh $biblibrary1 $ALEPH_BIB1_LOGON /aleph_scratch/$biblibrary1_lc $start_date $end_date $get_deleted
