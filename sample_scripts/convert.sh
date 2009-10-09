#!/bin/sh
# ==============================================
# Convert the MARC files to MARCXML
# ==============================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ==============================================

java -Xmx1024m -jar lib/OAIToolkit-0.6.2alpha.jar -convert -source marc -destination xml -destination_xml dest_xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema/MARC21slim_rochester.xsd -marc_encoding ISO_8859_1 -char_conversion MARC8 -split_size 10000 -translate_leader_bad_chars_to_zero -translate_nonleader_bad_chars_to_spaces