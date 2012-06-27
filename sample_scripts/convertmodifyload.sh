#!/bin/sh
# ==============================================
# convert the MARC files to MARCXML and import to Lucene
# ==============================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ==============================================

java -Xmx1024m -cp "lib/xercesImpl.jar:lib/xml-apis.jar" -jar lib/OAIToolkit-1.0.8.jar -convert -modify drop_pipeline.xsl -load -source marc -destination marc_dest -destination_xml xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema/MARC21slim_rochester.xsd -lucene_index lucene_index
