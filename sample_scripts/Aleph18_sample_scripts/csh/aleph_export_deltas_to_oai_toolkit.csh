#!/bin/csh -f
#
#  This script automates regular delta updates of MARC records from Aleph to the XC OAIToolkit.
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
#  06/19/09  Created                                        Rick Johnson (U. of Notre Dame)
#  07/15/09  Modified to easier to parameterize				Rick Johnson

set echo

#setup local environment
set aleph_proc = /exlibris/aleph/a18_1/aleph/proc

source /exlibris/aleph/a18_1/alephm/.cshrc
source $aleph_proc/def_local_env
source /exlibris/aleph/u18_1/alephe/aleph_start

set record_id_file_dir = /aleph_scratch/alephe

set xc_bin_dir = /home/aleph18/xc/bin

## Execute a full load for the bib below, copy and paste this block as needed for other bib libraries #############################################################
set biblibrary1 = NDU01
#set biblibrary1 = SMC01
#set biblibrary1 = BCI01
#set biblibrary1 = HCC01

set biblibrary1_lc = `echo $biblibrary1 | tr '[A-Z]' '[a-z]'`

#get file last modified date in form yyyymmdd
set tomorrow = `date --date="+1 days" +%Y%m%d`
set end_date = $tomorrow

#get most recent date for file matching this pattern
set start_date = `ls -lt --time-style=long-iso $record_id_file_dir | grep xc_out_record_ids_$biblibrary1 | awk '{print $6}' | sed -n '1p' | sed 's/\-//g'`
if ($start_date=="") then
	#default to all records if file does not exist
	set start_date = 00000000
	set end_date = 99999999
endif

#get sql connection credentials from Aleph
setenv ALEPH_BIB1_LOGON "$biblibrary1/`/exlibris/aleph/a18_1/aleph/exe/get_ora_passwd $biblibrary1`"

csh -f $xc_bin_dir/aleph_export_to_oai_toolkit.csh $biblibrary1 $ALEPH_BIB1_LOGON /aleph_scratch/$biblibrary1_lc $start_date $end_date