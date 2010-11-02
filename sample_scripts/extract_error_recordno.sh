#!/bin/sh
#Script for reporting errors in the records of the import process of OAI Toolkit

touch /import/OAIToolkit/log/convert_errors_recordno.log
touch /import/OAIToolkit/test_recordno

grep -i record# /import/OAIToolkit/log/librarian_convert.log >> /import/OAIToolkit/test_recordno

if [ -s /import/OAIToolkit/test_recordno ]; then
echo "Error records reported on $(date)" >> /import/OAIToolkit/log/convert_errors_recordno.log 
cat test_recordno /import/OAIToolkit/log/convert_errors_recordno.log
cat <<EOF | mailx -s "Errors in the convert process of import -- OAIToolkit" shreyanshv@library.rochester.edu
There are some errors in the records. This was found out in the convert process of import of daily_updates through the OAI Toolkit. You can look at the error records control numbers in /import/OAIToolkit/log/convert_errors_recordno.log file on the development server to analyse/rectify the records.
EOF
else
echo No Errors and running fine;
fi

cat /dev/null > /import/OAIToolkit/test_recordno

