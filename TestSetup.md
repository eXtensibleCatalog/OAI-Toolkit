# Run the Software and Test It #

There is a directory named “sample\_marcdata” in the location where the software was extracted (**OAIToolkit\sample\_marcdata**). This directory has two sample raw MARC files called “sample\_marc1.mrc” and "sample\_marc2.mrc." Each file has 10 sample records inside it.
  * Copy the sample file from the **OAIToolkit\sample\_marcdata** to the **OAIToolkit\marc** folder.
  * Run the convertload.sh/bat script. If you wish to change any parameters in it, you can read the description of each parameter below and then add/modify it.
  * After the sample records go through, there will be various folders created in the OAIToolkit directory, such as “error\_xml,” “lucene\_index,” “xml\_dest,” etc. The directories created and what they contain is explained below in detail.
  * Go to http://localhost:8080/OAIToolkit/ (assuming port 8080 was configured to use when the Apache Tomcat was installed).
  * If you can see the OAI Toolkit screen in your browser, then you have installed the OAI Toolkit successfully! You can view the 20 sample records you imported when by clicking “ListRecords” on that page.
  * If you cannot see the OAI Toolkit screen in your browser, please refer to the "TroubleshootingTips Troubleshooting Tips" section.