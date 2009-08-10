#!/bin/sh
#
# Archive deleted and replaced marc files
# Updated 11/13/08
#
# This script will move a list of Voyager reports to Voyager's home directory.
#
# Voyager must be stopped in order to move these files
#
# Create start date
# Four digit year format
YEAR="`date +%Y`"
	
# Two digit day format
DAY="`date +%d`"

# Three letter month format
MONTH="`date +%b`"

FILEDATE=$YEAR-$MONTH-$DAY

# Stopping Voyager
/etc/init.d/voyager stop
echo Moving Files....
mv /m1/voyager/rochdb/rpt/delete.item /export/home/voyager/Archive/delete.item$FILEDATE
mv /m1/voyager/rochdb/rpt/deleted.auth.marc /export/home/voyager/Archive/deleted.auth.marc$FILEDATE
mv /m1/voyager/rochdb/rpt/deleted.bib.marc /export/home/voyager/Archive/deleted.bib.marc$FILEDATE
mv /m1/voyager/rochdb/rpt/deleted.mfhd.marc /export/home/voyager/Archive/deleted.mfhd.marc$FILEDATE
mv /m1/voyager/rochdb/rpt/discard.bib.marc /export/home/voyager/Archive/discard.bib.marc$FILEDATE
mv /m1/voyager/rochdb/rpt/replace.auth.marc /export/home/voyager/Archive/replace.auth.marc$FILEDATE
mv /m1/voyager/rochdb/rpt/replace.bib.marc /export/home/voyager/Archive/replace.bib.marc$FILEDATE
echo Finished moving files

# Starting Voyager
/etc/init.d/voyager start

# Move to Archive directory
cd /export/home/voyager/Archive

echo Tar and zipping files
/usr/local/bin/tar cvzf deleted.$FILEDATE.tgz *marc$FILEDATE

echo Changing ownership
chown voyager:endeavor *$FILEDATE.tgz

echo Archive_marc finished
