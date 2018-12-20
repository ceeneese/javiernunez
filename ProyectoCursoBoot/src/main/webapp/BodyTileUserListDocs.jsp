<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspUserError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		<label class="main-title"><spring:message code="jsp.user.docslist.body"/></label>
		<br>

		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<h3><label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label></h3>
		<br><br>
	
		<table class="table table-bordered">
			<thead class="thead-dark">
				<tr align="center">
					<td><h3><spring:message code="jsp.user.docslist.docname"/></h3></td>
					<td><h3><spring:message code="jsp.user.docslist.download"/></h3></td>
				</tr>
			</thead>
			
			<tbody>			
				<c:forEach items="${ModelAttrDocsList}" var="doc">
				<tr>
					<td>${doc.name}</td>
					<spring:message code="jsp.user.docslist.download" var="alternate"/>
					<td><a href="/user/download?docId=${doc.idDoc}" target="_blank"><img src="/images/Descarga.png" height="20" alt="${alternate}"/></a></td>
				</tr>
				</c:forEach>
			</tbody>			
		</table>
		
		<br>
		<br>
		<a href="/user/doclist?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/user/doclist?lang=en"><spring:message code="idioma.en"/></a>
