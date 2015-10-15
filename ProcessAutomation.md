# Automated Running #

The scripts can be launched automatically by a linux tool: cron. _Cron_ is a time-based scheduling service in Unix-like computer operating systems (definition from [Wikipedia](http://en.wikipedia.org/wiki/Cron)). For cron, the user specifies a time and a script, and the OS will launch the script at the given time. The user may define a single time (e.g. 12/31/2008 12:00) or multiple times (e.g. to run the script every X hours).

A cron expression contains six parts, separated with spaces:

```
# +---------------- minute (0 - 59)
# |  +------------- hour (0 - 23)
# |  |  +---------- day of month (1 - 31)
# |  |  |  +------- month (1 - 12)
# |  |  |  |  +---- day of week (0 - 7) (Sunday=0 or 7)
# |  |  |  |  |
  *  *  *  *  *  command to be executed
```

The first five fields are the time definition, and the sixth is the command to be executed. The time definitions can have the following formats:
  * Simple number:

```
# run at the first minute of every hours
1 * * * * echo xc
```

  * Multiple numbers, separated by comma (",") operator, ranges:

```
# run at the 20th, 30th, 60th minutes of every hours
0,20,40 * * * * echo xc
```

  * Range of numbers:

```
# run at the 1st, 2nd, 3rd minutes of every hours
1-3 * * * * echo xc
```

  * Every possible value:

```
# run at the 1st, 2nd, 3rd ... 59th minutes of every hours
* * * * * echo xc
```

You can edit the cron expression with:

```
crontab -e
```

The crontab’s default editor is **ed**, which is quite different from the more popular **vi** editor`*`. If you want to use **vi** instead, first make **vi** your default editor with:

```
export EDITOR=vi
```

_`*` Note: In this manual we do not describe the usage of ed or vi editor. You can find more information in the following articles on Wikipedia: http://en.wikipedia.org/wiki/Ed_(text_editor) and http://en.wikipedia.org/wiki/Vi ._

This makes **vi** the default editor only in the current session. If you want to use **vi** in the future as well, place this line into the **.profile** file. If you want to list the content of crontab file, use the following:

```
crontab -l
```

Now we have covered how to set up the timeframe for an automatic launch, but not the command we want to execute, which goes:

```
0 0 * * * cd /path/to/OAIToolkit-1.0.0; ./convertload.sh
```

The result of this expression is that the marc conversion will run every night at midnight. The application moves the MARC files from the source directory to another directory. If the source directory is empty, the application will stop operating, so you do not have to be concerned that the application will process the same data over and over.

Note that the zip file contains **load.sh**, **convert.sh**, and convertload.sh files with default values. You should update these files with your preferred parameters before running the application, as described in the previous sections.