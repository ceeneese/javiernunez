<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page errorPage="JspAdminError.jsp"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

		<label class="main-title"><spring:message code="jsp.admin.mainmenu.options"/></label>
		<br><br>
		
		<table class="table table-bordered">
			<thead class="thead-dark">
				<tr>
					<th><spring:message code="jsp.admin.mainmenu.operations"/>
				</th>
			</thead>
			<tbody>				
				<tr><td><a href="/admin/user/new"><spring:message code="jsp.admin.mainmenu.newuser"/></a></td></tr>

				<tr><td><a href="/admin/user/list"><spring:message code="jsp.admin.mainmenu.listusers"/></a></td></tr>

				<tr><td><a href="/admin/group/new"><spring:message code="jsp.admin.mainmenu.newgroup"/></a></td></tr>

				<tr><td><a href="/admin/group/list"><spring:message code="jsp.admin.mainmenu.listgroups"/></a></td></tr>

				<tr><td><a href="/admin/doc/new"><spring:message code="jsp.admin.mainmenu.newdoc"/></a></td></tr>

				<tr><td><a href="/admin/doc/list"><spring:message code="jsp.admin.mainmenu.listdocs"/></a></td></tr>
			</tbody>
		</table>		
		<br>
		<br>
		<a href="/admin/mainmenu?lang=es"><spring:message code="idioma.es"/></a>
		<a href="/admin/mainmenu?lang=en"><spring:message code="idioma.en"/></a>
