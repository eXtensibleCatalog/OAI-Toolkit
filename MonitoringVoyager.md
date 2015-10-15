# Monitoring #

It is important to set up some sort of mechanism in place to monitor both XcExport and archive\_marc to make sure the files are actually getting to the OAI server. Since both of those files are scheduled via cron, you should have a log file to record what is going on when the script is running. Here (again) is the crontab entry for XcExport:

```
05 23 * * 1-5 /m1/voyager/local/bin/XcMarcExport/XcExport > /m1/voyager/local/cronlog/XcExport.txt 2>&1
```

The log file, XcExport.txt, gets overwritten every day. You could choose to append to this file instead of overwriting by using “>>” instead of ” >.” We felt this was not necessary because of the scripts that we have on the OAI server (which we will get to in the next paragraph). While the log files are nice, it still makes checking to see if the files actually arrived a manual process.

Instead of having to check two log files every day, we wrote two scripts that will check to see if the files arrived. If they did not, then the OAI server will send you an email (both these scripts run from the OAI server). This is the script that checks for XcExport’s file **xcexport\_monitor\_script**:

```
#!/bin/sh
#
# This script will check to see if the BIB and Authority file extract arrrived
#Create date formatYEAR="`date +%Y`"

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
```

We use the familiar $FILEDATE variable for consistency and simplicity. The script then does a file check to see if that file exists. If it does, then it will echo “XcExport file arrived” into the log file. If the file has not arrived, then the script will send out an email.

The script to check for the deleted/replaced file export works a little differently. Since it is created three days before it is actually moved, we cannot use the $FILEDATE method like the xcexport\_monitor\_script does. We instead have to use the find command in the same way the XcExport script does **delrep\_monitor\_script**:

```
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
```

Here is the script to monitor the Authority record import **Auth\_Monitor\_Script**:

```
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
```

Timing is important in making these scripts work properly. We schedule these scripts to run 15 minutes after XcExport. That gives plenty of time for the exported files to arrive (it usually only takes a minute or two). Also, make sure that they are scheduled to run on the same days as the scripts they are monitoring. For us, delrec\_monitor\_script and Auth\_Monitor\_Script runs once a week on Monday while **xcexport\_monitor\_script** runs Monday through Friday.

Finally, make sure that both your Voyager and OAI server are using NTP or some other time synchronization mechanism. The system times for both servers need to be in sync with each other. Otherwise, you might end up with a monitoring script going off before the script that is supposed to generate and move the file.

These scripts are provided as a convenience for keeping your Xc repository up to date. As mentioned before, there are many ways to accomplish what these scripts do. It is ultimately up to you and your institution to determine the best way.

**Putting It All Together:**

Now that all the elements are working, put your **XcExport** and del\_rep\_archive (if you do not already have one) scripts in place. If XcExport does not work correctly, comment out everything except for the **marcexport**. If that works, then enable the SCP portion. Keep going until you find the piece that is not working. Run it manually a few times to be sure it works properly before scheduling it as a **cron** job.