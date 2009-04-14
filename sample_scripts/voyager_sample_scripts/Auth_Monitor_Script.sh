#!/bin/sh
#Create date format
YEAR="`date +%Y`"

# Two digit day format
DAY="`date +%d`"

# Two digit month format
MONTH="`date +%m`"
FILEDATE=$YEAR-$MONTH-$DAY

if [ -f /import/marc_extract/auth_records$FILEDATE.mrc ] ; then 
date
echo Bib Auth file arrived ;
else
date
cat <<EOF | mailx -s "Authority Records Did Not Arrive" rarbelo@library.rochester.edu, shreyanshv@library.rochester.edu
EOF
fi
