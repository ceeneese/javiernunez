<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="ISO-8859-1">
		<title><spring:message code="jsp.admin.listgroups.title"/></title>
	</head>
	
	<body>
		<h1><spring:message code="jsp.admin.listgroups.body"/></h1>
		<br><br>
		
		<!-- Constants 'MODEL_ATTR_RESULTMSG' -->
		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<h3><label id="errorLabel">${ModelAttrResultMsg}</label></h3>
		<br><br>		

		<table>
			<tr>
				<th><spring:message code="jsp.admin.listgroups.tableheader.groupname"/></th>
			</tr>
			
			<!-- Constants.MODEL_ATTR_GROUPSLIST -->
			<c:forEach items="${ModelAttrGroupsList}" var="group">
				
				<tr>
      				<td>${group.name}</td>
					<td><a href="admin/group/edit?idUser=${group.id}"><img src="images/modificar.png"/></a></td>
					<td><a href="admin/group/delete?idUser=${group.id}"><img src="images/borrar.png"/></a></td>
				</tr>
		</c:forEach>
			
		</table>
	</body>
</html>
