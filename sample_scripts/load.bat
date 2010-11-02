@echo off
rem ==============================================
rem Import the MARCXML files to MySQL
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx1024m -jar lib\OAIToolkit-0.6.7alpha.jar -load -source modified_xml -destination_xml xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd -storage_type Lucene -lucene_index lucene_index -indent_xml
