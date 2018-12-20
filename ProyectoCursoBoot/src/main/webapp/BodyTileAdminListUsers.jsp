<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<label class="main-title"><spring:message code="jsp.admin.listusers.body"/></label>
		<br><br>

		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>
		<br><br>		
		
		<table class="table table-bordered">
			<thead class="thead-dark">
				<tr>
					<th><spring:message code="jsp.admin.listusers.tableheader.clientid"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.username"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.usercity"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.userprovince"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.userphone"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.usermobile"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.usermail"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.groups"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.modify"/></th>
					<th><spring:message code="jsp.admin.listusers.tableheader.delete"/></th>
				</tr>
			</thead>
			
			<tbody>			
				<!-- Constants.MODEL_ATTR_USERSLIST -->
				<c:forEach items="${ModelAttrUsersList}" var="user">
				
				<c:choose>
					<c:when test = "${user.idCliente != null}">
 						<c:set var="clientid" value="${user.idCliente}"/>
					</c:when>
         
					<c:otherwise>
						<spring:message code="jsp.admin.listusers.noclient" var="clientid"/>
					</c:otherwise>
				</c:choose>

				<c:set var="grupos" value="<p>"/>
				<c:forEach items="${user.grupos}" var="grupo">
					<c:set var="grupos" value="${grupos}${grupo.nombre}<br>"/>
				</c:forEach>
				<c:set var="grupos" value="${grupos}</p>"/>

				<tr>
					<td>${clientid}</td>
      				<td>${user.nombre} ${user.apellidos}</td>
     				<td>${user.ciudad}</td>
     				<td>${user.provincia}</td>
     				<td>${user.tfnoFijo}</td>
     				<td>${user.tfnoMovil}</td>
     				<td>${user.correo_e}</td>
     				<td>${grupos}</td>
					<td><a href="/admin/user/edit?idUser=${user.idUsuario}"><img src="/images/Modificar.png" height="20"/></a></td>
					<td><a href="/admin/user/delete?idUser=${user.idUsuario}" onclick="return confirm('<spring:message code="jsp.admin.listgroups.confirm" />')">
						<img src="/images/Borrar.png" height="20"/>
						</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>		
		<a href="/admin/mainmenu"><spring:message code="jsp.admin.listusers.back"/></a>
