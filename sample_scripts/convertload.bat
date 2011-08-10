@echo off
rem ==============================================
rem convert the MARC files to MARCXML and import to MySQL
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx1024m -cp "lib\xercesImpl.jar:lib\xml-apis.jar" -jar lib\OAIToolkit-1.0.4.jar -load -convert -source marc -destination marc_dest -destination_xml xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd -marc_encoding utf8 -split_size 10000 -lucene_index lucene_index -translate_leader_bad_chars_to_zero -translate_nonleader_bad_chars_to_spaces -indent_xml
