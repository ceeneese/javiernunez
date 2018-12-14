package com.cenec.imfe.proyecto.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.ModelAttrLoginUser;
import com.cenec.imfe.proyecto.services.OperationResult;
import com.cenec.imfe.proyecto.services.ServiceAdministrador;
import com.cenec.imfe.proyecto.services.ServiceUsuario;

/**
 * Controlador para acceso al sistema (login) y cambio de idioma (redirección a página de inicio)
 */
@Controller
public class ControllerInicio
{
	@Autowired
	private ServiceUsuario srvcUsuario;
	
	@Autowired
	private ServiceAdministrador srvcAdmin;
	

	@RequestMapping(value="/inicio", method=RequestMethod.GET)
    public String processLanguageChange(HttpServletRequest request, Model model)
	{
		try
		{
			// Se comprueba si se ha llegado aquí a partir de algún error pero la sesión ya fue iniciada
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
			
			if (userId == null)
			{
				// No hay sesión
				return Constants.JSP_USER_LOGIN;
			}
			
			// Sí hay sesión
			return "forward:user/doclist?userId=" + userId.toString();
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_USER_ERROR;
		}
    }
	
    @GetMapping(Constants.URI_USER_BASE + Constants.URI_USER_LOGIN)
    public String processUserLogin(Model model)
    {
    	try
    	{
    		ModelAttrLoginUser usr = new ModelAttrLoginUser();
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
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@PostMapping(Constants.URI_USER_BASE + Constants.URI_USER_LOGIN)
	public String processUserLogin(@ModelAttribute ModelAttrLoginUser modelAttrUser,
			HttpServletRequest request, Model model) throws Exception
	{
		// TODO ¿Qué hacer si ya hay sesión iniciada?
		
		// TODO ¿Se puede unificar este método con el 'processLanguageChange'?
		
		// TODO Podría usar un BindingResult y un parámetro de tipo 'LoginData' que contuviera usr/pwd y que fuese rellenado por Spring:form
		
		try
		{
			OperationResult result = srvcUsuario.login(modelAttrUser.getUsr(), modelAttrUser.getPwd());
			
			if (result.getOperationResult())
			{
				Integer userId = result.getAccess();

				// El userId es añadido a la sesión
				request.getSession().setAttribute(Constants.SESSION_ATTR_USERID, userId);

				// TODO Añadir filtros de sesión (interceptor)
				
				// return "forward:user/doclist";
				return "redirect:" + Constants.URI_USER_BASE + Constants.URI_USER_DOCLIST;
				
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

    @GetMapping(Constants.URI_ADMIN_BASE + Constants.URI_ADMIN_LOGIN)
    public String processAdminLogin(Model model)
    {
    	try
    	{
    		ModelAttrLoginUser usr = new ModelAttrLoginUser();
    		model.addAttribute(Constants.MODEL_ATTR_ADMIN, usr);
    		
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
	 * @throws Exception
	 */
	@PostMapping(Constants.URI_ADMIN_BASE + Constants.URI_ADMIN_LOGIN)
	public String processAdminLogin(@ModelAttribute ModelAttrLoginUser modelAttrAdmin,
			HttpServletRequest request, Model model) throws Exception
	{
		try
		{
			OperationResult result = srvcAdmin.autenticar(modelAttrAdmin.getUsr(), modelAttrAdmin.getPwd());
			
			if (result.getOperationResult())
			{
				Integer adminId = result.getAccess();

				// El adminId es añadido a la sesión
				request.getSession().setAttribute(Constants.SESSION_ATTR_ADMINID, adminId);

				// TODO Añadir filtros de sesión (interceptor)
				
				// return "redirect:" + Constants.URI_ADMIN_BASE + Constants.URI_ADMIN_MAINMENU;
				return processAdminMainMenu();
			}
			else
			{
				// Autenticación no válida
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, result.getError());
				return Constants.JSP_ADMIN_LOGIN;
			}
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	@GetMapping(Constants.URI_ADMIN_BASE + Constants.URI_ADMIN_MAINMENU)
	public String processAdminMainMenu()
	{
		return Constants.JSP_ADMIN_MAINMENU;
	}
}
