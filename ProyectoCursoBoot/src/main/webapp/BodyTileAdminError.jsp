<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page isErrorPage="true"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
		
		<label class="main-title"><spring:message code="jsp.admin.error.message"/></label>
		<br>
		${ModelAttrError}
		<br><br>
		
		<!-- TODO 'inicio' está definido como constante -->
		<a href="/admin/login"><spring:message code="jsp.admin.error.exitlink"/></a>
