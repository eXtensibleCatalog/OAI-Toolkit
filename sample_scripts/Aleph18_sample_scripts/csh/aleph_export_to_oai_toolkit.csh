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
csh -f $XC_HOME/bin/environment.csh

#######################################################################################################
# Get runtime input variables such as bib library, output file names, etc.
#######################################################################################################
set biblibrary = $1
set sql_login = $2
set aleph_scratch = $3
set start_date = $4
set end_date = $5
set get_deleted = $6

set biblibrary_lc = `echo $biblibrary | tr '[A-Z]' '[a-z]'`

######################################################################################################
# Set file names used for Aleph Export and fixing marc records
######################################################################################################
set datestamp = `date +%Y%m%d`

if ($get_deleted == "Y") then
	set record_id_file = "xc_out_del_record_ids_$biblibrary_lc.$datestamp.lst"
	set record_id_file_path = "$record_id_file_dir/$record_id_file"
	set marc_output_file = "xc_out_del_$biblibrary_lc.$datestamp.mrc"

	#marc 8 and utf 8 file to split marc records into each format to make processes cleaner
	set marc_output_file_marc8 = "xc_out_del_fixed_m8_$biblibrary.$datestamp.mrc"
	set marc_output_file_utf8 = "xc_out_del_fixed_utf8_$biblibrary.$datestamp.mrc"
else
	set record_id_file = "xc_out_record_ids_$biblibrary_lc.$datestamp.lst"
	set record_id_file_path = "$record_id_file_dir/$record_id_file"
	set marc_output_file = "xc_out_$biblibrary_lc.$datestamp.mrc"

	#marc 8 and utf 8 file to split marc records into each format to make processes cleaner
	set marc_output_file_marc8 = "xc_out_fixed_m8_$biblibrary.$datestamp.mrc"
	set marc_output_file_utf8 = "xc_out_fixed_utf8_$biblibrary.$datestamp.mrc"
endif

#########################################################################################################################
# For step 1 in the main description above, you can either call p-ret-01 or use the custom query supplied
# p-ret-01 is extremely slow, so it is commented out by default.  However, if a direct DB connection is not possible, the 
# query code can be commented out and the p-ret-01 call can be uncommented.
#########################################################################################################################
#call ret-01 with target file as output

###### uncomment following line to run p-ret-01 ###############################################################
#csh -f $aleph_proc/p_ret_01 $biblibrary,,$record_id_file,000000000,999999999,,00,$start_date,$end_date,$start_date,$end_date,,AND,NOT,,,,,,,,,,,,,,,,,,,,,,00000000,99999999

################# comment these lines to disable query ###########################################################################
if ($end_date == 99999999 && $get_deleted == "N") then
  $ORACLE_HOME/bin/sqlplus $sql_login @$xc_sql_dir/get_all_active_records.sql $record_id_file_path $biblibrary
else if ($end_date == 99999999 && $get_deleted == "Y") then
  $ORACLE_HOME/bin/sqlplus $sql_login @$xc_sql_dir/get_all_deleted_records.sql $record_id_file_path $biblibrary
else if ($get_deleted == "Y") then
  $ORACLE_HOME/bin/sqlplus $sql_login @/$xc_sql_dir/get_range_deleted_records $record_id_file_path $biblibrary $start_date $end_date
else 
  $ORACLE_HOME/bin/sqlplus $sql_login @/$xc_sql_dir/get_range_active_records $record_id_file_path $biblibrary $start_date $end_date
endif
###################################################################################################################################

#call p-print-03 with target file as input and mrc file as output
csh -f $aleph_proc/p_print_03 $biblibrary,$record_id_file,ALL,,,,,,,,$marc_output_file,M1,$fix_routine_add_sys_no_to_01,,,$get_deleted,

#call marc_record perl script to clean up marc records and separate into marc-8 and utf-8 encoded files
$xc_bin_dir/fix_marc_leader_and_subfields.pl $aleph_scratch/$marc_output_file $aleph_scratch/$marc_output_file_marc8 $aleph_scratch/$marc_output_file_utf8

#call importer script as separate user for mount point from aleph server to app server where oaitoolkit server running
sudo -u tomcat $xc_bin_dir/aleph_call_java_oai_toolkit_importer.csh $aleph_scratch/$marc_output_file_utf8 $aleph_scratch/$marc_output_file_marc8 $get_deleted

exit
