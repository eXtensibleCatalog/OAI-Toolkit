@echo off
rem ==============================================
rem Import the MARCXML files as deleted to Lucene
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx1024m -jar lib\OAIToolkit-1.0.1.jar -load -source dest_xml -destination_xml xml_dest -error error -error_xml error_xml -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd -lucene_index lucene_index -fileof_deleted_records
