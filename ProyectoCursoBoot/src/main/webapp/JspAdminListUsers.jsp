<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title><spring:message code="jsp.admin.listusers.title"/></title>
	</head>
	
	<body>
		<h1><spring:message code="jsp.admin.listusers.body"/></h1>
		<br><br>

		<!-- Constants 'MODEL_ATTR_RESULTMSG' -->
		<!-- Mensaje de error a mostrar (en caso de que exista alguno) -->
		<h3><label id="errorLabel">${ModelAttrResultMsg}</label></h3>
		<br><br>		
		
		<table>
			<tr>
				<th><spring:message code="jsp.admin.listusers.tableheader.clientid"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.username"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.usercity"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.userprovince"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.userphone"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.usermobile"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.usermail"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.modify"/></th>
				<th><spring:message code="jsp.admin.listusers.tableheader.delete"/></th>
			</tr>
			
			<!-- Constants.MODEL_ATTR_USERSLIST -->
			<c:forEach items="${ModelAttrUsersList}" var="user">
				
				<!-- Creo que este 'choose' no es necesario,  -->
				<c:choose>
					<c:when test = "${user.clientid != null}">
 						<c:set var = "clientid" value = "${user.clientid}"/>
					</c:when>
         
					<c:otherwise>
						<c:set var = "clientid" value = "---"/>
					</c:otherwise>
				</c:choose>

				<tr>
					<td>${clientid}</td>
      				<td>${user.nombre}</td>
     				<td>${user.ciudad}</td>
     				<td>${user.provincia}</td>
     				<td>${user.tfnoFijo}</td>
     				<td>${user.tfnoMovil}</td>
     				<td>${user.correo_e}</td>
					<td><a href="admin/user/edit?idUser=${user.idCliente}"><img src="images/modificar.png"/></a></td>
					<td><a href="admin/user/delete?idUser=${user.idCliente}"><img src="images/borrar.png"/></a></td>
				</tr>
		</c:forEach>
			
		</table>
	</body>
</html>
