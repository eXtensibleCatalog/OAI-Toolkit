**_There are two options for how the import process of the application can be run, and these are detailed below as_Option A_and_Option B_._Option A_converts the raw MARC to MARCXML and loads the data into a database in one step._Option B_performs the convert, modify, and load processes in separate steps to allow for more flexibility. Some of the pros and cons for each option are discussed below. If your ILS can export MARCXML, you should follow the second half of_Option B_(only the loading step)._**

# _Option B._ Convert, Modify, and Load in Separate Steps #

With this option, the script first uses the command line interface to convert raw MARC file(s) into MARCXML. Then, if necessary, another command is issued to perform any additional changes to the MARCXML files(s). Finally, it uses the command line interface to load the MARCXML file(s) into the OAI Repository. A library may have a need to clean up records in cases where this was not possible prior to **MARC4J** conversion. However, the goal of this step is simply to produce valid MARCXML records. (The XC Metadata Services Toolkit will allow institutions to further clean up their data later in the process.) The purpose of Option B is to allow the institution to have the opportunity to make some changes after the conversion from raw MARC to MARCXML, but before the loading of the data into the OAI Repository. For example, the script could do various things to the MARCMXL data, including using **MARC4J** to do additional cleanup, calling a custom application such as a PERL script to automatically perform cleanup, or allowing a person to perform manual cleanup.

For this option, three individual shell scripts tell the OAI Toolkit to process the data, then to perform local processing (independently from OAI Toolkit), and, finally, to load the data, respectively.

## Convert: ##

Preconditions:
  * The files have already been moved to the same server that the OAI Toolkit is installed on. They are stored in a directory called **-source** (**marc**).

Call to OAIToolkit to convert the data from raw MARC into MARCXML:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar -convert -source <sourcedir> -destination <destdir> -destination_xml <destxmldir> -error <errordir> -error_xml <errorxmldir> -log_detail
```

Example:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar -convert -source marc -destination marc_dest -destination_xml xml -error error -error_xml error_xml -log_detail
```

This command would cause the Toolkit to begin retrieving a directory listing of the contents of **–source** (**marc**), and then iterate through the listing to process all the files. Each time a file is processed, it is removed from **–source** (**marc**) and placed, unchanged, into **–destination** (**marc\_dest**). A file containing MARCXML versions of each input file is placed in **–destination\_xml** (**xml\_dest**). The purpose of the initial directory listing is to deal with the possibility that additional files may be moved into **–source** (**marc**) before the OAIToolkit is finished processing the first set. Any files added while processing is in progress will be ignored until the next time the command is called.

Again, you don not need to type the long command to initialize this step of _Option B_ because there is a **convert.bat/.sh**  file provided with the Toolkit that launches this command on its own. You can edit this file if you want to change the default values described in the ["Directory Structure and Workflow"](StructureandWorkflow.md) section.

Postconditions:
  * If all the files that were placed in **–source** (**marc**) have been moved into the **–destination** (**marc\_dest**), and the MARCXML versions appear in **–destination\_xml** (**xml\_dest**), then all the data has been successfully converted to MARCXML. Because the command did not use the **–load** flag, the data has not yet been loaded into the repository.
  * If some files appear in **–error** (error), then the conversion to MARCXML failed for those files, and the log file will offer more information.

## Modify: ##

The Modify step is optional. If your institution does not require modification of the MARCXML records from the **Convert** step, then you can skip this section and go to the **Load** step.

The Modify step reads the MARCXML and transforms it with the help of one or more XSLT stylesheets. modify.sh/bat will look for MARCXML records in the dest\_xml directory, perform any directives according to the stylesheet, and then put the modified records in the modified\_xml directory.

Call to OAIToolkit to modify the MARCXML records:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar –modify <xslt files> -source <xml directory> -destination <destination xml directory> -log_detail
```

Example using one XSLT file:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar –modify process.xsl -log_detail
```

Example using multiple XSLT files (note the quotation marks and note the space between the file names):

```
shell> java -jar lib\OAIToolkit-1.0.1.jar –modify ”one.xsl two.xsl” -log_detail
```

The Modify step looks for stylesheet files in the xslt directory. Do not use the full path to indicate the files; put the xslt files into the xslt directory.

You do not need to type the long command to initialize this step of _Option B_ because there is a **modify.bat/.sh**  file provided with the Toolkit that launches this command. You can edit this file if you want to change the default values described in the ["Directory Structure and Workflow"](StructureandWorkflow.md) section.

## Load: ##

The final step is to call OAIToolkit again as follows to load the data into the repository.

Preconditions:
  * The files to be processed are stored in the directory named in **–source** (**xml**)

Call to OAIToolkit to load the data into the OAI Repository:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar -load -source <xml directory> -destination_xml <destination xml directory> -error_xml <error xml directory> -log_detail -marc_schema <XML schema file> -lucene_index <path of the Lucene index>
```

Example:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar -load -source xml -destination_xml xml_dest -error_xml error_xml -log_detail -marc_schema schema\MARC21slim_rochester.xsd –lucene_index lucene_index
```

As in the cases of the Convert and Modify commands above, you do not need to type the long command to initialize this step of _Option B_ because the Toolkit provides a **load.bat/.sh** file that launches this command on its own. You can edit this file if you want to modify the default values described in the ["Directory Structure and Workflow"](StructureandWorkflow.md) section.

Postconditions:
  * If all the files that were placed in **–source** (**xml**) have been moved into the **–destination\_xml** (**xml\_dest**) directory, then all the data has been successfully loaded into the OAI Repository. When this is true, the data is immediately available for harvesting by the XC Metadata Services Toolkit.
  * If some files appear in **–error\_xml** (**error\_xml**) directory, then the loading of MARCXML into the OAI Repository failed for those files, and the log will offer more information.