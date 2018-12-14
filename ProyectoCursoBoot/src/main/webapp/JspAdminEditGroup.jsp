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
		<br>
		<h1>
		<c:choose>
			<c:when test = "${ModelAttrGroup.id != null}">
				<spring:message code="jsp.admin.editgroup.body.edit"/> ${ModelAttrGroup.nombre}
				<c:set var="backhref" value="admin/group/list"/>
				<c:set var="msgcode" value="jsp.admin.editgroup.back.list"/>
			</c:when>
	      
			<c:otherwise>
				<spring:message code="jsp.admin.editgroup.body.new"/>
				<c:set var="backhref" value="admin/mainmenu"/>
				<c:set var="msgcode" value="jsp.admin.editgroup.back.mainmenu"/>
			</c:otherwise>
		</c:choose>
		</h1><br>
		
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

		<c:choose>
			<c:when test = "${not empty ModelAttrGroup}">
				<a href="${backhref}"><spring:message code="${msgcode}"/></a>
			</c:when>
	      
			<c:otherwise>
				<a href="${backhref}"><spring:message code="${msgcode}"/></a>
			</c:otherwise>
		</c:choose>

	</body>
</html>