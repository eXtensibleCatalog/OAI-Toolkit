#!/bin/csh -f
#
#  This sets up the local environment variables used by scripts to export MARC records from
#  Aleph 18 and then import them into the OAI toolkit.
#
#  Requirements:
#         1. Run on machine with Aleph install
#         2. Local Perl installation
#         3. Perl Module MARC::Record
#         4. JVM version 6 or greater install
#         5. Configured OAIToolkitImporter 
#
#  05/24/10  Created                                        Rick Johnson (U. of Notre Dame)


######################################################################################################
# Setup aleph environment
######################################################################################################
set echo

setenv aleph_proc /exlibris/aleph/a18_1/aleph/proc

source /exlibris/aleph/a18_1/alephm/.cshrc_xc
source $aleph_proc/def_local_env
source /exlibris/aleph/u18_1/alephe/aleph_start

setenv record_id_file_dir /aleph_scratch/alephe

######################################################################################################
# Set script home paths, xc_home should be set to route directory for these scripts (csh reside in xc_bin_dir below)
######################################################################################################
setenv xc_home $XC_HOME
setenv xc_bin_dir $xc_home/bin
setenv xc_sql_dir $xc_home/sql

## Execute a full load for the bib below, copy and paste this block as needed for other bib libraries #############################################################
setenv biblibrary1 NDU01
#setenv biblibrary1 SMC01
#setenv biblibrary1 BCI01
#setenv biblibrary1 HCC01

setenv biblibrary1_lc `echo $biblibrary1 | tr '[A-Z]' '[a-z]'`

#get sql connection credentials from Aleph
setenv ALEPH_BIB1_LOGON "$biblibrary1/`/exlibris/aleph/a18_1/aleph/exe/get_ora_passwd $biblibrary1`"

#######################################################################################################
# This is the fix routine that must be added to tab_fix in Aleph to run fix_doc_001.
# We have chosen to name it ADD01, but it can be called anything.  Just need to change below.
#######################################################################################################
setenv fix_routine_add_sys_no_to_01 ADD01

######################################################################################################
# Set OAIToolkit Importer paths
######################################################################################################
setenv xc_oai_toolkit_importer_dir /clu_shared/importer/OAIToolkit
setenv xc_oai_jar $xc_oai_toolkit_importer_dir/lib/OAIToolkit-0.6.2alpha.jar

#java location
setenv java_dir /usr/java/bin

setenv oai_toolkit_importer_data_dir /clu_shared
setenv oai_mrc_src_dir $xc_oai_toolkit_importer_dir/src
setenv oai_log_dir $oai_toolkit_importer_data_dir/log
setenv oai_error_xml_dir $xc_oai_toolkit_importer_dir/temp_error
setenv oai_error_dir $xc_oai_toolkit_importer_dir/temp_error
setenv oai_dest_dir $xc_oai_toolkit_importer_dir/marc_dest
setenv oai_dest_xml_dir $xc_oai_toolkit_importer_dir/temp

# lucene destination dir for importer result
setenv oai_lucene_dir $oai_toolkit_importer_data_dir/lucene_index

