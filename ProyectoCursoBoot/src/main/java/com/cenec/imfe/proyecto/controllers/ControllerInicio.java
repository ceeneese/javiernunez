package com.cenec.imfe.proyecto.controllers;


import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.ModelAttrLoginData;
import com.cenec.imfe.proyecto.services.OperationResult;
import com.cenec.imfe.proyecto.services.ServiceAdministrador;
import com.cenec.imfe.proyecto.services.ServiceUsuario;
import com.cenec.imfe.proyecto.utils.LanguageUtils;

/**
 * Controlador para acceso al sistema (login) y cambio de idioma (redirección a página de inicio)
 */
@Controller
@SessionAttributes({Constants.SESSION_ATTR_USERID, Constants.SESSION_ATTR_ADMINID})
public class ControllerInicio
{
	@Autowired
	private ServiceUsuario srvcUsuario;
	
	@Autowired
	private ServiceAdministrador srvcAdmin;
	
 	@Autowired  
    private LanguageUtils messageSource;

 	/**
	 * Método encargado de recibir las peticiones de acceso como usuario
	 * 
	 * Este método es invocado desde el inicio de la aplicación web.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /user/login
	 *
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP de login de usuario
	 */
    @GetMapping(Constants.URI_USER_LOGIN)
    public String processUserLogin(Model model)
    {
    	try
    	{
    		ModelAttrLoginData usr = new ModelAttrLoginData();
    		model.addAttribute(Constants.MODEL_ATTR_USER, usr);
    		
        	return Constants.JSP_USER_LOGIN;
    	}
    	catch (Exception e)
    	{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_USER_ERROR;
    	}
    }
    
	/**
	 * Método encargado de recibir las peticiones de acceso como usuario
	 * 
	 * Este método es invocado desde la JSP de acceso de usuarios
	 * 
	 * Método HTTP: POST
	 * 
	 * URI de llamada: /user/login
	 *
	 * @param loginData Datos de acceso del usuario
	 * @param hasErrors Resultado de validación de datos
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP tras la operación
	 */
	@PostMapping(Constants.URI_USER_LOGIN)
	public String processUserLogin(@Valid @ModelAttribute(name=Constants.MODEL_ATTR_USER) ModelAttrLoginData loginData,
			BindingResult hasErrors, Model model, Locale locale)
	{
		if (hasErrors.hasErrors())
		{
			List<FieldError> errors = hasErrors.getFieldErrors();
			
			String errorStr = messageSource.getMessage("controller.init.userlogin.errorfields", null, locale);
			for (FieldError fe : errors)
			{
				errorStr = errorStr + " -" + fe.getField();
			}
			
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, errorStr);
			return Constants.JSP_USER_LOGIN;
		}

		try
		{
			OperationResult result = srvcUsuario.login(loginData.getNombre(), loginData.getClave());
			
			if (result.getOperationResult())
			{
				Integer userId = (Integer)result.getResultObject();

				// El userId es añadido a la sesión
				model.addAttribute(Constants.SESSION_ATTR_USERID, userId);
				
				// return "forward:" + Constants.URI_USER_DOCLIST;
				return "redirect:" + Constants.URI_USER_DOCLIST;
			}
			else
			{
				// Autenticación no válida
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, result.getError());
				return Constants.JSP_USER_LOGIN;
			}
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_USER_ERROR;
		}
	}
	
	/**
	 * Método encargado de recibir las peticiones de desconexión del usuario
	 * 
	 * Este método es invocado desde la JSP de listado de documentos de usuario
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /user/logout
	 *
	 * @param userId Identificador del usuario actualmente conectado en la sesión
	 * @param model Modelo de datos Req/Res de Spring
	 * @param sessionStatus Modelo de la sesión HTTP proporcionado por Spring
	 * @return Nombre de la JSP de acceso
	 */
	@GetMapping(Constants.URI_USER_LOGOUT)
	public String processUserLogout(@ModelAttribute(name=Constants.SESSION_ATTR_USERID) Integer userId, Model model, SessionStatus sessionStatus)
	{
		if (userId != null)
		{
			// Un usuario tiene la sesión iniciada: borrar la sesión
			sessionStatus.setComplete();
		}
		
		// En cualquier caso, nos vamos a UserLogin; el interceptor sabrá qué hacer con la sesión
		return "redirect:" + Constants.URI_USER_LOGIN;
	}
	
	/**
	 * Método encargado de recibir las peticiones de acceso como administrador
	 * 
	 * Este método es invocado desde la JSP de acceso como usuario (enlace para cambiar a modo administrador).
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/login
	 *
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP de login de administrador
	 */
	@GetMapping(Constants.URI_ADMIN_LOGIN)
	public String processAdminLogin(Model model)
    {
    	try
    	{
    		ModelAttrLoginData admin = new ModelAttrLoginData();
    		model.addAttribute(Constants.MODEL_ATTR_ADMIN, admin);
    		
        	return Constants.JSP_ADMIN_LOGIN;
    	}
    	catch (Exception e)
    	{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
    	}
    }

	/**
	 * Método encargado de recibir las peticiones de acceso como administrador
	 * 
	 * Este método es invocado desde la JSP de acceso de administrador
	 * 
	 * Método HTTP: POST
	 * 
	 * URI de llamada: /admin/login
	 * 
	 * @param loginData Datos de acceso del administrador
	 * @param hasErrors Resultado de validación de datos
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Nombre de la JSP tras la operación
	 */
	@PostMapping(Constants.URI_ADMIN_LOGIN)
	public String processAdminLogin(@Valid @ModelAttribute(name=Constants.MODEL_ATTR_ADMIN) ModelAttrLoginData loginData,
			BindingResult hasErrors, Model model, Locale locale)
	{
		if (hasErrors.hasErrors())
		{
			List<FieldError> errors = hasErrors.getFieldErrors();
			
			String errorStr = messageSource.getMessage("controller.init.adminlogin.errorfields", null, locale);
			for (FieldError fe : errors)
			{
				errorStr = errorStr + " -" + fe.getField();
			}
			
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, errorStr);
			return Constants.JSP_ADMIN_LOGIN;
		}

		try
		{
			OperationResult result = srvcAdmin.autenticar(loginData.getNombre(), loginData.getClave());
			
			if (result.getOperationResult())
			{
				Integer adminId = (Integer)result.getResultObject();

				// El adminId es añadido a la sesión
				model.addAttribute(Constants.SESSION_ATTR_ADMINID, adminId);

				// TODO ¿Es más correcto redirect a la URI (y así pasa por el interceptor) o envío directo de la JSP? 
				return "redirect:" + Constants.URI_ADMIN_MAINMENU;
				// return processAdminMainMenu();
			}
			else
			{
				// Autenticación no válida
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, result.getError());
				return processAdminLogin(model);
			}
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	/**
	 * Método invocado para mostrar el menú principal de administración
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/mainmenu
	 * 
	 * @return Nombre de la JSP de menú de administración
	 */
	@GetMapping(Constants.URI_ADMIN_MAINMENU)
	public String processAdminMainMenu()
	{
		return Constants.JSP_ADMIN_MAINMENU;
	}
	
	/**
	 * Método encargado de recibir las peticiones de desconexión del administrador
	 * 
	 * Este método es invocado desde la JSP de menú principal
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/logout
	 * 
	 * @param adminId Identificador del administrador actualmente conectado en la sesión
	 * @param model Modelo de datos Req/Res de Spring
	 * @param sessionStatus Modelo de la sesión HTTP proporcionado por Spring
	 * @return Nombre de la JSP de acceso como administrador
	 */
	@GetMapping(Constants.URI_ADMIN_LOGOUT)
	public String processAdminLogout(@ModelAttribute(name=Constants.SESSION_ATTR_ADMINID) Integer adminId, Model model, SessionStatus sessionStatus)
	{
		if (adminId != null)
		{
			// Un administrador tiene la sesión iniciada: borrar la sesión
			sessionStatus.setComplete();
		}
		
		// En cualquier caso, nos vamos a AdminLogin; el interceptor sabrá qué hacer con la sesión
		return "redirect:" + Constants.URI_ADMIN_LOGIN;
	}
	
}
