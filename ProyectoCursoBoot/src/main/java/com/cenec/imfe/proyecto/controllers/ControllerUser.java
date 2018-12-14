package com.cenec.imfe.proyecto.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.services.OperationResult;
import com.cenec.imfe.proyecto.services.ServiceDocumento;
import com.cenec.imfe.proyecto.services.ServiceUsuario;

@Controller
@RequestMapping("/user")
public class ControllerUser
{
	@Autowired
	private ServiceUsuario srvcUsuario;
	
	@Autowired
	private ServiceDocumento srvcDocs;

	/**
	 * Constructor
	 */
	public ControllerUser()
	{
		super();
	}

	/**
	 * Procesa la petición de listado de documentos para un usuario
	 */
	@GetMapping(Constants.URI_USER_DOCLIST)
	public String processList(HttpServletRequest request, Model model) throws Exception
	{
		try
		{
			// El userId es obtenido de la sesión
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
			
			// Controlar acceso directo a 'doclist' sin usuario en la sesión
			if (userId == null)
			{
				// TODO Internacionalizar
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "Es necesario iniciar sesión");
				return Constants.JSP_USER_LOGIN;
			}
			
			List<DocumentInfo> docs = srvcUsuario.getDocumentsForUser(userId);
			
			model.addAttribute(Constants.MODEL_ATTR_DOCSLIST, docs);
			
			return Constants.JSP_USER_LISTDOCS;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_USER_ERROR;
		}
	}

	/**
	 * Procesa la petición de descarga de un documento para un usuario
	 */
	@GetMapping("/download")
	public String processDownload(@RequestParam Integer docId, HttpServletRequest request, Model model) throws Exception
	{
		// El userId es obtenido de la sesión
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
		
		// Controlar acceso directo a 'download' sin usuario en la sesión
		if (userId == null)
		{
			// TODO Internacionalizar
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "Es necesario iniciar sesión");
			return Constants.JSP_USER_LOGIN;
		}
		
		if (docId == null)
		{
			// TODO Internacionalizar
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El documento no existe");
			return Constants.JSP_USER_LISTDOCS;
		}
		
		try
		{
			OperationResult result = srvcDocs.downloadDoc(docId);
			
			if (result.getOperationResult())
			{
				// TODO Internacionalizar
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El documento ha sido descargado");
			}
			else
			{
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, result.getError());
			}
			
			return Constants.JSP_USER_LISTDOCS;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_USER_ERROR;
		}
	}
}
