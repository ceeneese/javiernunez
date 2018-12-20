<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<label class="main-title"><spring:message code="jsp.admin.listgroups.body"/></label>
		<br><br>
		
		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>
		<br><br>		

		<table class="table table-bordered">
			<thead class="thead-dark">
				<tr>
					<th><spring:message code="jsp.admin.listgroups.tableheader.groupname"/></th>
					<th><spring:message code="jsp.admin.listgroups.tableheader.docs"/></th>
					<th><spring:message code="jsp.admin.listgroups.tableheader.modify"/></th>
					<th><spring:message code="jsp.admin.listgroups.tableheader.delete"/></th>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${ModelAttrGroupsList}" var="group">

					<c:set var="docs" value=""/>
					<c:forEach items="${group.documentos}" var="doc">
						<c:set var="docs" value="${docs} ${doc.name}<br>"/>
					</c:forEach>
					<!-- c:set var="docs" value="${docs}</p>"/ -->

				<tr>
      				<td>${group.nombre}</td>
      				<td>${docs}</td>

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
