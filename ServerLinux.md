# Linux Prerequisite Software Installation #

There are many distributions of Linux, each with its own package manager. It would be too difficult to include installation instructions for every one of them. It is up to you to decide what method you want to use to install this software. As long as the version of the software meets the requirements outlined below, it makes no difference to the OAI toolkit whether it is installed via RPM or unpacked binary, or built from source installed via a package manager. The instructions below will describe how to install software as a binary or RPM distribution, as both methods will work with any Linux distribution.


## JAVA Installation ##

Downloads available [here](http://www.oracle.com/technetwork/java/javase/downloads/index.html).

**Version:** Java Development Kit (JDK) 1.6.0\_04 (or higher)

Download and install the appropriate self-extracting binary for your version of Linux or Solaris. Installation instructions are located on the same page. Note that for Solaris, a patch is required before installing the JDK. If installed via a package manager, take note of where it was installed.


### Create JAVA\_HOME Environmental Variable ###

There are a couple of ways to create an environmental variable. You can make it available to all users by putting it in /etc/profile, or for one specific user by putting it in the corresponding .profile file in their home directory (it can also be put in .bash\_profile). If you opt to put it in a specific user’s .profile or .bash\_profile file, make sure that user is the one who starts and stops Tomcat.

Whichever file you select, open it in your preferred file editor and add the following. Note that this example assumes you have unpacked the JDK in /usr/local. Please substitute the path to where you unpacked it.

```
JAVA_HOME=/usr/local/jdk1.6.0_xx (substitute the xx with your version) export JAVA_HOME
```

## MySQL Installation ##

XC uses the open source relational database MySQL. The following steps walk you through setting up MySQL, which is required for your OAI-PMH repository.  MySQL is necessary for all OAI Toolkit Configurations, even those which store metadata only in Lucene.

**Version:** MySQL Community Server 5.1 or higher

MySQL recommends that you install via RPM packages. You only need to install the MySQL-server and MySQL-client to get a functional MySQL server. Select the files that are compatible with your version of Linux and architecture from [this page](http://dev.mysql.com/downloads/mysql/).

Once downloaded, untar them to any location on your server (no matter where the installation files are put, MySQL will always be installed in the same place) and use the commands below to install. In this example, we are installing MySQL on 32 bit SLES 10:

```
shell> rpm –ivh MySQL-server-community-5.1.51-1.sles10.i586.rpm
shell> rpm –ivh MySQL-client-community-5.1.51-1.sles10.i586.rpm
```

[MySQL’s documentation](http://dev.mysql.com/doc/refman/5.5/en/linux-installation-rpm.html) has more details about the installation.


### Optional: Create a my.cnf File ###

You do not need a my.cnf file to run MySQL. However, having one allows you to fine tune MySQL’s performance and add many directives. If you installed MySQL using the RPM package, there will be several .cnf files in /usr/share/mysql. Find the one that best suits your server and copy it to /etc:

```
shell> cp /usr/share/mysql/my-large.cnf /etc/my.cnf
```

Set the permissions so it is only writeable by root:

```
shell> chmod 644 /etc/my.cnf
```

If you installed MySQL via a package manager, check the documentation to see where it placed my.cnf.


### Securing MySQL ###

Regardless of whether you installed MySQL via RPM or a package manager, you need to set MySQL’s root password.  Please learn how to set it by reading through the documentation on [this page](http://dev.mysql.com/doc/refman/5.5/en/default-privileges.html).


### Test MySQL Command Line Client ###

After you run the commands to add MySQL root’s password, you need to test it. **Enter the following command:**

```
shell> mysql –u root –p
enter password:
```

After entering your password, you should be at the mysql prompt:

```
mysql>
```

If you want to quit from MySQL, use the quit command (or \q as shorthand command):

```
mysql> quit
```

The installation of MySQL software is now complete.  If you have any trouble with MySQL, please consult the detailed MySQL [Reference Manual](http://dev.mysql.com/doc/refman/5.5/en/index.html) with user comments.


## Apache Tomcat ##

Apache Tomcat is a popular free implementation of server side Java technology, like the Java Servlet and JavaServer Pages.  Tomcat is maintained and developed by The Apache Software Foundation. Tomcat enables Java to be run on a server and allows it to respond to HTTP requests.

**Version:** 6.0.x

The OAIToolkit uses version is 6.0.18, but you are free to use any minor version in the 6.0 series.

Download available [here](http://tomcat.apache.org/download-60.cgi).

Use the Binary Distribution -> core -> tar.gz. Unpack Tomcat to the location of your choice.

**Make sure you have the environmental variable CATALINA\_HOME set.**  There are a couple of ways to create an environmental variable. You can make it available to all users by putting it in /etc/profile, or for one specific user by putting it in the corresponding .profile file in their home directory (it can also be put in .bash\_profile). If you opt to put it a specific user’s .profile or .bash\_profile file, make sure that user is the one who starts and stops Tomcat. The example below unpacks tomcat version 6.0.16 in /usr/local:

```
CATALINA_HOME=/usr/local/apache-tomcat-6.0.16
export CATALINA_HOME
```

If you installed Tomcat via a package manager, verify that you have CATALINA\_HOME set with this command (note that you will want to be the user who starts Tomcat when doing this):

```
prompt> echo $CATALINA_HOME
```

You should get back the path to tomcat. If nothing is returned, then set it using the command above.

Finish the Tomcat installation using the documentation provided on [this page](http://tomcat.apache.org/tomcat-6.0-doc/setup.html).

Now you should be able to start Tomcat. Issue the following command:

```
prompt> $CATALINA_HOME/bin/catalina.sh start
```

The Tomcat’s default port number is 8080, so if everything runs smoothly you can see your own Tomcat server at http://localhost:8080

Tomcat has a lot of options to customize and fine tune how it is run. This document gives you the very basic steps. You can find more information in the Tomcat documentation (http://tomcat.apache.org/tomcat-6.0-doc/index.html) and in these books: Chopra, Vivek – Li, Sing – Genender, Jeff: _Professional Apache Tomcat 6_ (Wiley Publishing, 2007); Moodie, Matthew: _Pro Apache Tomcat 6_ (Apress, 2007); and Brittain, Jason – Darwin, Ian F.: _Tomcat. The Definitive Guide_. 2nd Edition. (O’Reilly, 2007).