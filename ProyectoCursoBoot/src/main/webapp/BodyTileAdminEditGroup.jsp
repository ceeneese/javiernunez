<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

	
	<%-- A esta JSP le llega MODEL_ATTR_DOCSLIST, y además un atributo MODEL_ATTR_GROUP que puede estar vacío en el caso de nuevo grupo --%>

	<script>
	$(document).ready(function()
	{
		// function updateCheckersStatus()
		var attachedDocs = ${ModelAttrCheckedDocsIds};
		attachedDocs.forEach(updateChecker);
	});
			
	function updateChecker(documentId)
	{
		var checkbox = document.getElementById(documentId);
		// checkbox.click();
		checkbox.checked = true;
	}
	
	</script>
	
		<br><label class="main-title">
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
		</label><br>
		
		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>
		
		<form:form modelAttribute="ModelAttrGroup" method="POST" action="/admin/group/save">
			
			<form:hidden path="id"/>
			
			<table class="table table-bordered">
				<thead class="thead-dark">
				<tr>
					<th><spring:message code="jsp.admin.editgroup.tableheader.field"/></th>
					<th><spring:message code="jsp.admin.editgroup.tableheader.value"/></th>
				</tr>
				</thead>
				
				<tbody>
				<tr>
					<td><spring:message code="jsp.admin.editgroup.groupname"/></td>
					<td><form:input path="nombre"/></td>
					<!-- td><form:errors path="nombre" cssClass="error" /></td -->
				</tr>
			
				<tr>
					<td><label class="checklist"><spring:message code="jsp.admin.editgroup.includeddocs"/></label></td>
					<td></td>
				</tr>

				<c:forEach items="${ModelAttrDocsList}" var="doc">
					<tr>
						<td><input type="checkbox" name="checkedDocs" value="${doc.idDoc}" id="${doc.idDoc}"/></td>
						<td>${doc.name}</td>
					</tr>
				</c:forEach> 

				<tr>
					<td></td>
					<td><input class="btn btn-lg btn-primary btn-block" type="submit" value="<spring:message code="jsp.admin.editgroup.submit"/>"/></td>
				</tr>
				</tbody>
			</table>
		</form:form>
		
		<br><br>
		<a href="${backhref}">${msgcode}</a>
