package com.cenec.imfe.proyecto.controllers;

import java.util.List;
import java.util.Locale;

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
import com.cenec.imfe.proyecto.utils.LanguageUtils;

/**
 * Controlador encargado de atender las peticiones de usuarios
 * 
 * @author Javier
 */
@Controller
@RequestMapping(Constants.URI_BASE_USER)
public class ControllerUser
{
	@Autowired
	private ServiceUsuario srvcUsuario;
	
	@Autowired
	private ServiceDocumento srvcDocs;

 	@Autowired  
    private LanguageUtils messageSource;

 	/**
	 * Constructor
	 */
	public ControllerUser()
	{
		super();
	}

	/**
	 * Procesa la petición de listado de documentos para un usuario
	 * 
	 * Este método es invocado tras el acceso de un usuario al sistema
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /user/doclist
	 *
	 * @param request Petición HTTP
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return
	 */
	@GetMapping(Constants.URI_OPERATION_DOCLIST)
	public String processList(HttpServletRequest request, Model model, Locale locale)
	{
		try
		{
			// El userId es obtenido de la sesión (nota: esto es por probar diferentes formas de
			// acceso a la sesión, en otros casos he accedido a atributos de sesión a través de Model)
			Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
			
			// Controlar acceso directo a 'doclist' sin usuario en la sesión
			// (NOTA: esto ya lo hace el interceptor; esto sería otra forma de hacerlo)
			if (userId == null)
			{
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.user.list.sessionrequired", null, locale));
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
	 * 
	 * Este método es invocado desde el listado de documentos disponibles para el usuario
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /user/download
	 *
	 * @param docId El identificador del documento a descargar
	 * @param request Petición HTTP
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Página JSP de listado de documentos
	 */
	@GetMapping(Constants.URI_OPERATION_DOWNLOAD)
	public String processDownload(@RequestParam Integer docId, HttpServletRequest request, Model model, Locale locale)
	{
		// El userId es obtenido de la sesión
		Integer userId = (Integer)request.getSession().getAttribute(Constants.SESSION_ATTR_USERID);
		
		// Controlar acceso directo a 'download' sin usuario en la sesión
		// (NOTA: esto ya lo hace el interceptor; esto sería otra forma de hacerlo)
		if (userId == null)
		{
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.user.download.sessionrequired", null, locale));
			return Constants.JSP_USER_LOGIN;
		}
		
		if (docId == null)
		{
			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.user.download.docnotexists", null, locale));
			return Constants.JSP_USER_LISTDOCS;
		}
		
		try
		{
			OperationResult result = srvcDocs.downloadDoc(docId);
			
			if (result.getOperationResult())
			{
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage("controller.user.download.ok", null, locale));
			}
			else
			{
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, result.getError());
			}
			
			return processList(request, model, locale);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_USER_ERROR;
		}
	}
}
