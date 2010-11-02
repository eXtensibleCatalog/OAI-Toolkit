@echo off
rem ==============================================
rem Create the database user
rem ==============================================
rem Read the  OAIToolkit Manual for the details.
rem ==============================================

if /i "%1"=="" goto usage
if /i "%2"=="" goto usage
if /i "%3"=="" goto usage
if /i "%4"=="" goto usage

rem set the MySQL root user name here
set root_user=%1

rem set the MySQL root user's password here
set root_password=%2

rem set the MySQL user name, who will use the extensiblecatalog
set user=%3

rem set the MySQL user password who will use the extensiblecatalog
set password=%4

mysql --user=%root_user% --password=%root_password% -e "CREATE USER %user%@localhost IDENTIFIED BY '%password%';"
mysql --user=%root_user% --password=%root_password% -e "GRANT all privileges ON extensiblecatalog.* TO %user%@localhost;"

goto end

:usage
echo create_user [root] [root's password] [new user] [new user's password]

:end