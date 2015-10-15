# Editing the OAIToolkit Configuration Files #

A configuration file is a plain text file where the information is stored as **`<key>`=`<value>`**.

There are three configuration files needed for the importer:
  * **OAIToolkit.db.properties** (for the database settings)
  * **OAIToolkit.log4j.properties** (for the logging)
  * **OAIToolkit.xcoaiid.properties** (for constructing the format of the XC OAI ID).

## Database Settings (OAIToolkit.db.properties) ##

You should customize the following values:

|db.host|The host’s name or IP address, the location of the database (default: “localhost”)|
|:------|:---------------------------------------------------------------------------------|
|db.port|The port of the MySQL (the default port is 3306, but it can be changed)           |
|db.database|The name of the database. When you create the OAI Toolkit database using the provided sql script, it will be called “extensiblecatalog.” The database is created as a part of the installation process which is described in a later step.|
|db.user|The user who has privileges to access and modify the database (see the ["Create a New MySQL User" section](DatabaseSetup.md))|
|db.password|The password of that user (see the ["Create a New MySQL User" section](DatabaseSetup.md))|

## Logging (OAIToolkit.log4j.properties) ##

The logging of the application is based on the popular [log4j](http://logging.apache.org/log4j/1.2/index.html) logging system. The **OAIToolkit.log4j.properties** file defines what to log (by “loggers” - properties starting with **log4j.logger**), and where and how to log it (by “appenders” - properties starting with (log4j.appender**). The most useful lines in the configuration file are:**

```
log4j.rootLogger = INFO, console, toolkit
log4j.logger.librarian_convert = DEBUG, librarian_convert
log4j.logger.librarian_load = DEBUG, librarian_load
log4j.logger.programmer = DEBUG, programmer
log4j.logger.lucene_dbStatistics = DEBUG, lucene_dbStatistics
```

If you want to record more or less detailed information in the logs, you must modify these lines first. The first line says that the rootLogger is the **console** and the **toolkit** log files. These logs will basically log every line of each of the other individual log files. There are five log files used for logging:

  * **Lucene\_dbStatistics.log**
> > This logger is used to log the output of the script that finds the number of records in the Lucene Database at any given point in time. A special script called “lucene\_dbStatstics.sh” for Linux and “lucene\_dbStatistics.bat” for Windows has been written. When a user runs these scripts, the output is logged with the help of this logger.
  * **Librarian\_convert.log**
> > This file is very useful for librarians who are trying to find the location of errors in the raw MARC files after the convert process. Errors in this log file can be used to identify problems and correct source record data.


> _We are working on improving the logging for this step by trying to solve some of the bugs in **MARC4J**. We are thereby participating in the **MARC4J** open source code project. We are changing the source code and committing the same back to the open source **MARC4J** code._
  * **Librarian\_load.log**
> > This file is very useful for librarians who are trying to find the location of errors in the MARC-XML files after the loading process. Errors in this log file can be used to identify problems and correct source record data.
  * **Programmer.log**
> > This file is typically used by programmers who would like to see the Debug/Info statements and gain a general knowledge the processing of the OAI Toolkit. It also detects errors, if there are any, in the OAIToolkit.
  * **Toolkit.log**
> > This file duplicates all of the statements from the librarian\_convert.log, librarian\_load.log and the programmer.log files into one single file.

The **INFO** parameter means that all messages with a level of **INFO** or greater are printed to the log file. Each logger can be assigned a level based on the significance of the logging messages desired. The levels have a priority order: **FATAL**, **ERROR**, **WARN**, **INFO**, and **DEBUG**, from highest to lowest. This means that if we change the level from **INFO** to **ERROR**, only the **FATAL** and **ERROR** messages will be printed out. In the development state, it is suggestible to set logging to a low level, and then to change to a higher value in the production environment.

**console** and **toolkit** are appenders, which define where and how the messages should be printed out. **console** is a **ConsoleAppender**, which writes out to the standard output, and **toolkit** is a **DailyRollingFileAppender**, which writes out to a daily log file (every day it will automatically open a new log file). In the **ConversionPattern**, you can formulate a special output format. This conversion pattern can be built from a number of different specifiers (similar to sprintf patterns), a few of which are shown below:

|%d|Date|
|:-|:---|
|%t|Thread|
|%F|File|
|%L|Line number in the source code|
|%-5p|Depth|
|%c|Class|
|%m|Message|
|%n|New line|

More information can be found in the [“Short Introduction to log4j” manual](http://logging.apache.org/log4j/1.2/manual.html).

## XC OAI ID (OAIToolkit.xcoaiid.properties) ##

The creation of the **XC OAI ID** takes place in the convert step of the import process in the OAI Toolkit. The structure of the XC OAI ID is set or configured from this file. Some sample structures of the OAI ID would be:
  * **oai:library.rochester.edu:URVoyager1/0**
  * **oai:cornell.edu:CSVoyager3/4**

The first part, “library.rochester.edu,” is defined in the **oaiIdentifierDomainName**, the second part, “URVoyager1,” is defined in the **oaiIdentifierRepositoryIdentifier**. An institution must configure both of these parameters in this configuration file. The XC OAI ID will be created per record and will be persistent in the MySQL/Lucene database (depending on which type of database was chosen during installation).

Now copy **OAIToolkit.log4j.properties**, **OAIToolkit.xcoaiid.properties**, and **OAIToolkit.db.properties** to **OAIToolkit/tomcat/bin**.

## Copy Specified Service Package Files to Tomcat Directories (For OAI Toolkit Server) ##

**Windows: C:\OAIToolkit\tomcat\bin**

**Linux: \OAIToolkit\tomcat\bin**

**CATALINA\_HOME** refers to the Apache Tomcat installation directory on the machine. To find out where your **CATALINA\_HOME** is, input the following command:

**Windows:**
```
C:\> echo %CATALINA_HOME%
```

**Linux:**
```
C:\> echo $CATALINA_HOME
```

In **CATALINA\_HOME\bin**, there should be five properties files. Three of them we have already configured in the steps above. We will now set up the last two properties files:
  * **OAIToolkit.directory.properties** - Basic parameters; the default directory paths are set for the Windows operating system and must be adjusted for Linux
  * **OAIToolkit.server.properties** - Base parameters for the OAI responses (mainly for the Identifier verb and the size of the lists)

Copy all five properties files from the **OAIToolkit\tomcat\bin directory** to **CATALINA\_HOME\bin** directory.

Finally, you will need to install the OAI server application itself.  To do so, copy the **OAIToolkit\tomcat\webapps\OAIToolkit.war** file to the **CATALINA\_HOME\webapps** directory.