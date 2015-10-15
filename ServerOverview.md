# Preparation and Installation #

## Server Requirements ##

The OAI Toolkit is a compute- and disk space-intensive service.  We recommend a fairly powerful server.  As we continue to test the software with partner institutions, we will make further recommendations in this section of the manual.

Disk space recommendations based on testing: After conversion, 1 GB of raw MARC records resulted in 3 GB of MARCXML files. When we imported this set into Lucene, the index became about the same size as the MARCXML files. During index optimization, Lucene temporarily allocates more than 200% of the index size. So, if you have 1 GB of MARC records, you should have at least 1 GB (for original MARC), 3 GB (for MARCXML), 3 GB (for Lucene index), 3 GB (for temporary files) = 10 GB free space.

We suggest you have at least 3 GB of memory on the machine/server that you are installing the OAI Toolkit on.  But this, too, depends on the size of your index (the larger the index, the greater the need for more memory).  To get an idea of the performance you might expect to achieve, see our [performance](Performance.md) page for harvesting timings using several different configurations.


## Operating System ##

The OAI Toolkit was tested on Solaris 10 x86, Windows XP, and Linux.  We do not recommend installing on older versions of Solaris for support reasons, though there’s no technical reason why Solaris 8 or 9 would not work. Other flavors of Unix like HP-UX or AIX have not been tested, so use at your own risk.  You could also build your OAI server using Linux with most any distribution.

Due to the vast number of Linux distributions, we cannot create a specific document to install it.  Follow your current procedures to install the operating system you selected and any patches/updates.  You should also follow your standard security hardening procedures to protect your server. In this manual we use “Linux” for every Unix-like operating system, including different Linux distributions and Solaris.

The OAI Toolkit will also work in a virtual server environment. It was tested using VMware ESX Server 3.5. Other server virtualization technologies should also work, but have not been tested as of yet.

## Installation Overview ##

These are the following steps you need to perform to install the prerequisite applications and the OAI Toolkit. Each step will be described in depth in its respective section. Windows and Linux operating systems will have separate sections for the installation of the prerequisite applications. The OAI Toolkit installation information itself will be provided both for Windows and Linux operating systems.

  1. Java Software Installation (if you do not already have it)
  1. Database installation
    * Download and install the MySQL Community Server.
    * Test the MySQL Command Line Client
  1. Download, install, and configure Apache Tomcat
  1. Download and install the OAI Toolkit software
    * Determine an appropriate directory in which store the OAI Toolkit.  Unzip OAIToolkit **importer package**. Unzip OAIToolkit **server package** to the Directory structure after unzipping the packages.
  1. Database setup
    * Create extensiblecatalog database
    * Create user
  1. Edit configuration files
    * Copy specified service package files to Tomcat directories
  1. Run the OAI Toolkit software and test it
  1. Write ILS extraction script for one of the following:
    * Voyager (a sample script is included; refer to the command-line export feature of Voyager and options, version numbers, etc)
    * III
    * Aleph
  1. Install and schedule ILS extraction script
    * Give an example of how to setup scheduled execution of a script
  1. Testing (see separate testing document)
    * Test extraction from the ILS
    * Test conversion to MARCXML
    * Test loading into the OAI Toolkit
    * Test harvesting from the OAI Toolkit
    * Test incremental extractions from the ILS
    * Test incremental loading into the OAI Toolkit
    * Test other record formats (holdings, authority, item)