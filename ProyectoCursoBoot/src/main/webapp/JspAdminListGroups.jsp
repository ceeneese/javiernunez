<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
		<h3><label id="msgLabel">${ModelAttrResultMsg}</label></h3>
		<br><br>		

		<table>
			<thead>
				<tr>
					<th><spring:message code="jsp.admin.listgroups.tableheader.groupname"/></th>
					<th><spring:message code="jsp.admin.listgroups.tableheader.modify"/></th>
					<th><spring:message code="jsp.admin.listgroups.tableheader.delete"/></th>
				</tr>
			</thead>
			
			<tbody>
				<!-- Constants.MODEL_ATTR_GROUPSLIST -->
				<c:forEach items="${ModelAttrGroupsList}" var="group">
				<tr>
      				<td>${group.nombre}</td>
					<td><a href="/admin/group/edit?groupId=${group.id}"><img src="/resources/images/Modificar.png"/></a></td>
					<td><a href="/admin/group/delete?groupId=${group.id}"><img src="/src/main/resources/images/Borrar.png"/></a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>		
		<a href="/admin/mainmenu"><spring:message code="jsp.admin.listgroups.back"/></a>
	</body>
</html>
