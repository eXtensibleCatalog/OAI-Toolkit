#!/bin/csh -f
#
#  This script does the following:
#        1. Runs OAIToolkitImporter on cleaned up data to import into OAIToolkit
#
#  Special Note: This script should never be run by itself.  It should be called via 'aleph_export_to_oai_toolkit.csh'
#
#  Requirements:
#         1. Run on machine with Aleph install
#         2. JVM version 6 or greater install
#         3. Configured OAIToolkitImporter 
#
#  07/14/09  Created                                        				Rick Johnson (U. of Notre Dame)
#

######################################################################################################
# Set OAIToolkit Importer paths
######################################################################################################
csh -f $XC_HOME/bin/environment.csh

######################################################################################################
# Get Output files and whether to include deleted records from input
######################################################################################################

set marc_output_file_utf8 = $1
set marc_output_file_marc8 = $2
set get_deleted = $3

#make directories if necessary
#if (! -d $oai_dir) then 
#    mkdir $oai_dir
#endif

if (! -d $oai_mrc_src_dir) then
    mkdir $oai_mrc_src_dir
endif

if (! -d $oai_log_dir) then
    mkdir $oai_log_dir
endif

if (! -d $oai_error_xml_dir) then
    mkdir $oai_error_xml_dir
endif

if (! -d $oai_error_dir) then
    mkdir $oai_error_dir
endif

if (! -d $oai_dest_dir) then
    mkdir $oai_dest_dir
endif

if (! -d $oai_dest_xml_dir) then
    mkdir $oai_dest_xml_dir
endif

if (! -d $oai_lucene_dir) then
    mkdir $oai_lucene_dir
endif

#import marc-8 and utf-8 mrc files to OAI toolkit
cd $xc_oai_toolkit_importer_dir 
rm $oai_dest_dir/*
rm $oai_dest_xml_dir/*
rm $oai_mrc_src_dir/*

cp $marc_output_file_utf8 $oai_mrc_src_dir
cp $marc_output_file_marc8 $oai_mrc_src_dir

if ($get_deleted == "Y") then
	$java_dir/java -jar $xc_oai_jar -convert -load -fileof_deleted_records -source $oai_mrc_src_dir -destination $oai_dest_dir -destination_xml $oai_dest_xml_dir -error $oai_error_dir -error_xml $oai_error_xml_dir -log $oai_log_dir -log_detail -split_size 10000 -storage_type Lucene -lucene_index $oai_lucene_dir -translate_leader_bad_chars_to_zero -translate_nonleader_bad_chars_to_spaces > /dev/null
else
	$java_dir/java -jar $xc_oai_jar -convert -load -source $oai_mrc_src_dir -destination $oai_dest_dir -destination_xml $oai_dest_xml_dir -error $oai_error_dir -error_xml $oai_error_xml_dir -log $oai_log_dir -log_detail -split_size 10000 -storage_type Lucene -lucene_index $oai_lucene_dir -translate_leader_bad_chars_to_zero -translate_nonleader_bad_chars_to_spaces > /dev/null
endif