@echo off
rem ==============================================
rem Modify the MARCXML files
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx1024m -cp lib\xercesImpl.jar -jar lib\OAIToolkit-1.0.1.jar -modify populate003.xsl -source dest_xml -destination modified_xml -destination xml -error error -error_xml error_xml -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd -marc_encoding utf8
