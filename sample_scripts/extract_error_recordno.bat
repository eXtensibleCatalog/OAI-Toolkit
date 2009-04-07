@ECHO OFF
REM =============================================================
REM Script for extracting the record numbers in the error records  
REM of the convert process from the librarian_convert.log
REM =============================================================
REM ATTENTION; This is a sample script. Read more about it in 
REM the OAI Toolkit Manual.
REM =============================================================

FINDSTR /C:"record#" C:\OAIToolkit\log\librarian_convert.log >> test_recordno.txt
IF EXIST test_recordno.txt (
ECHO . >> log\convert_errors_recordno.log
ECHO Error records reported by the convert process of import on %date% - %time% >> log\convert_errors_recordno.log
ECHO . >> log\convert_errors_recordno.log
TYPE test_recordno.txt >> log\convert_errors_recordno.log
DEL test_recordno.txt
)
ELSE (
ECHO No Errors in the error records of convert process
)
EXIT

