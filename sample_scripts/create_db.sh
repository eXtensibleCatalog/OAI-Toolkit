#!/bin/sh
# ==============================================
# Create the database
# ==============================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ==============================================


# set the MySQL user name here
user=

# set the MySQL user's password here
password=

mysql --user=$user --password=$password oaitoolkit < sql/oai.sql

