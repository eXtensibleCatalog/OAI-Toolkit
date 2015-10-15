### Deleted and Replaced Records ###

We need to know which records have been deleted or replaced in your ILS so we can do the same to the Xc repository. Voyager keeps track of them in six separate files located in /m1/voyager/xxxdb/rpt:
  * delete.item
  * deleted.auth.marc
  * deleted.bib.marc
  * deleted.mfhd.marc
  * discard.bib.marc
  * replace.auth.marc
  * replace.bib.marc

The process of moving them is simple. Just stop Voyager, move them to another directory, and start Voyager again. Once Voyager starts back up, it will recreate those files (which is why it is important to stop Voyager before moving them). This is something you may already be doing as part of your routine maintenance for Voyager (we run this once a week). Depending on the volume of records your library processes, you can adjust yours to run more or less frequently.

The XcExport script will look for **del\_rep\_archive**:

```
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
```

Just like we did with **XcExport**, we place the current date into the file name (this will be important for another script that checks to see if the file arrived). The script then moves the files to an archive directory in Voyager’s home directory. Once the files are there, we tar them in a single file archive (except for the item records, which we do not use in our Xc implementation) and compress it with gzip. Note that if you want to tar and gzip in one command, you have to use GNU tar. The version of tar that ships with Solaris does not understand the –z option (zipping).

The script does a cd into the archive directory instead of using the full (or absolute) path to the files in the tar command. If you use the absolute path in the tar command, that path will be recreated when the files are unzipped and utarred on the OAI server.

This script is run as root (unlike the **XcExport** script, which is run as Voyager) since we have to stop and start Voyager. To make sure the ownership of the tgz file is correct, the script will set the owner to Voyager and the group to Endeavor. If this file is owed by root the SCP will fail.

The file is moved to the OAI server by the **XcExport** script. It is done this way for a couple of reasons. First, we do not allow root SSH access on our servers for security reasons. While you can use su to run SCP as Voyager (su – voyager –c) in a script, you get a new shell and lose the $FILEDATE variable. Instead of creating a new script just to move this file over, we added a section in the **XcExport** script to do it. Since that script uses the same variables, it is a perfect fit.

This is the section of code that moves the deleted.$FILEDATE.tgz file:

```
if [ -f `find /export/home/voyager/Archive/ -mtime -3 -name "*.tgz"` ] ; then
        /usr/local/bin/scp `find /export/home/voyager/Archive/ -mtime -4 -name "*.tgz"` voyager@xcdevvoyoai:/import/deleted_records
else
        echo No new file today
fi
```

It does a find in the /export/home/voyager/Archive directory for a file that was created in the last three days and ends with .tgz. If such a file exists, then it is moved to the OAI server. If it does not exist, then the script will echo “No new file today” in the log.

Why does this file use the find command instead of just moving the file directly? Because of timing. The del\_rep\_archive script runs on Saturday morning while the **XcExport** script runs Monday through Friday night. The deleted.$FILEDATE.tgz file will have Saturday’s date in the file name. When the **XcExport** script runs on Monday, it will have Monday’s date in the $FILEDATE variable. If the script checks for the file using the $FILEDATE variable, it will always fail. The find command will always come back with the latest version, but skip over any older files that may be in that directory.

Another way around this is to just have your **XcExport** script run on Saturdays, or to have your del\_rep\_archive script run on a weekday. Alternatively, you could have a directory that contains only the latest deleted.$FILEDATE.tgz file and always move whatever is in it. Once it is moved to the OAI server, you would then have to move that file to another directory. There are many ways to accomplish what these scripts do.