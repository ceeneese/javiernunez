<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

<!DOCTYPE html>

<html>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
	
	function changeElementsStatus(labelStr, disable)
	{
		var label = document.getElementById(labelStr);
		label.style.color = (disable ? "red" : "black");
		document.getElementById('botonSubmit').disabled = disable;
	}

	function comprobarWebUser()
	{
		var v = document.getElementById('usrAccesoWeb').value.length;
		
		if (v == 0)
		{
			changeElementsStatus('labelWebUser', false);
		}
		else
		{
			$.ajax(
			{
				url: "/admin/ajax/checkWebUser?webUsername=" + document.getElementById('usrAccesoWeb').value,
				method: "GET",
				contentType: "application/json",
				timeout: 20000,
				success: function(result)
				{
					if (result == 'ocupado')
					{
						changeElementsStatus('labelWebUser', true);
						alert('Nombre de usuario web ya utilizado');
					}
					else // if (result == 'libre')
					{
						changeElementsStatus('labelWebUser', false);
					}
				},
				error: function(result)
				{
					alert('Error');
					changeElementsStatus('labelWebUser', false);
				}
			});
		}
	}

	function comprobarIdCliente()
	{
		var v = document.getElementById('idCliente').value.length;
		
		if (v == 0)
		{
			changeElementsStatus('labelClientId', false);
		}
		else
		{
			$.ajax(
			{
				url: "/admin/ajax/checkClientId/" + document.getElementById('idCliente').value,
				method: "GET",
				contentType: "application/json",
				timeout: 20000,
				success: function(result)
				{
					if (result == 'ocupado')
					{
						changeElementsStatus('labelClientId', true);
						alert('Número de cliente ya utilizado');
					}
					else // if (result == 'libre')
					{
						changeElementsStatus('labelClientId', false);
					}
				},
				error: function(result)
				{
					alert('Error');
					changeElementsStatus('labelClientId', false);
				}
			});
		}
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

		<form:form modelAttribute="ModelAttrUser" method="POST" action="/admin/user/save" accept-charset="UTF-8">
			
			<form:hidden path="idUsuario"/>
			
			<table>
				<tr>
					<td><label id="labelClientId"><spring:message code="jsp.admin.edituser.clientid"/></label></td>
					<td><form:input path="idCliente" type="number" onblur="comprobarIdCliente()" readonly="${modelAttrUser.idCliente != null}"/></td>
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
					<td><label id="labelWebUser"><spring:message code="jsp.admin.edituser.webusr"/></label></td>
					<td><form:input path="usrAccesoWeb" onblur="comprobarWebUser()"/></td>
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
					<td><input id="botonSubmit" type="submit" value="<spring:message code="jsp.admin.edituser.submit"/>"/></td>
				</tr>
			</table>
		</form:form>
		
		<br><br>
		<a href="${backhref}">${msgcode}</a>

	</body>
	
</html>