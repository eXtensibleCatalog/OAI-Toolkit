# Typographical Conventions #

Below is a listing of the various typographical conventions that are used in these sections.

Directories, files, and notes will appear as follows:

```
[parent directory]
  |- [directory]                       // note
  o- file
```

When a new directory is inserted into an existing directory, the new directory will appear in bold as follows:

```
[parent directory]
  |- [directory]
  |- [a new directory]                 // note for the new directory
  o- file
```

Shell prompt (in Windows or Linux environment) will use:

```
shell>java -version
```

MySQL clients will use:

```
mysql>select version();
```

Variables in command line will be in pointed brackets as follows:

```
shell>create_db.bat <user> <password> <newuser> <newpassword>
```

The **`<user>`**, **`<password>`**, **`<newuser>`**, and **`<newpassword>`** are placeholders for actual values (in this example: name of MySQL user, password of MySQL user, name of new MySQL user, password of new MySQL user, respectively). You would add actual values instead of these strings. In most cases when we have attempted to provide examples, we have replaced these placeholders with actual values.