<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.admin.editgroup.title"/></title>
	</head>
	
	<!-- A esta JSP le llega MODEL_ATTR_DOCSLIST, y además un atributo MODEL_ATTR_GROUP en el caso de editar un grupo ya existente -->
	
	<body>
		<br><h1><spring:message code="jsp.admin.editgroup.body"/> ${ModelAttrGroup.nombre}</h1><br>
		
		<form:form modelAttribute="ModelAttrGroup" method="POST" action="/admin/group/save">
			
			<form:hidden path="id"/>
			
			<table>
				<tr>
					<td><spring:message code="jsp.admin.editgroup.groupname"/></td>
					<td><form:input path="nombre"/></td>
					<td><form:errors path="nombre" cssClass="error" /></td>
				</tr>
			</table>
				
			<form:checkboxes path="ModelAttrDocsList" items="${documentos}"/>				

			<!-- TODO Falta poner a 'checked' los documentos que actualmente están incluidos en el grupo -->

			<td><input type="submit" value="<spring:message code="jsp.admin.editdoc.docname"/>"/></td>
		</form:form>

		<a href="admin/group/list"><spring:message code="jsp.admin.editdoc.back"/></a>
	</body>
</html>