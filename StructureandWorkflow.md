# Directory structure #

|_Step/Property Description_|Argument|Default Directory|Workflow Image|
|:--------------------------|:-------|:----------------|:-------------|
|Convert source             |-source |marc             |1             |
|Destination of MARC files  |-destination|marc\_dest       |3             |
|MARC error files           |-error  |marc\_error      |2             |
|Destination of MARCXML     |-destination\_xml|dest\_xml        |6             |
|MARCXML error files        |-error\_xml|xml\_error       |5             |
|Modify source              |-source |dest\_xml        |6             |
|Destination of modified MARCXML|-destination\_modifiedxml|modifiedxml\_dest|9             |
|Error modified MARCXML files|-error\_modifiedxml|modifiedxml\_error|8             |
|Load source                |-source |modified\_xml    |7             |

Each step has both a source directory, from which it reads files, and a target directory, where it writes the new files after processing. If a step finds an error in a record that cannot be written out to the target file, it writes to an error file in an error directory. When a file is successfully processed, it then puts it into the marc\_dest directory. The Convert step creates MARCXML files from MARC files; the Modify step creates modified MARCXML from MARCXML. The Load step’s target is the database (Lucene). If you chain steps together, the target directory of one step will be the source directory of the next . Every directory has its default name, so even if you do not use the directory name parameters, the OAI Toolkit will know which directories to use. The default names and parameters are:

?

You do not need to use all of these parameters. In a normal case, you may not need to set any additional parameters because the toolkit will use the default values. The next image shows the workflow of the whole importing process, including the directory structure.

![http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/OAIToolkit%20file%20workflow.jpg](http://www.extensiblecatalog.org/doc/OAI/OAIWikiPics/OAIToolkit%20file%20workflow.jpg)