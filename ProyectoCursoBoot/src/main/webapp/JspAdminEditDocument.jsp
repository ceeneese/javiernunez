<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.admin.editdoc.title"/></title>
	</head>
	
	<body>
		<form:form modelAttribute="ModelAttrDocument" method="POST" action="/admin/doc/save">
			
			<form:hidden path="idDoc"/>
			
			<table>
				<tr>
					<td><spring:message code="jsp.admin.editdoc.docname"/></td>
					<td><form:input path="name"/></td>
					<td><form:errors path="name" cssClass="error" /></td>
				</tr>
				
				<!-- TODO Falta poner el botón de subida de archivo --> 
				
				<tr>
					<td><spring:message code="jsp.admin.editdoc.file"/></td>
					<td><form:input type = "file" path="location___MAL" /></td>
					<td><form:errors path="location___MAL" cssClass="error" /></td>
				</tr>
			<tr>
				<td><input type="submit" value="<spring:message code="jsp.admin.editdoc.docname"/>"/></td>
			</tr>
		</table>
		</form:form>

		<br>
		${msg}
		<br>

		<a href="listar"><spring:message code="accion.listar"/></a>
	</body>
</html>