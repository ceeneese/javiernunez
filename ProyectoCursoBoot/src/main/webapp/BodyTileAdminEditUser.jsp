<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  

	
	<%-- A esta JSP le llegan los siguientes parámetros:
		MODEL_ATTR_USER (ModelAttrUser) - El usuario a editar (vacío en caso de nuevo usuario)
		MODEL_ATTR_CHECKEDGROUPIDS (ModelAttrCheckedGroupsIds) - Lista de ids de los grupos a los que el usuario está suscrito
		MODEL_ATTR_GROUPSLIST (ModelAttrGroupsList) - Lista de todos los grupos de documentos disponibles en el sistema
	 --%>

	<script>
	
	$(document).ready(function()
	{
		// function updateCheckersStatus()
		var attachedGroups = ${ModelAttrCheckedGroupsIds};
		attachedGroups.forEach(updateChecker);
	});
	
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
					if (result == 'libre')
					{
						changeElementsStatus('labelWebUser', false);
					}
					else // se recibe el id de usuario que ya usa ese identificador
					{
						var v = document.getElementById('idUsuario').value
						
						if (result == v)
						{
							// Un usuario puede seguir teniendo el mismo identificador web 
							changeElementsStatus('labelWebUser', false);
						}
						else
						{
							changeElementsStatus('labelWebUser', true);
							alert('Nombre de usuario web ya utilizado');
						}
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
	
		<br><label class="main-title">
		<c:choose>
			<c:when test = "${ModelAttrUser.idUsuario != null}">
				<%-- Estamos en modo de edición de usuario ya existente --%>
				<spring:message code="jsp.admin.edituser.body.edit"/> '${ModelAttrUser.nombre} ${ModelAttrUser.apellidos}'
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
		</label><br>
		
		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>

		<form:form modelAttribute="ModelAttrUser" method="POST" action="/admin/user/save" accept-charset="UTF-8">
			
			<form:hidden path="idUsuario"/>
			
			<table class="table table-bordered">
				<thead class="thead-dark">
				<tr>
					<th><spring:message code="jsp.admin.edituser.tableheader.field"/></th>
					<th><spring:message code="jsp.admin.edituser.tableheader.value"/></th>
				</tr>
				
				</thead>
				<tbody>
				<tr>
					<td><label id="labelClientId"><spring:message code="jsp.admin.edituser.clientid"/></label></td>
					<td><form:input path="idCliente" type="number" onblur="comprobarIdCliente()" readonly="${ModelAttrUser.idCliente != null}"/></td>
					<!-- td><form:errors path="idCliente" cssClass="error" /></td -->
				</tr>
				
				<tr>
					<td><spring:message code="jsp.admin.edituser.username"/></td>
					<td><form:input path="nombre"/></td>
					<!-- td><form:errors path="nombre" cssClass="error" /></td -->
				</tr>
			
				<tr>
					<td><spring:message code="jsp.admin.edituser.usersurname"/></td>
					<td><form:input path="apellidos"/></td>
					<!-- td><form:errors path="apellidos" cssClass="error" /></td -->
				</tr>
			
				<tr>
					<td><spring:message code="jsp.admin.edituser.date"/></td>
					<fmt:formatDate value="${ModelAttrUser.fechaAlta}" pattern="yyyy-MM-dd" var="fechaAlta"/>
					<td><form:input path="fechaAlta" type="date" value="${fechaAlta}"/></td>
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.address"/></td>
					<td><form:input path="direccion"/></td>
					<!-- td><form:errors path="direccion" cssClass="error" /></td -->
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.city"/></td>
					<td><form:input path="ciudad"/></td>
					<!-- td><form:errors path="ciudad" cssClass="error" /></td -->
				</tr>
				
				<tr>
					<td><spring:message code="jsp.admin.edituser.province"/></td>
					<td><form:input path="provincia"/></td>
					<!-- td><form:errors path="provincia" cssClass="error" /></td -->
				</tr>
				
				<tr>
					<td><spring:message code="jsp.admin.edituser.phone"/></td>
					<td><form:input path="tfnoFijo"/></td>
					<!-- td><form:errors path="tfnoFijo" cssClass="error" /></td -->
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.mobile"/></td>
					<td><form:input path="tfnoMovil"/></td>
					<!-- td><form:errors path="tfnoMovil" cssClass="error" /></td -->
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.email"/></td>
					<td><form:input path="correo_e" type="email"/></td>
					<!-- td><form:errors path="correo_e" cssClass="error" /></td -->
				</tr>

				<tr>
					<td><label id="labelWebUser"><spring:message code="jsp.admin.edituser.webusr"/></label></td>
					<td><form:input path="usrAccesoWeb" onblur="comprobarWebUser()"/></td>
					<!-- td><form:errors path="usrAccesoWeb" cssClass="error" /></td -->
				</tr>

				<tr>
					<td><spring:message code="jsp.admin.edituser.webpwd"/></td>
					<td><form:input path="pwdAccesoWeb"/></td>
					<!-- td><form:errors path="pwdAccesoWeb" cssClass="error" /></td -->
				</tr>

				<tr>
					<td><label class="checklist"><spring:message code="jsp.admin.edituser.includedgroups"/></label></td>
					<td></td>
				</tr>

				<c:forEach items="${ModelAttrGroupsList}" var="group">
					<tr>
						<td><input type="checkbox" name="checkedGroups" value="${group.id}" id="${group.id}"/></td>
						<td>${group.nombre}</td>
					</tr>
				</c:forEach> 

				<tr>
					<td></td>
					<td><input class="btn btn-lg btn-primary btn-block" id="botonSubmit" type="submit" value="<spring:message code="jsp.admin.edituser.submit"/>"/></td>
				</tr>
				</tbody>
			</table>
		</form:form>
		
		<br><br>
		<a href="${backhref}">${msgcode}</a>
