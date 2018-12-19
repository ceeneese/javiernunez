package com.cenec.imfe.proyecto.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.springframework.context.MessageSource;

public class LanguageUtilsImpl implements LanguageUtils
{
	private static final String DEFAULT_LANGUAGE = "es";
	private static final String BUNDLE_BASENAME = "com.cenec.imfe.proyecto.utils.messagesBundle";
	private static LanguageUtilsImpl instance;
	
	private MessageSource springMessageSource;

    private ResourceBundle defaultMessageSource;

	/**
	 * Constructor privado (patrón Singleton)
	 */
	private LanguageUtilsImpl()
	{
		super();
		
		changeDefaultLocale(DEFAULT_LANGUAGE);
	}
	
	public void setSpringMessageSource(MessageSource messageSource)
	{
		this.springMessageSource = messageSource;
	}
	
	/**
	 * Método de acceso a la instancia única de LanguaageUtilsImpl.
	 * 
	 * @return Instancia única de la clase
	 */
	public synchronized static LanguageUtilsImpl getInstance()
	{
		if (instance == null)
		{
			instance = new LanguageUtilsImpl();
		}
		
		return instance;
	}
		
	@Override
	public void changeDefaultLocale(String language)
	{
		if (language != null)
		{
			Locale currentLocale = new Locale(language);
			
	        defaultMessageSource = ResourceBundle.getBundle(BUNDLE_BASENAME, currentLocale);   
		}
	}

	@Override
	public String getMessage(String code, Object[] params, Locale locale)
	{
		try
		{
			return springMessageSource.getMessage(code, params, locale);
		}
		catch (Exception e)
		{
			return code;
		}
	}
	
	@Override
	public String getMessageFromDefaultLocale(String code)
	{
		try
		{
			return defaultMessageSource.getString(code);
		}
		catch (Exception e)
		{
			return code;
		}
	}

	@Override
	public String getMessageFromDefaultLocale(String code, Object... args)
	{
		try
		{
			String pattern = defaultMessageSource.getString(code);
			return MessageFormat.format(pattern, args);
			
		}
		catch (Exception e)
		{
			return code;
		}
	}
}
