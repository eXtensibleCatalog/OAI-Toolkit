<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" 
	xmlns:marc="http://www.loc.gov/MARC21/slim" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	exclude-result-prefixes="marc" >

<!--* PARAMETERS                                   *-->
<!--************************************************-->
<!--* Create 001 field from 907 if there is no 001 field. If you want this behavior, set it to 1 *-->
<xsl:param name="use907as001"   >1</xsl:param>

<xsl:output method="xml" indent="yes" encoding="UTF-8" omit-xml-declaration="yes" />

<xsl:template match="/">
	<xsl:apply-templates select="//marc:record"/>
</xsl:template>

<!-- process collection -->
<xsl:template match="marc:collection">
	<xsl:copy>
		<xsl:copy-of select="@*" />
		<xsl:apply-templates />
	</xsl:copy>
</xsl:template>

<!-- process MARC record -->
<xsl:template match="marc:record">
	<xsl:copy>
		<xsl:apply-templates />
	</xsl:copy>
</xsl:template>

<!-- process MARC Leader -->
<xsl:template match="marc:leader">
	<xsl:copy-of select="." />
</xsl:template>

<!-- process control number field (MARC 001) 
<xsl:template match="marc:controlfield[@tag='001']">
	<xsl:copy>
		<xsl:copy-of select="@*" />
		<xsl:apply-templates />
	</xsl:copy>
</xsl:template>-->

<!-- process control fields (MARC 002-009) -->
<xsl:template match="marc:controlfield">
	<!--* create 001 field from 907 
	<xsl:if test="$use907as001 = '1' and name(preceding-sibling::*[1]) != 'controlfield'">
		<xsl:if test="not(../marc:controlfield[@tag='001']) and ../marc:datafield[@tag='907']">-->
			<!-- xsl:message>apply use907as001 modification</xsl:message -->
			<xsl:copy>
				<xsl:attribute name="tag">001</xsl:attribute>
				<xsl:value-of select="../marc:datafield[@tag='907']/*/text()" />
			</xsl:copy>
		<!--</xsl:if>
	</xsl:if>-->
	<xsl:copy>
		<xsl:copy-of select="@*" />
		<xsl:apply-templates />
	</xsl:copy>
</xsl:template>
	
<!-- process data fields (MARC 010-999) -->
<xsl:template match="marc:datafield">
	<xsl:copy>
		<xsl:copy-of select="@*" />
		<xsl:apply-templates />
	</xsl:copy>
</xsl:template>
	
<!-- process subfields -->
<xsl:template match="marc:subfield">
	<xsl:copy>
		<xsl:copy-of select="@*" />
		<xsl:apply-templates />
	</xsl:copy>
</xsl:template>

<!-- no not process other elements -->
<xsl:template match="*">
	<xsl:message>unhandled XML element: <xsl:value-of select="name(.)" /></xsl:message>
</xsl:template>

</xsl:stylesheet>
