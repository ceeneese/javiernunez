<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><spring:message code="jsp.admin.login.title"/></title>
	</head>

	<body>
		<h1><spring:message code="jsp.admin.login.body"/></h1>
		<br>

		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>
		<br><br>
		
		<form:form modelAttribute="ModelAttrAdmin" action="/admin/login" method="post">
			<table>
				<tr>
					<td><spring:message code="jsp.admin.login.nombre"/></td>
					<td><form:input path="nombre"/></td>
					<td><form:errors path="nombre"/></td>
				</tr>
				<tr>
					<td><spring:message code="jsp.admin.login.pwd"/></td>
					<td><form:password path="clave"/></td>
					<td><form:errors path="clave"/></td>
				</tr>
			</table>
			<br>
		
			<input type="submit" value=<spring:message code="jsp.admin.login.enviar"/>>
		</form:form>

		<br>
		<br>
		<a href="/admin/login?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/admin/login?lang=en"><spring:message code="idioma.en"/></a>
	</body>
</html>
