#!/bin/sh
#
# It shouldn’t be necessary since we use the full path to executables, but feel free to uncomment
# the PATH statements below and add any relevant path information for your server
#PATH=
#export PATH
#############################################################
#                                                  
# MARC record export script, by Ralph Arbelo 4/18/07      
# Last edited: 11/11/08
#                                                 
# XcExport runs Pmarcexport with the following parameters:
# rG - bibliographic & authority records
# mU - Updated records
# ttoday-0 Records updated today
# aM Assign character mapping, MARC-8,21
#
# The output file is then moved to another server.
#
##############################################################


# Create start date 
# Four digit year format
YEAR="`date +%Y`"

# Two digit day format
DAY="`date +%d`"

# Two digit month format
MONTH="`date +%m`"

echo $MONTH
# Put date in format that Pmarcexport expects (YYYY-MM-DD)
FILEDATE=$YEAR-$MONTH-$DAY

# Run MarcExport
/m1/voyager/rochdb/sbin/Pmarcexport -o/m1/voyager/local/bin/XcMarcExport/updates$FILEDATE.mrc -rG -mB -i -ttoday-0 -aM

echo Moving file to  XcVoyOAI
usr/local/bin/scp /m1/voyager/local/bin/XcMarcExport/updates$FILEDATE.mrc voyager@xcvoyoai:/import/marc_extract 

# Zip updates$FILEDATE  
echo zipping updates$FILEDATE.mrc
/bin/gzip /m1/voyager/local/bin/XcMarcExport/updates$FILEDATE.mrc

echo Move file to archive directory 
mv /m1/voyager/local/bin/XcMarcExport/updates$FILEDATE.mrc.gz /m1/voyager/local/bin/XcMarcExport/XcArchive/.

echo Finished with record export
echo
echo Checking for a new deleted records file. If exists then copy it to the OAI server

#Check to see if new deleted file exists, if so then move it

if [ -f `find /export/home/voyager/Archive/ -mtime -4 -name "*.tgz"` ] ; then
        /usr/local/bin/scp `find /export/home/voyager/Archive/ -mtime -4 -name "*.tgz"` voyager@xcvoyoai:/import/deleted_reco
rds
        echo files moved
else
        echo No new file today
fi

# Authority record export
# This section will export authority records once a week (on Monday)

# This will look for Mon and if it is Monday, will put it in the file
date | grep Mon >> /m1/voyager/local/bin/XcMarcExport/tempfile

# If testfile is an empty file, then do nothing. Otherwise, run the export and move them to the OAI server
if [ -s /m1/voyager/local/bin/XcMarcExport/tempfile ] ; then
        echo "It's Monday, run the authority record export"
        /m1/voyager/rochdb/sbin/Pmarcexport -o/m1/voyager/local/bin/XcMarcExport/auth_records$FILEDATE.mrc -rA -mB -aM -ttoda
y-7
        # Move the extract to the OAI server
        /usr/local/bin/scp /m1/voyager/local/bin/XcMarcExport/auth_records$FILEDATE.mrc voyager@xcdevvoyoai:/import/marc_extr
act
        # Gzip and move to archive directory
        /bin/gzip /m1/voyager/local/bin/XcMarcExport/auth_records$FILEDATE.mrc
        mv /m1/voyager/local/bin/XcMarcExport/auth_records$FILEDATE.mrc.gz /m1/voyager/local/bin/XcMarcExport/XcArchive/.
        echo "Finished moving authority record extract!"
else
        echo "It's not Monday, nothing to do"
fi
rm /m1/voyager/local/bin/XcMarcExport/tempfile

echo XcExport finished!
