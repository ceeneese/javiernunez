<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ page isErrorPage="true"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title><spring:message code="jsp.user.error.title"/></title>
	</head>

	<body>

		<spring:message code="jsp.user.error.message"/>
		<br>
		${ModelAttrError}
		<br><br>
		
		<!-- TODO 'inicio' está definido como constante -->
		<a href="inicio"><spring:message code="jsp.user.error.exitlink"/></a>
	</body>
</html>