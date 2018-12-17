<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.admin.edituser.title"/></title>
	</head>
	
	<%-- A esta JSP le llegan los siguientes parámetros:
		MODEL_ATTR_USER (modelAttrUser) - El usuario a editar (vacío en caso de nuevo usuario)
		MODEL_ATTR_CHECKEDGROUPIDS (ModelAttrCheckedGroupsIds) - Lista de ids de los grupos a los que el usuario está suscrito
		MODEL_ATTR_GROUPSLIST (ModelAttrGroupsList) - Lista de todos los grupos de documentos disponibles en el sistema
	 --%>

	<script>
	
	function updateCheckersStatus()
	{
		var attachedGroups = ${ModelAttrCheckedGroupsIds};
		attachedGroups.forEach(updateChecker);
	}
	
	function updateChecker(groupId)
	{
		var checkbox = document.getElementById(groupId);
		checkbox.checked = true;
	}
	
	</script>
	
	<body onload="updateCheckersStatus()">
		
		<br><h1>
		<c:choose>
			<c:when test = "${modelAttrUser.idUsuario != null}">
				<%-- Estamos en modo de edición de usuario ya existente --%>
				<spring:message code="jsp.admin.edituser.body.edit"/> '${modelAttrUser.nombre} ${modelAttrUser.apellidos}'
				<c:set var="backhref" value="/admin/user/list"/>
				<spring:message code="jsp.admin.edituser.back.list" var="msgcode"/>
			</c:when>
	      
			<c:otherwise>
				<%-- Estamos en modo de nuevo usuario --%>
				<spring:message code="jsp.admin.edituser.body.new"/>
				<c:set var="backhref" value="/admin/mainmenu"/>
				<spring:message code="jsp.admin.edituser.back.mainmenu" var="msgcode"/>
			</c:otherwise>
		</c:choose>
		</h1><br>
		
		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>

		<form:form modelAttribute="ModelAttrUser" method="POST" action="/admin/user/save" accept-charset="ISO-8859-1">
			
			<form:hidden path="idUsuario"/>
			
			<table>
				<tr>
					<td><spring:message code="jsp.admin.edituser.clientid"/></td>
					<td><form:input path="idCliente" type="number" readonly="${modelAttrUser.idCliente != null}"/></td>
					<td><form:errors path="idCliente" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><spring:message code="jsp.admin.edituser.username"/></td>
					<td><form:input path="nombre"/></td>
					<td><form:errors path="nombre" cssClass="error" /></td>
				</tr>
			
				<tr>
					<td><spring:message code="jsp.admin.edituser.usersurname"/></td>
					<td><form:input path="apellidos"/></td>
					<td><form:errors path="apellidos" cssClass="error" /></td>
				</tr>
			
				<tr>
					<td><spring:message code="jsp.admin.edituser.date"/></td>
					<fmt:formatDate value="${modelAttrUser.fechaAlta}" pattern="yyyy-MM-dd" var="fechaAlta"/>
					<td><form:input path="fechaAlta" type="date" value="${fechaAlta}"/></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.address"/></td>
					<td><form:input path="direccion"/></td>
					<td><form:errors path="direccion" cssClass="error" /></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.city"/></td>
					<td><form:input path="ciudad"/></td>
					<td><form:errors path="ciudad" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><spring:message code="jsp.admin.edituser.province"/></td>
					<td><form:input path="provincia"/></td>
					<td><form:errors path="provincia" cssClass="error" /></td>
				</tr>
				
				<tr>
					<td><spring:message code="jsp.admin.edituser.phone"/></td>
					<td><form:input path="tfnoFijo"/></td>
					<td><form:errors path="tfnoFijo" cssClass="error" /></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.mobile"/></td>
					<td><form:input path="tfnoMovil"/></td>
					<td><form:errors path="tfnoMovil" cssClass="error" /></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.email"/></td>
					<td><form:input path="correo_e" type="email"/></td>
					<td><form:errors path="correo_e" cssClass="error" /></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.webusr"/></td>
					<td><form:input path="usrAccesoWeb"/></td>
					<td><form:errors path="usrAccesoWeb" cssClass="error" /></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.webpwd"/></td>
					<td><form:input path="pwdAccesoWeb"/></td>
					<td><form:errors path="pwdAccesoWeb" cssClass="error" /></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.includedgroups"/></td>
				</tr>

				<c:forEach items="${ModelAttrGroupsList}" var="group">
					<tr>
						<td><input type="checkbox" name="checkedGroups" value="${group.id}" id="${group.id}"/></td>
						<td>${group.nombre}</td>
					</tr>
				</c:forEach> 

				<tr>
					<td><input type="submit" value="<spring:message code="jsp.admin.edituser.submit"/>"/></td>
				</tr>
			</table>
		</form:form>
		
		<br><br>
		<a href="${backhref}">${msgcode}</a>

	</body>
	
</html>