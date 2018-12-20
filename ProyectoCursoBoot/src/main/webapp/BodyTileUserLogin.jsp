<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.cenec.imfe.proyecto.Constants"%>

<%@ page errorPage="JspUserError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<label class="main-title"><spring:message code="jsp.user.login.body"/></label>
		<br>
		
		<!-- Mensaje a mostrar (en caso de que exista alguno) -->
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>
		
		<form:form class="form-signin" modelAttribute="ModelAttrUser" action="/user/login" method="post">

			<spring:message code="jsp.user.login.nombre" var="labelUsername"/>
			<form:input path="nombre" class="form-control" placeholder="${labelUsername}"/>
			<form:errors path="nombre"/>

			<spring:message code="jsp.user.login.pwd" var="labelUserpwd"/>
			<form:input path="clave" class="form-control" placeholder="${labelUserpwd}"/>
			<form:errors path="clave"/>
			<br>
			<input type="submit" class="btn btn-lg btn-primary btn-block" value=<spring:message code="jsp.user.login.enviar"/>>
		</form:form>

		<br>
		<a href="/user/login?lang=es"><spring:message code="idioma.es"/></a>
		-
		<a href="/user/login?lang=en"><spring:message code="idioma.en"/></a>
