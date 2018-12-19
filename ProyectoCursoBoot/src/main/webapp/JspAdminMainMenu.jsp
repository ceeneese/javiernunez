<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
		<title><spring:message code="jsp.admin.mainmenu.title"/></title>
	</head>
	
	<body>
		<h1><spring:message code="jsp.admin.mainmenu.options"/></h1>
		<br><br>
		<a href="/admin/user/new"><spring:message code="jsp.admin.mainmenu.newuser"/></a>
		<br>
		<a href="/admin/user/list"><spring:message code="jsp.admin.mainmenu.listusers"/></a>
		<br>
		<a href="/admin/group/new"><spring:message code="jsp.admin.mainmenu.newgroup"/></a>
		<br>
		<a href="/admin/group/list"><spring:message code="jsp.admin.mainmenu.listgroups"/></a>
		<br>
		<a href="/admin/doc/new"><spring:message code="jsp.admin.mainmenu.newdoc"/></a>
		<br>
		<a href="/admin/doc/list"><spring:message code="jsp.admin.mainmenu.listdocs"/></a>

		<br><br>
		<a href="/admin/logout"><spring:message code="jsp.admin.mainmenu.logout"/></a>
		
		<br>
		<br>
		<a href="/admin/mainmenu?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/admin/mainmenu?lang=en"><spring:message code="idioma.en"/></a>
	</body>
</html>