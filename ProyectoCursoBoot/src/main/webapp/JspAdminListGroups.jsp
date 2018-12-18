<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html>
	<head>
		<meta charset="UTF-8">
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
					<td><a href="/admin/group/edit?groupId=${group.id}"><img src="/images/Modificar.png" height="20"/></a></td>
					<td><a href="/admin/group/delete?groupId=${group.id}" onclick="return confirm('<spring:message code="jsp.admin.listgroups.confirm" />')">
						<img src="/images/Borrar.png" height="20"/>
						</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>		
		<a href="/admin/mainmenu"><spring:message code="jsp.admin.listgroups.back"/></a>
	</body>
</html>
