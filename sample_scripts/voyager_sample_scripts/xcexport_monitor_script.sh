#!/bin/sh
#
# This script will check to see if the BIB and Authority file extract arrrived
# Create date formatYEAR="`date +%Y`"
# Two digit day format

DAY="`date +%d`"

# Three letter month format
MONTH="`date +%b`"
FILEDATE=$YEAR-$MONTH-$DAY

echo $FILEDATE
if [ -f /import/marc_extract/updates$FILEDATE.mrc ] ; then 
echo XcExport file arrived ;
else
cat <<EOF | mailx -s "Bib & Auth Records Did Not Arrive" rarbelo@library.rochester.edu
EOF
Fi
