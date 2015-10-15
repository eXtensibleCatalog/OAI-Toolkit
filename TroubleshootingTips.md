# Troubleshooting Tips for the OAI Toolkit #

It could happen that the OAI Toolkit which you have installed is not working properly. Although seeing the logs should help you detect the problem, here are some additional simple steps to follow to make sure you are not missing important files in the installation of the OAI Toolkit.

The quick checklist is as follows:
  1. Make sure any old OAIToolkit.war files from previous installations have been deleted, as well as the old OAIToolkit folder from the webapps folder of the tomcat install directory. (For example, on Windows it might be **C:\Program Files\Apache Software Foundation\Tomcat 6.0\webapps**). Make sure the new OAIToolkit.war file is in that directory.
  1. Restarting Apache Tomcat might just solve your problem sometimes.
  1. Check to see if the configuration files **OAIToolkit.directory.properties**, **OAIToolkit.log4j.properties**, **OAIToolkit.server.properties**, and **OAIToolkit.db.properties** are in the bin folder of the Tomcat install directory.
  1. Make sure the configuration files mentioned above are the latest version. If not, update them.
  1. Check the contents of the **OAIToolkit.directory.properties** file to see whether it has the right location of the various directories of the OAI Toolkit.
  1. Check whether the **OAIToolkit.db.properties** has the right username, password, and port set for the database access to mySQL.
  1. Do any of the changes above that have been missed and do not forget to restart the Apache Tomcat Server afterwards.