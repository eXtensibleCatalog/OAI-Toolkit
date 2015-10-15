_The rest of the installation procedures are for both **Windows** and **Linux** servers_

# OAI Toolkit Software #

The next step is to download the OAI Toolkit software packages (importer and server), install them, and edit their configuration files.

## Download OAI Toolkit Importer and Server Packages ##

The OAI Toolkit files need to be downloaded from the OAI Toolkit Google code [website](http://code.google.com/p/xcoaitoolkit/downloads/list).

In the Downloads section, there are three files to look for:
  1. **OAIToolkit-1.0.0-importer-windows.zip** (importer package for Windows)
  1. **OAIToolkit-1.0.0-importer-linux.zip** (importer package for Linux)
  1. **OAIToolkit-1.0.0-server.zip** (server package for Windows and Linux)

First, download either the Windows or Linux importer package. This file will be referred to as **OAIToolkit-1.0.0-importer-`<OS>`.zip**, where
**`<OS>`** refers to the Windows or Linux version.  Next, download the cross-platform server package. This file is the same for both operating systems.

Note that the difference between the Windows and Linux versions of the importer is in the executable scripts. In the Windows version, the scripts are batch files, while in the Linux version, they are .sh shell scripts.

## Extract the OAI Toolkit Importer and Server Packages ##

Extract both the **OAIToolkit-1.0.0-importer-`<OS>`.zip** and **OAIToolkit-1.0.0-server.zip** to any convenient directory. **Make sure that they are both extracted to the same directory.**

**Windows:** Extract both to **c:\** so that the software will reside in **c:\OAIToolkit**

**Linux:** Extract both to **/** so that the software will reside in **/OAIToolkit**

## Directory Structure ##

The main purpose of the **OAIToolkit** directory is to create a place for the input MARC files, the converted MARCXML files, the error records, and the logs to be stored. After unzipping **OAIToolkit-1.0.0-importer-`<OS>`.zip** and **OAIToolkit-1.0.0-server.zip**, the internal structure of the directory will be:

**Windows: c:\OAIToolkit**

**Linux: /OAIToolkit**

Note that this assumes the OAIToolkit was unpacked at the root of your file system. If you decided to unpack it in another location, please keep that location in mind when reading through the rest of these sections.

```
  |- [docs]                       // manual, Java documentation
  |- [lib]                        // Java libraries
  |- [marc]                       // empty, later the place of MARC files
  |- [resources]		     // file for XML handling
  |- [schema]                     // MARCXML schema file
  |- [server_logs]		     // log files from the OAI server app.
  |- [sql]                        // database schema files
  |- [xslt]                       // XSLT stylesheets
  |- [tomcat]		     // files copiable to Tomcat Server
  |  |- [bin]		     // files need to be copied to tomcat/bin
  |  o- [webapps] 		     // files need to be copied to tomcat/webapps
  |- convert.sh
  |- convertload.sh
  |- convertload_as_deleted.sh	
  |- load.sh
  |- create_db.sh
  |- create_user.sh
  |- extract_error_recordno.sh
  |- load_as_deleted.sh
  |- lucene_dbStatistics.sh
  |- OAIToolkit.xcoaiid.properties  // file for creating the XC OAI ID
  |- OAIToolkit.log4j.properties
  o- OAIToolkit.db.properties
```


**Windows:**  You will have files such as **convert.bat**, (`*`.bat), etc., instead of the shell (`*`.sh) files, but the rest of the directory structure will be the same.

**Linux:** Before running, you will need to make all of the `*`.sh files executable with the following command:

```
chmod +x *.sh
```

The only modifiable directory here is **marc**, so do not modify the content of other directories. This **marc** directory will serve as the place where you will put the incoming raw MARC files. The location of the incoming **marc** folder is chosen at the discretion of each institution, but if your institution places the **marc** folder elsewhere, then you will need to modify the executable scripts’ **marc** parameter (**Linux** users see **convert.sh** and **convertload.sh**; **Windows** users see **convert.bat** and **convertload.bat**). If you can export MARCXML files from your ILS, either put those files into the **xml** directory or make the load (**Windows: load.bat**, **Linux: load.sh**) script’s **source** parameter point to the directory where you put those files. Be sure to keep a copy of the data (if you anticipate using it in the future) before you point to the directory where the files are, as the OAI Toolkit deletes the files from the source folder after processing.