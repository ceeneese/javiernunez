package com.cenec.imfe.proyecto.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cenec.imfe.proyecto.Constants;

/**
 * Clase que controla el acceso a las diferentes URIs del sistema en función de si hay algún usuario o administrador que ha iniciado sesión
 * 
 * @author Javier
 */
public class LoginInterceptor extends HandlerInterceptorAdapter
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		String uri = request.getRequestURI();
		
		Integer usrId = (Integer) request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
		Integer adminId = (Integer) request.getSession().getAttribute(Constants.SESSION_ATTR_ADMINID);
		
		// TODO Falta comprobar los accesos a error
		if (uri.endsWith("Error") || uri.endsWith("/error"))
		{
			return true;
		}
		
		if (usrId == null && adminId == null)
		{
			// Nadie ha iniciado sesión, se permite acceder al login
			// TODO Falta controlar si es necesario comprobar 'index'
			if (uri.endsWith(Constants.URI_OPERATION_LOGIN) || uri.endsWith("/index"))
			{
				return true;
			}
			else
			{
				response.sendRedirect(Constants.URI_USER_LOGIN);
				return false;
			}
		}
		else if (usrId != null && adminId != null)
		{
			// Algo raro ha ocurrido, tanto usuario como administrador han iniciado sesión
			request.getSession().invalidate();
			response.sendRedirect(Constants.URI_USER_LOGIN);
			
			return false;
		}
		else if (usrId != null)
		{
			// Usuario conectado
			if (uri.startsWith(Constants.URI_BASE_USER))
			{
				if (uri.equals(Constants.URI_USER_LOGIN))
				{
					// Un usuario ya conectado quiere hacer login de nuevo: se le envía al inicio
					response.sendRedirect(Constants.URI_USER_DOCLIST);
					return false;
				}
				
				return true;
			}
			else
			{
				// Está tratando de acceder a donde no debe: se le expulsa y se le envía al login
				request.getSession().invalidate();
				response.sendRedirect(Constants.URI_USER_LOGIN);
				// response.sendRedirect(request.getContextPath() + "/index.jsp");

				return false;
			}
		}
		else // if (adminId != null)
		{
			// Administrador conectado
			if (uri.startsWith(Constants.URI_BASE_ADMIN))
			{
				if (uri.equals(Constants.URI_ADMIN_LOGIN))
				{
					// Un admin ya conectado quiere hacer login de nuevo: se le envía al inicio
					response.sendRedirect(Constants.URI_ADMIN_MAINMENU);
					return false;
				}
				
				return true;
			}
			else
			{
				// Está tratando de acceder a donde no debe: se le envía al inicio
				response.sendRedirect(Constants.URI_ADMIN_MAINMENU);

				return false;
			}
		}
	}
}