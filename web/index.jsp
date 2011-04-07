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
		<li><html:link action="configuration">OAI sample requests / repository configuration</html:link></li>
	</ul>
</fieldset>

</body>
</html>
