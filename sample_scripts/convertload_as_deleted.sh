#!/bin/sh
# ==============================================
# convert the MARC files to MARCXML as deleted and import to MySQL
# ==============================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ==============================================

java -Xmx2g -cp "lib/xercesImpl.jar:lib/xml-apis.jar" -jar lib/OAIToolkit-1.0.8.jar -load -convert -source marc -destination marc_dest -destination_xml xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema/MARC21slim_rochester.xsd -marc_encoding utf8 -split_size 10000 -lucene_index lucene_index -translate_leader_bad_chars_to_zero -translate_nonleader_bad_chars_to_spaces -fileof_deleted_records
