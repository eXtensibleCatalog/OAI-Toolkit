<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html version="-//W3C//DTD HTML 4.01 Transitional//EN">
	<head>
		<title><bean:message key="configuration.title" /></title>

		<style type="text/css">
		body { font-family: Arial, Lucida sans Unicode, tahoma, sans-serif; }
		td { vertical-align: top; font-size: 10pt; }
		a { color: maroon; }
		fieldset legend { font-weight: bold; border: 2px solid black; padding: 0 3px 0 3px; }
		fieldset.admin { width: 200px; float: left; }
		fieldset.container { width: 470px; padding-bottom: 15px; }
		fieldset.test { width: 200px; vertical-align: top; margin-right: 10px; margin: 5px; float: left; }
		fieldset.test legend { background-color: #fff; border: 1px solid black; }
		ul { margin: 5px 0px 5px 20px; padding: 0; }
		li { margin-left: 0px; }
		</style>

	</head>
	<body>
	<h1><bean:message key="configuration.title" /></h1>

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
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=${configurationForm.oaiIdentifierSampleIdentifier}&metadataPrefix=oai_dc"
				>GetRecord</html:link> (dc)</li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=${configurationForm.oaiIdentifierSampleIdentifier}&metadataPrefix=marc21"
				>GetRecord</html:link> (marc21)</li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=${configurationForm.oaiIdentifierSampleIdentifier}&metadataPrefix=mods"
				>GetRecord</html:link> (mods)</li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=${configurationForm.oaiIdentifierSampleIdentifier}&metadataPrefix=html"
				>GetRecord</html:link> (html)</li>
			<li><html:link action="oai-request?verb=GetRecord"
				>w/o param</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier="
				>empty param</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:${configurationForm.oaiIdentifierRepositoryIdentifier}:s3&metadataPrefix=marc21"
				>bad param</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=oai:library.rochester.edu:BogusIdentifier/9999999&metadataPrefix=marc21"
				>nonexistent identifier</html:link></li>
			<li><html:link action="oai-request?verb=GetRecord&amp;identifier=${configurationForm.oaiIdentifierSampleIdentifier}"
				>no metadata format</html:link></li>
		</ul>
	</fieldset>

	<fieldset class="test">
		<legend>ListRecords</legend>
		<ul type="square">
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=oai_marc"
				>ListRecords</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marc21"
				>all (no params)</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marc21&amp;from=2002-06-01T19:20:30%2B0200"
				>from 2002-06-01</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marc21&amp;from=2002-06-01T19:20:30%2B0200&amp;until=2003-06-01T19:20:30%2B0200"
				>from 2002-06-01 until 2003-06-01</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marc21&amp;from=2004-06-01T19:20:30%2B0200&amp;set=auth"
				>from: 2004-06-01, set: auth</html:link></li>
			<li><html:link action="oai-request?verb=ListRecords&metadataPrefix=marc21&amp;set=bib"
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

<br/>


		<html:form action="/configuration">
			<fieldset>
				<legend>OAI Identify settings ([tomcat]/bin/OAIToolkit.server.properties)</legend>
			<table>
				<tr>
					<td><bean:message key="configuration.repositoryName" /></td>
					<td>
						<html:text property="repositoryName" readonly="true" />
						<html:errors property="repositoryName" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.description" /></td>
					<td>
						<html:textarea  property="description" cols="80" rows="5" readonly="true" />
						<html:errors property="description" />
					</td>
				</tr>

				<tr>
					<td><bean:message key="configuration.oai-identifier.scheme" /></td>
					<td>
						<html:text property="oaiIdentifierScheme" readonly="true" />
						<html:errors property="oaiIdentifierScheme" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.oai-identifier.delimiter" /></td>
					<td>
						<html:text property="oaiIdentifierDelimiter" readonly="true" />
						<html:errors property="oaiIdentifierDelimiter" />
					</td>
				</tr>

                <tr>
					<td><bean:message key="configuration.oai-identifier.repositoryIdentifier" /></td>
					<td>
						<html:text property="oaiIdentifierRepositoryIdentifier" readonly="true" />
						<html:errors property="oaiIdentifierRepositoryIdentifier" />
					</td>
				</tr>

				<tr>
					<td><bean:message key="configuration.oai-identifier.sampleIdentifier" /></td>
					<td>
						<html:text property="oaiIdentifierSampleIdentifier" readonly="true" />
						<html:errors property="oaiIdentifierSampleIdentifier" />
					</td>
				</tr>

				<tr>
					<td><bean:message key="configuration.baseUrl" /></td>
					<td>
						<html:text property="baseUrl" readonly="true" />
						<html:errors property="baseUrl" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.adminEmail" /></td>
					<td>
						<html:text property="adminEmail" readonly="true" />
						<html:errors property="adminEmail" />
						<br />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.granularity" /></td>
					<td>
						<html:radio property="granularity" value="YYYY-MM-DD" disabled="true" />
						<bean:message key="configuration.granularity.day" />
						<html:radio property="granularity" value="YYYY-MM-DDThh:mm:ssZ" disabled="true" />
						<bean:message key="configuration.granularity.seconds" />
						<a href="http://www.openarchives.org/OAI/openarchivesprotocol.html#SelectiveHarvestingandDatestamps" target="_blank">[?]</a>
						<html:errors property="granularity" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.compression" /></td>
					<td>
						<html:multibox property="compression" value="gzip" disabled="true" />
						gzip
						<html:multibox property="compression" value="compress" disabled="true" />
						compress
						<html:multibox property="compression" value="deflate" disabled="true" />
						deflate
						<html:errors property="compression" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.deletedRecord" /></td>
					<td>
						<html:radio property="deletedRecord" value="no" disabled="true" />
						no
						<html:radio property="deletedRecord" value="persistent" disabled="true" />
						persistent
						<html:radio property="deletedRecord" value="transient" disabled="true" />
						transient
						<html:errors property="deletedRecord" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.protocolVersion" /></td>
					<td>
						<html:radio property="protocolVersion" value="1.0" disabled="true" />
						1.0
						<html:radio property="protocolVersion" value="2.0" disabled="true" />
						2.0
						<html:errors property="protocolVersion" />
					</td>
				</tr>
				</table>
				</fieldset>
<br/>

				<fieldset>
				<legend>Technical fields ([tomcat]/bin/OAIToolkit.server.properties)</legend>
				<table>
				<tr>
					<td><bean:message key="configuration.setsChunk.maxNumberOfRecords" /></td>
					<td>
						<html:text property="setsChunk_maxNumberOfRecords" readonly="true" />
						<html:errors property="setsChunk_maxNumberOfRecords" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.setsChunk.maxSizeInBytes" /></td>
					<td>
						<html:text property="setsChunk_maxSizeInBytes" readonly="true" />
						<html:errors property="setsChunk_maxSizeInBytes" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.identifiersChunk.maxNumberOfRecords" /></td>
					<td>
						<html:text property="identifiersChunk_maxNumberOfRecords" readonly="true" />
						<html:errors property="identifiersChunk_maxNumberOfRecords" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.identifiersChunk.maxSizeInBytes" /></td>
					<td>
						<html:text property="identifiersChunk_maxSizeInBytes" readonly="true" />
						<html:errors property="identifiersChunk_maxSizeInBytes" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.recordsChunk.maxNumberOfRecords" /></td>
					<td>
						<html:text property="recordsChunk_maxNumberOfRecords" readonly="true" />
						<html:errors property="recordsChunk_maxNumberOfRecords" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.recordsChunk.maxSizeInBytes" /></td>
					<td>
						<html:text property="recordsChunk_maxSizeInBytes" readonly="true" />
						<html:errors property="recordsChunk_maxSizeInBytes" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.expirationTime" /></td>
					<td>
						<html:text property="expirationDate" readonly="true" />
						<html:errors property="expirationDate" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.maxSimultaneousRequest" /></td>
					<td>&nbsp;<html:text property="maxSimultaneousRequest" readonly="true" />
						<html:errors property="maxSimultaneousRequest" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.schemaFile" /></td>
					<td>&nbsp;
						<html:radio property="schema" value="standard" disabled="true" />
						<bean:message key="configuration.schemaFile.standard" />
						<html:radio property="schema" value="custom" disabled="true" />
						<bean:message key="configuration.schemaFile.custom" />
						<html:errors property="schema" />
					</td>
				</tr>
<!--
				<tr>
					<td><bean:message key="configuration.storageType" /></td>
					<td>&nbsp;
						<html:radio property="storageType" value="MySQL" disabled="true" />
						<bean:message key="configuration.storageType.MySQL" />
						<html:radio property="storageType" value="mixed" disabled="true" />
						<bean:message key="configuration.storageType.mixed" />
						<html:radio property="storageType" value="Lucene" disabled="true" />
						<bean:message key="configuration.storageType.Lucene" />
						<html:errors property="schema" />
					</td>
				</tr>
-->
				<tr>
					<td><bean:message key="configuration.maxCacheLifetime" /></td>
					<td>&nbsp;<html:text property="maxCacheLifetime" readonly="true" />
						<html:errors property="maxCacheLifetime" />
					</td>
				</tr>
			</table>
			</fieldset>
		</html:form>

		<p><a href="index.jsp">back to index page</a></p>
	</body>
</html>

