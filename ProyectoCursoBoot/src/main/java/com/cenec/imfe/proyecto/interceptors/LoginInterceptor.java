package com.cenec.imfe.proyecto.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cenec.imfe.proyecto.Constants;


public class LoginInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String uri = request.getRequestURI();
		
		// TODO Falta controlar el acceso del administrador
		
		Integer usrId = (Integer) request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
		
		if (usrId != null || uri.endsWith("/login") || uri.endsWith("/index") || uri.endsWith("/error"))
		{
			// Previamente autenticado o bien está tratando de acceder correctamente
			return true;
		}
		
		//Redirigir al inicio en caso de acceso prohibido
		response.sendRedirect(request.getContextPath() + "/index.jsp");

		return false;
	}
}