package com.cenec.imfe.proyecto;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;

import com.cenec.imfe.proyecto.interceptors.LoginInterceptor;
import com.cenec.imfe.proyecto.interceptors.MyOwnLanguageInterceptor;
import com.cenec.imfe.proyecto.utils.LanguageUtils;
import com.cenec.imfe.proyecto.utils.LanguageUtilsImpl;


/**
 * Configurador de la app
 * 
 * La configuración de acceso a datos se encuentra en application.properties
 */
@Configuration
public class SpringConfiguration implements WebMvcConfigurer
{
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(new Locale("es"));
	    return localeResolver;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor()
	{
		LocaleChangeInterceptor interceptor = new MyOwnLanguageInterceptor();
	    interceptor.setParamName("lang");
	    return interceptor;
	}
	
	@Bean
	public LoginInterceptor loginInterceptor()
	{
		return new LoginInterceptor();
	}
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
	    messageSource.setBasename("classpath:messages");
	    messageSource.setDefaultEncoding("LATIN1");
	    
	    // Se le establece el MessageSource de Spring para que LanguageUtils use la misma instancia creada como bean
	    LanguageUtilsImpl.getInstance().setSpringMessageSource(messageSource);
	    
	    return messageSource;
	}

	@Bean
	public LanguageUtils langUtils()
	{
		// TODO Este bean es a su vez singleton porque no sé cómo acceder a él
		// utilizando el contexto propio de Spring y lo necesito en el LocaleChangeInterceptor
		return LanguageUtilsImpl.getInstance();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
	    registry.addInterceptor(localeChangeInterceptor());
	    InterceptorRegistration reg = registry.addInterceptor(loginInterceptor());
	    
	    reg.excludePathPatterns("/css/*");
	    reg.excludePathPatterns("/images/*");
	    reg.excludePathPatterns("/js/*");
	}
	
	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tiles = new TilesConfigurer();
		tiles.setDefinitions(new String[] { "/WEB-INF/tiles.xml" });
		return tiles;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		// registry.jsp("/", ".jsp");
		registry.tiles();
	}
	
	/**
	 * Dirigir a index en inicio de app
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	    registry.addViewController("/").setViewName("forward:/index.jsp");
	}
}
