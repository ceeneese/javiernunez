<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspUserError.jsp"%>
 
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><spring:message code="jsp.user.downloaderror.title"/></title>
	</head>

	<body>
		<h1><spring:message code="jsp.user.downloaderror.message"/></h1>
		<br><br>
		${ModelAttrResultMsg}
	</body>
</html>