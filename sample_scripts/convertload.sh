#!/bin/sh
# ==============================================
# convert the MARC files to MARCXML and import to MySQL
# ==============================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ==============================================

java -Xmx1024m -jar lib/OAIToolkit-0.6.5alpha.jar -load -convert -source marc -destination marc_dest -destination_xml xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema/MARC21slim_rochester.xsd -marc_encoding utf8 -char_conversion ISO5426 -split_size 10000 -storage_type Lucene -lucene_index lucene_index -translate_leader_bad_chars_to_zero -translate_nonleader_bad_chars_to_spaces -indent_xml