#!/bin/sh
# ====================================================
# Script for getting the record count with their types
# and the deleted count of the Lucene Database
# ====================================================
# ATTENTION: this is a sample script, modify the 
# parameters according to your needs. Read the 
# OAIToolkit Manual for the details.
# ====================================================

java -Xmx1024m -cp "lib/xercesImpl.jar:lib/xml-apis.jar" -jar lib/OAIToolkit-1.0.2.jar -lucene_statistics -lucene_index lucene_index
