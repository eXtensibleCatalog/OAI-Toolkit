# Import Process Explained in Detail #

## Introduction ##

There are two basic steps for moving data into the OAI Toolkit application: converting raw MARC into MARCXML, and loading MARCXML into the repository. The command line interface is defined below:

```
shell> java -jar lib\OAIToolkit-1.0.1.jar
	[-convert]
	[-modify <xslt stylesheet(s)>]
	[-load]
	[-production]
	-source <source directory>
	-destination <destination directory>
	[-destination_xml <xml destination directory>]
	[-error <error directory>]
	[-error_xml <xml error directory>]
	[-log <logfile path>]
	[-log_detail]
	[-marc_schema <marc schema file>]
	[-split_size <maximal number of records in a MARCXML file>]
	-lucene_index <path of the Lucene index>
	[-indent_xml]
	[-default_repository_code <repository code>]
	[-delete]
	[-translate_leader_bad_chars_to_zero]
	[-translate_nonleader_bad_chars_to_spaces]
	[-modify_validation]
```

_Note: For the sake of easy reading, this example and those that follow show a single line command on multiple lines. Please note that the whole command would be written in one line._

## Argument descriptions: ##

|-convert|Flag to convert file(s) with raw MARC records into MARCXML. One of **convert**, **modify**, or **load** is a required argument.|
|:-------|:------------------------------------------------------------------------------------------------------------------------------|
|-modify `<stylesheet(s)>`|This step transforms the MARCXML files with XSLT stylesheets. We created some sample stylesheets, but you can create your own XSLT files. You can also apply multiple files. The order or transformation will be the same as the order in the command line parameter. You can give one stylesheet, like **-modify my\_stylesheet.xslt**, or multiple stylesheets, like **-modify "one.xslt two.xslt three.xslt."** If you apply multiple stylesheets, you must use quotation marks around the list and separate each file with a space. One of **convert**, **modify**, or **load** is a required argument.|
|-load   |Flag to load file(s) into the OAI repository. One of **convert**, **modify**, or **load** is a required argument.              |
|**-production** (optional))|Flag to process MARC and MARCXML in memory only, which will not create files. Without this flag, the **convert** step creates MARCXML files, and **modify** transforms them into other files. You can save the file reading and writing time and resources if the Toolkit works in memory. The process will be quicker, but this flag is effective only if you chain at least two steps (convert, modify, load) together.|
|**-source `<directory>`** (required))|The directory where the toolkit looks for files to process                                                                     |
|**-destination `<directory>`** (required)|The directory that the toolkit moves the source files into as it successfully completes the processing of each file            |
|-destination\_xml `<directory>`|The directory into which the toolkit places MARCXML versions of the source data. Required if you use **convert argument**.     |
|**-error `<directory>`**(optional))|The directory that the toolkit moves files into when there is a processing error for that file. The default is “error.”        |
|**-error\_xml `<directory>`** (optional))|The directory into which the toolkit places MARCXML versions of the source data, if that MARCXML file was unable to be loaded into the OAI repository due to an error. The default is “error\_xml.”|
|**-log `<directory>`** (optional))|The directory of log files for warnings and errors. The default is “log.”                                                      |
|**-log\_detail** (optional))|Setting this flag will produce more detailed log files. Note that doing so will increase the size of the log files.            |
|**-marc\_schema `<filename>`** (optional))|A custom schema file (.xsd) to validate the MARCXML file. The default schema is located [here](http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd). If you do not add this parameter, the OAIToolkit will use the default schema file.|
|**-marc\_encoding** (optional))|This parameter is passed to the **MARC4J** when the raw MARC record is read. Ideally, this parameter should have the value “utf8.” If you want the records to be read as a different format, you can change this.|
|**-char\_conversion** (required))|Records are converted from their current format to the specified value of this parameter. The recommended parameter is “ISO5426.” This ensures that, after the conversion process in the OAI Toolkit, the characters are not in the form of `<U+number>`, which could be the case if this value were not used.|
|**-split\_size `<integer>`** (optional))|The maximum number of records an XML file can contain. The default value is 10,000. If this argument is not explicitly set, or if it is set to a number greater than zero, the Toolkit creates multiple MARCXML files from a single MARC file, and the split\_size governs the maximal number of record in each files.  If you want your MARCXML files to contain the same number of records as the original MARC files, give a non-positive number or zero as a value, like **–split\_size 0**.|
|**-default\_repository\_code `<repository code>`** (optional))|The OAI toolkit supports consortia data. In order to distinguish between institutions, a repository code (the MARC 003 field) is used. You can either have this set in the actual MARC input records, or use this parameter to supply the respository code.|
|**-lucene\_index `<directory>`** (required))|The directory of the Lucene index files                                                                                        |
|**-indent\_xml** (optional))|Flag to create indentations in XML to make it more readable (note that indentation requires 10-15% more space). If you do not add this flag, the XML will not contain indentations.|
|**-translate\_leader\_bad\_chars\_to\_zero** (optional))|Replace ASCII control characters with zero (0) in Leader                                                                       |
|**-translate\_nonleader\_bad\_chars\_to\_spaces** (optional))|Replace ASCII control characters with space ( ) in the non-Leader fields                                                       |
|-delete |Flag to delete files created after processing. This does not have the same function as **-production**, the difference being that **delete** allows the OAI Toolkit to create files, but, after processing the content, it deletes them. For example, it can delete all the marc-xml files from **convert** process after they have been loaded in the mySQL/Lucene index. This saves space.|
|-fileof\_deleted\_records|Flag to process files as though they were deleted. It stores the records imported with this flag as deleted in the Lucene/mySQL database. Ready sample script files have been written for this feature.|
|-lucene\_statistics|Flag to get the statistics of the records stored in the Lucene Database. Since there is no direct way to see the count of the various types of records, including the deleted ones, this parameter facilitates that. The **lucene\_dbStatistics.bat/sh** script has been written for this purpose, logging the results in **lucene\_dbStatistics.log** file.|
|**-modify\_validation** (optional))|Flag to allow schema validation during the **modify** step (the default is to not validate, since validation will always occur during **load** step)|

_This table does not contain all parameters concerning the directory structures. See the ["Directory Structure and Workflow"](StructureandWorkflow.md) section for details._