<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" 
	xmlns:marc="http://www.loc.gov/MARC21/slim"
	xmlns:oai="http://www.openarchives.org/OAI/1.1/oai_marc" 
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	exclude-result-prefixes="oai">

<xsl:output method="xml" indent="yes" encoding="UTF-8" omit-xml-declaration="yes" />

<xsl:template match="marc:record">
	<!-- the standard count from 0, so the MARC 5th position 
		is (5+1)th position in XSLT -->
	<oai:oai_marc 
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		xsi:schemaLocation="http://www.openarchives.org/OAI/1.1/oai_marc http://www.openarchives.org/OAI/1.1/oai_marc.xsd">
		<xsl:attribute name="status"><xsl:value-of select="substring(marc:leader, 5+1, 1)"/></xsl:attribute>
		<xsl:attribute name="type"><xsl:value-of select="substring(marc:leader, 6+1, 1)"/></xsl:attribute>
		<xsl:attribute name="level"><xsl:value-of select="substring(marc:leader, 7+1, 1)"/></xsl:attribute>
		<xsl:attribute name="ctlType"><xsl:value-of select="substring(marc:leader, 8+1, 1)"/></xsl:attribute>
		<xsl:attribute name="charEnc"><xsl:value-of select="substring(marc:leader, 9+1, 1)"/></xsl:attribute>
		<xsl:attribute name="encLvl"><xsl:value-of select="substring(marc:leader, 17+1, 1)"/></xsl:attribute>
		<xsl:attribute name="catForm"><xsl:value-of select="substring(marc:leader, 18+1, 1)"/></xsl:attribute>
		<xsl:attribute name="lrRqrd"><xsl:value-of select="substring(marc:leader, 19+1, 1)"/></xsl:attribute>
		<xsl:apply-templates select="marc:controlfield|marc:datafield"/>
	</oai:oai_marc>
</xsl:template>

<xsl:template match="marc:controlfield">
	<xsl:element name="oai:fixfield">
		<xsl:attribute name="id">
			<xsl:call-template name="tag2id">
				<xsl:with-param name="tag" select="@tag"/>
			</xsl:call-template>
		</xsl:attribute>

		<xsl:value-of select="concat('&quot;', ., '&quot;')"/>
	</xsl:element>
</xsl:template>

<xsl:template match="marc:datafield">
	<xsl:element name="oai:varfield">

		<xsl:attribute name="id">
			<xsl:call-template name="tag2id">
				<xsl:with-param name="tag" select="@tag"/>
			</xsl:call-template>
		</xsl:attribute>

		<xsl:attribute name="i1">
			<xsl:call-template name="idBlankSpace">
				<xsl:with-param name="value" select="@ind1"/>
			</xsl:call-template>
		</xsl:attribute>

		<xsl:attribute name="i2">
			<xsl:call-template name="idBlankSpace">
				<xsl:with-param name="value" select="@ind2"/>
			</xsl:call-template>
		</xsl:attribute>

		<xsl:apply-templates select="marc:subfield"/>
	</xsl:element>
</xsl:template>

<xsl:template match="marc:subfield">
	<xsl:element name="oai:subfield">
		<xsl:attribute name="label">
			<xsl:value-of select="@code"/>
		</xsl:attribute>
		<xsl:value-of select="text()"/>
	</xsl:element>
</xsl:template>

<xsl:template name="tag2id">
	<xsl:param name="tag" />

	<xsl:choose>
		<xsl:when test="string-length($tag)=1">
			<xsl:text>00</xsl:text>
			<xsl:value-of select="$tag"/>
		</xsl:when>
		<xsl:when test="string-length($tag)=2">
			<xsl:text>0</xsl:text>
			<xsl:value-of select="$tag"/>
		</xsl:when>
		<xsl:when test="string-length($tag)=3">
			<xsl:value-of select="$tag"/>
		</xsl:when>
	</xsl:choose>
</xsl:template>

<xsl:template name="idBlankSpace">
	<xsl:param name="value" />
	<xsl:choose>
		<xsl:when test="string-length($value)=0">
			<xsl:text>&#160;</xsl:text>
		</xsl:when>
		<xsl:otherwise>
			<xsl:value-of select="$value"/>
		</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>
<!-- Stylus Studio meta-information - (c)1998-2002 eXcelon Corp.
<metaInformation>
	<scenarios/>
	<MapperInfo srcSchemaPath="oai_marc.xsd" srcSchemaRoot="oai_marc" 
		srcSchemaPathIsRelative="yes" srcSchemaInterpretAsXML="no" 
		destSchemaPath="http://www.loc.gov/standards/marcxml/schema/MARC21slim.xsd" 
		destSchemaRoot="" destSchemaPathIsRelative="yes" 
		destSchemaInterpretAsXML="no" />
</metaInformation>
-->