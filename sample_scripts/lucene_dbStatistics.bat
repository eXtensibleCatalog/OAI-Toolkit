@echo off
rem ====================================================
rem Script for getting the record count with their types
rem and the deleted count of the Lucene Database
rem ====================================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ====================================================

java -Xmx1024m -cp "lib\xercesImpl.jar:lib\xml-apis.jar" -jar lib\OAIToolkit-1.0.10.jar -lucene_statistics -lucene_index lucene_index

