<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="xml" indent="yes" encoding="UTF-8" 
	omit-xml-declaration="yes"/>

<xsl:template match="/">
	<xsl:apply-templates />
</xsl:template>

<xsl:template match="metadata-formats">
	<xsl:element name="ListMetadataFormats">
		<xsl:apply-templates />
	</xsl:element>
</xsl:template>

<xsl:template match="metadata-format">
	<xsl:element name="metadataFormat">
		<xsl:apply-templates />
	</xsl:element>
</xsl:template>

<xsl:template match="metadata-prefix">
	<xsl:element name="metadataPrefix">
		<xsl:apply-templates />
	</xsl:element>
</xsl:template>

<xsl:template match="metadata-namespace">
	<xsl:element name="metadataNamespace">
		<xsl:apply-templates />
	</xsl:element>
</xsl:template>

<xsl:template match="xslt-file-name">
</xsl:template>

<xsl:template match="*">
	<xsl:copy-of select=".|@*|*" />
</xsl:template>

</xsl:stylesheet>