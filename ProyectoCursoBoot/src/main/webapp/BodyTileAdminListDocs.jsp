<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

		<label class="main-title"><spring:message code="jsp.admin.listdocs.body"/></label>
		<br><br>
		
		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>
		<br><br>		

		<table class="table table-bordered">
			<thead class="thead-dark">
				<tr>
					<th><spring:message code="jsp.admin.listdocs.tableheader.docname"/></th>
					<th><spring:message code="jsp.admin.listdocs.tableheader.modify"/></th>
					<th><spring:message code="jsp.admin.listdocs.tableheader.delete"/></th>
				</tr>
			</thead>
			
			<tbody>
				<!-- Constants.MODEL_ATTR_DOCSLIST -->
				<c:forEach items="${ModelAttrDocsList}" var="doc">
				<tr>
      				<td>${doc.name}</td>
					<td><a href="/admin/doc/edit?docId=${doc.idDoc}">
							<img src="/images/Modificar.png" height="20"/>
						</a></td>
					<td><a href="/admin/doc/delete?docId=${doc.idDoc}" onclick="return confirm('<spring:message code="jsp.admin.listdocs.confirm" />')">
						<img src="/images/Borrar.png" height="20"/>
						</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
				
		<a href="/admin/mainmenu"><spring:message code="jsp.admin.listdocs.back"/></a>
