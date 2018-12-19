package com.cenec.imfe.proyecto.interceptors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import com.cenec.imfe.proyecto.utils.LanguageUtilsImpl;

public class MyOwnLanguageInterceptor extends LocaleChangeInterceptor
{
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException {

		String newLocale = request.getParameter(getParamName());

		// TODO No sé cómo acceder a un Bean desde el contexto Spring, por lo que mi bean es a su vez singleton
		if (newLocale != null)
		{
			LanguageUtilsImpl.getInstance().changeDefaultLocale(newLocale);
		}
		
		return super.preHandle(request, response, handler);
	}
}
