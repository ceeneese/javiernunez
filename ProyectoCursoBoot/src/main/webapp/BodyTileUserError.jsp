<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page isErrorPage="true"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


		<label class="main-title"><spring:message code="jsp.user.error.message"/></label>
		<br>
		${ModelAttrError}
		<br><br>
		
		<a href="/user/login"><spring:message code="jsp.user.error.exitlink"/></a>
