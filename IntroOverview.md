# ILS Connector #

## Voyager – University of Rochester ##

### Introduction ###

As described in this manual, data needs to be extracted from a commercial ILS and made available to the OAI Toolkit, and this is accomplished via the ILS Connector.  This section will detail how the University of Rochester accomplishes this task using the Voyager ILS.

This ILS Connector can be used to produce a bulk export of records or to incrementally extract updated records based on date ranges. Voyager has a tool called **marcexport** that can be used to export updated and newly created records. This section will describe the process of automating **marcexport** and moving the MARC records to your OAI server location. The only requirement, besides Voyager, is to have OpenSSL and OpenSSH installed on both your Voyager server and OAI server.

Note that this section will refer to both Voyager (capital “V”) and voyager (lower-case “v”). Voyager with a capital “V” refers to the entire Voyager ILS.  Lower-case “v” voyager refers to the Unix user, “voyager”.

This document represents only one way of automating the MARC export process to your OAI server. There are other ways to accomplish the same thing and it will be ultimately up to you to decide which method is the best for your institution.  We recommend that you try to use ILS supported mechanism(s) to accomplish this instead of direct database calls.

### Overview ###

The process of exporting MARC records and moving them is controlled by a shell script called **XcExport**. **XcExport** will:
  1. Get the current date to use as part of the filename for the exported records file
  1. Launch Pmarcexport (marcexport’s executable file) with the proper parameters
  1. Copy the exported records file to the OAI server
  1. Compress the exported records file and move it to an archive directory
  1. Remove the original exported records file
  1. Check to see if any deleted records need to be moved to the OAI server
  1. Will export authority records once a week in a separate process

Note that we have **XcExport** set up to run once a day as a **cron** job. Since it only runs once a day, we use the **today-0** option as the date range.

### Parameters for Marcexport ###

Voyager’s **marcexport** extracts the records from the database and puts them into a single file with a .mrc extension. This section will describe the parameters we use. There are different options available for this program, which are detailed in Voyager’s _Technical User’s Guide_ for the version of Voyager that you’re using.

The entire command is:

```
/m1/voyager/rochdb/sbin/Pmarcexport -o/m1/voyager/local/bin/XcMarcExport/updates$FILEDATE.mrc -rB -mB -ttoday-0 -aM
```

**Pmarcexport** is the name of the executable file (not to be confused with **marcexport**, which is the name of the process). Note that the generic **xxxdb** is used only for documentation purposes, and you would replace **xxxdb** with whatever your institution uses as its Voyager instance.

|-o|Output - the first option (–o) is for the output filename. This is optional; if not specified the output file will be  marc.exp.yyyymmdd.hhmm with yyyy = year, mm = month, dd = day, hh = hour and mm = minutes that the script is run. It will also be placed in /m1/voyager/xxxdb/rpt. This default name is a little cumbersome, so we opt to give it our own name.  $FILEDATE is a variable created by XcExport which is the current date in a nicer format. Also, by using the –o option, we get to put the output file into our current working directory. |
|:-|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|-r|Record type - option (-r) is the record type. In this example, we are exporting bibliographic records, so we use the B parameter with the –r option (–rB).                                                                                                                                                                                                                                                                                                                                                                                                       |
|-m|The –m or mode option lets you pick which records you want to export. We use the B parameter for both created and updated records. That means that any records created or updated for the date range passed to marcexport will be exported. If you just wanted updated records, you could the U parameter. If you wanted newly created records only, you would use the C parameter.  We want both, so we use the B parameter (-mB).                                                                                                                              |
|-t|The –t option is the target date range. We use the today-0 parameter, which means export any record either updated or created on the same day marcexport is run. You can use a date range instead of the today-0 parameter. The date range must be in this format: `yyyy-mm-dd:yyyy-mm-dd`                                                                                                                                                                                                                                                                       |
|-a|The last parameter (-a) is character mapping. We use the M option, which is MARC21 MARC-8.                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |

As mentioned in the Overview, the **XcExport** script actually launches **Pmarcexport**. **XcExport** is run once a day at 11:30 PM as a **cron** job. This will ensure we catch all records created and updated for the entire day.

As a test, run **Pmarcexport** with the settings shown below to make sure it works properly:

```
/m1/voyager/xxxdb/sbin/Pmarcexport -oupdates.mrc -rB -mB -ttoday-0 -aM 
```