<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.admin.editgroup.title"/></title>
	</head>
	
	<%-- A esta JSP le llega MODEL_ATTR_DOCSLIST, y además un atributo MODEL_ATTR_GROUP que puede estar vacío en el caso de nuevo grupo --%>

	<script>
	
	function updateCheckersStatus()
	{
		var attachedDocs = ${ModelAttrCheckedDocsIds};
		attachedDocs.forEach(updateChecker);
	}
	
	function updateChecker(documentId)
	{
		var checkbox = document.getElementById(documentId);
		// checkbox.click();
		checkbox.checked = true;
	}
	
	</script>
	
	<body onload="updateCheckersStatus()">
		<br><h1>
		<c:choose>
			<c:when test = "${ModelAttrGroup.id != null}">
				<%-- Estamos en modo de edición de grupo ya existente --%>
				<spring:message code="jsp.admin.editgroup.body.edit"/> '${ModelAttrGroup.nombre}'
				<c:set var="backhref" value="/admin/group/list"/>
				<spring:message code="jsp.admin.editgroup.back.list" var="msgcode"/>
			</c:when>
	      
			<c:otherwise>
				<%-- Estamos en modo de nuevo grupo --%>
				<spring:message code="jsp.admin.editgroup.body.new"/>
				<c:set var="backhref" value="/admin/mainmenu"/>
				<spring:message code="jsp.admin.editgroup.back.mainmenu" var="msgcode"/>
			</c:otherwise>
		</c:choose>
		</h1><br>
		
		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>
		
		<form:form modelAttribute="ModelAttrGroup" method="POST" action="/admin/group/save">
			
			<form:hidden path="id"/>
			
			<table>
				<tr>
					<td><spring:message code="jsp.admin.editgroup.groupname"/></td>
					<td><form:input path="nombre"/></td>
					<td><form:errors path="nombre" cssClass="error" /></td>
				</tr>
			
				<tr>
					<td><spring:message code="jsp.admin.editgroup.includeddocs"/></td>
				</tr>

				<c:forEach items="${ModelAttrDocsList}" var="doc">
					<tr>
						<td><input type="checkbox" name="checkedDocs" value="${doc.idDoc}" id="${doc.idDoc}"/></td>
						<td>${doc.name}</td>
					</tr>
				</c:forEach> 

				<tr>
					<td><input type="submit" value="<spring:message code="jsp.admin.editgroup.submit"/>"/></td>
				</tr>
			</table>
		</form:form>
		
		<br><br>
		<a href="${backhref}">${msgcode}</a>

	</body>
	
</html>