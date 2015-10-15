# XC OAI Toolkit Aleph 18 Sample Scripts #

Scripts are included to perform the following from an Aleph 18 instance to the OAIToolkit:
  1. Full MARC Record Export from Aleph 18 and load into XC OAIToolkit
  1. Delta MARC Record Export from Aleph 18 and load into XC OAIToolkit
  1. Full Deleted MARC Record Export from Aleph 18 and load into XC OAIToolkit
  1. Delta Deleted MARC Record Export from Aleph 18 and load into XC OAIToolkit

In order to run correctly, some configuration is necessary.
At the minimum, the folowing steps need to be taken:
  1. Install and run the scripts on the machine with Aleph install
  1. There must be a local Perl installation on the Aleph Server
  1. Perl Module MARC::Record must be installed on the Aleph Server (can be obtained [here](http://www.cpan.org/))
  1. Have JVM version 6 or greater installed where OAIToolkitImporter will run
  1. Have Ant version 1.7 or greater installed

### OAIToolkit Importer Install and Configuration: ###

These scripts expect the latest copy of the OAIToolkit Importer to be installed on the Application server where the OAIToolkit Server is installed.

It is also assumed that if they are not the same server, there is a mounted drive on the Aleph server that points to the application server
drive space where the OAIToolkitImporter is installed, and to the lucene target directory if you will be using lucene. The OAIToolkit Server and the OAIToolkitImporter should also be configured to point to the same MySQL instance. Of course, alternative configurations are possible, but it would require some modification of the scripts included.

### Aleph Configuration: ###

You need to add an entry to tab\_fix for your Aleph biblibrary that you will be loading from. The fixroutine should call fix\_doc\_001.
These scripts expect the fixroutine name ADD01, but it can be called anything. The script just needs to be updated to reference the
correct name. This will add the system numbers to the 001 field for exported MARC records.

### Scripts Configuration: ###

Before building the scripts you will want update various paths in the csh scripts to mirror your environment. The runtime/install
location of the scripts, in particular, must be correct. You will also want to update the biblibrary mentioned in the aleph\_export\_full\_oai\_toolkit.csh and aleph\_export\_deltas\_to\_oai\_toolkit.csh scripts. You also need to confirm that the location of the OAIToolkitImporter and lucene target directory paths are correct.

`*****`
**You must make sure XC\_HOME environment variable is set in the user's environment that will execute the scripts**
`*****`

Also, at a minimum you should check and update the following environment variables in environment.csh:

|biblibrary1|You should change this to the appropriate bib library.|
|:----------|:-----------------------------------------------------|
|fix\_routine\_add\_sys\_no\_to\_01|The name of the fixroutine created above to put the system number into 001|
|xc\_oai\_toolkit\_importer\_dir|OAIToolkit Importer install location                  |
|java\_bin\_dir|Java 6 bin directory                                  |
|oai\_toolkit\_importer\_data\_dir|Data Directory for running the OAIToolkit Importer (lucene files will be placed in **$oai\_toolkit\_importer\_data\_dir/lucene**)|

### Build and Deploy: ###

  * Run ant dist to build the zip file for the OAIToolkit Importer shell scripts
  * SCP the tar ball created in the dist directory to your Aleph server
  * Extract the contents to a temporary directory and run the following to deploy the files to the correct location:

```
chmod + deploy.pl
deploy.pl [destination_dir] [deploy_person_initials]
```

For example:
```
deploy.pl /home/aleph18/xc rpj
```

The deploy script will automatically copy all files to the destination directory while maintaining the folder hierarchy in the tarball. Before overwriting any files, it will create a copy of pre-existing files in an archive directory. When creating the archive copy, it will add the initials supplied and the date/timestamp of the archive.

### Execution Full Load: ###

```
csh -f [install_location]/bin/aleph_export_full_oai_toolkit.csh
```

The delta load is split into two parts as deleted items need to be loaded separately. They would most likely be run daily following one or infrequent full load....

### Execution Delta Load (Add/Modify): ###

```
csh -f [install_location]/bin/aleph_export_deltas_to_oai_toolkit.csh
```

### Execution Delta Load (Delete): ###

```
csh -f [install_location]/bin/aleph_export_deleted_deltas_to_oai_toolkit.csh
```

There is also a script to load all deleted items from Aleph. This will probably only be used if data on the XC side cannot be completely refreshed and many deleted items are missed. Normally, the full load script would be run once and then the two delta scripts would be cronned to run daily.

### Execution Load All Deleted Items: ###

```
csh -f [install_location]/bin/aleph_export_deleted_full_oai_toolkit.csh
```