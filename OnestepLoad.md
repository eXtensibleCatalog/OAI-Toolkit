**_There are two options for how the import process of the application can be run, and these are detailed below as_Option A_and_Option B_._Option A_converts the raw MARC to MARCXML and loads the data into a database in one step._Option B_performs the convert, modify, and load processes in separate steps to allow for more flexibility. Some of the pros and cons for each option are discussed below. If your ILS can export MARCXML, you should follow the second half of_Option B_(only the loading step)._**

# _Option A._ Convert, Modify, and Load in One Step #

For this option, the shell script tells the OAI Toolkit to convert, modify, and load the data in a single command. The OAI Toolkit first uses **MARC4J** to process the conversion to MARCXML, handles any errors, then runs the XSLT transformations on the MARCXML, and finally loads the data into the OAI Repository. The potential disadvantage here is that the shell script does not have an opportunity to perform any local cleanup on the data after the raw MARC to MARCXML step, but before the data is loaded in the OAI Repository. The advantage of this option is that the institution does as little work as possible.

Preconditions:
  * The raw MARC files - via the ILS Connector script or manually - have already been moved to the same server that the OAI Toolkit is installed on and are stored in a directory defined by the **-source** parameter of the command (the default source is the marc directory, so if your MARC files are located here, you do not need to set the **-source** parameter).

Call to OAIToolkit to convert, modify, and load the data:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar -convert –modify <marc modification stylesheet> -load -source <sourcedir> -destination <destdir> -destination_xml <destxmldir> -error <errordir> -error_xml <errorxmldir> -log <logdir> -log_detail –marc_schema <marc schema file>  -split_size <split size> -translate_leader_bad_chars_to_zero –translate_nonleader_bad_chars_to_spaces -lucene_index <path of the Lucene index>
```

Example:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar -convert –modify cleanup_records.xsl -load -source marc -destination marc_dest -destination_xml xml_dest -error error -error_xml error_xml -log log -log_detail -marc_schema schema\MARC21slim_rochester.xsd –translate_leader_bad_chars_to_zero –translate_nonleader_bad_chars_to_spaces –lucene_index lucene_index
```

This command would cause the OAI Toolkit to begin by retrieving a directory listing of the contents of the directory named by **-source** (**marc**)`*` and then iterate through the listing to process all the files. Each time a file is processed, it is removed from **-source** (**marc**) and placed, unchanged, into the **–destination** (**marc\_dest**). A file containing MARCXML versions of each input file is placed in the **–destination\_xml** (**xml\_dest**). The purpose of the initial directory listing is to deal with the possibility that additional files may be moved into the **–source** (**marc**) before the OAIToolkit is finished processing the first set. Any files added while processing is in progress will be ignored until the next time the command is called.

_`*` Note: In this section, we use the formula –parameter (default value) to indicate the Toolkit’s default value for a given argument, e.g., –source (marc) means that the “-source” argument uses the “marc” directory by default, unless you choose to modify the scripts._

During the process, the program will write out different messages to the screen and to the log file informing you of the status of the process. At the end of the process, you will see a report of the statistics of the imported records and an “Import finished” message.

You **do not need to type the long command** (as shown in the example boxes above) because there is a **convertmodifyload.bat/.sh** file provided with the Toolkit that launches it automatically. **You can edit this file to change the default values** described in the ["Directory Structure and Workflow"](StructureandWorkflow.md) section.

Postconditions:
  * If all the files that were placed in **–source** (**marc**) have been moved into the **-destination** (**marc\_dest**), and the MARCXML versions appear in **-destination\_xml** (**xml\_dest**), then all the data has been successfully converted to MARCXML and loaded into the OAI Repository. When this is true, the data is immediately available for harvesting.
  * If files appear in **–error** (**error**), then the conversion to MARCXML failed for those records, and the log file will offer more information. (See the ["Handling Errors and Invalid Characters"](ErrorHandling.md) section for more information.)
  * If any files appear in **–error\_xml** (**error\_xml**), then the loading of MARCXML into the OAI Repository failed for those records, and the log will offer more information.