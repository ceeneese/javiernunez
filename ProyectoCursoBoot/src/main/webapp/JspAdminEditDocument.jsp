<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.admin.editdoc.title"/></title>
	</head>
	
	<body>

		<br><h1>
		<c:choose>
			<c:when test = "${ModelAttrDocument.idDoc != null}">
				<%-- Estamos en modo de edici�n de documento ya existente --%>
				<spring:message code="jsp.admin.editdoc.body.edit"/> ${ModelAttrDocument.name}
				<c:set var="backhref" value="/admin/doc/list"/>
				<spring:message code="jsp.admin.editdoc.back.list" var="msgcode"/>
			</c:when>
	      
			<c:otherwise>
				<%-- Estamos en modo de nuevo documento --%>
				<spring:message code="jsp.admin.editdoc.body.new"/>
				<c:set var="backhref" value="/admin/mainmenu"/>
				<spring:message code="jsp.admin.editdoc.back.mainmenu" var="msgcode"/>
			</c:otherwise>
		</c:choose>
		</h1><br>
		
		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>

		<form:form modelAttribute="ModelAttrDocument" method="POST" action="/admin/doc/save" enctype="multipart/form-data">
			
			<form:hidden path="idDoc"/>
			
			<table>
				<tr>
					<td><spring:message code="jsp.admin.editdoc.docname"/></td>
					<td><form:input path="name"/></td>
					<td><form:errors path="name" cssClass="error"/></td>
				</tr>
<%--
				<tr>
					<td><spring:message code="jsp.admin.editdoc.file"/></td>
					<td><form:input path="location"/></td>
					<td><form:errors path="location" cssClass="error"/></td>
				</tr>
-->
				<%-- S�lo se permite subir un archivo cuando se trata de nuevo documento.
				     Para modificaciones s�lo se permite cambiar el nombre --%>
				<c:if test="${ModelAttrDocument.idDoc == null}">
				<tr>
					<td><spring:message code="jsp.admin.editdoc.file"/></td>
					<td><input type="file" name="file"></td>
				</tr>
				</c:if>
				<tr>
					<td><input type="submit" value="<spring:message code="jsp.admin.editdoc.submit"/>"/></td>
				</tr>
		</table>
		</form:form>
		
		<br><br>
		<a href="${backhref}">${msgcode}</a>
	</body>
</html>