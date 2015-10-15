# Quick Install Checklist #

  * ant OAIToolkitInstallPackage-windows
  * ant OAIToolkitInstallPackage-server
  * unzip both of the above to the same place (OAIToolkit)
  * move OAIToolkit/tomcat/bin/`*` to tomcat/bin
  * edit OAIToolkit/OAIToolkit.db.properties
  * edit tomcat/bin/OAIToolkit.db.properties
  * edit tomcat/bin/OAIToolkit.directory.properties
  * move OAIToolkit/tomcat/webapps/OAIToolkit.war to tomcat/webapps
  * mysql -u root --password=root < ./sql/oai.sql
  * import data
    * move an mrc file (sample\_marcdata) into marc
    * ./convertload.bat