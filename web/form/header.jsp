<%@ page language="java" pageEncoding="UTF-8"
%><%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" 
%><%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" 
%><%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" 
%><%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" 
%><% response.addHeader("Content-Type", "text/xml;charset=UTF-8"); 
%><bean:write name="oaiRequestForm" property="responseDate" filter="false"/>
	<bean:write name="oaiRequestForm" property="requestUrl" filter="false"/>
