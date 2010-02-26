<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" 
    xmlns:marc="http://www.loc.gov/MARC21/slim" 
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    exclude-result-prefixes="marc" >
    
    <!--* PARAMETERS                                   *-->
    <!--************************************************-->
    <!--* Populate controlfield 003 if it is missing,e mpty or replace the existing 003 if present *-->
    <xsl:param name="val003"></xsl:param> 
    <!-- Put the value of your institution's repository code here before the </xsl:param> which is the 003 field 
        For example: <xsl:param name="val003">NRU</xsl:param>  -->
    
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
    
    <!-- process control number field (MARC 001) -->
    <xsl:template match="marc:controlfield">
        <!-- <xsl:message>position: <xsl:value-of select="count(preceding-sibling::marc:controlfield)"/></xsl:message> -->
        <xsl:if test="not(@tag = '003')">
            <xsl:copy>
                <xsl:copy-of select="@*" />
                <xsl:apply-templates />
            </xsl:copy>
        </xsl:if>
        <xsl:if test="count(preceding-sibling::marc:controlfield) = 0 
            and $val003 != ''">
            <controlfield>
                <xsl:attribute name="tag">003</xsl:attribute>
                <xsl:value-of select="$val003" />
            </controlfield>
        </xsl:if>
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
