<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.cenec.imfe.proyecto.Constants"%>

<%@ page errorPage="JspError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.user.login.title"/></title>
	</head>

	<body>
		<h1><spring:message code="jsp.user.login.body"/></h1>
		<br><br><br>
		
		<!-- TODO 'ModelAttrResultMsg' está declarado como constante 'MODEL_ATTR_RESULTMSG' en Constants -->
		<!-- Mensaje de error a mostrar (en caso de que exista alguno) -->
		<h3><label id="errorLabel">${ModelAttrResultMsg}</label></h3>
		
		<form:form modelAttribute="modelAttrUser" action="/user/login" method="post">
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
		<!-- TODO 'inicio' está declarado como constante '' en Constants -->
		<a href="inicio?lang=es"><spring:message code="idioma.es"/></a>
		<a href="inicio?lang=en"><spring:message code="idioma.en"/></a>
	</body>
</html>
