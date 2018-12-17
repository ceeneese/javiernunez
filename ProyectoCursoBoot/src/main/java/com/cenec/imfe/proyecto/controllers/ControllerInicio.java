package com.cenec.imfe.proyecto.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	 * 
	 * @param modelAttrUser
	 * @param model
	 * @return
	 */
	@PostMapping(Constants.URI_USER_LOGIN)
	public String processUserLogin(@ModelAttribute(name=Constants.MODEL_ATTR_USER) ModelAttrLoginData loginData, Model model)
	{
		// TODO ¿Se puede unificar este método con el 'processLanguageChange'?
		
		// TODO ¿Podría usar un BindingResult para el tipo 'ModelAttrLoginUser' dado que este tipo no es persistente?
		
		try
		{
			OperationResult result = srvcUsuario.login(loginData.getUsr(), loginData.getPwd());
			
			if (result.getOperationResult())
			{
				Integer userId = result.getAccess();

				// El userId es añadido a la sesión
				model.addAttribute(Constants.SESSION_ATTR_USERID, userId);

				// return "forward:user/doclist";
				return "redirect:" + Constants.URI_USER_DOCLIST;
				
				// También podría hacerse la llamada directamente en Java:
//				return processList(userId, model);
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
	 * 
	 * @param userId
	 * @param model
	 * @param sessionStatus
	 * @return
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
		return "forward:" + Constants.URI_USER_LOGIN;
	}
	
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
	 * 
	 * @param adminName
	 * @param adminPwd
	 * @param model
	 * @return
	 */
	@PostMapping(Constants.URI_ADMIN_LOGIN)
	public String processAdminLogin(@ModelAttribute(name=Constants.MODEL_ATTR_ADMIN) ModelAttrLoginData loginData, Model model)
	{
		try
		{
			OperationResult result = srvcAdmin.autenticar(loginData.getUsr(), loginData.getPwd());
			
			if (result.getOperationResult())
			{
				Integer adminId = result.getAccess();

				// El adminId es añadido a la sesión
				model.addAttribute(Constants.SESSION_ATTR_ADMINID, adminId);

				// TODO ¿Es más correcto redirect a la URI (y así pasa por el interceptor) o envío directo de la JSP? 
				// return "redirect:" + Constants.URI_ADMIN_MAINMENU;
				return processAdminMainMenu();
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
	
	@GetMapping(Constants.URI_ADMIN_MAINMENU)
	public String processAdminMainMenu()
	{
		return Constants.JSP_ADMIN_MAINMENU;
	}
	
	@GetMapping(Constants.URI_ADMIN_LOGOUT)
	public String processAdminLogout(@ModelAttribute(name=Constants.SESSION_ATTR_ADMINID) Integer adminId, Model model, SessionStatus sessionStatus)
	{
		if (adminId != null)
		{
			// Un administrador tiene la sesión iniciada: borrar la sesión
			sessionStatus.setComplete();
		}
		
		// En cualquier caso, nos vamos a AdminLogin; el interceptor sabrá qué hacer con la sesión
		return "forward:" + Constants.URI_ADMIN_LOGIN;
	}
	
}
