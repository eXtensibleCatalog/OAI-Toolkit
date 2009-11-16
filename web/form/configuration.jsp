<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean"%>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html"%>
<html version="-//W3C//DTD HTML 4.01 Transitional//EN">
	<head>
		<title><bean:message key="configuration.title" /></title>
		<style type="text/css">
		body {
			font-family: 'Lucida Grande',Verdana,Arial,sans-serif;
		}
		td {
			vertical-align: top;
			font-size: 10pt;
		}
		</style>
	</head>
	<body>
	<h1><bean:message key="configuration.title" /></h1>
		<html:form action="/configuration">
			<fieldset>
				<legend>OAI Identify settings</legend>
			<table>
				<tr>
					<td><bean:message key="configuration.repositoryName" /></td>
					<td>
						<html:text property="repositoryName" />
						<html:errors property="repositoryName" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.description" /></td>
					<td>
						<html:textarea  property="description" cols="80" rows="5" />
						<html:errors property="description" />
					</td>
				</tr>

				<tr>
					<td><bean:message key="configuration.oai-identifier.scheme" /></td>
					<td>
						<html:text property="oaiIdentifierScheme" />
						<html:errors property="oaiIdentifierScheme" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.oai-identifier.domainName" /></td>
					<td>
						<html:text property="oaiIdentifierDomainName" />
						<html:errors property="oaiIdentifierDomainName" />
					</td>
				</tr>

                <tr>
					<td><bean:message key="configuration.oai-identifier.repositoryIdentifier" /></td>
					<td>
						<html:text property="oaiIdentifierRepositoryIdentifier" />
						<html:errors property="oaiIdentifierRepositoryIdentifier" />
					</td>
				</tr>

				<tr>
					<td><bean:message key="configuration.oai-identifier.sampleIdentifier" /></td>
					<td>
						<html:text property="oaiIdentifierSampleIdentifier" />
						<html:errors property="oaiIdentifierSampleIdentifier" />
					</td>
				</tr>

				<tr>
					<td><bean:message key="configuration.baseUrl" /></td>
					<td>
						<html:text property="baseUrl" />
						<html:errors property="baseUrl" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.adminEmail" /></td>
					<td>
						<html:text property="adminEmail" />
						<html:errors property="adminEmail" />
						<br />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.granularity" /></td>
					<td>
						<html:radio property="granularity" value="YYYY-MM-DD" />
						<bean:message key="configuration.granularity.day" />
						<html:radio property="granularity" value="YYYY-MM-DDThh:mm:ssZ" />
						<bean:message key="configuration.granularity.seconds" />
						<a href="http://www.openarchives.org/OAI/openarchivesprotocol.html#SelectiveHarvestingandDatestamps" target="_blank">[?]</a>
						<html:errors property="granularity" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.compression" /></td>
					<td>
						<html:multibox property="compression" value="gzip" />
						gzip
						<html:multibox property="compression" value="compress" />
						compress
						<html:multibox property="compression" value="deflate" />
						deflate
						<html:errors property="compression" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.deletedRecord" /></td>
					<td>
						<html:radio property="deletedRecord" value="no" />
						no
						<html:radio property="deletedRecord" value="persistent" />
						persistent
						<html:radio property="deletedRecord" value="transient" />
						transient
						<html:errors property="deletedRecord" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.protocolVersion" /></td>
					<td>
						<html:radio property="protocolVersion" value="1.0" />
						1.0
						<html:radio property="protocolVersion" value="2.0" />
						2.0
						<html:errors property="protocolVersion" />
					</td>
				</tr>
				</table>
				</fieldset>
				<fieldset>
				<legend>Technical fields</legend>
				<table>
				<tr>
					<td><bean:message key="configuration.setsChunk.maxNumberOfRecords" /></td>
					<td>
						<html:text property="setsChunk_maxNumberOfRecords" />
						<html:errors property="setsChunk_maxNumberOfRecords" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.setsChunk.maxSizeInBytes" /></td>
					<td>
						<html:text property="setsChunk_maxSizeInBytes" />
						<html:errors property="setsChunk_maxSizeInBytes" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.identifiersChunk.maxNumberOfRecords" /></td>
					<td>
						<html:text property="identifiersChunk_maxNumberOfRecords" />
						<html:errors property="identifiersChunk_maxNumberOfRecords" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.identifiersChunk.maxSizeInBytes" /></td>
					<td>
						<html:text property="identifiersChunk_maxSizeInBytes" />
						<html:errors property="identifiersChunk_maxSizeInBytes" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.recordsChunk.maxNumberOfRecords" /></td>
					<td>
						<html:text property="recordsChunk_maxNumberOfRecords" />
						<html:errors property="recordsChunk_maxNumberOfRecords" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.recordsChunk.maxSizeInBytes" /></td>
					<td>
						<html:text property="recordsChunk_maxSizeInBytes" />
						<html:errors property="recordsChunk_maxSizeInBytes" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.expirationTime" /></td>
					<td>
						<html:text property="expirationDate" />
						<html:errors property="expirationDate" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.maxSimultaneousRequest" /></td>
					<td>&nbsp;<html:text property="maxSimultaneousRequest" />
						<html:errors property="maxSimultaneousRequest" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.schemaFile" /></td>
					<td>&nbsp;
						<html:radio property="schema" value="standard" />
						<bean:message key="configuration.schemaFile.standard" />
						<html:radio property="schema" value="custom" />
						<bean:message key="configuration.schemaFile.custom" />
						<html:errors property="schema" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.storageType" /></td>
					<td>&nbsp;
						<html:radio property="storageType" value="MySQL" />
						<bean:message key="configuration.storageType.MySQL" />
						<html:radio property="storageType" value="mixed" />
						<bean:message key="configuration.storageType.mixed" />
						<html:radio property="storageType" value="Lucene" />
						<bean:message key="configuration.storageType.Lucene" />
						<html:errors property="schema" />
					</td>
				</tr>
				<tr>
					<td><bean:message key="configuration.maxCacheLifetime" /></td>
					<td>&nbsp;<html:text property="maxCacheLifetime" />
						<html:errors property="maxCacheLifetime" />
					</td>
				</tr>
			</table>
			</fieldset>
			<html:submit property="submitButton" value="Submit" />
			<html:cancel />
		</html:form>

		<p><a href="index.jsp">back to index page</a></p>
	</body>
</html>

