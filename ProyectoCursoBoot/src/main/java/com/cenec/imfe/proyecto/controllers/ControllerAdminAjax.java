package com.cenec.imfe.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.dao.AccessBy;
import com.cenec.imfe.proyecto.model.Usuario;
import com.cenec.imfe.proyecto.services.ServiceUsuario;

/**
 * Controlador de administración que se encarga de gestionar las peticiones vía AJAX
 * 
 * @author Javier
 */
@Controller
@RequestMapping(value = Constants.URI_BASE_ADMIN + Constants.URI_OVER_AJAX)
public class ControllerAdminAjax
{
	@Autowired
	private ServiceUsuario srvcUser;
	
	/**
	 * Método encargado de recibir las peticiones de chequeo de existencia de usuario web.
	 * 
	 * Este método es invocado desde la JSP de edición de usuarios.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/ajax/checkWebUser?webUserName={value} (paramétro dentro de la petición GET) 
	 * 
	 * @param webUsername El parámetro recibido desde la JSP que contiene el identificador de usuario web
	 * @param model Modelo de datos Req/Res de Spring
	 * @return AJAX_RESULT_AVAILABLE o AJAX_RESULT_UNAVAILABLE en función de si el identificador está disponible o no
	 */
	@RequestMapping(value=Constants.URI_OPERATION_CHECKWEBUSER, method=RequestMethod.GET)
    public ResponseEntity<Object> processCheckWebUser(@RequestParam String webUsername, Model model)
	{
		try
		{
			AccessBy access = new AccessBy.AccessByWebUsr(webUsername);
			Usuario usr = srvcUser.getUsuario(access);
			
			String result = (usr == null ? Constants.AJAX_RESULT_AVAILABLE : usr.getIdUsuario().toString());
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<Object>(Constants.AJAX_RESULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Método encargado de recibir las peticiones de chequeo de existencia de número de cliente
	 * 
	 * Este método es invocado desde la JSP de edición de usuarios. 
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/ajax/checkClientId/{clientId} (parámetro pasado como parte de la ruta)
	 * 
	 * @param clientId El parámetro recibido desde la JSP que contiene el identificador de cliente
	 * @param model Modelo de datos Req/Res de Spring
	 * @return AJAX_RESULT_AVAILABLE o AJAX_RESULT_UNAVAILABLE en función de si el identificador está disponible o no
	 */
	@RequestMapping(value=Constants.URI_OPERATION_CHECKCLIENTID + "/{clientId}", method=RequestMethod.GET)
    public ResponseEntity<Object> processCheckClientId(@PathVariable Integer clientId, Model model)
	{
		try
		{
			AccessBy access = new AccessBy.AccessByClient(clientId);
			Usuario usr = srvcUser.getUsuario(access);
			
			String result = (usr == null ? Constants.AJAX_RESULT_AVAILABLE : Constants.AJAX_RESULT_UNAVAILABLE);
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}
		catch (Exception e)
		{
			return new ResponseEntity<Object>(Constants.AJAX_RESULT_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
