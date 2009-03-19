@echo off
rem ==============================================
rem Create the database
rem ==============================================
rem ATTENTION: this is a sample script, modify the 
rem parameters according to your needs. Read the 
rem OAIToolkit Manual for the details.
rem ==============================================

rem set the MySQL user name here
set user=

rem set the MySQL user's password here
set password=

mysql --user=%user% --password=%password% < sql\oai.sql