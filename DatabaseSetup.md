# Setting Up the Database #

## Create the oaitoolkit Database ##

A script is provided to create the “oaitoolkit” database tables in MySQL. This script is called **oai.sql**. You must have database administrator rights to create databases, manage users, and grant privileges. In the default MySQL installation, it is the “root” user that has these rights. The simplest way to run the script is from the command line, using the example below as a guide:

**Windows:**

```
shell> mysql --user=<user> --password=<password> < <path>
```

**Linux:**

```
shell> mysql --user=<user> --password=<password> < <path>
```

Where:

|`<user>`|The MySQL user that has the rights to create a new database (e.g. ‘root’ user)|
|:-------|:-----------------------------------------------------------------------------|
|`<password>`|The MySQL password for that user                                              |
|`<path>`|The path to the SQL script **oai.sql** (in Windows: C:\OAIToolkit\sql\oai.sql; in Linux: /OAIToolkit/sql/oai.sql)|


## Create a New MySQL User ##

This new user will be responsible for importing records and for the OAI Server processes. One reason to create a new MySQL user is so that you have a user who has the right to manipulate the OAI Server database, but not any other databases. This user will have privileges to select, create, delete, and modify records for the **oaitoolkit** database only. To create a new user, log in to MySQL and enter the following two commands:

```
mysql>CREATE USER <username>@localhost IDENTIFIED BY '<password>';
mysql>GRANT all privileges ON oaitoolkit.* TO <username>@localhost;
```

Alternatively, there is a utility file in the root directory of the OAIToolkit application that will create the new user and automatically assign the correct privileges.

**Windows: create\_user.bat.** You can run it with:

```
shell>create_user.bat <user> <password> <newuser> <newpassword>
```

**Linux:** create\_user.sh**You can run it with:**

```
shell>create_user.sh <user> <password> <newuser> <newpassword>
```

Where:

|`<user>`|The MySQL user that has the rights to create a new database (e.g. ‘root’ user)|
|:-------|:-----------------------------------------------------------------------------|
|`<password>`|The password for `<user>`                                                     |
|`<newuser>`|The name of the new user                                                      |
|`<newpassword>`|The password for the new user                                                 |