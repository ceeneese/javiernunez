<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page errorPage="JspError.jsp"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.user.docslist.title"/></title>
	</head>

	<body>
	
		<!-- TODO 'ModelAttrResultMsg' está declarado como constante 'MODEL_ATTR_RESULTMSG' en Constants -->
		<!-- Mensaje de error a mostrar (en caso de que exista alguno) -->
		<h3><label id="errorLabel">${ModelAttrResultMsg}</label></h3>
	
		<h1><spring:message code="jsp.user.docslist.availabledocs"/></h1>
		<br>
		<table>
			<tr align="center">
				<td><h3><spring:message code="jsp.user.docslist.docname"/></h3></td>
				<td><h3><spring:message code="jsp.user.docslist.download"/></h3></td>
			</tr>
			
		<!-- 	items="${Constants.MODEL_ATTR_DOCSLIST}" var="doc">  -->
			<c:forEach
		 		items="${ModelAttrDocsList}" var="doc">
				<tr>
					<td>${doc.name}</td>
					<td><a href="user/download?docId=${doc.idDoc}"> <img src="images/descarga.png" height="20" alt="<spring:message code="user.doc.download"/>"/></a> </td>
				</tr>
			</c:forEach>
			
		</table>
	
	<br>
		<a href="index.jsp">Volver al inicio</a>
	</body>
</html>