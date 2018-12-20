<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

		<label class="main-title"><spring:message code="jsp.admin.login.body"/></label>
		<br>
		
		<%-- Mensaje a mostrar (en caso de que exista alguno) --%>
		<label id="msgLabel" class="result-msg">${ModelAttrResultMsg}</label>
		
		<form:form class="form-signin" modelAttribute="ModelAttrAdmin" action="/admin/login" method="post">

			<spring:message code="jsp.admin.login.nombre" var="labelAdminname"/>
			<form:input path="nombre" class="form-control" placeholder="${labelAdminname}"/>
			<form:errors path="nombre"/>

			<spring:message code="jsp.admin.login.pwd" var="labelAdminpwd"/>
			<form:input path="clave" class="form-control" placeholder="${labelAdminpwd}"/>
			<form:errors path="clave"/>
			<br>
			<input type="submit" class="btn btn-lg btn-primary btn-block" value=<spring:message code="jsp.admin.login.enviar"/>>
		</form:form>

		<br>
		<a href="/admin/login?lang=es"><spring:message code="idioma.es"/></a>
		-
		<a href="/admin/login?lang=en"><spring:message code="idioma.en"/></a>
