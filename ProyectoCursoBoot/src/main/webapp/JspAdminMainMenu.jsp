<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title><spring:message code="admin.menu.title"/></title>
	</head>
	
	<body>
		<h1><spring:message code="admin.menu.options"/></h1>
		<br><br>
		<a href="/admin/user/new"><spring:message code="admin.menu.newuser"/></a>
		<br>
		<a href="/admin/user/list"><spring:message code="admin.menu.listusers"/></a>
		<br>
		<a href="/admin/group/new"><spring:message code="admin.menu.newgroup"/></a>
		<br>
		<a href="/admin/group/list"><spring:message code="admin.menu.listgroups"/></a>
		<br>
		<a href="/admin/doc/new"><spring:message code="admin.menu.newdoc"/></a>
		<br>
		<a href="/admin/doc/list"><spring:message code="admin.menu.listdocs"/></a>

		<br><br>
		<a href="/admin/logout"><spring:message code="admin.menu.logout"/></a>
		
		<br>
		<br>
		<a href="/admin/mainmenu?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/admin/mainmenu?lang=en"><spring:message code="idioma.en"/></a>
	</body>
</html>