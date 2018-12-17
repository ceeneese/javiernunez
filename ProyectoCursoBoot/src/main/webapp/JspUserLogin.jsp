<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.cenec.imfe.proyecto.Constants"%>

<%@ page errorPage="JspUserError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.user.login.title"/></title>
	</head>

	<body>
		<h1><spring:message code="jsp.user.login.body"/></h1>
		<br><br><br>
		
		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>
		
		<form:form modelAttribute="ModelAttrUser" action="/user/login" method="post">
			<table>
				<tr>
					<td><spring:message code="jsp.user.login.nombre"/></td>
					<td><form:input path="usr"/></td>
				</tr>
				<tr>
					<td><spring:message code="jsp.user.login.pwd"/></td>
					<td><form:password path="pwd"/></td>
				</tr>
			</table>
			<br>
		
			<input type="submit" value=<spring:message code="jsp.user.login.enviar"/>>
		</form:form>

		<a href="/admin/login"><spring:message code="jsp.user.login.gotoadmin"/></a>

		<br>
		<br>
		<a href="/user/login?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/user/login?lang=en"><spring:message code="idioma.en"/></a>
	</body>
</html>
