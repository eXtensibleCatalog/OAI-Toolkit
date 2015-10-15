  * download files from http://code.google.com/p/xcoaitoolkit/downloads/list
```
wget http://xcoaitoolkit.googlecode.com/files/OAIToolkit-0.6.10alpha-importer-linux.zip
wget http://xcoaitoolkit.googlecode.com/files/OAIToolkit-0.6.10alpha-server.zip
```
  * unzip files and rename
```
mv OAIToolkit OAIToolkit-0.6.10
```
  * move tomcat/bin/OAIToolkit.server.properties to .
  * move tomcat/bin/OAIToolkit.directory.properties to tomcat's working path (or don't if it's already there)
  * edit $TOMCAT\_WORKING\_DIR/OAIToolkit.directory.properties.  Add this to the end
```
OAIToolkit-0.6.10.configDir=c:/dev/xc/oai-toolkit/installation/OAIToolkit-0.6.10
OAIToolkit-0.6.10.logDir=c:/dev/xc/oai-toolkit/installation/OAIToolkit-0.6.10/server_logs
OAIToolkit-0.6.10.resourceDir=c:/dev/xc/oai-toolkit/installation/OAIToolkit-0.6.10/resources
OAIToolkit-0.6.10.luceneDir=c:/dev/xc/oai-toolkit/installation/OAIToolkit-0.6.10/lucene_index
OAIToolkit-0.6.10.cacheDir=c:/dev/xc/oai-toolkit/installation/OAIToolkit-0.6.10/cache
```
  * edit sql/oai.sql and replace extensiblecatalog with new name (oaitoolkit\_0\_6\_10)
  * run sql
```
mysql -u root --password=root < ./sql/oai.sql
```
  * mv the webapp
```
cp ./tomcat/webapps/OAIToolkit.war /cygdrive/c/dev/java/tomcat_6.0/webapps/OAIToolkit-0.6.10.war
```
  * edit OAIToolkit.db.properties
  * edit OAIToolkit.server.properties (webapp name, max records)
  * cp marc files to marc/
  * run importer
```
./convertload.sh
```
  * start tomcat
  * get records
```
curl -s 'http://localhost:8080/OAIToolkit-0.6.10/oai-request.do?verb=ListRecords&metadataPrefix=marc21'
```