package com.cenec.imfe.proyecto.controllers;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.services.DownloadableFileBundle;
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
	private static final int DOWNLOAD_ERROR_DOCUMENTNOTEXISTS = 404;
	private static final int DOWNLOAD_ERROR_SERVICEERROR = 500;
	
	private static final String URI_PARAM_MSGCODE = "msgCode";

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
	 * Procesa la petición de error durante la descarga de un documento
	 * 
	 * Este método es invocado desde el método de gestión de descargas, su URI no se invoca desde
	 * la GUI del usuario.
	 * 
	 * En la URI se incluye un parámetro 'msgCode' para indicar el mensaje de error que se mostrará
	 * al usuario.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /user/downloaderror
	 *
	 * @param docId El identificador del documento a descargar
	 * @param request Petición HTTP
	 * @param model Modelo de datos Req/Res de Spring
	 * @param locale Identificador de localización para internacionalización de mensajes
	 * @return Página JSP de error de descarga
	 */
	@GetMapping(Constants.URI_OPERATION_DOWNLOADERROR)
	public String processDownloadError(@RequestParam(name=URI_PARAM_MSGCODE) int msgCode, Model model, Locale locale)
	{
		String code;
		switch (msgCode)
		{
			case DOWNLOAD_ERROR_DOCUMENTNOTEXISTS :
				code = "controller.user.download.docnotexists";
				break;
				
			case DOWNLOAD_ERROR_SERVICEERROR :
			default:
				code = "controller.user.download.serviceerror";
				break;
		}
		
		model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, messageSource.getMessage(code, null, locale));
		
		return Constants.JSP_USER_DOWNLOADERROR;
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
	public ResponseEntity<InputStreamResource> processDownload(@RequestParam Integer docId,
			HttpServletRequest request, HttpServletResponse res, Model model)
	{
		//TODO Lo correcto sería responder con un ResponseEntity también en caso de error
		
		try
		{
			if (docId == null)
			{
				// return ResponseEntity.notFound().build();
	
				res.sendRedirect(Constants.URI_BASE_USER +
					Constants.URI_OPERATION_DOWNLOADERROR + "?" + URI_PARAM_MSGCODE + "=" + DOWNLOAD_ERROR_DOCUMENTNOTEXISTS);
				return null;
			}
		
			OperationResult result = srvcDocs.downloadDoc(docId);
			
			if (result.getOperationResult())
			{
				DownloadableFileBundle dfb = (DownloadableFileBundle)result.getResultObject();
				
				MediaType mediaType = getMediaTypeForFileName(request.getServletContext(), dfb.getFileName());

				return ResponseEntity.ok()
	                // Content-Disposition
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + dfb.getFileName())
	                // Content-Type
	                .contentType(mediaType)
	                // Contet-Length
	                .contentLength(dfb.getFileLength()) //
	                .body(dfb.getStream());
			}
			else
			{
				// Al pasar código de error por la URI se pierde el mensaje de error recibido desde el servicio
//				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, result.getError());
				
//				return ResponseEntity.notFound().build();

				res.sendRedirect(Constants.URI_BASE_USER +
						Constants.URI_OPERATION_DOWNLOADERROR + "?" + URI_PARAM_MSGCODE + "=" + DOWNLOAD_ERROR_SERVICEERROR);
				return null;
			}
		}
		catch (Exception e)
		{
			try
			{
				res.sendRedirect(Constants.URI_BASE_USER +
						Constants.URI_OPERATION_DOWNLOADERROR + "?" + URI_PARAM_MSGCODE + "=" + DOWNLOAD_ERROR_SERVICEERROR);
			}
			catch (Exception e2) {} // No se puede hacer más...

			return null;
		}
	}
	
	private MediaType getMediaTypeForFileName(ServletContext context, String fileName)
	{
		// application/pdf - application/xml - image/gif ...
		String mimeType = context.getMimeType(fileName);
		try
		{
			return MediaType.parseMediaType(mimeType);
		}
		catch (Exception e)
		{
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

}
