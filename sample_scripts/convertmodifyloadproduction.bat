@echo off
rem ==============================================
rem convert the MARC files to MARCXML and import to MySQL
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx2g -cp "lib\xercesImpl.jar:lib\xml-apis.jar" -jar lib\OAIToolkit-1.0.8.jar -convert -modify drop_pipeline.xsl -load -production -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd -lucene_index lucene_index
