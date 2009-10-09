#!/bin/sh
# ==============================================
# Import the MARCXML files to Lucene Database
# ==============================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ==============================================

java -Xmx1024m -jar lib/OAIToolkit-0.6.2alpha.jar -load -source dest_xml -destination_xml xml_dest -error error -error_xml error_xml -log log -log_detail -marc_schema schema/MARC21slim_rochester.xsd -storage_type Lucene -lucene_index lucene_index