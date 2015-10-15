# Windows Prerequisite Software Installation #

## JAVA Installation ##

Downloads available [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

**Version:**   Java Development Kit (JDK) 1.6.0\_04 (or higher).
Download and Install the appropriate self-extracting binary for Windows.


### Create JAVA\_HOME Environmental Variable ###

My Computer -> Properties -> Advanced
Click on Environmental Variables -> New System Variables

Variable name: JAVA\_HOME

Variable value: C:\Program Files\Java\jdk1.6.0\_xx (replace xx with the version you installed)

Click OK to close the Variable window, then OK again to close the Environmental Variables window, then OK one more time to close the Properties window.


## MySQL Installation ##

XC uses the open source relational database MySQL. The following steps walk you through setting up MySQL, which is required for your OAI-PMH repository.  MySQL is necessary for all OAI Toolkit Configurations, even those which store metadata only in Lucene.

**Version:**   MySQL Community Server 5.1 or higher

MySQL for Windows is available in two forms:
  * MSI package
  * Noinstall ZIP archive

We recommend using an MSI package, as it is a much simpler installation.  You can download it [here](http://dev.mysql.com/doc/refman/5.5/en/windows-using-installer.html).

Please follow MySQL’s [installation documentation](http://dev.mysql.com/doc/refman/5.5/en/windows-using-installer.html) for MSI packages.

Once it has been downloaded and you have started the installation, the Installation Wizard will ask you a series of questions. We have provided answers below to simplify the process. The installation documentation does a good job of explaining each option if you’re not sure what they mean.


### Installation Wizard ###

Choose setup type
  * **Complete**

After the Installation Wizard finishes, make sure that **Launch the MySQL Instance Configure Wizard** is checked

**Configuration Wizard**
  * **Detailed Configuration**
  * **Server Machine**
  * **Multifunction Database
  * Selected drive and directory for InnoDB tablespace
  ***Decision Support (DSS)/OLAP*** Keep**Enable TCP/IP Networking**and**Enable Strict Mode**checked
  * Keep default port**3306**. If you are using Windows Firewall, check**Add firewall exception for this port**.
  * Best support for Multilingualism
  * Keep**Install as a Windows Service**checked and also check**Include Bin Directory in WINDOWS PATH*** Choose a root password.**DO NOT**opt to**Enable root access from remote machines**or to**Create an Anonymous Account**.**

Once the Configuration Wizard finishes, MySQL should be running. To test it, open a command prompt and enter the following command:

```
shell>mysql –-user=root –-password=<your password>
```

Upon entering the MySQL client, your prompt will change into this:

```
mysql>
```

If you want to quit from MySQL, use the quit command (or \q as shorthand command):

```
mysql>quit
```

The installation of MySQL software is now complete. If you have any trouble with MySQL, please consult the detailed MySQL [Reference Manual](http://dev.mysql.com/doc/refman/5.5/en/windows-troubleshooting.html) with user comments.


## Apache Tomcat ##

Apache Tomcat is a popular free implementation of server side Java technology, like the Java Servlet and JavaServer Pages. Tomcat is maintained and developed by The Apache Software Foundation. Tomcat enables Java to be run on a server and allows it to respond to HTTP requests.

**Version:** 6.0.x

The OAIToolkit uses version 6.0.18, but you are free to use any minor version in the 6.0 series.

Download available [here](http://tomcat.apache.org/download-60.cgi).

There are two options for installing Tomcat.

If you use Windows NT/2000/XP, there is a _Windows Service Installer_ version that will guide you through the procedure.  This will install Tomcat as a Windows service. However, the Windows Service Installer does not include the command line batch files you need if you want to run Tomcat from the command line. That is why we recommend using the **Binary Distribution Core**. Please select the zip version that matches your server’s architecture


### Installation ###

Unzip the file at the root of c:\.
This will create a folder called apache-tomcat-6.0.xx (xx will depend upon the version you download). Inside this directory, there is a RUNNING.txt file that gives more details about the next steps.

Create CATALINA\_HOME Environmental Variable.

My Computer -> Properties -> Advanced

Click on Environmental Variables -> New System Variables

Variable name: CATALINA\_HOME

Variable value: C:\apache\_tomcat-6.0.xx (replace xx with the version you installed)

Click OK to close the Variable window then OK again to close the Environmental Variables window, then OK one more time to close Properties window.

Now you should be able to start Tomcat. Open a command prompt and input the following:

```
C:\>%> %CATALINA_HOME%\bin\startup.bat
```

You may get a window asking for permission to run Tomcat. Please allow it to.

The Tomcat’s default port number is 8080, so if everything runs smoothly, you can see your own Tomcat server at http://localhost:8080.

Tomcat has a lot of options to customize and fine tune how it is run. This document gives you the very basic steps to do so. You can find more information in the Tomcat documentation (http://tomcat.apache.org/tomcat-6.0-doc/index.html) and in these books: Chopra, Vivek – Li, Sing – Genender, Jeff: _Professional Apache Tomcat 6_ (Wiley Publishing, 2007); Moodie, Matthew: _Pro Apache Tomcat 6_ (Apress, 2007); and Brittain, Jason – Darwin, Ian F.: _Tomcat. The Definitive Guide_. 2nd Edition. (O’Reilly, 2007).