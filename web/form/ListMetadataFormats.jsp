<%@ page language="java" pageEncoding="UTF-8"
%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" 
%><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" 
%><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" 
%><%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" 
%><% response.addHeader("Content-Type", "text/xml;charset=UTF-8"); 
%><?xml version="1.0" encoding="UTF-8" ?>
<OAI-PMH xmlns="http://www.openarchives.org/OAI/2.0/" 
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
	xsi:schemaLocation="http://www.openarchives.org/OAI/2.0/ http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd">
	<tiles:insert page="/form/header.jsp" />
<bean:write name="oaiRequestForm" property="xml" filter="false" />
</OAI-PMH>