package com.cenec.imfe.proyecto.controllers;


import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cenec.imfe.proyecto.Constants;
import com.cenec.imfe.proyecto.model.DocumentInfo;
import com.cenec.imfe.proyecto.services.FileAccessorImplSpring;
import com.cenec.imfe.proyecto.services.ServiceDocumento;

/**
 * Controlador de administración encargado de atender las peticiones relacionadas con documentos
 *  
 * @author Javier
 */
@Controller
@RequestMapping(Constants.URI_BASE_ADMIN + Constants.URI_OVER_DOC)
public class ControllerAdminDocs
{
	// Ruta donde se almacenarán los documentos dentro del servidor
	private final static String DOCUMENTS_PATH = "documentos";
	
	@Autowired
	private ServiceDocumento srvcDocs;
	
	@Autowired
	private ServletContext context;

	/**
	 * Método encargado de recibir las peticiones de creación de un nuevo documento
	 * 
	 * Este método es invocado desde la JSP de menú principal.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/doc/new
	 * 
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP de edición de documentos
	 */
	@GetMapping(Constants.URI_OPERATION_NEW)
	public String processNewDoc(Model model)
	{
		return processEditDoc0(null, model);
	}
	
	/**
	 * Método encargado de recibir las peticiones de edición de un documento
	 * 
	 * Este método es invocado desde la JSP de menú principal.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/doc/edit
	 *
	 * @param docId Identificador del documento a editar
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP de edición de documentos
	 */
	@GetMapping(Constants.URI_OPERATION_EDIT)
	public String processEditDoc(@RequestParam Integer docId, Model model)
	{
		return processEditDoc0(docId, model);
	}

	/**
	 * Método para unificar las llamadas a 'new' y 'edit' de documentos
	 * 
	 * @param docId el identificador del documento a editar (null para nuevo documento)
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Llamada a la JSP de edición de usuarios
	 */
	private String processEditDoc0(Integer docId, Model model)
	{
		try
		{
			DocumentInfo doc = (docId == null ? new DocumentInfo() : srvcDocs.getDocument(docId));

			model.addAttribute(Constants.MODEL_ATTR_DOC, doc);
			
			return Constants.JSP_ADMIN_EDITDOC;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}

	/**
	 * Método encargado de recibir las peticiones de guardado de documentos
	 * 
	 * Este método es invocado desde la JSP de edición de documentos.
	 * 
	 * Método HTTP: POST
	 * 
	 * URI de llamada: /admin/doc/save 
	 * 
	 * @param doc Datos del documento
	 * @param result Resultado de validación de datos
	 * @param mpFile El fichero a almacenar
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@PostMapping(Constants.URI_OPERATION_SAVE)
	public String processSaveDoc(@Valid DocumentInfo doc, BindingResult result,
		@RequestParam(name="file") MultipartFile mpFile, Model model)
	{
		try
		{
			if (result.hasErrors())
			{
				// TODO Internacionalizar
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "Error en los datos del documento");
				
				if (doc.getIdDoc() == null)
				{
					return processNewDoc(model);
				}
				else
				{
					return processEditDoc(doc.getIdDoc(), model);
				}
			}
			
			if (doc.getIdDoc() != null)
			{
				// Actualización de archivo (sólo nombre), no archivo nuevo
				srvcDocs.updateDocument(doc);
			}
			
			if (mpFile != null && mpFile.getOriginalFilename() != null && !mpFile.getOriginalFilename().isEmpty())
			{
				String initPath = File.separator + DOCUMENTS_PATH + File.separator + mpFile.getOriginalFilename();
		        String filePath = context.getRealPath(initPath);
		        
		        FileAccessorImplSpring accessor = new FileAccessorImplSpring(mpFile, filePath);
		        
				srvcDocs.saveNewDocument(doc, accessor);

				// TODO Internacionalizar
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "El documento '" + doc.getName() + "' ha sido guardado");
			}
			else
			{
				model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, "No se ha incluido ningún documento");
			}
			
			return processListDocs(model);
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	/**
	 * Método encargado de recibir las peticiones de listado de documentos
	 * 
	 * Este método es invocado desde la JSP de menú principal
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/doc/list 
	 * 
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@GetMapping(Constants.URI_OPERATION_LIST)
	public String processListDocs(Model model)
	{
		try
		{
			List<DocumentInfo> docsList = srvcDocs.getDocuments();

			model.addAttribute(Constants.MODEL_ATTR_DOCSLIST, docsList);
			
			return Constants.JSP_ADMIN_LISTDOCS;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
	
	/**
	 * Método encargado de recibir las peticiones de borrado de documentos
	 * 
	 * Este método es invocado desde la JSP de listado de documentos.
	 * 
	 * Método HTTP: GET
	 * 
	 * URI de llamada: /admin/doc/delete 
	 * 
	 * @param docId Identificador del documento a borrar
	 * @param model Modelo de datos Req/Res de Spring
	 * @return Nombre de la JSP a invocar tras la operación
	 */
	@GetMapping(Constants.URI_OPERATION_DELETE)
	public String processDeleteDoc(@RequestParam Integer docId, Model model)
	{
		try
		{
			boolean deleted = srvcDocs.deleteDocument(docId);

			// TODO Internacionalizar
			String msg = (deleted ? "El documento ha sido borrado" : "El documento no pudo ser borrado");

			model.addAttribute(Constants.MODEL_ATTR_RESULTMSG, msg);
			
			return Constants.JSP_ADMIN_MAINMENU;
		}
		catch (Exception e)
		{
			model.addAttribute(Constants.MODEL_ATTR_ERROR, e);
			return Constants.JSP_ADMIN_ERROR;
		}
	}
}
