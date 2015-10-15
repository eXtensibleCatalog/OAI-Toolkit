### XcExport ###

**XcExport** is a simple shell script that runs the entire export process. The script is show below:

```
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
 
/usr/local/bin/scp /m1/voyager/local/bin/XcMarcExport/updates$FILEDATE.mrc voyager@xcvoyoai:/import/marc_extract 

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

echo "- - - - - - - - - - - - - - - - - - - - - - - - - - - "
```

The **PATH** environmental variable should not be necessary since the script uses full paths to all files. However, having PATH information does not hurt, and if the script is not working, it is a good first step to try. Please note that the path to some of your executable files like scp and tar may be different on your server. Please customize the script accordingly for your environment.

**XcExport** creates a variable called **FILENAME**, which is the current date in a nice (yyyymmm-dd) format. **Pmarcexport** is executed and produces an output file called **updates$FILENAME.mrc** (for example, **updates2008-05-21.mrc**).

The output file is then copied to the OAI server via secure copy (details on setting this up are in the next section). Since a copy of the file is moved, we need to do something with the original. Our **XcExport** script **zips** the output file (using gzip), copies it to a directory called **XcArchive**, and then deletes the original output file.

**XcExport** will also check for any new deleted/replaced records. We have a routine set up where, once a week, any deleted/replaced records are combined in a tgz file (see ["Deleted and Replaced Records"](AlteredRecords.md) section for more details). Each time **XcExport** runs, it checks for a recent version (4 days or less old). If it finds one, it copies it to the OAI server.

Finally, **XcExport** will extract and move Authority records. The records extracted in the first part of the script are Bibliographic and Holding records. Unfortunately, Pmarcexport does not allow the extraction of all three record types at the same time.

We use the same parameters with Pmarcexport except for two:
  * -rA
  * -ttoday-7

The “A” part of –rA tells Pmarcexport to export Authority records. The –ttoday-7 tell it to export Authority records from the last seven days. Feel free to adjust the time interval to what best suits the needs of your institution.

The **XcExport** script checks to see what day of the week it is. If it is a Monday, the script will run Pmarcexport and copy the files to the OAI server. Like the Bibliographic and Holding records, the Authority records extracted will then be gzipped and moved to an archive directory. Tuesday through Thursday, the Authority record section of **XcExport** will be ignored.

The **XcExport** script is owned by the user Voyager and the group Endeavor. It also needs to have executive privileges as shown below:

```
-rwxr--r--   1 voyager  endeavor    1806 May 23 10:25 XcExport 
```

The script can live anywhere as long as Voyager can write to the directory to create the output file. We like to keep customized scripts that interact with Voyager in the /m1 directory structure.

If you want to have this script run as a **cron** job (which is recommended), it should to be run from Voyager’s **crontab**. Schedule it at the end of the day when no one will be updating or adding records to ensure that the script covers them all. **XcExport** only takes about a minute to run, so it can be scheduled close to midnight. For troubleshooting purposes, it is also recommended that you have a log file for the **XcExport cron** job. The entire **crontab** entry is shown below:

```
05 23 * * 1-5 /m1/voyager/local/bin/XcMarcExport/XcExport > /m1/voyager/local/cronlog/XcExport.txt 2>&1
```