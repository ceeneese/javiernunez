<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspUserError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><spring:message code="jsp.user.docslist.title"/></title>
	</head>

	<body>
		<h1><spring:message code="jsp.user.docslist.body"/></h1>
		<br>

		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>
		<br><br>
	
		<table>
			<thead>
				<tr align="center">
					<td><h3><spring:message code="jsp.user.docslist.docname"/></h3></td>
					<td><h3><spring:message code="jsp.user.docslist.download"/></h3></td>
				</tr>
			</thead>
			
			<tbody>			
				<c:forEach items="${ModelAttrDocsList}" var="doc">
				<tr>
					<td>${doc.name}</td>
					<spring:message code="jsp.user.docslist.download" var="alternate"/>
					<td><a href="/user/download?docId=${doc.idDoc}" target="_blank"><img src="/images/Descarga.png" height="20" alt="${alternate}"/></a></td>
				</tr>
				</c:forEach>
			</tbody>			
		</table>
	
	<br>
		<a href="/user/logout"><spring:message code="jsp.user.docslist.logout"/></a>
		
		<br>
		<br>
		<a href="/user/doclist?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/user/doclist?lang=en"><spring:message code="idioma.en"/></a>
		
	</body>
</html>