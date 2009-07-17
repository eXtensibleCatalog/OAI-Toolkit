#!/bin/csh -f
#
#  This script does the following:
#        1. Retrieves records from aleph using ret-01 (using supplied date range and bib library) or query
#        2. Exports all records in MARC format using p-print-03
#        3. Runs clean-up perl script
#        4. Runs OAIToolkitImporter on cleaned up data to import into OAIToolkit
#
#  Special Note: This script should never be run by itself.  It should be called via 'aleph_export_deltas_oai_toolkit.csh'
#				 or via 'aleph_export_full_oai_toolkit.csh'
#
#  Input:
#
#	biblibrary
#	sql_login
#	aleph_scratch
#	start_date
#	end_date
#
#  Example call:
#
#   csh -f /afs/nd.edu/user14/aleph/a18_bin/aleph_export_to_oai_toolkit.csh NDU01 ndu01_user/[password] /aleph_scratch/ndu01 00000000 99999999
#  
#  Requirements:
#         1. Run on machine with Aleph install
#         2. Local Perl installation
#         3. Perl Module MARC::Record
#         4. JVM version 6 or greater install
#         5. Configured OAIToolkitImporter 
#
#  05/27/09  Created                                        				Rick Johnson (U. of Notre Dame)
#  06/17/09  Added support for date filtering for deltas    				Rick Johnson
#  06/19/09  Parameterized for varied bib library, and start and end dates 	Rick Johnson
#  06/29/09  Added sql script calls for faster p-ret-01 alternative         Rick Johnson
#  07/14/09  Moved java call to separate script								Rick Johnson
#

set echo

#setup local environment
set aleph_proc = /exlibris/aleph/a18_1/aleph/proc

source /exlibris/aleph/a18_1/alephm/.cshrc
source $aleph_proc/def_local_env
source /exlibris/aleph/u18_1/alephe/aleph_start

#######################################################################################################
# Get runtime input variables such as bib library, output file names, etc.
#######################################################################################################
set biblibrary = $1
set sql_login = $2
set aleph_scratch = $3
set start_date = $4
set end_date = $5

#convert bib library to lowercase since Aleph cannot handle input file name with uppercase letters
set biblibrary_lc = `echo $biblibrary | tr '[A-Z]' '[a-z]'`

#######################################################################################################
# This is the fix routine that must be added to tab_fix in Aleph to run fix_doc_001.
# We have chosen to name it ADD01, but it can be called anything.  Just need to change below.
#######################################################################################################
set fix_routine_add_sys_no_to_01 = ADD01

######################################################################################################
# Set file names used for Aleph Export and fixing marc records
######################################################################################################
set datestamp = `date +%Y%m%d`
set record_id_file = "xc_out_record_ids_$biblibrary_lc.$datestamp.lst"
set record_id_file_path = "/aleph_scratch/alephe/$record_id_file"
set marc_output_file = "xc_out_$biblibrary_lc.$datestamp.mrc"

#marc 8 and utf 8 file to split marc records into each format to make processes cleaner
set marc_output_file_marc8 = "xc_out_fixed_m8_$biblibrary.$datestamp.mrc"
set marc_output_file_utf8 = "xc_out_fixed_utf8_$biblibrary.$datestamp.mrc"

######################################################################################################
# Set paths
######################################################################################################
set xc_home = /home/aleph18/xc
set xc_bin_dir = $xc_home/bin
set xc_sql_dir = $xc_home/sql

#########################################################################################################################
# For step 1 in the main description above, you can either call p-ret-01 or use the custom query supplied
# p-ret-01 is extremely slow, so it is commented out by default.  However, if a direct DB connection is not possible, the 
# query code can be commented out and the p-ret-01 call can be uncommented.
#########################################################################################################################
#call ret-01 with target file as output

###### uncomment following line to run p-ret-01 ###############################################################
#csh -f $aleph_proc/p_ret_01 $biblibrary,,$record_id_file,000000000,999999999,,00,$start_date,$end_date,$start_date,$end_date,,AND,NOT,,,,,,,,,,,,,,,,,,,,,,00000000,99999999

################# comment these lines to disable query ###########################################################################
if ($end_date == 99999999) then
  $ORACLE_HOME/bin/sqlplus $sql_login @$xc_sql_dir/get_all_active_records.sql $record_id_file_path $biblibrary
else 
  $ORACLE_HOME/bin/sqlplus $sql_login @/$xc_sql_dir/get_range_active_records $record_id_file_path $biblibrary $start_date $end_date
endif
###################################################################################################################################

#call p-print-03 with target file as input and mrc file as output
csh -f $aleph_proc/p_print_03 $biblibrary,$record_id_file,ALL,,,,,,,,$marc_output_file,M1,$fix_routine_add_sys_no_to_01,,,N,

#call marc_record perl script to clean up marc records and separate into marc-8 and utf-8 encoded files
$xc_bin_dir/fix_marc_leader_and_subfields.pl $aleph_scratch/$marc_output_file $aleph_scratch/$marc_output_file_marc8 $aleph_scratch/$marc_output_file_utf8

#call importer script as separate user for mount point from aleph server to app server where oaitoolkit server running
sudo -u tomcat $xc_bin_dir/aleph_call_java_oai_toolkit_importer.csh $aleph_scratch/$marc_output_file_utf8 $aleph_scratch/$marc_output_file_marc8

exit
