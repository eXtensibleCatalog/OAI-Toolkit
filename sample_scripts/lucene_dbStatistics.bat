@echo off
rem ==============================================
rem convert the MARC files to MARCXML and import to MySQL
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

java -Xmx1024m -jar lib\OAIToolkit-0.6alpha.jar -lucene_statistics

