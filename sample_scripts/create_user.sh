#!/bin/sh
# ==============================================
# Create the database user
# ==============================================
# Read the OAIToolkit Manual for the details.
# ==============================================

if [$# -ne 4]
then 
	echo "$0 [root] [root's password] [new user] [new user's password]"
	exit
fi

# set the MySQL root user name here
root_user=$1

# set the MySQL root user's password here
root_password=$2

# set the MySQL user name, who will use the extensiblecatalog
user=$3

# set the MySQL user password who will use the extensiblecatalog
password=$4

mysql --user="$root_user" --password="$root_password" -e "CREATE USER $user@localhost IDENTIFIED BY '$password';"
mysql --user="$root_user" --password="$root_password" -e "GRANT all privileges ON extensiblecatalog.* TO $user@localhost;"

