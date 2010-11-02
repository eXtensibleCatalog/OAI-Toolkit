<%@ page language="java" pageEncoding="UTF-8"
%><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" 
%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>OAIToolkit starting page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<style type="text/css">
		body { font-family: Arial, Lucida sans Unicode, tahoma, sans-serif; }
		td { vertical-align: top; font-size: 10pt; }
		a { color: maroon; }
		fieldset legend { font-weight: bold; border: 2px solid black; padding: 0 3px 0 3px; }
		fieldset { width: 200px; vertical-align: top; margin-right: 10px; }
		fieldset.admin { width: 200px; float: left; }
		fieldset.container { width: 470px; padding-bottom: 15px; }
		fieldset.test { margin: 5px; float: left; }
		fieldset.test legend { background-color: #fff; border: 1px solid black; }
		ul { margin: 5px 0px 5px 20px; padding: 0; }
		li { margin-left: 0px; }
	</style>
</head>
<body>

<h1>OAIToolkit starting page</h1>

<fieldset class="admin">
	<legend>Administration</legend>
	<ul type="square">
		<li><html:link action="configuration">configuration</html:link></li>
	</ul>
	
	<h2>documentation</h2>
	<ul type="square">
		<li><a href="README.html">Manual</a></li>
		<li><a href="javadoc/index.html">OAIToolkit API JavaDoc</a></li>
	</ul>
</fieldset>

<fieldset class="container">
	<legend>OAI test requests</legend>
	<fieldset class="test">
		<legend>Identify</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=Identify">Identify</html:link></li>
		</ul>
	</fieldset>

	<fieldset class="test">
		<legend>ListMetadataFormats</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=ListMetadataFormats"
				>ListMetadataFormats</html:link></li>
		</ul>
	</fieldset>

	<fieldset class="test">
		<legend>ListSets</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=ListSets">ListSets</html:link></li>
		</ul>
	</fieldset>

	<fieldset class="test">
		<legend>GetRecord</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:URVoyager1/2&metadataPrefix=oai_dc"
				>GetRecord</html:link> (dc)</li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:URVoyager1/2&metadataPrefix=oai_marc"
				>GetRecord</html:link> (oai_marc)</li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:URVoyager1/2&metadataPrefix=mods"
				>GetRecord</html:link> (mods)</li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:URVoyager1/2&metadataPrefix=marcxml"
				>GetRecord</html:link> (marc21)</li>
			<li><html:link action="oai-request?verb=GetRecord"
				>w/o param</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier="
				>empty param</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:s3"
				>bad param</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:BogusIdentifier/9999999"
				>nonexistent identifier</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:URVoyager1/2"
				>no metadata format</html:link></li>
		</ul>
	</fieldset>

	<fieldset class="test">
		<legend>ListRecords</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=oai_marc"
				>ListRecords</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marcxml"
				>all (no params)</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marcxml&amp;from=2002-06-01T19:20:30%2B0200"
				>from 2002-06-01</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marcxml&amp;from=2002-06-01T19:20:30%2B0200&amp;until=2003-06-01T19:20:30%2B0200"
				>from 2002-06-01 until 2003-06-01</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marcxml&amp;from=2004-06-01T19:20:30%2B0200&amp;set=auth"
				>from: 2004-06-01, set: auth</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marcxml&amp;set=bib"
				>set: bib</html:link></li>
		</ul>
	</fieldset>

	<fieldset class="test">
		<legend>ListIdentifiers</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=ListIdentifiers"
				>all (no params)</html:link></li>
			<li><html:link action="oai-request?verb=ListIdentifiers&amp;from=2002-06-01T19:20:30%2B0200"
				>from 2002-06-01</html:link></li>
			<li><html:link action="oai-request?verb=ListIdentifiers&amp;from=2002-06-01T19:20:30%2B0200&amp;until=2003-06-01T19:20:30%2B0200"
				>from 2002-06-01 until 2003-06-01</html:link></li>
			<li><html:link action="oai-request?verb=ListIdentifiers&amp;from=2004-06-01T19:20:30%2B0200&amp;set=auth"
				>from: 2004-06-01, set: auth</html:link></li>
			<li><html:link action="oai-request?verb=ListIdentifiers&amp;set=bib"
				>set: bib</html:link></li>
		</ul>
	</fieldset>
</fieldset>
</body>
</html>
