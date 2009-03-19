@echo off
rem ==============================================
rem convert the MARC files to MARCXML and import to MySQL
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx1024m -jar lib\OAIToolkit-0.5alpha.jar -load -convert -source marc -destination marc_dest -destination_xml xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd -marc_encoding ISO_8859_1 -char_conversion MARC8 -split_size 10000 -storage_type Lucene -lucene_index lucene_index

