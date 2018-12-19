package com.cenec.imfe.proyecto.utils;

import java.util.Locale;

public interface LanguageUtils
{
	/**
	 * Cambia el lenguaje desde el que se obtendrán los mensajes por defecto
	 * @param language
	 */
	void changeDefaultLocale(String language);
	
	/**
 	 * Obtiene un mensaje desde la configuración Spring
 	 * 
	 * @param code
	 * @param paramns
	 * @param locale
	 * @return
	 */
	String getMessage(String code, Object[] paramns, Locale locale);
	
	/**
 	 * Obtiene un mensaje desde la configuración Locale por defecto
	 *  
	 * @param code
	 * @return
	 */
	String getMessageFromDefaultLocale(String code);
	
	/**
 	 * Obtiene un mensaje desde la configuración Locale por defecto
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	String getMessageFromDefaultLocale(String code, Object... args);

}
