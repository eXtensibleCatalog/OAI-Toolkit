#!/bin/sh
find /import/deleted_records -mtime -4 -name "*.tgz" > /import/deleted_records/TESTFILE
if [ -s /import/deleted_records/TESTFILE ] ; then 
echo Deleted file arrived ;
time
else
echo "Deleted records did not arrive" >> /import/deleted_records/mailfile
date
echo "Deleted records did not arrive"
mailx -s "Deleted Records Did Not Arrive" rarbelo@library.rochester.edu,shreyanshv@library.rochester.edu < /import/deleted_re
cords/mailfile 
fi
rm /import/deleted_records/TESTFILE
rm /import/deleted_records/mailfile
