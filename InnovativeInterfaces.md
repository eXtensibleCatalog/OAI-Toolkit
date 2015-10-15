# XC OAI Toolkit Innovative Interfaces Sample Scripts #

_Note that this script has only been tested using III 2007 1.2. We cannot say whether it will work on other versions._

### Prerequisites ###

  * Telnet access to the Innovative Interfaces (non-Oracle) catalog
  * A server - preferably Unix/Linux - on which the OAI Toolkit has been installed
  * The server needs to have the following features
    * Expect
    * Perl
    * Perl::Expect
    * FTP Server open to the III Millenium server's IP address
  * The III server must
    * Provide for telnet access from the OAI Toolkit box and have the address listed in the FTS server list
    * It must either be open to a direct connect from that address or have an authorized account with which to log into the server. The scripts as written support the former option.

### Configuration ###

A number of parameters need to be set in the xc\_get\_marc.pl file:

|$server|Name/IP address of III Telnet interface|
|:------|:--------------------------------------|
|$filenum|Work area for bib output               |
|$sc\_filenum|Workarea for created/suppressed records|
|sm\_filenum|Workarea for modified/suppressed records (note that the values for both of these last two can be the same as $filenum once you are sure that things are being transferred properly)|
|$testing|Whether you are working on a subset of the full bib database for testing purposes|
|@my\_test\_range|This is the range of bibnumbers within which you wish to do the testing|

**Email parameters (not yet implemented):**

|$to|Email address of person to whom to send the results of the script run|
|:--|:--------------------------------------------------------------------|
|$from|XC user account that runs the script                                 |
|$cc|Other email address to which results should be sent                  |

**Values used in the script:**

|@exp\_user|Username for the account on XC box to which the files will be sent|
|:---------|:-----------------------------------------------------------------|
|@exp\_pass|@exp\_user's password                                             |
|$filename |Name for bibfile to be sent to XC box                             |
|$sc\_filename|Name of suppressed created records                                |
|$sm\_filename|Name of suppressed modified records                               |
|$init     |Initials and password needed to get into the List creation        |
|$ipass    |FTS subsystems of III                                             |
|$suppreeddate|Earliest date to check for suppression in the suppressed records search|


**Other values that may need to be changed:**

As noted in the third paragraph of the header to the xc\_get\_marc.pl script, "Expect" literally _expects_ certain values to be sent to it before it will proceed to the next step. If any of the menus have a different set of letters in any of the menus selection strings (e.g. "E,R,X,U,Q") than what is included here, you will need to modify the script to look for the string that your system provides as a prompt.

### Implementation ###

Once the scripts have been installed and you have verified that the data is transferred properly, you will need to do the following:
  1. Set up a cron process to run every day (we are doing it just after midnight) to transfer the files to the XC server.
  1. Once on the XC server, there should be a cron job that moves the files to the OAIToolkit/marc directory, changing the suffix to .mrc and changing the ownership of the files to that under which the OAIToolkit runs.
  1. Run the OAIToolkit loading routine.