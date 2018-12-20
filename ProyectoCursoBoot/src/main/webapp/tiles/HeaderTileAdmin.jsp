<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
	 
	<c:choose>
		<c:when test="${empty SessionAttrAdminId}">
			<a class="navbar-brand" href="/user/login"><spring:message code="tile.admin.header.logasuser"/></a>
		</c:when>
		<c:otherwise>
			<a class="navbar-brand" href="/admin/logout"><spring:message code="tile.admin.header.logout"/></a>
		</c:otherwise>
	</c:choose>     

	<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<c:if test="${not empty SessionAttrAdminId}">
		<div class="collapse navbar-collapse" id="navbarsExampleDefault">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="/admin/mainmenu"><spring:message code="tile.admin.header.mainmenu"/></a></li>
			</ul>
		</div>
	</c:if>
		
	</nav>

	